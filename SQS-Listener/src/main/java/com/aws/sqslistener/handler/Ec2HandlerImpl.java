package com.aws.sqslistener.handler;

import com.aws.sqslistener.core.Ec2Core;
import com.aws.sqslistener.core.Ec2CoreImpl;

public class Ec2HandlerImpl implements Ec2Handler {
	
	private Ec2Core ec2Core;
	
	public Ec2HandlerImpl() {
		ec2Core = new Ec2CoreImpl();
	}

	@Override
	public void stopInstance() {
		
		ec2Core.stopInstance();
	}

}