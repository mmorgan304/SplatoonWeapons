FROM ubuntu:latest
LABEL authors="Melissa"

ENTRYPOINT ["top", "-b"]

# Use an official AWS Lambda Python base image
FROM public.ecr.aws/lambda/python:3.13

# Copy your requirements.txt and install ML libraries
COPY requirements.txt .
RUN pip install -r requirements.txt

# Copy your 2.4 GB trained model file and your execution script
COPY rf_splatoon_model.joblib .
COPY model_metadata.json .
COPY lambda_function.py .

# Tell Lambda which function to run when an API request comes in
CMD [ "lambda_function.lambda_handler" ]