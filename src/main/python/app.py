from fastapi import FastAPI
from inference import recommend_team

app = FastAPI()


@app.post("/recommend")
def recommend(body: dict):
    try:
        return recommend_team(body)

    except ValueError as e:
        return {
            "error": str(e)
        }

    except Exception as e:
        return {
            "error": str(e)
        }