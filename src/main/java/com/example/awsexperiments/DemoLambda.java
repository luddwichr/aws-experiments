package com.example.awsexperiments;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3EventNotificationRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.UncheckedIOException;

public class DemoLambda implements RequestHandler<S3Event, Void> {

	private final S3Client s3Client;
	private final ObjectMapper objectMapper;

	@SuppressWarnings("unused")
	public DemoLambda() {
		this(S3Client.builder().build(), new ObjectMapper());
	}

	DemoLambda(S3Client s3Client, ObjectMapper objectMapper) {
		this.s3Client = s3Client;
		this.objectMapper = objectMapper;
	}

	@Override
	public Void handleRequest(S3Event s3Event, Context context) {
		s3Event.getRecords().forEach(this::handleRecord);
		return null;
	}

	private void handleRecord(S3EventNotificationRecord record) {
		S3EventNotification.S3Entity s3 = record.getS3();
		String keyName = s3.getObject().getUrlDecodedKey();
		String bucketName = s3.getBucket().getName();
		GetObjectRequest getObjectRequest = GetObjectRequest.builder().key(keyName).bucket(bucketName).build();
		byte[] objectAsString = s3Client.getObjectAsBytes(getObjectRequest).asByteArray();
		try {
			SomeDto someDto = objectMapper.readValue(objectAsString, SomeDto.class);
			someDto.setValue(someDto.getValue().toLowerCase());
			PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(keyName + "-processed").build();
			RequestBody requestBody = RequestBody.fromString(objectMapper.writeValueAsString(someDto));
			s3Client.putObject(putObjectRequest, requestBody);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}


}
