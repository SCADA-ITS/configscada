import BackOfficeModule from "./backOfficeModule.js";
import BackOfficeView from "./backOfficeView.js";
import BackOfficeHelper from "./backOfficeHelper.js";
import DataTable from "../../component/datatable.js";
import MessageBox from "../../component/messageBox.js";
import { util } from "../../lib/util.js";

export default class BackOfficeViewGrid extends BackOfficeView {
	
	static get GRID_COMMANDS() { return { refresh: 1, new: 2, edit: 3, delete: 4 } }

	constructor(backOffice, data, currentTableData) {

		super(backOffice, data, currentTableData);
		
		this.dataTable = undefined;
		this.formHeight = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_HEIGHT, backOffice.metadata, currentTableData);
		this.formWidth = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_WIDTH, backOffice.metadata, currentTableData);
		this.gridFilters = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.GRID_FILTERS, backOffice.metadata, currentTableData);

		if (backOffice.containerId) {
			
			util.ui.centerWindow(backOffice.containerId, backOffice.isModal ? 20: 0);
			util.ui.setTitle(backOffice.containerId, util.getStringCapitalized(currentTableData.label ?? currentTableData.name));	
		}
	}
	
	getView() {
		
		let self = this;
		let view = getDataTableView();
		
		function getDataTableView() {

			let viewDataTable;
			
			self.dataTable = getDataTable();
			
			if (self.dataTable) {
				
				viewDataTable = {
					
					id: "backOffice-view-" + self.uid,
					rows: self.dataTable.getView()
				}
				
				viewDataTable.rows.push({
					view: "template",
					height: 1,
					on: {
						onAfterRender: function() {
		
							self.dataTable.callbacks();
						}
					}	
				});	
			}
			
			function getData() {
			
				let data = [];
				
				if (Array.isArray(self.currentTableData.rows) && self.currentTableData.rows.length > 0) {
					
					self.currentTableData.rows.forEach(row => {
				
						if (Array.isArray(row.data) && row.data.length > 0) {
						
							let record = {};
							
							row.data.forEach(e => {
							
								const column = self.currentTableData.columns.find(col => col.id === e.columnId);
								
								if (column && column.name) {
									
									let filterColumnName = undefined;
						
									if (self.backOffice.filterColumn && self.backOffice.filterColumnValue) {
										
										filterColumnName = self.backOffice.filterColumn.name;
									}
										
									if (!filterColumnName || column.name != filterColumnName) {
										
										const referencedColumnValue = BackOfficeHelper.getReferencedColumnValue(column, e.value, self.data);
										
										let value = e.value;
										
										if (column.colType === BackOfficeHelper.DB_TYPE_TIMESTAMP && !isNaN(value)) {
											
											value = new Date(parseInt(value));
										}
									
										if (referencedColumnValue) {
											
											let referencedValue = referencedColumnValue.value;
											
											if (referencedColumnValue.colType === BackOfficeHelper.DB_TYPE_TIMESTAMP && !isNaN(referencedValue)) {
											
												referencedValue = new Date(parsetInt(referencedColumnValue.value));												
											}
											
											record[BackOfficeHelper.getColumnUID(referencedColumnValue)] = referencedValue ?? value;	
										}
										else {
											
											record[column.name === "id" ? "id" : BackOfficeHelper.getColumnUID(column)] = value;	
										}	
									}
								} 
							});
							
							data.push(record);
						}		
					});
				}
				
				return data;	
			}
			
			function getDataTable() {
				
				const firstCommandIdCalculated = 5;
				
				function getMetadata() {
					
					let icon;	
					let withIcon = (self.currentTableData.metadata && self.currentTableData.metadata.mdiIcon) ? "true" : "false"; 

					const defaultGridOptionValues = [
						{ gridOption: { id: 1 }, value: "true" },
						//					{ gridOption: { id: 2 }, value: "true" },
						//					{ gridOption: { id: 3 }, value: "2" },
						//					{ gridOption: { id: 4 }, value: "asc" },
						{ gridOption: { id: 7 }, value: withIcon },
						{ gridOption: { id: 8 }, value: "false" },
						{ gridOption: { id: 9 }, value: "true" },
						{ gridOption: { id: 10 }, value: "true" },
						{ gridOption: { id: 11 }, value: "true" },
						{ gridOption: { id: 12 }, value: "true" },
						{ gridOption: { id: 13 }, value: "true" },
						{ gridOption: { id: 14 }, value: "false" },						
						{ gridOption: { id: 21 }, value: "auto" },
						{ gridOption: { id: 23 }, value: "true" },
						{ gridOption: { id: 25 }, value: "true" }
					];
				
					if (IS_TRUE(withIcon)) {

						icon = self.currentTableData.metadata.mdiIcon;
						defaultGridOptionValues.push({ gridOption: { id: 16 }, value: icon });
					}

					let metadata = {
						filter: self.gridFilters,
						label: self.currentTableData.label ?? self.currentTableData.name,
						visible: true,
						gridOptionValues: defaultGridOptionValues,
						gridFields: [],
						gridCommands: []
					};
					
					if (Array.isArray(self.currentTableData.columns)) {
						
						let idxField = 1;
						
						self.currentTableData.columns.sort((a, b) => (a.position > b.position) ? 1 : (( b.position > a.position) ? -1 : 0));
						
						let gridFields = [];
						
						self.currentTableData.columns.forEach(column => {
							
							let filterColumnName = undefined;
							
							if (self.backOffice.filterColumn && self.backOffice.filterColumnValue) {
											
								filterColumnName = self.backOffice.filterColumn.name;
							}
								
							if (!filterColumnName || column.name != filterColumnName) {
								
								const referencedColumnName = BackOfficeHelper.getReferencedColumnName(column, self.data);
								const sourceField = referencedColumnName ? (BackOfficeHelper.getColumnUID(referencedColumnName)) : BackOfficeHelper.getColumnUID(column);
								
								let dataTypeId = BackOfficeHelper.getDBTypes(referencedColumnName ? referencedColumnName.colType : column.colType).id;
								let isBoolean = false;
								
								if (dataTypeId == BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_BOOLEAN).id) {
									
									dataTypeId = BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_STRING).id
									isBoolean = true;
								}
								
								let label = column.label ?? column.name;
								
								if (referencedColumnName) {
									
									const table = self.data.find(e => e.id === referencedColumnName.tableId);
									
									if (table) {
										
										label = table.labelSingular ?? (table.label ?? table.name);
									}
								}

								let gridFiltersColumn = (column.metadata) ? column.metadata[BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.GRID_FILTERS.name] : undefined;
								let gridFilter = (gridFiltersColumn != undefined) ? gridFiltersColumn : self.gridFilters;  

								let gridField = {
									id: idxField,
									extended: false,
									filtered: gridFilter,
									isEpochMilli: dataTypeId == BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_TIMESTAMP).id,
									isPk: column.name === "id",
									needsTranslation: false,
									parameterized: false,
									position: idxField,
									required: true,
									label: util.getStringCapitalized(label),
									sourceField: column.name === "id" ? "id" : sourceField,
									visible: column.name !== "id",
									gridFieldDataType: {
										id: dataTypeId
									}
								};
								
								if (isBoolean) {
									
									gridField.gridFieldIcons = [
										{
											id: 1,
											avatar: false,
											badgeColor: false,
											enabled: true,
											icon: "./img/state/valid.png",
											showValue: false,
											value: "true",
											labelValue: "LBL_YES"
										},
										{
											id: 2,
											avatar: false,
											badgeColor: false,
											enabled: true,
											icon: "./img/state/invalid.png",
											showValue: false,
											value: "false",
											labelValue: "LBL_NO"
										},
										{
											id: 3,
											avatar: false,
											badgeColor: false,
											enabled: true,
											icon: "./img/state/valid.png",
											showValue: false,
											value: "1",
											labelValue: "LBL_YES"
										},
										{
											id: 4,
											avatar: false,
											badgeColor: false,
											enabled: true,
											icon: "./img/state/invalid.png",
											showValue: false,
											value: "0",
											labelValue: "LBL_NO"
										}
									];
								}
								
								gridFields.push(gridField);
								
								idxField++;
							}
						});
						
						metadata.gridFields = gridFields;
					}
					
					metadata.gridCommands.push(
						{
							id: BackOfficeViewGrid.GRID_COMMANDS.refresh,
							default_: false,
							icon: "mdi mdi-refresh",
							label: "LBL_TABLE_REFRESH",
							multiselect: true,
							position: 1,
							showText: true,
							itemRequired: false
						},
						{
							id: BackOfficeViewGrid.GRID_COMMANDS.new,
							default_: false,
							icon: "mdi mdi-plus",
							label: "LBL_CMD_ADD",
							multiselect: false,
							position: 2,
							showText: false,
							itemRequired: false
						},
						{
							id: BackOfficeViewGrid.GRID_COMMANDS.edit,
							default_: true,
							icon: "mdi mdi-pencil",
							label: "LBL_CMD_EDIT",
							multiselect: false,
							position: 3,
							showText: false,
							itemRequired: true
						},
						{
							id: BackOfficeViewGrid.GRID_COMMANDS.delete,
							default_: false,
							icon: "mdi mdi-delete",
							label: "LBL_CMD_DELETE",
							multiselect: true,
							position: 4,
							showText: false,
							itemRequired: true
						}
					);
					
					if (Array.isArray(self.currentTableData.commands) && self.currentTableData.commands.length > 0) {
										
						const commandButtons = getCommandButtons();
						
						if (Array.isArray(commandButtons) && commandButtons.length > 0) {
							
							metadata.gridCommands.push(...commandButtons);	
						}
					}
					
					function getCommandButtons() {

						let commandIdValue = firstCommandIdCalculated;
						let commandButtons = [];
						let cmdPosition = metadata.gridCommands.length + 1;
						
						if (Array.isArray(self.currentTableData.commands) && self.currentTableData.commands.length > 0) {
							
							const commandsAvailable = self.currentTableData.commands.filter(e => e.availableInTable);
							
							commandsAvailable.forEach(cmd => {
		
								commandButtons.push(getCommand(cmd));
							});	
						}
		
						function getCommand(cmd) {
		
							return {
								id: commandIdValue++,
								icon: cmd.mdiIcon,
								label: cmd.label ?? cmd.name, 
								multiselect: false,
								position: cmdPosition++,
								showText: true,
								itemRequired: true,
								grouped: true
							}
						}
		
						return commandButtons;
					}
					
					return metadata;
				}
				
				function getEvents() {
					
					let commandIdValue = firstCommandIdCalculated;
					let events = {};
		
					events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
		
					events[DataTable.EventTypes.ON_COMMAND_EXEC].push(
						
						{ commandId: BackOfficeViewGrid.GRID_COMMANDS.refresh, function: cmdRefresh },
						{ commandId: BackOfficeViewGrid.GRID_COMMANDS.new, function: cmdNew },
						{ commandId: BackOfficeViewGrid.GRID_COMMANDS.edit, function: cmdEdit },
						{ commandId: BackOfficeViewGrid.GRID_COMMANDS.delete, function: cmdDelete }
					);
					
					if (Array.isArray(self.currentTableData.commands) && self.currentTableData.commands.length > 0) {
						
						const commandButtons = getCommandButtons();
						
						if (Array.isArray(commandButtons) && commandButtons.length > 0) {
							
							events[DataTable.EventTypes.ON_COMMAND_EXEC].push(...commandButtons);	
						}
					}
					
					function getCommandButtons() {

						let commandButtons = [];
						
						const commandsAvailable = self.currentTableData.commands.filter(e => e.availableInTable); 
						
						commandsAvailable.forEach(cmd => {
							
							let commandButton = {
								
								commandId: commandIdValue++,
								function: command
							}
	
							commandButtons.push(commandButton);
							
							function command(ids, items) {
								
								if (Array.isArray(items) && items.length === 1) {
								
									if (cmd.requireConfirmation) {
											
										(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_CONFIRM_COMMAND") + " " + (cmd.label ?? cmd.name), null, executeCommand, null)).show();	
									}
									else {
										
										executeCommand();
									}	
								}
								
								function executeCommand() {
								
									const requestInput = BackOfficeHelper.getRequestInput(items[0], self.currentTableData, true);
									const id = items[0]["id"];
									
									if (id && (Array.isArray(requestInput) && requestInput.length > 0)) {
										
										BackOfficeHelper.command(self.backOffice, self.currentTableData, cmd.id, requestInput, id).then((requestResult) => {
													
											if (!requestResult.requestResultMessage.error) {
												
												if (requestResult.requestResultMessage.info) {
												
													log.info(requestResult.requestResultMessage.info + "\n" + requestResult.requestResultMessage.infoDescription);
												
													(new MessageBox (MessageBox.INFO, i18n.t("LBL_BACKOFFICE_INFO_ON_COMMAND") + " " + requestResult.requestResultMessage.info + ":" + requestResult.requestResultMessage.infoDescription, null, null, null)).show();
												}
												else if (requestResult.requestResultMessage.warn) {
												
													log.warn(requestResult.requestResultMessage.info + "\n" + requestResult.requestResultMessage.infoDescription);
												
													(new MessageBox (MessageBox.WARN, i18n.t("LBL_BACKOFFICE_WARN_ON_COMMAND") + " " + requestResult.requestResultMessage.warn + ":" + requestResult.requestResultMessage.warnDescription, null, null, null)).show();
												}
											}
											else {
												
												log.error(requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription);
												
												(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_ON_COMMAND") + " " + (self.currentTableData.label ?? self.currentTableData.name), requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription, null, null)).show();
											}
										});
									}
								}
							}
						});	

						return commandButtons;
					}
					
					function cmdRefresh() {
						
						if ($$("backOffice-view-" + self.uid) && self.dataTable) {
							
							const grid = $$(self.dataTable.getViewId())
			
							if (grid) {
							
								webix.extend(grid, webix.ProgressBar);

								grid.showProgress({
					
									hide: false, type: "bottom"
								});
							
								BackOfficeHelper.getData(self.backOffice).then((data) => {
									
									const selectedIds = self.dataTable.getSelectedIds();
									
									self.data = data;
									self.currentTableData = data.find(e => e.name === self.backOffice.tableName);
	
									self.dataTable.clear();
									self.dataTable.add(getData());
									self.dataTable.select(selectedIds);
									
									grid.hideProgress();								
								});	
							}
						}
					}
					
					function cmdNew() {
						
						let persistValues = [];
						
						if (self.backOffice.filterColumn && self.backOffice.filterColumnValue) {
											
							persistValues.push({
								colName: self.backOffice.filterColumn.name,
								oldValue: undefined,
								newValue: self.backOffice.filterColumnValue,
								colType: self.backOffice.filterColumn.colType
							});
						}
						
						BackOfficeHelper.insertData(self.backOffice, self.currentTableData, persistValues).then((requestResult) => {
										
							if (!requestResult.requestResultMessage.error) {
								
								if (requestResult.newRow && Array.isArray(requestResult.newRow.data)) {
									
									const columnId = self.currentTableData.columns.find(e => e.name === "id");
									
									if (columnId) {
										
										const data = requestResult.newRow.data.find(e => e.columnId === columnId.id);
										
										if (data && data.value) {
											
											const columnIdValue = data.value;
								
											edit(columnIdValue, function () {
							
												BackOfficeHelper.deleteData(self.backOffice, self.currentTableData, persistValues, columnIdValue).then((requestResult) => {
													
													if (requestResult.requestResultMessage.error) {
														
														log.error(requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription);
														
														(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_SAVING_CHANGES_ON") + " " + (self.currentTableData.label ?? self.currentTableData.name), requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription, null, null)).show();
													}	
												});
											});			
										}	
									}
								}
							}
							else {
								
								log.error(requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription);
								
								(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_SAVING_CHANGES_ON") + " " + (self.currentTableData.label ?? self.currentTableData.name), requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription, null, null)).show();
							}
						});
					}
					
					function cmdEdit(ids) {
						
						if (Array.isArray(ids) && ids.length > 0) {
						
							edit(ids[0]);
						}
					}
					
					function cmdDelete(ids) {
						
						if (Array.isArray(ids) && ids.length > 0) {
							
							(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_DELETE_CONFIRM") + " " + (self.currentTableData.label ?? self.currentTableData.name), null, onAccept , null)).show();
							
							function onAccept() {
								
								let recordsToDelete = ids.length;
								let error = "";
								const columnId = self.currentTableData.columns.find(c => c.name === "id");
								
								let deletedRecords = 0;
								
								while (recordsToDelete > 0) {
									
									let persistValues = [];
									recordsToDelete--;
									
									if (Array.isArray(self.currentTableData.rows) && self.currentTableData.rows.length > 0) {
										
										const row = self.currentTableData.rows.find(row => 
																Array.isArray(row.data) && 
																(row.data.find(dat => dat.columnId === columnId.id)) != undefined && 
																(row.data.find(dat => dat.value == ids[recordsToDelete])) != undefined);
										
										if (row) {
										
											row.data.forEach(dat => {
											
												const column = self.currentTableData.columns.find(c => c.id === dat.columnId);
												
												if (column) {
													
													persistValues.push({
														colName: column.name,
														oldValue: dat.value,
														newValue: undefined,
														colType: column.colType
													});
												}
											});
										}
									}
									
									if (Array.isArray(persistValues) && persistValues.length > 0) {
										
										BackOfficeHelper.deleteData(self.backOffice, self.currentTableData, persistValues, ids[recordsToDelete]).then((requestResult) => {
											
											if (requestResult.requestResultMessage.error) {
			
												error += "\nError id: "+ ids[recordsToDelete] + " => " +  requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription;
											}
											
											if (++deletedRecords === ids.length) {
												
												if (error) {
													
													(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_DELETING_RECORDS") + " " + (self.currentTableData.label ?? self.currentTableData.name), error, null, null)).show();				
												}
												else {
													
													ids.forEach(id => {
														
														const itemToDelete = self.dataTable.getItem(id);
												
														if (itemToDelete) {
															
															self.dataTable.delete(itemToDelete);
														}
														
														const idxRow = self.currentTableData.rows.findIndex(row => 
																Array.isArray(row.data) && 
																(row.data.find(dat => dat.columnId === columnId.id)) != undefined && 
																(row.data.find(dat => dat.value == ids[recordsToDelete])) != undefined);
										
														if (idxRow > -1) {
														
															self.currentTableData.rows.splice(idxRow, 1);
														}
													});
												}
												
												//self.refresh();
											}
										});	
									}
								}
							}
						}
					}
					
					function edit(id, onCancel) {
						
						const module = window.em.getByField(EntityType.MODULE, "id", BackOfficeModule.Config.MODULE_ID);

						if (module) {
							
//							const winId = util.ui.loadWindow(undefined, { id: self.uid }, BackOfficeModule.Config.DEFAULT_FORM_HEIGHT, 
//																BackOfficeModule.Config.DEFAULT_FORM_WIDTH, true);

							const winId = util.ui.loadWindow(undefined, { id: self.uid }, self.formHeight, self.formWidth, true);
							
							const args = {
								backOffice: {
									id: self.backOffice.id,
									tableName: self.currentTableData.name,
									filterColumnName: "id",
									filterColumnValue: id,
									isModal: true,
									onAccept: onAccept,
									onCancel: onCancel,
									filterColumn: self.backOffice.filterColumn
								},
								containerId: winId,
								onLoad: onLoad
							}
	
							util.module.open(module, {id: BackOfficeModule.Config.MODULE_ACTION_OPEN_ID }, VIEW_TYPE_CUSTOM, drawContent, args);
						
							function drawContent(view) {
	
								webix.ui(view, $$(self.uid));
							}
							
							function onLoad() {
								
								//util.ui.resizeWindow(winId, BackOfficeModule.Config.DEFAULT_FORM_WIDTH, BackOfficeModule.Config.DEFAULT_FORM_HEIGHT);
								
								util.ui.resizeWindow(winId, self.formWidth, self.formHeight);
								util.ui.centerWindow(winId);
								
								let windowTitle = i18n.t("LBL_EDIT"); 
			
								if (args.backOffice.onCancel) {
								
									windowTitle = i18n.t("LBL_NEW");
								}
								
								windowTitle += " " + (self.currentTableData.labelSingular ?? (self.currentTableData.label ?? self.currentTableData.name));
								
								util.ui.setTitle(args.backOffice.containerId, windowTitle);	
							}
							
							function onAccept(values) {
								
								if (values && self.dataTable) {
									
									const id = values["id"];
									const columnIdentify = self.currentTableData.columns.find(e => e.name === "id");
									
									if (id && columnIdentify) {
										
										const grid = $$(self.dataTable.getViewId())
			
										if (grid) {
										
											webix.extend(grid, webix.ProgressBar);
			
											grid.showProgress({
								
												hide: false, type: "bottom"
											});
											
											const keys = Object.keys(values);
									
											let row = {};
											let rowData = [];
		
											if (Array.isArray(keys) && keys.length > 0) {
												
												keys.forEach(key => {
																									
													row[key] = (values[key] ? values[key] : undefined);
													
													if (key !== "id") {
														
														if (self.currentTableData.id == BackOfficeHelper.getTableIdFromColumnUID(key)) {
														
															const columnId = parseInt(BackOfficeHelper.getColumnIdFromColumnUID(key));
															
															rowData.push({
																columnId: columnId,
																value: row[key]
															});
														}	
													}
													else {
														
														rowData.push({
															columnId: columnIdentify.id,
															value: row[key]
														});
													}
												});
											}
											
											if (Array.isArray(rowData) && rowData.length > 0) {
												
												let indexRow = self.currentTableData.rows.findIndex(row => row.data.columnId == columnIdentify.id);
											
												if (indexRow > -1) {
													
													self.currentTableData.rows.splice(indexRow, 1);
												}
												
												self.currentTableData.rows.push({
													
													data: rowData
												});	
											}
											
											if (self.dataTable.getItem(id)) {
												
												self.dataTable.update(row);	
											}
											else {
												
												self.dataTable.add(row);
											}
											
											self.dataTable.select(id);
											
											grid.hideProgress();
										}
									}
								}								
							}
						}
					}
					
					return events;	
				}
				
				return new DataTable(null, null, getMetadata(), getData(), getEvents());;
			}

			return viewDataTable;
		}
		
		return view;
	}
	
	refresh() {
		
		let self = this;
		
		if (self.dataTable) {
			
			const grid = $$(self.dataTable.getViewId())
			
			if (grid) {
				
				self.dataTable.executeCommand(BackOfficeViewGrid.GRID_COMMANDS.refresh)	
			}
		}
	}
}
