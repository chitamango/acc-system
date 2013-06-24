package com.inter.event;

import java.util.EventObject;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;


import com.inter.instance.CoordinationInstance;
import com.inter.instance.SharedArtifactInstance;
import com.inter.instance.SharedAttributeInstance;

public class CombineEvent extends EventObject {
	
	protected OMElement MessageElement;
	protected CoordinationInstance InstanceOfCoordination;
	
	

	public CombineEvent(Object Source, OMElement Message, CoordinationInstance coordination) {
		super(Source);
		// TODO Auto-generated constructor stub
		
		this.MessageElement = Message;
		this.InstanceOfCoordination = coordination;
		
		
		
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
						
						value = TargetAttribute.get(0).toString();
						
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
	
	public String getValueFromPart(String PartName)
	{
		String PartValue = null;
		
		OMElement Part = this.MessageElement.getFirstChildWithName(new QName(this.MessageElement.getNamespace().getNamespaceURI(),PartName));
		
		if(Part != null)
		{	
			PartValue = Part.getText();
		}
		
		
		
		return PartValue;
	}
	
	public OMElement getMessage()
	{
	
		return this.MessageElement;
		
	}
	
	public CoordinationInstance getCoordinationInstance()
	{
		
		
		
		return this.InstanceOfCoordination;
		
	}
	
	public String getCoordinationId()
	{
		
		return this.InstanceOfCoordination.getCoordinationId();
		
	}

	
	
}
