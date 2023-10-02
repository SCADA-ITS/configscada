import { util } from "../lib/util.js";

const COLUMNS = {
	MAX_COLUMN_WIDTH: 250,
	COLUMN_WIDTH_CORRECTION: 1.1
};

const FONTS = {
	FONT_FAMILY: "monospace",
	FONT_SIZE: 12,
	LETTER_SPACING: 0.2
};

const SRC_TEMPLATE = "./templates/";

export default class PrintPreview {

	static get TYPE_PRINT_DATATABLE() { return 1 }
	static get TYPE_EXPORT_DATATABLE() { return 2 }
	static get TYPE_TEMPLATE() { return 3 }
	static get TYPE_CHART() { return 4 }

	constructor(type, data, options) {

		this.type = type;
		this.data = data;
		this.options = options;
		this.fieldsSelectedIdsArr = [];

		this.uid = webix.uid();
	}

	show() {

		let self = this;
		let containerId;

		if (self.type === PrintPreview.TYPE_PRINT_DATATABLE || self.type === PrintPreview.TYPE_EXPORT_DATATABLE) {

			let body = {};

			body = {
				cols: [
					{
						id: "view-loading-" + self.uid,
						rows: [util.ui.getViewDrawLoading()]
					},
					{
						id: self.uid,
						hidden: true
					}
				]
			};

			containerId = util.ui.loadWindow((self.type === PrintPreview.TYPE_PRINT_DATATABLE) ? i18n.t("LBL_TABLE_PRINT") : i18n.t("LBL_TABLE_EXPORT"), body, 600, 750, true);

			webix.ui(drawDataTable(), $$(self.uid));
			$$("view-loading-" + self.uid).hide();

		} else if (self.type === PrintPreview.TYPE_TEMPLATE) {

			drawTemplate();

		} else if (self.type === PrintPreview.TYPE_CHART) {

			drawChart();
		}


		function drawDataTable() {

			let viewContent = {};
			let icon;
			let label;
			let labelDescription;
			let labelAcceptButton;

			if (self.type === PrintPreview.TYPE_PRINT_DATATABLE) {

				icon = "img/grid/print.png";
				label = i18n.t("LBL_SELECT_PRINT_FIELDS")
				labelDescription = i18n.t("LBL_SELECT_PRINT_FIELDS_DESCRIPTION");
				labelAcceptButton = i18n.t("LBL_TABLE_PRINT");

			} else if (self.type === PrintPreview.TYPE_EXPORT_DATATABLE) {

				icon = "img/grid/csv-export.png";
				label = i18n.t("LBL_SELECT_EXPORT_FIELDS")
				labelDescription = i18n.t("LBL_SELECT_EXPORT_FIELDS_DESCRIPTION");
				labelAcceptButton = i18n.t("LBL_EXPORT");
			}

			viewContent = {
				id: self.uid,
				css: { "box-sizing": "border-box" },
				rows: [
					getHeader(),
					{ height: 15 },
					{
						cols: [
							{ width: 15 },
							getDbList(),
							{ width: 15 }
						]
					},
					getButtons(),
					{ height: 15 }
				]
			};

			function getHeader() {

				let header = {};

				header = {
					id: "viewHeader-" + self.uid,
					cols: [
						{
							view: "label",
							label: "<img src='" + icon + "' class='grid-icon'/>",
							height: 75,
							css: "global-header-icon"
						},
						{
							view: "label",
							height: 75,
							width: 450,
							id: "label-bottom-" + self.uid,
							template: "<span>" + label + "</span> <br> <span style='font-size: 13px'>" + labelDescription + "</span>",
							css: "global-header-label"
						}
					]
				};

				return header;
			}

			function getDbList() {

				let fieldsSelected = "";
				let dbList = {};
				let dbListDataArr = [];
				let fields = self.options.metadata.gridFields.filter(e => e.visible);

				if (Array.isArray(fields) && fields.length > 0) {

					for (let i = 0; i < fields.length; i++) {

						dbListDataArr.push({
							id: fields[i].id,
							value: (fields[i].label) ? i18n.t(fields[i].label) : fields[i].sourceField
						});

						if (i === fields.length - 1) {

							fieldsSelected += fields[i].id;

						} else {

							fieldsSelected += fields[i].id + ",";
						}
					}
				}

				dbList = {
					css: "print-dblist",
					cols: [
						{ width: 10 },
						{
							rows: [
								{ height: 10 },
								{
									id: "view-dbllist-" + self.uid,
									view: "dbllist",
									css: "dbllist",
									labelLeft: i18n.t("LBL_AVAILABLE"),
									labelRight: i18n.t("LBL_SELECTED"),
									data: dbListDataArr,
									value: fieldsSelected
								},
								{ height: 10 }
							]
						},
						{ width: 10 }
					]
				};

				return dbList;
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
											$$(containerId).close();
										}
								},
								{},
								{
									view: "button",
									value: labelAcceptButton,
									width: 130,
									height: 41,
									css: "dialog-accept",
									click:
										function() {

											let fieldsSelectedIds = $$("view-dbllist-" + self.uid).getValue().split(",");

											fieldsSelectedIds.forEach(id => {

												self.fieldsSelectedIdsArr.push(parseInt(id));
											});

											$$(containerId).close();
											createTable();
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

			function createTable() {

				let tableWinId;
				let newGridFields = [];
				let dataToPrint = [];
				let columns = [];
				let columnWidthMap = new Map();

				for (let fieldSelectedId of self.fieldsSelectedIdsArr) {

					let field = self.options.metadata.gridFields.find(e => e.id === fieldSelectedId);

					if (field) {

						newGridFields.push(field);
					}
				}

				newGridFields.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));

				newGridFields.forEach(fld => {

					let dataType = {
						id: fld.gridFieldDataType.id,
						type: "DataType:" + fld.gridFieldDataType.id
					};

					columns.push({
						id: fld.sourceField,
						header: (fld.label) ? i18n.t(fld.label) : "",
						width: fld.widthPx,
						icons: fld.gridFieldIcons,
						replacementFunction: fld.replacementFunction,
						references: fld.gridFieldReferences,
						dataType: dataType
					});
				});

				self.data.forEach(dat => {

					let newData = {};

					columns.forEach(col => {

						let value = dat[col.id];

						if (col.replacementFunction) {

							if (eval("util.datatable." + col.replacementFunction)) {

								value = eval("util.datatable." + col.replacementFunction)(dat[col.id]);
							}
						}

						if (col.references != undefined && col.references.length > 0) {

							let gridFieldReference = col.references[0];
							let refField = self.options.metadata.gridFields.filter(e => e.id == gridFieldReference.refFieldId);

							if (refField != undefined) {

								value = window.em.getByField(EntityType[gridFieldReference.emId], gridFieldReference.emField, dat[refField[0].sourceField]);
								value = value[gridFieldReference.emFieldReturned];

								if (col.needsTranslation) {

									value = i18n.t(value);

									dat[field.id] = value;
								}
							}
						}

						if (col.dataType && col.dataType.type === DataType.TIMESTAMP && value != undefined && value != null && dat[col.id]) {

							value = dat[col.id].toLocaleString(i18n.getLocale(), FORMAT_NUMERIC_DATES);
						}

						if (Array.isArray(col.icons) && col.icons.length > 0) {

							let dataWithIcon = col.icons.find(e => e.value == value);

							if (dataWithIcon && dataWithIcon.icon) {

								if (self.type === PrintPreview.TYPE_PRINT_DATATABLE) {

									value = `<img class="table-field-icons" src="${dataWithIcon.icon}">`;

								} else if (self.type === PrintPreview.TYPE_EXPORT_DATATABLE) {

									value = i18n.t(dataWithIcon.labelValue);
								}
							}
						}

						newData[col.id] = (value != undefined) ? value : "-";
					});

					dataToPrint.push(newData);
				});

				columns.forEach(col => {

					let characters = 0;

					dataToPrint.forEach(d => {

						if (characters < d[col.id].length) {

							characters = d[col.id].length;
							columnWidthMap.set(col.id, d[col.id]);
						}
					});
				});

				if (self.type === PrintPreview.TYPE_PRINT_DATATABLE) {

					let tableToPrint = {};

					tableToPrint = {
						rows: [
							{
								id: "dt-print-" + self.uid,
								view: "template",
								css: "print-table-template",
								template: function() {

									let table;
									let tableWidth = 0;
									let tHeaders = "";
									let tRows = "";
									let width;
									let head;
									let tableLabel = (self.options.metadata && self.options.metadata.label) ? i18n.t(self.options.metadata.label) : "";
									let date = new Date().toLocaleString();

									head = `<div class="print-head">
											<div><img class="print-head-logo" src="./img/logo-project.svg"></div>
											<div><span class="print-head-text">${tableLabel}</span></div>
											<div><span class="print-head-text">${date}</span></div>
											</div>`;

									for (let col of columns) {

										if (columnWidthMap.has(col.id)) {

											if (columnWidthMap.get(col.id).startsWith('<img')) {

												width = col.width;

											} else {

												let textWidth;
												let dataWidth = util.ui.calculateTextWidth(columnWidthMap.get(col.id), FONTS.FONT_FAMILY, FONTS.FONT_SIZE, FONTS.LETTER_SPACING);
												let headerWidth = util.ui.calculateTextWidth(col.header, FONTS.FONT_FAMILY, FONTS.FONT_SIZE, FONTS.LETTER_SPACING);

												textWidth = (headerWidth > dataWidth) ? headerWidth : dataWidth;

												width = (textWidth > COLUMNS.MAX_COLUMN_WIDTH) ? COLUMNS.MAX_COLUMN_WIDTH : textWidth;
											}
										} else {

											let headerTextWidth = util.ui.calculateTextWidth(col.header, FONTS.FONT_FAMILY, FONTS.FONT_SIZE, FONTS.LETTER_SPACING);
											let headerWidth = (headerTextWidth > col.width) ? headerTextWidth : col.width;

											width = (headerWidth > COLUMNS.MAX_COLUMN_WIDTH) ? COLUMNS.MAX_COLUMN_WIDTH : headerWidth;
										}

										let th = `<th id="${col.id}" class="print-th-headers" style="width:${width * COLUMNS.COLUMN_WIDTH_CORRECTION}px !important; font-size: ${FONTS.FONT_SIZE}px;">${col.header}</th>`;
										tHeaders += th;
										tableWidth = tableWidth + width;
									}

									for (let j = 0; j < dataToPrint.length; j++) {

										let tDatas = "";

										for (let dat in dataToPrint[j]) {

											let td = `<td><div class="print-cell" style="font-size: ${FONTS.FONT_SIZE}px;">${dataToPrint[j][dat]}</div></td>`;
											tDatas += td;
										}

										let oddBackground = (j % 2) ? "#f5f5fa" : "";

										tRows += `<tr id="row${j}" class="print-data-row" style="background-color: ${oddBackground}">${tDatas}</tr>`;
									}

									table = `${head}
											 <table id="print-table" class="print-datatable" style="width: ${tableWidth}px">
											 <thead>
											 <tr id="print-headers">${tHeaders}</tr>										
									  	     </thead>
											 <tbody id="print-body">${tRows}</tbody>										
										     </table>`;

									return table;
								}()
							}
						]
					};

					tableWinId = util.ui.loadWindow(i18n.t("LBL_TABLE_PRINT"), tableToPrint, 1, 1, false);

					if (tableWinId) {

						webix.print($$("dt-print-" + self.uid), { mode: "landscape" });
						$$(tableWinId).close();
					}

				} else if (self.type === PrintPreview.TYPE_EXPORT_DATATABLE) {

					let tableToExport = {};

					tableToExport = {
						id: "dt-export-" + self.uid,
						view: "datatable",
						columns: columns,
						data: dataToPrint
					};

					tableWinId = util.ui.loadWindow(i18n.t("LBL_TABLE_EXPORT"), tableToExport, 1, 1, false);

					if (tableWinId) {

						webix.toCSV($$("dt-export-" + self.uid), { filname: "table_csv" });
						$$(tableWinId).close();
					}
				}
			}
		}

		function drawTemplate() {

			let templateWinId;
			let templateToPrint = {};

			templateToPrint = {
				rows: [
					{
						id: "scroll-" + self.uid,
						view: "scrollview",
						scroll: "auto",
						width: document.documentElement.clientWidth * 0.7,
						body:
						{
							rows: [
								{
									id: "template-" + self.uid,
									view: "template",
									src: SRC_TEMPLATE + self.options.srcTemplate,
									autoheight: true,
									on: {
										onAfterRender: function() {

											createReport();

											if (templateWinId) {
												
												$$(templateWinId).hide();

												webix.delay(function() {

													webix.print($$("template-" + self.uid), { mode: "landscape" });
													$$(templateWinId).close();
												}, 1500);
											}
										}
									}
								}
							]
						}
					}
				]
			};

			function createReport() {

				let numOfTable = 0;
				let HTMLTemplate = $$("template-" + self.uid).getNode();
				let keys = Object.keys(self.data);

				for (let key of keys) {

					let val = self.data[key];

					if (Array.isArray(val) && val.length > 0) {

						let tableToCreate = document.getElementById('field.' + key);

						if (tableToCreate) {

							numOfTable++;
							let tableTitle = document.createElement("caption");

							tableToCreate.appendChild(tableTitle);
							tableTitle.setAttribute("id", "title" + numOfTable);

							let HTMLtitle = HTMLTemplate.querySelector("#title" + numOfTable);

							HTMLtitle.innerHTML = val[0].caption ?? "";

							for (let j = 0; j < val.length; j++) {

								let tr = document.createElement("tr");

								tableToCreate.appendChild(tr);
								tr.setAttribute("id", "row" + j);

								for (let id in val[j]) {

									let th = document.getElementById('field.' + key + "." + id);

									if (th) {

										let cell = document.createElement("td");
										let paragraph = document.createElement("p");

										cell.appendChild(paragraph);
										tr.appendChild(cell);
										th.innerHTML = (val[j][id] && val[j][id].header) ? val[j][id].header : "";
										paragraph.innerHTML = (val[j][id] && val[j][id].value) ? val[j][id].value : "";
									}
								}
							}
						}

					} else {

						let classData = document.getElementsByClassName(key);

						if (classData.length > 0) {

							for (let dat of classData) {

								dat.innerHTML = (self.data[key]) ? self.data[key] : "-";
							}
						} else {

							let fieldData = document.getElementById('field.' + key);

							if (fieldData) {

								fieldData.innerHTML = (self.data[key]) ? self.data[key] : "-";
							}
						}
					}
				}
			}

			templateWinId = util.ui.loadWindow(i18n.t("LBL_TABLE_PRINT"), templateToPrint, 750, 1200, true);
		}

		function drawChart() {

			let chartToPrint = {};

			chartToPrint = {
				rows: [
					{
						id: "chart-print-" + self.uid,
						view: "template",
						css: "print-chart-template",
						template: function() {

							let head;
							let chartLabel = (self.options.title) ? self.options.title : "";
							let date = new Date().toLocaleString();

							head = `<div class="print-head" style="width: 99%;">
											<div><img class="print-head-logo" src="./img/logo-project.svg"></div>
											<div><span class="print-head-text">${chartLabel}</span></div>
											<div><span class="print-head-text">${date}</span></div>
											</div>`;

							return `${head}
									<img style="width:99%;" src="${self.options.canvas}">`;
						}()
					}
				]
			};

			let chartWinId = util.ui.loadWindow(i18n.t("LBL_CHART_PRINT"), chartToPrint, 750, 1200, false);

			if (chartWinId) {

				$$(chartWinId).hide();

				webix.delay(function() {

					webix.print($$("chart-print-" + self.uid), { mode: "landscape" });
					$$(chartWinId).close();
				}, 500);
			}
		}
	}
}