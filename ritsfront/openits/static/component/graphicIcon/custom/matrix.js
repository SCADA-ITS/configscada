import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const MARGIN_MATRIX = 5;


export default class Matrix extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);
	}
	
	render(elementInfo) {

		this.elementInfo = elementInfo;
		
//		let position = "t" + [this.x, this.y].join();
//		let scale = "s" + ((this.width / this.layerElement.graphicIcon.width)  + "," + (this.height / this.layerElement.graphicIcon.height));
//		
//		this.g.transform(position + ',' + "s1,1");
		
		let r = this.s.rect(0 + MARGIN_MATRIX, 0 + MARGIN_MATRIX, this.width - (MARGIN_MATRIX * 2), this.height - (MARGIN_MATRIX * 2)).attr({ fill: 'red', stroke: 'black', strokeWidth: 5 });
		
		this.g.append(r);
	}
	
	visible(value) {
		
	}
}
