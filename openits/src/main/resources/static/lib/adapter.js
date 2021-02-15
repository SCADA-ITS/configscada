import { util } from "../lib/util.js";

const INCIDENT_REPORT_END_POINT = "incidents/summary"

export let adapter = {

	ADAPTER_TYPE_VO_TO_GRID_DATA: 1,
	ADAPTER_DATA_SOURCE_TYPE_JS: 2,

	getElementTypeParamId(elementValueId) {

		let elementTypeParamId;

		let splitStr = elementValueId.split(":");

		elementTypeParamId = "ElementTypeParam" + ":" + splitStr[1] + ":" + splitStr[4] + ":" + splitStr[3];

		return elementTypeParamId;
	},

	ws: {

		element: {

			getList(data, update_callback) {

				let gridData = {};

				if (data != undefined && Array.isArray(data.basicElementsVO) && data.basicElementsVO.length > 0) {

					gridData.data = [];

					data.basicElementsVO.forEach(element => {

						let dataItem = {};

						dataItem.id = element.id;

						if (element.elementTypeState != undefined) {

							dataItem.state = element.elementTypeState.id;
						}

						if (element.elementValues != undefined && Array.isArray(element.elementValues) && element.elementValues.length > 0) {

							element.elementValues.forEach(elementValue => {

								let elementTypeParamId = adapter.getElementTypeParamId(elementValue.id);

								dataItem[elementTypeParamId] = {};

								if (elementValue.elementValueState != undefined) {

									dataItem[elementTypeParamId].state = elementValue.elementValueState.id;
								}

								if (elementValue.value != undefined) {

									dataItem[elementTypeParamId].value = elementValue.value;
								}
								else {

									dataItem[elementTypeParamId].value = "";
								}

							});
						}

						gridData.data.push(dataItem);
					});
				}

				update_callback(gridData);
			}
		},
		
		alarm: {
			
			getList(data, update_callback) {
			
				let listOutput = [];
				
				if (data != undefined && Array.isArray(data) && data.length > 0) {
					
					data.forEach(e => {
						
						let itemOutput = {};
						
						let targetElement = window.em.getByField(EntityType.ELEMENT, undefined, e.targetElement.id);
						let alarmConfig = window.em.getByField(EntityType.ALARM_CONFIG, "id", e.alarmConfig.id);
						let alarmLevel = window.em.getByField(EntityType.ALARM_LEVEL, "id", alarmConfig.alarmLevel.id);
			
						itemOutput.id = e.id;
						
						itemOutput.activation = e.activation;
						
//						if (e.activation && typeof e.activation == 'number') {
//						
//							itemOutput.activation = util.toLocaleString(e.activation / 1000);	
//						}
//						else {
//							
//							itemOutput.activation = undefined;
//						}
						
						itemOutput.idStr = e.id;
						itemOutput.alarmConfigId = e.alarmConfig.id;
						itemOutput.alarmConfigAlias = alarmConfig.alias;
						itemOutput.alarmLevelId = alarmLevel.id;
						itemOutput.alarmLevelAlias = alarmLevel.labelAlias;
						
						itemOutput.mutedUntil = e.mutedUntil;
						itemOutput.recognized = e.recognized;
						itemOutput.deactivation = e.deactivation;
						
//						if (e.mutedUntil && typeof e.mutedUntil == 'number') {
//						
//							itemOutput.mutedUntil = util.toLocaleString(e.mutedUntil / 1000);	
//						}
//						else {
//							
//							itemOutput.mutedUntil = undefined;
//						}
//						
//						if (e.recognized && typeof e.recognized == 'number') {
//						
//							itemOutput.recognized = util.toLocaleString(e.recognized / 1000);	
//						}
//						else {
//							
//							itemOutput.recognized = undefined;
//						}
//						
//						if (e.deactivation && typeof e.deactivation == 'number') {
//						
//							itemOutput.deactivation = util.toLocaleString(e.deactivation / 1000);	
//						}
//						else {
//							
//							itemOutput.deactivation = undefined;
//						}

						itemOutput.targetElementId = targetElement.id;
						itemOutput.targetElementAlias = targetElement.alias;
						
						itemOutput.targetElementTypeId = targetElement.elementType.id;
						itemOutput.targetElementTypeAlias = i18n.t(window.em.getByField(EntityType.ELEMENT_TYPE, "id", targetElement.elementType.id).labelAlias); 
						
						itemOutput.isActive = !itemOutput.deactivation;
						itemOutput.alarmType = alarmConfig.alarmType.id;
						
						
						listOutput.push(itemOutput);
					});
					
					if (update_callback) {
						
						update_callback(listOutput);
					}
				}	
				
				return listOutput;
			}
		},

		incidentReport: {

			getList(data, update_callback) {

				if (data != undefined && Array.isArray(data.basicElementsVO) && data.basicElementsVO.length > 0) {
		
					let gridData = {};
					let count = data.basicElementsVO.length;
					gridData.data = [];
					gridData.action = data.action;

					data.basicElementsVO.forEach(incidentReport => {

						let dataItem = {};
						dataItem.id = incidentReport.id;

						if (gridData.action != 'DELETE') {

							webix.ajax().get(appConfig.restUrl + INCIDENT_REPORT_END_POINT + "/" + incidentReport.id).then(function(dat) {
								
								console.log(dataItem);
								if (dat != null) {

									let incidentReport = dat.json();

									dataItem.incidentId = incidentReport.id.split(":")[1];
									dataItem.confirm = incidentReport.confirm;
									dataItem.incidentLevelId = incidentReport.incidentLevel.id;
									dataItem.incidentLevelLabelAlias = incidentReport.incidentLevel.labelAlias;
									dataItem.generatedAt = incidentReport.generatedAt;
									dataItem.incidentTypeAlias = incidentReport.incidentType.alias;
									dataItem.incidentTypeDescription = incidentReport.incidentType.description;
									dataItem.affectionStretchAlias = incidentReport.affectionStretch.alias;
									dataItem.locationAlias = incidentReport.location.alias;
									dataItem.incidentStateLabelAlias = incidentReport.incidentState.labelAlias;
									dataItem.currentUserName = incidentReport.currentUser.name;

									gridData.data.push(dataItem);

									count--;

									if (count == 0)
										update_callback(gridData);
								}

							}, function(response) { util.ajax.handleError(response); });
							
						} else {
							
							gridData.data.push(dataItem);

							count--;

							if (count == 0)
								update_callback(gridData);
						}
					});
				}
			}
		}
	}
};
