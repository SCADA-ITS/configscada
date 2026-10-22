package com.revenga.rits.front.openitsdao.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the map_incidents database table.
 * 
 */
@Entity
@Table(name = "map_incidents")
@NamedQuery(name = "MapIncident.findAll", query = "SELECT m FROM MapIncident m")
public class MapIncident implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Integer id;
	
	//uni-directional many-to-one association to GraphicIcon
	@ManyToOne
	@JoinColumn(name="incident_graphic_icon_id")
	private GraphicIcon incidentGraphicIcon;
	
	//uni-directional many-to-one association to ModuleAction
	@ManyToOne
	@JoinColumn(name="incident_report_module_action_id")
	private ModuleAction incidentReportModuleAction;
	
	//uni-directional many-to-one association to ModuleAction
	@ManyToOne
	@JoinColumn(name="incident_wizard_module_action_id")
	private ModuleAction incidentWizardModuleAction;
	
	public MapIncident() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public GraphicIcon getIncidentGraphicIcon() {
		return incidentGraphicIcon;
	}

	public void setIncidentGraphicIcon(GraphicIcon incidentGraphicIcon) {
		this.incidentGraphicIcon = incidentGraphicIcon;
	}

	public ModuleAction getIncidentReportModuleAction() {
		return incidentReportModuleAction;
	}

	public void setIncidentReportModuleAction(ModuleAction incidentReportModuleAction) {
		this.incidentReportModuleAction = incidentReportModuleAction;
	}

	public ModuleAction getIncidentWizardModuleAction() {
		return incidentWizardModuleAction;
	}

	public void setIncidentWizardModuleAction(ModuleAction incidentWizardModuleAction) {
		this.incidentWizardModuleAction = incidentWizardModuleAction;
	}
}