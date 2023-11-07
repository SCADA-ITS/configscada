import BackOfficeViewGenericForm from "./backOfficeViewGenericForm.js";
import BackOfficeViewFormField from "./backOfficeViewFormField.js";
import BackOfficeViewFormImage from "./backOfficeViewFormImage.js";
import BackOfficeViewFormAttachment from "./backOfficeViewFormAttachment.js";
import BackOfficeViewGrid from "./backOfficeViewGrid.js";
import BackOfficeHelper from "./backOfficeHelper.js";

import { util } from "../../lib/util.js";

export default class BackOfficeViewForm extends BackOfficeViewGenericForm {

	constructor(backOffice, data, currentTableData) {

		super(backOffice, data, currentTableData);

		this.tabInfoVisible = false;
	}
	
	getView() {
		
		let self = this;
		
		return super.getView(function() {
			
			let view;
			
			let tabs = {
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
			
			view = {
				rows: [
					tabs,
					{
						view: "template",
						height: 1,
						borderless: true,
						on: {
							onAfterRender: function() {
								
								if (!self.tabInfoVisible) {
									
									if ($$("tabview-" + self.uid)) {
									
										$$("tabview-" + self.uid).getTabbar().hideOption("info-tab-" + self.uid)	
									}
								}
							}
						}	
					}
				]
			}
			
			
			if (!self.backOffice.noReferTables) {
				
				pushReferTables();	
			}

			function getInfoView() {

				let infoView;
				
				infoView = {
					id: "info-tab-" + self.uid,
					rows: [
						{
							id: self.formId,
							view: "form",
							css: "bordered-layout-without-border-top",
							elementsConfig: { labelAlign: "left" },
							elements: function() {
								
								let elements = [];
								
								if ((Array.isArray(self.currentTableData.commands) && self.currentTableData.commands.length > 0) ||
									self.currentTableData.metadata && self.currentTableData.metadata.autorefresh && self.currentTableData.metadata[BackOfficeHelper.META_TYPE_TABLE.PROPERTY.AUTOREFRESH_FORM.name] && self.currentTableData.metadata.autorefresh > 0) {
									
									let actionButtons = {
										cols: [
											self.getActionButtons()
										]
									}
										
									elements.push(actionButtons);
								}
								
								elements.push(...getElements());
								
								return elements;
							}(),
						},
						{ height: 15 },
						self.getToolbarActions()
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
									
									const rowDataId = self.currentTableData.rows[0].data.find(data => data.columnId === self.currentTableData.columns.find(col => col.primaryKey === true).id);
									
									let backOfficeViewFormImage = new BackOfficeViewFormImage(self.backOffice, item.value.groupImage, self.currentTableData.name, rowDataId.value, item.value.images, onChangeAttachment);
									
									fields.push(backOfficeViewFormImage.getView());
									
									self.tabInfoVisible |= true;
								}
								else if (item.type === TYPE_ATTACHMENT) {
									
									const rowDataId = self.currentTableData.rows[0].data.find(data => data.columnId === self.currentTableData.columns.find(col => col.primaryKey === true).id);
									
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
				
				return infoView;
			}
			
			
			
			function pushReferTables() {
				
				if (Array.isArray(self.currentTableData.referTableNames) && self.currentTableData.referTableNames.length > 0) {
					
					if (Array.isArray(self.currentTableData.columns) && self.currentTableData.columns.length > 0) {
							
						const columnId = self.currentTableData.columns.find(e => e.primaryKey === true);
						
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

			return view;
		})
	}
	
}
