package com.revenga.rits.front.openits.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revenga.rits.front.openitsdao.model.Tree;

import lombok.Data;

@Entity
@Data
public class TreeUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1783450175806606694L;
	
	@JsonProperty("metadata")
	private Tree tree;
	private List<TreeDataUI> data;
}
