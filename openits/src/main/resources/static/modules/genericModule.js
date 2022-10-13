import { util } from "../lib/util.js";
import EntitiesManager from "../managers/entitiesManager.js";
import MessageBox from "../component/messageBox.js";

export default class GenericModule {

	constructor(module, moduleAction, args, entitiesRequired) {

		if (this.constructor == GenericModule) {

			throw new Error("Abstract classes can't be instantiated.");
		}

		if (moduleAction &&	!util.module.isAllowedModuleAction(window.em.getAll(EntityType.MODULE), moduleAction.id)) {
			
			let ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_THE_MODULE_ACTION_IS_NOT_ALLOWED"), null, null, null);
			ms.show();
			
			throw new Error("Module action not allowed");

		} else if (!util.module.isAllowedModule(window.em.getAll(EntityType.MODULE), module.id)) {

			let ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_THE_MODULE_ACTION_IS_NOT_ALLOWED"), null, null, null);
			ms.show();

			throw new Error("Module not allowed");
		}

		this.waitingForEntities = false;
		this.module = module;
		this.moduleAction = moduleAction;
		this.args = args;
		this.uid = webix.uid();
		
		// Sleep for required entities
		if (Array.isArray(entitiesRequired) && entitiesRequired.length > 0) {
		
			this.waitingForEntities = true;	
			let hnd;
			let self = this;
			
			hnd = setInterval(function() {
				
				let allEntitiesLoaded = true;
				
				for (let i = 0; allEntitiesLoaded && i < entitiesRequired.length; i++) {
						
					allEntitiesLoaded &= (window.em.getState(entitiesRequired[i]) === EntitiesManager.ENTITY_STATE_LOADED);
				}
				
				if (allEntitiesLoaded) {
					
					clearInterval(hnd);
					
					self.waitingForEntities = false;
				}
	
			}, 100);
		}
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
			
			let ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_MODULE_OPEN_ERROR_NO_MODULE_ACTION"), null, null, null);
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
			
			let ms = new MessageBox (MessageBox.ERROR, i18n.t("LBL_MODULE_OPEN_ERROR_NO_MODULE_ACTION_OR_IDS"), null, null, null);
			ms.show();

		}
		
		return view;
	}
	
	getDefaultView() {
		
		return {
			css: "custom_window",
			cols: [
				{
					css: {"background-color": "white"},
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
	
	destroy() {
		
	}
	
	refresh() {
		
		let container = $$(this.args.containerId);
		
		if (container) {
			
			if (this.args.containerId.includes("tab_body")) {
				
				let tabId = this.args.containerId.replace("tab_body", "tab");
				
				let tabUndockActionDom = document.getElementById(tabId + '_action');
			
				if (tabUndockActionDom) {
					
					tabUndockActionDom.innerHTML = `<span id='${tabId}_action' style='float: right; padding-top: 2px'><a href='#' onclick='window.sm.getService(Services.TASK_MANAGER_SERVICE).undock(${JSON.stringify(tabId)}, ${JSON.stringify(this)})'><img id='${tabId}-externalize' title='${i18n.t("LBL_VIEW_IN_WINDOW")}' src='./img/externalize_button.png'></a></span>`;
				}
			}
		}
	}
}
