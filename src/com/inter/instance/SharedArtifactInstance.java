package com.inter.instance;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import com.inter.process.AttributesType.Attribute;
import com.inter.process.SharedArtifactType;
import com.inter.process.SharedStatesType.State;

public class SharedArtifactInstance {

	private String SharedArtifactId; 
	private String SharedArtifactName;
	private ArrayList<SharedAttributeInstance> listOfSharedAttribute;
	private ArrayList<SharedStateInstance> listOfSharedState;
	private String CurrentState;
	
	public SharedArtifactInstance(SharedArtifactType xmlBingindSharedArtifact)
	{
		
		this.SharedArtifactName = xmlBingindSharedArtifact.getName();
		this.listOfSharedAttribute = new ArrayList<SharedAttributeInstance>();
		this.listOfSharedState = new ArrayList<SharedStateInstance>();	
		this.extractXmlBindingSharedArtifactData(xmlBingindSharedArtifact);
		this.setInitialstate();
		
		
		
	}
	
	/**
	 * 
	 * this private method to populate data from xml binding artifact class to artifact instance class
	 * 
	 * 
	 * @param XmlBindingArtifact
	 */
	
	private void extractXmlBindingSharedArtifactData(SharedArtifactType XmlBindingSharedArtifact)
	{
		
		List<Attribute> bindingAttList = XmlBindingSharedArtifact.getAttributes().getAttribute();
		List<State> bindingStateList= XmlBindingSharedArtifact.getSharedStates().getState();
		
		//extract attribute data
		ListIterator<Attribute> AttItr = bindingAttList.listIterator();
		
		while(AttItr.hasNext())
		{
			Attribute currentAttr = AttItr.next();
			String attType = currentAttr.getType();
			String attStructure = currentAttr.getStructure();
			String attName = currentAttr.getName();
			String attUniqueId = currentAttr.getUniqueId();
			SharedAttributeInstance newAttr = new SharedAttributeInstance(attName,attType,attStructure,attUniqueId);
			this.listOfSharedAttribute.add(newAttr);
				
			
		}
		
		//extract state
		
		ListIterator<State> StateItr = bindingStateList.listIterator();
		
		while(StateItr.hasNext())
		{
			State previousState = StateItr.next();
			
			String stateType = previousState.getType();
			String stateName = previousState.getName();
			
			SharedStateInstance newState = new SharedStateInstance(stateName, stateType);
			
			this.listOfSharedState.add(newState);
			
							
		}	
		
	}
	
	/**
	 * 
	 * 
	 *  The method to set initial state of artifact
	 * 
	 * 
	 * 
	 */
	private void setInitialstate()
	{
		ListIterator<SharedStateInstance> itr = this.listOfSharedState.listIterator();
		while(itr.hasNext())
		{
			SharedStateInstance InspectedState = itr.next();
			String StateType = InspectedState.getStateType();
			
			if(StateType.equalsIgnoreCase("init"));
			{
				this.CurrentState = InspectedState.getStateName();
				break;
			}
			
			
			
			
		}
		
		
		
	}
	
	public void setCurrentState(String StateName)
	{
		
		
		this.CurrentState = StateName;
		
		
	}
	
	public String getCurrentState()
	{
		
		
		return this.CurrentState;
		
	}
	
	
	public ArrayList<SharedAttributeInstance> getAttributeList()
	{
		
		
		return this.listOfSharedAttribute;
		
	}
	
	public ArrayList<SharedStateInstance> getStateList()
	{
		
		
		
		return this.listOfSharedState;
		
	}
	
	public String getSharedArtifactId()
	{
		return this.SharedArtifactId;
		
	}
	
	
	public void setSharedArtifactId(String Id)
	{
		
		this.SharedArtifactId = Id;
		
	}
	
	public String getSharedArtifactName()
	{
		
		
		return this.SharedArtifactName;
		
	}
	
	public SharedAttributeInstance getSharedAttributeInstance(String AttributeName)
	{
		SharedAttributeInstance returnSharedAttribute = null;
		
		ListIterator<SharedAttributeInstance> itr = this.listOfSharedAttribute.listIterator();
		while(itr.hasNext())
		{
			SharedAttributeInstance attribute = itr.next();
			String Name = attribute.getAttributeName();
			if(AttributeName.equalsIgnoreCase(Name))
			{
				
				returnSharedAttribute = attribute;
				break;
			}
					
			
		}
		
		return returnSharedAttribute;
	}
	
	public SharedStateInstance getStateInstance(String StateName)
	{
		SharedStateInstance returnSharedState = null;
		
		ListIterator<SharedStateInstance> itr = this.listOfSharedState.listIterator();
		while(itr.hasNext())
		{
			SharedStateInstance state = itr.next();
			String Name = state.getStateName();
			if(StateName.equalsIgnoreCase(Name))
			{
				
				returnSharedState  = state;
				break;
			}
					
			
		}
		
		return returnSharedState;
		
	
		
		
	}
	
	
	
	
}
