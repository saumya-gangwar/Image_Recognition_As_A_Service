package com.aws.controller.constants;

import com.amazonaws.regions.Regions;

public class Constants {

	public static final Regions REGION = Regions.US_EAST_1;
	public static final String INPUTQUEUENAME = "InputRequestQueue";
	public static final String OUTPUTQUEUE = "OutputResponseQueue";
	public static final Integer MAXIMUMRUNNINGINSTANCES = 19;
	public static final String AMIID = "ami-0c0e913a920737c30";
	public static final String SECURITYGROUPID = "sg-0dc186d14407ca152";
	
}
