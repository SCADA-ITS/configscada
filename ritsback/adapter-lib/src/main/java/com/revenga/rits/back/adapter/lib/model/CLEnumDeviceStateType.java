
package com.revenga.rits.back.adapter.lib.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CL_Enum_DeviceStateType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="CL_Enum_DeviceStateType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CommError"/&gt;
 *     &lt;enumeration value="OK"/&gt;
 *     &lt;enumeration value="NOK"/&gt;
 *     &lt;enumeration value="VideoError"/&gt;
 *     &lt;enumeration value="Warning"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CL_Enum_DeviceStateType", namespace = "Citilog-CommonTypes")
@XmlEnum
public enum CLEnumDeviceStateType {

    @XmlEnumValue("CommError")
    COMM_ERROR("CommError"),
    OK("OK"),
    NOK("NOK"),
    @XmlEnumValue("VideoError")
    VIDEO_ERROR("VideoError"),
    @XmlEnumValue("Warning")
    WARNING("Warning");
    private final String value;

    CLEnumDeviceStateType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CLEnumDeviceStateType fromValue(String v) {
        for (CLEnumDeviceStateType c: CLEnumDeviceStateType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
