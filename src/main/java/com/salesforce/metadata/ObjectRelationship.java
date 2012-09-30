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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ObjectRelationship complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjectRelationship">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="join" type="{}ObjectRelationship" minOccurs="0"/>
 *         &lt;element name="outerJoin" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="relationship" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectRelationship", propOrder = {
    "join",
    "outerJoin",
    "relationship"
})
public class ObjectRelationship {

    protected ObjectRelationship join;
    protected boolean outerJoin;
    @XmlElement(required = true)
    protected String relationship;

    /**
     * Gets the value of the join property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectRelationship }
     *     
     */
    public ObjectRelationship getJoin() {
        return join;
    }

    /**
     * Sets the value of the join property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectRelationship }
     *     
     */
    public void setJoin(ObjectRelationship value) {
        this.join = value;
    }

    /**
     * Gets the value of the outerJoin property.
     * 
     */
    public boolean isOuterJoin() {
        return outerJoin;
    }

    /**
     * Sets the value of the outerJoin property.
     * 
     */
    public void setOuterJoin(boolean value) {
        this.outerJoin = value;
    }

    /**
     * Gets the value of the relationship property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * Sets the value of the relationship property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationship(String value) {
        this.relationship = value;
    }

}
