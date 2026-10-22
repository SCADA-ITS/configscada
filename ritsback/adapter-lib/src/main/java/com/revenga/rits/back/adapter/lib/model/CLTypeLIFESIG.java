
package com.revenga.rits.back.adapter.lib.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CL_Type_LIFESIG complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CL_Type_LIFESIG"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="PeriodSec" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
 *       &lt;attribute name="TimeOutSec" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CL_Type_LIFESIG")
public class CLTypeLIFESIG {

    @XmlAttribute(name = "PeriodSec", required = true)
    @XmlSchemaType(name = "unsignedByte")
    protected short periodSec;
    @XmlAttribute(name = "TimeOutSec", required = true)
    @XmlSchemaType(name = "unsignedByte")
    protected short timeOutSec;

    /**
     * Obtiene el valor de la propiedad periodSec.
     * 
     */
    public short getPeriodSec() {
        return periodSec;
    }

    /**
     * Define el valor de la propiedad periodSec.
     * 
     */
    public void setPeriodSec(short value) {
        this.periodSec = value;
    }

    /**
     * Obtiene el valor de la propiedad timeOutSec.
     * 
     */
    public short getTimeOutSec() {
        return timeOutSec;
    }

    /**
     * Define el valor de la propiedad timeOutSec.
     * 
     */
    public void setTimeOutSec(short value) {
        this.timeOutSec = value;
    }

}
