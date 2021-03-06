//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.24 at 12:06:48 PM PDT 
//


package com.salesforce.metadata;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WebLinkType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="WebLinkType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="url"/>
 *     &lt;enumeration value="sControl"/>
 *     &lt;enumeration value="javascript"/>
 *     &lt;enumeration value="page"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "WebLinkType")
@XmlEnum
public enum WebLinkType {

    @XmlEnumValue("url")
    URL("url"),
    @XmlEnumValue("sControl")
    S_CONTROL("sControl"),
    @XmlEnumValue("javascript")
    JAVASCRIPT("javascript"),
    @XmlEnumValue("page")
    PAGE("page");
    private final String value;

    WebLinkType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WebLinkType fromValue(String v) {
        for (WebLinkType c: WebLinkType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
