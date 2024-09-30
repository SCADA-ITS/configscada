import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_TEMP = "ElementTypeParam:86:2:3";

const G_ID_TEMP = "measure1";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:86:0","ElementTypeState:86:2"];

export default class Section extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.TEMP = this.g.select("#" + G_ID_TEMP);
     

	}
	
	render(elementInfo) {
       
		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {

			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let tempVal = this.getValue(PARAM_TEMP) + " ºC";

			if (tempVal && treatmentValue) {
					
				this.TEMP.attr( { text: tempVal} );


			}
		}
		
	}
}

