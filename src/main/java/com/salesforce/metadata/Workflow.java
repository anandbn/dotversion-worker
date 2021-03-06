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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Workflow complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Workflow">
 *   &lt;complexContent>
 *     &lt;extension base="{}Metadata">
 *       &lt;sequence>
 *         &lt;element name="alerts" type="{}WorkflowAlert" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="fieldUpdates" type="{}WorkflowFieldUpdate" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="knowledgePublishes" type="{}WorkflowKnowledgePublish" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="outboundMessages" type="{}WorkflowOutboundMessage" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="rules" type="{}WorkflowRule" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tasks" type="{}WorkflowTask" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name="Workflow")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Workflow", propOrder = {
    "alerts",
    "fieldUpdates",
    "knowledgePublishes",
    "outboundMessages",
    "rules",
    "tasks"
})
public class Workflow
    extends Metadata
{

    protected List<WorkflowAlert> alerts;
    protected List<WorkflowFieldUpdate> fieldUpdates;
    protected List<WorkflowKnowledgePublish> knowledgePublishes;
    protected List<WorkflowOutboundMessage> outboundMessages;
    protected List<WorkflowRule> rules;
    protected List<WorkflowTask> tasks;

    /**
     * Gets the value of the alerts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alerts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlerts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WorkflowAlert }
     * 
     * 
     */
    public List<WorkflowAlert> getAlerts() {
        if (alerts == null) {
            alerts = new ArrayList<WorkflowAlert>();
        }
        return this.alerts;
    }

    /**
     * Gets the value of the fieldUpdates property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fieldUpdates property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFieldUpdates().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WorkflowFieldUpdate }
     * 
     * 
     */
    public List<WorkflowFieldUpdate> getFieldUpdates() {
        if (fieldUpdates == null) {
            fieldUpdates = new ArrayList<WorkflowFieldUpdate>();
        }
        return this.fieldUpdates;
    }

    /**
     * Gets the value of the knowledgePublishes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the knowledgePublishes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKnowledgePublishes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WorkflowKnowledgePublish }
     * 
     * 
     */
    public List<WorkflowKnowledgePublish> getKnowledgePublishes() {
        if (knowledgePublishes == null) {
            knowledgePublishes = new ArrayList<WorkflowKnowledgePublish>();
        }
        return this.knowledgePublishes;
    }

    /**
     * Gets the value of the outboundMessages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outboundMessages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutboundMessages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WorkflowOutboundMessage }
     * 
     * 
     */
    public List<WorkflowOutboundMessage> getOutboundMessages() {
        if (outboundMessages == null) {
            outboundMessages = new ArrayList<WorkflowOutboundMessage>();
        }
        return this.outboundMessages;
    }

    /**
     * Gets the value of the rules property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rules property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRules().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WorkflowRule }
     * 
     * 
     */
    public List<WorkflowRule> getRules() {
        if (rules == null) {
            rules = new ArrayList<WorkflowRule>();
        }
        return this.rules;
    }

    /**
     * Gets the value of the tasks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tasks property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTasks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WorkflowTask }
     * 
     * 
     */
    public List<WorkflowTask> getTasks() {
        if (tasks == null) {
            tasks = new ArrayList<WorkflowTask>();
        }
        return this.tasks;
    }

}
