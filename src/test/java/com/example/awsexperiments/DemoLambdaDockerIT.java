package com.example.awsexperiments;

import cloud.localstack.DockerTestUtils;
import cloud.localstack.docker.LocalstackDockerTestRunner;
import cloud.localstack.docker.annotation.LocalstackDockerProperties;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(LocalstackDockerTestRunner.class)
@LocalstackDockerProperties(randomizePorts = true)
public class DemoLambdaDockerIT {

	@Test
	public void handleRequest() {
		AmazonS3 s3 = DockerTestUtils.getClientS3();
		Bucket testBucket = s3.createBucket("testBucket");
	}

}
