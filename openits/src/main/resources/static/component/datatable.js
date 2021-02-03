import { util } from "../lib/util.js";
import { adapter } from "../lib/adapter.js";

const DataType = {
	STRING: { value: 1, filter: "textFilter" },
	INT: { value: 2, filter: "numberFilter" },
	DECIMAL: { value: 3, filter: "numberFilter" },
	DATE: { value: 4, filter: "dateFilter" }
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
	ICON: 16
}

export default class DataTable {

	constructor(dataGridId, dataGridParams, metadata, data) {

		this.dataGridId = dataGridId;
		this.dataGridParams = dataGridParams;
		this.metadata = metadata;
		this.data = data;
		this.stomp = {};
		this.uid = webix.uid();
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
		
		let resultSort = this.getGridOptions(GridOptions.SORT);
		let resultFilter = this.getGridOptions(GridOptions.FILTER);
		let resultMulti = this.getGridOptions(GridOptions.MULTISELECT);
				
		if (metadata != undefined) {

			datatable.id = "dt-" + this.uid;
			datatable.view = "datatable";
			datatable.css = "rows";
			datatable.select = "row";
			datatable.tooltip = true;
			datatable.resizeColumn = true;
			datatable.multiselect = resultMulti;
			datatable.sort = resultSort;
			datatable.filter = resultFilter;

			if (metadata.gridFields.length > 0) {

				datatable.columns = [];

				metadata.gridFields.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));

				self.translateFieldValues(data);

				for (let posField = 0; posField < metadata.gridFields.length; posField++) {
					let field = metadata.gridFields[posField];
					
					console.log(field)
					let col = {};
					col.id = field.sourceField;

					// Paint header and filters headers
					col.header = [];

					if (resultFilter) {

						if (field.label) {

							col.header.push(`<div title="${i18n.t(field.labelTooltip)}">${i18n.t(field.label)}</div>`);
						}
						else {

							col.header.push(`<div></div>`);
						}

						if (field.parameterized) {
							col.header.push({ content: "selectFilter" });
						}
						else {
							if (field.gridFieldDataType) {
								
								if (field.gridFieldDataType.id == DataType.STRING.value) {
									col.header.push({ content: DataType.STRING.filter });
								}
								else if (field.gridFieldDataType.id == DataType.INT.value) {
									col.header.push({ content: DataType.INT.filter });
								}
								else if (field.gridFieldDataType.id == DataType.DECIMAL.value) {
									col.header.push({ content: DataType.DECIMAL.filter });
								}
								else if (field.gridFieldDataType.id == DataType.DATE.value) {
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

							col.header = `<div title="${i18n.t(field.labelTooltip)}">${i18n.t(field.label)}</div>`;
						}
						else {

							col.header = `<div></div>`;
						}
					}

					if (posField < metadata.gridFields.length) {

						col.hidden = !field.visible;
						col.width = field.widthPx;
					}

					if (field.gridFieldDataType) {
						
						col.sort = field.gridFieldDataType.name;	
					}
					else {
						
						col.sort = "string";
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

							if (typeof obj[field.sourceField] === 'object') {

								value = obj[field.sourceField].value;
								state = obj[field.sourceField].state;
							}
							else {

								if (field.gridFieldIcons != undefined && field.gridFieldIcons.length > 0) {

									badgeColor = (field.gridFieldIcons[0].badgeColor == true);
									avatar = (field.gridFieldIcons[0].avatar == true);
									showValue = (field.gridFieldIcons[0].showValue == true);
									value = obj[field.sourceField];

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

							if (field.isEpochMilli) {

								value = util.toLocaleString(value / 1000);
							}

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

								return `<div><span class="table-badge" style="background: ${'#607d8b3b'}; color: ${'#607d8ba0'};">${i18n.t('LBL_NO_AVAILABLE')}</span></div>`;
							}
							else {
							
								return `<span class="table-row-text">${value}</span>`;	
							}

						}
					}

					datatable.columns.push(col);
				}

				let lastCol = {};

				lastCol.fillspace = true;
				datatable.columns.push(lastCol);

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


	drawMainToolbar() {

		let metadata = this.metadata;
		let resultSegmented = this.getGridOptions(GridOptions.TB_MAIN_SEGMENTED);
		let resultDetails = this.getGridOptions(GridOptions.SHOW_DETAILS);

		let toolbar = {
			view: "toolbar",
			id: "tbMain-" + this.uid,
		}


		toolbar.elements = [];
		toolbar.elements.push({ width: 4 })

		toolbar.elements.push({ view: "label", label: "<img src='" + metadata.icon + "' class='grid-icon'/><span class='grid-title'>" + i18n.t(metadata.label) + "</span>" });

		toolbar.elements.push({ minWidth: 4 });

		if (resultSegmented) {


			let segmentViews = {
				view: "segmented", minWidth: 333,
				id: "seg-" + this.uid,
				options: [
					{ id: "seg-basic-" + this.uid, css: "table-tab-button", value: i18n.t("LBL_TABLE_BASIC") },
					{ id: "seg-details-" + this.uid, css: "table-tab-button", value: i18n.t("LBL_TABLE_DETAILED") }
				]
			}

			if (resultDetails) {

				segmentViews.value = "seg-details-" + this.uid;

			} else {

				segmentViews.value = "seg-basic-" + this.uid;
			}
			toolbar.elements.push(segmentViews)
		}

		toolbar.elements.push({ width: 8 });

		return toolbar;
	}



	drawActionsToolbar() {

		let metadata = this.metadata;

		let resultCommons = this.getGridOptions(GridOptions.TB_ACTIONS_BUTTONS_COMMONS);
		let resultCommands = this.getGridOptions(GridOptions.TB_ACTIONS_BUTTONS_COMMANDS);
		let resultSearch = this.getGridOptions(GridOptions.SEARCH);
		let resultMulti = this.getGridOptions(GridOptions.MULTISELECT);

		let toolbar = {
			view: "toolbar",
			id: "tbActions-" + this.uid
		}

		toolbar.elements = [];

		toolbar.elements.push({ width: 4 })

		if (resultMulti) {

			toolbar.elements.push({ id: "tb_btn-select-all-" + this.uid, view: "icon", icon: "mdi mdi-check-box-outline", value: i18n.t("LBL_SELECT_ALL"), tooltip: true });

			toolbar.elements.push({ id: "tb_btn-unselect-all-" + this.uid, view: "icon", icon: "mdi mdi-checkbox-blank-outline", value: i18n.t("LBL_UNSELECT_ALL"), tooltip: true });
		}

		if (resultCommons) {

			toolbar.elements.push({ id: "tb_btn-refresh-" + this.uid, view: "icon", icon: "mdi mdi-refresh", value: i18n.t("LBL_TABLE_REFRESH"), tooltip: true });
			toolbar.elements.push({ id: "tb_btn-print-" + this.uid, view: "icon", icon: "mdi mdi-printer", value: i18n.t("LBL_TABLE_PRINT"), tooltip: true });
			toolbar.elements.push({ id: "tb_btn-export-pdf-" + this.uid, view: "icon", icon: "mdi mdi-file-pdf-box", value: i18n.t("LBL_TABLE_EXPORT_PDF"), tooltip: true });
			toolbar.elements.push({ id: "tb_btn-export-xls-" + this.uid, view: "icon", icon: "mdi mdi-file-excel", value: i18n.t("LBL_TABLE_EXPORT_XLS"), tooltip: true });

		}

		if (metadata.gridCommands && metadata.gridCommands.length > 0 && resultCommands) {

			toolbar.elements.push({ width: 8 });
			metadata.gridCommands.forEach(cmd => {

				toolbar.elements.push({ id: "tb_btn-" + this.uid + "_" + cmd.id, view: "icon", icon: cmd.icon, value: i18n.t(cmd.label), tooltip: true });
				//toolbar.elements.push({ id: "tb_btn-label-" + this.uid + "_" + cmd.id, css: "grid-toolbar-actions-button-text", view: "label", label: i18n.t(cmd.label) });
			});
		}

		toolbar.elements.push({ minWidth: 4 });

		toolbar.elements.push({
			id: "tb_ws_status-" + this.uid,
			width: 150,
			view: "label"
		});

		if (resultSearch) {

			toolbar.elements.push({
				view: "search",
				id: "txtSearch-" + this.uid,
				placeholder: i18n.t("LBL_SEARCH"),
				width: 300
			});
		}

		toolbar.elements.push({ width: 8 });

		return toolbar;

	}



	drawContextButtonAction(datatable) {

		let ctxMenuData = [];
		let self = this;
		let metadata = this.metadata;
		
		if (metadata.gridCommands) {
		
			metadata.gridCommands.forEach(cmd => {

				ctxMenuData.push({ id: cmd.id, value: i18n.t(cmd.label), icon: cmd.icon, default: cmd.default_ });
			});
	
			if (ctxMenuData.length > 0) {
				datatable.ready = function() {
					webix.ui({
						view: "contextmenu",
						id: "ctx_menu-" + self.uid,
						width: 250,
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
	
								self.open_module(gridCommand);
							}
						}
					}).attachTo(this);
				};
			}	
		}
	}

	sortDatatable(gridFieldId, order) {

		let self = this;
		let dataTable = $$("dt-" + this.uid);
		let metadata = this.metadata;

		if (gridFieldId && order) {

			let gridField = this.metadata.gridFields.find(e => {

				return e.id === gridFieldId;

			});

			if (gridField) {

				dataTable.markSorting(gridField.sourceField, order);
				dataTable.sort(gridField.sourceField, order);
			}

		} else {

			let resultId = this.getGridOptions(GridOptions.SORT_FIELD_ID);

			let gridField = this.metadata.gridFields.find(e => {

				return e.id === parseInt(resultId.value);
			});

			if (gridField) {

				let resultOrd = this.getGridOptions(GridOptions.SORT_FIELD_ORDER);

				dataTable.markSorting(gridField.sourceField, resultOrd.value);
				dataTable.sort(gridField.sourceField, resultOrd.value);
			}

		}
	}



	filterDatatable(gridFieldId, value) {

		let metadata = this.metadata;
		let self = this;
		let dataTable = $$("dt-" + this.uid);

		if (gridFieldId && value) {

			let gridField = this.metadata.gridFields.find(e => {

				return e.id === gridFieldId;
			});

			if (gridField) {

				dataTable.filter(function(obj) {

					return obj[gridField.sourceField].toString().indexOf(value) != -1;

				});
			}
		} else {

			let resultId = this.getGridOptions(GridOptions.FILTER_FIELD_ID);

			let gridField = this.metadata.gridFields.find(e => {

				return e.id === parseInt(resultId.value);
			});

			if (gridField) {

				let resultFilter = this.getGridOptions(GridOptions.FILTER_FIELD_VALUE);

				dataTable.filter(function(obj) {

					return obj[gridField.sourceField].toString().indexOf(resultFilter.value) != -1;
				})

			}

		}
	}



	showMainToolbar() {

		let metadata = this.metadata;
		let dataTable = $$("dt-" + this.uid);
		let resultViews = this.getGridOptions(GridOptions.SHOW_DETAILS);

		if (resultViews) {

			for (let i = 0; i < metadata.gridFields.length; i++) {

				let field = metadata.gridFields[i];

				if (field.extended) {

					dataTable.showColumn(field.sourceField)
				}
			}
		}
	}



	getGridOptions(option) {

		let metadata = this.metadata;
		let optionValues;

		if (metadata) {
			optionValues = metadata.gridOptionValues.find(e => e.gridOption.id === option)
		}
		
		if(optionValues.value === "true"){
			
			return true;
	
		} else if (optionValues.value === "false"){
		
			return false;
		}
		
		return optionValues;
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



	callbacks() {

		this.sortDatatable();
		this.filterDatatable();
		this.search_callback();
		this.select_all_callback();
		this.unselect_all_callback();
		this.refresh_callback();
		this.print_callback();
		this.export_pdf_callback();
		this.export_xls_callback();
		this.main_toolbar_callback();
		this.change_view("hide");
		this.showMainToolbar();
		this.connect_ws();
		this.on_destroy();
		this.on_item_dbl_click();
		this.other_commands_callback();
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

	//	change_view(selection) {
	//
	//		let grid = $$("dt-" + this.uid);
	//		let metadata = this.metadata;
	//		let select = undefined;
	//		let resultDetails = this.getGridOptions(GridOptions.SHOW_DETAILS);
	//		console.log(resultDetails)
	//		if (grid) {
	//		
	//			if (selection) {
	//
	//				select = selection;
	//				
	//			} else {
	//				
	//				if (resultDetails.value === "true") {
	//				
	//					select = (resultDetails.value === "true") ? "show" : "hide" ;	
	//				}
	//				else {
	//					
	//					select = "hide";
	//				}
	//			}	
	//			
	//			if (metadata.gridFields.length > 0) {
	//
	//				for (let posField = 0; posField < metadata.gridFields.length; posField++) {
	//
	//					let field = metadata.gridFields[posField];
	//
	//					if (field.extended) {
	//
	//						if (select === "hide" && this.selection === "show") {
	//							grid.hideColumn(field.sourceField);
	//							this.selection = select;
	//						}
	//						else if (select === "show" && this.selection === "hide") {
	//							grid.showColumn(field.sourceField);
	//							this.selection = select;
	//						}
	//					}
	//				}
	//			}
	//		}
	//
	//	}

	change_view(selection) {

		let grid = $$("dt-" + this.uid);
		let metadata = this.metadata;

		if (grid) {

			if (metadata.gridFields.length > 0) {

				for (let posField = 0; posField < metadata.gridFields.length; posField++) {

					let field = metadata.gridFields[posField];

					if (field.extended) {

						if (selection === "hide") {
							grid.hideColumn(field.sourceField);
						}
						else if (selection === "show") {
							grid.showColumn(field.sourceField);
						}
					}
				}
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
							res |= (String(obj[col.id]).toLowerCase().indexOf(value) !== -1);
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

		if (gridCommand) {

			let module = this.find_module(gridCommand.moduleAction.id);

			let selectedPks = this.getSelectedIds();

			if (selectedPks.length > 0) {

				if (selectedPks.length > 1 && gridCommand.multiselect === false) {

					webix.alert({
						title: i18n.t("LBL_COMMAND_NOT_COMPATIBLE_MULTISELECT"),
						text: i18n.t("LBL_COMMAND_NOT_COMPATIBLE_MULTISELECT_DESCRIPTION"),
						type: "alert-error",
						width: 500
					});
				}
				else {

					let viewTypeId = undefined;

					if (gridCommand.viewType) {

						viewTypeId = gridCommand.viewType.id;
					}

					if (gridCommand.args) {

						args = gridCommand.args;
					}

					args.selectedPks = selectedPks;
					args.callbacks = [];

					args.callbacks.push(this.refresh);

					util.module.open(module, gridCommand.moduleAction, viewTypeId, null, args);
				}
			}
			else {

				webix.alert({
					title: i18n.t("LBL_COMMAND_NO_ROWS_SELECTED"),
					text: i18n.t("LBL_COMMAND_NO_ROWS_SELECTED_DESCRIPTION"),
					type: "alert-warning",
					width: 500
				});
			}
		}
	}

	on_item_dbl_click() {

		let self = this;
		let grid = $$("dt-" + this.uid);

		if (grid) {

			grid.attachEvent("onItemDblClick", function(id, e, node) {

				let defaultCommand = self.metadata.gridCommands.find(gridCommand => {
					return gridCommand.default_ === true
				});

				self.open_module(defaultCommand);
			});
		}
	}

	main_toolbar_callback() {

		let self = this;
		let segInstance = $$("seg-" + this.uid);

		if (segInstance) {

			segInstance.attachEvent("onChange", function(newv, oldv) {

				if (newv === "seg-basic-" + self.uid) {
					self.change_view("hide");
				}
				else if (newv === "seg-details-" + self.uid) {
					self.change_view("show");
				}
			});
		}
	}

	select_all_callback() {

		let selectAllButton = $$("tb_btn-select-all-" + this.uid);
		let grid = $$("dt-" + this.uid);

		if (!webix.isUndefined(grid) && !webix.isUndefined(selectAllButton)) {

			selectAllButton.attachEvent("onItemClick", function(id, e) {

				grid.selectAll();
			});
		}
	}

	unselect_all_callback() {

		let unselectAllButton = $$("tb_btn-unselect-all-" + this.uid);
		let grid = $$("dt-" + this.uid);

		if (!webix.isUndefined(grid) && !webix.isUndefined(unselectAllButton)) {

			unselectAllButton.attachEvent("onItemClick", function(id, e) {

				grid.unselectAll();
			});
		}
	}

	refresh_callback() {

		let self = this;
		let refreshButton = $$("tb_btn-refresh-" + this.uid);
		let grid = $$("dt-" + this.uid);

		if (!webix.isUndefined(grid) && !webix.isUndefined(refreshButton)) {

			webix.extend(grid, webix.ProgressBar);

			refreshButton.attachEvent("onItemClick", function(id, e) {

				self.refresh();
			});
		}
	}

	refresh() {
		
		let grid = $$("dt-" + this.uid);
		let self = this;
		let url = undefined;

		if (!webix.isUndefined(grid)) {
			
			grid.showProgress({

				hide: true, type: "bottom"
			});
			
			if (this.dataGridParams) {
				
				url = appConfig.restUrl + "grids/" + this.dataGridId + "/" + this.dataGridParams;
			}
			else {
				
				url = appConfig.restUrl + "grids/" + this.dataGridId;
			}
				
			webix.ajax(url).then(function(dat) {

				webix.delay(function() {
					
					if (dat && dat.json) {
						
						let data = dat.json();
						self.translateFieldValues(data.data);

						grid.parse(data.data);
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

							let gridCommand = metadata.gridCommands.find(gridCmd => {
								return gridCmd.id === cmd.id
							});

							self.open_module(gridCommand);
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

						$$("tb_ws_status-" + self.uid).setHTML(`<div><span class="status_ws_connected">${i18n.t("LBL_CONNECTED")}</span></div>`);
					}

					function on_message(message) {

						function update(gridData) {

							let grid = $$("dt-" + self.uid);
							let rowsUpdated = 0;

							if (!webix.isUndefined(grid) && gridData != undefined && gridData.data != undefined && Array.isArray(gridData.data) && gridData.data.length > 0) {

								self.translateFieldValues(gridData.data);

								gridData.data.forEach(dataItem => {

									var record = grid.getItem(dataItem.id);
		
									if (record != undefined && gridData.action == 'DELETE') {
											
										grid.remove(record.id);
										rowsUpdated++;
									}
									else if (gridData.action == 'CREATE') {
										
										grid.add(dataItem);
										rowsUpdated++;
										
									} else if(record != undefined ) {
										
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

					$$("tb_ws_status-" + this.uid).setHTML(`<div><span class="status_ws_disconnected">${i18n.t("LBL_DISCONNECTED")}</span></div>`);

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

	update(data) {

		let grid = $$("dt-" + this.uid);
		let rowsUpdated = 0;

		if (!webix.isUndefined(grid) && data != undefined && Array.isArray(data) && data.length > 0) {

			data.forEach(dataItem => {

				var record = grid.getItem(dataItem.id);

				if (record != undefined) {

					var keys = Object.keys(dataItem);

					if (keys != undefined && Array.isArray(keys) && keys.length > 0) {

						keys.forEach(key => {

							if (dataItem[key] != undefined) {

								record[key] = dataItem[key];
							}
							else {

								record[key] = "";
							}
						});
					}

					rowsUpdated++;
				}
			});

			if (rowsUpdated > 0) {

				grid.refresh();
			}
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
