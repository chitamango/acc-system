package com.inter.event;



//import com.inter.instance.CoordinationInstance;
import com.inter.listener.MessageEventListener;
import com.inter.listener.SharedArtifactChangeEventListener;
import com.inter.manager.SharedArtifactManager;
import com.inter.rule.AccRuleImpl;



public class EventListenerImpl implements SharedArtifactChangeEventListener, MessageEventListener {
	
	private AccRuleImpl RuleEngine ;
	private SharedArtifactManager sharedArtifactManager;
	
	
	
	
	public EventListenerImpl()
	{
		/*
		this.RuleEngine = new AccRuleImpl();
		this.sharedArtifactManager = new SharedArtifactManager();
		this.sharedArtifactManager.addEventListener(this);
		*/
		
	}
	

	
	@Override //I just worked to this part
	public void invokeRuleEngine(MessageEvent evt) {
		// TODO Auto-generated method stub
		
		//OMElement Message = evt.getMessage();
		
		//System.out.println("message event is here at event listener");
		
		RuleEngine.executeRules(evt, this.sharedArtifactManager);
		
		
	}

	@Override
	public void invokeRuleEngine(SharedArtifactChangeEvent evt) {
		// TODO Auto-generated method stub
		
		//CoordinationInstance Coordination = evt.getCoordinationInstance();
		RuleEngine.executeRules(evt, this.sharedArtifactManager);
		
	}



	@Override
	public void invokeRuleEngine(CombineEvent evt) {
		// TODO Auto-generated method stub
		
		RuleEngine.executeRules(evt, this.sharedArtifactManager);
		
	}



	@Override
	public void setRuleEngine(AccRuleImpl RuleEngine) {
		// TODO Auto-generated method stub
		
		this.RuleEngine = RuleEngine;
		
	}



	@Override
	public void setSharedArtifactManager(SharedArtifactManager sharedArtifactManager) {
		// TODO Auto-generated method stub
		
		this.sharedArtifactManager = sharedArtifactManager;
		this.sharedArtifactManager.addEventListener(this);
		
		
	}
	
	

}
