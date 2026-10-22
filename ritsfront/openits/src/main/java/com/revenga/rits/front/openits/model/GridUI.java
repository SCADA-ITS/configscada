package com.revenga.rits.front.openits.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revenga.rits.front.openitsdao.model.Grid;

import lombok.Data;

@Data
public class GridUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1783450175806606694L;
	
	@JsonProperty("metadata")
	Grid grid;
	List<Map<String, Object>> data;
}
