import { util } from "../../lib/util.js";

const REST_BACKOFFICE_DATA = "backoffice/${backOfficeId}";

const REST_BACKOFFICE_TABLE = "backoffice/${backOfficeId}/table/${tableName}";
const REST_BACKOFFICE_TABLE_NODATA = "backoffice/${backOfficeId}/table/${tableName}/nodata";
const REST_BACKOFFICE_TABLE_PERSIST = "backoffice/${backOfficeId}/persist";
const REST_BACKOFFICE_TABLE_COMMAND = "backoffice/${backOfficeId}/command";
const REST_BACKOFFICE_COLUMN_FILLER = "backoffice/${backOfficeId}/filler";
const REST_BACKOFFICE_TRANSLATE = "backoffice/${backOfficeId}/translate/${label}";
const REST_GRIDS_ID = "grids/${id}";

export default class BackOfficeHelper {

	static get DB_TYPE_SHORT() { return "SHORT" }
	static get DB_TYPE_INTEGER() { return "INTEGER" }
	static get DB_TYPE_LONG() { return "LONG" }
	static get DB_TYPE_DECIMAL() { return "DECIMAL" }
	static get DB_TYPE_STRING() { return "STRING" }
	static get DB_TYPE_TIMESTAMP() { return "TIMESTAMP" }
	static get DB_TYPE_BOOLEAN() { return "BOOLEAN" }

	static get META_TYPE_BACKOFFICE() {
		return {
			name: "META_TYPE_BACKOFFICE",
			DEFAULT_METADATA: "defaultMetadataBackoffice",
			PROPERTY: {
				REPRESENTATIVE_COL_NAMES: { name: "representativeColNames", default: ["name", "alias", "description"] } 
			}
		}
	}
	static get META_TYPE_TABLE() {
		return {
			name: "META_TYPE_TABLE",
			DEFAULT_METADATA: "defaultMetadataTable",
			PROPERTY: {
				FORM_HEIGHT: { name: "formHeight", default: 700 },
				FORM_WIDTH: { name: "formWidth", default: 1000 },
				GRID_HEIGHT: { name: "gridHeight", default: 700 },
				GRID_WIDTH: { name: "gridWidth", default: 1000 },
				SHOW_ID_FORM: { name: "showIdForm", default: true },
				SHOW_ID_TABLE: { name: "showIdTable", default: false },
				SHOW_ID_SEARCH: { name: "showIdSearch", default: false },
				GRID_FILTERS: { name: "gridFilters", default: false },
				INITIAL_TAB_LABEL: { name: "initialTabLabel" },
				TABS: { name: "tabs" },
				AUTOREFRESH: { name: "autorefresh" },
				AUTOREFRESH_FORM: { name: "autorefresh" },
				REFER_TABLES: { name: "referTables" },
				GRID_TABLES: { name: "gridTables" },
				GRID_TABLES_COLS_ORIENTATION: { name: "gridTablesColsOrientation", default: false },
				GROUP_IMAGES: { name: "groupImages" },
				GROUP_ATTACHMENTS: { name: "groupAttachments" },				
				PRINTABLE: { name: "printable", default: true },
				EXPORTABLE: { name: "exportable", default: true },
				IMPORTABLE: { name: "importable", default: false },
				CUSTOM_JS: { name: "customJS" },
				EDITABLE: { name: "editable", default: true },
				VIRTUAL_COLS: { name: "virtualCols" },
				MANAGEMENT_AREA_FILTERS: { name: "managementAreaFilters" },
				FILE_PREVIEW_HEIGHT: {name: "filePreviewHeight", default: 450},
				FILE_PREVIEW_WIDTH: {name: "filePreviewWidth", default: 750},
				TABLE_RATIO_PERCENT: {name: "tableRatioPercent", default: 50}  				
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
				FORM_VISIBLE: { name: "formVisible", default: true },
				TABLE_VISIBLE: { name: "tableVisible", default: true },
				SEARCH_VISIBLE: { name: "searchVisible", default: false },
				REF_ADD_BUTTON: { name: "refAddButton", default: true },
				REF_EDIT_BUTTON: { name: "refEditButton", default: true },
				GRID_FILTERS: { name: "gridFilters", default: false },
				POSITION: { name: "position" },
				REF_NAME: { name: "refName" },
				MULTILINE: { name: "multiline" },
				ASSOCIATED_FIELD_NAME: { name: "associatedFieldName" },
				REF_GROUP_FIELD_NAME: { name: "refGroupFieldName" },
				BADGE_IN_HEADER: { name: "badgeInHeader" },
				SHOW_IN_FILTER: {name: "showInFilter", default: true}
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

		let locale = webix.i18n.locales["en-US"];
		
		if (webix.i18n.locales[i18n.localeCode]) {
			
			locale = webix.i18n.locales[i18n.localeCode];
		}
		else {
		
			log.warn("No locale js file for: " + i18n.localeCode + " Set default locale to en-US")
		}

		dbType[BackOfficeHelper.DB_TYPE_SHORT] = { id: 1, name: BackOfficeHelper.DB_TYPE_SHORT, component: { view: "text", required: false, labelWidth: 275 } };
		dbType[BackOfficeHelper.DB_TYPE_INTEGER] = { id: 2, name: BackOfficeHelper.DB_TYPE_INTEGER, component: { view: "text", required: false, labelWidth: 275 } };
		dbType[BackOfficeHelper.DB_TYPE_LONG] = { id: 3, name: BackOfficeHelper.DB_TYPE_LONG, component: { view: "text", required: false, labelWidth: 275 } };
		dbType[BackOfficeHelper.DB_TYPE_DECIMAL] = { id: 4, name: BackOfficeHelper.DB_TYPE_DECIMAL, component: { view: "text", required: false, labelWidth: 275 } };
		dbType[BackOfficeHelper.DB_TYPE_STRING] = { id: 5, name: BackOfficeHelper.DB_TYPE_STRING, component: { view: "text", required: false, labelWidth: 275 } };

		if (locale) {
			dbType[BackOfficeHelper.DB_TYPE_TIMESTAMP] = { id: 6, name: BackOfficeHelper.DB_TYPE_TIMESTAMP, component: { view: "datepicker", timepicker: true, required: false, labelWidth: 275, format: (locale.dateFormat + " " + locale.timeFormat) } };
		}
		
		dbType[BackOfficeHelper.DB_TYPE_BOOLEAN] = { id: 7, name: BackOfficeHelper.DB_TYPE_BOOLEAN, component: { view: "checkbox", required: false, labelWidth: 275 } };

		return dbType[name];
	}

	static getMetadata(metaType, prop, defaultMetadata, object) {

		let value = undefined;

		if (metaType.name === BackOfficeHelper.META_TYPE_BACKOFFICE.name ||
			metaType.name === BackOfficeHelper.META_TYPE_TABLE.name ||
			metaType.name === BackOfficeHelper.META_TYPE_COLUMN.name) {

			value = prop.default ?? value;

			if (defaultMetadata) {

				if (defaultMetadata[metaType.DEFAULT_METADATA] && defaultMetadata[metaType.DEFAULT_METADATA][prop.name] !== undefined) {

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

		let data;
		let response;
		let url = appConfig.restUrl +
					util.fillTemplate(REST_BACKOFFICE_TABLE, { backOfficeId: backOffice.id, tableName: backOffice.tableName });

		let conditions = undefined;

		if (backOffice.filterColumnName && backOffice.filterColumnValue) {

			addCondition(backOffice.filterColumnName + "='" + backOffice.filterColumnValue + "'");
			
			response = await webix.ajax().headers(CONTENT_TYPE_JSON).post(url, conditions ? conditions.join(" AND ") : undefined);
		}
		else {
		
			if (Array.isArray(backOffice.filters)) {
			
				const userData = util.user.getUserData();
				
				if (userData) {
					
					let filters = [];
					
					filters = backOffice.filters.filter(e => e.userId == userData.id);
					
					if (Array.isArray(filters) && filters.length > 0) {
						
						filters.forEach(filter => {
							
							addCondition(filter.filterField + " " + filter.filterOperator + " '" + filter.filterValue + "'");
						})	
					}
				}
			}
			
			
			
			if ((Array.isArray(conditions) && conditions.length > 0) || !backOffice.filtersRequired) {
					
				response = await webix.ajax().headers(CONTENT_TYPE_JSON).post(url, conditions ? conditions.join(" AND ") : undefined);
			}
			else {
				
				url = appConfig.restUrl +
					util.fillTemplate(REST_BACKOFFICE_TABLE_NODATA, { backOfficeId: backOffice.id, tableName: backOffice.tableName });
				
				response = await webix.ajax().get(url);
			}
		}
		
		data = response ? response.json() : undefined;
		
		filtersRowsByUserManagementArea();
		
		
		
		function addCondition(conditionExpression) {
			
			if (!conditions) {
				
				conditions = [];
			}
			
			conditions.push(conditionExpression);
		}
		
		function filtersRowsByUserManagementArea() {
					
			const sessionData = webix.storage.session.get(SESSION_STORAGE_SESSION_DATA);
				
			if (sessionData) {
			
				const managementAreaId = sessionData.managementAreaId;
				
				if (managementAreaId) {
					
					let currentTableData = data.find(e => e.name === backOffice.tableName);
					
					if (currentTableData) {
						
						const managementAreaFilters = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.MANAGEMENT_AREA_FILTERS, backOffice.metadata, currentTableData);
					
						if (Array.isArray(managementAreaFilters) && managementAreaFilters.length > 0) {
							
							const managementAreaFilter = managementAreaFilters.find(e => e.managementAreaId === managementAreaId);
	
							if (managementAreaFilter && Array.isArray(managementAreaFilter.filters) && managementAreaFilter.filters.length > 0) {
										
								let rowsFiltered = [];
								
								if (Array.isArray(currentTableData.rows) && currentTableData.rows.length > 0) {
							
									currentTableData.rows.forEach(row => {
										
										let matchRow = true;
								
										managementAreaFilter.filters.every(filter => {
											
											const column = currentTableData.columns.find(e => e.name == filter.filterField);
											
											if (column) {
												
												matchRow &= (row.data.find(rowData => rowData.columnId === column.id && rowData.value == filter.filterValue) != undefined);
											}
											
											return matchRow;
										})
										
										if (matchRow) {
											
											rowsFiltered.push(row);
										}
									})		
								}
								
								currentTableData.rows = rowsFiltered;	
							}
						}	
					}
				}
			}
		}

		return data; 
	}
	
	static translate(backOffice, label) {

		const url = appConfig.restUrl +
			util.fillTemplate(REST_BACKOFFICE_TRANSLATE, { backOfficeId: backOffice.id, label: label });

		const translationResponse = webix.ajax().sync().get(url);

		return (translationResponse && translationResponse.responseText) ? translationResponse.responseText : "¿¿" + label + "??";
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

	static async deleteData(backOffice, table, id) {

		let response;

		const body = {
			id: id,
			requestType: BackOfficeHelper.REQUEST_TYPE_DELETE,
			tableName: table.name
		}

		const url = appConfig.restUrl +
			util.fillTemplate(REST_BACKOFFICE_TABLE_PERSIST, { backOfficeId: backOffice.id });

		response = await webix.ajax().headers(CONTENT_TYPE_JSON).post(url, body);

		return response ? response.json() : undefined;
	}

	static async command(backOffice, table, commandId, requestInput, requestParams, id) {

		let response;

		const body = {
			id: id,
			requestInput: requestInput,
			requestParams: requestParams,
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

	static async getGrid(gridId) {

		let response;

		response = await webix.ajax().get(appConfig.restUrl + util.fillTemplate(REST_GRIDS_ID, { id: gridId }));

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

	static getRepresentativeField(backOffice, table) {

		let refColumnName = undefined;

		if (Array.isArray(table.columns)) {

			refColumnName = table.columns.find(col => col.metadata && col.metadata[BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.REF_NAME.name]);
			
			const representativeColNames = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_BACKOFFICE, 
										 			BackOfficeHelper.META_TYPE_BACKOFFICE.PROPERTY.REPRESENTATIVE_COL_NAMES, 
										 			backOffice.metadata);

			if (!refColumnName && representativeColNames) {
				
				if (Array.isArray(representativeColNames)) {

					for (let i = 0; !refColumnName && i < representativeColNames.length; i++) {
						
						const regExp = new RegExp(representativeColNames[i], "i");

						refColumnName = table.columns.find(col => col.name.match(regExp));
					}					
				}
			}

			if (!refColumnName) {

				refColumnName = table.columns.find(col => col.primaryKey === true);
			}
		}

		return refColumnName;
	}

	static getReferencedColumnName(backOffice, column, data) {

		let refColumnName = undefined;

		if (column.refColumn) {

			const refTable = data.find(table => table.id === column.refColumn.tableId);

			if (refTable) {

				refColumnName = BackOfficeHelper.getRepresentativeField(backOffice, refTable);
			}
		}

		return refColumnName;
	}

	static getReferencedColumnValue(backOffice, column, valueId, data) {

		let refColumnValue = undefined;

		if (column.refColumn) {

			const refTable = data.find(table => table.id === column.refColumn.tableId);

			if (refTable) {

				let refColumnName = BackOfficeHelper.getReferencedColumnName(backOffice, column, data);

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
						column.colType === BackOfficeHelper.DB_TYPE_SHORT ||
						column.colType === BackOfficeHelper.DB_TYPE_INTEGER ||
						column.colType === BackOfficeHelper.DB_TYPE_LONG) {

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

		if (typeof (values) === 'object') {

			const keys = Object.keys(values);
			
			const columnId = table.columns.find(e => e.primaryKey === true);

			if (Array.isArray(keys) && keys.length > 0 && columnId) {

				keys.forEach(key => {

					if (key != columnId.name) {

						const keySplitted = key.split("$");

						if (Array.isArray(keySplitted) && keySplitted.length == 3) {

							const tableId = parseInt(keySplitted[1]);

							if (tableId === table.id) {

								const colId = parseInt(keySplitted[2]);
								const column = table.columns.find(e => e.id === colId);

								if (column) {

									const oldValue = BackOfficeHelper.getColumnValue(table, colId, 0);

									let newValue = undefined;

									if (values[key]) {

										if (column.colType === BackOfficeHelper.DB_TYPE_SHORT ||
											column.colType === BackOfficeHelper.DB_TYPE_INTEGER ||
											column.colType === BackOfficeHelper.DB_TYPE_LONG) {

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
	
	static getVirtualColumns(backOffice, table, fkColumn, data) {

		let virtualColumns = undefined;
		let virtualColumnsMetadata = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, 
																	BackOfficeHelper.META_TYPE_TABLE.PROPERTY.VIRTUAL_COLS, 
																	backOffice.metadata, table);
		
		const refTable = data.find(e => fkColumn.refColumn && e.id === fkColumn.refColumn.tableId);
		
		if (refTable && Array.isArray(virtualColumnsMetadata) && virtualColumnsMetadata.length > 0) {
			
			virtualColumns = [];
			
			virtualColumnsMetadata = virtualColumnsMetadata.filter(e => e.tableName === refTable.name);
			
			virtualColumnsMetadata.forEach(virtualColumnMetadata => {
				
				const refTableColumn = refTable.columns.find(col => col.name === virtualColumnMetadata.columnName);
				
				if (refTableColumn) {
					
					let virtualColumn = webix.copy(refTableColumn);
					
					virtualColumn.virtualColumnMetadata = virtualColumnMetadata;
					
					virtualColumns.push(virtualColumn);	
				}
			});
		}
		
		return virtualColumns;	
	}
	
	static getVirtualColumnValue(virtualColumn, valueId, data) {
		
		let virtualColumnValue = undefined;
		
		const refTable = data.find(e => e.id === virtualColumn.tableId);
		
		if (refTable) {
			
			const refTableColumnPk = refTable.columns.find(e => e.primaryKey === true);
			
			if (refTableColumnPk) {
				
				virtualColumnValue = webix.copy(virtualColumn);

				if (Array.isArray(refTable.rows) && refTable.rows.length > 0) {
	
					const row = refTable.rows.find(row => row.data.find(dat => dat.columnId === refTableColumnPk.id && dat.value == valueId) != null);
	
					if (row) {
	
						const rowColumnValue = row.data.find(dat => dat.columnId === virtualColumn.id);
	
						if (rowColumnValue) {
	
							virtualColumnValue.value = rowColumnValue.value;
						}
					}
				}
			} 
		}

		return virtualColumnValue;
	}
}