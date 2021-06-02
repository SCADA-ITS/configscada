import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_TEXT = "ElementTypeParam:10:2:1001";
const LINE_SEPARATOR = ":$";

const G_ID_LINE_PREFIX = "line";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:10:0", "ElementTypeState:10:2"];

export default class VmsTxt extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, layerElement, ratio) {

		super(s, g, elementInfo, layerElement, ratio);
		
		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
	}
	
	render(elementInfo) {
		
		this.elementInfo = elementInfo;
				
		if (this.gState) {
			
			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);
			
			let gLine;
			let i = 0;
			
			let textValues = this.getValue(PARAM_TEXT);		
			
			let linesValue;
			
			if (textValues) {
				
				linesValue = textValues.split(LINE_SEPARATOR);
			}

			while(gLine = this.g.select("#" + G_ID_LINE_PREFIX + (++i))) {

				if (treatmentValue && Array.isArray(linesValue) && linesValue.length >= i) {
					
					gLine.attr( { text: linesValue[i-1]} );	
				}
				else {
					
					gLine.attr( { text: ''} );
				}
			}	
		}
	}
}

