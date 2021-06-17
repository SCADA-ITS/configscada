import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_RED_LIGHT = "ElementTypeParam:13:2:2";
const PARAM_AMBER_LIGHT = "ElementTypeParam:13:2:3";
const PARAM_GREEN_LIGHT = "ElementTypeParam:13:2:4";


const G_ID_GROUP_GREEN = "green";
const G_ID_GROUP_RED = "red";
const G_ID_GROUP_YELLOW = "yellow";	

const GROUP_COLOR_OFF = "#b3b3b3";
const GROUP_COLOR_RED = "#eb5569";
const GROUP_COLOR_GREEN = "#0ab69f";
const GROUP_COLOR_YELLOW = "#fec165";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:13:0","ElementTypeState:13:2"];

export default class TrafficLight extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.gRed = this.g.select("#" + G_ID_GROUP_RED);
		this.gGreen = this.g.select("#" + G_ID_GROUP_GREEN);
		this.gAmber = this.g.select("#" + G_ID_GROUP_YELLOW)
	}
	
	render(elementInfo) {

		let self = this;        
		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {
           
			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let redLight = this.getValue(PARAM_RED_LIGHT);
			let amberLight = this.getValue(PARAM_AMBER_LIGHT);
			let greenLight = this.getValue(PARAM_GREEN_LIGHT);

			
			if (treatmentValue) {
				if(redLight == "true"){
					switchOff();
					this.gRed.attr({ fill: GROUP_COLOR_RED });

				}else if (amberLight == "true"){
					switchOff();
					this.gAmber.attr({ fill: GROUP_COLOR_YELLOW });

				}else if(greenLight == "true"){
					switchOff();
					this.gGreen.attr({ fill: GROUP_COLOR_GREEN });

				}else{
					
					console.error("graphicicon/custom/TrafficLight_MODBUS.js => " + elementInfo.id + " TRAFFIC_LIGHT WITHOUT VALUE");
				}
			}
			else {
				
				switchOff();
			}
		}
		
		function switchOff() {
			
			self.gRed.attr({ fill: GROUP_COLOR_OFF });
			self.gAmber.attr({ fill: GROUP_COLOR_OFF });
			self.gGreen.attr({ fill: GROUP_COLOR_OFF });

		}
	}
}

