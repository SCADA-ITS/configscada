import { util } from "../lib/util.js";
import OptionDialogBox from "./optionDialogBox.js";

export default class PrintPreview {

	static get TYPE_DATATABLE() { return 1 }
	static get TYPE_TEMPLATE() { return 2 }

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

		if (self.type === PrintPreview.TYPE_DATATABLE) {

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

			containerId = util.ui.loadWindow(i18n.t("LBL_TABLE_PRINT"), body, 600, 750, true);

			webix.ui(drawDataTable(), $$(self.uid));
			$$("view-loading-" + self.uid).hide();

		} else if (self.type === PrintPreview.TYPE_TEMPLATE) {

			drawTemplate();
		}


		function drawDataTable() {

			let viewContent = {};

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
							label: "<img src='img/grid/print.png' class='grid-icon'/>",
							height: 75,
							css: "global-header-icon"
						},
						{
							view: "label",
							height: 75,
							width: 450,
							id: "label-bottom-" + self.uid,
							template: "<span>" + i18n.t("LBL_SELECT_FIELDS") + "</span> <br> <span style='font-size: 13px'>" + i18n.t("LBL_SELECT_FIELDS_DESCRIPTION") + "</span>",
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
									cols: [
										{ width: 15 },
										{
											view: "label",
											align: "center",
											label: i18n.t("LBL_SELECT_FIELDS_DBLIST_DESCRIPTION") + " " + i18n.t(self.options.metadata.label)
										},
										{ width: 15 }
									]
								},
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
									value: i18n.t("LBL_ACCEPT"),
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

				let newGridFields = [];
				let dataToPrint = [];
				let columns = [];

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
						header: (fld.label) ? i18n.t(fld.label) : fld.sourceField,
						width: fld.widthPx,
						icons: fld.gridFieldIcons,
						replacementFunction: fld.replacementFunction,
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

						} else if (col.dataType && col.dataType.type === DataType.TIMESTAMP && value != undefined && value != null) {

							value = dat[col.id].toLocaleString(i18n.getLocale(), FORMAT_NUMERIC_DATES);
						}

						if (Array.isArray(col.icons) && col.icons.length > 0) {

							let dataWithIcon = col.icons.find(e => e.value == value);

							if (dataWithIcon) {

								if (dataWithIcon.icon) {

									value = `<img class="table-field-icons" src="${dataWithIcon.icon}">`;//i18n.t(dataWithIcon.labelValue);									
								}
							}
						}

						newData[col.id] = (value != undefined) ? value : "-";
					});

					dataToPrint.push(newData);
				});

				let tableToPrint = {
					rows: [
						{
							id: "toolbar-print-" + self.uid,
							view: "toolbar",
							elements: [
								{
									id: "button-print-" + self.uid,
									view: "button",
									type: "icon",
									icon: "mdi mdi-printer",
									css: "grid-button-text",
									width: 120,
									on: {
										onItemClick: function() {

											const FORMAT = {
												PDF: 1,
												XLS: 2
											};

											let options = [
												{
													id: FORMAT.PDF,
													label: i18n.t("LBL_TABLE_EXPORT_PDF"),
													icon: "mdi mdi-file-pdf-box",
													description: i18n.t("LBL_TABLE_EXPORT_PDF_DESCRIPTION"),
												},
												{
													id: FORMAT.XLS,
													label: i18n.t("LBL_TABLE_EXPORT_XLS"),
													icon: "mdi mdi-file-excel",
													description: i18n.t("LBL_TABLE_EXPORT_XLS_DESCRIPTION"),
												}
											];

											let optionDialogBox = new OptionDialogBox(i18n.t("LBL_SELECT_EXPORT_FORMAT"), options, selectOption, OptionDialogBox.MODE_TOGGLE);
											optionDialogBox.show();

											function selectOption(sel, callbackCloseDialog) {

												if (sel == FORMAT.PDF) {

													webix.print($$("dt-print-" + self.uid), { mode: "landscape" });

												} else if (sel == FORMAT.XLS) {

													let downloadLink;
													let dataType = 'application/vnd.ms-excel';
													let tableSelect = $$("dt-print-" + self.uid).getNode().querySelector('#print-table');
													let tableHTML = tableSelect.outerHTML.replace(/ /g, '%20');

													downloadLink = document.createElement("a");

													document.body.appendChild(downloadLink);

													if (navigator.msSaveOrOpenBlob) {

														let blob = new Blob(['\ufeff', tableHTML], {
															type: dataType
														});

														navigator.msSaveOrOpenBlob(blob, filename);

													} else {

														downloadLink.href = 'data:' + dataType + ', ' + tableHTML;
														downloadLink.download = 'excel_data.xls';
														downloadLink.click();
													}
												}

												callbackCloseDialog(true);
											}
										}
									}
								}
							]
						},
						{
							id: "scroll-" + self.uid,
							view: "scrollview",
							scroll: "auto",
							height: 650,
							width: document.documentElement.clientWidth * 0.7,
							body:
							{
								id: "dt-print-" + self.uid,
								view: "template",
								css: "print-global-template",
								template: function() {

									let table;
									let tableWidth = 0;
									let tHeaders = "";
									let tRows = "";

									for (let col of columns) {

										let th = `<th id="${col.id}" class="print-cell print-strong-border" style="width:${col.width}px !important">${col.header}</th>`;
										tHeaders += th;
										tableWidth = tableWidth + col.width;
									}

									for (let j = 0; j < dataToPrint.length; j++) {

										let tDatas = "";

										for (let dat in dataToPrint[j]) {

											let td = `<td class="print-cell"><p class="print-cell-text">${dataToPrint[j][dat]}</p></td>`;
											tDatas += td;
										}

										tRows += `<tr id="row${j}" class="print-row">${tDatas}</tr>`;
									}

									table = `<table id="print-table" class="print-strong-border" style="width:${tableWidth}px">
											<thead>
											<tr id="print-headers">${tHeaders}</tr>										
										    </thead>
											<tbody id="print-body">${tRows}</tbody>										
										</table>`;

									return table;
								}()
							}
						}
					]
				};

				util.ui.loadWindow(i18n.t("LBL_TABLE_PRINT"), tableToPrint, 750, document.documentElement.clientWidth * 0.85, true);
			}
		}

		function drawTemplate() {

			let templateToPrint = {
				rows: [
					{
						id: "toolbar-print-" + self.uid,
						view: "toolbar",
						elements: [
							{
								id: "button-print-" + self.uid,
								view: "button",
								type: "icon",
								icon: "mdi mdi-printer",
								css: "grid-button-text",
								width: 120,
								on: {
									onItemClick: function() {

										webix.print($$("template-" + self.uid), { mode: "landscape" });
									}
								}
							}
						]
					},
					{
						id: "template-" + self.uid,
						view: "template",
						css: "print-global-template",
						src: self.options.templateSrc,
						on: {
							onAfterRender: function() {

								createReport();
							}
						}
					}
				]
			};

			function createReport() {

				let HTMLTemplate = $$("template-" + self.uid).getNode();
				let keys = Object.keys(self.data);

				for (let key of keys) {

					let val = self.data[key];

					if (Array.isArray(val) && val.length > 0) {

						let HTMLtable = HTMLTemplate.querySelector("#print-table");

						if (HTMLtable) {

							let tableHeaders = Object.keys(val[0]);
							let tableHeader = document.createElement("tr");

							HTMLtable.appendChild(tableHeader);
							tableHeader.setAttribute("id", "headers");

							let HTMLheaders = HTMLTemplate.querySelector("#headers");

							for (let header of tableHeaders) {

								let th = document.createElement("th");

								HTMLheaders.appendChild(th);
								th.innerHTML = header;
								th.classList.add("print-cell", "print-strong-border");
								th.setAttribute("id", header);
								th.setAttribute("width", "200px");
							}

							for (let j = 0; j < val.length; j++) {

								let tr = document.createElement("tr");

								HTMLtable.appendChild(tr);
								tr.setAttribute("id", "row" + j);

								for (let header in val[j]) {

									let cell = document.createElement("td");
									let paragraph = document.createElement("p");

									cell.appendChild(paragraph);
									tr.appendChild(cell);
									paragraph.innerHTML = val[j][header];
									cell.className = "print-cell";
									paragraph.className = "print-cell-text";
								}
							}
						}

					} else {

						let fieldData = document.getElementById('field.' + key);
						fieldData.innerHTML = self.data[key];
					}
				}
			}

			util.ui.loadWindow(i18n.t("LBL_TABLE_PRINT"), templateToPrint, 750, 1200, true);
		}
	}
}