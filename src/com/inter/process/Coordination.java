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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for coordination element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="coordination">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="roles" type="{http://www.swin.edu.au}rolesType"/>
 *           &lt;element name="shared_artifacts" type="{http://www.swin.edu.au}sharedArtifactsType"/>
 *           &lt;element name="shared_rules" type="{http://www.swin.edu.au}sharedRulesType"/>
 *         &lt;/sequence>
 *         &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "roles",
    "sharedArtifacts",
    "sharedRules"
})
@XmlRootElement(name = "coordination")
public class Coordination {

    @XmlElement(namespace = "http://www.swin.edu.au", required = true)
    protected RolesType roles;
    @XmlElement(name = "shared_artifacts", namespace = "http://www.swin.edu.au", required = true)
    protected SharedArtifactsType sharedArtifacts;
    @XmlElement(name = "shared_rules", namespace = "http://www.swin.edu.au", required = true)
    protected SharedRulesType sharedRules;
    @XmlAttribute(required = true)
    protected String name;

    /**
     * Gets the value of the roles property.
     * 
     * @return
     *     possible object is
     *     {@link RolesType }
     *     
     */
    public RolesType getRoles() {
        return roles;
    }

    /**
     * Sets the value of the roles property.
     * 
     * @param value
     *     allowed object is
     *     {@link RolesType }
     *     
     */
    public void setRoles(RolesType value) {
        this.roles = value;
    }

    /**
     * Gets the value of the sharedArtifacts property.
     * 
     * @return
     *     possible object is
     *     {@link SharedArtifactsType }
     *     
     */
    public SharedArtifactsType getSharedArtifacts() {
        return sharedArtifacts;
    }

    /**
     * Sets the value of the sharedArtifacts property.
     * 
     * @param value
     *     allowed object is
     *     {@link SharedArtifactsType }
     *     
     */
    public void setSharedArtifacts(SharedArtifactsType value) {
        this.sharedArtifacts = value;
    }

    /**
     * Gets the value of the sharedRules property.
     * 
     * @return
     *     possible object is
     *     {@link SharedRulesType }
     *     
     */
    public SharedRulesType getSharedRules() {
        return sharedRules;
    }

    /**
     * Sets the value of the sharedRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link SharedRulesType }
     *     
     */
    public void setSharedRules(SharedRulesType value) {
        this.sharedRules = value;
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

}
