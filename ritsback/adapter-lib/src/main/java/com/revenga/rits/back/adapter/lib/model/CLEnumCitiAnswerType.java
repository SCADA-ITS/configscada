
package com.revenga.rits.back.adapter.lib.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CL_Enum_CitiAnswerType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="CL_Enum_CitiAnswerType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Alarm"/&gt;
 *     &lt;enumeration value="Device"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CL_Enum_CitiAnswerType", namespace = "Citilog-CommonTypes")
@XmlEnum
public enum CLEnumCitiAnswerType {

    @XmlEnumValue("Alarm")
    ALARM("Alarm"),
    @XmlEnumValue("Device")
    DEVICE("Device");
    private final String value;

    CLEnumCitiAnswerType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CLEnumCitiAnswerType fromValue(String v) {
        for (CLEnumCitiAnswerType c: CLEnumCitiAnswerType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
