
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
 *         &lt;element name="general"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="check_alive_s" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
 *                   &lt;element name="trace_stats_s" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="processes"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="proc" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="executable" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="xml_adapter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="restart_on_fail" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                             &lt;element name="dependencies" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="time_out" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *                             &lt;element name="parameters" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="param" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="thresholds" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="threshold" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
 *                           &lt;attribute name="enabled" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
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
    "general",
    "processes"
})
@XmlRootElement(name = "server_launcher_adapter_v1", namespace = "server_launcher_adapter_v1")
public class ServerLauncherAdapterV1 {

    @XmlElement(namespace = "server_launcher_adapter_v1", required = true)
    protected ServerLauncherAdapterV1 .General general;
    @XmlElement(namespace = "server_launcher_adapter_v1", required = true)
    protected ServerLauncherAdapterV1 .Processes processes;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "version", required = true)
    protected String version;

    /**
     * Obtiene el valor de la propiedad general.
     * 
     * @return
     *     possible object is
     *     {@link ServerLauncherAdapterV1 .General }
     *     
     */
    public ServerLauncherAdapterV1 .General getGeneral() {
        return general;
    }

    /**
     * Define el valor de la propiedad general.
     * 
     * @param value
     *     allowed object is
     *     {@link ServerLauncherAdapterV1 .General }
     *     
     */
    public void setGeneral(ServerLauncherAdapterV1 .General value) {
        this.general = value;
    }

    /**
     * Obtiene el valor de la propiedad processes.
     * 
     * @return
     *     possible object is
     *     {@link ServerLauncherAdapterV1 .Processes }
     *     
     */
    public ServerLauncherAdapterV1 .Processes getProcesses() {
        return processes;
    }

    /**
     * Define el valor de la propiedad processes.
     * 
     * @param value
     *     allowed object is
     *     {@link ServerLauncherAdapterV1 .Processes }
     *     
     */
    public void setProcesses(ServerLauncherAdapterV1 .Processes value) {
        this.processes = value;
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
     *         &lt;element name="check_alive_s" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
     *         &lt;element name="trace_stats_s" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
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
        "checkAliveS",
        "traceStatsS"
    })
    public static class General {

        @XmlElement(name = "check_alive_s", namespace = "server_launcher_adapter_v1")
        @XmlSchemaType(name = "unsignedByte")
        protected short checkAliveS;
        @XmlElement(name = "trace_stats_s", namespace = "server_launcher_adapter_v1")
        @XmlSchemaType(name = "unsignedByte")
        protected short traceStatsS;

        /**
         * Obtiene el valor de la propiedad checkAliveS.
         * 
         */
        public short getCheckAliveS() {
            return checkAliveS;
        }

        /**
         * Define el valor de la propiedad checkAliveS.
         * 
         */
        public void setCheckAliveS(short value) {
            this.checkAliveS = value;
        }

        /**
         * Obtiene el valor de la propiedad traceStatsS.
         * 
         */
        public short getTraceStatsS() {
            return traceStatsS;
        }

        /**
         * Define el valor de la propiedad traceStatsS.
         * 
         */
        public void setTraceStatsS(short value) {
            this.traceStatsS = value;
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
     *         &lt;element name="proc" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="executable" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="xml_adapter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="restart_on_fail" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *                   &lt;element name="dependencies" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="time_out" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
     *                   &lt;element name="parameters" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="param" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="thresholds" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="threshold" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
     *                 &lt;attribute name="enabled" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
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
        "proc"
    })
    public static class Processes {

        @XmlElement(namespace = "server_launcher_adapter_v1", required = true)
        protected List<ServerLauncherAdapterV1 .Processes.Proc> proc;

        /**
         * Gets the value of the proc property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the proc property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProc().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ServerLauncherAdapterV1 .Processes.Proc }
         * 
         * 
         */
        public List<ServerLauncherAdapterV1 .Processes.Proc> getProc() {
            if (proc == null) {
                proc = new ArrayList<ServerLauncherAdapterV1 .Processes.Proc>();
            }
            return this.proc;
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
         *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="executable" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="xml_adapter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="restart_on_fail" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
         *         &lt;element name="dependencies" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="time_out" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
         *         &lt;element name="parameters" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="param" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="thresholds" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="threshold" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
         *       &lt;attribute name="enabled" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "name",
            "path",
            "executable",
            "xmlAdapter",
            "restartOnFail",
            "dependencies",
            "timeOut",
            "parameters",
            "thresholds"
        })
        public static class Proc {

            @XmlElement(namespace = "server_launcher_adapter_v1", required = true)
            protected String name;
            @XmlElement(namespace = "server_launcher_adapter_v1", required = true)
            protected String path;
            @XmlElement(namespace = "server_launcher_adapter_v1", required = true)
            protected String executable;
            @XmlElement(name = "xml_adapter", namespace = "server_launcher_adapter_v1")
            protected String xmlAdapter;
            @XmlElement(name = "restart_on_fail", namespace = "server_launcher_adapter_v1")
            protected boolean restartOnFail;
            @XmlElement(namespace = "server_launcher_adapter_v1")
            protected String dependencies;
            @XmlElement(name = "time_out", namespace = "server_launcher_adapter_v1")
            protected Long timeOut;
            @XmlElement(namespace = "server_launcher_adapter_v1")
            protected ServerLauncherAdapterV1 .Processes.Proc.Parameters parameters;
            @XmlElement(namespace = "server_launcher_adapter_v1")
            protected ServerLauncherAdapterV1 .Processes.Proc.Thresholds thresholds;
            @XmlAttribute(name = "id", required = true)
            @XmlSchemaType(name = "unsignedByte")
            protected short id;
            @XmlAttribute(name = "enabled", required = true)
            protected boolean enabled;

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
             * Obtiene el valor de la propiedad executable.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getExecutable() {
                return executable;
            }

            /**
             * Define el valor de la propiedad executable.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setExecutable(String value) {
                this.executable = value;
            }

            /**
             * Obtiene el valor de la propiedad xmlAdapter.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getXmlAdapter() {
                return xmlAdapter;
            }

            /**
             * Define el valor de la propiedad xmlAdapter.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setXmlAdapter(String value) {
                this.xmlAdapter = value;
            }

            /**
             * Obtiene el valor de la propiedad restartOnFail.
             * 
             */
            public boolean isRestartOnFail() {
                return restartOnFail;
            }

            /**
             * Define el valor de la propiedad restartOnFail.
             * 
             */
            public void setRestartOnFail(boolean value) {
                this.restartOnFail = value;
            }

            /**
             * Obtiene el valor de la propiedad dependencies.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDependencies() {
                return dependencies;
            }

            /**
             * Define el valor de la propiedad dependencies.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDependencies(String value) {
                this.dependencies = value;
            }

            /**
             * Obtiene el valor de la propiedad timeOut.
             * 
             * @return
             *     possible object is
             *     {@link Long }
             *     
             */
            public Long getTimeOut() {
                return timeOut;
            }

            /**
             * Define el valor de la propiedad timeOut.
             * 
             * @param value
             *     allowed object is
             *     {@link Long }
             *     
             */
            public void setTimeOut(Long value) {
                this.timeOut = value;
            }

            /**
             * Obtiene el valor de la propiedad parameters.
             * 
             * @return
             *     possible object is
             *     {@link ServerLauncherAdapterV1 .Processes.Proc.Parameters }
             *     
             */
            public ServerLauncherAdapterV1 .Processes.Proc.Parameters getParameters() {
                return parameters;
            }

            /**
             * Define el valor de la propiedad parameters.
             * 
             * @param value
             *     allowed object is
             *     {@link ServerLauncherAdapterV1 .Processes.Proc.Parameters }
             *     
             */
            public void setParameters(ServerLauncherAdapterV1 .Processes.Proc.Parameters value) {
                this.parameters = value;
            }

            /**
             * Obtiene el valor de la propiedad thresholds.
             * 
             * @return
             *     possible object is
             *     {@link ServerLauncherAdapterV1 .Processes.Proc.Thresholds }
             *     
             */
            public ServerLauncherAdapterV1 .Processes.Proc.Thresholds getThresholds() {
                return thresholds;
            }

            /**
             * Define el valor de la propiedad thresholds.
             * 
             * @param value
             *     allowed object is
             *     {@link ServerLauncherAdapterV1 .Processes.Proc.Thresholds }
             *     
             */
            public void setThresholds(ServerLauncherAdapterV1 .Processes.Proc.Thresholds value) {
                this.thresholds = value;
            }

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
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="param" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
                "param"
            })
            public static class Parameters {

                @XmlElement(namespace = "server_launcher_adapter_v1", required = true)
                protected List<String> param;

                /**
                 * Gets the value of the param property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the param property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getParam().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getParam() {
                    if (param == null) {
                        param = new ArrayList<String>();
                    }
                    return this.param;
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
             *         &lt;element name="threshold" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
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
                "threshold"
            })
            public static class Thresholds {

                @XmlElement(namespace = "server_launcher_adapter_v1", required = true)
                protected List<String> threshold;

                /**
                 * Gets the value of the threshold property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the threshold property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getThreshold().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getThreshold() {
                    if (threshold == null) {
                        threshold = new ArrayList<String>();
                    }
                    return this.threshold;
                }

            }

        }

    }

}
