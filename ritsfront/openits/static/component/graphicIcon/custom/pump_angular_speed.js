import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_ANG_SPEED = "ElementTypeParam:86:2:4";

const G_ID_ANG_SPEED = "measure1";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:86:0","ElementTypeState:86:2"];

export default class Section extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.ang_speed = this.g.select("#" + G_ID_ANG_SPEED);
     

	}
	
	render(elementInfo) {
       
		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {

			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let ang_speedVal = this.getValue(PARAM_ANG_SPEED) + "m/s";

			if (ang_speedVal && treatmentValue) {
					
				this.ang_speed.attr( { text: ang_speedVal} );


			}
		}
		
	}
}

