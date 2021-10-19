import { util } from "../lib/util.js";
import MessageBox from "../component/messageBox.js";

export default class GenericModule {

	constructor(module, moduleAction, args) {

		if (this.constructor == GenericModule) {

			throw new Error("Abstract classes can't be instantiated.");
		}

		if (moduleAction &&	!util.module.isAllowedModuleAction(window.em.getAll(EntityType.MODULE), moduleAction.id)) {
			
			let ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_THE_MODULE_ACTION_IS_NOT_ALLOWED"), null, null);
			ms.show();
			
			throw new Error("Module action not allowed");

		} else if (!util.module.isAllowedModule(window.em.getAll(EntityType.MODULE), module.id)) {

			let ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_THE_MODULE_ACTION_IS_NOT_ALLOWED"), null, null);
			ms.show();

			throw new Error("Module not allowed");
		}


		this.module = module;
		this.moduleAction = moduleAction;
		this.args = args;
		this.uid = webix.uid();
	}

	getView() {

		return this.getDefaultView();
	}
	
	getViewCheckModuleAction() {

		let view = undefined;
		
		if (this.moduleAction) {
			
			view = this.getDefaultView();	
		}
		else {
			
			let ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_MODULE_OPEN_ERROR_NO_MODULE_ACTION"), null, null);
			ms.show();
			
		}
		
		return view;
	}
	
	getViewCheckModuleActionAndSelectedPks() {

		let view = undefined;
		
		if (this.moduleAction && Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {
			
			view = this.getDefaultView();	
		}
		else {
			
			let ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_MODULE_OPEN_ERROR_NO_MODULE_ACTION_OR_IDS"), null, null);
			ms.show();

		}
		
		return view;
	}
	
	getDefaultView() {
		
		return {
			css: "custom_window",
			cols: [
				{
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									id: this.uid,
									rows: [],
									hidden: true
								},
								{
									id: "loading-" + this.uid,
									rows: [util.ui.getViewDrawLoading()],
									hidden: false
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		};
	} 

	loadData() {

		throw new Error("Method 'loadData()' must be implemented.");
	}

	runOne(method) {
		return this[method]();
	}

}
