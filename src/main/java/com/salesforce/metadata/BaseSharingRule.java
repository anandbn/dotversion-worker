//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.24 at 12:06:48 PM PDT 
//


package com.salesforce.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BaseSharingRule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseSharingRule">
 *   &lt;complexContent>
 *     &lt;extension base="{}Metadata">
 *       &lt;sequence>
 *         &lt;element name="sharedTo" type="{}SharedTo"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseSharingRule", propOrder = {
    "sharedTo"
})
@XmlSeeAlso({
    OwnerSharingRule.class,
    CriteriaBasedSharingRule.class
})
public class BaseSharingRule
    extends Metadata
{

    @XmlElement(required = true)
    protected SharedTo sharedTo;

    /**
     * Gets the value of the sharedTo property.
     * 
     * @return
     *     possible object is
     *     {@link SharedTo }
     *     
     */
    public SharedTo getSharedTo() {
        return sharedTo;
    }

    /**
     * Sets the value of the sharedTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SharedTo }
     *     
     */
    public void setSharedTo(SharedTo value) {
        this.sharedTo = value;
    }

}