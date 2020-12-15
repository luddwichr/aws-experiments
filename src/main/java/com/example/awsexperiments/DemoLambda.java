package com.example.awsexperiments;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectId;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;

public class DemoLambda implements RequestHandler<S3Event, Void> {

	private final AmazonS3 s3Client;
	private final ObjectMapper objectMapper;

	@SuppressWarnings("unused")
	public DemoLambda() {
		this(AmazonS3ClientBuilder.defaultClient(), new ObjectMapper());
	}

	DemoLambda(AmazonS3 s3Client, ObjectMapper objectMapper) {
		this.s3Client = s3Client;
		this.objectMapper = objectMapper;
	}

	@Override
	public Void handleRequest(S3Event s3Event, Context context) {
		s3Event.getRecords().forEach(this::handleRecord);
		return null;
	}

	private void handleRecord(S3EventNotificationRecord record) {
		S3ObjectId objectId = getS3ObjectId(record);
		String objectAsString = s3Client.getObjectAsString(objectId.getBucket(), objectId.getKey());
		try {
			SomeDto someDto = objectMapper.readValue(objectAsString, SomeDto.class);
			someDto.setValue(someDto.getValue().toLowerCase());
			s3Client.putObject(objectId.getBucket(), objectId.getKey() + "-processed", objectMapper.writeValueAsString(someDto));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private S3ObjectId getS3ObjectId(S3EventNotificationRecord record) {
		S3EventNotification.S3Entity s3 = record.getS3();
		String s3Key = s3.getObject().getUrlDecodedKey();
		String s3Bucket = s3.getBucket().getName();
		return new S3ObjectId(s3Bucket, s3Key);
	}

}
