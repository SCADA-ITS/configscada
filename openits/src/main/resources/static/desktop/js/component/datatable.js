if (window.app)
	app.datatable = {

		dataType: {
			string: { value: 1, filter: "textFilter" },
			int: { value: 2, filter: "numberFilter" },
			decimal: { value: 3, filter: "numberFilter" },
			date: { value: 4, filter: "dateFilter" }
		},

		currentGridInstances: [],

		draw(dataGridId, dataGridParams, moduleInstance, metadata, data) {

			this.currentGridInstances[moduleInstance] = {
				id: dataGridId,
				params: dataGridParams,
				metadata: metadata,
				stomp: {
					client: {}
				}
			};

			let toolbarMain = this.drawMainToolbar(moduleInstance, metadata);
			let toolbarActions = this.drawActionsToolbar(moduleInstance, metadata);
			let datatable = {};
			let layout = [];
			let pager = {};


			if (metadata != undefined) {

				datatable.id = "dt-" + moduleInstance;
				datatable.view = "datatable";
				datatable.css = "rows";
				datatable.select = "row";
				datatable.tooltip = true;
				datatable.resizeColumn = true;
				datatable.multiselect = metadata.multiselect;
				datatable.sort = metadata.sort;
				datatable.filter = metadata.filter;

				if (metadata.gridFields.length > 0) {

					datatable.columns = [];
					for (let posField = 0; posField < metadata.gridFields.length; posField++) {
						let field = metadata.gridFields[posField];
						let col = {};
						col.id = field.sourceField;

						// Paint header and filters headers
						col.header = [];

						if (field.filtered) {

							col.header.push(`<div title="${i18n.t(field.labelTooltip)}">${i18n.t(field.label)}</div>`);

							if (field.parameterized) {
								col.header.push({ content: "selectFilter" });
							}
							else {
								if (field.gridFieldDataType.id == this.dataType.string.value) {
									col.header.push({ content: this.dataType.string.filter });
								}
								else if (field.gridFieldDataType.id == this.dataType.int.value) {
									col.header.push({ content: this.dataType.int.filter });
								}
								else if (field.gridFieldDataType.id == this.dataType.decimal.value) {
									col.header.push({ content: this.dataType.decimal.filter });
								}
								else if (field.gridFieldDataType.id == this.dataType.date.value) {
									col.header.push({ content: this.dataType.date.filter });
								}
							}
						}
						else {

							//col.header = i18n.t(field.label);
							col.header = `<div title="${i18n.t(field.labelTooltip)}">${i18n.t(field.label)}</div>`;
						}

						if (posField < metadata.gridFields.length) {
							col.width = field.widthPx;
						}
						col.sort = field.gridFieldDataType.name;

						if (field.align != undefined) {

							col.css = { 'text-align': field.align }
						}

						col.template = function(data) {

							if (data[field.sourceField] != undefined) {

								let value;
								let state;
								let icon;

								if (typeof data[field.sourceField] === 'object') {

									value = data[field.sourceField].value;
									state = data[field.sourceField].state;
								}
								else {

									if (field.gridFieldIcons != undefined && field.gridFieldIcons.length > 0) {

										let gridFieldIcon = field.gridFieldIcons.filter(e => e.value == data[field.sourceField]);

										if (gridFieldIcon != undefined && gridFieldIcon.length > 0) {

											icon = gridFieldIcon[0].icon;
											//return `<img src="${gridFieldIcon[0].icon}" />`;	
										}
									}
									else if (field.gridFieldReferences != undefined && field.gridFieldReferences.length > 0) {

										
										let gridFieldReference = field.gridFieldReferences[0];
										
										let refField = metadata.gridFields.filter(e => e.id == gridFieldReference.refFieldId);

							
										if (refField != undefined) {

											value = app.em.getByField(em_types[gridFieldReference.emId].id , gridFieldReference.emField, data[refField[0].sourceField]);
											
											value = value[0][gridFieldReference.emFieldReturned];
										}
									}
									else {

										value = data[field.sourceField];
									}
								}

								if (icon != undefined) {

									return `<img class="table-field-icons" src="${icon}" />`;
								}
								else if (value != undefined) {

									if (field.needsTranslation) {

										value = i18n.t(value);
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
										return `<span>${value}</span>`;
									}
								}
								else {
									return `<span></span>`;
								}
							}
						}

						datatable.columns.push(col);
					}

					let lastCol = {};

					lastCol.fillspace = true;
					datatable.columns.push(lastCol);

					datatable.data = data;

					let ctxMenuData = [];

					metadata.gridCommands.forEach(cmd => {

						ctxMenuData.push(i18n.t(cmd.label));
					});

					if (ctxMenuData.length > 0) {
						datatable.ready = function() {
							webix.ui({
								view: "contextmenu", id: "ctx_menu-" + moduleInstance,
								data: ctxMenuData,
								on: {
									onMenuItemClick: function(id) {
										webix.message(this.getItem(id).value);
										// ...access the API or data of the master component
									}
								}
							}).attachTo(this);
						};
					}
				}

				//layout.push({ height: 8 });
				layout.push(toolbarMain);
				layout.push(toolbarActions);
				layout.push(datatable);

				if (metadata.pagination) {
					datatable.pager = "pager-" + moduleInstance;
					pager = {
						view: "pager",
						id: "pager-" + moduleInstance,
						size: 50,
					}

					layout.push(pager);
				}

				//layout.push({ height: 16 });
			}

			return layout;
		},

		drawMainToolbar(moduleInstance, metadata) {

			let toolbar = {
				view: "toolbar",
				id: "tbMain-" + moduleInstance,
			}

			toolbar.elements = [];
			toolbar.elements.push({ width: 4 })
			toolbar.elements.push({ view: "label", label: i18n.t(metadata.label), width: 200 });

			toolbar.elements.push({ minWidth: 4 });

			toolbar.elements.push({

				view: "segmented", minWidth: 333,
				id: "seg-" + moduleInstance,
				options: [
					{ id: "seg-basic-" + moduleInstance, css: "table-tab-button", value: i18n.t("LBL_TABLE_BASIC") },
					{ id: "seg-details-" + moduleInstance, css: "table-tab-button", value: i18n.t("LBL_TABLE_DETAILED") }
				]
			});

			toolbar.elements.push({ width: 8 });

			return toolbar;
		},

		drawActionsToolbar(moduleInstance, metadata) {

			let toolbar = {
				view: "toolbar",
				id: "tbActions-" + moduleInstance,
			}

			toolbar.elements = [];
			toolbar.elements.push({ width: 4 })

			toolbar.elements.push({ id: "tb_btn-refresh-" + moduleInstance, view: "icon", icon: "mdi mdi-refresh", value: i18n.t("LBL_TABLE_REFRESH"), tooltip: true });
			toolbar.elements.push({ id: "tb_btn-print-" + moduleInstance, view: "icon", icon: "mdi mdi-printer", value: i18n.t("LBL_TABLE_PRINT"), tooltip: true });
			toolbar.elements.push({ id: "tb_btn-export-pdf-" + moduleInstance, view: "icon", icon: "mdi mdi-file-pdf-box", value: i18n.t("LBL_TABLE_EXPORT_PDF"), tooltip: true });
			toolbar.elements.push({ id: "tb_btn-export-xls-" + moduleInstance, view: "icon", icon: "mdi mdi-file-excel", value: i18n.t("LBL_TABLE_EXPORT_XLS"), tooltip: true });



			toolbar.elements.push({ minWidth: 4 });

			toolbar.elements.push({
				id: "tb_ws_status-" + moduleInstance,
				width: 150,
				view: "label"
			});

			if (metadata.search) {

				toolbar.elements.push({
					view: "search",
					id: "txtSearch-" + moduleInstance,
					placeholder: i18n.t("LBL_EQUIPMENT_SEARCH"),
					width: 300
				});
			}

			toolbar.elements.push({ width: 8 });

			return toolbar;
		},

		callbacks(module, instance) {

			let moduleInstance = "module-" + module.id + "-" + instance;

			this.search_callback(module, instance);
			this.refresh_callback(module, instance);
			this.print_callback(module, instance);
			this.export_pdf_callback(module, instance);
			this.export_xls_callback(module, instance);
			this.main_toolbar_callback(module, instance);
			this.change_view(module, instance, "hide");
			this.connect_ws(module, instance);
			this.on_destroy(module, instance);
		},

		on_destroy(module, instance) {

			let moduleInstance = "module-" + module.id + "-" + instance;
			let gridInstances = this.currentGridInstances;
			let gridInstance = this.currentGridInstances[moduleInstance];
			let grid = $$("dt-" + moduleInstance);

			if (gridInstance != undefined && gridInstance.metadata != undefined && grid != undefined) {

				grid.attachEvent("onDestruct", function() {

					app.datatable.disconnect_ws(module, instance);
					delete gridInstances[moduleInstance];
				});
			}
		},

		change_view(module, instance, selection) {

			let moduleInstance = "module-" + module.id + "-" + instance;
			let gridInstance = this.currentGridInstances[moduleInstance];
			let grid = $$("dt-" + moduleInstance);

			if (gridInstance != undefined && gridInstance.metadata != undefined && grid != undefined) {

				let metadata = gridInstance.metadata;

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
		},

		search_callback(module, instance) {

			let moduleInstance = "module-" + module.id + "-" + instance;
			let grid = $$("dt-" + moduleInstance);
			let txtSearch = $$("txtSearch-" + moduleInstance);

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
		},

		main_toolbar_callback(module, instance) {

			let moduleInstance = "module-" + module.id + "-" + instance;
			let gridInstance = this.currentGridInstances[moduleInstance];
			let segInstance = $$("seg-" + moduleInstance);

			if (segInstance != undefined) {

				segInstance.attachEvent("onChange", function(newv, oldv) {
					//webix.message("Value changed from: "+oldv+" to: "+newv);

					if (newv === "seg-basic-" + moduleInstance) {
						app.datatable.change_view(module, instance, "hide");
					}
					else if (newv === "seg-details-" + moduleInstance) {
						app.datatable.change_view(module, instance, "show");
					}
				});
			}
		},

		refresh_callback(module, instance) {

			let moduleInstance = "module-" + module.id + "-" + instance;
			let gridInstance = this.currentGridInstances[moduleInstance];

			if (gridInstance != undefined) {

				let refreshButton = $$("tb_btn-refresh-" + moduleInstance);
				let grid = $$("dt-" + moduleInstance);

				if (!webix.isUndefined(grid) && !webix.isUndefined(refreshButton)) {

					webix.extend(grid, webix.ProgressBar);

					refreshButton.attachEvent("onItemClick", function(id, e) {

						grid.showProgress({
							//hide: false, type: "icon"
							hide: true, type: "bottom"
						});

						webix.ajax(appConfig.restUrl + "grids/" + gridInstance.id + "/" + gridInstance.params).then(function(data) {
							webix.delay(function() {

								grid.parse(data);
								grid.hideProgress();
							}, null, null, 1);

						});
					});
				}
			}
		},

		print_callback(module, instance) {

			let moduleInstance = "module-" + module.id + "-" + instance;
			let gridInstance = this.currentGridInstances[moduleInstance];

			if (gridInstance != undefined) {

				let printButton = $$("tb_btn-print-" + moduleInstance);
				let grid = $$("dt-" + moduleInstance);

				if (!webix.isUndefined(grid) && !webix.isUndefined(printButton)) {

					printButton.attachEvent("onItemClick", function(id, e) {

						webix.message("not implemented yet");
					});
				}
			}
		},
		
		export_pdf_callback(module, instance) {

			let moduleInstance = "module-" + module.id + "-" + instance;
			let gridInstance = this.currentGridInstances[moduleInstance];

			if (gridInstance != undefined) {

				let pdfButton = $$("tb_btn-export-pdf-" + moduleInstance);
				let grid = $$("dt-" + moduleInstance);

				if (!webix.isUndefined(grid) && !webix.isUndefined(pdfButton)) {

					webix.extend(grid, webix.ProgressBar);

					pdfButton.attachEvent("onItemClick", function(id, e) {

						grid.showProgress({
							//hide: false, type: "icon"
							hide: true, type: "bottom"
						});

						webix.delay(function() {
							
							webix.toPDF(grid,{
							     filename: i18n.t(gridInstance.metadata.label),
								 docHeader: i18n.t(gridInstance.metadata.label),
								 orientation:"landscape",
								 display: "image"
							});
							
							grid.hideProgress();
						}, null, null, 1);
					});
				}
			}
		},
		
		export_xls_callback(module, instance) {

			let moduleInstance = "module-" + module.id + "-" + instance;
			let gridInstance = this.currentGridInstances[moduleInstance];

			if (gridInstance != undefined) {

				let xlsButton = $$("tb_btn-export-xls-" + moduleInstance);
				let grid = $$("dt-" + moduleInstance);

				if (!webix.isUndefined(grid) && !webix.isUndefined(xlsButton)) {

					xlsButton.attachEvent("onItemClick", function(id, e) {

						webix.message("not implemented yet");
					});
				}
			}
		},

		connect_ws(module, instance) {

			let moduleInstance = "module-" + module.id + "-" + instance;
			let gridInstance = this.currentGridInstances[moduleInstance];

			if (gridInstance != undefined && gridInstance.metadata != undefined && gridInstance.metadata.adapter != undefined) {

				let grid = $$("dt-" + moduleInstance);
				let dataSource = gridInstance.metadata.adapter.dataSources.filter(e => e.dataSourceType.id === STOMP_DATA_SOURCE_TYPE);

				if (!webix.isUndefined(grid) && dataSource != undefined && dataSource.length > 0) {

					let url = dataSource[0].dataSourceValues[0].value;
					let topic = dataSource[0].dataSourceValues[1].value;
					let params = ".>";

					$$("tb_ws_status-" + moduleInstance).setHTML(`<div><span class="status_ws_disconnected">${i18n.t("LBL_DISCONNECTED")}</span></div>`);

					gridInstance.stomp.client = app.util.stomp.connect(url, topic + params, on_message);

					$$("tb_ws_status-" + moduleInstance).setHTML(`<div><span class="status_ws_connected">${i18n.t("LBL_CONNECTED")}</span></div>`);

					function on_message(message) {

						var data = JSON.parse(JSON.parse(message.body));

						console.log("S T O M P   O V E R   W E B S O C K E T   M E S S A G E");
						console.log(data);

						var dataGrid = app.adapter.ws.element.getList(data);

						app.datatable.update(module, instance, dataGrid.data);
					}
				}
			}
		},

		disconnect_ws(module, instance) {

			let moduleInstance = "module-" + module.id + "-" + instance;
			let gridInstance = this.currentGridInstances[moduleInstance];

			if (gridInstance != undefined && gridInstance.stomp != undefined && gridInstance.stomp.client != undefined) {

				app.util.stomp.disconnect(gridInstance.stomp.client);
				gridInstance.stomp = {};
			}
		},

		update(module, instance, data) {

			let moduleInstance = "module-" + module.id + "-" + instance;
			let gridInstance = this.currentGridInstances[moduleInstance];
			let rowsUpdated = 0;

			if (gridInstance != undefined && data != undefined && Array.isArray(data) && data.length > 0) {

				let grid = $$("dt-" + moduleInstance);

				if (!webix.isUndefined(grid)) {

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
		}
	};
