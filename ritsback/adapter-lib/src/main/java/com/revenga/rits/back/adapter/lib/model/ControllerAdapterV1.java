
package com.revenga.rits.back.adapter.lib.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="subscriptors"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="subscriptor" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
 *                             &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                             &lt;element name="entity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="entity_pks" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="entity_pk" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
 *         &lt;element name="mods"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="mod" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *                             &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *                             &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                             &lt;element name="threads" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/&gt;
 *                             &lt;element name="subscriptors_ref" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="subscriptor_id_ref" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" maxOccurs="unbounded"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="mod_params" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="mod_param_value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "subscriptors",
    "mods"
})
@XmlRootElement(name = "controller_adapter_v1", namespace = "controller_adapter_v1")
public class ControllerAdapterV1 {

    @XmlElement(namespace = "controller_adapter_v1", required = true)
    protected ControllerAdapterV1 .Subscriptors subscriptors;
    @XmlElement(namespace = "controller_adapter_v1", required = true)
    protected ControllerAdapterV1 .Mods mods;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * Obtiene el valor de la propiedad subscriptors.
     * 
     * @return
     *     possible object is
     *     {@link ControllerAdapterV1 .Subscriptors }
     *     
     */
    public ControllerAdapterV1 .Subscriptors getSubscriptors() {
        return subscriptors;
    }

    /**
     * Define el valor de la propiedad subscriptors.
     * 
     * @param value
     *     allowed object is
     *     {@link ControllerAdapterV1 .Subscriptors }
     *     
     */
    public void setSubscriptors(ControllerAdapterV1 .Subscriptors value) {
        this.subscriptors = value;
    }

    /**
     * Obtiene el valor de la propiedad mods.
     * 
     * @return
     *     possible object is
     *     {@link ControllerAdapterV1 .Mods }
     *     
     */
    public ControllerAdapterV1 .Mods getMods() {
        return mods;
    }

    /**
     * Define el valor de la propiedad mods.
     * 
     * @param value
     *     allowed object is
     *     {@link ControllerAdapterV1 .Mods }
     *     
     */
    public void setMods(ControllerAdapterV1 .Mods value) {
        this.mods = value;
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
     *         &lt;element name="mod" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
     *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
     *                   &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *                   &lt;element name="threads" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/&gt;
     *                   &lt;element name="subscriptors_ref" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="subscriptor_id_ref" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" maxOccurs="unbounded"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="mod_params" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="mod_param_value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
        "mod"
    })
    public static class Mods {

        @XmlElement(namespace = "controller_adapter_v1", required = true)
        protected List<ControllerAdapterV1 .Mods.Mod> mod;

        /**
         * Gets the value of the mod property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the mod property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMod().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ControllerAdapterV1 .Mods.Mod }
         * 
         * 
         */
        public List<ControllerAdapterV1 .Mods.Mod> getMod() {
            if (mod == null) {
                mod = new ArrayList<ControllerAdapterV1 .Mods.Mod>();
            }
            return this.mod;
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
         *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
         *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
         *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
         *         &lt;element name="threads" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/&gt;
         *         &lt;element name="subscriptors_ref" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="subscriptor_id_ref" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" maxOccurs="unbounded"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="mod_params" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="mod_param_value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
            "type",
            "id",
            "enabled",
            "threads",
            "subscriptorsRef",
            "modParams"
        })
        public static class Mod {

            @XmlElement(namespace = "controller_adapter_v1")
            protected long type;
            @XmlElement(namespace = "controller_adapter_v1")
            protected long id;
            @XmlElement(namespace = "controller_adapter_v1")
            protected boolean enabled;
            @XmlElement(namespace = "controller_adapter_v1")
            @XmlSchemaType(name = "unsignedByte")
            protected Short threads;
            @XmlElement(name = "subscriptors_ref", namespace = "controller_adapter_v1")
            protected ControllerAdapterV1 .Mods.Mod.SubscriptorsRef subscriptorsRef;
            @XmlElement(name = "mod_params", namespace = "controller_adapter_v1")
            protected ControllerAdapterV1 .Mods.Mod.ModParams modParams;

            /**
             * Obtiene el valor de la propiedad type.
             * 
             */
            public long getType() {
                return type;
            }

            /**
             * Define el valor de la propiedad type.
             * 
             */
            public void setType(long value) {
                this.type = value;
            }

            /**
             * Obtiene el valor de la propiedad id.
             * 
             */
            public long getId() {
                return id;
            }

            /**
             * Define el valor de la propiedad id.
             * 
             */
            public void setId(long value) {
                this.id = value;
            }

            /**
             * Obtiene el valor de la propiedad enabled.
             * 
             */
            public boolean isEnabled() {
                return enabled;
            }

            /**
             * Define el valor de la propiedad enabled.
             * 
             */
            public void setEnabled(boolean value) {
                this.enabled = value;
            }

            /**
             * Obtiene el valor de la propiedad threads.
             * 
             * @return
             *     possible object is
             *     {@link Short }
             *     
             */
            public Short getThreads() {
                return threads;
            }

            /**
             * Define el valor de la propiedad threads.
             * 
             * @param value
             *     allowed object is
             *     {@link Short }
             *     
             */
            public void setThreads(Short value) {
                this.threads = value;
            }

            /**
             * Obtiene el valor de la propiedad subscriptorsRef.
             * 
             * @return
             *     possible object is
             *     {@link ControllerAdapterV1 .Mods.Mod.SubscriptorsRef }
             *     
             */
            public ControllerAdapterV1 .Mods.Mod.SubscriptorsRef getSubscriptorsRef() {
                return subscriptorsRef;
            }

            /**
             * Define el valor de la propiedad subscriptorsRef.
             * 
             * @param value
             *     allowed object is
             *     {@link ControllerAdapterV1 .Mods.Mod.SubscriptorsRef }
             *     
             */
            public void setSubscriptorsRef(ControllerAdapterV1 .Mods.Mod.SubscriptorsRef value) {
                this.subscriptorsRef = value;
            }

            /**
             * Obtiene el valor de la propiedad modParams.
             * 
             * @return
             *     possible object is
             *     {@link ControllerAdapterV1 .Mods.Mod.ModParams }
             *     
             */
            public ControllerAdapterV1 .Mods.Mod.ModParams getModParams() {
                return modParams;
            }

            /**
             * Define el valor de la propiedad modParams.
             * 
             * @param value
             *     allowed object is
             *     {@link ControllerAdapterV1 .Mods.Mod.ModParams }
             *     
             */
            public void setModParams(ControllerAdapterV1 .Mods.Mod.ModParams value) {
                this.modParams = value;
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
             *         &lt;element name="mod_param_value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
                "modParamValue"
            })
            public static class ModParams {

                @XmlElement(name = "mod_param_value", namespace = "controller_adapter_v1", required = true)
                protected List<String> modParamValue;

                /**
                 * Gets the value of the modParamValue property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the modParamValue property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getModParamValue().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getModParamValue() {
                    if (modParamValue == null) {
                        modParamValue = new ArrayList<String>();
                    }
                    return this.modParamValue;
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
             *         &lt;element name="subscriptor_id_ref" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" maxOccurs="unbounded"/&gt;
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
                "subscriptorIdRef"
            })
            public static class SubscriptorsRef {

                @XmlElement(name = "subscriptor_id_ref", namespace = "controller_adapter_v1", type = Short.class)
                @XmlSchemaType(name = "unsignedByte")
                protected List<Short> subscriptorIdRef;

                /**
                 * Gets the value of the subscriptorIdRef property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the subscriptorIdRef property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getSubscriptorIdRef().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Short }
                 * 
                 * 
                 */
                public List<Short> getSubscriptorIdRef() {
                    if (subscriptorIdRef == null) {
                        subscriptorIdRef = new ArrayList<Short>();
                    }
                    return this.subscriptorIdRef;
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
     *       &lt;sequence&gt;
     *         &lt;element name="subscriptor" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
     *                   &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *                   &lt;element name="entity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="entity_pks" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="entity_pk" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
        "subscriptor"
    })
    public static class Subscriptors {

        @XmlElement(namespace = "controller_adapter_v1", required = true)
        protected List<ControllerAdapterV1 .Subscriptors.Subscriptor> subscriptor;

        /**
         * Gets the value of the subscriptor property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the subscriptor property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSubscriptor().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ControllerAdapterV1 .Subscriptors.Subscriptor }
         * 
         * 
         */
        public List<ControllerAdapterV1 .Subscriptors.Subscriptor> getSubscriptor() {
            if (subscriptor == null) {
                subscriptor = new ArrayList<ControllerAdapterV1 .Subscriptors.Subscriptor>();
            }
            return this.subscriptor;
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
         *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
         *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
         *         &lt;element name="entity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="entity_pks" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="entity_pk" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
            "id",
            "enabled",
            "entity",
            "entityPks"
        })
        public static class Subscriptor {

            @XmlElement(namespace = "controller_adapter_v1")
            @XmlSchemaType(name = "unsignedByte")
            protected short id;
            @XmlElement(namespace = "controller_adapter_v1")
            protected boolean enabled;
            @XmlElement(namespace = "controller_adapter_v1", required = true)
            protected String entity;
            @XmlElement(name = "entity_pks", namespace = "controller_adapter_v1")
            protected ControllerAdapterV1 .Subscriptors.Subscriptor.EntityPks entityPks;

            /**
             * Obtiene el valor de la propiedad id.
             * 
             */
            public short getId() {
                return id;
            }

            /**
             * Define el valor de la propiedad id.
             * 
             */
            public void setId(short value) {
                this.id = value;
            }

            /**
             * Obtiene el valor de la propiedad enabled.
             * 
             */
            public boolean isEnabled() {
                return enabled;
            }

            /**
             * Define el valor de la propiedad enabled.
             * 
             */
            public void setEnabled(boolean value) {
                this.enabled = value;
            }

            /**
             * Obtiene el valor de la propiedad entity.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEntity() {
                return entity;
            }

            /**
             * Define el valor de la propiedad entity.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEntity(String value) {
                this.entity = value;
            }

            /**
             * Obtiene el valor de la propiedad entityPks.
             * 
             * @return
             *     possible object is
             *     {@link ControllerAdapterV1 .Subscriptors.Subscriptor.EntityPks }
             *     
             */
            public ControllerAdapterV1 .Subscriptors.Subscriptor.EntityPks getEntityPks() {
                return entityPks;
            }

            /**
             * Define el valor de la propiedad entityPks.
             * 
             * @param value
             *     allowed object is
             *     {@link ControllerAdapterV1 .Subscriptors.Subscriptor.EntityPks }
             *     
             */
            public void setEntityPks(ControllerAdapterV1 .Subscriptors.Subscriptor.EntityPks value) {
                this.entityPks = value;
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
             *         &lt;element name="entity_pk" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
                "entityPk"
            })
            public static class EntityPks {

                @XmlElement(name = "entity_pk", namespace = "controller_adapter_v1", required = true)
                protected List<String> entityPk;

                /**
                 * Gets the value of the entityPk property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the entityPk property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getEntityPk().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getEntityPk() {
                    if (entityPk == null) {
                        entityPk = new ArrayList<String>();
                    }
                    return this.entityPk;
                }

            }

        }

    }

}
