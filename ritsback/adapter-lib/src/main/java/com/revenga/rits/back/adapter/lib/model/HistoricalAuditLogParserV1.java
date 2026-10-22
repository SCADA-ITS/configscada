
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
 *         &lt;element name="target_table" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="table_space" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="field_log_subtype_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fields"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="field" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="log_type_param_id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="indexed" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
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
 *       &lt;attribute name="log_type_id" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "targetTable",
    "tableSpace",
    "fieldLogSubtypeId",
    "fields"
})
@XmlRootElement(name = "historical_audit_log_parser_v1", namespace = "historical_audit_log_parser_v1")
public class HistoricalAuditLogParserV1 {

    @XmlElement(name = "target_table", namespace = "historical_audit_log_parser_v1", required = true)
    protected String targetTable;
    @XmlElement(name = "table_space", namespace = "historical_audit_log_parser_v1", required = true)
    protected String tableSpace;
    @XmlElement(name = "field_log_subtype_id", namespace = "historical_audit_log_parser_v1")
    protected String fieldLogSubtypeId;
    @XmlElement(namespace = "historical_audit_log_parser_v1", required = true)
    protected HistoricalAuditLogParserV1 .Fields fields;
    @XmlAttribute(name = "log_type_id", required = true)
    @XmlSchemaType(name = "unsignedByte")
    protected short logTypeId;

    /**
     * Obtiene el valor de la propiedad targetTable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetTable() {
        return targetTable;
    }

    /**
     * Define el valor de la propiedad targetTable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetTable(String value) {
        this.targetTable = value;
    }

    /**
     * Obtiene el valor de la propiedad tableSpace.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTableSpace() {
        return tableSpace;
    }

    /**
     * Define el valor de la propiedad tableSpace.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTableSpace(String value) {
        this.tableSpace = value;
    }

    /**
     * Obtiene el valor de la propiedad fieldLogSubtypeId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldLogSubtypeId() {
        return fieldLogSubtypeId;
    }

    /**
     * Define el valor de la propiedad fieldLogSubtypeId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldLogSubtypeId(String value) {
        this.fieldLogSubtypeId = value;
    }

    /**
     * Obtiene el valor de la propiedad fields.
     * 
     * @return
     *     possible object is
     *     {@link HistoricalAuditLogParserV1 .Fields }
     *     
     */
    public HistoricalAuditLogParserV1 .Fields getFields() {
        return fields;
    }

    /**
     * Define el valor de la propiedad fields.
     * 
     * @param value
     *     allowed object is
     *     {@link HistoricalAuditLogParserV1 .Fields }
     *     
     */
    public void setFields(HistoricalAuditLogParserV1 .Fields value) {
        this.fields = value;
    }

    /**
     * Obtiene el valor de la propiedad logTypeId.
     * 
     */
    public short getLogTypeId() {
        return logTypeId;
    }

    /**
     * Define el valor de la propiedad logTypeId.
     * 
     */
    public void setLogTypeId(short value) {
        this.logTypeId = value;
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
     *                   &lt;element name="log_type_param_id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="indexed" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
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

        @XmlElement(namespace = "historical_audit_log_parser_v1", required = true)
        protected List<HistoricalAuditLogParserV1 .Fields.Field> field;

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
         * {@link HistoricalAuditLogParserV1 .Fields.Field }
         * 
         * 
         */
        public List<HistoricalAuditLogParserV1 .Fields.Field> getField() {
            if (field == null) {
                field = new ArrayList<HistoricalAuditLogParserV1 .Fields.Field>();
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
         *         &lt;element name="log_type_param_id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="indexed" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "logTypeParamId"
        })
        public static class Field {

            @XmlElement(name = "log_type_param_id", namespace = "historical_audit_log_parser_v1")
            @XmlSchemaType(name = "unsignedByte")
            protected short logTypeParamId;
            @XmlAttribute(name = "name", required = true)
            protected String name;
            @XmlAttribute(name = "indexed")
            protected Boolean indexed;

            /**
             * Obtiene el valor de la propiedad logTypeParamId.
             * 
             */
            public short getLogTypeParamId() {
                return logTypeParamId;
            }

            /**
             * Define el valor de la propiedad logTypeParamId.
             * 
             */
            public void setLogTypeParamId(short value) {
                this.logTypeParamId = value;
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
             * Obtiene el valor de la propiedad indexed.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isIndexed() {
                return indexed;
            }

            /**
             * Define el valor de la propiedad indexed.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setIndexed(Boolean value) {
                this.indexed = value;
            }

        }

    }

}
