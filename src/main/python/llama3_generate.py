import json
import time
import ollama
from groq import Groq

WEAPON_KNOWLEDGE_BASE = {
    "Splattershot Jr.": {
        "subweapon": "Splat Bomb",
        "special weapon": "Big Bubbler",
        "class": "Shooters",
        "weight": "Lightweight",
        "nickname": "Jr",
        "strengths": "Good paint output for turf control. High mobility helps it be in good positions.",
        "weaknesses": "Short range and high shot variance makes it poor at 1v1 combat.",
        "playstyle": "Supports teammates by painting and aiding better fighters.",
        "recommendation": "Use Splat Bombs to more reliably get splats. Big Bubbler helps it hold down positions it can't hold with its own range."
    },
    "N-Zap '85": {
        "subweapon": "Suction Bomb",
        "special weapon": "Tacticooler",
        "class": "Shooters",
        "weight": "Lightweight",
        "nickname": "Zap",
        "strengths": "High fire rate and high strafe speed mean good paint output.",
        "weaknesses": "Low damage bullets and unimpressive range.",
        "playstyle": "Supports team with paint output and buffs from Tacticooler.",
        "recommendation": "Focus on surviving to produce more Tacticoolers. Aid better fighters in 2v1 engagements."
    },
    "Custom Range Blaster": {
        "subweapon": "Splat Bomb",
        "special weapon": "Kraken Royale",
        "class": "Blasters",
        "weight": "Middleweight",
        "nickname": "CRB",
        "strengths": "Longest range one-hit-kill Blaster. Can use AOE explosion to poke corners and ledges.",
        "weaknesses": "Poor painting ability and paint efficiency makes it less mobile than other slayers.",
        "playstyle": "Uses one shot and poking ability to take down opponents before they can engage.",
        "recommendation": "Stay within working range of teammates with better paint output."
    },
    "E-liter 4K Scope": {
        "subweapon": "Ink Mine",
        "special weapon": "Wave Breaker",
        "class": "Chargers",
        "weight": "Heavyweight",
        "nickname": "Scoped Liter",
        "strengths": "Longest range in the game. Locks down chokepoints and open sightlines with one-shot potential.",
        "weaknesses": "Low mobility and terrible close-quarters combat. Scope restricts peripheral vision.",
        "playstyle": "Holds positions and locks down easy enemy approaches.",
        "recommendation": "Keep teammates in front and maintain situational awareness to avoid being rushed down."
    },
    "Tri-Slosher Nouveau": {
        "subweapon": "Fizzy Bomb",
        "special weapon": "Tacticooler",
        "class": "Sloshers",
        "weight": "Lightweight",
        "nickname": "Tri-Nouveau",
        "strengths": "Fast mobility and wide coverage around corners and ledges",
        "weaknesses": "Short range makes it a poor fighter on open sightlines.",
        "playstyle": "Quickly moves to take control of key positions.",
        "recommendation": "Combine weapon damage with Fizzy Bomb for more reliable splats. Tacticooler buffs help it stay active."
    },
    "Splat Charger CAM-O": {
        "subweapon": "Sprinkler",
        "special weapon": "Crab Tank",
        "class": "Chargers",
        "weight": "Middleweight",
        "nickname": "Crab Charger",
        "strengths": "Long range sniper with average mobility.",
        "weaknesses": "Poor close range combat is compounded by lack of a bomb or Splash Wall for protection.",
        "playstyle": "Holds positions and locks down easy enemy approaches.",
        "recommendation": "Use sprinkler to charge Crabs more quickly. Use Crab to become a more stable anchor when needed."
    },
    "Heavy Splatling": {
        "subweapon": "Sprinkler",
        "special weapon": "Wave Breaker",
        "class": "Splatlings",
        "weight": "Middleweight",
        "nickname": "Heavy",
        "strengths": "High paint output combined with long range. Quick time-to-kill in close combat if it gets jumped.",
        "weaknesses": "High out-of-ink time makes it easy pickings for longer ranged opponents or AOE ledge campers.",
        "playstyle": "Zones out opponents with high paint output from a distance.",
        "recommendation": "Support more aggressive teammates with paint and damage from afar. Use Wave Breaker to locate opponents and stay active."
    },
    "Splat Roller": {
        "subweapon": "Curling Bomb",
        "special weapon": "Big Bubbler",
        "class": "Rollers",
        "weight": "Middleweight",
        "nickname": "Roller",
        "strengths": "Quick one-shot swing with a wide hitbox. Can ledge camp to hold down drops.",
        "weaknesses": "Low paint output and short range makes it struggle to retake space on its own.",
        "playstyle": "Sharking slayer. Forces opponents play more cautiously. Can also aid other slayers with low-damage range.",
        "recommendation": "Use Ninja Squid and Curling Bomb to make it harder for opponents to predict position."
    }
}

SUB_KNOWLEDGE_BASE = {
    "Splat Bomb": "Lethal bomb. A standard explosive bomb with a short fuse after touching a surface. Great for lethal traps and flushing out enemies.",
    "Suction Bomb": "Lethal bomb. An explosive that sticks to any surface with a long fuse. Excellent for area denial and objective control.",
    "Ink Mine": "A hidden trap that deals chip damage and tracks enemies who step on it. Good for defending flanks and preventing pushes.",
    "Fizzy Bomb": "A bomb that requires shaking to activate. When thrown, it paints a long trail of ink that ends in three small explosions that are deadly in combination.",
    "Sprinkler": "A sprinkler that passively paints a circle of turf around it. A set-and-forget method of painting, and a distraction to opponents.",
    "Curling Bomb": "Lethal bomb. An explosive that travels along the ground, leaving a trail of ink. Once it travels its maximum distance, it has a deadly explosion."
}

SPECIAL_KNOWLEDGE_BASE = {
    "Big Bubbler": "A stationary dome shield that protects teammates inside from outside fire. Great for holding ground or the objective.",
    "Tacticooler": "A deployable fridge that gives teammates massive buffs to swim speed, run speed, and respawn time.",
    "Kraken Royale": "Transforms the user into an invincible giant squid that can charge forward for one-hit splats or stall the objective.",
    "Wave Breaker": "A deployable device that sends out waves of energy, dealing chip damage and tracking enemies who don't jump over them.",
    "Crab Tank": "The player sits inside a tank with additional HP. The tank can fire both a stream of lower damage bullets and launch explosive rounds."
}

client = Groq(api_key="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")

def generate_team_explanation(recommended_team, bravo_team, advantages, deficits):
    """
    Takes the output from your ML model, retrieves the lore, and asks the local LLM to explain the matchup.
    """

    # STEP 1: RETRIEVAL (Build Alpha Team Context)
    alpha_context = []
    for player_pool in recommended_team.values():
        weapon_name = player_pool[0]
        w_data = WEAPON_KNOWLEDGE_BASE.get(weapon_name, {})

        # Safely extract kit (Notice the updated keys to match your new JSON!)
        sub_name = w_data.get("subweapon", "Unknown Sub")
        special_name = w_data.get("special weapon", "Unknown Special")

        # Retrieve lore from the other dictionaries
        sub_lore = SUB_KNOWLEDGE_BASE.get(sub_name, "")
        special_lore = SPECIAL_KNOWLEDGE_BASE.get(special_name, "")

        # Extract the new descriptive fields
        nickname = w_data.get("nickname", {weapon_name})
        strengths = w_data.get("strengths", "No data.")
        weaknesses = w_data.get("weaknesses", "No data.")
        playstyle = w_data.get("playstyle", "No data.")
        recommendation = w_data.get("recommendation", "No data.")

        # Format this weapon's full profile for the LLM
        # Added \n to the end of each line so it formats properly!
        alpha_context.append(
            f"- {weapon_name}:\n"
            f"  * Kit: {sub_name} ({sub_lore}) / {special_name} ({special_lore})\n"
            f"  * Nickname: {nickname} ({nickname})\n"
            f"  * Playstyle: {playstyle}\n"
            f"  * Strengths: {strengths}\n"
            f"  * Weaknesses: {weaknesses}\n"
            f"  * Recommendation: {recommendation}"
        )

    alpha_string = "\n".join(alpha_context)

    # STEP 2: RETRIEVAL (Build Bravo Team Context)
    bravo_context = []
    for weapon_name in bravo_team:
        w_data = WEAPON_KNOWLEDGE_BASE.get(weapon_name, {})
        strengths = w_data.get("strengths", "No data.")
        weaknesses = w_data.get("weaknesses", "No data.")
        bravo_context.append(f"- {weapon_name} (Strengths: {strengths} | Weaknesses: {weaknesses})")

    bravo_string = "\n".join(bravo_context)

    # STEP 3: PROMPT ENGINEERING
    system_prompt = """You are an expert competitive Splatoon 3 coach. 
Your job is to analyze a team composition generated by a machine learning model (Team Alpha) and explain how it can beat the opposing team (Team Bravo).
Translate the raw data, weapon strengths, and kit synergies into a concise competitive strategy.
You must return your response as a raw JSON object with no markdown formatting. Make sure to return a hover role for all four Team Alpha weapons labelled precisely with the weapon's name.

Use this exact JSON schema:
{
  "justification_paragraph": "A 3-4 sentence analytical paragraph explaining Team Alpha's strengths and sub/special synergies. You can call the weapons by their nicknames here. If possible, highlight one weakness of a Team Bravo weapon they can exploit.",
  "rephrased_advantages": ["A snappy, bullet point version of Machine Learning Advantage 1", "etc..."],
  "rephrased_deficits": ["A snappy, constructive, bullet point warning of Machine Learning Deficit 1", "etc..."],
  "hover_roles": {
    "Weapon Name 1": "A 1 to 2-sentence description of what this player should focus on with this weapon.",
    "Weapon Name 2": "...",
    "Weapon Name 3": "...",
    "Weapon Name 4": "..."
  }
}

Example:
{
    "justification_paragraph": "This team can focus on oppressive area denial, using the high paint output of the Pencil and Heavy. They provide support to the Luna Neo, which can use its one-shot kill to make it difficult to enter uninked turf. .52 provides additional area denial with its decent paint output, Splash Wall, and quick time-to-kill.",
    "rephrased_advantages": ["Extra sprinklers contribute to denying area to opposing teams.", "Splash Wall blocks off routes, slowing opponents down."],
    "rephrased_disadvantages": ["Few lethal bombs reduce team's splatting power.", "No anchoring specials make holds harder to enforce."],
    "hover_roles": {
        "Snipewriter HB": "Use your long range and high paint output to make it harder for enemies to enter areas.",
        "Heavy Splatling": "Focus on painting in range of your teammates to allow them to focus on enemies instead of turf control.",
        "Luna Blaster Neo": "Use your blast radius to control ledges and corners. Stay within painting range of your Heavy Splatling or .52 Gal.",
        ".52 Gal": "Hold areas with your range and Splash Wall, and always work to hold opponent attention."
    }
}
"""

    user_message = f"""
Here is the matchup data:

OUR TEAM (Team Alpha):
{alpha_string}

ENEMY TEAM (Team Bravo):
{bravo_string}

Machine Learning Advantages for Team Alpha:
{json.dumps(advantages)}

Machine Learning Deficits for Team Alpha:
{json.dumps(deficits)}
"""

    # STEP 4: GENERATION (Calling your local LLM)
    try:
        print("Thinking... (Sending to local Llama 3)")
        start_time = time.time()
        # response = ollama.chat(
        #     model='llama3.1',
        #     format='json',
        #     messages=[
        #         {'role': 'system', 'content': system_prompt},
        #         {'role': 'user', 'content': user_message}
        #     ]
        # )

        response = client.chat.completions.create(
            model='llama-3.1-8b-instant',
            response_format={"type": "json_object"},
            messages=[
                {'role': 'system', 'content': system_prompt},
                {'role': 'user', 'content': user_message}
            ]
        )

        end_time = time.time()
        elapsed_time = end_time - start_time
        print(f"✅ LLM Generation Complete! Time taken: {elapsed_time:.2f} seconds")


        # llm_output = json.loads(response['message']['content'])
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

    alpha_team = {
        "player1Pool": ["Splattershot Jr."],
        "player2Pool": ["N-Zap '85"],
        "player3Pool": ["Custom Range Blaster"],
        "player4Pool": ["E-liter 4K Scope"]
    }
    bravo_team = ["Splat Charger CAM-O", "Heavy Splatling", "Tri-Slosher Nouveau", "Splat Roller"]
    adv = [
        "Difference in Total Paint (105.70)",
        "Difference in Total spec pts (10.00)",
        "Difference in Count: Splat Bomb (2.00)",
        "Difference in Count: Suction Bomb (1.00)",
        "Mode: Splat Zones (1.00)"
    ]
    defs = [
        "Difference in Count: Sprinkler (-2.00)",
        "Difference in Count: Curling Bomb (-1.00)",
        "Difference in Count: Fizzy Bomb (-1.00)",
        "Difference in Count: Crab Tank (-1.00)"
    ]

    result = generate_team_explanation(alpha_team, bravo_team, adv, defs)

    print("\n--- LLM OUTPUT ---")
    print(json.dumps(result, indent=2))