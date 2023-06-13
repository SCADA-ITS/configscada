import BackOfficeView from "./backOfficeView.js";
import BackOfficeViewFormField from "./backOfficeViewFormField.js";
import BackOfficeViewGrid from "./backOfficeViewGrid.js";
import BackOfficeHelper from "./backOfficeHelper.js";
import MessageBox from "../../component/messageBox.js";
import { util } from "../../lib/util.js";

export default class BackOfficeViewForm extends BackOfficeView {

	constructor(backOffice, data, currentTableData) {

		super(backOffice, data, currentTableData);
		this.accepted = false;
		this.formHeight = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_HEIGHT, backOffice.metadata, currentTableData);
		this.formWidth = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_WIDTH, backOffice.metadata, currentTableData);
		
		if (backOffice.containerId) {
			
			util.ui.resizeWindow(backOffice.containerId, this.formWidth, this.formHeight);
			util.ui.centerWindow(backOffice.containerId);
		}
	}
	
	getView() {
		
		let self = this;
		let view;
		
		view = getViewContent();
		
		function getViewContent() {

			let viewContent;

			viewContent = {
				id: "backOffice-view-" + self.uid,
				css: { "box-sizing": "border-box" },
				rows: [
					getHeader(),
					{ height: 15 },
					{
						cols: [
							{ width: 15 },
							getTabs(),
							{ width: 15 }
						]
					}
				]
			};
			
			function getHeader() {

				let header;
				const label = (self.backOffice.onCancel ? i18n.t("LBL_NEW") : i18n.t("LBL_EDIT")) + " " + (self.currentTableData.labelSingular ?? (self.currentTableData.label ?? self.currentTableData.name));
				const description = self.currentTableData.labelDescription ?? "";
				
				const icon = self.currentTableData.metadata && self.currentTableData.metadata.mdiIcon ? self.currentTableData.metadata.mdiIcon : "mdi mdi-application"; 

				header = {
					id: "viewHeader-" + self.uid,
					cols: [
						{
							view: "label",
							height: 75,
							template: `<span class='${icon}'></span>`,
							css: "global-header-mdi"
						},
						{
							view: "label",
							height: 75,
							width: 450,
							template: "<span>" + label + "</span> <br> <span style='font-size: 13px'>" + description + "</span>",
							css: "global-header-label",
							on: {
								onDestruct: function() {
									
									if (self.backOffice.onCancel && !self.accepted) {
										
										self.backOffice.onCancel();
									}		
								}
							}
						}
					]
				};

				return header;
			}

			function getTabs() {

				let tabs;

				tabs = {
					view: "tabview",
					id: "tabview-" + self.uid,
					borderless: true,
					css: "secondary-tab",
					cells: [],
					tabbar: {
						tabMargin: 6,
					}
				}

				tabs.cells.push({
					header: i18n.t("LBL_INFO"),
					body: getInfoView(),
				});
				
				if (!self.backOffice.noReferTables) {
					
					pushReferTables();	
				}

				function getInfoView() {

					let infoView;
					
					infoView = {
						rows: [
							{
								id: "form-" + self.uid,
								view: "form",
								css: "bordered-layout-without-border-top",
								elementsConfig: { labelAlign: "left" },
								elements: getElements()
							},
							{ height: 15 },
							getButtons()
						]
					};
					
					function getElements() {
						
						let elements = [];
						
						if (Array.isArray(self.currentTableData.columns) &&
							self.currentTableData.columns.length > 0) {
						
							if (self.currentTableData.metatada && 
								Array.isArray(self.currentTableData.metatada.tabs) && 
								self.currentTableData.metatada.tabs.length > 0) {
									
								let tabInfo = {
									view: "tabview",
									borderless: true,
									css: "auxiliary-tab",
									cells: [],
									tabbar: {
										tabMargin: 6,
									}
								}
									
								self.currentTableData.metatada.tabs.forEach(tab => {

									tabInfo.cells.push({
										
										header: tab.label,
										body: {
											view: "scrollview",
											scroll: "auto",
											borderless: true,
											body: {
												cols: [
													{ width: 45 },
													{
														rows: getFields(tab)
													},
													{ width: 45 }
												]
											}
										}
									});
								});
								
								elements.push(tabInfo);
							}
							else {
																
								let info = {
									view: "scrollview",
									scroll: "y",
									borderless: true,
									body: {
										cols: [
											{ width: 45 },
											{
												rows: getFields()
											},
											{ width: 45 }
										]
									}
								}
								
								elements.push(info);
							}		
						}
						
						return elements;
					}
					
					function getFields(tab) {
						
						let fields = [{ height: 45 }];
						
						let backOfficeViewFormField = new BackOfficeViewFormField(self);
						
						if (tab && Array.isArray(tab.fields) && tab.fields.length > 0) {
						
							tab.fields.forEach(field => {
								
								fields.push(backOfficeViewFormField.getField(self.currentTableData.columns.find(e => e.name === field.name)));
							});
						}
						else {
							
							self.currentTableData.columns.forEach(column => {
								
								fields.push(backOfficeViewFormField.getField(column));	
							});
						}	
						
						return fields;
					}
					
					function getButtons() {

						let viewToolbarActions = {};
						
						const container = $$(self.backOffice.containerId);
		
						viewToolbarActions = {
							id: "buttons-" + self.uid,
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
											click:
												function() {
													
													if (self.backOffice.onCancel) {
														
														self.backOffice.onCancel()
													}
													
													if (container) {
														
														container.close();	
													}
												}
										},
										{},
										{
											id: "btn-accept-" + self.uid,
											view: "button",
											value: i18n.t("LBL_ACCEPT"),
											disabled: !self.backOffice.onCancel,
											width: 130,
											height: 41,
											css: "dialog-accept",
											click:
												function() {
		
													let validation = $$("form-" + self.uid).validate({ disabled: true });
		
													if (validation) {
		
														persistUpdate();
		
													} else {
		
														$$("form-" + self.uid).show();
													}
												}
										},
										{ width: 15 }
									]
								}
							]
						}
						
						function persistUpdate() {
							
							const id = getIdToPersist();
							
							const form = $$("form-" + self.uid);
												
							if (form) {
													
								const persistValues = BackOfficeHelper.getPersistValues(form.getValues(), self.currentTableData);
							
								if (id && ((Array.isArray(persistValues) && persistValues.length > 0) || self.backOffice.onCancel)) {
									
									(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_SAVE_CHANGES_ON") + " " + (self.currentTableData.label ?? self.currentTableData.name), null, onAccept , null)).show();
								}
								else {
									
									if (container) {
						
										container.close();	
									}
								}
								
								function onAccept() {
							
									self.accepted = true;
									
									const form = $$("form-" + self.uid);
									
									if (form && self.backOffice.onAccept) {
										
										const values = form.getValues();
										
										if (typeof(values) === 'object') {
										
											self.backOffice.onAccept();	
											
											BackOfficeHelper.updateData(self.backOffice, self.currentTableData, persistValues, id).then((persistResult) => {
														
												if (!persistResult.error) {
													
													self.backOffice.onAccept(values);
											
													if (container) {
											
														container.close();	
													}
												}
												else {
													
													log.error(persistResult.error + "\n" + persistResult.errorDescription);
													
													(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_SAVING_CHANGES_ON") + " " + (self.currentTableData.label ?? self.currentTableData.name), persistResult.error + "\n" + persistResult.errorDescription, null, null)).show();
												}
											});
										}
									}
								}
							}
						}
		
						return viewToolbarActions;
					}

					return infoView;
				}
				
				function getIdToPersist() {
							
					const form = $$("form-" + self.uid);
					let id = undefined;
					
					if (form) {
						
						const values = form.getValues();
						
						if (typeof(values) === 'object') {
							
							const keys = Object.keys(values);

							if (Array.isArray(keys) && keys.length > 0) {
								
								const key = keys.find(e => e === "id");
								
								if (key) {
									
									id = parseInt(values[key]);
								}
							}
						}
					}
					
					return id;
				}
				
				function pushReferTables() {
					
					if (Array.isArray(self.currentTableData.referTableNames) && self.currentTableData.referTableNames.length > 0) {
						
						if (Array.isArray(self.currentTableData.columns) && self.currentTableData.columns.length > 0) {
								
							const columnId = self.currentTableData.columns.find(e => e.name === 'id');
							
							if (columnId) {
								
								self.currentTableData.referTableNames.forEach(referTableName => {
									
									const referTable = self.data.find(e => e.name === referTableName);
									
									if (referTable) {
									
										const label = util.getStringCapitalized(referTable.label ?? referTable.name);
										const idValue = BackOfficeHelper.getColumnValue(self.currentTableData, columnId.id, 0); 
										const referColumn = BackOfficeHelper.getRefColumn(self.currentTableData, referTable.name, self.data);
										
										if (referColumn && idValue) {
											
											let newBackOffice = {
											
												id: self.backOffice.id,
												tableName: referTable.name,
												filterColumnName: referColumn.name,
												filterColumnValue: idValue,
												containerId: self.backOffice.containerId,
												filterColumn: referColumn
											}
											
											let backOfficeViewGrid = new BackOfficeViewGrid(newBackOffice, self.data, self.data.find(e => e.id === referColumn.tableId));
											
											if (backOfficeViewGrid) {
												
												let viewBackOfficeViewGrid = backOfficeViewGrid.getView();
												
												if (viewBackOfficeViewGrid) {
													
													viewBackOfficeViewGrid.css = "bordered-layout-without-border-top";
													
													tabs.cells.push({
														header: label,
														body: viewBackOfficeViewGrid
													});	 
												}
											}
										}	
									}
								
									
								});
							}		
						}
					}
				}

				return tabs;
			}
			
			return viewContent;
		}
		
		return view;
	}
	
	refresh() {
		
		let self = this;
		
		BackOfficeHelper.getData(self.backOffice).then((data) => {
			
			self.data = data;
			self.currentTableData = data.find(e => e.name === self.backOffice.tableName);
		});
	}
}
