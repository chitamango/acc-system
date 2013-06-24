package com.inter.instance;

import com.inter.process.TransitionType;



public class TransitionRule {
	
	private String artifact;
	private String fromState;
	private String toState;
	
	
	public TransitionRule(TransitionType xmlBindingTransition)
	{
		this.artifact = xmlBindingTransition.getSharedArtifact();
		this.fromState = xmlBindingTransition.getFromState();
		this.toState = xmlBindingTransition.getToState();
		
		
		
	}
	
	public String getArtifact()
	{
		
		return this.artifact;
		
		
	}
	
	public String getFromState()
	{
		
		return this.fromState;
		
	}
	
	public String getToState()
	{
		
		
		return this.toState;
		
		
	}
	
	
	

}
