package com.aws.controller.config;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.aws.controller.constants.Constants;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

public class AwsConfiguration {
	
	public ProfileCredentialsProvider basicAWSCredentials() {
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
		 try {
	            credentialsProvider.getCredentials();
	        } catch (Exception e) {
	            throw new AmazonClientException(
	                    "Cannot load the credentials from the credential profiles file. "+e); 
	        }
		 return credentialsProvider;
	}
	
	public AmazonSQS amazonSQS() {
		AmazonSQS amazonSQSClient = AmazonSQSClientBuilder.standard().withRegion(Constants.REGION)
				.withCredentials(basicAWSCredentials()).build();
		return amazonSQSClient;
	}

	public AmazonEC2 amazonEC2() {
		AmazonEC2 amazonEC2 = AmazonEC2ClientBuilder.standard().withRegion(Constants.REGION)
				.withCredentials(basicAWSCredentials()).build();
		return amazonEC2;
	}
}
