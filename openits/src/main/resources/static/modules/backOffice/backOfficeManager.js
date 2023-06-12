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
	
	getView(onCreate) {
		
		let self = this;
		let view;
		
		let currentTableData = undefined;
		
		if (Array.isArray(self.data)) {
			
			currentTableData = self.data.find(e => e.name === self.backOffice.tableName);
		}
		
		if (currentTableData) {
			
			if (self.backOffice.filterColumnName && self.backOffice.filterColumnValue) {
				
				if (Array.isArray(currentTableData.rows)) {
					
					if (currentTableData.rows.length === 1) {
					
						loadForm(currentTableData);	
					}
					else if (currentTableData.rows.length === 0) {
							
						create();	
					}
					else {
						
						loadGrid(currentTableData);
					}
				}		
			}
			else {
				
				loadGrid(currentTableData);
			}
		}
	
		function create() {
			
			let column = currentTableData.columns.find(e => e.name === self.backOffice.filterColumnName && self.backOffice.filterColumnName !== "id");
			
			if (column) {
				
				(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_CREATE_NEW_RECORDS_ON_TABLE") + " " + (currentTableData.label ?? currentTableData.name) + i18n.t("LBL_BACKOFFICE_WITH_CONDITIONS") + ": " + self.backOffice.filterColumnName + "=" + self.backOffice.filterColumnValue, null, onAccept , null)).show();
		
				function onAccept() {
					
					let persistValues = [];
					
					if (self.backOffice.filterColumnName && self.backOffice.filterColumnValue) {
										
						persistValues.push({
							colName: self.backOffice.filterColumnName,
							oldValue: undefined,
							newValue: self.backOffice.filterColumnValue,
							colType: BackOfficeHelper.DB_TYPE_INTEGER
						});
						
						BackOfficeHelper.insertData(self.backOffice, currentTableData, persistValues).then((persistResult) => {
									
							if (!persistResult.error) {
								
								if (persistResult.newRow && Array.isArray(persistResult.newRow.data)) {
									
									const columnId = currentTableData.columns.find(e => e.name === "id");
									
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
							
												BackOfficeHelper.deleteData(self.backOffice, currentTableData, persistValues, columnIdValue).then((persistResult) => {
													
													if (persistResult.error) {
					
														log.error(persistResult.error + "\n" + persistResult.errorDescription);
														
														(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_SAVING_CHANGES_ON") + " " + (currentTableData.label ?? currentTableData.name), persistResult.error + "\n" + persistResult.errorDescription, null, null)).show();
													}	
												});
											});			
										}	
									}
								}
							}
							else {
								
								log.error(persistResult.error + "\n" + persistResult.errorDescription);
								
								(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_SAVING_CHANGES_ON") + " " + (currentTableData.label ?? currentTableData.name), persistResult.error + "\n" + persistResult.errorDescription, null, null)).show();
							}
						});
					}
				}
				
				function edit(id, onCancel) {
				
					const module = window.em.getByField(EntityType.MODULE, "id", BackOfficeModule.Config.MODULE_ID);
					const formHeight = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_HEIGHT, self.backoffice.metadata);
					const formWidth = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_WIDTH, self.backoffice.metadata);

					if (module) {
						
//						const winId = util.ui.loadWindow(undefined, { id: self.uid }, BackOfficeModule.Config.DEFAULT_FORM_HEIGHT, 
//															BackOfficeModule.Config.DEFAULT_FORM_WIDTH, true);
															
						const winId = util.ui.loadWindow(undefined, { id: self.uid }, formHeight, formWidth, true);
							const args = {
								backOffice: {
									id: self.backOffice.id,
									tableName: currentTableData.name,
									filterColumnName: "id",
									filterColumnValue: id,
									isModal: true,
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
							//util.ui.resizeWindow(winId, BackOfficeModule.Config.DEFAULT_FORM_WIDTH, BackOfficeModule.Config.DEFAULT_FORM_HEIGHT);
							
							util.ui.resizeWindow(winId, formWidth, formHeight);
							util.ui.centerWindow(winId, 20);	
						}
						
						function onAccept(values) {
							
							log.debug(values);
						}
					}
				}
				
			}
			else {
				
				(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_CANNOT_CREATE_NEW_RECORD") + ": " + self.backOffice.tableName + i18n.t("LBL_BACKOFFICE_WITH_CONDITIONS") + ": " + self.backOffice.filterColumnName + "=" + self.backOffice.filterColumnValue)).show();
			}
			
			if (onCreate) {
				
				onCreate();
			}
		}
				
		function loadGrid(currentTableData) {
			
			let backOfficeView = new BackOfficeViewGrid(self.backOffice, self.data, currentTableData);
			
			view = backOfficeView.getView();
		}
		
		function loadForm(currentTableData) {
			
			let backOfficeView = new BackOfficeViewForm(self.backOffice, self.data, currentTableData);
			
			view = backOfficeView.getView();
		}
		
		return view;
	}
}