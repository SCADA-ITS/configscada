package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the backoffice database table.
 * 
 */
@Entity
@Table(name="backoffices")
@NamedQuery(name="BackOffice.findAll", query="SELECT d FROM BackOffice d")
public class BackOffice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String name;
	
	private String description;
	
	private String label;
	
	private String jdbc;
	
	private String user;
	
	private String password;
	
	private String schema;
	
	@Column(name="default_metadata_backoffice")
	private String defaultMetadataBackoffice;
	
	@Column(name="default_metadata_table")
	private String defaultMetadataTable;
	
	@Column(name="default_metadata_column")
	private String defaultMetadataColumn;

	private Boolean enabled;
	
	public BackOffice() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getJdbc() {
		return jdbc;
	}

	public void setJdbc(String jdbc) {
		this.jdbc = jdbc;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getDefaultMetadataBackoffice() {
		return defaultMetadataBackoffice;
	}

	public void setDefaultMetadataBackoffice(String defaultMetadataBackoffice) {
		this.defaultMetadataBackoffice = defaultMetadataBackoffice;
	}

	public String getDefaultMetadataTable() {
		return defaultMetadataTable;
	}

	public void setDefaultMetadataTable(String defaultMetadataTable) {
		this.defaultMetadataTable = defaultMetadataTable;
	}

	public String getDefaultMetadataColumn() {
		return defaultMetadataColumn;
	}

	public void setDefaultMetadataColumn(String defaultMetadataColumn) {
		this.defaultMetadataColumn = defaultMetadataColumn;
	}
}