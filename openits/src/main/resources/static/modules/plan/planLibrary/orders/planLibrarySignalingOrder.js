import { util } from "../../../../lib/util.js";
import PlanLibraryOrder from "./planLibraryOrder.js";
import PlanLibraryPersist from "../planLibraryPersist.js";
import SearchDialogBox from "../../../../component/searchDialogBox.js";
import MessageBox from "../../../../component/messageBox.js";

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
						planOrder: self.planOrder,
						newPlanOrder: undefined
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
	
	getPlanOrder() {

		let self = this;	
		let planOrder;	
		
		if (self.planOrder && self.signalingModuleInstance) {
			
			let commandElement = self.signalingModuleInstance.getSignaling();
			
			if (commandElement) {
				
				planOrder = {
					id: self.planOrder.id,
					planOrderTypeId: self.planOrder.planOrderType.id,
					comandElement: {
						
						commandElementTypeId: commandElement.commandElementType.id,
						elementId: commandElement.element.id,
						commandElementValues: function() {
							
							let commandElementValues = undefined;
							
							if (Array.isArray(commandElement.commandElementValues) && commandElement.commandElementValues.length > 0) {
							
								commandElementValues = commandElement.commandElementValues.map(e => {
								
									let value = undefined;
									
									if (e.value && typeof e.value === 'object') {
										
										value = JSON.stringify(e.value);
									}
									else {
										
										value = e.value;
									}
									
									return {
										
										commandElementTypeParamId: e.commandElementTypeParam.id,
										value: value
									}
								});	
							}
							
							return commandElementValues;
							 
						}()
					},
				}
			}
		}
		
		return planOrder;
	}
	
	createPlanOrder(planOrder) {
		
		let self = this;
		let elementTypeSelected;
		let elementSelected;
		
		if (planOrder && self.plan) {
			
			let planLibraryPersist = new PlanLibraryPersist(self.config);
		
			let newPlanOrder = {
				planId: self.plan.id,
				planOrderTypeId: planOrder.planOrderType.id,
				planOrderOrder: planOrder.planOrderOrder
			}
			
			searchDialogElementType();
			
			function onSelectElementType(elementType) {
				
				elementTypeSelected = elementType;
				
				if (elementTypeSelected && Array.isArray(elementTypeSelected.commandElementTypes) && elementTypeSelected.commandElementTypes.length > 0) {
								
					searchDialogElement();
				}
				else {
					
					let ms = new MessageBox (MessageBox.WARN, i18n.t("LBL_ELEMENT_TYPE_WITHOUT_COMMANDS_DESCRIPTION"), null, null);
					ms.show();		
				}
			}
			
			function onSelectElement(elementId) {
				
				if (newPlanOrder && elementId && elementTypeSelected && Array.isArray(elementTypeSelected.commandElementTypes) && elementTypeSelected.commandElementTypes.length > 0) {
					
					elementSelected = window.em.getByField(EntityType.ELEMENT, "id", elementId);
					
					if (elementSelected) {
						
						newPlanOrder.alias = planOrder.planOrderType.alias + " - " + elementSelected.alias;
						newPlanOrder.description = newPlanOrder.alias;
						
						newPlanOrder.comandElement = {
							elementId: elementSelected.id,
							commandElementTypeId: elementTypeSelected.commandElementTypes[0].id
						}
						
						planLibraryPersist.createPlanOrder(newPlanOrder).then(result => {
							
							self.onUpdate(self.plan, result);	
						});	
					}
						
				}
			}
			
			function searchDialogElementType() {
				
				let dialogElementTypes = webix.copy(SearchDialogBox.CALLBACK);
				let label = i18n.t("LBL_STEP_1_OF_2_SELECT_ELEMENT_TYPE");
				let elementTypes;
				let elements;
				
				dialogElementTypes.height = 600;
				dialogElementTypes.width = 800;
				dialogElementTypes.fillDialogEntity = fillDialogEntity;
				
				function fillDialogEntity(dialogEntity, onFilledDialogEntity) {
					
					elementTypes = window.em.getAll(EntityType.ELEMENT_TYPE);
					elements = window.em.getAll(EntityType.ELEMENT);
				
					if (Array.isArray(elementTypes) && elementTypes.length > 0) {
						
						elementTypes = elementTypes.filter(e => Array.isArray(e.commandElementTypes) && e.commandElementTypes.length > 0);
						
						if (Array.isArray(elements) && elements.length > 0) {
							
							elementTypes = elementTypes.filter(elementType => (elements.findIndex(element => element.elementType.id === elementType.id) !== -1));
						}
						
						dialogEntity.data = [];
		
						dialogEntity.pkField = "id"
						dialogEntity.fields = ["idIcon=", "alias=LBL_ELEMENT_TYPE_ALIAS", "description=LBL_ELEMENT_TYPE_DESCRIPTION"];
						dialogEntity.sortFieldId = 3; 
						
						dialogEntity.icons = {
							idIcon: []
						}
						
						elementTypes.forEach(elementType => {
							
							dialogEntity.icons.idIcon.push({
								id: elementType.id,
								icon: "img/" + elementType.elementTypePhoto
							});
							
							dialogEntity.data.push({
								id: elementType.id,
								idIcon: elementType.id,
								alias: i18n.t(elementType.labelAlias),
								description: i18n.t(elementType.labelDescription)
							});
						});
					}
					
					onFilledDialogEntity(dialogEntity);
				}
					
				let s = new SearchDialogBox(label, dialogElementTypes, function(items) {

					if (Array.isArray(items) && items.length === 1) {
						
						onSelectElementType(elementTypes.find(elementType => elementType.id == items[0].id));
					}
				});
	
				s.show();
			}
			
			function searchDialogElement() {
				
				let dialogElements = webix.copy(SearchDialogBox.CALLBACK);
				let label = i18n.t("LBL_STEP_2_OF_2_SELECT_ELEMENT");
				let elements;
				
				dialogElements.height = 600;
				dialogElements.width = 800;
				dialogElements.fillDialogEntity = fillDialogEntity;
				
				function fillDialogEntity(dialogEntity, onFilledDialogEntity) {
					
					elements = window.em.getAll(EntityType.ELEMENT);
				
					if (Array.isArray(elements) && elements.length > 0) {
						
						elements = elements.filter(e => e.elementType.id === elementTypeSelected.id); 
					
						if (Array.isArray(elements) && elements.length > 0) {
							
							dialogEntity.data = [];
			
							dialogEntity.pkField = "id"
							dialogEntity.fields = ["alias=LBL_ELEMENT_ALIAS", "description=LBL_ELEMENT_DESCRIPTION"];
							dialogEntity.sortFieldId = 2; 
							
							elements.forEach(element => {
								
								dialogEntity.data.push({
									id: element.id,
									alias: element.alias,
									description: element.description
								});
							});
						}
					}
					
					onFilledDialogEntity(dialogEntity);
				}
						
				let s = new SearchDialogBox(label, dialogElements, function(items) {

					if (Array.isArray(items) && items.length === 1) {

						onSelectElement(items[0].id);
					}
				});
	
				s.show();
			}
		}
	}
}	
