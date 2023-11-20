AWS SDK Lambda Example

Creates a lambda handler in Java that receives a request and returns a simple hello response obtained from AWS Parameter Store

Items included
* Lambda
* Aws secret (Included but not used)
* Parameter store
* SAM (Cloudformation)
  * Create the function item 
  * Grant permission for Lambda to get values from parameter store
  * Create the trigger event (an API object type in this example) 

Steps to run this project:
To build the Cloudformation template: sam build
To run the app locally: sam local start-api
To deploy the app to aws: sam deploy
To update the stack: sam deploy --stack-name [stack-name] --s3-bucket [s3-bucket-name] --capabilities CAPABILITY_IAM

To run it locally with "sam local start-api" make sure you have docker installed, and you are login with `docker login` to pull the required images from public.ecr.aws. If you have problems do the following:
    - Write next command to authenticate the Docker CLI to your default registry. That way, the docker command can push and pull images with Amazon ECR: aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws
    - Also ensure that you have configured your AWS CLI to interact with AWS (See [AWS CLI configuration basics](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-configure.html))

The parameter value on AWS must follow the next scheme:
{
    "value": "Example of value"
}

Example of curl: https://<host>/<api-stage>/hello

Note: the lambda here is not used as an entry point or proxy stream to a spring web app (See SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse>) but as function handler that simple returns a response. To create a proxy stream processor using spring boot see the [Run a Spring Boot Application in AWS Lambda](https://www.baeldung.com/spring-boot-aws-lambda) post.

Additional resources:
[Run a Spring Boot Application in AWS Lambda" to process API Gateway requests and pass them through the application context](https://www.baeldung.com/spring-boot-aws-lambda)