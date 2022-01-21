package com.aws.controller.handler;

import com.aws.controller.constants.Constants;

public class LoadBalancerHandlerImpl implements LoadBalancerHandler {
	
	private SqsHandler sqsHandler;
	private EC2Handler ec2Handler;
	
	public LoadBalancerHandlerImpl() {
		 sqsHandler = new SqsHandlerImpl();
		 ec2Handler = new EC2HandlerImpl();
	}

	@Override
	public void scaleOut() {
		Integer nameCount = 0;
		while (true) {
			Integer numOfMsgs = sqsHandler.getApproximateNumberOfMsgs(Constants.INPUTQUEUENAME);
			System.out.println("Number of messages in sqs -  "+ numOfMsgs);
			Integer totalRunningInstances = 0;
			try {
				Thread.sleep(20000);
				totalRunningInstances = ec2Handler.getNumberOfInstances();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			
			System.out.println("Number of running instances - "+ totalRunningInstances);
			Integer totalAppInstances = totalRunningInstances - 1;
			System.out.println("Number of app instances - "+ totalAppInstances);
			if (totalAppInstances < Constants.MAXIMUMRUNNINGINSTANCES && numOfMsgs > 0 && numOfMsgs > totalAppInstances) {
				Integer get1 = Constants.MAXIMUMRUNNINGINSTANCES - totalAppInstances;
				//System.out.println("Get 1 : " +get1);
				if (get1 > 0) {
					Integer get2 = numOfMsgs - totalAppInstances;
					//System.out.println("Get 2 : " +get2);
					if (get2 >= get1) {
						nameCount = ec2Handler.startInstances(get1, nameCount);
					} else {
						nameCount = ec2Handler.startInstances(get2, nameCount);
					}
					nameCount++;
				}
			}
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
		}
	}
}
