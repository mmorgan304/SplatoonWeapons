import json
import joblib
import numpy as np
import pandas as pd

# =====================================================================
# 1. GLOBAL INITIALIZATION (Runs once when Lambda container spins up)
# =====================================================================
print("Loading model and metadata...")
model = joblib.load('rf_splatoon_model.joblib')
trained_features = model.feature_names_in_

with open('model_metadata.json', 'r') as f:
    metadata = json.load(f)

all_subs = metadata["all_subs"]
all_specials = metadata["all_specials"]
fallback_defaults = metadata["defaults"]
rich_kit_dict = metadata["kit_dict"]
print("Lambda is warm and ready!")


# =====================================================================
# 2. FEATURE IMPORTANCE EXTRACTOR
# =====================================================================
def get_top_contributing_features(model, final_scenario_df, trained_features, top_n=5):
    """
    Finds the features that the model cares about most which were also
    highly active/pushed in favor of this specific team composition.
    """
    # Get the global importance weights assigned by the Random Forest
    importances = model.feature_importances_

    # Get the actual feature values for our optimized row
    row_values = final_scenario_df.iloc[0].values

    # Multiply importance by feature value to find what drove *this* layout
    # (Using absolute value of row data so large negative or positive differentials show impact)
    impact_scores = importances * np.abs(row_values)

    # Sort and grab the top indices
    top_indices = np.argsort(impact_scores)[::-1][:top_n]

    explanation_features = []
    for idx in top_indices:
        feat_name = trained_features[idx]
        feat_val = row_values[idx]
        explanation_features.append(f"{feat_name} (Value: {feat_val:.2f})")

    return explanation_features


# =====================================================================
# 3. YOUR OPTIMIZATION ALGORITHM (Slightly modified to return the row)
# =====================================================================
def find_optimal_team_comp_v3(weapon_pools_per_slot, target_mode, target_stage, bravo_team=None, max_iter=5):
    # Pull the first valid weapon from EACH slot's specific pool
    optimized_alpha_team = [pool[0] if pool else "liter4k_scope" for pool in weapon_pools_per_slot]

    match_scenario_base = pd.DataFrame(0, index=[0], columns=trained_features)
    mode_col, stage_col = f"mode_{target_mode}", f"stage_{target_stage}"

    if mode_col not in match_scenario_base.columns or stage_col not in match_scenario_base.columns:
        return None, 0, None

    match_scenario_base[mode_col] = 1
    match_scenario_base[stage_col] = 1

    b_paint, b_pts, b_weight = 0, 0, 0
    b_max_damage, b_min_damage, b_ink_efficiency, b_effective_range, b_has_oneshot = -np.inf, np.inf, 0, -np.inf, False
    b_sub_counts = {s: 0 for s in all_subs}
    b_spec_counts = {sp: 0 for sp in all_specials}

    if bravo_team:
        for weapon in bravo_team:
            bravo_col = f"Bravo_has_{weapon}"
            if bravo_col in match_scenario_base.columns:
                match_scenario_base[bravo_col] += 1

            stats = rich_kit_dict.get(weapon, fallback_defaults)
            b_paint += stats["avg_paint"]
            b_pts += stats["special_pts"]
            b_weight += stats["weight"]
            if stats["range"] > b_max_damage: b_max_damage = stats["range"] # mapping safely based on your kit logic
            if stats["sub_id"] in b_sub_counts: b_sub_counts[stats["sub_id"]] += 1
            if stats["special_id"] in b_spec_counts: b_spec_counts[stats["special_id"]] += 1

    overall_best_probability = -1
    final_test_scenario = match_scenario_base.copy()

    for iteration in range(max_iter):
        has_changed = False
        for slot in range(4):
            best_prob_for_slot = -1
            best_weapon_for_slot = optimized_alpha_team[slot]

            for weapon in weapon_pools_per_slot[slot]:
                proposed_team = list(optimized_alpha_team)
                proposed_team[slot] = weapon

                test_scenario = match_scenario_base.copy()

                for w in proposed_team:
                    alpha_col = f"Alpha_has_{w}"
                    if alpha_col in test_scenario.columns:
                        test_scenario[alpha_col] += 1

                a_paint, a_pts, a_weight = 0, 0, 0
                a_sub_counts = {s: 0 for s in all_subs}
                a_spec_counts = {sp: 0 for sp in all_specials}

                for w in proposed_team:
                    stats = rich_kit_dict.get(w, fallback_defaults)
                    a_paint += stats["avg_paint"]
                    a_pts += stats["special_pts"]
                    a_weight += stats["weight"]
                    if stats["sub_id"] in a_sub_counts: a_sub_counts[stats["sub_id"]] += 1
                    if stats["special_id"] in a_spec_counts: a_spec_counts[stats["special_id"]] += 1

                test_scenario["Diff_total_paint"] = a_paint - b_paint
                test_scenario["Diff_total_spec_pts"] = a_pts - b_pts
                test_scenario["Diff_mean_weight"] = (a_weight / 4.0) - (b_weight / 4.0 if bravo_team else 0)

                for s in all_subs:
                    col = f"Diff_count_sub_{s}"
                    if col in test_scenario.columns: test_scenario[col] = a_sub_counts[s] - b_sub_counts[s]
                for sp in all_specials:
                    col = f"Diff_count_spec_{sp}"
                    if col in test_scenario.columns: test_scenario[col] = a_spec_counts[sp] - b_spec_counts[sp]

                win_probability = model.predict_proba(test_scenario)[0][1]

                if win_probability > best_prob_for_slot:
                    best_prob_for_slot = win_probability
                    best_weapon_for_slot = weapon

            if optimized_alpha_team[slot] != best_weapon_for_slot:
                optimized_alpha_team[slot] = best_weapon_for_slot
                has_changed = True

            overall_best_probability = best_prob_for_slot
            final_test_scenario = test_scenario # Keep track of the winning row configuration

        if not has_changed:
            break

    return optimized_alpha_team, overall_best_probability, final_test_scenario


# =====================================================================
# 4. LAMBDA HANDLER (API Entry Point)
# =====================================================================
def lambda_handler(event, context):
    try:
        # If your API Gateway passes JSON directly, parsing varies slightly.
        # Assuming direct event payload mapping:
        body = json.loads(event.get('body', '{}')) if 'body' in event else event

        target_mode = body.get('mode', 'area')
        target_stage = body.get('stage', 'ama')
        bravo_team = body.get('bravo_team', []) # Enemy composition

        # Expecting an array of 4 arrays containing weapon secret names
        weapon_pools = body.get('weapon_pools', [[], [], [], []])

        # Run optimization
        best_team, win_rate, final_row = find_optimal_team_comp_v3(
            weapon_pools_per_slot=weapon_pools,
            target_mode=target_mode,
            target_stage=target_stage,
            bravo_team=bravo_team
        )

        if best_team is None:
            return {
                "statusCode": 400,
                "body": json.dumps({"error": "Invalid mode or stage context provided."})
            }

        # Extract features driving this decision
        top_features = get_top_contributing_features(model, final_row, trained_features, top_n=5)

        # Format Response for Java Back-end
        response_payload = {
            "recommendedTeam": {
                "player1Pool": [best_team[0]],
                "player2Pool": [best_team[1]],
                "player3Pool": [best_team[2]],
                "player4Pool": [best_team[3]]
            },
            "features": top_features,
            "projectedWinRate": float(win_rate)
        }

        return {
            "statusCode": 200,
            "headers": {"Content-Type": "application/json"},
            "body": json.dumps(response_payload)
        }

    except Exception as e:
        return {
            "statusCode": 500,
            "body": json.dumps({"error": str(e)})
        }