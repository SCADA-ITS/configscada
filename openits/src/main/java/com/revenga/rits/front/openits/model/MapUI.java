package com.revenga.rits.front.openits.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Data
public class MapUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9172778465627488055L;
	
	@JsonProperty("metadata")
	com.revenga.rits.front.openitsdao.model.Map map;
	List<Map<String, Object>> data;
}
