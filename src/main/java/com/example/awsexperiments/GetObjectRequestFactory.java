package com.example.awsexperiments;

import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;

public class GetObjectRequestFactory {

	public GetObjectRequest getObjectRequest(S3EventNotificationRecord record) {
		// TODO: Law of demeter is violated here -> introduce some additional class to handle information retrieval
		String s3Key = record.getS3().getObject().getUrlDecodedKey();
		String s3Bucket = record.getS3().getBucket().getName();
		return new GetObjectRequest(s3Bucket, s3Key);
	}
}
