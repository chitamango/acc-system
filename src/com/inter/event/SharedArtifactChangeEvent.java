package com.inter.event;
/*
 *  @version 1.1
 */

import java.util.EventObject;


import com.inter.instance.CoordinationInstance;
import com.inter.instance.SharedArtifactInstance;
import com.inter.instance.SharedAttributeInstance;

public class SharedArtifactChangeEvent extends EventObject {
	
	protected CoordinationInstance InstanceOfCoordination;

	public SharedArtifactChangeEvent(Object source, CoordinationInstance coordination) {
		super(source);
		// TODO Auto-generated constructor stub
		
		this.InstanceOfCoordination = coordination;
		
		
		
		
	}
	
	public CoordinationInstance getCoordinationInstance()
	{
		
		
		
		return this.InstanceOfCoordination;
		
	}
	
	public String getSharedArtifactData(String SharedArtifactName, String AttributeName) 
	{
		String value = null;
		
		if( this.InstanceOfCoordination.getSharedArtifactInstance(SharedArtifactName) != null)
		{
			SharedArtifactInstance TargetArtifact = this.InstanceOfCoordination.getSharedArtifactInstance(SharedArtifactName);
			
			if(TargetArtifact.getSharedAttributeInstance(AttributeName) != null)
			{
				SharedAttributeInstance TargetAttribute = TargetArtifact.getSharedAttributeInstance(AttributeName);
				
				if(TargetAttribute.getAttributeStructure().equalsIgnoreCase("pair"))
				{
					try {
						if(TargetAttribute.Size() > 0)
						{
							value = TargetAttribute.get(0).toString();
							
						}	
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else if(TargetAttribute.getAttributeStructure().equalsIgnoreCase("list"))
				{
					
				
						
					
				}
				
				
				
			}
			
			
		
		
		}
		
		return value;
	}
	
	
	public String getArtifactState(String ArtifactName)
	{
		String ArtifactState = null;
		
		if(this.InstanceOfCoordination.getSharedArtifactInstance(ArtifactName) != null)
		{
			
		ArtifactState = this.InstanceOfCoordination.getSharedArtifactInstance(ArtifactName).getCurrentState();
		
		}
		
		return ArtifactState;
		

	}
	
	public String getCoordinationId()
	{
		
		return this.InstanceOfCoordination.getCoordinationId();
		
	}
	
	

}
