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

						let container = $$(self.parent.args.containerId);
					 
						if (container) {
						
							let w = container.$width;
							let h = container.$height;
							
							let camViewer = new CamViewer(self.parent.config, element, w, h, CamViewer.MODE_SINGLE, self.parent.config.single.fit);
			
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
				}
			});
		}
	}
}
