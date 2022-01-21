package com.aws.sqslistener;

import com.aws.sqslistener.handler.ListenerAndProcessHandler;
import com.aws.sqslistener.handler.ListenerAndProcessHandlerImpl;

public class MainListener 
{
    public static void main( String[] args )
    {
        System.out.println( "Running Listener Application" );
        
        ListenerAndProcessHandler listenerAndProcessHandler = new ListenerAndProcessHandlerImpl();
        
        listenerAndProcessHandler.mainProcessingFunction();
        
    }
}
