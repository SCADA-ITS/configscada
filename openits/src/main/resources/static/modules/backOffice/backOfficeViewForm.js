import BackOfficeModule from "./backOfficeModule.js";
import BackOfficeView from "./backOfficeView.js";
import BackOfficeViewGrid from "./backOfficeViewGrid.js";
import BackOfficeHelper from "./backOfficeHelper.js";
import SearchDialogBox from "../../component/searchDialogBox.js";
import MessageBox from "../../component/messageBox.js";
import { util } from "../../lib/util.js";

export default class BackOfficeViewForm extends BackOfficeView {

	constructor(backOffice, data, currentTableData) {

		super(backOffice, data, currentTableData);
		this.accepted = false;
		
		if (backOffice.containerId) {
			
			util.ui.resizeWindow(backOffice.containerId, BackOfficeModule.Config.DEFAULT_FORM_WIDTH, BackOfficeModule.Config.DEFAULT_FORM_HEIGHT);
			util.ui.centerWindow(backOffice.containerId);
		}
	}
	
	getView() {
		
		let self = this;
		let view;
		
		view = getViewContent();
		
		function getViewContent() {

			let viewContent;

			viewContent = {
				id: "backOffice-view-" + self.uid,
				css: { "box-sizing": "border-box" },
				rows: [
					getHeader(),
					{ height: 15 },
					{
						cols: [
							{ width: 15 },
							getTabs(),
							{ width: 15 }
						]
					},
					getButtons(),
					{ height: 15 }
				]
			};
			
			function getHeader() {

				let header;
				const label = (self.backOffice.onCancel ? i18n.t("LBL_NEW") : i18n.t("LBL_EDIT")) + " " + self.currentTableData.labelSingular ?? (self.currentTableData.label ?? self.currentTableData.name);
				const description = self.currentTableData.labelDescription ?? "";
				
				const icon = self.currentTableData.metadata && self.currentTableData.metadata.mdiIcon ? self.currentTableData.metadata.mdiIcon : "mdi mdi-application"; 

				header = {
					id: "viewHeader-" + self.uid,
					cols: [
//						{
//							view: "label",
//							label: "<img src='img/grid/default.png' class='grid-icon'/>",
//							height: 75,
//							css: "global-header-icon"
//						},
						{
							view: "label",
							height: 75,
							template: `<span class='${icon}'></span>`,
							css: "global-header-mdi"
						},
						{
							view: "label",
							height: 75,
							width: 450,
							template: "<span>" + label + "</span> <br> <span style='font-size: 13px'>" + description + "</span>",
							css: "global-header-label",
							on: {
								onDestruct: function() {
									
									if (self.backOffice.onCancel && !self.accepted) {
										
										self.backOffice.onCancel();
									}		
								}
							}
						}
					]
				};

				return header;
			}

			function getTabs() {

				let tabs;

				tabs = {
					view: "tabview",
					id: "tabview-" + self.uid,
					borderless: true,
					css: "secondary-tab",
					cells: [],
					tabbar: {
						tabMargin: 6,
					}
				}

				tabs.cells.push({
					header: i18n.t("LBL_INFO"),
					body: getInfoView(),
				});
				
				if (!self.backOffice.noReferTables) {
					
					pushReferTables();	
				}

				function getInfoView() {

					let infoView;

					infoView = {
						id: "form-" + self.uid,
						view: "form",
						css: "bordered-layout-without-border-top",
						elementsConfig: { labelAlign: "left" },
						elements: getElements()
					}
					
					function getElements() {
						
						let elements = [];
						
						if (Array.isArray(self.currentTableData.columns) &&
							self.currentTableData.columns.length > 0) {
						
							if (self.currentTableData.metatada && 
								Array.isArray(self.currentTableData.metatada.tabs) && 
								self.currentTableData.metatada.tabs.length > 0) {
									
								let tabInfo = {
									view: "tabview",
									borderless: true,
									css: "auxiliary-tab",
									cells: [],
									tabbar: {
										tabMargin: 6,
									}
								}
									
								self.currentTableData.metatada.tabs.forEach(tab => {

									tabInfo.cells.push({
										
										header: tab.label,
										body: {
											view: "scrollview",
											scroll: "auto",
											borderless: true,
											body: {
												cols: [
													{ width: 45 },
													{
														rows: getFields(tab)
													},
													{ width: 45 }
												]
											}
										}
									});
								});
								
								elements.push(tabInfo);
							}
							else {
																
								let info = {
									view: "scrollview",
									scroll: "auto",
									borderless: true,
									body: {
										cols: [
											{ width: 45 },
											{
												rows: getFields()
											},
											{ width: 45 }
										]
									}
								}
								
								elements.push(info);
							}		
						}
						
						return elements;
					}
					
					function getFields(tab) {
						
						let fields = [{ height: 45 }];
						
						if (tab && Array.isArray(tab.fields) && tab.fields.length > 0) {
						
							tab.fields.forEach(field => {
						
								addField(self.currentTableData.columns.find(e => e.name === field.name));
							});
						}
						else {
							
							self.currentTableData.columns.forEach(column => addField(column));
						}	
						
						function addField(column) {
							
							if (column) {
								
								const dbType = BackOfficeHelper.getDBTypes(column.colType);
								
								if (dbType) {
									
									let field = webix.copy(dbType.component);
								
									field.name = column.tableId + "_" + column.id + "_" + column.name;
									field.label = util.getStringCapitalized(column.alias ?? column.name);
									field.disabled = (column.name === "id");
									
									let value = BackOfficeHelper.getColumnValue(self.currentTableData, column.id, 0);
									
									if (value) {
									
										if (dbType.name === BackOfficeHelper.DB_TYPE_TIMESTAMP) {
										
											value = new Date(parseInt(value));
										}
										else if (dbType.name === BackOfficeHelper.DB_TYPE_DECIMAL || 
												 dbType.name === BackOfficeHelper.DB_TYPE_INTEGER) {
										
											field.type = "number";
										}	
										else {
											
											field.type = "text";
										}
									}
									
									
									field.value = value;
									
									const refColumn = column.refColumn;
									const refTable = refColumn ? self.data.find(e => e.id === refColumn.tableId) : undefined;
									
									if (refColumn && refTable) {
										
										const referencedColumnValue = BackOfficeHelper.getReferencedColumnValue(column, value, self.data);
										
										if (referencedColumnValue) {
											
											const dbTypeRefColumn = BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_STRING);
										
											let fieldRef = webix.copy(dbTypeRefColumn.component);
										
											let fieldGroup = {
												cols: [
													{ view: fieldRef.view, type: "text", name: refColumn.tableId + "_" + refColumn.id + "_" + referencedColumnValue.name, required: fieldRef.required, label: util.getStringCapitalized(refTable.labelSingular ?? (refTable.label ?? refTable.name)), readonly: true, labelWidth: 275, value: referencedColumnValue.value },
													{ view: field.view, name: field.name, hidden: false, readonly: true, value: field.value, width: 50 },
													{
														view: "icon", 
														icon: "wxi-search",
														tooltip: i18n.t("LBL_SEARCH"),
														on: {
															onItemClick: updateRefField
														}
													},
													{
														view: "icon", 
														icon: "wxi-plus",
														tooltip: i18n.t("LBL_CMD_ADD"),
														on: {
															onItemClick: createRefField
														}
													}
												]
											}
											
											fields.push(fieldGroup);
										}
										
										function updateRefField() {
											
											const table = self.data.find(e => e.id === refColumn.tableId);
								
											if (table && Array.isArray(table.rows) && table.rows.length > 0) {
												
												if (field.name && referencedColumnValue) {
													
													let dialogRefField = webix.copy(SearchDialogBox.CALLBACK);				
						
													dialogRefField.fillDialogEntity = fillDialogEntity;
													dialogRefField.pkField = field.name
													const fields = referencedColumnValue.tableId + "_" + referencedColumnValue.id + "_" + referencedColumnValue.name + "=" + util.getStringCapitalized(referencedColumnValue.alias ?? referencedColumnValue.name);
													dialogRefField.fields = [fields];
													dialogRefField.sortFieldId = 1;
													dialogRefField.height = 600;
													dialogRefField.width = 800;
													
													function fillDialogEntity(dialogEntity, onFilledDialogEntity) {
										
														dialogRefField.data = table.rows.map(row => {
														
															const rowId = row.data.find(data => data.columnId === table.columns.find(col => col.name === "id").id);
															const rowAlias = row.data.find(data => data.columnId === referencedColumnValue.id);
																
															let obj = {};
															
															if (rowId && rowAlias) {
																
																obj[field.name] = rowId.value;
																obj[referencedColumnValue.tableId + "_" + referencedColumnValue.id + "_" + referencedColumnValue.name] = rowAlias.value;	
															}
															
															return obj;
														});
										
														onFilledDialogEntity(dialogEntity);
													}
													
													(new SearchDialogBox(i18n.t("LBL_SEARCH " + util.getStringCapitalized(table.alias ?? table.name)), dialogRefField, onAccept)).show();
													
													function onAccept(selection) {
														
														const form = $$("form-" + self.uid);
														
														if (form && selection) {
															
															let itemSelected = selection[0];
															
															let newValue = {};
															let oldValue = form.getValues();
															
															newValue[field.name] = itemSelected.id;
															newValue[refColumn.tableId + "_" + refColumn.id + "_" + referencedColumnValue.name] = itemSelected[referencedColumnValue.tableId + "_" + referencedColumnValue.id + "_" + referencedColumnValue.name];
															
															form.setValues(newValue, true);
														}
													}
												}
											}
											else {
												
												(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_NO_RECORDS_ON_REF_TABLE") + ": " + (table.alias ?? table.name) + i18n.t("LBL_BACKOFFICE_CREATE_NEW_RECORD"), null, onAccept, null)).show();
												
												function onAccept() {
													
													createRefField();
												}
											}
										}
										
										function createRefField() {
											
											const table = self.data.find(e => e.id === refColumn.tableId);
								
											if (table && Array.isArray(table.rows) && field.name && referencedColumnValue) {
											
												BackOfficeHelper.insertData(self.backOffice, table).then((persistResult) => {
															
													if (!persistResult.error) {
														
														if (persistResult.newRow && Array.isArray(persistResult.newRow.data)) {
															
															const columnId = table.columns.find(e => e.name === "id");
															
															if (columnId) {
																
																const data = persistResult.newRow.data.find(e => e.columnId === columnId.id);
																
																if (data && data.value) {
																	
																	const columnIdValue = data.value; 
														
																	edit(columnIdValue, function () {
																		
																		let persistValues = [];
																		
																		persistValues.push({
																			colName: columnId.name,
																			oldValue: columnIdValue,
																			newValue: undefined,
																			colType: columnId.colType
																		});
													
																		BackOfficeHelper.deleteData(self.backOffice, table, persistValues, columnIdValue).then((persistResult) => {
																			
																			if (persistResult.error) {
																				
																				log.error(persistResult.error + "\n" + persistResult.errorDescription);
																				
																				(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_SAVING_CHANGES_ON") + " " + (self.currentTableData.alias ?? self.currentTableData.name), persistResult.error + "\n" + persistResult.errorDescription, null, null)).show();
																			}	
																		});
																	});			
																}	
															}
														}
													}
													else {
														
														log.error(persistResult.error + "\n" + persistResult.errorDescription);
														
														(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_SAVING_CHANGES_ON") + " " + (self.currentTableData.alias ?? self.currentTableData.name), persistResult.error + "\n" + persistResult.errorDescription, null, null)).show();
													}
												});	
											}
											
											function edit(id, onCancel) {
						
												const module = window.em.getByField(EntityType.MODULE, "id", BackOfficeModule.Config.MODULE_ID);
						
												if (module) {
													
													const winId = util.ui.loadWindow(i18n.t("LBL_NEW") + " " + (table.labelSingular ?? (table.label ?? table.name)), { id: self.uid }, BackOfficeModule.Config.DEFAULT_FORM_HEIGHT, 
																						BackOfficeModule.Config.DEFAULT_FORM_WIDTH, true);
														const args = {
															backOffice: {
																id: self.backOffice.id,
																tableName: table.name,
																filterColumnName: "id",
																filterColumnValue: id,
																isModal: true,
																noReferTables: true,
																onAccept: onAccept,
																onCancel: onCancel
															},
															containerId: winId,
															onLoad: onLoad
														}
							
													util.module.open(module, {id: BackOfficeModule.Config.MODULE_ACTION_OPEN_ID }, VIEW_TYPE_CUSTOM, drawContent, args);
												
													function drawContent(view) {
							
														webix.ui(view, $$(self.uid));
													}
													
													function onLoad() {
														
														util.ui.resizeWindow(winId, BackOfficeModule.Config.DEFAULT_FORM_WIDTH, BackOfficeModule.Config.DEFAULT_FORM_HEIGHT);
														util.ui.centerWindow(winId, 20);	
													}
													
													function onAccept(newRow) {
														
														if (newRow) {
														
															log.debug(newRow);
															
															self.refresh();
															
															const form = $$("form-" + self.uid);
															
															if (form) {
															
																const rowId = newRow.data.find(data => data.columnId === table.columns.find(col => col.name === "id").id);
																const rowAlias = newRow.data.find(data => data.columnId === referencedColumnValue.id);
																	
																if (rowId && rowAlias) {
																	
																	let newValue = {};
																	
																	newValue[self.currentTableData.id + "_" + column.id + "_" + column.name] = rowId.value;
																	newValue[refColumn.tableId + "_" + refColumn.id + "_" + referencedColumnValue.name] = rowAlias.value;
																	
																	form.setValues(newValue, true);
																}
															}	
														}
													}
												}
											}
										}
									}
									else {
									
										fields.push(field);	
									}
								}
							}
						}
						
						return fields;
					}

					return infoView;
				}
				
				function pushReferTables() {
					
					if (Array.isArray(self.currentTableData.referTables) && self.currentTableData.referTables.length > 0) {
						
						if (Array.isArray(self.currentTableData.columns) && self.currentTableData.columns.length > 0) {
								
							const columnId = self.currentTableData.columns.find(e => e.name === 'id');
							
							if (columnId) {
								
								self.currentTableData.referTables.forEach(referTable => {
								
									const label = util.getStringCapitalized(referTable.label ?? referTable.name);
									const idValue = BackOfficeHelper.getColumnValue(self.currentTableData, columnId.id, 0); 
									const referColumn = BackOfficeHelper.getRefColumn(self.currentTableData, referTable.id);
									
									if (referColumn && idValue) {
										
										let newBackOffice = {
										
											id: self.backOffice.id,
											tableName: referTable.name,
											filterColumnName: referColumn.name,
											filterColumnValue: idValue,
											containerId: self.backOffice.containerId,
											filterColumn: referColumn
										}
										
										let backOfficeViewGrid = new BackOfficeViewGrid(newBackOffice, self.data, self.data.find(e => e.id === referColumn.tableId));
										
										if (backOfficeViewGrid) {
											
											let viewBackOfficeViewGrid = backOfficeViewGrid.getView();
											
											if (viewBackOfficeViewGrid) {
												
												viewBackOfficeViewGrid.css = "bordered-layout-without-border-top";
												
												tabs.cells.push({
													header: label,
													body: viewBackOfficeViewGrid
												});	 
											}
										}
									}
								});
							}		
						}
					}
				}

				return tabs;
			}

			function getButtons() {

				let viewToolbarActions = {};
				
				const container = $$(self.backOffice.containerId);

				viewToolbarActions = {
					id: "buttons-" + self.uid,
					borderless: true,
					css: { "margin-top": "1px !important" },
					rows: [
						{
							cols: [
								{ width: 15 },
								{
									view: "button",
									value: i18n.t("LBL_CANCEL"),
									width: 130,
									height: 41,
									css: "dialog-cancel",
									click:
										function() {
											
											if (self.backOffice.onCancel) {
												
												self.backOffice.onCancel()
											}
											
											if (container) {
												
												container.close();	
											}
										}
								},
								{},
								{
									view: "button",
									value: i18n.t("LBL_ACCEPT"),
									width: 130,
									height: 41,
									css: "dialog-accept",
									click:
										function() {

											let validation = $$("form-" + self.uid).validate({ disabled: true });

											if (validation) {

												persistUpdate();

											} else {

												$$("form-" + self.uid).show();
											}
										}
								},
								{ width: 15 }
							]
						}
					]
				}
				
				function persistUpdate() {
					
					const form = $$("form-" + self.uid);
					let id = undefined;
					let persistValues = [];
					
					if (form) {
						
						const values = form.getValues();
						
						if (typeof(values) === 'object') {
							
							const keys = Object.keys(values);

							if (Array.isArray(keys) && keys.length > 0) {
								
								keys.forEach(key => {
									
									const keySplitted = key.split("_");
									
									if (Array.isArray(keySplitted) && keySplitted.length > 2) {
										
										const tableId = parseInt(keySplitted[0]);

										if (tableId === self.currentTableData.id) {
											
											const colId = parseInt(keySplitted[1]);
											const column = self.currentTableData.columns.find(e => e.id === colId);
												
											if (column) {
												
												const oldValue = BackOfficeHelper.getColumnValue(self.currentTableData, colId, 0);
												let newValue = undefined;
												
												if (column.colType === BackOfficeHelper.DB_TYPE_INTEGER) {
													
													newValue = parseInt(values[key]);
												}
												else if (column.colType === BackOfficeHelper.DB_TYPE_TIMESTAMP) {
													
													newValue = parseInt(values[key].getTime());
												}
												else if (column.colType === BackOfficeHelper.DB_TYPE_DECIMAL) {
													
													newValue = parseFloat(values[key]);
												}
												else {
													
													newValue = values[key];	
												}
												
												if (column.name === "id") {
														
													id = newValue;
												}
												
												if (oldValue !== newValue) {
													
													persistValues.push({
														colName: column.name,
														oldValue: oldValue,
														newValue: newValue,
														colType: column.colType
													});
												}
											}	
										}
										
									}
								});
								
								if (id && ((Array.isArray(persistValues) && persistValues.length > 0) || self.backOffice.onCancel)) {
									
									(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_SAVE_CHANGES_ON") + " " + (self.currentTableData.alias ?? self.currentTableData.name), null, onAccept , null)).show();
								}
								else {
									
									if (container) {
						
										container.close();	
									}
								}
							}	
						}
					}
					
					function onAccept() {
						
						self.accepted = true;
						
						BackOfficeHelper.updateData(self.backOffice, self.currentTableData, persistValues, id).then((persistResult) => {
										
							if (!persistResult.error) {
								
								const form = $$("form-" + self.uid);
						
								if (form && self.backOffice.onAccept) {
									
									self.backOffice.onAccept(persistResult.newRow);
								}
								
								if (container) {
						
									container.close();	
								}
							}
							else {
								
								log.error(persistResult.error + "\n" + persistResult.errorDescription);
								
								(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_SAVING_CHANGES_ON") + " " + (self.currentTableData.alias ?? self.currentTableData.name), persistResult.error + "\n" + persistResult.errorDescription, null, null)).show();
							}
						});	
					}
				}

				return viewToolbarActions;
			}
			
			return viewContent;
		}
		
		return view;
	}
	
	refresh() {
		
		let self = this;
		
		BackOfficeHelper.getData(self.backOffice).then((data) => {
			
			self.data = data;
			self.currentTableData = data.find(e => e.name === self.backOffice.tableName);
		});
	}
}
