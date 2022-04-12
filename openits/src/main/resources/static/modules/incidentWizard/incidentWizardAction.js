import IncidentWizardModule from "./incidentWizardModule.js";
import { util } from "../../lib/util.js";
import MessageBox from "../../component/messageBox.js";
import SearchDialogBox from "../../component/searchDialogBox.js";
import StompSubscriptorService from "../../services/stompSubscriptorService.js";

export default class IncidentWizardAction {

	constructor(parent) {

		this.parent = parent;
	}

	createIncident() {

		let parent = this.parent;
		let self = this;
		
		let confirmMsg = i18n.t("LBL_ARE_YOU_SURE_CREATE_INCIDENT");
		
		if (parent.incidentData.affectionStretchsSelected.length > 1) {
			
			confirmMsg = i18n.t("LBL_INCIDENTS_TO_CREATE") + ": " + parent.incidentData.affectionStretchsSelected.length + " " + i18n.t("LBL_ARE_YOU_CONTINUE"); 
		}

		(new MessageBox(MessageBox.CONFIRM, confirmMsg,
								
			// onAccept
			function() {
				
				$$("loading-" + self.parent.uid).show();
				$$(self.parent.uid).hide();
				
				checkNewIncident(
					
					function onAccept() {
						
						let body = {
							incidentTypeId: parent.incidentData.incidentTypeId,
							locationId: parent.incidentData.locationId,
							affectionStretchIds: parent.incidentData.affectionStretchsSelected.map(e => e.id),
							userId: parent.incidentData.userId
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
								
								if (parent.args.viewTypeId === VIEW_TYPE_WINDOW) {
													
									let container = $$(parent.args.containerId);

									if (container) {
				
										container.close();
									}
								}
								else {
									
									window.open('', '_self').close();
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
	
				idStompSubscriptor = stompSubscriptorService.subscribe(appConfig.stompUrl, parent.config.ws.incidentReports, onMessage);
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

	validateStep(step, onValidate) {

		let parent = this.parent;
		let self = this;

		let valid = false;
		let summaryIncident = $$("summary-incident-" + parent.uid);
		let summaryIncidentType = $$("summary-incident-type-" + parent.uid);
		let summaryLocation = $$("summary-location-" + parent.uid);
		let summaryStretch = $$("summary-stretch-" + parent.uid);
		let summaryUser = $$("summary-user-" + parent.uid);
		let summaryLevel = $$("summary-level-" + parent.uid);

		function validateStepStretchAndLocation(onValidateStep) {

			let stretchsSelected = parent.listViewStrechs.getSelectedItems();

			if (Array.isArray(stretchsSelected) && stretchsSelected.length > 0) {

				parent.incidentData.stretchId = stretchsSelected[0][IncidentWizardModule.FIELD_STRETCH_ID];
				parent.incidentData.stretch = stretchsSelected[0][IncidentWizardModule.FIELD_STRETCH_ALIAS];
				
				parent.incidentData.affectionStretchsSelected = [stretchsSelected[0]];
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
			
			onValidateStep(valid);

			if (valid) {
				
				getAffectionStretchs(parent.incidentData.locationId,
					
					function(affectedStretchs) {
					
						if (Array.isArray(affectedStretchs) && affectedStretchs.length > 1) {
					
							let message = affectedStretchs.length + " " + i18n.t("LBL_STRETCHS_AVAILABLE_FOR_LOCATION") + " " + parent.incidentData.location + ": " + affectedStretchs.map(e => e.alias).join(",");
										
							(new MessageBox(MessageBox.INFO, message, null, null)).show();
						}
					}
				);
			}
		}
		
		function validateStepLocationFromMap(onValidateStep) {

			if (parent.mapLocationSelected && parent.mapIncidentTypeSelected) {
				
				parent.incidentData.locationId = parent.mapLocationSelected.id;
				parent.incidentData.location = parent.mapLocationSelected.alias;
				parent.incidentData.stretchs = parent.mapLocationSelected.stretchs;
				parent.incidentData.stretchId = parent.mapLocationSelected.stretchs[0].id;
				parent.incidentData.stretch = parent.mapLocationSelected.stretchs[0].alias;
				
				parent.incidentData.incidentGroupId = parent.mapIncidentTypeSelected.incidentGroup.id;
				parent.incidentData.incidentGroup = parent.mapIncidentTypeSelected.incidentGroup.alias;
				
				parent.incidentData.incidentTypeId = parent.mapIncidentTypeSelected.id;
				parent.incidentData.incidentType = parent.mapIncidentTypeSelected.alias;
				parent.incidentData.incidentTypeDesc = parent.mapIncidentTypeSelected.description;

				parent.incidentData.incidentLevelId = parent.mapIncidentTypeSelected.incidentLevel.id;
				parent.incidentData.incidentLevelLabelAlias = i18n.t(parent.mapIncidentTypeSelected.incidentLevel.labelAlias);
				parent.incidentData.incidentLevelIcon = parent.mapIncidentTypeSelected.incidentLevel.icon;
				parent.incidentData.incidentLevelColor = parent.mapIncidentTypeSelected.incidentLevel.color;

				parent.incidentData.userId = parent.mapIncidentTypeSelected.defaultUser.id;
				parent.incidentData.user = parent.mapIncidentTypeSelected.defaultUser.name;
				
				parent.incidentData.affectionStretchsSelected = [parent.mapLocationSelected.stretchs[0]];
				
				getAffectionStretchs(parent.incidentData.locationId,
					
					function(affectedStretchs) {
					
						if (Array.isArray(affectedStretchs) && affectedStretchs.length > 1) {
							
							let message = i18n.t("LBL_STRETCH_SELECTED_FOR_LOCATION") + " " + parent.incidentData.stretch + ". " + i18n.t("LBL_CHANGE_STRECH_FOR_SELECTED_LOCATION_QUESTION");
										
							(new MessageBox(MessageBox.CONFIRM, message, 
							
								// onAccept	
								function() {
									
									let dialogEntity = webix.copy(SearchDialogBox.CUSTOM);
	
									dialogEntity.data = affectedStretchs.map(e => {
										
										return {
											id: e.id,
											alias: e.alias
										}
									});
									
									dialogEntity.pkField = "id"
									dialogEntity.fields = ["alias=LBL_STRECH"];
									dialogEntity.height = 600;
									dialogEntity.width = 800;
									dialogEntity.sortFieldId = 1;
										
									let s = new SearchDialogBox(i18n.t("LBL_SELECT_STRECH"), dialogEntity, 

										function(selectedIds) {
											
											if (selectedIds && Array.isArray(selectedIds) && selectedIds.length === 1) {
												
												let selectedStretch = affectedStretchs.find(e => e.id === selectedIds[0].id);
												
												if (selectedStretch) {
													
													parent.incidentData.affectionStretchsSelected = [selectedStretch];
													parent.incidentData.stretchId = selectedStretch.id;
													parent.incidentData.stretch = selectedStretch.alias;
												}
											}
											
											onValidateStep(true);
										}
									);
						
									s.show();
								},
								
								// onCancel
								function() {
									
									onValidateStep(true);
								}
							, null)).show();
						}
						else {
							
							onValidateStep(true);
						}
					}
				);
			}
			else {
				
				onValidateStep(false);
			}
		}
		
		function getAffectionStretchs(locationId, onResponse) {
			
			if (locationId) {
				
				webix.ajax().get(appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.stretchsByLocation, { id: locationId })).then(
							
					function(dat) {
	
						if (dat != null) {
		
							let stretchs = dat.json();
							
							if (stretchs && onResponse) {
								
								onResponse(stretchs);	
							} 
						}
					}
				);
			}
		}

		function validateStepIncidentType(onValidateStep) {

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
					
					onValidateStep(true);	
				}
			}
			else {

				parent.incidentData.incidentTypeId = undefined;
				parent.incidentData.stretchs = undefined;
				parent.incidentData.incidentType = undefined;
				parent.incidentData.incidentTypeDesc = undefined;
				parent.incidentData.userId = undefined;
				parent.incidentData.user = undefined;
				
				onValidateStep(false);
			}
		}
		
		function validateStepAffectionStretch(onValidateStep) {
			
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
				
				onValidateStep(valid);	
			}
		}

		function validateStepSummary(onValidateStep) {
			
			 onValidateStep(true);
		}

		if (step) {
			
			$$("button-next-" + step.position + "-" + parent.uid).disable();

			eval(step.validateFunction)(onValidateStep);
			
			function onValidateStep(v) {
				
				step.valid = v;

				if (v) {
					
					if (summaryIncident) {
	
						summaryIncident.setValues({ affectionStretchs: parent.incidentData.affectionStretchsSelected });
					}
	
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
	
//					$$("button-next-" + step.position + "-" + parent.uid).enable();
				}
				else {
	
//					$$("button-next-" + step.position + "-" + parent.uid).disable();
				}
	
				self.resetFromStep(step, function() {
					
					if (v) {
						
						$$("button-next-" + step.position + "-" + parent.uid).enable();
					}
					else {
						
						$$("button-next-" + step.position + "-" + parent.uid).disable();	
					}
					
					if (onValidate) {
						
						onValidate(v);
					}
				});
			}
		}
	}

	resetFromStep(step, onReset) {

		let parent = this.parent;

		function resetStepStretchAndLocation(onResetStep) {

			parent.incidentData.stretchId = undefined;
			parent.incidentData.stretch = undefined;
			parent.incidentData.locationId = undefined;
			parent.incidentData.location = undefined;

			parent.dtLocations.clear();
			
			if (onResetStep) {
				
				onResetStep();
			}
		}

		function resetStepIncidentType(onResetStep) {

			parent.incidentData.incidentGroupId = undefined;
			parent.incidentData.incidentGroup = undefined;
			parent.incidentData.incidentTypeId = undefined;
			parent.incidentData.incidentType = undefined;
			parent.incidentData.incidentTypeDesc = undefined;
			parent.incidentData.userId = undefined;
			parent.incidentData.user = undefined;

			parent.listViewIncidentGroups.unselect();
			parent.dtIncidentTypes.clear();
			
			if (onResetStep) {
				
				onResetStep();
			}
		}
		
		function resetStepAffectionStretch(onResetStep) {
			
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
								
								if (onResetStep) {
									
									onResetStep();
								}
							}
						}
					);	
				}
			}
		}

		function resetStepStretchAndLocationFromIncidentType(onResetStep) {

			parent.incidentData.stretchId = undefined;
			parent.incidentData.stretch = undefined;
			parent.incidentData.locationId = undefined;
			parent.incidentData.location = undefined;

			parent.listViewStrechs.clear();
			parent.dtLocations.clear();

			if (parent.incidentData.incidentTypeId) {
				
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
						
						if ($$("view-locations-" + parent.uid)) {
							
							try {
								
								$$("view-locations-" + parent.uid).hideProgress();	
							}
							catch {}
						}
						
						if (onResetStep) {
					
							onResetStep();
						}
					}
	
				})				
			}
			else {
				
				if ($$("view-locations-" + parent.uid)) {
							
					try {
						
						$$("view-locations-" + parent.uid).hideProgress();	
					}
					catch {}
				}
				
				if (onResetStep) {
			
					onResetStep();
				}
			}
			
		}
		
		function resetStepLocationFromMap(onResetStep) {

			parent.mapLocationSelected = undefined;
			
			parent.incidentData.stretchId = undefined;
			parent.incidentData.stretch = undefined;
			parent.incidentData.locationId = undefined;
			parent.incidentData.location = undefined;
			
			if (onResetStep) {
				
				onResetStep();
			}			
		}

		function resetStepSummary(onResetStep) {
			
			if (onResetStep) {
				
				onResetStep();
			}

		}

		if (step) {

			let stepsToReset = parent.steps.filter(e => e.position > step.position);
			let stepsReseted = 0;

			if (Array.isArray(stepsToReset) && stepsToReset.length > 0) {

				stepsToReset.forEach(e => {

					eval(e.resetFunction)(function() {
						
						if (++stepsReseted == stepsToReset.length) {
							
							if (onReset) {
								
								onReset();
							}
						}
					});

					e.valid = false;
					e.initializated = true;
					
					if ($$("button-next-" + e.position + "-" + parent.uid)) {
						
						$$("button-next-" + e.position + "-" + parent.uid).disable();	
					}
				});
			}
			else {
				
				if (onReset) {
								
					onReset();
				}
			}
		}
	}
}	
