import { util } from "../lib/util.js";
import { adapter } from "../lib/adapter.js";

const ALL_PARAM_TYPES = "ParamType:2";
const DEFAULT_WIDTH = 400;
const TOPIC_ELEMENTS = "/topic/element.>";
const AJAX_PATH = "elements/";

export default class EquipmentMeasures {

	constructor(elements, params, options) {

		this.elements = elements;
		this.params = (params === null) ? ALL_PARAM_TYPES : params;
		this.paramsArray = (Array.isArray(this.params) && this.params.length > 0) ? true : false;
		this.options = options;

		this.combo = ((options != null) && (options.combo === false)) ? false : true;

		this.uid = webix.uid();

		this.elementTypeId = undefined;
		this.element = undefined;
		this.measuresToUpdate = undefined;
		this.idStompSubscriptor = undefined;
		this.multiviewRequired = undefined;
		this.labelWidth = ((options != null) && (options.width)) ? options.width * 0.66 : DEFAULT_WIDTH * 0.66;
		this.valueWidth = ((options != null) && (options.width)) ? options.width - this.labelWidth : DEFAULT_WIDTH - this.labelWidth;
	}


	show() {

		let self = this;
		let equipMeasures = {};

		equipMeasures = {
			id: "equipMeasures-" + self.uid,
			cols: [
				{
					id: "view-loading-" + self.uid,
					rows: [util.ui.getViewDrawLoading()]
				},
				{
					id: "view-content-" + self.uid,
					hidden: true
				}
			],
			on: {
				onDestruct: (obj) => {

					disConnectWS();
				}
			}
		};

		loadData();

		function loadData() {

			let detailedElements = [];
			let elementsTypesArray = [];
			let elementsTypes = new Set();
			let elementsValuesMap = new Map();
			let counter = 0;

			self.elements.forEach(el => {

				webix.ajax().get(appConfig.restUrl + AJAX_PATH + el).then(function(data) {

					if (data != null) {

						let elementValues;
						let allParams = true;
						let element = data.json();

						elementsTypes.add(element.elementType.id);

						if (self.paramsArray) {

							for (let param of self.params) {

								let paramToFind = element.elementValues.find(typeParam => typeParam.elementTypeParam.id === param);

								if (paramToFind) {

									allParams = false;
								}
							}
						}

						if (allParams) {

							elementValues = element.elementValues.filter(typeParam => typeParam.elementTypeParam.paramType.id === ALL_PARAM_TYPES);

							if (Array.isArray(elementValues) && elementValues.length > 0) {

								detailedElements.push(
									{
										alias: element.alias,
										id: element.id,
										elementType: element.elementType.id,
										elementValues: elementValues,
									});

								for (let elementValue of elementValues) {

									if (elementValue.value) {

										elementsValuesMap.set(elementValue.id, elementValue.value);
									}
								}
							}

						} else {

							let elementValuesArray = [];

							for (let param of self.params) {

								elementValues = element.elementValues.find(typeParam => typeParam.elementTypeParam.id === param);

								if (elementValues) {

									elementValuesArray.push(elementValues);

									let sameElement = detailedElements.find(e => e.id === element.id);

									if (!sameElement) {

										detailedElements.push(
											{
												alias: element.alias,
												id: element.id,
												elementType: element.elementType.id,
												elementValues: elementValuesArray,
											});

										for (let elementValue of elementValuesArray) {

											if (elementValue.value) {

												elementsValuesMap.set(elementValue.id, elementValue.value);
											}
										}
									}
								}
							}
						}
					}

					counter++;

					if (counter === self.elements.length) {

						for (let elTypeId of elementsTypes) {

							let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", elTypeId);

							if (elementType) {

								elementsTypesArray.push(elementType);
							}
						}

						elementsTypesArray.sort((a, b) => (i18n.t(a.labelAlias) > i18n.t(b.labelAlias)) ? 1 : ((i18n.t(b.labelAlias) > i18n.t(a.labelAlias)) ? -1 : 0));

						webix.ui(drawContent(), $$("view-content-" + self.uid));
						$$("view-loading-" + self.uid).hide();

						connectWS();
					}
				});
			});


			function drawContent() {

				let viewContent;
				let tablesPages = 1;

				viewContent = {
					id: "view-content-" + self.uid,
					css: { "border": "1px solid #DADEE0 !important", "box-sizing": "border-box" },
					rows: [
						{ height: 3 },
						getContent(),
						{ height: 3 },
						getPager()
					]
				};

				function getContent() {

					let containerWidth = $$("equipMeasures-" + self.uid).getParentView().$width;
					let tablesWidth = ((self.options != null) && (self.options.width)) ? self.options.width : DEFAULT_WIDTH;
					let tablesRows = ((self.options != null) && (self.options.rows)) ? self.options.rows : 1;
					let tablesForRow = Math.floor(containerWidth / tablesWidth);
					let spacesForTablesAvailableForView = tablesRows * tablesForRow;
					let spacers = (containerWidth - (tablesForRow * tablesWidth)) / tablesForRow - 1;
					let data = [];
					let temporallyTablesArray = [];

					elementsTypesArray.forEach(element => {

						let selectOptions = [];
						data = [];

						let elementsSameTypeArray = detailedElements.filter(el => el.elementType === element.id);

						if (elementsSameTypeArray && elementsSameTypeArray.length > 0) {

							elementsSameTypeArray.sort((a, b) => (i18n.t(a.alias) > i18n.t(b.alias)) ? 1 : ((i18n.t(b.alias) > i18n.t(a.alias)) ? -1 : 0));

							for (let el of elementsSameTypeArray) {

								let p = el.elementValues;
								p.sort((a, b) => (i18n.t(a.elementTypeParam.labelAlias) > i18n.t(b.elementTypeParam.labelAlias)) ? 1 : ((i18n.t(b.elementTypeParam.labelAlias) > i18n.t(a.elementTypeParam.labelAlias)) ? -1 : 0));
							}

							if (self.combo) {

								for (let el of elementsSameTypeArray) {

									selectOptions.push({
										id: el.id,
										value: el.alias
									});
								}

								drawInitMeasures(elementsSameTypeArray[0].elementValues);

							} else {

								for (let el of elementsSameTypeArray) {

									data.push(
										{ label: el.alias, type: "label", id: el.alias }
									);

									drawInitMeasures(el.elementValues);
								}
							}

							function drawInitMeasures(propertiesArray) {

								let labelEllipsis = `style='display: inline-block; max-width: ${self.labelWidth * 0.95}px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis'`;
								let valueEllipsis = `style='display: inline-block; max-width: ${self.valueWidth * 0.75}px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis'`;

								for (let property of propertiesArray) {

									let value;
									let htmlId = (self.combo) ? property.elementTypeParam.alias : property.id.split(":").join("");
									let label = `<div id="${htmlId}-label" ${labelEllipsis}>${i18n.t(property.elementTypeParam.labelAlias)}</div>`;

									if (property.value) {

										let val = property.value;
										let icon = undefined;
										let unit = (property.elementTypeParam.labelUnit) ? i18n.t(property.elementTypeParam.labelUnit) : "";

										if (Array.isArray(property.elementTypeParam.enum) && property.elementTypeParam.enum.length > 0) {

											let enumListValues = [];
											let enumList = property.elementTypeParam.enum;

											for (let e of enumList) {

												for (let enumTypeValue of e.voEnumTypeValues) {

													enumListValues.push(enumTypeValue);
												}
											}

											let enumValue = enumListValues.find(e => e.key == val);

											if (enumValue) {

												if (enumValue.image) {

													val = `<img style="width:40px" src="${enumValue.image}">`;

												} else {

													val = (enumValue.translate) ? i18n.t(enumValue.value) : enumValue.value;
												}
											}
										}

										if (property.elementValueState && property.elementValueState.id) {

											icon = property.elementValueState.id.replace(":", "_") + ".png";
										}

										let iconHTML = (icon != undefined) ? `<image src="img/state/${icon}"/>` : "";

										value = `<div id="${htmlId}" ${valueEllipsis} class="equipment-measures-values">${iconHTML} ${val} ${unit}</div>`;

									} else {

										value = `<div id="${htmlId}" ${valueEllipsis} class="equipment-measures-values"> </div>`;
									}

									data.push(
										{ label: label, type: "text", id: htmlId, value: value, realLabel: i18n.t(property.elementTypeParam.labelAlias) }
									);
								}
							}

							selectOptions.sort((a, b) => (i18n.t(a.value) > i18n.t(b.value)) ? 1 : ((i18n.t(b.value) > i18n.t(a.value)) ? -1 : 0));

							temporallyTablesArray.push(
								{
									cols: [
										{ width: spacers / 2 },
										{
											width: ((self.options != null) && (self.options.width)) ? self.options.width : DEFAULT_WIDTH,
											rows: [
												{ height: 5 },
												{
													view: "template",
													height: 50,
													template: `<div> 
																<image style="width:32px; height:32px; float: left; padding: 8px" src="img/${element.elementTypePhoto}" onerror="this.onerror=null;this.src='img/not_found_50.png';">
																<div><span style="display:inline-block; padding:15px 8px; font-size: 15px; letter-spacing: 2px; font-weight:500" >${i18n.t(element.labelAlias).toUpperCase()}</span></div>
															</div>`
												},
												{
													id: "tableContent-select-" + element.id + "-" + self.uid,
													height: 28,
													view: "richselect",
													css: "equipment-measures-combo",
													value: selectOptions[0],
													options: selectOptions,
													hidden: (self.combo) ? false : true,
													on: {
														onChange(newv, oldv) {

															let propertiesId = this.config.id.replace("tableContent-select-", "sets-");
															let newElementSelected = detailedElements.find(e => e.id === newv);
															let oldElement = detailedElements.find(e => e.id === oldv);

															if (newElementSelected) {

																for (let newValue of newElementSelected.elementValues) {

																	let valueToUpdate = elementsValuesMap.has(newValue.id);

																	if (valueToUpdate) {

																		newValue.value = elementsValuesMap.get(newValue.id);
																	}
																}

																self.measuresToUpdate = newElementSelected;
																updateMeasures(propertiesId, false, oldElement);
															}
														}
													}
												},
												{
													view: "property",
													id: "sets-" + element.id + "-" + self.uid,
													scroll: "y",
													editable: false,
													nameWidth: ((self.options != null) && (self.options.width)) ? self.options.width * 0.66 : DEFAULT_WIDTH * 0.66,
													tooltip: function(obj) {

														let realValue = "";
														let textValueWidth;
														let textLabelWidth;
														let nodeValue = $$("sets-" + element.id + "-" + self.uid).getNode().querySelector("#" + obj.id);
														let nodeLabel = $$("sets-" + element.id + "-" + self.uid).getNode().querySelector("#" + obj.id + "-label");

														if (nodeValue != null) {

															let elementStyle = window.getComputedStyle(nodeValue);
															let fontSize = elementStyle.getPropertyValue('font-size');
															let fontFamily = elementStyle.getPropertyValue('font-family');
															let letterSpacing = elementStyle.getPropertyValue('letter-spacing');

															realValue = nodeValue.innerHTML;
															textValueWidth = util.ui.calculateTextWidth(realValue, fontFamily, fontSize, letterSpacing);
														}

														if (nodeLabel != null) {

															let elementStyle = window.getComputedStyle(nodeLabel);
															let fontSize = elementStyle.getPropertyValue('font-size');
															let fontFamily = elementStyle.getPropertyValue('font-family');
															let letterSpacing = elementStyle.getPropertyValue('letter-spacing');

															textLabelWidth = util.ui.calculateTextWidth(obj.realLabel, fontFamily, fontSize, letterSpacing);
														}

														if (textLabelWidth > (self.labelWidth * 0.85) || textValueWidth > (self.valueWidth * 0.75)) {

															return `${obj.realLabel}:<br>${realValue}`;

														} else {

															return "";
														}
													},
													elements: data
												},
												{ height: 5 }
											]
										},
										{ width: spacers / 2 }
									]
								}
							);
						}
					});

					let content = {};

					self.multiviewRequired = (spacesForTablesAvailableForView >= temporallyTablesArray.length) ? false : true;

					if (self.multiviewRequired) {

						content = {
							id: "multiview-" + self.uid,
							view: "multiview",
							animate: true,
							borderless: true,
							keepViews: true,
							cells: []
						};

					} else {

						content = {
							id: "multiview-" + self.uid,
							rows: []
						};
					}

					calculateRows();

					function calculateRows() {

						let tablesCounter = 0;
						let tablesPending = temporallyTablesArray.length;
						let cellsArray = [];
						let tables = { cols: [] };

						if (tablesRows === 1) {

							for (let table of temporallyTablesArray) {

								if (tablesCounter < tablesForRow) {

									tables.cols.push(table);
									tablesCounter++;

									if (--tablesPending === 0) {

										tables.cols.push({});
										let tab = webix.copy(tables);
										tab._id = "multiview-tables-" + tablesPages + "-" + self.uid;
										cellsArray.push(tab);
									}

								} else if (tablesCounter === tablesForRow) {

									let tab = webix.copy(tables);
									tab._id = "multiview-tables-" + tablesPages + "-" + self.uid;
									cellsArray.push(tab);
									tables.cols = [];
									tables.cols.push(table);
									tablesCounter = 1;
									tablesPages++;

									if (--tablesPending === 0) {

										tables.cols.push({});
										let tab = webix.copy(tables);
										tab._id = "multiview-tables-" + tablesPages + "-" + self.uid;
										cellsArray.push(tab);
									}
								}
							}

						} else {

							let view = { rows: [{ cols: [{}] }] };
							let row = 0;
							let spacesAvailable = spacesForTablesAvailableForView;

							for (let table of temporallyTablesArray) {

								if (tablesCounter < tablesForRow) {

									view.rows[row].cols.push(table);
									tablesCounter++;

									if (--tablesPending === 0) {

										view.rows[row].cols.push({});
										let tab = webix.copy(view);
										tab._id = "multiview-tables-" + tablesPages + "-" + self.uid;
										cellsArray.push(tab);
									}

								} else if (tablesCounter === tablesForRow) {

									spacesAvailable = spacesAvailable - tablesCounter;

									if (spacesAvailable === 0) {

										let tab = webix.copy(view);
										tab._id = "multiview-tables-" + tablesPages + "-" + self.uid;
										cellsArray.push(tab);
										tablesPages++;
										row = -1;
										view = { rows: [] };
										spacesAvailable = spacesForTablesAvailableForView;
									}

									row++;
									view.rows.push({ cols: [] });
									view.rows[row].cols.push(table);
									tablesCounter = 1;

									if (--tablesPending === 0) {

										view.rows[row].cols.push({});
										let tab = webix.copy(view);
										tab._id = "multiview-tables-" + tablesPages + "-" + self.uid;
										cellsArray.push(tab);
									}
								}
							}
						}

						let layout = (self.multiviewRequired) ? "cells" : "rows";

						for (let cell of cellsArray) {

							let cellContent = {
								id: cell._id,
								cols: [
									{},
									cell,
									{}
								]
							}

							content[layout].push(cellContent);
						}
					}

					return content;
				}


				function getPager() {

					let pager = { height: 1 };

					if (self.multiviewRequired) {

						pager = {
							view: "toolbar",
							id: "toolbar-" + self.uid,
							css: "equipment-measures-pager",
							align: "center",
							cols: []
						};

						for (let i = 0; i < tablesPages; i++) {

							pager.cols.push({
								view: "button", id: i + 1, value: i + 1, width: 50, click: clickButton
							});
						}

						pager.cols.unshift({
							view: "button", id: "first", value: "<<", width: 50, click: clickButton
						});

						pager.cols.push({
							view: "button", id: "last", value: ">>", width: 50, click: clickButton
						});

						function clickButton(obj) {

							const FIRST_PAGE = "first";
							const LAST_PAGE = "last";

							if (obj != FIRST_PAGE && obj != LAST_PAGE) {

								$$("multiview-tables-" + obj + "-" + self.uid).show();

							} else {

								let page = (obj === FIRST_PAGE) ? 1 : tablesPages;

								$$("multiview-tables-" + page + "-" + self.uid).show();
							}
						}
					}

					return pager;
				}

				return viewContent;
			}


			function updateMeasures(propertiesId, automaticUpdate, oldElement) {

				let newValues = {};
				let newMeasures = self.measuresToUpdate.elementValues;
				let elementId = self.measuresToUpdate.id;
				let tableId = $$("tableContent-select-" + self.measuresToUpdate.elementType + "-" + self.uid).config.id;
				let changeElement = (oldElement != null);
				let newMeasure, actualValue, actualElementId;
				let changeValue = false;

				for (newMeasure of newMeasures) {

					if (newMeasure.value) {

						actualValue = elementsValuesMap.get(newMeasure.id);

						if (actualValue != newMeasure.value) {

							elementsValuesMap.set(newMeasure.id, newMeasure.value);
							changeValue = true;
						}

						if (!automaticUpdate) {

							drawMeasures();

						} else {

							if (self.combo) {

								actualElementId = $$(tableId).getValue();

								if ((actualElementId === elementId) && changeValue) {

									drawMeasures();
								}

							} else {

								if (changeValue) {

									drawMeasures();
								}
							}
						}
					}
				}

				if ((Object.keys(newValues).length != 0) || changeElement) {

					$$(propertiesId).setValues(newValues, automaticUpdate);
				}

				self.measuresToUpdate = undefined;

				function drawMeasures() {

					let unit, htmlId;
					let value = elementsValuesMap.get(newMeasure.id);
					let icon = undefined;
					let values = {};

					if (Array.isArray(newMeasure.elementTypeParam.enum) && newMeasure.elementTypeParam.enum.length > 0) {

						let enumListValues = [];
						let enumList = newMeasure.elementTypeParam.enum;

						for (let e of enumList) {

							for (let enumTypeValue of e.voEnumTypeValues) {

								enumListValues.push(enumTypeValue);
							}
						}

						let enumValue = enumListValues.find(e => e.key == newMeasure.value);

						if (enumValue) {

							if (enumValue.image) {

								value = `<img style="width:40px" src="${enumValue.image}">`;

							} else {

								value = (enumValue.translate) ? i18n.t(enumValue.value) : enumValue.value;
							}
						}
					}

					if (self.combo) {

						htmlId = newMeasure.elementTypeParam.alias;

					} else {

						htmlId = newMeasure.id.split(":").join("");
					}

					unit = (newMeasure.elementTypeParam.labelUnit) ? i18n.t(newMeasure.elementTypeParam.labelUnit) : "";

					if (newMeasure.elementValueState && newMeasure.elementValueState.id) {

						icon = newMeasure.elementValueState.id.replace(":", "_") + ".png";
					}

					let iconHTML = (icon != undefined) ? `<image src="img/state/${icon}"/>` : "";
					let valueHTML = `<div id="${htmlId}" style="display: inline-block; max-width: ${self.valueWidth * 0.75}px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis" class="equipment-measures-values">${iconHTML} ${value} ${unit}</div>`;

					values = {
						key: htmlId,
						value: valueHTML
					}

					newValues[values["key"]] = values["value"];
				}
			}


			function connectWS() {

				let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

				if (stompSubscriptorService) {

					let fields = undefined;

					if (Array.isArray(self.elements) && self.elements.length > 0) {

						fields = [];

						fields.push({
							target: "id",
							values: []
						});

						self.elements.forEach(element => fields[0].values.push(element));

						self.idStompSubscriptor = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(TOPIC_ELEMENTS), onMessage, fields);
					}
				}

				function onMessage(data) {

					let elementTypeParams = [];
					let updateElement;

					if (data && data.id) {

						updateElement = detailedElements.find(el => el.id === data.id);

						if (updateElement != undefined) {

							let elementTypeId = updateElement.elementType;

							if (Array.isArray(updateElement.elementValues) && updateElement.elementValues.length > 0) {

								elementTypeParams = updateElement.elementValues.map(elementValue => elementValue.elementTypeParam);
							}

							let element = adapter.ws.element.get(data, elementTypeParams);

							if (element) {

								if ($$("tableContent-select-" + elementTypeId + "-" + self.uid) != undefined) {

									let tableId = $$("tableContent-select-" + elementTypeId + "-" + self.uid).config.id;
									let propertiesId = tableId.replace("tableContent-select-", "sets-");

									element.elementType = elementTypeId;
									self.measuresToUpdate = element;
									updateMeasures(propertiesId, true, null);
								}
							}
						}
					}
				}
			}
		}


		function disConnectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				if (self.idStompSubscriptor) {

					stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
				}

			}
		}

		return equipMeasures;
	}
}