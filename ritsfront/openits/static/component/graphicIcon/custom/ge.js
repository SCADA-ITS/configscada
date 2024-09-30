import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_TEMPERATURE1 = "ElementTypeParam:75:2:19";
const PARAM_TEMPERATURE2 = "ElementTypeParam:75:2:22";

const G_ID_TEMPERATURE1 = "temp1";
const G_ID_TEMPERATURE2 = "temp2"

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:75:0","ElementTypeState:75:2"];

export default class Section extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.temp1 = this.g.select("#" + G_ID_TEMPERATURE1);
        this.temp2 = this.g.select("#" + G_ID_TEMPERATURE2);
     

	}
	
	render(elementInfo) {
       
		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {

			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let temperature1Val = this.getValue(PARAM_TEMPERATURE1) + " ºC";
			let temperature2Val = this.getValue(PARAM_TEMPERATURE2) + " ºC";

			if (temperature1Val && treatmentValue) {
					
				this.temp1.attr( { text: temperature1Val} );


			}
			if (temperature2Val && treatmentValue) {
				
				this.temp2.attr( { text: temperature2Val} );


			}
		}
		
	}
}

