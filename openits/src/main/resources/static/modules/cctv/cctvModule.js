import GenericModule from "../../modules/genericModule.js";
import CamViewer from "./camViewer.js";
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

		if (this.moduleAction.id === this.config.moduleAction.singleCam) {
			
			loadSingleCam();
		}
		
		function loadSingleCam() {
			
			let elementId = self.args.selectedPks[0];
			
			if (elementId) {
			
				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.element, { id: elementId })).then(function(elementResponse) {
				
					if (elementResponse != null) {
						
						let element = elementResponse.json();
							
						if (element) {
					
							let camViewer = new CamViewer(self, element);
				
							let viewCamViewer = camViewer.getView();
							
							let viewMain = {
								id: self.uid,
								rows: [
									viewCamViewer
								]
							};
					
							$$("loading-" + self.uid).hide();
					
							webix.ui(viewMain, $$(self.uid));
						}
					}
				});
			}
		}
	}
}
