import { util } from "../../lib/util.js";
import HistoricalChartModule from "./historicalChartModule.js";
import ListView from "../../component/listview.js";

const DEFAULT_INIT_TIMES = {
	RANGE: 24,
	PERIOD: 60,
	NUM_OF_MEASURES: 500
};

const LINES_COLORS = ["#87CEFA88", "#3b607a88", "#00800088", "#FFFF0088", "#fc08fc", "#FFA500", "#7d4c4c", "#C0C0C0", "#9fc944", "#438c73", "#ebdcab", "#48474d", "#48dbaa", "#c7a5ab", "#a4bf8a"];

export default class HistoricalChartGeneric {

	constructor(args, mode, parent) {

		this.args = args;
		this.mode = mode;
		this.parent = parent;
		this.isCharging = false;
		this.elementValues = undefined;
		this.xAxis = [];
		this.elementTypeParamsIdsSet = new Set();
		this.valuesMap = new Map();
		this.list = undefined;
		this.dateIni = this.args.dates.dateIni;
		this.dateEnd = this.args.dates.dateEnd;

		this.uid = parent.uid;

		if (this.mode === HistoricalChartModule.MODE_ELEMENT_VALUES.id) {

			this.chartType = HistoricalChartModule.MODE_ELEMENT_VALUES.chartType;
			this.label = i18n.t("LBL_HISTORICAL_CHART_OF_ELEMENT_VALUES");

		} else if (this.mode === HistoricalChartModule.MODE_ALARMS.id) {

			this.chartType = HistoricalChartModule.MODE_ALARMS.chartType;
			this.label = i18n.t("LBL_HISTORICAL_CHART_OF_ALARMS");

		} else if (this.mode === HistoricalChartModule.MODE_STATES.id) {

			this.chartType = HistoricalChartModule.MODE_STATES.chartType;
			this.label = i18n.t("LBL_HISTORICAL_CHART_OF_STATES");
		}

		if (!this.parent.isEmbed) {

			for (let elId of this.args.elementsIds) {

				let element = window.em.getByField(EntityType.ELEMENT, "id", elId);

				this.label += " - " + element.alias;
			}
		}
	}

	getData(selectedRangeInHours) {

		let self = this;
		let body;
		let dataSet = [];
		let dataSeries = [];
		let initRange;
		let initPeriod;
		let initCountdown;
		let listElements = [];
		let legendValues = [];

		body = {
			dateEnd: self.dateEnd,
			dateIni: self.dateIni,
			elementIds: self.args.elementsIds
		};

		webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.args.url, JSON.stringify(body)).then(function(response) {

			if (response != null) {

				calculateTimeRanges(selectedRangeInHours);
				self.isCharging = false;

				if (self.mode === HistoricalChartModule.MODE_ELEMENT_VALUES.id) {

					self.elementValues = response.json();
					let element = window.em.getByField(EntityType.ELEMENT, "id", self.args.elementsIds[0]);
					let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);

					if (Array.isArray(self.elementValues) && self.elementValues.length > 0) {

						self.elementValues.forEach(ev => {
							self.elementTypeParamsIdsSet.add(ev.elementTypeParam.id);
						});
					}

					if (elementType.elementTypeParams) {

						self.elementTypeParamsIdsSet.forEach(elementTypeParamId => {

							let eTypeParam = elementType.elementTypeParams.find(param => param.id === elementTypeParamId);

							if (eTypeParam) {

								let elementValuesArr = self.elementValues.filter(ev => ev.elementTypeParam.id === eTypeParam.id);

								if (Array.isArray(elementValuesArr) && elementValuesArr.length > 0) {

									let valuesArr = [];

									for (let ev of elementValuesArr) {

										valuesArr.push({
											epoch: ev.createdAt,
											time: new Date(ev.createdAt),
											value: ev.value
										});
									}

									self.valuesMap.set(eTypeParam.id, { valuesArr });
								}
							}
						});

						elementType.elementTypeParams.forEach(eTypeParam => {

							if (eTypeParam.paramType.id === self.parent.config.paramTypeMeasure) {

								listElements.push({
									id: eTypeParam.id,
									alias: i18n.t(eTypeParam.labelAlias),
									description: i18n.t(eTypeParam.labelAlias)
								});
							}
						});
					}

				} else if (self.mode === HistoricalChartModule.MODE_ALARMS.id) {

					self.alarms = response.json();

					self.args.elementsIds.forEach(id => {

						let element = window.em.getByField(EntityType.ELEMENT, "id", id);

						listElements.push({
							id: element.id,
							alias: element.alias,
							description: element.description
						});
					});

				} else if (self.mode === HistoricalChartModule.MODE_STATES.id) {

					self.states = response.json();
				}

				if (!$$("view-main-" + self.uid)) {

					drawComponent();

					if (self.mode === HistoricalChartModule.MODE_STATES.id) {

						getRangeData(self.args.elementsIds);
					}

				} else {

					let id = (self.mode === HistoricalChartModule.MODE_STATES.id) ? self.args.elementsIds : self.list.getSelectedItems()[0].id;

					getRangeData(id);
				}
			}
		});

		function calculateTimeRanges(selectedRangeInHours) {

			self.xAxis = [];
			let xAxisAux = [];
			let xAxisToShow = [];
			let rangeInHours;
			let defaultInitPeriodInMinutes = (self.parent.config.defaultInitPeriodInMinutes) ? self.parent.config.defaultInitPeriodInMinutes : DEFAULT_INIT_TIMES.PERIOD;

			if (selectedRangeInHours) {

				rangeInHours = selectedRangeInHours;

			} else {

				rangeInHours = (self.parent.config.defaultInitRangeInHours) ? self.parent.config.defaultInitRangeInHours : DEFAULT_INIT_TIMES.RANGE;
			}


			if (rangeInHours < 1) {

				initRange = "minute";
				initCountdown = rangeInHours * -60;

			} else if (rangeInHours >= 1 && rangeInHours < 24) {

				initRange = "hour";
				initCountdown = -rangeInHours;

			} else {

				initRange = "day";
				initCountdown = -(rangeInHours / 24);
			}

			if (self.mode === HistoricalChartModule.MODE_ELEMENT_VALUES.id) {

				initPeriod = (rangeInHours * 60) / defaultInitPeriodInMinutes;

			} else {

				initPeriod = DEFAULT_INIT_TIMES.NUM_OF_MEASURES;
				defaultInitPeriodInMinutes = (rangeInHours * 60) / initPeriod;
			}

			for (let i = 0; i <= initPeriod; i++) {

				let deleteTime = ((defaultInitPeriodInMinutes * 60000) * i);
				let newPeriod = self.dateEnd - deleteTime;
				let hour = new Date(newPeriod).getHours().toString();
				let minute = new Date(newPeriod).getMinutes().toString();
				let day = new Date(newPeriod).getDate().toString();
				let month = (new Date(newPeriod).getMonth() + 1).toString();

				let xValue = `<div>
						<span>${day.padStart(2, "0") + "/" + month.padStart(2, "0")}</span><br>
						<span>${hour.padStart(2, "0") + ":" + minute.padStart(2, "0")}</span>
						</div>`

				xAxisAux.push({
					initEpoch: newPeriod,
					endEpoch: newPeriod + ((defaultInitPeriodInMinutes * 60000) - 1000),
					initTime: new Date(newPeriod),
					endTime: new Date(newPeriod + ((defaultInitPeriodInMinutes * 60000) - 1000)),
					xValue: xValue
				});
			}

			xAxisAux.reverse();

			if (self.mode === HistoricalChartModule.MODE_ELEMENT_VALUES.id) {

				let diferencesArr = [];
				let minArr = [];
				let min;

				for (let i = 100; i < 250; i++) {

					let valuesForMeasure = Math.floor(xAxisAux.length / i);
					let diference = xAxisAux.length - (valuesForMeasure * i);

					minArr.push(diference);
					min = Math.min(...minArr);

					diferencesArr.push({
						numOfMeasures: i,
						valuesForMeasure: valuesForMeasure,
						diference: diference,
						min: min
					});
				}

				let dataOfMeasures = diferencesArr.find(d => d.min === min);
				let start = dataOfMeasures.valuesForMeasure;
				let end = dataOfMeasures.valuesForMeasure * 2;

				for (let i = 0; i < dataOfMeasures.numOfMeasures; i++) {

					let val;

					if (i === 0) {

						val = xAxisAux.slice(i, start);

					} else if (i === dataOfMeasures.numOfMeasures - 1) {

						val = xAxisAux.slice(start, end + dataOfMeasures.diference);

					} else {

						val = xAxisAux.slice(start, end);

						start = start + dataOfMeasures.valuesForMeasure;
						end = end + dataOfMeasures.valuesForMeasure;
					}

					xAxisToShow.push(val);
				}

				xAxisToShow.forEach(xAux => {

					self.xAxis.push({
						initEpoch: xAux[0].initEpoch,
						initTime: xAux[0].initTime,
						endEpoch: xAux[xAux.length - 1].endEpoch,
						endTime: xAux[xAux.length - 1].endTime,
						xValue: xAux[xAux.length - 1].xValue
					});
				});

			} else {

				self.xAxis = xAxisAux;
			}

			console.log(self.xAxis)
		}


		function getRangeData(ids) {

			if (self.mode === HistoricalChartModule.MODE_ELEMENT_VALUES.id) {

				$$("chart-" + self.uid).clearAll();

				if (self.valuesMap.has(ids)) {

					let dataArr = [];
					let values = self.valuesMap.get(ids);

					if (values && Array.isArray(values.valuesArr) && values.valuesArr.length > 0) {

						for (let i = 0; i < self.xAxis.length; i++) {

							let average = 0;
							let count = 0;
							let d = {};

							values.valuesArr.forEach(val => {

								if (((self.xAxis[i].initEpoch <= val.epoch) && (val.epoch <= self.xAxis[i].endEpoch))) {

									average = average + parseFloat(val.value);
									count++;
								}
							});

							d['yValue'] = (count > 0) ? average / count : undefined;
							d['xValue'] = self.xAxis[i].xValue;

							if (i === 0 || i === self.xAxis.length - 1 || i % 5 === 0) {

								d["epoch"] = self.xAxis[i].initEpoch;
							}

							dataArr.push(d);
						}

						getSeriesOfElementValues(dataArr);
					}
				}

				$$("view-main-" + self.uid).show();

				if ("chart-" + self.uid) {

					$$("chart-" + self.uid).show();
				}

				if ("loading-chart-" + self.uid) {

					$$("loading-chart-" + self.uid).hide();
				}

				$$("loading-" + self.parent.uid).hide();

			} else if (self.mode === HistoricalChartModule.MODE_ALARMS.id) {

				let alarmConfigs = window.em.getAll(EntityType.ALARM_CONFIG);
				let element = window.em.getByField(EntityType.ELEMENT, "id", ids);
				let aConfigsArr = alarmConfigs.filter(e => e.elementType.id === element.elementType.id);

				getDataArr(aConfigsArr, ids);

			} else if (self.mode === HistoricalChartModule.MODE_STATES.id) {

				getDataArr(ids);
			}
		}

		function getDataArr(ids, elementId) {

			let dataArrAux = [];
			let dataArr = [];
			let sameElementArr = [];
			let property;

			if (self.mode === HistoricalChartModule.MODE_ALARMS.id) {

				sameElementArr = self.alarms.filter(e => e.targetElement.id === elementId);
			}

			ids.forEach(id => {

				let arrAux = [];
				let d = {};

				if (self.mode === HistoricalChartModule.MODE_STATES.id) {

					sameElementArr = self.states.filter(e => e.element.id === id);
				}

				for (let xVal of self.xAxis) {

					let withData;

					if (self.mode === HistoricalChartModule.MODE_STATES.id) {

						withData = sameElementArr.filter(el => ((xVal.initEpoch < el.createdAt) && (el.createdAt < xVal.endEpoch)));
						property = "createdAt";

					} else if (self.mode === HistoricalChartModule.MODE_ALARMS.id) {

						withData = sameElementArr.filter(el => ((xVal.initEpoch < el.activation) && (el.activation < xVal.endEpoch) && (el.alarmConfig.id === id.id)));
						property = "activation";
					}

					if (Array.isArray(withData) && withData.length > 0) {

						withData.sort((a, b) => (a[property] > b[property]) ? 1 : ((b[property] > a[property]) ? -1 : 0));

						d['yValue'] = withData;
						d['withData'] = true;

					} else {

						d['yValue'] = undefined;
						d['withData'] = false;
					}

					d['xValue'] = xVal.xValue;
					d["epoch"] = xVal.initEpoch;
					d['element'] = (self.mode === HistoricalChartModule.MODE_ALARMS.id) ? id.id : id;

					arrAux.push(d);
					d = {};
				}

				dataArrAux.push(arrAux);
			});

			for (let j = 0; j < self.xAxis.length; j++) {

				let d = {};

				for (let dArrAux of dataArrAux) {

					let elementId = dArrAux[j].element.replaceAll(":", "");

					d['yValue' + elementId] = dArrAux[j].yValue;
					d['withData' + elementId] = dArrAux[j].withData;
				}

				d['xValue'] = dataArrAux[0][j].xValue;
				d['epoch'] = dataArrAux[0][j].epoch;
				dataArr.push(d);
				d = {};
			}

			getDataSeries(dataArr);

			function getDataSeries(dataArr) {

				dataSet = [];
				dataSeries = [];

				let firstData;
				let firstState;
				let lastState;
				let firstColor;
				let tooltipText;
				let numOfSerie = 0;
				let alias;

				for (let i = 0; i < dataArr.length; i++) {

					let d = {};

					for (let j = 0; j < ids.length; j++) {

						let elId = (self.mode === HistoricalChartModule.MODE_ALARMS.id) ? ids[j].id : ids[j];
						let id = elId.replaceAll(":", "");
						let bgColor = LINES_COLORS[j % LINES_COLORS.length];

						if (i === 0) {

							firstData = dataArr.find(e => e['yValue' + id] != undefined);

							if (!legendValues.find(e => e.id === elId)) {

								let el;

								if (self.mode === HistoricalChartModule.MODE_STATES.id) {

									el = window.em.getByField(EntityType.ELEMENT, "id", elId);
									alias = el.alias;

								} else if (self.mode === HistoricalChartModule.MODE_ALARMS.id) {

									el = window.em.getByField(EntityType.ALARM_CONFIG, "id", elId);
									alias = i18n.t(el.labelAlias);
								}

								legendValues.push({
									id: elId,
									color: bgColor,
									text: alias
								});
							}

							if (self.mode === HistoricalChartModule.MODE_STATES.id) {

								if (firstData) {

									let firstVal;
									let lastVal;

									firstVal = firstData['yValue' + id][0];
									lastVal = firstData['yValue' + id][firstData['yValue' + id].length - 1];

									firstState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", firstVal.currentElementTypeState.id);
									lastState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", lastVal.currentElementTypeState.id);
									firstColor = (firstState.color) ? firstState.color : util.element.getColor(firstState.id);
									tooltipText = alias + " - " + i18n.t(firstState.labelAlias);

								} else {

									tooltipText = alias + " - " + i18n.t("LBL_ELEMENT_TYPE_STATE_UNKNOWN");
								}

								if (!self.args.isEmbed) {

									dataSeries.push({
										value: "#bG" + id + numOfSerie + j + "#",
										line: {
											width: 20,
											color: bgColor,
											item: { radius: 0 }
										}
									});
								}

								dataSeries.push({
									value: "#yValue" + id + numOfSerie + "#",
									line: {
										width: 10,
										color: (firstColor) ? firstColor : "#ffcc00",
										item: {},
									},
									tooltip: {
										template: function(obj) {
											return obj['tooltip' + id + obj['numOfSerie' + id]];
										}
									}
								});

							} else if (self.mode === HistoricalChartModule.MODE_ALARMS.id) {

								tooltipText = (firstData && firstData.labelAlias) ? i18n.t(firstData.labelAlias) : "";
							}

							let firstDate = new Date(self.dateIni).toLocaleString();

							let tooltipHTML = `<div>
												<span>${firstDate}</span><br>
												<span>${tooltipText}</span><br>
												</div>`;

							d['yValue' + id + numOfSerie] = j + 1;
							d['element' + id] = alias;
							d['withData' + id] = dataArr[i]['withData' + id];
							d['numOfSerie' + id] = numOfSerie;
							d['tooltip' + id + numOfSerie] = tooltipHTML;
							d['bG' + id + numOfSerie + j] = j + 1;

						} else {

							let color;
							let lastValue = dataSet[dataSet.length - 1];
							numOfSerie = lastValue['numOfSerie' + id];
							alias = lastValue['element' + id];

							if ((lastValue['withData' + id] === false) && (dataArr[i]['withData' + id] === true)) {

								if (self.mode === HistoricalChartModule.MODE_STATES.id) {

									lastState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", dataArr[i]['yValue' + id][dataArr[i]['yValue' + id].length - 1].currentElementTypeState.id);
									tooltipText = alias + " - " + i18n.t(lastState.labelAlias);
									color = (lastState.color) ? lastState.color : util.element.getColor(lastState.id);

								} else if (self.mode === HistoricalChartModule.MODE_ALARMS.id) {

									tooltipText = i18n.t(alias);
									color = bgColor;
								}

								let firstDate = new Date(dataArr[i]['yValue' + id][0][property]).toLocaleString();

								let tooltipHTML = `<div>
												<span>${firstDate}</span><br>
												<span>${tooltipText}</span><br>
												</div>`;

								numOfSerie++;
								d['yValue' + id + numOfSerie] = j + 1;
								d['element' + id] = alias;
								d['withData' + id] = dataArr[i]['withData' + id];
								d['numOfSerie' + id] = numOfSerie;
								d['tooltip' + id + numOfSerie] = tooltipHTML;
								d['bG' + id + numOfSerie + j] = j + 1;

								if (!self.args.isEmbed && self.mode === HistoricalChartModule.MODE_STATES.id) {

									dataSeries.push({
										value: "#bG" + id + numOfSerie + j + "#",
										line: {
											width: 20,
											color: bgColor,
											item: {}
										}
									});
								}

								dataSeries.push({
									value: "#yValue" + id + numOfSerie + "#",
									line: {
										width: 10,
										color: color,
										item: {},
									},
									tooltip: {
										template: function(obj) {

											return obj['tooltip' + id + obj['numOfSerie' + id]];
										}
									}
								});

							} else {

								d['yValue' + id + numOfSerie] = j + 1;
								d['element' + id] = alias;
								d['withData' + id] = dataArr[i]['withData' + id];
								d['numOfSerie' + id] = lastValue['numOfSerie' + id];
								d['tooltip' + id + numOfSerie] = lastValue['tooltip' + id + numOfSerie];
								d['bG' + id + numOfSerie + j] = j + 1;
							}
						}
					}

					d['xValue'] = dataArr[i].xValue;

					if (i === 0 || i === self.xAxis.length - 1 || i % 25 === 0) {

						d["epoch"] = dataArr[i].epoch;
					}



					dataSet.push(d);
					d = {};
				}

				if ($$("chart-" + self.uid)) {

					$$("chart-" + self.uid).clearAll();
					$$("chart-" + self.uid).define("series", dataSeries);
					$$("chart-" + self.uid).define("data", dataSet);

					if (!self.parent.isEmbed || (self.parent.isEmbed && self.mode === HistoricalChartModule.MODE_ALARMS.id)) {

						let legend = {
							values: legendValues.reverse(),
							align: "right",
							valign: "middle",
							width: 200,
							layout: "y"
						};

						$$("chart-" + self.uid).define("legend", legend);
					}

					$$("chart-" + self.uid).refresh();

					$$("chart-" + self.uid).show();
					$$("loading-chart-" + self.uid).hide();
				}
			}
		}

		function getSeriesOfElementValues(dataArr) {

			dataSet = [];
			dataSeries = [{ value: '#yValue#' }];
			let initYKey = 'yValue';
			let numOfUndefinedSeries = 0;

			for (let i = 0; i < dataArr.length; i++) {

				if (i === 0) {

					dataSet.push(dataArr[i]);

				} else {

					let d = {};
					let lastYValue = dataSet[dataSet.length - 1];
					let keys = Object.keys(lastYValue);
					let YValueIndex = keys.indexOf(initYKey);

					if (lastYValue[keys[YValueIndex]] === undefined && dataArr[i][keys[YValueIndex]] != undefined) {

						d["yValue" + numOfUndefinedSeries] = dataArr[i][keys[YValueIndex]];
						d["numOfSerie"] = numOfUndefinedSeries;

						dataSeries.push(
							{
								value: "#yValue" + numOfUndefinedSeries + "#",
								tooltip: {
									template: function(obj) {

										return obj["tooltip"];
									}
								}
							});

						initYKey = "yValue" + numOfUndefinedSeries;
						numOfUndefinedSeries++;

					} else {

						d[[keys[YValueIndex]]] = dataArr[i].yValue;
					}

					d['xValue'] = dataArr[i].xValue;
					d['epoch'] = dataArr[i].epoch;
					d["tooltip"] = dataArr[i].yValue;

					dataSet.push(d);
				}
			}

			if ($$("chart-" + self.uid)) {

				$$("chart-" + self.uid).clearAll();
				$$("chart-" + self.uid).define("series", dataSeries);
				$$("chart-" + self.uid).define("data", dataSet);
				$$("chart-" + self.uid).refresh();
			}

			console.log(dataSet, dataSeries)
		}

		function drawComponent() {

			let viewMain;

			viewMain = {
				id: "view-main-" + self.uid,
				rows: [
					getCalendar(),
					getChart()
				]
			};

			$$("loading-" + self.parent.uid).hide();
			webix.ui(viewMain, $$(self.parent.uid));

			if (self.list) {

				self.list.selectByIndex(0);
			}


			function getCalendar() {

				let toolbar;

				toolbar = {
					id: "toolbar-" + self.uid,
					view: "toolbar",
					type: "clean",
					elements: [
						{ width: 20 },
						{
							id: "label-" + self.uid,
							view: "label",
							label: self.label
						},
						{
							id: "daterangepicker-" + self.uid,
							view: "daterangepicker",
							name: "date",
							width: 500,
							label: i18n.t("LBL_DATE"),
							value: { start: new Date(), end: webix.Date.add(new Date(), initCountdown, initRange) },
							timepicker: true,
							suggest: {
								view: "daterangesuggest",
								body: {
									calendarCount: 2,
									icons: false,
									timepicker: true,
									calendar: {
										weekNumber: true,
										maxDate: new Date(),
										on: {
											onAfterRender: function() {

												let daterangepicker = $$("daterangepicker-" + self.uid);

												if (daterangepicker && daterangepicker.getPopup()) {

													let button = daterangepicker.getPopup().getButton();

													button.attachEvent("onItemClick", function() {

														if (!self.isCharging) {

															$$("loading-chart-" + self.uid).show();
															$$("chart-" + self.uid).hide();

															self.isCharging = true;
															self.dateIni = new Date(daterangepicker.getValue().start).getTime();
															self.dateEnd = new Date(daterangepicker.getValue().end).getTime();
															let hoursInMillis = self.dateEnd - self.dateIni;

															self.getData(hoursInMillis / (60000 * 60));
														}
													});
												}
											}
										}
									}
								}
							}
						}
					]
				};

				return toolbar;
			}


			function getChart() {

				let chart = {};
				let list = { width: 1 };

				if (self.mode != HistoricalChartModule.MODE_STATES.id) {

					list = {
						width: 300,
						hidden: (self.parent.isEmbed && self.mode === HistoricalChartModule.MODE_ALARMS.id),
						rows: [
							getList()
						]
					};
				}

				chart = {
					cols: [
						list,
						{
							id: "chart-" + self.uid,
							view: "chart",
							type: self.chartType,
							value: "#yValue#",
							item: { radius: 0 },
							offset: false,
							line: {
								width: 2
							},
							xAxis: {
								lines: function(obj) {

									if (obj.epoch) {

										return true;
									}
								},
								template: function(obj) {

									return (obj.epoch) ? obj.xValue : "";
								}
							},
							//							yAxis: {},
							tooltip: {
								template: function(obj) {

									return obj.yValue;
								}
							},
							data: dataSet,
							series: dataSeries
						},
						{
							id: "loading-chart-" + self.uid,
							rows: [util.ui.getViewDrawLoading()],
							hidden: true
						},
					]
				};

				return chart;
			}


			function getList() {

				let listview = {};
				let listEvents = {};

				listEvents[ListView.EventTypes.ON_SELECT_ROW] = function() {

					$$("loading-chart-" + self.uid).show();
					$$("chart-" + self.uid).hide();

					let evSelected = self.list.getSelectedItems()[0];
					getRangeData(evSelected.id);
				}

				let listOptions = {};

				listOptions[ListView.Options.MODE] = ListView.DrawMode.COMPACT;
				listOptions[ListView.Options.SEARCH] = false;

				self.list = new ListView(listElements, listOptions, listEvents);
				listview = self.list.getView();

				return listview;
			}
		}
	}
}