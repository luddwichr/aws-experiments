package com.example.awsexperiments;

import com.amazonaws.services.s3.model.S3Object;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class S3ObjectMapperTest {

	@Test
	void map() throws IOException {
		S3ObjectMapper s3ObjectMapper = new S3ObjectMapper();
		S3Object s3Object = new S3ObjectBuilder().withContent("{\"value\": \"test\"}").build();

		SomeDto someDto = s3ObjectMapper.map(s3Object, SomeDto.class);

		assertThat(someDto.getValue()).isEqualTo("test");
	}

}