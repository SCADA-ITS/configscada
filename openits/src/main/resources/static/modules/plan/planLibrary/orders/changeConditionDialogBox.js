import { util } from "../../../../lib/util.js";
import SearchDialogBox from "../../../../component/searchDialogBox.js";
import InputDialogBox from "../../../../component/inputDialogBox.js";


const CHANGE_DIALOG_BOX_WIDTH = 700;
const CHANGE_DIALOG_BOX_HEIGHT = 500;
	
export default class ChangeConditionDialogBox {
	
	constructor(planOrderCondition, onAccept) {

		this.uid = webix.uid();
		this.planOrderCondition = planOrderCondition;
		this.onAccept = onAccept;
		this.containerId = undefined;

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
						{ height: 20 },
						getBodyComponent(),
						{ height: 20 },
						getToolbarActions(),
						{ height: 10 },			
					]
				},	
				{ width: 20 }
			]
		};
		
		self.containerId = util.ui.loadWindow(i18n.t("LBL_CHANGE_PARAM_DIALOG_BOX_TITLE"), body, CHANGE_DIALOG_BOX_HEIGHT, CHANGE_DIALOG_BOX_WIDTH, true);
		
		function getBodyComponent() {

			let bodyComponent = {
				rows: [
					getElemenRow(),
					getElementTypeParamRow(),
					getElementTypeStateRow(),
					getThresholdOperationRow(),
					getThresholdVal1Row(),
					getThresholdVal2Row(),
					getNexusAndRow()
				]
			};

			function getElemenRow() {

				let elementLabel = "<span class='grid-title'>  " + i18n.t("LBL_EQUIPMENT") + "</span>";

				if(self.element && self.element.id != null){

					elementLabel = "<img src='img/" + self.element.elementType.elementTypePhoto + "' class='grid-icon'/><span class='grid-title'>" + self.element.alias + "</span>"
				}

				let elementRow = {
					id: "change-condition-element-row-" + self.uid,
					cols: [
						{ width: 10 },
						{
							rows: [
								{
									cols: [
										{
											view: "label",
											label: elementLabel,
											autowidth: true
										},
										{ width: 5 },
										{
											view: "icon",
											icon: "mdi mdi-pencil",
											tooltip: i18n.t("LBL_EDIT_CHANGE_CONDITION_EQUIPMENT"),
											on: {
												onItemClick: function() {
													
													getWindowElementSelected();
												}
											}
										},
										{}
									]
								}
							]
						},
						{ width: 10 }
					]
					
				};

				return elementRow;
			}

			function getElementTypeParamRow() {

				let disabled = true;
				let elementTypeParamLabel = "<span class='grid-title'>  " + i18n.t("LBL_EQUIPMENT_TYPE_PARAM") + "</span>";

				if(self.element && self.element.id != null) {

					disabled = false;
				} else {

					disabled = true;
				}

				if(self.elementTypeParam && self.elementTypeParam.id != null){

					elementTypeParamLabel = "<span class='grid-title'>" + i18n.t(self.elementTypeParam.labelAlias) + "</span>"
				}

				let elementTypeParamRow = {
					id: "change-condition-element-type-param-row-" + self.uid,
					cols: [
						{ width: 10 },
						{
							rows: [
								{
									cols: [
										{
											view: "label",
											label: elementTypeParamLabel,
											autowidth: true
										},
										{ width: 5 },
										{
											view: "icon",
											icon: "mdi mdi-pencil",
											disabled: disabled,
											tooltip: i18n.t("LBL_EDIT_CHANGE_CONDITION_EQUIPMENT_TYPE_PARAM"),
											on: {
												onItemClick: function() {
													
													getWindowElementTypeParamSelected();
												}
											}
										},
										{}
									]
								}
							]
						},
						{ width: 10 }
					]
					
				};

				return elementTypeParamRow;
			}

			function getElementTypeStateRow() {

				let disabled = true;
				let elementTypeStateLabel = "<span class='grid-title'>  " + i18n.t("LBL_EQUIPMENT_TYPE_STATE") + "</span>";

				if(self.element && self.element.id != null) {

					disabled = false;
				} else {

					disabled = true;
				}

				if(self.elementTypeState && self.elementTypeState.id != null){

					elementTypeStateLabel = "<span class='grid-title'>" + i18n.t(self.elementTypeState.labelAlias) + "</span>"
				}

				let elementTypeStateRow = {
					id: "change-condition-element-type-state-row-" + self.uid,
					cols: [
						{ width: 10 },
						{
							rows: [
								{
									cols: [
										{
											view: "label",
											label: elementTypeStateLabel,
											autowidth: true
										},
										{ width: 5 },
										{
											view: "icon",
											icon: "mdi mdi-pencil",
											disabled: disabled,
											tooltip: i18n.t("LBL_EDIT_CHANGE_CONDITION_EQUIPMENT_STATE"),
											on: {
												onItemClick: function() {
													
													getWindowElementTypeStateSelected();
												}
											}
										},
										{}
									]
								}
							]
						},
						{ width: 10 }
					]
					
				};

				return elementTypeStateRow;
			}

			function getThresholdOperationRow() {

				let thresholdOperationLabel = "<span class='grid-title'>  " + i18n.t("LBL_TRESHOLD_OPERATION") + "</span>";

				if(self.element && self.element.id != null){

					thresholdOperationLabel = "<span class='grid-title'>" + i18n.t(self.thresholdOperation.labelAlias) + "</span>"
				}

				let thresholdOperationRow = {
					id: "change-condition-threshold-operation-row-" + self.uid,
					cols: [
						{ width: 10 },
						{
							rows: [
								{
									cols: [
										{
											view: "label",
											label: thresholdOperationLabel,
											autowidth: true
										},
										{ width: 5 },
										{
											view: "icon",
											icon: "mdi mdi-pencil",
											tooltip: i18n.t("LBL_EDIT_CHANGE_CONDITION_THRESHOLD_OPERATION"),
											on: {
												onItemClick: function() {
													
													getWindowThresholdOperationSelected();
												}
											}
										},
										{}
									]
								}
							]
						},
						{ width: 10 }
					]
					
				};

				return thresholdOperationRow;
			}

			function getThresholdVal1Row() {

				let thresholdVal1Label = "<span class='grid-title'>  " + i18n.t("LBL_THRESHOLD_VAL_ONE") + "</span>";

				if(self.thresholdVal1){

					thresholdVal1Label = "<span class='grid-title'>" + self.thresholdVal1 + "</span>"
				}

				let thresholdVal1Row = {
					id: "change-threshold-value-one-row-" + self.uid,
					cols: [
						{ width: 10 },
						{
							rows: [
								{
									cols: [
										{
											view: "label",
											label: thresholdVal1Label,
											autowidth: true
										},
										{ width: 5 },
										{
											view: "icon",
											icon: "mdi mdi-pencil",
											tooltip: i18n.t("LBL_EDIT_CHANGE_THRESHOLD_VALUE_ONE"),
											on: {
												onItemClick: function() {
													
													getWindowThresholdVal1Selected();
												}
											}
										},
										{}
									]
								}
							]
						},
						{ width: 10 }
					]
					
				};

				return thresholdVal1Row;
			}

			function getThresholdVal2Row() {

				let thresholdVal2Label = "<span class='grid-title'>  " + i18n.t("LBL_THRESHOLD_VAL_TWO") + "</span>";

				if(self.thresholdVal2){

					thresholdVal2Label = "<span class='grid-title'>" + self.thresholdVal2 + "</span>"
				}

				let thresholdVal2Row = {
					id: "change-threshold-value-two-row-" + self.uid,
					cols: [
						{ width: 10 },
						{
							rows: [
								{
									cols: [
										{
											view: "label",
											label: thresholdVal2Label,
											autowidth: true
										},
										{ width: 5 },
										{
											view: "icon",
											icon: "mdi mdi-pencil",
											tooltip: i18n.t("LBL_EDIT_CHANGE_THRESHOLD_VALUE_TWO"),
											on: {
												onItemClick: function() {
													
													getWindowThresholdVal2Selected();
												}
											}
										},
										{}
									]
								}
							]
						},
						{ width: 10 }
					]
					
				};

				return thresholdVal2Row;
			}

			function getNexusAndRow() {

				let nexusAndLabel = "<span class='grid-title'>  " + i18n.t("LBL_NEXUS_OPERATOR") + "</span>";

				if(self.nexusAnd){

					nexusAndLabel = "<span class='grid-title'>" + self.nexusAnd + "</span>"
				}

				let valueRow = {
					id: "change-nexus-operator-row-" + self.uid,
					cols: [
						{ width: 10 },
						{
							rows: [
								{
									cols: [
										{
											view: "label",
											label: nexusAndLabel,
											autowidth: true
										},
										{ width: 5 },
										{
											view: "icon",
											icon: "mdi mdi-pencil",
											tooltip: i18n.t("LBL_EDIT_CHANGE_NEXUS_OPERATOR"),
											on: {
												onItemClick: function() {
													
													getWindowNexusAndSelected();
												}
											}
										},
										{}
									]
								}
							]
						},
						{ width: 10 }
					]
					
				};

				return valueRow;
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
					
				let s = new SearchDialogBox(i18n.t("LBL_EQUIPMENT_SELECTED"), dialogEntity, onAccept);
	
				s.show();

				function onAccept(selectedIds) {

					let updateEquipmentTypeParamRow = false;

					if (selectedIds && Array.isArray(selectedIds) && selectedIds.length > 0){

						if(!self.element || self.element.id != selectedIds[0].id){

							updateEquipmentTypeParamRow = true;
						}

						self.element = window.em.getByField(EntityType.ELEMENT, "id", selectedIds[0].id);
					} else {

						self.element = null;
						updateEquipmentTypeParamRow = true;
					}
					
					webix.ui(getElemenRow(), $$("change-condition-element-row-" + self.uid));

					if(updateEquipmentTypeParamRow) {

						self.elementTypeParam = null;
						webix.ui(getElementTypeParamRow(), $$("change-condition-element-type-param-row-" + self.uid));
						
						self.planOrderValue = null;
						webix.ui(getNexusAndRow(), $$("change-param-value-row-" + self.uid));
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
					
				let s = new SearchDialogBox(i18n.t("LBL_EQUIPMENT_SELECTED"), dialogEntity, onAccept);
	
				s.show();

				function onAccept(selectedIds) {

					let updateValueRow = false;

					if (selectedIds && Array.isArray(selectedIds) && selectedIds.length > 0){

						if(!self.elementTypeParam || self.elementTypeParam.id != selectedIds[0].id){

							updateValueRow = true;
						}

						self.elementTypeParam = window.em.getByField(EntityType.ELEMENT_TYPE, "id", self.element.elementType.id).elementTypeParams.find(e => e.id == selectedIds[0].id);
					
					} else {
						
						self.elementTypeParam = null;
						updateValueRow = true;
					}
					
					webix.ui(getElementTypeParamRow(), $$("change-condition-element-type-param-row-" + self.uid));

					if(updateValueRow){

						self.planOrderValue = null;
						webix.ui(getNexusAndRow(), $$("change-param-value-row-" + self.uid));
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

				let elements = window.em.getAll(EntityType.ELEMENT_TYPE_STATE);
				
				if (Array.isArray(elements) && elements.length > 0) {
						
					elements.forEach(el => {

						dialogEntity.data.push({
							id: el.id,
							state: i18n.t(el.labelAlias)
						});						
					});
				}
					
				let s = new SearchDialogBox(i18n.t("LBL_ELEMENT_TYPE_STATE_SELECTED"), dialogEntity, onAccept);
	
				s.show();

				function onAccept(selectedIds) {

					if (selectedIds && Array.isArray(selectedIds) && selectedIds.length > 0){

						self.elementTypeState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", selectedIds[0].id);
					
					} else {
						
						self.elementTypeState = null;
					}
					
					webix.ui(getElementTypeStateRow(), $$("change-condition-element-type-state-row-" + self.uid));
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

				webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.thresoldOperations).then(function(dat){
				
					if (dat != null) {

						let elements = dat.json();
				
						if (Array.isArray(elements) && elements.length > 0) {
								
							elements.forEach(el => {

								dialogEntity.data.push({
									id: el.id,
									operation: i18n.t(el.labelAlias)
								});						
							});
						}
							
						let s = new SearchDialogBox(i18n.t("LBL_THRESHOLD_OPERATION_SELECTED"), dialogEntity, onAccept);
			
						s.show();

						function onAccept(selectedIds) {

							if (selectedIds && Array.isArray(selectedIds) && selectedIds.length > 0){

								self.thresholdOperation = selectedIds[0];
							
							} else {
								
								self.thresholdOperation = null;
							}
							
							webix.ui(getElementTypeStateRow(), $$("change-condition-threshold-operation-row-" + self.uid));
						}
					}
				});				
			}
			
			function getWindowThresholdVal1Selected() {
					
				let i = new InputDialogBox(i18n.t("LBL_CHANGE_THRESHOLD_VAL_1_SELECTED"), i18n.t("LBL_CHANGE_THRESHOLD_VAL_1_SELECTED_DESCRIPTION"), onAccept, false, self.thresholdVal1);
	
				i.show();

				function onAccept(inputValue) {

					self.thresholdVal1 = inputValue;

					webix.ui(getThresholdVal1Row(), $$("change-threshold-value-one-row-" + self.uid));
				}
			}

			function getWindowThresholdVal2Selected() {
					
				let i = new InputDialogBox(i18n.t("LBL_CHANGE_THRESHOLD_VAL_2_SELECTED"), i18n.t("LBL_CHANGE_THRESHOLD_VAL_2_SELECTED_DESCRIPTION"), onAccept, false, self.thresholdVal2);
	
				i.show();

				function onAccept(inputValue) {

					self.thresholdVal2 = inputValue;

					webix.ui(getThresholdVal2Row(), $$("change-threshold-value-two-row-" + self.uid));
				}
			}

			function getWindowNexusAndSelected() {
					
				let i = new InputDialogBox(i18n.t("LBL_CHANGE_PARAM_VALUE_SELECTED"), i18n.t("LBL_CHANGE_PARAM_VALUE_SELECTED_DESCRIPTION"), onAccept, false, self.planOrderValue);
	
				i.show();

				function onAccept(inputValue) {

					self.planOrderValue = inputValue;

					webix.ui(getNexusAndRow(), $$("change-param-value-row-" + self.uid));
				}
			}
			
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
							
							if(!self.planOrderCondition){

								self.planOrderCondition = {};
							}

							self.planOrderCondition.element = self.element;
							self.planOrderCondition.elementTypeParam = self.elementTypeParam;
							self.planOrderCondition.value = self.planOrderValue;

							self.onAccept(self.planOrderCondition);

							$$(self.containerId).close();
						}
					}
				});
			}
			
			return viewToolbarActions;	
		}
	}
}