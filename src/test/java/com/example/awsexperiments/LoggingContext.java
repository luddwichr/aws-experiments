package com.example.awsexperiments;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingContext implements Context {

	private final Logger logger = Logger.getLogger("AwsTestingLogger");

	@Override
	public String getAwsRequestId() {
		return null;
	}

	@Override
	public String getLogGroupName() {
		return null;
	}

	@Override
	public String getLogStreamName() {
		return null;
	}

	@Override
	public String getFunctionName() {
		return null;
	}

	@Override
	public String getFunctionVersion() {
		return null;
	}

	@Override
	public String getInvokedFunctionArn() {
		return null;
	}

	@Override
	public CognitoIdentity getIdentity() {
		return null;
	}

	@Override
	public ClientContext getClientContext() {
		return null;
	}

	@Override
	public int getRemainingTimeInMillis() {
		return 0;
	}

	@Override
	public int getMemoryLimitInMB() {
		return 0;
	}

	@Override
	public LambdaLogger getLogger() {
		return new LambdaLogger() {
			@Override
			public void log(String message) {
				logger.log(Level.INFO, message);
			}

			@Override
			public void log(byte[] message) {
				logger.log(Level.INFO, Arrays.toString(message));
			}
		};
	}
}
