import { util } from "../../../../lib/util.js";
import PlanLibraryOrder from "./planLibraryOrder.js";
import PlanLibraryPersist from "../planLibraryPersist.js";
import SearchDialogBox from "../../../../component/searchDialogBox.js";
import MessageBox from "../../../../component/messageBox.js";
import EquipmentList from "../../../../component/equipmentList.js";

export default class PlanLibrarySignalingOrder extends PlanLibraryOrder {

	constructor(config, uid, plan, planOrder, onUpdate) {
		
		super(config, uid, plan, planOrder, onUpdate);
		
		this.signalingModuleInstance = undefined;
	}
	
	loadData() {
	
		let self = this;

		let moduleActionId = self.config.moduleAction.defaultPlanOrderEdit;
		
		if (self.planOrder && self.planOrder.comandElement && self.planOrder.comandElement.element && self.planOrder.comandElement.element.elementType) {
			
			if (self.planOrder.comandElement.element.elementType.planOrderSignalingModule) {
			
				moduleActionId = parseInt(self.planOrder.comandElement.element.elementType.planOrderSignalingModule);
			}
			
			let module = util.module.getFromModuleAction(moduleActionId);
			
			if (module) {
	
				let moduleAction = { id: moduleActionId };
				let args = { 
					selectedPks: [self.planOrder.comandElement.element.id],
					data: {
						commandElement: self.planOrder.comandElement,

					}
				};
	
				util.module.open(module, moduleAction, VIEW_TYPE_CUSTOM, drawTabContent, args, function(instance) { self.signalingModuleInstance = instance });
				
				function drawTabContent(view) {

					let newContentTab = {
						id: "plan-order-signaling-content-" + self.uid,
						view: "scrollview",
						scroll: "y",
						body: {
							cols: [view]
						}
//						cols: [view]
					};

					webix.ui(newContentTab, $$("plan-order-signaling-content-" + self.uid));
				}
			}
		}
		
		function saveOrder(commandElement) {
			
			// TODO: Implement saveOrder and call save method
		}
	}
	
	getContent() {
			
		let self = this;
		let viewContent; 
		
		viewContent = {
			id: "plan-order-signaling-content-" + self.uid,
		}
		
		return viewContent;
	}
	
	createCustomPlanOrder(newPlanOrder) {

	}
	
	getPlanOrder(newPlan) {

		let self = this;	
		let planOrder;	
		let newPlanOrder = (newPlan) ? newPlan : self.planOrder;
		let commandElement;
		
		if (newPlanOrder && self.signalingModuleInstance) {
			
			commandElement = self.signalingModuleInstance.getSignaling();
		}
			
		if (commandElement) {
			
			planOrder = {
				id: newPlanOrder.id,
				planOrderTypeId: newPlanOrder.planOrderType.id,
				comandElement: {
					
					commandElementTypeId: commandElement.commandElementType.id,
					elementId: newPlanOrder.element ? newPlanOrder.element.id : commandElement.element.id,
					commandElementValues: getCommandElementValues()
				}
			}
		}
		
		function getCommandElementValues() {
							
			let commandElementValues = undefined;
			
			if (Array.isArray(commandElement.commandElementValues) && commandElement.commandElementValues.length > 0) {
			
				commandElementValues = commandElement.commandElementValues.filter(f => f.value).map(e => {

					return {
					
						commandElementTypeParamId: e.commandElementTypeParam.id,
						value: (typeof e.value === 'object') ? JSON.stringify(e.value) : e.value
					}	
				});	
			}
			
			return Array.isArray(commandElementValues) && commandElementValues.length > 0 ? commandElementValues : undefined;
			 
		}
		
		return planOrder;
	}
	
	createPlanOrder(planOrder) {
		
		let self = this;
		let elementTypeSelected;
		
		if (planOrder && self.plan) {
			
			let planLibraryPersist = new PlanLibraryPersist(self.config);
		
			let newPlanOrder = {
				planId: self.plan.id,
				planOrderTypeId: planOrder.planOrderType.id,
				planOrderOrder: planOrder.planOrderOrder
			}
			
			searchDialogElementType();
			
			function onSelectElement(elementSelected) {
				
				if (newPlanOrder && elementSelected && Array.isArray(elementSelected.commandElements) && elementSelected.commandElements.length > 0) {
					
					newPlanOrder.alias = planOrder.planOrderType.alias + " - " + elementSelected.alias;
					newPlanOrder.description = newPlanOrder.alias;
					
					newPlanOrder.comandElement = {
						elementId: elementSelected.id,
						commandElementTypeId: elementSelected.commandElements[0].id
					}
					
					planLibraryPersist.createPlanOrder(newPlanOrder).then(result => {
						
						self.onUpdate(self.plan, result);	
					});	
				}
			}
			
			function onMultiselectElement(elementsSelected) {

				if (newPlanOrder && Array.isArray(elementsSelected)) {

					let counter = 0;

					persist();

					function persist() {
					
						let elementSelected = elementsSelected[counter];
						
						if (elementSelected && Array.isArray(elementSelected.commandElements) && elementSelected.commandElements.length > 0) {

							newPlanOrder.alias = planOrder.planOrderType.alias + " - " + elementSelected.alias;
							newPlanOrder.description = newPlanOrder.alias;
							newPlanOrder.planOrderOrder = planOrder.planOrderOrder + counter;

							newPlanOrder.comandElement = {
								elementId: elementSelected.id,
								commandElementTypeId: elementTypeSelected.commandElementTypes[0].id
							}

							planLibraryPersist.createPlanOrder(newPlanOrder).then(response => {

								let responseNewPlanOrder = {
									id: response.id,
									planOrderType: { id: response.planOrderType.id },
									element: { id: elementSelected.id }
								}


								let generatedPlanOrder = self.getPlanOrder(responseNewPlanOrder);

								if (generatedPlanOrder) {

									planLibraryPersist.updatePlanOrder(generatedPlanOrder).then(result => {

										if (result != null) {

											if (++counter < elementsSelected.length) {

												persist();

											} else {

												$$(self.signalingModuleInstance.args.containerId).close();
												self.onUpdate(self.plan, result);
											}
										}
									});
								}
							});
						}
					}
				}
			}
			
		
			function searchDialogElementType() {

				let elementTypes = window.em.getAll(EntityType.ELEMENT_TYPE);
				let elements = window.em.getAll(EntityType.ELEMENT);

				if (Array.isArray(elementTypes) && elementTypes.length > 0) {

					elementTypes = elementTypes.filter(e => Array.isArray(e.commandElementTypes) && e.commandElementTypes.length > 0);

					if (Array.isArray(elements) && elements.length > 0) {

						elementTypes = elementTypes.filter(elementType => (elements.findIndex(element => element.elementType.id === elementType.id) !== -1));
					}
				}

				let equipmentList = new EquipmentList(elementTypes, onSelect);
				
				function onSelect(items) {

					if (Array.isArray(items) && items.length > 0) {

						$$("dialog-accept-" + self.uid).enable();

					} else {

						$$("dialog-accept-" + self.uid).disable();
					}

				}

				let equipmentViewBody = {
					id: "equipmentList-" + self.uid,
					rows: [
						{
							cols: [
								{ width: 20 },
								{
									rows: [
										{ height: 20 },
										equipmentList.getView(),
										{ height: 15 }
									]
								},
								{ width: 20 }
							]
						},
						{
							cols: [
								{ width: 15 },
								{
									id: "toolbar-" + self.uid,
									view: "toolbar",
									height: 45,
									borderless: true,
									elements: [
										{
											view: "button",
											css: "dialog-cancel",
											width: 130,
											height: 35,
											label: i18n.t("LBL_CANCEL"),
											on: {
												onItemClick() {

													$$(equipmentListWindow).close();
												}
											}
										},
										{},
										{
											id: "dialog-accept-" + self.uid,
											view: "button",
											css: "dialog-accept",
											width: 130,
											height: 35,
											label: i18n.t("LBL_ACCEPT"),
											on: {
												onItemClick() {

													onAccept(equipmentList.tableView.getSelectedIds(), function() {
															
														$$(equipmentListWindow).close();
													});												
												}
											}
										}
									]
								},
								{ width: 15 }
							]
						}
					]
				};

				let equipmentListWindow = util.ui.loadWindow(i18n.t("LBL_ALARM_FILTER_LIST_EQUIPMENT_GRID"), equipmentViewBody, 600, 1100, true);

				if (equipmentList.listView && equipmentList.tableView) {

					equipmentList.tableView.callbacks();
					equipmentList.listView.selectByIndex(0);
				}


				function onAccept(items, close) {

					if (Array.isArray(items) && items.length > 0) {

						webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.config.restEndpoints.elementByIdsCommands, JSON.stringify(items)).then(function(elementsSelectedResponse) {

							if (elementsSelectedResponse != null) {

								let elementsSelected = elementsSelectedResponse.json();

								if (Array.isArray(elementsSelected) && elementsSelected.length > 0) {

									if (elementsSelected.length === 1) {

										onSelectElement(elementsSelected[0]);
										close();

									} else {

										let sameSubtype = true;										
										let subtypeIdsArr = elementsSelected.filter(element => element.elementSubtype);

										if (Array.isArray(subtypeIdsArr) && subtypeIdsArr.length > 0) {

											if (subtypeIdsArr.length === elementsSelected.length) {

												let subtypeId = elementsSelected[0].elementSubtype.id;

												for (let i = 0; sameSubtype && i < elementsSelected.length; i++) {

													sameSubtype = (elementsSelected[i].elementSubtype.id === subtypeId) ? true : false;
												}
											} else {

												sameSubtype = false;
											}
										} 

										if (sameSubtype) {

											let elementSelected = elements.find(element => element.id === elementsSelected[0].id);

											if (elementSelected) {

												elementTypeSelected = elementTypes.find(elementType => elementType.id === elementSelected.elementType.id);

												if (elementTypeSelected) {

													let modAction = (elementTypeSelected.planOrderSignalingModule) ? elementTypeSelected.planOrderSignalingModule : self.config.moduleAction.defaultPlanOrderEdit;
													let module = util.module.getFromModuleAction(parseInt(modAction));

													if (module) {

														let moduleAction = { id: module.config.moduleAction.commandEdit };

														let args = {
															selectedPks: items,
															callbacks: [
																function() {

																	$$("plan-lib-loading-" + self.uid).show();
																	$$("view-option-dialog-" + self.uid).hide();
																	$$("plan-lib-resizer-" + self.uid).hide();
																	$$("plan-lib-content-" + self.uid).hide();
																	onMultiselectElement(elementsSelected);
																}]
														};

														util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args, function(instance) {

															self.signalingModuleInstance = instance;
															let win = $$(self.signalingModuleInstance.args.containerId);

															util.ui.setTitle(win, i18n.t("LBL_CREATE_SIGNALING_ORDER"));															
															close();
														});

													} else {

														let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_MODULE_NOT_FOUND"), null, null);
														ms.show();
													}
												}
											}
											
										} else {
											
											let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_ELEMENTS_OF_DIFFERENT_SUBTYPE"), null, null);
											ms.show();																					
										}
									}
								}
							}
						});
					}
				}
			}
		}
	}
}	
