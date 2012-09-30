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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeployOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeployOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allowMissingFiles" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="autoUpdatePackage" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="checkOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ignoreWarnings" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="performRetrieve" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="purgeOnDelete" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="rollbackOnError" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="runAllTests" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="runTests" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="singlePackage" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeployOptions", propOrder = {
    "allowMissingFiles",
    "autoUpdatePackage",
    "checkOnly",
    "ignoreWarnings",
    "performRetrieve",
    "purgeOnDelete",
    "rollbackOnError",
    "runAllTests",
    "runTests",
    "singlePackage"
})
public class DeployOptions {

    protected boolean allowMissingFiles;
    protected boolean autoUpdatePackage;
    protected boolean checkOnly;
    protected boolean ignoreWarnings;
    protected boolean performRetrieve;
    protected boolean purgeOnDelete;
    protected boolean rollbackOnError;
    protected boolean runAllTests;
    protected List<String> runTests;
    protected boolean singlePackage;

    /**
     * Gets the value of the allowMissingFiles property.
     * 
     */
    public boolean isAllowMissingFiles() {
        return allowMissingFiles;
    }

    /**
     * Sets the value of the allowMissingFiles property.
     * 
     */
    public void setAllowMissingFiles(boolean value) {
        this.allowMissingFiles = value;
    }

    /**
     * Gets the value of the autoUpdatePackage property.
     * 
     */
    public boolean isAutoUpdatePackage() {
        return autoUpdatePackage;
    }

    /**
     * Sets the value of the autoUpdatePackage property.
     * 
     */
    public void setAutoUpdatePackage(boolean value) {
        this.autoUpdatePackage = value;
    }

    /**
     * Gets the value of the checkOnly property.
     * 
     */
    public boolean isCheckOnly() {
        return checkOnly;
    }

    /**
     * Sets the value of the checkOnly property.
     * 
     */
    public void setCheckOnly(boolean value) {
        this.checkOnly = value;
    }

    /**
     * Gets the value of the ignoreWarnings property.
     * 
     */
    public boolean isIgnoreWarnings() {
        return ignoreWarnings;
    }

    /**
     * Sets the value of the ignoreWarnings property.
     * 
     */
    public void setIgnoreWarnings(boolean value) {
        this.ignoreWarnings = value;
    }

    /**
     * Gets the value of the performRetrieve property.
     * 
     */
    public boolean isPerformRetrieve() {
        return performRetrieve;
    }

    /**
     * Sets the value of the performRetrieve property.
     * 
     */
    public void setPerformRetrieve(boolean value) {
        this.performRetrieve = value;
    }

    /**
     * Gets the value of the purgeOnDelete property.
     * 
     */
    public boolean isPurgeOnDelete() {
        return purgeOnDelete;
    }

    /**
     * Sets the value of the purgeOnDelete property.
     * 
     */
    public void setPurgeOnDelete(boolean value) {
        this.purgeOnDelete = value;
    }

    /**
     * Gets the value of the rollbackOnError property.
     * 
     */
    public boolean isRollbackOnError() {
        return rollbackOnError;
    }

    /**
     * Sets the value of the rollbackOnError property.
     * 
     */
    public void setRollbackOnError(boolean value) {
        this.rollbackOnError = value;
    }

    /**
     * Gets the value of the runAllTests property.
     * 
     */
    public boolean isRunAllTests() {
        return runAllTests;
    }

    /**
     * Sets the value of the runAllTests property.
     * 
     */
    public void setRunAllTests(boolean value) {
        this.runAllTests = value;
    }

    /**
     * Gets the value of the runTests property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the runTests property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRunTests().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRunTests() {
        if (runTests == null) {
            runTests = new ArrayList<String>();
        }
        return this.runTests;
    }

    /**
     * Gets the value of the singlePackage property.
     * 
     */
    public boolean isSinglePackage() {
        return singlePackage;
    }

    /**
     * Sets the value of the singlePackage property.
     * 
     */
    public void setSinglePackage(boolean value) {
        this.singlePackage = value;
    }

}
