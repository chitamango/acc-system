package com.inter.manager;
/*
 *  @version 1.1
 */

import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.event.EventListenerList;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;





import com.inter.event.EventDispatcher;
import com.inter.event.SharedArtifactChangeEvent;
import com.inter.factory.AccFactory;
import com.inter.instance.CoordinationInstance;
import com.inter.instance.RoleInstance;
import com.inter.instance.SharedArtifactInstance;
import com.inter.instance.SharedAttributeInstance;
import com.inter.instance.SharedRuleInstance;
import com.inter.listener.SharedArtifactChangeEventListener;

public class SharedArtifactManager {
	
	private AccFactory factory;
	private ArrayList<CoordinationInstance> listOfCoordinationInstance;
	private SharedArtifactDataManager sharedArtifactDatamanager;
	private RoleManager roleManager;
	private EventListenerList listenerList ;
	private Mapper DataMapper ;
	private AccLogger Acclogger;
	private EventDispatcher EDispatcher;
	
	
	
	
	public SharedArtifactManager()
	{
		
		this.factory = new AccFactory();
		this.listOfCoordinationInstance = new ArrayList<CoordinationInstance>();
		this.DataMapper = new Mapper();
		this.sharedArtifactDatamanager = new SharedArtifactDataManager(this.DataMapper);	
		this.roleManager = new RoleManager(this.DataMapper);
		this.listenerList = new EventListenerList();
		this.Acclogger = new AccLogger();
		
		System.out.println("Initial Shared Artifact Data Manager");
		System.out.println("Initial Role Manager");
		
		

		
		
	}
	
	public void startProcess(ArrayList<String> SharedArtifactNames, String RoleName,String ServiceName,String OperationName, SharedRuleInstance ExecutedSharedRule,String CoordinationName, OMElement RequestMessage) throws Exception
	{
		RoleInstance InvokedRole = null;
		
		//get process instance of particular process and set processID
		CoordinationInstance Coordination = factory.getCoordinationInstance(CoordinationName);
	
		
		Coordination.setCoordinationId(Coordination.getCoordinationName()+"-P"+(this.listOfCoordinationInstance.size()+1));
		
		//System.out.println(Process.getProcessId());
		
		//add process instance to the process instance list
		
		this.listOfCoordinationInstance.add(Coordination);
		
		//start the thread
		
		
		Coordination.start();
		
		//create process log
		
		Acclogger.createProcessLog(Coordination);
		
		//set ruleID of a rule instance here and add ruleInstance
		
		
		
		Coordination.addSharedRuleInstance(this.initialSharedRuleWithIdAndTimeStamp(Coordination, ExecutedSharedRule));
		
		
		//this loop retrieve artifact name in order to create artifact instances
		ListIterator<String> itr = SharedArtifactNames.listIterator();
		
	
		while(itr.hasNext())
		{
			String SharedArtifactName = itr.next().toString();
			//Artifact is created here, basically it has no business data
			SharedArtifactInstance SharedArtifact = factory.getSharedArtifactInstance(CoordinationName, SharedArtifactName);
			
			sharedArtifactDatamanager.AddSharedArtifactInstance(SharedArtifact, Coordination);
			
			
		}
		
		//create service instance and invoke a webservice using Client Message data
		
		InvokedRole = factory.getRoleInstance(CoordinationName, RoleName, ServiceName, OperationName) ;
				
		Coordination.addRoleInstance(this.initialRoleWithId(Coordination, InvokedRole));
			
		Acclogger.createProcessLogRecord(Coordination.getCoordinationLog(), Coordination.getSharedArtifactInstanceList(), ExecutedSharedRule.getRuleId(), InvokedRole.getRoleID(), ExecutedSharedRule.getTimeStamp());
		
		roleManager.invokeParticipatingRole(Coordination.getSharedArtifactInstanceList(), RequestMessage, RoleName, ServiceName, OperationName, ExecutedSharedRule, Coordination); 
		
		//update artifact 
		
		sharedArtifactDatamanager.updateSharedArtifact(Coordination.getSharedArtifactInstanceList(), InvokedRole.getNotificationMessage(), Coordination, ExecutedSharedRule);
		
		//inital artifact id
		ListIterator<SharedArtifactInstance> SharedArtifactInstanceListIterator = Coordination.getSharedArtifactInstanceList().listIterator();
		while(SharedArtifactInstanceListIterator.hasNext())
		{
			SharedArtifactInstance CurrentSharedArtifactInstance = SharedArtifactInstanceListIterator.next();
			if(CurrentSharedArtifactInstance.getSharedArtifactId() == null)
			{
				this.initialSharedArtifactInstanceWithId(CurrentSharedArtifactInstance);
				
				
			}
			
			
		}
		
		
		
		
		//should fire event here
		Acclogger.UpdateProcessLogRecord(Coordination.getCoordinationLog(), Coordination.getSharedArtifactInstanceList());
		
		this.EDispatcher.setCoordinationId(Coordination.getCoordinationId());
		
		this.fireSharedArtifactChangeEvent(new SharedArtifactChangeEvent(this,Coordination));
	
		
		
	}
	
	public void runProcess(ArrayList<String> SharedArtifactNames,OMElement RequestMessage ,String RoleName,String ServiceName,String OperationName,SharedRuleInstance ExecutedSharedRule,String CoordinationID) throws Exception
	{
		
		CoordinationInstance Coordination = null;
		RoleInstance InvokedRole = null;
		ListIterator<CoordinationInstance> CoordinationItr = this.listOfCoordinationInstance.listIterator();
		
		
		while(CoordinationItr.hasNext())
		{
			 CoordinationInstance CurrentIteratedCoordination = CoordinationItr.next();
			if(CoordinationID.equals(CurrentIteratedCoordination.getCoordinationId()))
			{
				
				Coordination = CurrentIteratedCoordination;
				
			}
			else
			{
			
				// do something here ie. show error message
		
			}
			
			
			
		}
		
		//add ruleInstance 
		

		
		Coordination.addSharedRuleInstance(this.initialSharedRuleWithIdAndTimeStamp(Coordination, ExecutedSharedRule));
		
		
		if(SharedArtifactNames.size() != 0)
		{
			ListIterator<String> itr = SharedArtifactNames.listIterator();
			
			
			//artifact is created at this point
			while(itr.hasNext())
			{
				String SharedArtifactName = itr.next().toString();
				
				SharedArtifactInstance Artifact = factory.getSharedArtifactInstance(Coordination.getCoordinationName(), SharedArtifactName);
				
				sharedArtifactDatamanager.AddSharedArtifactInstance(Artifact, Coordination); //?? not consistent
	
				
			}
		
		}

		
		//create service instance and invoke a web service using Artifact data or client message data
		
		InvokedRole = factory.getRoleInstance(Coordination.getCoordinationName(),RoleName, ServiceName, OperationName ); // have to fix this to support operation
		
		Coordination.addRoleInstance(this.initialRoleWithId(Coordination, InvokedRole));
			
		Acclogger.createProcessLogRecord(Coordination.getCoordinationLog(), Coordination.getSharedArtifactInstanceList(), ExecutedSharedRule.getRuleId(), InvokedRole.getRoleID() , ExecutedSharedRule.getTimeStamp());
		//may have a condition to check whether a role need to be invoked
		if(RoleName != "system")
		{
		
		roleManager.invokeParticipatingRole(Coordination.getSharedArtifactInstanceList(), RequestMessage, RoleName, ServiceName, OperationName, ExecutedSharedRule, Coordination);
		
		}
		//update artifact 
		
		sharedArtifactDatamanager.updateSharedArtifact(Coordination.getSharedArtifactInstanceList(), InvokedRole.getNotificationMessage(), Coordination, ExecutedSharedRule);
		
		//inital artifact id
		ListIterator<SharedArtifactInstance> SharedArtifactInstanceListIterator = Coordination.getSharedArtifactInstanceList().listIterator();
		while(SharedArtifactInstanceListIterator.hasNext())
		{
			SharedArtifactInstance CurrentArtifactInstance = SharedArtifactInstanceListIterator.next();
			if(CurrentArtifactInstance.getSharedArtifactId() == null)
			{
				this.initialSharedArtifactInstanceWithId(CurrentArtifactInstance);
				
				
			}
			
			
		}
		
		
		//updatelog again
		
		Acclogger.UpdateProcessLogRecord(Coordination.getCoordinationLog(), Coordination.getSharedArtifactInstanceList());
		
		
		//fire event
		this.fireSharedArtifactChangeEvent(new SharedArtifactChangeEvent(this,Coordination));
		
		
		
	}
	
	public void endProcess(String CoordinationID, SharedRuleInstance ExecutedSharedRule) throws Exception
	{
		
		CoordinationInstance Coordination = null;
		ListIterator<CoordinationInstance> CoordinationItr = this.listOfCoordinationInstance.listIterator();
		
		while(CoordinationItr.hasNext())
		{
			 CoordinationInstance CurrentIteratedCoordination = CoordinationItr.next();
			if(CoordinationID.equals(CurrentIteratedCoordination.getCoordinationId()))
			{
				
				Coordination = CurrentIteratedCoordination;
				
			}
			else
			{
			
				// do something here ie. show error message
		
			}
			
			
			
		}
		
		
		//add ruleInstance 
		
		Coordination.addSharedRuleInstance(this.initialSharedRuleWithIdAndTimeStamp(Coordination, ExecutedSharedRule));
		
		Acclogger.createProcessLogRecord(Coordination.getCoordinationLog(), Coordination.getSharedArtifactInstanceList(), ExecutedSharedRule.getRuleId(), "system", ExecutedSharedRule.getTimeStamp());
		
		sharedArtifactDatamanager.updateSharedArtifact(Coordination.getSharedArtifactInstanceList(), Coordination, ExecutedSharedRule);
		
		
		// should change state of artifact to end state
		
	
		
		Acclogger.UpdateProcessLogRecord(Coordination.getCoordinationLog(), Coordination.getSharedArtifactInstanceList());
		
	
		
		
		//endprocess here
		
		
	}
	
	private SharedRuleInstance initialSharedRuleWithIdAndTimeStamp(CoordinationInstance Coordination,SharedRuleInstance SharedRule)
	{
		Integer RuleInstanceListSize = Coordination.getSharedRuleInstanceList().size();
		
		String GeneratedRuleID =  "RL"+(RuleInstanceListSize+1)+":"+SharedRule.getRuleName();
		
		SharedRule.initialRuleInstance(GeneratedRuleID);
		
		return SharedRule;
		
		
	}
	
	
	private RoleInstance initialRoleWithId(CoordinationInstance Coordination, RoleInstance InvokedRole)
	{
		Integer RoleInstanceListSize = Coordination.getRoleInstanceList().size();
		
		String GeneratedRoleID = "S"+(RoleInstanceListSize+1) +":"+ InvokedRole.getRole()+ "-"+ InvokedRole.getService() + "-" + InvokedRole.getOperation() ;
		
		InvokedRole.setRoleID(GeneratedRoleID);
		
		return InvokedRole;
		
	}
	
	private void initialSharedArtifactInstanceWithId( SharedArtifactInstance SharedArtifact)
	{
		ListIterator<SharedAttributeInstance> AttributeInstanceListIterator = SharedArtifact.getAttributeList().listIterator();
		String Artifactid = SharedArtifact.getSharedArtifactName()+ ":";
		
		while(AttributeInstanceListIterator.hasNext())
		{
			SharedAttributeInstance CurrentSharedAttributeInstance = AttributeInstanceListIterator.next();
			if(CurrentSharedAttributeInstance.isUniqueId() == true)
			{
				try {
					
					Artifactid = Artifactid + CurrentSharedAttributeInstance.get(0).toString();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
			
		}
		
		SharedArtifact.setSharedArtifactId(Artifactid);
		
		
	}
	
	  public CoordinationInstance getCoordinationInstance(String CoordinationId)
	  {
		  ListIterator<CoordinationInstance> Coordinationitr = this.listOfCoordinationInstance.listIterator();
		  CoordinationInstance coordination = null;
		  
			while(Coordinationitr.hasNext())
			{
				CoordinationInstance CurrentCoordination = Coordinationitr.next();
				if(CoordinationId.equals(CurrentCoordination.getCoordinationId()))
				{
					
					coordination = CurrentCoordination;
					
					break;
				}
			
				
				
			}
		  
		  
		  
		  
		  return coordination;
		  
		  
	
		  
	  }
	  
	  public void setEventDispatcher(EventDispatcher Edispatcher)
	  {
		  
		  this.EDispatcher = Edispatcher;
		  
	  }

	
	public void addEventListener(SharedArtifactChangeEventListener listener) {
	    listenerList.add(SharedArtifactChangeEventListener.class, listener);
	    
	  }
	
	  public void removeEventListener(SharedArtifactChangeEventListener listener) {
	    listenerList.remove(SharedArtifactChangeEventListener.class, listener);
	  }
	  
	  private void fireSharedArtifactChangeEvent(SharedArtifactChangeEvent sharedArtifactChangeEvent) {
	    Object[] listeners = listenerList.getListenerList();
	    for (int i = 0; i < listeners.length; i = i+2) {
	      if (listeners[i] == SharedArtifactChangeEventListener.class) {
	        ((SharedArtifactChangeEventListener) listeners[i+1]).invokeRuleEngine(sharedArtifactChangeEvent);
	      }
	    }
	
	  }

}
