
package com.revenga.rits.back.adapter.lib.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CL_Enum_AlarmType.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="CL_Enum_AlarmType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="StopF"/&gt;
 *     &lt;enumeration value="SlowVeh"/&gt;
 *     &lt;enumeration value="Debris"/&gt;
 *     &lt;enumeration value="Pedestrian"/&gt;
 *     &lt;enumeration value="Visibility"/&gt;
 *     &lt;enumeration value="SlowDown"/&gt;
 *     &lt;enumeration value="WrongWay"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CL_Enum_AlarmType", namespace = "Citilog-CommonTypes")
@XmlEnum
public enum CLEnumAlarmType {

    @XmlEnumValue("StopF")
    STOP_F("StopF"),
    @XmlEnumValue("SlowVeh")
    SLOW_VEH("SlowVeh"),
    @XmlEnumValue("Debris")
    DEBRIS("Debris"),
    @XmlEnumValue("Pedestrian")
    PEDESTRIAN("Pedestrian"),
    @XmlEnumValue("Visibility")
    VISIBILITY("Visibility"),
    @XmlEnumValue("SlowDown")
    SLOW_DOWN("SlowDown"),
    @XmlEnumValue("WrongWay")
    WRONG_WAY("WrongWay");
    private final String value;

    CLEnumAlarmType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CLEnumAlarmType fromValue(String v) {
        for (CLEnumAlarmType c: CLEnumAlarmType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
