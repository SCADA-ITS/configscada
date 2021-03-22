import { util } from "../lib/util.js";
import { adapter } from "../lib/adapter.js";

const DataType = {
	STRING: { value: 1, filter: "textFilter", sort: "string" },
	INT: { value: 2, filter: "numberFilter", sort: "decimal" },
	DECIMAL: { value: 3, filter: "numberFilter", sort: "int" },
	DATE: { value: 4, filter: "dateRangeFilter", sort: "date" }
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
	SHOW_HEADER: 24

};

const RowAdjustement = {
	DATA: "data",
	HEADER: "header",
	AUTO: "auto",
	CUSTOM: "custom"
};

const DefOptionValues = { 1: true, 2: true, 7: true, 8: true, 9: false, 10: true, 11: true, 12: true, 13: true, 14: false, 15: false, 17: true, 20: true, 21: RowAdjustement.CUSTOM, 23: true, 24: true };

const _TRUE = "true";
const _FALSE = "false";

const ExtendedHeader = {
	TRUE: 42,
	FALSE: 21,
	CLOSE: 0
};

export default class DataTable {

	constructor(dataGridId, dataGridParams, metadata, data, events) {

		this.dataGridId = dataGridId;
		this.dataGridParams = dataGridParams;
		this.metadata = metadata;
		this.data = data;
		this.stomp = {};
		this.uid = webix.uid();
		this.events = events;
		this.sortedByField = {
			field: undefined,
			sort: undefined
		};
		this.filteredFields = {};
		this.rowsSelected = [];
		this.disabledUnselectEvent = false;
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

		console.log(metadata)
		console.log(data)
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
			//			console.log(metadata)
			//			console.log(data)
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

					if (field.filtered) {

						if (field.label) {

							if (field.headerVertical) {

								col.header.push({
									text: `<div title="${i18n.t(field.labelTooltip)}">${i18n.t(field.label)}</div>`,
									rotate: true,
									css: { "font-size": "14px" }
								});

							} else {

								col.header.push(`<div title="${i18n.t(field.labelTooltip)}">${i18n.t(field.label)}</div>`);
							}

						}
						else {

							col.header.push(`<div></div>`);
						}

						if (field.parameterized) {
							col.header.push({ content: "selectFilter" });
						}
						else {
							if (field.gridFieldDataType) {

								if (field.gridFieldDataType.id === DataType.STRING.value) {
									col.header.push({ content: DataType.STRING.filter });
								}
								else if (field.gridFieldDataType.id === DataType.INT.value) {
									col.header.push({ content: DataType.INT.filter });
								}
								else if (field.gridFieldDataType.id === DataType.DECIMAL.value) {
									col.header.push({ content: DataType.DECIMAL.filter });
								}
								else if (field.gridFieldDataType.id === DataType.DATE.value) {
									col.header.push({ content: DataType.DATE.filter });
								}
							}
							else {
								col.header.push({ content: DataType.STRING.filter });
							}
						}
					}
					else {

						//col.header = i18n.t(field.label);

						if (field.label) {

							if (field.headerVertical) {

								col.header.push({
									text: `<div title="${i18n.t(field.labelTooltip)}">${i18n.t(field.label)}</div>`,
									rotate: true,
									css: { "font-size": "14px" }
								});

							} else {

								col.header.push(`<div title="${i18n.t(field.labelTooltip)}">${i18n.t(field.label)}</div>`);
							}

						}
						else {

							col.header.push(`<div></div>`);
						}
					}

					if (posField < metadata.gridFields.length) {

						col.hidden = !field.visible;
						col.width = field.widthPx;
					}

					col.sort = DataType.STRING.sort;

					if (field.gridFieldDataType) {

						if (field.gridFieldDataType.id === DataType.DECIMAL.value) {

							col.sort = DataType.DECIMAL.sort;
						}
						else if (field.gridFieldDataType.id === DataType.INT.value) {

							col.sort = DataType.INT.sort;
						}
						else if (field.gridFieldDataType.id === DataType.DATE.value) {

							col.sort = DataType.DATE.sort;
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

								value = obj[field.sourceField].value;
								state = obj[field.sourceField].state;
							}
							else if (field.gridFieldDataType && field.gridFieldDataType.id === DataType.DATE.value) {

								value = obj[field.sourceField].toLocaleString(i18n.getLocale(), FORMAT_NUMERIC_DATES);
							}
							else {

								if (field.gridFieldIcons != undefined && field.gridFieldIcons.length > 0) {

									badgeColor = (field.gridFieldIcons[0].badgeColor == true);
									avatar = (field.gridFieldIcons[0].avatar == true);
									showValue = (field.gridFieldIcons[0].showValue == true);
									value = obj[field.sourceField];

//									let gridFieldIcon;
//									let fieldsWithIcons = field.gridFieldIcons.filter(e => (e.value));
//
//									if (fieldsWithIcons.length > 0) {
//
//										let lang = obj[field.sourceField].substr(obj[field.sourceField].indexOf(':') + 1, 2);
//
//										gridFieldIcon = field.gridFieldIcons.find(e => e.value === lang);
//									}

									let gridFieldIcon = field.gridFieldIcons.find(e => e.value == obj[field.sourceField]);

									if (gridFieldIcon) {

										icon = gridFieldIcon.icon;
										showValue = (gridFieldIcon.showValue == true);
										value = gridFieldIcon.value;
									}
								}
								else if (field.gridFieldReferences != undefined && field.gridFieldReferences.length > 0) {


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
								else {

									value = obj[field.sourceField];
								}
							}

							if (field.replacementFunction) {

								if (eval("util.datatable." + field.replacementFunction)) {
									value = eval("util.datatable." + field.replacementFunction)(obj[field.sourceField]);
								}

							}

							col.tooltip = function(data) {

								if (field.gridFieldDataType.id === DataType.DATE.value) {

									return data[field.sourceField].toLocaleString(i18n.getLocale(), FORMAT_STRING_DATES);

								} else if (Array.isArray(field.gridFieldIcons) && field.gridFieldIcons.length > 0) {

									let label = field.gridFieldIcons.find(e => e.value === data[field.sourceField]);

									if (label != undefined) {
										//let tool = label.labelValue != "" && label.labelValue ? label.labelValue : data[field.sourceField];
										return !label.labelValue ? i18n.t("LBL_UNKNOWN") : i18n.t(label.labelValue);

									} else {

										return data[field.sourceField];
									}
								}
								else {

									return data[field.sourceField];
								}
							};

							//							if (field.isEpochMilli) {
							//
							//								value = util.toLocaleString(value / 1000);
							//							}

							if (icon || badgeColor || avatar) {

								if (icon) {

									if (!showValue) {

										return `<img class="table-field-icons" src="${icon}" />`;
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

										return `<div><span class="table-badge" style="background: ${balloon.color + '3b'}; color: ${balloon.color + 'a0'};">${value}</span></div>`;

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

										return `<img class="table-field-icons" src="https://avatars.dicebear.com/4.5/api/initials/${value}.svg"/>`;
									}
									else {

										return `<div style="width: ${field.widthPx}">	
													<img class="table-field-icons" src="https://avatars.dicebear.com/4.5/api/initials/${value}.svg"/>
													<span>${value}</span>
												</div>`
									}
								}
							}
							else if (value != undefined) {

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

									return `<span class="table-row-text">${value}</span>`;
								}
							}
							else {
								return `<span></span>`;
							}
						}
						else {

							if (value === '') {
								return `<span class="table-row-text">-</span>`;
								//return `<div><span class="table-badge" style="background: ${'#607d8b3b'}; color: ${'#607d8ba0'};">${i18n.t('LBL_NO_AVAILABLE')}</span></div>`;
							}
							else {

								return `<span class="table-row-text">${value}</span>`;
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

				let resultCommands = this.getGridOptions(GridOptions.TB_ACTIONS_BUTTONS_COMMANDS);

				if (resultCommands) {
					this.drawContextButtonAction(datatable);

				}
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

				if (field.needsTranslation) {

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
			for (let posField = 0; posField < this.metadata.gridFields.length; posField++) {

				let field = this.metadata.gridFields[posField];

				if (field.isEpochMilli) {

					data.forEach(item => {

						item[field.sourceField] = new Date(item[field.sourceField]);
					});
				}
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

	getSelectedItems() {

		let grid = $$("dt-" + this.uid);
		let selectedItems = [];

		if (grid != undefined) {

			selectedItems = grid.getSelectedItem(true);
		}

		return selectedItems;
	}

	drawMainToolbar() {

		let metadata = this.metadata;
		//		let resultSegmented = this.getGridOptions(GridOptions.TB_MAIN_SEGMENTED);
		//		let resultDetails = this.getGridOptions(GridOptions.SHOW_DETAILS);
		let resultIcon = this.getGridOptions(GridOptions.ICON);

		let toolbar = {
			view: "toolbar",
			id: "tbMain-" + this.uid,
			borderless: true
		}


		toolbar.elements = [];
		toolbar.elements.push({ width: 4 })

		toolbar.elements.push({ view: "label", label: "<img src='" + resultIcon + "' class='grid-icon'/><span class='grid-title'>" + i18n.t(metadata.label) + "</span>" });

		toolbar.elements.push({ minWidth: 4 });

		//		if (resultSegmented) {
		//
		//			let segmentViews = {
		//				view: "segmented", minWidth: 333,
		//				id: "seg-" + this.uid,
		//				options: [
		//					{ id: "seg-basic-" + this.uid, css: "table-tab-button", value: i18n.t("LBL_TABLE_BASIC") },
		//					{ id: "seg-details-" + this.uid, css: "table-tab-button", value: i18n.t("LBL_TABLE_DETAILED") }
		//				]
		//			}
		//
		//			if (resultDetails) {
		//
		//				segmentViews.value = "seg-details-" + this.uid;
		//
		//			} else {
		//
		//				segmentViews.value = "seg-basic-" + this.uid;
		//			}
		//			toolbar.elements.push(segmentViews)
		//		}

		toolbar.elements.push({ width: 8 });

		return toolbar;
	}



	drawActionsToolbar() {

		let metadata = this.metadata;
		let dataGridId = this.dataGridId;

		let resultCommons = this.getGridOptions(GridOptions.TB_ACTIONS_BUTTONS_COMMONS);
		let resultCommands = this.getGridOptions(GridOptions.TB_ACTIONS_BUTTONS_COMMANDS);
		let resultSearch = this.getGridOptions(GridOptions.SEARCH);
		let resultMulti = this.getGridOptions(GridOptions.MULTISELECT);
		let resultSelect = this.getGridOptions(GridOptions.SELECT);
		let resultRefresh = this.getGridOptions(GridOptions.REFRESH_BUTTON);
		let resultSegmented = this.getGridOptions(GridOptions.TB_MAIN_SEGMENTED);
		let resultSelRows = this.getGridOptions(GridOptions.SHOW_SELECTED);
		console.log(resultSelRows)


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

			toolbar.elements.push({ id: "tb_btn-select-all-" + this.uid, view: "icon", icon: "mdi mdi-check-box-outline", value: i18n.t("LBL_SELECT_ALL"), tooltip: true });
			toolbar.elements.push({ id: "tb_btn-unselect-all-" + this.uid, view: "icon", icon: "mdi mdi-checkbox-blank-outline", value: i18n.t("LBL_UNSELECT_ALL"), tooltip: true });
		}

		if (resultCommons) {

			if (resultFilter) {

				toolbar.elements.push({ id: "tb_btn-showfilters-" + this.uid, view: "icon", icon: "wxi-filter", value: i18n.t("LBL_FILTERS"), tooltip: true });

			}

			if (dataGridId && resultRefresh) {

				toolbar.elements.push({ id: "tb_btn-refresh-" + this.uid, view: "icon", icon: "mdi mdi-refresh", value: i18n.t("LBL_TABLE_REFRESH"), tooltip: true });

			}

			toolbar.elements.push({ id: "tb_btn-print-" + this.uid, view: "icon", icon: "mdi mdi-printer", value: i18n.t("LBL_TABLE_PRINT"), tooltip: true });
			toolbar.elements.push({ id: "tb_btn-export-pdf-" + this.uid, view: "icon", icon: "mdi mdi-file-pdf-box", value: i18n.t("LBL_TABLE_EXPORT_PDF"), tooltip: true });
			toolbar.elements.push({ id: "tb_btn-export-xls-" + this.uid, view: "icon", icon: "mdi mdi-file-excel", value: i18n.t("LBL_TABLE_EXPORT_XLS"), tooltip: true });

			if (resultSegmented) {

				toolbar.elements.push({ id: "tb_btn-showseg-" + this.uid, view: "icon", icon: "wxi-columns", value: i18n.t("LBL_SHOW_DETAILS"), tooltip: true });
			}

			if (showLegend) {

				toolbar.elements.push({ id: "tb_btn-icons-" + this.uid, view: "icon", icon: "mdi mdi-map-legend", value: i18n.t("LBL_TABLE_LEGEND"), tooltip: true })
			}
		}

		if (Array.isArray(metadata.gridCommands) && metadata.gridCommands.length > 0 && resultCommands) {

			toolbar.elements.push({ width: 8 });
			metadata.gridCommands.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));

			metadata.gridCommands.forEach(cmd => {

				if (cmd.showText) {

					toolbar.elements.push({ id: "tb_btn-" + this.uid + "_" + cmd.id, view: "button", type: "icon", icon: cmd.icon, css: "grid-button-text", width: 150, label: i18n.t(cmd.label), tooltip: i18n.t(cmd.label) });

				} else {

					toolbar.elements.push({ id: "tb_btn-" + this.uid + "_" + cmd.id, view: "icon", icon: cmd.icon, value: i18n.t(cmd.label), tooltip: true });
				}
			});
		}

		toolbar.elements.push({});
		toolbar.elements.push({ minWidth: 4 });

		toolbar.elements.push({
			id: "tb_ws_status-" + this.uid,
			width: 1,
			view: "label"
		});

		if (resultSelRows) {

			toolbar.elements.push({ view: "label", id: "lb-" + this.uid, label: "Sel", width: 20, height: 15, css: "sel_label" });

			toolbar.elements.push({ view: "label", id: "lb-sel" + this.uid, label: "0/", value: "Ninguna fila seleccionada", css: "sel_label_selected", width: 36, height: 13, tooltip: true });

		}

		if (resultSearch) {

			searchBar.width = 300;
			toolbar.elements.push(searchBar);
		}

		if (!showOnlySearchBar) {

			toolbar.elements.push({ width: 8 });
		}

		return toolbar;
	}

	drawContextButtonAction(datatable) {

		let ctxMenuData = [];
		let self = this;
		let metadata = this.metadata;
		let data = this.data;

		if (metadata.gridCommands && data) {

			metadata.gridCommands.forEach(cmd => {

				ctxMenuData.push({ id: cmd.id, value: i18n.t(cmd.label), icon: cmd.icon, default: cmd.default_ });
			});

			if (ctxMenuData.length > 0) {
				datatable.ready = function() {
					webix.ui({
						view: "contextmenu",
						id: "ctx_menu-" + self.uid,
						width: 300,
						data: ctxMenuData,
						template: (obj) => {

							if (!obj.default) {
								return `<span class = 'webix_icon ${obj.icon}'></span> ${obj.value}`;
							}
							else {
								return `<span class = 'webix_icon ${obj.icon}'></span> <span style='font-weight: 500;'>${obj.value}</span>`;
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
						
					}).attachTo(this);
				};
			}
		}
	}

	selectDatatable() {

		let self = this;
		let datatable = $$("dt-" + this.uid);
		let rowsSelected = this.rowsSelected;
		let label = $$("lb-sel" + this.uid);
		const Selected = { ZERO: 0, ONE: 1 }

		datatable.attachEvent("onAfterSelect", function(selection, preserve) {

			if (preserve) {

				rowsSelected.push(selection.id);

			} else {

				rowsSelected.length = 0;
				rowsSelected.push(selection.id);
			}

		});

		datatable.attachEvent("onBeforeUnSelect", function(selection) {

			if (!self.disabledUnselectEvent && rowsSelected != undefined && rowsSelected.length > 0) {

				let deselection = rowsSelected.findIndex(e => e === selection.id);
				rowsSelected.splice(deselection, 1);
			}

		});

		datatable.attachEvent("onAfterRender", function() {

			if (label && label != undefined) {

				label.data.label = rowsSelected.length + "/" + datatable.count();

				if (rowsSelected.length === Selected.ZERO) {

					label.data.value = "Ninguna fila seleccionada";

				} else if (rowsSelected.length === Selected.ONE) {

					label.data.value = rowsSelected.length + " fila seleccionada de " + datatable.count();

				} else {

					label.data.value = rowsSelected.length + " filas seleccionadas de " + datatable.count();
				}

				label.refresh();
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

					return obj[gridField.sourceField].toString().indexOf(value) != -1;

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

						return obj[gridField.sourceField].toString().indexOf(resultFilter) != -1;
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

			if (optionValue) {

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

			this.rowsSelected.length = 0;
		}
	}

	add(items) {

		let dataTable = $$("dt-" + this.uid);
		let anyChange = false;

		if (dataTable && items) {

			this.disabledUnselectEvent = true;

			if (Array.isArray(items) && items.length > 0) {

				items.forEach(dat => {

					if (dataTable.find(function(e) { return e.id === dat.id; }), true) {

						dataTable.add(dat);
						anyChange = true;
					}
				});
			}
			else if (!Array.isArray(items)) {

				if (dataTable.find(function(e) { return e.id === dat.id; }), true) {

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

				let dataToUpdate = dataTable.find(function(e) { return e.id === dat.id; }, true);

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

				if (dataTable.find(function(e) { return e.id === dat.id; }), true) {

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
		let rowsSelected = this.rowsSelected;

		if (rowsSelected != undefined && rowsSelected.length > 0) {

			rowsSelected.forEach(e => grid.select(e, true));

		}
	}

	restoreSort() {

		let grid = $$("dt-" + this.uid);

		if (this.sortedByField.field != undefined && this.sortedByField.sort != undefined) {

			grid.sort(this.sortedByField.field, this.sortedByField.sort, grid.getColumnConfig(this.sortedByField.field).sort);
		}
	}

	restoreFilters() {

		let self = this;
		let grid = $$("dt-" + this.uid);

		if (this.filteredFields) {

			var fields = Object.keys(this.filteredFields);

			grid.filter(function(data) {

				let res = true;

				fields.forEach(field => {

					if (self.filteredFields[field]) {

						let metadataField = self.metadata.gridFields.find(f => {

							return f.sourceField === field;
						});

						if (metadataField) {

							if ((metadataField.parameterized)) {

								res &= (data[field].toString() === self.filteredFields[field]);
							}
							else {

								res &= (data[field].toString().toLowerCase().indexOf(self.filteredFields[field].toString().toLowerCase())) != -1;
							}
						}
					}
				});

				return res;
			});
		}
	}


	clickShowFilters() {

		let self = this;
		let showFiltersButton = $$("tb_btn-showfilters-" + this.uid);
		let datatable = $$("dt-" + this.uid);
		let resultFilters = this.getGridOptions(GridOptions.FILTER);
		let columnVertical = this.metadata.gridFields.filter(e => (e.headerVertical));
		let filtered = this.metadata.gridFields.find(e => e.filtered === true && e.visible === true);

		let visible = resultFilters;

		if (showFiltersButton) {

			if (resultFilters) {

				showFiltersButton._contentobj.className = "webix_view webix_control webix_el_icon filter_icon_selected";
				showFiltersButton.refresh();
			}

			showFiltersButton.attachEvent("onItemClick", function(id, e) {

				if (datatable && filtered) {

					let header = document.querySelector('[view_id = dt-' + self.uid + ']').getElementsByClassName('webix_ss_header');
					let lateral = document.querySelector('[view_id = dt-' + self.uid + ']').getElementsByClassName('webix_ss_vscroll_header');

					if (visible) {

						showFiltersButton._contentobj.className = "webix_view webix_control webix_el_icon";
						showFiltersButton.refresh();

						visible = false;
						header[0].style.borderBottom = "1px solid var(--blue-strong)";
						lateral[0].style.borderBottom = "0px solid var(--blue-strong)";

						if (columnVertical && columnVertical.length > 0) {

							datatable.config.headerRowHeight = ExtendedHeader.CLOSE;

						} else {

							datatable.config.headerRowHeight = ExtendedHeader.FALSE;

						}

					} else {
						showFiltersButton._contentobj.className = "webix_view webix_control webix_el_icon filter_icon_selected";
						showFiltersButton.refresh();

						visible = true;
						datatable.config.headerRowHeight = ExtendedHeader.TRUE;
						header[0].style.borderBottom = "0px solid var(--blue-strong)";
						lateral[0].style.borderBottom = "1px solid var(--blue-strong)";

					}
				}

				datatable.refreshColumns();
			});
		}
	}


	showFilters() {

		let self = this;
		let datatable = $$("dt-" + this.uid);
		let resultFilters = this.getGridOptions(GridOptions.FILTER);
		let resultVertical = this.getGridOptions(GridOptions.HEADER_ROW_HEIGHT);
		let columnVertical = this.metadata.gridFields.filter(e => (e.headerVertical));
		let columnFilterTrue = this.metadata.gridFields.filter(e => (e.filtered));

		if (columnFilterTrue && columnFilterTrue.length > 0) {

			let header = document.querySelector('[view_id = dt-' + self.uid + ']').getElementsByClassName('webix_ss_header');
			let lateral = document.querySelector('[view_id = dt-' + self.uid + ']').getElementsByClassName('webix_ss_vscroll_header');
			let columnFilterFalse = this.metadata.gridFields.filter(e => e.filtered === false);

			if (columnVertical && columnVertical.length > 0) {

				if (resultFilters) {

					if (columnFilterFalse && columnFilterFalse.length > 0) {

						if (!isNaN(parseInt(resultVertical))) {

							for (let column of datatable.config.columns) {

								column.header[0].height = parseInt(resultVertical);

								if (column.header[0].rotate && !column.header[1]) {
									column.header[0].css = { "text-align": "center" };
								}

								if (!column.header[0].rotate && column.header[1]) {
									column.header[0].css = { "padding-top": "20px" };

								}
							}

							datatable.config.headerRowHeight = ExtendedHeader.TRUE;

						} else {

							for (let column of datatable.config.columns) {

								column.header[0].autoheight = true;
								if (column.header[0].rotate && !column.header[1]) {
									column.header[0].css = { "text-align": "center" };
								}

								if (!column.header[0].rotate && column.header[1]) {
									column.header[0].css = { "padding-top": "20px" };

								}
							}
						}

					} else {

						if (!isNaN(parseInt(resultVertical))) {

							for (let column of datatable.config.columns) {

								column.header[0].height = parseInt(resultVertical);
							}

						} else {
							for (let column of datatable.config.columns) {

								column.header[0].autoheight = true;
							}
						}
					}

				} else {

					datatable.config.headerRowHeight = ExtendedHeader.CLOSE;
					header[0].style.borderBottom = "1px solid var(--blue-strong)";
					lateral[0].style.borderBottom = "0px solid var(--blue-strong)";

					if (columnFilterFalse && columnFilterFalse.length > 0) {

						if (!isNaN(parseInt(resultVertical))) {

							for (let column of datatable.config.columns) {

								column.header[0].height = parseInt(resultVertical);

								if (column.header[0].rotate && !column.header[1]) {
									column.header[0].css = { "text-align": "center" };
								}
							}

						} else {

							for (let column of datatable.config.columns) {
								column.header[0].autoheight = true;

								if (column.header[0].rotate) {
									column.header[0].css = { "text-align": "center" };
								}
							}
						}

					} else {

						if (!isNaN(parseInt(resultVertical))) {

							for (let column of datatable.config.columns) {

								column.header[0].height = parseInt(resultVertical);
							}

						} else {

							for (let column of datatable.config.columns) {

								column.header[0].autoheight = true;
							}
						}
					}
				}

			} else {

				if (resultFilters) {

					if (columnFilterFalse && columnFilterFalse.length > 0) {

						columnFilterFalse.forEach(item => {

							let pad = datatable.config.columns.find(e => e.id === item.sourceField);

							if (pad) {

								pad.header[0].css = { "padding-bottom": "85px !important" };
							}

						});
					}

					datatable.config.headerRowHeight = ExtendedHeader.TRUE;

				} else {
					if (columnFilterFalse && columnFilterFalse.length > 0) {

						columnFilterFalse.forEach(item => {

							let pad = datatable.config.columns.find(e => e.id === item.sourceField);

							if (pad) {

								pad.header[0].css = { "padding-bottom": "85px !important" };
							}


						});
					}

					datatable.config.headerRowHeight = ExtendedHeader.FALSE;
					header[0].style.borderBottom = "1px solid var(--blue-strong)";
					lateral[0].style.borderBottom = "0px solid var(--blue-strong)";

				}

			}
		} else {

			if (columnVertical && columnVertical.length > 0) {

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

				datatable.config.headerRowHeight = ExtendedHeader.TRUE;

			}
		}

		datatable.refreshColumns();
	}




	datatableCapture() {
		let self = this;
		let datatable = $$("dt-" + this.uid);
		let sortedByField = this.sortedByField;

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

						if (gridField.gridFieldIcons.length > 0) {

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

	callbacks() {

		this.selectDatatable();
		this.datatableCapture();
		this.search_callback();
		this.select_all_callback();
		this.unselect_all_callback();
		this.refresh_callback();
		this.print_callback();
		this.export_pdf_callback();
		this.export_xls_callback();
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

		this.restoreSelected();
		this.sortDatatable();
		this.filterDatatable();
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
					}
				});
			}
		}
	}

	search_callback() {

		let grid = $$("dt-" + this.uid);
		let txtSearch = $$("txtSearch-" + this.uid);

		if (!webix.isUndefined(grid) && !webix.isUndefined(txtSearch)) {

			txtSearch.attachEvent("onTimedKeyPress", function() {
				let value = this.getValue().toLowerCase(); // input data is derived
				grid.filter(function(obj) {  // here it filters all titles from the dataset

					let res = false;
					grid.config.columns.forEach(col => {
						if (col.id != undefined) {

							if (col.sort === DataType.DATE.sort) {

								res |= (obj[col.id].toLocaleString(i18n.getLocale(), FORMAT_NUMERIC_DATES).indexOf(value) !== -1);
							}
							else {

								res |= (String(obj[col.id]).toLowerCase().indexOf(value) !== -1);
							}
						}
					});

					return res;
				});
			});
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

				args.callbacks = [];
				args.callbacks.push(function() { self.refresh(self) });
			}

			// Any selected?
			if (selectedPks.length > 0) {

				// Multiselect not allowed with 2 o more selected
				if (selectedPks.length > 1 && gridCommand.multiselect === false) {

					webix.alert({
						title: i18n.t("LBL_COMMAND_NOT_COMPATIBLE_MULTISELECT"),
						text: i18n.t("LBL_COMMAND_NOT_COMPATIBLE_MULTISELECT_DESCRIPTION"),
						type: "alert-error",
						width: 500
					});
				}
				// One or more selected and multiselect allowed
				else {

					args.selectedPks = selectedPks;
					
					if (!self.command_exec_callback(gridCommand)) {
					
						if (gridCommand.moduleAction) {
						
							let module = this.find_module(gridCommand.moduleAction.id);
							util.module.open(module, gridCommand.moduleAction, viewTypeId, null, args);	
						}
					}
				}
			}
			// No rows selected
			else {
				// Required one or more selected
				if (gridCommand.itemRequired) {
					webix.alert({
						title: i18n.t("LBL_COMMAND_NO_ROWS_SELECTED"),
						text: i18n.t("LBL_COMMAND_NO_ROWS_SELECTED_DESCRIPTION"),
						type: "alert-warning",
						width: 500
					});
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

		if (grid) {

			grid.attachEvent("onItemDblClick", function(id, e, node) {

				if (self.events && self.events[DataTable.EventTypes.ON_ITEM_DBL_CLIC]) {

					eval(self.events[DataTable.EventTypes.ON_ITEM_DBL_CLIC])(self.getSelectedIds());
				}
				else {

					let defaultCommand = self.metadata.gridCommands.find(gridCommand => {
						return gridCommand.default_ === true
					});

					self.open_module(defaultCommand);
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

					eval(self.events[DataTable.EventTypes.ON_SELECT_ROW])(self.getSelectedIds());
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

		if (!webix.isUndefined(grid) && !webix.isUndefined(selectAllButton)) {

			selectAllButton.attachEvent("onItemClick", function(id, e) {

				if (grid.getFirstId()) {

					grid.selectAll();
				}
			});
		}
	}

	unselect_all_callback() {

		let unselectAllButton = $$("tb_btn-unselect-all-" + this.uid);
		let grid = $$("dt-" + this.uid);

		if (!webix.isUndefined(grid) && !webix.isUndefined(unselectAllButton)) {

			unselectAllButton.attachEvent("onItemClick", function(id, e) {

				if (grid.getFirstId()) {

					grid.unselectAll();
				}
			});
		}
	}

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

	refresh(obj) {

		let url = undefined;
		let self = undefined;

		if (obj != undefined) {
			self = obj;
		}
		else {
			self = this;
		}

		let grid = $$("dt-" + self.uid);

		if (!webix.isUndefined(grid)) {

			grid.showProgress({

				hide: true, type: "bottom"
			});

			if (this.dataGridParams) {

				url = appConfig.restUrl + "grids/" + self.dataGridId + "/" + self.dataGridParams;
			}
			else {

				url = appConfig.restUrl + "grids/" + self.dataGridId;
			}

			webix.ajax(url).then(function(dat) {

				webix.delay(function() {

					if (dat && dat.json) {

						let data = dat.json();

						if (data.data) {

							self.translateFieldValues(data.data);
							self.convertDate(data.data);

							grid.clearAll();
							grid.parse(data.data);

							self.restoreSort();
							self.restoreFilters();
						}
					}

					grid.hideProgress();

				}, null, null, 1);
			});
		}
	}

	print_callback() {

		let printButton = $$("tb_btn-print-" + this.uid);
		let grid = $$("dt-" + this.uid);

		if (!webix.isUndefined(grid) && !webix.isUndefined(printButton)) {

			printButton.attachEvent("onItemClick", function(id, e) {

				webix.alert({
					title: i18n.t("LBL_NOT_IMPLEMENTED"),
					text: i18n.t("LBL_NOT_IMPLEMENTED_DESCRIPTION"),
					type: "alert-error",
					width: 500
				});
			});
		}
	}

	export_pdf_callback() {

		let metadata = this.metadata;
		let pdfButton = $$("tb_btn-export-pdf-" + this.uid);
		let grid = $$("dt-" + this.uid);

		if (!webix.isUndefined(grid) && !webix.isUndefined(pdfButton)) {

			webix.extend(grid, webix.ProgressBar);

			pdfButton.attachEvent("onItemClick", function(id, e) {

				grid.showProgress({
					hide: true, type: "bottom"
				});

				webix.delay(function() {

					webix.toPDF(grid, {
						filename: i18n.t(metadata.label),
						docHeader: i18n.t(metadata.label),
						orientation: "landscape",
						display: "image"
					});

					grid.hideProgress();
				}, null, null, 1);
			});
		}
	}

	export_xls_callback() {

		let xlsButton = $$("tb_btn-export-xls-" + this.uid);
		let grid = $$("dt-" + this.uid);

		if (!webix.isUndefined(grid) && !webix.isUndefined(xlsButton)) {

			xlsButton.attachEvent("onItemClick", function(id, e) {

				webix.alert({
					title: i18n.t("LBL_NOT_IMPLEMENTED"),
					text: i18n.t("LBL_NOT_IMPLEMENTED_DESCRIPTION"),
					type: "alert-error",
					width: 500
				});
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
			
				cmdCallback.function(this.getSelectedIds());
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

					function on_message(message) {

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
									}
									else if (gridData.action == 'CREATE') {

										grid.add(dataItem);
										rowsUpdated++;

									} else if (record != undefined) {

										var keys = Object.keys(dataItem);

										if (keys != undefined && Array.isArray(keys) && keys.length > 0) {

											keys.forEach(key => {

												if (dataItem[key] != undefined) {

													record[key] = dataItem[key];
												} else {

													record[key] = "";
												}
											});
										}
									}
									rowsUpdated++;
								});

								if (rowsUpdated > 0) {

									grid.refresh();
								}
							}
						}

						var data = JSON.parse(JSON.parse(message.body));

						eval("adapter." + adapterDataSource.value)(data, update);
					}

					let url = dataSource.dataSourceValues[0].value;
					let topic = dataSource.dataSourceValues[1].value;

					let params;

					if (self.dataGridParams) {

						params = "." + self.dataGridParams + ".>";
					}
					else {

						params = ".>";
					}

					this.stomp.client = util.stomp.connect(url, topic + params, on_connected, on_message);


				}

			}
		}
	}

	disconnect_ws() {

		if (this.stomp != undefined && this.stomp.client != undefined && this.stomp.client.connected) {

			util.stomp.disconnect(this.stomp.client);
			this.stomp = {};
		}
	}

	select(ids) {

		let grid = $$("dt-" + this.uid);

		if (grid != undefined) {

			if (Array.isArray(ids) && ids.length) {

				ids.forEach(id => {

					let record = grid.getItem(id);

					if (record != undefined) {

						grid.select(id);
					}
				});
			}
			else if (ids) {

				let id = ids;

				let record = grid.getItem(id);

				if (record != undefined) {

					grid.select(id);
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