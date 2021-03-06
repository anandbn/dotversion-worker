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
 * <p>Java class for PackageVersion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PackageVersion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="majorNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="minorNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="namespace" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PackageVersion", propOrder = {
    "majorNumber",
    "minorNumber",
    "namespace"
})
public class PackageVersion {

    protected int majorNumber;
    protected int minorNumber;
    @XmlElement(required = true)
    protected String namespace;

    /**
     * Gets the value of the majorNumber property.
     * 
     */
    public int getMajorNumber() {
        return majorNumber;
    }

    /**
     * Sets the value of the majorNumber property.
     * 
     */
    public void setMajorNumber(int value) {
        this.majorNumber = value;
    }

    /**
     * Gets the value of the minorNumber property.
     * 
     */
    public int getMinorNumber() {
        return minorNumber;
    }

    /**
     * Sets the value of the minorNumber property.
     * 
     */
    public void setMinorNumber(int value) {
        this.minorNumber = value;
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

}
