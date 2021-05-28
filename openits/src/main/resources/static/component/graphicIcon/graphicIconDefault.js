import GraphicIcon from "./graphicIcon.js";
import { util } from "../../lib/util.js"; 

export default class GraphicIconDefault extends GraphicIcon {
	
	constructor(layerElement, element, container, events) {

		super(layerElement, element, container, events);
	}
	
	updateState(elementTypeState) {
					
		super.updateState(elementTypeState);
					
		if (this.g) {
			
			let gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
			
			if (gState) {
				
				let color = util.element.getColor(elementTypeState);
				
				gState.attr({
					
					fill: color
				})
			}
		}
	}
	
	updateValues(elementValues) {
		
		super.updateValues(elementValues);
		//console.error("Cannot set values with GraphicIconDefault renderer");
	}
	
	updateAlarms(alarms) {
		
		super.updateAlarms(alarms);
		//console.error("Cannot set alarms with GraphicIconDefault renderer");
	}
}

