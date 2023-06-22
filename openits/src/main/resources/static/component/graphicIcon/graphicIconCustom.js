import GraphicIcon from "./graphicIcon.js";
import {util} from "../../lib/util.js";

export default class GraphicIconCustom extends GraphicIcon {
	
	constructor(layerElement, element, container, events) {

		super(layerElement, element, container, events);
		
		this.graphicIconCustomType = undefined;
		this.elementInfo = {
			id: element.id,
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

				let jsFile = util.getContextPath() + "/" + graphicIcon.jsCustomTypeRender;

				const mod = await import(jsFile);
				
				self.elementInfo = getElementInfo();
				
				self.graphicIconCustomType = new mod.default(self.container.obj, self.g, self.elementInfo, self.events);
				
				onRenderReady();
			}
			
			lazy();
		}
		else {
			
			log.warn("Render not configured for graphicIcon = " + graphicIcon.id);
			onRenderReady();
		}
		
		function getElementInfo() {
			
			return {
				
				id: self.element.id,
				state: getState(),
				values: getValues(),
				alarms: []
			}
			
			function getState() {
				
				let id = self.element.elementState ? self.element.elementState.currentElementTypeState.id : self.element.elementTypeState.id;
				let color = self.element.elementState ? util.element.getColorByState(self.element.elementState) : util.element.getColor(self.element.elementTypeState);
							
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
			
			let color = util.element.getColor(elementTypeState);
					
			this.elementInfo.state = {
				id: id,
				color: color 
			};
			
			this.graphicIconCustomType.render(this.elementInfo);	
		}
	}
	
	updateElementState(elementState) {
		
		super.updateElementState(elementState);
		let id = elementState.currentElementTypeState.id;
		
		if (this.graphicIconCustomType) {
			
			let color = util.element.getColorByState(elementState);
					
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
	
	stop() {
		
		if (this.graphicIconCustomType) {
			
			this.graphicIconCustomType.stop();
		}
	}
	
	updateAlarms(alarms) {
		
		super.updateAlarms(alarms);
		// TODO
	}
	
	show() {
		
		super.show();
		
		if (this.graphicIconCustomType && typeof this.graphicIconCustomType.reset === "function") { 
			    
			this.graphicIconCustomType.reset();
			this.graphicIconCustomType.render(this.elementInfo);
		}
	}
	
	getConfiguration(){
		
		if(this.graphicIconCustomType.getConfiguration){
			
			return this.graphicIconCustomType.getConfiguration();
		}
	}
}

