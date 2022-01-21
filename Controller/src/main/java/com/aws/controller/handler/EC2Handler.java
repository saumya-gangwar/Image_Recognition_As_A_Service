package com.aws.controller.handler;

public interface EC2Handler {
	
	public Integer getNumberOfInstances();

	public Integer startInstances(Integer count, Integer nameCount);
	
}
