import GenericModule from "../modules/genericModule.js";
import InputDialogBox from "../component/inputDialogBox.js";
import CalendarDialogBox from "../component/calendarDialogBox.js";
import { util } from "../lib/util.js";

export default class AlarmModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);

		//this.config = JSON.parse(module.moduleValues.find(e => e.moduleParam.id === PARAM_MODULE_TYPE_CUSTOM_WINDOW_CONFIG).value);
		this.config = module.config;
	}

	getView() {

		let view = undefined;
		
		if (this.moduleAction.id === this.config.moduleAction.view) {

			view = {};
		}

		return view;
	}

	loadData() {

		let self = this;

		if (this.moduleAction.id === this.config.moduleAction.view) {

			util.ui.notImplementedMessage();
		}
		else if (this.moduleAction.id === this.config.moduleAction.recognize) {

			recognize();
		}
		else if (this.moduleAction.id === this.config.moduleAction.deactivate) {

			deactivate();
		}
		else if (this.moduleAction.id === this.config.moduleAction.mute) {

			mute(true);
		}
		else if (this.moduleAction.id === this.config.moduleAction.unmute) {

			mute(false);
		}

		function recognize() {
			
			let recog = function(alarmsId) {
				
				(new InputDialogBox(i18n.t("LBL_DIALOG_RECOGNIZE_ALARMS"), i18n.t("LBL_ARE_YOU_SURE_RECOGNIZE"), function(comments) {
					
					let body = {
						alarmIds: alarmsId,
						recognizedComments: comments,
						userId: self.userData.id
					};

					webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.recognize, body).then(function(data) {
						console.log(data);
					});
					
				})).show();
								
			};
			
			function recognizeAll() {
				
				webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.alarms).then(function(dat) {
					
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
			
			// Recognize selectedPks
			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

				recog(self.args.selectedPks);
			}
			// Recognize all
			else {
				
				recognizeAll();
			}
		}
		
		function deactivate() {
			
			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
				
				let allAlarmsManual = true;
				
				webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.config.restEndpoints.alarmsArray, JSON.stringify(self.args.selectedPks)).then(function(data) {
					
					let responseAlarms = data.json();
					
					if (Array.isArray(responseAlarms) && responseAlarms.length > 0) {
						
						responseAlarms.forEach(alarm => {
							
							let alarmConfig = window.em.getByField(EntityType.ALARM_CONFIG, "id", alarm.alarmConfig.id);
							
							if (alarmConfig) {
							
								let alarmType = alarmConfig.alarmType.id;
								
								if (alarmType) {
								
									allAlarmsManual &= Array.isArray(self.config.alarmTypeManual) && (self.config.alarmTypeManual.indexOf(alarmType) !== -1);	
								}
							}
						});
						
						if (allAlarmsManual) {
							
							(new InputDialogBox(i18n.t("LBL_DIALOG_DEACTIVATE_ALARMS"), i18n.t("LBL_ARE_YOU_SURE_DEACTIVATE"), function(comments) {
					
								let body = {
									alarmIds: self.args.selectedPks,
									deactivationComments: comments,
									userId: self.userData.id
								};

								webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.deactivate, body).then(function(data) {
									console.log(data);
								});
								
							})).show();
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
				});
			}
		}

		function mute(action) {

			let title;
			let text;
			
			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
				
				title = action ? i18n.t("LBL_DIALOG_MUTE_ALARMS") : i18n.t("LBL_DIALOG_UNMUTE_ALARMS");
				text = action ? i18n.t("LBL_ARE_YOU_SURE_MUTE") : i18n.t("LBL_ARE_YOU_SURE_UNMUTE");
				
				(new CalendarDialogBox(title, text, false, 
				
					function(date) {
						
						return (Math.floor(date) < Math.floor(new Date()));
					},
					function(dates) {

						webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.config.restEndpoints.alarmsArray, JSON.stringify(self.args.selectedPks)).then(function(data) {
						
							let responseAlarms = data.json();
							
							if (Array.isArray(responseAlarms) && responseAlarms.length > 0) {
								
								let mutedAlarms = [];
								
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
									
									webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.mute, body).then(function(data) {
										console.log(data);
									});									
								} 
							}
						});					
					}
				)).show();
			}
		}
	}
}
