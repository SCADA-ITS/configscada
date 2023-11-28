import DataTable from "../../component/datatable.js";
import MessageBox from "../../component/messageBox.js";
import BackOfficeHelper from "./backOfficeHelper.js";
import { util } from "../../lib/util.js";

export default class BackOfficeViewGridFilter {
	
	static get OPERATOR() { 
		return { 
			EQUAL: { id: 1, label: i18n.t("LBL_EQUAL"), operator: "=", onlyAlphanumeric: true },
			NOT_EQUAL: { id: 2, label: i18n.t("LBL_NOT_EQUAL"), operator: "<>", onlyAlphanumeric: true },
			GREATER: { id: 3, label: i18n.t("LBL_GREATER"), operator: ">" },
			GREATER_OR_EQUAL: { id: 4, label: i18n.t("LBL_GREATER_OR_EQUAL"), operator: ">=" },
			LESS: { id: 5, label: i18n.t("LBL_LESS"), operator: "<" },
			LESS_OR_EQUAL: { id: 6, label: i18n.t("LBL_LESS_OR_EQUAL"), operator: "<=" } 
		} 
	}
	
	constructor(backOffice, data, currentTableData, onAccept, onCancel) {
		
		this.backOffice = backOffice;
		this.data = data;
		this.currentTableData = currentTableData;
		this.onAccept = onAccept;
		this.onCancel = onCancel;
		this.uid = webix.uid();
		this.dtFilter = undefined;
		this.oldFilters = webix.copy(backOffice.filters);
		this.currentFilters = webix.copy(backOffice.filters);
	}
	
	getView() {
		
		let self = this;
		let view;
		
		view = {
			
			rows: [
				
				getHeaderView(),
				{
					cols: [
						{ width: 15 },
						{
							rows: [
								{ height: 5 },								
								getContentView(),
								{ height: 15 }
							]
						},
						{ width: 15 }
					]
				},
				getFooterView(),
				{ height: 15 },
				{
					view: "template",
					height: 1,
					borderless: true,
					on: {
						onAfterRender: function() {
							
							fillFilters(self.oldFilters);
							self.currentFilters = webix.copy(self.oldFilters); 
						
							if (self.dtFilter) {
								
								self.dtFilter.callbacks();
								self.dtFilter.add(self.oldFilters);
							}	
						}
					}	
				}
			]
		}
		
		function fillFilters(filters) {
			
			if (Array.isArray(filters) && filters.length > 0) {
			
				filters.forEach(filter => {
					
					if (filter.filterField && filter.filterOperator && filter.filterValue) {
							
						let column = self.currentTableData.columns.find(e => e.name == filter.filterField);
						
						if (column) {
							
							filter.id = webix.uid();
							filter.fieldId = column.id;
							filter.fieldName = util.getStringCapitalized((column.label ?? column.name));
							
							const keys = Object.keys(BackOfficeViewGridFilter.OPERATOR);
									
							if (Array.isArray(keys)) {
								
								keys.every(key => {
									
									if (BackOfficeViewGridFilter.OPERATOR[key].operator == filter.filterOperator) {
							
										filter.fieldOperatorId = BackOfficeViewGridFilter.OPERATOR[key].id;
										filter.fieldOperator = BackOfficeViewGridFilter.OPERATOR[key].label;
									}
									
									return !filter.fieldOperatorId ? true : false;
								});
							}
							
							if (column.refColumn) {
							
								const tableRef = self.data.find(e => e.id == column.refColumn.tableId);
								
								if (tableRef) {
	
									const refColumnRepresentative = BackOfficeHelper.getRepresentativeField(self.backOffice, tableRef);
									
									if (refColumnRepresentative) {
										
										filter.fieldId = refColumnRepresentative.id;
										filter.fieldName = util.getStringCapitalized((tableRef.labelSingular ?? (tableRef.label ?? tableRef.name)));
										
										const refColumnId = tableRef.columns.find(e => e.primaryKey === true);
									
										if (refColumnId) {
											
											tableRef.rows.every(row => {
											
												const idxRowId = row.data.findIndex(e => e.columnId == refColumnId.id);
												
												if (idxRowId > -1 && row.data[idxRowId].value == filter.filterValue) {
													
													const idxRowRepresentative = row.data.findIndex(e => e.columnId == refColumnRepresentative.id);
													
													if (idxRowRepresentative > -1) {
											
														filter.fieldValue = row.data[idxRowRepresentative].value;	
													}
												}
												
												return !filter.fieldValue ? true : false;
											});	
										}
									}
								}
							}
							else {
								
								filter.fieldValue = filter.filterValue;
							}
						}
					}
				});
			}
		}
			
		function getHeaderView() {
			
			let headerView;
			
			headerView = {
				cols: [
					{
						view: "label",
						width: 75,
						label: "<span style='font-size: 40px;' class='mdi mdi-filter'></span>",
						css: "global-header-icon"
					},
					{
						rows: [
							{ height: 10 },
							{
								view: "label",
								height: 65,
								id: "label-bottom-" + self.uid,
								template: "<span>" + i18n.t("LBL_VIEW_GRID_FILTER_TITLE") + "</span> <br> <span style='font-size: 13px'>" + i18n.t("LBL_VIEW_GRID_FILTER_TITLE_DESCRIPTION") + "</span>",
								css: "global-header-label"
							}		
						]
					}
					
				]
			};

			
			return headerView;
		}
		
		function getContentView() {
			
			let contentView;
			
			contentView = {
				rows: [
					
					{ height: 25 },
					getContentFilterView(),
					{ height: 15 },
					getContentTableView()
				]
			}
			
			function setStatusAddButton() {
			
				const filterAddButton = $$("filter-add-" + self.uid);
				const optionColumn = $$("option-column-" + self.uid);
				const optionOperator = $$("option-operator-" + self.uid);
				const columnValue = $$("column-value-" + self.uid);
				
				if (filterAddButton && optionColumn && optionOperator && columnValue && 
					optionColumn.getValue() !== -1 && optionOperator.getValue() !== -1 && columnValue.getValue()) {
						
					filterAddButton.enable();	
				}
				else {
					
					filterAddButton.disable(); 
				}
			}
			
			function getContentFilterView() {
			
				let contentFilterView;
			
				contentFilterView = {
					css: { "border": "1px solid #DADEE0 !important", "box-sizing": "border-box" },
					rows: [
						{ height: 15 },
						{
							view: "label",
							css: { "padding-left": "15px" },
							label: i18n.t("LBL_ENTER_FILTERS_AND_PRESS_ADD_BUTTON"),
						},
						{ height: 5 },
						{
							cols: [
								{ width: 15 },
								getFilterColumnView(),
								getFilterColumnOperator(),
								getFilterColumnValueText(),
								getFilterColumnAdd(),
								{ width: 15 },
							]		
						},
						{ height: 15 }
					]
				}
				
				function getFilterColumnValueText() {
					
					let filterColumnValueText;
					
					filterColumnValueText = {
						
						id: "column-value-" + self.uid,
						view: "text",
						type: "text",
						on:{
							
							onChange: function(newValue, oldValue, config){
							
								log.debug("FilterColumnValueText from " + oldValue + " to " + newValue);
								
								setStatusAddButton();
							}
						}
					}
					
					return filterColumnValueText;
				}
				
				function getFilterColumnValueNumber() {
					
					let filterColumnValueNumber;
					
					filterColumnValueNumber = {
						
						id: "column-value-" + self.uid,
						view: "text",
						type: "number",
						on:{
							
							onChange: function(newValue, oldValue, config){
							
								log.debug("FilterColumnValueNumber from " + oldValue + " to " + newValue);
								
								setStatusAddButton();
							}
						}
					}
					
					return filterColumnValueNumber;
				}
				
				function getFilterColumnValueDate() {
					
					let filterColumnValueDate;
					
					let locale = webix.i18n.locales["en-US"];
		
					if (webix.i18n.locales[i18n.localeCode]) {
						
						locale = webix.i18n.locales[i18n.localeCode];
					}
					else {
					
						log.warn("No locale js file for: " + i18n.localeCode + " Set default locale to en-US")
					}
					
					filterColumnValueDate = {
						
						id: "column-value-" + self.uid,
						view: "datepicker", 
						timepicker: true,
						format: (locale.dateFormat + " " + locale.timeFormat),
						on:{
							
							onChange: function(newValue, oldValue, config){
							
								log.debug("FilterColumnValueDate from " + oldValue + " to " + newValue);
								
								setStatusAddButton();
							}
						}
					}
					
					return filterColumnValueDate;
				}
				
				function getFilterColumnValueRef(table) {
					
					let filterColumnValueRef;
					
					filterColumnValueRef = {
						
						id: "column-value-" + self.uid,
						view: "richselect",
						placeholder: i18n.t("LBL_SELECT_VALUE"),
					    options: function() {
						
							let options = [{
								id: -1,
								value: ""
							}];
							
							if (Array.isArray(table.rows)) {
								
								const column = BackOfficeHelper.getRepresentativeField(self.backOffice, table);
								
								table.rows.forEach(row => {
									
									const columnData = row.data.find(d => d.columnId == column.id);
									
									if (columnData) {
										
										options.push({
											id: columnData.id,
											value: columnData.value
										})
									}
								})
							}
													
							return options;
							
						}(),
						on:{
							
							onChange: function(newValue, oldValue, config){
							
								log.debug("FilterColumnValueRef from " + oldValue + " to " + newValue);
								
								setStatusAddButton();
							}
						}
					}
					
					return filterColumnValueRef;
				}
				
				function setColumnValue(column) {
				
					let dbType = BackOfficeHelper.getDBTypes(column.colType);
					const columnValue = $$("column-value-" + self.uid);
					
					if (column.tableId == self.currentTableData.id) {
						
						if (columnValue && dbType) {
						
							if (dbType.name) {
								
								if (dbType.name === BackOfficeHelper.DB_TYPE_STRING ||
									dbType.name === BackOfficeHelper.DB_TYPE_BOOLEAN) {
									
									webix.ui(getFilterColumnValueText(), columnValue);
								}
								else if (dbType.name === BackOfficeHelper.DB_TYPE_SHORT || 
										 dbType.name === BackOfficeHelper.DB_TYPE_INTEGER ||
										 dbType.name === BackOfficeHelper.DB_TYPE_LONG ||
										 dbType.name === BackOfficeHelper.DB_TYPE_DECIMAL) {
									
									webix.ui(getFilterColumnValueNumber(), columnValue);
								}
								else if (dbType.name === BackOfficeHelper.DB_TYPE_TIMESTAMP) {
									
									webix.ui(getFilterColumnValueDate(), columnValue);
								}	
								
								setOperators();
							}
						}	
					}
					else {
						
						const table = self.data.find(e => e.id === column.tableId);

						if (table) {
							
							webix.ui(getFilterColumnValueRef(table), columnValue);
							
							dbType = BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_STRING);
							
							setOperators();	
						}
					}
					
					function setOperators() {

						const optionOperator = $$("option-operator-" + self.uid);
						
						if (optionOperator) {
							
							let options = [{
								id: -1,
								value: ""
							}];
							
							const keys = Object.keys(BackOfficeViewGridFilter.OPERATOR);

							if (Array.isArray(keys) && keys.length > 0) {
								
								keys.forEach(key => {
									
									if (dbType.name === BackOfficeHelper.DB_TYPE_STRING) {
										
										if (BackOfficeViewGridFilter.OPERATOR[key].onlyAlphanumeric) {
											
											options.push({
												id: BackOfficeViewGridFilter.OPERATOR[key].id,
												value: BackOfficeViewGridFilter.OPERATOR[key].label
											})	
										}
									}
									else {
										
										options.push({
											id: BackOfficeViewGridFilter.OPERATOR[key].id,
											value: BackOfficeViewGridFilter.OPERATOR[key].label
										})	
									}
								});
							}
							
							optionOperator.define("options", options);
							optionOperator.render();
						}
					}
				}
				
				function getFilterColumnView() {
					
					let filterColumnView;
					
					filterColumnView = {
						
						id: "option-column-" + self.uid,
						view: "richselect", 
						width: 250,
						placeholder: i18n.t("LBL_SELECT_FIELD"),
					    options: function() {
						
							let options = [{
								id: -1,
								value: ""
							}];
							
							const columnsAvailable = self.currentTableData.columns.filter(e => e.primaryKey !== true);
							
							for (let column of columnsAvailable) {

								const dbType = BackOfficeHelper.getDBTypes(column.colType);
								
								if (dbType) {
									
									let label = column.label ?? column.name;
									
									const referencedColumnName = BackOfficeHelper.getReferencedColumnName(self.backOffice, column, self.data);
									
									if (referencedColumnName) {

										const table = self.data.find(e => e.id === referencedColumnName.tableId);

										if (table) {

											label = table.labelSingular ?? (table.label ?? table.name);
										}
									}
		
									options.push({
										
										id: referencedColumnName ? referencedColumnName.id : column.id,
										value: util.getStringCapitalized(label)	
									})
								}
							}
							
							options.sort((a, b) => (a.value > b.value) ? 1 : ((b.value > a.value) ? -1 : 0));
							
							return options;
							
						}(),
						on:{
							
							onChange: function(newValue, oldValue, config){
							
								log.debug("FilterColumnView from " + oldValue + " to " + newValue);
								
								setStatusAddButton();
								
								let column = self.currentTableData.columns.find(col => col.id == newValue);
								
								if (!column && Array.isArray(self.data)) {
									
									self.data.every(dat => {
										
										column = dat.columns.find(col => col.id == newValue);
										
										return !column ? true : false; 
										
									});
								}
								
								if (column) {
									
									setColumnValue(column);
								}
							}
						}
					}
					
					return filterColumnView;
				}	
				
				function getFilterColumnOperator() {
					
					let filterColumnOperator;
					
					filterColumnOperator = {
						
						id: "option-operator-" + self.uid,
						view: "richselect", 
						placeholder: i18n.t("LBL_SELECT_OPERATOR"),
						width: 200,
					    options: function() {
						
							let options = [{
								id: -1,
								value: ""
							}];
							
							const keys = Object.keys(BackOfficeViewGridFilter.OPERATOR);

							if (Array.isArray(keys) && keys.length > 0) {
								
								options.push(...keys.map(key => {
									
									return {
										id: BackOfficeViewGridFilter.OPERATOR[key].id,
										value: BackOfficeViewGridFilter.OPERATOR[key].label
									}
								}))
							}
							
							return options;
							
						}(),
						on:{
							
							onChange: function(newValue, oldValue, config){
							
								log.debug("FilterColumnOperator from " + oldValue + " to " + newValue);
								
								setStatusAddButton();
							}
						}
					}
					
					return filterColumnOperator;
				}
				
				function getFilterColumnAdd() {
					
					let filterColumnAdd;
					
					filterColumnAdd = {
						id: "filter-add-" + self.uid,
						view: "icon", 
						icon: "wxi-plus",
						disabled: true,
						tooltip: i18n.t("LBL_CMD_ADD"),
						on: {
							onItemClick: addFilter
						}
					}
					
					function addFilter() {
						
						if (self.dtFilter) {
							
							const optionColumn = $$("option-column-" + self.uid);
							const optionOperator = $$("option-operator-" + self.uid);
							const columnValue = $$("column-value-" + self.uid); 
							
							if (optionColumn && optionOperator && columnValue) {
								
								const keys = Object.keys(BackOfficeViewGridFilter.OPERATOR);
								
								const columnId = optionColumn.getValue();
								const operatorId = optionOperator.getValue();
								let value = columnValue.getValue();
								
								let operator = undefined;
								
								keys.every(key => {
									
									if (BackOfficeViewGridFilter.OPERATOR[key].id == operatorId) {
										
										operator = BackOfficeViewGridFilter.OPERATOR[key];
									}
									
									return !operator ? true : false; 
								})
								
								let column = self.currentTableData.columns.find(c => c.id == columnId);
								
								let colFk = undefined;
								let valueFk = undefined;
								
								if (!column && Array.isArray(self.data)) {
									
									let columnRef = undefined;
									
									self.data.every(dat => {
										
										columnRef = dat.columns.find(col => col.id == columnId);
										
										return !columnRef ? true : false; 
										
									});
									
									
									if (columnRef) {
										
										column = columnRef;
										
										const tableRef = self.data.find(e => e.id === columnRef.tableId);
										const columnRefId = tableRef.columns.find(c => c.primaryKey === true);
										
										if (tableRef && columnRefId) {
											
											colFk = self.currentTableData.columns.find(c => c.refColumn && c.refColumn.id == columnRefId.id);
											
											tableRef.rows.every(row => {
												
												const idxRowData = row.data.findIndex(d => d.value == value);
												
												if (idxRowData > -1) {
													
													const columnRefIdValue = row.data.find(d => d.columnId == columnRefId.id);
													
													if (columnRefIdValue) {
														
														valueFk = columnRefIdValue.value;
													}
												}
												
												return !valueFk ? true : false;
											});
										}
									}
								}
								
								if (column && operator && value) {
									
									if (value instanceof Date) {
										
										value = value.toISOString().split('.')[0] + "Z";
										
										//value = util.toLocaleString(Math.floor(value.getTime()) / 1000)
									}
									
									let tableRef = undefined;
									
									if (column.tableId != self.currentTableData.id) {
										
										tableRef = self.data.find(e => e.id == column.tableId);
									}
									
									let label = tableRef ? (tableRef.labelSingular ?? (tableRef.label ?? tableRef.name)) : (column.label ?? column.name);
									
									let newFilter = {
										id: webix.uid(),
										userId: util.user.getUserData().id, 
										fieldId: column.id,
										fieldName: util.getStringCapitalized(label),
										fieldOperatorId: operator.id,
										fieldOperator: operator.label,
										fieldValue: value,
										filterField: colFk ? colFk.name : column.name,
										filterOperator: operator.operator,
										filterValue: valueFk ?? value
									}; 
									
									self.currentFilters.push(newFilter);
									self.dtFilter.add(newFilter);
								}
							}	
						}
					}
					
					return filterColumnAdd;
				}
				
				return contentFilterView;
			}
			
			function getContentTableView() {
				
				let events = {};
				
				function getMetadata() {

					let gridMetadata = {};

					const gridOptionValues = [
						{ gridOption: { id: 1 }, value: "false" },
						{ gridOption: { id: 7 }, value: "false" },
						{ gridOption: { id: 8 }, value: "false" },
						{ gridOption: { id: 9 }, value: "true" },
						{ gridOption: { id: 11 }, value: "false" },
						{ gridOption: { id: 13 }, value: "false" },
						{ gridOption: { id: 16 }, value: "img/grid/default.png" },
						{ gridOption: { id: 21 }, value: "auto" },
						{ gridOption: { id: 23 }, value: "false" }
					];

					gridMetadata = {
						filter: true,
						label: "",
						visible: true,
						gridOptionValues: gridOptionValues,
						gridFields: [],
						gridCommands: []
					};

					gridMetadata.gridCommands = [
						{
							id: 1,
							value: "LBL_CMD_DELETE",
							icon: "mdi mdi-delete",
							default_: true,
							label: "LBL_DELETE",
							showText: true,
							position: 1,
							multiselect: true,
							itemRequired: true
						},
						{
							id: 2,
							value: "LBL_UNDO_ALL",
							icon: "mdi mdi-undo",
							label: "LBL_UNDO_ALL",
							default_: false,
							showText: true,
							position: 2,
							multiselect: false,
							itemRequired: false
						}
					]

					events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 1,
						function: cmdDelete
					});

					events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
						commandId: 2,
						function: function() {

							let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_UNDO_FILTERS"), null, onAccept, null);
							ms.show();
							
							function onAccept() {
								
								cmdUndoAll();
							};
						}
					});

					gridMetadata.gridFields = [
						{
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: true,
							needsTranslation: false,
							parameterized: false,
							position: 1,
							required: true,
							sourceField: "id",
							visible: false,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						},
						{
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_FIELD_ID",
							needsTranslation: false,
							parameterized: false,
							position: 2,
							required: true,
							sourceField: "fieldId",
							visible: false,
							gridFieldDataType: {
								id: 2,
								name: "int"
							}
						},
						{
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_FIELD_NAME",
							needsTranslation: false,
							parameterized: false,
							position: 2,
							required: true,
							sourceField: "fieldName",
							visible: true,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						},
						{
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_FIELD_OPERATOR_ID",
							needsTranslation: false,
							parameterized: false,
							position: 3,
							required: true,
							sourceField: "fieldOperatorId",
							visible: false,
							gridFieldDataType: {
								id: 2,
								name: "int"
							}
						},
						{
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_FIELD_OPERATOR",
							needsTranslation: false,
							parameterized: false,
							position: 4,
							required: true,
							sourceField: "fieldOperator",
							visible: true,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						},
						{
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_FIELD_VALUE",
							needsTranslation: false,
							parameterized: false,
							position: 5,
							required: true,
							sourceField: "fieldValue",
							visible: true,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						}
					];
					
					function cmdDelete() {
						
						const selectedItems = self.dtFilter.getSelectedItems();

						if (Array.isArray(selectedItems) && selectedItems.length > 0) {
							
							selectedItems.forEach(selectedItem => {
								
								self.currentFilters = self.currentFilters.filter(e => e.id != selectedItem.id);
								
								self.dtFilter.delete(selectedItem);		
							});
						}
					}
					
					function cmdUndoAll() {
						
						self.dtFilter.clear();
						self.dtFilter.add(self.oldFilters);
						
						self.currentFilters = webix.copy(self.oldFilters);
					}

					return gridMetadata;
				}
				
				self.dtFilter = new DataTable(null, null, getMetadata(), null, events);

				return {
					css: { "border": "1px solid #DADEE0 !important", "box-sizing": "border-box" },
					cols: [
						{ width: 15 },
						{
							rows: function() {
								
								let rows = [];
								
								rows.push({ height: 15 });
								rows.push(...self.dtFilter.getView());
								rows.push({ height: 15 });
								
								return rows;
							}(),
						},
						{ width: 15 }
					]
				}
			}
			
			return contentView;
		}
		
		function getFooterView() {
			
			let footerView;
			
			footerView = {
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
								click: function() {
										
									if (self.onCancel) {
														
										self.onCancel();	
									}
								}
							},
							{},
							{ 
								id: "chkbox-save-" + self.uid,
								view: "checkbox", 
								label: i18n.t("LBL_SAVE_FILTERS"),
								labelWidth: 200,
								width: 250,
								labelAlign: "right"
							},
							{
								id: "btn-accept-" + self.uid,
								view: "button",
								value: i18n.t("LBL_ACCEPT"),
								width: 130,
								height: 41,
								css: "dialog-accept",
								click: function() {
									
									const chkboxSave = $$("chkbox-save-" + self.uid);
									
									if (self.onAccept && chkboxSave) {
										
										if (Array.isArray(self.currentFilters)) {
											
											const filters = self.currentFilters.map(e => {
												
												return {
													
													filterField: e.filterField,
													filterOperator: e.filterOperator,
													filterValue: e.filterValue,
													userId: e.userId
												}
											})
											
											self.onAccept(filters, chkboxSave.getValue() === 1 ? true : false);
										}
									}
								}
							},
							{ width: 15 }
						]
					}
				]
			}
			
			return footerView;
		}
		
		return view;
	}		
}
