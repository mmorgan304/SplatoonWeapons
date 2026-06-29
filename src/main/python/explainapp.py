# FastAPI endpoint for the explainer

from fastapi import FastAPI
from llama3_generage_groq import generate_team_explanation

app = FastAPI()

@app.post("/explain")
def explain(body: dict):
    try:
        # 1: Translate Java's camelCase JSON properties to Python variables
        recommended_team = body.get("recommendedTeam", {})
        bravo_team_comp = body.get("bravoTeam", [])
        advantages = body.get("advantages", [])
        deficits = body.get("deficits", [])
        projected_win_rate = body.get("projectedWinRate", 50.0)

        print(bravo_team_comp)

        # 2: Execute the RAG function with the extracted arguments
        result = generate_team_explanation(
            recommended_team=recommended_team,
            bravo_team_comp=bravo_team_comp,
            advantages=advantages,
            deficits=deficits,
            projected_win_rate_interior=projected_win_rate
        )

        # 3: Translate snake_case results back to camelCase
        return {
            "justificationParagraph": result.get("justification_paragraph", "Could not generate paragraph."),
            "rephrasedAdvantages": result.get("rephrased_advantages", advantages),
            "rephrasedDeficits": result.get("rephrased_deficits", deficits),
            "hoverRoles": result.get("hover_roles", {})
        }

    except ValueError as e:
        print(f"❌ Validation Error: {e}")
        return {
            "justificationParagraph": f"Error during generation",
            "rephrasedAdvantages": [],
            "rephrasedDeficits": [],
            "hoverRoles": {}
        }

    except Exception as e:
        print(f"❌ Internal Server Error: {e}")
        return {
            "justificationParagraph": f"Could not generate strategy context at this time. (Internal Error)",
            "rephrasedAdvantages": [],
            "rephrasedDeficits": [],
            "hoverRoles": {}
        }