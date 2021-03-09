package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the tree_params database table.
 * 
 */
@Entity
@Table(name="tree_params")
@NamedQuery(name="TreeParam.findAll", query="SELECT t FROM TreeParam t")
public class TreeParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String name;
	
	//bi-directional many-to-one association to TreeCommand
	@OneToMany(mappedBy="treeParam")
	private List<TreeParamStyle> treeParamStyles;

	//bi-directional many-to-one association to Tree
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="tree_id")
	private Tree tree;

	public TreeParam() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<TreeParamStyle> getTreeParamStyles() {
		return this.treeParamStyles;
	}

	public void setTreeParamStyles(List<TreeParamStyle> treeParamStyles) {
		this.treeParamStyles = treeParamStyles;
	}

	public TreeParamStyle addTreeParamStyle(TreeParamStyle treeParamStyle) {
		getTreeParamStyles().add(treeParamStyle);
		treeParamStyle.setTreeParam(this);

		return treeParamStyle;
	}

	public TreeParamStyle removeTreeParamStyle(TreeParamStyle treeParamStyle) {
		getTreeParamStyles().remove(treeParamStyle);
		treeParamStyle.setTreeParam(null);

		return treeParamStyle;
	}

	public Tree getTree() {
		return this.tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

}