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
				
				function getMetadata() {
					
					const defaultGridOptionValues = [
						{ gridOption: { id: 1 }, value: "true" },
	//					{ gridOption: { id: 2 }, value: "true" },
	//					{ gridOption: { id: 3 }, value: "2" },
	//					{ gridOption: { id: 4 }, value: "asc" },
						{ gridOption: { id: 7 }, value: "false" },
						{ gridOption: { id: 8 }, value: "false" },
						{ gridOption: { id: 9 }, value: "true" },
						{ gridOption: { id: 10 }, value: "true" },
						{ gridOption: { id: 11 }, value: "true" },
						{ gridOption: { id: 12 }, value: "true" },
						{ gridOption: { id: 13 }, value: "true" },
						{ gridOption: { id: 14 }, value: "true" },
						{ gridOption: { id: 21 }, value: "auto" },
						{ gridOption: { id: 23 }, value: "true" },
						{ gridOption: { id: 25 }, value: "true" }
					];
		
					let metadata = {
						filter: true,
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
								
								const dataTypeId = BackOfficeHelper.getDBTypes(referencedColumnName ? referencedColumnName.colType : column.colType).id;
								
								let label = column.alias ?? column.name;
								
								if (referencedColumnName) {
									
									const table = self.data.find(e => e.id === referencedColumnName.tableId);
									
									if (table) {
										
										label = table.labelSingular ?? (table.label ?? table.name);
									}
								}
		
								gridFields.push({
									id: idxField,
									extended: false,
									filtered: false,
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
								});
								
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
					
					return metadata;
				}
				
				function getEvents() {
				
					let events = {};
		
					events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
		
					events[DataTable.EventTypes.ON_COMMAND_EXEC].push(
						
						{ commandId: BackOfficeViewGrid.GRID_COMMANDS.refresh, function: cmdRefresh },
						{ commandId: BackOfficeViewGrid.GRID_COMMANDS.new, function: cmdNew },
						{ commandId: BackOfficeViewGrid.GRID_COMMANDS.edit, function: cmdEdit },
						{ commandId: BackOfficeViewGrid.GRID_COMMANDS.delete, function: cmdDelete }
					);
					
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
						
						BackOfficeHelper.insertData(self.backOffice, self.currentTableData, persistValues).then((persistResult) => {
										
							if (!persistResult.error) {
								
								if (persistResult.newRow && Array.isArray(persistResult.newRow.data)) {
									
									const columnId = self.currentTableData.columns.find(e => e.name === "id");
									
									if (columnId) {
										
										const data = persistResult.newRow.data.find(e => e.columnId === columnId.id);
										
										if (data && data.value) {
											
											const columnIdValue = data.value;
								
											edit(columnIdValue, function () {
							
												BackOfficeHelper.deleteData(self.backOffice, self.currentTableData, persistValues, columnIdValue).then((persistResult) => {
													
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
					
					function cmdEdit(ids) {
						
						if (Array.isArray(ids) && ids.length > 0) {
						
							edit(ids[0]);
						}
					}
					
					function cmdDelete(ids) {
						
						if (Array.isArray(ids) && ids.length > 0) {
							
							(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_DELETE_CONFIRM") + " " + (self.currentTableData.alias ?? self.currentTableData.name), null, onAccept , null)).show();
							
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
										
										BackOfficeHelper.deleteData(self.backOffice, self.currentTableData, persistValues, ids[recordsToDelete]).then((persistResult) => {
											
											if (persistResult.error) {
			
												error += "\nError id: "+ ids[recordsToDelete] + " => " +  persistResult.error + "\n" + persistResult.errorDescription;
											}
											
											if (++deletedRecords === ids.length) {
												
												if (error) {
													
													(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_DELETING_RECORDS") + " " + (self.currentTableData.alias ?? self.currentTableData.name), error, null, null)).show();				
												}
												else {
													
													ids.forEach(id => {
														
														const itemToDelete = self.dataTable.getItem(id);
												
														if (itemToDelete) {
															
															self.dataTable.delete(itemToDelete);
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
							
							const winId = util.ui.loadWindow(undefined, { id: self.uid }, BackOfficeModule.Config.DEFAULT_FORM_HEIGHT, 
																BackOfficeModule.Config.DEFAULT_FORM_WIDTH, true);
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
								
								util.ui.resizeWindow(winId, BackOfficeModule.Config.DEFAULT_FORM_WIDTH, BackOfficeModule.Config.DEFAULT_FORM_HEIGHT);
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
									
									if (id) {
										
										const grid = $$(self.dataTable.getViewId())
			
										if (grid) {
										
											webix.extend(grid, webix.ProgressBar);
			
											grid.showProgress({
								
												hide: false, type: "bottom"
											});
											
											const keys = Object.keys(values);
									
											let row = {};
		
											if (Array.isArray(keys) && keys.length > 0) {
												
												keys.forEach(key => {
													
													row[key] = (values[key] ? values[key] : undefined);
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
