import { util } from "../../lib/util.js";
import HistoricalChartModule from "./historicalChartModule.js";
import HistoricalChartGeneric from "./historicalChartGeneric.js";

const LINES_COLORS = ["#87CEFA88", "#3b607a88", "#00800088", "#FFFF0088", "#fc08fc", "#FFA500", "#7d4c4c", "#C0C0C0", "#9fc944", "#438c73", "#ebdcab", "#48474d", "#48dbaa", "#c7a5ab", "#a4bf8a"];

export default class HistoricalChartStates extends HistoricalChartGeneric {

	constructor(args, parent) {

		super(args, parent);

		this.args = args;
		this.parent = parent;
		this.states = undefined;
		this.selectedRangeInHours = undefined;
		this.xAxis = [];
		this.dataSet = [];
		this.dataSeries = [];
		this.chartType = HistoricalChartModule.MODE_STATES.chartType;
		this.label = i18n.t("LBL_HISTORICAL_CHART_OF_STATES");

		if (!this.args.isEmbed) {

			for (let elId of this.args.elementsIds) {

				let element = window.em.getByField(EntityType.ELEMENT, "id", elId);

				this.label += " - " + element.alias;
			}
		}
	}

	initialize() {

		let self = this;

		self.getData().then(function(dataResponse) {

			if (dataResponse != null) {

				self.xAxis = self.calculateTimeRanges();
				self.states = dataResponse.json();
			}

			if (!$$("view-main-" + self.uid)) {

				self.drawComponent();
				getDataArr(self.args.elementsIds);

			} else {

				getDataArr(self.args.elementsIds);
			}
		});


		function getDataArr(ids) {

			let dataArrAux = [];
			let dataArr = [];

			ids.forEach(id => {

				let arrAux = [];								
				let d = {};				

				let sameElementArr = self.states.filter(e => e.element.id === id);
				
				if (Array.isArray(sameElementArr) && sameElementArr.length > 0) {

					sameElementArr.sort((a, b) => (a.createdAt > b.createdAt) ? 1 : ((b.createdAt > a.createdAt) ? -1 : 0));

					let elementTypeStateId = sameElementArr[0].currentElementTypeState.id;

					for (let xVal of self.xAxis) {

						let withData = sameElementArr.filter(el => ((xVal.initEpoch < el.createdAt) && (el.createdAt < xVal.endEpoch)));

						if (Array.isArray(withData) && withData.length > 0) {

							withData.sort((a, b) => (a.createdAt > b.createdAt) ? 1 : ((b.createdAt > a.createdAt) ? -1 : 0));

							elementTypeStateId = withData[withData.length - 1].currentElementTypeState.id;
							d['yValue'] = withData;
							d['withData'] = true;

						} else {

							d['yValue'] = undefined;
							d['withData'] = false;
						}

						d['xValue'] = xVal.xValue;
						d["epoch"] = xVal.initEpoch;
						d['element'] = id;
						d['elementTypeStateId'] = elementTypeStateId;

						arrAux.push(d);
						d = {};
					}

					dataArrAux.push(arrAux);
				}
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
				d['elementTypeStateId'] = dataArrAux[0][j].elementTypeStateId; 
				dataArr.push(d);
				d = {};
			}

			getDataSeries();

			function getDataSeries() {

				self.dataSet = [];
				self.dataSeries = [];

				let firstData;
				let firstState;
				let lastState;
				let firstColor;
				let tooltipText;
				let alias;
				let numOfSerie = 0;
				let legendValues = [];

				for (let i = 0; i < dataArr.length; i++) {

					let d = {};

					for (let j = 0; j < ids.length; j++) {

						let elId = ids[j];
						let id = elId.replaceAll(":", "");
						let bgColor = LINES_COLORS[j % LINES_COLORS.length];

						if (i === 0) {

							firstData = dataArr.find(e => e['yValue' + id] != undefined);

							if (!legendValues.find(e => e.id === elId)) {

								let el = window.em.getByField(EntityType.ELEMENT, "id", elId);
								alias = el.alias;

								legendValues.push({
									id: elId,
									color: bgColor,
									text: alias
								});
							}

							if (firstData) {

								let firstVal;
								let lastVal;

								firstVal = firstData['yValue' + id][0];
								lastVal = firstData['yValue' + id][firstData['yValue' + id].length - 1];

								firstState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", firstVal.currentElementTypeState.id);
								lastState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", lastVal.currentElementTypeState.id);
								firstColor = (firstState.color) ? firstState.color : util.element.getColor(firstState);
								tooltipText = alias + " - " + i18n.t(firstState.labelAlias);

							} else {

								tooltipText = alias + " - " + i18n.t("LBL_ELEMENT_TYPE_STATE_UNKNOWN");
							}

							if (!self.args.isEmbed) {

								self.dataSeries.push({
									value: "#bG" + id + numOfSerie + j + "#",
									line: {
										width: 20,
										color: bgColor,
										item: { radius: 0 }
									}
								});
							}

							self.dataSeries.push({
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
							let lastValue = self.dataSet[self.dataSet.length - 1];
							let changeState = (dataArr[i - 1]['elementTypeStateId'] != dataArr[i]['elementTypeStateId']);

							numOfSerie = lastValue['numOfSerie' + id];
							alias = lastValue['element' + id];

							if ((lastValue['withData' + id] === false) && (dataArr[i]['withData' + id] === true) && changeState) {								
								
								lastState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", dataArr[i]['yValue' + id][dataArr[i]['yValue' + id].length - 1].currentElementTypeState.id);
								tooltipText = alias + " - " + i18n.t(lastState.labelAlias);
								color = (lastState.color) ? lastState.color : util.element.getColor(lastState);

								let firstDate = new Date(dataArr[i]['yValue' + id][0]["createdAt"]).toLocaleString();

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

								if (!self.args.isEmbed) {

									self.dataSeries.push({
										value: "#bG" + id + numOfSerie + j + "#",
										line: {
											width: 20,
											color: bgColor,
											item: {}
										}
									});
								}

								self.dataSeries.push({
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

					self.dataSet.push(d);
					d = {};
				}

				if ($$("chart-" + self.uid)) {

					$$("chart-" + self.uid).clearAll();
					$$("chart-" + self.uid).define("series", self.dataSeries);
					$$("chart-" + self.uid).define("data", self.dataSet);

					if (!self.parent.isEmbed) {

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
	}
}