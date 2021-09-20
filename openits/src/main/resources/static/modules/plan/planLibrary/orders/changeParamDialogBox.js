import { util } from "../../../../lib/util.js";
import SearchDialogBox from "../../../../component/searchDialogBox.js";
import InputDialogBox from "../../../../component/inputDialogBox.js";


const CHANGE_DIALOG_BOX_WIDTH = 700;
const CHANGE_DIALOG_BOX_HEIGHT = 500;
	
export default class ChangeParamDialogBox {
	
	constructor(planOrderElement, onAccept) {

		this.uid = webix.uid();
		this.planOrderElement = planOrderElement;
		this.onAccept = onAccept;
		this.containerId = undefined;

		this.element = undefined;
		this.elementTypeParam = undefined;
		this.planOrderValue = undefined;
	}

	show() {

		let self = this;
		
		if(self.planOrderElement) {

			if(self.planOrderElement.element) {

				self.element = self.planOrderElement.element;
			}

			if(self.planOrderElement.elementTypeParam) {

				self.elementTypeParam = self.planOrderElement.elementTypeParam;
			}

			if(self.planOrderElement.value) {

				self.planOrderValue = self.planOrderElement.value;
			}
		}
		
		let body = {
			id: "change-param-search-dialog-" + this.uid,
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
					// { height: 20 },
					getEquipmentRow(),
					// { height: 20 },
					getEquipmentTypeParamRow(),
					getValueRow()
				]
			};

			function getEquipmentRow() {

				let equipmentLabel = "<span class='grid-title'>  " + i18n.t("LBL_EQUIPMENT") + "</span>";

				if(self.element && self.element.id != null){

					equipmentLabel = "<img src='img/" + self.element.elementType.elementTypePhoto + "' class='grid-icon'/><span class='grid-title'>" + self.element.alias + "</span>"
				}

				let equipmentRow = {
					id: "change-param-equipment-row-" + self.uid,
					cols: [
						{ width: 10 },
						{
							rows: [
								{
									cols: [
										{
											view: "label",
											label: equipmentLabel,
											autowidth: true
										},
										{ width: 5 },
										{
											view: "icon",
											icon: "mdi mdi-pencil",
											tooltip: i18n.t("LBL_EDIT_CHANGE_PARAM_EQUIPMENT"),
											on: {
												onItemClick: function() {
													
													getWindowEquipmentSelected();
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

				return equipmentRow;
			}

			function getEquipmentTypeParamRow() {

				let disabled = true;
				let equipmentTypeParamLabel = "<span class='grid-title'>  " + i18n.t("LBL_EQUIPMENT_TYPE_PARAM") + "</span>";

				if(self.element && self.element.id != null) {

					disabled = false;
				} else {

					disabled = true;
				}

				if(self.elementTypeParam && self.elementTypeParam.id != null){

					equipmentTypeParamLabel = "<span class='grid-title'>" + i18n.t(self.elementTypeParam.labelAlias) + "</span>"
				}

				let equipmentTypeParamRow = {
					id: "change-param-equipment-type-param-row-" + self.uid,
					cols: [
						{ width: 10 },
						{
							rows: [
								{
									cols: [
										{
											view: "label",
											label: equipmentTypeParamLabel,
											autowidth: true
										},
										{ width: 5 },
										{
											view: "icon",
											icon: "mdi mdi-pencil",
											disabled: disabled,
											tooltip: i18n.t("LBL_EDIT_CHANGE_PARAM_EQUIPMENT_TYPE_PARAM"),
											on: {
												onItemClick: function() {
													
													getWindowEquipmentTypeParamSelected();
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

				return equipmentTypeParamRow;
			}

			function getValueRow() {

				let disabled = true;
				let planOrderValueLabel = "<span class='grid-title'>  " + i18n.t("LBL_VALUE") + "</span>";

				if(self.element && self.element.id != null
					&& self.elementTypeParam && self.elementTypeParam.id != null) {

					disabled = false;
				} else {

					disabled = true;
				}

				if(self.planOrderValue){

					planOrderValueLabel = "<span class='grid-title'>" + self.planOrderValue + "</span>"
				}

				let valueRow = {
					id: "change-param-value-row-" + self.uid,
					cols: [
						{ width: 10 },
						{
							rows: [
								{
									cols: [
										{
											view: "label",
											label: planOrderValueLabel,
											autowidth: true
										},
										{ width: 5 },
										{
											view: "icon",
											icon: "mdi mdi-pencil",
											disabled: disabled,
											tooltip: i18n.t("LBL_EDIT_CHANGE_PARAM_VALUE"),
											on: {
												onItemClick: function() {
													
													getWindowValueSelected();
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

			function getWindowEquipmentSelected() {
	
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
					
					webix.ui(getEquipmentRow(), $$("change-param-equipment-row-" + self.uid));

					if(updateEquipmentTypeParamRow) {

						self.elementTypeParam = null;
						webix.ui(getEquipmentTypeParamRow(), $$("change-param-equipment-type-param-row-" + self.uid));
						
						self.planOrderValue = null;
						webix.ui(getValueRow(), $$("change-param-value-row-" + self.uid));
					}
				}
			}

			function getWindowEquipmentTypeParamSelected() {
	
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
					
					webix.ui(getEquipmentTypeParamRow(), $$("change-param-equipment-type-param-row-" + self.uid));

					if(updateValueRow){

						self.planOrderValue = null;
						webix.ui(getValueRow(), $$("change-param-value-row-" + self.uid));
					}
				}
			}

			function getWindowValueSelected() {
					
				let i = new InputDialogBox(i18n.t("LBL_CHANGE_PARAM_VALUE_SELECTED"), i18n.t("LBL_CHANGE_PARAM_VALUE_SELECTED_DESCRIPTION"), onAccept, true, self.planOrderValue, self.elementTypeParam.dataType.id);
	
				i.show();

				function onAccept(inputValue) {

					self.planOrderValue = inputValue;

					webix.ui(getValueRow(), $$("change-param-value-row-" + self.uid));
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
							
							if(!self.planOrderElement){

								self.planOrderElement = {};
							}

							self.planOrderElement.element = self.element;
							self.planOrderElement.elementTypeParam = self.elementTypeParam;
							self.planOrderElement.value = self.planOrderValue;

							self.onAccept(self.planOrderElement);

							$$(self.containerId).close();
						}
					}
				});
			}
			
			return viewToolbarActions;	
		}
	}
}