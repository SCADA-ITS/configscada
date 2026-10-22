
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
 *         &lt;element name="groovy_adapter" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="file" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="session_uid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="element_type_param_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *                 &lt;attribute name="param_type_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="state"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="state_value" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="source" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *                           &lt;attribute name="input" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *                           &lt;attribute name="disconnected" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="field" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="fields" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="field" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="input" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="element_type_param_id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *                                       &lt;element name="param_type_id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="use_adapter" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="alarms" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="alarm" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="source" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="active_value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="element_type_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *       &lt;attribute name="array_element_id" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "groovyAdapter",
    "sessionUid",
    "state",
    "fields",
    "alarms"
})
@XmlRootElement(name = "json_generic_mango_v1", namespace = "json_generic_mango_v1")
public class JsonGenericMangoV1 {

    @XmlElement(name = "groovy_adapter", namespace = "json_generic_mango_v1")
    protected JsonGenericMangoV1 .GroovyAdapter groovyAdapter;
    @XmlElement(name = "session_uid", namespace = "json_generic_mango_v1", required = true)
    protected JsonGenericMangoV1 .SessionUid sessionUid;
    @XmlElement(namespace = "json_generic_mango_v1", required = true)
    protected JsonGenericMangoV1 .State state;
    @XmlElement(namespace = "json_generic_mango_v1")
    protected JsonGenericMangoV1 .Fields fields;
    @XmlElement(namespace = "json_generic_mango_v1")
    protected JsonGenericMangoV1 .Alarms alarms;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "version", required = true)
    protected String version;
    @XmlAttribute(name = "element_type_id", required = true)
    protected long elementTypeId;
    @XmlAttribute(name = "array_element_id")
    protected String arrayElementId;

    /**
     * Obtiene el valor de la propiedad groovyAdapter.
     * 
     * @return
     *     possible object is
     *     {@link JsonGenericMangoV1 .GroovyAdapter }
     *     
     */
    public JsonGenericMangoV1 .GroovyAdapter getGroovyAdapter() {
        return groovyAdapter;
    }

    /**
     * Define el valor de la propiedad groovyAdapter.
     * 
     * @param value
     *     allowed object is
     *     {@link JsonGenericMangoV1 .GroovyAdapter }
     *     
     */
    public void setGroovyAdapter(JsonGenericMangoV1 .GroovyAdapter value) {
        this.groovyAdapter = value;
    }

    /**
     * Obtiene el valor de la propiedad sessionUid.
     * 
     * @return
     *     possible object is
     *     {@link JsonGenericMangoV1 .SessionUid }
     *     
     */
    public JsonGenericMangoV1 .SessionUid getSessionUid() {
        return sessionUid;
    }

    /**
     * Define el valor de la propiedad sessionUid.
     * 
     * @param value
     *     allowed object is
     *     {@link JsonGenericMangoV1 .SessionUid }
     *     
     */
    public void setSessionUid(JsonGenericMangoV1 .SessionUid value) {
        this.sessionUid = value;
    }

    /**
     * Obtiene el valor de la propiedad state.
     * 
     * @return
     *     possible object is
     *     {@link JsonGenericMangoV1 .State }
     *     
     */
    public JsonGenericMangoV1 .State getState() {
        return state;
    }

    /**
     * Define el valor de la propiedad state.
     * 
     * @param value
     *     allowed object is
     *     {@link JsonGenericMangoV1 .State }
     *     
     */
    public void setState(JsonGenericMangoV1 .State value) {
        this.state = value;
    }

    /**
     * Obtiene el valor de la propiedad fields.
     * 
     * @return
     *     possible object is
     *     {@link JsonGenericMangoV1 .Fields }
     *     
     */
    public JsonGenericMangoV1 .Fields getFields() {
        return fields;
    }

    /**
     * Define el valor de la propiedad fields.
     * 
     * @param value
     *     allowed object is
     *     {@link JsonGenericMangoV1 .Fields }
     *     
     */
    public void setFields(JsonGenericMangoV1 .Fields value) {
        this.fields = value;
    }

    /**
     * Obtiene el valor de la propiedad alarms.
     * 
     * @return
     *     possible object is
     *     {@link JsonGenericMangoV1 .Alarms }
     *     
     */
    public JsonGenericMangoV1 .Alarms getAlarms() {
        return alarms;
    }

    /**
     * Define el valor de la propiedad alarms.
     * 
     * @param value
     *     allowed object is
     *     {@link JsonGenericMangoV1 .Alarms }
     *     
     */
    public void setAlarms(JsonGenericMangoV1 .Alarms value) {
        this.alarms = value;
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
     * Obtiene el valor de la propiedad arrayElementId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrayElementId() {
        return arrayElementId;
    }

    /**
     * Define el valor de la propiedad arrayElementId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrayElementId(String value) {
        this.arrayElementId = value;
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
     *         &lt;element name="alarm" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="source" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="active_value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
        "alarm"
    })
    public static class Alarms {

        @XmlElement(namespace = "json_generic_mango_v1", required = true)
        protected List<JsonGenericMangoV1 .Alarms.Alarm> alarm;

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
         * {@link JsonGenericMangoV1 .Alarms.Alarm }
         * 
         * 
         */
        public List<JsonGenericMangoV1 .Alarms.Alarm> getAlarm() {
            if (alarm == null) {
                alarm = new ArrayList<JsonGenericMangoV1 .Alarms.Alarm>();
            }
            return this.alarm;
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
         *       &lt;attribute name="source" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="active_value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
        public static class Alarm {

            @XmlAttribute(name = "source", required = true)
            protected String source;
            @XmlAttribute(name = "active_value", required = true)
            protected String activeValue;
            @XmlAttribute(name = "alarm_config_id", required = true)
            protected long alarmConfigId;

            /**
             * Obtiene el valor de la propiedad source.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSource() {
                return source;
            }

            /**
             * Define el valor de la propiedad source.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSource(String value) {
                this.source = value;
            }

            /**
             * Obtiene el valor de la propiedad activeValue.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getActiveValue() {
                return activeValue;
            }

            /**
             * Define el valor de la propiedad activeValue.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setActiveValue(String value) {
                this.activeValue = value;
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
     *         &lt;element name="field" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="input" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="element_type_param_id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
     *                             &lt;element name="param_type_id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="use_adapter" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
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
        "field"
    })
    public static class Fields {

        @XmlElement(namespace = "json_generic_mango_v1", required = true)
        protected List<JsonGenericMangoV1 .Fields.Field> field;

        /**
         * Gets the value of the field property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the field property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getField().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link JsonGenericMangoV1 .Fields.Field }
         * 
         * 
         */
        public List<JsonGenericMangoV1 .Fields.Field> getField() {
            if (field == null) {
                field = new ArrayList<JsonGenericMangoV1 .Fields.Field>();
            }
            return this.field;
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
         *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="input" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="element_type_param_id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
         *                   &lt;element name="param_type_id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="use_adapter" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
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
            "source",
            "input",
            "useAdapter"
        })
        public static class Field {

            @XmlElement(namespace = "json_generic_mango_v1")
            protected String source;
            @XmlElement(namespace = "json_generic_mango_v1")
            protected JsonGenericMangoV1 .Fields.Field.Input input;
            @XmlElement(name = "use_adapter", namespace = "json_generic_mango_v1")
            protected Boolean useAdapter;

            /**
             * Obtiene el valor de la propiedad source.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSource() {
                return source;
            }

            /**
             * Define el valor de la propiedad source.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSource(String value) {
                this.source = value;
            }

            /**
             * Obtiene el valor de la propiedad input.
             * 
             * @return
             *     possible object is
             *     {@link JsonGenericMangoV1 .Fields.Field.Input }
             *     
             */
            public JsonGenericMangoV1 .Fields.Field.Input getInput() {
                return input;
            }

            /**
             * Define el valor de la propiedad input.
             * 
             * @param value
             *     allowed object is
             *     {@link JsonGenericMangoV1 .Fields.Field.Input }
             *     
             */
            public void setInput(JsonGenericMangoV1 .Fields.Field.Input value) {
                this.input = value;
            }

            /**
             * Obtiene el valor de la propiedad useAdapter.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isUseAdapter() {
                return useAdapter;
            }

            /**
             * Define el valor de la propiedad useAdapter.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setUseAdapter(Boolean value) {
                this.useAdapter = value;
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
             *         &lt;element name="element_type_param_id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
             *         &lt;element name="param_type_id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
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
                "elementTypeParamId",
                "paramTypeId"
            })
            public static class Input {

                @XmlElement(name = "element_type_param_id", namespace = "json_generic_mango_v1")
                protected long elementTypeParamId;
                @XmlElement(name = "param_type_id", namespace = "json_generic_mango_v1")
                protected long paramTypeId;

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
     *       &lt;attribute name="file" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class GroovyAdapter {

        @XmlAttribute(name = "file", required = true)
        protected String file;

        /**
         * Obtiene el valor de la propiedad file.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFile() {
            return file;
        }

        /**
         * Define el valor de la propiedad file.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFile(String value) {
            this.file = value;
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
    public static class SessionUid {

        @XmlAttribute(name = "element_type_param_id", required = true)
        protected long elementTypeParamId;
        @XmlAttribute(name = "param_type_id", required = true)
        protected long paramTypeId;

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
     *         &lt;element name="state_value" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;attribute name="source" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *                 &lt;attribute name="input" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *                 &lt;attribute name="disconnected" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="field" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "stateValue"
    })
    public static class State {

        @XmlElement(name = "state_value", namespace = "json_generic_mango_v1", required = true)
        protected List<JsonGenericMangoV1 .State.StateValue> stateValue;
        @XmlAttribute(name = "field", required = true)
        protected String field;

        /**
         * Gets the value of the stateValue property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the stateValue property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getStateValue().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link JsonGenericMangoV1 .State.StateValue }
         * 
         * 
         */
        public List<JsonGenericMangoV1 .State.StateValue> getStateValue() {
            if (stateValue == null) {
                stateValue = new ArrayList<JsonGenericMangoV1 .State.StateValue>();
            }
            return this.stateValue;
        }

        /**
         * Obtiene el valor de la propiedad field.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getField() {
            return field;
        }

        /**
         * Define el valor de la propiedad field.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setField(String value) {
            this.field = value;
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
         *       &lt;attribute name="source" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
         *       &lt;attribute name="input" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
         *       &lt;attribute name="disconnected" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class StateValue {

            @XmlAttribute(name = "source", required = true)
            protected long source;
            @XmlAttribute(name = "input", required = true)
            protected long input;
            @XmlAttribute(name = "disconnected")
            protected Boolean disconnected;

            /**
             * Obtiene el valor de la propiedad source.
             * 
             */
            public long getSource() {
                return source;
            }

            /**
             * Define el valor de la propiedad source.
             * 
             */
            public void setSource(long value) {
                this.source = value;
            }

            /**
             * Obtiene el valor de la propiedad input.
             * 
             */
            public long getInput() {
                return input;
            }

            /**
             * Define el valor de la propiedad input.
             * 
             */
            public void setInput(long value) {
                this.input = value;
            }

            /**
             * Obtiene el valor de la propiedad disconnected.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isDisconnected() {
                return disconnected;
            }

            /**
             * Define el valor de la propiedad disconnected.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setDisconnected(Boolean value) {
                this.disconnected = value;
            }

        }

    }

}
