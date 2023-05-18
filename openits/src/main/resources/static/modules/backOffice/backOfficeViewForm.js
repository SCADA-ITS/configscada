import BackOfficeView from "./backOfficeView.js";
import BackOfficeViewGrid from "./backOfficeViewGrid.js";
import BackOfficeHelper from "./backOfficeHelper.js";
import { util } from "../../lib/util.js";

const FORM_WIDTH = 900;
const FORM_HEIGHT = 700;

export default class BackOfficeViewForm extends BackOfficeView {

	constructor(backOffice, data, currentTableData) {

		super(backOffice, data, currentTableData);
		
		if (backOffice.containerId) {
			
			util.ui.resizeWindow(backOffice.containerId, FORM_WIDTH, FORM_HEIGHT);
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
					},
					getButtons(),
					{ height: 15 }
				]
			};
			
			function getHeader() {

				let header;
				let label = "LBL_FORM";
				let description = "LBL_FORM_DESCRIPTION";

				header = {
					id: "viewHeader-" + self.uid,
					cols: [
						{
							view: "label",
							label: "<img src='img/grid/default.png' class='grid-icon'/>",
							height: 75,
							css: "global-header-icon"
						},
						{
							view: "label",
							height: 75,
							width: 450,
							id: "label-bottom-" + self.uid,
							template: "<span>" + i18n.t(label) + "</span> <br> <span style='font-size: 13px'>" + i18n.t(description) + "</span>",
							css: "global-header-label"
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
				
				pushReferTables();

				function getInfoView() {

					let infoView;

					infoView = {
						id: "form-" + self.uid,
						view: "form",
						css: "bordered-layout-without-border-top",
						elementsConfig: { labelAlign: "left" },
						elements: getElements()
					}
					
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
									scroll: "auto",
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
						
						if (tab && Array.isArray(tab.fields) && tab.fields.length > 0) {
							
							tab.fields.forEach(field => {
						
								addField(self.currentTableData.columns.find(e => e.name === field.name));
							});
						}
						else {
							
							self.currentTableData.columns.forEach(column => addField(column));
						}
						
						function addField(column) {
							
							if (column) {
								
								const dbType = BackOfficeHelper.getDBTypes(column.colType);
								
								if (dbType) {
									
									let field = webix.copy(dbType.component);
								
									field.name = column.name;
									field.label = column.alias ?? column.name;
									field.disabled = (column.name === "id");
									
									let value = BackOfficeHelper.getColumnValue(self.currentTableData, column.id, 0);
									
									if (value && dbType.name === BackOfficeHelper.DB_TYPE_TIMESTAMP) {
										
										value = new Date(parseInt(value));
									}
									
									field.value = value;
									
									const refColumn = column.refColumn;
									
									if (refColumn) {
										
										const referencedColumnValue = BackOfficeHelper.getReferencedColumnValue(column, value, self.data);
										
										if (referencedColumnValue) {
											
											const dbTypeRefColumn = BackOfficeHelper.getDBTypes(BackOfficeHelper.DB_TYPE_STRING);
										
											let fieldRef = webix.copy(dbTypeRefColumn.component);
										
											let fieldGroup = {
												cols: [
													{ view: field.view, name: field.name, hidden: true, value: field.value },
													{ view: fieldRef.view, name: referencedColumnValue.name, required: fieldRef.required, label: referencedColumnValue.alias ?? referencedColumnValue.name, readonly: true, labelWidth: 275, value: referencedColumnValue.value },
													{
														view: "icon", 
														icon: "wxi-search",
														tooltip: i18n.t("LBL_SEARCH"),
														on: {
															onItemClick: function() {

																updateRefField(self.data.find(e => e.id === refColumn.tableId), field.name, referencedColumnValue.name);
															}
														}
													},
													{
														view: "icon", 
														icon: "wxi-plus",
														tooltip: i18n.t("LBL_CMD_ADD"),
														on: {
															onItemClick: function() {

															}
														}
													}
												]
											}
											
											fields.push(fieldGroup);
										}
										
									}
									else {
									
										fields.push(field);	
									}
								}
							}
							
							function updateRefField(table, refFieldIdName, refFieldValueName) {
								
								if (table && refFieldIdName && refFieldValueName) {
									
								}
							}
						}
						
						return fields;
					}

					return infoView;
				}
				
				function pushReferTables() {
					
					if (Array.isArray(self.currentTableData.referTables) && self.currentTableData.referTables.length > 0) {
						
						if (Array.isArray(self.currentTableData.columns) && self.currentTableData.columns.length > 0) {
								
							const columnId = self.currentTableData.columns.find(e => e.name === 'id');
							
							if (columnId) {
								
								self.currentTableData.referTables.forEach(referTable => {
								
									const label = referTable.label ?? referTable.name;
									const idValue = BackOfficeHelper.getColumnValue(self.currentTableData, columnId.id, 0); 
									const referColumn = BackOfficeHelper.getRefColumn(self.currentTableData, referTable.id);
									
									if (referColumn && idValue) {
										
										let newBackOffice = {
										
											id: self.backOffice.id,
											tableName: referTable.name,
											conditions: referColumn.name + " = '" + idValue + "'",
											containerId: self.backOffice.containerId1
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
								});
							}		
						}
					}
				}

				return tabs;
			}

			function getButtons() {

				let viewToolbarActions = {};

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
											$$(self.backOffice.containerId).close();
										}
								},
								{},
								{
									view: "button",
									value: i18n.t("LBL_ACCEPT"),
									width: 130,
									height: 41,
									css: "dialog-accept",
									click:
										function() {

											let validation = $$("form-" + self.uid).validate({ disabled: true });

											if (validation) {

												let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_TRAVEL_TIME_SAVE"), null, onAccept, null);
												ms.show();

											} else {

												$$("form-" + self.uid).show();
											}

											function onAccept() {

											}
										}
								},
								{ width: 15 }
							]
						}
					]
				}

				return viewToolbarActions;
			}
			
			return viewContent;
		}
		
		return view;
	}
}
