package com.aws.sqscontinuouslistener.config;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

public class AWSCredentialsProvider {
	private static ProfileCredentialsProvider credentialsProvider = null;
	 public static ProfileCredentialsProvider getCredentials() {
		 credentialsProvider = new ProfileCredentialsProvider();
		 try {
	            credentialsProvider.getCredentials();
	        } catch (Exception e) {
	            throw new AmazonClientException(
	                    "Cannot load the credentials from the credential profiles file. " +
	                    "Please make sure that your credentials file is at the correct " +
	                    "location (~/.aws/credentials), and is in valid format.",
	                    e);
	        }
		 return credentialsProvider;
	 }
}
