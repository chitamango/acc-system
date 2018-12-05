package com.inter.instance;



import com.inter.process.Maptype;




/*
 *  @version 1.1
 */

public class CoordinationRule {
	
	private String fromType;
	private String fromName;
	private String fromPart;
	private String fromAttribute;
	private String toType;
	private String toName;
	private String toPart;
	private String toAttribute;
	
	
	
	
	public CoordinationRule(Maptype xmlBindingMap)
	{ 
		
		this.fromType = xmlBindingMap.getFrom().getType();
		this.fromName = xmlBindingMap.getFrom().getName();
		this.fromPart = xmlBindingMap.getFrom().getPart();
		this.fromAttribute = xmlBindingMap.getFrom().getAttribute();
		this.toType = xmlBindingMap.getTo().getType();
		this.toName = xmlBindingMap.getTo().getType();
		this.toPart = xmlBindingMap.getTo().getPart();
		this.toAttribute = xmlBindingMap.getTo().getAttribute();
		
	}
	
	
	
	public String getFromType()
	
	{
		return this.fromType;
		
	}
	
	
	public String getFromName()
	
	{
		return this.fromName;
		
	}
	
	public String getFromPart()
	
	{
		return this.fromPart;
		
	}
	
	public String getFromAttribute()
	{
		return this.fromAttribute;
	}
	
	public String getToType()
	
	{
		return this.toType;
		
	}
	
	
	public String getToName()
	
	{
		return this.toName;
		
	}
	
	public String getToPart()
	
	{
		return this.toPart;
		
	}
	
	public String getToAttribute()
	{
		return this.toAttribute;
		
	}
	
	
	
	
	
	
	

}
