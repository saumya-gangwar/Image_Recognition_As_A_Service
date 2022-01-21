package com.aws.controller.handler;

import com.aws.controller.core.SqsCore;
import com.aws.controller.core.SqsCoreImpl;

public class SqsHandlerImpl implements SqsHandler{

	private SqsCore sqsCore = new SqsCoreImpl();
	
	@Override
	public Integer getApproximateNumberOfMsgs(String queueName) {
		return sqsCore.getApproximateNumberOfMsgs(queueName);
	}

}
