package com.example.awsexperiments;

import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectId;

public class GetObjectRequestFactory {

	public GetObjectRequest getObjectRequest(S3EventNotificationRecord record) {
		S3ObjectId s3ObjectId = getS3ObjectId(record);
		return new GetObjectRequest(s3ObjectId);
	}

	private S3ObjectId getS3ObjectId(S3EventNotificationRecord record) {
		// TODO: Law of demeter is violated here -> introduce some additional class to extract S3ObjectId
		String s3Key = record.getS3().getObject().getUrlDecodedKey();
		String s3Bucket = record.getS3().getBucket().getName();
		return new S3ObjectId(s3Key, s3Bucket);
	}
}
