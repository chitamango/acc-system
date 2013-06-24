package com.inter.manager;

import java.util.ArrayList;
import java.util.ListIterator;
import org.apache.axiom.om.OMElement;
import com.inter.instance.CoordinationInstance;
import com.inter.instance.SharedArtifactInstance;
import com.inter.instance.SharedRuleInstance;
import com.inter.instance.SharedStateInstance;
import com.inter.instance.TransitionRule;

public class SharedArtifactDataManager {
	
	private Mapper mapper;
	
	public SharedArtifactDataManager(Mapper DataMapper)
	{
		
		mapper = DataMapper;
		
	}
	
	public void updateSharedArtifact(ArrayList<SharedArtifactInstance> Artifacts,OMElement NotificationMessage ,CoordinationInstance Coordination ,SharedRuleInstance SharedRule )
	{
		ArrayList<SharedArtifactInstance> ListofSharedArtifactInCoordinationInstance = Coordination.getSharedArtifactInstanceList();
		
		ListIterator<SharedArtifactInstance> itr = Artifacts.listIterator();
		
		Integer NoOfNotificationRule = SharedRule.getNotificationRules().size();
		
		
		//if there is no need for notification rule, then do only state transition
		if(NoOfNotificationRule > 0)
		{
			while(itr.hasNext())
			{
				SharedArtifactInstance CurrentSharedArtifact = itr.next();
				
				
				if(ListofSharedArtifactInCoordinationInstance.contains(CurrentSharedArtifact))
				{
				
				//this call mapping method, pass artifact and request, message
				
					try {
						
						this.mapper.mapppingNotificationMessageToSharedArtifact(SharedRule, CurrentSharedArtifact, NotificationMessage);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				
				
				
				try {
					
					
				
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		
		}
		
		//next is to do a state transition
		this.updateSharedArtifactState(SharedRule.getTransitionRules(), Artifacts);
		
	
		
		
	}
	
	
	// to end the process change artifact state only no service invoked
	public void updateSharedArtifact(ArrayList<SharedArtifactInstance> Artifacts,CoordinationInstance Coordination ,SharedRuleInstance SharedRule )
	{
		
		ArrayList<SharedArtifactInstance> ListofSharedArtifactInCoordinationInstance = Coordination.getSharedArtifactInstanceList();
		
		if(ListofSharedArtifactInCoordinationInstance.containsAll(Artifacts))
		{
			
			this.updateSharedArtifactState(SharedRule.getTransitionRules(), Artifacts);
			
		}
		
		
		
		
		
	}
	
	
	
	
	public void AddSharedArtifactInstance(SharedArtifactInstance SharedArtifact, CoordinationInstance Coordination)
	{
		//the way to give artifact id should be revised
		//Artifact.setArtifactId(Process.getProcessId()+ Artifact.getArtifactName()+DateFormat.getDateTimeInstance().format(new Date()));
		
		Coordination.addSharedArtifactInstance(SharedArtifact);
		
		
		
	}
	
	
	private void updateSharedArtifactState(ArrayList<TransitionRule> transitionRules, ArrayList<SharedArtifactInstance> SharedArtifacts)
	{ 
		
		ListIterator<TransitionRule>  TrasitionIterator = transitionRules.listIterator();
		
		//System.out.println("size of transition Rule:" + transitionRules.size());
		
		while(TrasitionIterator.hasNext())
		{
			TransitionRule currentTransitionRule = TrasitionIterator.next();
			String targetArtifact = currentTransitionRule.getArtifact();
			String fromState = currentTransitionRule.getFromState();
			String toState = currentTransitionRule.getToState();
			
			//System.out.println(targetArtifact);
			//System.out.println(fromState);
			//System.out.println(toState);
			
			ListIterator<SharedArtifactInstance> SharedArtifactIterator = SharedArtifacts.listIterator();
			
			while(SharedArtifactIterator.hasNext())
			{
				
				SharedArtifactInstance CurrentSharedArtifact = SharedArtifactIterator.next();
				String SharedArtifactName = CurrentSharedArtifact.getSharedArtifactName();
			//	System.out.println(ArtifactName);
				String CurrentSharedArtifactState = CurrentSharedArtifact.getCurrentState();
				ArrayList<SharedStateInstance> SharedStateList = CurrentSharedArtifact.getStateList();
				
			 // to be continue state transiton
				if(SharedArtifactName.equalsIgnoreCase(targetArtifact) && CurrentSharedArtifactState.equalsIgnoreCase(fromState) ) //&& this.checkState(StateList, fromState, toState)
				{
					
					CurrentSharedArtifact.setCurrentState(toState);
					//System.out.println(CurrentArtifact.getArtifactName()+":"+CurrentArtifact.getCurrentState());
					
				}
				
				
				
			}
			
		
		
		
		}

	}
	
	private Boolean checkState(ArrayList<SharedStateInstance> StateList,String FromState,String ToState )
	{
		ListIterator<SharedStateInstance> itr = StateList.listIterator();
		Boolean FromStateValid = false;
		Boolean ToStateValid = false;
		Boolean valid = false;
		
		while(itr.hasNext())
		{
			SharedStateInstance CurrentInspectedstate = itr.next();
			String StateName = CurrentInspectedstate.getStateName();
			
			if(StateName.equalsIgnoreCase(FromState))
			{
				
				FromStateValid = true;
				
			}
			
			if(StateName.equalsIgnoreCase(ToState))
			{
				
				ToStateValid = true;
				
			}
			
			
			
		}
		
		if(FromStateValid && ToStateValid )
		{
			
			valid = true;
			
			
		}
		
		
		
		
		return valid;
		
	}
}
