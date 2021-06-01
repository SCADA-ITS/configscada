import { util } from "../lib/util.js";

const INCIDENT_REPORT_END_POINT = "incidents/summary"
const PLAN_END_POINT = "plans"


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

				if (update_callback) {

					update_callback(gridData);
				}
			},

			// Only for one element
			get(data, elementTypeParams, update_callback) {

				let element = undefined;

				if (data) {

					element = webix.copy(data);

					// Adapter for state
					if (element.elementTypeState) {

						let elementTypeState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", element.elementTypeState.id);

						if (elementTypeState) {

							element.elementTypeState = elementTypeState;
						}
					}

					// Adapter for measures
					let elementValues = getElementValues();

					if (elementValues) {

						element.elementValues = elementValues;
					}

					// TODO: Adapter for alarms


					if (update_callback) {

						update_callback(elements);
					}

					function getElementValues() {

						let elementValuesToUpdate = [];

						if (element && Array.isArray(elementTypeParams) && elementTypeParams.length > 0 &&
							Array.isArray(element.elementValues) && element.elementValues.length > 0) {

							element.elementValues.forEach(elementValue => {

								let elementTypeParamId = util.element.getElementTypeParamId(elementValue.id);

								let elementTypeParam = elementTypeParams.find(e => e.id === elementTypeParamId);

								if (elementTypeParam) {

									elementValue.elementTypeParam = elementTypeParam;

									elementValuesToUpdate.push(elementValue);
								}

							});
						}

						return elementValuesToUpdate;
					}
				}


				return element;
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

						if (e.activation) {

							itemOutput.activation = new Date(e.activation);
						}

						itemOutput.idStr = e.id;
						itemOutput.alarmConfigId = e.alarmConfig.id;
						itemOutput.alarmConfigLabelAlias = i18n.t(alarmConfig.labelAlias);
						itemOutput.alarmLevelId = alarmLevel.id;
						itemOutput.alarmLevelAlias = alarmLevel.labelAlias;

						if (e.mutedUntil) {

							itemOutput.mutedUntil = new Date(e.mutedUntil);
						}

						if (e.recognized) {

							itemOutput.recognized = new Date(e.recognized);
						}

						if (e.deactivation) {

							itemOutput.deactivation = new Date(e.deactivation);
						}

						itemOutput.targetElementId = targetElement.id;
						itemOutput.targetElementAlias = targetElement.alias;

						itemOutput.targetElementTypeId = targetElement.elementType.id;
						itemOutput.targetElementTypeAlias = i18n.t(window.em.getByField(EntityType.ELEMENT_TYPE, "id", targetElement.elementType.id).labelAlias);

						if (itemOutput.deactivation) {

							itemOutput.isActive = String(false);
						}
						else {

							itemOutput.isActive = String(true);
						}


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
		},

		auditLog: {

			getList(data, update_callback) {

				if (data != undefined && Array.isArray(data.basicElementsVO) && data.basicElementsVO.length > 0) {

					let gridData = {};
					gridData.data = [];
					gridData.action = data.action;

					data.basicElementsVO.forEach(auditLog => {

						let dataItem = {};
						dataItem.id = auditLog.id;

						if (gridData.action == 'CREATE') {

							let logType = window.em.getByField(EntityType.LOG_TYPE, "id", auditLog.logType.id);
							let user = window.em.getByField(EntityType.USER, "id", auditLog.user.id);
							let logSubtype = {};

							if (logType != null && logType.logSubtypes != undefined && logType.logSubtypes.length > 0) {

								logSubtype = logType.logSubtypes.find(logSubtype => logSubtype.id == auditLog.logSubtype.id);
							}

							dataItem.time = auditLog.time;
							dataItem.level = auditLog.level;
							dataItem.userName = user.name;

							if (logSubtype != undefined) {

								dataItem.logSubtypeLabelAlias = logSubtype.labelAlias;
							}

							if (auditLog.auditLogValues != undefined && Array.isArray(auditLog.auditLogValues) && auditLog.auditLogValues.length > 0) {

								auditLog.auditLogValues.forEach(auditLogValue => {

									if (auditLogValue.value != undefined && auditLogValue.logTypeParam != undefined) {

										let logTypeParamId = auditLogValue.logTypeParam.id;

										dataItem[logTypeParamId] = auditLogValue.value;
									}

								});
							}
							gridData.data.push(dataItem);

						} else if (gridData.action == 'DELETE') {

							gridData.data.push(dataItem);

						}
					});

					update_callback(gridData);
				}
			}
		},

		planSchedule: {

			getList(data, update_callback) {

				if (data != undefined && Array.isArray(data.basicElementsVO) && data.basicElementsVO.length > 0) {

					let gridData = {};
					gridData.data = [];
					gridData.action = data.action;


					data.basicElementsVO.forEach(planSchedule => {


						let dataItem = {};
						dataItem.id = planSchedule.id;

						if (gridData.action == 'CREATE') {

							let plan = window.em.getByField(EntityType.PLAN, "id", planSchedule.plan.id);

							planSchedule.alias !== undefined ? dataItem.alias = planSchedule.alias : null;
							planSchedule.description !== undefined ? dataItem.description = planSchedule.description : null;
							planSchedule.cronExpression !== undefined ? dataItem.cronExpression = planSchedule.cronExpression : null;
							planSchedule.plan !== undefined ? dataItem.planAlias = plan.alias : null;

							gridData.data.push(dataItem);

						}

						if (gridData.action == 'UPDATE') {

							let planScheduleStateType = null;
							let plan = null;
							planSchedule.planScheduleStateType !== undefined ? planScheduleStateType = window.em.getByField(EntityType.PLAN_SCHEDULE_STATE, "id", planSchedule.planScheduleStateType.id) : null;
							planSchedule.plan !== undefined ? plan = window.em.getByField(EntityType.PLAN, "id", planSchedule.plan.id) : null;

							planSchedule.alias !== undefined ? dataItem.alias = planSchedule.alias : null;
							planSchedule.description !== undefined ? dataItem.description = planSchedule.description : null;
							planSchedule.cronExpression !== undefined ? dataItem.cronExpression = planSchedule.cronExpression : null;
							plan !== null ? dataItem.planAlias = plan.alias : null;
							planScheduleStateType !== null ? dataItem.planScheduleStateTypeLabelAlias = planScheduleStateType.labelAlias : null;

							gridData.data.push(dataItem);

						}

						if (gridData.action == 'DELETE') {

							gridData.data.push(dataItem);
						}

					});

					update_callback(gridData);
				}

			}
		},

		plan: {

			getList(data, update_callback) {

				if (data != undefined && Array.isArray(data.basicElementsVO) && data.basicElementsVO.length > 0) {

					let gridData = {};
					let count = data.basicElementsVO.length;
					gridData.data = [];
					gridData.action = data.action;

					data.basicElementsVO.forEach(plan => {

						let dataItem = {};
						dataItem.id = plan.id;

						if (gridData.action === 'UPDATE') {

							webix.ajax().get(appConfig.restUrl + PLAN_END_POINT + "/" + plan.id).then(function(dat) {

								if (dat != null) {

									let plan = dat.json();


									dataItem.alias = plan.alias;
									dataItem.description = plan.description;
									dataItem.planStateTypeLabelAlias = plan.planStateType ? plan.planStateType.labelAlias : undefined;
									dataItem.planSubtypeAlias = plan.planSubtype ? plan.planSubtype.alias : undefined;
									dataItem.planSubtypePlanTypeAlias = plan.planSubtype && plan.planSubtype.planType ? plan.planSubtype.planType.alias : undefined;
									dataItem.currentPlanOrderAlias = plan.currentPlanOrder ? plan.currentPlanOrder.alias : undefined;
									dataItem.currentPlanOrderPlanOrderOrder = plan.currentPlanOrder ? plan.currentPlanOrder.planOrderOrder : undefined;

									gridData.data.push(dataItem);

									count--;

									if (count == 0)
										update_callback(gridData);
								}

							}, function(response) { util.ajax.handleError(response); });

						} else  if (gridData.action === 'DELETE'){

							gridData.data.push(dataItem);

							count--;

							if (count == 0)
								update_callback(gridData);
						}
					});
				}
			}
		},

		order: {

			getList(data, update_callback) {

				let listOutput = [];

				if (data != undefined && Array.isArray(data) && data.length > 0) {

					data.forEach(e => {

						let itemOutput = {};

						itemOutput.id = e.id;

						try {

							itemOutput.orderStatusId = e.planOrderStateType.id;
							itemOutput.orderStatus = e.planOrderStateType.alias;
							itemOutput.orderResult = e.planOrderStateType.labelAlias;

						} catch (error) {
							itemOutput.orderStatusId = null;
							itemOutput.orderStatus = "-";
							itemOutput.orderResult = "LBL_EMPTY";
						}

						itemOutput.orderTypeId = e.planOrderType.id;

						try {

							itemOutput.orderTypeLabelAlias = i18n.t(e.planOrderType.labelAlias);

						} catch (error) {

							itemOutput.orderTypeLabelAlias = "-";
						}

						itemOutput.orderAlias = e.alias;
						itemOutput.orderExuctedAt = e.orderExuctedAt;

						listOutput.push(itemOutput);
					});

					if (update_callback) {

						update_callback(listOutput);
					}
				}

				return listOutput;
			}
		}

	}
};

function updateObject(oldItem, newItem) {

	if (oldItem) {

		if (newItem) {

			for (let i in newItem) {

				if (typeof newItem[i] === 'object' && newItem[i] !== null) {

					oldItem[i] = updateObject(oldItem[i], newItem[i]);

				} else if (newItem[i] !== undefined) {

					oldItem[i] = newItem[i];
				}
			}
		}

	} else {

		oldItem = newItem;
	}

	return oldItem;
}  