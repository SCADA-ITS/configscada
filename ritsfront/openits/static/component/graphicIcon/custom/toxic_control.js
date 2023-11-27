import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_GATE_OPENED = "ElementTypeParam:48:2:2";

const G_ID_SWITCH = "switch";

const SWITCH_X_OPEN = 6.1692076
const SWITCH_Y_OPEN = 14.114111
const SWITCH_OPEN_POSITION = -31.931037

const SWITCH_X_CLOSE = 23.174528
const SWITCH_Y_CLOSE = 4.6745257
const SWITCH_CLOSE_POSITION = 0

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:48:0","ElementTypeState:48:2"];

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

			let value_opened = this.getValue(PARAM_GATE_OPENED);
			
			if (treatmentValue) {
				
				if(value_opened == "true"){
					this.gSwitch.transform("rotate(" + SWITCH_OPEN_POSITION + ")");
					this.gSwitch.node.setAttribute("x", SWITCH_X_OPEN);
					this.gSwitch.node.setAttribute("y", SWITCH_Y_OPEN);

				}else{
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

