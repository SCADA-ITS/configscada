import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";

export default class ExternalApp extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);
		//        this.module = module;
		//		this.args = args;
		//		this.uid = webix.uid();
	}

	getView() {

		let url = util.module.getParamValue(this.module, MODULE_PARAM_TYPE_GENERIC_EXTERNAL_APP_WINDOW_URL);
		let externalWebTab = util.module.getParamValue(this.module, MODULE_PARAM_TYPE_GENERIC_EXTERNAL_APP_WINDOW_EXTERNAL_WEB_APP);

		if (IS_TRUE(externalWebTab)) {

			window.open(url, "_blank");

		} else {

			let view = {
				view: "iframe",
				src: url
			};

			return view;
		}
	}
}
