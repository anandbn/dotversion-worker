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
 * <p>Java class for CampaignSharingRules complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CampaignSharingRules">
 *   &lt;complexContent>
 *     &lt;extension base="{}SharingRules">
 *       &lt;sequence>
 *         &lt;element name="criteriaBasedRules" type="{}CampaignCriteriaBasedSharingRule" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ownerRules" type="{}CampaignOwnerSharingRule" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CampaignSharingRules", propOrder = {
    "criteriaBasedRules",
    "ownerRules"
})
public class CampaignSharingRules
    extends SharingRules
{

    protected List<CampaignCriteriaBasedSharingRule> criteriaBasedRules;
    protected List<CampaignOwnerSharingRule> ownerRules;

    /**
     * Gets the value of the criteriaBasedRules property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the criteriaBasedRules property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCriteriaBasedRules().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CampaignCriteriaBasedSharingRule }
     * 
     * 
     */
    public List<CampaignCriteriaBasedSharingRule> getCriteriaBasedRules() {
        if (criteriaBasedRules == null) {
            criteriaBasedRules = new ArrayList<CampaignCriteriaBasedSharingRule>();
        }
        return this.criteriaBasedRules;
    }

    /**
     * Gets the value of the ownerRules property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ownerRules property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOwnerRules().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CampaignOwnerSharingRule }
     * 
     * 
     */
    public List<CampaignOwnerSharingRule> getOwnerRules() {
        if (ownerRules == null) {
            ownerRules = new ArrayList<CampaignOwnerSharingRule>();
        }
        return this.ownerRules;
    }

}