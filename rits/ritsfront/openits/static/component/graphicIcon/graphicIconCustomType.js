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
	
	reset() {
		
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
	
	flip() {
		
		let self = this;
		
		const INIT_TRANSFORM_S = "s" + [-1, 1].join();
		
		let transform_S;
		
		if (self.g.transform().globalMatrix) {
			
			let x = self.g.transform().globalMatrix.e + (self.g.getBBox().width);
			let y = self.g.transform().globalMatrix.f;
			let sx = self.g.transform().globalMatrix.a * -1;
			let sy = self.g.transform().globalMatrix.d;
			
			let transformStr = "t" + [x, y].join() + ",s" + [sx, sy, 0, 0].join(); 
			self.g.transform(transformStr);
		}
		
		let childs = self.g.selectAll("*");
		
		if (childs && Array.isArray(childs.items)) {
			
			childs.items.filter(e => e.type === "text").forEach(textNode => {
			
				transform_S = INIT_TRANSFORM_S;
				
				if (textNode.transform().string && textNode.transform().string.indexOf("s") > -1) {
					
					transform_S = textNode.transform().string.replace("s", "s-");
				}
				
				textNode.transform(transform_S);
			});	
		}
	}
}
