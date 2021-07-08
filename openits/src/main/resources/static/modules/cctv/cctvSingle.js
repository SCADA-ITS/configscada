import CamViewer from "./camViewer.js";
import { util } from "../../lib/util.js";


export default class CCTVSingle {

	constructor(parent, elementId, onLoad) {

		this.parent = parent;
		this.elementId = elementId;
		this.onLoad = onLoad;
	}

	loadData() {
		
		let self = this;
			
		if (self.elementId) {
		
			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.element, { id: self.elementId })).then(function(elementResponse) {
			
				if (elementResponse != null) {
					
					let element = elementResponse.json();
						
					if (element) {

						let w, h;
						
						if (self.parent.config.single.fit) {
							
							w = "100%";
							h = "100%";
						}
						else {
							
							let container = $$(self.parent.args.containerId);
							 
							if (container) {
								
								w = container.$width + "px";
								h = container.$height + "px";
							}
						}
				
						let camViewer = new CamViewer(self.parent.config.camConf, element, w, h);
			
						let viewCamViewer = camViewer.getView();
						
						let viewMain = {
							id: self.parent.uid,
							rows: [
								viewCamViewer
							]
						};
						
						self.onLoad(viewMain);
					}
				}
			});
		}
	}
}
