package com.aws.controller.handler;


import java.util.List;

import com.amazonaws.services.ec2.model.DescribeInstanceStatusRequest;
import com.amazonaws.services.ec2.model.DescribeInstanceStatusResult;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.InstanceStateName;
import com.amazonaws.services.ec2.model.InstanceStatus;
import com.aws.controller.constants.Constants;
import com.aws.controller.core.EC2Core;
import com.aws.controller.core.EC2CoreImpl;

public class EC2HandlerImpl implements EC2Handler {

	private EC2Core ec2Core = new EC2CoreImpl();
	
	@Override
	public Integer startInstances(Integer count, Integer nameCount) {
		return ec2Core.createInstance(Constants.AMIID, count, nameCount);
		
	}
	
	@Override
	public Integer getNumberOfInstances() {
		DescribeInstanceStatusRequest describeRequest = new DescribeInstanceStatusRequest();
		describeRequest.setIncludeAllInstances(true);
		DescribeInstanceStatusResult describeInstances = ec2Core.describeInstance(describeRequest);
		List<InstanceStatus> instanceStatusList = describeInstances.getInstanceStatuses();
		Integer countOfRunningInstances = 0;
		for (InstanceStatus instanceStatus : instanceStatusList) {
			InstanceState instanceState = instanceStatus.getInstanceState();
			if (instanceState.getName().equals(InstanceStateName.Running.toString())) {
				countOfRunningInstances++;
			}
		}

		return countOfRunningInstances;
	}

}
