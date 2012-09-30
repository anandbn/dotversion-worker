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
 * <p>Java class for AccountTerritorySharingRule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountTerritorySharingRule">
 *   &lt;complexContent>
 *     &lt;extension base="{}OwnerSharingRule">
 *       &lt;sequence>
 *         &lt;element name="accountAccessLevel" type="{}ShareAccessLevelNoNone"/>
 *         &lt;element name="caseAccessLevel" type="{}ShareAccessLevelNoAll"/>
 *         &lt;element name="contactAccessLevel" type="{}ShareAccessLevelNoAll"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="opportunityAccessLevel" type="{}ShareAccessLevelNoAll"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountTerritorySharingRule", propOrder = {
    "accountAccessLevel",
    "caseAccessLevel",
    "contactAccessLevel",
    "name",
    "opportunityAccessLevel"
})
public class AccountTerritorySharingRule
    extends OwnerSharingRule
{

    @XmlElement(required = true)
    protected ShareAccessLevelNoNone accountAccessLevel;
    @XmlElement(required = true)
    protected ShareAccessLevelNoAll caseAccessLevel;
    @XmlElement(required = true)
    protected ShareAccessLevelNoAll contactAccessLevel;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected ShareAccessLevelNoAll opportunityAccessLevel;

    /**
     * Gets the value of the accountAccessLevel property.
     * 
     * @return
     *     possible object is
     *     {@link ShareAccessLevelNoNone }
     *     
     */
    public ShareAccessLevelNoNone getAccountAccessLevel() {
        return accountAccessLevel;
    }

    /**
     * Sets the value of the accountAccessLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShareAccessLevelNoNone }
     *     
     */
    public void setAccountAccessLevel(ShareAccessLevelNoNone value) {
        this.accountAccessLevel = value;
    }

    /**
     * Gets the value of the caseAccessLevel property.
     * 
     * @return
     *     possible object is
     *     {@link ShareAccessLevelNoAll }
     *     
     */
    public ShareAccessLevelNoAll getCaseAccessLevel() {
        return caseAccessLevel;
    }

    /**
     * Sets the value of the caseAccessLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShareAccessLevelNoAll }
     *     
     */
    public void setCaseAccessLevel(ShareAccessLevelNoAll value) {
        this.caseAccessLevel = value;
    }

    /**
     * Gets the value of the contactAccessLevel property.
     * 
     * @return
     *     possible object is
     *     {@link ShareAccessLevelNoAll }
     *     
     */
    public ShareAccessLevelNoAll getContactAccessLevel() {
        return contactAccessLevel;
    }

    /**
     * Sets the value of the contactAccessLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShareAccessLevelNoAll }
     *     
     */
    public void setContactAccessLevel(ShareAccessLevelNoAll value) {
        this.contactAccessLevel = value;
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
     * Gets the value of the opportunityAccessLevel property.
     * 
     * @return
     *     possible object is
     *     {@link ShareAccessLevelNoAll }
     *     
     */
    public ShareAccessLevelNoAll getOpportunityAccessLevel() {
        return opportunityAccessLevel;
    }

    /**
     * Sets the value of the opportunityAccessLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShareAccessLevelNoAll }
     *     
     */
    public void setOpportunityAccessLevel(ShareAccessLevelNoAll value) {
        this.opportunityAccessLevel = value;
    }

}