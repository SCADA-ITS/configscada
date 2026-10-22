
package com.revenga.rits.back.adapter.lib.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CL_Enum_RequestType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="CL_Enum_RequestType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="alarms"/&gt;
 *     &lt;enumeration value="devices"/&gt;
 *     &lt;enumeration value="keepAlive"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CL_Enum_RequestType", namespace = "Citilog-CommonTypes")
@XmlEnum
public enum CLEnumRequestType {

    @XmlEnumValue("alarms")
    ALARMS("alarms"),
    @XmlEnumValue("devices")
    DEVICES("devices"),
    @XmlEnumValue("keepAlive")
    KEEP_ALIVE("keepAlive");
    private final String value;

    CLEnumRequestType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CLEnumRequestType fromValue(String v) {
        for (CLEnumRequestType c: CLEnumRequestType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
