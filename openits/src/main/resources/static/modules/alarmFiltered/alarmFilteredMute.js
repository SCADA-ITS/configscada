import CalendarDialogBox from "../../component/calendarDialogBox.js";
import { util } from "../../lib/util.js";

export default class AlarmFilteredMute {

	constructor(parent) {

		this.parent = parent;

	}


	separateAlarms(action, arrAlarms) {

		let parent = this.parent;
		let filterAlarms = [];
		let counter = 0;
		const ACTION = { true: true, false: false };

		if (action === ACTION.true) {

			deleteFilters(action, arrAlarms);

		} else {

			if (arrAlarms && arrAlarms.length > 0) {

				arrAlarms.forEach(alarm => {

					webix.ajax().get(appConfig.restUrl + util.fillTemplate(parent.config.restEndpoints.alarmMuted, { id: alarm })).then(function(data) {

						if (data != null) {

							let alarmData = data.json();

							filterAlarms.push(alarmData);

							counter++;
						}

						if (counter === arrAlarms.length) {

							webix.confirm({
								title: i18n.t("LBL_ALARM_REMOVE_FILTER_CONFIRM"),
								ok: i18n.t("LBL_YES"),
								cancel: i18n.t("LBL_NO"),
								text: i18n.t("LBL_ALARM_REMOVE_FILTER_CONFIRM_DESCRIPTION"),
								width: 600
							}).then(function() {

								deleteFilters(action, filterAlarms);
							});
						}
					});
				});
			}
		}


		function deleteFilters(action, arrAlarms) {

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

									let alarmsToFilter = [];

									responseAlarms.forEach(alarm => {

										alarmsToFilter.push({

											alarmConfigId: alarm.alarmConfig.id,
											filteredUntil: dates.from.getTime(),
											elementId: alarm.targetElement.id,
											alarmDisabled: false,
											enable: true
										});
									});

									if (Array.isArray(alarmsToFilter) && alarmsToFilter.length > 0) {

										let body = {

											alarmFiltereds: alarmsToFilter
										};

										webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + parent.config.actionEndpoints.mute, body).then((data) => {

											parent.args.callbacks[0](1000);

										});
									}
								}
							});
						}
					)).show();

				}

				if (action === ACTION.false) {

					let filtersToDelete = [];

					arrAlarms.forEach(e => {

						filtersToDelete.push({

							alarmConfigId: e.alarmConfig.id,
							elementId: e.element.id,
							enable: false

						});

					});

					if (Array.isArray(filtersToDelete) && filtersToDelete.length > 0) {
						let body = {

							alarmFiltereds: filtersToDelete
						};

						webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + parent.config.actionEndpoints.unmute, body).then((data) => {

							parent.args.callbacks[0](1000);

						});
					}
				}
			}
		}
		


	}

}