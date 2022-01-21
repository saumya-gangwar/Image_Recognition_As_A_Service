package com.aws.sqslistener.handler;

import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;
import com.aws.sqslistener.core.SqsCore;
import com.aws.sqslistener.core.SqsCoreImpl;

public class SqsHandlerImpl implements SqsHandler {
	
	private SqsCore sqsCore;
	
	public SqsHandlerImpl() {
		sqsCore = new SqsCoreImpl();
	}
	
	@Override
	public void deleteMessage(Message message, String queueName) {
		sqsCore.deleteMessage(message, queueName);
	}
	
	@Override
	public CreateQueueResult createQueue(String queueName) {
		CreateQueueResult createQueueResult = sqsCore.createQueue(queueName);
		return createQueueResult;
	}
	
	@Override
	public String modelOutput(String key) {
		String predictValue = sqsCore.modelProcessing(key);
		
		return predictValue;
	}
	
	@Override
	public Message receiveMessage(String queueName, Integer waitTime, Integer visibilityTimeout) {
		return sqsCore.receiveMessage(queueName, waitTime, visibilityTimeout);
	}

	@Override
	public void sendMessage(String messageBody, String queueName, Integer delaySeconds) {
		sqsCore.sendMessage(messageBody, queueName, delaySeconds);
	}
}