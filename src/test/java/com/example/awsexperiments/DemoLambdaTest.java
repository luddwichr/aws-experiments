package com.example.awsexperiments;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DemoLambdaTest {

	private final Context context = new LoggingContext();

	@Test
	public void handleRequest() throws IOException {
		AmazonS3 s3Client = mock(AmazonS3.class);
		GetObjectRequestFactory getObjectRequestFactory = mock(GetObjectRequestFactory.class);
		S3ObjectMapper s3ObjectMapper = mock(S3ObjectMapper.class);
		DemoLambda demoLambda = new DemoLambda(s3Client, getObjectRequestFactory, s3ObjectMapper);

		S3EventNotificationRecord record = mock(S3EventNotificationRecord.class);
		GetObjectRequest getObjectRequest = mock(GetObjectRequest.class);
		when(getObjectRequestFactory.getObjectRequest(record)).thenReturn(getObjectRequest);
		S3Object s3Object = mock(S3Object.class);
		when(s3Client.getObject(getObjectRequest)).thenReturn(s3Object);
		SomeDto someDto = new SomeDto("test");
		when(s3ObjectMapper.map(s3Object, SomeDto.class)).thenReturn(someDto);

		S3Event s3Event = new S3Event(Collections.singletonList(record));
		Void response = demoLambda.handleRequest(s3Event, context);

		assertThat(response).isNull();
	}

}
