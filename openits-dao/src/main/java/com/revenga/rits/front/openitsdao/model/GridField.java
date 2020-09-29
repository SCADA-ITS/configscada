package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the grid_fields database table.
 * 
 */
@Entity
@Table(name="grid_fields")
@NamedQuery(name="GridField.findAll", query="SELECT g FROM GridField g")
public class GridField implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Boolean enabled;

	private Boolean extended;

	private Boolean filtered;

	@Column(name="grouping_summary")
	private Boolean groupingSummary;

	private Boolean ispk;

	private String label;
	
	private String labelTooltip;

	@Column(name="needs_translation")
	private Boolean needsTranslation;

	private Boolean parameterized;

	private Long position;

	private Boolean required;

	@Column(name="source_field")
	private String sourceField;

	private Boolean visible;

	@Column(name="width_px")
	private Long widthPx;

	//bi-directional many-to-one association to GridFieldIcon
	@OneToMany(mappedBy="gridField")
	private List<GridFieldIcon> gridFieldIcons;

	//bi-directional many-to-one association to GridFieldReplacement
	@OneToMany(mappedBy="gridField")
	private List<GridFieldReplacement> gridFieldReplacements;

	//uni-directional many-to-one association to GridFieldDataType
	@ManyToOne
	@JoinColumn(name="grid_field_data_type_id")
	private GridFieldDataType gridFieldDataType;

	//uni-directional many-to-one association to GridFieldGroupOperation
	@ManyToOne
	@JoinColumn(name="grid_field_group_operation_id")
	private GridFieldGroupOperation gridFieldGroupOperation;

	//bi-directional many-to-one association to Grid
	@JsonIgnore
	@ManyToOne
	private Grid grid;

	public GridField() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getExtended() {
		return this.extended;
	}

	public void setExtended(Boolean extended) {
		this.extended = extended;
	}

	public Boolean getFiltered() {
		return this.filtered;
	}

	public void setFiltered(Boolean filtered) {
		this.filtered = filtered;
	}

	public Boolean getGroupingSummary() {
		return this.groupingSummary;
	}

	public void setGroupingSummary(Boolean groupingSummary) {
		this.groupingSummary = groupingSummary;
	}

	public Boolean getIspk() {
		return this.ispk;
	}

	public void setIspk(Boolean ispk) {
		this.ispk = ispk;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabelTooltip() {
		return this.labelTooltip;
	}

	public void setLabelTooltip(String labelTooltip) {
		this.labelTooltip = labelTooltip;
	}

	public Boolean getNeedsTranslation() {
		return this.needsTranslation;
	}

	public void setNeedsTranslation(Boolean needsTranslation) {
		this.needsTranslation = needsTranslation;
	}

	public Boolean getParameterized() {
		return this.parameterized;
	}

	public void setParameterized(Boolean parameterized) {
		this.parameterized = parameterized;
	}

	public Long getPosition() {
		return this.position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public Boolean getRequired() {
		return this.required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getSourceField() {
		return this.sourceField;
	}

	public void setSourceField(String sourceField) {
		this.sourceField = sourceField;
	}

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Long getWidthPx() {
		return this.widthPx;
	}

	public void setWidthPx(Long widthPx) {
		this.widthPx = widthPx;
	}

	public List<GridFieldIcon> getGridFieldIcons() {
		return this.gridFieldIcons;
	}

	public void setGridFieldIcons(List<GridFieldIcon> gridFieldIcons) {
		this.gridFieldIcons = gridFieldIcons;
	}

	public GridFieldIcon addGridFieldIcon(GridFieldIcon gridFieldIcon) {
		getGridFieldIcons().add(gridFieldIcon);
		gridFieldIcon.setGridField(this);

		return gridFieldIcon;
	}

	public GridFieldIcon removeGridFieldIcon(GridFieldIcon gridFieldIcon) {
		getGridFieldIcons().remove(gridFieldIcon);
		gridFieldIcon.setGridField(null);

		return gridFieldIcon;
	}

	public List<GridFieldReplacement> getGridFieldReplacements() {
		return this.gridFieldReplacements;
	}

	public void setGridFieldReplacements(List<GridFieldReplacement> gridFieldReplacements) {
		this.gridFieldReplacements = gridFieldReplacements;
	}

	public GridFieldReplacement addGridFieldReplacement(GridFieldReplacement gridFieldReplacement) {
		getGridFieldReplacements().add(gridFieldReplacement);
		gridFieldReplacement.setGridField(this);

		return gridFieldReplacement;
	}

	public GridFieldReplacement removeGridFieldReplacement(GridFieldReplacement gridFieldReplacement) {
		getGridFieldReplacements().remove(gridFieldReplacement);
		gridFieldReplacement.setGridField(null);

		return gridFieldReplacement;
	}

	public GridFieldDataType getGridFieldDataType() {
		return this.gridFieldDataType;
	}

	public void setGridFieldDataType(GridFieldDataType gridFieldDataType) {
		this.gridFieldDataType = gridFieldDataType;
	}

	public GridFieldGroupOperation getGridFieldGroupOperation() {
		return this.gridFieldGroupOperation;
	}

	public void setGridFieldGroupOperation(GridFieldGroupOperation gridFieldGroupOperation) {
		this.gridFieldGroupOperation = gridFieldGroupOperation;
	}

	public Grid getGrid() {
		return this.grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

}