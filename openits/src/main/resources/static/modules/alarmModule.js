import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import { util } from "../lib/util.js";


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
			
			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
			
				webix.prompt({
					title: i18n.t("LBL_ARE_YOU_SURE_RECOGNIZED")  + " " + self.args.selectedPks.length + " " + i18n.t("LBL_ALARMS") + "?",
					ok: i18n.t("LBL_YES"),
					cancel: i18n.t("LBL_NO"),
					input: {
						required: false,
						placeholder: i18n.t("LBL_ALARM_TYPE_REASON_HERE"),
					},
					width: 600,
				}).then(function(result) {
					
					// TODO
					console.log(result);
				}).fail(function() {
					
					console.log(cancel);
				});	
			}
		}

		function deactivate() {

			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
			
				webix.confirm({
					title: i18n.t("LBL_ARE_YOU_SURE_DEACTIVATE")  + " " + self.args.selectedPks.length + " " + i18n.t("LBL_ALARMS") + "?",
					ok: i18n.t("LBL_YES"),
					cancel: i18n.t("LBL_NO"),
					text: i18n.t("LBL_ALARM_DEACTIVATE_DESCRIPTION"),
					width: 600
				}).then(function(result) {
					
					// TODO
					console.log(result);
				});
			}
		}

		function mute() {

			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
			
				webix.confirm({
					title: i18n.t("LBL_ARE_YOU_SURE_MUTE")  + " " + self.args.selectedPks.length + " " + i18n.t("LBL_ALARMS") + "?",
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
