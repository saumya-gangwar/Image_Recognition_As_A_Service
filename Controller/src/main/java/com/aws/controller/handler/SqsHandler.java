package com.aws.controller.handler;

public interface SqsHandler {
	
	public Integer getApproximateNumberOfMsgs(String queueName);
	
}
