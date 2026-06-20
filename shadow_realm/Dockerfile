FROM ubuntu:latest
LABEL authors="Melissa"

ENTRYPOINT ["top", "-b"]

# Use an official AWS Lambda Python base image
FROM public.ecr.aws/lambda/python:3.13

# Copy your requirements.txt and install ML libraries
COPY ../src/main/python/requirements.txt ${LAMBDA_TASK_ROOT}
RUN pip install --no-cache-dir -r requirements.txt

# Copy your 2.4 GB trained model file and your execution script
COPY splatoon_model.onnx ${LAMBDA_TASK_ROOT}
COPY ../src/main/python/model_metadata.json ${LAMBDA_TASK_ROOT}
COPY lambda_function.py ${LAMBDA_TASK_ROOT}

# Tell Lambda which function to run when an API request comes in
CMD [ "lambda_function.lambda_handler" ]