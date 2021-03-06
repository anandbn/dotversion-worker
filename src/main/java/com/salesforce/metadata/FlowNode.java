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
 * <p>Java class for FlowNode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlowNode">
 *   &lt;complexContent>
 *     &lt;extension base="{}FlowElement">
 *       &lt;sequence>
 *         &lt;element name="label" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="locationX" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="locationY" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlowNode", propOrder = {
    "label",
    "locationX",
    "locationY"
})
@XmlSeeAlso({
    FlowApexPluginCall.class,
    FlowRecordUpdate.class,
    FlowStep.class,
    FlowRecordCreate.class,
    FlowRecordLookup.class,
    FlowRecordDelete.class,
    FlowDecision.class,
    FlowScreen.class,
    FlowSubflow.class,
    FlowAssignment.class
})
public class FlowNode
    extends FlowElement
{

    @XmlElement(required = true)
    protected String label;
    protected int locationX;
    protected int locationY;

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Gets the value of the locationX property.
     * 
     */
    public int getLocationX() {
        return locationX;
    }

    /**
     * Sets the value of the locationX property.
     * 
     */
    public void setLocationX(int value) {
        this.locationX = value;
    }

    /**
     * Gets the value of the locationY property.
     * 
     */
    public int getLocationY() {
        return locationY;
    }

    /**
     * Sets the value of the locationY property.
     * 
     */
    public void setLocationY(int value) {
        this.locationY = value;
    }

}
