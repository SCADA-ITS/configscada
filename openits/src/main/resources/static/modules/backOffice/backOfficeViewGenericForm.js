import BackOfficeView from "./backOfficeView.js";
import BackOfficeHelper from "./backOfficeHelper.js";
import PrintPreview from "../../component/printPreview.js";
import MessageBox from "../../component/messageBox.js";

import { util } from "../../lib/util.js";

export default class BackOfficeViewGenericForm extends BackOfficeView {

	constructor(backOffice, data, currentTableData) {
		
		super(backOffice, data, currentTableData);
		
		if (this.constructor == BackOfficeViewGenericForm) {

			throw new Error("Abstract classes can't be instantiated.");
		}
		
		
		this.formId = "form-" + this.uid;
		this.accepted = false;
		this.formHeight = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_HEIGHT, backOffice.metadata, currentTableData);
		this.formWidth = BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.FORM_WIDTH, backOffice.metadata, currentTableData);
		
		if (backOffice.containerId) {
			
			util.ui.resizeWindow(backOffice.containerId, this.formWidth, this.formHeight);
			util.ui.centerWindow(backOffice.containerId);
		}
		
		this.refreshInterval = undefined;
		this.selectedRefreshTime = currentTableData.metadata && currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH_FORM.name] && currentTableData.metadata.autorefresh && currentTableData.metadata.autorefresh > 0 ? currentTableData.metadata.autorefresh : undefined;
		
		this.editable = !currentTableData.sqlView && (backOffice.editable || BackOfficeHelper.getMetadata(BackOfficeHelper.META_TYPE_TABLE, BackOfficeHelper.META_TYPE_TABLE.PROPERTY.EDITABLE, backOffice.metadata, currentTableData));
	}
	
	setValues(row, formRef) {
		
		let self = this;
		const form = formRef ?? $$(this.formId);
		
		if (form && row && Array.isArray(row.data) && row.data.length > 0) {
			
			const formValues = form.getValues();
			
			if (typeof(formValues) === 'object') {
			
				const keys = Object.keys(formValues);
				let newValues = {};
				
				const columnId = self.currentTableData.columns.find(e => e.primaryKey === true);
	
				if (Array.isArray(keys) && keys.length > 0 && columnId) {
					
					keys.forEach(key => {
						
						if (key != columnId.name) {
							
							const colId = BackOfficeHelper.getColumnIdFromColumnUID(key);
							
							const column = self.currentTableData.columns.find(e => e.id === colId);
							
							if (column) {
								
								let rowData = row.data.find(e => e.columnId === colId);
							
								let valueComparison = isNaN(formValues[key]) ? formValues[key] : (formValues[key] === 1 ? "true" : "false"); 
								
								if (rowData && valueComparison !== rowData.value) {
									
									newValues[key] = rowData.value;
									
									const refColumn = column.refColumn;
									const refTable = refColumn ? self.data.find(e => e.id === refColumn.tableId) : undefined;
									
									if (refColumn && refTable) {
										
										const referencedColumnValue = BackOfficeHelper.getReferencedColumnValue(self.backOffice, column, newValues[key], self.data);
										
										if (referencedColumnValue) {
											
											newValues[key + "$ref"] = referencedColumnValue.value;	
										} 
									}
								}	
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
	
	getView(getBodyView) {
		
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
							getBodyView(),
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
									!isNaN(self.currentTableData.metadata.autorefresh) &&
									!self.backOffice.onCancel) {
									
									$$("slider-" + self.uid).setValue(self.currentTableData.metadata.autorefresh);

									self.timerRefresh();
								}
								
								let windowTitle = i18n.t("LBL_CMD_EDIT"); 
			
								if (self.backOffice.onCancel) {
								
									windowTitle = i18n.t("LBL_CMD_ADD");
								}
								
								const representativeField = BackOfficeHelper.getRepresentativeField(self.backOffice, self.currentTableData);
								
								let rowData = undefined;
								
								if (representativeField) {
								
									rowData = self.currentTableData.rows[0].data.find(data => data.columnId === self.currentTableData.columns.find(col => col.name === representativeField.name).id);	
								}
								
								windowTitle += " " + (self.currentTableData.labelSingular ? BackOfficeHelper.translate(self.backOffice, self.currentTableData.labelSingular) : (self.currentTableData.label ? BackOfficeHelper.translate(self.backOffice, self.currentTableData.label) : self.currentTableData.name)) + ((rowData && rowData.value !== undefined) ? " - " + rowData.value : "");
								
								util.ui.setTitle(self.backOffice.containerId, windowTitle);
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
								
								const referencedColumnValue = BackOfficeHelper.getReferencedColumnValue(self.backOffice, col, value, self.data);
								
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
										
										let windowTitle = i18n.t("LBL_CMD_EDIT"); 
			
										if (self.backOffice.onCancel) {
										
											windowTitle = i18n.t("LBL_CMD_ADD");
										}
										
										const representativeField = BackOfficeHelper.getRepresentativeField(self.backOffice, self.currentTableData);
										
										let rowData = undefined;
										
										if (representativeField) {
										
											rowData = self.currentTableData.rows[0].data.find(data => data.columnId === self.currentTableData.columns.find(col => col.name === representativeField.name).id);	
										}
										
										windowTitle += " " + (self.currentTableData.labelSingular ? BackOfficeHelper.translate(self.backOffice, self.currentTableData.labelSingular) : (self.currentTableData.label ? BackOfficeHelper.translate(self.backOffice, self.currentTableData.label) : self.currentTableData.name)) + ((rowData && rowData.value !== undefined) ? " - " + rowData.value : "");
										
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
			
			return viewContent;
		}
		
		return view;
	}
	
	getActionButtons() {

		let self = this;
		
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
			
				const form = $$(self.formId);
									
				if (form) {
					
					if (Array.isArray(self.currentTableData.commands) && self.currentTableData.commands.length > 0) {
						
						const values = form.getValues();
					
						if (typeof(values) === 'object') {
							
							const requestInput = BackOfficeHelper.getRequestInput(values, self.currentTableData, true);
							
							const columnId = self.currentTableData.columns.find(e => e.primaryKey === true);
							
							if (columnId) {
								
								const id = values[columnId.name];
							
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
			}

			return button;
		}
		
		

		buttonsGrid.cols.push({});
		
		if (!self.backOffice.onCancel && self.currentTableData.metadata && self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH_FORM.name] && self.currentTableData.metadata.autorefresh > 0) {
			
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
						
						self.timerRefresh();
					}
				}
			});	
		}
		
		

		return buttonsGrid;
	}
	
	getToolbarActions() {
		
		let self = this;

		let viewToolbarActions = {};
		
		const container = $$(self.backOffice.containerId);
		
		const cancelButtonView = {
			view: "button",
			value: i18n.t("LBL_CANCEL"),
			width: 130,
			height: 41,
			css: "dialog-cancel",
			click: function() {
					
				if (self.backOffice.onCancel) {
					
					self.backOffice.onCancel()
				}
				
				if (container) {
					
					container.close();	
				}
			}
		}
		
		const acceptButtonView = {
			id: "btn-accept-" + self.uid,
			view: "button",
			value: i18n.t("LBL_ACCEPT"),
			disabled: !self.backOffice.onCancel,
			width: 130,
			height: 41,
			css: "dialog-accept",
			hidden: !self.editable,
			click: function() {

				let validation = $$(self.formId).validate({ disabled: true });

				if (validation) {

					persistUpdate();

				} else {

					$$(self.formId).show();
				}
			}
		}
		
		const printButtonView = {
			view: "button",
			value: i18n.t("LBL_TABLE_PRINT"),
			width: 130,
			height: 41,
			css: "dialog-finish",
			hidden: (self.currentTableData.metadata && self.currentTableData.metadata.srcTemplate) ? false : true,
			click:
				function() {

					if (self.currentTableData.metadata && self.currentTableData.metadata.srcTemplate) {

						let inputData = {};
						inputData.tables = [];									
						inputData.groupImages = {};
						inputData.fields = {};

						let options = {
							srcTemplate: self.currentTableData.metadata.srcTemplate
						};

						if (Array.isArray(self.currentTableData.columns) && self.currentTableData.columns.length > 0) {

							for (let column of self.currentTableData.columns) {

								let val;
								let label = column.name;
								const refColumn = column.refColumn;

								let col = self.currentTableData.rows[0].data.find(d => d.columnId === column.id);

								if (col) {
								
									val = (column.colType === "TIMESTAMP") ? new Date(parseInt(col.value)).toLocaleString() : col.value;													

									if (refColumn) {

										const referencedColumnValue = BackOfficeHelper.getReferencedColumnValue(self.backOffice, column, col.value, self.data);

										if (referencedColumnValue) {

											val = referencedColumnValue.value;
										}
									}
								}

								inputData.fields[label] = val;
							}

							if (Array.isArray(self.currentTableData.referTableNames) && self.currentTableData.referTableNames.length > 0) {

								const columnId = self.currentTableData.columns.find(e => e.primaryKey === true);

								if (columnId) {

									self.currentTableData.referTableNames.forEach(referTableName => {

										const referTable = self.data.find(e => e.name === referTableName);

										if (referTable) {

											let tableObj = {};

											tableObj.data = [];
											tableObj.caption = referTable.label ?? referTable.name;
											tableObj.tableName = referTable.name;

											if (Array.isArray(referTable.rows) && referTable.rows.length > 0) {

												for (let row of referTable.rows) {

													let aux = {}

													for (let dat of row.data) {

														let d = referTable.columns.find(e => e.id === dat.columnId);

														if (d) {

															let lab;
															let referencedColumnName = BackOfficeHelper.getReferencedColumnName(self.backOffice, d, self.data);
															let referencedColumnValue = BackOfficeHelper.getReferencedColumnValue(self.backOffice, d, dat.value, self.data);

															if (referencedColumnName) {

																let table = self.data.find(e => e.id === referencedColumnName.tableId);

																if (table) {

																	lab = table.labelSingular ?? (table.label ?? table.name);
																}
															}

															aux[d.name] = {
																value: (referencedColumnValue && referencedColumnValue.value) ? referencedColumnValue.value : dat.value,
																header: lab ?? d.name
															};
														}
													}

													tableObj.data.push(aux);
												}

											} else {

												let aux = {};

												for (let col of referTable.columns) {

													aux[col.name] = undefined;
												}

												tableObj.data.push(aux);
											}

											inputData.tables.push(tableObj);
										}
									});
								}
							}

							if (Array.isArray(self.currentTableData.rows[0].images) && self.currentTableData.rows[0].images.length > 0) {
								
								inputData.groupImages.name = "groupImage" + self.currentTableData.rows[0].images[0].idValue;
								inputData.groupImages.data = [];

								for (let image of self.currentTableData.rows[0].images) {

									inputData.groupImages.data.push({
										src: `backoffice/${self.backOffice.id}/attachments/image/${self.currentTableData.name}/${image.id}`,
										fileName: image.fileName
									});
								}
							}
						}

						let printPreview = new PrintPreview(PrintPreview.TYPE_TEMPLATE, inputData, options);
						printPreview.show();

					} else {

						new MessageBox(MessageBox.ERROR, i18n.t("LBL_NO_SRC_TEMPLATE_CONFIG"), null, null, null).show();
					}
				}
		}

		viewToolbarActions = {
			id: "buttons-" + self.uid,
			borderless: true,
			css: { "margin-top": "1px !important" },
			rows: [
				{
					cols: function() {
						
						let cols = [];
						
						cols.push({ width: 15 });
						
						if (self.editable) {
						
							cols.push(cancelButtonView);	
						}
						
						cols.push({});
						
						if (!self.backOffice.onCancel && self.currentTableData.metadata && self.currentTableData.metadata.srcTemplate) {
							
							cols.push(printButtonView);
							cols.push({ width: 5 });
						}
						
						if (self.editable) {
							
							cols.push(acceptButtonView);
						}
						else {
							
							cols.push(cancelButtonView);
						}
						
						cols.push({ width: 15 });
						
						return cols;
					}(),
//					cols: [
//						{ width: 15 },
//						{
//							view: "button",
//							value: i18n.t("LBL_CANCEL"),
//							width: 130,
//							height: 41,
//							css: "dialog-cancel",
//							hidden: !self.editable,
//							click:
//								function() {
//									
//									if (self.backOffice.onCancel) {
//										
//										self.backOffice.onCancel()
//									}
//									
//									if (container) {
//										
//										container.close();	
//									}
//								}
//						},
//						{},
//						{
//							view: "button",
//							value: i18n.t("LBL_TABLE_PRINT"),
//							width: 130,
//							height: 41,
//							css: "dialog-finish",
//							hidden: (self.currentTableData.metadata && self.currentTableData.metadata.srcTemplate) ? false : true,
//							click:
//								function() {
//
//									if (self.currentTableData.metadata && self.currentTableData.metadata.srcTemplate) {
//
//										let inputData = {};
//										inputData.tables = [];									
//										inputData.groupImages = {};
//										inputData.fields = {};
//
//										let options = {
//											srcTemplate: self.currentTableData.metadata.srcTemplate
//										};
//
//										if (Array.isArray(self.currentTableData.columns) && self.currentTableData.columns.length > 0) {
//
//											for (let column of self.currentTableData.columns) {
//
//												let val;
//												let label = column.name;
//												const refColumn = column.refColumn;
//
//												let col = self.currentTableData.rows[0].data.find(d => d.columnId === column.id);
//
//												if (col) {
//												
//													val = (column.colType === "TIMESTAMP") ? new Date(parseInt(col.value)).toLocaleString() : col.value;													
//
//													if (refColumn) {
//
//														const referencedColumnValue = BackOfficeHelper.getReferencedColumnValue(self.backOffice, column, col.value, self.data);
//
//														if (referencedColumnValue) {
//
//															val = referencedColumnValue.value;
//														}
//													}
//												}
//
//												inputData.fields[label] = val;
//											}
//
//											if (Array.isArray(self.currentTableData.referTableNames) && self.currentTableData.referTableNames.length > 0) {
//
//												const columnId = self.currentTableData.columns.find(e => e.primaryKey === true);
//
//												if (columnId) {
//
//													self.currentTableData.referTableNames.forEach(referTableName => {
//
//														const referTable = self.data.find(e => e.name === referTableName);
//
//														if (referTable) {
//
//															let tableObj = {};
//
//															tableObj.data = [];
//															tableObj.caption = referTable.label ?? referTable.name;
//															tableObj.tableName = referTable.name;
//
//															if (Array.isArray(referTable.rows) && referTable.rows.length > 0) {
//
//																for (let row of referTable.rows) {
//
//																	let aux = {}
//
//																	for (let dat of row.data) {
//
//																		let d = referTable.columns.find(e => e.id === dat.columnId);
//
//																		if (d) {
//
//																			let lab;
//																			let referencedColumnName = BackOfficeHelper.getReferencedColumnName(self.backOffice, d, self.data);
//																			let referencedColumnValue = BackOfficeHelper.getReferencedColumnValue(self.backOffice, d, dat.value, self.data);
//
//																			if (referencedColumnName) {
//
//																				let table = self.data.find(e => e.id === referencedColumnName.tableId);
//
//																				if (table) {
//
//																					lab = table.labelSingular ?? (table.label ?? table.name);
//																				}
//																			}
//
//																			aux[d.name] = {
//																				value: (referencedColumnValue && referencedColumnValue.value) ? referencedColumnValue.value : dat.value,
//																				header: lab ?? d.name
//																			};
//																		}
//																	}
//
//																	tableObj.data.push(aux);
//																}
//
//															} else {
//
//																let aux = {};
//
//																for (let col of referTable.columns) {
//
//																	aux[col.name] = undefined;
//																}
//
//																tableObj.data.push(aux);
//															}
//
//															inputData.tables.push(tableObj);
//														}
//													});
//												}
//											}
//
//											if (Array.isArray(self.currentTableData.rows[0].images) && self.currentTableData.rows[0].images.length > 0) {
//												
//												inputData.groupImages.name = "groupImage" + self.currentTableData.rows[0].images[0].idValue;
//												inputData.groupImages.data = [];
//
//												for (let image of self.currentTableData.rows[0].images) {
//
//													inputData.groupImages.data.push({
//														src: `backoffice/${self.backOffice.id}/attachments/image/${self.currentTableData.name}/${image.id}`,
//														fileName: image.fileName
//													});
//												}
//											}
//										}
//
//										let printPreview = new PrintPreview(PrintPreview.TYPE_TEMPLATE, inputData, options);
//										printPreview.show();
//
//									} else {
//
//										new MessageBox(MessageBox.ERROR, i18n.t("LBL_NO_SRC_TEMPLATE_CONFIG"), null, null, null).show();
//									}
//								}
//						},										
//						{
//							id: "btn-accept-" + self.uid,
//							view: "button",
//							value: i18n.t("LBL_ACCEPT"),
//							disabled: !self.backOffice.onCancel,
//							width: 130,
//							height: 41,
//							css: "dialog-accept",
//							hidden: !self.editable,
//							click:
//								function() {
//
//									let validation = $$(self.formId).validate({ disabled: true });
//
//									if (validation) {
//
//										persistUpdate();
//
//									} else {
//
//										$$(self.formId).show();
//									}
//								}
//						},
//						{
//							view: "button",
//							value: i18n.t("LBL_CANCEL"),
//							width: 130,
//							height: 41,
//							css: "dialog-cancel",
//							hidden: self.editable,
//							click:
//								function() {
//									
//									if (self.backOffice.onCancel) {
//										
//										self.backOffice.onCancel()
//									}
//									
//									if (container) {
//										
//										container.close();	
//									}
//								}
//						},
//						{ width: 15 }
//					]
				}
			]
		}
		
		function persistUpdate() {
			
			const id = getIdToPersist();
			
			const form = $$(self.formId);
								
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
					
					const form = $$(self.formId);
					
					if (form) {
						
						const values = form.getValues();
						
						if (typeof(values) === 'object') {
							
							if (self.backOffice.onAccept) {
							
								self.backOffice.onAccept();	
							}
							
							BackOfficeHelper.updateData(self.backOffice, self.currentTableData, requestInput, id).then((requestResult) => {
										
								if (!requestResult.requestResultMessage.error) {
									
									if (self.backOffice.onAccept) {
									
										//self.backOffice.onAccept(values);	
										self.backOffice.onAccept(requestResult.newRow);
									}
							
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
			
			function getIdToPersist() {
						
				const form = $$(self.formId);
				let id = undefined;
				
				if (form) {
					
					const values = form.getValues();
					
					
					
					if (typeof(values) === 'object') {
						
						const keys = Object.keys(values);
						
						const columnId = self.currentTableData.columns.find(e => e.primaryKey === true);

						if (Array.isArray(keys) && keys.length > 0 && columnId) {
							
							const key = keys.find(e => e === columnId.name);
							
							if (key) {
								
								id = parseInt(values[key]);
							}
						}
					}
				}
				
				return id;
			}
		}

		return viewToolbarActions;
	}
	
	timerRefresh() {
		
		let self = this;
				
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
