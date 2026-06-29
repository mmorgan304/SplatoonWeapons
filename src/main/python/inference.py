# team recommender script
# takes the user weapon submission and runs them against the Extra Trees model to determine the best comp
# inputs: alpha team weapons (as secret_name), stage, mode, bravo team comp (as secret_names)
# outputs: optimized team alpha (as English names), top 5 human-readable advantages, top 5 human-readable deficits, predicted win percent

import json
import os
import re

import joblib
import numpy as np
import pandas as pd

# Name conversion dictionary for specs and subs
SPECIAL_NAMES = {
    1: "trizooka", 2: "big_bubbler", 3: "zipcaster", 4: "tenta_missiles",
    5: "ink_storm", 6: "booyah_bomb", 7: "kraken_royale", 8: "wave_breaker",
    9: "ink_vac", 10: "killer_wail_5_1", 11: "inkjet", 12: "ultra_stamp",
    13: "crab_tank", 14: "reefslider", 15: "triple_inkstrike", 16: "tacticooler",
    17: "super_chump", 18: "splattercolor_screen", 19: "triple_splashdown"
}

SUB_NAMES = {
    1: "splat_bomb", 2: "suction_bomb", 3: "burst_bomb", 4: "curling_bomb",
    5: "autobomb", 6: "ink_mine", 7: "toxic_mist", 8: "point_sensor",
    9: "splash_wall", 10: "sprinkler", 11: "squid_beakon", 12: "fizzy_bomb",
    13: "torpedo", 14: "angle_shooter"
}

def load_dictionary(filename):
    script_dir = Path(__file__).resolve().parent
    file_path = script_dir / "dictionary" / filename
    try:
        with open(file_path, "r", encoding="utf-8") as f:
            return json.load(f)
    except FileNotFoundError:
        print(f"⚠️ Warning: Could not find dictionary file at: {file_path}")
        return {} # Return an empty dictionary fallback

WEAPON_NAMES = load_dictionary("weapon_names.json")

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
print("Lambda is ready")


# =====================================================================
# 2. FEATURE IMPORTANCE EXTRACTOR
# =====================================================================
def get_structured_features(model, final_scenario_df, trained_features, top_n=3):
    importances = model.feature_importances_
    row_values = final_scenario_df.iloc[0].values

    # Calculate raw signed impact (keep the positive/negative direction)
    signed_impacts = importances * row_values

    # Get indices sorted by the raw value (highest positive to lowest negative)
    sorted_indices = np.argsort(signed_impacts)

    # Top Positive Drivers (Alpha Advantages) - End of the sorted array
    positive_indices = [idx for idx in sorted_indices[::-1] if row_values[idx] > 0][:top_n]

    # Top Negative Drivers (Alpha Deficits) - Beginning of the sorted array
    negative_indices = [idx for idx in sorted_indices if row_values[idx] < 0][:top_n]

    alpha_advantages = [f"{trained_features[i]} ({row_values[i]:.2f})" for i in positive_indices]
    alpha_deficits = [f"{trained_features[i]} ({row_values[i]:.2f})" for i in negative_indices]

    return {
        "advantages": alpha_advantages,
        "deficits": alpha_deficits
    }

# =====================================================================
# 3. OPTIMIZATION ALGORITHM
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

    if bravo_team: # only one weapon per bravo slot
        for weapon in bravo_team:
            bravo_col = f"Bravo_has_{weapon}"
            if bravo_col in match_scenario_base.columns:
                match_scenario_base[bravo_col] += 1

            stats = rich_kit_dict.get(weapon, fallback_defaults)
            b_paint += stats["avg_paint"]
            b_pts += stats["special_pts"]
            b_weight += stats["weight"]
            if stats["range"] > b_max_damage: b_max_damage = stats["range"]
            if stats["sub_id"] in b_sub_counts: b_sub_counts[stats["sub_id"]] += 1
            if stats["special_id"] in b_spec_counts: b_spec_counts[stats["special_id"]] += 1

    overall_best_probability = -1
    final_test_scenario = match_scenario_base.copy()

    # iterate over the weapon pool to maximize win %
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
            final_test_scenario = test_scenario  # Keep track of the winning row configuration

        if not has_changed:
            break

    return optimized_alpha_team, overall_best_probability, final_test_scenario

# converting the ET feature ranking into a human/Groq readable description
def humanize_feature_name(item_string):
    # 1. Separate the feature name from the (value)
    match = re.search(r"(.*) \(([-0-9.]+)\)", item_string)
    if not match:
        return item_string

    raw_feature = match.group(1)
    val = match.group(2)

    # 2. Map Sub and Special IDs to their formatted names (e.g. "ultra_stamp" -> "Ultra Stamp")
    spec_match = re.search(r'spec_(\d+)', raw_feature, re.IGNORECASE)
    sub_match = re.search(r'sub_(\d+)', raw_feature, re.IGNORECASE)

    if spec_match:
        spec_id = int(spec_match.group(1))
        # Title case to make it pretty!
        name = SPECIAL_NAMES.get(spec_id, f"Special {spec_id}").replace("_", " ").title()
        raw_feature = re.sub(r'spec_\d+', name, raw_feature, flags=re.IGNORECASE)
    elif sub_match:
        sub_id = int(sub_match.group(1))
        name = SUB_NAMES.get(sub_id, f"Sub {sub_id}").replace("_", " ").title()
        raw_feature = re.sub(r'sub_\d+', name, raw_feature, flags=re.IGNORECASE)

    # 3. Humanize common ML prefixes for RAG consumption
    replacements = {
        "Diff_count_": "Difference in Count: ",
        "Diff_total_": "Difference in Total ",
        "Diff_mean_": "Difference in Average ",
        "mode_area": "Mode: Splat Zones",
        "mode_yagura": "Mode: Tower Control",
        "mode_hoko": "Mode: Rainmaker",
        "mode_asari": "Mode: Clam Blitz",
        "paint": "Paint",
        "weight": "Weapon Weight"
    }

    readable = raw_feature
    for old, new in replacements.items():
        readable = readable.replace(old, new)

    # Clean up any leftover underscores just in case
    readable = readable.replace("_", " ").strip()

    # 4. Stitch it back together
    return f"{readable} ({val})"

def recommend_team(body):
    # 1. Extract base fields matching Java
    target_mode = body.get('mode', 'area')
    target_stage = body.get('stage', 'amabi')

    # 2. Navigate nested objects created by Java Jackson
    alpha_pool_obj = body.get('alphaTeamPool', {})
    bravo_pool_obj = body.get('bravoTeamPool', {})

    # Enemy composition
    bravo_team = []
    for i in range(1, 5):
        pool = bravo_pool_obj.get(f'player{i}Pool', [])
        if pool and len(pool) > 0:
            bravo_team.append(pool[0])

    # Extract the 4 pool tracks from the alpha pool object
    weapon_pools = [
        alpha_pool_obj.get('player1Pool', []),
        alpha_pool_obj.get('player2Pool', []),
        alpha_pool_obj.get('player3Pool', []),
        alpha_pool_obj.get('player4Pool', [])
    ]

    # 3. Run optimization
    best_team, win_rate, final_row = find_optimal_team_comp_v3(
        weapon_pools_per_slot=weapon_pools,
        target_mode=target_mode,
        target_stage=target_stage,
        bravo_team=bravo_team
    )

    if best_team is None:
        raise ValueError("Invalid mode or stage context provided.")

    # 4. Extract feature importance
    top_features = get_structured_features(
        model,
        final_row,
        trained_features,
        top_n=5
    )

    # Convert feature names
    humanized_features = {}
    for category, feature_list in top_features.items():
        if isinstance(feature_list, list):
            humanized_features[category] = [humanize_feature_name(item) for item in feature_list]
        else:
            humanized_features[category] = feature_list  # Fallback to prevent crashes

    # 5. Translate Secret Names -> Human Names
    # We use `.get()` to fallback to the original string if it somehow isn't in our dictionary
    player_1_readable = WEAPON_NAMES.get(best_team[0], best_team[0])
    player_2_readable = WEAPON_NAMES.get(best_team[1], best_team[1])
    player_3_readable = WEAPON_NAMES.get(best_team[2], best_team[2])
    player_4_readable = WEAPON_NAMES.get(best_team[3], best_team[3])

    # 6. Return the perfectly mapped payload
    return {
        "recommendedTeam": {
            "player1Pool": [player_1_readable],
            "player2Pool": [player_2_readable],
            "player3Pool": [player_3_readable],
            "player4Pool": [player_4_readable]
        },
        "features": humanized_features,
        "projectedWinRate": float(win_rate)
    }

# ==========================================
# LOCAL TESTING BLOCK
# ==========================================
if __name__ == "__main__":
    import traceback
    import json

    # 1. Create a fake payload mimicking your Java backend's Jackson JSON
    mock_body = {
        "mode": "area",
        "stage": "amabi",
        "alphaTeamPool": {
            "player1Pool": ["52gal", "sshooter"],
            "player2Pool": ["wakaba", "bold"],
            "player3Pool": ["prime", "96gal"],
            "player4Pool": ["liter4k", "splatcharger"]
        },
        "bravoTeamPool": {
            "weaponSecretNames": ["sshooter", "wakaba", "splatroller", "liter4k"]
        }
    }

    print("--- Starting Local Test ---")
    try:
        # 2. Call the function (make sure your model and find_optimal_team_comp_v3 are loaded above!)
        result = recommend_team(mock_body)

        # 3. Print the successful result
        print("Success! Here is the JSON payload:")
        print(json.dumps(result, indent=2))

    except Exception as e:
        # 4. If it crashes, print the exact line and error message
        print("\n!!! CRASH DETECTED !!!")
        traceback.print_exc()
