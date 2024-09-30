import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_TEMPERATURE = "ElementTypeParam:86:2:3";
const PARAM_PRESSURE = "ElementTypeParam:86:2:2";

const G_ID_TEMPERATURE = "measure2";
const G_ID_PRESSURE = "measure1";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:86:0","ElementTypeState:86:2"];

export default class Section extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.temperature = this.g.select("#" + G_ID_TEMPERATURE);
        this.pressure = this.g.select("#" + G_ID_PRESSURE);
     

	}
	
	render(elementInfo) {
       
		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {

			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let temperatureVal = this.getValue(PARAM_TEMPERATURE) + " ºC";
			let pressureVal = this.getValue(PARAM_PRESSURE) + " bar";

			if (temperatureVal && treatmentValue) {
					
				this.temperature.attr( { text: temperatureVal} );


			}
			if (pressureVal && treatmentValue) {
				
				this.pressure.attr( { text: pressureVal} );


			}
		}
		
	}
}

