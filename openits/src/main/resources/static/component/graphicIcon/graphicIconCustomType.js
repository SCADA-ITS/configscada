export default class GraphicIconCustomType {
	
	constructor(s, g, elementInfo, layerElement, ratio) {

		if (this.constructor == GraphicIconCustomType) {

			throw new Error("Abstract classes can't be instantiated.");
		}
		
		this.s = s;
		this.g = g;
		this.elementInfo = elementInfo;
		this.layerElement = layerElement;
		this.ratio = ratio;
		
		this.x = layerElement.x;
		this.y = layerElement.y;
		
		this.width = layerElement.graphicIcon.width / ratio;
		this.height = layerElement.graphicIcon.height / ratio;
		
//		this.isVisible = true;
	}
	
	render(elementInfo) {
		
		throw new Error("Method 'render(elementInfo)' must be implemented.");
	}
	
	visible(value) {
		
		throw new Error("Method 'visible(value)' must be implemented.");
	}
	
	getValue(elementParamId) {
		
		let value = undefined;
		
		if (this.elementInfo && Array.isArray(this.elementInfo.values) && this.elementInfo.values.length > 0) {
			
			let elementInfoValue = this.elementInfo.values.find(e => e.id === elementParamId);
			
			if (elementInfoValue) {
				
				value = elementInfoValue.value; 
			}
		}
		
		return value;
	}
}
