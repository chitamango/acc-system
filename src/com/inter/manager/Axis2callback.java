package com.inter.manager;
/*
 *  @version 1.1
 */

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.client.async.AxisCallback;
import org.apache.axis2.context.MessageContext;

public class Axis2callback implements AxisCallback {
	
	
	private OMElement Response;
	
	
	

	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		
		System.out.println("asynchronous invoking completed");
		
		
	}

	@Override
	public void onError(Exception e) {
		// TODO Auto-generated method stub
	
		e.printStackTrace();
		
		
	}

	@Override
	public void onFault(MessageContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(MessageContext messagecontext) {
		// TODO Auto-generated method stub
		
		String namespace = messagecontext.getEnvelope().getBody().getFirstElement().getNamespace().getNamespaceURI();
		
		QName returnQname = new QName(namespace, "return");
		
		System.out.println(this.Response);
		
		this.Response = messagecontext.getEnvelope().getBody().getFirstElement().getFirstChildWithName(returnQname).getFirstElement();
		
	}
	
	public OMElement getResponseMessage()
	{
		
		return this.Response;
		
	}

}
