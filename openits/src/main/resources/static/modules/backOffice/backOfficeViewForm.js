import BackOfficeView from "./backOfficeView.js";
import BackOfficeViewFormField from "./backOfficeViewFormField.js";
import BackOfficeViewFormImage from "./backOfficeViewFormImage.js";
import BackOfficeViewFormAttachment from "./backOfficeViewFormAttachment.js";
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
		
		this.refreshInterval = undefined;
		this.selectedRefreshTime = currentTableData.metadata && currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH_FORM.name] && currentTableData.metadata.autorefresh && currentTableData.metadata.autorefresh > 0 ? currentTableData.metadata.autorefresh : undefined;
		this.tabInfoVisible = false;
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
							
							let valueComparison = isNaN(formValues[key]) ? formValues[key] : (formValues[key] === 1 ? "true" : "false"); 
							
							if (rowData && valueComparison !== rowData.value) {
								
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
					},
					{
						view: "template",
						height: 1,
						borderless: true,
						on: {
							onAfterRender: function() {
								
								if (self.currentTableData.metadata && self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH_FORM.name] &&
									self.currentTableData.metadata.autorefresh && 
									!isNaN(self.currentTableData.metadata.autorefresh)) {
									
									$$("slider-" + self.uid).setValue(self.currentTableData.metadata.autorefresh);

									timerRefresh();
								}
								
								let windowTitle = i18n.t("LBL_EDIT"); 
			
								if (self.backOffice.onCancel) {
								
									windowTitle = i18n.t("LBL_NEW");
								}
								
								const representativeField = BackOfficeHelper.getRepresentativeField(self.currentTableData);
								
								let rowData = undefined;
								
								if (representativeField) {
								
									rowData = self.currentTableData.rows[0].data.find(data => data.columnId === self.currentTableData.columns.find(col => col.name === representativeField.name).id);	
								}
								
								windowTitle += " " + (self.currentTableData.labelSingular ?? (self.currentTableData.label ?? self.currentTableData.name)) + ((rowData && rowData.value !== undefined) ? " - " + rowData.value : "");
								
								util.ui.setTitle(self.backOffice.containerId, windowTitle);
								
								if (!self.tabInfoVisible) {
									
									$$("tabview-" + self.uid).getTabbar().hideOption("info-tab-" + self.uid)
								}
							}
						}
					}
				],
				on: {
					onDestruct: function() {
	
						if (self.refreshInterval) {
							
							clearInterval(self.refreshInterval);
						}
					}
				}
			};
			
			function timerRefresh() {
				
				if (self.refreshInterval) {
										
					clearInterval(self.refreshInterval);
				}
				
				if (self.selectedRefreshTime > 0) {
					
					self.refreshInterval = setInterval(function() {
						
						self.refresh(function() {
							
							if (Array.isArray(self.currentTableData.rows) && self.currentTableData.rows.length > 0) {
								
								if ($$(self.backOffice.containerId)) {
									
									webix.extend($$(self.backOffice.containerId), webix.ProgressBar);
	
									$$(self.backOffice.containerId).showProgress({
						
										hide: false, type: "bottom"
									});
									
									self.setValues(self.currentTableData.rows[0]);
									
									$$(self.backOffice.containerId).hideProgress();	
								}									
							}
						})
						
					}, self.selectedRefreshTime * 1000);	
				}
			}
			
			function getHeader() {

				let header;				
				let colValuesInHeader = [];
				
				if (Array.isArray(self.currentTableData.rows) && self.currentTableData.rows.length > 0) {
					
					self.currentTableData.columns.forEach(col => {
						
						const badgeInHeader = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_COLUMN, BackOfficeHelper.META_TYPE_COLUMN.PROPERTY.BADGE_IN_HEADER, self.backOffice.metadata, col);
					
						if (badgeInHeader) {
							
							const refColumn = col.refColumn;
							const refTable = refColumn ? self.data.find(e => e.id === refColumn.tableId) : undefined;
				
							let colName;
							let value = BackOfficeHelper.getColumnValue(self.currentTableData, col.id, 0);
							
							if (refColumn && refTable) {
								
								const referencedColumnValue = BackOfficeHelper.getReferencedColumnValue(col, value, self.data);
								
								if (referencedColumnValue) {
									
									colName = util.getStringCapitalized(refTable.labelSingular ?? refTable.name);
									value = referencedColumnValue.value
								}
							}
							else {

								colName = util.getStringCapitalized(col.label ?? col.name);
								
							}
							
							if (colName && value) {
									
								colValuesInHeader.push({
									field: colName,
									icon: badgeInHeader,
									value: value
								});
							}
						} 
					});	
				}

				header = {
					id: "viewHeader-" + self.uid,
					height: 95 - (colValuesInHeader.length === 0 ? 30 : 0),
					cols: [
						{
							rows: [
								{},
								{
									height: 86 - (colValuesInHeader.length === 0 ? 30 : 0),
									borderless: true,
									template: function() {
										
										const icon = (self.currentTableData.mdiIcon) ? self.currentTableData.mdiIcon : "mdi mdi-application";
										const description = self.currentTableData.labelDescription ?? "";
										
										let windowTitle = i18n.t("LBL_EDIT"); 
			
										if (self.backOffice.onCancel) {
										
											windowTitle = i18n.t("LBL_NEW");
										}
										
										const representativeField = BackOfficeHelper.getRepresentativeField(self.currentTableData);
										
										let rowData = undefined;
										
										if (representativeField) {
										
											rowData = self.currentTableData.rows[0].data.find(data => data.columnId === self.currentTableData.columns.find(col => col.name === representativeField.name).id);	
										}
										
										windowTitle += " " + (self.currentTableData.labelSingular ?? (self.currentTableData.label ?? self.currentTableData.name)) + ((rowData && rowData.value !== undefined) ? " - " + rowData.value : "");
										
										let badges = '';
										
										if (Array.isArray(colValuesInHeader) && colValuesInHeader.length > 0) {
											
											colValuesInHeader.forEach(colValue => {
												
												badges += `<span title='${colValue.field}' class='backoffice-badge' style='border: 1px solid rgb(71, 84, 102); padding-left: 14px; margin-left: 6px'><span class="ico webix_icon ${colValue.icon}"></span>${colValue.field + ": " + colValue.value}</span>`;	
											});
										}
										
										let res =  `<span style='font-size: 40px; padding-left: 14px' class='${icon}'></span>
													<div class="backoffice-header" id="equipDescription" style="padding-left: 10px !important; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; display: inline-block" title="${description ?? i18n.t("LBL_NO_DESCRIPTION")}">
														${windowTitle}
													</div>`;
													
										if (badges) {
											
											res += `<div style="padding-top: 8px; padding-left: 44px">
													${badges}
													</div>`
										}
										
										return res;
		
									},
									on: {
										onDestruct: function() {
											
											if (self.backOffice.onCancel && !self.accepted) {
												
												self.backOffice.onCancel();
											}		
										}
									}
								},
								{}
							]
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
						id: "info-tab-" + self.uid,
						rows: [
							{
								id: "form-" + self.uid,
								view: "form",
								css: "bordered-layout-without-border-top",
								elementsConfig: { labelAlign: "left" },
								elements: function() {
									
									let elements = [];
									
									if ((Array.isArray(self.currentTableData.commands) && self.currentTableData.commands.length > 0) ||
										self.currentTableData.metadata && self.currentTableData.metadata.autorefresh && self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH_FORM.name] && self.currentTableData.metadata.autorefresh > 0) {
										
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
								Array.isArray(self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.TABS.name]) && 
								self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.TABS.name].length > 0) {
									
								let tabInfo = {
									view: "tabview",
									borderless: true,
									css: "auxiliary-tab",
									cells: [],
									tabbar: {
										tabMargin: 6,
									}
								};
								
								let initialTabLabel = BackOfficeHelper.translate(self.backOffice, self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.INITIAL_TAB_LABEL.name]) ?? i18n.t("LBL_INITIAL_TAB");
																
								self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.TABS.name].sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
																							
								self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.TABS.name].forEach(tab => {

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
							const TYPE_ATTACHMENT = "attachment";
							
							let items = [];
							
							assignFields();
							assignFiles(TYPE_IMAGE);
							assignFiles(TYPE_ATTACHMENT);
							
							if (Array.isArray(items) && items.length > 0) {
								
								items.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
								
								for (let item of items) {
									
									if (item.type === TYPE_COLUMN) {
										
										fields.push(backOfficeViewFormField.getField(item.value));
										
										self.tabInfoVisible |= backOfficeViewFormField.isVisible;
									}
									else if (item.type === TYPE_IMAGE) {
										
										const rowDataId = self.currentTableData.rows[0].data.find(data => data.columnId === self.currentTableData.columns.find(col => col.name === "id").id);
										
										let backOfficeViewFormImage = new BackOfficeViewFormImage(self.backOffice, item.value.groupImage, self.currentTableData.name, rowDataId.value, item.value.images, onChangeAttachment);
										
										fields.push(backOfficeViewFormImage.getView());
										
										self.tabInfoVisible |= true;
									}
									else if (item.type === TYPE_ATTACHMENT) {
										
										const rowDataId = self.currentTableData.rows[0].data.find(data => data.columnId === self.currentTableData.columns.find(col => col.name === "id").id);
										
										let backOfficeViewFormAttachment = new BackOfficeViewFormAttachment(self.backOffice, item.value.groupAttachment, self.currentTableData.name, rowDataId.value, item.value.attachments, onChangeAttachment);
										
										fields.push(backOfficeViewFormAttachment.getView());
										
										self.tabInfoVisible |= true;
									}
								}
							}
							
							function onChangeAttachment() {
								
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
							
							function assignFiles(fileType) {
								
								if (self.currentTableData.metadata) {
									
									let groups = undefined;
									
									if (fileType === TYPE_IMAGE) {
										
										groups = self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.GROUP_IMAGES.name];
									} 
									else if (fileType === TYPE_ATTACHMENT) {
										
										groups = self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.GROUP_ATTACHMENTS.name];
									}
									
									if (Array.isArray(groups) && groups.length > 0) {
									
										groups.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
									
										for (let group of groups) {
										
											if (Array.isArray(self.currentTableData.rows) && self.currentTableData.rows.length === 1) {
												
												const row = self.currentTableData.rows[0];
												
												if ((!tab && !group.tabPosition) || (tab && group.tabPosition == tab.position)) {
													
													if (row && Array.isArray(row.images)) {
														
														let item = {
															position: group.position,
															type: fileType
														} 
														
														if (fileType === TYPE_IMAGE && Array.isArray(row.images)) {
											
															item.value = {
																groupImage: group,
																images: row.images.filter(e => e.groupName === group.name)
															}
														} 
														else if (fileType === TYPE_ATTACHMENT && Array.isArray(row.attachments)) {
															
															item.value = {
																groupAttachment: group,
																attachments: row.attachments.filter(e => e.groupName === group.name)
															}
														}
														
														if (item.value) {
															
															items.push(item);	
														}
													}	
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
							hidden: (!Array.isArray(self.currentTableData.commands) || self.currentTableData.commands.length === 0) && !(self.currentTableData.metadata && self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH_FORM.name] && self.currentTableData.metadata.autorefresh > 0),
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
										
											(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_CONFIRM_COMMAND") + " " + (cmd.label ?? cmd.name) + "?", null, executeCommand, null)).show();	
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
						
						if (self.currentTableData.metadata && self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH_FORM.name] && self.currentTableData.metadata.autorefresh > 0) {
							
							buttonsGrid.cols.push({
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
								on: {
									onChange: function(newv) {
		
										self.selectedRefreshTime = newv;
										
										timerRefresh();
									}
								}
							});	
						}
						
						
		
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
									
									(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_BACKOFFICE_SAVE_CHANGES_ON") + " " + (self.currentTableData.label ?? self.currentTableData.name) + "?", null, onAccept , null)).show();
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
									
									if (referTable && isVisibleReferTable(referTableName)) {
									
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
					
					function isVisibleReferTable(referTableName) {
						
						let isVisible = true;
						
						if (self.currentTableData.metadata && self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.REFER_TABLES.name]) {
							
							if (Array.isArray(self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.REFER_TABLES.name])) {
								
								if (self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.REFER_TABLES.name].length === 0) {
									
									isVisible = false;
								}
								else {
									
									isVisible = self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.REFER_TABLES.name].includes(referTableName);
								}								
							}
						}
						
						return isVisible;
					}
				}

				return tabs;
			}
			
			return viewContent;
		}
		
		return view;
	}
	
	refresh(onRefresh) {
		
		let self = this;
		
		BackOfficeHelper.getData(self.backOffice).then((data) => {
			
			self.data = data;
			self.currentTableData = data.find(e => e.name === self.backOffice.tableName);
			
			if (onRefresh) {
				
				onRefresh();
			}
		});
	}
}
