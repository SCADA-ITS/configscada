import BackOfficeModule from "./backOfficeModule.js";
import BackOfficeHelper from "./backOfficeHelper.js";
import SearchDialogBox from "../../component/searchDialogBox.js";
import MessageBox from "../../component/messageBox.js";
import { util } from "../../lib/util.js";

export default class BackOfficeViewFormField  {

	constructor(form) {

		this.backOffice = form.backOffice;
		this.data = form.data;
		this.currentTableData = form.currentTableData;
		this.uid = form.uid;
		this.refresh = form.refresh;
		this.setValues = form.setValues;
		this.isVisible = true;
		this.formId = form.formId;
	}
	
	getField(column, options) {
		
		let self = this;
		let newField;
		
		if (column) {
			
			const dbType = BackOfficeHelper.getDBTypes(column.colType);
			
			if (dbType) {
				
				let field = webix.copy(dbType.component);
			
				field.name = BackOfficeHelper.getColumnUID(column);
				field.label = util.getStringCapitalized(column.label ? BackOfficeHelper.translate(self.backOffice, column.label) : column.name);
				field.disabled = false;
				field.required = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_COLUMN, BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.REQUIRED, self.backOffice.metadata, column) || !column.nullable;
				field.readonly = !BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_COLUMN, BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.EDITABLE, self.backOffice.metadata, column);
				field.hidden = !BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_COLUMN, BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.FORM_VISIBLE, self.backOffice.metadata, column);
				
				applyOptions(field);
				
				const showIdForm = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.SHOW_ID_FORM, self.backOffice.metadata, self.currentTableData);
				
				if (column.primaryKey === true) {
					
					field.name = column.name;
					field.disabled = true;
					field.hidden = !showIdForm;
				}
				
				if (dbType.name === BackOfficeHelper.DB_TYPE_STRING) {
					
					const multiline = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_COLUMN, BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.MULTILINE, self.backOffice.metadata, column);
					
					if (!isNaN(multiline) && multiline > 0) {
						
						field.view = "textarea";
						field.height = multiline * 20;	
					}
				}
				
				if (field.view === "text") {
					
					if (dbType.name === BackOfficeHelper.DB_TYPE_DECIMAL || 
							 dbType.name === BackOfficeHelper.DB_TYPE_INTEGER) {
					
						field.type = "number";
					}
					else {
						
						field.type = "text";
					}
					
					field.css = field.readonly ? "backoffice-input-readonly" : undefined;
				}
				
				let value = BackOfficeHelper.getColumnValue(self.currentTableData, column.id, 0);
				
				if (value) {
					
					if (dbType.name === BackOfficeHelper.DB_TYPE_TIMESTAMP) {
					
						value = new Date(parseInt(value));
					}
					else if (dbType.name === BackOfficeHelper.DB_TYPE_BOOLEAN) {
					
						value = IS_TRUE(value);
					}
				}
				
				field.value = undefined;
				
				if (!self.backOffice.onCancel || column.primaryKey === true) {
					
					field.value = value;	
				}
				
				self.isVisible = !field.hidden;
				
				const refColumn = column.refColumn;
				const refTable = refColumn ? self.data.find(e => e.id === refColumn.tableId) : undefined;
				
				if (refColumn && refTable) {
					
					field.value = value;
					
					const referencedColumnValue = BackOfficeHelper.getReferencedColumnValue(self.backOffice, column, value, self.data);
					const associatedField = self.currentTableData.columns.find(e => column.metadata && BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_COLUMN, BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.ASSOCIATED_FIELD_NAME, self.backOffice.metadata, column)  === e.name);
					const refGroupField = refTable.columns.find(e => column.metadata && BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_COLUMN, BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.REF_GROUP_FIELD_NAME, self.backOffice.metadata, column) === e.name);
					const dependentColumns = self.currentTableData.columns.filter(e => e.metadata && BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_COLUMN, BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.ASSOCIATED_FIELD_NAME, self.backOffice.metadata, e) === BackOfficeHelper.getColumnNameFromColumnUID(field.name));										
					const disabled = self.backOffice.filterColumn && self.backOffice.filterColumn.refColumn && self.backOffice.filterColumn.refColumn.id === refColumn.id && self.backOffice.filterColumn.refColumn.tableId === refColumn.tableId; 
					//const refAddButtonTable = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.REF_ADD_BUTTON, self.backOffice.metadata, self.currentTableData);
									
					//let refAddButtonColumn = (column.metadata) ? column.metadata[BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.REF_ADD_BUTTON.name] : undefined;
					//let refAddButton = (refAddButtonColumn != undefined) ? refAddButtonColumn : refAddButtonTable;  
					const visibleAddButton = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_COLUMN, BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.REF_ADD_BUTTON, self.backOffice.metadata, column);
					const visibleEditButton = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_COLUMN, BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.REF_EDIT_BUTTON, self.backOffice.metadata, column);
					let visibleColumn = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_COLUMN, BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.FORM_VISIBLE, self.backOffice.metadata, column);
					
					self.isVisible = visibleColumn;

					if (referencedColumnValue && (!self.backOffice.filterColumn || refColumn.id != self.backOffice.filterColumn.id)) {
						
						const dbTypeRefColumn = BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_STRING);
					
						let fieldRef = webix.copy(dbTypeRefColumn.component);
					
						let fieldGroup = {
							cols: [
								{
									width: 725,
									height: 1,
									hidden: visibleColumn
								},
								{ 
									view: fieldRef.view, 
									type: "text", 
									name: BackOfficeHelper.getColumnUID(referencedColumnValue), 
									required: field.required, 
									label: util.getStringCapitalized(refTable.labelSingular ? BackOfficeHelper.translate(self.backOffice, refTable.labelSingular) : (refTable.label ? BackOfficeHelper.translate(self.backOffice, refTable.label) : refTable.name)), 
									readonly: true, 
									labelWidth: 275, 
									value: !this.backOffice.onCancel || disabled ? (referencedColumnValue.value ?? field.value) : undefined, 
									printValue: referencedColumnValue.value ?? field.value,
									printId: field.name,
									disabled: disabled,
									css: "backoffice-input-readonly",
									hidden: !visibleColumn
								},
								{ 
									view: field.view, 
									name: field.name, 
									hidden: !visibleColumn || !showIdForm, 
									readonly: true, 
									css: "backoffice-input-readonly",
									value: !this.backOffice.onCancel || disabled ? field.value : undefined, 
									width: 50,
									on: {
										onChange: function() {
											
											const persistValues = onChangeField();
											
											if (Array.isArray(persistValues) && persistValues.length > 0 &&
												Array.isArray(dependentColumns) && dependentColumns.length > 0) {
													
												dependentColumns.forEach(dependCol => {
													
													const form = $$(self.formId);
													const dependentReferencedColumnName = BackOfficeHelper.getReferencedColumnName(self.backOffice, dependCol, self.data);
													
													if (dependentReferencedColumnName && form) {
														
														let newValue = {};
														
														newValue[BackOfficeHelper.getColumnUID(dependCol)] = "";
														newValue[BackOfficeHelper.getColumnUID(dependentReferencedColumnName)] = "";
														
														form.setValues(newValue, true);	
													}
												});
											}
												
											const fieldEditButton = $$(field.name + "-edit");
											
											if (fieldEditButton) {
												
												if (this.getValue()) {
													
													fieldEditButton.enable();	
												}
												else {
													
													fieldEditButton.disable();
												}
											}
										}
									}
								},
								{
									view: "icon", 
									icon: "wxi-search",
									tooltip: i18n.t("LBL_SEARCH"),
									hidden: !visibleColumn || disabled,
									on: {
										onItemClick: function() {
											
											const table = self.data.find(e => e.id === refColumn.tableId);
											let colFilter = undefined;
											let colFilterValue = undefined;
								
											if (associatedField) {
									
												colFilter = table.columns.find(e => e.refColumn && associatedField.refColumn && e.refColumn.id === associatedField.refColumn.id);
												
												if (colFilter) {
													
													const form = $$(self.formId);
													
													if (form) {
														
														const formValues = form.getValues();
														
														if (formValues) {
														
															colFilterValue = formValues[BackOfficeHelper.getColumnUID(associatedField)];	
														}
													}
												}
											}
											
											if (colFilter && !colFilterValue) {
									
												(new MessageBox(MessageBox.WARN, i18n.t("LBL_BACKOFFICE_FIELD_IS_DEPENDENT_OF") + " " + (colFilter.label ?? colFilter.name))).show();
											}
											else {
												
												if (refGroupField) {
													
													updateRefFieldTree(table, refGroupField);	
												}
												else {
													
													updateRefFieldTable(table, colFilter, colFilterValue);	
												}
													
											}
										}  
									}
								},
								{
									view: "icon", 
									icon: "wxi-plus",
									tooltip: i18n.t("LBL_CMD_ADD"),
									hidden: ((!visibleColumn) ? !visibleColumn: !visibleAddButton) || disabled,
									on: {
										onItemClick: createRefField
									}
								},
								{
									view: "icon", 
									id: field.name + "-edit",
									icon: "wxi-pencil",
									tooltip: i18n.t("LBL_CMD_EDIT"),
									hidden: ((!visibleColumn) ? !visibleColumn: !visibleEditButton) || disabled,
									disable: !field.value,
									on: {
										onItemClick: function() {
											
											const form = $$(self.formId);
											const table = self.data.find(e => e.id === refColumn.tableId);
											
											if (form && table) {
												
												const formValues = form.getValues();
												
												if (formValues && field.name && formValues[field.name]) {
													
													edit(table, formValues[field.name]);
												}
											}
										}
									}
								}
							]
						}
						
						newField = fieldGroup;
					}
					
					function updateRefFieldTable(table, colFilter, colFilterValue) {
						
						if (table && Array.isArray(table.rows) && table.rows.length > 0) {
							
							if (field.name && referencedColumnValue) {
								
								let dialogRefField = webix.copy(SearchDialogBox.CALLBACK);	
								let aditionalColumnsValues = [];			
							
								dialogRefField.fillDialogEntity = fillDialogEntity;

								const fields = BackOfficeHelper.getColumnUID(referencedColumnValue) + "=" + util.getStringCapitalized(referencedColumnValue.label ?? referencedColumnValue.name);
								dialogRefField.fields = [fields];

								if (Array.isArray(table.columns) && table.columns.length > 0) {

									table.columns.forEach(col => {

										if (BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_COLUMN, BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.SEARCH_VISIBLE, self.backOffice.metadata, col)) {

											aditionalColumnsValues.push(col);
											dialogRefField.fields.push(BackOfficeHelper.getColumnUID(col) + "=" + util.getStringCapitalized(col.label ?? col.name));
										}
									});
								}
								
								const columnId = table.columns.find(e => e.primaryKey === true);
								
								if (columnId) {
									
									dialogRefField.pkField = "id";
									dialogRefField.pkFieldVisible = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.SHOW_ID_SEARCH, self.backOffice.metadata, self.currentTableData);
									dialogRefField.sortFieldId = 1;
									dialogRefField.height = 600;
									dialogRefField.width = 800;
									dialogRefField.showEmptyRowCol = SearchDialogBox.ID_NOT_SELECTED;	
								}

								function fillDialogEntity(dialogEntity, onFilledDialogEntity) {
					
									dialogEntity.data = table.rows.map(row => {
									
										const rowId = row.data.find(data => data.columnId === table.columns.find(col => col.primaryKey === true).id);
										const rowAlias = row.data.find(data => data.columnId === referencedColumnValue.id);
											
										let obj = {};
										
										if (rowId && rowAlias) {
											
											obj.id = rowId.value;
											obj[BackOfficeHelper.getColumnUID(referencedColumnValue)] = rowAlias.value;

											if (Array.isArray(aditionalColumnsValues) && aditionalColumnsValues.length > 0) {

												for (let aditionalColumn of aditionalColumnsValues) {

													obj[BackOfficeHelper.getColumnUID(aditionalColumn)] = row.data.find(data => data.columnId === aditionalColumn.id).value;
												}
											}
																							
											if (colFilter) {
												
												const rowFilter = row.data.find(data => data.columnId === colFilter.id);
												
												if (rowFilter) {
													
													obj[BackOfficeHelper.getColumnUID(colFilter)] = rowFilter.value;	
												}
											}
										}
										
										return obj;
									});
									
									if (colFilter && colFilterValue) {
										
										dialogEntity.data = dialogEntity.data.filter(e => e[BackOfficeHelper.getColumnUID(colFilter)] === colFilterValue);
									}
					
									onFilledDialogEntity(dialogEntity);
								}
								
								(new SearchDialogBox(i18n.t("LBL_SEARCH " + util.getStringCapitalized(table.label ?? table.name)), dialogRefField, onAccept)).show();	
								
								function onAccept(selection) {
									
									const form = $$(self.formId);
									
									if (form && selection) {
										
										let itemSelected = selection[0];
										
										let newValue = {};
										
										newValue[field.name] = (itemSelected.id != -1 ? itemSelected.id : "");
										newValue[BackOfficeHelper.getColumnUID(referencedColumnValue)] = (itemSelected.id != -1 ? itemSelected[BackOfficeHelper.getColumnUID(referencedColumnValue)] : "");
										
										form.setValues(newValue, true);
									}
								}
							}
						}
						else {
							
							if (((!visibleColumn) ? !visibleColumn: !visibleAddButton) || disabled) {
								
								(new MessageBox(MessageBox.WARN, i18n.t("LBL_BACKOFFICE_NO_RECORDS_ON_REF_TABLE") + ": " + (table.label ?? table.name))).show();
							}
							else {
								
								(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_NO_RECORDS_ON_REF_TABLE") + ": " + (table.label ?? table.name) + ". " + i18n.t("LBL_BACKOFFICE_CREATE_NEW_RECORD"), null, createRefField, null)).show();
							}							
						}
					}
					
					function updateRefFieldTree(table, refGroupField) {
						
						if (table && Array.isArray(table.rows) && table.rows.length > 0) {
							
							if (field.name && referencedColumnValue) {
								
								let dialogRefField = webix.copy(SearchDialogBox.CALLBACK);				
								dialogRefField.fillDialogEntity = fillDialogEntity;
								dialogRefField.dataType = SearchDialogBox.DIALOG_DATA_TYPE_TREE;
								dialogRefField.height = 600;
								dialogRefField.width = 800;
								
								function fillDialogEntity(dialogEntity, onFilledDialogEntity) {
									
									dialogEntity.data = {};
									let itemsTree = [];
									let itemRelationships = [];
									
									let group = new Map();
									
									table.rows.forEach(row => {
										
										const rowId = row.data.find(data => data.columnId === table.columns.find(col => col.primaryKey === true).id);
										const rowAlias = row.data.find(data => data.columnId === referencedColumnValue.id);
										const rowRefColId = row.data.find(data => data.columnId === refGroupField.id);
										
										
										if (rowId && rowRefColId && rowAlias) {
											
											let items = group.get(JSON.stringify(rowRefColId));
											
											const item = {rowId: rowId, rowAlias: rowAlias };
											
											if (Array.isArray(items)) {
												
												items.push(item);
											}
											else {
												
												group.set(JSON.stringify(rowRefColId), [item])
											}
										}
									});
									
									for (let [key, value] of group) {
										
										const groupValue = BackOfficeHelper.getReferencedColumnValue(self.backOffice, refGroupField, JSON.parse(key).value, self.data);
										
										let folder = {
											
											id: webix.uid(),
											label: groupValue ? groupValue.value : JSON.parse(key).value,
											aux: {
												id: JSON.parse(key).id
											},
											folder: true
										}
										
										itemsTree.push(folder);
										
										if (Array.isArray(value)) {
																
											value.forEach(e => {
												
												let item = {
													id: webix.uid(),
													label: e.rowAlias ? e.rowAlias.value : "",
													aux: {
														id: e.rowId ? e.rowId.value : ""
													},
													folder: false
												}
												
												itemsTree.push(item);
												
												itemRelationships.push({
													id: webix.uid(),
													nodeParentId: folder.id,
													nodeChildId: item.id
												})
											});
										}
									}
									
									
									dialogEntity.data.nodes = itemsTree;
									dialogEntity.data.relationships = itemRelationships;
									dialogEntity.data.nodeParentId = "nodeParentId";
									dialogEntity.data.nodeChildId = "nodeChildId";
									
									onFilledDialogEntity(dialogEntity);
								}
								
								(new SearchDialogBox(i18n.t("LBL_SEARCH " + util.getStringCapitalized(table.label ?? table.name)), dialogRefField, onAccept)).show();
								
								function onAccept(selection) {
									
									const form = $$(self.formId);
									
									if (form && selection) {
										
										let itemSelected = selection[0];
										
										if (itemSelected && itemSelected.aux) {
											
											let newValue = {};
										
											newValue[field.name] = itemSelected.aux.id;
											newValue[BackOfficeHelper.getColumnUID(referencedColumnValue)] = itemSelected.value;
											
											form.setValues(newValue, true);
										}														
									}
								}
							}
						}
						else {
							
							if (((!visibleColumn) ? !visibleColumn: !visibleAddButton) || disabled) {
								
								(new MessageBox(MessageBox.WARN, i18n.t("LBL_BACKOFFICE_NO_RECORDS_ON_REF_TABLE") + ": " + (table.label ?? table.name))).show();
							}
							else {
							
								(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_NO_RECORDS_ON_REF_TABLE") + ": " + (table.label ?? table.name) + ". " + i18n.t("LBL_BACKOFFICE_CREATE_NEW_RECORD"), null, createRefField, null)).show();	
							}
						}
					}
					
					function createRefField() {
						
						const table = self.data.find(e => e.id === refColumn.tableId);
			
						if (table && Array.isArray(table.rows) && field.name && referencedColumnValue) {
						
							BackOfficeHelper.insertData(self.backOffice, table).then((requestResult) => {
										
								if (!requestResult.requestResultMessage.error) {
									
									if (requestResult.newRow && Array.isArray(requestResult.newRow.data)) {
										
										const columnId = table.columns.find(e => e.primaryKey === true);
										
										if (columnId) {
											
											const data = requestResult.newRow.data.find(e => e.columnId === columnId.id);
											
											if (data && data.value) {
												
												const columnIdValue = data.value; 
									
												edit(table, columnIdValue, function () {
													
													let persistValues = [];
													
													persistValues.push({
														colName: columnId.name,
														oldValue: columnIdValue,
														newValue: undefined,
														colType: columnId.colType
													});
								
													BackOfficeHelper.deleteData(self.backOffice, table, persistValues, columnIdValue).then((requestResult) => {
														
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
						
						
					}
					
					function edit(table, id, onCancel) {
	
						const module = window.em.getByField(EntityType.MODULE, "id", BackOfficeModule.Config.MODULE_ID);

						if (module) {
																												
							const formHeight = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_HEIGHT, self.backOffice.metadata, table);
							const formWidth = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_WIDTH, self.backOffice.metadata, table);
							
							const winId = util.ui.loadWindow(i18n.t("LBL_NEW") + " " + (table.labelSingular ?? (table.label ?? table.name)), { id: self.uid }, formHeight, formWidth, true);
							
							const columnId = table.columns.find(e => e.primaryKey === true);
							
							if (columnId) {
							
								const args = {
									backOffice: {
										id: self.backOffice.id,
										tableName: table.name,
										filterColumnName: columnId.name,
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
							}
						
							function drawContent(view) {
	
								webix.ui(view, $$(self.uid));
							}
							
							function onLoad() {
								
								util.ui.resizeWindow(winId, formWidth, formHeight);
								util.ui.centerWindow(winId, 20);	
							}
							
							function onAccept(newRow) {
								
								if (newRow) {
								
									log.debug(newRow);
									
									self.refresh();
									
									const form = $$(self.formId);
									
									if (form) {
									
										if (referencedColumnValue) {
											
											let newValue = {};
											
											newValue[BackOfficeHelper.getColumnUID(column)] = newRow[columnId.name];
											newValue[BackOfficeHelper.getColumnUID(referencedColumnValue)] = newRow[BackOfficeHelper.getColumnUID(referencedColumnValue)];
											
											form.setValues(newValue, true);
										}
									}	
								}
							}
						}
					}
				}
				else {
					
					if (dbType.name === BackOfficeHelper.DB_TYPE_BOOLEAN ||
						dbType.name === BackOfficeHelper.DB_TYPE_TIMESTAMP) {
						
						field.on = { 
							onChange: function() { 

								const persistValues = onChangeField(); 
								
								if (Array.isArray(persistValues) && persistValues.length > 0) {
								
									filler();	
								}
							} 
						}
					}
					else if (dbType.name === BackOfficeHelper.DB_TYPE_INTEGER ||
						dbType.name === BackOfficeHelper.DB_TYPE_DECIMAL) {

						field.on = {
							onKeyPress: function(code, e) {

								if (e.key === "Dead") {

									this.disable();
									this.enable();
									this.focus();
								}

								const persistValues = onChangeField();

								if (Array.isArray(persistValues) && persistValues.length > 0) {

									filler();
								}

								return code == 46 || code == 8 || (code >= 35 && code <= 40) || (code >= 48 && code <= 57) || code == 190 || code == 173;
							}
						};
											
					} else {
						
						field.on = { onTimedKeyPress: onChangeField }
						field.on = {
							onChange: function() {

								const persistValues = onChangeField();

								if (Array.isArray(persistValues) && persistValues.length > 0) {

									filler();
								}
							}
						}
					}
					
					//fields.push(field);
					
					newField = field;	
				}
				
				function onChangeField() {
					
					let requestInput;
					const btnAccept = $$("btn-accept-" + self.uid);
									    	
			    	if (btnAccept) {
				
						const form = $$(self.formId);
																					
						if (form) {
												
							requestInput = BackOfficeHelper.getRequestInput(form.getValues(), self.currentTableData);
						
							if (Array.isArray(requestInput) && requestInput.length > 0) {
						
								btnAccept.enable();
							}
							else {
								
								btnAccept.disable();
							}	
						}
					}
					
					return requestInput;
				}
				
				function filler() {
							
					const form = $$(self.formId);
										
					if (form) {
						
						if (Array.isArray(column.fillers) && column.fillers.length > 0) {
							
							const values = form.getValues();
						
							if (typeof(values) === 'object') {
								
								const columnId = self.currentTableData.columns.find(e => e.primaryKey === true);
							
								if (columnId) {
									
									const requestInput = BackOfficeHelper.getRequestInput(values, self.currentTableData);
									const id = values[columnId.name];
									
									if (id && (Array.isArray(requestInput) && requestInput.length > 0)) {
								
										BackOfficeHelper.filler(self.backOffice, self.currentTableData, column, requestInput, id).then((requestResult) => {
													
											if (!requestResult.requestResultMessage.error) {
												
												self.setValues(requestResult.newRow, form);
												
												if (requestResult.requestResultMessage.info) {
												
													log.info(requestResult.requestResultMessage.info + "\n" + requestResult.requestResultMessage.infoDescription);
												
													(new MessageBox (MessageBox.INFO, i18n.t("LBL_BACKOFFICE_INFO_ON_FILLER") + " " + requestResult.requestResultMessage.info + ":" + requestResult.requestResultMessage.infoDescription, null, null, null)).show();
												}
												else if (requestResult.requestResultMessage.warn) {
												
													log.warn(requestResult.warn + "\n" + requestResult.warnDescription);
												
													(new MessageBox (MessageBox.WARN, i18n.t("LBL_BACKOFFICE_INFO_ON_FILLER") + " " + requestResult.requestResultMessage.warn + ":" + requestResult.requestResultMessage.warnDescription, null, null, null)).show();
												}
											}
											else {
												
												log.error(requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription);
												
												(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_ON_FILLER") + " " + (self.currentTableData.label ?? self.currentTableData.name), requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription, null, null)).show();
											}
										});
									}
								}
							}
						}
					}
				}
			}
		}
		
		function applyOptions(field) {
			
			if (options) {
				
				if (options.view) {
					
					const keys = Object.keys(options.view);
					
					if (Array.isArray(keys) && keys.length > 0) {
					
						keys.forEach(key => {
							
							field[key] = options.view[key];	
						})
					}
				}
			}
		}
		
		return newField;
	}
}
