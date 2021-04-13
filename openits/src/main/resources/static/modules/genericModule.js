import { util } from "../lib/util.js";

export default class GenericModule {

	constructor(module, moduleAction, args) {

		if (this.constructor == GenericModule) {

			throw new Error("Abstract classes can't be instantiated.");
		}

		if (moduleAction != null && moduleAction != undefined &&
			!util.module.isAllowedModuleAction(window.em.getAll(EntityType.MODULE), moduleAction.id)) {

			webix.alert({
				title: i18n.t("LBL_MODULE_ACTION_NOT_ALLOWED"),
				text: i18n.t("LBL_THE_MODULE_ACTION_IS_NOT_ALLOWED"),
				type: "alert-error",
				width: 600
			});

			throw new Error("Module action not allowed");

		} else if (!util.module.isAllowedModule(window.em.getAll(EntityType.MODULE), module.id)) {

			webix.alert({
				title: i18n.t("LBL_MODULE_NOT_ALLOWED"),
				text: i18n.t("LBL_THE_MODULE_IS_NOT_ALLOWED"),
				type: "alert-error",
				width: 600
			});

			throw new Error("Module not allowed");
		}


		this.module = module;
		this.moduleAction = moduleAction;
		this.args = args;
		this.uid = webix.uid();
	}

	getView() {

		throw new Error("Method 'getView()' must be implemented.");
	}

	loadData() {

		throw new Error("Method 'loadData()' must be implemented.");
	}

	runOne(method) {
		return this[method]();
	}

}
