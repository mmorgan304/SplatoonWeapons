
import csv
import numpy as np
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score, classification_report
from sklearn.model_selection import train_test_split
import os

# ==========================================
# 1. PARSE BROKEN CSV & BUILD WEAPON KITS DICTIONARY
# ==========================================
weapons_data = []

weapons_csv_path = r"C:\Users\Melissa\Desktop\Splatoon Weapons\SplatoonWeapons\pandas test folder\sp_weapons_removed_firing_modes.csv"
with open(weapons_csv_path, "r", encoding="utf-8") as f:
    header = f.readline().strip().split(",")[:11]  # Keep first 11 clean columns
    for line in f:
        line = line.strip()
        if not line:
            continue
        parts = line.split(',"[')  # Isolate core attributes from malformed array
        if len(parts) >= 1:
            row = list(csv.reader([parts[0]]))[0]
            weapons_data.append(row)

# Convert to DataFrame and fix numeric structural types
param_df = pd.DataFrame(weapons_data, columns=header)
cols_to_numeric = [
    "subweapon_id",
    "special_weapon_id",
    "weight_id",
    "special_points",
    "range_actual",
    "avg_paint_per_match",
]
for col in cols_to_numeric:
    param_df[col] = pd.to_numeric(param_df[col], errors="coerce")

# Clean tracking fields
param_df["weapon_name"] = param_df["weapon_name"].str.strip()
param_df["secret_name"] = param_df["secret_name"].str.strip()

# Collect unique system-wide items for one-hot vector matrices
all_subs = sorted(param_df["subweapon_id"].dropna().unique().astype(int))
all_specials = sorted(param_df["special_weapon_id"].dropna().unique().astype(int))

# Build global fallback profile defaults for missing data entries
defaults = {
    "avg_paint": param_df["avg_paint_per_match"].mean(),
    "range": param_df["range_actual"].mean(),
    "special_pts": param_df["special_points"].mean(),
    "weight": param_df["weight_id"].mean(),
    "sub_id": -1,
    "special_id": -1,
}

# Map keys to kits
kit_dict = {}
for _, row in param_df.iterrows():
    stats = {
        "avg_paint": row["avg_paint_per_match"],
        "range": row["range_actual"],
        "special_pts": row["special_points"],
        "weight": row["weight_id"],
        "sub_id": int(row["subweapon_id"]),
        "special_id": int(row["special_weapon_id"]),
    }
    kit_dict[row["secret_name"]] = stats
    kit_dict[row["weapon_name"]] = stats

# ==========================================
# 2. LOAD AND SPLIT BASE SPLATOON MATCH DATA
# ==========================================

# Dynamically load all CSV files that match the 'YYYY-MM-DD.csv' pattern
data_frames = []
file_count = 0
battle_results_dir = r"C:\Users\Melissa\Desktop\Splatoon Weapons\SplatoonWeapons\pandas test folder\battle_results"
for filename in os.listdir(battle_results_dir):
    if filename.endswith('.csv') and len(filename) == 14 and filename[4] == '-' and filename[7] == '-':
        file_path = os.path.join(battle_results_dir, filename)
        try:
            df = pd.read_csv(file_path, on_bad_lines='skip', low_memory=False)
            data_frames.append(df)
        except Exception as e:
            print(f"Error reading {filename}: {e}")

splatoon_data = pd.concat(data_frames, ignore_index=True)

allowed_modes = ["yagura", "hoko", "area", "asari"]
splatoon_data = splatoon_data[splatoon_data["mode"].isin(allowed_modes)]
print(f"Number of rows in splatoon_data: {len(splatoon_data)}")
weapon_cols = [
    "A1-weapon",
    "A2-weapon",
    "A3-weapon",
    "A4-weapon",
    "B1-weapon",
    "B2-weapon",
    "B3-weapon",
    "B4-weapon",
]
splatoon_data = splatoon_data.dropna(
    subset=["alpha-count", "bravo-count"] + weapon_cols
)

y = (splatoon_data["alpha-count"] > splatoon_data["bravo-count"]).astype(int)
X_context = pd.get_dummies(splatoon_data[['mode', 'stage']])

# Tally raw weapon instances
all_weapons = [
    w
    for w in pd.unique(splatoon_data[weapon_cols].values.ravel())
    if pd.notna(w)
]
alpha_counts = pd.DataFrame(
    0, index=splatoon_data.index, columns=[f"Alpha_has_{w}" for w in all_weapons]
)
bravo_counts = pd.DataFrame(
    0, index=splatoon_data.index, columns=[f"Bravo_has_{w}" for w in all_weapons]
)

alpha_slots = ["A1-weapon", "A2-weapon", "A3-weapon", "A4-weapon"]
bravo_slots = ["B1-weapon", "B2-weapon", "B3-weapon", "B4-weapon"]

for weapon in all_weapons:
    for slot in alpha_slots:
        alpha_counts[f"Alpha_has_{weapon}"] += (
            (splatoon_data[slot] == weapon).astype(int)
        )
    for slot in bravo_slots:
        bravo_counts[f"Bravo_has_{weapon}"] += (
            (splatoon_data[slot] == weapon).astype(int)
        )

# ==========================================
# 3. COMPUTE DYNAMIC SUB and SPECIAL
# ==========================================
print("Deconstructing weapon rosters into sub and special combinations...")


def process_match_kits(df, slots, all_subs, all_specials):
    sub_mat = np.zeros((len(df), len(all_subs)))
    spec_mat = np.zeros((len(df), len(all_specials)))

    sub_lookup = {s: i for i, s in enumerate(all_subs)}
    spec_lookup = {sp: i for i, sp in enumerate(all_specials)}

    paint_vals, max_ranges, spec_points, weights = [], [], [], []

    for _, row in df.iterrows():
        p_tot, r_max, s_tot, w_tot = 0, 0, 0, 0
        for slot in slots:
            stats = kit_dict.get(row[slot], defaults)
            p_tot += stats["avg_paint"]
            s_tot += stats["special_pts"]
            w_tot += stats["weight"]
            if stats["range"] > r_max:
                r_max = stats["range"]

            if stats["sub_id"] in sub_lookup:
                sub_mat[len(paint_vals), sub_lookup[stats["sub_id"]]] += 1
            if stats["special_id"] in spec_lookup:
                spec_mat[len(paint_vals), spec_lookup[stats["special_id"]]] += 1

        paint_vals.append(p_tot)
        max_ranges.append(r_max)
        spec_points.append(s_tot)
        weights.append(w_tot / 4.0)

    return sub_mat, spec_mat, paint_vals, max_ranges, spec_points, weights


# Extract Alpha & Bravo Metrics
a_sub, a_spec, a_paint, a_range, a_pts, a_weight = process_match_kits(
    splatoon_data, alpha_slots, all_subs, all_specials
)
b_sub, b_spec, b_paint, b_range, b_pts, b_weight = process_match_kits(
    splatoon_data, bravo_slots, all_subs, all_specials
)

# Convert arrays directly to strategic team differentials (Alpha - Bravo)
df_diffs = pd.DataFrame(index=splatoon_data.index)
df_diffs["Diff_total_paint"] = np.array(a_paint) - np.array(b_paint)
df_diffs["Diff_max_range"] = np.array(a_range) - np.array(b_range)
df_diffs["Diff_total_spec_pts"] = np.array(a_pts) - np.array(b_pts)
df_diffs["Diff_mean_weight"] = np.array(a_weight) - np.array(b_weight)

# Generate specific layout category count columns
for i, s in enumerate(all_subs):
    df_diffs[f"Diff_count_sub_{s}"] = a_sub[:, i] - b_sub[:, i]
for i, sp in enumerate(all_specials):
    df_diffs[f"Diff_count_spec_{sp}"] = a_spec[:, i] - b_spec[:, i]

# Merge everything together
X_encoded = pd.concat([X_context, alpha_counts, bravo_counts, df_diffs], axis=1)

# ==========================================
# 4. TRAIN AND ASSESS MODEL
# ==========================================
X_train, X_test, y_train, y_test = train_test_split(
    X_encoded, y, test_size=0.2, random_state=42
)

rf_classifier = RandomForestClassifier(n_estimators=100, random_state=42)
rf_classifier.fit(X_train, y_train)

print(f"\n🚀 Kit-Aware Match Prediction Accuracy: {rf_classifier.score(X_test, y_test):.2%}")

# View features to see where sub/special configurations sit
importance_df = pd.DataFrame(
    {"Feature": X_encoded.columns, "Importance": rf_classifier.feature_importances_}
).sort_values(by="Importance", ascending=False)
print("\n--- Top 50 Most Important Structural Configuration Features ---")
print(importance_df.head(50).to_string(index=False))