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
 * <p>Java class for HomePageLayout complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HomePageLayout">
 *   &lt;complexContent>
 *     &lt;extension base="{}Metadata">
 *       &lt;sequence>
 *         &lt;element name="narrowComponents" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="wideComponents" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name="HomePageLayout")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HomePageLayout", propOrder = {
    "narrowComponents",
    "wideComponents"
})
public class HomePageLayout
    extends Metadata
{

    protected List<String> narrowComponents;
    protected List<String> wideComponents;

    /**
     * Gets the value of the narrowComponents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the narrowComponents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNarrowComponents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNarrowComponents() {
        if (narrowComponents == null) {
            narrowComponents = new ArrayList<String>();
        }
        return this.narrowComponents;
    }

    /**
     * Gets the value of the wideComponents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the wideComponents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWideComponents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getWideComponents() {
        if (wideComponents == null) {
            wideComponents = new ArrayList<String>();
        }
        return this.wideComponents;
    }

}
