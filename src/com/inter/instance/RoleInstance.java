package com.inter.instance;
/*
 *  @version 1.1
 */

import java.util.Iterator;
import java.util.List;

import org.apache.axiom.om.OMElement;

import com.inter.process.RoleType;
import com.inter.process.ServiceType;

public class RoleInstance {
	
	private String roleName;
	private String roleInstanceId;
	private String roleLocation;
	private String rolePort;
	private String nameSpace;
	private String serviceName; 
	private String operation; 
	private String inputMessageName; 
	private String outputMessageName; 
	private String port; 
	private String location; 
	private OMElement NotifcationMessage;
	private OMElement CoordinationMessage;
	
	public RoleInstance(RoleType xmlBindingRole, String Service, String Operation)
	{
		this.roleName = xmlBindingRole.getName();
		this.nameSpace = xmlBindingRole.getNamespace();
		this.roleLocation = xmlBindingRole.getRoleLocation();
		this.rolePort = xmlBindingRole.getRolePort();
		this.setServiceAndOperation(xmlBindingRole.getService(), Service, Operation);
		
	
	}
	
	private void setServiceAndOperation(List<ServiceType> listOfService ,String Service, String Operation)
	{
		ServiceType xmlBindingService = null;
		Iterator<ServiceType> Sitr =   listOfService.iterator();
		
		while(Sitr.hasNext())
		{
			ServiceType InspectedService = Sitr.next();
			String InspectedServiceName = InspectedService.getName();
			String InspectedServiceOperation = InspectedService.getOperation();
			
			if(InspectedServiceName.equalsIgnoreCase(Service) && InspectedServiceOperation.equalsIgnoreCase(Operation) )
			{
				Integer removeIndex = listOfService.indexOf(InspectedService);
				
				xmlBindingService = InspectedService;
				
				listOfService.remove(removeIndex);
				
				
				break;
				
			}
			
		
		}
		
		this.serviceName = xmlBindingService.getName();
		this.operation = xmlBindingService.getOperation();
		this.location = xmlBindingService.getLocation();
		this.port = xmlBindingService.getPort();
		this.inputMessageName =xmlBindingService.getInputmessage();
		this.outputMessageName =xmlBindingService.getOutputmessage();
		
		
		
	}
	
	
	public String getWsdlLocation()
	{
		
		
		return this.location;
		
	}
	
	public String getInputMessageName()
	{
		
		
		return this.inputMessageName;
		
	}
	
	public String getOutoutMessageName()
	{
		
		return this.outputMessageName;
		
		
	}
	
	
	public String getOperation()
	{
		
		
		return this.operation;
	}
	
	public String getNamespace()
	{
		
		return this.nameSpace;
		
	}
	
	public String getPort()
	{
		return this.port;
		
	}
	
	public String getService()
	{
		return this.serviceName;
		
		
	}
	
	public OMElement getCoordinationMessage()
	{
		
		return this.CoordinationMessage;
	}
	
	public void setCoordinationMessage(OMElement coordinationmessage )
	{
		
		this.CoordinationMessage = coordinationmessage;
		
	}
	
	public OMElement getNotificationMessage()
	{
		
		return this.NotifcationMessage;
	}
	
	public void setNotificationMessage(OMElement notificationmessage)
	{
		
		this.NotifcationMessage = notificationmessage;
		
	}
	
	public String getRoleID()
	{
		return this.roleInstanceId;
		
	}
	
	public void setRoleID(String IdOfRole)
	{
		
		this.roleInstanceId = IdOfRole;
		
	}
	
	public String getRole()
	{
		
		return this.roleName;
	}
	
	public String getRoleLocation()
	{
		
		return this.roleLocation;
		
	}
	
	public String getRolePort()
	{
		return this.rolePort;
		
	}
	
	
	

}
