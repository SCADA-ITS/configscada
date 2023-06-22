import HistoricalChartModule from "./historicalChartModule.js";
import HistoricalChartGeneric from "./historicalChartGeneric.js";

const LINES_COLORS = ["#87CEFA88", "#3b607a88", "#00800088", "#FFFF0088", "#fc08fc", "#FFA500", "#7d4c4c", "#C0C0C0", "#9fc944", "#438c73", "#ebdcab", "#48474d", "#48dbaa", "#c7a5ab", "#a4bf8a"];

export default class HistoricalChartAlarms extends HistoricalChartGeneric {

	constructor(args, parent) {

		super(args, parent);

		this.args = args;
		this.parent = parent;
		this.alarms = undefined;
		this.list = undefined;
		this.selectedRangeInHours = undefined;
		this.xAxis = [];
		this.listElements = [];
		this.dataSet = [];
		this.dataSeries = [];
		this.chartType = HistoricalChartModule.MODE_ALARMS.chartType;
		this.label = i18n.t("LBL_HISTORICAL_CHART_OF_ALARMS");

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
				self.alarms = dataResponse.json();

				self.args.elementsIds.forEach(id => {

					let element = window.em.getByField(EntityType.ELEMENT, "id", id);

					self.listElements.push({
						id: element.id,
						alias: element.alias,
						description: element.description
					});
				});
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
	}

	getRangeData(ids) {

		let self = this;
		let alarmConfigs = window.em.getAll(EntityType.ALARM_CONFIG);
		let element = window.em.getByField(EntityType.ELEMENT, "id", ids);
		let aConfigsArr = alarmConfigs.filter(e => e.elementType.id === element.elementType.id);
		let dataArrAux = [];
		let dataArr = [];
		let sameElementArr = self.alarms.filter(e => e.targetElement.id === ids);

		aConfigsArr.forEach(id => {

			let arrAux = [];
			let d = {};

			for (let xVal of self.xAxis) {

				let withData = sameElementArr.filter(el => ((xVal.initEpoch < el.activation) && (el.activation < xVal.endEpoch) && (el.alarmConfig.id === id.id)));

				if (Array.isArray(withData) && withData.length > 0) {

					withData.sort((a, b) => (a["activation"] > b["activation"]) ? 1 : ((b["activation"] > a["activation"]) ? -1 : 0));

					d['yValue'] = withData;
					d['withData'] = true;

				} else {

					d['yValue'] = undefined;
					d['withData'] = false;
				}

				d['xValue'] = xVal.xValue;
				d["epoch"] = xVal.initEpoch;
				d['element'] = id.id;

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

		getDataSeries();

		function getDataSeries() {

			self.dataSet = [];
			self.dataSeries = [];

			let firstData;
			let tooltipText;
			let alias;
			let numOfSerie = 0;
			let legendValues = [];

			for (let i = 0; i < dataArr.length; i++) {

				let d = {};

				for (let j = 0; j < aConfigsArr.length; j++) {

					let elId = aConfigsArr[j].id;
					let id = elId.replaceAll(":", "");
					let bgColor = LINES_COLORS[j % LINES_COLORS.length];

					if (i === 0) {

						firstData = dataArr.find(e => e['yValue' + id] != undefined);

						if (!legendValues.find(e => e.id === elId)) {

							let el = window.em.getByField(EntityType.ALARM_CONFIG, "id", elId);
							alias = i18n.t(el.labelAlias);

							legendValues.push({
								id: elId,
								color: bgColor,
								text: alias
							});
						}

						tooltipText = (firstData && firstData.labelAlias) ? i18n.t(firstData.labelAlias) : "";
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
						numOfSerie = lastValue['numOfSerie' + id];
						alias = lastValue['element' + id];

						if ((lastValue['withData' + id] === false) && (dataArr[i]['withData' + id] === true)) {

							tooltipText = i18n.t(alias);
							color = bgColor;

							let firstDate = new Date(dataArr[i]['yValue' + id][0]["activation"]).toLocaleString();

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

				let legend = {
					values: legendValues.reverse(),
					align: "right",
					valign: "middle",
					width: 200,
					layout: "y"
				};

				$$("chart-" + self.uid).define("legend", legend);
				$$("chart-" + self.uid).refresh();
				$$("chart-" + self.uid).show();
				$$("loading-chart-" + self.uid).hide();
			}
		}
	}
}