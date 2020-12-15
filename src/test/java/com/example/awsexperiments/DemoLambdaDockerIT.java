package com.example.awsexperiments;

import cloud.localstack.awssdkv1.TestUtils;
import cloud.localstack.docker.LocalstackDockerExtension;
import cloud.localstack.docker.annotation.LocalstackDockerProperties;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.tests.annotations.Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(LocalstackDockerExtension.class)
@LocalstackDockerProperties(randomizePorts = true, services = {"s3"})
class DemoLambdaDockerIT {

	@ParameterizedTest
	@Event(value = "s3-event.json", type = S3Event.class)
	void handleRequest(S3Event event) throws IOException {
		AmazonS3 s3 = TestUtils.getClientS3();
		Bucket testBucket = s3.createBucket("example-bucket");
		s3.putObject("example-bucket", "test/key", "{\"value\":\"TEST\"}");

		DemoLambda demoLambda = new DemoLambda(s3, new ObjectMapper());

		demoLambda.handleRequest(event, null);

		assertThat(s3.getObjectAsString("example-bucket", "test/key-processed")).isEqualTo("{\"value\":\"test\"}");
	}

}
