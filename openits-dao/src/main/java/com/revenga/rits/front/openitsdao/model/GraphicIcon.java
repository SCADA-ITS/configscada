package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the graphic_icons database table.
 * 
 */
@Entity
@Table(name="graphic_icons")
@NamedQuery(name="GraphicIcon.findAll", query="SELECT g FROM GraphicIcon g")
public class GraphicIcon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Boolean alarms;

	private String description;

	private Float height;

	@Column(name="js_draw_file")
	private String jsDrawFile;

	private Boolean measures;

	private String name;

	@Column(name="svg_pattern_var")
	private String svgPatternVar;

	private Float width;

	public GraphicIcon() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getAlarms() {
		return alarms;
	}

	public void setAlarms(Boolean alarms) {
		this.alarms = alarms;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public String getJsDrawFile() {
		return jsDrawFile;
	}

	public void setJsDrawFile(String jsDrawFile) {
		this.jsDrawFile = jsDrawFile;
	}

	public Boolean getMeasures() {
		return measures;
	}

	public void setMeasures(Boolean measures) {
		this.measures = measures;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSvgPatternVar() {
		return svgPatternVar;
	}

	public void setSvgPatternVar(String svgPatternVar) {
		this.svgPatternVar = svgPatternVar;
	}

	public Float getWidth() {
		return width;
	}

	public void setWidth(Float width) {
		this.width = width;
	}
	
}