import { util } from "../../lib/util.js";

const REST_BACKOFFICE_TABLE = "backoffice/${backOfficeId}/table/${tableName}";
const REST_BACKOFFICE_TABLE_PERSIST = "backoffice/${backOfficeId}/persist";
const COL_NAMES_REF = [/name/i, /description/i, /alias/i];

export default class BackOfficeHelper {
	
	static get DB_TYPE_STRING() { return "STRING" }
	static get DB_TYPE_INTEGER() { return "INTEGER" }
	static get DB_TYPE_DECIMAL() { return "DECIMAL" }
	static get DB_TYPE_TIMESTAMP() { return "TIMESTAMP" }
	
	static get PERSISTENCE_TYPE_INSERT() { return "INSERT" }
	static get PERSISTENCE_TYPE_UPDATE() { return "UPDATE" }
	static get PERSISTENCE_TYPE_DELETE() { return "DELETE" }
		
	static getDBTypes(name) {
		
		const dbType = {};
		
		dbType[BackOfficeHelper.DB_TYPE_STRING] = { id: 1, name: BackOfficeHelper.DB_TYPE_STRING, component: { view: "text", required: false, labelWidth: 275 }};
		dbType[BackOfficeHelper.DB_TYPE_INTEGER] = { id: 2, name: BackOfficeHelper.DB_TYPE_INTEGER, component: { view: "text", required: false, labelWidth: 275 }};
		dbType[BackOfficeHelper.DB_TYPE_DECIMAL] = { id: 3, name: BackOfficeHelper.DB_TYPE_DECIMAL, component: { view: "text", required: false, labelWidth: 275 }},
		dbType[BackOfficeHelper.DB_TYPE_TIMESTAMP] = { id: 4, name: BackOfficeHelper.DB_TYPE_TIMESTAMP, component: { view: "datepicker", timepicker: true, required: false, labelWidth: 275, format: (webix.i18n.locales[i18n.localeCode].dateFormat + " " + webix.i18n.locales[i18n.localeCode].timeFormat)}} 
		
		return dbType[name];
	}
	
	static async getData(backOffice) {
		
		let response;
		
		const url = appConfig.restUrl + 
						util.fillTemplate(REST_BACKOFFICE_TABLE, { backOfficeId: backOffice.id, tableName: backOffice.tableName });
			
		if (backOffice.filterColumnName && backOffice.filterColumnValue) {
			
			const conditions = backOffice.filterColumnName + "=" + backOffice.filterColumnValue
		
			response = await webix.ajax().headers(CONTENT_TYPE_JSON).post(url, conditions);
		}
		else {
		
			response = await webix.ajax().get(url);	
		}
		
		return response ? response.json() : undefined;
	}
	
	static async insertData(backOffice, table, persistValues) {
		
		let response;
		
		const body = {
			id: undefined,
			persistValues: persistValues,
			persistenceType: BackOfficeHelper.PERSISTENCE_TYPE_INSERT,
			tableName: table.name
		}
		
		const url = appConfig.restUrl + 
						util.fillTemplate(REST_BACKOFFICE_TABLE_PERSIST, { backOfficeId: backOffice.id });
			
		response = await webix.ajax().headers(CONTENT_TYPE_JSON).post(url, body);
		
		return response ? response.json() : undefined;
	}
	
	static async updateData(backOffice, table, persistValues, id) {
		
		let response;
		
		const body = {
			id: id,
			persistValues: persistValues,
			persistenceType: BackOfficeHelper.PERSISTENCE_TYPE_UPDATE,
			tableName: table.name
		}
		
		const url = appConfig.restUrl + 
						util.fillTemplate(REST_BACKOFFICE_TABLE_PERSIST, { backOfficeId: backOffice.id });
			
		response = await webix.ajax().headers(CONTENT_TYPE_JSON).post(url, body);
		
		return response ? response.json() : undefined;
	}
	
	static async deleteData(backOffice, table, persistValues, id) {
		
		let response;
		
		const body = {
			id: id,
			persistValues: persistValues,
			persistenceType: BackOfficeHelper.PERSISTENCE_TYPE_DELETE,
			tableName: table.name
		}
		
		const url = appConfig.restUrl + 
						util.fillTemplate(REST_BACKOFFICE_TABLE_PERSIST, { backOfficeId: backOffice.id });
			
		response = await webix.ajax().headers(CONTENT_TYPE_JSON).post(url, body);
		
		return response ? response.json() : undefined;
	}
	
	static getRefColumn(parentTable, referTableId) {
		
		let refColumn = undefined;
		
		if (parentTable && Array.isArray(parentTable.referTables)) {
		
			const referTable = parentTable.referTables.find(e => e.id === referTableId);
			
			if (referTable) {
				
				refColumn = referTable.columns.find(e => e.refColumn && e.refColumn.tableId === parentTable.id)
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
				
				if (rowData) {
					
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
}