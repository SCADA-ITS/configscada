import DataTable from "../component/datatable.js";
import TreeView from "../component/treeview.js";
import MessageBox from "../component/messageBox.js";
import { util } from "../lib/util.js";

export default class SearchDialogBox {
	
	static get DIALOG_DATA_TYPE_TABLE() { return 1 }
	static get DIALOG_DATA_TYPE_TREE() { return 2 }
	
	static get DIALOG_ENDPOINT_TYPE_REST() { return 1 }
	static get DIALOG_ENDPOINT_TYPE_EM() { return 2 }
	static get DIALOG_ENDPOINT_TYPE_CUSTOM() { return 3 }
	static get DIALOG_ENDPOINT_TYPE_CALLBACK() { return 4 }
	
	static get ID_NOT_SELECTED() {return -1}
	
	constructor(title, dialogEntity, onAccept) {

		this.uid = webix.uid();
		this.title = title;
		this.dialogEntity = dialogEntity;
		this.onAccept = onAccept;
		this.dialogInfo = undefined;
		this.searchDataTable = undefined;
		this.searchTreeView = undefined;
		this.containerId = undefined;
		this.planNodes = undefined;
		this.planHierarchies = undefined;
		this.plans = undefined;
		this.pkField = (dialogEntity.pkField) ? dialogEntity.pkField : "id";
		this.windowHeight = (dialogEntity.height) ? dialogEntity.height : 600;
		this.windowWidth = (dialogEntity.width) ? dialogEntity.width : 800;
		
	}

	show() {

		let self = this;
		
		function acceptCallback() {
			
			try {
				
				let selected = undefined;
				
				if (self.dialogEntity.dataType === SearchDialogBox.DIALOG_DATA_TYPE_TABLE) {
						
					selected = self.searchDataTable.getSelectedItems();
				}
				else if (self.dialogEntity.dataType === SearchDialogBox.DIALOG_DATA_TYPE_TREE) {

					let selectedNode = self.searchTreeView.getSelected();

					if (Array.isArray(selectedNode) && selectedNode.length > 0) {

						let planNode = self.dialogEntity.data.nodes.find(e => e.id === selectedNode[0].id_);

						if (planNode && planNode.plan) {

							selected = [{
								id: planNode.plan.id,
								alias: planNode.label
							}];
						}
					}
				}
				
				let res = undefined;
				
				if (self.onAccept && Array.isArray(selected) && selected.length > 0) { 
					
					res = self.onAccept(selected);
				
					if (res === undefined || res === true) {
						
						$$(self.containerId).hide();
						
						setTimeout(function() {

							$$(self.containerId).close();
				
						}, 100);
					}
				}
				else {
					
					let m = new MessageBox(MessageBox.ERROR, i18n.t("LBL_WRONG_SELECTION"), null, null);
					m.show();					
				}
			}
			catch {}
			
		};
		
		function getDialogInfo(onGetDialogInfo) {
		
			if (self.dialogEntity.endpointType === SearchDialogBox.DIALOG_ENDPOINT_TYPE_CALLBACK) {
				
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
			
			function getFieldDataType(fieldName) {

				let dataType = 1;

				if (Array.isArray(self.dialogEntity.fieldsDataType) && self.dialogEntity.fieldsDataType.length > 0) {

					for (let fieldDataType of self.dialogEntity.fieldsDataType) {

						let offset = fieldDataType.indexOf("=");

						if (offset !== -1) {

							let f = fieldDataType.substring(0, offset);

							if (f === fieldName) {

								dataType = parseInt(fieldDataType.substring(offset + 1));
							}
						}
					}
				}

				return dataType;
			}
			
			function getData() {
				
				let dat = [];
				
				function getDataForTable() {
					
					let data;
				
					if (self.dialogEntity.endpointType === SearchDialogBox.DIALOG_ENDPOINT_TYPE_EM) {
						
						data = window.em.getAll(EntityType[self.dialogEntity.endpoint]);
					}
					else {
						
						data = self.dialogEntity.data;
					}
					
					if (self.dialogEntity.showEmptyRowCol && self.dialogEntity.showEmptyRowCol === SearchDialogBox.ID_NOT_SELECTED) {

						let emptyRow = { id: self.dialogEntity.showEmptyRowCol };
						
						if (self.dialogEntity.fields.length > 0) {
							
							let firstField = getFieldName(self.dialogEntity.fields[0]);
							
							emptyRow[firstField] = "[" + i18n.t("LBL_EMPTY") + "]";
						}
						
						data.unshift(emptyRow);
					}
					
					if (Array.isArray(data) && data.length > 0) {
							
						return data.map(function(e) {
							
							let output = {};
							
							output.id = e[self.pkField];
							
							self.dialogEntity.fields.forEach(field => {
								
								let fieldName = getFieldName(field); 
								
								output[fieldName] = e[fieldName];
							});
	
							return output;
						});
					}	
				}
				
				function getDataForTree() {

					let data = [];

					if (self.dialogEntity.endpointType === SearchDialogBox.DIALOG_ENDPOINT_TYPE_CUSTOM ||
						self.dialogEntity.endpointType === SearchDialogBox.DIALOG_ENDPOINT_TYPE_CALLBACK) {

						if (self.dialogEntity.data) {

							data = util.tree.getNodes(
								self.dialogEntity.data.nodes,
								self.dialogEntity.data.relationships,
								{
									parentNodeId: self.dialogEntity.data.nodeParentId,
									childNodeId: self.dialogEntity.data.nodeChildId
								},
								false);
						}
					}

					return data;
				}
				
				if (self.dialogEntity.dataType === SearchDialogBox.DIALOG_DATA_TYPE_TABLE) {
					
					dat = getDataForTable();
				}
				else if (self.dialogEntity.dataType === SearchDialogBox.DIALOG_DATA_TYPE_TREE) {
				
					dat = getDataForTree();
				}
	
				return dat;
			};
			
			function getMetadata() {
				
				let meta = {};
				
				if (self.dialogEntity.dataType === SearchDialogBox.DIALOG_DATA_TYPE_TABLE) {
					
					let searchBar = (self.dialogEntity.messageType) ? "false": "true";
					let select = (self.dialogEntity.messageType) ? "false": "true";
										
					const gridOptionValues = [
						{ gridOption: { id: 1 }, value: (self.dialogEntity.multiselect) ? String(self.dialogEntity.multiselect) : "false" },
						{ gridOption: { id: 2 }, value: "true" },
						{ gridOption: { id: 3 }, value: self.dialogEntity.sortFieldId ? String(self.dialogEntity.sortFieldId) : undefined },
						{ gridOption: { id: 4 }, value: "asc" },
						{ gridOption: { id: 7 }, value: "false" },
						{ gridOption: { id: 8 }, value: "false" },
						{ gridOption: { id: 9 }, value: "true" },
						{ gridOption: { id: 10 }, value: "true" },
						{ gridOption: { id: 11 }, value: "false" },
						{ gridOption: { id: 12 }, value: "false" },
						{ gridOption: { id: 13 }, value: searchBar },
						{ gridOption: { id: 14 }, value: "false" },
						{ gridOption: { id: 15 }, value: "false" },
						{ gridOption: { id: 16 }, value: "img/grid/default.png" },
						{ gridOption: { id: 17 }, value: select },
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
						sourceField: self.pkField,
						visible: false,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					});
					
					self.dialogEntity.fields.forEach(field => {
								
						let fieldName = getFieldName(field);
						let fieldLabel = getFieldLabel(field);
						let fieldDataType = getFieldDataType(fieldName);
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
								id: fieldDataType
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
				else if (self.dialogEntity.dataType === SearchDialogBox.DIALOG_DATA_TYPE_TREE) {
					
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
		
		self.containerId = util.ui.loadWindow(self.dialogEntity.messageType ? i18n.t(self.dialogEntity.messageType.title) : self.title, body, self.windowHeight, self.windowWidth, true);

		if (self.dialogEntity.endpointType === SearchDialogBox.DIALOG_ENDPOINT_TYPE_REST) {
				
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
								getMessageBoxHeader(),
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
				if (self.dialogEntity.dataType === SearchDialogBox.DIALOG_DATA_TYPE_TABLE && self.searchDataTable) {
				
					self.searchDataTable.callbacks();
				}	
			}
		}
		
		function getMessageBoxHeader() {

			let messageBoxHeader = { height: 1 };

			if (self.dialogEntity.messageType) {

				messageBoxHeader = {
					height: 50,
					cols: [
						{ width: 35 },
						{
							width: 45,
							css: { "border": "0px !important" },
							template: `<div><img style='width: 40px; padding: 0px 15px 15px 0px;' src=${self.dialogEntity.messageType.icon}></div>`
						},
						{
							rows: [
								{
									view: "template",
									template: `<p style='font-size: 14px; color: #475466; text-align: center'>${self.title}</p>`,
									css: "messagebox"
								}
							]
						},
						{ width: 60 }
					]
				}
			}

			return messageBoxHeader;

		}
		
		function getSearchComponent(onSearchComponent) {
			
			getDialogInfo(onGetDialogInfo);
			
			function onGetDialogInfo(dialogInfo) {
				
				let v;
				
				self.dialogInfo = dialogInfo;
				
				if (self.dialogEntity.dataType === SearchDialogBox.DIALOG_DATA_TYPE_TABLE) {
			
					v = {
						
						cols: [
							{
								rows: getDataTable()
							}
						]  
					};
				}	
				else if (self.dialogEntity.dataType === SearchDialogBox.DIALOG_DATA_TYPE_TREE) {

					v = {
						css: { "border": "1px solid #CCD7E6", "box-sizing": "border-box" },
						cols: [getTreeView()]  
					} 
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
			
			let cancelButton = { 
				view: "button",
				css: "dialog-cancel", 
				width: 130,
				height: 35, 
				label: (self.dialogEntity.messageType) ? i18n.t("LBL_CLOSE") : i18n.t("LBL_CANCEL"),
				on: {
					onItemClick() {

						if (self.dialogEntity.messageType && self.onAccept) {

							self.onAccept();
						}

						$$(self.containerId).close();
					}
				}
			};
			
			let acceptButton = { 
					view: "button", 
					css: (self.dialogEntity.messageType) ?  "dialog-cancel" :"dialog-accept",
					width: 130,
					height: 35,
					label: (self.dialogEntity.messageType) ? i18n.t("LBL_CLOSE") : i18n.t("LBL_ACCEPT"),
					on: {
						onItemClick(){
							
							if ((self.dialogEntity.dataType === SearchDialogBox.DIALOG_DATA_TYPE_TABLE && self.searchDataTable) || (self.dialogEntity.dataType === SearchDialogBox.DIALOG_DATA_TYPE_TREE && self.searchTreeView)) {
							
								acceptCallback();	
							}
						}
					}
				};
			
			if (!self.onAccept || self.dialogEntity.messageType) {

				viewToolbarActions.elements.push({});
				viewToolbarActions.elements.push(cancelButton);

			} else {

				viewToolbarActions.elements.push(cancelButton);
				viewToolbarActions.elements.push({});
				viewToolbarActions.elements.push(acceptButton);
			}
			
			return viewToolbarActions;	
		}
	}
}

Object.defineProperty(SearchDialogBox, 'USERS', {
	value: { id: 1, dataType: SearchDialogBox.DIALOG_DATA_TYPE_TABLE, endpointType: SearchDialogBox.DIALOG_ENDPOINT_TYPE_EM, endpoint: "USER", pkField: "id", fields: ["name=LBL_USER", "fullName=LBL_FULL_NAME"], height: 600, width: 800 },
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(SearchDialogBox, 'LOCALES', {
	value: { id: 2, dataType: SearchDialogBox.DIALOG_DATA_TYPE_TABLE, endpointType: SearchDialogBox.DIALOG_ENDPOINT_TYPE_REST, endpoint: "i18n/locales", pkField: "id", fields: ["code=LBL_LOCALE_CODE", "name=LBL_LOCALE_NAME"], height: 600, width: 800 },
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(SearchDialogBox, 'ROLES', {
	value: { id: 3, dataType: SearchDialogBox.DIALOG_DATA_TYPE_TABLE, endpointType: SearchDialogBox.DIALOG_ENDPOINT_TYPE_REST, endpoint: "users/roles", pkField: "id", fields: ["alias=LBL_ROLE", "description=LBL_ROLE_DESCRIPTION"], height: 600, width: 800 },
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(SearchDialogBox, 'PERMISSIONS', {
	value: {id: 4, dataType: SearchDialogBox.DIALOG_DATA_TYPE_TABLE, endpointType: SearchDialogBox.DIALOG_ENDPOINT_TYPE_REST, endpoint: "users/systemPermissions", pkField: "id", fields: ["labelAlias=LBL_LABEL_PERMISSION", "labelDescription=LBL_LABEL_PERMISSION_DESCRIPTION"], translate: ["labelAlias", "labelDescription"], height: 600, width: 800},
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(SearchDialogBox, 'PLANS', {
	value: { id: 5, dataType: SearchDialogBox.DIALOG_DATA_TYPE_TABLE, endpointType: SearchDialogBox.DIALOG_ENDPOINT_TYPE_REST, endpoint: "plans/summary", pkField: "id", fields: ["alias=LBL_PLAN", "description=LBL_PLAN_DESCRIPTION"], height: 600, width: 800 },
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(SearchDialogBox, 'CUSTOM', {
	value: { id: 6, dataType: SearchDialogBox.DIALOG_DATA_TYPE_TABLE, endpointType: SearchDialogBox.DIALOG_ENDPOINT_TYPE_CUSTOM },
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(SearchDialogBox, 'CALLBACK', {
	value: { id: 7, dataType: SearchDialogBox.DIALOG_DATA_TYPE_TABLE, endpointType: SearchDialogBox.DIALOG_ENDPOINT_TYPE_CALLBACK },
	writable: false,
	enumerable: true,
	configurable: false
});
