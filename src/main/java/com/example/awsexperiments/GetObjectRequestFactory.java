package com.example.awsexperiments;

import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;

public class GetObjectRequestFactory {

	public GetObjectRequest getObjectRequest(S3EventNotificationRecord record) {
		// AWS code uses the following approach: to be evaluated which one is correct
		// (https://docs.aws.amazon.com/lambda/latest/dg/with-s3-example-deployment-pkg.html)
		// Object key may have spaces or unicode non-ASCII characters.
		//String srcKey = record.getS3().getObject().getKey()
		//		.replace('+', ' ');
		// srcKey = URLDecoder.decode(srcKey, "UTF-8");

		// TODO: Law of demeter is violated here -> introduce some additional class to handle information retrieval
		String s3Key = record.getS3().getObject().getKey();
		String s3Bucket = record.getS3().getBucket().getName();
		return new GetObjectRequest(s3Bucket, s3Key);
	}
}
