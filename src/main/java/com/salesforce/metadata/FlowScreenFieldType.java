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
 * <p>Java class for FlowScreenFieldType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FlowScreenFieldType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DisplayText"/>
 *     &lt;enumeration value="InputField"/>
 *     &lt;enumeration value="LargeTextArea"/>
 *     &lt;enumeration value="PasswordField"/>
 *     &lt;enumeration value="RadioButtons"/>
 *     &lt;enumeration value="DropdownBox"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FlowScreenFieldType")
@XmlEnum
public enum FlowScreenFieldType {

    @XmlEnumValue("DisplayText")
    DISPLAY_TEXT("DisplayText"),
    @XmlEnumValue("InputField")
    INPUT_FIELD("InputField"),
    @XmlEnumValue("LargeTextArea")
    LARGE_TEXT_AREA("LargeTextArea"),
    @XmlEnumValue("PasswordField")
    PASSWORD_FIELD("PasswordField"),
    @XmlEnumValue("RadioButtons")
    RADIO_BUTTONS("RadioButtons"),
    @XmlEnumValue("DropdownBox")
    DROPDOWN_BOX("DropdownBox");
    private final String value;

    FlowScreenFieldType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FlowScreenFieldType fromValue(String v) {
        for (FlowScreenFieldType c: FlowScreenFieldType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
