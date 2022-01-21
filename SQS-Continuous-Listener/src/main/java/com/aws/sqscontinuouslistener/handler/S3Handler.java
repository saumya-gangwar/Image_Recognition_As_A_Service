package com.aws.sqscontinuouslistener.handler;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface S3Handler {
	
	public void putObject(String key, String value);
	
	public void downloadImageFiles(String keyName) throws FileNotFoundException, IOException;

}