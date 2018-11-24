package com.example.awsexperiments;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class DemoLambda implements RequestHandler<S3Event, Void> {

	private static final Logger logger = LogManager.getLogger(DemoLambda.class);
	private final AmazonS3 s3Client;
	private final GetObjectRequestFactory getObjectRequestFactory;
	private final S3ObjectMapper s3ObjectMapper;

	@SuppressWarnings("unused")
	public DemoLambda() {
		this( AmazonS3ClientBuilder.defaultClient(), new GetObjectRequestFactory(), new S3ObjectMapper());
	}

	/*for testing*/ DemoLambda(AmazonS3 s3Client, GetObjectRequestFactory getObjectRequestFactory, S3ObjectMapper s3ObjectMapper) {
		this.s3Client = s3Client;
		this.getObjectRequestFactory = getObjectRequestFactory;
		this.s3ObjectMapper = s3ObjectMapper;
	}

	@Override
	public Void handleRequest(S3Event s3Event, Context context) {
		for (S3EventNotificationRecord record : s3Event.getRecords()) {
			// TODO: too many responsibilities here, introduce additional abstraction layer to map record to S3Object?
			S3Object object = s3Client.getObject(getObjectRequestFactory.getObjectRequest(record));
			try {
				SomeDto someDto  = s3ObjectMapper.map(object, SomeDto.class);
				logger.info(someDto.toString());
			} catch (IOException e) {
				logger.error("failed to process input stream!" + e.getMessage());
			}
		}
		return null;
	}

}