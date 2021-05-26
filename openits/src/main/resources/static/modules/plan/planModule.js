import GenericModule from "../genericModule.js";


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

		if (this.moduleAction.id === this.config.moduleAction.play) {

			this.actionPlay();
		
		} else if(this.moduleAction.id === this.config.moduleAction.execute){
			
			this.executePlan();
		}

	}

	actionPlay() {

		let self = this;

		if (Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {

			webix.confirm({
				title: i18n.t("LBL_PLAN_CONFIRM_PLAY"),
				ok: i18n.t("LBL_YES"),
				cancel: i18n.t("LBL_NO"),
				text: i18n.t("LBL_PLAN_CONFIRM_PLAY_DESCRIPTION"),
				width: 600
			}).then(function() {
				
				self.args.planAction = "PLAY";
				let body = self.prepareData(self.args);

				webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.play, body).then(function(data) {
					if (self.args.callbacks.length > 0) {

						self.args.callbacks[0]();
					}
				});
			});
		}
	}

	executePlan() {

		let self = this;

		if (Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {

			webix.confirm({
				title: i18n.t("LBL_PLAN_CONFIRM_EXECUTE"),
				ok: i18n.t("LBL_YES"),
				cancel: i18n.t("LBL_NO"),
				text: i18n.t("LBL_PLAN_CONFIRM_EXECUTE_DESCRIPTION"),
				width: 600
			}).then(function() {
				
				let body = this.prepareData(self.args);

				webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.play, body).then(function(data) {
					if (self.args.callbacks.length > 0) {

						self.args.callbacks[0]();
					}
				});
			});
		}
	}

	prepareData(args){

		let body = { 
			planActions: [
			]
		};

		args.selectedPks.forEach(element => {
			body.planActions.push({
				planId: element,
				action: args.planAction
			});
		});

		return body;

	}

}
