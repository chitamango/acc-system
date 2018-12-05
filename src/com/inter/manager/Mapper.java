package com.inter.manager;
/*
 *  @version 1.1
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;

import com.inter.instance.CoordinationRule;
import com.inter.instance.NotificationRule;
import com.inter.instance.SharedArtifactInstance;
import com.inter.instance.SharedAttributeInstance;
import com.inter.instance.SharedRuleInstance;



public class Mapper {

	
	
	

	public void mapppingNotificationMessageToSharedArtifact(SharedRuleInstance SharedRule, SharedArtifactInstance SharedArtifact, OMElement NotificationMessage)throws Exception 
	{
		ArrayList<NotificationRule> NotificationRuleList = SharedRule.getNotificationRules();
		ListIterator<NotificationRule> NotificationRuleListIterator = NotificationRuleList.listIterator();
		//loop mapping of notification message
		
		
		
		while(NotificationRuleListIterator.hasNext())
		{
			//current notification rule
			NotificationRule CurrentInspectedNotificationRule = NotificationRuleListIterator.next();
			
			OMElement SourceLocalArtifactElement = null;
			OMElement SourceLocalAttributeElement = null;
			ArrayList<String> AttributeValueList = new ArrayList<String>();
		
			
			//namespace of notificaiton message
			String NamespaceURI = NotificationMessage.getNamespace().getNamespaceURI();
				
			//From or source defined in notification rule
			String FromType = CurrentInspectedNotificationRule.getFromType();
			String FromArtifact = CurrentInspectedNotificationRule.getFromName();
			String FromAttribute = CurrentInspectedNotificationRule.getFromAttribute();
			
			//Destination or target artifact defined in notification rule
			String ToType = CurrentInspectedNotificationRule.getToType();
			String ToArtifact = CurrentInspectedNotificationRule.getToName();
			String ToAttribute = CurrentInspectedNotificationRule.getToAttribute();
			
			
			
			//QName of notification message, this will used to access element in the notification message 
			QName ArtifactsQname = new QName(NamespaceURI,"artifacts");
			QName LocalArtifactQname = new QName(NamespaceURI,"local_artifact");
			QName ArtifactNameAttributeQname = new QName(NamespaceURI,"name");
			
			//QName used to access element in the selected artifact
			QName ArtifactAttributeQname = new QName(NamespaceURI,"attribute");
			QName AttributeNameAttributeQname = new QName(NamespaceURI,"name");
			
			
			//select the right local artifact element
			 Iterator<OMElement> FromLocalArtifactsIterator = NotificationMessage.getFirstChildWithName(ArtifactsQname).getChildElements(); 
			 
			
			 
			 while(FromLocalArtifactsIterator.hasNext())
			 {
				 //get current local artifact element
				 OMElement currentInspectedLocalArtifactElement = FromLocalArtifactsIterator.next();
				 //compare name of artifact to source artifact of the notification rule
				 
				
				 
				 OMAttribute NameAttributeOfCurrentInspectedLocalArtifactElement = currentInspectedLocalArtifactElement.getAttribute(ArtifactNameAttributeQname);
				 
				 String InspectedArtifactName = NameAttributeOfCurrentInspectedLocalArtifactElement.getAttributeValue();
				 
					 if(InspectedArtifactName.equalsIgnoreCase(FromArtifact))
					 {
						 
						 SourceLocalArtifactElement = currentInspectedLocalArtifactElement;
						 break;
						 
					 }
				 
				 
			 }
			 
			 
			
			//select the right Attribute
			Iterator<OMElement> FromAttributeIterator = SourceLocalArtifactElement.getChildrenWithName(ArtifactAttributeQname);
			
			while(FromAttributeIterator.hasNext())
			{
				 OMElement currentInspectedLocalAttributeElement = FromAttributeIterator.next();
				 OMAttribute NameAttributeOfCurrentInspectedLocalAttributeElement = currentInspectedLocalAttributeElement.getAttribute(AttributeNameAttributeQname);
				 String InspectedAttributeName = NameAttributeOfCurrentInspectedLocalAttributeElement.getAttributeValue();
				 
					 if(InspectedAttributeName.equalsIgnoreCase(FromAttribute))
					 {
						 
						 SourceLocalAttributeElement = currentInspectedLocalAttributeElement;
						 break;
						 
					 }
					
				
			}
			
		
			
			//value of current attribute
			Iterator<OMElement> AttributeValueIterator = SourceLocalAttributeElement.getChildElements();
			while(AttributeValueIterator.hasNext())
			{
				OMElement CurrentValueElement = AttributeValueIterator.next();
				String CurrentValue = CurrentValueElement.getText();
				
				
				AttributeValueList.add(CurrentValue);
				
			}
			
			
			
			
			
			if(SharedArtifact.getSharedArtifactName().equalsIgnoreCase(ToArtifact))
			{
				
				
				//this to select target attribute
				SharedAttributeInstance MappingSharedAtt = SharedArtifact.getSharedAttributeInstance(ToAttribute);
				String AttributeType = MappingSharedAtt.getAttributeType();
				String AttributeStructure = MappingSharedAtt.getAttributeStructure();
				
				
				
				if(AttributeStructure.equalsIgnoreCase("pair"))
				{
					
					try {
						
						if(MappingSharedAtt.Size()== 0)
						{
							if(AttributeType.equalsIgnoreCase("string"))
							{	
								MappingSharedAtt.add(AttributeValueList.get(0));
							}
							else if (AttributeType.equalsIgnoreCase("integer"))
							{
								Integer ConvertedValue = Integer.parseInt(AttributeValueList.get(0));
								MappingSharedAtt.add(ConvertedValue);
								
							}
							else if (AttributeType.equalsIgnoreCase("short"))
							{
								Short ConvertedValue = new Short( AttributeValueList.get(0));
								MappingSharedAtt.add(ConvertedValue);
								
							}
							else if (AttributeType.equalsIgnoreCase("double"))
							{
								Double ConvertedValue = Double.parseDouble(AttributeValueList.get(0));
								MappingSharedAtt.add(ConvertedValue);
								
							}
							else if (AttributeType.equalsIgnoreCase("boolean"))
							{
								
								Boolean ConvertedValue = Boolean.parseBoolean( AttributeValueList.get(0));
								MappingSharedAtt.add(ConvertedValue);
								
								
							}
							// the last one should be artifact
							
							
						}
						else
						{
							
							if(AttributeType.equalsIgnoreCase("string"))
							{	
								MappingSharedAtt.set(0, AttributeValueList.get(0));
							}
							else if (AttributeType.equalsIgnoreCase("integer"))
							{
								Integer ConvertedValue = Integer.parseInt( AttributeValueList.get(0));
								MappingSharedAtt.set(0, ConvertedValue);
								
								
							}
							else if (AttributeType.equalsIgnoreCase("short"))
							{
								Short ConvertedValue = new Short(AttributeValueList.get(0));
								MappingSharedAtt.set(0, ConvertedValue);
								
							}
							else if (AttributeType.equalsIgnoreCase("double"))
							{
								Double ConvertedValue = Double.parseDouble(AttributeValueList.get(0));
								MappingSharedAtt.set(0, ConvertedValue);
								
							}
							else if (AttributeType.equalsIgnoreCase("boolean"))
							{
								
								Boolean ConvertedValue = Boolean.parseBoolean(AttributeValueList.get(0));
								MappingSharedAtt.set(0, ConvertedValue);
								
								
							}
							// the last one should be artifact
							
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
			}
			else if (AttributeStructure.equalsIgnoreCase("list"))
			{
				
				try {
					
					if(AttributeType.equalsIgnoreCase("string"))
					{	
						MappingSharedAtt.addAll(AttributeValueList);
					}
					else if (AttributeType.equalsIgnoreCase("integer"))
					{ 
						ListIterator<String> Aitr =AttributeValueList.listIterator();
						
						while(Aitr.hasNext())
						{
							Integer ConvertedValue = Integer.parseInt(Aitr.next());
							MappingSharedAtt.add(ConvertedValue);
						
						}
						
					}
					else if (AttributeType.equalsIgnoreCase("short"))
					{
						
						ListIterator<String> Aitr =AttributeValueList.listIterator();
						
						while(Aitr.hasNext())
						{
							Short ConvertedValue = new Short(Aitr.next());	
							MappingSharedAtt.add(ConvertedValue);
						
						}
						
					}
					else if (AttributeType.equalsIgnoreCase("double"))
					{
						
						ListIterator<String> Aitr =AttributeValueList.listIterator();
						
						while(Aitr.hasNext())
						{
							Double ConvertedValue = Double.parseDouble(Aitr.next());
							MappingSharedAtt.add(ConvertedValue);
						
						}
						
					}
					else if (AttributeType.equalsIgnoreCase("boolean"))
					{
						
						
						ListIterator<String> Aitr =AttributeValueList.listIterator();
						
						while(Aitr.hasNext())
						{
							Boolean ConvertedValue = Boolean.parseBoolean(Aitr.next());
							MappingSharedAtt.add(ConvertedValue);
						
						}
						
						
					}
					// the last one should be artifact
								
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			
			
		}
			
				
		
	
			
		}		
			
	}
	
	
	public void mapppingArtifactToPayload(SharedRuleInstance SharedRule, SharedArtifactInstance SharedArtifact, OMElement Payload)	
	{
		
		ArrayList<CoordinationRule> CoordinationRuleList = SharedRule.getCoordinationRules();
		ListIterator<CoordinationRule> CoordinationRuleListIterator = CoordinationRuleList.listIterator();
		
		while(CoordinationRuleListIterator.hasNext())
		{
		
			//current notification rule
			CoordinationRule CurrentInspectedCoordinationRule = CoordinationRuleListIterator.next();
			
			//namespace of payload 
			String NamespaceURIofPayload = Payload.getNamespace().getNamespaceURI();
			
			
			//From or source defined in coordination rule
			String FromType = CurrentInspectedCoordinationRule.getFromType();
			String FromArtifact = CurrentInspectedCoordinationRule.getFromName();
			String FromArtifactAttribute = CurrentInspectedCoordinationRule.getFromAttribute();
			
			//to or destination defined in coordination rule
			String ToType = CurrentInspectedCoordinationRule.getToType();
			String ToPayloadmessage = CurrentInspectedCoordinationRule.getToName();
			String ToPayloadPart = CurrentInspectedCoordinationRule.getToPart();
			
			//
			
			String ArtifactName = SharedArtifact.getSharedArtifactName();
			String payloadMessageName = Payload.getLocalName();
			
			//check type of mapping -> to be artifact to message
			if(FromType.equalsIgnoreCase("shared_artifact") && ToType.equalsIgnoreCase("message"))
			{
				
	
				
				if(FromArtifact.equalsIgnoreCase(ArtifactName))//&& ToPayloadmessage.equalsIgnoreCase(payloadMessageName) )
				{	
						
					
						//from attribute of an artifact
						SharedAttributeInstance MappingAttribute = SharedArtifact.getSharedAttributeInstance(FromArtifactAttribute);
						
						//String AttributeType = MappingAttribute.getAttributeType();
						
						String AttributeStructure = MappingAttribute.getAttributeStructure();
					
						
						//to part
						QName PayloadPartQName = new QName(NamespaceURIofPayload,ToPayloadPart);
					
						OMElement ToPart = Payload.getFirstChildWithName(PayloadPartQName);
						
						
						if(AttributeStructure.equalsIgnoreCase("pair"))
						{
							try {
								
								
								ToPart.setText( MappingAttribute.get(0).toString());
						
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							
						}
						else if (AttributeStructure.equalsIgnoreCase("list"))
						{
							
							
							
							
						}
						
						
						
						
			
				}
				
			}
			
			
		
			
		}
		
		
		
		
	
		
		
		
		
	}
	
	public void mappingRequestMessageToPayload(SharedRuleInstance SharedRule, OMElement RequestMessage,OMElement Payload)
	{
		
		
		ArrayList<CoordinationRule> CoordinationRuleList = SharedRule.getCoordinationRules();
		ListIterator<CoordinationRule> CoordinationRuleListIterator = CoordinationRuleList.listIterator();
		
		while(CoordinationRuleListIterator.hasNext())
		{
		
			//current notification rule
			CoordinationRule CurrentInspectedCoordinationRule = CoordinationRuleListIterator.next();
			
			//namespace of payload and requestmessage
			String NamespaceURIofRequestMessage = RequestMessage.getNamespace().getNamespaceURI();
			String NamespaceURIofPayload = Payload.getNamespace().getNamespaceURI();
			
			
			//From or source defined in coordination rule
			String FromType = CurrentInspectedCoordinationRule.getFromType();
			String FromRequestmessage = CurrentInspectedCoordinationRule.getFromName();
			String FromRequestPart = CurrentInspectedCoordinationRule.getFromPart();
			
			//to or destination defined in coordination rule
			String ToType = CurrentInspectedCoordinationRule.getToType();
			String ToPayloadmessage = CurrentInspectedCoordinationRule.getToName();
			String ToPayloadPart = CurrentInspectedCoordinationRule.getToPart();
			
			
			
			String requestMessageName = RequestMessage.getLocalName();
			String payloadMessageName = Payload.getLocalName();
			
			//check type of mapping -> to be message to message
			if(FromType.equalsIgnoreCase("message") && ToType.equalsIgnoreCase("message"))
			{
				
	
				
			//	if(FromRequestmessage.equalsIgnoreCase(requestMessageName)&& ToPayloadmessage.equalsIgnoreCase(payloadMessageName) )
			//	{	
						//from part
						//QName UserMessageQName = new QName(NamespaceURIofUserMessage,currentCopy.getFromMessage());
						QName RequestPartQName = new QName(NamespaceURIofRequestMessage,FromRequestPart);
						
						OMElement fromRequestPart = RequestMessage.getFirstChildWithName(RequestPartQName);
						
						String RequestParameter = fromRequestPart.getText();
						
						
						
						//to part
						//QName inputMessageQName = new QName(NamespaceURIofInputMessage,currentCopy.getToMessage());
						QName PayloadPartQName = new QName(NamespaceURIofPayload,ToPayloadPart);
					
						OMElement ToPart = Payload.getFirstChildWithName(PayloadPartQName);
						
						ToPart.setText(RequestParameter);
			
			//	}
				
			}
			
			
		
			
		}
		
		
		
		
	}
	
}
