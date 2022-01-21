package com.aws.controller.core;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aws.controller.config.AwsConfiguration;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;


public class SqsCoreImpl implements SqsCore{

	private AwsConfiguration awsConfiguration = new AwsConfiguration();
	private AmazonSQS amazonSqs = null;
	
	public SqsCoreImpl() {
		amazonSqs = awsConfiguration.amazonSQS();
	}
	
	@Override
	public Integer getApproximateNumberOfMsgs(String queueName) {;
		String queueUrl = null;
		try {
			queueUrl = amazonSqs.getQueueUrl(queueName).getQueueUrl();
		} catch (QueueDoesNotExistException queueDoesNotExistException) {
			System.out.println(queueDoesNotExistException.getMessage());
		}
		queueUrl = amazonSqs.getQueueUrl(queueName).getQueueUrl();
		List<String> attributeNames = new ArrayList<String>();
		attributeNames.add("ApproximateNumberOfMessages");
		GetQueueAttributesRequest getQueueAttributesRequest = new GetQueueAttributesRequest(queueUrl, attributeNames);
		Map map = amazonSqs.getQueueAttributes(getQueueAttributesRequest).getAttributes();
		String numberOfMessagesString = (String) map.get("ApproximateNumberOfMessages");
		Integer numberOfMessages = Integer.valueOf(numberOfMessagesString);
		return numberOfMessages;
	}

}
