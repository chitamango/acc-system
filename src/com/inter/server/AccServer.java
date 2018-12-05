package com.inter.server;
/*
 *  @version 1.1
 */

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.engine.AxisConfiguration;
import org.apache.axis2.transport.http.SimpleHTTPServer;

import com.inter.event.EventDispatcher;
import com.inter.event.EventListenerImpl;
import com.inter.listener.MessageEventListener;
import com.inter.manager.SharedArtifactManager;
import com.inter.rule.AccRuleImpl;



public class AccServer extends SimpleHTTPServer{

	/**
	 * @param args
	 */
	public static final String AXIS2_REPOSITORY = "repository";
	public static final String AXIS2_configuration = "conf/axis2.xml";
	public static EventDispatcher eventDispatcher; 
	public static MessageEventListener msgEventListener;
	public static SharedArtifactManager sharedArtifactManager ;
	public static AccRuleImpl RuleEngine; 
	
	
	public AccServer(ConfigurationContext confContext, int i) throws AxisFault
	{
		
		super(confContext,i);
		
	}
	
	public void start()
	{
		try {
			super.start();
			
			//instantiate 
			sharedArtifactManager = new SharedArtifactManager();
			RuleEngine = new AccRuleImpl();
			eventDispatcher = new EventDispatcher();
			msgEventListener = new EventListenerImpl();
			
			//initial EventListener Implement class
			msgEventListener.setSharedArtifactManager(sharedArtifactManager);   	
			msgEventListener.setRuleEngine(RuleEngine);
			
			
			//initial EventDispatcher
			eventDispatcher.addEventListener(msgEventListener);
			eventDispatcher.setSharedArtifactManager(sharedArtifactManager);
			sharedArtifactManager.setEventDispatcher(eventDispatcher);
			
			//get AxixConfiguration
        	AxisConfiguration myCustomAxisConfiguration = super.configurationContext.getAxisConfiguration();
			//retreive AcpReceiver
        	 AccMsgReceiver AccReceiver =  (AccMsgReceiver) myCustomAxisConfiguration.getMessageReceiver("http://www.w3.org/2004/10/wsdl/in-out");
            //initial AcpReceiver 	
        	 AccReceiver.setEventDispatcher(eventDispatcher);
        	 
        	 AccInOnlyMsgReceiver AccInOnlyReceiver =  (AccInOnlyMsgReceiver) myCustomAxisConfiguration.getMessageReceiver("http://www.w3.org/2004/10/wsdl/in-only");
             //initial AcpReceiver 	
        	 AccInOnlyReceiver.setEventDispatcher(eventDispatcher);
        
        	 System.out.println("Initialize RuleEngine");
        	 System.out.println("Initialize SharedArtifactManager");
        	 
			
			
			
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try {
				ConfigurationContext confContext =
				     ConfigurationContextFactory.createConfigurationContextFromFileSystem(
				    		 AXIS2_REPOSITORY, AXIS2_configuration);
				
					
				 //change server port here
					new AccServer(confContext, 8000).start();
					
				
			} catch (AxisFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		
		
		

	}

}
