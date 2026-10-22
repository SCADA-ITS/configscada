
package com.revenga.rits.back.adapter.lib.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


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
 *         &lt;element name="camera_uid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="element_type_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *                 &lt;attribute name="element_type_param_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *                 &lt;attribute name="param_type_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="camera_alarms" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="camera_alarm" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="alarm" use="required" type="{Citilog-CommonTypes}CL_Enum_AlarmType" /&gt;
 *                           &lt;attribute name="lane_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *                           &lt;attribute name="alarm_config_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="dai_uid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="element_type_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *                 &lt;attribute name="element_type_param_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *                 &lt;attribute name="param_type_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="dai_alarms" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="dai_alarm" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="type" use="required" type="{Citilog-CommonTypes}CL_Enum_DeviceType" /&gt;
 *                           &lt;attribute name="state" use="required" type="{Citilog-CommonTypes}CL_Enum_DeviceStateType" /&gt;
 *                           &lt;attribute name="alarm_config_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="dai_states" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="dai_state" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="type" use="required" type="{Citilog-CommonTypes}CL_Enum_DeviceType" /&gt;
 *                           &lt;attribute name="state" use="required" type="{Citilog-CommonTypes}CL_Enum_DeviceStateType" /&gt;
 *                           &lt;attribute name="element_state_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="requests"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="request" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;simpleContent&gt;
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                           &lt;attribute name="type" use="required" type="{Citilog-CommonTypes}CL_Enum_RequestType" /&gt;
 *                         &lt;/extension&gt;
 *                       &lt;/simpleContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cameraUid",
    "cameraAlarms",
    "daiUid",
    "daiAlarms",
    "daiStates",
    "requests"
})
@XmlRootElement(name = "citilog_config_v1", namespace = "citilog_config_v1")
public class CitilogConfigV1 {

    @XmlElement(name = "camera_uid", namespace = "citilog_config_v1", required = true)
    protected CitilogConfigV1 .CameraUid cameraUid;
    @XmlElement(name = "camera_alarms", namespace = "citilog_config_v1")
    protected CitilogConfigV1 .CameraAlarms cameraAlarms;
    @XmlElement(name = "dai_uid", namespace = "citilog_config_v1", required = true)
    protected CitilogConfigV1 .DaiUid daiUid;
    @XmlElement(name = "dai_alarms", namespace = "citilog_config_v1")
    protected CitilogConfigV1 .DaiAlarms daiAlarms;
    @XmlElement(name = "dai_states", namespace = "citilog_config_v1")
    protected CitilogConfigV1 .DaiStates daiStates;
    @XmlElement(namespace = "citilog_config_v1", required = true)
    protected CitilogConfigV1 .Requests requests;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * Obtiene el valor de la propiedad cameraUid.
     * 
     * @return
     *     possible object is
     *     {@link CitilogConfigV1 .CameraUid }
     *     
     */
    public CitilogConfigV1 .CameraUid getCameraUid() {
        return cameraUid;
    }

    /**
     * Define el valor de la propiedad cameraUid.
     * 
     * @param value
     *     allowed object is
     *     {@link CitilogConfigV1 .CameraUid }
     *     
     */
    public void setCameraUid(CitilogConfigV1 .CameraUid value) {
        this.cameraUid = value;
    }

    /**
     * Obtiene el valor de la propiedad cameraAlarms.
     * 
     * @return
     *     possible object is
     *     {@link CitilogConfigV1 .CameraAlarms }
     *     
     */
    public CitilogConfigV1 .CameraAlarms getCameraAlarms() {
        return cameraAlarms;
    }

    /**
     * Define el valor de la propiedad cameraAlarms.
     * 
     * @param value
     *     allowed object is
     *     {@link CitilogConfigV1 .CameraAlarms }
     *     
     */
    public void setCameraAlarms(CitilogConfigV1 .CameraAlarms value) {
        this.cameraAlarms = value;
    }

    /**
     * Obtiene el valor de la propiedad daiUid.
     * 
     * @return
     *     possible object is
     *     {@link CitilogConfigV1 .DaiUid }
     *     
     */
    public CitilogConfigV1 .DaiUid getDaiUid() {
        return daiUid;
    }

    /**
     * Define el valor de la propiedad daiUid.
     * 
     * @param value
     *     allowed object is
     *     {@link CitilogConfigV1 .DaiUid }
     *     
     */
    public void setDaiUid(CitilogConfigV1 .DaiUid value) {
        this.daiUid = value;
    }

    /**
     * Obtiene el valor de la propiedad daiAlarms.
     * 
     * @return
     *     possible object is
     *     {@link CitilogConfigV1 .DaiAlarms }
     *     
     */
    public CitilogConfigV1 .DaiAlarms getDaiAlarms() {
        return daiAlarms;
    }

    /**
     * Define el valor de la propiedad daiAlarms.
     * 
     * @param value
     *     allowed object is
     *     {@link CitilogConfigV1 .DaiAlarms }
     *     
     */
    public void setDaiAlarms(CitilogConfigV1 .DaiAlarms value) {
        this.daiAlarms = value;
    }

    /**
     * Obtiene el valor de la propiedad daiStates.
     * 
     * @return
     *     possible object is
     *     {@link CitilogConfigV1 .DaiStates }
     *     
     */
    public CitilogConfigV1 .DaiStates getDaiStates() {
        return daiStates;
    }

    /**
     * Define el valor de la propiedad daiStates.
     * 
     * @param value
     *     allowed object is
     *     {@link CitilogConfigV1 .DaiStates }
     *     
     */
    public void setDaiStates(CitilogConfigV1 .DaiStates value) {
        this.daiStates = value;
    }

    /**
     * Obtiene el valor de la propiedad requests.
     * 
     * @return
     *     possible object is
     *     {@link CitilogConfigV1 .Requests }
     *     
     */
    public CitilogConfigV1 .Requests getRequests() {
        return requests;
    }

    /**
     * Define el valor de la propiedad requests.
     * 
     * @param value
     *     allowed object is
     *     {@link CitilogConfigV1 .Requests }
     *     
     */
    public void setRequests(CitilogConfigV1 .Requests value) {
        this.requests = value;
    }

    /**
     * Obtiene el valor de la propiedad name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Define el valor de la propiedad name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtiene el valor de la propiedad version.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Define el valor de la propiedad version.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }


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
     *         &lt;element name="camera_alarm" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="alarm" use="required" type="{Citilog-CommonTypes}CL_Enum_AlarmType" /&gt;
     *                 &lt;attribute name="lane_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *                 &lt;attribute name="alarm_config_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "cameraAlarm"
    })
    public static class CameraAlarms {

        @XmlElement(name = "camera_alarm", namespace = "citilog_config_v1", required = true)
        protected List<CitilogConfigV1 .CameraAlarms.CameraAlarm> cameraAlarm;

        /**
         * Gets the value of the cameraAlarm property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the cameraAlarm property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCameraAlarm().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CitilogConfigV1 .CameraAlarms.CameraAlarm }
         * 
         * 
         */
        public List<CitilogConfigV1 .CameraAlarms.CameraAlarm> getCameraAlarm() {
            if (cameraAlarm == null) {
                cameraAlarm = new ArrayList<CitilogConfigV1 .CameraAlarms.CameraAlarm>();
            }
            return this.cameraAlarm;
        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="alarm" use="required" type="{Citilog-CommonTypes}CL_Enum_AlarmType" /&gt;
         *       &lt;attribute name="lane_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
         *       &lt;attribute name="alarm_config_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class CameraAlarm {

            @XmlAttribute(name = "alarm", required = true)
            protected CLEnumAlarmType alarm;
            @XmlAttribute(name = "lane_id", required = true)
            protected long laneId;
            @XmlAttribute(name = "alarm_config_id", required = true)
            protected long alarmConfigId;

            /**
             * Obtiene el valor de la propiedad alarm.
             * 
             * @return
             *     possible object is
             *     {@link CLEnumAlarmType }
             *     
             */
            public CLEnumAlarmType getAlarm() {
                return alarm;
            }

            /**
             * Define el valor de la propiedad alarm.
             * 
             * @param value
             *     allowed object is
             *     {@link CLEnumAlarmType }
             *     
             */
            public void setAlarm(CLEnumAlarmType value) {
                this.alarm = value;
            }

            /**
             * Obtiene el valor de la propiedad laneId.
             * 
             */
            public long getLaneId() {
                return laneId;
            }

            /**
             * Define el valor de la propiedad laneId.
             * 
             */
            public void setLaneId(long value) {
                this.laneId = value;
            }

            /**
             * Obtiene el valor de la propiedad alarmConfigId.
             * 
             */
            public long getAlarmConfigId() {
                return alarmConfigId;
            }

            /**
             * Define el valor de la propiedad alarmConfigId.
             * 
             */
            public void setAlarmConfigId(long value) {
                this.alarmConfigId = value;
            }

        }

    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="element_type_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *       &lt;attribute name="element_type_param_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *       &lt;attribute name="param_type_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class CameraUid {

        @XmlAttribute(name = "element_type_id", required = true)
        protected long elementTypeId;
        @XmlAttribute(name = "element_type_param_id", required = true)
        protected long elementTypeParamId;
        @XmlAttribute(name = "param_type_id", required = true)
        protected long paramTypeId;

        /**
         * Obtiene el valor de la propiedad elementTypeId.
         * 
         */
        public long getElementTypeId() {
            return elementTypeId;
        }

        /**
         * Define el valor de la propiedad elementTypeId.
         * 
         */
        public void setElementTypeId(long value) {
            this.elementTypeId = value;
        }

        /**
         * Obtiene el valor de la propiedad elementTypeParamId.
         * 
         */
        public long getElementTypeParamId() {
            return elementTypeParamId;
        }

        /**
         * Define el valor de la propiedad elementTypeParamId.
         * 
         */
        public void setElementTypeParamId(long value) {
            this.elementTypeParamId = value;
        }

        /**
         * Obtiene el valor de la propiedad paramTypeId.
         * 
         */
        public long getParamTypeId() {
            return paramTypeId;
        }

        /**
         * Define el valor de la propiedad paramTypeId.
         * 
         */
        public void setParamTypeId(long value) {
            this.paramTypeId = value;
        }

    }


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
     *         &lt;element name="dai_alarm" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="type" use="required" type="{Citilog-CommonTypes}CL_Enum_DeviceType" /&gt;
     *                 &lt;attribute name="state" use="required" type="{Citilog-CommonTypes}CL_Enum_DeviceStateType" /&gt;
     *                 &lt;attribute name="alarm_config_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "daiAlarm"
    })
    public static class DaiAlarms {

        @XmlElement(name = "dai_alarm", namespace = "citilog_config_v1", required = true)
        protected List<CitilogConfigV1 .DaiAlarms.DaiAlarm> daiAlarm;

        /**
         * Gets the value of the daiAlarm property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the daiAlarm property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDaiAlarm().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CitilogConfigV1 .DaiAlarms.DaiAlarm }
         * 
         * 
         */
        public List<CitilogConfigV1 .DaiAlarms.DaiAlarm> getDaiAlarm() {
            if (daiAlarm == null) {
                daiAlarm = new ArrayList<CitilogConfigV1 .DaiAlarms.DaiAlarm>();
            }
            return this.daiAlarm;
        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="type" use="required" type="{Citilog-CommonTypes}CL_Enum_DeviceType" /&gt;
         *       &lt;attribute name="state" use="required" type="{Citilog-CommonTypes}CL_Enum_DeviceStateType" /&gt;
         *       &lt;attribute name="alarm_config_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class DaiAlarm {

            @XmlAttribute(name = "type", required = true)
            protected CLEnumDeviceType type;
            @XmlAttribute(name = "state", required = true)
            protected CLEnumDeviceStateType state;
            @XmlAttribute(name = "alarm_config_id", required = true)
            protected long alarmConfigId;

            /**
             * Obtiene el valor de la propiedad type.
             * 
             * @return
             *     possible object is
             *     {@link CLEnumDeviceType }
             *     
             */
            public CLEnumDeviceType getType() {
                return type;
            }

            /**
             * Define el valor de la propiedad type.
             * 
             * @param value
             *     allowed object is
             *     {@link CLEnumDeviceType }
             *     
             */
            public void setType(CLEnumDeviceType value) {
                this.type = value;
            }

            /**
             * Obtiene el valor de la propiedad state.
             * 
             * @return
             *     possible object is
             *     {@link CLEnumDeviceStateType }
             *     
             */
            public CLEnumDeviceStateType getState() {
                return state;
            }

            /**
             * Define el valor de la propiedad state.
             * 
             * @param value
             *     allowed object is
             *     {@link CLEnumDeviceStateType }
             *     
             */
            public void setState(CLEnumDeviceStateType value) {
                this.state = value;
            }

            /**
             * Obtiene el valor de la propiedad alarmConfigId.
             * 
             */
            public long getAlarmConfigId() {
                return alarmConfigId;
            }

            /**
             * Define el valor de la propiedad alarmConfigId.
             * 
             */
            public void setAlarmConfigId(long value) {
                this.alarmConfigId = value;
            }

        }

    }


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
     *         &lt;element name="dai_state" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="type" use="required" type="{Citilog-CommonTypes}CL_Enum_DeviceType" /&gt;
     *                 &lt;attribute name="state" use="required" type="{Citilog-CommonTypes}CL_Enum_DeviceStateType" /&gt;
     *                 &lt;attribute name="element_state_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "daiState"
    })
    public static class DaiStates {

        @XmlElement(name = "dai_state", namespace = "citilog_config_v1", required = true)
        protected List<CitilogConfigV1 .DaiStates.DaiState> daiState;

        /**
         * Gets the value of the daiState property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the daiState property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDaiState().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CitilogConfigV1 .DaiStates.DaiState }
         * 
         * 
         */
        public List<CitilogConfigV1 .DaiStates.DaiState> getDaiState() {
            if (daiState == null) {
                daiState = new ArrayList<CitilogConfigV1 .DaiStates.DaiState>();
            }
            return this.daiState;
        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;attribute name="type" use="required" type="{Citilog-CommonTypes}CL_Enum_DeviceType" /&gt;
         *       &lt;attribute name="state" use="required" type="{Citilog-CommonTypes}CL_Enum_DeviceStateType" /&gt;
         *       &lt;attribute name="element_state_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class DaiState {

            @XmlAttribute(name = "type", required = true)
            protected CLEnumDeviceType type;
            @XmlAttribute(name = "state", required = true)
            protected CLEnumDeviceStateType state;
            @XmlAttribute(name = "element_state_id", required = true)
            protected long elementStateId;

            /**
             * Obtiene el valor de la propiedad type.
             * 
             * @return
             *     possible object is
             *     {@link CLEnumDeviceType }
             *     
             */
            public CLEnumDeviceType getType() {
                return type;
            }

            /**
             * Define el valor de la propiedad type.
             * 
             * @param value
             *     allowed object is
             *     {@link CLEnumDeviceType }
             *     
             */
            public void setType(CLEnumDeviceType value) {
                this.type = value;
            }

            /**
             * Obtiene el valor de la propiedad state.
             * 
             * @return
             *     possible object is
             *     {@link CLEnumDeviceStateType }
             *     
             */
            public CLEnumDeviceStateType getState() {
                return state;
            }

            /**
             * Define el valor de la propiedad state.
             * 
             * @param value
             *     allowed object is
             *     {@link CLEnumDeviceStateType }
             *     
             */
            public void setState(CLEnumDeviceStateType value) {
                this.state = value;
            }

            /**
             * Obtiene el valor de la propiedad elementStateId.
             * 
             */
            public long getElementStateId() {
                return elementStateId;
            }

            /**
             * Define el valor de la propiedad elementStateId.
             * 
             */
            public void setElementStateId(long value) {
                this.elementStateId = value;
            }

        }

    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="element_type_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *       &lt;attribute name="element_type_param_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *       &lt;attribute name="param_type_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class DaiUid {

        @XmlAttribute(name = "element_type_id", required = true)
        protected long elementTypeId;
        @XmlAttribute(name = "element_type_param_id", required = true)
        protected long elementTypeParamId;
        @XmlAttribute(name = "param_type_id", required = true)
        protected long paramTypeId;

        /**
         * Obtiene el valor de la propiedad elementTypeId.
         * 
         */
        public long getElementTypeId() {
            return elementTypeId;
        }

        /**
         * Define el valor de la propiedad elementTypeId.
         * 
         */
        public void setElementTypeId(long value) {
            this.elementTypeId = value;
        }

        /**
         * Obtiene el valor de la propiedad elementTypeParamId.
         * 
         */
        public long getElementTypeParamId() {
            return elementTypeParamId;
        }

        /**
         * Define el valor de la propiedad elementTypeParamId.
         * 
         */
        public void setElementTypeParamId(long value) {
            this.elementTypeParamId = value;
        }

        /**
         * Obtiene el valor de la propiedad paramTypeId.
         * 
         */
        public long getParamTypeId() {
            return paramTypeId;
        }

        /**
         * Define el valor de la propiedad paramTypeId.
         * 
         */
        public void setParamTypeId(long value) {
            this.paramTypeId = value;
        }

    }


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
     *         &lt;element name="request" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;simpleContent&gt;
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *                 &lt;attribute name="type" use="required" type="{Citilog-CommonTypes}CL_Enum_RequestType" /&gt;
     *               &lt;/extension&gt;
     *             &lt;/simpleContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "request"
    })
    public static class Requests {

        @XmlElement(namespace = "citilog_config_v1", required = true)
        protected List<CitilogConfigV1 .Requests.Request> request;

        /**
         * Gets the value of the request property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the request property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRequest().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CitilogConfigV1 .Requests.Request }
         * 
         * 
         */
        public List<CitilogConfigV1 .Requests.Request> getRequest() {
            if (request == null) {
                request = new ArrayList<CitilogConfigV1 .Requests.Request>();
            }
            return this.request;
        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;simpleContent&gt;
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
         *       &lt;attribute name="type" use="required" type="{Citilog-CommonTypes}CL_Enum_RequestType" /&gt;
         *     &lt;/extension&gt;
         *   &lt;/simpleContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Request {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "type", required = true)
            protected CLEnumRequestType type;

            /**
             * Obtiene el valor de la propiedad value.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Define el valor de la propiedad value.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Obtiene el valor de la propiedad type.
             * 
             * @return
             *     possible object is
             *     {@link CLEnumRequestType }
             *     
             */
            public CLEnumRequestType getType() {
                return type;
            }

            /**
             * Define el valor de la propiedad type.
             * 
             * @param value
             *     allowed object is
             *     {@link CLEnumRequestType }
             *     
             */
            public void setType(CLEnumRequestType value) {
                this.type = value;
            }

        }

    }

}
