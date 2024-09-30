import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_TEMPERATURE = "ElementTypeParam:62:2:2";
const PARAM_PRESSURE = "ElementTypeParam:62:2:1";
const PARAM_FLOW_RATE = "ElementTypeParam:62:2:3";

const G_ID_TEMPERATURE = "temperature";
const G_ID_PRESSURE = "pressure";
const G_ID_FLOW_RATE = "flow_rate";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:62:0","ElementTypeState:62:2"];

export default class Section extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.temperature = this.g.select("#" + G_ID_TEMPERATURE);
        this.pressure = this.g.select("#" + G_ID_PRESSURE);
        this.flow_rate = this.g.select("#" + G_ID_FLOW_RATE);
     

	}
	
	render(elementInfo) {
       
		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {

			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let temperatureVal = this.getValue(PARAM_TEMPERATURE) + " ºC";
			let pressureVal = this.getValue(PARAM_PRESSURE) + " bar";
			let flow_rateVal = this.getValue(PARAM_FLOW_RATE) + " kg/s";

			if (temperatureVal && treatmentValue) {
					
				this.temperature.attr( { text: temperatureVal} );


			}
			if (pressureVal && treatmentValue) {
				
				this.pressure.attr( { text: pressureVal} );


			}
			if (flow_rateVal && treatmentValue) {
				
				this.flow_rate.attr( { text: flow_rateVal} );


			}
			
		}
		
	}
}

