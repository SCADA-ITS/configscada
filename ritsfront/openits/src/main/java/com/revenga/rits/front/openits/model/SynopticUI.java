package com.revenga.rits.front.openits.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revenga.rits.front.openitsdao.model.Synoptic;

import lombok.Data;

@Data
public class SynopticUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 540043917441553439L;
	
	@JsonProperty("metadata")
	Synoptic synoptic;
	List<Map<String, Object>> data;
}
