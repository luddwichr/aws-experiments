package com.example.awsexperiments;

public class SomeDto {
	private String value;

	public SomeDto() {}

	public SomeDto(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "SomeDto[value=" + value + "]";
	}
}
