import HistoricalChartModule from "./historicalChartModule.js";
import HistoricalChartGeneric from "./historicalChartGeneric.js";

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
		this.dataSet = [];
		this.dataSeries = [];
		this.valuesMap = new Map();
		this.chartType = HistoricalChartModule.MODE_ELEMENT_VALUES.chartType;
		this.label = i18n.t("LBL_HISTORICAL_CHART_OF_ELEMENT_VALUES");
		
		if (!this.args.isEmbed) {

			for (let elId of this.args.elementsIds) {

				let element = window.em.getByField(EntityType.ELEMENT, "id", elId);

				this.label += " - " + element.alias;
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

							self.listElements.push({
								id: eTypeParam.id,
								alias: i18n.t(eTypeParam.labelAlias),
								description: i18n.t(eTypeParam.labelAlias)
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

				self.getRangeData(self.list.getSelectedItems()[0].id);
			}
		});


		function getXAxisPeriods() {

			let xAxisToShow = [];
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
		}
	}

	getRangeData(ids) {

		let self = this;

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

		function getSeriesOfElementValues(dataArr) {

			self.dataSet = [];
			self.dataSeries = [{ value: '#yValue#' }];
			let initYKey = 'yValue';
			let numOfUndefinedSeries = 0;

			for (let i = 0; i < dataArr.length; i++) {

				if (i === 0) {

					self.dataSet.push(dataArr[i]);

				} else {

					let d = {};
					let lastYValue = self.dataSet[self.dataSet.length - 1];
					let keys = Object.keys(lastYValue);
					let YValueIndex = keys.indexOf(initYKey);

					if (lastYValue[keys[YValueIndex]] === undefined && dataArr[i][keys[YValueIndex]] != undefined) {

						d["yValue" + numOfUndefinedSeries] = dataArr[i][keys[YValueIndex]];
						d["numOfSerie"] = numOfUndefinedSeries;

						self.dataSeries.push(
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

					self.dataSet.push(d);
				}
			}

			if ($$("chart-" + self.uid)) {

				$$("chart-" + self.uid).clearAll();
				$$("chart-" + self.uid).define("series", self.dataSeries);
				$$("chart-" + self.uid).define("data", self.dataSet);
				$$("chart-" + self.uid).refresh();
			}
		}
	}
}