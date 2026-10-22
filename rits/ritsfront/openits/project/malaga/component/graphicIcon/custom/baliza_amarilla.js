import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_MEASURE = "ElementTypeParam:36:2:1";

const G_ID_BALIZA_BLINK = "blink";

const CMD_STATE_OFF = "1";
const CMD_STATE_ON_100 = "2";
const CMD_STATE_ON_70 = "3";
const CMD_BLINK_100 = "4";
const CMD_BLINK_70 = "5";

const GROUP_COLOR_OFF = "#b3b3b3";
const GROUP_COLOR_ON = "#ffff00";


const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:36:0", "ElementTypeState:36:2", "ElementTypeState:36:3"];

export default class Baliza extends GraphicIconCustomType {

	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gBalizaBlink = this.g.select("#" + G_ID_BALIZA_BLINK);

	}

	render(elementInfo) {

		this.elementInfo = elementInfo;

		let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

		let cmdGroupValue = this.getValue(PARAM_MEASURE);
		let cmd = cmdGroupValue;

		if (treatmentValue) {
			if (cmd === CMD_STATE_ON_100 || cmd === CMD_STATE_ON_70) {

				this.gBalizaBlink.attr({ from: GROUP_COLOR_ON, to: GROUP_COLOR_ON });

			} else if (cmd === CMD_STATE_OFF) {

				this.gBalizaBlink.attr({ from: GROUP_COLOR_OFF, to: GROUP_COLOR_OFF });

			} else if (cmd === CMD_BLINK_100 || cmd === CMD_BLINK_70) {

				this.gBalizaBlink.attr({ from: GROUP_COLOR_ON, to: GROUP_COLOR_OFF });

			} else {

				this.gBalizaBlink.attr({ from: GROUP_COLOR_OFF, to: GROUP_COLOR_OFF });

			}

		} else {

			this.gBalizaBlink.attr({ from: elementInfo.state.color, to: elementInfo.state.color });

		}


	}

}


