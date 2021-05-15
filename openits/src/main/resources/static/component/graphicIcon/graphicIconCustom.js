import GraphicIcon from "./graphicIcon.js";
import {util} from "../../lib/util.js";

export default class GraphicIconCustom extends GraphicIcon {
	
	constructor(layerElement, element, container, events) {

		super(layerElement, element, container, events);
		
		this.graphicIconCustomType = undefined;
		this.elementInfo = {
			state: undefined,
			values: [],
			alarms: []
		}
	}
	
	loadRender(onRenderReady) {
		
		let self = this;
			
		let graphicIcon = self.layerElement.graphicIcon;
		
		if (graphicIcon.jsCustomTypeRender) {
			
			const lazy = async _ => {

				//let jsFile = window.location.protocol + '//' + window.location.host + '/' + graphicIcon.jsCustomTypeRender;
				let jsFile = util.getContextPath() + "/" + graphicIcon.jsCustomTypeRender;

				const mod = await import(jsFile);
				
				self.elementInfo = getElementInfo();
				
				self.graphicIconCustomType = new mod.default(self.container.obj, self.g, self.elementInfo, self.layerElement, self.ratio);
				
				onRenderReady();
			}
			
			lazy();
		}
		else {
			
			console.error("Render not configured for graphicIcon = " + graphicIcon.id);
			onRenderReady();
		}
		
		function getElementInfo() {
			
			return {
				
				state: getState(),
				values: getValues(),
				alarms: []
			}
			
			function getState() {
				
				let id = self.element.elementTypeState.id;
				
				let color;
			
				if (!(color = self.element.elementTypeState.color)) {
						
					color = GraphicIcon.DEFAULT_STATE_COLOR(self.element.elementTypeState.id.substring(elementState.lastIndexOf(":") + 1));  
				}
				
				return {
					id: id,
					color: color 
				}
			}
			
			function getValues() {
				
				return self.element.elementValues.map(e => {
					
					return {
						
						id: e.elementTypeParam.id,
						value: e.value	
					}
				});
			}
		}
	}
	
	updateState(elementTypeState) {
		
		super.updateState(elementTypeState);
		let id = elementTypeState.id;
		
		if (this.graphicIconCustomType) {
			
			let color;
			
			if (!(color = elementTypeState.color)) {
					
				color = GraphicIcon.DEFAULT_STATE_COLOR(id.substring(id.lastIndexOf(":") + 1));  
			}
			
			this.elementInfo.state = {
				id: id,
				color: color 
			};
			
			this.graphicIconCustomType.render(this.elementInfo);	
		}
	}
	
	updateValues(elementValues) {
	
		super.updateAlarms(elementValues);
		let currentValues = this.elementInfo.values;
		
		if (this.graphicIconCustomType) {
			
			if (Array.isArray(elementValues) && elementValues.length > 0) {
			
				elementValues.forEach(elementValue => {
			
					let currentValue = currentValues.find(e => e.id === elementValue.elementTypeParam.id);
					
					if (currentValue) {
						
						currentValue.value = elementValue.value;
					}		
					else {
						
						currentValues.push({
							
							id: elementValue.elementTypeParam.id,
							value: elementValue.value	
						});
					}
				});
			}
			
			this.elementInfo.values = currentValues;
			
			this.graphicIconCustomType.render(this.elementInfo);	
		}
	}
	
	updateAlarms(alarms) {
		
		super.updateAlarms(alarms);
		// TODO
	}
}

