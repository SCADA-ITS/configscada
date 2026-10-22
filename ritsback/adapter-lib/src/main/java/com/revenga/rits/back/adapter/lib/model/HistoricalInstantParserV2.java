
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
 *         &lt;element name="target_table" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="table_space" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                             &lt;element name="soure_field_value" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="source_entity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="references" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="reference" maxOccurs="unbounded"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;attribute name="sourceField" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                                               &lt;attribute name="targetField" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
 *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
 *       &lt;attribute name="model" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
    "fields"
})
@XmlRootElement(name = "historical_instant_parser_v2", namespace = "historical_instant_parser_v2")
public class HistoricalInstantParserV2 {

    @XmlElement(name = "target_table", namespace = "historical_instant_parser_v2", required = true)
    protected String targetTable;
    @XmlElement(name = "table_space", namespace = "historical_instant_parser_v2", required = true)
    protected String tableSpace;
    @XmlElement(namespace = "historical_instant_parser_v2", required = true)
    protected HistoricalInstantParserV2 .Fields fields;
    @XmlAttribute(name = "model", required = true)
    protected String model;

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
     * Obtiene el valor de la propiedad fields.
     * 
     * @return
     *     possible object is
     *     {@link HistoricalInstantParserV2 .Fields }
     *     
     */
    public HistoricalInstantParserV2 .Fields getFields() {
        return fields;
    }

    /**
     * Define el valor de la propiedad fields.
     * 
     * @param value
     *     allowed object is
     *     {@link HistoricalInstantParserV2 .Fields }
     *     
     */
    public void setFields(HistoricalInstantParserV2 .Fields value) {
        this.fields = value;
    }

    /**
     * Obtiene el valor de la propiedad model.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Define el valor de la propiedad model.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
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
     *                   &lt;element name="soure_field_value" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="source_entity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="references" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="reference" maxOccurs="unbounded"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;attribute name="sourceField" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                                     &lt;attribute name="targetField" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
     *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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

        @XmlElement(namespace = "historical_instant_parser_v2", required = true)
        protected List<HistoricalInstantParserV2 .Fields.Field> field;

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
         * {@link HistoricalInstantParserV2 .Fields.Field }
         * 
         * 
         */
        public List<HistoricalInstantParserV2 .Fields.Field> getField() {
            if (field == null) {
                field = new ArrayList<HistoricalInstantParserV2 .Fields.Field>();
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
         *         &lt;element name="soure_field_value" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="source_entity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="references" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="reference" maxOccurs="unbounded"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;attribute name="sourceField" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *                           &lt;attribute name="targetField" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "soureFieldValue",
            "sourceEntity",
            "references"
        })
        public static class Field {

            @XmlElement(name = "soure_field_value", namespace = "historical_instant_parser_v2", required = true)
            protected String soureFieldValue;
            @XmlElement(name = "source_entity", namespace = "historical_instant_parser_v2")
            protected String sourceEntity;
            @XmlElement(namespace = "historical_instant_parser_v2")
            protected HistoricalInstantParserV2 .Fields.Field.References references;
            @XmlAttribute(name = "name", required = true)
            protected String name;

            /**
             * Obtiene el valor de la propiedad soureFieldValue.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSoureFieldValue() {
                return soureFieldValue;
            }

            /**
             * Define el valor de la propiedad soureFieldValue.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSoureFieldValue(String value) {
                this.soureFieldValue = value;
            }

            /**
             * Obtiene el valor de la propiedad sourceEntity.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSourceEntity() {
                return sourceEntity;
            }

            /**
             * Define el valor de la propiedad sourceEntity.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSourceEntity(String value) {
                this.sourceEntity = value;
            }

            /**
             * Obtiene el valor de la propiedad references.
             * 
             * @return
             *     possible object is
             *     {@link HistoricalInstantParserV2 .Fields.Field.References }
             *     
             */
            public HistoricalInstantParserV2 .Fields.Field.References getReferences() {
                return references;
            }

            /**
             * Define el valor de la propiedad references.
             * 
             * @param value
             *     allowed object is
             *     {@link HistoricalInstantParserV2 .Fields.Field.References }
             *     
             */
            public void setReferences(HistoricalInstantParserV2 .Fields.Field.References value) {
                this.references = value;
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
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="reference" maxOccurs="unbounded"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;attribute name="sourceField" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
             *                 &lt;attribute name="targetField" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
                "reference"
            })
            public static class References {

                @XmlElement(namespace = "historical_instant_parser_v2", required = true)
                protected List<HistoricalInstantParserV2 .Fields.Field.References.Reference> reference;

                /**
                 * Gets the value of the reference property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the reference property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getReference().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link HistoricalInstantParserV2 .Fields.Field.References.Reference }
                 * 
                 * 
                 */
                public List<HistoricalInstantParserV2 .Fields.Field.References.Reference> getReference() {
                    if (reference == null) {
                        reference = new ArrayList<HistoricalInstantParserV2 .Fields.Field.References.Reference>();
                    }
                    return this.reference;
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
                 *       &lt;attribute name="sourceField" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
                 *       &lt;attribute name="targetField" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
                 *     &lt;/restriction&gt;
                 *   &lt;/complexContent&gt;
                 * &lt;/complexType&gt;
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "")
                public static class Reference {

                    @XmlAttribute(name = "sourceField", required = true)
                    protected String sourceField;
                    @XmlAttribute(name = "targetField", required = true)
                    protected String targetField;

                    /**
                     * Obtiene el valor de la propiedad sourceField.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSourceField() {
                        return sourceField;
                    }

                    /**
                     * Define el valor de la propiedad sourceField.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSourceField(String value) {
                        this.sourceField = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad targetField.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getTargetField() {
                        return targetField;
                    }

                    /**
                     * Define el valor de la propiedad targetField.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setTargetField(String value) {
                        this.targetField = value;
                    }

                }

            }

        }

    }

}
