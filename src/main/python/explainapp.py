from fastapi import FastAPI
from llama3_generage_groq import generate_team_explanation

app = FastAPI()

@app.post("/explain")
def explain(body: dict):
    try:
        # STEP 1: Translate Java's camelCase JSON properties to Python variables
        recommended_team = body.get("recommendedTeam", {})
        bravo_team = body.get("bravoTeam", [])
        advantages = body.get("advantages", [])
        deficits = body.get("deficits", [])
        projected_win_rate = body.get("projectedWinRate", 50.0)

        # STEP 2: Execute the RAG function with the extracted arguments
        result = generate_team_explanation(
            recommended_team=recommended_team,
            bravo_team=bravo_team,
            advantages=advantages,
            deficits=deficits,
            projected_win_rate=projected_win_rate
        )

        # FastAPI automatically serializes this returned dict back into JSON for Java
        return result

    except ValueError as e:
        print(f"❌ Validation Error: {e}")
        return {
            "justification_paragraph": f"Value Error during generation: {str(e)}",
            "rephrased_advantages": [],
            "rephrased_deficits": [],
            "hover_roles": {}
        }

    except Exception as e:
        print(f"❌ Internal Server Error: {e}")
        return {
            "justification_paragraph": f"Could not generate strategy context at this time. (Internal Error: {str(e)})",
            "rephrased_advantages": [],
            "rephrased_deficits": [],
            "hover_roles": {}
        }