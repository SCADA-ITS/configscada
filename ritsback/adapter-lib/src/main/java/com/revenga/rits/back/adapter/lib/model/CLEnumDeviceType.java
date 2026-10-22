
package com.revenga.rits.back.adapter.lib.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CL_Enum_DeviceType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="CL_Enum_DeviceType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Analyzer"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CL_Enum_DeviceType", namespace = "Citilog-CommonTypes")
@XmlEnum
public enum CLEnumDeviceType {

    @XmlEnumValue("Analyzer")
    ANALYZER("Analyzer");
    private final String value;

    CLEnumDeviceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CLEnumDeviceType fromValue(String v) {
        for (CLEnumDeviceType c: CLEnumDeviceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
