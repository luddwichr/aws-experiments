package com.example.awsexperiments;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.tests.annotations.Event;
import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
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
	private ObjectMapper objectMapper;

	@ParameterizedTest
	@Event(value = "s3-event.json", type = S3Event.class)
	void handleRequest(S3Event event) throws IOException {
		String objectAsString = "object as string";
		when(s3Client.getObjectAsString("example-bucket", "test/key")).thenReturn(objectAsString);
		SomeDto someDto = new SomeDto("test");
		when(objectMapper.readValue(objectAsString, SomeDto.class)).thenReturn(someDto);

		Void response = demoLambda.handleRequest(event, context);

		assertThat(response).isNull();
	}

}
