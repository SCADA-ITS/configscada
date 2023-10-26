import HistoricalChartModule from "./historicalChartModule.js";
import HistoricalChartGeneric from "./historicalChartGeneric.js";

const LINES_COLORS = ["#87CEFA88", "#3b607a88", "#00800088", "#FFFF0088", "#fc08fc", "#FFA500", "#7d4c4c", "#C0C0C0", "#9fc944", "#438c73", "#ebdcab", "#48474d", "#48dbaa", "#c7a5ab", "#a4bf8a"];

export default class HistoricalChartElementValues extends HistoricalChartGeneric {

	constructor(args, parent) {

		super(args, parent);

		this.args = args;
		this.parent = parent;
		this.elementValues = undefined;
		this.list = undefined;
		this.selectedRangeInHours = undefined;
		this.xAxis = [];
		this.listElements = [];
		this.listLegend = [];
		this.dataSet = [];
		this.dataSeries = [];
		this.valuesMap = new Map();
		this.chartType = HistoricalChartModule.MODE_ELEMENT_VALUES.chartType;
		this.label = i18n.t("LBL_HISTORICAL_CHART_OF_ELEMENT_VALUES");
		this.tooltip = i18n.t("LBL_HISTORICAL_CHART_OF_ELEMENT_VALUES");
		this.headerTitle = i18n.t("LBL_HISTORICAL_CHART_OF_ELEMENT_VALUES");

		for (let i = 0; i < this.args.elementsIds.length; i++) {

			let element = window.em.getByField(EntityType.ELEMENT, "id", this.args.elementsIds[i]);

			this.listLegend.push({
				id: this.args.elementsIds[i],
				alias: element.alias,
				description: element.alias,
				color: LINES_COLORS[i % LINES_COLORS.length]
			});

			if (!this.parent.isEmbed) {

				this.tooltip += " - " + element.alias;
			}
		}

		if (!this.parent.isEmbed) {

			this.label += " - " + this.listLegend[0].alias;

			if (Array.isArray(this.args.elementsIds) && this.args.elementsIds.length > 1) {

				this.label += " " + i18n.t("LBL_AND").toLowerCase() + " " + (this.args.elementsIds.length - 1) + " " + i18n.t("LBL_MORE_SELECTED") + " ...";
			}
		}
	}

	initialize() {

		let self = this;
		let xAxisAux = [];

		self.getData().then(function(dataResponse) {

			if (dataResponse != null) {

				xAxisAux = self.calculateTimeRanges();
				getXAxisPeriods();

				self.elementValues = dataResponse.json();
				let element = window.em.getByField(EntityType.ELEMENT, "id", self.args.elementsIds[0]);
				let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);
				let elementTypeParamsIdsSet = new Set();

				if (Array.isArray(self.elementValues) && self.elementValues.length > 0) {

					self.elementValues.forEach(ev => {
						elementTypeParamsIdsSet.add(ev.elementTypeParam.id);
					});
				}

				if (elementType.elementTypeParams) {

					elementTypeParamsIdsSet.forEach(elementTypeParamId => {

						let eTypeParam = elementType.elementTypeParams.find(param => param.id === elementTypeParamId);

						if (eTypeParam) {

							let elementValuesArr = self.elementValues.filter(ev => ev.elementTypeParam.id === eTypeParam.id);

							if (Array.isArray(elementValuesArr) && elementValuesArr.length > 0) {

								let valuesArr = [];

								for (let ev of elementValuesArr) {

									let id;

									self.elementsIds.forEach(elementId => {

										let elementValue = elementId.replace("Element", "ElementValue");

										if (ev.id.startsWith(elementValue)) {

											id = elementId;
										}
									});

									valuesArr.push({
										epoch: ev.createdAt,
										time: new Date(ev.createdAt),
										value: ev.value,
										id: id
									});
								}

								self.valuesMap.set(eTypeParam.id, { valuesArr });
							}
						}
					});

					let elementTypeParamsToShow = (element.elementSubtype && Array.isArray(element.elementSubtype.elementSubtypeActiveParams) && element.elementSubtype.elementSubtypeActiveParams.length > 0) ? element.elementSubtype.elementSubtypeActiveParams : elementType.elementTypeParams;

					elementTypeParamsToShow.forEach(eTypeParam => {

						let show = (eTypeParam.noShowMeasureInChart) ? !IS_TRUE(eTypeParam.noShowMeasureInChart) : true;

						if (show && (eTypeParam.paramType.id === self.parent.config.paramTypeMeasure) && (eTypeParam.dataType && (eTypeParam.dataType.id === DataType.INTEGER || eTypeParam.dataType.id === DataType.DECIMAL))) {

							self.listElements.push({
								id: eTypeParam.id,
								alias: i18n.t(eTypeParam.labelAlias),
								description: i18n.t(eTypeParam.labelAlias),
								unit: i18n.t(eTypeParam.labelUnit) ?? ""
							});
						}
					});

				}
			}

			if (!$$("view-main-" + self.uid)) {

				self.drawComponent();

				if (self.list) {

					self.list.selectByIndex(0);
				}

			} else {

				for (let i = 0; i < self.args.elementsIds.length; i++) {

					let element = window.em.getByField(EntityType.ELEMENT, "id", self.args.elementsIds[i]);

					self.listLegend.push({
						id: self.args.elementsIds[i],
						alias: element.alias,
						description: element.alias,
						color: LINES_COLORS[i % LINES_COLORS.length]
					});
				}
				
				let selecteditemArr = self.list.getSelectedItems();

				if (Array.isArray(selecteditemArr) && selecteditemArr.length > 0) {

					self.getRangeData(selecteditemArr[0].id);
				}
			}
		});


		function getXAxisPeriods() {

			let minNumOfMeasures = (xAxisAux.length < 100) ? xAxisAux.length : 100;
			let xAxisToShow = [];
			let diferencesArr = [];
			let minArr = [];
			let min;

			for (let i = minNumOfMeasures; i < 250; i++) {

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

			if (Array.isArray(xAxisToShow) && xAxisToShow.length > 0) {

				xAxisToShow.forEach(xAux => {

					self.xAxis.push({
						initEpoch: xAux[0].initEpoch,
						initTime: xAux[0].initTime,
						endEpoch: xAux[xAux.length - 1].endEpoch,
						endTime: xAux[xAux.length - 1].endTime,
						xValue: xAux[xAux.length - 1].xValue
					});
				});
			}
		}
	}

	getRangeData(ids) {

		let self = this;

		$$("chart-" + self.uid).clearAll();

		if (self.valuesMap.has(ids)) {

			let dataArr = [];
			let values = self.valuesMap.get(ids);

			if (values && Array.isArray(values.valuesArr) && values.valuesArr.length > 0) {

				for (let i = 0; i < self.xAxis.length; i++) {


					let d = {};

					self.elementsIds.forEach(elementId => {

						let average = 0;
						let count = 0;
						let valuesOfElement = values.valuesArr.filter(v => v.id === elementId);
						let id = elementId.replaceAll(":", "");

						valuesOfElement.forEach(val => {

							if (((self.xAxis[i].initEpoch <= val.epoch) && (val.epoch <= self.xAxis[i].endEpoch))) {

								average = average + parseFloat(val.value);
								count++;
							}
						});

						d['yValue' + id] = (count > 0) ? average / count : undefined;
					});

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

			let measure = self.listElements.find(e => e.id === ids);

			if (measure) {

				$$("chart-" + self.uid).config.yAxis.title = measure.unit;
			}

			$$("chart-" + self.uid).show();
		}

		if ("loading-chart-" + self.uid) {

			$$("loading-chart-" + self.uid).hide();
		}

		$$("loading-" + self.parent.uid).hide();

		function getSeriesOfElementValues(dataArr) {

			self.dataSet = [];
			self.dataSeries = [];

			for (let i = 0; i < dataArr.length; i++) {

				if (i === 0) {

					self.dataSet.push(dataArr[i]);

					self.elementsIds.forEach(elementId => {

						let bgColor = self.listLegend.find(e => e.id === elementId).color;
						let id = elementId.replaceAll(":", "");

						self.dataSeries.push({
							value: "#yValue" + id + "#",
							item: {
								radius: 0
							},
							line: {
								color: bgColor,
								width: 2
							},
							tooltip: {
								template: function(obj) {

									return obj["tooltip"];
								}
							}
						});
					});

				} else {

					let d = {};

					for (let elementId of self.elementsIds) {

						let bgColor = self.listLegend.find(e => e.id === elementId).color;
						let id = elementId.replaceAll(":", "");
						let initYKey = 'yValue' + id;
						let lastYValue = self.dataSet[self.dataSet.length - 1];
						let lastSerie = lastYValue['numOfSerie'] ?? 0;
						let keys = Object.keys(lastYValue);
						let YValueIndex = (keys.indexOf(initYKey) != -1) ? keys.indexOf(initYKey) : keys.indexOf('yValue' + lastSerie + id);

						if (lastYValue[keys[YValueIndex]] === undefined && dataArr[i][keys[YValueIndex]] != undefined) {

							d["yValue" + lastSerie + id] = dataArr[i][keys[YValueIndex]];
							d["numOfSerie"] = lastSerie;

							self.dataSeries.push(
								{
									value: "#yValue" + lastSerie + id + "#",
									item: {
										radius: 0
									},
									line: {
										color: bgColor,
										width: 2
									},
									tooltip: {
										template: function(obj) {

											return obj["tooltip"];
										}
									}
								});

						} else {

							d[[keys[YValueIndex]]] = dataArr[i]["yValue" + id];
						}

						d['xValue'] = dataArr[i].xValue;
						d['epoch'] = dataArr[i].epoch;
						d["tooltip"] = dataArr[i]["yValue" + id];
					}

					self.dataSet.push(d);
				}
			}

			if ($$("chart-" + self.uid)) {

				$$("chart-" + self.uid).define("series", self.dataSeries);
				$$("chart-" + self.uid).define("data", self.dataSet);
				$$("chart-" + self.uid).refresh();
			}
		}
	}
}