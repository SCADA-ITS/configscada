import { util } from "../../lib/util.js";

const REST_BACKOFFICE_DATA = "backoffice/${backOfficeId}";
const REST_BACKOFFICE_TABLE = "backoffice/${backOfficeId}/table/${tableName}";
const REST_BACKOFFICE_TABLE_PERSIST = "backoffice/${backOfficeId}/persist";
const REST_BACKOFFICE_TABLE_COMMAND = "backoffice/${backOfficeId}/command";
const REST_BACKOFFICE_COLUMN_FILLER = "backoffice/${backOfficeId}/filler";
const COL_NAMES_REF = [/name/i, /alias/i, /description/i];

export default class BackOfficeHelper {
	
	static get DB_TYPE_STRING() { return "STRING" }
	static get DB_TYPE_INTEGER() { return "INTEGER" }
	static get DB_TYPE_DECIMAL() { return "DECIMAL" }
	static get DB_TYPE_TIMESTAMP() { return "TIMESTAMP" }
	static get DB_TYPE_BOOLEAN() { return "BOOLEAN" }
	
	static get META_TYPE_BACKOFFICE() { 
		return { 
			name: "META_TYPE_BACKOFFICE",
			DEFAULT_METADATA: "defaultMetadataBackoffice",
			PROPERTY: {
				
			} 
		} 
	}
	static get META_TYPE_TABLE() { 
		return { 
			name: "META_TYPE_TABLE",
			DEFAULT_METADATA: "defaultMetadataTable",
			PROPERTY: {
				FORM_HEIGHT: { name: "formHeight", default: 700 }, 
				FORM_WIDTH: { name: "formWidth", default: 900 }, 
				GRID_HEIGHT: { name: "gridHeight", default: 700 }, 
				GRID_WIDTH: { name: "gridWidth", default: 900 },
				SHOW_ID_FORM: { name: "showIdForm", default: true }, 
				SHOW_ID_TABLE: { name: "showIdTable", default: false },
				SHOW_ID_SEARCH: { name: "showIdSearch", default: false },
				GRID_FILTERS: { name: "gridFilters", default: false }
			} 
		} 
	}
	static get META_TYPE_COLUMN() { 
		return { 
			name: "META_TYPE_COLUMN",
			DEFAULT_METADATA: "defaultMetadataColumn",
			PROPERTY: {
				REQUIRED: { name: "required", default: false }, 
				EDITABLE: { name: "editable", default: true }, 
				RED_ADD_BUTTON: { name: "refAddButton", default: true }
			} 
		} 
	}
	
	static get REQUEST_TYPE_INSERT() { return "INSERT" }
	static get REQUEST_TYPE_UPDATE() { return "UPDATE" }
	static get REQUEST_TYPE_DELETE() { return "DELETE" }
	static get REQUEST_TYPE_FILLER() { return "FILLER" }
	static get REQUEST_TYPE_COMMAND() { return "COMMAND" }
		
	static getDBTypes(name) {
		
		const dbType = {};
		
		dbType[BackOfficeHelper.DB_TYPE_STRING] = { id: 1, name: BackOfficeHelper.DB_TYPE_STRING, component: { view: "text", required: false, labelWidth: 275 }};
		dbType[BackOfficeHelper.DB_TYPE_INTEGER] = { id: 2, name: BackOfficeHelper.DB_TYPE_INTEGER, component: { view: "text", required: false, labelWidth: 275 }};
		dbType[BackOfficeHelper.DB_TYPE_DECIMAL] = { id: 3, name: BackOfficeHelper.DB_TYPE_DECIMAL, component: { view: "text", required: false, labelWidth: 275 }};
		dbType[BackOfficeHelper.DB_TYPE_TIMESTAMP] = { id: 4, name: BackOfficeHelper.DB_TYPE_TIMESTAMP, component: { view: "datepicker", timepicker: true, required: false, labelWidth: 275, format: (webix.i18n.locales[i18n.localeCode].dateFormat + " " + webix.i18n.locales[i18n.localeCode].timeFormat)}};
		dbType[BackOfficeHelper.DB_TYPE_BOOLEAN] = { id: 5, name: BackOfficeHelper.DB_TYPE_BOOLEAN, component: { view: "checkbox", required: false, labelWidth: 275 }}; 
		
		return dbType[name];
	}
	
	static getMetadata(metaType, prop, defaultMetadata, object) {
		
		let value = undefined;
		
		if (metaType.name === BackOfficeHelper.META_TYPE_BACKOFFICE.name ||
			metaType.name === BackOfficeHelper.META_TYPE_TABLE.name || 
			metaType.name === BackOfficeHelper.META_TYPE_COLUMN.name) {
		
			if (defaultMetadata) {
				
				value = prop.default;
				
				if (defaultMetadata[metaType.DEFAULT_METADATA]) {
					
					value = defaultMetadata[metaType.DEFAULT_METADATA][prop.name];
				}
			}
			
			if (object && object.metadata && (metaType.name === BackOfficeHelper.META_TYPE_TABLE.name || 
							metaType.name === BackOfficeHelper.META_TYPE_COLUMN.name)) {
					
				value = object.metadata[prop.name] ?? value;	
			}		
		}
		
		return value;
	}
	
	static async getBackOfficeData(backOffice) {
		
		let response;
		
		const url = appConfig.restUrl + 
						util.fillTemplate(REST_BACKOFFICE_DATA, { backOfficeId: backOffice.id });
						
		response = await webix.ajax().get(url);
			
		return response ? response.json() : undefined;
	}
	
	static async getData(backOffice) {
		
		let response;
		
		const url = appConfig.restUrl + 
						util.fillTemplate(REST_BACKOFFICE_TABLE, { backOfficeId: backOffice.id, tableName: backOffice.tableName });
			
		let conditions = undefined;
		
		if (backOffice.filterColumnName && backOffice.filterColumnValue) {
			
			conditions = backOffice.filterColumnName + "=" + backOffice.filterColumnValue;
		}
		
		response = await webix.ajax().headers(CONTENT_TYPE_JSON).post(url, conditions);
		
		return response ? response.json() : undefined;
	}
	
	static async insertData(backOffice, table, requestInput) {
		
		let response;
		
		const body = {
			id: undefined,
			requestInput: requestInput,
			requestType: BackOfficeHelper.REQUEST_TYPE_INSERT,
			tableName: table.name
		}
		
		const url = appConfig.restUrl + 
						util.fillTemplate(REST_BACKOFFICE_TABLE_PERSIST, { backOfficeId: backOffice.id });
			
		response = await webix.ajax().headers(CONTENT_TYPE_JSON).post(url, body);
		
		return response ? response.json() : undefined;
	}
	
	static async updateData(backOffice, table, requestInput, id) {
		
		let response;
		
		const body = {
			id: id,
			requestInput: requestInput,
			requestType: BackOfficeHelper.REQUEST_TYPE_UPDATE,
			tableName: table.name
		}
		
		const url = appConfig.restUrl + 
						util.fillTemplate(REST_BACKOFFICE_TABLE_PERSIST, { backOfficeId: backOffice.id });
			
		response = await webix.ajax().headers(CONTENT_TYPE_JSON).post(url, body);
		
		return response ? response.json() : undefined;
	}
	
	static async deleteData(backOffice, table, requestInput, id) {
		
		let response;
		
		const body = {
			id: id,
			requestInput: requestInput,
			requestType: BackOfficeHelper.REQUEST_TYPE_DELETE,
			tableName: table.name
		}
		
		const url = appConfig.restUrl + 
						util.fillTemplate(REST_BACKOFFICE_TABLE_PERSIST, { backOfficeId: backOffice.id });
			
		response = await webix.ajax().headers(CONTENT_TYPE_JSON).post(url, body);
		
		return response ? response.json() : undefined;
	}
	
	static async command(backOffice, table, commandId, requestInput, id) {
		
		let response;
		
		const body = {
			id: id,
			requestInput: requestInput,
			requestType: BackOfficeHelper.REQUEST_TYPE_COMMAND,
			tableName: table.name,
			commandId: commandId
		}
		
		const url = appConfig.restUrl + 
						util.fillTemplate(REST_BACKOFFICE_TABLE_COMMAND, { backOfficeId: backOffice.id });
			
		response = await webix.ajax().headers(CONTENT_TYPE_JSON).post(url, body);
		
		return response ? response.json() : undefined;
	}
	
	static async filler(backOffice, table, column, requestInput, id) {
		
		let response;
		
		const body = {
			id: id,
			requestInput: requestInput,
			requestType: BackOfficeHelper.REQUEST_TYPE_FILLER,
			tableName: table.name,
			columnName: column.name
		}
		
		const url = appConfig.restUrl + 
						util.fillTemplate(REST_BACKOFFICE_COLUMN_FILLER, { backOfficeId: backOffice.id });
			
		response = await webix.ajax().headers(CONTENT_TYPE_JSON).post(url, body);
		
		return response ? response.json() : undefined;
	}
	
	static getRefColumn(parentTable, referTableName, data) {
		
		let refColumn = undefined;
		
		if (parentTable && Array.isArray(parentTable.referTableNames) && data && referTableName) {
		
			const referTableNameFound = parentTable.referTableNames.find(e => e === referTableName);
			
			if (referTableNameFound) {
				
				const referTable = data.find(e => e.name === referTableNameFound);
					
				if (referTable) {
					
					refColumn = referTable.columns.find(e => e.refColumn && e.refColumn.tableId === parentTable.id)	
				}
			}
		}
		
		return refColumn;
	}
	
	static getReferencedColumnName(column, data) {
		
		let refColumnName = undefined;
		
		if (column.refColumn) {
			
			const refTable = data.find(table => table.id === column.refColumn.tableId);
			
			if (refTable) {
				
				if (Array.isArray(refTable.columns)) {
					
					refColumnName = refTable.columns.find(col => col.metadata && col.metadata.refName);
					
					if (!refColumnName) {
						
						for (let i = 0; !refColumnName && i < COL_NAMES_REF.length; i++) {
						
							refColumnName = refTable.columns.find(col => col.name.match(COL_NAMES_REF[i]));	
						}
					}
					
					if (!refColumnName) {
						
						refColumnName = refTable.columns.find(col => col.name === "id");
					}
				}
			}
		}
		
		return refColumnName;
	}

	static getReferencedColumnValue(column, valueId, data) {

		let refColumnValue = undefined;
		
		if (column.refColumn) {
			
			const refTable = data.find(table => table.id === column.refColumn.tableId);
			
			if (refTable) {
				
				let refColumnName = BackOfficeHelper.getReferencedColumnName(column, data);
					
				if (refColumnName) {
					
					refColumnValue = webix.copy(refColumnName);
					
					if (Array.isArray(refTable.rows) && refTable.rows.length > 0) {
						
						const row = refTable.rows.find(row => row.data.find(dat => dat.columnId === column.refColumn.id && dat.value == valueId) != null);
				
						if (row) {
							
							const rowColumnValue = row.data.find(dat => dat.columnId === refColumnName.id);
							
							if (rowColumnValue) {
							
								refColumnValue.value = rowColumnValue.value;
							}
						}
					}
				}
			}
		}
		
		return refColumnValue;
	}
	
	static getColumnValue(table, columnId, idx) {
		
		let value = undefined;
		
		if (table) {
			
			const column = table.columns.find(e => e.id === columnId);
			
			if (column && Array.isArray(table.rows) && table.rows.length > idx) {
				
				const rowData = table.rows[idx].data.find(e => e.columnId === column.id);
				
				if (rowData && rowData.value) {
					
					if (column.colType === BackOfficeHelper.DB_TYPE_TIMESTAMP ||
					    column.colType === BackOfficeHelper.DB_TYPE_INTEGER) {
						
						value = parseInt(rowData.value);
					}
					else if (column.colType === BackOfficeHelper.DB_TYPE_DECIMAL) {
						
						value = parseFloat(rowData.value);
					}
					else {
						
						value = rowData.value;	
					}
				}
			}
		}
		
		return value;
	}
	
	static getColumnUID(column) {
		
		return column.name + "$" + column.tableId + "$" + column.id;
	}
	
	static getColumnNameFromColumnUID(columnUID) {
		
		return columnUID.split("$")[0];
	}
	
	static getTableIdFromColumnUID(columnUID) {
		
		return parseInt(columnUID.split("$")[1]);
	}
	
	static getColumnIdFromColumnUID(columnUID) {
		
		return parseInt(columnUID.split("$")[2]);
	}
	
	static getRequestInput(formValues, table, allValues) {
		
		let requestInput = [];
		
		const values = formValues;
		
		if (typeof(values) === 'object') {
			
			const keys = Object.keys(values);

			if (Array.isArray(keys) && keys.length > 0) {
				
				keys.forEach(key => {
					
					if (key != "id") {

						const keySplitted = key.split("$");
					
						if (Array.isArray(keySplitted) && keySplitted.length > 2) {
							
							const tableId = parseInt(keySplitted[1]);

							if (tableId === table.id) {
								
								const colId = parseInt(keySplitted[2]);
								const column = table.columns.find(e => e.id === colId);
									
								if (column) {
									
									const oldValue = BackOfficeHelper.getColumnValue(table, colId, 0);
									
									let newValue = undefined;
									
									if (values[key]) {
										
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
									}
									
									if (oldValue !== newValue || allValues === true) {
										
										requestInput.push({
											colName: column.name,
											oldValue: oldValue,
											newValue: newValue,
											colType: column.colType
										});
									}
								}	
							}
						}
					}
				});
			}
		}
		
		return requestInput;
	}
}