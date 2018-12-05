package com.inter.instance;
/*
 *  @version 1.1
 */

public class SharedStateInstance {
	
	private String Name;
	private String Type;
	
	
	public SharedStateInstance(String StateName,String StateType)
	{
		this.Name = StateName;
		this.Type = StateType;
				
	}
	
	public SharedStateInstance(String StateName)
	{
		this.Name = StateName;
		
				
	}
	
	
	
	public void setStateName(String StateName)
	{
		
		this.Name = StateName;
		
		
	}
	
	public String getStateName()
	{
		
		return this.Name;
		
	}
	
	public void setStateType(String StateType)
	{
		
		this.Type = StateType;
		
	}
	
	public String getStateType()
	{
		return this.Type;
		
		
	}

}
