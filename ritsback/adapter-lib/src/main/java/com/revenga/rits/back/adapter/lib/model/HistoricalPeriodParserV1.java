
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
 *                                       &lt;element name="element_type_param_id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *                                       &lt;element name="param_type_id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
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
 *       &lt;attribute name="element_type_id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "fields"
})
@XmlRootElement(name = "historical_period_parser_v1", namespace = "historical_period_parser_v1")
public class HistoricalPeriodParserV1 {

    @XmlElement(namespace = "historical_period_parser_v1", required = true)
    protected HistoricalPeriodParserV1 .Fields fields;
    @XmlAttribute(name = "element_type_id", required = true)
    protected long elementTypeId;

    /**
     * Obtiene el valor de la propiedad fields.
     * 
     * @return
     *     possible object is
     *     {@link HistoricalPeriodParserV1 .Fields }
     *     
     */
    public HistoricalPeriodParserV1 .Fields getFields() {
        return fields;
    }

    /**
     * Define el valor de la propiedad fields.
     * 
     * @param value
     *     allowed object is
     *     {@link HistoricalPeriodParserV1 .Fields }
     *     
     */
    public void setFields(HistoricalPeriodParserV1 .Fields value) {
        this.fields = value;
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
     *                   &lt;element name="element_type_param"&gt;
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

        @XmlElement(namespace = "historical_period_parser_v1", required = true)
        protected List<HistoricalPeriodParserV1 .Fields.Field> field;

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
         * {@link HistoricalPeriodParserV1 .Fields.Field }
         * 
         * 
         */
        public List<HistoricalPeriodParserV1 .Fields.Field> getField() {
            if (field == null) {
                field = new ArrayList<HistoricalPeriodParserV1 .Fields.Field>();
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
         *         &lt;element name="element_type_param"&gt;
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
            "elementTypeParam"
        })
        public static class Field {

            @XmlElement(name = "element_type_param", namespace = "historical_period_parser_v1", required = true)
            protected HistoricalPeriodParserV1 .Fields.Field.ElementTypeParam elementTypeParam;
            @XmlAttribute(name = "name", required = true)
            protected String name;

            /**
             * Obtiene el valor de la propiedad elementTypeParam.
             * 
             * @return
             *     possible object is
             *     {@link HistoricalPeriodParserV1 .Fields.Field.ElementTypeParam }
             *     
             */
            public HistoricalPeriodParserV1 .Fields.Field.ElementTypeParam getElementTypeParam() {
                return elementTypeParam;
            }

            /**
             * Define el valor de la propiedad elementTypeParam.
             * 
             * @param value
             *     allowed object is
             *     {@link HistoricalPeriodParserV1 .Fields.Field.ElementTypeParam }
             *     
             */
            public void setElementTypeParam(HistoricalPeriodParserV1 .Fields.Field.ElementTypeParam value) {
                this.elementTypeParam = value;
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
            public static class ElementTypeParam {

                @XmlElement(name = "element_type_param_id", namespace = "historical_period_parser_v1")
                protected long elementTypeParamId;
                @XmlElement(name = "param_type_id", namespace = "historical_period_parser_v1")
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

}
