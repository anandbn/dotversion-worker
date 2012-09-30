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
 * <p>Java class for Letterhead complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Letterhead">
 *   &lt;complexContent>
 *     &lt;extension base="{}Metadata">
 *       &lt;sequence>
 *         &lt;element name="available" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="backgroundColor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bodyColor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bottomLine" type="{}LetterheadLine"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="footer" type="{}LetterheadHeaderFooter"/>
 *         &lt;element name="header" type="{}LetterheadHeaderFooter"/>
 *         &lt;element name="middleLine" type="{}LetterheadLine"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="topLine" type="{}LetterheadLine"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Letterhead", propOrder = {
    "available",
    "backgroundColor",
    "bodyColor",
    "bottomLine",
    "description",
    "footer",
    "header",
    "middleLine",
    "name",
    "topLine"
})
public class Letterhead
    extends Metadata
{

    protected boolean available;
    @XmlElement(required = true)
    protected String backgroundColor;
    @XmlElement(required = true)
    protected String bodyColor;
    @XmlElement(required = true)
    protected LetterheadLine bottomLine;
    protected String description;
    @XmlElement(required = true)
    protected LetterheadHeaderFooter footer;
    @XmlElement(required = true)
    protected LetterheadHeaderFooter header;
    @XmlElement(required = true)
    protected LetterheadLine middleLine;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected LetterheadLine topLine;

    /**
     * Gets the value of the available property.
     * 
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the value of the available property.
     * 
     */
    public void setAvailable(boolean value) {
        this.available = value;
    }

    /**
     * Gets the value of the backgroundColor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Sets the value of the backgroundColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackgroundColor(String value) {
        this.backgroundColor = value;
    }

    /**
     * Gets the value of the bodyColor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBodyColor() {
        return bodyColor;
    }

    /**
     * Sets the value of the bodyColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBodyColor(String value) {
        this.bodyColor = value;
    }

    /**
     * Gets the value of the bottomLine property.
     * 
     * @return
     *     possible object is
     *     {@link LetterheadLine }
     *     
     */
    public LetterheadLine getBottomLine() {
        return bottomLine;
    }

    /**
     * Sets the value of the bottomLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link LetterheadLine }
     *     
     */
    public void setBottomLine(LetterheadLine value) {
        this.bottomLine = value;
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
     * Gets the value of the footer property.
     * 
     * @return
     *     possible object is
     *     {@link LetterheadHeaderFooter }
     *     
     */
    public LetterheadHeaderFooter getFooter() {
        return footer;
    }

    /**
     * Sets the value of the footer property.
     * 
     * @param value
     *     allowed object is
     *     {@link LetterheadHeaderFooter }
     *     
     */
    public void setFooter(LetterheadHeaderFooter value) {
        this.footer = value;
    }

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link LetterheadHeaderFooter }
     *     
     */
    public LetterheadHeaderFooter getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link LetterheadHeaderFooter }
     *     
     */
    public void setHeader(LetterheadHeaderFooter value) {
        this.header = value;
    }

    /**
     * Gets the value of the middleLine property.
     * 
     * @return
     *     possible object is
     *     {@link LetterheadLine }
     *     
     */
    public LetterheadLine getMiddleLine() {
        return middleLine;
    }

    /**
     * Sets the value of the middleLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link LetterheadLine }
     *     
     */
    public void setMiddleLine(LetterheadLine value) {
        this.middleLine = value;
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

    /**
     * Gets the value of the topLine property.
     * 
     * @return
     *     possible object is
     *     {@link LetterheadLine }
     *     
     */
    public LetterheadLine getTopLine() {
        return topLine;
    }

    /**
     * Sets the value of the topLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link LetterheadLine }
     *     
     */
    public void setTopLine(LetterheadLine value) {
        this.topLine = value;
    }

}