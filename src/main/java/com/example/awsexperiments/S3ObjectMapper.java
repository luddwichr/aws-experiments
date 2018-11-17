package com.example.awsexperiments;

import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class S3ObjectMapper {

	private final ObjectMapper objectMapper = new ObjectMapper();

	public <T> T map(S3Object s3Object, Class<T> s3ObjectClass) throws IOException {
		return objectMapper.readValue(s3Object.getObjectContent(), s3ObjectClass);
	}
}
