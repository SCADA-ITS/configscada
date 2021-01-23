package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the window_values database table.
 * 
 */
@Entity
@Table(name="window_values")
@NamedQuery(name="WindowValue.findAll", query="SELECT w FROM WindowValue w")
public class WindowValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String value;

	//bi-directional many-to-one association to Module
	@JsonIgnore
	@ManyToOne
	private Module module;

	//uni-directional many-to-one association to WindowParam
	@ManyToOne
	@JoinColumn(name="window_param_id")
	private WindowParam windowParam;

	public WindowValue() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Module getModule() {
		return this.module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public WindowParam getWindowParam() {
		return this.windowParam;
	}

	public void setWindowParam(WindowParam windowParam) {
		this.windowParam = windowParam;
	}

}
