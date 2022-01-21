package com.aws.sqscontinuouslistener.handler;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.aws.sqscontinuouslistener.core.S3Core;
import com.aws.sqscontinuouslistener.core.S3CoreImpl;

public class S3HandlerImpl implements S3Handler {
	
	private S3Core s3Core;
	
	public S3HandlerImpl() {
		s3Core = new S3CoreImpl();
	}

	@Override
	public void putObject(String key, String value) {
		
		s3Core.putObject(key, value);
	}
	
	@Override
	public void downloadImageFiles(String keyName) throws FileNotFoundException, IOException{
		
		s3Core.downloadImageFiles(keyName);
	}

}