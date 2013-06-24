package com.inter.server;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.AxisConfiguration;
import org.apache.axis2.rpc.receivers.RPCInOnlyMessageReceiver;

import com.inter.event.EventDispatcher;



public class AccInOnlyMsgReceiver extends RPCInOnlyMessageReceiver {
	
	
	private EventDispatcher EDispatcher;
	
	public void invokeBusinessLogic(MessageContext inMessageContext) throws AxisFault
	{
		
		System.out.println("message is here in acc in only msgreceiver");
		
		//access global axis configuration
		AxisConfiguration myCustomAxisConfiguration =  inMessageContext.getConfigurationContext().getAxisConfiguration();
		//receive AcpMessage Receiver
		AccInOnlyMsgReceiver AccInOnlyReceiver = (AccInOnlyMsgReceiver) myCustomAxisConfiguration.getMessageReceiver("http://www.w3.org/2004/10/wsdl/in-only");
	       
      	//add event dispatcher from global config to AcpReceiver 
      	 this.setEventDispatcher(AccInOnlyReceiver.getEventDispathcer());
      	 
     	
		
     	SOAPEnvelope InMessageEnvelope = inMessageContext.getEnvelope();
     		
		
     	
     	SOAPBody InMessageBody = InMessageEnvelope.getBody();
     	
	
     	OMElement SoapAction = InMessageBody.getFirstElement();
     		
		EDispatcher.eventReceived(inMessageContext, SoapAction); // this should return something so that we can manipulate outgoing message
		
		
		
		
	}
	
	public void setEventDispatcher(EventDispatcher eventDispatcher)
	{
		
		EDispatcher = eventDispatcher;
		
		
	}
	
	public EventDispatcher getEventDispathcer()
	{
		return EDispatcher;
		
		
	}

}
