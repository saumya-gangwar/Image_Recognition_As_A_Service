package com.aws.sqscontinuouslistener.core;

import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;

public interface SqsCore {
	
	public CreateQueueResult createQueue(String queueName);
	
	public void sendMessage(String messageBody, String queueName, Integer delaySeconds);
	
	public Message receiveMessage(String queueName, Integer waitTime, Integer visibilityTimeout);
	
	public void deleteMessage(Message message, String queueName);
	
	public String modelProcessing(String key);
	
}