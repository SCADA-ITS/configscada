
package com.revenga.rits.back.adapter.lib.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Device" type="{}CL_Type_Device" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Alarm" type="{}CL_Type_Alarm" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="LIFESIG" type="{}CL_Type_LIFESIG" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Type" use="required" type="{Citilog-CommonTypes}CL_EnumCitiEventType" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "device",
    "alarm",
    "lifesig"
})
@XmlRootElement(name = "CitiEvent")
public class CitiEvent {

    @XmlElement(name = "Device")
    protected List<CLTypeDevice> device;
    @XmlElement(name = "Alarm")
    protected List<CLTypeAlarm> alarm;
    @XmlElement(name = "LIFESIG")
    protected List<CLTypeLIFESIG> lifesig;
    @XmlAttribute(name = "Type", required = true)
    protected CLEnumCitiEventType type;

    /**
     * Gets the value of the device property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the device property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDevice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CLTypeDevice }
     * 
     * 
     */
    public List<CLTypeDevice> getDevice() {
        if (device == null) {
            device = new ArrayList<CLTypeDevice>();
        }
        return this.device;
    }

    /**
     * Gets the value of the alarm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alarm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlarm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CLTypeAlarm }
     * 
     * 
     */
    public List<CLTypeAlarm> getAlarm() {
        if (alarm == null) {
            alarm = new ArrayList<CLTypeAlarm>();
        }
        return this.alarm;
    }

    /**
     * Gets the value of the lifesig property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lifesig property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLIFESIG().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CLTypeLIFESIG }
     * 
     * 
     */
    public List<CLTypeLIFESIG> getLIFESIG() {
        if (lifesig == null) {
            lifesig = new ArrayList<CLTypeLIFESIG>();
        }
        return this.lifesig;
    }

    /**
     * Obtiene el valor de la propiedad type.
     * 
     * @return
     *     possible object is
     *     {@link CLEnumCitiEventType }
     *     
     */
    public CLEnumCitiEventType getType() {
        return type;
    }

    /**
     * Define el valor de la propiedad type.
     * 
     * @param value
     *     allowed object is
     *     {@link CLEnumCitiEventType }
     *     
     */
    public void setType(CLEnumCitiEventType value) {
        this.type = value;
    }

}
