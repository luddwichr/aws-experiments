package com.example.awsexperiments;

import cloud.localstack.awssdkv1.TestUtils;
import cloud.localstack.docker.LocalstackDockerExtension;
import cloud.localstack.docker.annotation.LocalstackDockerProperties;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(LocalstackDockerExtension.class)
@LocalstackDockerProperties(randomizePorts = true, services = {"s3"})
class DemoLambdaDockerIT {

	@Test
	void handleRequest() {
		AmazonS3 s3 = TestUtils.getClientS3();
		Bucket testBucket = s3.createBucket("test-bucket");
	}

}
