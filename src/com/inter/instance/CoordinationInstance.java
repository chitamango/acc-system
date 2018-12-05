package com.inter.instance;
/*
 *  @version 1.1
 */

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.logging.Logger;

import com.inter.log.CoordinationLog;
import com.inter.process.Coordination;

public class CoordinationInstance extends Thread {
	
	private String coordinationName;
	private String coordinatinId;
	private ArrayList<RoleInstance> listOfRoleInstance;
	private ArrayList<SharedArtifactInstance> listOfSharedArtifactInstance;
	private ArrayList<SharedRuleInstance> listOfSharedRuleInstance;
	private static Logger log = Logger.getLogger(CoordinationInstance.class.getName());
	private Boolean coordinationStatusRunning;
	private CoordinationLog CoordinationLog;
	
	
	
	public CoordinationInstance(Coordination xmlBindingCoordination)
	{
		coordinationName = xmlBindingCoordination.getName();
		listOfRoleInstance = new ArrayList<RoleInstance>();
		listOfSharedArtifactInstance = new ArrayList<SharedArtifactInstance>();
		listOfSharedRuleInstance = new ArrayList<SharedRuleInstance>();
		this.coordinationStatusRunning = false;
		
	}
	
	public String getCoordinationName()
	{
		
		return this.coordinationName; 
			
	}
	
	public void addSharedArtifactInstance(SharedArtifactInstance SharedArtifact)
	{
		//should perform duplicate artifact checking 
		this.listOfSharedArtifactInstance.add(SharedArtifact);
			
	}
	
	
	public void addRoleInstance(RoleInstance Role)
	{
		
		this.listOfRoleInstance.add(Role);
		
	}
	
	public void addSharedRuleInstance(SharedRuleInstance SharedRule)
	{
		
		this.listOfSharedRuleInstance.add(SharedRule);
	}
	

	public String getCoordinationId()
	{
		
		
		return this.coordinatinId;
		
	}
	
	public void setCoordinationId(String Id)
	{
		
		this.coordinatinId = Id;
		
		
	}


	@Override
	// have to think about this as well
	public void run() {
		// TODO Auto-generated method stub
		Boolean AllArtifactInFinalState = false;
		
		log.info("Process is starting");
		
		this.coordinationStatusRunning = true;
		
	
		
		/*while(this.ProcessStatusRunning == true)
		{
			
			ListIterator<ArtifactInstance> ArtifactInstanceListIterator = this.artifactInstanceList.listIterator();
			
			while(ArtifactInstanceListIterator.hasNext())
			{
				ArtifactInstance CurrentArtifactInstance = ArtifactInstanceListIterator.next();
				
				String StateOfCurrentArtifact = CurrentArtifactInstance.getCurrentState();
				
				StateInstance StateInstanceOfCurrentArtifact = CurrentArtifactInstance.getStateInstance(StateOfCurrentArtifact);
				
				if(StateInstanceOfCurrentArtifact.getStateType() != null)
				{	
					if(StateInstanceOfCurrentArtifact.getStateType().equalsIgnoreCase("end"))
					{
						AllArtifactInFinalState = true;
						
					}
					
				}
				else
				{
					
					
					AllArtifactInFinalState = false;
					
				}
			}
			
			
			
			if(AllArtifactInFinalState == true)
			{
				this.ProcessStatusRunning = false;
				
			}
			
				
		}
	 
	
		log.info("Process is completed!!!!");	
		
		*/
		
	}
	
	
	//should get artifact form id
	public SharedArtifactInstance getSharedArtifactInstance(String SharedArtifactName)
	{
		
		SharedArtifactInstance returnSharedArtifact = null;
		
		ListIterator<SharedArtifactInstance> itr = this.listOfSharedArtifactInstance.listIterator();
		while(itr.hasNext())
		{
			SharedArtifactInstance SharedArtifact = itr.next();
			String Name = SharedArtifact.getSharedArtifactName(); // here
			if(SharedArtifactName.equalsIgnoreCase(Name))
			{
				
				returnSharedArtifact = SharedArtifact;
				break;
			}
					
			
		}
		
		return returnSharedArtifact;
		
		
		
		
	}
	
	public SharedRuleInstance getRuleInstance(String SharedRuleName)
	{
		
		SharedRuleInstance returnSharedRule = null;
		
		ListIterator<SharedRuleInstance> itr = this.listOfSharedRuleInstance.listIterator();
		while(itr.hasNext())
		{
			SharedRuleInstance SharedRule = itr.next();
			String Name = SharedRule.getRuleName();
			if(SharedRuleName.equalsIgnoreCase(Name))
			{
				
				returnSharedRule = SharedRule;
				break;
			}
					
			
		}
		
		return returnSharedRule;
		
		
		
		
	}
	
	
	public ArrayList<SharedArtifactInstance> getSharedArtifactInstanceList()
	{
		
		return this.listOfSharedArtifactInstance;
		
		
	}
	
	public ArrayList<SharedRuleInstance> getSharedRuleInstanceList()
	{
		
		return this.listOfSharedRuleInstance;
	}
	
	
	public ArrayList<RoleInstance> getRoleInstanceList()
	{
		
		return this.listOfRoleInstance;
		
	}
	
	public RoleInstance getRoleInstance(String RoleName, String ServiceName, String OperationName)
	{
		RoleInstance returnRole = null;
		
		ListIterator<RoleInstance> itr = this.listOfRoleInstance.listIterator();
		while(itr.hasNext())
		{
			RoleInstance Role = itr.next();
			String RName = Role.getRole();
			String SName = Role.getService();
			String OName = Role.getOperation();
			if(RoleName.equalsIgnoreCase(RName) && ServiceName.equalsIgnoreCase(SName) && OperationName.equalsIgnoreCase(OName))
			{
				
				returnRole = Role;
				break;
			}
					
			
		}	
		
		return returnRole;
	}
	
	public void setCoordinationLog(CoordinationLog coordinationlog)
	{
		
		this.CoordinationLog = coordinationlog;
		
		
	}
	
	public CoordinationLog getCoordinationLog()
	{
		
		return this.CoordinationLog;
		
	}
	
	
	
	
	
	
	
	
}
