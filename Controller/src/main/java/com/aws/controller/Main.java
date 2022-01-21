package com.aws.controller;

import com.aws.controller.handler.LoadBalancerHandler;
import com.aws.controller.handler.LoadBalancerHandlerImpl;

public class Main {

	public static void main(String[] args) {
	
		LoadBalancerHandler loadBalancer = new LoadBalancerHandlerImpl();
		loadBalancer.scaleOut();
	}

}
