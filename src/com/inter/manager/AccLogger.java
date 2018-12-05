package com.inter.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.inter.instance.CoordinationInstance;
import com.inter.instance.SharedArtifactInstance;
import com.inter.instance.SharedAttributeInstance;
import com.inter.log.CoordinationLog;
import com.inter.log.LogRecord;


/*

     this class is to create log file

*/

public class AccLogger {

	DocumentBuilderFactory domFactory; 
    DocumentBuilder docBuilder; 
    Document doc;
    
    
    public AccLogger()
    {
    	
    	
	    try {
	    	
	    	domFactory = DocumentBuilderFactory.newInstance();
	    	
		  //  domFactory.setNamespaceAware(true); // never forget this
		    
		    docBuilder = domFactory.newDocumentBuilder();
		    doc = docBuilder.newDocument();
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    public void createProcessLog(CoordinationInstance InstanceOfCoordination)
    {
    	
    	CoordinationLog LogOfProcess = new CoordinationLog(InstanceOfCoordination.getCoordinationId());
    		
    	InstanceOfCoordination.setCoordinationLog(LogOfProcess);
    	
    
    	
    	
    }
    
    public void createProcessLogRecord(CoordinationLog LogOfCoordination,ArrayList<SharedArtifactInstance> ListOfSharedArtifactInstance,String RuleId, String RoleId, String Timestamp)
    {
    	Integer currentLogRecordNumber = LogOfCoordination.getCurrentLogRecordNumber();
    
    	Integer NextLogRecordNumber = currentLogRecordNumber+1;
    	
    	LogRecord Record = new LogRecord(NextLogRecordNumber);
    	
    	Record.setTimestamp(Timestamp);
    	
    	Record.setRuleId(RuleId);
    	
    	Record.setRoleId(RoleId);
    	
    	ListIterator<SharedArtifactInstance> ListOfArtifactInstanceIterator =ListOfSharedArtifactInstance.listIterator();
    	
    	while(ListOfArtifactInstanceIterator.hasNext())
    	{
    		SharedArtifactInstance CurrentArtifact = ListOfArtifactInstanceIterator.next();
    		
    		Record.setPreSharedArtifacts(this.transformToDOM(CurrentArtifact));
    			
    		
    	}
    	
    	LogOfCoordination.addLogRecord(Record);
    	
    	
    	
    	this.writeLogfile(LogOfCoordination);
    	
    	
    }
    
    public void UpdateProcessLogRecord(CoordinationLog coordinationLog,ArrayList<SharedArtifactInstance> ListOfArtifactInstance )
    {
    	Integer CurrentProcessLogRecordNumber = coordinationLog.getCurrentLogRecordNumber();
    	
    	LogRecord Record = coordinationLog.getLogRecord(CurrentProcessLogRecordNumber); 
    	
    	ListIterator<SharedArtifactInstance> ListOfArtifactInstanceIterator =ListOfArtifactInstance.listIterator();
    	
    	while(ListOfArtifactInstanceIterator.hasNext())
    	{
    		SharedArtifactInstance CurrentArtifact = ListOfArtifactInstanceIterator.next();
    		
    		Record.setPostSharedtArtifacts(this.transformToDOM(CurrentArtifact));
    			
    		
    	}
    	
    	
    	
    	
    	this.writeLogfile(coordinationLog);
    	
    }
    
    private void writeLogfile(CoordinationLog coordinationLog)
    {
    	
    	try {
    	//why relative path doesn't work
 
    	//this part is location of deployed xml file
    	File logfolder= new File("C:/Users/KanPhd/Dropbox/workplace2/Inter-organization/log");//have to change this as well
    	
    	File logFile = new File("C:/Users/KanPhd/Dropbox/workplace2/Inter-organization/log/"+coordinationLog.getCoordinationId()+".xml");//have to change this as well
    	    
//    	File processfolder= new File("deployed/process/");//have to change this as well
//    	File deployedfolder = new File("deployed/");//have to change this as well
//    	File processFile = new File("deployed/process/"+InputFile.getName());//have to change this as well
    	
    	
    	    
    	    
    	    if(logfolder.exists()== false)
    	    {
    	
    	    	logfolder.mkdir();
    	    		
    	    }
    	  
    	
    	//save xml file 
	    //create a transformer factory
	    TransformerFactory factory = TransformerFactory.newInstance();
	    //create a new transformer
	    Transformer transformer = factory.newTransformer();
		//format OutPut
	    
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    
	    //create a stream result
	    Result result = new StreamResult(logFile);
	    //create new dom source
	    Source source = new DOMSource(coordinationLog.getCompleteLog());
	    //transform and save to xml file
	    
		transformer.transform(source, result);
		
		
			
			
    	}catch (TransformerConfigurationException e) {
			
    		// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			
		catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
	    //save a process file to process folder
	  
    	
    	
    	
    }
    
    private Element transformToDOM(SharedArtifactInstance InstanceOfSharedArtifact)
    {
    	
    	
    	   	
    	Element XmlArtifact = doc.createElement(InstanceOfSharedArtifact.getSharedArtifactName());
    	
    	XmlArtifact.setAttribute("id", InstanceOfSharedArtifact.getSharedArtifactId());
    	
    	XmlArtifact.setAttribute("state", InstanceOfSharedArtifact.getCurrentState());
		
    	ArrayList<SharedAttributeInstance> listOfAtt = InstanceOfSharedArtifact.getAttributeList();
		
		ListIterator<SharedAttributeInstance> ListOfAttIterator = listOfAtt.listIterator();
		
		
		while(ListOfAttIterator.hasNext())
		{
			SharedAttributeInstance currentAtt = ListOfAttIterator.next();
			String attName = currentAtt.getAttributeName();
			Element XmlAttribute = doc.createElement(attName);

			if(currentAtt.isEmpty())
			{
				XmlAttribute.setTextContent("null");
				
			}
			else
			{
				try {
					
					XmlAttribute.setTextContent(currentAtt.get(0).toString());
					
				} catch (DOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
				
			XmlArtifact.appendChild(XmlAttribute);
		}
		
    
		
    	return XmlArtifact;
    	
    }
	
	
}
