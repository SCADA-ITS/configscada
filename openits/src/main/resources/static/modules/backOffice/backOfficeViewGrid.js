import BackOfficeModule from "./backOfficeModule.js";
import BackOfficeView from "./backOfficeView.js";
import BackOfficeViewGridFilter from "./backOfficeViewGridFilter.js";
import BackOfficeHelper from "./backOfficeHelper.js";
import DataTable from "../../component/datatable.js";
import MessageBox from "../../component/messageBox.js";
import { util } from "../../lib/util.js";

const REST_BACKOFFICE_UPLOAD_CSV = "backoffice/${backOfficeId}/table/${tableName}/import";

export default class BackOfficeViewGrid extends BackOfficeView {
	
	static get GRID_COMMANDS() { return { refresh: 1, filter: 2, new: 3, edit: 4, delete: 5, import: 6} }

	constructor(backOffice, data, currentTableData) {

		super(backOffice, data, currentTableData);
		
		this.dataTable = undefined;
		this.formHeight = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_HEIGHT, backOffice.metadata, currentTableData);
		this.formWidth = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_WIDTH, backOffice.metadata, currentTableData);
		this.gridFilters = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.GRID_FILTERS, backOffice.metadata, currentTableData);
		this.gridTableNames = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.GRID_TABLES, backOffice.metadata, currentTableData);
		this.gridTableColsOrientation = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.GRID_TABLES_COLS_ORIENTATION, backOffice.metadata, currentTableData);
		this.referTablesCounter = !backOffice.referTablesCounter ? 2 : backOffice.referTablesCounter;  
		
		
		if (backOffice.containerId) {
			
			let windowTitle = util.getStringCapitalized(currentTableData.label ? BackOfficeHelper.translate(backOffice, currentTableData.label) : currentTableData.name);
			
			if (Array.isArray(backOffice.filters) && backOffice.filters.length > 0) {
				
				windowTitle += " (" + i18n.t("LBL_DATATABLE_FILTERED") + ")";
			}
			
			util.ui.centerWindow(backOffice.containerId, backOffice.isModal ? 20: 0);
			util.ui.setTitle(backOffice.containerId, windowTitle);	
		}
		
		this.refreshInterval = undefined;
		this.selectedRefreshTime = currentTableData.metadata && currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH.name] && currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH.name] > 0 ? currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH.name] : undefined;
		this.selectedId = undefined;
		this.newId = undefined;
		this.auxWin= undefined;
	}
	
	getView() {
		
		let self = this;
		let view = getDataTableView();
		
		function getDataTableView() {

			let viewDataTable;
			
			self.dataTable = getDataTable();
			
			if (self.dataTable) {
				
				let dataTableContent = self.dataTable.getView(); 
				
				viewDataTable = {
					cols: function() {
					
						let cols = [];
						
						cols.push({
							id: "loading-backOffice-view-" + self.uid,
							hidden: true,
							rows: [util.ui.getViewDrawLoading()]
						});
						
						let viewContent = { id: "content-backOffice-view-" + self.uid };
						
						viewContent[!self.gridTableColsOrientation ? "rows" : "cols"] = [
							{
								id: "backOffice-view-" + self.uid,
								rows: dataTableContent
							},
							{
								id: "backOffice-view-resizer-" + self.uid,
								view: "resizer",
								hidden: true,
							},
							{
								id: "backOffice-view-refer-tables-" + self.uid,
								hidden: true,
							}		
						];
						
						cols.push(viewContent);
						
						return cols;
					}(),
					on: {
						onDestruct: function() {
	
							if (self.refreshInterval) {
								
								clearInterval(self.refreshInterval);
							}
						}
					}
				};
				
				viewDataTable.cols.push({
					view: "template",
					width: 1,
					borderless: true,
					on: {
						onAfterRender: function() {
		
							self.dataTable.callbacks();
							
							if (self.currentTableData.metadata && self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH.name] && self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH.name] > 0) {
												
								$$("slider-" + self.uid).setValue(self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH.name]);
							}
							
							setTimeout(function() {
									
								if (self.referTablesCounter > 0 && Array.isArray(self.gridTableNames) && self.gridTableNames.length > 0) {
								
									self.dataTable.selectByIndex(0);
								}						

							}, 10);
						},
					}	
				});	
				
				if (self.currentTableData.metadata && self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH.name] && self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH.name] > 0) {
					
					viewDataTable.cols[1].rows.push({
						height: 55,
						cols: [
							{},
							{
								id: "slider-" + self.uid,
								view: "slider",
								value: 0,
								label: i18n.t("LBL_UPDATE_TIME"),
								labelWidth: 175,
								gravity: 0.5,
								min: 0,
								max: 300,
								step: 5,
								title: webix.template("#value# secs"),
								hidden: !self.backOffice.moduleId,
								on: {
									onChange: function(newv) {
		
										self.selectedRefreshTime = newv;
										timerRefresh();
									}
								}
							},
							{ width: 30 }
						]  
					});
				}
			}
			
			function timerRefresh() {
				
				if (self.refreshInterval) {
						
					clearInterval(self.refreshInterval);
				}
				
				if (self.backOffice.moduleId && self.currentTableData.metadata && self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH.name] && 
					!isNaN(self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH.name])) {
						
					if (self.selectedRefreshTime > 0) {
						
						self.refreshInterval = setInterval(refreshGrid, self.selectedRefreshTime * 1000);	
					}
				}	
			}
			
			function refreshGrid() {
					
				if (self.backOffice.filtersRequired && 
					((Array.isArray(self.backOffice.filters) && self.backOffice.filters.length === 0) || !self.backOffice.filters)) {

					(new MessageBox(MessageBox.WARN, i18n.t("LBL_BACKOFFICE_REQUIRED_FILTERS_TO_REFRESH"))).show();												
				}
				else {
					
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
								
								if (!isNaN(self.newId)) {
									
									self.dataTable.select(self.newId);
									
									self.newId = undefined;	
								}
								
								grid.hideProgress();								
							});	
						}
					}
				}
			}
			
			function cmdFilterGrid() {
				
				let backOfficeViewGridFilter = new BackOfficeViewGridFilter(self.backOffice, self.data, self.currentTableData, onAccept, onCancel);
				
				const winId = util.ui.loadWindow(i18n.t("LBL_VIEW_GRID_FILTER_TITLE"), backOfficeViewGridFilter.getView(), 
									BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_HEIGHT.default, 
									BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_WIDTH.default, true);
				
				function onAccept(filters, save) {
					
					self.backOffice.filters = filters;
					
					$$(winId).close();
					
					if (save && self.backOffice.moduleId) {
						
						const module = window.em.getByField(EntityType.MODULE, "id", self.backOffice.moduleId);

						if (module) {
							
							const moduleParam = module.moduleType.moduleParams.find(e => e.id == MODULE_PARAM_TYPE_GENERIC_BACKOFFICE_WINDOW_FILTERS);
							
							if (moduleParam) {
								
								const moduleValueFilters = module.moduleValues.find(e => e.moduleParam.id === moduleParam.id); 
			
								let currentFilters = [];
								
								if (moduleValueFilters && moduleValueFilters.value) {
									
									currentFilters = JSON.parse(moduleValueFilters.value);
								}
								
								let newFilters = currentFilters.filter(e => e.userId != util.user.getUserData().id);
									
								newFilters.push(...filters);
								
								const moduleValue = {
									
									id: moduleValueFilters ? moduleValueFilters.id : undefined,
									moduleParam: moduleParam,
									module: module,
									value: JSON.stringify(newFilters)
								}
								
								util.module.saveModuleValue(moduleValue).then(updModuleValue => {
									
									module.moduleValues = module.moduleValues.filter(e => e.moduleParam.id != moduleParam.id);
									module.moduleValues.push(updModuleValue);
								});
								
								let windowTitle = util.getStringCapitalized(self.currentTableData.label ?? self.currentTableData.name);
			
								if (Array.isArray(self.backOffice.filters) && self.backOffice.filters.length > 0) {
									
									windowTitle += " (" + i18n.t("LBL_DATATABLE_FILTERED") + ")";
								}
								
								util.ui.setTitle(self.backOffice.containerId, windowTitle);
							}
						}	
					}
					
					refreshGrid();
				}
				
				function onCancel() {
					
					$$(winId).close();
				}
			}
			
			function cmdImport() {

				uploaderDestructor();

				function uploaderDestructor() {

					if ($$("uploader-container-" + self.uid)) {

						$$("uploader-container-" + self.uid).destructor();
					}
				}

				webix.ui({
					id: "uploader-container-" + self.uid,
					height: 1,
					rows: [
						{
							view: "uploader",
							id: "uploader-" + self.uid,
							height: 1,
							accept: "text/csv",
							autosend: true,
							multiple: false,
							apiOnly: true,
							on: {
								onBeforeFileAdd: function(item) {

									let res = true;

									if (item.type === "csv") {

										this.config.upload = appConfig.restUrl + util.fillTemplate(REST_BACKOFFICE_UPLOAD_CSV, { backOfficeId: self.backOffice.id, tableName: self.currentTableData.name });

									} else {

										res = false;

										let suportedErrorMessage = new MessageBox(MessageBox.ERROR, i18n.t("LBL_UNSUPPORTED_FILE_TYPE"), null, uploaderDestructor, uploaderDestructor);
										suportedErrorMessage.show();
									}

									return res;
								},
								onFileUpload: function(item) {

									if (item.requestResultMessage.error != "") {

										let options = {
											textMultiline: item.requestResultMessage.error
										};

										let errorMessage = new MessageBox(MessageBox.ERROR, i18n.t("LBL_UPLOADING_ERROR"), options, uploaderDestructor, uploaderDestructor);
										errorMessage.show();

									} else if (item.requestResultMessage.info === "success") {

										let msg = i18n.t("LBL_DATA_LOADED_SUCCESSFULLY") + ": " + item.requestResultMessage.infoDescription + " " + i18n.t("LBL_UPDATED_RECORDS");
										let successMessage = new MessageBox(MessageBox.INFO, msg, null, uploaderDestructor, uploaderDestructor);
										successMessage.show();

										self.refresh();
									}
								},
								onFileUploadError: function() {

									let options = {
										textMultiline: i18n.t("LBL_UNSPECIFIED_ERROR")
									};

									let errorMessage = new MessageBox(MessageBox.ERROR, i18n.t("LBL_UPLOADING_ERROR"), options, uploaderDestructor, uploaderDestructor);
									errorMessage.show();
								}
							}
						}
					]
				});

				$$("uploader-" + self.uid).fileDialog();
			}
			
			
			function getRecord(row) {
				
				let record = {};
				
				if (Array.isArray(row.data) && row.data.length > 0) {
						
					row.data.forEach(rowData => {
					
						const column = self.currentTableData.columns.find(col => col.id === rowData.columnId);
						
						if (column && column.name) {
							
							let filterColumnName = undefined;
				
							if (self.backOffice.filterColumn && self.backOffice.filterColumnValue) {
								
								filterColumnName = self.backOffice.filterColumn.name;
							}
								
							if (!filterColumnName || column.name != filterColumnName) {
								
								const referencedColumnValue = BackOfficeHelper.getReferencedColumnValue(self.backOffice, column, rowData.value, self.data);
								
								let value = rowData.value;
								
								if (column.colType === BackOfficeHelper.DB_TYPE_TIMESTAMP && !isNaN(value)) {
									
									value = new Date(parseInt(value));
								}
							
								if (referencedColumnValue) {
									
									let referencedValue = referencedColumnValue.value;
									
									if (referencedColumnValue.colType === BackOfficeHelper.DB_TYPE_TIMESTAMP && !isNaN(referencedValue)) {
									
										referencedValue = new Date(parsetInt(referencedColumnValue.value));												
									}
									
									record[BackOfficeHelper.getColumnUID(column) + "$ref"] = referencedValue ?? value;
									
									const virtualColumns = BackOfficeHelper.getVirtualColumns(self.backOffice, self.currentTableData, column, self.data);
				
									if (Array.isArray(virtualColumns) && virtualColumns.length > 0) {
										
										virtualColumns.sort((a, b) => (a.virtualColumnMetadata.position > b.virtualColumnMetadata.position) ? 1 : ((b.virtualColumnMetadata.position > a.virtualColumnMetadata.position) ? -1 : 0));
										
										virtualColumns.forEach(virtualColumn => {
											
											const virtualColumnValue = BackOfficeHelper.getVirtualColumnValue(virtualColumn, rowData.value, self.data);
											
											if (virtualColumnValue) {
												
												record[BackOfficeHelper.getColumnUID(virtualColumn) + "$ref"] = virtualColumnValue.value; 	
											}
										});
									}
								}
								else {
									
									record[column.primaryKey ? "id" : BackOfficeHelper.getColumnUID(column)] = value;	
								}	
							}
						} 
					});
				}
				
				return record;
			}
			
			function getData() {
			
				let data = [];
				
				if (Array.isArray(self.currentTableData.rows) && self.currentTableData.rows.length > 0) {
					
					self.currentTableData.rows.forEach(row => {
				
						data.push(getRecord(row));
							
					});
				}
				
				return data;	
			}
			
			function getDataTable() {
				
				let firstCommandIdCalculated = Object.keys(BackOfficeViewGrid.GRID_COMMANDS).length;
				const editable = !self.currentTableData.sqlView && BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.EDITABLE, self.backOffice.metadata, self.currentTableData);
				const importable = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.IMPORTABLE, self.backOffice.metadata, self.currentTableData);					
					
				function getMetadata() {

					let icon;
					let withIcon = (self.currentTableData.mdiIcon) ? "true" : "false";

					const printable = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.PRINTABLE, self.backOffice.metadata, self.currentTableData);
					const exportable = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.EXPORTABLE, self.backOffice.metadata, self.currentTableData);
					
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
						{ gridOption: { id: 25 }, value: printable },
						{ gridOption: { id: 26 }, value: exportable }
					];

					if (IS_TRUE(withIcon)) {

						icon = self.currentTableData.mdiIcon;
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
						
						if (self.currentTableData.grid) {
							
							assignCustomGrid();
						}
						else {
							
							assignDefaultGrid();
						}
					}
					
					metadata.gridCommands.push({
						id: BackOfficeViewGrid.GRID_COMMANDS.refresh,
						default_: false,
						icon: "mdi mdi-refresh",
						label: "LBL_TABLE_REFRESH",
						multiselect: false,
						position: BackOfficeViewGrid.GRID_COMMANDS.refresh,
						showText: false,
						itemRequired: false
					});
					
					
					if (editable) {
						
						metadata.gridCommands.push({
							id: BackOfficeViewGrid.GRID_COMMANDS.new,
							default_: false,
							icon: "mdi mdi-plus",
							label: "LBL_CMD_ADD",
							multiselect: false,
							position: BackOfficeViewGrid.GRID_COMMANDS.new,
							showText: false,
							unselectAllAfter: true,
							itemRequired: false
						});	
					}

					metadata.gridCommands.push({
						id: BackOfficeViewGrid.GRID_COMMANDS.edit,
						default_: true,
						icon: editable ? "mdi mdi-pencil" : "mdi mdi-eye",
						label: editable ? "LBL_CMD_EDIT" : "LBL_CMD_VIEW",
						multiselect: false,
						position: BackOfficeViewGrid.GRID_COMMANDS.edit,
						showText: false,
						itemRequired: true
					});
					
					if (editable) {
						
						metadata.gridCommands.push({
							id: BackOfficeViewGrid.GRID_COMMANDS.delete,
							default_: false,
							icon: "mdi mdi-delete",
							label: "LBL_CMD_DELETE",
							multiselect: true,
							position: BackOfficeViewGrid.GRID_COMMANDS.delete,
							showText: false,
							itemRequired: true
						});
					}	
					
					if (!self.backOffice.filterColumn) {
					
						metadata.gridCommands.push(
							{
								id: BackOfficeViewGrid.GRID_COMMANDS.filter,
								default_: false,
								icon: "mdi mdi-filter",
								label: "LBL_TABLE_FILTER",
								multiselect: false,
								position: BackOfficeViewGrid.GRID_COMMANDS.filter,
								showText: true,
								itemRequired: false
							}
						);
					}
					
					if (importable) {

						metadata.gridCommands.push({												
							id: BackOfficeViewGrid.GRID_COMMANDS.import,
							default_: false,
							icon: "mdi mdi-file-import",
							label: "LBL_TABLE_IMPORT",
							multiselect: false,
 							position: BackOfficeViewGrid.GRID_COMMANDS.import,
							showText: false,
							itemRequired: false
						});
					}

					if (Array.isArray(self.currentTableData.commands) && self.currentTableData.commands.length > 0) {

						const commandButtons = getCommandButtons();

						if (Array.isArray(commandButtons) && commandButtons.length > 0) {

							metadata.gridCommands.push(...commandButtons);
						}
					}
					
					function assignCustomGrid() {
						
						let gridMetadata = webix.copy(self.currentTableData.grid);
											
						if (Array.isArray(gridMetadata.gridCommands)) {
							
							metadata.gridCommands.push(...gridMetadata.gridCommands);
						}
						
						for (let gridField of gridMetadata.gridFields) {
							
							const column = self.currentTableData.columns.find(e => e.name == gridField.sourceField);
							
							if (column) {
							
								const referencedColumnName = BackOfficeHelper.getReferencedColumnName(self.backOffice, column, self.data);
								const sourceField = referencedColumnName ? (BackOfficeHelper.getColumnUID(column) + "$ref") : (column.primaryKey === true ? "id" : BackOfficeHelper.getColumnUID(column));
								
								gridField.sourceField = sourceField;
								gridField.label = util.getStringCapitalized(BackOfficeHelper.translate(self.backOffice, gridField.label));
								
								metadata.gridFields.push(gridField);	
							}
						}
					}
					
					function assignDefaultGrid() {
						
						self.currentTableData.columns.forEach(column => {

							let pos = (column.metadata) ? column.metadata[BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.POSITION.name] : undefined;
	
							if (pos != undefined) {
	
								column.position = pos;
							}
						});
	
						self.currentTableData.columns.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
	
						let gridFields = [];
						
						let idxField = 1;
	
						self.currentTableData.columns.forEach(column => {
	
							const gridField = getGridField(column, idxField++);
							
							if (gridField) {
								
								gridFields.push(gridField);
								
								if (column.refColumn) {
									
									const virtualColumns = BackOfficeHelper.getVirtualColumns(self.backOffice, self.currentTableData, column, self.data);
						
									if (Array.isArray(virtualColumns) && virtualColumns.length > 0) {
										
										virtualColumns.sort((a, b) => (a.virtualColumnMetadata.position > b.virtualColumnMetadata.position) ? 1 : ((b.virtualColumnMetadata.position > a.virtualColumnMetadata.position) ? -1 : 0));
										
										virtualColumns.forEach(virtualColumn => {
											
											const gridVirtualField = getGridVirtualField(virtualColumn, idxField++)
											
											if (gridVirtualField) {
									
												gridFields.push(gridVirtualField);				
											}
										});
									}
	
								}
							}
						});
	
						metadata.gridFields = gridFields;
					}

					function getCommandButtons() {
						
						let lastCommandIdValue = firstCommandIdCalculated;
						
						let commandButtons = [];

						if (Array.isArray(self.currentTableData.commands) && self.currentTableData.commands.length > 0) {

							const commandsAvailable = self.currentTableData.commands.filter(e => e.availableInTable);

							commandsAvailable.forEach(cmd => {

								commandButtons.push(getCommand(cmd));
							});
						}

						function getCommand(cmd) {

							lastCommandIdValue++;

							return {
								id: lastCommandIdValue,
								icon: cmd.mdiIcon,
								label: cmd.label ?? cmd.name,
								multiselect: false,
								position: lastCommandIdValue,
								showText: true,
								itemRequired: true,
								grouped: true
							}
						}

						return commandButtons;
					}
					
					function getGridFieldDataType(dataTypeId) {
						
						let gridFieldDataTypeId = undefined;
							
						if (dataTypeId == BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_STRING).id ||
						    dataTypeId == BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_BOOLEAN).id) {
							
							gridFieldDataTypeId = 1;
						}
						else if (dataTypeId == BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_SHORT).id ||
								 dataTypeId == BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_INTEGER).id ||
								 dataTypeId == BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_LONG).id) {
									
							gridFieldDataTypeId = 2;
						}
						else if (dataTypeId == BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_DECIMAL).id) {
									
							gridFieldDataTypeId = 3;
						}
						else if (dataTypeId == BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_TIMESTAMP).id) {
									
							gridFieldDataTypeId = 4;
						}
						
						return gridFieldDataTypeId;
					}
					
					function assignGridFieldIcons(gridField) {
						
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
					
					function getGridField(column, idxField) {
						
						let gridField = undefined;
						const visibleColumn = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_COLUMN, BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.TABLE_VISIBLE, self.backOffice.metadata, column);
						const showIdTable = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.SHOW_ID_TABLE,  self.backOffice.metadata, self.currentTableData);

						let filterColumnName = undefined;

						if (self.backOffice.filterColumn && self.backOffice.filterColumnValue) {

							filterColumnName = self.backOffice.filterColumn.name;
						}

						if (!filterColumnName || column.name != filterColumnName) {

							const referencedColumnName = BackOfficeHelper.getReferencedColumnName(self.backOffice, column, self.data);
							const sourceField = referencedColumnName ? (BackOfficeHelper.getColumnUID(column) + "$ref") : BackOfficeHelper.getColumnUID(column);

							const dataTypeId = BackOfficeHelper.getDBTypes(referencedColumnName ? referencedColumnName.colType : column.colType).id;
							
							let label = column.label ?? column.name;

							if (referencedColumnName) {

								const table = self.data.find(e => e.id === referencedColumnName.tableId);

								if (table) {

									label = table.labelSingular ?? (table.label ?? table.name);
								}
							}

							const gridFiltersColumn = (column.metadata) ? column.metadata[BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.GRID_FILTERS.name] : undefined;
							const gridFilter = (gridFiltersColumn != undefined) ? gridFiltersColumn : self.gridFilters;
							
							gridField = {
								id: idxField,
								extended: false,
								filtered: gridFilter,
								isEpochMilli: dataTypeId == BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_TIMESTAMP).id,
								isPk: column.primaryKey === true,
								needsTranslation: false,
								parameterized: referencedColumnName ? true : false,
								position: idxField,
								required: true,
								label: util.getStringCapitalized(label),
								sourceField: column.primaryKey ? "id" : sourceField,
								visible: !column.primaryKey ? visibleColumn : (visibleColumn && showIdTable),
								gridFieldDataType: {
									id: getGridFieldDataType(dataTypeId)
								}
							};

							if (dataTypeId == BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_BOOLEAN).id) {

								assignGridFieldIcons(gridField);
							}
						}
						
						return gridField;
					}
					
					function getGridVirtualField(virtualColumn, idxField) {
						
						let gridVirtualField = undefined;
						
						const dataTypeId = BackOfficeHelper.getDBTypes(virtualColumn.colType).id;
						const sourceField = BackOfficeHelper.getColumnUID(virtualColumn) + "$ref";
						const label = virtualColumn.label ?? virtualColumn.name;
						
						gridVirtualField = {
							id: idxField,
							extended: false,
							filtered: true,
							isEpochMilli: dataTypeId == BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_TIMESTAMP).id,
							isPk: false,
							needsTranslation: false,
							parameterized: true,
							position: idxField,
							required: true,
							label: util.getStringCapitalized(label),
							sourceField: sourceField,
							visible: virtualColumn.virtualColumnMetadata.tableVisible === true,
							gridFieldDataType: {
								id: getGridFieldDataType(dataTypeId)
							}
						};
						
						return gridVirtualField;
					}

					return metadata;
				}
				
				function getEvents() {
					
					let commandIdValue = firstCommandIdCalculated;
					let events = {};
					
					events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

						if (Array.isArray(rows) && rows.length > 0) {

							// log.debug(rows[0]);
							
							self.selectedId = rows[0];
							
							if (self.referTablesCounter > 0 && Array.isArray(self.gridTableNames) && self.gridTableNames.length > 0) {
								
								setTimeout(function() {
									
									$$("backOffice-view-resizer-" + self.uid).show();

									const backOfficeViewReferTablesLoading = {
										id: "backOffice-view-refer-tables-" + self.uid,
										rows: [util.ui.getViewDrawLoading()]
									}
	
									webix.ui(backOfficeViewReferTablesLoading, $$("backOffice-view-refer-tables-" + self.uid));
									
									$$("content-backOffice-view-" + self.uid).resize();
									
									drawReferTables();						
	
								}, 10);
							}
						}
					};	
		
					events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
		
					events[DataTable.EventTypes.ON_COMMAND_EXEC].push(
						{ commandId: BackOfficeViewGrid.GRID_COMMANDS.refresh, function: refreshGrid },
						{ commandId: BackOfficeViewGrid.GRID_COMMANDS.edit, function: cmdEdit }
						
					);
					
					if (editable) {
						
						events[DataTable.EventTypes.ON_COMMAND_EXEC].push(
							{ commandId: BackOfficeViewGrid.GRID_COMMANDS.new, function: cmdNew },
							{ commandId: BackOfficeViewGrid.GRID_COMMANDS.delete, function: cmdDelete }	
						);
					}			
					
					if (!self.backOffice.filterColumn) {
						
						events[DataTable.EventTypes.ON_COMMAND_EXEC].push(
							{ commandId: BackOfficeViewGrid.GRID_COMMANDS.filter, function: cmdFilterGrid }
						);
					}
					
					if (importable) {

						events[DataTable.EventTypes.ON_COMMAND_EXEC].push(
							{ commandId: BackOfficeViewGrid.GRID_COMMANDS.import, function: cmdImport }
						);
					}						
					
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
									
									const columnId = self.currentTableData.columns.find(e => e.primaryKey === true);
									
									if (columnId) {
										
										const id = items[0][columnId.name];
									
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
							}
						});	

						return commandButtons;
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
									
									const columnId = self.currentTableData.columns.find(e => e.primaryKey === true);
									
									if (columnId) {
										
										const data = requestResult.newRow.data.find(e => e.columnId === columnId.id);
										
										if (data && data.value) {
											
											const columnIdValue = data.value;
								
											edit(columnIdValue, function () {
							
												BackOfficeHelper.deleteData(self.backOffice, self.currentTableData, columnIdValue).then((requestResult) => {
													
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
							
							(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_DELETE_CONFIRM") + " " + (self.currentTableData.label ?? self.currentTableData.name) + "?", null, onAccept , null)).show();
							
							function onAccept() {
								
								let recordsToDelete = ids.length;
								let error = "";
								const columnId = self.currentTableData.columns.find(c => c.primaryKey === true);
								
								let deletedRecords = 0;
								
								while (recordsToDelete > 0) {
									
									recordsToDelete--;
									
									BackOfficeHelper.deleteData(self.backOffice, self.currentTableData, ids[recordsToDelete]).then((requestResult) => {
										
										if (requestResult.requestResultMessage.error) {
		
											error += "\nError id: "+ ids[recordsToDelete] + " => " +  requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription;
										}
										
										if (++deletedRecords === ids.length) {
											
											if (error) {
												
												(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_DELETING_RECORDS") + " " + (self.currentTableData.label ?? self.currentTableData.name), error, null, null)).show();				
											}
											else {
												
												onFinishDelete();
											}
										}
									});	
								}
								
								function onFinishDelete() {
						
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
							}
						}
					}
					
					function edit(id, onCancel) {
						
						const module = window.em.getByField(EntityType.MODULE, "id", BackOfficeModule.Config.MODULE_ID);

						if (module) {
							
							const winId = util.ui.loadWindow(undefined, { id: self.uid }, self.formHeight, self.formWidth, true);
							
							const columnId = self.currentTableData.columns.find(e => e.primaryKey === true);
							
							if (columnId) {
							
								const args = {
									backOffice: {
										id: self.backOffice.id,
										tableName: self.currentTableData.name,
										filterColumnName: columnId.name,
										filterColumnValue: id,
										isModal: true,
										onAccept: onAccept,
										onCancel: onCancel,
										filterColumn: self.backOffice.filterColumn
									},
									containerId: winId,
									onLoad: onLoad
								}
		
								util.module.open(module, {id: BackOfficeModule.Config.MODULE_ACTION_OPEN }, VIEW_TYPE_CUSTOM, drawContent, args);	
							}
							
							
						
							function drawContent(view) {
	
								webix.ui(view, $$(self.uid));
							}
							
							function onLoad() {
								
								util.ui.resizeWindow(winId, self.formWidth, self.formHeight);
								util.ui.centerWindow(winId);
							}
							
							function onAccept(newRow) {
								
								if (newRow && Array.isArray(newRow.data)) {
									
									const newRowData = newRow.data.find(e => e.columnId === columnId.id);
	
									if (newRowData && !isNaN(newRowData.value) && self.dataTable) {
										
										self.newId = parseInt(newRowData.value);
										
										self.refresh(); 
									}
								}
							}
						}
					}
					
					return events;	
				}			
				
				return new DataTable(null, null, getMetadata(), getData(), getEvents());	
			}

			return viewDataTable;
		}
		
		function drawReferTables() {
				
			if (self.selectedId && Array.isArray(self.currentTableData.referTableNames) && 
				Array.isArray(self.gridTableNames) && self.gridTableNames.length > 0 && 
				self.gridTableNames.length <= self.currentTableData.referTableNames.length) {
					
				let viewBackOfficeViewGridCells = [];
				
				let referTablesCounter = 0;
				
				self.gridTableNames.forEach(gridTableName => {
					
					const module = window.em.getByField(EntityType.MODULE, "id", BackOfficeModule.Config.MODULE_ID);

					if (module) {
						
						const refTable = self.data.find(e => e.name === gridTableName);
						
						if (refTable) {
							
							const refTableColumn = refTable.columns.find(column => column.refColumn && column.refColumn.tableId === self.currentTableData.id)
							
							if (refTableColumn) {
								
								const args = {
									backOffice: {
										id: self.backOffice.id,
										tableName: gridTableName,
										filterColumnName: refTableColumn.name,
										filterColumnValue: self.selectedId,
										filterColumn: refTableColumn,
										referTablesCounter: self.referTablesCounter - 1
									},
									onLoad: onLoad
								}
		
								util.module.open(module, {id: BackOfficeModule.Config.MODULE_ACTION_OPEN }, VIEW_TYPE_CUSTOM, drawContent, args);
							}
							
							function drawContent(view) {
							
								viewBackOfficeViewGridCells.push({
									header: refTable.label,
									body: view
								});
								
								if (self.gridTableNames.length == ++referTablesCounter) {
					
									if (Array.isArray(viewBackOfficeViewGridCells) && viewBackOfficeViewGridCells.length > 0) {
								
										const tabs = {
											view: "tabview",
											id: "tab-refer-tables-" + self.uid,
											borderless: true,
											css: "auxiliary-tab",
											cells: viewBackOfficeViewGridCells,
											tabbar: {
												tabMargin: 6
											}
										}
										
										const backOfficeViewReferTablesLoading = {
											id: "backOffice-view-refer-tables-" + self.uid,
											rows: [tabs]
										}
										
										webix.ui(backOfficeViewReferTablesLoading, $$("backOffice-view-refer-tables-" + self.uid));
									}
								}
							}
							
							function onLoad() {
						
							}
						}
					}
				});
			}
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
