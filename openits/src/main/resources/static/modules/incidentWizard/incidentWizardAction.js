import IncidentWizardModule from "./incidentWizardModule.js";
import { util } from "../../lib/util.js";

export default class IncidentWizardAction {

	constructor(parent) {

		this.parent = parent;
	}
	
	createIncident() {
			
		let parent = this.parent;
		
		webix.confirm({
			title: i18n.t("LBL_DIALOG_CREATE_INCIDENT"),
			ok: i18n.t("LBL_YES"),
			cancel: i18n.t("LBL_NO"),
			text: i18n.t("LBL_ARE_YOU_SURE_CREATE_INCIDENT"),
			width: 600
		}).then(function() {

			let body = {
				incidentTypeId: parent.incidentData.incidentTypeId,
				locationId: parent.incidentData.locationId
			};

			webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + parent.config.actionEndpoints.create, body).then(function(data) {
				console.log(data);
				
				let container = $$(parent.args.containerId);

				if (container) {

					container.close();
				}
			});											
		});
	}
	
	getCurrentStep() {
		
		let parent = this.parent;
			
		let currentStep = undefined;
		
		currentStep = parent.steps.find(e => e.active);
		
		return currentStep;
	};
	
	getStep(position) {
		
		let parent = this.parent;
		let step = undefined;
		
		step = parent.steps.find(e => e.position === position);
		
		return step;
	};
	
	setCurrentStep(position) {
		
		let parent = this.parent;
		let currentStep = this.getCurrentStep();
		let newStep = undefined;
		
		if (position > 0 && position <= parent.steps.length) {
			
			currentStep.active = false;
			
			newStep = this.getStep(position);
			newStep.active = true;	
			
			if (position === parent.steps.length) {
				
				$$("button-next-" + position + "-" + parent.uid).enable();	
			}
		}
		
		return !newStep ? currentStep : newStep;
	}
	
	validateStep(step) {
		
		let parent = this.parent;
			
		let valid = false;
		let summaryIncidentType = $$("summary-incident-" + parent.uid);
		let summaryLocation = $$("summary-location-" + parent.uid);
		let summaryStretch = $$("summary-stretch-" + parent.uid);
		let summaryUser = $$("summary-user-" + parent.uid);
		let summaryLevel = $$("summary-level-" + parent.uid);
		
		let validateStepStretchAndLocation = function() {
			
			let stretchsSelected = parent.listViewStrechs.getSelectedItems();
			
			if (Array.isArray(stretchsSelected) && stretchsSelected.length > 0) {

				parent.incidentData.stretchId = stretchsSelected[0][IncidentWizardModule.FIELD_STRETCH_ID];
				parent.incidentData.stretch = stretchsSelected[0][IncidentWizardModule.FIELD_STRETCH_ALIAS];
			}
			else {
				parent.incidentData.stretchId = undefined;
				parent.incidentData.stretch = undefined;
			}
			
			let locationsSelected = parent.dtLocations.getSelectedItems();
			
			if (Array.isArray(locationsSelected) && locationsSelected.length > 0) {
			
				parent.incidentData.locationId = locationsSelected[0][IncidentWizardModule.FIELD_LOCATION_ID];
				parent.incidentData.location = locationsSelected[0][IncidentWizardModule.FIELD_LOCATION_ALIAS];	
			}
			else {
				
				parent.incidentData.locationId = undefined;
				parent.incidentData.location = undefined;
			}
			
			valid = parent.incidentData.stretchId && parent.incidentData.locationId;
		}
		
		let validateStepIncidentType = function() {
			
			let incidentGroupsSelected = parent.listViewIncidentGroups.getSelectedItems();
			
			if (Array.isArray(incidentGroupsSelected) && incidentGroupsSelected.length > 0) {
			
				parent.incidentData.incidentGroupId = incidentGroupsSelected[0][IncidentWizardModule.FIELD_INCIDENT_GROUP_ID];
				parent.incidentData.incidentGroup = incidentGroupsSelected[0][IncidentWizardModule.FIELD_INCIDENT_GROUP_ALIAS];
			}
			else {
				
				parent.incidentData.incidentGroupId = undefined;
				parent.incidentData.incidentGroup = undefined;
			}
			
			let incidentTypesSelected = parent.dtIncidentTypes.getSelectedItems();
			
			if (Array.isArray(incidentTypesSelected) && incidentTypesSelected.length > 0) {
			
				parent.incidentData.incidentTypeId = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_TYPE_ID];
				parent.incidentData.incidentType = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_TYPE_ALIAS];
				parent.incidentData.incidentTypeDesc = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_TYPE_DESCRIPTION];
				
				parent.incidentData.incidentLevelId = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_LEVEL_ID];
				parent.incidentData.incidentLevelLabelAlias = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_LEVEL_LABEL_ALIAS];
				parent.incidentData.incidentLevelIcon = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_LEVEL_ICON];
				parent.incidentData.incidentLevelColor = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_LEVEL_COLOR];
				
				parent.incidentData.userId = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_USER_ID];
				parent.incidentData.user = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_USER];
			}
			else {
				
				parent.incidentData.incidentTypeId = undefined;
				parent.incidentData.incidentType = undefined;
				parent.incidentData.incidentTypeDesc = undefined;
				parent.incidentData.userId = undefined;
				parent.incidentData.user = undefined;
			}
			
			valid = parent.incidentData.incidentGroupId && parent.incidentData.incidentTypeId;
		}
		
		let validateStepSummary = function() {
			
		}
		
		if (step) {
		
			eval(step.validateFunction)();
			
			step.valid = valid;
			
			if (valid) {
				
				if (summaryIncidentType) {
				
					summaryIncidentType.setValues({ incidentType: parent.incidentData.incidentType, incidentTypeDesc: parent.incidentData.incidentTypeDesc });
				}
				
				if (summaryLocation) {
					
					summaryLocation.setValues({ location: parent.incidentData.location });
				}
				
				if (summaryStretch) {
					
					summaryStretch.setValues({ stretch: parent.incidentData.stretch });
				}
				
				if (summaryUser) {
					
					summaryUser.setValues({ user: parent.incidentData.user });
				}
				
				if (summaryLevel) {
					
					summaryLevel.setValues({ level: parent.incidentData.incidentLevelLabelAlias, levelColor: parent.incidentData.incidentLevelColor });
				}
				
				$$("button-next-" + step.position + "-" + parent.uid).enable();
			}
			else {
				
				$$("button-next-" + step.position + "-" + parent.uid).disable();
			}
			
			this.resetFromStep(step);
		}			
	}
	
	resetFromStep(step) {
		
		let parent = this.parent;
			
		let resetStepStretchAndLocation = function() {
			
			parent.incidentData.stretchId = undefined;
			parent.incidentData.stretch = undefined;
			parent.incidentData.locationId = undefined;
			parent.incidentData.location = undefined;
			
			parent.listViewStrechs.selectByIndex(0);
			parent.dtLocations.clear();
		}
		
		let resetStepIncidentType = function() {
			
			parent.incidentData.incidentGroupId = undefined;
			parent.incidentData.incidentGroup = undefined;
			parent.incidentData.incidentTypeId = undefined;
			parent.incidentData.incidentType = undefined;
			parent.incidentData.incidentTypeDesc = undefined;
			parent.incidentData.userId = undefined;
			parent.incidentData.user = undefined;
			
			parent.listViewIncidentGroups.unselect();
			parent.dtIncidentTypes.clear();
		}

		let resetStepStretchAndLocationFromIncidentType = function() {
			
			parent.incidentData.stretchId = undefined;
			parent.incidentData.stretch = undefined;
			parent.incidentData.locationId = undefined;
			parent.incidentData.location = undefined;
			
			$$("view-locations-" + parent.uid).showProgress({
				type: "bottom",
				hide: false
			});

			parent.listViewStrechs.clear();
			
			webix.ajax().get(appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.stretchsByIncidentType, { id: parent.incidentData.incidentTypeId })).then(function(dat) {
			
				if (dat != null) {
			
					let strechs = dat.json();
					
					if (Array.isArray(strechs) && strechs.length > 0) {
						
						stretchs.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));
						let dataStrechs = strechs.map(e => {
							
							return {
								id: e.id,
								alias: e.alias,
								description: e.description
							}
						});
						
						parent.listViewStrechs.add(dataStrechs);
					}
					
					$$("view-locations-" + parent.uid).hideProgress();
				}

			})

			parent.dtLocations.clear();
		}
		
		let resetStepSummary = function() {
			
		}
		
		if (step) {
		
			let stepsToReset = parent.steps.filter(e => e.position > step.position);
		
			if (Array.isArray(stepsToReset) && stepsToReset.length > 0) {
				
				stepsToReset.forEach(e => {
					
					eval(e.resetFunction)();
					
					e.valid = false;
					
					$$("button-next-" + e.position + "-" + parent.uid).disable();
				});
			}	
		}
	}
}	
