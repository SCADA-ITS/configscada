import GraphicIcon from "./graphicIcon.js";

export default class GraphicIconDefault extends GraphicIcon {
	
	constructor(layerElement, element, container, events) {

		super(layerElement, element, container, events);
	}
	
	updateState(elementTypeState) {
					
		super.updateState(elementTypeState);
					
		if (this.g) {
			
			let gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
			
			if (gState) {
				
				let color;
				
				if (!(color = elementTypeState.color)) {
					
					color = GraphicIcon.DEFAULT_STATE_COLOR(elementTypeState.id.substring(elementTypeState.id.lastIndexOf(":") + 1));  
				}
				
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

