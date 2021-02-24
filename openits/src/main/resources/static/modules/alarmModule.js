import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import { util } from "../lib/util.js";

const ALARM_TYPE_MANUAL = "AlarmType:1";

export default class AlarmModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();

		//this.config = JSON.parse(module.moduleValues.find(e => e.moduleParam.id === PARAM_MODULE_TYPE_CUSTOM_WINDOW_CONFIG).value);
		this.config = module.config;
	}

	getView() {

		let view = undefined;

		return view;
	}

	loadData() {

		let self = this;

		if (this.moduleAction.id === this.config.moduleAction.recognize) {

			recognize();
		}
		else if (this.moduleAction.id === this.config.moduleAction.deactivate) {

			deactivate();
		}
		else if (this.moduleAction.id === this.config.moduleAction.mute) {

			mute();
		}

		function recognize() {
			
			let recog = function(alarmsId) {
				
				webix.prompt({
					title: i18n.t("LBL_ARE_YOU_SURE_RECOGNIZED") + " " + alarmsId.length + " " + i18n.t("LBL_ALARMS") + "?",
					ok: i18n.t("LBL_YES"),
					cancel: i18n.t("LBL_NO"),
					input: {
						required: false,
						placeholder: i18n.t("LBL_ALARM_TYPE_REASON_HERE"),
					},
					width: 600,
				}).then(function(comments) {

					let body = {
						alarmIds: self.args.selectedPks,
						recognizedComments: comments
					};

					webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.recognize, body).then(function(data) {
						console.log(data);
					});
				}).fail(function() {

					console.log(cancel);
				});
			};
			
			// Recognize selectedPks
			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

				recog(self.args.selectedPks);
			}
			// Recognize all
			else {
				
				recognizeAll();
			}
			
			function recognizeAll() {
				
				webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.alarm).then(function(dat) {
					
					if (dat != null) {

						let alarms = dat.json();
						
						if (Array.isArray(alarms) && alarms.length > 0) {
							
							let alarmsToRecognize = alarms.filter(alarm => {
								
								return !alarm.recognized;
							});
							
							if (Array.isArray(alarmsToRecognize) && alarmsToRecognize.length > 0) {
								
								let alarmsId = alarmsToRecognize.map(alarm => {
									
									return alarm.id;
								});
								
								recog(alarmsId);
							} 
						}
					}
				});
			}
		}

		function deactivate() {

			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

				let allAlarmsManual = true;

				for (let i = 0; allAlarmsManual && i < self.args.selectedPks.length; i++) {

					let endpoint = util.fillTemplate(self.config.restEndpoints.alarm, { id: self.args.selectedPks[i] });

					webix.ajax().get(appConfig.restUrl + endpoint).then(function(dat) {

						if (dat != null) {

							let alarm = dat.json();
							let alarmConfig = window.em.getByField(EntityType.ALARM_CONFIG, "id", alarm.alarmConfig.id);
							let alarmType = alarmConfig.alarmType.id;

							allAlarmsManual &= (alarmType === ALARM_TYPE_MANUAL);
							
							if (i >= self.args.selectedPks.length - 1) {
								
								if (allAlarmsManual) {
									
									webix.prompt({
										title: i18n.t("LBL_ARE_YOU_SURE_DEACTIVATE") + " " + self.args.selectedPks.length + " " + i18n.t("LBL_ALARMS") + "?",
										ok: i18n.t("LBL_YES"),
										cancel: i18n.t("LBL_NO"),
										input: {
											required: false,
											placeholder: i18n.t("LBL_ALARM_TYPE_REASON_HERE"),
										},
										width: 600,
									}).then(function(comments) {
	
										let body = {
											alarmIds: self.args.selectedPks,
											recognizedComments: comments
										};
	
										webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.deactivate, body).then(function(data) {
											console.log(data);
										});
									});
								}
								else {
									
									webix.alert({
										title: i18n.t("LBL_DEACTIVATE_ALARMS_ERROR"),
										text: i18n.t("LBL_DEACTIVATE_ALARMS_ERROR_SELECTED_AUTO_ALARMS_NOT_COMPATIBLE"),
										type: "alert-error",
										width: 500
									});									
								}
							}
						}
					});
				}
			}
		}

		function mute() {

			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

				webix.confirm({
					title: i18n.t("LBL_ARE_YOU_SURE_MUTE") + " " + self.args.selectedPks.length + " " + i18n.t("LBL_ALARMS") + "?",
					ok: i18n.t("LBL_YES"),
					cancel: i18n.t("LBL_NO"),
					text: i18n.t("LBL_ALARM_MUTE_DESCRIPTION"),
					width: 600
				}).then(function(result) {

					// TODO
					console.log(result);
				});
			}
		}
	}
}
