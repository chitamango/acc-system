//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.10.07 at 10:50:51 PM EST 
//


package com.inter.process;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mappingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mappingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="coordination" type="{http://www.swin.edu.au}coordinationType"/>
 *         &lt;element name="notification" type="{http://www.swin.edu.au}notificationType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mappingType", propOrder = {
    "coordination",
    "notification"
})
public class MappingType {

    @XmlElement(namespace = "http://www.swin.edu.au", required = true)
    protected CoordinationType coordination;
    @XmlElement(namespace = "http://www.swin.edu.au", required = true)
    protected NotificationType notification;

    /**
     * Gets the value of the coordination property.
     * 
     * @return
     *     possible object is
     *     {@link CoordinationType }
     *     
     */
    public CoordinationType getCoordination() {
        return coordination;
    }

    /**
     * Sets the value of the coordination property.
     * 
     * @param value
     *     allowed object is
     *     {@link CoordinationType }
     *     
     */
    public void setCoordination(CoordinationType value) {
        this.coordination = value;
    }

    /**
     * Gets the value of the notification property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationType }
     *     
     */
    public NotificationType getNotification() {
        return notification;
    }

    /**
     * Sets the value of the notification property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationType }
     *     
     */
    public void setNotification(NotificationType value) {
        this.notification = value;
    }

}