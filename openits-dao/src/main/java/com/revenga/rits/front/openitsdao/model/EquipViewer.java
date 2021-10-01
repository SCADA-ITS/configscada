package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the equip_viewers database table.
 * 
 */
@Entity
@Table(name="equip_viewers")
@NamedQuery(name="EquipViewer.findAll", query="SELECT e FROM EquipViewer e")
public class EquipViewer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private String description;
	
	//uni-directional many-to-one association to GraphicIcon
	@ManyToOne
	@JoinColumn(name="graphic_icon_id")
	private GraphicIcon graphicIcon;

	private String icon;
	
	@Column(name="initial_tab")
	private Integer initialTab;

	private String label;
	
	@Column(name="measure_max_rows")
	private Integer measureMaxRows;
	
	@Column(name="measure_only")
	private Boolean measureOnly;
	
	@Column(name="measure_width")
	private Integer measureWidth;

	private String name;
	
	@Column(name="show_actions")
	private Boolean showActions;

	@Column(name="show_childs")
	private Boolean showChilds;

	@Column(name="show_info_tab")
	private Boolean showInfoTab;
	
	@Column(name="no_show_measures")
	private Boolean noShowMeasures;
	
	//bi-directional many-to-one association to EquipViewerConfig
	@OneToMany(mappedBy="equipViewer")
	private List<EquipViewerConfig> equipViewerConfigs;

	//bi-directional many-to-one association to EquipViewerGrpMeasure
	@OneToMany(mappedBy="equipViewer")
	private List<EquipViewerGrpMeasure> equipViewerGrpMeasures;

	//bi-directional many-to-one association to EquipViewerTab
	@OneToMany(mappedBy="equipViewer")
	private List<EquipViewerTab> equipViewerTabs;

	//bi-directional one-to-one association to VoEntity
	@OneToOne
	@JoinColumn(name="id")
	private VoEntity voEntity;

	public EquipViewer() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public GraphicIcon getGraphicIcon() {
		return graphicIcon;
	}

	public void setGraphicIcon(GraphicIcon graphicIcon) {
		this.graphicIcon = graphicIcon;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public Integer getInitialTab() {
		return this.initialTab;
	}

	public void setInitialTab(Integer initialTab) {
		this.initialTab = initialTab;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public Integer getMeasureMaxRows() {
		return this.measureMaxRows;
	}

	public void setMeasureMaxRows(Integer measureMaxRows) {
		this.measureMaxRows = measureMaxRows;
	}
	
	public Boolean getMeasureOnly() {
		return measureOnly;
	}

	public void setMeasureOnly(Boolean measureOnly) {
		this.measureOnly = measureOnly;
	}

	public Integer getMeasureWidth() {
		return this.measureWidth;
	}

	public void setMeasureWidth(Integer measureWidth) {
		this.measureWidth = measureWidth;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getShowActions() {
		return showActions;
	}

	public void setShowActions(Boolean showActions) {
		this.showActions = showActions;
	}

	public Boolean getShowChilds() {
		return this.showChilds;
	}

	public void setShowChilds(Boolean showChilds) {
		this.showChilds = showChilds;
	}

	public Boolean getShowInfoTab() {
		return this.showInfoTab;
	}

	public void setShowInfoTab(Boolean showInfoTab) {
		this.showInfoTab = showInfoTab;
	}
	
	public Boolean getNoShowMeasures() {
		return this.noShowMeasures;
	}

	public void setNoShowMeasures(Boolean noShowMeasures) {
		this.noShowMeasures = noShowMeasures;
	}
	
	public List<EquipViewerConfig> getEquipViewerConfigs() {
		return this.equipViewerConfigs;
	}

	public void setEquipViewerConfigs(List<EquipViewerConfig> equipViewerConfigs) {
		this.equipViewerConfigs = equipViewerConfigs;
	}

	public EquipViewerConfig addEquipViewerConfig(EquipViewerConfig equipViewerConfig) {
		getEquipViewerConfigs().add(equipViewerConfig);
		equipViewerConfig.setEquipViewer(this);

		return equipViewerConfig;
	}

	public EquipViewerConfig removeEquipViewerConfig(EquipViewerConfig equipViewerConfig) {
		getEquipViewerConfigs().remove(equipViewerConfig);
		equipViewerConfig.setEquipViewer(null);

		return equipViewerConfig;
	}

	public List<EquipViewerGrpMeasure> getEquipViewerGrpMeasures() {
		return this.equipViewerGrpMeasures;
	}

	public void setEquipViewerGrpMeasures(List<EquipViewerGrpMeasure> equipViewerGrpMeasures) {
		this.equipViewerGrpMeasures = equipViewerGrpMeasures;
	}

	public EquipViewerGrpMeasure addEquipViewerGrpMeasure(EquipViewerGrpMeasure equipViewerGrpMeasure) {
		getEquipViewerGrpMeasures().add(equipViewerGrpMeasure);
		equipViewerGrpMeasure.setEquipViewer(this);

		return equipViewerGrpMeasure;
	}

	public EquipViewerGrpMeasure removeEquipViewerGrpMeasure(EquipViewerGrpMeasure equipViewerGrpMeasure) {
		getEquipViewerGrpMeasures().remove(equipViewerGrpMeasure);
		equipViewerGrpMeasure.setEquipViewer(null);

		return equipViewerGrpMeasure;
	}

	public List<EquipViewerTab> getEquipViewerTabs() {
		return this.equipViewerTabs;
	}

	public void setEquipViewerTabs(List<EquipViewerTab> equipViewerTabs) {
		this.equipViewerTabs = equipViewerTabs;
	}

	public EquipViewerTab addEquipViewerTab(EquipViewerTab equipViewerTab) {
		getEquipViewerTabs().add(equipViewerTab);
		equipViewerTab.setEquipViewer(this);

		return equipViewerTab;
	}

	public EquipViewerTab removeEquipViewerTab(EquipViewerTab equipViewerTab) {
		getEquipViewerTabs().remove(equipViewerTab);
		equipViewerTab.setEquipViewer(null);

		return equipViewerTab;
	}

	public VoEntity getVoEntity() {
		return this.voEntity;
	}

	public void setVoEntity(VoEntity voEntity) {
		this.voEntity = voEntity;
	}

}