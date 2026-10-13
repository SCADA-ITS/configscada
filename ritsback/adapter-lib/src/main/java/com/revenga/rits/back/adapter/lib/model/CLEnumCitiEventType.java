
package com.revenga.rits.back.adapter.lib.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CL_EnumCitiEventType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="CL_EnumCitiEventType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Alarm"/&gt;
 *     &lt;enumeration value="Device"/&gt;
 *     &lt;enumeration value="LIFESIG"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CL_EnumCitiEventType", namespace = "Citilog-CommonTypes")
@XmlEnum
public enum CLEnumCitiEventType {

    @XmlEnumValue("Alarm")
    ALARM("Alarm"),
    @XmlEnumValue("Device")
    DEVICE("Device"),
    LIFESIG("LIFESIG");
    private final String value;

    CLEnumCitiEventType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CLEnumCitiEventType fromValue(String v) {
        for (CLEnumCitiEventType c: CLEnumCitiEventType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
