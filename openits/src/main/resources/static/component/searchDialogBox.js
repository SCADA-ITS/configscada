import DataTable from "../component/datatable.js";
import TreeView from "../component/treeview.js";
import { util } from "../lib/util.js";

const DIALOG_DATA_TYPE_TABLE = 1;
const DIALOG_DATA_TYPE_TREE = 2;

const DIALOG_ENDPOINT_TYPE_REST = 1;
const DIALOG_ENDPOINT_TYPE_EM = 2;
const DIALOG_ENDPOINT_TYPE_CUSTOM = 3;
const DIALOG_ENDPOINT_TYPE_CALLBACK = 4;
	
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
		
		let acceptCallback = function() {
			
			try {
				
				let selected = undefined;
				
				if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TABLE) {
						
					selected = self.searchDataTable.getSelectedItems();
				}
				else if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TREE) {
					
					selected = self.searchTreeVuew.getSelected();
				}
				
				let res = undefined;
				
				if (self.onAccept && Array.isArray(selected) && selected.length > 0) { 
					
					res = self.onAccept(selected);
				
					if (res === undefined || res === true) {
						
						$$(self.containerId).close();	
					}
				}

			}
			catch {}
			
		};
		
		function getDialogInfo(onGetDialogInfo) {
		
			if (self.dialogEntity.endpointType === DIALOG_ENDPOINT_TYPE_CALLBACK) {
				
				self.dialogEntity.fillDialogEntity(self.dialogEntity, onFilledDialogEntity);
			}
			else {
				
				onFilledDialogEntity(self.dialogEntity);
			}
			
			function onFilledDialogEntity(dialogEntity) {
				
				let data = undefined;
				let metadata = undefined;
				
				self.dialogEntity = dialogEntity;
				
				data = getData();
				metadata = getMetadata();
			
				onGetDialogInfo({ type: self.dialogEntity.dataType, data: data, metadata: metadata });
			}
			
			function getFieldName(field) {
					
				if (field) {
				
					let offset = field.indexOf("=");
					
					if (offset !== -1) {
						
						return field.substring(0, offset);
					}	
				}
				
				return field;
			};
			
			function getFieldLabel(field) {
				
				if (field) {
				
					let offset = field.indexOf("=");
					
					if (offset !== -1) {
						
						return field.substring(offset + 1);
					}	
				}
			};
			
			function getData() {
				
				let dat = [];
				
				function getDataTable() {
					
					let dataTable;
				
					if (self.dialogEntity.endpointType === DIALOG_ENDPOINT_TYPE_EM) {
						
						dataTable = window.em.getAll(EntityType[self.dialogEntity.endpoint]);
					}
					else {
						
						dataTable = self.dialogEntity.data;
					}
					
					if (Array.isArray(dataTable) && dataTable.length > 0) {
							
						return dataTable.map(function(e) {
							
							let output = {};
							
							output.id = e[self.dialogEntity.pkField];
							
							self.dialogEntity.fields.forEach(field => {
								
								let fieldName = getFieldName(field); 
								
								output[fieldName] = e[fieldName];
							});
	
							return output;
						});
					}	
				}
				
				function getDataTree() {
					
					let dataTree = [];
										
					if (self.dialogEntity.endpointType === DIALOG_ENDPOINT_TYPE_CUSTOM ||
					    self.dialogEntity.endpointType === DIALOG_ENDPOINT_TYPE_CALLBACK) {
						
						
						if (self.dialogEntity.treeData) {
						
							dataTree = util.tree.getNodes(
													self.dialogEntity.treeData.nodes,
													self.dialogEntity.treeData.relationships, 
													{ 
														parentNodeId: self.dialogEntity.treeData.nodeParentId, 
														childNodeId: self.dialogEntity.treeData.nodeChildId 
													}, 
													false);	
						}
							
					}
					
					return dataTree;
				}
				
				if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TABLE) {
					
					dat = getDataTable();
				}
				else if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TREE) {
				
					dat = getDataTree();
				}
	
				return dat;
			};
			
			function getMetadata() {
				
				let meta = {};
				
				if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TABLE) {
					
					const gridOptionValues = [
						{ gridOption: { id: 1 }, value: "false" },
						{ gridOption: { id: 2 }, value: "true" },
						{ gridOption: { id: 3 }, value: self.dialogEntity.sortFieldId ? String(self.dialogEntity.sortFieldId) : undefined },
						{ gridOption: { id: 4 }, value: "asc" },
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
					
					meta = {
						filter: true,
						label: "",
						visible: true,
						gridOptionValues: gridOptionValues,
						gridFields: []
					};
					
					let position = 1;
					
					meta.gridFields.push({
						id: position,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: true,
						needsTranslation: false,
						parameterized: false,
						position: position,
						required: true,
						sourceField: self.dialogEntity.pkField,
						visible: false,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					});
					
					self.dialogEntity.fields.forEach(field => {
								
						let fieldName = getFieldName(field);
						let fieldLabel = getFieldLabel(field);
						let needsTranslation = false;
						
						if (Array.isArray(self.dialogEntity.translate)) {
							
							needsTranslation = self.dialogEntity.translate.includes(fieldName);
						}
						
						let adjust = (position + 1 >= self.dialogEntity.fields.length);
						
						position++;
						
						meta.gridFields.push({
							id: position,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: fieldLabel,
							needsTranslation: needsTranslation,
							parameterized: false,
							position: position,
							required: true,
							adjust: adjust,
							sourceField: fieldName,
							visible: true,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: function() {
								
								let gridFieldIcons = [];
								
								if (self.dialogEntity.icons && Array.isArray(self.dialogEntity.icons[fieldName])) {
								
									let icons = self.dialogEntity.icons[fieldName];
									
									gridFieldIcons = icons.map(e => {
									
										return {
											
											icon: e.icon,
											showValue: false,
											value: e.id
										}	
									});
								}
								
								return gridFieldIcons;
							}()
						});
					});
				}
				else if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TREE) {
					
					meta = {
							
						id: -1,
						label: "",
						treeOptionValues: [
							{ value: true, treeOption: { id: 6 } }
						]
					}
				}
				
				return meta;
			};
		};
		
		let body = {
			id: "search-dialog-" + this.uid,
			cols: [util.ui.getViewDrawLoading()]
		}
		
		self.containerId = util.ui.loadWindow(self.title, body, self.dialogEntity.height, self.dialogEntity.width, true);

		if (self.dialogEntity.endpointType === DIALOG_ENDPOINT_TYPE_REST) {
				
			webix.ajax().get(appConfig.restUrl + self.dialogEntity.endpoint).then(
				
				function(response) {
					
					if (response) {
						
						self.dialogEntity.data = response.json();
						
						doActions();
					}
				}
			);
		}
		else {
			
			doActions();
		}
		
		function doActions() {
			
			getSearchComponent(onGetSearchComponent);
			
			function onGetSearchComponent(viewSearchComponent) {
				
				body = {
					id: "search-dialog-" + self.uid,
					cols: [
						{ width: 20 },
						{
							rows: [
								{ height: 20 },
								viewSearchComponent,
								{ height: 20 },
								getToolbarActions(),
								{ height: 10 },			
							]
						},	
						{ width: 20 },
					]
				};
				
				webix.ui(body, $$("search-dialog-" + self.uid));
				
				// Post draw views actions
				if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TABLE && self.searchDataTable) {
				
					self.searchDataTable.callbacks();
				}	
			}
		}
		
		function getSearchComponent(onSearchComponent) {
			
			getDialogInfo(onGetDialogInfo);
			
			function onGetDialogInfo(dialogInfo) {
				
				let v;
				
				self.dialogInfo = dialogInfo;
				
				if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TABLE) {
			
					v = {
						
						cols: [
							{
								rows: getDataTable()
							}
						]  
					};
				}	
				else if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TREE) {
				
					v = getTreeView();
				}
				
				if (v) {
					
					onSearchComponent(v);
				}
				
				function getDataTable() {
					
					let events = {};
					
					events[DataTable.EventTypes.ON_ITEM_DBL_CLIC] = acceptCallback;
					
					self.searchDataTable = new DataTable(null, null, self.dialogInfo.metadata, self.dialogInfo.data, events);
					
					return self.searchDataTable.getView();
				}
				
				function getTreeView() {
					
					let events = {};
					
					self.searchTreeView = new TreeView(self.dialogInfo.metadata, self.dialogInfo.data, events);
					
					return self.searchTreeView.getView();
				}
			}
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
							
							if (self.dialogEntity.dataType === DIALOG_DATA_TYPE_TABLE && self.searchDataTable) {
							
								acceptCallback();	
							}
						}
					}
				});
			}
			
			return viewToolbarActions;	
		}
	}
}

Object.defineProperty(SearchDialogBox, 'USERS', {
	value: { id: 1, dataType: DIALOG_DATA_TYPE_TABLE, endpointType: DIALOG_ENDPOINT_TYPE_EM, endpoint: "USER", pkField: "id", fields: ["name=LBL_USER", "fullName=LBL_FULL_NAME"], height: 600, width: 800 },
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(SearchDialogBox, 'LOCALES', {
	value: { id: 2, dataType: DIALOG_DATA_TYPE_TABLE, endpointType: DIALOG_ENDPOINT_TYPE_REST, endpoint: "i18n/locales", pkField: "id", fields: ["code=LBL_LOCALE_CODE", "name=LBL_LOCALE_NAME"], height: 600, width: 800 },
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(SearchDialogBox, 'ROLES', {
	value: { id: 3, dataType: DIALOG_DATA_TYPE_TABLE, endpointType: DIALOG_ENDPOINT_TYPE_REST, endpoint: "users/roles", pkField: "id", fields: ["alias=LBL_ROLE", "description=LBL_ROLE_DESCRIPTION"], height: 600, width: 800 },
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(SearchDialogBox, 'PERMISSIONS', {
	value: {id: 4, dataType: DIALOG_DATA_TYPE_TABLE, endpointType: DIALOG_ENDPOINT_TYPE_REST, endpoint: "users/systemPermissions", pkField: "id", fields: ["labelAlias=LBL_LABEL_PERMISSION", "labelDescription=LBL_LABEL_PERMISSION_DESCRIPTION"], translate: ["labelAlias", "labelDescription"], height: 600, width: 800},
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(SearchDialogBox, 'PLANS', {
	value: { id: 5, dataType: DIALOG_DATA_TYPE_TABLE, endpointType: DIALOG_ENDPOINT_TYPE_REST, endpoint: "plans/summary", pkField: "id", fields: ["alias=LBL_PLAN", "description=LBL_PLAN_DESCRIPTION"], height: 600, width: 800 },
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(SearchDialogBox, 'CUSTOM', {
	value: { id: 6, dataType: DIALOG_DATA_TYPE_TABLE, endpointType: DIALOG_ENDPOINT_TYPE_CUSTOM },
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(SearchDialogBox, 'CALLBACK', {
	value: { id: 7, dataType: DIALOG_DATA_TYPE_TABLE, endpointType: DIALOG_ENDPOINT_TYPE_CALLBACK },
	writable: false,
	enumerable: true,
	configurable: false
});
