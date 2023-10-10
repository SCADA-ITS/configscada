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
		this.listLegend = [];
		this.allAlarmConfigsArr = [];
		this.dataSet = [];
		this.dataSeries = [];
		this.chartType = HistoricalChartModule.MODE_ALARMS.chartType;
		this.label = i18n.t("LBL_HISTORICAL_CHART_OF_ALARMS");
		this.alarmConfigs = window.em.getAll(EntityType.ALARM_CONFIG);

		for (let elementId of this.args.elementsIds) {

			let element = window.em.getByField(EntityType.ELEMENT, "id", elementId);

			if (!this.parent.isEmbed) {

				this.label += " - " + element.alias;
			}

			let aConfigsArr = this.alarmConfigs.filter(e => e.elementType.id === element.elementType.id);

			for (let j = 0; j < aConfigsArr.length; j++) {

				let aConf = window.em.getByField(EntityType.ALARM_CONFIG, "id", aConfigsArr[j].id);

				this.allAlarmConfigsArr.push({
					id: aConfigsArr[j].id,
					alias: i18n.t(aConf.labelAlias),
					description: i18n.t(aConf.labelAlias),
					color: LINES_COLORS[j % LINES_COLORS.length]
				});
			}
		}

	}

	initialize() {

		let self = this;

		self.getData().then(function(dataResponse) {

			if (dataResponse != null) {

				self.xAxis = self.calculateTimeRanges();
				self.alarms = dataResponse.json();

				self.alarms.forEach(alarm => {

					let al = self.allAlarmConfigsArr.find(e => e.id === alarm.alarmConfig.id);

					if (al && (self.listLegend.find(e => e.id === al.id) === undefined)) {

						self.listLegend.push(al);
					}

				});

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

				} else {

					self.getRangeData(self.args.elementsIds[0]);
				}

			} else {

				if (self.list) {

					self.getRangeData(self.list.getSelectedItems()[0].id);

				} else {

					self.getRangeData(self.args.elementsIds[0]);
				}
			}
		});
	}

	getRangeData(ids) {

		let self = this;
		let element = window.em.getByField(EntityType.ELEMENT, "id", ids);
		let alarmConfigsArr = self.alarmConfigs.filter(e => e.elementType.id === element.elementType.id);
		let dataArrAux = [];
		let dataArr = [];
		let aConfigsArr = [];
		let sameElementArr = self.alarms.filter(e => e.targetElement.id === ids);

		alarmConfigsArr.forEach(aConf => {

			if (self.alarms.find(alarm => alarm.alarmConfig.id === aConf.id)) {

				aConfigsArr.push(aConf);
			}
		});

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

			if (Array.isArray(dataArrAux) && dataArrAux.length > 0) {

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
		}

		getDataSeries();

		function getDataSeries() {

			self.dataSet = [];
			self.dataSeries = [];

			let firstData;
			let tooltipText;
			let alias;
			let numOfSerie = 0;

			for (let i = 0; i < dataArr.length; i++) {

				let d = {};

				for (let j = 0; j < aConfigsArr.length; j++) {

					let elId = aConfigsArr[j].id;
					let id = elId.replaceAll(":", "");
					let bgColor = self.listLegend.find(e => e.id === elId).color;

					if (i === 0) {

						let el = window.em.getByField(EntityType.ALARM_CONFIG, "id", elId);
						alias = i18n.t(el.labelAlias);
						firstData = dataArr.find(e => e['yValue' + id] != undefined);

						tooltipText = (firstData && firstData.labelAlias) ? i18n.t(firstData.labelAlias) : "";
						let firstDate = new Date(self.dateIni).toLocaleString();

						let tooltipHTML = `<div>
												<span>${firstDate}</span><br>
												<span>${tooltipText}</span><br>
												</div>`;

						d['yValue' + id + numOfSerie] = (dataArr[i]['yValue' + id]) ? j + 1 : undefined;
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
							d['yValue' + id + numOfSerie] = (dataArr[i]['yValue' + id]) ? j + 1 : undefined;
							d['element' + id] = alias;
							d['withData' + id] = dataArr[i]['withData' + id];
							d['numOfSerie' + id] = numOfSerie;
							d['tooltip' + id + numOfSerie] = tooltipHTML;
							d['bG' + id + numOfSerie + j] = j + 1;

							self.dataSeries.push({
								value: "#yValue" + id + numOfSerie + "#",
								line: {
									width: 15,
									color: color
								},
								tooltip: {
									template: function(obj) {

										return obj['tooltip' + id + obj['numOfSerie' + id]];
									}
								}
							});
						} else if ((lastValue['withData' + id] === true) && (dataArr[i]['withData' + id] === false)) {

							d['yValue' + id + numOfSerie] = j + 1;
							d['element' + id] = alias;
							d['withData' + id] = dataArr[i]['withData' + id];
							d['numOfSerie' + id] = lastValue['numOfSerie' + id];
							d['tooltip' + id + numOfSerie] = lastValue['tooltip' + id + numOfSerie];
							d['bG' + id + numOfSerie + j] = j + 1;

						} else {

							d['yValue' + id + numOfSerie] = (dataArr[i]['yValue' + id]) ? j + 1 : undefined;
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
				$$("chart-" + self.uid).refresh();
				$$("chart-" + self.uid).show();
				$$("loading-chart-" + self.uid).hide();
			}
		}
	}
}