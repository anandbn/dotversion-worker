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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataCategoryGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataCategoryGroup">
 *   &lt;complexContent>
 *     &lt;extension base="{}Metadata">
 *       &lt;sequence>
 *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="dataCategory" type="{}DataCategory"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="label" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objectUsage" type="{}ObjectUsage" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name="DataCategoryGroup")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataCategoryGroup", propOrder = {
    "active",
    "dataCategory",
    "description",
    "label",
    "objectUsage"
})
public class DataCategoryGroup
    extends Metadata
{

    protected boolean active;
    @XmlElement(required = true)
    protected DataCategory dataCategory;
    protected String description;
    @XmlElement(required = true)
    protected String label;
    protected ObjectUsage objectUsage;

    /**
     * Gets the value of the active property.
     * 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the value of the active property.
     * 
     */
    public void setActive(boolean value) {
        this.active = value;
    }

    /**
     * Gets the value of the dataCategory property.
     * 
     * @return
     *     possible object is
     *     {@link DataCategory }
     *     
     */
    public DataCategory getDataCategory() {
        return dataCategory;
    }

    /**
     * Sets the value of the dataCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataCategory }
     *     
     */
    public void setDataCategory(DataCategory value) {
        this.dataCategory = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

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
     * Gets the value of the objectUsage property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectUsage }
     *     
     */
    public ObjectUsage getObjectUsage() {
        return objectUsage;
    }

    /**
     * Sets the value of the objectUsage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectUsage }
     *     
     */
    public void setObjectUsage(ObjectUsage value) {
        this.objectUsage = value;
    }

}
