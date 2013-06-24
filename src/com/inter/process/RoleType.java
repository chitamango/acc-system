//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.07 at 10:50:51 PM EST 
//


package com.inter.process;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for roleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="roleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="service" type="{http://www.swin.edu.au}serviceType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="namespace" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="role_location" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="role_port" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "roleType", propOrder = {
    "service"
})
public class RoleType {

    @XmlElement(namespace = "http://www.swin.edu.au", required = true)
    protected List<ServiceType> service;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute(required = true)
    protected String namespace;
    @XmlAttribute(name = "role_location", required = true)
    protected String roleLocation;
    @XmlAttribute(name = "role_port", required = true)
    protected String rolePort;

    /**
     * Gets the value of the service property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the service property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceType }
     * 
     * 
     */
    public List<ServiceType> getService() {
        if (service == null) {
            service = new ArrayList<ServiceType>();
        }
        return this.service;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the namespace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Sets the value of the namespace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamespace(String value) {
        this.namespace = value;
    }

    /**
     * Gets the value of the roleLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleLocation() {
        return roleLocation;
    }

    /**
     * Sets the value of the roleLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleLocation(String value) {
        this.roleLocation = value;
    }

    /**
     * Gets the value of the rolePort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRolePort() {
        return rolePort;
    }

    /**
     * Sets the value of the rolePort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRolePort(String value) {
        this.rolePort = value;
    }

}
