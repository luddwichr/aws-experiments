package com.example.awsexperiments;

import cloud.localstack.Localstack;
import cloud.localstack.docker.LocalstackDockerExtension;
import cloud.localstack.docker.annotation.LocalstackDockerProperties;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.tests.annotations.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(LocalstackDockerExtension.class)
@LocalstackDockerProperties(randomizePorts = true, services = {"s3"})
class DemoLambdaDockerIT {

	@ParameterizedTest
	@Event(value = "s3-event.json", type = S3Event.class)
	void handleRequest(S3Event event) throws URISyntaxException {
		S3Client s3 = S3Client.builder()
				.endpointOverride(new URI(Localstack.INSTANCE.getEndpointS3()))
				.region(Region.of(Localstack.getDefaultRegion()))
				.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("access", "secret")))
				.build();
		s3.createBucket(CreateBucketRequest.builder().bucket("example-bucket").build());
		s3.putObject(PutObjectRequest.builder().bucket("example-bucket").key("test/key").build(), RequestBody.fromString("{\"value\":\"TEST\"}"));

		DemoLambda demoLambda = new DemoLambda(s3, new ObjectMapper());

		demoLambda.handleRequest(event, null);

		assertThat(s3.getObjectAsBytes(GetObjectRequest.builder().bucket("example-bucket").key("test/key-processed").build()).asUtf8String()).isEqualTo("{\"value\":\"test\"}");
	}

}
