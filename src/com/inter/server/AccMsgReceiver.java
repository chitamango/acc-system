package com.inter.server;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.AxisConfiguration;
import org.apache.axis2.rpc.receivers.RPCMessageReceiver;
import com.inter.event.EventDispatcher;



public class AccMsgReceiver extends RPCMessageReceiver {
	
	private EventDispatcher EDispatcher;
	
	public void invokeBusinessLogic(MessageContext inMessageContext,
            MessageContext outMessageContext) throws AxisFault
	{
		
		System.out.println("message is here in acc msgreceiver");
		
		//access global axis configuration
		AxisConfiguration myCustomAxisConfiguration =  inMessageContext.getConfigurationContext().getAxisConfiguration();
		//receive AcpMessage Receiver
		AccMsgReceiver AccReceiver = (AccMsgReceiver) myCustomAxisConfiguration.getMessageReceiver("http://www.w3.org/2004/10/wsdl/in-out");
	       
      	//add event dispatcher from global config to AcpReceiver 
      	 this.setEventDispatcher(AccReceiver.getEventDispathcer());
      	   	 

		
     	SOAPEnvelope InMessageEnvelope = inMessageContext.getEnvelope();
     		
		
     	
     	SOAPBody InMessageBody = InMessageEnvelope.getBody();
     	
	
     	OMElement SoapAction = InMessageBody.getFirstElement();
     		
		EDispatcher.eventReceived(inMessageContext, SoapAction); // this should return something so that we can manipulate outgoing message
		
		while(EDispatcher.getCoordinationId() == null)
		{
			
			
			
		}
		
		
		SOAPFactory fac = OMAbstractFactory.getSOAP11Factory();
		
		SOAPEnvelope envelope = fac.getDefaultEnvelope();
		
		OMElement coordinationid = fac.createOMElement("coordination_id", SoapAction.getNamespace());
		
		System.out.println(EDispatcher.getCoordinationId());
		
		coordinationid.setText(EDispatcher.getCoordinationId());

		envelope.getBody().addChild(coordinationid );
		
		outMessageContext.setEnvelope(envelope);
		
	
		
		
		
		
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
