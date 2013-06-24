package com.inter.factory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.inter.instance.CoordinationInstance;
import com.inter.instance.RoleInstance;
import com.inter.instance.SharedArtifactInstance;
import com.inter.instance.SharedRuleInstance;
import com.inter.process.Coordination;
import com.inter.process.RoleType;
import com.inter.process.SharedArtifactType;
import com.inter.process.SharedRuleType;





public class AccFactory {
	
	private JAXBContext jaxbContext;
	private  Unmarshaller unmarshaller;
	
	public AccFactory()
	{
		
		try {
			jaxbContext = JAXBContext.newInstance("com.inter.process");
			unmarshaller = jaxbContext.createUnmarshaller();
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			
	}
	
	/**
	 * 
	 * this method return a complete Process Instance
	 * 
	 * @param istrm
	 * @return
	 * @throws JAXBException 
	 */

	private Coordination  getXmlBindingCoordination(File CoordinationLocation) 
	{
		
	

			Coordination XmlBindingCoordination = null;
			
			try {
				
			
				
				if(XmlBindingCoordination == null)
				{
					
					Object obj = unmarshaller.unmarshal(CoordinationLocation) ;
			
					
					XmlBindingCoordination = (Coordination)obj;
							
										
				}
					
						
				
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return XmlBindingCoordination;	
								
	}
	/**
	 * 
	 * 
	 * get process file location using xml file
	 * 
	 * 
	 * @param ProcessName
	 * @return
	 * @throws Exception 
	 */
	
	private File getCoordinationLocation(String CoordinationName) throws Exception
	{
		
		File CoordinationLocation = null;
	    File registerProcessFile = new File("definition/deployedAcc.xml");
	   // 
	   // System.out.println(registerProcessFile.getAbsolutePath());
		
	//	File registerProcessFile = new File("deployed/deployedprocess.xml");
		if (registerProcessFile.exists()== false)
		{
			throw new Exception("Acc Factory Error: Please deploy process first");
		
			
		}
		
	    try {
	    	
	    	   	
	    	DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(true); // never forget this
		    DocumentBuilder Dbuilder = domFactory.newDocumentBuilder();
	    	Document doc = Dbuilder.parse(registerProcessFile);
	    	
	    	NodeList ProcessList = doc.getElementsByTagName("process");
	    	//System.out.println(ProcessList.getLength());
	    	
	    	for(int i=0; i<ProcessList.getLength();i++)
	    	{
	    		
	    		
	    		String registerProcessName = ProcessList.item(i).getFirstChild().getTextContent();
	    		
	    		
	    		//System.out.println(registerProcessName);
	    		
	    		if(CoordinationName.equalsIgnoreCase(registerProcessName) == true )
	    		{
	    			
	    			String Location = ProcessList.item(i).getLastChild().getTextContent();
	    			
	    			//System.out.println(Location);
	    			
	    			CoordinationLocation = new File(Location);
	    			
	    			break;
	    						
	    		}
	    		    			
	    	}
	    	
	    	
	    	   	
	    	
	    	
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return CoordinationLocation;
		
		
		
	}
	
	/**
	 * 
	 * 
	 * this method will return process instance include a list of rule,service and artifact
	 * @throws Exception 
	 * 
	 * 
	 * 
	 */
	
	public CoordinationInstance getCoordinationInstance(String CoordinationName) throws Exception
	{
		//get coordination instance
		File CoordinationLocation = getCoordinationLocation(CoordinationName);	
		//File CoordinationLocation = new File("C:/Users/KanPhd/Dropbox/workplace2/Inter-organization/definition/testAcc.xml");
		Coordination CompleteXmlBindingCoordination = getXmlBindingCoordination(CoordinationLocation);
		Coordination XmlBindingCoordination = null;
		CoordinationInstance returnCoodinationInstance = null;
		
		//break complete xmlbindingCoordination into piece
		
		
		CompleteXmlBindingCoordination.getRoles().getRole().clear();
		CompleteXmlBindingCoordination.getSharedArtifacts().getSharedArtifact().clear();
		CompleteXmlBindingCoordination.getSharedRules().getSharedRule().clear();
		
		XmlBindingCoordination = CompleteXmlBindingCoordination;
		
		returnCoodinationInstance = new CoordinationInstance(XmlBindingCoordination);
		
		//create new  instance here and populate with its data	
		
		return returnCoodinationInstance;
		
		
	}
	/**
	 * 
	 *  get artifact instance by processName and artifactName
	 * 
	 * @return
	 * @throws Exception 
	 */
	
	public SharedArtifactInstance getSharedArtifactInstance(String CoordinationName, String SharedArtifactName) throws Exception
	{
		
		//get coordination 
		File CoordinationLocation = getCoordinationLocation(CoordinationName);
		Coordination CompleteXmlBindingCoordination = getXmlBindingCoordination(CoordinationLocation);
		SharedArtifactType xmlbindingSharedArtifact = null;
		SharedArtifactInstance returnSharedArtifactInstance = null;
		
		List<SharedArtifactType> SharedArtifactList = CompleteXmlBindingCoordination.getSharedArtifacts().getSharedArtifact();
		
		Iterator<SharedArtifactType> Aitr =   SharedArtifactList.iterator();
		
		while(Aitr.hasNext())
		{
			SharedArtifactType InspectedSharedArtifact = Aitr.next();
			String InspectedSharedArtifactName = InspectedSharedArtifact.getName();
			
			if(InspectedSharedArtifactName.equalsIgnoreCase(SharedArtifactName))
			{
				Integer removeIndex = CompleteXmlBindingCoordination.getSharedArtifacts().getSharedArtifact().indexOf(InspectedSharedArtifact);
				
				xmlbindingSharedArtifact = InspectedSharedArtifact;
				
				CompleteXmlBindingCoordination.getSharedArtifacts().getSharedArtifact().remove(removeIndex);
				
				
				break;
				
			}
			
		
		}
		
		returnSharedArtifactInstance = new SharedArtifactInstance(xmlbindingSharedArtifact); 
		
		//create new  instance here and populate with its data	
		return returnSharedArtifactInstance;
				
	}
	
	
	public SharedRuleInstance getSharedRuleInstance(String CoordinationName, String RuleName) throws Exception
	{
		
		//get coordination 
		File CoordinationLocation = getCoordinationLocation(CoordinationName);
		Coordination CompleteXmlBindingCoordination = getXmlBindingCoordination(CoordinationLocation);
		SharedRuleType xmlBindingSharedRule= null;
		SharedRuleInstance returnSharedRuleInstance = null;
		
		List<SharedRuleType> SharedRuleList = CompleteXmlBindingCoordination.getSharedRules().getSharedRule();
		
		Iterator<SharedRuleType> Ritr =   SharedRuleList.iterator();
		
		while(Ritr.hasNext())
		{
			SharedRuleType InspectedSharedRule = Ritr.next();
			String InspectedSharedRuleName = InspectedSharedRule.getName();
			
			if(InspectedSharedRuleName.equalsIgnoreCase(RuleName))
			{
				Integer removeIndex = CompleteXmlBindingCoordination.getSharedRules().getSharedRule().indexOf(InspectedSharedRule);
				
				xmlBindingSharedRule = InspectedSharedRule;
				
				CompleteXmlBindingCoordination.getSharedRules().getSharedRule().remove(removeIndex);
				
				
				break;
				
			}
			
		
		}
		
		returnSharedRuleInstance = new SharedRuleInstance(xmlBindingSharedRule);
		
		//create new  instance here and populate with its data		
		return returnSharedRuleInstance;
				
	}
	
	public RoleInstance getRoleInstance(String CoordinationName,String RoleName, String ServiceName, String OperationName) throws Exception
	{
		
		//get process instance
		File CoordinationLocation = getCoordinationLocation(CoordinationName);
		Coordination CompleteXmlBindingCoordination = getXmlBindingCoordination(CoordinationLocation);
		RoleType xmlBindingRole = null;
		RoleInstance returnRoleInstance = null;
		
		List<RoleType> RoleList = CompleteXmlBindingCoordination.getRoles().getRole();
		
		Iterator<RoleType> Ritr =   RoleList.iterator();
		
		while(Ritr.hasNext())
		{
			RoleType InspectedRole = Ritr.next();
			String InspectedRoleName = InspectedRole.getName();
			
			if(InspectedRoleName.equalsIgnoreCase(RoleName))
			{
				Integer removeIndex = CompleteXmlBindingCoordination.getRoles().getRole().indexOf(InspectedRole);
				
				xmlBindingRole = InspectedRole;
				
				CompleteXmlBindingCoordination.getRoles().getRole().remove(removeIndex);
				
				
				break;
				
			}
			
		
		}
		
		returnRoleInstance = new RoleInstance(xmlBindingRole,ServiceName,OperationName);
		
		//create new  instance here and populate with its data		
		return returnRoleInstance;
				
	}
	
	
	
	
	
}
