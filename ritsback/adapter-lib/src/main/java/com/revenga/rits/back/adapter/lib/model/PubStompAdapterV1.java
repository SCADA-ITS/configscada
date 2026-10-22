
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
 *         &lt;element name="publishers"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="publisher" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
 *                             &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                             &lt;element name="broker" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="topics"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="topic" maxOccurs="unbounded"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
 *                                                 &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                                                 &lt;element name="entity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="publisher_class" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                                 &lt;element name="merge_wait_time" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *                                                 &lt;element name="subscriptors_ref"&gt;
 *                                                   &lt;complexType&gt;
 *                                                     &lt;complexContent&gt;
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                         &lt;sequence&gt;
 *                                                           &lt;element name="subscriptor_id_ref" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" maxOccurs="unbounded"/&gt;
 *                                                         &lt;/sequence&gt;
 *                                                       &lt;/restriction&gt;
 *                                                     &lt;/complexContent&gt;
 *                                                   &lt;/complexType&gt;
 *                                                 &lt;/element&gt;
 *                                                 &lt;element name="custom_path" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                                                 &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
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
    "publishers"
})
@XmlRootElement(name = "pub_stomp_adapter_v1", namespace = "pub_stomp_adapter_v1")
public class PubStompAdapterV1 {

    @XmlElement(namespace = "pub_stomp_adapter_v1", required = true)
    protected PubStompAdapterV1 .Subscriptors subscriptors;
    @XmlElement(namespace = "pub_stomp_adapter_v1", required = true)
    protected PubStompAdapterV1 .Publishers publishers;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * Obtiene el valor de la propiedad subscriptors.
     * 
     * @return
     *     possible object is
     *     {@link PubStompAdapterV1 .Subscriptors }
     *     
     */
    public PubStompAdapterV1 .Subscriptors getSubscriptors() {
        return subscriptors;
    }

    /**
     * Define el valor de la propiedad subscriptors.
     * 
     * @param value
     *     allowed object is
     *     {@link PubStompAdapterV1 .Subscriptors }
     *     
     */
    public void setSubscriptors(PubStompAdapterV1 .Subscriptors value) {
        this.subscriptors = value;
    }

    /**
     * Obtiene el valor de la propiedad publishers.
     * 
     * @return
     *     possible object is
     *     {@link PubStompAdapterV1 .Publishers }
     *     
     */
    public PubStompAdapterV1 .Publishers getPublishers() {
        return publishers;
    }

    /**
     * Define el valor de la propiedad publishers.
     * 
     * @param value
     *     allowed object is
     *     {@link PubStompAdapterV1 .Publishers }
     *     
     */
    public void setPublishers(PubStompAdapterV1 .Publishers value) {
        this.publishers = value;
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
     *         &lt;element name="publisher" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
     *                   &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *                   &lt;element name="broker" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="topics"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="topic" maxOccurs="unbounded"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
     *                                       &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *                                       &lt;element name="entity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="publisher_class" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                                       &lt;element name="merge_wait_time" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
     *                                       &lt;element name="subscriptors_ref"&gt;
     *                                         &lt;complexType&gt;
     *                                           &lt;complexContent&gt;
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                               &lt;sequence&gt;
     *                                                 &lt;element name="subscriptor_id_ref" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" maxOccurs="unbounded"/&gt;
     *                                               &lt;/sequence&gt;
     *                                             &lt;/restriction&gt;
     *                                           &lt;/complexContent&gt;
     *                                         &lt;/complexType&gt;
     *                                       &lt;/element&gt;
     *                                       &lt;element name="custom_path" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *                                       &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "publisher"
    })
    public static class Publishers {

        @XmlElement(namespace = "pub_stomp_adapter_v1", required = true)
        protected List<PubStompAdapterV1 .Publishers.Publisher> publisher;

        /**
         * Gets the value of the publisher property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the publisher property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPublisher().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PubStompAdapterV1 .Publishers.Publisher }
         * 
         * 
         */
        public List<PubStompAdapterV1 .Publishers.Publisher> getPublisher() {
            if (publisher == null) {
                publisher = new ArrayList<PubStompAdapterV1 .Publishers.Publisher>();
            }
            return this.publisher;
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
         *         &lt;element name="broker" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="topics"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="topic" maxOccurs="unbounded"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
         *                             &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
         *                             &lt;element name="entity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="publisher_class" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                             &lt;element name="merge_wait_time" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
         *                             &lt;element name="subscriptors_ref"&gt;
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
         *                             &lt;element name="custom_path" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
         *                             &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "id",
            "enabled",
            "broker",
            "user",
            "password",
            "topics"
        })
        public static class Publisher {

            @XmlElement(namespace = "pub_stomp_adapter_v1")
            @XmlSchemaType(name = "unsignedByte")
            protected short id;
            @XmlElement(namespace = "pub_stomp_adapter_v1")
            protected boolean enabled;
            @XmlElement(namespace = "pub_stomp_adapter_v1", required = true)
            protected String broker;
            @XmlElement(namespace = "pub_stomp_adapter_v1")
            protected String user;
            @XmlElement(namespace = "pub_stomp_adapter_v1")
            protected String password;
            @XmlElement(namespace = "pub_stomp_adapter_v1", required = true)
            protected PubStompAdapterV1 .Publishers.Publisher.Topics topics;

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
             * Obtiene el valor de la propiedad broker.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBroker() {
                return broker;
            }

            /**
             * Define el valor de la propiedad broker.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBroker(String value) {
                this.broker = value;
            }

            /**
             * Obtiene el valor de la propiedad user.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUser() {
                return user;
            }

            /**
             * Define el valor de la propiedad user.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUser(String value) {
                this.user = value;
            }

            /**
             * Obtiene el valor de la propiedad password.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPassword() {
                return password;
            }

            /**
             * Define el valor de la propiedad password.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPassword(String value) {
                this.password = value;
            }

            /**
             * Obtiene el valor de la propiedad topics.
             * 
             * @return
             *     possible object is
             *     {@link PubStompAdapterV1 .Publishers.Publisher.Topics }
             *     
             */
            public PubStompAdapterV1 .Publishers.Publisher.Topics getTopics() {
                return topics;
            }

            /**
             * Define el valor de la propiedad topics.
             * 
             * @param value
             *     allowed object is
             *     {@link PubStompAdapterV1 .Publishers.Publisher.Topics }
             *     
             */
            public void setTopics(PubStompAdapterV1 .Publishers.Publisher.Topics value) {
                this.topics = value;
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
             *         &lt;element name="topic" maxOccurs="unbounded"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
             *                   &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
             *                   &lt;element name="entity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="publisher_class" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *                   &lt;element name="merge_wait_time" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
             *                   &lt;element name="subscriptors_ref"&gt;
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
             *                   &lt;element name="custom_path" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
             *                   &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                "topic"
            })
            public static class Topics {

                @XmlElement(namespace = "pub_stomp_adapter_v1", required = true)
                protected List<PubStompAdapterV1 .Publishers.Publisher.Topics.Topic> topic;

                /**
                 * Gets the value of the topic property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the topic property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getTopic().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link PubStompAdapterV1 .Publishers.Publisher.Topics.Topic }
                 * 
                 * 
                 */
                public List<PubStompAdapterV1 .Publishers.Publisher.Topics.Topic> getTopic() {
                    if (topic == null) {
                        topic = new ArrayList<PubStompAdapterV1 .Publishers.Publisher.Topics.Topic>();
                    }
                    return this.topic;
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
                 *         &lt;element name="publisher_class" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
                 *         &lt;element name="merge_wait_time" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
                 *         &lt;element name="subscriptors_ref"&gt;
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
                 *         &lt;element name="custom_path" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
                 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                    "publisherClass",
                    "mergeWaitTime",
                    "subscriptorsRef",
                    "customPath",
                    "path"
                })
                public static class Topic {

                    @XmlElement(namespace = "pub_stomp_adapter_v1")
                    @XmlSchemaType(name = "unsignedByte")
                    protected short id;
                    @XmlElement(namespace = "pub_stomp_adapter_v1")
                    protected boolean enabled;
                    @XmlElement(namespace = "pub_stomp_adapter_v1", required = true)
                    protected String entity;
                    @XmlElement(name = "publisher_class", namespace = "pub_stomp_adapter_v1", required = true)
                    protected String publisherClass;
                    @XmlElement(name = "merge_wait_time", namespace = "pub_stomp_adapter_v1")
                    protected long mergeWaitTime;
                    @XmlElement(name = "subscriptors_ref", namespace = "pub_stomp_adapter_v1", required = true)
                    protected PubStompAdapterV1 .Publishers.Publisher.Topics.Topic.SubscriptorsRef subscriptorsRef;
                    @XmlElement(name = "custom_path", namespace = "pub_stomp_adapter_v1")
                    protected boolean customPath;
                    @XmlElement(namespace = "pub_stomp_adapter_v1")
                    protected String path;

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
                     * Obtiene el valor de la propiedad publisherClass.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPublisherClass() {
                        return publisherClass;
                    }

                    /**
                     * Define el valor de la propiedad publisherClass.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPublisherClass(String value) {
                        this.publisherClass = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad mergeWaitTime.
                     * 
                     */
                    public long getMergeWaitTime() {
                        return mergeWaitTime;
                    }

                    /**
                     * Define el valor de la propiedad mergeWaitTime.
                     * 
                     */
                    public void setMergeWaitTime(long value) {
                        this.mergeWaitTime = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad subscriptorsRef.
                     * 
                     * @return
                     *     possible object is
                     *     {@link PubStompAdapterV1 .Publishers.Publisher.Topics.Topic.SubscriptorsRef }
                     *     
                     */
                    public PubStompAdapterV1 .Publishers.Publisher.Topics.Topic.SubscriptorsRef getSubscriptorsRef() {
                        return subscriptorsRef;
                    }

                    /**
                     * Define el valor de la propiedad subscriptorsRef.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link PubStompAdapterV1 .Publishers.Publisher.Topics.Topic.SubscriptorsRef }
                     *     
                     */
                    public void setSubscriptorsRef(PubStompAdapterV1 .Publishers.Publisher.Topics.Topic.SubscriptorsRef value) {
                        this.subscriptorsRef = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad customPath.
                     * 
                     */
                    public boolean isCustomPath() {
                        return customPath;
                    }

                    /**
                     * Define el valor de la propiedad customPath.
                     * 
                     */
                    public void setCustomPath(boolean value) {
                        this.customPath = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad path.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPath() {
                        return path;
                    }

                    /**
                     * Define el valor de la propiedad path.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPath(String value) {
                        this.path = value;
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

                        @XmlElement(name = "subscriptor_id_ref", namespace = "pub_stomp_adapter_v1", type = Short.class)
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

        @XmlElement(namespace = "pub_stomp_adapter_v1", required = true)
        protected List<PubStompAdapterV1 .Subscriptors.Subscriptor> subscriptor;

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
         * {@link PubStompAdapterV1 .Subscriptors.Subscriptor }
         * 
         * 
         */
        public List<PubStompAdapterV1 .Subscriptors.Subscriptor> getSubscriptor() {
            if (subscriptor == null) {
                subscriptor = new ArrayList<PubStompAdapterV1 .Subscriptors.Subscriptor>();
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
            "entity"
        })
        public static class Subscriptor {

            @XmlElement(namespace = "pub_stomp_adapter_v1")
            @XmlSchemaType(name = "unsignedByte")
            protected short id;
            @XmlElement(namespace = "pub_stomp_adapter_v1")
            protected boolean enabled;
            @XmlElement(namespace = "pub_stomp_adapter_v1", required = true)
            protected String entity;

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

        }

    }

}
