package com.aws.sqslistener.handler;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.amazonaws.services.sqs.model.Message;
import com.aws.sqslistener.constants.Constants;

public class ListenerAndProcessHandlerImpl implements ListenerAndProcessHandler {
	
	private SqsHandler sqsHandler;

	private S3Handler s3Handler;
	
	private Ec2Handler ec2Handler;
	
	public ListenerAndProcessHandlerImpl() {
		sqsHandler = new SqsHandlerImpl();
		s3Handler = new S3HandlerImpl();
		ec2Handler = new Ec2HandlerImpl();
	}

	@Override
	public void mainProcessingFunction() {
		while (true) {
			
			Message message = sqsHandler.receiveMessage(Constants.INPUTQUEUENAME, 0, 15);
			
			if(message == null) {
				break;
			}
			
			String keyName = exractObjectKey(message.getBody());
			
			try {
			s3Handler.downloadImageFiles(keyName);
			} catch (FileNotFoundException e){
				
			} catch (IOException e) {
				
			}
			
			String predictionResponse = sqsHandler.modelOutput(keyName);
			
			if(predictionResponse == null) {
				predictionResponse = "Null";
			}
			
			//Omit spaces
			predictionResponse = predictionResponse.trim();
			sqsHandler.sendMessage(keyName + "/"+ predictionResponse, Constants.OUTPUTQUEUE, 0);
			sqsHandler.deleteMessage(message, Constants.INPUTQUEUENAME);
			s3Handler.putObject(keyName, predictionResponse);
		}
		ec2Handler.stopInstance();
	}
	
	private static String exractObjectKey(String message) {
		String key = "";
		try {
			JSONObject jsonObject  = new JSONObject(message);
			JSONObject s3Obj = jsonObject.getJSONArray("Records").getJSONObject(0).getJSONObject("s3");
			JSONObject object = s3Obj.getJSONObject("object");
			key = object.getString("key");
		} catch(JSONException e) {
			System.out.println (e.getMessage());
		}
       return key;
	}

}