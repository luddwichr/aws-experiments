
package com.example.awsexperiments;

import com.amazonaws.services.s3.model.S3Object;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class S3ObjectBuilder {

	private String content = "";


	public S3ObjectBuilder withContent(String content) {
		this.content = content;
		return this;
	}

	public S3Object build() {
		S3Object s3Object = new S3Object();
		s3Object.setObjectContent(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
		return s3Object;
	}
}
