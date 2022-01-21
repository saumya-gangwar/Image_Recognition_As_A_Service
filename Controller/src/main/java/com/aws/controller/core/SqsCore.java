package com.aws.controller.core;

public interface SqsCore {
	public Integer getApproximateNumberOfMsgs(String queueName);
}
