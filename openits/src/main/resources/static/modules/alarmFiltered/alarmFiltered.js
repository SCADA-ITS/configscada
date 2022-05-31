import { util } from "../../lib/util.js";
import CalendarDialogBox from "../../component/calendarDialogBox.js";
import MessageBox from "../../component/messageBox.js";
import InputDialogBox from "../../component/inputDialogBox.js";

export default class AlarmFiltered {

	constructor(parent) {

		this.parent = parent;

	}


	separateAlarms(action, arrAlarms, alarmDisabled) {

		let parent = this.parent;
		let filterAlarms = [];
		let counter = 0;
		const ACTION = { true: true, false: false };

		if (action === ACTION.true) {

			deleteFilters(action, arrAlarms, alarmDisabled);

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

							let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_ALARM_REMOVE_FILTER_CONFIRM_DESCRIPTION"), onAccept, null);
							ms.show();

							function onAccept() {

								deleteFilters(action, filterAlarms, alarmDisabled);
							};
						}
					});
				});
			}
		}


		function deleteFilters(action, arrAlarms, alarmDisabled) {

			let title;
			let text;

			if (Array.isArray(arrAlarms) && arrAlarms.length > 0) {

				if (alarmDisabled) {
					title = action ? i18n.t("LBL_DIALOG_DISABLE_ALARMS") : i18n.t("LBL_DIALOG_ENABLE_ALARMS");
					text = action ? i18n.t("LBL_ARE_YOU_SURE_DISABLE") : i18n.t("LBL_ARE_YOU_SURE_ENABLE");
				} else {
					title = action ? i18n.t("LBL_DIALOG_MUTE_ALARMS") : i18n.t("LBL_DIALOG_UNMUTE_ALARMS");
					text = action ? i18n.t("LBL_ARE_YOU_SURE_MUTE") : i18n.t("LBL_ARE_YOU_SURE_UNMUTE");
				}


				if (action === ACTION.true) {

					(new CalendarDialogBox(title, text, null, false, function(date) {

						let timeService = window.sm.getService(Services.TIME_SERVICE);
						let dat = new Date(timeService.getEpoch());

						return (Math.floor(date) < Math.floor(dat));
					},
						onAcceptCalendar
					)).show();

					function onAcceptCalendar(dates) {

						(new InputDialogBox(title, text, onAcceptInputDialogBox, false, null, { id: DataType.ALPHANUMERIC })).show();

						function onAcceptInputDialogBox(comment) {

							webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + parent.config.restEndpoints.alarmsArray, JSON.stringify(arrAlarms)).then(function(data) {

								let responseAlarms = data.json();

								if (Array.isArray(responseAlarms) && responseAlarms.length > 0) {

									let user = util.user.getUserData();
									let alarmsToFilter = [];

									responseAlarms.forEach(alarm => {

										alarmsToFilter.push({

											alarmConfigId: alarm.alarmConfig.id,
											filteredUntil: dates.from.getTime(),
											elementId: alarm.targetElement.id,
											alarmDisabled: alarmDisabled,
											enable: true,
											userId: user.id,
											comment: comment
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

					}
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