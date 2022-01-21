package com.aws.sqslistener.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.util.IOUtils;
import com.aws.sqslistener.config.AWSCredentialsProvider;
import com.aws.sqslistener.constants.Constants;

public class S3CoreImpl implements S3Core {
	
	private AmazonS3 amazonS3;
	
	public S3CoreImpl() {
		this.amazonS3 = AmazonS3ClientBuilder.standard()
				.withRegion(Constants.REGION)
				.withCredentials(AWSCredentialsProvider.getCredentials()).build();
	}
	
	@Override
	public Bucket createBucket() {
		
		Bucket bucket = null;
		if (amazonS3.doesBucketExistV2(Constants.OUTPUTBUCKETNAME)) {
			
			bucket = getBucket();
			
		} else {
			
			bucket = amazonS3.createBucket(Constants.OUTPUTBUCKETNAME);
			
		}

		return bucket;
	}

	@Override
	public Bucket getBucket() {
		Bucket bucket = null;
		List<Bucket> buckets = amazonS3.listBuckets();
		for (Bucket b : buckets) {
			if (b.getName().equals(Constants.OUTPUTBUCKETNAME)) {
				bucket = b;
			}
		}

		return bucket;
	}

	@Override
	public void putObject(String key, String value) {
		createBucket();
		String newKey = key.substring(0, key.indexOf("."));
		amazonS3.putObject(Constants.OUTPUTBUCKETNAME, newKey + "/" + value, value);
	}
	
	@Override
	public void downloadImageFiles(String keyName) throws FileNotFoundException, IOException{
		S3Object fullObject = null;
		fullObject = amazonS3.getObject(new GetObjectRequest(Constants.INPUTBUCKETNAME, keyName));
		S3ObjectInputStream objectContent = fullObject.getObjectContent();
    	IOUtils.copy(objectContent, new FileOutputStream(keyName));
	}

}