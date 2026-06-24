import json
import time
import ollama

import os
from pathlib import Path
from dotenv import load_dotenv
from groq import Groq

script_dir = Path(__file__).resolve().parent

# Go up 3 levels to reach the main "SplatoonWeapons" folder
project_root = script_dir.parent.parent.parent

# Load your .env file directly from the root
env_path = project_root / '.env'
load_dotenv(dotenv_path=env_path)


# =====================================================================
# 2. SIMPLE JSON LOADERS (Simple, straightforward file reads)
# =====================================================================

def load_dictionary(filename):
    """Opens a JSON file inside the dictionary folder and returns it."""
    file_path = project_root / 'src/main/python/dictionary' / filename
    with open(file_path, "r", encoding="utf-8") as f:
        return json.load(f)


# Load your three JSON databases automatically!
WEAPON_KNOWLEDGE_BASE = load_dictionary("weapons.json")
SUB_KNOWLEDGE_BASE = load_dictionary("subweapons.json")
SPECIAL_KNOWLEDGE_BASE = load_dictionary("special_weapons.json")

api_key = os.getenv("DEV_GROQ_API_KEY")
client = Groq(api_key=api_key)


def generate_team_explanation(recommended_team, bravo_team, advantages, deficits, projected_win_rate):
    """
    Takes the output from your ML model, retrieves the lore, and asks the local LLM to explain the matchup.
    """

    # STEP 1: RETRIEVAL (Build Alpha Team Context)
    safe_kb = {k.lower(): v for k, v in WEAPON_KNOWLEDGE_BASE.items()}

    # Fetch context for Team Alpha
    alpha_context = []
    for player_pool in recommended_team.values():
        weapon_name = player_pool[0]
        w_data = safe_kb.get(weapon_name.lower(), {})

        sub_name = w_data.get("subweapon", "Unknown Sub")
        special_name = w_data.get("special weapon", "Unknown Special")

        # Pull descriptions and roles from your newly structured sub/special files
        sub_data = SUB_KNOWLEDGE_BASE.get(sub_name, {})
        if isinstance(sub_data, dict):
            sub_role = sub_data.get("role", "No role details.")
            sub_desc = sub_data.get("description", "")
            sub_lore = f"Role: {sub_role} | Description: {sub_desc}" if sub_desc else f"Role: {sub_role}"
        else:
            sub_lore = sub_data if sub_data else "No database details."

        special_data = SPECIAL_KNOWLEDGE_BASE.get(special_name, {})
        if isinstance(special_data, dict):
            special_role = special_data.get("role", "No role details.")
            special_desc = special_data.get("description", "")
            special_lore = f"Role: {special_role} | Description: {special_desc}" if special_desc else f"Role: {special_role}"
        else:
            special_lore = special_data if special_data else "No database details."

        alpha_context.append(
            f"- {weapon_name}:\n"
            f"  * Kit: {sub_name} ({sub_lore}) / {special_name} ({special_lore})\n"
            f"  * Playstyle: {w_data.get('playstyle', 'No details.')}\n"
            f"  * Strengths: {w_data.get('strengths', 'No details.')}\n"
            f"  * Weaknesses: {w_data.get('weaknesses', 'No details.')}\n"
            f"  * Recommendation: {w_data.get('recommendation', 'No details.')}"
        )

    # Fetch context for Team Bravo
    bravo_context = []
    for weapon_name in bravo_team:
        w_data = safe_kb.get(weapon_name.lower(), {})
        bravo_context.append(f"- {weapon_name} (Strengths: {w_data.get('strengths', 'Unknown')})")

    # STEP 3: PROMPT ENGINEERING (Groq Optimized Structure)
    system_prompt = """ROLE:
You are an expert competitive Splatoon 3 coach provided with a machine-learning generated team composition. You're charged with explaining why 'you' have suggested these weapons.

INSTRUCTIONS:
- Write a snappy, 3-4 sentence explanation of how Team Alpha's weapons work together. Not too superlative; you're a coach justifying your picks, not a salesman.
- Translate the raw data, weapon strengths, and kit synergies into a concise competitive strategy.
- Call the weapons by their nicknames in the justification paragraph for authenticity.
- If PROJECTED WIN RATE is:
    Greater than 53%: Team is at a strong advantage based on weapons alone.
    Between 52% and 53%: Team is at a good advantage. Game is losable if they play poorly.
    Between 50.5% and 52%: Team is at a minor advantage based on weapons. Will still need to play well to win.
    Between 49.5% and 50.5%: Teams are a near match, and skill will be most important.
    Between 48% and 49.5% Team is at a minor disadvantage based on weapons. Game can be won on skill.
    Between 47% and 48%: Team is at an overcomeable disadvantage. Still winnable if they play their best game.
    Less than 47%: Team is at a major disadvantage.
- Make sure to return a hover role for all four Team Alpha weapons labelled precisely with only the weapon's name.
- You must return your response as a raw JSON object with no markdown formatting.

EXPECTED OUTPUT:
Use this exact JSON schema:
{
  "justification_paragraph": "A 3-4 sentence analytical paragraph explaining Team Alpha's strengths and sub/special synergies. If possible, highlight one weakness of a Team Bravo weapon they can exploit. Include your assessment of the strength of the comp, but do not include the projected win rate number directly.",
  "rephrased_advantages": ["A snappy, bullet point version of Machine Learning Advantage 1", "etc..."],
  "rephrased_deficits": ["A snappy, constructive, bullet point warning of Machine Learning Deficit 1", "etc..."],
  "hover_roles": {
    "Weapon Name 1": "A 1-2 sentence description of what this player should focus on with this weapon.",
    "Weapon Name 2": "...",
    "Weapon Name 3": "...",
    "Weapon Name 4": "..."
  }
}

Example:
{
    "justification_paragraph": "This team can focus on oppressive area denial, using the high paint output of the Pencil and Heavy. They provide support to the Luna Neo, which can use its one-shot kill to make it difficult to enter uninked turf. .52 provides additional area denial with its decent paint output, Splash Wall, and quick time-to-kill. This weapon comp has an advantage over the opponents, but will still require skill to pull off.",
    "rephrased_advantages": ["Extra sprinklers contribute to denying area to opposing teams.", "Splash Wall blocks off routes, slowing opponents down."],
    "rephrased_deficits": ["Few lethal bombs reduce team's splatting power.", "No anchoring specials make holds harder to enforce."],
    "hover_roles": {
        "Snipewriter 5H": "Use your long range and high paint output to make it harder for enemies to enter areas.",
        "Heavy Splatling": "Focus on painting in range of your teammates to allow them to focus on enemies instead of turf control.",
        "Luna Blaster Neo": "Use your blast radius to control ledges and corners. Stay within painting range of your Heavy Splatling or .52 Gal.",
        ".52 Gal": "Hold areas with your range and Splash Wall, and always work to hold opponent attention."
    }
}
"""

    user_message = f"""CONTEXT:
Background Matchup Data:

OUR TEAM (Team Alpha):
{alpha_context}

ENEMY TEAM (Team Bravo):
{bravo_context}

INPUT:
Machine Learning Features for Team Alpha to transform:

ADVANTAGES:
{json.dumps(advantages)}

DEFICITS:
{json.dumps(deficits)}

PROJECTED WIN RATE:
{projected_win_rate}
"""

    # STEP 4: GENERATION (Calling your local LLM)
    try:
        print("Thinking... (Sending to Groq)")
        start_time = time.time()

        response = client.chat.completions.create(
            # model='llama-3.1-8b-instant',
            model='llama-3.3-70b-versatile',
            response_format={"type": "json_object"},
            messages=[
                {'role': 'system', 'content': system_prompt},
                {'role': 'user', 'content': user_message}
            ]
        )

        end_time = time.time()
        elapsed_time = end_time - start_time
        print(f"✅ LLM Generation Complete! Time taken: {elapsed_time:.2f} seconds")

        llm_output = json.loads(response.choices[0].message.content)
        return llm_output

    except Exception as e:
        print(f"LLM Error: {e}")
        return {
            "justification_paragraph": "Our AI analyst is currently unavailable.",
            "rephrased_advantages": advantages,
            "rephrased_deficits": deficits,
            "hover_roles": {}
        }

# ==========================================
# LOCAL TESTING BLOCK
# ==========================================
if __name__ == "__main__":
    # alpha_team = {
    #     "player1Pool": ["Splattershot Jr."],
    #     "player2Pool": ["N-Zap '85"],
    #     "player3Pool": ["Custom Range Blaster"],
    #     "player4Pool": ["E-liter 4K Scope"]
    # }
    # bravo_team = ["Splat Charger CAM-O", "Heavy Splatling", "Tri-Slosher Nouveau", "Splat Roller"]
    # adv = []
    # defs = []
    # projected_win_rate = 0.5192639988414599

    alpha_team = {
        "player1Pool": ["Splat Charger CAM-O"],
        "player2Pool": ["Heavy Splatling"],
        "player3Pool": ["Tri-Slosher Nouveau"],
        "player4Pool": ["Splat Roller"]
    }
    bravo_team = ["Splattershot Jr", "N-Zap '85", "Custom Range Blaster", "E-Liter 4K Scope"]
    adv = [
        "Difference in Count: Sprinkler (2.00)",
        "Mode: Splat Zones (1.00)",
        "Difference in Count: Curling Bomb (1.00)",
        "Difference in Count: Fizzy Bomb (1.00)",
        "Difference in Count: Crab Tank (1.00)"
    ]
    defs = [
        "Difference in Total Paint (-105.70)",
        "Difference in Total spec pts (-10.00)",
        "Difference in Count: Splat Bomb (-2.00)",
        "Difference in Count: Suction Bomb (-1.00)",
        "Difference in Count: Ink Mine (-1.00)"
    ]
    projected_win_rate = 0.4807360011585401

    result = generate_team_explanation(alpha_team, bravo_team, adv, defs, projected_win_rate)

    print("\n--- LLM OUTPUT ---")
    print(json.dumps(result, indent=2))
