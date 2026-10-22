
package com.revenga.rits.back.adapter.lib.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CL_Type_Alarm complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CL_Type_Alarm"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Type" type="{Citilog-CommonTypes}CL_Enum_AlarmType" minOccurs="0"/&gt;
 *         &lt;element name="CameraName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CameraId" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/&gt;
 *         &lt;element name="LaneId" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/&gt;
 *         &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EndTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RowRatio" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/&gt;
 *         &lt;element name="ColumnRatio" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/&gt;
 *         &lt;element name="ExtraAlarmData" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Danger" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/&gt;
 *                   &lt;element name="Material" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" minOccurs="0"/&gt;
 *                   &lt;element name="Reliability" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Id" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CL_Type_Alarm", propOrder = {
    "type",
    "cameraName",
    "cameraId",
    "laneId",
    "startTime",
    "endTime",
    "comment",
    "rowRatio",
    "columnRatio",
    "extraAlarmData"
})
public class CLTypeAlarm {

    @XmlElement(name = "Type")
    @XmlSchemaType(name = "string")
    protected CLEnumAlarmType type;
    @XmlElement(name = "CameraName")
    protected String cameraName;
    @XmlElement(name = "CameraId")
    @XmlSchemaType(name = "unsignedByte")
    protected Short cameraId;
    @XmlElement(name = "LaneId")
    @XmlSchemaType(name = "unsignedByte")
    protected Short laneId;
    @XmlElement(name = "StartTime")
    protected String startTime;
    @XmlElement(name = "EndTime")
    protected String endTime;
    @XmlElement(name = "Comment")
    protected String comment;
    @XmlElement(name = "RowRatio")
    protected Byte rowRatio;
    @XmlElement(name = "ColumnRatio")
    protected Byte columnRatio;
    @XmlElement(name = "ExtraAlarmData")
    protected CLTypeAlarm.ExtraAlarmData extraAlarmData;
    @XmlAttribute(name = "Id", required = true)
    @XmlSchemaType(name = "unsignedByte")
    protected short id;

    /**
     * Obtiene el valor de la propiedad type.
     * 
     * @return
     *     possible object is
     *     {@link CLEnumAlarmType }
     *     
     */
    public CLEnumAlarmType getType() {
        return type;
    }

    /**
     * Define el valor de la propiedad type.
     * 
     * @param value
     *     allowed object is
     *     {@link CLEnumAlarmType }
     *     
     */
    public void setType(CLEnumAlarmType value) {
        this.type = value;
    }

    /**
     * Obtiene el valor de la propiedad cameraName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCameraName() {
        return cameraName;
    }

    /**
     * Define el valor de la propiedad cameraName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCameraName(String value) {
        this.cameraName = value;
    }

    /**
     * Obtiene el valor de la propiedad cameraId.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCameraId() {
        return cameraId;
    }

    /**
     * Define el valor de la propiedad cameraId.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCameraId(Short value) {
        this.cameraId = value;
    }

    /**
     * Obtiene el valor de la propiedad laneId.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getLaneId() {
        return laneId;
    }

    /**
     * Define el valor de la propiedad laneId.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setLaneId(Short value) {
        this.laneId = value;
    }

    /**
     * Obtiene el valor de la propiedad startTime.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Define el valor de la propiedad startTime.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(String value) {
        this.startTime = value;
    }

    /**
     * Obtiene el valor de la propiedad endTime.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Define el valor de la propiedad endTime.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndTime(String value) {
        this.endTime = value;
    }

    /**
     * Obtiene el valor de la propiedad comment.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Define el valor de la propiedad comment.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Obtiene el valor de la propiedad rowRatio.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getRowRatio() {
        return rowRatio;
    }

    /**
     * Define el valor de la propiedad rowRatio.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setRowRatio(Byte value) {
        this.rowRatio = value;
    }

    /**
     * Obtiene el valor de la propiedad columnRatio.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getColumnRatio() {
        return columnRatio;
    }

    /**
     * Define el valor de la propiedad columnRatio.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setColumnRatio(Byte value) {
        this.columnRatio = value;
    }

    /**
     * Obtiene el valor de la propiedad extraAlarmData.
     * 
     * @return
     *     possible object is
     *     {@link CLTypeAlarm.ExtraAlarmData }
     *     
     */
    public CLTypeAlarm.ExtraAlarmData getExtraAlarmData() {
        return extraAlarmData;
    }

    /**
     * Define el valor de la propiedad extraAlarmData.
     * 
     * @param value
     *     allowed object is
     *     {@link CLTypeAlarm.ExtraAlarmData }
     *     
     */
    public void setExtraAlarmData(CLTypeAlarm.ExtraAlarmData value) {
        this.extraAlarmData = value;
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
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Danger" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/&gt;
     *         &lt;element name="Material" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" minOccurs="0"/&gt;
     *         &lt;element name="Reliability" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/&gt;
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
        "danger",
        "material",
        "reliability"
    })
    public static class ExtraAlarmData {

        @XmlElement(name = "Danger")
        @XmlSchemaType(name = "unsignedByte")
        protected Short danger;
        @XmlElement(name = "Material")
        @XmlSchemaType(name = "unsignedShort")
        protected Integer material;
        @XmlElement(name = "Reliability")
        protected Byte reliability;

        /**
         * Obtiene el valor de la propiedad danger.
         * 
         * @return
         *     possible object is
         *     {@link Short }
         *     
         */
        public Short getDanger() {
            return danger;
        }

        /**
         * Define el valor de la propiedad danger.
         * 
         * @param value
         *     allowed object is
         *     {@link Short }
         *     
         */
        public void setDanger(Short value) {
            this.danger = value;
        }

        /**
         * Obtiene el valor de la propiedad material.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getMaterial() {
            return material;
        }

        /**
         * Define el valor de la propiedad material.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setMaterial(Integer value) {
            this.material = value;
        }

        /**
         * Obtiene el valor de la propiedad reliability.
         * 
         * @return
         *     possible object is
         *     {@link Byte }
         *     
         */
        public Byte getReliability() {
            return reliability;
        }

        /**
         * Define el valor de la propiedad reliability.
         * 
         * @param value
         *     allowed object is
         *     {@link Byte }
         *     
         */
        public void setReliability(Byte value) {
            this.reliability = value;
        }

    }

}
