# Deploy Lambda to AWS

# Setup for testing on local machine with LocalStack and AWS CLI

1. Create local copy of LocalStack repository: 

    ```git clone https://github.com/localstack/localstack.git```
    
2. Change to newly created LocalStack directory: 

    ```cd localstack```
    
3. If using Windows, open `docker-compose.yml` and comment out the following line:

    ```- "/var/run/docker.sock:/var/run/docker.sock"``` (cf. [here](https://gist.github.com/robfe/9a858b59f4d394ef5deb2517833e75c6#file-about-md))
    
4. Start local AWS-like environment in Docker: 
    
    ```docker-compose up -d```
    
5. Install AWS CLI: follow [instructions](https://docs.aws.amazon.com/cli/latest/userguide/installing.html)

6. Create a S3 bucket: `aws --endpoint-url=http://localhost:4572 s3 mb s3://testBucket --no-sign-request`


- `gradlew distZip`
- `aws lambda update-function-code --function-name DemoLambda --zip-file file://???`

- `aws cloudformation validate-template --template-body file://???`