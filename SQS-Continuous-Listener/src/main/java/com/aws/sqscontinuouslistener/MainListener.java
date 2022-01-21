package com.aws.sqscontinuouslistener;

import com.aws.sqscontinuouslistener.handler.ListenerAndProcessHandler;
import com.aws.sqscontinuouslistener.handler.ListenerAndProcessHandlerImpl;

public class MainListener 
{
    public static void main( String[] args )
    {
        System.out.println( "Running Listener Continuous Application" );
        
        ListenerAndProcessHandler listenerAndProcessHandler = new ListenerAndProcessHandlerImpl();
        
        listenerAndProcessHandler.mainProcessingFunction();
        
    }
}
