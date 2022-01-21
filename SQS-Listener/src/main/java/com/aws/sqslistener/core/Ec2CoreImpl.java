package com.aws.sqslistener.core;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.util.EC2MetadataUtils;
import com.aws.sqslistener.config.AWSCredentialsProvider;
import com.aws.sqslistener.constants.Constants;

public class Ec2CoreImpl implements Ec2Core {
	
	private AmazonEC2 amazonEc2;
	
	public Ec2CoreImpl() {
		this.amazonEc2 = AmazonEC2ClientBuilder.standard()
				.withRegion(Constants.REGION)
				.withCredentials(AWSCredentialsProvider.getCredentials()).build();
	}
	
	@Override
	public void stopInstance() {
		String myInstanceId = EC2MetadataUtils.getInstanceId();
		TerminateInstancesRequest request = new TerminateInstancesRequest().withInstanceIds(myInstanceId);
		amazonEc2.terminateInstances(request);
	}

}