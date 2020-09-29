package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the data_sources database table.
 * 
 */
@Entity
@Table(name="data_sources")
@NamedQuery(name="DataSource.findAll", query="SELECT d FROM DataSource d")
public class DataSource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String description;

	private Boolean enabled;

	@Column(name="entity_vo")
	private String entityVo;

	private String name;

	private Boolean visible;

	//bi-directional many-to-one association to DataSourceValue
	@OneToMany(mappedBy="dataSource")
	private List<DataSourceValue> dataSourceValues;

	//uni-directional many-to-one association to DataSourceType
	@ManyToOne
	@JoinColumn(name="data_source_type_id")
	private DataSourceType dataSourceType;

	public DataSource() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getEntityVo() {
		return this.entityVo;
	}

	public void setEntityVo(String entityVo) {
		this.entityVo = entityVo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public List<DataSourceValue> getDataSourceValues() {
		return this.dataSourceValues;
	}

	public void setDataSourceValues(List<DataSourceValue> dataSourceValues) {
		this.dataSourceValues = dataSourceValues;
	}

	public DataSourceValue addDataSourceValue(DataSourceValue dataSourceValue) {
		getDataSourceValues().add(dataSourceValue);
		dataSourceValue.setDataSource(this);

		return dataSourceValue;
	}

	public DataSourceValue removeDataSourceValue(DataSourceValue dataSourceValue) {
		getDataSourceValues().remove(dataSourceValue);
		dataSourceValue.setDataSource(null);

		return dataSourceValue;
	}

	public DataSourceType getDataSourceType() {
		return this.dataSourceType;
	}

	public void setDataSourceType(DataSourceType dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

}