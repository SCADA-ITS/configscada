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

		let self = this;

		if(this.moduleAction.id === this.config.moduleAction.execute){
			
			actionExecute();
		}

		function actionExecute() {
	
			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
		
				if(self.args.confirm == undefined || self.args.confirm){
	
					webix.confirm({
						title: i18n.t("LBL_PLAN_CONFIRM_EXECUTE"),
						ok: i18n.t("LBL_ACCEPT"),
						cancel: i18n.t("LBL_CANCEL"),
						text: i18n.t("LBL_PLAN_CONFIRM_EXECUTE_DESCRIPTION"),
						width: 600
					}).then(function() {
						
						let body = prepareData(self.args);
	
						webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.execute, body).then(function(data) {
							/*if (self.args.callbacks.length > 0) {
	
								self.args.callbacks[0]();
							}*/
						});
					});
	
				} else {
					
					let body = prepareData(self.args);
	
					webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.execute, body).then(function(data) {
						/*if (self.args.callbacks.length > 0) {
	
							self.args.callbacks[0]();
						}*/
					});
				}
			}
		}

		function prepareData(args) {

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

}
