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
 * <p>Java class for ReportBlockInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReportBlockInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="aggregateReferences" type="{}ReportAggregateReference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="blockId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="joinTable" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportBlockInfo", propOrder = {
    "aggregateReferences",
    "blockId",
    "joinTable"
})
public class ReportBlockInfo {

    protected List<ReportAggregateReference> aggregateReferences;
    @XmlElement(required = true)
    protected String blockId;
    @XmlElement(required = true)
    protected String joinTable;

    /**
     * Gets the value of the aggregateReferences property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aggregateReferences property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAggregateReferences().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReportAggregateReference }
     * 
     * 
     */
    public List<ReportAggregateReference> getAggregateReferences() {
        if (aggregateReferences == null) {
            aggregateReferences = new ArrayList<ReportAggregateReference>();
        }
        return this.aggregateReferences;
    }

    /**
     * Gets the value of the blockId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlockId() {
        return blockId;
    }

    /**
     * Sets the value of the blockId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlockId(String value) {
        this.blockId = value;
    }

    /**
     * Gets the value of the joinTable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJoinTable() {
        return joinTable;
    }

    /**
     * Sets the value of the joinTable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJoinTable(String value) {
        this.joinTable = value;
    }

}
