import { util } from "../../../../lib/util.js";
import SearchDialogBox from "../../../../component/searchDialogBox.js";
import MessageBox from "../../../../component/messageBox.js";

const CHANGE_DIALOG_BOX_WIDTH = 700;
const CHANGE_DIALOG_BOX_HEIGHT = 540;
	
export default class ChangeStatusDialogBox {
	
	constructor(planOrderElementTypeState, onAccept) {

		this.uid = webix.uid();
		this.planOrderElementTypeState = planOrderElementTypeState;
		this.onAccept = onAccept;
		this.containerId = undefined;

		this.element = undefined;
		this.elementTypeState = undefined;
	}

	show() {

		let self = this;
		
		if(self.planOrderElementTypeState) {

			if(self.planOrderElementTypeState.element) {

				self.element = self.planOrderElementTypeState.element;
			}

			if(self.planOrderElementTypeState.elementTypeState) {

				self.elementTypeState = self.planOrderElementTypeState.elementTypeState;
			}
		}
		
		let body = {
			id: "change-status-search-dialog-" + this.uid,
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
		
		self.containerId = util.ui.loadWindow(i18n.t("LBL_PLAN_ORDER_TYPE_CHANGE_STATUS"), body, CHANGE_DIALOG_BOX_HEIGHT, CHANGE_DIALOG_BOX_WIDTH, true);
		
		function getBodyComponent() {

			let bodyComponent = {
				view: "form",
				elementsConfig: { labelAlign: "left" },
				css: "global-form",
				elements: [
					getEquipmentRow(),
					getElementTypeStateRow()
				]
			};

			function getEquipmentRow() {

				let equipmentRow;

				equipmentRow = {
					id: "change-status-equipment-row-" + self.uid,
					cols: [
						{ view: "text", name: "equipmentName", value: (self.element?self.element.alias:""), required: true, label: i18n.t("LBL_EQUIPMENT"), disabled: true, labelWidth: 275 },
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

			function getElementTypeStateRow() {

				let elementTypeStateRow;
				let equipmentTypeDisable = true;

				if(self.element && self.element.id != null){

					equipmentTypeDisable = false;
				}

				elementTypeStateRow = {
					id: "change-status-element-type-state-row-" + self.uid,
					cols: [
						{ view: "text", name: "equipmentTypeName", value: (self.elementTypeState?i18n.t(self.elementTypeState.labelAlias):""), required: true, label: i18n.t("LBL_STATE_LABEL"), disabled: true, labelWidth: 275 },
						{
							view: "icon", icon: "wxi-search", disabled: equipmentTypeDisable,
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

					let updateElementTypeStateRow = false;

					if (selectedIds && Array.isArray(selectedIds) && selectedIds.length > 0){

						if(!self.element || self.element.id != selectedIds[0].id){

							updateElementTypeStateRow = true;
						}

						self.element = window.em.getByField(EntityType.ELEMENT, "id", selectedIds[0].id);
					} else {

						self.element = null;
						updateElementTypeStateRow = true;
					}
					
					webix.ui(getEquipmentRow(), $$("change-status-equipment-row-" + self.uid));

					if(updateElementTypeStateRow) {

						self.elementTypeState = null;
						webix.ui(getElementTypeStateRow(), $$("change-status-element-type-state-row-" + self.uid));
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
					
				let s = new SearchDialogBox(i18n.t("LBL_ELEMENT_TYPE_STATE_SELECTED"), dialogEntity, onAccept);
	
				s.show();

				function onAccept(selectedIds) {

					if (selectedIds && Array.isArray(selectedIds) && selectedIds.length > 0){

						self.elementTypeState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", selectedIds[0].id);
					
					} else {
						
						self.elementTypeState = null;
					}
					
					webix.ui(getElementTypeStateRow(), $$("change-status-element-type-state-row-" + self.uid));
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

								if(!self.planOrderElementTypeState){

									self.planOrderElementTypeState = {};
								}
	
								self.planOrderElementTypeState.element = self.element;
								self.planOrderElementTypeState.elementTypeState = self.elementTypeState;
	
								self.onAccept(self.planOrderElementTypeState);
	
								$$(self.containerId).close();
							
							} else{

								let ms = new MessageBox (MessageBox.WARN, i18n.t("LBL_PLAN_LIBRARY_REQUIRED_PARAMS"), null, null, null);
								ms.show();

							}
						}
					}
				});
			}

			function isValidForm(){

				let result = true;

				if(!self.element || !self.elementTypeState){

					result = false;
				}

				return result;
			}
			
			return viewToolbarActions;	
		}
	}
}