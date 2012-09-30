//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.24 at 12:06:48 PM PDT 
//


package com.salesforce.metadata;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FlowRecordUpdate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlowRecordUpdate">
 *   &lt;complexContent>
 *     &lt;extension base="{}FlowNode">
 *       &lt;sequence>
 *         &lt;element name="connector" type="{}FlowConnector" minOccurs="0"/>
 *         &lt;element name="faultConnector" type="{}FlowConnector" minOccurs="0"/>
 *         &lt;element name="filters" type="{}FlowRecordFilter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="inputAssignments" type="{}FlowInputFieldAssignment" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="object" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlowRecordUpdate", propOrder = {
    "connector",
    "faultConnector",
    "filters",
    "inputAssignments",
    "object"
})
public class FlowRecordUpdate
    extends FlowNode
{

    protected FlowConnector connector;
    protected FlowConnector faultConnector;
    protected List<FlowRecordFilter> filters;
    protected List<FlowInputFieldAssignment> inputAssignments;
    @XmlElement(required = true)
    protected String object;

    /**
     * Gets the value of the connector property.
     * 
     * @return
     *     possible object is
     *     {@link FlowConnector }
     *     
     */
    public FlowConnector getConnector() {
        return connector;
    }

    /**
     * Sets the value of the connector property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlowConnector }
     *     
     */
    public void setConnector(FlowConnector value) {
        this.connector = value;
    }

    /**
     * Gets the value of the faultConnector property.
     * 
     * @return
     *     possible object is
     *     {@link FlowConnector }
     *     
     */
    public FlowConnector getFaultConnector() {
        return faultConnector;
    }

    /**
     * Sets the value of the faultConnector property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlowConnector }
     *     
     */
    public void setFaultConnector(FlowConnector value) {
        this.faultConnector = value;
    }

    /**
     * Gets the value of the filters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the filters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFilters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlowRecordFilter }
     * 
     * 
     */
    public List<FlowRecordFilter> getFilters() {
        if (filters == null) {
            filters = new ArrayList<FlowRecordFilter>();
        }
        return this.filters;
    }

    /**
     * Gets the value of the inputAssignments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inputAssignments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInputAssignments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlowInputFieldAssignment }
     * 
     * 
     */
    public List<FlowInputFieldAssignment> getInputAssignments() {
        if (inputAssignments == null) {
            inputAssignments = new ArrayList<FlowInputFieldAssignment>();
        }
        return this.inputAssignments;
    }

    /**
     * Gets the value of the object property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObject() {
        return object;
    }

    /**
     * Sets the value of the object property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObject(String value) {
        this.object = value;
    }

}
