import { util } from "../../../../lib/util.js";
import SearchDialogBox from "../../../../component/searchDialogBox.js";
import InputDialogBox from "../../../../component/inputDialogBox.js";


const CHANGE_DIALOG_BOX_WIDTH = 700;
const CHANGE_DIALOG_BOX_HEIGHT = 500;
	
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
						{ height: 10 },			
					]
				},	
				{ width: 20 }
			]
		};
		
		self.containerId = util.ui.loadWindow(i18n.t("LBL_CHANGE_STATUS_DIALOG_BOX_TITLE"), body, CHANGE_DIALOG_BOX_HEIGHT, CHANGE_DIALOG_BOX_WIDTH, true);
		
		function getBodyComponent() {

			let bodyComponent = {
				rows: [
					getEquipmentRow(),
					getElementTypeStateRow()
				]
			};

			function getEquipmentRow() {

				let equipmentLabel = "<span class='grid-title'>  " + i18n.t("LBL_EQUIPMENT") + "</span>";

				if(self.element && self.element.id != null){

					equipmentLabel = "<img src='img/" + self.element.elementType.elementTypePhoto + "' class='grid-icon'/><span class='grid-title'>" + self.element.alias + "</span>"
				}

				let equipmentRow = {
					id: "change-status-equipment-row-" + self.uid,
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
											tooltip: i18n.t("LBL_EDIT_CHANGE_STATUS_EQUIPMENT"),
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

			function getElementTypeStateRow() {

				let disabled = true;
				let equipmentTypeStateLabel = "<span class='grid-title'>  " + i18n.t("LBL_EQUIPMENT_TYPE") + "</span>";

				if(self.element && self.element.id != null) {

					disabled = false;
				} else {

					disabled = true;
				}

				if(self.elementTypeState && self.elementTypeState.id != null){

					equipmentTypeStateLabel = "<span class='grid-title'>" + i18n.t(self.elementTypeState.labelAlias) + "</span>"
				}

				let elementTypeStateRow = {
					id: "change-status-element-type-state-row-" + self.uid,
					cols: [
						{ width: 10 },
						{
							rows: [
								{
									cols: [
										{
											view: "label",
											label: equipmentTypeStateLabel,
											autowidth: true
										},
										{ width: 5 },
										{
											view: "icon",
											icon: "mdi mdi-pencil",
											disabled: disabled,
											tooltip: i18n.t("LBL_EDIT_CHANGE_STATUS_ELEMENT_TYPE_STATE"),
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
							
							if(!self.planOrderElementTypeState){

								self.planOrderElementTypeState = {};
							}

							self.planOrderElementTypeState.element = self.element;
							self.planOrderElementTypeState.elementTypeState = self.elementTypeState;
							self.planOrderElementTypeState.value = self.planOrderValue;

							self.onAccept(self.planOrderElementTypeState);

							$$(self.containerId).close();
						}
					}
				});
			}
			
			return viewToolbarActions;	
		}
	}
}