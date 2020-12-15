package com.example.awsexperiments;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.lambda.runtime.tests.annotations.Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DemoLambdaTest {

	@InjectMocks
	private DemoLambda demoLambda;

	@Mock
	private Context context;

	@Mock
	private AmazonS3 s3Client;

	@Mock
	private GetObjectRequestFactory getObjectRequestFactory;

	@Mock
	private S3ObjectMapper s3ObjectMapper;

	@ParameterizedTest
	@Event(value = "s3-event.json", type = S3Event.class)
	void handleRequest(S3Event event) throws IOException {
		S3EventNotificationRecord record = event.getRecords().stream().findFirst().orElseThrow(IllegalStateException::new);
		GetObjectRequest getObjectRequest = mock(GetObjectRequest.class);
		when(getObjectRequestFactory.getObjectRequest(record)).thenReturn(getObjectRequest);
		S3Object s3Object = mock(S3Object.class);
		when(s3Client.getObject(getObjectRequest)).thenReturn(s3Object);
		SomeDto someDto = new SomeDto("test");
		when(s3ObjectMapper.map(s3Object, SomeDto.class)).thenReturn(someDto);

		Void response = demoLambda.handleRequest(event, context);

		assertThat(response).isNull();
	}

}
