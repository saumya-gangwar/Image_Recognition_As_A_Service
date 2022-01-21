package com.aws.sqslistener.core;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.amazonaws.services.s3.model.Bucket;

public interface S3Core {
	
	public Bucket createBucket();
	
	public Bucket getBucket();
	
	public void putObject(String key, String value);
	
	public void downloadImageFiles(String keyName) throws FileNotFoundException, IOException;

}