import { util } from "../lib/util.js";
import MessageBox from "./messageBox.js";

const DAYS_ARR = [i18n.t("LBL_SUNDAY"), i18n.t("LBL_MONDAY"), i18n.t("LBL_TUESDAY"), i18n.t("LBL_WEDNESDAY"), i18n.t("LBL_THURSDAY"), i18n.t("LBL_FRIDAY"), i18n.t("LBL_SATURDAY")];
const DAYSCRON_ARR = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];

export default class CronUi {

	constructor(input_id, popupId) {

		let input = input_id;
		var popupId = popupId;

		this.uid = webix.uid();
		this.input = input;
		this.cronWindow = undefined;
		this.windowLabel = undefined;
		this.daysOfWeek = [];
		this.daysOfWeekCron = [];
		this.checkedMins = [];
		this.checkedHours = [];
		this.checkedDays = [];
		this.checkedDaysWeek = [];
		this.checkedMonths = [];
		this.checkedYears = [];
		this.cronExpressionArr = ["0", "*", "*", "?", "*", "*", "*"];
		this.cronExpressionEditArr = [];
		this.currentTab = undefined;
	}

	getComponent(cronExpression) {

		let self = this;
		let editCronExpression = $$(self.input).elements.cronExpression.data.value;
		let userData = util.user.auth();
		let userLocale = userData.locale.code.split("_")[0];
		let localLocale = moment();

		localLocale.locale(userLocale);
		let startDay = localLocale._locale._week.dow;

		if (localLocale._locale._week.dow <= DAYS_ARR.length) {

			let temporalArr = webix.copy(DAYS_ARR);
			let temporalCronArr = webix.copy(DAYSCRON_ARR);

			for (let i = 0; i < startDay; i++) {

				let deleteDay = temporalArr.shift();
				temporalArr.push(deleteDay);

				let deleteCronDay = temporalCronArr.shift();
				temporalCronArr.push(deleteCronDay);
			}

			self.daysOfWeek = temporalArr;
			self.daysOfWeekCron = temporalCronArr;

		}

		let genericForm = {
			id: "win_body-" + self.uid,
			rows: [
				{ height: 10 },
				getViewHeader(cronExpression),
				{ height: 10 },
				{
					cols: [
						{ width: 6 },
						{
							id: "loading-" + this.uid,
							rows: [util.ui.getViewDrawLoading()],
							hidden: true
						},
						{
							id: this.uid,
							hidden: false,
							rows: [
								drawForm(),
								drawResume(),
								drawCurrentResume(),
								drawButtons()
							]
						},
						{ width: 6 }
					]
				}
			]
		};


		function getViewHeader(cronExpression) {

			let header = {};
			let label = undefined;
			let description = undefined;

			if (cronExpression === null) {

				label = "LBL_NEW_SCHEDULER";
				description = "LBL_NEW_SCHEDULER_DESCRIPTION";
				self.windowLabel = "LBL_NEW_SCHEDULER";

			} else {

				label = "LBL_EDIT_SCHEDULER";
				description = "LBL_EDIT_SCHEDULER_DESCRIPTION";
				self.windowLabel = "LBL_EDIT_SCHEDULER";
			}

			header = {
				id: "viewHeader-" + self.uid,
				cols: [
					{
						view: "label",
						label: "<img src='img/grid/sheduler.png' class='grid-icon'/>",
						height: 75,
						css: "global-header-icon"
					},
					{
						view: "label",
						height: 75,
						id: "label-bottom" + self.uid,
						template: "<span>" + i18n.t(label) + "</span> <br> <span style='font-size: 13px'>" + i18n.t(description) + "</span>",
						css: "global-header-label"
					}
				]
			};

			return header;
		}


		function drawForm() {

			let form = {
				id: "tabview_CronUi-" + self.uid,
				view: "tabview",
				css: "secondary-tab",
				width: 780,
				height: 335,
				tabbar: {
					css: { "width": "780px !important" },
					tabMargin: 6,
					options: [
						{ id: "cronUi_minutes-" + self.uid, value: i18n.t("LBL_MINUTES") },
						{ id: "cronUi_hours-" + self.uid, value: i18n.t("LBL_HOURS") },
						{ id: "cronUi_days-" + self.uid, value: i18n.t("LBL_DAYS") },
						{ id: "cronUi_months-" + self.uid, value: i18n.t("LBL_MONTHS") },
						{ id: "cronUi_years-" + self.uid, value: i18n.t("LBL_YEARS") }
					],
					on: {
						onChange: function(newv, oldv) {
							self.currentTab = newv;
						}
					}
				},
				cells: [
					{
						view: "form",
						id: "cronUi_minutes-" + self.uid,
						elements: [
							{
								css: { "border": "1px solid #dadee0", "box-sizing": "border-box" },
								cols: [
									{ width: 5 },
									{
										width: 250,
										height: 250,
										rows: [
											{ height: 55 },
											{
												view: "radio",
												id: "minutesOpt-" + self.uid,
												width: 245,
												value: "minutesOpt01",
												vertical: true,
												multiview: true,
												on: {
													onChange: function(newv, oldv) {
														$$(newv).show();
														calculateMinutes(newv);
													}
												}
												,
												options: [
													{ id: "minutesOpt01", value: i18n.t("LBL_EVERY_MINUTE") },
													{ id: "minutesOpt02", value: i18n.t("LBL_EVERY_X_MINUTES") },
													{ id: "minutesOpt03", value: i18n.t("LBL_SPECIFIC_MINUTE") },
													{ id: "minutesOpt04", value: i18n.t("LBL_EVERY_MINUTE_BETWEEN") }
												]
											}]
									},
									{
										view: "multiview",
										height: 250,
										width: 500,
										id: "multi_minutes-" + self.uid,
										animate: false,
										scroll: "auto",
										borderless: true,
										css: { "border-left": "1px solid #dadee0" },
										cells: [
											{ id: "minutesOpt01", view: "form", elements: drawMultiview("minutesOpt01") },
											{ id: "minutesOpt03", view: "form", elements: drawMultiview("minutesOpt03") },
											{ id: "minutesOpt02", view: "form", elements: drawMultiview("minutesOpt02") },
											{ id: "minutesOpt04", view: "form", elements: drawMultiview("minutesOpt04") }
										]
									}
								]
							}
						]
					},
					{
						view: "form",
						id: "cronUi_hours-" + self.uid,
						elements: [
							{
								css: { "border": "1px solid #dadee0", "box-sizing": "border-box" },
								cols: [
									{ width: 5 },
									{
										width: 250,
										height: 250,
										rows: [
											{ height: 55 },
											{
												view: "radio",
												id: "hoursOpt-" + self.uid,
												width: 245,
												value: "hoursOpt01",
												vertical: true,
												multiview: true,
												on: {
													onChange: function(newv, oldv) {
														$$(newv).show();
														calculateHours(newv);
													}
												}
												,
												options: [
													{ id: "hoursOpt01", value: i18n.t("LBL_EVERY_HOUR") },
													{ id: "hoursOpt02", value: i18n.t("LBL_EVERY_X_HOURS") },
													{ id: "hoursOpt03", value: i18n.t("LBL_SPECIFIC_HOUR") },
													{ id: "hoursOpt04", value: i18n.t("LBL_EVERY_HOUR_BETWEEN") }
												]
											}]
									},
									{
										view: "multiview",
										height: 250,
										width: 500,
										id: "multi_hours-" + self.uid,
										animate: false,
										scroll: "auto",
										borderless: true,
										css: { "border-left": "1px solid #dadee0" },
										cells: [
											{ id: "hoursOpt01", view: "form", elements: drawMultiview("hoursOpt01") },
											{ id: "hoursOpt03", view: "form", elements: drawMultiview("hoursOpt03") },
											{ id: "hoursOpt02", view: "form", elements: drawMultiview("hoursOpt02") },
											{ id: "hoursOpt04", view: "form", elements: drawMultiview("hoursOpt04") }
										]
									}
								]
							}
						]
					},
					{
						view: "form",
						id: "cronUi_days-" + self.uid,
						elements: [
							{
								css: { "border": "1px solid #dadee0", "box-sizing": "border-box" },
								cols: [
									{ width: 5 },
									{
										width: 250,
										height: 250,
										rows: [
											{ height: 40 },
											{
												view: "radio",
												id: "daysOpt-" + self.uid,
												width: 245,
												value: "daysOpt01",
												vertical: true,
												multiview: true,
												on: {
													onChange: function(newv, oldv) {
														$$(newv).show();
														calculateDays(newv);
													}
												}
												,
												options: [
													{ id: "daysOpt01", value: i18n.t("LBL_EVERY_DAY") },
													{ id: "daysOpt02", value: i18n.t("LBL_EVERY_X_DAYS") },
													{ id: "daysOpt04", value: i18n.t("LBL_EVERY_X_DAYS") },
													{ id: "daysOpt05", value: i18n.t("LBL_SPECIFIC_DAY_WEEK") },
													{ id: "daysOpt03", value: i18n.t("LBL_SPECIFIC_DAY_MONTH") }
												]
											}]
									},
									{
										view: "multiview",
										height: 250,
										width: 500,
										id: "multi_days-" + self.uid,
										animate: false,
										scroll: "auto",
										borderless: true,
										css: { "border-left": "1px solid #dadee0" },
										cells: [
											{ id: "daysOpt01", view: "form", elements: drawDays("daysOpt01") },
											{ id: "daysOpt03", view: "form", elements: drawDays("daysOpt03") },
											{ id: "daysOpt02", view: "form", elements: drawDays("daysOpt02") },
											{ id: "daysOpt05", view: "form", elements: drawDays("daysOpt05") },
											{ id: "daysOpt04", view: "form", elements: drawDays("daysOpt04") }
										]
									}
								]
							}
						]
					},
					{
						view: "form",
						id: "cronUi_months-" + self.uid,
						elements: [
							{
								css: { "border": "1px solid #dadee0", "box-sizing": "border-box" },
								cols: [
									{ width: 5 },
									{
										width: 250,
										height: 250,
										rows: [
											{ height: 55 },
											{
												view: "radio",
												id: "monthsOpt-" + self.uid,
												width: 245,
												value: "monthsOpt01",
												vertical: true,
												multiview: true,
												on: {
													onChange: function(newv, oldv) {
														$$(newv).show();
														calculateMonths(newv);
													}
												}
												,
												options: [
													{ id: "monthsOpt01", value: i18n.t("LBL_EVERY_MONTH") },
													{ id: "monthsOpt02", value: i18n.t("LBL_EVERY_X_MONTHS") },
													{ id: "monthsOpt03", value: i18n.t("LBL_SPECIFIC_MONTH") },
													{ id: "monthsOpt04", value: i18n.t("LBL_EVERY_MONTH_BETWEEN") }
												]
											}]
									},
									{
										view: "multiview",
										height: 250,
										width: 500,
										id: "multi_months-" + self.uid,
										animate: false,
										scroll: "auto",
										borderless: true,
										css: { "border-left": "1px solid #dadee0" },
										cells: [
											{ id: "monthsOpt01", view: "form", elements: drawMonths("monthsOpt01") },
											{ id: "monthsOpt03", view: "form", elements: drawMonths("monthsOpt03") },
											{ id: "monthsOpt02", view: "form", elements: drawMonths("monthsOpt02") },
											{ id: "monthsOpt04", view: "form", elements: drawMonths("monthsOpt04") }
										]
									}
								]
							}
						]
					},
					{
						view: "form",
						id: "cronUi_years-" + self.uid,
						elements: [
							{
								css: { "border": "1px solid #dadee0", "box-sizing": "border-box" },
								cols: [
									{ width: 5 },
									{
										width: 250,
										height: 250,
										rows: [
											{ height: 55 },
											{
												view: "radio",
												id: "yearsOpt-" + self.uid,
												width: 245,
												value: "yearsOpt01",
												vertical: true,
												multiview: true,
												on: {
													onChange: function(newv, oldv) {
														$$(newv).show();
														calculateYears(newv);
													}
												}
												,
												options: [
													{ id: "yearsOpt01", value: i18n.t("LBL_ANY_YEAR") },
													{ id: "yearsOpt02", value: i18n.t("LBL_EVERY_X_YEARS") },
													{ id: "yearsOpt03", value: i18n.t("LBL_SPECIFIC_YEAR") },
													{ id: "yearsOpt04", value: i18n.t("LBL_EVERY_YEARS_BETWEEN") }
												]
											}]
									},
									{
										view: "multiview",
										height: 250,
										width: 500,
										id: "multi_years-" + self.uid,
										animate: false,
										scroll: "auto",
										borderless: true,
										css: { "border-left": "1px solid #dadee0" },
										cells: [
											{ id: "yearsOpt01", view: "form", elements: drawMonths("yearsOpt01") },
											{ id: "yearsOpt03", view: "form", elements: drawMonths("yearsOpt03") },
											{ id: "yearsOpt02", view: "form", elements: drawMonths("yearsOpt02") },
											{ id: "yearsOpt04", view: "form", elements: drawMonths("yearsOpt04") }
										]
									}
								]
							}
						]
					}
				]
			};

			return form;
		}


		function drawMultiview(id) {

			let view = [{ rows: [{ cols: [] }] }];
			const MINUTES_OPT01 = "minutesOpt01";
			const MINUTES_OPT02 = "minutesOpt02";
			const MINUTES_OPT03 = "minutesOpt03";
			const MINUTES_OPT04 = "minutesOpt04";
			const HOURS_OPT01 = "hoursOpt01";
			const HOURS_OPT02 = "hoursOpt02";
			const HOURS_OPT03 = "hoursOpt03";
			const HOURS_OPT04 = "hoursOpt04";

			if (id === MINUTES_OPT03 || id === HOURS_OPT03) {

				let ids = (id === MINUTES_OPT03) ? MINUTES_OPT03 : HOURS_OPT03;

				let row1 = [{ rows: [] }];
				let row2 = [{ rows: [] }];
				let row3 = [{ rows: [] }];
				let row4 = [{ rows: [] }];
				let row5 = [{ rows: [] }];
				let row6 = [{ rows: [] }];
				let rowHour = [{ rows: [] }];
				let column1 = [];
				let column2 = [];
				let column3 = [];
				let column4 = [];
				let column5 = [];
				let column6 = [];
				let columnHour = [];

				for (let i = 0; i <= 9; i++) {

					let checkLabel = (i === 0) ? "00" : "0" + i;

					let check = {
						view: "checkbox",
						id: ids + "-" + i,
						margin: 0,
						padding: 0,
						label: checkLabel,
						value: 0,
						width: 48,
						labelWidth: 17,
						css: "cronui-checks",
						click: function(id, event) {
							timeSelector(ids + "-" + i);
						}
					}

					column1.push(check);
				}

				for (let i = 10; i <= 19; i++) {

					let check = {

						view: "checkbox",
						id: ids + "-" + i,
						margin: 0,
						padding: 0,
						label: i,
						value: 0,
						width: 48,
						labelWidth: 17,
						css: "cronui-checks",
						click: function(id, event) {
							timeSelector(ids + "-" + i);
						}
					}

					column2.push(check);
				}

				for (let i = 20; i <= 29; i++) {

					let check = {

						view: "checkbox",
						id: ids + "-" + i,
						margin: 0,
						padding: 0,
						label: i,
						value: 0,
						width: 48,
						labelWidth: 17,
						css: "cronui-checks",
						click: function(id, event) {
							timeSelector(ids + "-" + i);
						}
					}

					column3.push(check);
				}

				for (let i = 30; i <= 39; i++) {

					let check = {

						view: "checkbox",
						id: ids + "-" + i,
						margin: 0,
						padding: 0,
						label: i,
						value: 0,
						width: 48,
						labelWidth: 17,
						css: "cronui-checks",
						click: function(id, event) {
							timeSelector(ids + "-" + i);
						}
					}

					column4.push(check);
				}

				for (let i = 40; i <= 49; i++) {

					let check = {

						view: "checkbox",
						id: ids + "-" + i,
						margin: 0,
						padding: 0,
						label: i,
						value: 0,
						width: 48,
						labelWidth: 17,
						css: "cronui-checks",
						click: function(id, event) {
							timeSelector(ids + "-" + i);
						}
					}

					column5.push(check);
				}

				for (let i = 50; i <= 59; i++) {

					let check = {

						view: "checkbox",
						id: ids + "-" + i,
						margin: 0,
						padding: 0,
						label: i,
						value: 0,
						width: 48,
						labelWidth: 17,
						css: "cronui-checks",
						click: function(id, event) {
							timeSelector(ids + "-" + i);
						}
					}

					column6.push(check);
				}

				if (id === MINUTES_OPT03) {

					row1 = [{ cols: column1 }];
					row2 = [{ cols: column2 }];
					row3 = [{ cols: column3 }];
					row4 = [{ cols: column4 }];
					row5 = [{ cols: column5 }];
					row6 = [{ cols: column6 }];

					view[0].rows[0] = row1[0];
					view[0].rows[1] = row2[0];
					view[0].rows[2] = row3[0];
					view[0].rows[3] = row4[0];
					view[0].rows[4] = row5[0];
					view[0].rows[5] = row6[0];

				} else if (id === HOURS_OPT03) {

					for (let i = 20; i <= 23; i++) {

						let check = {

							view: "checkbox",
							id: ids + "-" + i,
							margin: 0,
							padding: 0,
							label: i,
							value: 0,
							width: 48,
							labelWidth: 17,
							css: "cronui-checks",
							click: function(id, event) {
								timeSelector(ids + "-" + i);
							}
						}

						columnHour.push(check);
					}

					row1 = [{ cols: column1 }];
					row2 = [{ cols: column2 }];
					rowHour = [{ cols: columnHour }];

					view[0].rows[0] = row1[0];
					view[0].rows[1] = row2[0];
					view[0].rows[2] = rowHour[0];
				}

			} else if (id === MINUTES_OPT02 || id === HOURS_OPT02) {

				let ids = (id === MINUTES_OPT02) ? MINUTES_OPT02 : HOURS_OPT02;
				let label = (id === MINUTES_OPT02) ? "LBL_MINUTE(S)_STARTING_AT_MINUTE" : "LBL_HOUR(S)_STARTING_AT_HOUR";
				let max1 = (id === HOURS_OPT02) ? 24 : 60;
				let max2 = (id === HOURS_OPT02) ? 23 : 59;

				view = [
					{
						rows: [
							{ height: 70 },
							{
								cols: [
									{
										view: "label",
										width: 360,
										label: i18n.t("LBL_EVERY"),
									},
									{
										view: "counter",
										id: ids + "_counter01",
										name: "",
										width: 105,
										value: 1,
										min: 1,
										max: max1,
										on: {
											onItemClick:
												function(id, e) {
													timeSelector(id);
												},
											onTimedKeyPress:
												function() {
													timeSelector(id);
												}
										}
									}]
							},
							{
								cols: [
									{
										view: "label",
										label: i18n.t(label),
										width: 360,
										align: "left"
									},
									{
										view: "counter",
										id: ids + "_counter02",
										name: "",
										width: 105,
										value: 0,
										min: 0,
										max: max2,
										on: {
											onItemClick:
												function(id, e) {
													timeSelector(id);
												},
											onTimedKeyPress:
												function() {
													timeSelector(id);
												}
										}
									}
								]
							}]
					}
				]

			} else if (id === MINUTES_OPT04 || id === HOURS_OPT04) {

				let ids = (id === MINUTES_OPT04) ? MINUTES_OPT04 : HOURS_OPT04;
				let label1 = (id === MINUTES_OPT04) ? "LBL_EVERY_MINUTE_BETWEEN_MINUTE" : "LBL_EVERY_HOUR_BETWEEN_HOUR";
				let label2 = (id === MINUTES_OPT04) ? "LBL_AND_MINUTE" : "LBL_AND_HOUR";
				let max = (id === HOURS_OPT04) ? 23 : 59;

				view = [
					{
						rows: [
							{ height: 70 },
							{
								cols: [
									{
										view: "label",
										width: 360,
										label: i18n.t(label1)
									},
									{
										view: "counter",
										id: ids + "_counter01",
										name: "",
										width: 105,
										value: 0,
										min: 0,
										max: max,
										on: {
											onItemClick:
												function(id, e) {
													timeSelector(id);
												},
											onTimedKeyPress:
												function() {
													timeSelector(id);
												}
										}
									}]
							},
							{
								cols: [
									{
										view: "label",
										label: i18n.t(label2),
										width: 360,
										align: "left"
									},
									{
										view: "counter",
										id: ids + "_counter02",
										name: "",
										width: 105,
										value: 0,
										min: 0,
										max: max,
										on: {
											onItemClick:
												function(id, e) {
													timeSelector(id);
												},
											onTimedKeyPress:
												function() {
													timeSelector(id);
												}
										}
									}
								]
							}]
					}
				]

			} else if (id === MINUTES_OPT01 || id === HOURS_OPT01) {

				let label = (id === MINUTES_OPT01) ? "LBL_EVERY_MINUTE" : "LBL_EVERY_HOUR";

				view = [
					{
						rows: [
							{ height: 80 },
							{
								view: "label",
								label: i18n.t(label)
							}
						]
					}
				]
			}

			return view;
		}


		function drawDays(id) {

			let view = [{ rows: [{ cols: [] }] }];
			const DAYS_OPT01 = "daysOpt01";
			const DAYS_OPT02 = "daysOpt02";
			const DAYS_OPT03 = "daysOpt03";
			const DAYS_OPT04 = "daysOpt04";
			const DAYS_OPT05 = "daysOpt05";

			if (id === DAYS_OPT03) {

				let row1 = [{ rows: [] }];
				let row2 = [{ rows: [] }];
				let row3 = [{ rows: [] }];
				let row4 = [{ rows: [] }];

				let column1 = [];
				let column2 = [];
				let column3 = [];
				let column4 = [];

				for (let i = 1; i <= 10; i++) {

					let checkLabel = (i === 0) ? "00" : (i === 10) ? i : "0" + i;

					let check = {
						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: checkLabel,
						value: 0,
						width: 48,
						labelWidth: 17,
						css: "cronui-checks",
						click: function(id, event) {
							timeSelector(id);
						}
					}

					column1.push(check);
				}

				for (let i = 11; i <= 20; i++) {

					let check = {
						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: i,
						value: 0,
						width: 48,
						labelWidth: 17,
						css: "cronui-checks",
						click: function(id, event) {
							timeSelector(id);
						}
					}

					column2.push(check);
				}

				for (let i = 21; i <= 30; i++) {

					let check = {
						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: i,
						value: 0,
						width: 48,
						labelWidth: 17,
						css: "cronui-checks",
						click: function(id, event) {
							timeSelector(id);
						}
					}

					column3.push(check);
				}

				let check31 = {
					view: "checkbox",
					id: id + "-" + 31,
					margin: 0,
					padding: 0,
					label: 31,
					value: 0,
					width: 48,
					labelWidth: 17,
					css: "cronui-checks",
					click: function(id, event) {
						timeSelector(id);
					}
				}

				column4.push(check31);


				row1 = [{ cols: column1 }];
				row2 = [{ cols: column2 }];
				row3 = [{ cols: column3 }];
				row4 = [{ cols: column4 }];

				view[0].rows[0] = row1[0];
				view[0].rows[1] = row2[0];
				view[0].rows[2] = row3[0];
				view[0].rows[3] = row4[0];

			} else if (id === DAYS_OPT02) {

				view = [
					{
						rows: [
							{ height: 70 },
							{
								cols: [
									{
										view: "label",
										width: 360,
										label: i18n.t("LBL_EVERY"),
									},
									{
										view: "counter",
										id: id + "_counter01",
										name: "",
										width: 105,
										value: 1,
										min: 1,
										max: 7,
										on: {
											onItemClick:
												function(id, e) {
													timeSelector(id);
												},
											onTimedKeyPress:
												function() {
													timeSelector(id);
												}
										}
									}]
							},
							{
								cols: [
									{
										view: "label",
										label: i18n.t("LBL_DAY(S)_STARTING_ON"),
										width: 300,
										align: "left"
									},
									{
										view: "combo",
										id: id + "_counter02",
										name: "",
										width: 166,
										value: self.daysOfWeek[0],
										options: webix.copy(self.daysOfWeek),
										on:
										{
											onChange:
												function(newv, oldv) {
													timeSelector(id)
												}
										}
									}
								]
							}]
					}
				]
			}

			else if (id === DAYS_OPT05) {

				let row1 = [{ rows: [] }];
				let row2 = [{ rows: [] }];
				let row3 = [{ rows: [] }];
				let row4 = [{ rows: [] }];
				let column1 = [];
				let column2 = [];
				let column3 = [];
				let column4 = [];

				for (let i = 0; i <= 1; i++) {

					let days = {
						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: self.daysOfWeek[i],
						value: 0,
						width: 245,
						labelWidth: 175,
						click: function(id, event) {
							timeSelector(id);
						}
					}
					column1.push(days);
				}

				for (let i = 2; i <= 3; i++) {

					let days = {
						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: self.daysOfWeek[i],
						value: 0,
						width: 245,
						labelWidth: 175,
						click: function(id, event) {
							timeSelector(id);
						}
					}
					column2.push(days);
				}

				for (let i = 4; i <= 5; i++) {

					let days = {
						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: self.daysOfWeek[i],
						value: 0,
						width: 245,
						labelWidth: 175,
						click: function(id, event) {
							timeSelector(id);
						}
					}
					column3.push(days);
				}

				let day7 = {
					view: "checkbox",
					id: id + "-" + 6,
					margin: 0,
					padding: 0,
					label: self.daysOfWeek[6],
					value: 0,
					width: 500,
					labelWidth: 175,
					click: function(id, event) {
						timeSelector(id);
					}
				}

				column4.push(day7);

				row1 = [{ cols: column1 }];
				row2 = [{ cols: column2 }];
				row3 = [{ cols: column3 }];
				row4 = [{ cols: column4 }];

				view[0].rows[0] = row1[0];
				view[0].rows[1] = row2[0];
				view[0].rows[2] = row3[0];
				view[0].rows[3] = row4[0];

			} else if (id === DAYS_OPT04) {

				let ordinalDays = [];
				for (let i = 1; i <= 31; i++) {

					ordinalDays.push(String(i));
				}

				view = [
					{
						rows: [
							{ height: 70 },
							{
								cols: [
									{
										view: "label",
										width: 360,
										label: i18n.t("LBL_EVERY")
									},
									{
										view: "counter",
										id: id + "_counter01",
										name: "",
										width: 105,
										value: 1,
										min: 1,
										max: 31,
										on: {
											onItemClick:
												function(id, e) {
													timeSelector(id);
												},
											onTimedKeyPress:
												function() {
													timeSelector(id);
												}
										}
									}]
							},
							{
								cols: [
									{
										view: "label",
										label: i18n.t("LBL_STARTING_DAY_Nº"),
										width: 200,
										align: "left"
									},
									{
										view: "combo",
										id: id + "_counter02",
										name: "",
										width: 80,
										value: "1",
										options: webix.copy(ordinalDays),
										on: {
											onChange:
												function(newv, oldv) {
													timeSelector(id)
												}
										}
									},
									{
										view: "label",
										label: i18n.t("LBL_OF_THE_MONTH"),
										width: 185,
										align: "left"
									},
								]
							}]
					}
				]

			} else if (id === DAYS_OPT01) {

				view = [
					{
						rows: [
							{ height: 80 },
							{
								view: "label",
								label: i18n.t("LBL_EVERY_DAY")
							}
						]
					}
				]
			}

			return view;
		}


		function drawMonths(id) {

			let view = [{ rows: [{ cols: [] }] }];

			const MONTHS_OPT01 = "monthsOpt01";
			const MONTHS_OPT02 = "monthsOpt02";
			const MONTHS_OPT03 = "monthsOpt03";
			const MONTHS_OPT04 = "monthsOpt04";
			const YEARS_OPT01 = "yearsOpt01";
			const YEARS_OPT02 = "yearsOpt02";
			const YEARS_OPT03 = "yearsOpt03";
			const YEARS_OPT04 = "yearsOpt04";

			let monthsOfYear = [i18n.t("LBL_JANUARY"), i18n.t("LBL_FEBRUARY"), i18n.t("LBL_MARCH"), i18n.t("LBL_APRIL"), i18n.t("LBL_MAY"), i18n.t("LBL_JUNE"), i18n.t("LBL_JULY"), i18n.t("LBL_AUGUST"), i18n.t("LBL_SEPTEMBER"), i18n.t("LBL_OCTOBER"), i18n.t("LBL_NOVEMBER"), i18n.t("LBL_DECEMBER")];
			let date = new Date().getFullYear();
			let yearsArr = [];

			for (let i = 1; i <= 50; i++) {

				yearsArr.push((date + i).toString());
			}

			if (id === MONTHS_OPT03) {

				let row1 = [{ rows: [] }];
				let row2 = [{ rows: [] }];
				let row3 = [{ rows: [] }];
				let row4 = [{ rows: [] }];
				let row5 = [{ rows: [] }];
				let row6 = [{ rows: [] }];
				let column1 = [];
				let column2 = [];
				let column3 = [];
				let column4 = [];
				let column5 = [];
				let column6 = [];

				for (let i = 0; i <= 1; i++) {

					let check = {
						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: monthsOfYear[i],
						value: 0,
						width: 245,
						labelWidth: 175,
						click: function(id, event) {
							timeSelector(id);
						}
					}

					column1.push(check);
				}

				for (let i = 2; i <= 3; i++) {

					let check = {

						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: monthsOfYear[i],
						value: 0,
						width: 245,
						labelWidth: 175,
						click: function(id, event) {
							timeSelector(id);
						}
					}

					column2.push(check);
				}

				for (let i = 4; i <= 5; i++) {

					let check = {

						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: monthsOfYear[i],
						value: 0,
						width: 245,
						labelWidth: 175,
						click: function(id, event) {
							timeSelector(id);
						}
					}

					column3.push(check);
				}

				for (let i = 6; i <= 7; i++) {

					let check = {

						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: monthsOfYear[i],
						value: 0,
						width: 245,
						labelWidth: 175,
						click: function(id, event) {
							timeSelector(id);
						}
					}

					column4.push(check);
				}

				for (let i = 8; i <= 9; i++) {

					let check = {

						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: monthsOfYear[i],
						value: 0,
						width: 245,
						labelWidth: 175,
						click: function(id, event) {
							timeSelector(id);
						}
					}

					column5.push(check);
				}

				for (let i = 10; i <= 11; i++) {

					let check = {

						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: monthsOfYear[i],
						value: 0,
						width: 245,
						labelWidth: 175,
						click: function(id, event) {
							timeSelector(id);
						}
					}

					column6.push(check);
				}

				row1 = [{ cols: column1 }];
				row2 = [{ cols: column2 }];
				row3 = [{ cols: column3 }];
				row4 = [{ cols: column4 }];
				row5 = [{ cols: column5 }];
				row6 = [{ cols: column6 }];

				view[0].rows[0] = row1[0];
				view[0].rows[1] = row2[0];
				view[0].rows[2] = row3[0];
				view[0].rows[3] = row4[0];
				view[0].rows[4] = row5[0];
				view[0].rows[5] = row6[0];

			} else if (id === MONTHS_OPT02 || id === YEARS_OPT02) {

				let ids = (id === MONTHS_OPT02) ? MONTHS_OPT02 : YEARS_OPT02;
				let label = (id === MONTHS_OPT02) ? "LBL_MONTH(S)_STARTING_IN" : "LBL_YEAR(S)_STARTING_IN";
				let maxValue = (id === MONTHS_OPT02) ? 12 : 50;
				let comboValue = (id === MONTHS_OPT02) ? monthsOfYear[0] : date;
				let comboOptions = (id === MONTHS_OPT02) ? monthsOfYear : yearsArr;
				let maxWidth1 = (id === MONTHS_OPT02) ? 300 : 360;
				let maxWidth2 = (id === MONTHS_OPT02) ? 166 : 105;

				view = [
					{
						rows: [
							{ height: 70 },
							{
								cols: [
									{
										view: "label",
										width: 360,
										label: i18n.t("LBL_EVERY"),
									},
									{
										view: "counter",
										id: ids + "_counter01",
										name: "",
										width: 105,
										value: 1,
										min: 1,
										max: maxValue,
										on: {
											onItemClick:
												function(id, e) {
													timeSelector(id);
												},
											onTimedKeyPress:
												function() {
													timeSelector(id);
												}
										}
									}]
							},
							{
								cols: [
									{
										view: "label",
										label: i18n.t(label),
										width: maxWidth1,
										align: "left"
									},
									{
										view: "combo",
										id: ids + "_counter02",
										name: "",
										width: maxWidth2,
										value: comboValue,
										options: webix.copy(comboOptions),
										on: {
											onChange:
												function(newv, oldv) {
													timeSelector(id)
												}
										}
									}
								]
							}]
					}
				]

			} else if (id === MONTHS_OPT04 || id === YEARS_OPT04) {

				let ids = (id === MONTHS_OPT04) ? MONTHS_OPT04 : YEARS_OPT04;
				let label = (id === MONTHS_OPT04) ? "LBL_EVERY_MONTH_BETWEEN" : "LBL_EVERY_YEAR_BETWEEN";
				let comboValue = (id === MONTHS_OPT04) ? monthsOfYear[0] : date;
				let comboOptions = (id === MONTHS_OPT04) ? monthsOfYear : yearsArr;
				let maxWidth1 = (id === MONTHS_OPT04) ? 300 : 360;
				let maxWidth2 = (id === MONTHS_OPT04) ? 166 : 105;

				view = [
					{
						rows: [
							{ height: 70 },
							{
								cols: [
									{
										view: "label",
										width: maxWidth1,
										label: i18n.t(label)
									},
									{
										view: "combo",
										id: ids + "_counter01",
										name: "",
										width: maxWidth2,
										value: comboValue,
										options: webix.copy(comboOptions),
										on: {
											onChange:
												function(newv, oldv) {
													timeSelector(id)
												}
										}
									}
								]
							},
							{
								cols: [
									{
										view: "label",
										label: i18n.t("LBL_AND"),
										width: maxWidth1,
										align: "left"
									},
									{
										view: "combo",
										id: ids + "_counter02",
										name: "",
										width: maxWidth2,
										value: comboValue,
										options: webix.copy(comboOptions),
										on: {
											onChange:
												function(newv, oldv) {
													timeSelector(id)
												}
										}
									}
								]
							}]
					}
				]
			}
			else if (id === MONTHS_OPT01 || id === YEARS_OPT01) {

				let label = (id === MONTHS_OPT01) ? "LBL_EVERY_MONTH" : "LBL_ANY_YEAR";

				view = [
					{
						rows: [
							{ height: 80 },
							{
								view: "label",
								label: i18n.t(label)
							}
						]
					}
				]
			} else if (id === YEARS_OPT03) {

				let row1 = [{ rows: [] }];
				let row2 = [{ rows: [] }];
				let row3 = [{ rows: [] }];
				let row4 = [{ rows: [] }];
				let row5 = [{ rows: [] }];
				let row6 = [{ rows: [] }];
				let column1 = [];
				let column2 = [];
				let column3 = [];
				let column4 = [];
				let column5 = [];
				let column6 = [];

				for (let i = date + 1; i <= date + 6; i++) {

					let check = {
						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: i,
						value: 0,
						width: 80,
						labelWidth: 41,
						click: function(id, event) {
							timeSelector(id);
						}
					}

					column1.push(check);
				}

				for (let i = date + 7; i <= date + 12; i++) {

					let check = {

						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: i,
						value: 0,
						width: 80,
						labelWidth: 41,
						click: function(id, event) {
							timeSelector(id);
						}
					}

					column2.push(check);
				}

				for (let i = date + 13; i <= date + 18; i++) {

					let check = {

						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: i,
						value: 0,
						width: 80,
						labelWidth: 41,
						click: function(id, event) {
							timeSelector(id);
						}
					}

					column3.push(check);
				}

				for (let i = date + 19; i <= date + 24; i++) {

					let check = {

						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: i,
						value: 0,
						width: 80,
						labelWidth: 41,
						click: function(id, event) {
							timeSelector(id);
						}
					}

					column4.push(check);
				}

				for (let i = date + 25; i <= date + 30; i++) {

					let check = {

						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: i,
						value: 0,
						width: 80,
						labelWidth: 41,
						click: function(id, event) {
							timeSelector(id);
						}
					}

					column5.push(check);
				}

				for (let i = date + 31; i <= date + 36; i++) {

					let check = {

						view: "checkbox",
						id: id + "-" + i,
						margin: 0,
						padding: 0,
						label: i,
						value: 0,
						width: 80,
						labelWidth: 41,
						click: function(id, event) {
							timeSelector(id);
						}
					}

					column6.push(check);
				}

				row1 = [{ cols: column1 }];
				row2 = [{ cols: column2 }];
				row3 = [{ cols: column3 }];
				row4 = [{ cols: column4 }];
				row5 = [{ cols: column5 }];
				row6 = [{ cols: column6 }];

				view[0].rows[0] = row1[0];
				view[0].rows[1] = row2[0];
				view[0].rows[2] = row3[0];
				view[0].rows[3] = row4[0];
				view[0].rows[4] = row5[0];
				view[0].rows[5] = row6[0];
			}

			return view;
		}


		function drawResume() {

			let resume = {
				css: "cronui-resume",
				id: "resume" + self.uid,
				height: 150,
				rows: [
					{ height: 15 },
					{
						width: 780,
						cols: [
							{ width: 15 },
							{
								width: 365,
								rows: [
									{
										height: 20,
										view: "label",
										align: "center",
										template: `<div><span style="padding: 0px 5px; font-size: 12px; text-align: center; color: #475269">${i18n.t("LBL_NEW_CRON_EXPRESSION")}</span></div>`
									},
									{
										view: "label",
										tooltip: true,
										align: "center",
										borderless: true,
										width: 250,
										height: 30,
										css: "cron-expression",
										id: "cronExpression" + self.uid,
										value: "0 * * ? * * *",
										label: "0 * * ? * * *"
									}
								]
							},
							{ width: 20 },
							{
								width: 365,
								rows: [
									{
										height: 20,
										view: "label",
										align: "center",
										template: `<div><span style="padding: 0px 5px; font-size: 12px; text-align: center; color: #475269">${i18n.t("LBL_DATE_TIME_NEXT_EXEC")}</span></div>`
									},
									{
										height: 25,
										view: "label",
										tooltip: true,
										align: "center",
										css: "cronui-description",
										id: "cronDescriptionId",
										label: i18n.t("LBL_DATE_TIME_NEXT_EXEC_DESCRIPTION"),
										value: i18n.t("LBL_DATE_TIME_NEXT_EXEC_DESCRIPTION")
									}
								]
							}
						]
					}
				]
			}

			return resume;
		}


		function drawCurrentResume() {

			let currentResume = {
				css: "cronui-current-resume",
				id: "currentResume" + self.uid,
				rows: [
					{ height: 15 },
					{
						width: 780,
						cols: [
							{ width: 15 },
							{
								width: 365,
								rows: [
									{
										height: 20,
										view: "label",
										align: "center",
										template: `<div><span style="padding: 0px 5px; font-size: 12px; text-align: center; color: #475269">${i18n.t("LBL_CURRENT_CRON_EXPRESSION")}</span></div>`
									},
									{
										view: "label",
										tooltip: true,
										align: "center",
										borderless: true,
										width: 250,
										height: 30,
										css: "cron-expression",
										id: "cronExpressionEdit",
										value: "-",
										label: "-"
									}
								]
							},
							{ width: 20 },
							{
								width: 365,
								rows: [
									{
										height: 20,
										view: "label",
										align: "center",
										template: `<div><span style="padding: 0px 5px; font-size: 12px; text-align: center; color: #475269">${i18n.t("LBL_CURRENT_DATE_TIME_NEXT_EXEC")}</span></div>`
									},
									{
										height: 25,
										view: "label",
										tooltip: true,
										align: "center",
										css: "cronui-description",
										id: "currentCronDescriptionId",
										label: "-",
										value: "-"
									}
								]
							}
						]
					},
					{ height: 15 }
				]
			}

			return currentResume;
		}


		function drawButtons() {

			let buttons = {

				id: "CronUiFormButtons",
				borderless: true,
				rows: [
					{ height: 20 },
					{
						cols: [
							{ width: 20 },
							{
								view: "button", value: i18n.t("LBL_CANCEL"), width: 130, height: 41, css: "dialog-cancel",
								click:
									function() {
										$$(self.cronWindow).close();
									}
							},
							{},
							{
								view: "button", value: i18n.t("LBL_RESET"), width: 130, height: 41, css: "action-button",
								click:
									function() {

										$$("tabview_CronUi-" + self.uid).reconstruct();
										$$("resume" + self.uid).reconstruct();

										self.checkedMins = [];
										self.checkedHours = [];
										self.checkedDays = [];
										self.checkedDaysWeek = [];
										self.checkedMonths = [];
										self.checkedYears = [];
										self.cronExpressionArr = ["0", "*", "*", "?", "*", "*", "*"];

										$$("minutesOpt03-0").setValue(1);
										$$("hoursOpt03-0").setValue(1);

										self.checkedMins.push("0");
										self.checkedHours.push("0");

										if (self.currentTab != undefined) {
											$$(self.currentTab).show();
										}

									}
							},
							{ width: 15 },
							{
								view: "button", id: "CronUiFormButtons_apply", label: i18n.t("LBL_ACCEPT"), value: "", width: 130, height: 41, css: "dialog-accept",
								click:
									function() {

										const CRON_EXPRESSION_NULL = "0 * * ? * * *";
										let cronEx = $$("cronExpression" + self.uid).getValue();
										let translate = prettyCron.toString(cronEx, true);

										if (cronEx === CRON_EXPRESSION_NULL) {

											let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_CRON_EXPRESSION_IS_NULL_DESCRIPTION"), null, null);
											ms.show();

										} else {

											$$(self.input).setValues({ cronExpression: cronEx, cronExpressionDescription: translate }, true);
											$$(self.cronWindow).close();

										}
									}
							},
							{ width: 20 }
						]
					},
					{ height: 10 }
				]
			};

			return buttons;
		}


		function calculateMinutes(id) {

			const MINUTES_OPT01 = "minutesOpt01";
			const MINUTES_OPT02 = "minutesOpt02";
			const MINUTES_OPT03 = "minutesOpt03";
			const MINUTES_OPT04 = "minutesOpt04";
			const MAX1 = 60;
			const MAX2 = 59;

			let minutes = '';

			if (id.startsWith(MINUTES_OPT01)) {

				minutes = '*';

			} else if (id.startsWith(MINUTES_OPT02)) {

				minutes = ($$("minutesOpt02_counter02").getValue() > MAX2) ? MAX2 : $$("minutesOpt02_counter02").getValue();
				minutes += '/';
				minutes += ($$("minutesOpt02_counter01").getValue() > MAX1) ? MAX1 : $$("minutesOpt02_counter01").getValue();

			} else if (id.startsWith(MINUTES_OPT03)) {

				if (id.includes("-")) {
					let checkId = id.split("-", 2);

					if ($$(id).getValue() === 1) {
						self.checkedMins.push(checkId[1]);

					} else {

						let deleteCheck = self.checkedMins.indexOf(checkId[1]);

						if (deleteCheck != -1) {
							self.checkedMins.splice(deleteCheck, 1);
						}

					}

					self.checkedMins.sort(function(a, b) { return a - b })
					minutes = self.checkedMins.join();

					if (self.checkedMins.length === 0) {
						minutes = 0;
					}

				} else {

					if (self.checkedMins.length > 0) {
						minutes = self.checkedMins.join();

					} else {
						minutes = 0;
					}
				}

			} else if (id.startsWith(MINUTES_OPT04)) {

				minutes = ($$("minutesOpt04_counter01").getValue() > MAX2) ? MAX2 : $$("minutesOpt04_counter01").getValue();
				minutes += '-';
				minutes += ($$("minutesOpt04_counter02").getValue() > MAX2) ? MAX2 : $$("minutesOpt04_counter02").getValue();

			}

			self.cronExpressionArr[1] = minutes;
			setCronExpression();
			setCronDescription();
		}


		function calculateHours(id) {

			const HOURS_OPT01 = "hoursOpt01";
			const HOURS_OPT02 = "hoursOpt02";
			const HOURS_OPT03 = "hoursOpt03";
			const HOURS_OPT04 = "hoursOpt04";
			const MAX1 = 24;
			const MAX2 = 23;

			let hours = '';

			if (id.startsWith(HOURS_OPT01)) {

				hours = '*';

			} else if (id.startsWith(HOURS_OPT02)) {

				hours = ($$("hoursOpt02_counter02").getValue() > MAX2) ? MAX2 : $$("hoursOpt02_counter02").getValue();
				hours += '/';
				hours += ($$("hoursOpt02_counter01").getValue() > MAX1) ? MAX1 : $$("hoursOpt02_counter01").getValue();

			} else if (id.startsWith(HOURS_OPT03)) {

				if (id.includes("-")) {
					let checkId = id.split("-", 2);

					if ($$(id).getValue() === 1) {
						self.checkedHours.push(checkId[1]);

					} else {

						let deleteCheck = self.checkedHours.indexOf(checkId[1]);

						if (deleteCheck != -1) {
							self.checkedHours.splice(deleteCheck, 1);
						}

					}

					self.checkedHours.sort(function(a, b) { return a - b })
					hours = self.checkedHours.join();

					if (self.checkedHours.length === 0) {
						hours = 0;
					}
				} else {

					if (self.checkedHours.length > 0) {
						hours = self.checkedHours.join();

					} else {
						hours = 0;
					}
				}

			} else if (id.startsWith(HOURS_OPT04)) {

				hours = ($$("hoursOpt04_counter01").getValue() > MAX2) ? MAX2 : $$("hoursOpt04_counter01").getValue();
				hours += '-';
				hours += ($$("hoursOpt04_counter02").getValue() > MAX2) ? MAX2 : $$("hoursOpt04_counter02").getValue();

			}

			self.cronExpressionArr[2] = hours;
			setCronExpression();
			setCronDescription();
		}


		function calculateDays(id) {

			const DAYS_OPT01 = "daysOpt01";
			const DAYS_OPT02 = "daysOpt02";
			const DAYS_OPT03 = "daysOpt03";
			const DAYS_OPT04 = "daysOpt04";
			const DAYS_OPT05 = "daysOpt05";
			const MAX1 = 7;
			const MAX2 = 31;

			let days = '';

			if (id.startsWith(DAYS_OPT01)) {

				days = '*';

			} else if (id.startsWith(DAYS_OPT02)) {

				let transformDay = $$("daysOpt02_counter02").getValue();
				let indexDay = self.daysOfWeek.indexOf(transformDay);

				if (startDay === 0) {

					days = indexDay + 1;

				} else {

					let transformDaysOfWeek = [];

					for (let i = 0; i <= DAYS_ARR.length - 1; i++) {
						transformDaysOfWeek.push({ id: i, value: DAYS_ARR[i] });
					}

					let selectedDay = transformDaysOfWeek.find(e => e.value === transformDay);

					if (selectedDay) {
						days = selectedDay.id + 1;
					}
				}

				days += '/';
				days += ($$("daysOpt02_counter01").getValue() > MAX1) ? MAX1 : $$("daysOpt02_counter01").getValue();

			} else if (id.startsWith(DAYS_OPT03)) {

				if (id.includes("-")) {
					let checkId = id.split("-", 2);

					if ($$(id).getValue() === 1) {
						self.checkedDays.push(checkId[1]);

					} else {

						let deleteCheck = self.checkedDays.indexOf(checkId[1]);

						if (deleteCheck != -1) {
							self.checkedDays.splice(deleteCheck, 1);
						}

					}

					self.checkedDays.sort(function(a, b) { return a - b })
					days = self.checkedDays.join();

					if (self.checkedDays.length === 0) {
						days = 0;
					}

				} else {

					if (self.checkedDays.length > 0) {
						days = self.checkedDays.join();

					} else {
						days = 0;
					}
				}

			} else if (id.startsWith(DAYS_OPT04)) {

				let transformDays02 = $$("daysOpt04_counter02").getValue();
				let transformDays01 = $$("daysOpt04_counter01").getValue();

				//				if (transformDays02.value ) {
				//
				//					days = transformDays.value.slice(0, -2);
				//				} else {
				//
				//					days = transformDays.slice(0, -2);
				//				}
				days += (transformDays02 > MAX2) ? MAX2 : transformDays02;
				days += '/';
				days += (transformDays01 > MAX2) ? MAX2 : transformDays01;

			} else if (id.startsWith(DAYS_OPT05)) {

				if (id.includes("-")) {

					let checkId = id.split("-", 2);

					if ($$(id).getValue() === 1) {
						self.checkedDaysWeek.push(checkId[1]);

					} else {

						let deleteCheck = self.checkedDaysWeek.indexOf(checkId[1]);

						if (deleteCheck != -1) {
							self.checkedDaysWeek.splice(deleteCheck, 1);
						}
					}
					self.checkedDaysWeek.sort(function(a, b) { return a - b });

					let daysArr = [];

					self.checkedDaysWeek.forEach(d => {
						daysArr.push(self.daysOfWeekCron[d]);
					});

					days = daysArr.join();

					if (self.checkedDaysWeek.length === 0) {
						days = "SUN";
					}
				} else {

					let daysArr = [];

					if (self.checkedDaysWeek.length > 0) {

						self.checkedDaysWeek.forEach(d => {
							daysArr.push(self.daysOfWeekCron[d]);
						});

						days = daysArr.join();

					} else {
						days = "SUN";
					}
				}
			}

			if (id.startsWith(DAYS_OPT01) || id.startsWith(DAYS_OPT02) || id.startsWith(DAYS_OPT05)) {

				self.cronExpressionArr[3] = "?";
				self.cronExpressionArr[5] = days;

			} else {

				self.cronExpressionArr[5] = "?";
				self.cronExpressionArr[3] = days;
			}

			setCronExpression();
			setCronDescription();
		}


		function calculateMonths(id) {

			const MONTHS_OPT01 = "monthsOpt01";
			const MONTHS_OPT02 = "monthsOpt02";
			const MONTHS_OPT03 = "monthsOpt03";
			const MONTHS_OPT04 = "monthsOpt04";
			const MAX1 = 12;
			let monthsOfYear = [i18n.t("LBL_JANUARY"), i18n.t("LBL_FEBRUARY"), i18n.t("LBL_MARCH"), i18n.t("LBL_APRIL"), i18n.t("LBL_MAY"), i18n.t("LBL_JUNE"), i18n.t("LBL_JULY"), i18n.t("LBL_AUGUST"), i18n.t("LBL_SEPTEMBER"), i18n.t("LBL_OCTOBER"), i18n.t("LBL_NOVEMBER"), i18n.t("LBL_DECEMBER")];
			let monthsOfYearCron = ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"];

			let months = '';

			if (id.startsWith(MONTHS_OPT01)) {

				months = '*';

			} else if (id.startsWith(MONTHS_OPT02)) {

				let transformMonths = $$("monthsOpt02_counter02").getValue();
				let indexMonth = monthsOfYear.indexOf(transformMonths);

				months = indexMonth + 1;
				months += '/';
				months += ($$("monthsOpt02_counter01").getValue() > MAX1) ? MAX1 : $$("monthsOpt02_counter01").getValue();

			} else if (id.startsWith(MONTHS_OPT03)) {

				if (id.includes("-")) {
					let checkId = id.split("-", 2);

					if ($$(id).getValue() === 1) {
						self.checkedMonths.push(checkId[1]);

					} else {

						let deleteCheck = self.checkedMonths.indexOf(checkId[1]);

						if (deleteCheck != -1) {
							self.checkedMonths.splice(deleteCheck, 1);
						}
					}

					self.checkedMonths.sort(function(a, b) { return a - b });

					let monthsArr = [];

					self.checkedMonths.forEach(month => {
						monthsArr.push(monthsOfYearCron[month]);
					});

					months = monthsArr.join();

					if (self.checkedMonths.length === 0) {
						months = 1;
					}

				} else {

					if (self.checkedMonths.length > 0) {
						let monthsArr = [];

						self.checkedMonths.forEach(month => {
							monthsArr.push(monthsOfYearCron[month]);
						});

						months = monthsArr.join();
					} else {
						months = 1;
					}
				}

			} else if (id.startsWith(MONTHS_OPT04)) {

				let transformMonths1 = $$("monthsOpt04_counter01").getValue();
				let transformMonths2 = $$("monthsOpt04_counter02").getValue();

				let indexMonth1 = monthsOfYear.indexOf(transformMonths1);
				let indexMonth2 = monthsOfYear.indexOf(transformMonths2);

				months = indexMonth1 + 1;
				months += "-";
				months += indexMonth2 + 1;
			}

			self.cronExpressionArr[4] = months;
			setCronExpression();
			setCronDescription();
		}


		function calculateYears(id) {

			const YEARS_OPT01 = "yearsOpt01";
			const YEARS_OPT02 = "yearsOpt02";
			const YEARS_OPT03 = "yearsOpt03";
			const YEARS_OPT04 = "yearsOpt04";
			const MAX1 = 50;

			let years = '';

			if (id.startsWith(YEARS_OPT01)) {

				years = '*';

			} else if (id.startsWith(YEARS_OPT02)) {

				years = $$("yearsOpt02_counter02").getValue();
				years += '/';
				years += ($$("yearsOpt02_counter01").getValue() > MAX1) ? MAX1 : $$("yearsOpt02_counter01").getValue();

			} else if (id.startsWith(YEARS_OPT03)) {

				if (id.includes("-")) {
					let checkId = id.split("-", 2);

					if ($$(id).getValue() === 1) {
						self.checkedYears.push(checkId[1]);

					} else {

						let deleteCheck = self.checkedYears.indexOf(checkId[1]);

						if (deleteCheck != -1) {
							self.checkedYears.splice(deleteCheck, 1);
						}
					}

					self.checkedYears.sort(function(a, b) { return a - b });
					years = self.checkedYears.join();

					if (self.checkedYears.length === 0) {
						years = new Date().getFullYear() + 1;
					}

				} else {

					if (self.checkedYears.length > 0) {
						years = self.checkedYears.join();

					} else {
						years = new Date().getFullYear() + 1;
					}

				}

			} else if (id.startsWith(YEARS_OPT04)) {

				years = $$("yearsOpt04_counter01").getValue();
				years += "-";
				years += $$("yearsOpt04_counter02").getValue();
			}

			self.cronExpressionArr[6] = years;
			setCronExpression();
			setCronDescription();
		}


		function timeSelector(id) {

			const MINUTES = "minutes";
			const HOURS = "hours";
			const DAYS = "days";
			const MONTHS = "months";
			const YEARS = "years";

			if (id.startsWith(MINUTES)) {

				calculateMinutes(id);

			} else if (id.startsWith(HOURS)) {

				calculateHours(id);

			} else if (id.startsWith(DAYS)) {

				calculateDays(id);

			} else if (id.startsWith(MONTHS)) {

				calculateMonths(id);

			} else if (id.startsWith(YEARS)) {

				calculateYears(id);
			}
		}


		function setCronExpression() {

			let expression = self.cronExpressionArr.join(" ");
			$$("cronExpression" + self.uid).setValue(expression);

		}


		function setCronDescription() {

			const ACTUAL_CRON = "* * * ? * * *";

			let cronEx = $$("cronExpression" + self.uid).getValue();

			if (cronEx != ACTUAL_CRON) {

				let nextDate = moment(prettyCron.getNextDate(cronEx, true));
				let dateToEpoch = Math.floor(new Date(nextDate._d).getTime()) / 1000;
				let localeDate = util.toLocaleDateString(dateToEpoch);
				let localeHour = util.toLocaleTimeString(dateToEpoch);

				$$("cronDescriptionId").setValue(localeDate + " - " + localeHour);
			}

		}


		this.cronWindow = util.ui.loadWindow(
			i18n.t(self.windowLabel),
			genericForm,
			685,
			790,
			true
		);


		self.currentExpression(cronExpression, editCronExpression);

		return genericForm;
	}


	currentExpression(cronExpression, editCronExpression) {

		if (editCronExpression.length != 0) {

			let nextDate = moment(prettyCron.getNextDate(editCronExpression, true));
			let dateToEpoch = Math.floor(new Date(nextDate._d).getTime()) / 1000;
			let localeDate = util.toLocaleDateString(dateToEpoch);
			let localeHour = util.toLocaleTimeString(dateToEpoch);

			$$("currentCronDescriptionId").setValue(localeDate + " - " + localeHour);
			$$("cronExpressionEdit").setValue(editCronExpression);
			$$("cronExpression" + this.uid).setValue(editCronExpression);
			$$("cronDescriptionId").setValue(localeDate + " - " + localeHour);

			this.setCronValues(editCronExpression);

		} else if (cronExpression != null) {

			let translate = prettyCron.toString(editCronExpression, true);

			$$("cronExpressionEdit").setValue(cronExpression);
			$$("cronExpression" + this.uid).setValue(cronExpression);
			$$("currentCronDescriptionId").setValue(translate);
			$$("cronDescriptionId").setValue(translate);
			this.setCronValues(cronExpression);

		} else {

			$$("minutesOpt03-0").setValue(1);
			$$("hoursOpt03-0").setValue(1);

			this.checkedMins.push("0");
			this.checkedHours.push("0");
		}
	}


	setCronValues(cronExp) {

		let self = this;
		let timesArr = ["minutesOpt", "hoursOpt", "daysOpt", "monthsOpt", "daysOpt", "yearsOpt"];
		let monthsOfYear = [i18n.t("LBL_JANUARY"), i18n.t("LBL_FEBRUARY"), i18n.t("LBL_MARCH"), i18n.t("LBL_APRIL"), i18n.t("LBL_MAY"), i18n.t("LBL_JUNE"), i18n.t("LBL_JULY"), i18n.t("LBL_AUGUST"), i18n.t("LBL_SEPTEMBER"), i18n.t("LBL_OCTOBER"), i18n.t("LBL_NOVEMBER"), i18n.t("LBL_DECEMBER")];
		let monthsOfYearCron = ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"];

		const MINUTES = 1;
		const HOURS = 2;
		const DAYS_MONTH = 3;
		const MONTHS = 4;
		const DAYS_WEEK = 5;
		const YEARS = 6;

		let editCronExpressionArr = cronExp.split(" ");

		self.cronExpressionArr = editCronExpressionArr;

		for (let i = 0; i < editCronExpressionArr.length; i++) {

			if (editCronExpressionArr[i].includes("-")) {

				let checkList = editCronExpressionArr[i].split("-");

				if (i === MONTHS) {

					$$(timesArr[i] + "04_counter01").setValue(monthsOfYear[checkList[0] - 1]);
					$$(timesArr[i] + "04_counter02").setValue(monthsOfYear[checkList[1] - 1]);

				} else {

					$$(timesArr[i] + "04_counter01").setValue(checkList[0]);
					$$(timesArr[i] + "04_counter02").setValue(checkList[1]);
				}

			} else if (editCronExpressionArr[i].includes("/")) {

				let checkList = editCronExpressionArr[i].split("/");

				if (i === DAYS_WEEK) {

					$$(timesArr[i] + "02_counter01").setValue(checkList[1]);
					$$(timesArr[i] + "02_counter02").setValue(self.daysOfWeek[checkList[0] - 1]);

				} else if (i === DAYS_MONTH) {

					$$(timesArr[i] + "04_counter01").setValue(checkList[1]);

					$$(timesArr[i] + "04_counter02").setValue(selectDays[checkList[0] - 1]);

				} else if (i === MONTHS) {

					$$(timesArr[i] + "02_counter01").setValue(checkList[1]);
					$$(timesArr[i] + "02_counter02").setValue(monthsOfYear[checkList[0] - 1]);

				} else {

					$$(timesArr[i] + "02_counter01").setValue(checkList[1]);
					$$(timesArr[i] + "02_counter02").setValue(checkList[0]);
				}

			} else if (editCronExpressionArr[i].includes(",")) {

				let checkList = editCronExpressionArr[i].split(",")

				for (let check of checkList) {

					if (i === MINUTES) {

						$$(timesArr[i] + "03-" + check).setValue(1);
						self.checkedMins.push(check);

					} else if (i === HOURS) {

						$$(timesArr[i] + "03-" + check).setValue(1);
						self.checkedHours.push(check);

					} else if (i === DAYS_MONTH) {

						$$(timesArr[i] + "03-" + check).setValue(1);
						self.checkedDays.push(check);

					} else if (i === YEARS) {

						$$(timesArr[i] + "03-" + check).setValue(1);
						self.checkedYears.push(check);

					} else if (i === DAYS_WEEK) {

						let dayOfWeek = self.daysOfWeekCron.indexOf(check);
						$$(timesArr[i] + "05-" + dayOfWeek).setValue(1);
						self.checkedDaysWeek.push(dayOfWeek.toString());

					} else {

						let monthOfYear = monthsOfYearCron.indexOf(check);
						$$(timesArr[i] + "03-" + monthOfYear).setValue(1);
						self.checkedMonths.push(monthOfYear.toString());
					}
				}

			} else {

				if (editCronExpressionArr[i] != "*" && editCronExpressionArr[i] != "?") {

					if (i === MINUTES) {

						$$(timesArr[i] + "03-" + editCronExpressionArr[i]).setValue(1);
						self.checkedMins.push(editCronExpressionArr[i]);

					} else if (i === HOURS) {

						$$(timesArr[i] + "03-" + editCronExpressionArr[i]).setValue(1);
						self.checkedHours.push(editCronExpressionArr[i]);

					} else if (i === DAYS_MONTH) {

						$$(timesArr[i] + "03-" + editCronExpressionArr[i]).setValue(1);
						self.checkedDays.push(editCronExpressionArr[i]);

					} else if (i === YEARS) {

						$$(timesArr[i] + "03-" + editCronExpressionArr[i]).setValue(1);
						self.checkedYears.push(editCronExpressionArr[i]);

					} else if (i === MONTHS) {

						let monthOfYear = monthsOfYearCron.indexOf(editCronExpressionArr[i]);
						$$(timesArr[i] + "03-" + monthOfYear).setValue(1);
						self.checkedMonths.push(monthOfYear.toString());

					} else {

						let dayOfWeek = self.daysOfWeekCron.indexOf(editCronExpressionArr[i]);
						$$(timesArr[i] + "05-" + dayOfWeek).setValue(1);
						self.checkedDaysWeek.push(dayOfWeek.toString());

					}
				}
			}
		}
	}

}