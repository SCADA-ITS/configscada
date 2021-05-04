import { util } from "../lib/util.js";

var selectDays = ["1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th", "13th", "14th", "15th", "16th", "17th", "18th", "19th", "20th", "21st", "22nd", "23rd", "24th", "25th", "26th", "27th", "28th", "29th", "30th", "31th"]

export default class CronUi {

	constructor(input_id, popupId) {

		let input = input_id;
		var popupId = popupId;

		this.uid = webix.uid();
		this.input = input;
		this.cronWindow = undefined;
		this.windowLabel = undefined;
		this.checkedSecs = [];
		this.checkedMins = [];
		this.checkedHours = [];
		this.checkedDays = [];
		this.checkedDaysWeek = [];
		this.checkedMonths = [];
		this.checkedYears = [];
		this.cronExpressionArr = ["0", "0", "0", "?", "*", "*", "*"];
		this.cronExpressionEditArr = [];
	}

	getComponent(cronExpression) {

		let self = this;

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
							rows: [
								drawForm(),
								drawResume(),
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
						label: "<img src='../img/grid/sheduler.png' class='grid-icon'/>",
						height: 75,
						css: "userrole-header-icon"
					},
					{
						view: "label",
						height: 75,
						id: "label-bottom" + self.uid,
						template: "<span>" + i18n.t(label) + "</span> <br> <span style='font-size: 13px'>" + i18n.t(description) + "</span>",
						css: "userrole-header-label"
					}
				]
			};

			return header;
		}


		function drawForm() {

			let form = {
				id: "tabview_CronUi-" + self.uid,
				view: "tabview",
				css: "secondary-tab .cronui-tab",
				width: 780,
				tabbar: {
					css: { "width": "780px !important" },
					tabMargin: 6,
					options: [
						{ id: "cronUi_seconds-" + self.uid, value: $.i18n("cronUi.popup.tab.seconds") },
						{ id: "cronUi_minutes-" + self.uid, value: $.i18n("cronUi.popup.tab.minutes") },
						{ id: "cronUi_hours-" + self.uid, value: $.i18n("cronUi.popup.tab.hours") },
						{ id: "cronUi_days-" + self.uid, value: $.i18n("cronUi.popup.tab.days") },
						{ id: "cronUi_months-" + self.uid, value: $.i18n("cronUi.popup.tab.months") },
						{ id: "cronUi_years-" + self.uid, value: $.i18n("cronUi.popup.tab.years") }
					]
				},
				cells: [
					{
						view: "form",
						id: "cronUi_seconds-" + self.uid,
						elements: [
							{
								cols: [
									{
										rows: [
											{ height: 66 },
											{
												view: "radio",
												id: "secondsOpt-" + self.uid,
												width: 245,
												value: "secondsOpt03",
												vertical: true,
												multiview: true,
												on: {
													onChange: function(newv, oldv) {
														$$(newv).show();
														$$("secondsOpt02_counter01").setValue("1");
														$$("secondsOpt02_counter02").setValue("0");
														$$("secondsOpt04_counter01").setValue("0");
														$$("secondsOpt04_counter02").setValue("0");

														for (let i = 0; i <= 59; i++) {

															$$("secondsOpt03-" + i).setValue("0");
														}

														self.checkedSecs = [];
														self.cronExpressionArr[0] = 0;
														$$("cronExpression" + self.uid).setValue(self.cronExpressionArr);

														calculateSeconds(newv);
													}
												}
												,
												options: [
													{ id: "secondsOpt01", value: i18n.t("LBL_EVERY_SECOND") },
													{ id: "secondsOpt02", value: i18n.t("LBL_EVERY_X_SECOND") },
													{ id: "secondsOpt03", value: i18n.t("LBL_SPECIFIC_SECOND") },
													{ id: "secondsOpt04", value: i18n.t("LBL_EVERY_SECOND_BETWEEN") }
												]
											}]
									},
									{
										view: "multiview",
										height: 260,
										width: 500,
										id: "multi_seconds-" + self.uid,
										animate: false,
										scroll: "auto",
										cells: [
											{ id: "secondsOpt03", view: "form", elements: drawMultiview("secondsOpt03") },
											{ id: "secondsOpt01", view: "form", elements: drawMultiview("secondsOpt01") },
											{ id: "secondsOpt02", view: "form", elements: drawMultiview("secondsOpt02") },
											{ id: "secondsOpt04", view: "form", elements: drawMultiview("secondsOpt04") }
										]
									}
								]
							}
						]
					},
					{
						view: "form",
						id: "cronUi_minutes-" + self.uid,
						elements: [
							{
								cols: [
									{
										rows: [
											{ height: 66 },
											{
												view: "radio",
												id: "minutesOpt-" + self.uid,
												width: 245,
												value: "minutesOpt03",
												vertical: true,
												multiview: true,
												on: {
													onChange: function(newv, oldv) {
														$$(newv).show();
														$$("minutesOpt02_counter01").setValue("1");
														$$("minutesOpt02_counter02").setValue("0");
														$$("minutesOpt04_counter01").setValue("0");
														$$("minutesOpt04_counter02").setValue("0");

														for (let i = 0; i <= 59; i++) {

															$$("minutesOpt03-" + i).setValue("0");
														}

														self.checkedMins = [];
														self.cronExpressionArr[1] = 0;
														$$("cronExpression" + self.uid).setValue(self.cronExpressionArr);

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
										height: 260,
										width: 500,
										id: "multi_minutes-" + self.uid,
										animate: false,
										scroll: "auto",
										cells: [
											{ id: "minutesOpt03", view: "form", elements: drawMultiview("minutesOpt03") },
											{ id: "minutesOpt01", view: "form", elements: drawMultiview("minutesOpt01") },
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
								cols: [
									{
										rows: [
											{ height: 66 },
											{
												view: "radio",
												id: "hoursOpt-" + self.uid,
												width: 245,
												value: "hoursOpt03",
												vertical: true,
												multiview: true,
												on: {
													onChange: function(newv, oldv) {
														$$(newv).show();
														$$("hoursOpt02_counter01").setValue("1");
														$$("hoursOpt02_counter02").setValue("0");
														$$("hoursOpt02_counter01").setValue("0");
														$$("hoursOpt02_counter02").setValue("0");

														for (let i = 0; i <= 23; i++) {
															$$("hoursOpt03-" + i).setValue("0");
														}

														self.checkedHours = [];
														self.cronExpressionArr[2] = 0;
														$$("cronExpression" + self.uid).setValue(self.cronExpressionArr);

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
										height: 260,
										width: 500,
										id: "multi_hours-" + self.uid,
										animate: false,
										scroll: "auto",
										cells: [
											{ id: "hoursOpt03", view: "form", elements: drawMultiview("hoursOpt03") },
											{ id: "hoursOpt01", view: "form", elements: drawMultiview("hoursOpt01") },
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
								cols: [
									{
										rows: [
											{ height: 50 },
											{
												view: "radio",
												id: "daysOpt-" + self.uid,
												width: 245,
												value: "daysOpt03",
												vertical: true,
												multiview: true,
												on: {
													onChange: function(newv, oldv) {
														$$(newv).show();
														$$("daysOpt02_counter01").setValue("1");
														$$("daysOpt02_counter02").setValue(i18n.t("LBL_SUNDAY"));
														$$("daysOpt04_counter01").setValue("1");
														$$("daysOpt04_counter02").setValue("1st");

														for (let i = 1; i <= 31; i++) {
															$$("daysOpt03-" + i).setValue("0");
														}

														for (let i = 0; i <= 6; i++) {
															$$("daysOpt05-" + i).setValue("0");
														}

														self.checkedDays = [];
														self.checkedDaysWeek = [];

														self.cronExpressionArr[3] = "?";
														self.cronExpressionArr[5] = "*";
														$$("cronExpression" + self.uid).setValue(self.cronExpressionArr);

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
										height: 260,
										width: 500,
										id: "multi_days-" + self.uid,
										animate: false,
										scroll: "auto",
										cells: [
											{ id: "daysOpt03", view: "form", elements: drawDays("daysOpt03") },
											{ id: "daysOpt01", view: "form", elements: drawDays("daysOpt01") },
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
								cols: [
									{
										rows: [
											{ height: 66 },
											{
												view: "radio",
												id: "monthsOpt-" + self.uid,
												width: 245,
												value: "monthsOpt03",
												vertical: true,
												multiview: true,
												on: {
													onChange: function(newv, oldv) {
														$$(newv).show();
														$$("monthsOpt02_counter01").setValue("1");
														$$("monthsOpt02_counter02").setValue(i18n.t("LBL_JANUARY"));
														$$("monthsOpt04_counter01").setValue(i18n.t("LBL_JANUARY"));
														$$("monthsOpt04_counter02").setValue(i18n.t("LBL_JANUARY"));

														for (let i = 0; i <= 11; i++) {
															$$("monthsOpt03-" + i).setValue("0");
														}

														self.checkedMonths = [];
														self.cronExpressionArr[4] = "*";
														$$("cronExpression" + self.uid).setValue(self.cronExpressionArr);

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
										height: 260,
										width: 500,
										id: "multi_months-" + self.uid,
										animate: false,
										scroll: "auto",
										cells: [
											{ id: "monthsOpt03", view: "form", elements: drawMonths("monthsOpt03") },
											{ id: "monthsOpt01", view: "form", elements: drawMonths("monthsOpt01") },
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
								cols: [
									{
										rows: [
											{ height: 66 },
											{
												view: "radio",
												id: "yearsOpt-" + self.uid,
												width: 245,
												value: "yearsOpt03",
												vertical: true,
												multiview: true,
												on: {
													onChange: function(newv, oldv) {
														let date = new Date().getFullYear();

														$$(newv).show();
														$$("yearsOpt02_counter01").setValue("1");
														$$("yearsOpt02_counter02").setValue(date + 1);
														$$("yearsOpt04_counter01").setValue(date + 1);
														$$("yearsOpt04_counter02").setValue(date + 1);

														for (let i = date + 1; i <= date + 36; i++) {
															$$("yearsOpt03-" + i).setValue("0");
														}

														self.checkedYears = [];
														self.cronExpressionArr[6] = "*";
														$$("cronExpression" + self.uid).setValue(self.cronExpressionArr);

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
										height: 260,
										width: 500,
										id: "multi_years-" + self.uid,
										animate: false,
										scroll: "auto",
										cells: [
											{ id: "yearsOpt03", view: "form", elements: drawMonths("yearsOpt03") },
											{ id: "yearsOpt01", view: "form", elements: drawMonths("yearsOpt01") },
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
			const SECONDS_OPT01 = "secondsOpt01";
			const SECONDS_OPT02 = "secondsOpt02";
			const SECONDS_OPT03 = "secondsOpt03";
			const SECONDS_OPT04 = "secondsOpt04";
			const MINUTES_OPT01 = "minutesOpt01";
			const MINUTES_OPT02 = "minutesOpt02";
			const MINUTES_OPT03 = "minutesOpt03";
			const MINUTES_OPT04 = "minutesOpt04";
			const HOURS_OPT01 = "hoursOpt01";
			const HOURS_OPT02 = "hoursOpt02";
			const HOURS_OPT03 = "hoursOpt03";
			const HOURS_OPT04 = "hoursOpt04";

			if (id === SECONDS_OPT03 || id === MINUTES_OPT03 || id === HOURS_OPT03) {

				let ids = (id === SECONDS_OPT03) ? SECONDS_OPT03 : (id === MINUTES_OPT03) ? MINUTES_OPT03 : HOURS_OPT03;

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
						click: function(id, event) {
							timeSelector(ids + "-" + i);
						}
					}

					column6.push(check);
				}

				if (id === SECONDS_OPT03 || id === MINUTES_OPT03) {

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

			} else if (id === SECONDS_OPT02 || id === MINUTES_OPT02 || id === HOURS_OPT02) {

				let ids = (id === SECONDS_OPT02) ? SECONDS_OPT02 : (id === MINUTES_OPT02) ? MINUTES_OPT02 : HOURS_OPT02;
				let label = (id === SECONDS_OPT02) ? "LBL_SECOND(S)_STARTING_AT_SECOND" : (id === MINUTES_OPT02) ? "LBL_MINUTE(S)_STARTING_AT_MINUTE" : "LBL_HOUR(S)_STARTING_AT_HOUR";

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
										max: 60,
										click:
											function(id, event) {
												timeSelector(id)
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
										max: 59,
										click:
											function(id, event) {
												timeSelector(id)
											}
									}
								]
							}]
					}
				]

			} else if (id === SECONDS_OPT04 || id === MINUTES_OPT04 || id === HOURS_OPT04) {

				let ids = (id === SECONDS_OPT04) ? SECONDS_OPT04 : (id === MINUTES_OPT04) ? MINUTES_OPT04 : HOURS_OPT04;
				let label1 = (id === SECONDS_OPT04) ? "LBL_EVERY_SECOND_BETWEEN_SECOND" : (id === MINUTES_OPT04) ? "LBL_EVERY_MINUTE_BETWEEN_MINUTE" : "LBL_EVERY_HOUR_BETWEEN_HOUR";
				let label2 = (id === SECONDS_OPT04) ? "LBL_AND_SECOND" : (id === MINUTES_OPT04) ? "LBL_AND_MINUTE" : "LBL_AND_HOUR";

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
										max: 59,
										click:
											function(id, event) {
												timeSelector(id)
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
										max: 59,
										click:
											function(id, event) {
												timeSelector(id)
											}
									}
								]
							}]
					}
				]
			} else if (id === SECONDS_OPT01 || id === MINUTES_OPT01 || id === HOURS_OPT01) {

				let label = (id === SECONDS_OPT01) ? "LBL_EVERY_SECOND" : (id === MINUTES_OPT01) ? "LBL_EVERY_MINUTE" : "LBL_EVERY_HOUR";

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
										click:
											function(id, event) {
												timeSelector(id)
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
										value: i18n.t("LBL_SUNDAY"),
										options: [i18n.t("LBL_SUNDAY"), i18n.t("LBL_MONDAY"), i18n.t("LBL_TUESDAY"), i18n.t("LBL_WEDNESDAY"), i18n.t("LBL_THURSDAY"), i18n.t("LBL_FRIDAY"), i18n.t("LBL_SATURDAY")],
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

				let daysOfWeek = [i18n.t("LBL_SUNDAY"), i18n.t("LBL_MONDAY"), i18n.t("LBL_TUESDAY"), i18n.t("LBL_WEDNESDAY"), i18n.t("LBL_THURSDAY"), i18n.t("LBL_FRIDAY"), i18n.t("LBL_SATURDAY")];
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
						label: daysOfWeek[i],
						value: 0,
						width: 225,
						labelWidth: 150,
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
						label: daysOfWeek[i],
						value: 0,
						width: 225,
						labelWidth: 150,
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
						label: daysOfWeek[i],
						value: 0,
						width: 225,
						labelWidth: 150,
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
					label: daysOfWeek[6],
					value: 0,
					width: 500,
					labelWidth: 150,
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
										click:
											function(id, event) {
												timeSelector(id)
											}
									}]
							},
							{
								cols: [
									{
										view: "label",
										label: i18n.t("LBL_DAYS_STARTING_ON"),
										width: 200,
										align: "left"
									},
									{
										view: "combo",
										id: id + "_counter02",
										name: "",
										width: 80,
										value: "1st",
										options: selectDays,
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
						width: 235,
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
						width: 235,
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
						width: 235,
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
						width: 235,
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
						width: 235,
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
						width: 235,
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
										click:
											function(id, event) {
												timeSelector(id)
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
										options: comboOptions,
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
										options: comboOptions,
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
										options: comboOptions,
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


		function drawResume(cronExpression) {

			let resume = {
				css: "cronui-resume",
				cols: [
					{ width: 15 },
					{
						width: 365,
						rows: [
							{ height: 15 },
							{
								height: 25,
								view: "label",
								template: `<div><span style="padding: 0px 5px; font-size: 12px; text-align: center; color: #475269">${i18n.t("LBL_NEW_CRON_EXPRESSION")}</span></div>`
							},
							{
								height: 25,
								cols: [
									{ width: 5 },
									{
										view: "label",
										tooltip: true,
										align: "center",
										borderless: false,
										id: "cronExpression" + self.uid,
										value: "0 0 0 ? * * *",
										label: "0 0 0 ? * * *"
									}
								]
							},
							{ height: 15 },
							{
								height: 25,
								view: "label",
								template: `<div><span style="padding: 0px 5px; font-size: 12px; text-align: center; color: #475269">${i18n.t("LBL_CURRENT_CRON_EXPRESSION")}</span></div>`
							},
							{
								height: 25,
								cols: [
									{ width: 5 },
									{
										view: "label",
										tooltip: true,
										align: "center",
										borderless: false,
										id: "cronExpressionEdit",
										value: "-",
										label: "-"
									}
								]
							}
						]
					},
					{ width: 20 },
					{
						width: 365,
						rows: [
							{ height: 15 },
							{
								height: 26,
								view: "label",
								template: `<div><span style="padding: 0px 5px; font-size: 12px; text-align: center; color: #475269">${i18n.t("LBL_NEW_SCHEDULE")}</span></div>`
							},
							{
								height: 25,
								view: "label",
								tooltip: true,
								css: "cronui-description",
								id: "cronDescriptionId",
								label: i18n.t("LBL_NEW_SCHEDULER_DESCRIPTION"),
								value: i18n.t("LBL_NEW_SCHEDULER_DESCRIPTION")
							},
							{ height: 15 },
							{
								height: 26,
								view: "label",
								template: `<div><span style="padding: 0px 5px; font-size: 12px; text-align: center; color: #475269">${i18n.t("LBL_CURRENT_SCHEDULE_DESCRIPTION")}</span></div>`
							},
							{
								height: 25,
								view: "label",
								tooltip: true,
								css: "cronui-description",
								id: "currentCronDescriptionId",
								label: i18n.t("LBL_CURRENT_SCHEDULE_DESCRIPTION"),
								value: i18n.t("LBL_CURRENT_SCHEDULE_DESCRIPTION")
							}
						]
					},
					{ width: 15 }
				]
			}

			return resume;
		}


		function drawButtons() {

			let buttons = {

				id: "CronUiFormButtons",
				borderless: true,
				rows: [
					{ height: 15 },
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

										let date = new Date().getFullYear();

										$$("secondsOpt02_counter01").setValue("1");
										$$("secondsOpt02_counter02").setValue("0");
										$$("secondsOpt04_counter01").setValue("1");
										$$("secondsOpt04_counter02").setValue("0");

										$$("minutesOpt02_counter01").setValue("1");
										$$("minutesOpt02_counter02").setValue("0");
										$$("minutesOpt04_counter01").setValue("1");
										$$("minutesOpt04_counter02").setValue("0");

										$$("hoursOpt02_counter01").setValue("1");
										$$("hoursOpt02_counter02").setValue("0");
										$$("hoursOpt02_counter01").setValue("1");
										$$("hoursOpt02_counter02").setValue("0");

										$$("daysOpt02_counter01").setValue("1");
										$$("daysOpt02_counter02").setValue(i18n.t("LBL_SUNDAY"));
										$$("daysOpt04_counter01").setValue("1");
										$$("daysOpt04_counter02").setValue("1st");

										$$("monthsOpt02_counter01").setValue("1");
										$$("monthsOpt02_counter02").setValue(i18n.t("LBL_JANUARY"));
										$$("monthsOpt04_counter01").setValue(i18n.t("LBL_JANUARY"));
										$$("monthsOpt04_counter02").setValue(i18n.t("LBL_JANUARY"));

										$$("yearsOpt02_counter01").setValue("1");
										$$("yearsOpt02_counter02").setValue(date + 1);
										$$("yearsOpt04_counter01").setValue(date + 1);
										$$("yearsOpt04_counter02").setValue(date + 1);

										for (let i = 0; i <= 59; i++) {
											$$("secondsOpt03-" + i).setValue("0");
											$$("minutesOpt03-" + i).setValue("0");
										}

										for (let i = 0; i <= 23; i++) {
											$$("hoursOpt03-" + i).setValue("0");
										}

										for (let i = 1; i <= 31; i++) {
											$$("daysOpt03-" + i).setValue("0");
										}

										for (let i = 0; i <= 6; i++) {
											$$("daysOpt05-" + i).setValue("0");
										}

										for (let i = 0; i <= 11; i++) {
											$$("monthsOpt03-" + i).setValue("0");
										}

										for (let i = date + 1; i <= date + 36; i++) {
											$$("yearsOpt03-" + i).setValue("0");
										}

										self.checkedSecs = [];
										self.checkedMins = [];
										self.checkedHours = [];
										self.checkedDays = [];
										self.checkedDaysWeek = [];
										self.checkedMonths = [];
										self.checkedYears = [];
										self.cronExpressionArr = ["0", "0", "0", "?", "*", "*", "*"];

										$$("cronExpression" + self.uid).setValue("0 0 0 ? * * *");
										$$("cronDescriptionId").setValue("At 00:00:00am every day");
									}
							},
							{
								view: "button", id: "CronUiFormButtons_apply", label: $.i18n("planSchedule.form.apply"), value: "", width: 130, height: 41, css: "dialog-accept",
								click:
									function() {

										let cronEx = $$("cronExpression" + self.uid).getValue();
										let translate = prettyCron.toString(cronEx, true);

										$$(self.input).setValues({ cronExpression: cronEx, cronExpressionDescription: translate }, true);
										$$(self.cronWindow).close();
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


		function calculateSeconds(id) {

			const SECONDS_OPT01 = "secondsOpt01";
			const SECONDS_OPT02 = "secondsOpt02";
			const SECONDS_OPT03 = "secondsOpt03";
			const SECONDS_OPT04 = "secondsOpt04";

			let seconds = '';

			if (id.startsWith(SECONDS_OPT01)) {

				seconds = '*';

			} else if (id.startsWith(SECONDS_OPT02)) {

				seconds = $$("secondsOpt02_counter02").getValue();
				seconds += '/';
				seconds += $$("secondsOpt02_counter01").getValue();

			} else if (id.startsWith(SECONDS_OPT03)) {

				if (id.includes("-")) {
					let checkId = id.split("-", 2);

					if ($$(id).getValue() === 1) {
						self.checkedSecs.push(checkId[1]);

					} else {

						let deleteCheck = self.checkedSecs.indexOf(checkId[1]);

						if (deleteCheck != -1) {
							self.checkedSecs.splice(deleteCheck, 1);
						}

					}

					self.checkedSecs.sort(function(a, b) { return a - b })
					seconds = self.checkedSecs.join();

					if (self.checkedSecs.length === 0) {
						seconds = 0;
					}

				} else {

					seconds = 0;
				}

			} else if (id.startsWith(SECONDS_OPT04)) {

				seconds = $$("secondsOpt04_counter01").getValue();
				seconds += '-';
				seconds += $$("secondsOpt04_counter02").getValue();
			}

			self.cronExpressionArr[0] = seconds;
			setCronExpression();
			setCronDescription();
		}


		function calculateMinutes(id) {

			const MINUTES_OPT01 = "minutesOpt01";
			const MINUTES_OPT02 = "minutesOpt02";
			const MINUTES_OPT03 = "minutesOpt03";
			const MINUTES_OPT04 = "minutesOpt04";

			let minutes = '';

			if (id.startsWith(MINUTES_OPT01)) {

				minutes = '*';

			} else if (id.startsWith(MINUTES_OPT02)) {

				minutes = $$("minutesOpt02_counter02").getValue();
				minutes += '/';
				minutes += $$("minutesOpt02_counter01").getValue();

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

					minutes = 0;
				}

			} else if (id.startsWith(MINUTES_OPT04)) {

				minutes = $$("minutesOpt04_counter01").getValue();
				minutes += '-';
				minutes += $$("minutesOpt04_counter02").getValue();
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

			let hours = '';

			if (id.startsWith(HOURS_OPT01)) {

				hours = '*';

			} else if (id.startsWith(HOURS_OPT02)) {

				hours = $$("hoursOpt02_counter02").getValue();
				hours += '/';
				hours += $$("hoursOpt02_counter01").getValue();

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

					hours = 0;
				}

			} else if (id.startsWith(HOURS_OPT04)) {

				hours = $$("hoursOpt04_counter01").getValue();
				hours += '-';
				hours += $$("hoursOpt04_counter02").getValue();
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
			let daysOfWeek = [i18n.t("LBL_SUNDAY"), i18n.t("LBL_MONDAY"), i18n.t("LBL_TUESDAY"), i18n.t("LBL_WEDNESDAY"), i18n.t("LBL_THURSDAY"), i18n.t("LBL_FRIDAY"), i18n.t("LBL_SATURDAY")];
			let daysOfWeekCron = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];

			let days = '';

			if (id.startsWith(DAYS_OPT01)) {

				days = '*';

			} else if (id.startsWith(DAYS_OPT02)) {

				let transformDay = $$("daysOpt02_counter02").getValue();
				let indexDay = daysOfWeek.indexOf(transformDay);

				days = indexDay + 1;
				days += '/';
				days += $$("daysOpt02_counter01").getValue();

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

					days = 0;
				}

			} else if (id.startsWith(DAYS_OPT04)) {

				let transformDays = $$("daysOpt04_counter02").getValue();

				days = transformDays.slice(0, -2)
				days += '/';
				days += $$("daysOpt04_counter01").getValue();

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

					self.checkedDaysWeek.forEach(days => {
						daysArr.push(daysOfWeekCron[days]);
					});

					days = daysArr.join();

					if (self.checkedDaysWeek.length === 0) {
						days = "SUN";
					}
				} else {

					days = "SUN";
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
				months += $$("monthsOpt02_counter01").getValue();

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

					months = 1;
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

			let years = '';

			if (id.startsWith(YEARS_OPT01)) {

				years = '*';

			} else if (id.startsWith(YEARS_OPT02)) {

				years = $$("yearsOpt02_counter02").getValue();
				years += '/';
				years += $$("yearsOpt02_counter01").getValue();

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

					years = new Date().getFullYear() + 1;
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

			const SECONDS = "seconds";
			const MINUTES = "minutes";
			const HOURS = "hours";
			const DAYS = "days";
			const MONTHS = "months";
			const YEARS = "years";

			if (id.startsWith(SECONDS)) {

				calculateSeconds(id);

			} else if (id.startsWith(MINUTES)) {

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
			let cronEx = $$("cronExpression" + self.uid).getValue();
			//let cronEx = self.cronExpressionArr[0] + " " + self.cronExpressionArr[1] + " " + self.cronExpressionArr[2] + " " + self.cronExpressionArr[3] + " " + self.cronExpressionArr[4] + " " + self.cronExpressionArr[5] + " " + self.cronExpressionArr[6];
			let translate = prettyCron.toString(cronEx, true);

			$$("cronDescriptionId").setValue(translate);
		}


		this.cronWindow = util.ui.loadWindow(
			i18n.t(self.windowLabel),
			genericForm,
			715,
			790,
			true
		);

		self.currentExpression(cronExpression);
		return genericForm;
	}


	currentExpression(cronExpression) {

		if (cronExpression != null) {

			let translate = prettyCron.toString(cronExpression, true);

			$$("cronExpressionEdit").setValue(cronExpression);
			$$("currentCronDescriptionId").setValue(translate);
		}
	}


	loadData() {
		let valor = $$(this.input).getValue();
		let valorList = valor.split(" ");
		let controlSecond = "";
		let controlMinute = "";
		let controlHour = "";
		let controlDay = "";
		let controlMonth = "";
		let controlYear = "";

		let controlTotal = true;

		if (valorList.length !== 7) {
			//ERROR
			console.log("ERROR DE TAMAÑO DE FORMATO");
		} else {
			if (valorList[0] === "*") {
				//EVERY YEAR
				controlSecond = "secondsOpt01";
			} else {
				if (valorList[0].search("/") > 0) {
					let control = true;
					let valores = valorList[0].split("/");
					let valor1 = null;
					let valor2 = null;
					try {
						valor1 = parseInt(valores[0]);
						valor2 = parseInt(valores[1]);
					} catch (e) {
						control = false;
					}

					if (control) {
						controlSecond = "secondsOpt02";
						$$("secondsOpt02_counter01").setValue(valor2);
						$$("secondsOpt02_counter02").setValue(valor1);
					} else {
						//ERROR 
						controlTotal = false;
						//console.log("ERROR FALLO / SEC 02");
						webix.message({ type: "error", text: $.i18n("cronUi.popup.error.second") });
					}
				} else {
					if (valorList[0].search("-") > 0) {
						let control = true;
						let valores = valorList[0].split("-");
						let valor1 = null;
						let valor2 = null;
						try {
							valor1 = parseInt(valores[0]);
							valor2 = parseInt(valores[1]);
						} catch (e) {
							control = false;
						}

						if (control) {
							controlSecond = "secondsOpt04";
							$$("secondsOpt04_counter01").setValue(valor2);
							$$("secondsOpt04_counter02").setValue(valor1);
						} else {
							//ERROR 
							controlTotal = false;
							webix.message({ type: "error", text: $.i18n("cronUi.popup.error.second") });
							//console.log("ERROR FALLO - SEC 04");
						}
					} else {
						if (valorList[0].search(",") > 0) {
							let control = true;
							let valores = valorList[0].split(",");
							let valor1 = null;
							let valor2 = null;
							try {
								valores.forEach(item => {
									valor1 = parseInt(item);
								});
							} catch (e) {
								control = false;
							}

							if (control) {
								controlSecond = "secondsOpt03";
								$$("secondsOpt03_combo").setValue(valorList[0]);
							} else {
								//ERROR 
								controlTotal = false;
								webix.message({ type: "error", text: $.i18n("cronUi.popup.error.second") });
								//console.log("ERROR FALLO , SEC 03");
							}
						} else {
							//un año individual
							let control = true;
							//let valores = valorList[6].split(",");
							let valor1 = null;
							let valor2 = null;
							try {
								valor1 = parseInt(valorList[0]);
							} catch (e) {
								console.log(e);
								control = false;
							}

							if (control) {
								controlSecond = "secondsOpt03";
								$$("secondsOpt03_combo").setValue(valorList[0]);
							} else {
								//ERROR 
								controlTotal = false;
								webix.message({ type: "error", text: $.i18n("cronUi.popup.error.second") });
								//console.log("ERROR FALLO , SEC 03_2");
							}
						}
					}
				}
			}

			if (valorList[1] === "*") {
				//EVERY minute
				controlMinute = "minsOpt01";
			} else {
				if (valorList[1].search("/") > 0) {
					let control = true;
					let valores = valorList[1].split("/");
					let valor1 = null;
					let valor2 = null;
					try {
						valor1 = parseInt(valores[0]);
						valor2 = parseInt(valores[1]);
					} catch (e) {
						control = false
					}

					if (control) {
						controlMinute = "minsOpt02";
						$$("minsOpt02_counter01").setValue(valor2);
						$$("minsOpt02_counter02").setValue(valor1);
					} else {
						//ERROR 
						controlTotal = false;
						webix.message({ type: "error", text: $.i18n("cronUi.popup.error.minute") });
						//console.log("ERROR FALLO / MIN 02");
					}
				} else {
					if (valorList[1].search("-") > 0) {
						let control = true;
						let valores = valorList[1].split("-");
						let valor1 = null;
						let valor2 = null;
						try {
							valor1 = parseInt(valores[0]);
							valor2 = parseInt(valores[1]);
						} catch (e) {
							control = false
						}

						if (control) {
							controlMinute = "minsOpt04";
							$$("minsOpt04_counter01").setValue(valor2);
							$$("minsOpt04_counter02").setValue(valor1);
						} else {
							//ERROR 
							controlTotal = false;
							webix.message({ type: "error", text: $.i18n("cronUi.popup.error.minute") });
							//console.log("ERROR FALLO - MIN 03");
						}
					} else {
						if (valorList[1].search(",") > 0) {
							let control = true;
							let valores = valorList[1].split(",");
							let valor1 = null;
							let valor2 = null;
							try {
								valores.forEach(item => {
									valor1 = parseInt(item);
								});
							} catch (e) {
								control = false
							}

							if (control) {
								controlMinute = "minsOpt03";
								$$("minsOpt03_combo").setValue(valorList[1]);
							} else {
								//ERROR 
								controlTotal = false;
								webix.message({ type: "error", text: $.i18n("cronUi.popup.error.minute") });
								//console.log("ERROR FALLO , MIN 03");
							}
						} else {
							//un año individual
							let control = true;
							//let valores = valorList[6].split(",");
							let valor1 = null;
							let valor2 = null;
							try {
								valor1 = parseInt(valorList[1]);
							} catch (e) {
								control = false
							}

							if (control) {
								controlMinute = "minsOpt03";
								$$("minsOpt03_combo").setValue(valorList[1]);
							} else {
								//ERROR 
								controlTotal = false;
								webix.message({ type: "error", text: $.i18n("cronUi.popup.error.minute") });
								//console.log("ERROR FALLO , MIN 03_2");
							}
						}
					}
				}
			}

			if (valorList[2] === "*") {
				//EVERY minute
				controlHour = "hoursOpt01";
			} else {
				if (valorList[2].search("/") > 0) {
					let control = true;
					let valores = valorList[2].split("/");
					let valor1 = null;
					let valor2 = null;
					try {
						valor1 = parseInt(valores[0]);
						valor2 = parseInt(valores[1]);
					} catch (e) {
						control = false
					}

					if (control) {
						controlHour = "hoursOpt02";
						$$("hoursOpt02_counter01").setValue(valor2);
						$$("hoursOpt02_counter02").setValue(valor1);
					} else {
						//ERROR 
						controlTotal = false;
						webix.message({ type: "error", text: $.i18n("cronUi.popup.error.hour") });
						//console.log("ERROR FALLO / HOUR 02");
					}
				} else {
					if (valorList[2].search("-") > 0) {
						let control = true;
						let valores = valorList[2].split("-");
						let valor1 = null;
						let valor2 = null;
						try {
							valor1 = parseInt(valores[0]);
							valor2 = parseInt(valores[1]);
						} catch (e) {
							control = false
						}

						if (control) {
							controlHour = "hoursOpt04";
							$$("hoursOpt04_counter01").setValue(valor2);
							$$("hoursOpt04_counter02").setValue(valor1);
						} else {
							//ERROR 
							controlTotal = false;
							webix.message({ type: "error", text: $.i18n("cronUi.popup.error.hour") });
							//console.log("ERROR FALLO - HOUR 04");
						}
					} else {
						if (valorList[2].search(",") > 0) {
							let control = true;
							let valores = valorList[2].split(",");
							let valor1 = null;
							let valor2 = null;
							try {
								valores.forEach(item => {
									valor1 = parseInt(item);
								});
							} catch (e) {
								control = false
							}

							if (control) {
								controlHour = "hoursOpt03";
								$$("hoursOpt03_combo").setValue(valorList[2]);
							} else {
								//ERROR 
								controlTotal = false;
								webix.message({ type: "error", text: $.i18n("cronUi.popup.error.hour") });
								//console.log("ERROR FALLO , HOUR 03");
							}
						} else {
							//un año individual
							let control = true;
							//let valores = valorList[6].split(",");
							let valor1 = null;
							let valor2 = null;
							try {
								valor1 = parseInt(valorList[2]);
							} catch (e) {
								control = false
							}

							if (control) {
								controlHour = "hoursOpt03";
								$$("hoursOpt03_combo").setValue(valorList[2]);
							} else {
								//ERROR 
								controlTotal = false;
								webix.message({ type: "error", text: $.i18n("cronUi.popup.error.hour") });
								//console.log("ERROR FALLO , HOUR 03");
							}
						}
					}
				}
			}

			if (valorList[3] === "*") {
				//EVERY minute
				controlDay = "daysOpt01";
			} else {
				if (valorList[3].search("/") > 0) {
					let control = true;
					let valores = valorList[3].split("/");
					let valor1 = null;
					let valor2 = null;
					try {
						valor1 = parseInt(valores[0]);
						valor2 = parseInt(valores[1]);
					} catch (e) {
						control = false
					}

					if (control) {
						controlDay = "daysOpt02";
						$$("daysOpt02_counter01").setValue(valor2);
						$$("daysOpt02_counter02").setValue(valor1);
					} else {
						//ERROR 
						controlTotal = false;
						//console.log("ERROR FALLO / DAY 02");
						webix.message({ type: "error", text: $.i18n("cronUi.popup.error.day") });
					}
				} else {
					if (valorList[3].search(" ? ") > 0) {
						let control = true;
						//let valores = valorList[3];

						/*
						try {
							valor1 = parseInt(valores[0]);
							valor2 = parseInt(valores[1]);
						} catch (e) {
							control = false
						}
						*/

						if (control) {
							controlDay = "daysOpt05";
							//$$("daysOpt04_counter01").setValue(valor2);
							//$$("daysOpt04_counter02").setValue(valor1);
						} else {
							//ERROR 
							controlTotal = false;
							webix.message({ type: "error", text: $.i18n("cronUi.popup.error.day") });
							//console.log("ERROR FALLO ? DAY 02");
						}
					} else {
						if (valorList[3].search(",") > 0) {
							let control = true;
							let valores = valorList[3].split(",");
							let valor1 = null;
							let valor2 = null;
							try {
								valores.forEach(item => {
									valor1 = parseInt(item);
								});
							} catch (e) {
								control = false
							}

							if (control) {
								controlDay = "daysOpt03";
								$$("daysOpt03_combo").setValue(valorList[3]);
							} else {
								//ERROR 
								controlTotal = false;
								webix.message({ type: "error", text: $.i18n("cronUi.popup.error.day") });
								//console.log("ERROR FALLO , DAY 03");
							}
						} else {
							//un año individual
							let control = true;
							//let valores = valorList[6].split(",");
							let valor1 = null;
							let valor2 = null;
							try {
								valor1 = parseInt(valorList[3]);
							} catch (e) {
								control = false
							}

							if (control) {
								controlDay = "daysOpt03";
								$$("daysOpt03_combo").setValue(valorList[3]);
							} else {
								//ERROR 
								controlTotal = false;
								webix.message({ type: "error", text: $.i18n("cronUi.popup.error.day") });
								//console.log("ERROR FALLO , DAY 03_01");
							}
						}
					}
				}
			}

			if (valorList[4] === "*") {
				//EVERY minute
				controlMonth = "monthsOpt01";
			} else {
				if (valorList[4].search("/") > 0) {
					let control = true;
					let valores = valorList[4].split("/");
					let valor1 = null;
					let valor2 = null;
					try {
						valor1 = parseInt(valores[0]);
						valor2 = parseInt(valores[1]);
					} catch (e) {
						control = false
					}

					if (control) {
						controlMonth = "monthsOpt02";
						$$("monthsOpt02_counter01").setValue(valor2);
						$$("monthsOpt02_counter02").setValue(valor1);
					} else {
						//ERROR 
						controlTotal = false;
						webix.message({ type: "error", text: $.i18n("cronUi.popup.error.month") });
						//console.log("ERROR FALLO / MON 02");
					}
				} else {
					if (valorList[4].search("-") > 0) {
						let control = true;
						let valores = valorList[4].split("-");
						let valor1 = null;
						let valor2 = null;
						try {
							valor1 = parseInt(valores[0]);
							valor2 = parseInt(valores[1]);
						} catch (e) {
							control = false
						}

						if (control) {
							controlMonth = "monthsOpt04";
							$$("monthsOpt04_counter01").setValue(valor2);
							$$("monthsOpt04_counter02").setValue(valor1);
						} else {
							//ERROR 
							controlTotal = false;
							webix.message({ type: "error", text: $.i18n("cronUi.popup.error.month") });
							//console.log("ERROR FALLO - MON 04");
						}
					} else {
						if (valorList[4].search(",") > 0) {
							let control = true;
							let valores = valorList[4].split(",");
							let valor1 = [];
							let valor2 = null;
							try {
								valores.forEach(item => {
									valor1.push(parseInt(v_month3[item]).toString());
								});
							} catch (e) {
								control = false
							}

							if (control) {
								controlMonth = "monthsOpt03";
								$$("monthsOpt03_combo").setValue(valor1.toString());
							} else {
								//ERROR 
								controlTotal = false;
								webix.message({ type: "error", text: $.i18n("cronUi.popup.error.month") });
								//console.log("ERROR FALLO , MON 03");
							}
						} else {
							//un año individual
							let control = true;
							//let valores = valorList[6].split(",");
							let valor1 = null;
							let valor2 = null;
							try {
								valor1 = parseInt(v_month3[valorList[4]]);
							} catch (e) {
								control = false
							}

							if (control) {
								controlMonth = "monthsOpt03";
								$$("monthsOpt03_combo").setValue(valor1.toString());
							} else {
								//ERROR 
								controlTotal = false;
								webix.message({ type: "error", text: $.i18n("cronUi.popup.error.month") });
								//console.log("ERROR FALLO , MON 03_02");
							}
						}
					}
				}
			}

			if (valorList[6] === "*") {
				//EVERY YEAR
				controlYear = "yearsOpt01";
			} else {
				if (valorList[6].search("/") > 0) {
					let control = true;
					let valores = valorList[6].split("/");
					let valor1 = null;
					let valor2 = null;
					try {
						valor1 = parseInt(valores[0]);
						valor2 = parseInt(valores[1]);
					} catch (e) {
						control = false
					}

					if (control) {
						controlYear = "yearsOpt02";
						$$("yearsOpt02_counter01").setValue(valor2);
						$$("yearsOpt02_counter02").setValue(valor1);
					} else {
						//ERROR 
						controlTotal = false;
						webix.message({ type: "error", text: $.i18n("cronUi.popup.error.year") });
						//console.log("ERROR FALLO / YEA 02");
					}
				} else {
					if (valorList[6].search("-") > 0) {
						let control = true;
						let valores = valorList[6].split("-");
						let valor1 = null;
						let valor2 = null;
						try {
							valor1 = parseInt(valores[0]);
							valor2 = parseInt(valores[1]);
						} catch (e) {
							control = false
						}

						if (control) {
							controlYear = "yearsOpt04";
							$$("yearsOpt04_counter01").setValue(valor2);
							$$("yearsOpt04_counter02").setValue(valor1);
						} else {
							//ERROR 
							controlTotal = false;
							webix.message({ type: "error", text: $.i18n("cronUi.popup.error.year") });
							//console.log("ERROR FALLO - YEA 04");
						}
					} else {
						if (valorList[6].search(",") > 0) {
							let control = true;
							let valores = valorList[6].split(",");
							let valor1 = null;
							let valor2 = null;
							try {
								valores.forEach(item => {
									valor1 = parseInt(item);
								});
							} catch (e) {
								control = false
							}

							if (control) {
								controlYear = "yearsOpt03";
								$$("yearsOpt03_combo").setValue(valorList[6]);
							} else {
								//ERROR 
								controlTotal = false;
								webix.message({ type: "error", text: $.i18n("cronUi.popup.error.year") });
								//console.log("ERROR FALLO , YEA 03");
							}
						} else {
							//un año individual
							let control = true;
							//let valores = valorList[6].split(",");
							let valor1 = null;
							let valor2 = null;
							try {
								valor1 = parseInt(valorList[6]);
							} catch (e) {
								control = false
							}

							if (control) {
								controlYear = "yearsOpt03";
								$$("yearsOpt03_combo").setValue(valorList[6]);
							} else {
								//ERROR 
								controlTotal = false;
								webix.message({ type: "error", text: $.i18n("cronUi.popup.error.year") });
								//console.log("ERROR FALLO , YEA 03_01");
							}
						}
					}
				}
			}

			if (controlTotal) {
				$$("secondsOpt01").setValue(0);
				$$("secondsOpt02").setValue(0);
				$$("secondsOpt03").setValue(0);
				$$("secondsOpt04").setValue(0);

				$$("minsOpt01").setValue(0);
				$$("minsOpt02").setValue(0);
				$$("minsOpt03").setValue(0);
				$$("minsOpt04").setValue(0);

				$$("hoursOpt01").setValue(0);
				$$("hoursOpt02").setValue(0);
				$$("hoursOpt03").setValue(0);
				$$("hoursOpt04").setValue(0);

				$$("daysOpt01").setValue(0);
				$$("daysOpt02").setValue(0);
				$$("daysOpt03").setValue(0);
				//$$("daysOpt04").setValue(0);
				$$("daysOpt05").setValue(0);

				$$("monthsOpt01").setValue(0);
				$$("monthsOpt02").setValue(0);
				$$("monthsOpt03").setValue(0);
				$$("monthsOpt04").setValue(0);

				$$("yearsOpt01").setValue(0);
				$$("yearsOpt02").setValue(0);
				$$("yearsOpt03").setValue(0);
				$$("yearsOpt04").setValue(0);

				$$(controlSecond).setValue(1);
				$$(controlMinute).setValue(1);
				$$(controlHour).setValue(1);
				$$(controlDay).setValue(1);
				$$(controlMonth).setValue(1);
				$$(controlYear).setValue(1);

				$$("inputsHeaderSeconds").setValue(valorList[0]);
				$$("inputsHeaderMinutes").setValue(valorList[1]);
				$$("inputsHeaderHours").setValue(valorList[2]);
				$$("inputsHeaderDays").setValue(valorList[3]);
				$$("inputsHeaderMonths").setValue(valorList[4]);
				$$("inputsHeaderYears").setValue(valorList[6]);
			} else {
				webix.message({ type: "error", text: $.i18n("cronUi.popup.error.total") });
			}
		}
	}

}