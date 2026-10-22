
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
 *         &lt;element name="zone_uid"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="element_type_param_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *                 &lt;attribute name="param_type_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="status"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="connected" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *                 &lt;attribute name="disconnected" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *                 &lt;attribute name="unknown" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
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
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="element_type_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *       &lt;attribute name="element_subtype_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
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
    "zoneUid",
    "status",
    "fields"
})
@XmlRootElement(name = "rtms_config_v1", namespace = "rtms_config_v1")
public class RtmsConfigV1 {

    @XmlElement(name = "groovy_adapter", namespace = "rtms_config_v1")
    protected RtmsConfigV1 .GroovyAdapter groovyAdapter;
    @XmlElement(name = "zone_uid", namespace = "rtms_config_v1", required = true)
    protected RtmsConfigV1 .ZoneUid zoneUid;
    @XmlElement(namespace = "rtms_config_v1", required = true)
    protected RtmsConfigV1 .Status status;
    @XmlElement(namespace = "rtms_config_v1")
    protected RtmsConfigV1 .Fields fields;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "version", required = true)
    protected String version;
    @XmlAttribute(name = "element_type_id", required = true)
    protected long elementTypeId;
    @XmlAttribute(name = "element_subtype_id", required = true)
    protected long elementSubtypeId;

    /**
     * Obtiene el valor de la propiedad groovyAdapter.
     * 
     * @return
     *     possible object is
     *     {@link RtmsConfigV1 .GroovyAdapter }
     *     
     */
    public RtmsConfigV1 .GroovyAdapter getGroovyAdapter() {
        return groovyAdapter;
    }

    /**
     * Define el valor de la propiedad groovyAdapter.
     * 
     * @param value
     *     allowed object is
     *     {@link RtmsConfigV1 .GroovyAdapter }
     *     
     */
    public void setGroovyAdapter(RtmsConfigV1 .GroovyAdapter value) {
        this.groovyAdapter = value;
    }

    /**
     * Obtiene el valor de la propiedad zoneUid.
     * 
     * @return
     *     possible object is
     *     {@link RtmsConfigV1 .ZoneUid }
     *     
     */
    public RtmsConfigV1 .ZoneUid getZoneUid() {
        return zoneUid;
    }

    /**
     * Define el valor de la propiedad zoneUid.
     * 
     * @param value
     *     allowed object is
     *     {@link RtmsConfigV1 .ZoneUid }
     *     
     */
    public void setZoneUid(RtmsConfigV1 .ZoneUid value) {
        this.zoneUid = value;
    }

    /**
     * Obtiene el valor de la propiedad status.
     * 
     * @return
     *     possible object is
     *     {@link RtmsConfigV1 .Status }
     *     
     */
    public RtmsConfigV1 .Status getStatus() {
        return status;
    }

    /**
     * Define el valor de la propiedad status.
     * 
     * @param value
     *     allowed object is
     *     {@link RtmsConfigV1 .Status }
     *     
     */
    public void setStatus(RtmsConfigV1 .Status value) {
        this.status = value;
    }

    /**
     * Obtiene el valor de la propiedad fields.
     * 
     * @return
     *     possible object is
     *     {@link RtmsConfigV1 .Fields }
     *     
     */
    public RtmsConfigV1 .Fields getFields() {
        return fields;
    }

    /**
     * Define el valor de la propiedad fields.
     * 
     * @param value
     *     allowed object is
     *     {@link RtmsConfigV1 .Fields }
     *     
     */
    public void setFields(RtmsConfigV1 .Fields value) {
        this.fields = value;
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
     * Obtiene el valor de la propiedad elementSubtypeId.
     * 
     */
    public long getElementSubtypeId() {
        return elementSubtypeId;
    }

    /**
     * Define el valor de la propiedad elementSubtypeId.
     * 
     */
    public void setElementSubtypeId(long value) {
        this.elementSubtypeId = value;
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

        @XmlElement(namespace = "rtms_config_v1", required = true)
        protected List<RtmsConfigV1 .Fields.Field> field;

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
         * {@link RtmsConfigV1 .Fields.Field }
         * 
         * 
         */
        public List<RtmsConfigV1 .Fields.Field> getField() {
            if (field == null) {
                field = new ArrayList<RtmsConfigV1 .Fields.Field>();
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
            "input"
        })
        public static class Field {

            @XmlElement(namespace = "rtms_config_v1")
            protected String source;
            @XmlElement(namespace = "rtms_config_v1")
            protected RtmsConfigV1 .Fields.Field.Input input;

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
             *     {@link RtmsConfigV1 .Fields.Field.Input }
             *     
             */
            public RtmsConfigV1 .Fields.Field.Input getInput() {
                return input;
            }

            /**
             * Define el valor de la propiedad input.
             * 
             * @param value
             *     allowed object is
             *     {@link RtmsConfigV1 .Fields.Field.Input }
             *     
             */
            public void setInput(RtmsConfigV1 .Fields.Field.Input value) {
                this.input = value;
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

                @XmlElement(name = "element_type_param_id", namespace = "rtms_config_v1")
                protected long elementTypeParamId;
                @XmlElement(name = "param_type_id", namespace = "rtms_config_v1")
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
     *       &lt;attribute name="connected" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *       &lt;attribute name="disconnected" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *       &lt;attribute name="unknown" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Status {

        @XmlAttribute(name = "connected", required = true)
        protected long connected;
        @XmlAttribute(name = "disconnected", required = true)
        protected long disconnected;
        @XmlAttribute(name = "unknown", required = true)
        protected long unknown;

        /**
         * Obtiene el valor de la propiedad connected.
         * 
         */
        public long getConnected() {
            return connected;
        }

        /**
         * Define el valor de la propiedad connected.
         * 
         */
        public void setConnected(long value) {
            this.connected = value;
        }

        /**
         * Obtiene el valor de la propiedad disconnected.
         * 
         */
        public long getDisconnected() {
            return disconnected;
        }

        /**
         * Define el valor de la propiedad disconnected.
         * 
         */
        public void setDisconnected(long value) {
            this.disconnected = value;
        }

        /**
         * Obtiene el valor de la propiedad unknown.
         * 
         */
        public long getUnknown() {
            return unknown;
        }

        /**
         * Define el valor de la propiedad unknown.
         * 
         */
        public void setUnknown(long value) {
            this.unknown = value;
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
    public static class ZoneUid {

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

}
