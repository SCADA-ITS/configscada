import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";
import HistoricalChartWizard from "./historicalChartWizard.js";
import ListView from "../component/listview.js";
import OptionDialogBox from "../component/optionDialogBox.js";

const DEFAULT_INIT_TIMES = {
	RANGE: 24,
	PERIOD: 60,
	MEASURES: 1000
};

export default class HistoricalChartModule extends GenericModule {

	static get MODE_ALARMS() { return 104501 }
	static get MODE_STATES() { return 104502 }
	static get MODE_ELEMENT_VALUES() { return 104503 }

	constructor(module, moduleAction, args, mode) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.moduleAction = moduleAction;
		this.mode = mode;
		this.list = undefined;
		this.elementValues = undefined;
		this.states = undefined;
		this.alarms = undefined;
		this.elementTypeParamsIdsSet = new Set();
		this.valuesMap = new Map();
		this.isCharging = false;
		this.dateEnd = Date.now();
		this.dateIni = Date.now() - (this.config.defaultInitRangeInHours * 60 * 60000);
		this.elementsIds = this.args.elementIds;

		this.uid = webix.uid();
	}


	getView() {

		let view;

		view = {
			css: "custom_window",
			cols: [
				{
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									id: this.uid,
									rows: [],
									hidden: true
								},
								{
									id: "loading-" + this.uid,
									rows: [util.ui.getViewDrawLoading()]
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		};

		return view;
	}


	loadData() {

		let self = this;
		let data;
		let chartType;
		let initRange;
		let initCountdown;
		let initPeriod;
		let dat = {};
		let dataSet = [];
		let dataSeries = [];
		let xAxis = [];
		let elementsIdArr = [];
		let listElements = [];

		//createAlgorithm()

		if (self.elementsIds && self.mode) {

			startModule();

		} else {

			if (!self.elementsIds && !self.mode) {

				selectElement();

			} else if (!self.elementsIds) {

				selectElement();

			} else if (!self.mode) {

				selectMode();
			}
		}

		function selectElement() {

			let hChartWizard = new HistoricalChartWizard(self, onAcceptWizard);
			hChartWizard.drawComponent();

			function onAcceptWizard() {

				if (!self.mode) {

					selectMode();

				} else {

					self.moduleAction = {
						id: self.mode
					};

					startModule();
				}
			}
		}


		function selectMode() {

			let options = [];

			options.push(
				{
					id: self.config.moduleAction.alarms,
					label: i18n.t("LBL_ALARMS"),
					icon: null,
					description: i18n.t("LBL_MODE_ALARMS_DESCRIPTION")
				},
				{
					id: self.config.moduleAction.states,
					label: i18n.t("LBL_STATES"),
					icon: null,
					description: i18n.t("LBL_MODE_STATES_DESCRIPTION"),
				},
				{
					id: self.config.moduleAction.elementValues,
					label: i18n.t("LBL_ELEMENT_VALUES"),
					icon: null,
					description: i18n.t("LBL_MODE_ELEMENT_VALUES_DESCRIPTION"),
				}
			);

			let optionDialog = new OptionDialogBox(i18n.t("LBL_SELECT_ANY_OPTION"), options, selectModuleAction, OptionDialogBox.MODE_TOGGLE);
			optionDialog.show();

			function selectModuleAction(obj) {

				$$(optionDialog.optionWindow).close();

				self.mode = obj;
				self.moduleAction = {
					id: obj
				};

				if (!self.elementsIds) {

					selectElement();

				} else {

					startModule();
				}
			}
		}


		function startModule() {

			if (self.moduleAction.id === self.config.moduleAction.alarms) {

				chartType = "line";
				getElementAlarms();

			} else if (self.moduleAction.id === self.config.moduleAction.states) {

				chartType = "line";
				getElementStates();

			} else if (self.moduleAction.id === self.config.moduleAction.elementValues) {

				chartType = "spline";
				getElementValues();
			}
		}


		function getElementAlarms(selectedRangeInHours) {

			let body;
			dataSet = [];
			dataSeries = [];

			body = {
				dateEnd: self.dateEnd,
				dateIni: self.dateIni,
				elementIds: self.elementsIds
			};

			webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.config.restEndpoints.alarms, JSON.stringify(body)).then(function(responseAlarms) {

				if (responseAlarms != null) {

					self.isCharging = false;
					self.alarms = responseAlarms.json();

					if (Array.isArray(self.alarms) && self.alarms.length > 0) {

						calculateTimeRanges(selectedRangeInHours);

						self.elementsIds.forEach(id => {

							let element = window.em.getByField(EntityType.ELEMENT, "id", id);

							listElements.push({
								id: element.id,
								alias: element.alias,
								description: element.description
							});
						});
					}

					if (!$$("view-main-" + self.uid)) {

						drawComponent();

					} else {

						getRangeDataOfAlarms(self.list.getSelectedItems()[0].id);
					}
				}
			});
		}


		function getElementStates(selectedRangeInHours) {

			let body;
			dataSet = [];
			dataSeries = [];

			body = {
				dateEnd: self.dateEnd,
				dateIni: self.dateIni,
				elementIds: self.elementsIds
			};

			webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.config.restEndpoints.states, JSON.stringify(body)).then(function(responseStates) {

				if (responseStates != null) {

					self.isCharging = false;
					self.states = responseStates.json();

					if (Array.isArray(self.states) && self.states.length > 0) {

						calculateTimeRanges(selectedRangeInHours);
					}

					if (!$$("view-main-" + self.uid)) {

						drawComponent();
						getRangeDataOfStates(self.elementsIds);

					} else {

						getRangeDataOfStates(self.elementsIds);
					}
				}
			});
		}


		function getElementValues(selectedRangeInHours) {

			let body;

			body = {
				dateEnd: self.dateEnd,
				dateIni: self.dateIni,
				elementIds: self.elementsIds
			};

			webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.config.restEndpoints.elementValues, JSON.stringify(body)).then(function(responseElementValues) {

				if (responseElementValues != null) {

					self.isCharging = false;
					self.elementValues = responseElementValues.json();
					let element = window.em.getByField(EntityType.ELEMENT, "id", self.elementsIds[0]);
					let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);

					calculateTimeRanges(selectedRangeInHours);

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

							if (eTypeParam.paramType.id === self.config.paramTypeMeasure) {

								listElements.push({
									id: eTypeParam.id,
									alias: i18n.t(eTypeParam.labelAlias),
									description: i18n.t(eTypeParam.labelAlias)
								});
							}
						});
					}

					if (!$$("view-main-" + self.uid)) {

						drawComponent();

					} else {

						getRangeDataOfElementValues(self.list.getSelectedItems()[0].id);
					}
				}
			});
		}

		function calculateTimeRanges(selectedRangeInHours) {

			xAxis = [];
			let xAxisAux = [];
			let xAxisToShow = [];
			let rangeInHours;
			let defaultInitPeriodInMinutes = (self.config.defaultInitPeriodInMinutes) ? self.config.defaultInitPeriodInMinutes : DEFAULT_INIT_TIMES.PERIOD;

			if (selectedRangeInHours) {

				rangeInHours = selectedRangeInHours;

			} else {

				rangeInHours = (self.config.defaultInitRangeInHours) ? self.config.defaultInitRangeInHours : DEFAULT_INIT_TIMES.RANGE;
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

			if (self.mode === HistoricalChartModule.MODE_ELEMENT_VALUES) {

				initPeriod = (rangeInHours * 60) / defaultInitPeriodInMinutes;

			} else {

				initPeriod = DEFAULT_INIT_TIMES.MEASURES;
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

			if (self.mode === HistoricalChartModule.MODE_ELEMENT_VALUES) {

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

					xAxis.push({
						initEpoch: xAux[0].initEpoch,
						initTime: xAux[0].initTime,
						endEpoch: xAux[xAux.length - 1].endEpoch,
						endTime: xAux[xAux.length - 1].endTime,
						xValue: xAux[xAux.length - 1].xValue
					});
				});

			} else {

				xAxis = xAxisAux;
			}

			console.log(xAxis)
		}


		function getRangeDataOfElementValues(evId) {

			$$("chart-" + self.uid).clearAll();

			if (self.valuesMap.has(evId)) {

				let dataArr = [];
				let values = self.valuesMap.get(evId);

				if (values && Array.isArray(values.valuesArr) && values.valuesArr.length > 0) {

					for (let i = 0; i < xAxis.length; i++) {

						let average = 0;
						let count = 0;
						let d = {};

						values.valuesArr.forEach(val => {

							if (((xAxis[i].initEpoch <= val.epoch) && (val.epoch <= xAxis[i].endEpoch))) {

								average = average + parseFloat(val.value);
								count++;
							}
						});

						d['yValue'] = (count > 0) ? average / count : undefined;
						d['xValue'] = xAxis[i].xValue;

						if (i === 0 || i === xAxis.length - 1 || i % 5 === 0) {

							d["epoch"] = xAxis[i].initEpoch;
						}

						dataArr.push(d);
					}

					getSeriesOfElementValues(dataArr);
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

			$$("view-main-" + self.uid).show();

			if ("chart-" + self.uid) {

				$$("chart-" + self.uid).show();
			}

			if ("loading-chart-" + self.uid) {

				$$("loading-chart-" + self.uid).hide();
			}

			$$("loading-" + self.uid).hide();
		}

		function getRangeDataOfAlarms(elementId) {

			let dataArrAux = [];
			let dataArr = [];
			let arrAux = [];
			let sameElementArr = self.alarms.filter(e => e.targetElement.id === elementId);
			let alarmConfigs = window.em.getAll(EntityType.ALARM_CONFIG);
			let element = window.em.getByField(EntityType.ELEMENT, "id", elementId);
			let aConfigsArr = alarmConfigs.filter(e => e.elementType.id === element.elementType.id);

			aConfigsArr.forEach(aConfig => {

				let d = {};

				for (let xVal of xAxis) {

					let withAlarms = sameElementArr.filter(el => ((xVal.initEpoch < el.activation) && (el.activation < xVal.endEpoch) && (el.alarmConfig.id === aConfig.id)));

					if (Array.isArray(withAlarms) && withAlarms.length > 0) {

						withAlarms.sort((a, b) => (a.activation > b.activation) ? 1 : ((b.activation > a.activation) ? -1 : 0));

						d['yValue'] = withAlarms;
						d['withAlarms'] = true;

					} else {

						d['yValue'] = undefined;
						d['withAlarms'] = false;
					}

					d['xValue'] = xVal.xValue;
					d["epoch"] = xVal.initEpoch;
					d['alarm'] = aConfig.id;

					arrAux.push(d);
					d = {};
				}

				dataArrAux.push(arrAux);
				arrAux = [];
			});

			for (let j = 0; j < xAxis.length; j++) {

				let d = {};

				for (let dArrAux of dataArrAux) {

					let elementId = dArrAux[j].alarm.replaceAll(":", "");

					d['yValue' + elementId] = dArrAux[j].yValue;
					d['withAlarms' + elementId] = dArrAux[j].withAlarms;
				}

				d['xValue'] = dataArrAux[0][j].xValue;
				d['epoch'] = dataArrAux[0][j].epoch;
				dataArr.push(d);
				d = {};
			}

			getSeriesOfAlarms(dataArr);

			function getSeriesOfAlarms(dataArr) {

				dataSet = [];
				dataSeries = [];

				let firstData;
				let tooltipText;
				let numOfSerie = 0;

				for (let i = 0; i < dataArr.length; i++) {

					let d = {};

					for (let j = 0; j < aConfigsArr.length; j++) {

						let alarmConfigId = aConfigsArr[j].id.replaceAll(":", "");

						if (i === 0) {

							firstData = dataArr.find(e => e['yValue' + alarmConfigId] != undefined);

							if (firstData) {


								let firstDate = new Date(self.dateIni).toLocaleString();
								tooltipText = i18n.t(firstData.labelAlias);

								let tooltipHTML = `<div>
												<span>${firstDate}</span><br>
												<span>${tooltipText}</span><br>
												</div>`;

								d['yValue' + alarmConfigId + numOfSerie] = j + 1;
								d['element'] = j + 1;
								d['withAlarms' + alarmConfigId] = dataArr[i]['withAlarms' + alarmConfigId];
								d['numOfSerie' + alarmConfigId] = numOfSerie;
								d['tooltip' + alarmConfigId + numOfSerie] = tooltipHTML;
							}

						} else {

							let lastValue = dataSet[dataSet.length - 1];
							numOfSerie = lastValue['numOfSerie' + alarmConfigId];

							if (dataArr[i]['withAlarms' + alarmConfigId] === true) {

								console.log(dataArr[i])
							}
							if ((lastValue['withAlarms' + alarmConfigId] === false) && (dataArr[i]['withAlarms' + alarmConfigId] === true)) {

								tooltipText = i18n.t("LBL_ALARMED");
								let firstDate = new Date(dataArr[i]['yValue' + alarmConfigId][0].activation).toLocaleString();

								let tooltipHTML = `<div>
												<span>${firstDate}</span><br>
												<span>${tooltipText}</span><br>
												</div>`;

								numOfSerie++;
								d['yValue' + alarmConfigId + numOfSerie] = j + 1;
								d['element'] = j + 1;
								d['withAlarms' + alarmConfigId] = dataArr[i]['withAlarms' + alarmConfigId];
								d['numOfSerie' + alarmConfigId] = numOfSerie;
								d['tooltip' + alarmConfigId + numOfSerie] = tooltipHTML;

								dataSeries.push({
									value: "#yValue" + alarmConfigId + numOfSerie + "#",
									state: tooltipText,
									line: {
										width: 10,
										color: "#EC5D30",
										item: { radius: 0 }
									},
									tooltip: {
										template: function(obj) {

											return obj['tooltip' + alarmConfigId + obj['numOfSerie' + alarmConfigId]];
										}
									}
								});

							} else {

								d['yValue' + alarmConfigId + numOfSerie] = j + 1;
								d['element'] = j + 1;
								d['withAlarms' + alarmConfigId] = dataArr[i]['withAlarms' + alarmConfigId];
								d['numOfSerie' + alarmConfigId] = lastValue['numOfSerie' + alarmConfigId];
								d['tooltip' + alarmConfigId + numOfSerie] = lastValue['tooltip' + alarmConfigId + numOfSerie];
							}
						}
					}

					d['xValue'] = dataArr[i].xValue;

					if (i === 0 || i === xAxis.length - 1 || i % 25 === 0) {

						d["epoch"] = dataArr[i].epoch;
					}

					dataSet.push(d);
					d = {};
				}

				if ($$("chart-" + self.uid)) {

					$$("chart-" + self.uid).clearAll();
					$$("chart-" + self.uid).define("series", dataSeries);
					$$("chart-" + self.uid).define("data", dataSet);
					$$("chart-" + self.uid).refresh();

					$$("chart-" + self.uid).show();
					$$("loading-chart-" + self.uid).hide();
				}

				//				console.log(dataSet, dataSeries)
			}
		}


		function getRangeDataOfStates(elementIds) {

			let dataArrAux = [];
			let dataArr = [];

			elementIds.forEach(id => {

				let arrAux = [];
				let sameElementArr = self.states.filter(e => e.element.id === id);
				let d = {};

				for (let xVal of xAxis) {

					let withStates = sameElementArr.filter(el => ((xVal.initEpoch < el.createdAt) && (el.createdAt < xVal.endEpoch)));

					if (Array.isArray(withStates) && withStates.length > 0) {

						withStates.sort((a, b) => (a.createdAt > b.createdAt) ? 1 : ((b.createdAt > a.createdAt) ? -1 : 0));

						d['yValue'] = withStates;
						d['withStates'] = true;

					} else {

						d['yValue'] = undefined;
						d['withStates'] = false;
					}

					d['xValue'] = xVal.xValue;
					d["epoch"] = xVal.initEpoch;
					d['element'] = id;

					arrAux.push(d);
					d = {};
				}

				dataArrAux.push(arrAux);
			});

			for (let j = 0; j < xAxis.length; j++) {

				let d = {};

				for (let dArrAux of dataArrAux) {

					let elementId = dArrAux[j].element.replaceAll(":", "");

					d['yValue' + elementId] = dArrAux[j].yValue;
					d['withStates' + elementId] = dArrAux[j].withStates;
				}

				d['xValue'] = dataArrAux[0][j].xValue;
				d['epoch'] = dataArrAux[0][j].epoch;
				dataArr.push(d);
				d = {};
			}

			getSeriesOfStates(dataArr);

			function getSeriesOfStates(dataArr) {

				console.log(dataArr)
				dataSet = [];
				dataSeries = [];

				let firstData;
				let firstState;
				let lastState;
				let firstColor;
				let tooltipText;
				let numOfSerie = 0;

				for (let i = 0; i < dataArr.length; i++) {

					let d = {};

					for (let j = 0; j < elementIds.length; j++) {

						let elementId = elementIds[j].replaceAll(":", "");

						if (i === 0) {

							firstData = dataArr.find(e => e['yValue' + elementId] != undefined);

							if (firstData) {

								let firstVal = firstData['yValue' + elementId][0];
								let lastVal = firstData['yValue' + elementId][firstData['yValue' + elementId].length - 1];

								firstState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", firstVal.currentElementTypeState.id);
								lastState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", lastVal.currentElementTypeState.id);
								firstColor = (firstState.color) ? firstState.color : util.element.getColor(firstState.id);
								tooltipText = i18n.t(firstState.labelAlias);

								let firstDate = new Date(self.dateIni).toLocaleString();

								let tooltipHTML = `<div>
												<span>${firstDate}</span><br>
												<span>${tooltipText}</span><br>
												</div>`;

								d['yValue' + elementId + numOfSerie] = j + 1;
								d['element'] = j + 1;
								d['withStates' + elementId] = dataArr[i]['withStates' + elementId];
								d['numOfSerie' + elementId] = numOfSerie;
								d['tooltip' + elementId + numOfSerie] = tooltipHTML;

								dataSeries.push({
									value: "#yValue" + elementId + numOfSerie + "#",
									line: {
										width: 10,
										color: firstColor,
										item: { radius: 0 }
									},
									state: tooltipText,
									tooltip: {
										template: function(obj) {
											return obj['tooltip' + elementId + obj['numOfSerie' + elementId]];
										}
									}
								});

							} else {

								tooltipText = i18n.t("LBL_ELEMENT_TYPE_STATE_UNKNOWN");
								let firstDate = new Date(self.dateIni).toLocaleString();

								let tooltipHTML = `<div>
												<span>${firstDate}</span><br>
												<span>${tooltipText}</span><br>
												</div>`;

								d['yValue' + elementId + numOfSerie] = j + 1;
								d['element'] = j + 1;
								d['withStates' + elementId] = dataArr[i]['withStates' + elementId];
								d['numOfSerie' + elementId] = numOfSerie;
								d['tooltip' + elementId + numOfSerie] = tooltipHTML;

								dataSeries.push({
									value: "#yValue" + elementId + numOfSerie + "#",
									line: {
										width: 10,
										color: "#ffcc00",
										item: { radius: 0 }
									},
									state: tooltipText,
									tooltip: {
										template: function(obj) {
											return obj['tooltip' + elementId + obj['numOfSerie' + elementId]];
										}
									}
								});
							}

						} else {

							let lastValue = dataSet[dataSet.length - 1];
							numOfSerie = lastValue['numOfSerie' + elementId];

							if ((lastValue['withStates' + elementId] === false) && (dataArr[i]['withStates' + elementId] === true)) {

								lastState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", dataArr[i]['yValue' + elementId][dataArr[i]['yValue' + elementId].length - 1].currentElementTypeState.id);
								tooltipText = i18n.t(lastState.labelAlias);
								let lastColor = (lastState.color) ? lastState.color : util.element.getColor(lastState.id);

								let firstDate = new Date(dataArr[i]['yValue' + elementId][0].createdAt).toLocaleString();

								let tooltipHTML = `<div>
												<span>${firstDate}</span><br>
												<span>${tooltipText}</span><br>
												</div>`;

								numOfSerie++;
								d['yValue' + elementId + numOfSerie] = j + 1;
								d['element'] = j + 1;
								d['withStates' + elementId] = dataArr[i]['withStates' + elementId];
								d['numOfSerie' + elementId] = numOfSerie;
								d['tooltip' + elementId + numOfSerie] = tooltipHTML;

								dataSeries.push({
									value: "#yValue" + elementId + numOfSerie + "#",
									state: tooltipText,
									line: {
										width: 10,
										color: lastColor,
										item: { radius: 0 }
									},
									tooltip: {
										template: function(obj) {

											return obj['tooltip' + elementId + obj['numOfSerie' + elementId]];
										}
									}
								});

							} else {

								d['yValue' + elementId + numOfSerie] = j + 1;
								d['element'] = j + 1;
								d['withStates' + elementId] = dataArr[i]['withStates' + elementId];
								d['numOfSerie' + elementId] = lastValue['numOfSerie' + elementId];
								d['tooltip' + elementId + numOfSerie] = lastValue['tooltip' + elementId + numOfSerie];
							}
						}
					}

					d['xValue'] = dataArr[i].xValue;

					if (i === 0 || i === xAxis.length - 1 || i % 25 === 0) {

						d["epoch"] = dataArr[i].epoch;
					}

					dataSet.push(d);
					d = {};
				}

				if ($$("chart-" + self.uid)) {

					$$("chart-" + self.uid).clearAll();
					$$("chart-" + self.uid).define("series", dataSeries);
					$$("chart-" + self.uid).define("data", dataSet);
					$$("chart-" + self.uid).refresh();

					$$("chart-" + self.uid).show();
					$$("loading-chart-" + self.uid).hide();
				}

				//				console.log(dataSet, dataSeries)
			}
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

			$$("loading-" + self.uid).hide();
			webix.ui(viewMain, $$(self.uid));

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
						{
							gravity: 2
						},
						{
							id: "daterangepicker-" + self.uid,
							view: "daterangepicker",
							name: "date",
							width: 500,
							label: "Fechas",
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

														$$("loading-chart-" + self.uid).show();
														$$("chart-" + self.uid).hide();

														if (!self.isCharging) {

															self.isCharging = true;
															self.dateIni = new Date(daterangepicker.getValue().start).getTime();
															self.dateEnd = new Date(daterangepicker.getValue().end).getTime();
															let hoursInMillis = self.dateEnd - self.dateIni;

															if (self.moduleAction && self.moduleAction.id === self.config.moduleAction.alarms) {

																getElementAlarms(hoursInMillis / (60000 * 60));

															} else if (self.moduleAction && self.moduleAction.id === self.config.moduleAction.states) {

																getElementStates(hoursInMillis / (60000 * 60));

															} else if (self.moduleAction && self.moduleAction.id === self.config.moduleAction.equipmentViewer) {

																getElementValues(hoursInMillis / (60000 * 60));

															} else {

																getElementValues(hoursInMillis / (60000 * 60));
															}
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

				console.log(dataSet, dataSeries)

				if ((self.moduleAction && self.moduleAction.id != self.config.moduleAction.states) || (self.mode != HistoricalChartModule.MODE_STATES)) {

					list = {
						width: 300,
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
							type: chartType,
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
							yAxis: {},
							tooltip: {
								template: function(obj) {

									if ((self.moduleAction && self.moduleAction.id === self.config.moduleAction.alarms) || (self.moduleAction && self.moduleAction.id === self.config.moduleAction.states)) {

										obj = obj.substring(0, obj.length - 1);

										if (obj === "") {

											obj = 0;
										}

										return xAxis[obj];

									} else {

										return obj.yValue;
									}
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

					if (self.moduleAction && self.moduleAction.id === self.config.moduleAction.alarms) {

						getRangeDataOfAlarms(evSelected.id);

					} else {

						getRangeDataOfElementValues(evSelected.id);
					}
				}

				let listOptions = {};

				listOptions[ListView.Options.MODE] = ListView.DrawMode.COMPACT;
				listOptions[ListView.Options.SEARCH] = false;

				self.list = new ListView(listElements, listOptions, listEvents);
				listview = self.list.getView();

				return listview;
			}
		}

		function createAlgorithm() {

			const DATES = {
				START: "06/6/2023 13:00:00",
				END: "06/8/2023 08:00:00"
			};

			const GENERAL_CONST = {
				PERIOD: 1,
				COURT: 2,
				CYCLE: 3
			};

			const IS_NULL = true;

			let configs = [
				{
					measure: "surface_temperature",
					max: 50,
					min: -15,
					th: 1,
					correction: 10
				},
				//				{
				//					measure: "sub_surface_temperature",
				//					max: 50,
				//					min: 0,
				//					th: 1,
				//					correction: 10
				//				},
				//				{
				//					measure: "freeze_point",
				//					max: 2,
				//					min: -2,
				//					th: 0.1,
				//					correction: 1
				//				},
				//				{
				//					measure: "air_pressure",
				//					max: 1300,
				//					min: 900,
				//					th: 50,
				//					correction: 100
				//				},
				//				{
				//					measure: "water_depth",
				//					max: 2,
				//					min: 0,
				//					th: 0.1,
				//					correction: 1
				//				},
				//				{
				//					measure: "concentration_nacl",
				//					max: 2000,
				//					min: 1000,
				//					th: 50,
				//					correction: 100
				//				},
				//				{
				//					measure: "concentration_mgcl2",
				//					max: 5000,
				//					min: 3000,
				//					th: 100,
				//					correction: 100
				//				},
				//				{
				//					measure: "concentration_cacl",
				//					max: 500,
				//					min: 200,
				//					th: 25,
				//					correction: 100
				//				},
				//				{
				//					measure: "road_condition",
				//					max: 5,
				//					min: 1,
				//					th: 1,
				//					correction: 10
				//				},
				//				{
				//					measure: "relative_humidity",
				//					max: 100,
				//					min: 0,
				//					th: 1,
				//					correction: 10
				//				},
				//				{
				//					measure: "precipitation_intensity",
				//					max: 1000,
				//					min: 0,
				//					th: 5,
				//					correction: 10
				//				},
				//				{
				//					measure: "snow_fall_intensity",
				//					max: 1000,
				//					min: 0,
				//					th: 10,
				//					correction: 100
				//				},
				//				{
				//					measure: "average_wind_speed",
				//					max: 250,
				//					min: 0,
				//					th: 10,
				//					correction: 100
				//				},
				//				{
				//					measure: "average_wind_direction",
				//					max: 8,
				//					min: 1,
				//					th: 1,
				//					correction: 10
				//				},
				//				{
				//					measure: "gust_wind_speed",
				//					max: 250,
				//					min: 0,
				//					th: 1,
				//					correction: 10
				//				},
				//				{
				//					measure: "gust_wind_direction",
				//					max: 8,
				//					min: 1,
				//					th: 1,
				//					correction: 10
				//				},
				//				{
				//					measure: "air_temperature",
				//					max: 50,
				//					min: -10,
				//					th: 1,
				//					correction: 10
				//				},
				//				{
				//					measure: "dew_point_temperature",
				//					max: 5,
				//					min: -10,
				//					th: 1,
				//					correction: 10
				//				},
				//				{
				//					measure: "precipitation_type",
				//					max: 5,
				//					min: 1,
				//					th: 1,
				//					correction: 10
				//				},
				//				{
				//					measure: "visibility",
				//					max: 1000,
				//					min: 1,
				//					th: 25,
				//					correction: 100
				//				},
				//				{
				//					measure: "black_ice_signal",
				//					max: 10,
				//					min: 1,
				//					th: 1,
				//					correction: 10
				//				},
				//				{
				//					measure: "prediction_alarm_code",
				//					max: 4,
				//					min: 1,
				//					th: 1,
				//					correction: 10
				//				},
				//				{
				//					measure: "precipitation_quantity",
				//					max: 500,
				//					min: 0,
				//					th: 10,
				//					correction: 10
				//				},
				//				{
				//					measure: "ice_percent",
				//					max: 100,
				//					min: 0,
				//					th: 1,
				//					correction: 10
				//				},
				//				{
				//					measure: "friction",
				//					max: 100,
				//					min: 0,
				//					th: 1,
				//					correction: 10
				//				}
			];

			let numOfMeasures = Math.round(((new Date(DATES.END).getTime() - new Date(DATES.START).getTime()) / 60000) / GENERAL_CONST.PERIOD);
			let measuresArr = [];

			//console.log(numOfMeasures)

			for (let i = 0; i < numOfMeasures; i++) {

				if (i === 0) {

					let d = {};

					d["timestamp_at"] = new Date(DATES.START).getTime();

					configs.forEach(c => {

						d[c.measure] = {
							measure: (c.max - c.min) / 2,
							name: c.measure
						};
					});

					measuresArr.push(d);

				} else {

					let lastMeasures = measuresArr[measuresArr.length - 1];
					let d = {};

					d["timestamp_at"] = lastMeasures["timestamp_at"] + (GENERAL_CONST.PERIOD * 60000);

					configs.forEach(c => {

						let newMeasure = lastMeasures[c.measure].measure;
						let court = (lastMeasures[c.measure].court) ? lastMeasures[c.measure].court : 0;
						let cycles = (lastMeasures[c.measure].cycle) ? lastMeasures[c.measure].cycle : 0;
						let cont = true;
						let isNull = false;
						let random = 0;

						if (GENERAL_CONST.COURT > 0 && court < GENERAL_CONST.COURT) {

							if (newMeasure === null) {

								if (cycles < GENERAL_CONST.CYCLE) {

									isNull = true;

								} else {

									cycles = 0;

									for (let z = 0; cont && z < 1000; z++) {

										newMeasure = (c.max - c.min) / 2;
										random = Math.random();
										random = (z % 2 == 0) ? random : random * -1;

										if (Math.abs((random * c["correction"])) <= c["th"]) {

											cont = false;

											if (((Math.abs(random * c["correction"])) >= (c["th"] * 0.9)) || ((Math.abs(random * c["correction"])) <= (c["th"] * 0.1))) {

												isNull = true;
											}
										}
									}
								}

							} else {

								for (let z = 0; cont && z < 1000; z++) {

									random = Math.random();
									random = (z % 2 == 0) ? random : random * -1;

									if (Math.abs((random * c["correction"])) <= c["th"]) {

										cont = false;

										if (((Math.abs(random * c["correction"])) >= (c["th"] * 0.9)) || ((Math.abs(random * c["correction"])) <= (c["th"] * 0.1))) {

											isNull = true;
										}
									}
								}
							}

						} else {

							for (let z = 0; cont && z < 1000; z++) {

								newMeasure = (c.max - c.min) / 2;
								random = Math.random();
								random = (z % 2 == 0) ? random : random * -1;

								if (Math.abs((random * c["correction"])) <= c["th"]) {

									cont = false;
								}
							}
						}

						//console.log(random * c["correction"])
						newMeasure = newMeasure + (random * c["correction"]);

						if (newMeasure > c["measure"].max) {

							newMeasure = c["measure"].max;
						}

						if (newMeasure < c["measure"].min) {

							newMeasure = c["measure"].min;
						}

						if (isNull) {

							if (IS_NULL) {

								newMeasure = null;
								cycles++;
								court = (cycles === GENERAL_CONST.CYCLE) ? court + 1 : court;

							} else {

								newMeasure = newMeasure - (random * c["correction"]);
							}
						}

						d[c.measure] = {
							measure: newMeasure,
							name: c.measure,
							court: court,
							cycle: cycles
						}
					});

					measuresArr.push(d);
				}

				//				console.log(measuresArr)
			}

			//			console.log(measuresArr)


			let columns = '(f_element_id,timestamp_at,';
			let values = "";

			configs.forEach(c => {

				columns += c.measure + ',';
			});

			columns += 'created_at)';

			for (let measure of measuresArr) {

				let date = "'" + new Date(measure.timestamp_at).toISOString() + "'";
				let value = '(1,' + date + ',';

				configs.forEach(c => {

					value += measure[c.measure].measure + ',';
				});

				value += "'" + new Date().toISOString() + "'" + ')';
				values += value + ",";
			}

			let totalQuery = 'INSERT INTO hist.ws ' + columns + 'VALUES ' + values + ";";
			console.log(totalQuery)
		}

	}
}

			//			data = {
			//				getHistoricalElementStates: [
			//					{
			//						element: {
			//							id: "Element:2:1",
			//							alias: "CAM-1"
			//						},
			//						currentElementTypeState: null,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:1",
			//							alias: "CAM-1"
			//						},
			//						currentElementTypeState: 1,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:1",
			//							alias: "CAM-1"
			//						},
			//						currentElementTypeState: null,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:1",
			//							alias: "CAM-1"
			//						},
			//						currentElementTypeState: 1,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:1",
			//							alias: "CAM-1"
			//						},
			//						currentElementTypeState: null,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:1",
			//							alias: "CAM-1"
			//						},
			//						currentElementTypeState: 1,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:2",
			//							alias: "CAM-2"
			//						},
			//						currentElementTypeState: 1,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:2",
			//							alias: "CAM-2"
			//						},
			//						currentElementTypeState: 1,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:2",
			//							alias: "CAM-2"
			//						},
			//						currentElementTypeState: null,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:2",
			//							alias: "CAM-2"
			//						},
			//						currentElementTypeState: null,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:2",
			//							alias: "CAM-2"
			//						},
			//						currentElementTypeState: null,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:2",
			//							alias: "CAM-2"
			//						},
			//						currentElementTypeState: null,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:3",
			//							alias: "CAM-3"
			//						},
			//						currentElementTypeState: null,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:3",
			//							alias: "CAM-3"
			//						},
			//						currentElementTypeState: 1,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:3",
			//							alias: "CAM-3"
			//						},
			//						currentElementTypeState: 1,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:3",
			//							alias: "CAM-3"
			//						},
			//						currentElementTypeState: 1,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:3",
			//							alias: "CAM-3"
			//						},
			//						currentElementTypeState: 1,
			//						createdAt: 1684137777752
			//					},
			//					{
			//						element: {
			//							id: "Element:2:3",
			//							alias: "CAM-3"
			//						},
			//						currentElementTypeState: 1,
			//						createdAt: 1684137777752
			//					}
			//				]
			//			};
			//
			//
			//			data.getHistoricalElementStates.forEach(e => {
			//
			//				if (elementsIdArr.indexOf(e.element.id) === -1) {
			//
			//					elementsIdArr.push(e.element.id);
			//				}
			//			});
			//
			//			elementsIdArr.forEach(id => {
			//
			//				let sameElementArr = data.getHistoricalElementStates.filter(e => e.element.id === id);
			//
			//				//console.log(sameElementArr)
			//
			//				if (Array.isArray(sameElementArr) && sameElementArr.length > 0) {
			//
			//					let tooltip = {};
			//
			//					for (let i = 0; i < sameElementArr.length; i++) {
			//
			//						let timeToInit = new Date(sameElementArr[i].createdAt + (i * 300000));
			//						let initHour = timeToInit.getHours().toString();
			//						let initMinute = timeToInit.getMinutes().toString();
			//						let newCreatedAt = sameElementArr[i].createdAt + (i * 300000);
			//
			//						let elementIdAlias = sameElementArr[i].element.id.replaceAll(":", "");
			//						dat["value" + newCreatedAt + elementIdAlias] = initHour.padStart(2, "0") + ":" + initMinute.padStart(2, "0");
			//						dat["element"] = sameElementArr[i].element.alias;
			//						dat["elementId"] = sameElementArr[i].element.id;
			//						tooltip["state" + newCreatedAt + elementIdAlias] = (sameElementArr[i].currentElementTypeState === null) ? "DESCONECTADO" : "CONECTADO";
			//
			//						dataSeries.push(
			//							{
			//								value: "#value" + newCreatedAt + elementIdAlias + "#",
			//								color: (sameElementArr[i].currentElementTypeState === null) ? "#de5757" : "#acde57",
			//								tooltip: {
			//									template: function() {
			//
			//										return tooltip["state" + newCreatedAt + elementIdAlias];
			//									}
			//								}
			//							});
			//
			//						xAxis.push(initHour.padStart(2, "0") + ":" + initMinute.padStart(2, "0"));
			//					}
			//				}
			//
			//				dataSet.push(dat);
			//				dat = {};
			//			});
			//
			//			drawComponent();


					//		else {
		//
		//			chartType = "spline";
		//
		//			let hChartWizard = new HistoricalChartWizard(self, getOptionDialogBox);
		//			hChartWizard.drawComponent();
		//
		//			function getOptionDialogBox() {
		//
		//				let options = [];
		//
		//				options.push(
		//					{
		//						id: self.config.moduleAction.alarms,
		//						label: i18n.t("LBL_ALARMS"),
		//						icon: null,
		//						description: i18n.t("LBL_MODE_ALARMS_DESCRIPTION"),
		//					},
		//					{
		//						id: self.config.moduleAction.states,
		//						label: i18n.t("LBL_STATES"),
		//						icon: null,
		//						description: i18n.t("LBL_MODE_STATES_DESCRIPTION"),
		//					},
		//					{
		//						id: self.config.moduleAction.elementValues,
		//						label: i18n.t("LBL_ELEMENT_VALUES"),
		//						icon: null,
		//						description: i18n.t("LBL_MODE_ELEMENT_VALUES_DESCRIPTION"),
		//					}
		//				);
		//
		//
		//				let optionDialog = new OptionDialogBox(i18n.t("LBL_SELECT_ANY_OPTION"), options, selectModuleAction, OptionDialogBox.MODE_TOGGLE);
		//				optionDialog.show();
		//
		//				function selectModuleAction(obj) {
		//
		//					console.log(obj)
		//					$$(optionDialog.optionWindow).close();
		//					self.moduleAction = {
		//						id: obj
		//					};
		//
		//					if (self.moduleAction.id === self.config.moduleAction.alarms) {
		//
		//						chartType = "line";
		//						getElementAlarms();
		//
		//					} else if (self.moduleAction.id === self.config.moduleAction.states) {
		//
		//						chartType = "line";
		//						getElementStates();
		//
		//
		//					} else if (self.moduleAction.id === self.config.moduleAction.elementValues) {
		//
		//						chartType = "spline";
		//						getElementValues();
		//					}
		//				}
		//			}
		//
		//		}
