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
 * <p>Java class for FlowChoice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlowChoice">
 *   &lt;complexContent>
 *     &lt;extension base="{}FlowElement">
 *       &lt;sequence>
 *         &lt;element name="choiceText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataType" type="{}FlowDataType"/>
 *         &lt;element name="userInput" type="{}FlowChoiceUserInput" minOccurs="0"/>
 *         &lt;element name="value" type="{}FlowElementReferenceOrValue" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlowChoice", propOrder = {
    "choiceText",
    "dataType",
    "userInput",
    "value"
})
public class FlowChoice
    extends FlowElement
{

    @XmlElement(required = true)
    protected String choiceText;
    @XmlElement(required = true)
    protected FlowDataType dataType;
    protected FlowChoiceUserInput userInput;
    protected FlowElementReferenceOrValue value;

    /**
     * Gets the value of the choiceText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChoiceText() {
        return choiceText;
    }

    /**
     * Sets the value of the choiceText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChoiceText(String value) {
        this.choiceText = value;
    }

    /**
     * Gets the value of the dataType property.
     * 
     * @return
     *     possible object is
     *     {@link FlowDataType }
     *     
     */
    public FlowDataType getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlowDataType }
     *     
     */
    public void setDataType(FlowDataType value) {
        this.dataType = value;
    }

    /**
     * Gets the value of the userInput property.
     * 
     * @return
     *     possible object is
     *     {@link FlowChoiceUserInput }
     *     
     */
    public FlowChoiceUserInput getUserInput() {
        return userInput;
    }

    /**
     * Sets the value of the userInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlowChoiceUserInput }
     *     
     */
    public void setUserInput(FlowChoiceUserInput value) {
        this.userInput = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link FlowElementReferenceOrValue }
     *     
     */
    public FlowElementReferenceOrValue getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlowElementReferenceOrValue }
     *     
     */
    public void setValue(FlowElementReferenceOrValue value) {
        this.value = value;
    }

}
