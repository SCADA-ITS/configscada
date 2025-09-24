import { util } from "../lib/util.js";
import { adapter } from "../lib/adapter.js";
import MessageBox from "./messageBox.js";
import PrintPreview from "./printPreview.js";

const EMPTY_ICON = "./img/grid/empty.png"; 

const DataType = {
	STRING: { value: 1, filter: "textFilter", sort: "string" },
	INT: { value: 2, filter: "numberFilter" },
	DECIMAL: { value: 3, filter: "numberFilter", defaultPrecision: 2 },
	DATE: { value: 4, filter: "dateRangeFilter", sort: "date" },
	IMAGE: { value: 5, filter: "textFilter", sort: "string" }
};

const GridOptions = {
	MULTISELECT: 1,
	SORT: 2,
	SORT_FIELD_ID: 3,
	SORT_FIELD_ORDER: 4,
	FILTER_FIELD_ID: 5,
	FILTER_FIELD_VALUE: 6,
	TB_MAIN: 7,
	TB_MAIN_SEGMENTED: 8,
	SHOW_DETAILS: 9,
	TB_ACTIONS: 10,
	TB_ACTIONS_BUTTONS_COMMONS: 11,
	TB_ACTIONS_BUTTONS_COMMANDS: 12,
	SEARCH: 13,
	FILTER: 14,
	PAGINATION: 15,
	ICON: 16,
	SELECT: 17,
	ROW_COLOR: 18,
	REFRESH_BUTTON: 20,
	ROW_ADJUSTEMENT: 21,
	HEADER_ROW_HEIGHT: 22,
	SHOW_SELECTED: 23,
	SHOW_HEADER: 24,
	SHOW_PRINT: 25,
	SHOW_EXPORT: 26,
	HELP_TAG: 27
};

const RowAdjustement = {
	DATA: "data",
	HEADER: "header",
	AUTO: "auto",
	CUSTOM: "custom"
};

const DefOptionValues = { 1: true, 2: true, 7: true, 8: true, 9: false, 10: true, 11: true, 12: true, 13: true, 14: false, 15: false, 17: true, 20: true, 21: RowAdjustement.CUSTOM, 22: 34, 23: true, 24: true, 25: false, 26: false, 27: false};

const _TRUE = "true";
const _FALSE = "false";

const DEFAULT_HEADER_HEIGHT = 38;
const NO_HEADER_HEIGHT = 0;

export default class DataTable {

	constructor(dataGridId, dataGridParams, metadata, data, events) {

		this.dataGridId = dataGridId;
		this.dataGridParams = dataGridParams;
		this.metadata = metadata;
		this.data = data;
		this.stomp = {};
		this.idStompSubscriptor = undefined;
		this.uid = webix.uid();
		this.events = events;
		this.sortedByField = {
			field: undefined,
			sort: undefined
		};
		this.filteredFields = {};
		this.rowsSelected = new Set();
		this.disabledUnselectEvent = false;
		this.filtersVisible = false;
		
		if (this.metadata.gridCommands != undefined && this.metadata.gridCommands != null && Array.isArray(this.metadata.gridCommands)){
			
			this.metadata.gridCommands = this.metadata.gridCommands.filter(item => item.moduleAction == undefined || item.moduleAction == null ||
				util.module.isAllowedModuleAction(window.em.getAll(EntityType.MODULE), item.moduleAction.id));
		}
	}

	getView() {

		let self = this;
		let metadata = this.metadata;
		let data = this.data;
		let toolbarMain = this.drawMainToolbar();
		let toolbarActions = this.drawActionsToolbar();
		let datatable = {};
		let layout = [];
		let pager = {};
		let colFillSpace = false;

		let resultSort = this.getGridOptions(GridOptions.SORT);
		let resultFilter = this.getGridOptions(GridOptions.FILTER);
		let resultMulti = this.getGridOptions(GridOptions.MULTISELECT);
		let resultSelect = this.getGridOptions(GridOptions.SELECT);
		let resultAdjColumns = this.getGridOptions(GridOptions.ROW_ADJUSTEMENT);
		let resultCommons = this.getGridOptions(GridOptions.TB_ACTIONS_BUTTONS_COMMONS);
		let resultCommands = this.getGridOptions(GridOptions.TB_ACTIONS_BUTTONS_COMMANDS);
		let resultSearch = this.getGridOptions(GridOptions.SEARCH);
		let showOnlySearchBar = !resultCommons && !resultCommands && resultSearch;
		let showHeader = this.getGridOptions(GridOptions.SHOW_HEADER);
		let columnsWithFilters = this.metadata.gridFields.filter(e => (e.filtered));
		let resultHeaderRowHeight = this.getGridOptions(GridOptions.HEADER_ROW_HEIGHT);

		if (metadata != undefined) {

			datatable.id = "dt-" + this.uid;
			datatable.view = "datatable";
			datatable.header = showHeader;
			datatable.css = "rows";
			datatable.select = "row";
			datatable.tooltip = true;
			datatable.resizeColumn = true;
			datatable.multiselect = resultMulti;
			datatable.sort = resultSort;
			datatable.filter = resultFilter;
			datatable.select = resultSelect;
			
			if (metadata.gridFields.length > 0) {

				datatable.columns = [];

				metadata.gridFields.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));

				self.translateFieldValues(data);
				self.convertDate(data);

				for (let posField = 0; posField < metadata.gridFields.length; posField++) {
					let field = metadata.gridFields[posField];

					let col = {};
					col.id = field.sourceField;

					// Paint header and filters headers
					col.header = [];
					
					if (field.label) {

						if (field.headerVertical) {

							col.header.push({
								text: `<div title="${i18n.t(field.labelTooltip)}">${i18n.t(field.label)}</div>`,
								rotate: true,
								css: { "font-size": "14px", "text-align": "center" }
							});

						} else {

							col.header.push({
								height: DEFAULT_HEADER_HEIGHT,
								text: `<div title="${i18n.t(field.labelTooltip)}">${i18n.t(field.label)}</div>`
							});
						}

					}
					else {

						col.header.push({ height: DEFAULT_HEADER_HEIGHT, text: `<div></div>` });
					}

					if (field.filtered) {

						if (field.parameterized) {
							col.header.push({ height: DEFAULT_HEADER_HEIGHT, content: "selectFilter" });
						}
						else {
							if (field.gridFieldDataType) {

								if (field.gridFieldDataType.id === DataType.STRING.value) {
									col.header.push({ height: DEFAULT_HEADER_HEIGHT, content: DataType.STRING.filter });
								}
								else if (field.gridFieldDataType.id === DataType.INT.value) {
									col.header.push({ height: DEFAULT_HEADER_HEIGHT, content: DataType.INT.filter });
								}
								else if (field.gridFieldDataType.id === DataType.DECIMAL.value) {
									col.header.push({ height: DEFAULT_HEADER_HEIGHT, content: DataType.DECIMAL.filter });
								}
								else if (field.gridFieldDataType.id === DataType.DATE.value) {
									col.header.push({ height: DEFAULT_HEADER_HEIGHT, content: DataType.DATE.filter });
								}
							}
							else {
								col.header.push({ height: DEFAULT_HEADER_HEIGHT, content: DataType.STRING.filter });
							}
						}
					}
					else {
						
						if (Array.isArray(columnsWithFilters) && columnsWithFilters.length > 0) {
							
							col.header.push({ height: DEFAULT_HEADER_HEIGHT, text: `<div></div>` });
						}
					}

					if (posField < metadata.gridFields.length) {

						col.hidden = !field.visible;
						col.width = field.widthPx;
					}

					//col.sort = DataType.STRING.sort;

					if (field.gridFieldDataType) {

						if (field.gridFieldDataType.id === DataType.INT.value || field.gridFieldDataType.id === DataType.DECIMAL.value) {

							col.sort = function(a, b) {

								let val1 = 0;
								let val2 = 0;
								
								if (!isNaN(a) || !isNaN(b)) {
									
									val1 = !isNaN(a) ? a : 0;
									val2 = !isNaN(b) ? b : 0;
									
									return (val1 > val2) ? 1 : ((val2 > val1) ? -1 : 0);
								}
								else {
									
									if (typeof a[field.sourceField] === 'object') {
	
										val1 = a[field.sourceField].value ? a[field.sourceField].value : 0;
									}
									else {
	
										val1 = a[field.sourceField] ? a[field.sourceField] : 0;
									}
	
									if (typeof b[field.sourceField] === 'object') {
	
										val2 = b[field.sourceField].value ? b[field.sourceField].value : 0;
									}
									else {
	
										val2 = b[field.sourceField] ? b[field.sourceField] : 0;
									}
	
									let val1Parse = (field.gridFieldDataType.id === DataType.INT.value) ? parseInt(val1) : parseFloat(val1);
									let val2Parse = (field.gridFieldDataType.id === DataType.INT.value) ? parseInt(val2) : parseFloat(val2);
									
									return (val1Parse > val2Parse) ? 1 : ((val2Parse > val1Parse) ? -1 : 0);	
								}
							};
						}
						else if (field.gridFieldDataType.id === DataType.DATE.value) {

							col.sort = DataType.DATE.sort;
						}
						else {
							
							col.sort = function(a, b) {

								let val1 = "";
								let val2 = "";
								
								if (a) {
								
									if (typeof a === 'string') {
									
										val1 = a;
									}
									else if (typeof a === 'object' && typeof a[field.sourceField] === 'object') {
										
										val1 = a[field.sourceField].value ?? "";
									}
									else {
										
										val1 = a[field.sourceField] ?? "";
									}	
								}
								
								if (b) {
									
									if (typeof b === 'string') {
									
										val2 = b;
									}
									else if (typeof b === 'object' && typeof b[field.sourceField] === 'object') {
										
										val2 = b[field.sourceField].value ?? "";
									}
									else {
										
										val2 = b[field.sourceField] ?? "";
									}	
								}
								
								
								if (field && Array.isArray(field.gridFieldReplacements) && field.gridFieldReplacements.length > 0) {
								
									if (val1) {
									
										let fieldReplacement1 = field.gridFieldReplacements.find(f => f.value == val1);
										
										if (fieldReplacement1 && fieldReplacement1.label) {
										
											val1 = i18n.t(fieldReplacement1.label) ?? "";	
										}
									} 	
									
									if (val2) {
									
										let fieldReplacement2 = field.gridFieldReplacements.find(f => f.value == val1);
										
										if (fieldReplacement2 && fieldReplacement2.label) {
										
											val2 = i18n.t(fieldReplacement2.label) ?? "";	
										}
									}
								}
								
								val1 = val1.toString().toLowerCase();
								val2 = val2.toString().toLowerCase();
								
								return (val1 > val2) ? 1 : ((val2 > val1) ? -1 : 0);
								
//								if (typeof a === 'string' && a !== undefined) {
//									
//									if (field && Array.isArray(field.gridFieldReplacements) && field.gridFieldReplacements.length > 0) {
//											
//										let fieldReplacementA = field.gridFieldReplacements.find(f => f.value == a);
//										
//										if (fieldReplacementA) {
//											
//											val1 = i18n.t(fieldReplacementA.label);
//										}
//									}
//									else {
//										
//										val1 = a ?? "";
//									}
//								}
//								else {
//									
//									if (typeof a[field.sourceField] === 'object' && a[field.sourceField].value !== undefined) {
//									
//										if (field && Array.isArray(field.gridFieldReplacements) && field.gridFieldReplacements.length > 0) {
//											
//											let fieldReplacementA = field.gridFieldReplacements.find(f => f.value == a[field.sourceField].value);
//											
//											if (fieldReplacementA) {
//												
//												val1 = i18n.t(fieldReplacementA.label);
//											}
//											
//										}
//										else {
//											
//											val1 = a[field.sourceField].value ? a[field.sourceField].value : "";
//										}
//									}
//									else {
//		
//										val1 = a[field.sourceField] ? a[field.sourceField] : "";
//									}	
//								}
//								
//								
//								if (typeof b === 'string' && b !== undefined) {
//									
//									if (field && Array.isArray(field.gridFieldReplacements) && field.gridFieldReplacements.length > 0) {
//											
//										let fieldReplacementB = field.gridFieldReplacements.find(f => f.value == b);
//
//										if (fieldReplacementB) {
//											
//											val2 = i18n.t(fieldReplacementB.label);
//										}
//										
//									}
//									else {
//										
//										val2 = b ?? "";
//									}
//								}
//								else {
//									
//									if (typeof b[field.sourceField] === 'object' && b[field.sourceField].value !== undefined) {
//									
//										if (field && Array.isArray(field.gridFieldReplacements) && field.gridFieldReplacements.length > 0) {
//											
//											let fieldReplacementB = field.gridFieldReplacements.find(f => f.value == b[field.sourceField].value);
//											
//											if (fieldReplacementB) {
//												
//												val2 = i18n.t(fieldReplacementB.label);
//											}
//											
//										}
//										else {
//											
//											val2 = b[field.sourceField].value ? b[field.sourceField].value : "";
//										}
//									}
//									else {
//		
//										val2 = b[field.sourceField] ? b[field.sourceField] : "";
//									}	
//								}
//								
//								
//								val1 = val1.toString().toLowerCase();
//								val2 = val2.toString().toLowerCase();
//								
//								return (val1 > val2) ? 1 : ((val2 > val1) ? -1 : 0);
							}
						}
					}

					if (field.align != undefined) {

						col.css = { 'text-align': field.align }
					}

					col.template = function(obj) {

						let value = "";

						if (obj[field.sourceField] != undefined) {

							let state;
							let icon;
							let badgeColor;
							let avatar;
							let showValue = false;

							if (typeof obj[field.sourceField] === 'object' && field.gridFieldDataType && field.gridFieldDataType.id !== DataType.DATE.value) {

								if (obj[field.sourceField].template) {

									value = eval(obj[field.sourceField].template)();

								} else {

									value = obj[field.sourceField].value;
									state = obj[field.sourceField].state;

									if (Array.isArray(field.gridFieldReplacements) && field.gridFieldReplacements.length > 0) {

										let gridFieldReplacement = field.gridFieldReplacements.find(e => e.value == value);

										if (gridFieldReplacement) {

											value = field.needsTranslation ? i18n.t(gridFieldReplacement.label) : gridFieldReplacement.label;
										}
									}
								}
							}
							else {
								
								if (field.replacementFunction) {

									if (eval("util.datatable." + field.replacementFunction)) {
										
										value = eval("util.datatable." + field.replacementFunction)(obj[field.sourceField], obj);
									}
								}
								
								if (field.gridFieldDataType && field.gridFieldDataType.id === DataType.DATE.value && value === "" && obj[field.sourceField] && typeof(obj[field.sourceField]) === 'object'){

									value = obj[field.sourceField].toLocaleString(i18n.getLocale(), FORMAT_NUMERIC_DATES);

								} else if (field.gridFieldDataType && field.gridFieldDataType.id === DataType.IMAGE.value) {
									
									return `<div><img class="table-field-icons" src='img/${obj[field.sourceField]}'></div>`;

								} else {
									
									if (Array.isArray(field.gridFieldReplacements) && field.gridFieldReplacements.length > 0) {
	
										let gridFieldReplacement = field.gridFieldReplacements.find(e => e.value == obj[field.sourceField]);
	
										if (gridFieldReplacement) {
	
											value = field.needsTranslation ? i18n.t(gridFieldReplacement.label) : gridFieldReplacement.label;
											
											obj[field.sourceField] = value;
										}
									}
									
									if (field.gridFieldReferences != undefined && field.gridFieldReferences.length > 0) {
	
	
										let gridFieldReference = field.gridFieldReferences[0];
	
										let refField = metadata.gridFields.filter(e => e.id == gridFieldReference.refFieldId);
	
	
										if (refField != undefined) {
	
											value = window.em.getByField(EntityType[gridFieldReference.emId], gridFieldReference.emField, obj[refField[0].sourceField]);
	
											value = value[gridFieldReference.emFieldReturned];
	
											if (field.needsTranslation) {
	
												value = i18n.t(value);
	
												obj[field.sourceField] = value;
											}
										}
									}
	
									if (field.gridFieldIcons != undefined && field.gridFieldIcons.length > 0) {
	
										badgeColor = (field.gridFieldIcons[0].badgeColor == true);
										avatar = (field.gridFieldIcons[0].avatar == true);
										showValue = (field.gridFieldIcons[0].showValue == true);
										
										if (value === "") {
										
											value = obj[field.sourceField];	
										}
										
										let gridFieldIcon = field.gridFieldIcons.find(e => e.value == value || i18n.t(e.labelValue) == value);
	
										if (gridFieldIcon) {
	
											icon = gridFieldIcon.icon;
											showValue = (gridFieldIcon.showValue == true);
										}
									}
									else {
	
										if (value === "") {
											
											value = obj[field.sourceField];	
										}
									}
								}
							}

							col.tooltip = function(data) {

								if (data[field.sourceField]) {
									
									if (field.gridFieldDataType.id === DataType.DATE.value) {
										
										return data[field.sourceField].toLocaleString(i18n.getLocale(), FORMAT_STRING_DATES);
	
									} else if (Array.isArray(field.gridFieldIcons) && field.gridFieldIcons.length > 0) {
	
										let label = field.gridFieldIcons.find(e => e.value == data[field.sourceField] || i18n.t(e.labelValue) == data[field.sourceField]);
	
										if (label != undefined) {
											//let tool = label.labelValue != "" && label.labelValue ? label.labelValue : data[field.sourceField];
											return !label.labelValue ? i18n.t("LBL_UNKNOWN") : i18n.t(label.labelValue);
	
										} else {
	
											return data[field.sourceField];
										}

									} else if (field.replacementFunction) {

										if (eval("util.datatable." + field.replacementFunction)) {

											return eval("util.datatable." + field.replacementFunction)(data[field.sourceField]);
										}

									} 
									else if (data[field.sourceField].tooltip) {

										return eval(data[field.sourceField].tooltip)();
									} 
									else {

										return (data[field.sourceField].value != undefined) ? data[field.sourceField].value : data[field.sourceField]; 	
										
									}
								} else {
									
									return "";
								}
							};

							if (icon || badgeColor || avatar) {

								if (icon) {

									if (!showValue) {

										return `<img class="table-field-icons" src="${icon}" onerror="this.onerror=null;this.src='./img/grid/empty.png'"/>`;
									}
									else {

										return `<div style="width: ${field.widthPx}">
													<span>${value}</span>		
													<img class="table-field-icons" src="${icon}" 
														 onerror="this.onerror=null; this.style.display = 'none'"/>
												</div>`
									}
								}
								else if (badgeColor) {

									let balloon = undefined;

									if (Array.isArray(field.gridFieldBalloons) && field.gridFieldBalloons.length > 0) {

										balloon = field.gridFieldBalloons.find(e => e.value === value);
									}

									if (balloon) {
										
										return `<div><span class="table-badge" style="background: ${balloon.color + 'a0'}; color: white;">${value}</span></div>`;

									} else {

										let hashValue = util.getHash(value);
										let color;

										let mod = Math.abs(hashValue) % 10;

										switch (mod) {

											case 0:
												color = '#008000';
												break;
											case 1:
												color = '#0000ff';
												break;
											case 2:
												color = '#a52a2a';
												break;
											case 3:
												color = '#009688';
												break;
											case 4:
												color = '#ffa500';
												break;
											case 5:
												color = '#ff00ff';
												break;
											case 6:
												color = '#800080';
												break;
											case 7:
												color = '#000000';
												break;
											case 8:
												color = '#808080';
												break;
											case 9:
												color = '#6495ed';
												break;
										}

										return `<div><span class="table-badge" style="background: ${color + '3b'}; color: ${color + 'a0'};">${value}</span></div>`;
									}


								}
								else if (avatar) {

									if (!showValue) {

										return `<img class="table-field-icons" src="${util.generateAvatar(value)}"/>`;
									}
									else {

										return `<div style="width: ${field.widthPx}">	
													<img class="table-field-icons" src="${util.generateAvatar(value)}"/>
													<span>${value}</span>
												</div>`
									}
								}
							}
							else if (value != undefined) {
								
								if (field.gridFieldDataType.id === DataType.DECIMAL.value) {

									let parseValue = (typeof (value) === DataType.STRING.sort) ? parseFloat(value) : value;
									let precision = (field.precisionFilter) ? field.precisionFilter : DataType.DECIMAL.defaultPrecision;
									value = parseValue.toFixed(precision);
								}

								if (state != undefined) {

									//let iconElementState = "ElementValueState_" + state + ".png";
									let iconElementState = state.replace(":", "_") + ".png";

									return `<div style="width: ${field.widthPx}">
												<span>${value}</span>		
												<img class="table-field-icons" src="./img/state/${iconElementState}" 
													 onerror="this.onerror=null; this.style.display = 'none'"/>
											</div>`
								}
								else {

									if (typeof value === "string") {
										
										return `<span class="table-row-text">${value.split(" ").map(util.convertToLink).join(" ")}</span>`;
									}
									
									return `<span class="table-row-text">${value}</span>`;
								}
							}
							else {
								if (field.gridFieldIcons != undefined && field.gridFieldIcons.length > 0) {
									
									return `<img class="table-field-icons" src="${EMPTY_ICON}" />`;
								}
								else {
								
									return `<span class="table-row-text">-</span>`;	
								}
							}
						}
						else {

							if (value === '') {
								
								if ((field.gridFieldIcons != undefined && field.gridFieldIcons.length > 0) ||
									(field.gridFieldDataType && field.gridFieldDataType.id === DataType.IMAGE.value)) {
									
									return `<img class="table-field-icons" src="${EMPTY_ICON}" />`;
								}
								else {
								
									return `<span class="table-row-text">-</span>`;	
								}
							}
							else {
								
								if (typeof value === "string") {
																		
									return `<span class="table-row-text">${value.split(" ").map(util.convertToLink).join(" ")}</span>`;
								}

								return `<span class="table-row-text">${value !== undefined ? value : "-"}</span>`;
							}

						}
					}

					if (resultAdjColumns) {

						if (resultAdjColumns === RowAdjustement.DATA) {

							col.adjust = RowAdjustement.DATA;

						} else if (resultAdjColumns === RowAdjustement.HEADER) {

							col.adjust = RowAdjustement.HEADER;

						} else if (resultAdjColumns === RowAdjustement.AUTO) {

							col.adjust = true;

						} else if (resultAdjColumns === RowAdjustement.CUSTOM) {

							if (field.adjust) {

								col.fillspace = true;
								col.width = undefined;

								colFillSpace = true;
							}
						}
					}

					if (field.fixedColumn) {

						datatable.leftSplit = field.position;

					}

					datatable.columns.push(col);
				}

				if (!colFillSpace) {

					let lastCol = {};

					lastCol.fillspace = true;
					datatable.columns.push(lastCol);
				}

				datatable.data = data;

			}

			//layout.push({ height: 8 });
			let resultTbMain = this.getGridOptions(GridOptions.TB_MAIN);

			if (resultTbMain) {

				layout.push(toolbarMain);
			}

			let resultTbActions = this.getGridOptions(GridOptions.TB_ACTIONS);
			let resultOption = this.evalActionsToolbar();

			if (resultOption && resultTbActions) {

				layout.push(toolbarActions);

				if (showOnlySearchBar) {

					layout.push({ height: 8 });
				}
			}

			layout.push(datatable);

			let resultPager = this.getGridOptions(GridOptions.PAGINATION);

			if (resultPager) {

				pager = {
					view: "pager",
					id: "pager-" + this.uid,
					size: 50,
					group: 5
				}
				datatable.pager = "pager-" + this.uid;
				layout.push(pager);
			}

			//layout.push({ height: 16 });
		}

		return layout;
	}

	translateFieldValues(data) {

		if (data && Array.isArray(data)) {
			for (let posField = 0; posField < this.metadata.gridFields.length; posField++) {

				let field = this.metadata.gridFields[posField];
				
				if (field.needsTranslation && (!Array.isArray(field.gridFieldReplacements) || field.gridFieldReplacements.length === 0) && !field.replacementFunction) {

					data.forEach(item => {

						if (item[field.sourceField] != undefined) {

							if (typeof item[field.sourceField] === 'object') {

								item[field.sourceField].value = i18n.t(item[field.sourceField].value);
							}
							else {

								item[field.sourceField] = i18n.t(item[field.sourceField]);
							}
						}
					});
				}	
			}
		}
	}

	convertDate(data) {

		if (data && Array.isArray(data)) {
			
			if (Array.isArray(this.metadata.gridFields) && this.metadata.gridFields.length > 0) {
			
				this.metadata.gridFields.forEach(field => {
					
					if (field.isEpochMilli) {

						data.forEach(item => {
	
							if(field.sourceField != undefined){
							
								if (item[field.sourceField]) {
									
									if (typeof(item[field.sourceField]) === 'object') {
										
										if (item[field.sourceField].value && !isNaN(item[field.sourceField].value)) {
									
											item[field.sourceField]	= new Date(parseInt(item[field.sourceField].value));
										}
										else {
											
											if(!(item[field.sourceField] instanceof Date)){
												
												item[field.sourceField] = undefined;	
											}											
										}
									}
									else {
										
										if (item[field.sourceField] && !isNaN(item[field.sourceField])) {
											
											item[field.sourceField]	= new Date(parseInt(item[field.sourceField]));
										}
									}
								}
							}
						});
					}
				});
			}
		}
	}

	getViewId() {

		return "dt-" + this.uid;
	}

	getSelectedIds() {

		let grid = $$("dt-" + this.uid);
		let selectedPks = [];

		if (grid != undefined) {

			let itemsSelected = grid.getSelectedItem(true);

			if (itemsSelected != null && itemsSelected.length > 0 && this.metadata.gridFields != undefined) {

				let pkField = this.metadata.gridFields.find(e => { return e.isPk === true });

				if (pkField) {

					itemsSelected.forEach(itemSelected => {

						selectedPks.push(itemSelected[pkField.sourceField]);
					});
				}
			}
		}

		return selectedPks;
	}

	getAllItems() {

		let grid = $$("dt-" + this.uid);
		let allItems = [];

		if (grid != undefined) {

			allItems = grid.serialize();
		}

		return allItems;
	}

	getItem(itemId) {

		let grid = $$("dt-" + this.uid);
		let item;

		if (grid != undefined) {

			item = grid.getItem(itemId);
		}

		return item;
	}

	getSelectedItems() {

		let grid = $$("dt-" + this.uid);
		let selectedItems = [];

		if (grid != undefined) {

			selectedItems = grid.getSelectedItem(true);
		}

		return selectedItems;
	}

	enable() {

		if($$("tbActions-" + this.uid)){

			$$("tbActions-" + this.uid).enable();
		}
		
		if($$("ctx_menu-" + this.uid)){

			$$("ctx_menu-" + this.uid).enable();
		}

		$$("dt-" + this.uid).on_dblclick=function(){};
	}

	disable() {

		if($$("tbActions-" + this.uid)){

			$$("tbActions-" + this.uid).disable();
		}
		
		if($$("ctx_menu-" + this.uid)){

			$$("ctx_menu-" + this.uid).disable();
		}

		if($$("dt-" + this.uid)){

			$$("dt-" + this.uid).on_dblclick="";
		}
	}
	
	disableAll() {
		
		if ($$("dt-" + this.uid)) {
			
			$$("dt-" + this.uid).disable();
		}
	}
	
	enableAll() {
		
		if ($$("dt-" + this.uid)) {
			
			$$("dt-" + this.uid).enable();
		}
	}
	
	commandDisable(commandId) {

		let command = $$("tb_btn-" + this.uid + "_" + commandId);
		let ctxMenu = $$("ctx_menu-" + this.uid);

		if (command) {
			
			let cssClass = (command.config.label != "") ? "command-label-datatable-disable" : "command-icon-datatable-disable";

			webix.html.addCss(command.$view, cssClass);
			command.disable();

			if (ctxMenu) {

				let commandIcon = ctxMenu.config.data.find(dat => dat.icon === command.config.icon);

				if (commandIcon) {

					commandIcon.disabled = true;
				}

				ctxMenu.refresh();
			}
		}
	}

	commandEnable(commandId) {

		let command = $$("tb_btn-" + this.uid + "_" + commandId);
		let ctxMenu = $$("ctx_menu-" + this.uid);

		if (command) {

			let cssClass = (command.config.label != "") ? "command-label-datatable-disable" : "command-icon-datatable-disable";
	
			webix.html.removeCss(command.$view, cssClass);
			command.enable();

			if (ctxMenu) {

				let commandIcon = ctxMenu.config.data.find(dat => dat.icon === command.config.icon);

				if (commandIcon) {

					commandIcon.disabled = false;
				}

				ctxMenu.refresh();
			}
		}
	}

	drawMainToolbar() {

		let metadata = this.metadata;
		let resultIcon = this.getGridOptions(GridOptions.ICON);

		let toolbar = {
			view: "toolbar",
			height: 30,
			id: "tbMain-" + this.uid,
			borderless: true
		}


		toolbar.elements = [];
		toolbar.elements.push({ width: 4 });
		
		if (resultIcon && resultIcon.startsWith('mdi')) {

			toolbar.elements.push({ view: "label", label: `<span class="${resultIcon} material-icons md-24"></span><span class="grid-title">${i18n.t(metadata.label)}</span>` });
	
		} else {

			toolbar.elements.push({ view: "label", label: `<img src="${resultIcon}" class="grid-icon" onerror="this.onerror=null;this.src='./img/grid/default.png'" /><span class="grid-title">${i18n.t(metadata.label)}</span>` });
		}

		toolbar.elements.push({ minWidth: 4 });

		toolbar.elements.push({ width: 8 });

		return toolbar;
	}



	drawActionsToolbar() {

		let metadata = this.metadata;
		let dataGridId = this.dataGridId;
		let self = this;

		let resultCommons = this.getGridOptions(GridOptions.TB_ACTIONS_BUTTONS_COMMONS);
		let resultCommands = this.getGridOptions(GridOptions.TB_ACTIONS_BUTTONS_COMMANDS);
		let resultSearch = this.getGridOptions(GridOptions.SEARCH);
		let resultMulti = this.getGridOptions(GridOptions.MULTISELECT);
		let resultSelect = this.getGridOptions(GridOptions.SELECT);
		let resultRefresh = this.getGridOptions(GridOptions.REFRESH_BUTTON);
		let resultSegmented = this.getGridOptions(GridOptions.TB_MAIN_SEGMENTED);
		let resultSelRows = this.getGridOptions(GridOptions.SHOW_SELECTED);
		let resultPrint = this.getGridOptions(GridOptions.SHOW_PRINT);
		let resultExport = this.getGridOptions(GridOptions.SHOW_EXPORT);
		let resultHelp = this.getGridOptions(GridOptions.HELP_TAG);

		let showOnlySearchBar = !resultCommons && !resultCommands && resultSearch;
		let resultFilter = undefined;
		let showLegend = false;

		if (Array.isArray(metadata.gridFields) && metadata.gridFields.length > 0) {

			let gridFieldsWithIcons = undefined;
			gridFieldsWithIcons = metadata.gridFields.filter(e => (Array.isArray(e.gridFieldIcons) && e.gridFieldIcons.length > 0));

			if (Array.isArray(gridFieldsWithIcons) && gridFieldsWithIcons.length > 0) {

				for (let gridFieldItem = 0; !showLegend && gridFieldItem < gridFieldsWithIcons.length; gridFieldItem++) {

					let gridField = gridFieldsWithIcons[gridFieldItem];

					showLegend |= (gridField.gridFieldIcons.find(e => e.icon) != undefined);
				}
			}

			resultFilter = metadata.gridFields.find(e => e.filtered === true);
		}

		let toolbar = {
			view: "toolbar",
			height: 38,
			id: "tbActions-" + this.uid,
			borderless: true
		}

		let searchBar = {
			view: "search",
			id: "txtSearch-" + this.uid,
			placeholder: i18n.t("LBL_SEARCH"),
		}

		if (showOnlySearchBar) {

			return searchBar;
		}

		toolbar.elements = [];

		toolbar.elements.push({ width: 4 })

		if (resultMulti && resultSelect) {

			toolbar.elements.push({ 
				id: "tb_btn-select-all-" + this.uid, 
				view: "icon", 
				icon: "mdi mdi-checkbox-blank-outline",
				value: i18n.t("LBL_SELECT_ALL"), 
				tooltip: function(obj){
					
					let tooltip = i18n.t("LBL_NO_ITEMS_TO_SELECT");
					
					if($$("dt-"+ self.uid).count() > 0){
					
						const ICON_ALL_SELECTED = "mdi mdi-check-box-outline";	
						tooltip = (obj.icon === ICON_ALL_SELECTED) ? i18n.t("LBL_UNSELECT_ALL") : i18n.t("LBL_SELECT_ALL");
					} 
					
					return tooltip;				
				}});
				
			//toolbar.elements.push({ id: "tb_btn-unselect-all-" + this.uid, view: "icon", icon: "mdi mdi-checkbox-blank-outline", value: i18n.t("LBL_UNSELECT_ALL"), tooltip: true });
		}

		if (resultCommons) {

			if (resultFilter) {

				toolbar.elements.push({ id: "tb_btn-showfilters-" + this.uid, view: "icon", icon: "wxi-filter", value: i18n.t("LBL_FILTERS"), tooltip: true });
			}

			if (dataGridId && resultRefresh) {

				toolbar.elements.push({ id: "tb_btn-refresh-" + this.uid, view: "icon", icon: "mdi mdi-refresh", value: i18n.t("LBL_TABLE_REFRESH"), tooltip: true });
			}

			if(resultPrint){
			
				toolbar.elements.push({ id: "tb_btn-print-" + this.uid, view: "icon", icon: "mdi mdi-printer", value: i18n.t("LBL_TABLE_PRINT"), tooltip: true });		
			}			

			if(resultExport){
			
				toolbar.elements.push({ id: "tb_btn-export-" + this.uid, view: "icon", icon: "mdi mdi-file-export", value: i18n.t("LBL_TABLE_EXPORT"), tooltip: true });		
			}			

			if (resultSegmented) {

				toolbar.elements.push({ id: "tb_btn-showseg-" + this.uid, view: "icon", icon: "wxi-columns", value: i18n.t("LBL_SHOW_DETAILS"), tooltip: true });
			}

			if (showLegend) {

				toolbar.elements.push({ id: "tb_btn-icons-" + this.uid, view: "icon", icon: "mdi mdi-map-legend", value: i18n.t("LBL_TABLE_LEGEND"), tooltip: true });
			}
			
			if (resultHelp) {

				toolbar.elements.push({ id: "tb_btn-help-" + this.uid, view: "icon", icon: "mdi mdi-help", value: i18n.t("LBL_HELP"), tooltip: true });
			}
		}

		if (Array.isArray(metadata.gridCommands) && metadata.gridCommands.length > 0 && resultCommands) {
			
			if (resultCommons) {
				
				toolbar.elements.push({ width: 3 });
				toolbar.elements.push(
					{ 
						width: 1,
						rows: [
							{ height: 8 },
							{ type: "template", css: {"border-left": "1px solid #dadee0 !important"}, borderless: true },
							{ height: 8 }		
						]
					}
				);
				toolbar.elements.push({ width: 3 });	
			}
			
			metadata.gridCommands.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));

			let groupedMenu = metadata.gridCommands.filter(e => e.grouped);
			let noGroupedMenu = metadata.gridCommands.filter(e => !e.grouped);

			if (noGroupedMenu && noGroupedMenu.length > 0) {

				noGroupedMenu.forEach(cmd => {

					if (cmd.showText) {

						toolbar.elements.push({ id: "tb_btn-" + this.uid + "_" + cmd.id, view: "button", type: "icon", icon: cmd.icon, css: "grid-button-text", width: 120, label: i18n.t(cmd.label), tooltip: i18n.t(cmd.label) });

					} else {

						toolbar.elements.push({ id: "tb_btn-" + this.uid + "_" + cmd.id, view: "icon", icon: cmd.icon, value: i18n.t(cmd.label), tooltip: true });
					}
				});
			}

			if (groupedMenu && groupedMenu.length > 0) {

				let subMenu = {
					view: "menu",
					id: "subMenu" + this.uid,
					autowidth: true,
					openAction: "click",
					width: 34,
					height: 34,
					css: "dt-submenu",
					subMenuPos: "down",
					data: [
						{ value: "", icon: "mdi mdi-dots-vertical", submenu: [] },
					],
					on: {
						onMenuItemClick: function(id) {
							
							const item = this.getMenuItem(id);
							
							if (item && item.cmd) {
								
								self.open_module(item.cmd);	
							}
							
//
//							let gridCommand = metadata.gridCommands.find(element => {
//								return (element.moduleAction.id === this.getMenuItem(id).moduleId);
//							});
//
//							if (gridCommand) {
//								self.open_module(gridCommand);
//							}
						}
					}
				};

				groupedMenu.forEach(cmd => {
					//subMenu.data[0].submenu.push({ id: "tb_btn-" + this.uid + "_" + cmd.id, view: "button", type: "icon", icon: cmd.icon, value: i18n.t(cmd.label), moduleId: cmd.moduleAction.id});
					subMenu.data[0].submenu.push({ id: "tb_btn-" + this.uid + "_" + cmd.id, view: "button", type: "icon", icon: cmd.icon, value: i18n.t(cmd.label), cmd: cmd});
				});

				toolbar.elements.push(subMenu);

			}
		}

		toolbar.elements.push({});
		//toolbar.elements.push({ minWidth: 4 });

		toolbar.elements.push({
			id: "tb_ws_status-" + this.uid,
			width: 1,
			view: "label"
		});

		if (resultSelRows) {
			
			toolbar.elements.push(
				{ 
					id: "template-sel-" + this.uid,
					borderless: true,
					minWidth: 120,
					template: function(obj) { 
						
						if (obj.selected !== undefined && obj.total !== undefined) {
							
							return `<div class="sel_label"><span>${i18n.t("LBL_SEL")}</span><span class="sel_label_text">${" " + obj.selected + "/" + obj.total}</span></div>`;	
						}
					}
				}
			);	
		}

		if (resultSearch) {

			searchBar.width = 200;
			toolbar.elements.push(searchBar);
		}

		if (!showOnlySearchBar) {

			toolbar.elements.push({ width: 8 });
		}

		return toolbar;
	}

	drawContextButtonAction() {

		let ctxMenuData = [];
		let self = this;
		let metadata = this.metadata;
		let resultCommands = this.getGridOptions(GridOptions.TB_ACTIONS_BUTTONS_COMMANDS);

		if (metadata.gridCommands && resultCommands) {

			metadata.gridCommands.forEach(cmd => {
					ctxMenuData.push({ id: cmd.id, value: i18n.t(cmd.label), icon: cmd.icon, default: cmd.default_ });

			});

			if (ctxMenuData.length > 0) {

				webix.ui({
					view: "contextmenu",
					id: "ctx_menu-" + self.uid,
					width: 300,
					data: ctxMenuData,
					master: $$("dt-" + self.uid).$view,
					template: (obj) => {

						if (!obj.default) {
							return `<span class = 'webix_icon ${obj.icon}'></span> <span style='position: relative; top: -1px; left: 10px;'>${obj.value}</span>`;
						}
						else {
							return `<span class = 'webix_icon ${obj.icon}'></span> <span style='position: relative; top: -1px; left: 10px; font-weight: 500;'>${obj.value}</span>`;
						}

					},
					on: {
						onMenuItemClick: function(id) {

							let gridCommand = metadata.gridCommands.find(e => {
								return e.id === this.getItem(id).id
							});

							if (gridCommand) {

								self.open_module(gridCommand);
							}
						}
					}

				});
			}
		}
	}
	
	executeCommand(gridCommandId) {
		
		let self = this;
		let metadata = this.metadata;

		if (metadata && metadata.gridCommands) {

			let gridCommand = metadata.gridCommands.find(e => e.id === gridCommandId);
			
			if (gridCommand) {

				self.open_module(gridCommand);
			}
		}
	}

	selectDatatable() {

		let self = this;
		let datatable = $$("dt-" + this.uid);
		let rowsSelected = this.rowsSelected;
		let labelSelected = $$("template-sel-" + this.uid);
		let selectAllButton = $$("tb_btn-select-all-" + this.uid);
		const Selected = { ZERO: 0, ONE: 1 };

		datatable.attachEvent("onAfterSelect", function(selection, preserve) {

			if (preserve) {

				if (!rowsSelected.has(selection.id)) {
				
					rowsSelected.add(selection.id);	
				}

			} else {

				rowsSelected.clear();
				rowsSelected.add(selection.id);
			}

		});

		datatable.attachEvent("onAfterUnSelect", function(selection) {

			if (!self.disabledUnselectEvent && rowsSelected.size > 0) {;
				
				rowsSelected.delete(selection.row);
			}

		});

		datatable.attachEvent("onAfterRender", function() {

			if (labelSelected) {
				
				labelSelected.setValues({ selected: rowsSelected.size, total: datatable.count() });
			}
			
			if(selectAllButton){
			
				let allSelected = ((rowsSelected.size == datatable.count()) && (rowsSelected.size > 0)) ? true: false;
				
				if(allSelected){
				
					selectAllButton.config.icon = "mdi mdi-check-box-outline";
					selectAllButton.refresh();
						
				} else {
						
					selectAllButton.config.icon = "mdi mdi-checkbox-blank-outline";
					selectAllButton.refresh();
				}				
			}	
		});

	}

	sortDatatable(gridFieldId, order) {

		let dataTable = $$("dt-" + this.uid);
		let metadata = this.metadata;

		if (gridFieldId && order) {

			let gridField = metadata.gridFields.find(e => {

				return e.id === gridFieldId;

			});

			if (gridField) {

				dataTable.markSorting(gridField.sourceField, order);
				dataTable.sort(gridField.sourceField, order, dataTable.getColumnConfig(gridField.sourceField).sort);
			}

		} else {

			let resultId = this.getGridOptions(GridOptions.SORT_FIELD_ID);

			if (resultId) {

				let gridField = metadata.gridFields.find(e => {
					return e.id === parseInt(resultId);
				});

				if (gridField) {

					let resultOrd = this.getGridOptions(GridOptions.SORT_FIELD_ORDER);

					dataTable.markSorting(gridField.sourceField, resultOrd);
					dataTable.sort(gridField.sourceField, resultOrd, dataTable.getColumnConfig(gridField.sourceField).sort);
				}
			}
		}
	}

	filterDatatable(gridFieldId, value) {

		let metadata = this.metadata;
		let dataTable = $$("dt-" + this.uid);

		if (gridFieldId) {

			let gridField = metadata.gridFields.find(e => {

				return e.id === gridFieldId;
			});

			if (gridField) {

				dataTable.filter(function(obj) {

					let res = false;
					
					if (obj[gridField.sourceField]) {
						
						res = (obj[gridField.sourceField].toString().indexOf(value) != -1);
					}
					
					return res;
				});
			}
		} else {

			let resultId = this.getGridOptions(GridOptions.FILTER_FIELD_ID);

			if (resultId) {

				let gridField = metadata.gridFields.find(e => {

					return e.id === parseInt(resultId);
				});

				if (gridField) {

					let resultFilter = this.getGridOptions(GridOptions.FILTER_FIELD_VALUE);

					dataTable.filter(function(obj) {

						let res = false;
					
						if (obj[gridField.sourceField]) {
							
							res = (obj[gridField.sourceField].toString().indexOf(resultFilter) != -1);
						}
						
						return res;
					});
				}
			}
		}
	}

	showMainToolbar() {

		let resultViews = this.getGridOptions(GridOptions.SHOW_DETAILS);

		if (!resultViews) {

			this.change_view("hide");
		}
	}

	getGridOptions(option) {

		let metadata = this.metadata;
		let optionValue = undefined;

		if (metadata) {

			optionValue = metadata.gridOptionValues.find(e => e.gridOption.id === option);

			if (optionValue && optionValue.value) {

				if (optionValue.value === _TRUE) {

					return true;

				} else if (optionValue.value === _FALSE) {

					return false;

				} else {

					return optionValue.value;
				}
			}
			else {

				return DefOptionValues[option];
			}
		}
	}

	evalActionsToolbar() {

		let resultCommons = this.getGridOptions(GridOptions.TB_ACTIONS_BUTTONS_COMMONS);
		let resultCommands = this.getGridOptions(GridOptions.TB_ACTIONS_BUTTONS_COMMANDS);
		let resultSearch = this.getGridOptions(GridOptions.SEARCH);
		let resultMulti = this.getGridOptions(GridOptions.MULTISELECT);

		let resultOption;
		let toolbarOptions = [];

		toolbarOptions.push(resultCommons, resultCommands, resultSearch, resultMulti);

		resultOption = toolbarOptions.find(e => e === true);

		return resultOption
	}

	clear() {

		let grid = $$("dt-" + this.uid);
		let reApplyFilters = false;

		if (grid) {

			grid.eachColumn(function(id, col) {

				var filter = this.getFilter(id);

				if (filter) {

					reApplyFilters = true;

					if (filter.setValue) filter.setValue("")	// suggest-based filters 
					else filter.value = "";					// html-based: select & text
				}
			});

			if (reApplyFilters) {

				grid.filterByAll(); //applies filters (necessary only in case of selectFilter)	
			}

			this.filteredFields = {};

			grid.refreshColumns();

			grid.clearAll();

			this.rowsSelected.clear();
		}
	}

	add(items) {

		let dataTable = $$("dt-" + this.uid);
		let anyChange = false;

		if (dataTable && items) {

			this.disabledUnselectEvent = true;

			if (Array.isArray(items) && items.length > 0) {

				items.forEach(dat => {

					if (!dataTable.find(function(e) { return e.id === dat.id; }, true)) {

						dataTable.add(dat);
						anyChange = true;
					}
				});
			}
			else if (!Array.isArray(items)) {

				if (!dataTable.find(function(e) { return e.id === items.id; }, true)) {

					dataTable.add(items);
					anyChange = true;
				}
			}

			if (anyChange) {

				//dataTable.refresh();
				this.restoreFilters();

				dataTable.refreshColumns();
				this.restoreSelected();
				this.restoreSort();
			}

			this.disabledUnselectEvent = false;
		}
	}

	update(items) {

		let dataTable = $$("dt-" + this.uid);
		let anyChange = false;


		if (dataTable && items) {

			this.disabledUnselectEvent = true;

			if (Array.isArray(items) && items.length > 0) {

				items.forEach(dat => {

					let dataToUpdate = dataTable.find(function(e) { return e.id === dat.id; }, true);

					if (dataToUpdate) {

						dataTable.updateItem(dataToUpdate.id, dat);

						anyChange = true;
					}
				});

			} else if (!Array.isArray(items)) {

				let dataToUpdate = dataTable.find(function(e) { return e.id === items.id; }, true);

				if (dataToUpdate) {

					dataTable.updateItem(dataToUpdate.id, items);

					anyChange = true;
				}
			}

			if (anyChange) {

				//dataTable.refresh();
				this.restoreFilters();
				this.restoreSort();
				dataTable.refreshColumns();
				this.restoreSelected();

			}

			this.disabledUnselectEvent = false;
		}
	}


	delete(items) {

		let dataTable = $$("dt-" + this.uid);
		let anyChange = false;

		if (dataTable && items) {

			this.disabledUnselectEvent = true;

			if (Array.isArray(items) && items.length > 0) {

				items.forEach(dat => {

					if (dataTable.find(function(e) { return e.id === dat.id; }), true) {

						dataTable.remove(dat.id);

						anyChange = true;
					}

				});

			} else if (!Array.isArray(items)) {

				if (dataTable.find(function(e) { return e.id === items.id; }), true) {

					dataTable.remove(items.id);

					anyChange = true;
				}
			}

			if (anyChange) {

				//dataTable.refresh();
				this.restoreFilters();
				this.restoreSort();
				dataTable.refreshColumns();
				this.restoreSelected();
			}

			this.disabledUnselectEvent = false;
		}
	}

	restoreSelected() {

		let grid = $$("dt-" + this.uid);
		let self = this;
		
		if (this.rowsSelected.size > 0) {
			
			let rowsSelectedCopy = new Set(this.rowsSelected);
		
			rowsSelectedCopy.forEach(rowSelected => {
				
				if (grid.getItem(rowSelected)) {
						
					grid.select(rowSelected, true);
				}
				else {
					self.rowsSelected.delete(rowSelected);
				}
			});	
		}
	}

	restoreSort() {

		let grid = $$("dt-" + this.uid);

		if (this.sortedByField.field != undefined && this.sortedByField.sort != undefined) {

			grid.sort(this.sortedByField.field, this.sortedByField.sort, grid.getColumnConfig(this.sortedByField.field).sort);
		}
	}

	restoreFilters() {

		let grid = $$("dt-" + this.uid);

		grid.filterByAll();

		//		if (Array.isArray(this.filteredFields) && this.filteredFields.length > 0)  {
		//
		//		var fields = Object.keys(this.filteredFields);
		//
		//		grid.filter(function(data) {
		//
		//			let res = true;
		//
		//			fields.forEach(field => {
		//
		//				if (res === true && self.filteredFields[field]) {
		//
		//					let metadataField = self.metadata.gridFields.find(f => {
		//
		//						return f.sourceField === field;
		//					});
		//
		//					if (metadataField) {
		//
		//						if ((metadataField.parameterized)) {
		//
		//							res &= (data[field].toString() === self.filteredFields[field]);
		//						}
		//						else {
		//
		//							res &= (data[field].toString().toLowerCase().indexOf(self.filteredFields[field].toString().toLowerCase())) != -1;
		//						}
		//					}
		//				}
		//			});
		//
		//			return res;
		//		});
		//}
	}


	clickShowFilters() {

		let showFiltersButton = $$("tb_btn-showfilters-" + this.uid);
		let datatable = $$("dt-" + this.uid);
		let resultFilters = this.getGridOptions(GridOptions.FILTER);
		let filtered = this.metadata.gridFields.find(e => e.filtered === true && e.visible === true);

		self.filtersVisible = (resultFilters != undefined && resultFilters !== false);

		if (showFiltersButton) {

			if (resultFilters) {

				showFiltersButton._contentobj.className = "webix_view webix_control webix_el_icon filter_icon_selected";
				showFiltersButton.refresh();
			}

			showFiltersButton.attachEvent("onItemClick", function(id, e) {

				if (datatable && filtered) {

					let header = datatable.getNode().getElementsByClassName('webix_ss_header');
					let lateral = datatable.getNode().getElementsByClassName('webix_ss_vscroll_header');

					showFiltersButton._contentobj.className = self.filtersVisible ? "webix_view webix_control webix_el_icon" : "webix_view webix_control webix_el_icon filter_icon_selected";
					showFiltersButton.refresh();

					datatable.config.headerRowHeight = self.filtersVisible ? NO_HEADER_HEIGHT : DEFAULT_HEADER_HEIGHT * 2;
					
					for (let column of datatable.config.columns) {

						if (column.header[1]) {
							
							column.header[1].height = self.filtersVisible ? NO_HEADER_HEIGHT: DEFAULT_HEADER_HEIGHT;	
						}
					}
					
					self.filtersVisible = !self.filtersVisible;
					
					header[0].style.borderBottom = "1px solid var(--blue-strong)";
					lateral[0].style.borderBottom = "0px solid var(--blue-strong)";
				}

				datatable.refreshColumns();
			});
		}
	}


	showFilters() {

		let datatable = $$("dt-" + this.uid);
		let resultFilters = this.getGridOptions(GridOptions.FILTER);
		let resultVertical = this.getGridOptions(GridOptions.HEADER_ROW_HEIGHT);
		let hasColumnsWithVerticalHeader = (this.metadata.gridFields.findIndex(e => (e.headerVertical)) !== -1);
		let hasColumnsWithFilters = (this.metadata.gridFields.findIndex(e => (e.filtered)) !== -1);

		let header = datatable.getNode().getElementsByClassName('webix_ss_header');
		let lateral = datatable.getNode().getElementsByClassName('webix_ss_vscroll_header');
		
		if (hasColumnsWithFilters) {

			makeColumnsWithFilters();
			
		} else {

			makeColumnsWithNoFilters();
		}
		
		header[0].style.borderBottom = "1px solid var(--blue-strong)";
		lateral[0].style.borderBottom = "0px solid var(--blue-strong)";

		datatable.refreshColumns();
		
		function makeColumnsWithFilters() {
			
			datatable.config.headerRowHeight = resultFilters ? DEFAULT_HEADER_HEIGHT : NO_HEADER_HEIGHT;
			
			for (let column of datatable.config.columns) {
					
				if (!isNaN(parseInt(resultVertical))) {
					
					column.header[0].height = parseInt(resultVertical);
				}
				else {
					
					column.header[0].autoheight = true;
				}

				if (column.header[0].rotate) {
					
					column.header[0].css = { "text-align": "center" };
				}
					
				if (column.header[1]) {
						
					column.header[1].height = resultFilters ? DEFAULT_HEADER_HEIGHT : NO_HEADER_HEIGHT;	
				}
			}
		}
		
		function makeColumnsWithNoFilters() {
			
			if (hasColumnsWithVerticalHeader) {

				if (!isNaN(parseInt(resultVertical))) {

					datatable.config.headerRowHeight = parseInt(resultVertical);
					
					for (let column of datatable.config.columns) {

						column.header[0].css = { "height": + parseInt(resultVertical) + "px !important", "line-height": + parseInt(resultVertical) + "px !important" };
					}

				} else {

					for (let column of datatable.config.columns) {

						column.header[0].autoheight = true;
					}
				}

			} else {

				datatable.config.headerRowHeight = DEFAULT_HEADER_HEIGHT;
			}
		}
	}

	datatableCapture() {

		let self = this;
		let datatable = $$("dt-" + this.uid);
		let sortedByField = this.sortedByField;

		datatable.filterByAll = function() {

			let filteredValues = [];
			let gridOptionsFilterFieldId = self.getGridOptions(GridOptions.FILTER_FIELD_ID)
			let gridOptionsFilterFieldValue = self.getGridOptions(GridOptions.FILTER_FIELD_VALUE)
			let existsInitFilter = (gridOptionsFilterFieldId && gridOptionsFilterFieldValue) ? true :false;
			let txtSearch = $$("txtSearch-" + self.uid);

			// gets filter values
			self.metadata.gridFields.forEach(gridField => {

				if (datatable.getFilter(gridField.sourceField)) {
					
					let filteredValue = {
						
						sourceField: gridField.sourceField,
						dataType: gridField.gridFieldDataType.id,
						value: undefined,
						start: undefined,
						end: undefined,
						parameterized: gridField.parameterized
					}
					
					if (gridField.gridFieldDataType.id != DataType.DATE.value) {
						
						if (datatable.getFilter(gridField.sourceField).value) {
							
							filteredValue.value = datatable.getFilter(gridField.sourceField).value.toLowerCase();
							filteredValues.push(filteredValue);	
						}
					}
					else {
						
						if (datatable.getFilter(gridField.sourceField).config && datatable.getFilter(gridField.sourceField).config.value.start) {
							
							filteredValue.start = datatable.getFilter(gridField.sourceField).config.value.start;
							filteredValue.end = datatable.getFilter(gridField.sourceField).config.value.end;
							
							filteredValues.push(filteredValue);	
						}
					}
				} 
			});
			
			// unfilters the table if values were not selected and init filters not exists
			if (filteredValues.length === 0 && !existsInitFilter && (!txtSearch || (txtSearch && !txtSearch.getValue()))) {
					
				datatable.filter();
			}
			else {
			
				datatable.filter(function(obj) {

					let res = check_search_filter(obj);
	
					for (let i = 0; res && i < filteredValues.length; i++) {
	
						let filteredValue = filteredValues[i];

						if (filteredValue.dataType != DataType.DATE.value) {

							if (filteredValue.parameterized) {
								
								res &= ((obj[filteredValue.sourceField]).toLowerCase() === filteredValue.value);

							} else {
								
								if (obj[filteredValue.sourceField]) {

									res &= (obj[filteredValue.sourceField].toString().toLowerCase().indexOf(filteredValue.value) !== -1);

								} else {

									res &= Number(Boolean(obj[filteredValue.sourceField]));
								}
							}

						} else {
							if (filteredValue.start) {
								if (filteredValue.end) {
									if (filteredValue.end.getTime() === filteredValue.start.getTime()) {
										// Caso: mismo día → filtrar solo ese día completo
										let startOfDay = new Date(filteredValue.start);
										startOfDay.setHours(0, 0, 0, 0);

										let endOfDay = new Date(filteredValue.end);
										endOfDay.setHours(23, 59, 59, 999);

										res &= obj[filteredValue.sourceField] >= startOfDay &&
											   obj[filteredValue.sourceField] <= endOfDay;
									}
									else if (filteredValue.end > filteredValue.start) {
										// Caso: rango de fechas normal
										res &= obj[filteredValue.sourceField] >= filteredValue.start &&
											   obj[filteredValue.sourceField] <= filteredValue.end;
									}
								}
								else {
									// Caso: solo fecha inicio (desde ahí en adelante)
									res &= obj[filteredValue.sourceField] >= filteredValue.start;
								}
							}
						}						
					}
	
					res = res & checkInitFilter(obj);
	
					return res;
				});	
			}
			

			function checkInitFilter(row) {

				let res = true;

				if (existsInitFilter) {

					let gridField = self.metadata.gridFields.find(e => e.id == gridOptionsFilterFieldId);

					if (gridField) {

						if (row) {

							res &= ((row[gridField.sourceField]).indexOf(gridOptionsFilterFieldValue) !== -1);
						}
					}
				}

				return res;
			}
			
			function check_search_filter(row) {
		
				let txtS = $$("txtSearch-" + self.uid);
				let grid = $$("dt-" + self.uid);
				
				let res = false;
				
				if (grid && txtS && txtS.getValue()) {
					
					let value = txtS.getValue().toLowerCase();
									
					grid.config.columns.forEach(col => {
						
						if (col.id != undefined && row[col.id]) {
		
							if (col.sort === DataType.DATE.sort) {
		
								res |= (row[col.id].toLocaleString(i18n.getLocale(), FORMAT_NUMERIC_DATES).indexOf(value) !== -1);
							}
							else {

								if (typeof (row[col.id]) === 'object' && row[col.id].value !== undefined) {

									if (row[col.id].value === true || row[col.id].value === false) {

										let field = self.metadata.gridFields.find(f => f.sourceField === col.id);

										if (field && Array.isArray(field.gridFieldReplacements) && field.gridFieldReplacements.length > 0) {

											let replaceValue = field.gridFieldReplacements.find(replacement => replacement.value == row[col.id].value);

											if(replaceValue){
												
												res |= (String(replaceValue.label).toLowerCase().indexOf(value) !== -1);
											}
										}

									} else {

										res |= (String(row[col.id].value).toLowerCase().indexOf(value) !== -1);
									}

								} else {

									res |= (String(row[col.id]).toLowerCase().indexOf(value) !== -1);
								}
							}
						}
					});
				}
				else {
					
					res = true;
				}
				
				return res;
			}

		};

		datatable.attachEvent("onAfterSort", function(by, dir, as) {

			sortedByField.field = by;
			sortedByField.sort = dir;
		});

		datatable.attachEvent("onBeforeFilter", function(id, value, config) {

			if (id) {

				self.filteredFields[id] = value;
			}
		});

		datatable.attachEvent("onAfterFilter", function() {

			self.restoreSort();

		});

	}


	rowColors() {

		let data = this.data;
		let resultColor = this.getGridOptions(GridOptions.ROW_COLOR);
		let fieldColor = undefined;

		//		{"fieldId": "100003", "value": "Operators", "backcolor": "ddd"}  {"fieldId": "roleAlias", "value": "Operators", "backcolor": "ddd"}
		if (resultColor != null) {
			let jsColor = JSON.parse(resultColor);

			if (isNaN(parseInt(jsColor.fieldId))) {

				fieldColor = data.filter(e => e[jsColor.fieldId] === jsColor.value);

				if (fieldColor && fieldColor.length > 0) {

					fieldColor.forEach(e => {
						e.$css = { "background-color": "#" + jsColor.backcolor + "3b !important", "color": "#" + jsColor.backcolor + "!important" };

					});
				}

			} else {

				fieldColor = this.metadata.gridFields.find(e => e.id === parseInt(jsColor.fieldId));

				if (fieldColor) {

					let rows = data.filter(e => e[fieldColor.sourceField] === jsColor.value);

					if (rows && rows.length > 0) {
						rows.forEach(e => {
							e.$css = { "background-color": "#" + jsColor.backcolor + "3b !important", "color": "#" + jsColor.backcolor + "!important" };
						});
					}
				}
			}
		}
	}

	openIconList() {

		let metadata = this.metadata;
		let datatable = $$("dt-" + this.uid);
		let listButton = $$("tb_btn-icons-" + this.uid);
		let iconData = [];
		let maxLengthLabel = 0;
		let widthColLabel = 0;
		const MIN_WIDTH_COL_LABEL = 200;
		const MAX_WIDTH_COL_LABEL = 350;
		const WIDTH_COL_ICON = 50;
		const MAX_WIN_HEIGHT = 600;
		const FONT_SIZE = 16;
		const COL_HEIGHT = 36;

		if (!webix.isUndefined(datatable) && !webix.isUndefined(listButton)) {

			listButton.attachEvent("onItemClick", function(id, e) {

				iconData.length = 0;

				if (Array.isArray(metadata.gridFields) && metadata.gridFields.length > 0) {
				
					for (let gridField of metadata.gridFields) {

						if (Array.isArray(gridField.gridFieldIcons) && gridField.gridFieldIcons.length > 0) {

							gridField.gridFieldIcons.forEach(e => {

								if (e.icon) {

									let icon = "<img class='table-field-icons' src=" + e.icon + "/>";
									let labelValue = !e.labelValue ? i18n.t("LBL_UNKNOWN") : i18n.t(e.labelValue);

									let idxIcon = iconData.findIndex(i => i.icon === icon);

									if (idxIcon !== -1) {

										labelValue = iconData[idxIcon].labelValue + ", " + labelValue;
										iconData.splice(idxIcon, 1);
									}

									if (maxLengthLabel < labelValue.length) {

										maxLengthLabel = labelValue.length;
									}

									iconData.push({ labelValue, icon });
								}
							});
						}
					}

					let lessThanMax = maxLengthLabel * FONT_SIZE < MAX_WIDTH_COL_LABEL;
					let greatterThanMin = maxLengthLabel * FONT_SIZE > MIN_WIDTH_COL_LABEL;

					if (lessThanMax && greatterThanMin) {

						widthColLabel = maxLengthLabel * FONT_SIZE;
					}
					else {

						widthColLabel = lessThanMax ? MIN_WIDTH_COL_LABEL : MAX_WIDTH_COL_LABEL;
					}

					let body = {
						view: "datatable",
						header: false,
						columns: [
							{ id: "icon", width: WIDTH_COL_ICON },
							{ id: "labelValue", width: widthColLabel }
						],
						data: iconData
					};

					let winHeight = COL_HEIGHT * iconData.length + 60;

					util.ui.loadWindow(i18n.t("LBL_TABLE_LEGEND"), body,
						winHeight < MAX_WIN_HEIGHT ? winHeight : MAX_WIN_HEIGHT,
						widthColLabel + WIDTH_COL_ICON + 20, false);
				}
			});
		}
	}
	
	showHelp() {

		let self = this;
		let helpButton = $$("tb_btn-help-" + self.uid);
		let grid = $$("dt-" + self.uid);
		let tagHelp = self.getGridOptions(GridOptions.HELP_TAG);

		if (!webix.isUndefined(grid) && !webix.isUndefined(helpButton) && tagHelp) {

			helpButton.attachEvent("onItemClick", function() {

				util.module.showTagHelp(tagHelp);
			});
		}
	}

	callbacks() {

		this.selectDatatable();
		this.datatableCapture();
		this.search_callback();
		this.select_all_callback();
		//this.unselect_all_callback();
		this.refresh_callback();
		this.exportCallback();
		//this.change_view("hide");
		this.main_toolbar_callback();
		this.showMainToolbar();
		this.connect_ws();
		this.on_destroy();
		this.on_item_dbl_click();
		this.on_item_selected();
		this.other_commands_callback();
		this.showFilters();
		this.rowColors();
		this.clickShowFilters();
		this.openIconList();
		this.showHelp();

		this.restoreSelected();
		this.sortDatatable();
		this.filterDatatable();
		this.drawContextButtonAction();
		this.adjustColumns();
	}

	on_destroy() {

		let self = this;
		let grid = $$("dt-" + this.uid);

		if (grid) {

			grid.attachEvent("onDestruct", function() {

				self.disconnect_ws();
			});
		}
	}


	change_view(selection) {

		let grid = $$("dt-" + this.uid);
		let metadata = this.metadata;

		if (grid) {

			let columns = grid.config.columns;

			if (metadata.gridFields.length > 0 && Array.isArray(columns) && columns.length > 0) {

				metadata.gridFields.forEach(e => {

					if (e.extended) {

						if (selection === "hide") {

							grid.hideColumn(e.sourceField);
						}
						else if (selection === "show") {

							grid.showColumn(e.sourceField);
						}
						
						for (let column of grid.config.columns) {
	
							if (column.header[1]) {
								
								column.header[1].height = self.filtersVisible ? DEFAULT_HEADER_HEIGHT : NO_HEADER_HEIGHT;	
							}
						}
					}
				});
				
				grid.refreshColumns();
			}
		}
	}
	
	search_callback() {

		let self = this;
		let grid = $$("dt-" + this.uid);
		let txtSearch = $$("txtSearch-" + this.uid);
		let gridOptionsFilterFieldId = self.getGridOptions(GridOptions.FILTER_FIELD_ID)
		let gridOptionsFilterFieldValue = self.getGridOptions(GridOptions.FILTER_FIELD_VALUE)
		let existsInitFilter = (gridOptionsFilterFieldId && gridOptionsFilterFieldValue) ? true : false;

		if (!webix.isUndefined(grid) && !webix.isUndefined(txtSearch)) {

			txtSearch.attachEvent("onTimedKeyPress", function() {
				
				grid.filterByAll();
//				let value = this.getValue().toLowerCase(); // input data is derived
//				grid.filter(function(obj) {  // here it filters all titles from the dataset
//
//					let res = false;
//					grid.config.columns.forEach(col => {
//						if (col.id != undefined && obj[col.id]) {
//
//							if (col.sort === DataType.DATE.sort) {
//
//								res |= (obj[col.id].toLocaleString(i18n.getLocale(), FORMAT_NUMERIC_DATES).indexOf(value) !== -1);
//							}
//							else {
//
//								res |= (String(obj[col.id]).toLowerCase().indexOf(value) !== -1);
//
//								if (existsInitFilter) {
//
//									let gridField = self.metadata.gridFields.find(e => e.id == gridOptionsFilterFieldId);
//
//									if (gridField) {
//
//										if (obj) {
//
//											res &= ((obj[gridField.sourceField]).indexOf(gridOptionsFilterFieldValue) !== -1);
//										}
//									}
//								}
//							}
//						}
//					});
//
//					return res;
//				});
//				
				self.restoreSort();
			});
			
			let resultCommons = this.getGridOptions(GridOptions.TB_ACTIONS_BUTTONS_COMMONS);
			let resultCommands = this.getGridOptions(GridOptions.TB_ACTIONS_BUTTONS_COMMANDS);
			let resultSearch = this.getGridOptions(GridOptions.SEARCH);
			let showOnlySearchBar = !resultCommons && !resultCommands && resultSearch;
			
			if (showOnlySearchBar) {
				
				txtSearch.focus();
			}
		}
	}

	find_module(moduleActionId) {

		let moduleFound = undefined;
		let modules = window.em.getAll(EntityType.MODULE);

		if (modules && modules.length > 0) {

			modules.forEach(module => {

				if (!moduleFound && module.moduleActions.find(moduleAction => moduleAction.id === moduleActionId)) {

					moduleFound = module;
				}
			});
		}

		return moduleFound;
	}

	open_module(gridCommand) {

		let args = {};
		let self = this;

		let viewTypeId = undefined;

		if (gridCommand) {

			let selectedPks = this.getSelectedIds();

			if (gridCommand.viewType) {

				viewTypeId = gridCommand.viewType.id;
			}

			if (gridCommand.args != undefined) {

				try {
					args = JSON.parse(gridCommand.args);

				} catch (error) {

					console.error(error);
					args = {};
				}
			}
			
			if (!args.callbacks) {
				
				args.callbacks = [];
			}


			args.callbacks.push(function(delay) { self.refresh(self,delay) });

			// Any selected?
			if (selectedPks.length > 0) {

				// Multiselect not allowed with 2 o more selected
				if (selectedPks.length > 1 && gridCommand.multiselect === false) {

					let ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_COMMAND_NOT_COMPATIBLE_MULTISELECT_DESCRIPTION"), null, null, null);
					ms.show();
					
//					webix.alert({
//						title: i18n.t("LBL_COMMAND_NOT_COMPATIBLE_MULTISELECT"),
//						text: i18n.t("LBL_COMMAND_NOT_COMPATIBLE_MULTISELECT_DESCRIPTION"),
//						type: "alert-error",
//						width: 500
//					});
				}
				// One or more selected and multiselect allowed
				else {

					args.selectedPks = selectedPks;
					args.selectedRows = self.getSelectedItems();
					
					if (!self.command_exec_callback(gridCommand)) {
					
						if (gridCommand.moduleAction) {
						
							let module = this.find_module(gridCommand.moduleAction.id);
							util.module.open(module, gridCommand.moduleAction, viewTypeId, null, args);
						}
					}
					
					let grid = $$("dt-" + this.uid);

					if (grid && gridCommand.unselectAllAfter) {
			
						grid.unselectAll();
					}
				}
			}
			// No rows selected
			else {
				// Required one or more selected
				if (gridCommand.itemRequired) {
					
					let ms = new MessageBox (MessageBox.WARN,i18n.t("LBL_COMMAND_NO_ROWS_SELECTED_DESCRIPTION"), null, null, null);
					ms.show();
					
//					webix.alert({
//						title: i18n.t("LBL_COMMAND_NO_ROWS_SELECTED"),
//						text: i18n.t("LBL_COMMAND_NO_ROWS_SELECTED_DESCRIPTION"),
//						type: "alert-warning",
//						width: 500
//					});
				}
				// No required select rows
				else {
					
					if (!self.command_exec_callback(gridCommand)) {
					
						if (gridCommand.moduleAction) {
						
							let module = this.find_module(gridCommand.moduleAction.id);
							util.module.open(module, gridCommand.moduleAction, viewTypeId, null, args);
						}
					}
				}
			}
		}
	}

	on_item_dbl_click() {

		let self = this;
		let grid = $$("dt-" + this.uid);
		let resultSelect = this.getGridOptions(GridOptions.SELECT);

		if (grid && resultSelect) {

			grid.attachEvent("onItemDblClick", function(id, e, node) {

				if (self.events && self.events[DataTable.EventTypes.ON_ITEM_DBL_CLIC]) {

					eval(self.events[DataTable.EventTypes.ON_ITEM_DBL_CLIC])(self.getSelectedIds(), self.getSelectedItems());
				}
				else {

					if (Array.isArray(self.metadata.gridCommands) && self.metadata.gridCommands.length > 0) {
					
						let defaultCommand = self.metadata.gridCommands.find(gridCommand => gridCommand.default_ === true);
						
						if (defaultCommand) {
							
							self.open_module(defaultCommand);	
						}
					}	
				}
			});
		}
	}

	on_item_selected() {

		if (this.events && this.events[DataTable.EventTypes.ON_SELECT_ROW]) {

			let self = this;
			let grid = $$("dt-" + this.uid);

			if (grid) {

				grid.attachEvent("onSelectChange", function() {

					eval(self.events[DataTable.EventTypes.ON_SELECT_ROW])(self.getSelectedIds(), self.getSelectedItems());
				});
			}

		}
	}


	main_toolbar_callback() {

		let self = this;
		let showButton = $$("tb_btn-showseg-" + this.uid);
		let resultDetails = this.getGridOptions(GridOptions.SHOW_DETAILS);

		if (showButton) {

			if (resultDetails) {
				showButton._contentobj.className = "webix_view webix_control webix_el_icon details_icon_selected";
				showButton.refresh();

			}

			showButton.attachEvent("onItemClick", function(id, e, node) {

				if (resultDetails) {
					resultDetails = false;
					self.change_view("hide");
					showButton._contentobj.className = "webix_view webix_control webix_el_icon";
					showButton.refresh();
				}
				else {
					resultDetails = true;
					self.change_view("show");
					showButton._contentobj.className = "webix_view webix_control webix_el_icon details_icon_selected";
					showButton.refresh();
				}
			});
		}
	}

	select_all_callback() {

		let selectAllButton = $$("tb_btn-select-all-" + this.uid);
		let grid = $$("dt-" + this.uid);
		let rowsSelected = this.rowsSelected;

		if (!webix.isUndefined(grid) && !webix.isUndefined(selectAllButton)) {
			
			selectAllButton.attachEvent("onItemClick", function(id, e) {

				if (grid.getFirstId()) {
					
					let allSelected = ((rowsSelected.size == grid.count()) && (rowsSelected.size > 0) ) ? true: false;
					
					if(allSelected){
						
						grid.unselectAll();
						selectAllButton.config.icon = "mdi mdi-checkbox-blank-outline";
						selectAllButton.refresh();
						
					} else {
						
						grid.selectAll();		
						selectAllButton.config.icon = "mdi mdi-check-box-outline";
						selectAllButton.refresh();
					}				
				} 
			});
		}
	}

//	unselect_all_callback() {
//
//		let unselectAllButton = $$("tb_btn-unselect-all-" + this.uid);
//		let grid = $$("dt-" + this.uid);
//
//		if (!webix.isUndefined(grid) && !webix.isUndefined(unselectAllButton)) {
//
//			unselectAllButton.attachEvent("onItemClick", function(id, e) {
//
//				if (grid.getFirstId()) {
//
//					grid.unselectAll();
//				}
//			});
//		}
//	}

	refresh_callback() {

		let self = this;
		let refreshButton = $$("tb_btn-refresh-" + this.uid);
		let grid = $$("dt-" + this.uid);
		let dataGridId = this.dataGridId;

		if (!webix.isUndefined(grid) && !webix.isUndefined(refreshButton) && dataGridId) {

			webix.extend(grid, webix.ProgressBar);

			refreshButton.attachEvent("onItemClick", function(id, e) {

				self.refresh();
			});
		}
	}

	refresh(obj, delay) {

		let url = undefined;
		let self = undefined;
		let millis = undefined;
		let metadata = undefined;
		
		if (obj != undefined) {

			self = obj;

		} else {
			
			self = this;
	
		}

		if (delay) {
			
			millis = delay;

		} else {

			millis = 0;
		}
		
		let grid = $$("dt-" + self.uid);
		metadata = self.metadata;
		
		webix.extend(grid, webix.ProgressBar);
		
		if (metadata && metadata.refreshCallback) {

			grid.showProgress({

				hide: false, type: "bottom"
			});
			
			metadata.refreshCallback();
			
			grid.hideProgress();

		} else {

			grid.showProgress({

				hide: false, type: "bottom"
			});

			webix.delay(function() {

				if (!webix.isUndefined(grid)) {

					if (self.dataGridParams) {

						url = appConfig.restUrl + "grids/" + self.dataGridId + "/" + self.dataGridParams;
					}
					else {

						url = appConfig.restUrl + "grids/" + self.dataGridId;
					}

					webix.ajax(url).then(function(dat) {

						grid.clearAll();

						webix.delay(function() {

							if (dat && dat.json) {

								let data = dat.json();

								if (data && data.data) {

									self.translateFieldValues(data.data);
									self.convertDate(data.data);

									grid.parse(data.data);

									self.filterDatatable();
									self.restoreSort();
									self.restoreFilters();
								}
							}

							if (self.getGridOptions(GridOptions.REFRESH_BUTTON)) {

								grid.hideProgress();
							}

						}, null, null, 1);
					});
				}
			}, null, null, millis);
		}
	}
	
	exportCallback() {

		let self = this;
		let exportButton = $$("tb_btn-export-" + self.uid);
		let printButton = $$("tb_btn-print-" + self.uid);
		let grid = $$("dt-" + self.uid);

		if (!webix.isUndefined(grid) && !webix.isUndefined(printButton)) {

			printButton.attachEvent("onItemClick", function() {

				let dataToPrint = [];

				let options = {
					metadata: self.metadata
				};

				grid.eachRow(function(row) {

					let dat = grid.getItem(row);

					if (dat) {

						dataToPrint.push(dat);
					}
				});

				let printPreview = new PrintPreview(PrintPreview.TYPE_PRINT_DATATABLE, dataToPrint, options);
				printPreview.show();
			});
		}

		if (!webix.isUndefined(grid) && !webix.isUndefined(exportButton)) {

			exportButton.attachEvent("onItemClick", function() {
					
				let dataToPrint = [];

				let options = {
					metadata: self.metadata
				};

				grid.eachRow(function(row) {

					let dat = grid.getItem(row);

					if (dat) {

						dataToPrint.push(dat);
					}
				});

				let printPreview = new PrintPreview(PrintPreview.TYPE_EXPORT_DATATABLE, dataToPrint, options);
				printPreview.show();
			});
		}
	}
	
	command_exec_callback(gridCommand) {
		
		let cmdCallback;
		
		if (this.events && this.events[DataTable.EventTypes.ON_COMMAND_EXEC] && 
		    Array.isArray(this.events[DataTable.EventTypes.ON_COMMAND_EXEC]) && 
			this.events[DataTable.EventTypes.ON_COMMAND_EXEC].length > 0) {
			
			let cmdCallbacks = this.events[DataTable.EventTypes.ON_COMMAND_EXEC];
			
			cmdCallback = cmdCallbacks.find(cmdCall => cmdCall.commandId === gridCommand.id);
			
			if (cmdCallback) {
			
				cmdCallback.function(this.getSelectedIds(), this.getSelectedItems());
			}
		}															
				
		return cmdCallback != undefined;
	}

	other_commands_callback() {

		let self = this;
		let grid = $$("dt-" + this.uid);
		let metadata = this.metadata;
		
		if (grid) {

			if (metadata.gridCommands && metadata.gridCommands.length > 0) {

				metadata.gridCommands.forEach(cmd => {

					let buttonAction = $$("tb_btn-" + this.uid + "_" + cmd.id);

					if (buttonAction) {

						buttonAction.attachEvent("onItemClick", function(id, e) {

							self.open_module(cmd);
						});
					}
				});
			}
		}
	}

	connect_ws() {

		let self = this;

		if (this.metadata.adapter != undefined) {

			let grid = $$("dt-" + this.uid);
			let dataSource = undefined;

			if (self.metadata.adapter.adapterType.id === adapter.ADAPTER_TYPE_VO_TO_GRID_DATA) {

				let adapterDataSource = self.metadata.adapter.adapterDataSources.find(e => {

					return e.adapterDataSourceType.id === adapter.ADAPTER_DATA_SOURCE_TYPE_JS
				});

				if (adapterDataSource && adapterDataSource.value) {

					dataSource = adapterDataSource.dataSource;

					function on_connected() {

					}

					function on_message(be, action, timestamp) {

						function update(gridData) {

							let grid = $$("dt-" + self.uid);
							let rowsUpdated = 0;

							if (!webix.isUndefined(grid) && gridData != undefined && gridData.data != undefined && Array.isArray(gridData.data) && gridData.data.length > 0) {

								self.translateFieldValues(gridData.data);
								self.convertDate(gridData.data);

								gridData.data.forEach(dataItem => {

									var record = grid.getItem(dataItem.id);

									if (record != undefined && gridData.action == 'DELETE') {

										grid.remove(record.id);
										rowsUpdated++;
										
									} else if (record != undefined) {

										var keys = Object.keys(dataItem);

										if (keys != undefined && Array.isArray(keys) && keys.length > 0) {

											keys.forEach(key => {
												
												record[key] = dataItem[key];
											});
										}
										
										//console.log (record);
									} else if (gridData.action != 'DELETE'){

										grid.add(dataItem);
										rowsUpdated++;

									}
									rowsUpdated++;
								});

								if (rowsUpdated > 0) {

									grid.refresh();
									self.filterDatatable();
								}
							}
						}

						//var data = JSON.parse(JSON.parse(message.body));
						
						let data = {
							basicElementsVO: [be],
							action: action,
							timestamp: timestamp
						}

						eval("adapter." + adapterDataSource.value)(data, update);
					}
					
					if (Array.isArray(dataSource.dataSourceValues)) {
						
						let dataSourceValue = dataSource.dataSourceValues.find(e => e.dataSourceParam.id === 6); 
						
						if (dataSourceValue) {
							
							let topic = dataSourceValue.value;

							let params;
		
							if (self.dataGridParams) {
		
								params = "." + self.dataGridParams + ".>";
							}
							else {
		
								params = ".>";
							}
		
							//this.stomp.client = util.stomp.connect(url, topic + params, on_connected, on_message);
							
							let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
			
							if (stompSubscriptorService) {
					
								this.idStompSubscriptor = stompSubscriptorService.subscribe(appConfig.stompUrl, topic + params, on_message);
							}	
						}
					}
				}
			}
		}
	}

	disconnect_ws() {

//		if (this.stomp != undefined && this.stomp.client != undefined && this.stomp.client.connected) {
//
//			util.stomp.disconnect(this.stomp.client);
//			this.stomp = {};
//		}

		if (this.idStompSubscriptor) {
			
			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
			if (stompSubscriptorService) {
	
				stompSubscriptorService.unsubscribe(this.idStompSubscriptor);
			}
		}
	}

	select(ids) {

		let grid = $$("dt-" + this.uid);

		if (Array.isArray(ids) && ids.length > 0) {

			if (ids.length > 1) {
			
				selectRows(ids);	
			}
			else {
				
				selectRow(ids[0]);
			}
		}
		else if (ids) {
			
			selectRow(ids);
		}
		
		function selectRow(rowId) {
			
			if (grid) {
			
				const record = grid.getItem(rowId);

				if (record) {

					grid.select(rowId, false);
					grid.showItem(rowId);
				}	
			}
		}
		
		function selectRows(rowIds) {
			
			let preserve = false;
			
			if (grid) {
				
				rowIds.forEach(id => {

					let record = grid.getItem(id);

					if (record != undefined) {

						grid.select(id, preserve);
						preserve = true;
					}
				});
			}
		}
	}
	
	selectByIndex(idx) {
		
		let grid = $$("dt-" + this.uid);
		
		if (grid && idx > -1) {
			
			let id = grid.getIdByIndex(idx);
			
			if (id) {
				
				this.select(id);
				grid.showItem(id);	
				grid.refresh();	
			}
		}		
	}
	
	unselectAll() {
		
		let grid = $$("dt-" + this.uid);
		
		if (grid) {
			
			grid.unselectAll();	
		}
	}
	
	adjustColumns() {

		let grid = $$("dt-" + this.uid);

		if (grid) {

			let resultAdjColumns = this.getGridOptions(GridOptions.ROW_ADJUSTEMENT);

			if (resultAdjColumns) {

				if (resultAdjColumns === RowAdjustement.AUTO && Array.isArray(this.data) && this.data.length === 0) {

					grid.eachColumn(function(columnId) {

						let headerElement = grid.getHeaderNode(columnId);
						
						if (headerElement) {
						
							let textSize = webix.html.getTextSize(headerElement.innerText);
							
							grid.adjustColumn(columnId, textSize.width);	
						}
					});
				}
			}
		}
	}
}

Object.defineProperty(DataTable, 'EventTypes', {
	value: {
	}
});


Object.defineProperty(DataTable.EventTypes, 'ON_SELECT_ROW', {
	value: 1,
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(DataTable.EventTypes, 'ON_ITEM_DBL_CLIC', {
	value: 2,
	writable: false,
	enumerable: true,
	configurable: false
});

Object.defineProperty(DataTable.EventTypes, 'ON_COMMAND_EXEC', {
	value: 3,
	writable: false,
	enumerable: true,
	configurable: false
});
