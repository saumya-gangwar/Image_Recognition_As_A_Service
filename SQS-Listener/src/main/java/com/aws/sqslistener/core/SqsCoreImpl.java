package com.aws.sqslistener.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.aws.sqslistener.config.AWSCredentialsProvider;
import com.aws.sqslistener.constants.Constants;


public class SqsCoreImpl implements SqsCore {

	private AmazonSQS amazonSQSClient;
	
	public SqsCoreImpl() {
		
		amazonSQSClient = AmazonSQSClientBuilder.standard()
				.withRegion(Constants.REGION)
				.withCredentials(AWSCredentialsProvider.getCredentials()).build();
	}

	@Override
	public CreateQueueResult createQueue(String queueName) {
		CreateQueueResult createQueueResult = amazonSQSClient.createQueue(queueName);
		return createQueueResult;
	}
	
	@Override
	public void sendMessage(String messageBody, String queueName, Integer delaySeconds) {
		String queueUrl = null;
		try {
			queueUrl = amazonSQSClient.getQueueUrl(queueName).getQueueUrl();
		} catch (QueueDoesNotExistException queueDoesNotExistException) {
			CreateQueueResult createQueueResult = this.createQueue(queueName);
		}
		queueUrl = amazonSQSClient.getQueueUrl(queueName).getQueueUrl();
		SendMessageRequest sendMessageRequest = new SendMessageRequest().withQueueUrl(queueUrl)
				.withMessageBody(messageBody).withDelaySeconds(delaySeconds);
		amazonSQSClient.sendMessage(sendMessageRequest);
		
	}

	

	@Override
	public Message receiveMessage(String queueName, Integer waitTime, Integer visibilityTimeout) {
		String queueUrl = amazonSQSClient.getQueueUrl(queueName).getQueueUrl();
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
		receiveMessageRequest.setMaxNumberOfMessages(1);
		receiveMessageRequest.setWaitTimeSeconds(waitTime);
		receiveMessageRequest.setVisibilityTimeout(visibilityTimeout);
		ReceiveMessageResult receiveMessageResult = amazonSQSClient.receiveMessage(receiveMessageRequest);
		List<Message> messageList = receiveMessageResult.getMessages();
		if (messageList.isEmpty()) {
			return null;
		}
		return messageList.get(0);
	}
	
	@Override
	public void deleteMessage(Message message, String queueName) {
		String queueUrl = amazonSQSClient.getQueueUrl(queueName).getQueueUrl();
		String messageReceiptHandle = message.getReceiptHandle();
		DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest(queueUrl, messageReceiptHandle);
		amazonSQSClient.deleteMessage(deleteMessageRequest);
	}

	@Override
	public String modelProcessing(String key) {
		System.out.println("Image received to Process: " +key);
		String output = null;
		ProcessBuilder pb;
		
		try {
			
			pb = new ProcessBuilder("/bin/bash", "-c", "python3 /home/ubuntu/classifier/image_classification.py "+ key);
			pb.redirectErrorStream(true);
			Process p = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			output = br.readLine();
			p.destroy();
			
		} catch (Exception e) {
			
		}
		return output;
	}
	
}