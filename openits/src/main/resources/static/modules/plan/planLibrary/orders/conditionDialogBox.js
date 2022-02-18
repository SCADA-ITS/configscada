import { util } from "../../../../lib/util.js";
import SearchDialogBox from "../../../../component/searchDialogBox.js";
import InputDialogBox from "../../../../component/inputDialogBox.js";
import MessageBox from "../../../../component/messageBox.js";

const CHANGE_DIALOG_BOX_WIDTH = 700;
const CHANGE_DIALOG_BOX_HEIGHT = 600;
	
export default class ConditionDialogBox {
	
	constructor(planOrderCondition, config, onAccept) {

		this.uid = webix.uid();
		this.planOrderCondition = planOrderCondition;
		this.onAccept = onAccept;
		this.containerId = undefined;
		this.config = config;

		this.element = undefined;
		this.elementTypeParam = undefined;
		this.elementTypeState = undefined;
		this.thresholdOperation = undefined;
		this.thresholdVal1 = undefined;
		this.thresholdVal2 = undefined;
		this.nexusAnd = undefined;
	}

	show() {

		let self = this;
		
		if(self.planOrderCondition) {

			if(self.planOrderCondition.element) {

				self.element = self.planOrderCondition.element;
			}

			if(self.planOrderCondition.elementTypeParam) {

				self.elementTypeParam = self.planOrderCondition.elementTypeParam;
			}
			
			if(self.planOrderCondition.elementTypeState) {

				self.elementTypeState = self.planOrderCondition.elementTypeState;
			}

			if(self.planOrderCondition.thresholdOperation) {

				self.thresholdOperation = self.planOrderCondition.thresholdOperation;
			}

			self.thresholdVal1 = self.planOrderCondition.thresholdVal1;
			self.thresholdVal2 = self.planOrderCondition.thresholdVal2;
			self.nexusAnd = self.planOrderCondition.nexusAnd;
		}
		
		let body = {
			id: "change-condition-search-dialog-" + this.uid,
			cols: [
				{ width: 20 },
				{					
					rows: [				
						{ height: 10 },
						getBodyComponent(),
						{ height: 10 },
						getToolbarActions()
					]
				},	
				{ width: 20 }
			]
		};
		
		self.containerId = util.ui.loadWindow(i18n.t("LBL_PLAN_ORDER_TYPE_CONDITION"), body, CHANGE_DIALOG_BOX_HEIGHT, CHANGE_DIALOG_BOX_WIDTH, true);
		
		function getBodyComponent() {

			let bodyComponent = {
				rows: [
					getNexusAndRow(),
					{
						view: "form",
						elementsConfig: { labelAlign: "left" },
						css: "global-form condition-dialog-box",
						elements: [
							getElemenRow(),
							{ view: "template", template: i18n.t("LBL_STATE"), type: "section" },
							getElementTypeStateRow(),
							{ view: "template", template: i18n.t("LBL_PARAM"), type: "section" },
							getElementTypeParamRow(),
							getThresholdOperationRow(),
							getThresholdVal1Row(),
							getThresholdVal2Row()
						]
					}
				]
			};
			

			function getElemenRow() {

				let equipmentRow;

				equipmentRow = {
					id: "change-condition-element-row-" + self.uid,
					cols: [
						{ view: "text", name: "equipmentName", value: (self.element?self.element.alias:""), required: true, label: i18n.t("LBL_EQUIPMENT"), disabled: true, labelWidth: 325 },
						{
							view: "icon", icon: "wxi-search", disabled: false,
							on: {
								onItemClick: function() {

									getWindowElementSelected();
								}
							}
						},
						{ width: 90 }
					]
				};

				return equipmentRow;
			}

			function getElementTypeParamRow() {

				let elementTypeParamRow;

				let disabled = true;				
				if(self.element && self.element.id != null) {

					disabled = false;
				} 

				elementTypeParamRow = {
					id: "change-condition-element-type-param-row-" + self.uid,
					cols: [
						{ view: "text", value: (self.elementTypeParam?i18n.t(self.elementTypeParam.labelAlias):""), required: false, label: i18n.t("LBL_PARAM"), disabled: true, labelWidth: 325 },
						{
							view: "icon", icon: "wxi-search", disabled: disabled,
							on: {
								onItemClick: function() {

									getWindowElementTypeParamSelected();
								}
							}
						},
						{ width: 90 }
					]
				};

				return elementTypeParamRow;
			}

			function getElementTypeStateRow() {

				let elementTypeStateRow;

				let disabled = true;				
				if(self.element && self.element.id != null) {

					disabled = false;
				} 

				elementTypeStateRow = {
					id: "change-condition-element-type-state-row-" + self.uid,
					cols: [
						{ view: "text", value: (self.elementTypeState?i18n.t(self.elementTypeState.labelAlias):""), required: false, label: i18n.t("LBL_STATE_LABEL"), disabled: true, labelWidth: 325 },
						{
							view: "icon", icon: "wxi-search", disabled: disabled,
							on: {
								onItemClick: function() {

									getWindowElementTypeStateSelected();
								}
							}
						},
						{ width: 90 }
					]
				};

				return elementTypeStateRow;
			}

			function getThresholdOperationRow() {

				let thresholdOperationRow;

				let disabled = true;				
				if(self.elementTypeParam && self.elementTypeParam.id != null) {

					disabled = false;
				}

				thresholdOperationRow = {
					id: "change-condition-threshold-operation-row-" + self.uid,
					cols: [
						{ view: "text", value: (self.thresholdOperation?i18n.t(self.thresholdOperation.labelAlias):""), required: false, label: i18n.t("LBL_THRESHOLD_OPERATION"), disabled: true, labelWidth: 325 },
						{
							view: "icon", icon: "wxi-search", disabled: disabled,
							on: {
								onItemClick: function() {

									getWindowThresholdOperationSelected();
								}
							}
						},
						{ width: 90 }
					]
				};

				return thresholdOperationRow;
			}

			function getThresholdVal1Row() {

				let thresholdVal1Row;

				let disabled = true;				
				if(self.elementTypeParam && self.elementTypeParam.id != null) {

					disabled = false;
				}

				thresholdVal1Row = {
					id: "change-threshold-value-one-row-" + self.uid,
					cols: [
						{ view: "text", value: self.thresholdVal1, required: false, label: i18n.t("LBL_THRESHOLD_OPERATION_ONE"), disabled: true, labelWidth: 325 },
						{
							view: "icon", icon: "mdi mdi-pencil", disabled: disabled,
							on: {
								onItemClick: function() {

									getWindowThresholdVal1Selected();
								}
							}
						},
						{ width: 90 }
					]
				};

				return thresholdVal1Row;
			}

			function getThresholdVal2Row() {

				let thresholdVal2Row;

				let disabled = true;				
				if(self.elementTypeParam && self.elementTypeParam.id != null) {

					disabled = false;
				}

				thresholdVal2Row = {
					id: "change-threshold-value-two-row-" + self.uid,
					cols: [
						{ view: "text", value: self.thresholdVal2, required: false, label: i18n.t("LBL_THRESHOLD_OPERATION_TWO"), disabled: true, labelWidth: 325 },
						{
							view: "icon", icon: "mdi mdi-pencil", disabled: disabled,
							on: {
								onItemClick: function() {

									getWindowThresholdVal2Selected();
								}
							}
						},
						{ width: 90 }
					]
				};

				return thresholdVal2Row;
			}

			function getNexusAndRow() {

				let nexusAndRow;

				nexusAndRow = {
					id: "change-nexus-operator-row-" + self.uid,
					cols: [
						{},
						{ 
							view: "switch", 
							value: self.nexusAnd,
							width :125,
							on:{
								onChange: function (newVal, oldVal) {

									updateNexusAnd(newVal);
								}
							},
							onLabel: i18n.t("LBL_OR"),
      						offLabel: i18n.t("LBL_YES")
						}
					]
				};

				return nexusAndRow;
			}

			function getWindowElementSelected() {
	
				let dialogEntity = webix.copy(SearchDialogBox.CUSTOM);
	
				dialogEntity.data = [];
				
				dialogEntity.pkField = "id"
				dialogEntity.fields = ["element=LBL_EQUIPMENT", "elementType=LBL_EQUIPMENT_TYPE"];
				dialogEntity.height = 600;
				dialogEntity.width = 800;
				dialogEntity.sortFieldId = 2;

				let elements = window.em.getAll(EntityType.ELEMENT);
				
				if (Array.isArray(elements) && elements.length > 0) {
						
					elements.forEach(el => {
					
						dialogEntity.data.push({
							id: el.id,
							element: el.alias,
							elementType: i18n.t(el.elementType.labelAlias)
						})
					});
				}
					
				let s = new SearchDialogBox(i18n.t("LBL_SELECT_EQUIPMENT"), dialogEntity, onAccept);
	
				s.show();

				function onAccept(selectedIds) {

					let updateRows = false;

					if (selectedIds && Array.isArray(selectedIds) && selectedIds.length > 0){

						if(!self.element || self.element.id != selectedIds[0].id){

							updateRows = true;
						}

						self.element = window.em.getByField(EntityType.ELEMENT, "id", selectedIds[0].id);
					} else {

						self.element = null;
						updateRows = true;
					}
					
					updateElement(self.element);

					if(updateRows) {

						updateElementTypeParam(null);
						updateThresholdOperation(null);
						updateThresholdVal1(null);
						updateThresholdVal2(null);
						updateElementTypeState(null);
					}
				}
			}

			function getWindowElementTypeParamSelected() {
	
				let dialogEntity = webix.copy(SearchDialogBox.CUSTOM);
	
				dialogEntity.data = [];
				
				dialogEntity.pkField = "id"
				dialogEntity.fields = ["element=LBL_EQUIPMENT_TYPE_PARAM"];
				dialogEntity.height = 600;
				dialogEntity.width = 800;
				dialogEntity.sortFieldId = 1;

				let elements = window.em.getByField(EntityType.ELEMENT_TYPE, "id", self.element.elementType.id).elementTypeParams;
				
				if (Array.isArray(elements) && elements.length > 0) {
						
					elements.forEach(el => {
					
						if (el.id.split(":")[2] == 1) {

							dialogEntity.data.push({
								id: el.id,
								element: i18n.t(el.labelAlias)
							});
						}
						
					});
				}
					
				let s = new SearchDialogBox(i18n.t("LBL_SELECT_PARAM"), dialogEntity, onAccept);
	
				s.show();

				function onAccept(selectedIds) {

					let updateRows = false;

					if (selectedIds && Array.isArray(selectedIds) && selectedIds.length > 0){

						if(!self.elementTypeParam || self.elementTypeParam.id != selectedIds[0].id){

							updateRows = true;
						}

						self.elementTypeParam = window.em.getByField(EntityType.ELEMENT_TYPE, "id", self.element.elementType.id).elementTypeParams.find(e => e.id == selectedIds[0].id);
					
					} else {
						
						self.elementTypeParam = null;
						updateRows = true;
					}
					
					updateElementTypeParam(self.elementTypeParam)

					if(updateRows){

						updateThresholdOperation();
						updateThresholdVal1();
						updateThresholdVal2();						
					}
				}
			}

			function getWindowElementTypeStateSelected() {
	
				let dialogEntity = webix.copy(SearchDialogBox.CUSTOM);
	
				dialogEntity.data = [];
				
				dialogEntity.pkField = "id"
				dialogEntity.fields = ["state=LBL_EQUIPMENT_TYPE_STATE"];
				dialogEntity.height = 600;
				dialogEntity.width = 800;
				dialogEntity.sortFieldId = 1;

				let elementTypeStates = window.em.getByField(EntityType.ELEMENT_TYPE, "id", self.element.elementType.id).elementTypeStates;
				
				if (Array.isArray(elementTypeStates) && elementTypeStates.length > 0) {
						
					elementTypeStates.forEach(el => {

						dialogEntity.data.push({
							id: el.id,
							state: i18n.t(el.labelAlias)
						});						
					});
				}
					
				let s = new SearchDialogBox(i18n.t("LBL_SELECT_STATE"), dialogEntity, onAccept);
	
				s.show();

				function onAccept(selectedIds) {

					if (selectedIds && Array.isArray(selectedIds) && selectedIds.length > 0){

						self.elementTypeState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", selectedIds[0].id);
					
					} else {
						
						self.elementTypeState = null;
					}
					
					updateElementTypeState(self.elementTypeState)
				}
			}

			function getWindowThresholdOperationSelected() {
	
				let dialogEntity = webix.copy(SearchDialogBox.CUSTOM);
	
				dialogEntity.data = [];
				
				dialogEntity.pkField = "id"
				dialogEntity.fields = ["operation=LBL_THRESHOLD_OPERATION"];
				dialogEntity.height = 600;
				dialogEntity.width = 800;
				dialogEntity.sortFieldId = 1;

				webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.thresholdOperations).then(function(dat){
				
					if (dat != null) {

						let thresholdOperations = dat.json();
				
						if (Array.isArray(thresholdOperations) && thresholdOperations.length > 0) {
								
							thresholdOperations.forEach(el => {

								dialogEntity.data.push({
									id: el.id,
									operation: i18n.t(el.labelAlias)
								});						
							});
						}
							
						let s = new SearchDialogBox(i18n.t("LBL_SELECT_THRESHOLD_OPERATION"), dialogEntity, onAccept);
			
						s.show();

						function onAccept(selectedIds) {

							if (selectedIds && Array.isArray(selectedIds) && selectedIds.length > 0){

								self.thresholdOperation = thresholdOperations.find(e => e.id == selectedIds[0].id);
							
							} else {
								
								self.thresholdOperation = null;
							}
							
							updateThresholdOperation(self.thresholdOperation);
						}
					}
				});				
			}
			
			function getWindowThresholdVal1Selected() {
					
				let i = new InputDialogBox(i18n.t("LBL_THRESHOLD_OPERATION_ONE"), i18n.t("LBL_ENTER_VALUE"), onAccept, false, self.thresholdVal1, self.elementTypeParam.dataType);
	
				i.show();

				function onAccept(inputValue) {

					updateThresholdVal1(inputValue);
				}
			}

			function getWindowThresholdVal2Selected() {
					
				let i = new InputDialogBox(i18n.t("LBL_THRESHOLD_OPERATION_TWO"), i18n.t("LBL_ENTER_VALUE"), onAccept, false, self.thresholdVal2, self.elementTypeParam.dataType);
	
				i.show();

				function onAccept(inputValue) {

					updateThresholdVal2(inputValue);
				}
			}


			function updateElement(value){

				self.element = value;
				webix.ui(getElemenRow(), $$("change-condition-element-row-" + self.uid));
			}
			
			function updateElementTypeParam(value){

				self.elementTypeParam = value;
				webix.ui(getElementTypeParamRow(), $$("change-condition-element-type-param-row-" + self.uid));
			}

			function updateElementTypeState(value){

				self.elementTypeState = value;
				webix.ui(getElementTypeStateRow(), $$("change-condition-element-type-state-row-" + self.uid));
			}

			function updateThresholdOperation(value){

				self.thresholdOperation = value;
				webix.ui(getThresholdOperationRow(), $$("change-condition-threshold-operation-row-" + self.uid));
			}

			function updateThresholdVal1(value){

				self.thresholdVal1 = value;
				webix.ui(getThresholdVal1Row(), $$("change-threshold-value-one-row-" + self.uid));
			}

			function updateThresholdVal2(value){

				self.thresholdVal2 = value;
				webix.ui(getThresholdVal2Row(), $$("change-threshold-value-two-row-" + self.uid));
			}

			function updateNexusAnd(value){

				self.nexusAnd = value;
				webix.ui(getNexusAndRow(), $$("change-nexus-operator-row-" + self.uid));			}

			return bodyComponent;
		}
		
		function getToolbarActions() {
		
			let viewToolbarActions = {};
			
			viewToolbarActions = {
				view: "toolbar",
				height: 45,
				borderless: true,
				elements: []
			};
			
			if (!self.onAccept) {
				
				viewToolbarActions.elements.push({});
			}
			
			viewToolbarActions.elements.push({ 
				view: "button",
				css: "dialog-cancel", 
				width: 130,
				height: 35, 
				label: i18n.t("LBL_CANCEL"),
				on: {
					onItemClick() {
					
						$$(self.containerId).close();
					}
				}
			});
			
			if (self.onAccept) {
				
				viewToolbarActions.elements.push({});
				viewToolbarActions.elements.push({ 
					view: "button", 
					css: "dialog-accept",
					width: 130,
					height: 35,
					label: i18n.t("LBL_ACCEPT"),
					on: {
						onItemClick() {

							if(isValidForm()){

								if(!self.planOrderCondition){

									self.planOrderCondition = {};
								}
	
								self.planOrderCondition.element = self.element;
								self.planOrderCondition.elementTypeParam = self.elementTypeParam;
								self.planOrderCondition.elementTypeState = self.elementTypeState;
								self.planOrderCondition.thresholdOperation = self.thresholdOperation;
								self.planOrderCondition.thresholdVal1 = self.thresholdVal1;
								self.planOrderCondition.thresholdVal2 = self.thresholdVal2;
								self.planOrderCondition.nexusAnd = self.nexusAnd;
	
								self.onAccept(self.planOrderCondition);
	
								$$(self.containerId).close();
							
							} else {
								
								let ms = new MessageBox (MessageBox.WARN, i18n.t("LBL_PLAN_LIBRARY_REQUIRED_PARAMS"), null, null);
								ms.show();

							}
						}
					}
				});
			}

			function isValidForm(){

				let result = true;

				if(!self.element){

					result = false;
				}

				return result;
			}
			
			return viewToolbarActions;	
		}
	}
}