import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_SWITCH_COMMAND = "ElementTypeParam:84:2:1";

const G_ID_BULB = "bulb";
const G_ID_COLOR = "colorVal";
const G_ID_TEXT = "textVal";

const CMD_SWITCH_VALUE_MODE_OPEN = 'false';
const CMD_SWITCH_VALUE_MODE_CLOSE = 'true';	

const G_COLOR_BULB_OFF = "#e8edee"
const G_COLOR_BULB_ON = "#f0ce49"
	
const G_COLOR_TEXT_OFF = "red"
const G_COLOR_TEXT_ON = "green"
	
const G_TEXT_OFF = "OFF"
const G_TEXT_ON = "ON"

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:84:0","ElementTypeState:84:2"];

export default class LightBulb extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.gBulb = this.g.select("#" + G_ID_BULB);
		
	}
	
	render(elementInfo) {

		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {

			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let cmdSwitchValue = this.getValue(PARAM_SWITCH_COMMAND);
			
			
			if (cmdSwitchValue && treatmentValue) {
				
				switch (cmdSwitchValue) {

					case CMD_SWITCH_VALUE_MODE_OPEN:
						
						this.gBulb.attr({ fill: G_COLOR_BULB_OFF });

						break;
						
					case CMD_SWITCH_VALUE_MODE_CLOSE:
						this.gBulb.attr({ fill: G_COLOR_BULB_ON });
						

						break;
						
					default:

						this.gBulb.attr({ fill: G_COLOR_BULB_OFF });

				}
			}
			else {

				this.gBulb.attr({ fill: G_COLOR_BULB_OFF });

			}
		}

	}
}

