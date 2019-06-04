package com.example.awsexperiments;

import com.amazonaws.services.s3.event.S3EventNotification.S3Entity;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectId;

public class GetObjectRequestFactory {

	public GetObjectRequest getObjectRequest(S3EventNotificationRecord record) {
		S3ObjectId s3ObjectId = getS3ObjectId(record);
		return new GetObjectRequest(s3ObjectId);
	}

	private S3ObjectId getS3ObjectId(S3EventNotificationRecord record) {
		S3Entity s3 = record.getS3();
		String s3Key = s3.getObject().getUrlDecodedKey();
		String s3Bucket = s3.getBucket().getName();
		return new S3ObjectId(s3Key, s3Bucket);
	}
}
