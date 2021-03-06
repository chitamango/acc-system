//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.07 at 10:50:51 PM EST 
//


package com.inter.process;
/*
 *  @version 1.1
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for invokeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="invokeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mapping" type="{http://www.swin.edu.au}mappingType"/>
 *         &lt;element name="transitions" type="{http://www.swin.edu.au}transitionsType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="operation" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="role" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="service" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "invokeType", propOrder = {
    "mapping",
    "transitions"
})
public class InvokeType {

    @XmlElement(namespace = "http://www.swin.edu.au", required = true)
    protected MappingType mapping;
    @XmlElement(namespace = "http://www.swin.edu.au", required = true)
    protected TransitionsType transitions;
    @XmlAttribute(required = true)
    protected String operation;
    @XmlAttribute(required = true)
    protected String role;
    @XmlAttribute(required = true)
    protected String service;

    /**
     * Gets the value of the mapping property.
     * 
     * @return
     *     possible object is
     *     {@link MappingType }
     *     
     */
    public MappingType getMapping() {
        return mapping;
    }

    /**
     * Sets the value of the mapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link MappingType }
     *     
     */
    public void setMapping(MappingType value) {
        this.mapping = value;
    }

    /**
     * Gets the value of the transitions property.
     * 
     * @return
     *     possible object is
     *     {@link TransitionsType }
     *     
     */
    public TransitionsType getTransitions() {
        return transitions;
    }

    /**
     * Sets the value of the transitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransitionsType }
     *     
     */
    public void setTransitions(TransitionsType value) {
        this.transitions = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperation(String value) {
        this.operation = value;
    }

    /**
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRole(String value) {
        this.role = value;
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setService(String value) {
        this.service = value;
    }

}
