import BackOfficeView from "./backOfficeView.js";
import BackOfficeViewFormField from "./backOfficeViewFormField.js";
import BackOfficeViewFormCarousel from "./backOfficeViewFormCarousel.js";
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
	
	setValues(row) {
		
		const form = $$("form-" + this.uid);
		
		if (form && row && Array.isArray(row.data) && row.data.length > 0) {
			
			const formValues = form.getValues();
			
			if (typeof(formValues) === 'object') {
			
				const keys = Object.keys(formValues);
				let newValues = {};
	
				if (Array.isArray(keys) && keys.length > 0) {
					
					keys.forEach(key => {
						
						if (key != "id") {
							
							const columnId = BackOfficeHelper.getColumnIdFromColumnUID(key);
							
							let rowData = row.data.find(e => e.columnId === columnId);
							
							if (rowData && formValues[key] != rowData.value) {
								
								newValues[key] = rowData.value;
							}
						}
					});
				}
				
				if (Object.keys(newValues).length > 0) {
					
					form.setValues(newValues, true);
				}
			}
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
				
				const icon = (self.currentTableData.mdiIcon) ? self.currentTableData.mdiIcon : "mdi mdi-application"; 

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
								elements: function() {
									
									let elements = [];
									
									if (Array.isArray(self.currentTableData.commands) && self.currentTableData.commands.length > 0) {
										
										let actionButtons = {
											cols: [
												getActionButtons()
											]
										}
											
										elements.push(actionButtons);
									}
									
									elements.push(...getElements());
									
									return elements;
								}(),
							},
							{ height: 15 },
							getButtons()
						]
					};
					
					function getElements() {
						
						let elements = [];
						let currentTableDataColumnsCopy = [];

						if (Array.isArray(self.currentTableData.columns) &&
							self.currentTableData.columns.length > 0) {
						
							if (self.currentTableData.metadata && 
								Array.isArray(self.currentTableData.metadata.tabs) && 
								self.currentTableData.metadata.tabs.length > 0) {
									
								let tabInfo = {
									view: "tabview",
									borderless: true,
									css: "auxiliary-tab",
									cells: [],
									tabbar: {
										tabMargin: 6,
									}
								};
								
								let initialTabLabel = BackOfficeHelper.translate(self.backOffice, self.currentTableData.metadata.initialTabLabel) ?? i18n.t("LBL_INITIAL_TAB");
								
								self.currentTableData.metadata.tabs.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
																							
								self.currentTableData.metadata.tabs.forEach(tab => {

									currentTableDataColumnsCopy = webix.copy(self.currentTableData.columns);
									
									for (let field of tab.fields) {

										currentTableDataColumnsCopy = currentTableDataColumnsCopy.filter(e => e.name != field.name);
									}

									if (Array.isArray(currentTableDataColumnsCopy) && currentTableDataColumnsCopy.length > 0) {

										tabInfo.cells.push({
											header: initialTabLabel,
											body: {
												view: "scrollview",
												scroll: "auto",
												borderless: true,
												body: {
													cols: [
														{ width: 45 },
														{
															rows: getItems()
														},
														{ width: 45 }
													]
												}
											}
										});
									}
									
									tabInfo.cells.push({
										header: BackOfficeHelper.translate(self.backOffice, tab.label),
										body: {
											view: "scrollview",
											scroll: "auto",
											borderless: true,
											body: {
												cols: [
													{ width: 45 },
													{
														rows: getItems(tab)
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
												rows: getItems()
											},
											{ width: 45 }
										]
									}
								}
								
								elements.push(info);
							}		
						}
						
						function getItems(tab) {
							
							let fields = [{ height: 45 }];
							let backOfficeViewFormField = new BackOfficeViewFormField(self);
							
							const TYPE_COLUMN = "column";
							const TYPE_IMAGE = "image";
							
							let items = [];
							
							assignFields();
							assignImages();
							
							if (Array.isArray(items) && items.length > 0) {
								
								items.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
								
								for (let item of items) {
									
									if (item.type === TYPE_COLUMN) {
										
										fields.push(backOfficeViewFormField.getField(item.value));
									}
									else if (item.type === TYPE_IMAGE) {
										
										const rowDataId = self.currentTableData.rows[0].data.find(data => data.columnId === self.currentTableData.columns.find(col => col.name === "id").id);
										
										let backOfficeViewFormCarousel = new BackOfficeViewFormCarousel(self.backOffice, item.value.groupImage, self.currentTableData.name, rowDataId.value, item.value.images, onChangeCarousel);
										
										fields.push(backOfficeViewFormCarousel.getView());
									}
								}
							}
							
							function onChangeCarousel() {
								
								const btnAccept = $$("btn-accept-" + self.uid);
								
								if (btnAccept) {
									
									btnAccept.enable();
								}
							}
							
							function assignFields() {
							
								if (tab && Array.isArray(tab.fields) && tab.fields.length > 0) {

									let position = 0;							
									tab.fields.forEach(field => {
										
										let column = self.currentTableData.columns.find(e => e.name === field.name);
										
										if (column) {
											
											items.push({
												position: ++position,
												type: TYPE_COLUMN,
												value: column
											})
										}
									});
								}
								else {
									
									let columns = (Array.isArray(currentTableDataColumnsCopy) && currentTableDataColumnsCopy.length > 0) ? currentTableDataColumnsCopy : self.currentTableData.columns;
									
									if (Array.isArray(columns)) {
										
										columns.forEach(column => {
	
											let pos = (column.metadata) ? column.metadata[BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.POSITION.name] : undefined;
			
											if (pos != undefined) {
			
												column.position = pos;
											}
										});
			
										columns.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
			
										columns.forEach(column => {
											
											items.push({
												position: column.position,
												type: TYPE_COLUMN,
												value: column
											});
										});	
									}
								}	
							}
							
							function assignImages() {
								
								if (self.currentTableData.metadata && 
									Array.isArray(self.currentTableData.metadata.groupImages) && 
									self.currentTableData.metadata.groupImages.length > 0) {
									
									self.currentTableData.metadata.groupImages.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
									
									for (let groupImage of self.currentTableData.metadata.groupImages) {
										
										if (Array.isArray(self.currentTableData.rows) && self.currentTableData.rows.length === 1) {
											
											const row = self.currentTableData.rows[0];
											
											if ((!tab && !groupImage.tabPosition) || (tab && groupImage.tabPosition == tab.position)) {
												
												if (row && Array.isArray(row.images)) {
										
													items.push({
														position: groupImage.position,
														type: TYPE_IMAGE,
														value: {
															groupImage: groupImage,
															images: row.images
														}
													});
												}	
											} 
										}
									}
								}
							}
							
							return fields;
						}
						
						return elements;
					}
					
					function getActionButtons() {

						let buttonsGrid = {
							hidden: !Array.isArray(self.currentTableData.commands) || self.currentTableData.commands.length === 0,
							css: { "margin-left": "2px !important" },
							height: 45,
							cols: [
							]
						};
						
						if (Array.isArray(self.currentTableData.commands) && self.currentTableData.commands.length > 0) {
							
							const commandsAvailable = self.currentTableData.commands.filter(e => e.availableInForm);
							
							commandsAvailable.forEach(cmd => {
		
								buttonsGrid.cols.push(getButton(cmd));
							});	
						}
		
						function getButton(cmd) {
		
							let button = {
								view: "button",
								type: "icon",
								css: "toolbar-button-text",
								width: 130,
								on: {
									onItemClick: function() {
										
										if (cmd.requireConfirmation) {
										
											(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_CONFIRM_COMMAND") + " " + (cmd.label ?? cmd.name), null, executeCommand, null)).show();	
										}
										else {
											
											executeCommand();
										}
									}
								}
							};
		
							button.label = i18n.t(cmd.label ?? cmd.name);
							button.tooltip = i18n.t(cmd.labelDescription ?? cmd.name);
							button.icon = cmd.mdiIcon;
							
							function executeCommand() {
							
								const form = $$("form-" + self.uid);
													
								if (form) {
									
									if (Array.isArray(self.currentTableData.commands) && self.currentTableData.commands.length > 0) {
										
										const values = form.getValues();
									
										if (typeof(values) === 'object') {
											
											const requestInput = BackOfficeHelper.getRequestInput(values, self.currentTableData, true);
											const id = values["id"];
											
											if (id && (Array.isArray(requestInput) && requestInput.length > 0)) {
										
												BackOfficeHelper.command(self.backOffice, self.currentTableData, cmd.id, requestInput, id).then((requestResult) => {
															
													if (!requestResult.requestResultMessage.error) {
														
														self.setValues(requestResult.newRow);
														
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
							}
		
							return button;
						}
						
						
		
						buttonsGrid.cols.push({});
		
						return buttonsGrid;
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
													
								const requestInput = BackOfficeHelper.getRequestInput(form.getValues(), self.currentTableData);
							
								if (id && ((Array.isArray(requestInput) && requestInput.length > 0) || self.backOffice.onCancel)) {
									
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
											
											BackOfficeHelper.updateData(self.backOffice, self.currentTableData, requestInput, id).then((requestResult) => {
														
												if (!requestResult.requestResultMessage.error) {
													
													self.backOffice.onAccept(values);
											
													if (container) {
											
														container.close();	
													}
												}
												else {
													
													log.error(requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription);
													
													(new MessageBox (MessageBox.ERROR, i18n.t("LBL_BACKOFFICE_ERROR_SAVING_CHANGES_ON") + " " + (self.currentTableData.label ?? self.currentTableData.name), requestResult.requestResultMessage.error + "\n" + requestResult.requestResultMessage.errorDescription, null, null)).show();
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
												filterColumn: referColumn,
												metadata: self.backOffice.metadata												
											};
											
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
