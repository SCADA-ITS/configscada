import { util } from "../../lib/util.js";
import ListView from "../../component/listview.js";

const CHART_INTERVAL_DEFAULT = 1;
const CHART_INTERVAL_TIME_DEFAULT = 60;
const MAX_NUM_OF_MEASURES = 20;
const WIDTH_CHART_CORRECTOR = 70;
const MEASURE_PARAM_TYPE = "ParamType:2";

export default class EquipmentViewerChart {

	constructor(parent, element, equipmentViewer, measuresGroups) {

		this.parent = parent;
		this.element = element;
		this.equipmentViewer = equipmentViewer;
		this.measuresGroups = measuresGroups;
		this.elementValuesList = new Map();
		this.elementValuesUpdates = new Map();
		this.uid = parent.uid;
		this.listView = undefined;
		this.hasMeasures = true;
		this.chartInterval = (equipmentViewer.chartIntervalMin) ? equipmentViewer.chartIntervalMin : CHART_INTERVAL_DEFAULT;
		this.chartIntervalTimeMin = (equipmentViewer.chartIntervalTimeMin) ? equipmentViewer.chartIntervalTimeMin : CHART_INTERVAL_TIME_DEFAULT;
		this.numOfMeasures = (this.chartIntervalTimeMin / this.chartInterval) < MAX_NUM_OF_MEASURES ? this.chartIntervalTimeMin / this.chartInterval : MAX_NUM_OF_MEASURES;
		this.checkMeasuresInterval = undefined;
		this.initChart = true;
	}

	getView() {

		let self = this;
		let mainChart = {};
		let elementsList = [];
		let groupOptionsArr = [];

		mainChart = {
			rows: [
				{ height: 10 },
				{
					cols: [
						{ width: 10 },
						{
							css: "bordered-layout-without-border-top",
							rows: [util.ui.getViewNoData()]
						},
						{ width: 10 }
					]
				},
				{ height: 10 }
			],
			on: {
				onDestruct: function() {

					clearInterval(self.checkMeasuresInterval);
				}
			}
		}

		if (Array.isArray(self.measuresGroups) && self.measuresGroups.length > 0) {

			for (let measureGroup of self.measuresGroups) {

				groupOptionsArr.push({
					id: measureGroup.group.id,
					value: i18n.t(measureGroup.group.label)
				});
			}

			if (Array.isArray(self.measuresGroups[0].elementValues) && self.measuresGroups[0].elementValues.length > 0) {

				self.measuresGroups[0].elementValues.forEach(item => {

					if (item.elementTypeParam.paramType.id === MEASURE_PARAM_TYPE && (item.elementTypeParam.dataType.id === DataType.INTEGER || item.elementTypeParam.dataType.id === DataType.DECIMAL)) {

						elementsList.push({
							id: item.id,
							alias: i18n.t(item.elementTypeParam.labelAlias),
							description: (item.elementTypeParam.labelUnit) ? i18n.t(item.elementTypeParam.labelUnit) : ""
						});
					}
				});
			}
		}

		if (Array.isArray(elementsList) != undefined && elementsList.length > 0) {

			let listviewEvents = {};

			listviewEvents[ListView.EventTypes.ON_SELECT_ROW] = function(ids, callbackEnable) {

				$$("loading-chart-" + self.uid).show();
				$$("content-chart-" + self.uid).hide();

				if (callbackEnable) {

					callbackEnable(false);
				}

				if ($$("chart-" + self.uid)) {

					$$("chart-" + self.uid).clearAll();

					let isMapped = self.elementValuesList.has(ids[0]);

					if (isMapped) {

						let measuresArr = self.elementValuesList.get(ids[0]);
						let newMeasure = elementsList.find(element => element.id === ids[0]);

						if (newMeasure) {

							$$("chart-" + self.uid).config.yAxis.title = newMeasure.description;
						}

						$$("chart-" + self.uid).define("data", measuresArr);
					}

					if (callbackEnable) {

						callbackEnable(true);
					}

					$$("content-chart-" + self.uid).show();
					$$("loading-chart-" + self.uid).hide();

					let chartWidth = $$("chart-" + self.uid).$width - WIDTH_CHART_CORRECTOR;
					let cellWidth = Math.floor(chartWidth / self.numOfMeasures);

					$$("chart-" + self.uid).config.cellWidth = cellWidth;
					$$("chart-" + self.uid).refresh();
				}
			};

			let options = {}

			options[ListView.Options.MODE] = ListView.DrawMode.COMPACT;

			self.listView = new ListView(elementsList, options, listviewEvents);

		} else {

			self.hasMeasures = false;
		}

		if (self.hasMeasures) {

			mainChart = {
				id: "view-main-chart-" + self.uid,
				rows: [
					{ height: 10 },
					{
						cols: [
							{ width: 10 },
							{
								css: "bordered-layout-with-border-top",
								rows: [getChartList()]
							},
							{ width: 10 },
							{
								id: "content-chart-" + self.uid,
								css: "bordered-layout-with-border-top",
								hidden: true,
								rows: [getChart()]
							},
							{
								id: "loading-chart-" + self.uid,
								row: util.ui.getViewDrawLoading()
							},
							{ width: 10 }
						]
					},
					{ height: 10 }
				],
				on: {
					onDestruct: function() {

						clearInterval(self.checkMeasuresInterval);
					}
				}
			};
		}

		function getChartList() {

			let chartList;

			chartList = {
				width: 300,
				rows: [
					{
						cols: [
							{ width: 10 },
							{
								view: "richselect",
								height: 40,
								options: groupOptionsArr,
								value: groupOptionsArr[0].id,
								on: {
									onChange: function(obj) {

										let groupMeasures = self.measuresGroups.find(group => group.group.id == obj);

										if ($$("chart-" + self.uid)) {

											$$("chart-" + self.uid).clearAll();
											$$("chart-" + self.uid).config.yAxis.title = "";
											$$("chart-" + self.uid).refresh();
										}

										if (groupMeasures) {

											elementsList = [];

											groupMeasures.elementValues.forEach(item => {

												if (item.elementTypeParam.paramType.id === MEASURE_PARAM_TYPE && (item.elementTypeParam.dataType.id === DataType.INTEGER || item.elementTypeParam.dataType.id === DataType.DECIMAL)) {

													elementsList.push({
														id: item.id,
														alias: i18n.t(item.elementTypeParam.labelAlias),
														description: (item.elementTypeParam.labelUnit) ? i18n.t(item.elementTypeParam.labelUnit) : ""
													});
												}
											});

											self.listView.clear();
											self.listView.add(elementsList);
										}
									}
								}
							},
							{ width: 10 }
						]
					},
					{
						cols: [self.listView.getView()]
					}
				]
			};

			return chartList;
		}

		function getChart() {

			let chart = {};

			chart = {
				rows: [
					{
						cols: [
							{},
							{
								view: "richselect",
								width: 350,
								value: 1,
								options: [
									{ id: 1, value: i18n.t("LBL_CHART_LINE") },
									{ id: 2, value: i18n.t("LBL_CHART_CURVED_LINE") },
									{ id: 4, value: i18n.t("LBL_CHART_BAR") }
								],
								on: {
									onChange: function(newv) {

										$$("loading-chart-" + self.uid).show();
										$$("content-chart-" + self.uid).hide();

										const CHART_TYPES = {
											LINE: { id: 1, type: "line" },
											CURVED: { id: 2, type: "spline" },
											BAR: { id: 4, type: "bar" }
										};

										if (newv == CHART_TYPES.LINE.id) {

											$$("chart-" + self.uid).config.type = CHART_TYPES.LINE.type;

										} else if (newv == CHART_TYPES.CURVED.id) {

											$$("chart-" + self.uid).config.type = CHART_TYPES.CURVED.type;

										} else if (newv == CHART_TYPES.BAR.id) {

											$$("chart-" + self.uid).config.type = CHART_TYPES.BAR.type;
											$$("chart-" + self.uid).config.color = function(obj) { return "#1293f8"; };
											$$("chart-" + self.uid).config.gradient = true;
										}

										$$("chart-" + self.uid).refresh();

										$$("content-chart-" + self.uid).show();
										$$("loading-chart-" + self.uid).hide();
									}
								}
							},
							{ width: 10 }
						]
					},
					{
						cols: [
							{ width: 10 },
							{
								rows: [
									{ height: 10 },
									{
										id: "chart-" + self.uid,
										view: "chart",
										type: "line",
										value: "#yValue#",
										cellWidth: 50,
										dynamic: true,
										label: function(obj) {
											return parseInt(obj.yValue, 10);
										},
										item: {
											borderColor: "#1293f8",
											color: "#ffffff"
										},
										line: {
											color: "#1293f8",
											width: 3
										},
										xAxis: {
											template: "#xValue#"
										},
										yAxis: {
											title: "",
											template: function(obj) {
												return (obj % 20 ? "" : obj)
											}
										},
										series: [
											{
												value: "#yValue#",
												item: {
													borderColor: "#1293f8",
													color: "#ffffff"
												},
												line: {
													color: "#1293f8",
													width: 2
												},
												tooltip: {
													template: "#yValue#"
												}
											}
										]
									},
									{ height: 10 }
								]
							},
							{ width: 10 }
						]
					}
				]
			}

			return chart;
		}

		return mainChart;
	}

	selectByIndex(idx) {

		let self = this;

		if (self.listView != undefined) {

			self.listView.selectByIndex(idx);
		}
	}

	updateMeasure(elementValues) {

		let self = this;

		let isMapped = self.elementValuesUpdates.has(elementValues[0].id);

		if (isMapped) {

			self.elementValuesUpdates.delete(elementValues[0].id);
		}

		self.elementValuesUpdates.set(elementValues[0].id, elementValues[0].value)
	}

	initMeasuresMaintenance() {

		let self = this;
		let timeToInit = new Date();
		let initHour = timeToInit.getHours().toString();
		let initMinute = timeToInit.getMinutes().toString();
		let initTime = initHour.padStart(2, "0") + ":" + initMinute.padStart(2, "0");

		for (let measureGroup of self.measuresGroups) {

			for (let ev of measureGroup.elementValues) {

				let measuresArr = [];

				measuresArr.push({
					xValue: initTime,
					yValue: ev.value
				});

				self.elementValuesList.set(ev.id, measuresArr);
			}
		}

		self.checkMeasuresInterval = setInterval(function() {

			let itemSelected = (self.listView) ? self.listView.getSelectedItems() : undefined;
			let timeToUpdate = new Date();
			let updateHour = timeToUpdate.getHours().toString();
			let updateMinute = timeToUpdate.getMinutes().toString();
			let newTime = updateHour.padStart(2, "0") + ":" + updateMinute.padStart(2, "0");

			for (let [key, value] of self.elementValuesList) {

				let isMappedInUpdates = self.elementValuesUpdates.has(key);

				if (isMappedInUpdates) {

					let updatedValue = self.elementValuesUpdates.get(key);

					value.push({
						xValue: newTime,
						yValue: updatedValue
					});

					self.elementValuesUpdates.delete(key);

				} else {

					let newValue = value[value.length - 1].yValue;

					if (newValue != undefined) {

						value.push({
							xValue: newTime,
							yValue: newValue
						});
					}
				}

				if (value.length > self.numOfMeasures) {

					let deleteFirstItem = true;

					for (let i = 0; deleteFirstItem && i < value.length; i++) {

						value.shift();

						if (value.length <= self.numOfMeasures) {

							deleteFirstItem = false;
						}
					}
				}
			}

			if (Array.isArray(itemSelected) && itemSelected.length > 0) {

				let measuresArr = self.elementValuesList.get(itemSelected[0].id);
				let lastMeasure = measuresArr[measuresArr.length - 1];

				if (parseFloat(lastMeasure.yValue)) {

					$$("chart-" + self.uid).add({ yValue: parseFloat(lastMeasure.yValue), xValue: lastMeasure.xValue });
				}
			}

		}, self.chartInterval * 60000);
	}
} 