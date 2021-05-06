import GraphicIcon from "./graphicIcon.js";

export default class GraphicIconDefault extends GraphicIcon {
	
	constructor(layerElement, element, container) {

		super(layerElement, element, container);
	}
	
	updateState(elementTypeState) {
								
		if (this.g) {
			
			let gState = this.g.select(GraphicIcon.G_ID_STATE);
			
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
		
		console.error("Cannot set values with GraphicIconDefault renderer");
	}
	
	updateAlarms(alarms) {
		
		console.error("Cannot set alarms with GraphicIconDefault renderer");
	}
}

