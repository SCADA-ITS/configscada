export default class GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		if (this.constructor == GraphicIconCustomType) {

			throw new Error("Abstract classes can't be instantiated.");
		}
		
		this.s = s;
		this.g = g;
		this.elementInfo = elementInfo;
		this.events = events;
	}
	
	render(elementInfo) {
		
		throw new Error("Method 'render(elementInfo)' must be implemented.");
	}
	
	stop() {
		
		
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
