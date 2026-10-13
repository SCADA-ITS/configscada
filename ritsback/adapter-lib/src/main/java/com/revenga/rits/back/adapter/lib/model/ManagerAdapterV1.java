
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
 *         &lt;element name="subscriptors" minOccurs="0"&gt;
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
 *                             &lt;element name="cmd_timestamp_filter" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/&gt;
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
 *         &lt;element name="executors"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="executor" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
 *                             &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                             &lt;element name="threads" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/&gt;
 *                             &lt;element name="thread_redirect_criteria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="time_to_start_s" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *                             &lt;element name="merge_wait_time" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *                             &lt;element name="manager_class" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="custom_ready_executor" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *                             &lt;element name="manager_params" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="manager_param_value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
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
 *                             &lt;element name="executor_params" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="executor_param_value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
    "executors"
})
@XmlRootElement(name = "manager_adapter_v1", namespace = "manager_adapter_v1")
public class ManagerAdapterV1 {

    @XmlElement(namespace = "manager_adapter_v1")
    protected ManagerAdapterV1 .Subscriptors subscriptors;
    @XmlElement(namespace = "manager_adapter_v1", required = true)
    protected ManagerAdapterV1 .Executors executors;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * Obtiene el valor de la propiedad subscriptors.
     * 
     * @return
     *     possible object is
     *     {@link ManagerAdapterV1 .Subscriptors }
     *     
     */
    public ManagerAdapterV1 .Subscriptors getSubscriptors() {
        return subscriptors;
    }

    /**
     * Define el valor de la propiedad subscriptors.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagerAdapterV1 .Subscriptors }
     *     
     */
    public void setSubscriptors(ManagerAdapterV1 .Subscriptors value) {
        this.subscriptors = value;
    }

    /**
     * Obtiene el valor de la propiedad executors.
     * 
     * @return
     *     possible object is
     *     {@link ManagerAdapterV1 .Executors }
     *     
     */
    public ManagerAdapterV1 .Executors getExecutors() {
        return executors;
    }

    /**
     * Define el valor de la propiedad executors.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagerAdapterV1 .Executors }
     *     
     */
    public void setExecutors(ManagerAdapterV1 .Executors value) {
        this.executors = value;
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
     *         &lt;element name="executor" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
     *                   &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *                   &lt;element name="threads" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/&gt;
     *                   &lt;element name="thread_redirect_criteria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="time_to_start_s" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
     *                   &lt;element name="merge_wait_time" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
     *                   &lt;element name="manager_class" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="custom_ready_executor" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
     *                   &lt;element name="manager_params" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="manager_param_value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
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
     *                   &lt;element name="executor_params" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="executor_param_value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
        "executor"
    })
    public static class Executors {

        @XmlElement(namespace = "manager_adapter_v1", required = true)
        protected List<ManagerAdapterV1 .Executors.Executor> executor;

        /**
         * Gets the value of the executor property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the executor property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getExecutor().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ManagerAdapterV1 .Executors.Executor }
         * 
         * 
         */
        public List<ManagerAdapterV1 .Executors.Executor> getExecutor() {
            if (executor == null) {
                executor = new ArrayList<ManagerAdapterV1 .Executors.Executor>();
            }
            return this.executor;
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
         *         &lt;element name="threads" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/&gt;
         *         &lt;element name="thread_redirect_criteria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="time_to_start_s" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
         *         &lt;element name="merge_wait_time" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
         *         &lt;element name="manager_class" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="custom_ready_executor" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
         *         &lt;element name="manager_params" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="manager_param_value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
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
         *         &lt;element name="executor_params" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="executor_param_value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
            "threads",
            "threadRedirectCriteria",
            "timeToStartS",
            "mergeWaitTime",
            "managerClass",
            "customReadyExecutor",
            "managerParams",
            "subscriptorsRef",
            "executorParams"
        })
        public static class Executor {

            @XmlElement(namespace = "manager_adapter_v1")
            @XmlSchemaType(name = "unsignedByte")
            protected short id;
            @XmlElement(namespace = "manager_adapter_v1")
            protected boolean enabled;
            @XmlElement(namespace = "manager_adapter_v1")
            @XmlSchemaType(name = "unsignedByte")
            protected Short threads;
            @XmlElement(name = "thread_redirect_criteria", namespace = "manager_adapter_v1")
            protected String threadRedirectCriteria;
            @XmlElement(name = "time_to_start_s", namespace = "manager_adapter_v1")
            protected Long timeToStartS;
            @XmlElement(name = "merge_wait_time", namespace = "manager_adapter_v1")
            protected long mergeWaitTime;
            @XmlElement(name = "manager_class", namespace = "manager_adapter_v1", required = true)
            protected String managerClass;
            @XmlElement(name = "custom_ready_executor", namespace = "manager_adapter_v1")
            protected Boolean customReadyExecutor;
            @XmlElement(name = "manager_params", namespace = "manager_adapter_v1")
            protected ManagerAdapterV1 .Executors.Executor.ManagerParams managerParams;
            @XmlElement(name = "subscriptors_ref", namespace = "manager_adapter_v1")
            protected ManagerAdapterV1 .Executors.Executor.SubscriptorsRef subscriptorsRef;
            @XmlElement(name = "executor_params", namespace = "manager_adapter_v1")
            protected ManagerAdapterV1 .Executors.Executor.ExecutorParams executorParams;

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
             * Obtiene el valor de la propiedad threadRedirectCriteria.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getThreadRedirectCriteria() {
                return threadRedirectCriteria;
            }

            /**
             * Define el valor de la propiedad threadRedirectCriteria.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setThreadRedirectCriteria(String value) {
                this.threadRedirectCriteria = value;
            }

            /**
             * Obtiene el valor de la propiedad timeToStartS.
             * 
             * @return
             *     possible object is
             *     {@link Long }
             *     
             */
            public Long getTimeToStartS() {
                return timeToStartS;
            }

            /**
             * Define el valor de la propiedad timeToStartS.
             * 
             * @param value
             *     allowed object is
             *     {@link Long }
             *     
             */
            public void setTimeToStartS(Long value) {
                this.timeToStartS = value;
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
             * Obtiene el valor de la propiedad managerClass.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getManagerClass() {
                return managerClass;
            }

            /**
             * Define el valor de la propiedad managerClass.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setManagerClass(String value) {
                this.managerClass = value;
            }

            /**
             * Obtiene el valor de la propiedad customReadyExecutor.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isCustomReadyExecutor() {
                return customReadyExecutor;
            }

            /**
             * Define el valor de la propiedad customReadyExecutor.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setCustomReadyExecutor(Boolean value) {
                this.customReadyExecutor = value;
            }

            /**
             * Obtiene el valor de la propiedad managerParams.
             * 
             * @return
             *     possible object is
             *     {@link ManagerAdapterV1 .Executors.Executor.ManagerParams }
             *     
             */
            public ManagerAdapterV1 .Executors.Executor.ManagerParams getManagerParams() {
                return managerParams;
            }

            /**
             * Define el valor de la propiedad managerParams.
             * 
             * @param value
             *     allowed object is
             *     {@link ManagerAdapterV1 .Executors.Executor.ManagerParams }
             *     
             */
            public void setManagerParams(ManagerAdapterV1 .Executors.Executor.ManagerParams value) {
                this.managerParams = value;
            }

            /**
             * Obtiene el valor de la propiedad subscriptorsRef.
             * 
             * @return
             *     possible object is
             *     {@link ManagerAdapterV1 .Executors.Executor.SubscriptorsRef }
             *     
             */
            public ManagerAdapterV1 .Executors.Executor.SubscriptorsRef getSubscriptorsRef() {
                return subscriptorsRef;
            }

            /**
             * Define el valor de la propiedad subscriptorsRef.
             * 
             * @param value
             *     allowed object is
             *     {@link ManagerAdapterV1 .Executors.Executor.SubscriptorsRef }
             *     
             */
            public void setSubscriptorsRef(ManagerAdapterV1 .Executors.Executor.SubscriptorsRef value) {
                this.subscriptorsRef = value;
            }

            /**
             * Obtiene el valor de la propiedad executorParams.
             * 
             * @return
             *     possible object is
             *     {@link ManagerAdapterV1 .Executors.Executor.ExecutorParams }
             *     
             */
            public ManagerAdapterV1 .Executors.Executor.ExecutorParams getExecutorParams() {
                return executorParams;
            }

            /**
             * Define el valor de la propiedad executorParams.
             * 
             * @param value
             *     allowed object is
             *     {@link ManagerAdapterV1 .Executors.Executor.ExecutorParams }
             *     
             */
            public void setExecutorParams(ManagerAdapterV1 .Executors.Executor.ExecutorParams value) {
                this.executorParams = value;
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
             *         &lt;element name="executor_param_value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
                "executorParamValue"
            })
            public static class ExecutorParams {

                @XmlElement(name = "executor_param_value", namespace = "manager_adapter_v1", required = true)
                protected List<String> executorParamValue;

                /**
                 * Gets the value of the executorParamValue property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the executorParamValue property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getExecutorParamValue().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getExecutorParamValue() {
                    if (executorParamValue == null) {
                        executorParamValue = new ArrayList<String>();
                    }
                    return this.executorParamValue;
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
             *         &lt;element name="manager_param_value" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
                "managerParamValue"
            })
            public static class ManagerParams {

                @XmlElement(name = "manager_param_value", namespace = "manager_adapter_v1", required = true)
                protected List<String> managerParamValue;

                /**
                 * Gets the value of the managerParamValue property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the managerParamValue property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getManagerParamValue().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getManagerParamValue() {
                    if (managerParamValue == null) {
                        managerParamValue = new ArrayList<String>();
                    }
                    return this.managerParamValue;
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

                @XmlElement(name = "subscriptor_id_ref", namespace = "manager_adapter_v1", type = Short.class)
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
     *                   &lt;element name="cmd_timestamp_filter" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/&gt;
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

        @XmlElement(namespace = "manager_adapter_v1", required = true)
        protected List<ManagerAdapterV1 .Subscriptors.Subscriptor> subscriptor;

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
         * {@link ManagerAdapterV1 .Subscriptors.Subscriptor }
         * 
         * 
         */
        public List<ManagerAdapterV1 .Subscriptors.Subscriptor> getSubscriptor() {
            if (subscriptor == null) {
                subscriptor = new ArrayList<ManagerAdapterV1 .Subscriptors.Subscriptor>();
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
         *         &lt;element name="cmd_timestamp_filter" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/&gt;
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
            "entityPks",
            "cmdTimestampFilter"
        })
        public static class Subscriptor {

            @XmlElement(namespace = "manager_adapter_v1")
            @XmlSchemaType(name = "unsignedByte")
            protected short id;
            @XmlElement(namespace = "manager_adapter_v1")
            protected boolean enabled;
            @XmlElement(namespace = "manager_adapter_v1", required = true)
            protected String entity;
            @XmlElement(name = "entity_pks", namespace = "manager_adapter_v1")
            protected ManagerAdapterV1 .Subscriptors.Subscriptor.EntityPks entityPks;
            @XmlElement(name = "cmd_timestamp_filter", namespace = "manager_adapter_v1")
            @XmlSchemaType(name = "unsignedByte")
            protected Short cmdTimestampFilter;

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
             *     {@link ManagerAdapterV1 .Subscriptors.Subscriptor.EntityPks }
             *     
             */
            public ManagerAdapterV1 .Subscriptors.Subscriptor.EntityPks getEntityPks() {
                return entityPks;
            }

            /**
             * Define el valor de la propiedad entityPks.
             * 
             * @param value
             *     allowed object is
             *     {@link ManagerAdapterV1 .Subscriptors.Subscriptor.EntityPks }
             *     
             */
            public void setEntityPks(ManagerAdapterV1 .Subscriptors.Subscriptor.EntityPks value) {
                this.entityPks = value;
            }

            /**
             * Obtiene el valor de la propiedad cmdTimestampFilter.
             * 
             * @return
             *     possible object is
             *     {@link Short }
             *     
             */
            public Short getCmdTimestampFilter() {
                return cmdTimestampFilter;
            }

            /**
             * Define el valor de la propiedad cmdTimestampFilter.
             * 
             * @param value
             *     allowed object is
             *     {@link Short }
             *     
             */
            public void setCmdTimestampFilter(Short value) {
                this.cmdTimestampFilter = value;
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

                @XmlElement(name = "entity_pk", namespace = "manager_adapter_v1", required = true)
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
