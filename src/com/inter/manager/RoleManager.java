package com.inter.manager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;


import javax.wsdl.Definition;
import javax.wsdl.Message;
import javax.wsdl.Part;
import javax.wsdl.Types;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.description.WSDL11ToAxisServiceBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;






import com.inter.instance.CoordinationInstance;
import com.inter.instance.RoleInstance;
import com.inter.instance.SharedArtifactInstance;
import com.inter.instance.SharedRuleInstance;



public class RoleManager {
	
	
	private Mapper mapper;
	private ArrayList<ServiceClient> ListOfServiceClient = new ArrayList<ServiceClient>();
	
	
	public RoleManager(Mapper DataMapper)
	{
		
		this.mapper = DataMapper;
		
	}

	public void invokeParticipatingRole(ArrayList<SharedArtifactInstance> Artifacts,OMElement RequestMessage,String RoleName,String ServiceName,String OperationName, SharedRuleInstance Rule, CoordinationInstance Coordination) throws MalformedURLException, AxisFault, InterruptedException, WSDLException
	{
		//declare some parameter
		ServiceClient client;
		RoleInstance InvokedRole;
		Axis2callback callback = new Axis2callback();
		OMElement payload = null;
		OMElement SOAPCoordinationMessage = null;
		OMElement SOAPNotificationtMessage = null;
		QName ServiceQname;
		QName OperationQname;
		URL WsdlLocation;
		String Port;
		String clientManagerServiceName ="SharedArtifactClientManagerService";
		String clientManagerOperation =  "invokePartnerRole";
		
		//assign parameter
		
		
		
		ArrayList<SharedArtifactInstance> ListofArtifactInProcessInstance = Coordination.getSharedArtifactInstanceList();
		ListIterator<SharedArtifactInstance> itr = Artifacts.listIterator();
		InvokedRole = Coordination.getRoleInstance(RoleName, ServiceName, OperationName);
		WsdlLocation = new URL(InvokedRole.getRoleLocation()); // client manager wsdl location
		ServiceQname = new QName("http://webservice.acp.com",clientManagerServiceName); //clientmanager service
		Port = InvokedRole.getRolePort();//client manager port
		OperationQname = new QName(InvokedRole.getNamespace(),clientManagerOperation); //clientmanager operation name
		
		
		
		
		//this should be an interface of clientManager
		//create dynamic client 
	//	System.out.println("creating Service client for " +InvokedRole.getRole()+":"+InvokedRole.getService() +":"+ InvokedRole.getOperation());
	
	if(this.checkServiceClient(clientManagerServiceName) == null)
	{	
		
		client = new ServiceClient(null, WsdlLocation,ServiceQname,Port); //wsdl of clientManager, ServiceName of clientmanager, port of clientmanager 
		this.ListOfServiceClient.add(client);
		
		System.out.println("Success createServiceClient");
	}
	else
	{
		
		client = this.checkServiceClient(clientManagerServiceName);
		
	}
		try {
						//perform payload mapping
			//will separate creation of message into two parts: payload and coordination message
			payload = this.createPayload(InvokedRole);
			
		//	System.out.println(payload);
			
			//mapping data here
			while(itr.hasNext())
			{
				SharedArtifactInstance CurrentArtifact = itr.next();
				
				//why i have to do this?? should check id??
				if(ListofArtifactInProcessInstance.contains(CurrentArtifact))
				{
						//this is way i have to fix it
					try {
						
						this.mapper.mapppingArtifactToPayload(Rule, CurrentArtifact, payload);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
					//need some condition
					
				
				}		
				
			}
			
		
			
			if(RequestMessage != null)	
			{
				//System.out.println(RequestMessage);
					try {
						
						this.mapper.mappingRequestMessageToPayload(Rule, RequestMessage, payload);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
			}
		
		
			
			
			System.out.println("Success createPayload");
		
			
		} catch (WSDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	//create coordination message	
		
	
		
		SOAPCoordinationMessage = this.createCoordinationMessage(Coordination.getCoordinationId(),RoleName, Rule.getRuleId(), payload);
		
		System.out.println(SOAPCoordinationMessage);
		
		System.out.println("Success createCoordinationMessage");
		
		
		InvokedRole.setCoordinationMessage(SOAPCoordinationMessage);
		
	//perform service invocation	
		
    //   client.sendReceiveNonBlocking(OperationQname,SOAPCoordinationMessage, callback);
		
		SOAPNotificationtMessage = client.sendReceive(OperationQname, SOAPCoordinationMessage);
       
	//	SOAPNotificationtMessage = client.sendReceive(SOAPCoordinationMessage);
		
      //some hacking for asynchronous service invocation
   //    while (callback.getResponseMessage() == null) {
           
  // 		Thread.sleep(1000);
   	
   //   }
       
    
   //    SOAPNotificationtMessage = callback.getResponseMessage();
       
      System.out.println(SOAPNotificationtMessage);
       
       //this to prevent Axis2 timeout error
       client.cleanupTransport();
       
    //   client.cleanup();
       
     //add output to service instance as well 
       InvokedRole.setNotificationMessage(SOAPNotificationtMessage);
		
		
		
		
		
	}
	
	
	private OMElement createPayload(RoleInstance Role) throws  FactoryConfigurationError, WSDLException
	{
		
    	OMFactory fac = OMAbstractFactory.getOMFactory();
    	OMNamespace omNs = fac.createOMNamespace(Role.getNamespace(), "");
    	WSDLReader reader = WSDLFactory.newInstance().newWSDLReader();
    	OMElement PayLoad = null;
    	Node targetNode = null;
    
    	
    	// read wsdl
    	Definition wsdldefinition = reader.readWSDL(Role.getWsdlLocation());
    	
    	//System.out.println(Role.getWsdlLocation());
    	
    	//get types element
    	Types typedefinition = wsdldefinition.getTypes();
    	
    	 QName WSDLMessageName = new QName(Role.getNamespace(),Role.getInputMessageName()); //message name  here
    	//get message element with a particular name
    	Message MessageDefinition = wsdldefinition.getMessage(WSDLMessageName);
    	
    	// get key to access each part in a wsdl message and use to loop
    	 Map<String, Part> parts =  MessageDefinition.getParts();
    	 
    	 Set<String> Keyset = parts.keySet();
    	 
    	 Iterator<String> itr = Keyset.iterator();
    	 
    	 while(itr.hasNext())
    	 {
    		 String keyname = itr.next();
    		 
    		 Part part = parts.get(keyname);
    		 
    		String PartName = part.getName(); //this will be used in the future
    		
    		QName PartType = part.getTypeName();// this will be used in the future
    		
    		//use this determine the complex type of xml schema
    		QName PartElement = part.getElementName();
    		
    		//System.out.println(PartElement);
    				
        	//use this method to access schema in wdsl4j
        	for( Object o : typedefinition.getExtensibilityElements()) {
        	    if( o instanceof javax.wsdl.extensions.schema.Schema ) 
        	    {
        	        Element elt = ((javax.wsdl.extensions.schema.Schema) o).getElement();
     
        	        	NodeList ListOfNode = elt.getChildNodes();
        	        	
        	        	for(int i = 0;i < ListOfNode.getLength(); i++ )
        	        	{
        	        		Node CurrentNode = ListOfNode.item(i);
        	        		//some hacking to overcome strange text element
        	        		if(CurrentNode.hasChildNodes())
        	        		{
        	        			if(CurrentNode.getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase(PartElement.getLocalPart()))
        	        			{
        	        				
        	        				
        	        				targetNode = CurrentNode;
        	        				
        	        				
        	        			}
	        			
        	        		}
		
        	        		
        	        	}
        	       
        	        
        	    }
        	
    	
        	}
        	
    	 }
    	 
    	//the message should be generated in the manner that restricted by wsdl
        
    	OMElement Method = fac.createOMElement(targetNode.getAttributes().getNamedItem("name").getNodeValue(),omNs );
    	
    	NodeList ListOfchild= targetNode.getChildNodes();
 	 	
    	for(int i = 0; i < ListOfchild.getLength(); i++)
    	{
    		//hacking to remove #text
    		if(ListOfchild.item(i).hasChildNodes())
    		{	
    			NodeList ComplexType = ListOfchild.item(i).getChildNodes();
    							
    			for(int I = 0; I < ComplexType.getLength(); I++)
    	    	{	
    				//hacking to remove #text
    				if(ComplexType.item(I).hasChildNodes())
    				{
    					NodeList ListOfParameter =  ComplexType.item(I).getChildNodes();
    						
    					for(int l = 0; l < ListOfParameter.getLength(); l++ )
    					{
    						if(ListOfParameter.item(l).hasAttributes())
    	    				{
    							String ParameterName =  ListOfParameter.item(l).getAttributes().getNamedItem("name").getNodeValue();
    							OMElement Parameter = fac.createOMElement(ParameterName,omNs );   
    							Method.addChild(Parameter);
    	    				}
    					}
    					
  	
    				}
    	    	}	
    		}
    		
    		
    	}
    	 
    	PayLoad = Method;
    	
    
    	 
    	return PayLoad;
	        
	       
		
	}
	
	//Need to think about method element
	private OMElement createCoordinationMessage(String CoordinationId,String role, String RuleId, OMElement payload)
	{
		
		OMFactory fac = OMAbstractFactory.getOMFactory();
		//create the elements
		OMNamespace omNs = fac.createOMNamespace(payload.getNamespace().getNamespaceURI(), "");
		OMElement coordinationMessageElement = fac.createOMElement("coordinationMessage", omNs);
		OMElement coordinationIdElement = fac.createOMElement("coordinateId", omNs);
		OMElement RoleElement = fac.createOMElement("role", omNs);
		OMElement RuleIdElement = fac.createOMElement("ruleId", omNs);   //should have rolename as well
		OMElement PayloadElement = fac.createOMElement("messagePayload", omNs);
		
		//assign value
		coordinationIdElement.setText(CoordinationId);
		RoleElement.setText(role);
		RuleIdElement.setText(RuleId);
		PayloadElement.addChild(payload);
		
		//build the complete coordinationmessage
		coordinationMessageElement.addChild(coordinationIdElement);
		coordinationMessageElement.addChild(RoleElement);
		coordinationMessageElement.addChild(RuleIdElement);
		coordinationMessageElement.addChild(PayloadElement);
		
		return coordinationMessageElement;
		
		
		
		
	}
	
	private ServiceClient checkServiceClient(String ServiceName)
	{
		ServiceClient ResultServiceClient = null;
		
		ListIterator<ServiceClient> ServiceClientListIterator = this.ListOfServiceClient.listIterator();
		
		while(ServiceClientListIterator.hasNext())
		{
			ServiceClient CurrentServiceClient = ServiceClientListIterator.next();
			
			
			
			String CurrentServiceName = CurrentServiceClient.getAxisService().getName();
			
		
			
			if(CurrentServiceName.equalsIgnoreCase(ServiceName))
			{
				ResultServiceClient =  CurrentServiceClient; 
				
			}
			
			
			
		}
		
		
		return ResultServiceClient;
		
	}
	
	
}
