import GenericModule from "./genericModule.js";


export default class PlanModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
	}

	getView() {

		let view = undefined;

		return view;
	}

	loadData() {

		let self = this;
		
		if (this.moduleAction.id === this.config.moduleAction.play) {

			actionPlay();
		}
		
		function actionPlay() {
	
			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
	
				webix.confirm({
					title: i18n.t("LBL_PLAN_CONFIRM_PLAY"),
					ok: i18n.t("LBL_YES"),
					cancel: i18n.t("LBL_NO"),
					text: i18n.t("LBL_PLAN_CONFIRM_PLAY_DESCRIPTION"),
					width: 600
				}).then(function() {
	
					webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.play, { "planIds": self.args.selectedPks }).then(function(data) {
						if (self.args.callbacks.length > 0) {
	
							self.args.callbacks[0]();
						}
					});
				});
			}
		}

	}

	

}
