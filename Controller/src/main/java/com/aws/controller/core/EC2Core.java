package com.aws.controller.core;

import com.amazonaws.services.ec2.model.DescribeInstanceStatusRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusResult;

public interface EC2Core {
	
	public DescribeInstanceStatusResult describeInstance(DescribeInstanceStatusRequest describeRequest);

	public Integer createInstance(String imageId, Integer maxNumberOfInstances, Integer nameCount);
}
