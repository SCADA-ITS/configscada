import GenericModule from "../modules/genericModule.js";


export default class AuditLogModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);
		this.config = JSON.parse(module.moduleValues.find(e => e.moduleParam.id === PARAM_MODULE_TYPE_CUSTOM_WINDOW_CONFIG).value);
	}

	getView() {

		let view = undefined;

		return view;
	}

	loadData() {

		if (this.moduleAction.id === this.config.moduleAction.recognize) {

			this.recognizeAuditLogs();
		}

	}

	recognizeAuditLogs() {

		let self = this;

		if (Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {

			webix.confirm({
				title: i18n.t("LBL_AUDIT_LOG_REZOGNIZE_CONFIRM"),
				ok: i18n.t("LBL_YES"),
				cancel: i18n.t("LBL_NO"),
				text: i18n.t("LBL_AUDIT_LOG_REZOGNIZE_CONFIRM_DESCRIPTION"),
				width: 600

			}).then(function() {

				webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.recognize, { "auditLogIds": self.args.selectedPks }).then(function(data) {
					console.log(data);
				});
			});
		}
	}

}
