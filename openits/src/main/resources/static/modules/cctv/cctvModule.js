import GenericModule from "../../modules/genericModule.js";
import CCTVSingle from "./cctvSingle.js";
import CCTVMulti from "./cctvMulti.js";
import { util } from "../../lib/util.js";

export default class CCTVModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.stompClient = undefined;
		this.config = module.config;

	}

	loadData() {

		let self = this;
		
		$$("loading-" + self.uid).show();
		
		if (this.moduleAction && Array.isArray(self.args.selectedPks) && self.args.selectedPks.length === 1) {
			
			if (this.moduleAction.id === this.config.moduleAction.singleCam) {
			
				loadSingleCam(self.args.selectedPks[0]);
			}
			else if(this.moduleAction.id === this.config.moduleAction.scenario) {
				
				loadScenario(self.args.selectedPks[0]);
			}	
		}
		else {
			
			loadScenario();
		}
		
		function loadSingleCam(elementId) {
			
			(new CCTVSingle(self, elementId, onLoad)).loadData();
		}
		
		function loadScenario(scenarioId) {
			
			(new CCTVMulti(self, scenarioId, onLoad)).loadData();
		}
		
		function onLoad(view, afterRender) {
			
			$$("loading-" + self.uid).hide();
			webix.ui(view, $$(self.uid));
			
			if (afterRender) {
				
				afterRender();
			}
		}
	}
}
