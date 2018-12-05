package com.inter.event;
/*
 *  @version 1.1
 */


import java.util.Iterator;

import javax.swing.event.EventListenerList;

import org.apache.axiom.om.OMElement;




import com.inter.instance.CoordinationInstance;
import com.inter.listener.MessageEventListener;
import com.inter.manager.SharedArtifactManager;



public class EventDispatcher {
	
	
	
	protected EventListenerList listenerList = new EventListenerList();
	protected SharedArtifactManager sharedArtifactManager; 
	protected String CoordinationId;
	
	/**
	 * 
	 * function to add eventlistener (request & message event)
	 * 
	 */
	
	
	public void addEventListener(MessageEventListener listener) {
	    listenerList.add(MessageEventListener.class, listener);
	   
	    
	  }
	
	
	/**
	 * 
	 * function to remove eventlistener (request & message event)
	 * 
	 * 
	 */
	 
	  
	  public void removeEventListener(MessageEventListener listener) {
		    listenerList.remove(MessageEventListener.class, listener);
		  }
	  
	  /**
	   * 
	   * private function to fire an event
	   * 
	   */
	
	  
	  private void fireMessageEvent(MessageEvent evt) {
		    Object[] listeners = listenerList.getListenerList();
		    for (int i = 0; i < listeners.length; i = i+2) {
		      if (listeners[i] == MessageEventListener.class) {
		        ((MessageEventListener) listeners[i+1]).invokeRuleEngine(evt);
		      }
		    }
		
		  }
	  
	  private void fireCombineEvent(CombineEvent evt) {
		    Object[] listeners = listenerList.getListenerList();
		    for (int i = 0; i < listeners.length; i = i+2) {
		      if (listeners[i] == MessageEventListener.class) {
		        ((MessageEventListener) listeners[i+1]).invokeRuleEngine(evt);
		      }
		    }
		
		  }
	
	
	  /**
	   * 
	   * receive an object event and determine event type and fire event
	   * 
	   * 
	   */
	  public void eventReceived(Object Source, Object eventdata)
	  {
		
				  
		 if(eventdata.getClass().getInterfaces()[0].equals(OMElement.class))
		  {
			  
			  OMElement MessageData = (OMElement)eventdata;
			 
			  if(this.checkCoordinationInstance(MessageData) == null)
			  {	 
			  
				  MessageEvent messageEvent = new MessageEvent(Source, MessageData);
				  
				  this.fireMessageEvent(messageEvent);
			  
			  }
			  else
			  {
				  CoordinationInstance TargetCoordinationInstance = this.checkCoordinationInstance(MessageData);
				  
				  CombineEvent comEvent = new CombineEvent(Source, MessageData, TargetCoordinationInstance);
				  
				  this.fireCombineEvent(comEvent);
				  
				  
				  
			  }
			  
			  
			  
			  
		  }
		  else
		  {
			  
			  System.out.println("Message Event could pass if-condition");
			  
		  }
		  
	
		  
		 	  
		  
	  }
	
	  public void setSharedArtifactManager(SharedArtifactManager sharedArtifactManager)
	{
		  
			this.sharedArtifactManager = sharedArtifactManager;
				
		}
	  
		private CoordinationInstance checkCoordinationInstance(OMElement MessageData)
		{
			CoordinationInstance targetProcessInstance = null; 
			
			Iterator<OMElement> messageIterator = MessageData.getChildElements();
			
			while(messageIterator.hasNext())
			{
				OMElement MessageElement = messageIterator.next();
				
				String MessageParameterdata = MessageElement.getText();
				
				if(this.sharedArtifactManager.getCoordinationInstance(MessageParameterdata) != null)
				{
					
					targetProcessInstance = this.sharedArtifactManager.getCoordinationInstance(MessageParameterdata);
					
				}
				
				
				
			}
			
			
			
			
			
			return targetProcessInstance;
		}
		
		
		  public synchronized void setCoordinationId(String coordinationId)
			{
				
				this.CoordinationId = coordinationId;
				
			}
			
			public String getCoordinationId()
			{
				
				 return this.CoordinationId;
				
			}
		
	  
	  
}
