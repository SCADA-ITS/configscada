import { util } from "../../../../lib/util.js";
import SearchDialogBox from "../../../../component/searchDialogBox.js";
import InputDialogBox from "../../../../component/inputDialogBox.js";
import MessageBox from "../../../../component/messageBox.js";

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
						{ height: 20 },			
					]
				},	
				{ width: 20 }
			]
		};
		
		self.containerId = util.ui.loadWindow(i18n.t("LBL_PLAN_ORDER_TYPE_CHANGE_ELEMENT_PARAM_CONFIG"), body, CHANGE_DIALOG_BOX_HEIGHT, CHANGE_DIALOG_BOX_WIDTH, true);
		
		function getBodyComponent() {

			let bodyComponent = {
				view: "form",
				elementsConfig: { labelAlign: "left" },
				css: "global-form",
				rows: [
					getEquipmentRow(),
					getEquipmentTypeParamRow(),
					getValueRow()
				]
			};

			function getEquipmentRow() {

				let equipmentRow;

				equipmentRow = {
					id: "change-param-equipment-row-" + self.uid,
					cols: [
						{ view: "text", value: (self.element?self.element.alias:""), required: true, label: i18n.t("LBL_EQUIPMENT"), disabled: true, labelWidth: 275 },
						{
							view: "icon", icon: "wxi-search", disabled: false,
							on: {
								onItemClick: function() {

									getWindowEquipmentSelected();
								}
							}
						},
						{ width: 90 }
					]
				};

				return equipmentRow;
			}

			function getEquipmentTypeParamRow() {

				let elementTypeParamRow;

				let disabled = true;				
				if(self.element && self.element.id != null) {

					disabled = false;
				} 

				elementTypeParamRow = {
					id: "change-param-equipment-type-param-row-" + self.uid,
					cols: [
						{ view: "text", value: (self.elementTypeParam?i18n.t(self.elementTypeParam.labelAlias):""), required: true, label: i18n.t("LBL_PARAM"), disabled: true, labelWidth: 275 },
						{
							view: "icon", icon: "wxi-search", disabled: disabled,
							on: {
								onItemClick: function() {

									getWindowEquipmentTypeParamSelected();
								}
							}
						},
						{ width: 90 }
					]
				};

				return elementTypeParamRow;
			}

			function getValueRow() {
				
				let valueRow;

				let disabled = true;				
				if(self.element && self.element.id != null
					&& self.elementTypeParam && self.elementTypeParam.id != null) {

					disabled = false;
				} 

				valueRow = {
					id: "change-param-value-row-" + self.uid,
					cols: [
						{ view: "text", value: self.planOrderValue, required: true, label: i18n.t("LBL_VALUE"), disabled: true, labelWidth: 275 },
						{
							view: "icon", icon: "mdi mdi-pencil", disabled: disabled,
							on: {
								onItemClick: function() {

									getWindowValueSelected();
								}
							}
						},
						{ width: 90 }
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
					
				let s = new SearchDialogBox(i18n.t("LBL_SELECT_EQUIPMENT"), dialogEntity, onAccept);
	
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
					
				let s = new SearchDialogBox(i18n.t("LBL_SELECT_PARAM"), dialogEntity, onAccept);
	
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
					
				let i = new InputDialogBox(i18n.t("LBL_VALUE"), i18n.t("LBL_ENTER_VALUE"), onAccept, true, self.planOrderValue, self.elementTypeParam.dataType);
	
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
							
							if(isValidForm()){

								if(!self.planOrderElement){

									self.planOrderElement = {};
								}
	
								self.planOrderElement.element = self.element;
								self.planOrderElement.elementTypeParam = self.elementTypeParam;
								self.planOrderElement.value = self.planOrderValue;
	
								self.onAccept(self.planOrderElement);
	
								$$(self.containerId).close();
							
							} else {
								
								let ms = new MessageBox (MessageBox.WARN, i18n.t("LBL_PLAN_LIBRARY_REQUIRED_PARAMS"), null, null, null);
								ms.show();

							}
						}
					}
				});
			}

			function isValidForm(){

				let result = true;

				if(!self.element || !self.elementTypeParam || !self.planOrderValue){

					result = false;
				}

				return result;
			}
			
			return viewToolbarActions;	
		}
	}
}