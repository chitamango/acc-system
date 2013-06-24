package com.inter.log;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class LogRecord {

	private Integer RecordOrderNumber;
	private Element Timestamp;
	private Element RuleId;
	private Element RoleId;
	private Element pre_SharedArtifacts;
	private Element post_SharedArtifacts;
	private Element Record;
	private DocumentBuilderFactory domFactory;
	DocumentBuilder Dbuilder;
	Document doc;
	
	
	public LogRecord(Integer RecordOrderNo )
	{
		
		try {
			
			domFactory = DocumentBuilderFactory.newInstance();
		   // domFactory.setNamespaceAware(true); // never forget this
			Dbuilder = domFactory.newDocumentBuilder();
			doc = Dbuilder.newDocument();
		
			this.RecordOrderNumber = RecordOrderNo;
			this.Timestamp = doc.createElement("timestamp");
			this.RuleId = doc.createElement("ruleId");
			this.RoleId = doc.createElement("roleId");
			this.pre_SharedArtifacts = doc.createElement("pre_sharedartifact");
			this.post_SharedArtifacts = doc.createElement("post_sharedartifact");
			this.Record = doc.createElement("record");
			this.Record.setAttribute("no", this.RecordOrderNumber.toString());
			
			this.Record.appendChild(Timestamp);
			this.Record.appendChild(RuleId);
			this.Record.appendChild(RoleId);
			this.Record.appendChild(pre_SharedArtifacts);
			this.Record.appendChild(post_SharedArtifacts);
			
			
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	
	}
	
	public void setTimestamp(String timestamp)
	{
		this.Timestamp.setTextContent(timestamp);
		
		
	}
	
	public void setRuleId(String RuleId)
	{
		
		this.RuleId.setTextContent(RuleId);
		
		
	}
	
	public void setRoleId(String RoleId)
	{
		
		this.RoleId.setTextContent(RoleId);
		
		
	}
	
	public void setPreSharedArtifacts(Element Artifact)
	{
		
		Node importedNode = doc.importNode(Artifact, true);
		//test
	
		
		this.pre_SharedArtifacts.appendChild(importedNode);
		
		
	}
	
	public void setPostSharedtArtifacts(Element Artifact)
	{
		
		
		Node importedNode = doc.importNode(Artifact, true);
		this.post_SharedArtifacts.appendChild(importedNode);
		
	}
	
	public String getTimestamp()
	{
		
		return this.Timestamp.getTextContent();
		
	}
	
	public String getRuleId()
	{
		
		return this.RuleId.getTextContent();
		
	}
	
	public String getRoleId()
	{
		
		return this.RoleId.getTextContent();
		
	}
	
	public Element getPreSharedArtifact()
	{
		
		return this.pre_SharedArtifacts;
		
	}
	
	public Element getPostSharedArtifact()
	{
		
		return this.post_SharedArtifacts;
		
	}
	
	public Element getLogRecord()
	{
		
		return this.Record;
		
	}
	
	public Integer getRecordNumber()
	{
		return this.RecordOrderNumber;
		
	}
	
	
	
	
	
	
	
	
}
