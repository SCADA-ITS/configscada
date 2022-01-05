import IncidentWizardModule from "./incidentWizardModule.js";
import { util } from "../../lib/util.js";
import MessageBox from "../../component/messageBox.js";
import StompSubscriptorService from "../../services/stompSubscriptorService.js";

export default class IncidentWizardAction {

	constructor(parent) {

		this.parent = parent;
	}

	createIncident() {

		let parent = this.parent;
		let self = this;
		
		(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_CREATE_INCIDENT"),
								
			// onAccept
			function() {
				
				$$("loading-" + self.parent.uid).show();
				$$(self.parent.uid).hide();
				
				checkNewIncident(
					
					function onAccept() {
						
						let body = {
							incidentTypeId: parent.incidentData.incidentTypeId,
							locationId: parent.incidentData.locationId,
							affectionStretchIds: parent.incidentData.affectionStretchsSelected.map(e => e.id)
						};
			
						webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + parent.config.actionEndpoints.create, body).then(function(data) {
							console.log(data);
							
							waitResponse(
								
								function onResponse(msg) {
									
									showMessage(new MessageBox(MessageBox.INFO, msg, null, null));		
								},
								
								function onTimeout(msg) {
									
									showMessage(new MessageBox(MessageBox.WARN, msg, null, null));
								}
							);
							
							function showMessage(messageBox) {
								
								messageBox.show();
								
								let container = $$(parent.args.containerId);
			
								if (container) {
				
									container.close();
								}
							}
						});			
					},
					function onError() {
						
						$$("loading-" + self.parent.uid).hide();
						$$(self.parent.uid).show();
						
						let message = `${i18n.t("LBL_INCIDENT_REPORT_NO_MORE_THAN_ONE_INCIDENT_OF_TYPE")} ${parent.incidentData.incidentType} ${i18n.t("LBL_INCIDENT_REPORT_IS_ALLOWED_IN_STRETCH")} ${parent.incidentData.stretch}`;
						
						(new MessageBox(MessageBox.ERROR, message, null, null)).show();
					}
				);
			}
		)).show();
		
		function checkNewIncident(onSuccess, onError) {
			
			webix.ajax().get(appConfig.restUrl + parent.config.restEndpoints.incidentReportsSummary).then(
				
				function(responseIncidentReportsSummary) {
					
					if (responseIncidentReportsSummary != null) {
						
						let incidentReportsSummary = responseIncidentReportsSummary.json();
						
						if (Array.isArray(incidentReportsSummary) && incidentReportsSummary.length > 0) {
							
							webix.ajax().get(appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.incidentTypeStretchs, { incidentTypeId: parent.incidentData.incidentTypeId, stretchId: parent.incidentData.stretchId })).then(
						
								function(responseIncidentTypeStretchs) {
				
									if (responseIncidentTypeStretchs != null) {
					
										let incidentTypeStretch = responseIncidentTypeStretchs.json();
										
										if (incidentTypeStretch && !incidentTypeStretch.multiInstance) {
											
											let existIncidentReportInSameStretch = incidentReportsSummary.find(e => e.confirm && 
																													!e.incidentState.finalState && 
																													e.affectionStretch.id === incidentTypeStretch.stretch.id && 
																													e.incidentType.id === incidentTypeStretch.incidentType.id);
											
											if (existIncidentReportInSameStretch) {
										
												onError();		
											}
											else {
												
												onSuccess();
											}
										}
										else {
											
											onSuccess();
										}
									}
								}
							);
						}
						else {
							
							onSuccess();		
						}
					}
				}
			);
		}
		
		function waitResponse(onResponse, onTimeout) {
				
			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
			let response = false;
			let idStompSubscriptor = undefined;

			if (stompSubscriptorService) {
	
				let dataSource = {
					dataSourceValues: [
						{ value: appConfig.stompUrl },
						{ value: parent.config.ws.incidentReports }
					]
				};
	
				idStompSubscriptor = stompSubscriptorService.subscribe(dataSource, onMessage);
			}
			
			let incidentCreationTimeout = setInterval(function() {
				
				if (!response) {
					
					onTimeout(i18n.t("LBL_INCIDENT_REPORT_CREATE_TIMEOUT"));	
				}
				
				if (stompSubscriptorService && idStompSubscriptor) {
		
					stompSubscriptorService.unsubscribe(idStompSubscriptor);
				}
				
				clearInterval(incidentCreationTimeout);
	
			}, self.parent.config.incidentCreateTimeoutMs);
			
			function onMessage(be, action, timestamp) {
				
				if (action === StompSubscriptorService.ACTION_CREATE) {
				
					webix.ajax(appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.incidentReport, { id: be.id })).then(function(data) {

						if (data.json() != undefined) {
						
							let incidentReport = data.json();
							
							if (incidentReport && 
								incidentReport.incidentType.id === parent.incidentData.incidentTypeId && 
								incidentReport.location.id === parent.incidentData.locationId) {
					
								response = true;
								
								let msg = `${i18n.t("LBL_INCIDENT_REPORT_CREATE_SUCCESS")}  #${util.getSimpleId(incidentReport.id)} ${i18n.t("LBL_GENERATED_AT")}: ${util.toLocaleString(incidentReport.generatedAt / 1000)}`;
								
								onResponse(msg);			
							}
						}
					});
				}
			}
		}
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
		let self = this;

		let valid = false;
		let summaryIncidentType = $$("summary-incident-" + parent.uid);
		let summaryLocation = $$("summary-location-" + parent.uid);
		let summaryStretch = $$("summary-stretch-" + parent.uid);
		let summaryUser = $$("summary-user-" + parent.uid);
		let summaryLevel = $$("summary-level-" + parent.uid);

		function validateStepStretchAndLocation(onValid) {

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

			valid = parent.incidentData.stretchId != null && parent.incidentData.locationId != null;
			
			onValid(valid);
		}
		
		function validateStepLocationFromMap(onValid) {

			if (parent.mapLocationSelected) {
				
				parent.incidentData.locationId = parent.mapLocationSelected.id;
				
				webix.ajax().get(appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.location, { id: parent.mapLocationSelected.id })).then(
						
					function(dat) {

						if (dat != null) {
		
							let location = dat.json();
							
							if (location) {
								
								parent.incidentData.location = location.alias;
								
								if (Array.isArray(location.stretchs) && location.stretchs.length > 0) {
									
									parent.incidentData.stretchs = location.stretchs;
									parent.incidentData.stretchId = location.stretchs[0].id;
									parent.incidentData.stretch = location.stretchs[0].alias;	
								}
								
								valid = true;
							
								onValid(valid);
							}
						}
					}
				);
			}
			else {
				
				onValid(valid);
			}
			
			
		}

		function validateStepIncidentType(onValid) {

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
				
				parent.incidentData.incidentType = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_TYPE_ALIAS];
				parent.incidentData.incidentTypeDesc = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_TYPE_DESCRIPTION];

				parent.incidentData.incidentLevelId = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_LEVEL_ID];
				parent.incidentData.incidentLevelLabelAlias = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_LEVEL_LABEL_ALIAS];
				parent.incidentData.incidentLevelIcon = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_LEVEL_ICON];
				parent.incidentData.incidentLevelColor = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_LEVEL_COLOR];

				parent.incidentData.userId = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_USER_ID];
				parent.incidentData.user = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_USER];
				
				parent.incidentData.incidentTypeId = incidentTypesSelected[0][IncidentWizardModule.FIELD_INCIDENT_TYPE_ID];
				
				if (parent.incidentData.incidentTypeId) {

					webix.ajax().get(appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.stretchsByIncidentType, { id: parent.incidentData.incidentTypeId })).then(
						
						function(dat) {

							if (dat != null) {
			
								let stretchs = dat.json();
								
								parent.incidentData.stretchs = stretchs;
							}
							
							valid = parent.incidentData.incidentGroupId != null && parent.incidentData.incidentTypeId != null;
							
							onValid(valid);
						}
					);
				}
			}
			else {

				parent.incidentData.incidentTypeId = undefined;
				parent.incidentData.stretchs = undefined;
				parent.incidentData.incidentType = undefined;
				parent.incidentData.incidentTypeDesc = undefined;
				parent.incidentData.userId = undefined;
				parent.incidentData.user = undefined;
				
				onValid(false);
			}
		}
		
		function validateStepAffectionStretch(onValid) {
			
			if (Array.isArray(parent.incidentData.affectionStretchs)) {
				
				let affectionStretchsSelectedStr = parent.dblListAffectionStretchs.getValue();
									
				if (affectionStretchsSelectedStr != "") {
					
					let affectionStretchsId = affectionStretchsSelectedStr.split(",");
					
					if (Array.isArray(affectionStretchsId)) {
						
						parent.incidentData.affectionStretchsSelected = [];
						
						affectionStretchsId.forEach(affectionStretchId => {
						
							let affectionStretch = parent.incidentData.affectionStretchs.find(e => e.id === affectionStretchId);
							
							if (affectionStretch) {
								
								parent.incidentData.affectionStretchsSelected.push(affectionStretch);
							}	
						});
					}
				}
	
				valid = Array.isArray(parent.incidentData.affectionStretchsSelected) && parent.incidentData.affectionStretchsSelected.length > 0;
				
				onValid(valid);	
			}
		}

		function validateStepSummary(onValid) {
			
			 onValid(true);
		}

		if (step) {

			eval(step.validateFunction)(onValidate);
			
			function onValidate(v) {
				
				step.valid = v;

				if (v) {
	
					if (summaryIncidentType) {
	
						summaryIncidentType.setValues({ incidentType: parent.incidentData.incidentType, incidentTypeDesc: parent.incidentData.incidentTypeDesc });
					}
	
					if (summaryLocation) {
	
						summaryLocation.setValues({ location: parent.incidentData.location });
					}
	
					if (summaryStretch) {
	
						summaryStretch.setValues({ affectionStretchs: parent.incidentData.affectionStretchsSelected });
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
	
				self.resetFromStep(step);
			}
		}
	}

	resetFromStep(step) {

		let parent = this.parent;

		function resetStepStretchAndLocation() {

			parent.incidentData.stretchId = undefined;
			parent.incidentData.stretch = undefined;
			parent.incidentData.locationId = undefined;
			parent.incidentData.location = undefined;

			parent.listViewStrechs.selectByIndex(0);
			parent.dtLocations.clear();
		}

		function resetStepIncidentType() {

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
		
		function resetStepAffectionStretch() {
			
			parent.incidentData.affectionStretchsSelected = undefined;
			
			if (parent.dblListAffectionStretchs) {
				
				parent.dblListAffectionStretchs.$$("left").clearAll();
				parent.dblListAffectionStretchs.$$("right").clearAll();
				
				if (parent.incidentData.locationId) {
				
					webix.ajax().get(appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.stretchsByLocation, { id: parent.incidentData.locationId })).then(
							
						function(dat) {
		
							if (dat != null) {
			
								let stretchs = dat.json();
								
								if (Array.isArray(stretchs) && stretchs.length > 0) {
									
									parent.incidentData.affectionStretchs = stretchs;
									
									let stretchData = stretchs.map(e => {
										
										return {
											id: e.id,
											value: e.alias
										}
									});
									
									parent.dblListAffectionStretchs.parse(stretchData);
									parent.dblListAffectionStretchs.setValue([parent.incidentData.stretchId]);
									
									parent.incidentWizardAction.validateStep(parent.incidentWizardAction.getCurrentStep());
								}
							}
						}
					);	
				}
			}
		}

		function resetStepStretchAndLocationFromIncidentType() {

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

						strechs = strechs.filter(e => {
							return (parent.config.stretchTypes.indexOf(e.stretchType.id) !== -1)
						});

						strechs.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));
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
		
		function resetStepLocationFromMap() {

			parent.mapLocationSelected = undefined;
			
			parent.incidentData.stretchId = undefined;
			parent.incidentData.stretch = undefined;
			parent.incidentData.locationId = undefined;
			parent.incidentData.location = undefined;			
		}

		function resetStepSummary() {

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
