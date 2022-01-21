package com.aws.controller.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.aws.controller.config.AwsConfiguration;
import com.aws.controller.constants.Constants;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusResult;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.AmazonEC2Exception;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TagSpecification;

public class EC2CoreImpl implements EC2Core {
	private AwsConfiguration awsConfiguration = new AwsConfiguration();
	private AmazonEC2 amazonEc2 = null;
	int numOfInstance = 1;

	public EC2CoreImpl() {
		amazonEc2 = awsConfiguration.amazonEC2();
	}

	@Override
	public Integer createInstance(String imageId, Integer maxNumberOfInstances, Integer nameCount) {
		
		int minInstanceCount = maxNumberOfInstances - 1; // create 1 instance
		int maxInstanceCount = maxNumberOfInstances;
		System.out.println("Creating the min num of instance - " + minInstanceCount);
		System.out.println("Creating the max num of instance - " + maxNumberOfInstances);
		if (minInstanceCount == 0)
			minInstanceCount = 1;
		for (int i = 0; i < maxInstanceCount; i++) {
			List<String> securityGroupIds = new ArrayList<String>();
			securityGroupIds.add(Constants.SECURITYGROUPID);
			Collection<TagSpecification> tagSpecifications = new ArrayList<TagSpecification>();
			TagSpecification tagSpecification = new TagSpecification();
			Collection<Tag> tags = new ArrayList<Tag>();
			Tag t = new Tag();
			t.setKey("Name");
			System.out.println("Creating Instance number " + numOfInstance);
			t.setValue("app-instance" + numOfInstance++);
			tags.add(t);
			tagSpecification.setResourceType("instance");
			tagSpecification.setTags(tags);
			tagSpecifications.add(tagSpecification);
			RunInstancesRequest rir = new RunInstancesRequest(imageId, 1, 1);
			rir.setInstanceType("t2.micro");
			rir.setSecurityGroupIds(securityGroupIds);
			rir.setTagSpecifications(tagSpecifications);
			RunInstancesResult result = null;
			try {
				result = amazonEc2.runInstances(rir);
				
			} catch (AmazonEC2Exception amzEc2Exp) {
				return nameCount;
			} catch (Exception e) {
				return nameCount;
			}
			if (numOfInstance == Constants.MAXIMUMRUNNINGINSTANCES) {
				numOfInstance = 1;
			}
		}
		return nameCount;
	}

	@Override
	public DescribeInstanceStatusResult describeInstance(DescribeInstanceStatusRequest describeRequest) {
		return amazonEc2.describeInstanceStatus(describeRequest);
	}

}
