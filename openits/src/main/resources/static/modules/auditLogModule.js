import GenericModule from "../modules/genericModule.js";
import MessageBox from "../component/messageBox.js";

export default class AuditLogModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);
		//this.config = JSON.parse(module.moduleValues.find(e => e.moduleParam.id === PARAM_MODULE_TYPE_CUSTOM_WINDOW_CONFIG).value);
		this.config = module.config;
	}

	getView() {

		let view = undefined;

		return view;
	}

	loadData() {

		let self = this;

		if (self.moduleAction.id === self.config.moduleAction.recognize) {

			recognizeAuditLogs();
		}

		function recognizeAuditLogs() {

			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

				let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_AUDIT_LOG_REZOGNIZE_CONFIRM_DESCRIPTION"), onAccept, null);
				ms.show();
				
				function onAccept() {
					
					webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.recognize, { "auditLogIds": self.args.selectedPks }).then(function(data) {
						if (self.args.callbacks.length > 0) {

							self.args.callbacks[0]();
						}
					});				
				};
			}
		}

	}

}
