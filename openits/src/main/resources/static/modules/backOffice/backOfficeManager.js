import BackOfficeViewGrid from "./backOfficeViewGrid.js";
import BackOfficeViewForm from "./backOfficeViewForm.js";
import BackOfficeModule from "./backOfficeModule.js";
import BackOfficeHelper from "./backOfficeHelper.js";
import { util } from "../../lib/util.js";
import MessageBox from "../../component/messageBox.js";

export default class BackOfficeManager {
	
	constructor(backOffice, data) {

		this.backOffice = backOffice;
		this.data = data;
		this.uid = webix.uid();
	}
	
	getView(onCreate, onGetView) {
		
		let self = this;
		
		let currentTableData = undefined;
		
		if (Array.isArray(self.data)) {
			
			currentTableData = self.data.find(e => e.name === self.backOffice.tableName);
		}
		
		if (currentTableData) {
			
			if (self.backOffice.filterColumnName && self.backOffice.filterColumnValue) {
				
				if (Array.isArray(currentTableData.rows)) {
					
					if (currentTableData.rows.length === 1) {
					
						loadForm(currentTableData, onGetView);	
					}
					else if (currentTableData.rows.length === 0) {
						
						if (onCreate) {
				
							onCreate();
						}
						
						let column = currentTableData.columns.find(e => e.name === self.backOffice.filterColumnName && e.primaryKey !== true);
			
						if (column) {
							
							(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_CREATE_NEW_RECORDS_ON_TABLE") + " " + (currentTableData.label ?? currentTableData.name) + " " + i18n.t("LBL_BACKOFFICE_WITH_CONDITIONS") + ": " + self.backOffice.filterColumnName + "=" + self.backOffice.filterColumnValue + "?", null, create , null)).show();
						}
						else {
				
							(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_CANNOT_CREATE_NEW_RECORD") + ": " + self.backOffice.tableName + i18n.t("LBL_BACKOFFICE_WITH_CONDITIONS") + ": " + self.backOffice.filterColumnName + "=" + self.backOffice.filterColumnValue)).show();
						}
					}
					else {
						
						loadGrid(currentTableData, onGetView);
					}
				}		
			}
			else {
				
				if (self.backOffice.moduleActionId == BackOfficeModule.Config.MODULE_ACTION_NEW) {
					
					if (onCreate) {
				
						onCreate();
					}
					
					create(onCancelCreate);
				}
				else {
				
					loadGrid(currentTableData, onGetView);	
				}
			}
		}
		
		function onCancelCreate(columnIdValue) {
			
			BackOfficeHelper.deleteData(self.backOffice, currentTableData, columnIdValue).then((requestResult) => {
											
				if (requestResult.requestResultMessage.error) {
					
					log.error(requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription);
					
					(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_SAVING_CHANGES_ON") + " " + (self.currentTableData.label ?? self.currentTableData.name), requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription, null, null)).show();
				}	
			});
		}
		
		function create(onCancel) {
			
			let persistValues = [];
			
			if (self.backOffice.filterColumnName && self.backOffice.filterColumnValue) {
								
				persistValues.push({
					colName: self.backOffice.filterColumnName,
					oldValue: undefined,
					newValue: self.backOffice.filterColumnValue,
					colType: BackOfficeHelper.DB_TYPE_STRING
				});
			}
				
			BackOfficeHelper.insertData(self.backOffice, currentTableData, persistValues).then((requestResult) => {
						
				if (!requestResult.requestResultMessage.error) {
					
					if (requestResult.newRow && Array.isArray(requestResult.newRow.data)) {
						
						const columnId = currentTableData.columns.find(e => e.primaryKey === true);
						
						if (columnId) {
							
							const data = requestResult.newRow.data.find(e => e.columnId === columnId.id);
							
							if (data && data.value) {
								
								const columnIdValue = data.value;
								
								let mod = util.module.getFromModuleAction(BackOfficeModule.Config.MODULE_ACTION_OPEN);

								let moduleAction = { id: BackOfficeModule.Config.MODULE_ACTION_OPEN };
								
								let args = {
									backOffice: {
										id: self.backOffice.id,
										tableName: currentTableData.name,
										filterColumnName: columnId.name,
										filterColumnValue: columnIdValue,
										onCancel: onCancel ? onCancelCallback : undefined
									}
								};
								
								util.module.open(mod, moduleAction, VIEW_TYPE_WINDOW, null, args);
								
								function onCancelCallback() {
									
									onCancel(columnIdValue);
								}
							}	
						}
					}
				}
				else {
					
					log.error(requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription);
					
					(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_SAVING_CHANGES_ON") + " " + (currentTableData.label ?? currentTableData.name), requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription, null, null)).show();
				}
			});
			
		}
				
		function loadGrid(currentTableData, onGetView) {
			
			let view = undefined;
			let backOfficeView = new BackOfficeViewGrid(self.backOffice, self.data, currentTableData);
			
			view = backOfficeView.getView();
			
			if (onGetView && view) {
				
				onGetView(view);
			}
		}
		
		function loadForm(currentTableData, onGetView) {
			
			let view = undefined;
			let backOfficeView = undefined;
			
			if (currentTableData.metadata && currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.CUSTOM_JS.name]) {			
			
				const lazy = async _ => {
					
					let jsFile = util.getContextPath() + "/templates/" + currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.CUSTOM_JS.name];
	
					const mod = await import(jsFile);
					
					backOfficeView = new mod.default(self.backOffice, self.data, currentTableData);
					
					view = backOfficeView.getView();
					
					if (onGetView && view) {
				
						onGetView(view);
					}
				}
				
				lazy();
			}
			else {
				
				backOfficeView = new BackOfficeViewForm(self.backOffice, self.data, currentTableData);
			
				view = backOfficeView.getView();
				
				if (onGetView && view) {
				
					onGetView(view);
				}
			}
		}
	}
}