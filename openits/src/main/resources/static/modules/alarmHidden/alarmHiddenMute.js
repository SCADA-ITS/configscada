import CalendarDialogBox from "../../component/calendarDialogBox.js";
import { util } from "../../lib/util.js";

export default class AlarmHiddenMute {

	constructor(parent) {

		this.parent = parent;

	}


	separateAlarms(action, arrAlarms) {

		let parent = this.parent;
		let mutedAlarms = [];
		let disabledAlarms = [];
		let counter = 0;
		const ACTION = { true: true, false: false };

		if (action === ACTION.true) {

			mute(action, arrAlarms);

		} else {

			if (arrAlarms && arrAlarms.length > 0) {

				arrAlarms.forEach(alarm => {


					webix.ajax().get(appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.alarmMuted, { id: alarm })).then(function(data) {

						if (data != null) {

							let alarmData = data.json();

							if (alarmData.alarmDisabled) {

								disabledAlarms.push(alarmData);

							} else if (alarmData.mutedUntil) {

								mutedAlarms.push(alarmData);
							}

							counter++;
						}

						if (counter === arrAlarms.length) {

							webix.confirm({
								title: i18n.t("LBL_ALARM_UNMUTE/ENABLE_CONFIRM"),
								ok: i18n.t("LBL_YES"),
								cancel: i18n.t("LBL_NO"),
								text: i18n.t("LBL_ALARM_UNMUTE/ENABLE_CONFIRM_DESCRIPTION"),
								width: 600
							}).then(function() {

								if (mutedAlarms.length > 0) {

									mute(action, mutedAlarms);

								} else if (disabledAlarms.length > 0) {

									enable(disabledAlarms);
								}
							});
						}
					});
				});
			}
		}


		function mute(action, arrAlarms) {

			let title;
			let text;

			if (Array.isArray(arrAlarms) && arrAlarms.length > 0) {

				title = action ? i18n.t("LBL_DIALOG_MUTE_ALARMS") : i18n.t("LBL_DIALOG_UNMUTE_ALARMS");
				text = action ? i18n.t("LBL_ARE_YOU_SURE_MUTE") : i18n.t("LBL_ARE_YOU_SURE_UNMUTE");

				if (action === ACTION.true) {

					(new CalendarDialogBox(title, text, false,

						function(date) {

							return (Math.floor(date) < Math.floor(new Date()));
						},
						function(dates) {

							webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + parent.config.restEndpoints.alarmsArray, JSON.stringify(arrAlarms)).then(function(data) {

								let responseAlarms = data.json();

								if (Array.isArray(responseAlarms) && responseAlarms.length > 0) {

									let mutedAlarms = [];
									let itemResponse = 0;

									responseAlarms.forEach(alarm => {

										mutedAlarms.push({

											alarmConfigId: alarm.alarmConfig.id,
											mutedUntil: dates.from.getTime(),
											elementId: alarm.targetElement.id,
											enable: true
										});
									});

									if (Array.isArray(mutedAlarms) && mutedAlarms.length > 0) {

										let body = {

											alarmMuteds: mutedAlarms
										};

										webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + parent.config.actionEndpoints.mute, body).then((data) => {

											itemResponse++
											if (responseAlarms.length === itemResponse) {
												parent.args.callbacks[0]();
											}


										});
									}
								}
							});
						}
					)).show();

				}

				if (action === ACTION.false) {

					let mutedAlarms = [];
					let itemResponse = 0;

					arrAlarms.forEach(e => {

						webix.ajax().get(appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.alarmMuted, { id: e.id })).then(function(dat) {

							if (dat != null) {

								let responseAlarms = dat.json();

								mutedAlarms.push({

									alarmConfigId: responseAlarms.alarmConfig.id,
									mutedUntil: responseAlarms.mutedUntil,
									elementId: responseAlarms.element.id,
									enable: false

								});

								if (Array.isArray(mutedAlarms) && mutedAlarms.length > 0) {

									let body = {

										alarmMuteds: mutedAlarms
									};

									webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + parent.config.actionEndpoints.unmute, body).then((data) => {

										itemResponse++
										if (arrAlarms.length === itemResponse) {
											parent.args.callbacks[0]();
										}

									});
								}

							}

						});

					});
				}
			}
		}


		function enable(disabledAlarms) {

			if (Array.isArray(disabledAlarms) && disabledAlarms.length > 0) {

				let enableAlarms = [];
				let itemResponse = 0;
				disabledAlarms.forEach(alarm => {

					enableAlarms.push({

						alarmConfigId: alarm.alarmConfig.id,
						mutedUntil: alarm.mutedUntil,
						elementId: alarm.element.id,
						enable: false,
						alarmDisabled: false

					});
				});

				if (Array.isArray(enableAlarms) && enableAlarms.length > 0) {

					let body = {

						alarmMuteds: enableAlarms
					};

					webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + parent.config.actionEndpoints.unmute, body).then(function(data) {

						itemResponse++
						if (disabledAlarms.length === itemResponse) {
							parent.args.callbacks[0]();
						}

					});
				}
			}
		}

	}

}