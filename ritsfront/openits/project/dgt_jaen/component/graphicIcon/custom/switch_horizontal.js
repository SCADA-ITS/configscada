import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_SWITCH_COMMAND = "ElementTypeParam:57:2:1";

const G_ID_SWITCH = "switch";

const CMD_SWITCH_VALUE_MODE_OPEN = 'false';
const CMD_SWITCH_VALUE_MODE_CLOSE = 'true';	

const SWITCH_X_OPEN = -34.740414
const SWITCH_Y_OPEN = -7.4178634
const SWITCH_OPEN_POSITION = -113.58734

const SWITCH_X_CLOSE = -26.956558
const SWITCH_Y_CLOSE = 4.542748
const SWITCH_CLOSE_POSITION = -90.301217

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:57:0","ElementTypeState:57:2"];

export default class Switch extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.gSwitch = this.g.select("#" + G_ID_SWITCH);
	}
	
	render(elementInfo) {

		this.elementInfo = elementInfo;

		// Update state
		if (this.gState && this.gSwitch) {

			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let cmdSwitchValue = this.getValue(PARAM_SWITCH_COMMAND);
			
			if (cmdSwitchValue && treatmentValue) {
				
				switch (cmdSwitchValue) {

					case CMD_SWITCH_VALUE_MODE_OPEN:
						
						this.gSwitch.transform("rotate(" + SWITCH_OPEN_POSITION + ")");
						this.gSwitch.node.setAttribute("x", SWITCH_X_OPEN);
						this.gSwitch.node.setAttribute("y", SWITCH_Y_OPEN);

						break;
						
					case CMD_SWITCH_VALUE_MODE_CLOSE:
						
						this.gSwitch.transform("rotate(" + SWITCH_CLOSE_POSITION + ")");
						this.gSwitch.node.setAttribute("x", SWITCH_X_CLOSE);
						this.gSwitch.node.setAttribute("y", SWITCH_Y_CLOSE);

						break;
						
					default:

						this.gSwitch.transform("rotate(" + SWITCH_CLOSE_POSITION + ")");
						this.gSwitch.node.setAttribute("x", SWITCH_X_CLOSE);
						this.gSwitch.node.setAttribute("y", SWITCH_Y_CLOSE);
				}
			}
			else {

				this.gSwitch.transform("rotate(" + SWITCH_CLOSE_POSITION + ")");
				this.gSwitch.node.setAttribute("x", SWITCH_X_CLOSE);
				this.gSwitch.node.setAttribute("y", SWITCH_Y_CLOSE);
			}
			
		}

	}
}

