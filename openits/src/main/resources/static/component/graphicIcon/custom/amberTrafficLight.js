import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_GROUP_COMMAND = "ElementTypeParam:53:2:1";

const G_ID_UPPER_LIGHT = "upperLight";
const G_ID_LOWER_LIGHT = "lowerLight";	

const GROUP_COLOR_OFF = "#b3b3b3";
const GROUP_COLOR_ON = "#ffff00";

const CMD_GROUP_OFF = "1";
const CMD_GROUP_AMBER_ALTERNANT = "0";	
const CMD_GROUP_AMBER_FLASHING = "2";	
const CMD_GROUP_AMBER = "3";	


const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:53:0","ElementTypeState:53:2"];

export default class AmberTrafficLight extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.gUpperLight = this.g.select("#" + G_ID_UPPER_LIGHT);
        this.gLowerLight = this.g.select("#" + G_ID_LOWER_LIGHT);

	}
	
	render(elementInfo) {
       
		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {

			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let cmdGroupValue = this.getValue(PARAM_GROUP_COMMAND);

			if (cmdGroupValue && treatmentValue) {

				let cmd = cmdGroupValue;

				if(cmd === CMD_GROUP_OFF) {
					
					this.gUpperLight.attr({ from: GROUP_COLOR_OFF, to: GROUP_COLOR_OFF });
					this.gLowerLight.attr({ from: GROUP_COLOR_OFF, to: GROUP_COLOR_OFF });
					
				} else if(cmd === CMD_GROUP_AMBER){

					this.gUpperLight.attr({ from: GROUP_COLOR_ON, to: GROUP_COLOR_ON });
					this.gLowerLight.attr({ from: GROUP_COLOR_ON, to: GROUP_COLOR_ON });

				} else if(cmd === CMD_GROUP_AMBER_FLASHING){
	
					this.gUpperLight.attr({ from: GROUP_COLOR_ON, to: GROUP_COLOR_OFF });
					this.gLowerLight.attr({ from: GROUP_COLOR_ON, to: GROUP_COLOR_OFF });
	
				} else if(cmd === CMD_GROUP_AMBER_ALTERNANT){
				
					this.gUpperLight.attr({ from: GROUP_COLOR_ON, to: GROUP_COLOR_OFF });
					this.gLowerLight.attr({ from: GROUP_COLOR_OFF, to: GROUP_COLOR_ON });
				}

			
			}

		}
		
	}
}

