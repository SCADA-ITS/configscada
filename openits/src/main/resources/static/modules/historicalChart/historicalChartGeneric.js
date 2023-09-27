import { util } from "../../lib/util.js";
import HistoricalChartModule from "./historicalChartModule.js";
import ListView from "../../component/listview.js";
import PrintPreview from "../../component/printPreview.js";

const DEFAULT_INIT_TIMES = {
	RANGE: 24,
	PERIOD: 60,
	NUM_OF_MEASURES: 500,
	REAL_TIME: 60
};

export default class HistoricalChartGeneric {

	constructor(args, parent) {

		if (this.constructor == HistoricalChartGeneric) {

			throw new Error("Abstract classes can't be instantiated.");
		}

		this.args = args;
		this.parent = parent;
		this.dateIni = this.args.dates.dateIni;
		this.dateEnd = this.args.dates.dateEnd;
		this.elementsIds = this.args.elementsIds;
		this.selectedRangeInHours = undefined;
		this.initRange = undefined;
		this.initCountdown = undefined;
		this.realTimeInterval = undefined;
		this.isCharging = false;
		this.xAxis = [];

		this.uid = parent.uid;
	}

	initialize() {

		throw new Error("Method 'initialize()' must be implemented.");
	}


	async getData() {

		let self = this;
		let body;

		body = {
			dateEnd: self.dateEnd,
			dateIni: self.dateIni,
			elementIds: self.elementsIds
		};

		let response = await webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.args.url, JSON.stringify(body));
		self.isCharging = false;

		return response;
	}


	calculateTimeRanges() {

		let self = this;
		self.xAxis = [];
		let xAxisAux = [];
		let rangeInHours;
		let initPeriod;
		let defaultInitPeriodInMinutes = (self.parent.config.defaultInitPeriodInMinutes) ? self.parent.config.defaultInitPeriodInMinutes : DEFAULT_INIT_TIMES.PERIOD;

		if (self.selectedRangeInHours) {

			rangeInHours = self.selectedRangeInHours;

		} else {

			rangeInHours = (self.parent.config.defaultInitRangeInHours) ? self.parent.config.defaultInitRangeInHours : DEFAULT_INIT_TIMES.RANGE;
		}

		if (rangeInHours < 1) {

			self.initRange = "minute";
			self.initCountdown = rangeInHours * -60;

		} else if (rangeInHours >= 1 && rangeInHours < 24) {

			self.initRange = "hour";
			self.initCountdown = -rangeInHours;

		} else {

			self.initRange = "day";
			self.initCountdown = -(rangeInHours / 24);
		}

		if (self.args.mode.id === HistoricalChartModule.MODE_ELEMENT_VALUES.id) {

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

		return xAxisAux.reverse();
	}


	drawComponent() {

		let self = this;
		let viewMain;

		viewMain = {
			id: "view-main-" + self.uid,
			rows: [
				getCalendar(),
				getChart()
			],
			on: {
				onDestruct: function() {

					clearInterval(self.realTimeInterval);
				}
			}
		};

		$$("loading-" + self.parent.uid).hide();
		webix.ui(viewMain, $$(self.parent.uid));

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
						cols: [
							{
								gravity: 2
							},
							{
								view: "icon",
								icon: "mdi mdi-printer",
								value: i18n.t("LBL_TABLE_PRINT"),
								css: { "padding-right": "15px" },
								tooltip: true,
								on: {
									onItemClick: function() {

										let chart = $$("chart-" + self.uid)

										if (chart) {

											let chartNode = chart.getNode();

											html2canvas(chartNode).then(function(canvas) {

												let element;
												let title = self.label;
												let image = canvas.toDataURL();

												if (self.args.isEmbed) {

													element = window.em.getByField(EntityType.ELEMENT, "id", self.args.elementsIds[0]);

													title += " - " + element.alias;
												} 

												let options = {
													canvas: image,
													title: title
												};

												let chartToPrint = new PrintPreview(PrintPreview.TYPE_CHART, null, options);
												chartToPrint.show();
											});
										}
									}
								}
							},
							{
								view: "checkbox",
								label: i18n.t("LBL_HISTORICAL_CHART_REAL_TIME"),
								labelWidth: 85,
								minWidth: 200,
								gravity: 1,
								align: "right",
								on: {
									onChange: function(newv) {

										clearInterval(self.realTimeInterval);

										if (newv === 1) {

											startRealTime();

										} else {

											$$("daterangepicker-" + self.uid).enable();
										}
									}
								}
							}
						]
					},
					{
						id: "daterangepicker-" + self.uid,
						view: "daterangepicker",
						name: "date",
						width: 500,
						label: i18n.t("LBL_DATE"),
						value: { start: new Date(), end: webix.Date.add(new Date(), self.initCountdown, self.initRange) },
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

														$$("loading-chart-" + self.parent.uid).show();
														$$("chart-" + self.parent.uid).hide();

														self.isCharging = true;
														self.dateIni = new Date(daterangepicker.getValue().start).getTime();
														self.dateEnd = new Date(daterangepicker.getValue().end).getTime();
														self.selectedRangeInHours = (self.dateEnd - self.dateIni) / (60000 * 60);

														self.initialize();
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

			if (self.args.mode.id != HistoricalChartModule.MODE_STATES.id) {

				list = {
					width: 300,
					hidden: (self.parent.isEmbed && self.args.mode.id === HistoricalChartModule.MODE_ALARMS.id),
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
						tooltip: {
							template: function(obj) {

								return obj.yValue;
							}
						},
						data: self.dataSet,
						series: self.dataSeries
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
				self.getRangeData(evSelected.id);
			}

			let listOptions = {};

			listOptions[ListView.Options.MODE] = ListView.DrawMode.COMPACT;
			listOptions[ListView.Options.SEARCH] = false;

			self.list = new ListView(self.listElements, listOptions, listEvents);
			listview = self.list.getView();

			return listview;
		}

		function startRealTime() {

			let realTimePeriodInSeconds = (self.parent.config.defaultRealTimePeriodInSeconds) ? self.parent.config.defaultRealTimePeriodInSeconds : DEFAULT_INIT_TIMES.REAL_TIME;
			let datePicker = $$("daterangepicker-" + self.uid);

			self.dateEnd = new Date().getTime();
			self.selectedRangeInHours = (self.dateEnd - self.dateIni) / (60000 * 60);

			if (datePicker) {

				datePicker.disable();
				datePicker.setValue(
					{ start: new Date(self.dateEnd), end: new Date(self.dateIni) }
				);
			}

			self.initialize();

			self.realTimeInterval = setInterval(function() {

				self.dateIni = self.dateIni + (realTimePeriodInSeconds * 1000);
				self.dateEnd = new Date().getTime();

				if (datePicker) {

					datePicker.setValue(
						{ start: new Date(self.dateEnd), end: new Date(self.dateIni) }
					);
				}

				self.initialize();

			}, realTimePeriodInSeconds * 1000);
		}
	}
}