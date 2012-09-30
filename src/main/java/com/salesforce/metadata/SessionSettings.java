//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.24 at 12:06:48 PM PDT 
//


package com.salesforce.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SessionSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SessionSettings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="disableTimeoutWarning" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="enableCacheAndAutocomplete" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="enableSMSIdentity" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="forceRelogin" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="lockSessionsToIp" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="sessionTimeout" type="{}SessionTimeout" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SessionSettings", propOrder = {
    "disableTimeoutWarning",
    "enableCacheAndAutocomplete",
    "enableSMSIdentity",
    "forceRelogin",
    "lockSessionsToIp",
    "sessionTimeout"
})
public class SessionSettings {

    protected Boolean disableTimeoutWarning;
    protected Boolean enableCacheAndAutocomplete;
    protected Boolean enableSMSIdentity;
    protected Boolean forceRelogin;
    protected Boolean lockSessionsToIp;
    protected SessionTimeout sessionTimeout;

    /**
     * Gets the value of the disableTimeoutWarning property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDisableTimeoutWarning() {
        return disableTimeoutWarning;
    }

    /**
     * Sets the value of the disableTimeoutWarning property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDisableTimeoutWarning(Boolean value) {
        this.disableTimeoutWarning = value;
    }

    /**
     * Gets the value of the enableCacheAndAutocomplete property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnableCacheAndAutocomplete() {
        return enableCacheAndAutocomplete;
    }

    /**
     * Sets the value of the enableCacheAndAutocomplete property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnableCacheAndAutocomplete(Boolean value) {
        this.enableCacheAndAutocomplete = value;
    }

    /**
     * Gets the value of the enableSMSIdentity property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEnableSMSIdentity() {
        return enableSMSIdentity;
    }

    /**
     * Sets the value of the enableSMSIdentity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnableSMSIdentity(Boolean value) {
        this.enableSMSIdentity = value;
    }

    /**
     * Gets the value of the forceRelogin property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isForceRelogin() {
        return forceRelogin;
    }

    /**
     * Sets the value of the forceRelogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setForceRelogin(Boolean value) {
        this.forceRelogin = value;
    }

    /**
     * Gets the value of the lockSessionsToIp property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLockSessionsToIp() {
        return lockSessionsToIp;
    }

    /**
     * Sets the value of the lockSessionsToIp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLockSessionsToIp(Boolean value) {
        this.lockSessionsToIp = value;
    }

    /**
     * Gets the value of the sessionTimeout property.
     * 
     * @return
     *     possible object is
     *     {@link SessionTimeout }
     *     
     */
    public SessionTimeout getSessionTimeout() {
        return sessionTimeout;
    }

    /**
     * Sets the value of the sessionTimeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link SessionTimeout }
     *     
     */
    public void setSessionTimeout(SessionTimeout value) {
        this.sessionTimeout = value;
    }

}
