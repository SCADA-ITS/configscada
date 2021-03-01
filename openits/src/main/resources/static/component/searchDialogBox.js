//import { EventTypes } from "../component/datatable.js";
import DataTable from "../component/datatable.js";
//import DialogEntitiesRepository from "../managers/dialogEntitiesRepository.js";
import { util } from "../lib/util.js";

const DIALOG_DATA_TYPE_TABLE = 1;
const DIALOG_DATA_TYPE_TREE = 2;

const DIALOG_ENDPOINT_TYPE_REST = 1;
const DIALOG_ENDPOINT_TYPE_EM = 2;
	
export default class SearchDialogBox {
	
	constructor(title, dialogEntity, onAccept) {

		this.uid = webix.uid();
		this.title = title;
		this.dialogEntity = dialogEntity;
		this.onAccept = onAccept;
		this.dialogInfo = undefined;
		this.searchDataTable = undefined;
		this.searchTreeView = undefined;
		this.containerId = undefined;
	}

	show() {

		let self = this;
		
		let acceptCallback = function(selectedIds) {
			
			try {
			
				if (self.onAccept && Array.isArray(selectedIds) && selectedIds.length > 0) {
									
					self.onAccept(selectedIds);
					
					$$(self.containerId).close();
				}
				else {
					
					webix.alert({
						title: i18n.t("LBL_ROW_NOT_SELECTED"),
						text: i18n.t("LBL_ROW_NOT_SELECTED_DESCRIPTION"),
						type: "alert-error",
						width: 500
					});
				}
			}
			catch {}
			
		};
		
		let getDialogInfo = function(dialogEntity) {
		
			let data = undefined;
			let metadata = undefined;
			
			let getFieldName = function(field) {
					
				if (field) {
				
					let offset = field.indexOf("=");
					
					if (offset !== -1) {
						
						return field.substring(0, offset);
					}	
				}
				
				return field;
			};
			
			let getFieldLabel = function(field) {
				
				if (field) {
				
					let offset = field.indexOf("=");
					
					if (offset !== -1) {
						
						return field.substring(offset + 1);
					}	
				}
			};
			
			let getData = function() {
				
				let getFromEntitiesManager = function(em_type) {
	
					return window.em.getAll(em_type);
				};
	
				if (dialogEntity.endpointType === DIALOG_ENDPOINT_TYPE_EM) {
						
					let entities = getFromEntitiesManager(EntityType[dialogEntity.endpoint]);
					
					if (Array.isArray(entities) && entities.length > 0) {
						
						return entities.map(function(e) {
							
							let output = {};
							
							output.id = e[dialogEntity.pkField];
							
							dialogEntity.fields.forEach(field => {
								
								let fieldName = getFieldName(field); 
								
								output[fieldName] = e[fieldName];
							});
							
							return output;
						});
					}
				}
				else if (dialogEntity.endpointType === DIALOG_ENDPOINT_TYPE_REST) {
					
					// TODO
				}
			};
			
			let getMetadata = function() {
				
				let gridMetadata = {};
				
				if (dialogEntity.dataType === DIALOG_DATA_TYPE_TABLE) {
					
					const gridOptionValues = [
						{ gridOption: { id: 1 }, value: "false" },
						{ gridOption: { id: 2 }, value: "true" },
						{ gridOption: { id: 7 }, value: "false" },
						{ gridOption: { id: 8 }, value: "false" },
						{ gridOption: { id: 9 }, value: "true" },
						{ gridOption: { id: 10 }, value: "true" },
						{ gridOption: { id: 11 }, value: "false" },
						{ gridOption: { id: 12 }, value: "false" },
						{ gridOption: { id: 13 }, value: "true" },
						{ gridOption: { id: 14 }, value: "false" },
						{ gridOption: { id: 15 }, value: "false" },
						{ gridOption: { id: 16 }, value: "img/grid/default.png" },
						{ gridOption: { id: 21 }, value: "auto" }
					];
					
					gridMetadata = {
						filter: true,
						label: "LBL_LOGS",
						visible: true,
						gridOptionValues: gridOptionValues,
						gridFields: []
					};
					
					let position = 0;
					
					gridMetadata.gridFields.push({
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: true,
						needsTranslation: false,
						parameterized: false,
						position: ++position,
						required: true,
						sourceField: dialogEntity.pkField,
						visible: false,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					});
					
					dialogEntity.fields.forEach(field => {
								
						let fieldName = getFieldName(field);
						let fieldLabel = getFieldLabel(field);
						
						let adjust = (position + 1 >= dialogEntity.fields.length);
						
						gridMetadata.gridFields.push({
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: fieldLabel,
							needsTranslation: false,
							parameterized: false,
							position: ++position,
							required: true,
							adjust: adjust,
							sourceField: fieldName,
							visible: true,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						});
					});
				}
				
				return gridMetadata;
			};
			
			data = getData();
			metadata = getMetadata();
			
			return { type: dialogEntity.dataType, data: data, metadata: metadata };
		};

		let body = {
			id: "search-dialog-" + this.uid,
			cols: [
				{ width: 20 },
				{
					rows: [
						{ height: 20 },
						getSearchComponent(),
						{ height: 20 },
						getToolbarActions(),
						{ height: 10 },			
					]
				},	
				{ width: 20 },
			]
		};
		
		self.containerId = util.ui.loadWindow(self.title, body, self.dialogEntity.height, self.dialogEntity.width, true);
		
		// Post draw views actions
		if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TABLE && self.searchDataTable) {
		
			self.searchDataTable.callbacks();
		}
		
		function getSearchComponent() {
			
			self.dialogInfo = getDialogInfo(self.dialogEntity);
			
			if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TABLE) {
			
				return {
					
					cols: [
						{
							rows: getDataTable()
						}
					]  
				};
			}	
			else if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TREE) {
			
				return getTreeView();
			}
			
			function getDataTable() {
				
				let events = {};

				events[DataTable.EventTypes.ON_ITEM_DBL_CLIC] = acceptCallback;
				
				self.searchDataTable = new DataTable(null, null, self.dialogInfo.metadata, self.dialogInfo.data, events);
				
				return self.searchDataTable.getView();
			}
			
			function getTreeView() {
				
				return {};
			}
		}
		
		function getToolbarActions() {
		
			let viewToolbarActions = {};
			
			viewToolbarActions = {
				view: "toolbar",
				height: 50,
				borderless: true,
				elements: [
					{ 
						view: "button",
						css: "dialog-cancel", 
						width: 150, 
						label: i18n.t("LBL_CANCEL"),
						on: {
							onItemClick() {
							
								$$(self.containerId).close();
							}
						}
					},
					{},
					{ 
						view: "button", 
						css: "dialog-accept",
						width: 150, 
						label: i18n.t("LBL_ACCEPT"),
						on: {
							onItemClick() {
								
								if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TABLE && self.searchDataTable) {
								
									acceptCallback(self.searchDataTable.getSelectedIds());	
								}
							}
						}
					}
				]
			};
			
			return viewToolbarActions;	
		}
	}
}

Object.defineProperty(SearchDialogBox, 'USERS', {
    value: { id: 1, dataType: DIALOG_DATA_TYPE_TABLE, endpointType: DIALOG_ENDPOINT_TYPE_EM, endpoint: "USER", pkField: "id", fields: ["name=LBL_USER", "fullName=LBL_FULL_NAME"], height: 600, width: 800 },
    writable : false,
    enumerable : true,
    configurable : false
});