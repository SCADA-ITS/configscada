
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
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="target_table" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="table_space" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="field_element_id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="field_timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cron_expression" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="no_save_empty_rows" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
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
 *                             &lt;element name="element_type_param"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="element_type_param_id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
 *                                       &lt;element name="param_type_id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="pk" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
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
 *       &lt;attribute name="element_type_id" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
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
    "fieldElementId",
    "fieldTimestamp",
    "cronExpression",
    "noSaveEmptyRows",
    "fields"
})
@XmlRootElement(name = "historical_element_parser_v1", namespace = "historical_element_parser_v1")
public class HistoricalElementParserV1 {

    @XmlElement(name = "target_table", namespace = "historical_element_parser_v1", required = true)
    protected String targetTable;
    @XmlElement(name = "table_space", namespace = "historical_element_parser_v1", required = true)
    protected String tableSpace;
    @XmlElement(name = "field_element_id", namespace = "historical_element_parser_v1", required = true)
    protected String fieldElementId;
    @XmlElement(name = "field_timestamp", namespace = "historical_element_parser_v1")
    protected String fieldTimestamp;
    @XmlElement(name = "cron_expression", namespace = "historical_element_parser_v1")
    protected String cronExpression;
    @XmlElement(name = "no_save_empty_rows", namespace = "historical_element_parser_v1")
    protected Boolean noSaveEmptyRows;
    @XmlElement(namespace = "historical_element_parser_v1", required = true)
    protected HistoricalElementParserV1 .Fields fields;
    @XmlAttribute(name = "element_type_id", required = true)
    @XmlSchemaType(name = "unsignedByte")
    protected short elementTypeId;

    /**
     * Gets the value of the targetTable property.
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
     * Sets the value of the targetTable property.
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
     * Gets the value of the tableSpace property.
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
     * Sets the value of the tableSpace property.
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
     * Gets the value of the fieldElementId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldElementId() {
        return fieldElementId;
    }

    /**
     * Sets the value of the fieldElementId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldElementId(String value) {
        this.fieldElementId = value;
    }

    /**
     * Gets the value of the fieldTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldTimestamp() {
        return fieldTimestamp;
    }

    /**
     * Sets the value of the fieldTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldTimestamp(String value) {
        this.fieldTimestamp = value;
    }

    /**
     * Gets the value of the cronExpression property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * Sets the value of the cronExpression property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCronExpression(String value) {
        this.cronExpression = value;
    }

    /**
     * Gets the value of the noSaveEmptyRows property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoSaveEmptyRows() {
        return noSaveEmptyRows;
    }

    /**
     * Sets the value of the noSaveEmptyRows property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoSaveEmptyRows(Boolean value) {
        this.noSaveEmptyRows = value;
    }

    /**
     * Gets the value of the fields property.
     * 
     * @return
     *     possible object is
     *     {@link HistoricalElementParserV1 .Fields }
     *     
     */
    public HistoricalElementParserV1 .Fields getFields() {
        return fields;
    }

    /**
     * Sets the value of the fields property.
     * 
     * @param value
     *     allowed object is
     *     {@link HistoricalElementParserV1 .Fields }
     *     
     */
    public void setFields(HistoricalElementParserV1 .Fields value) {
        this.fields = value;
    }

    /**
     * Gets the value of the elementTypeId property.
     * 
     */
    public short getElementTypeId() {
        return elementTypeId;
    }

    /**
     * Sets the value of the elementTypeId property.
     * 
     */
    public void setElementTypeId(short value) {
        this.elementTypeId = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
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
     *                   &lt;element name="element_type_param"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="element_type_param_id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
     *                             &lt;element name="param_type_id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
     *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="pk" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
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

        @XmlElement(namespace = "historical_element_parser_v1", required = true)
        protected List<HistoricalElementParserV1 .Fields.Field> field;

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
         * {@link HistoricalElementParserV1 .Fields.Field }
         * 
         * 
         */
        public List<HistoricalElementParserV1 .Fields.Field> getField() {
            if (field == null) {
                field = new ArrayList<HistoricalElementParserV1 .Fields.Field>();
            }
            return this.field;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;element name="element_type_param"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="element_type_param_id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
         *                   &lt;element name="param_type_id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/sequence&gt;
         *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="pk" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
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
            "elementTypeParam"
        })
        public static class Field {

            @XmlElement(name = "element_type_param", namespace = "historical_element_parser_v1", required = true)
            protected HistoricalElementParserV1 .Fields.Field.ElementTypeParam elementTypeParam;
            @XmlAttribute(name = "name", required = true)
            protected String name;
            @XmlAttribute(name = "pk")
            protected Boolean pk;
            @XmlAttribute(name = "indexed")
            protected Boolean indexed;

            /**
             * Gets the value of the elementTypeParam property.
             * 
             * @return
             *     possible object is
             *     {@link HistoricalElementParserV1 .Fields.Field.ElementTypeParam }
             *     
             */
            public HistoricalElementParserV1 .Fields.Field.ElementTypeParam getElementTypeParam() {
                return elementTypeParam;
            }

            /**
             * Sets the value of the elementTypeParam property.
             * 
             * @param value
             *     allowed object is
             *     {@link HistoricalElementParserV1 .Fields.Field.ElementTypeParam }
             *     
             */
            public void setElementTypeParam(HistoricalElementParserV1 .Fields.Field.ElementTypeParam value) {
                this.elementTypeParam = value;
            }

            /**
             * Gets the value of the name property.
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
             * Sets the value of the name property.
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
             * Gets the value of the pk property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isPk() {
                return pk;
            }

            /**
             * Sets the value of the pk property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setPk(Boolean value) {
                this.pk = value;
            }

            /**
             * Gets the value of the indexed property.
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
             * Sets the value of the indexed property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setIndexed(Boolean value) {
                this.indexed = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="element_type_param_id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
             *         &lt;element name="param_type_id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/&gt;
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
            public static class ElementTypeParam {

                @XmlElement(name = "element_type_param_id", namespace = "historical_element_parser_v1")
                @XmlSchemaType(name = "unsignedByte")
                protected short elementTypeParamId;
                @XmlElement(name = "param_type_id", namespace = "historical_element_parser_v1")
                @XmlSchemaType(name = "unsignedByte")
                protected short paramTypeId;

                /**
                 * Gets the value of the elementTypeParamId property.
                 * 
                 */
                public short getElementTypeParamId() {
                    return elementTypeParamId;
                }

                /**
                 * Sets the value of the elementTypeParamId property.
                 * 
                 */
                public void setElementTypeParamId(short value) {
                    this.elementTypeParamId = value;
                }

                /**
                 * Gets the value of the paramTypeId property.
                 * 
                 */
                public short getParamTypeId() {
                    return paramTypeId;
                }

                /**
                 * Sets the value of the paramTypeId property.
                 * 
                 */
                public void setParamTypeId(short value) {
                    this.paramTypeId = value;
                }

            }

        }

    }

}
