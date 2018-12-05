package com.inter.instance;
/*
 *  @version 1.1
 */

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import com.inter.process.Maptype;
import com.inter.process.SharedRuleType;
import com.inter.process.TransitionType;



public class SharedRuleInstance {
	
	private String ruleId;
	private String ruleName;
	private String timeStamp; 
	private String roleName;
	private String invokingService; 
	private String oprationName;
	private ArrayList<String> InputValue;
	private ArrayList<TransitionRule> transitions = new ArrayList<TransitionRule>(); 
	private ArrayList<CoordinationRule> CRule = new ArrayList<CoordinationRule>(); //this part here
	private ArrayList<NotificationRule> NRule = new ArrayList<NotificationRule>(); //this part as well
	
	
	public SharedRuleInstance(SharedRuleType xmlBindingSharedRule)
	{
		this.ruleName = xmlBindingSharedRule.getName();
		this.roleName = xmlBindingSharedRule.getDo().getInvoke().getRole();
		this.invokingService = xmlBindingSharedRule.getDo().getInvoke().getService();
		this.oprationName = xmlBindingSharedRule.getDo().getInvoke().getOperation();
		this.extractTransitionRule(xmlBindingSharedRule.getDo().getInvoke().getTransitions().getTransition());
		this.extractCoordinationRule(xmlBindingSharedRule.getDo().getInvoke().getMapping().getCoordination().getMap());
		this.extractNotificationRule(xmlBindingSharedRule.getDo().getInvoke().getMapping().getNotification().getMap());
		
	}
	
	
	private void extractTransitionRule(List<TransitionType> ListOfTransition)
	{
		ListIterator<TransitionType> itr = ListOfTransition.listIterator();
		
		while(itr.hasNext())
		{
			TransitionType CurrentTransitionType = itr.next();
			
			TransitionRule newTransitionRule = new TransitionRule(CurrentTransitionType);
			
			transitions.add(newTransitionRule);
			
		}
		
		
		
	}
	
	
	private void extractCoordinationRule(List<Maptype> ListOfCoordinationRule)
	{
		
		ListIterator<Maptype> itr = ListOfCoordinationRule.listIterator();
		
		while(itr.hasNext())
		{
			Maptype CurrentMaptype = itr.next();
			
			CoordinationRule newCoordinationRule = new CoordinationRule(CurrentMaptype);
			
			CRule.add(newCoordinationRule);
			
		}
		
	}	
		
	private void extractNotificationRule(List<Maptype> ListOfNotificationRule)
	{
			
			ListIterator<Maptype> itr = ListOfNotificationRule.listIterator();
			
			while(itr.hasNext())
			{
				Maptype CurrentMaptype = itr.next();
				
				NotificationRule newMapRule = new NotificationRule(CurrentMaptype);
				
				NRule.add(newMapRule);
				
			}
		
		
		
		
	}
	
	public String getRuleName()
	{
		
		return this.ruleName;
		
	}
	
	public String getRoleName()
	{
		
		return this.roleName;
		
	}
	
	public String getInvokingServiceName()
	{
		
		return this.invokingService;
		
		
	}
	
	public String getOperationName()
	{
		
		return this.oprationName;
		
	}
	
	public String getTimeStamp()
	{
		
		return this.timeStamp;
		
	}
	
	public ArrayList<TransitionRule> getTransitionRules()
	{
		
		return this.transitions;
		
		
	}
	
	public ArrayList<CoordinationRule> getCoordinationRules()
	{
		
		
		return this.CRule;
		
	}
	
	public ArrayList<NotificationRule> getNotificationRules()
	{
		
		
		return this.NRule;
		
	}
	
	
	
	public void initialRuleInstance(String RuleId)
	{
		this.timeStamp = DateFormat.getDateTimeInstance().format(new Date());
		this.ruleId = RuleId;
		
	}
	
	public String getRuleId()
	{
		
		return this.ruleId;
		
	}
	
	
	
	
	

}
