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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sharedArtifactsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sharedArtifactsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shared_artifact" type="{http://www.swin.edu.au}sharedArtifactType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sharedArtifactsType", propOrder = {
    "sharedArtifact"
})
public class SharedArtifactsType {

    @XmlElement(name = "shared_artifact", namespace = "http://www.swin.edu.au", required = true)
    protected List<SharedArtifactType> sharedArtifact;

    /**
     * Gets the value of the sharedArtifact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sharedArtifact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSharedArtifact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SharedArtifactType }
     * 
     * 
     */
    public List<SharedArtifactType> getSharedArtifact() {
        if (sharedArtifact == null) {
            sharedArtifact = new ArrayList<SharedArtifactType>();
        }
        return this.sharedArtifact;
    }

}
