import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_FAN_COMMAND = "ElementTypeParam:9:2:1";
const PARAM_FAN_FLIP = "ElementTypeParam:9:1:6";

const G_ID_ANIMATION = "animation";
const G_ID_ARROW = "arrow";
const G_ID_FAN = "fan";

const CMD_FAN_VALUE_MODE_DIRECT = 1;
const CMD_FAN_VALUE_MODE_REVERSE = 2;

const SPEED_FAN = 5; // From 1 to 10. Lower value is more slow
const MAX_TIME_ROTATE = 10;
const FAN_START_POSITION = "0 305 305";
const FAN_END_POSITION = "360 305 305";
const ARROW_COLOR_ENABLED = "#323232";
const ARROW_COLOR_DISABLED = "transparent";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:9:0", "ElementTypeState:9:2"];

export default class Fan extends GraphicIconCustomType {

	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.gFan = this.g.select("#" + G_ID_FAN);
		this.gAnimation = this.g.select("#" + G_ID_ANIMATION);
		this.gArrow = this.g.select("#" + G_ID_ARROW);
		
		if (IS_TRUE(this.getValue(PARAM_FAN_FLIP))) {
			
			this.flip();	
		}
	}

	render(elementInfo) {

		this.elementInfo = elementInfo;

		// Update state

		if (this.gState && this.gFan && this.gAnimation && this.gArrow) {

			let timeFanRotate = MAX_TIME_ROTATE + 1 - SPEED_FAN + "s";

			this.gState.attr({ fill: elementInfo.state.color });
			this.gFan.attr({ fill: elementInfo.state.color });

			// Update others
			let cmdFanValue = this.getValue(PARAM_FAN_COMMAND);
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			if (cmdFanValue && treatmentValue) {
				if (cmdFanValue == CMD_FAN_VALUE_MODE_DIRECT) {
					this.gArrow.attr({ fill: ARROW_COLOR_ENABLED });
					this.gAnimation.attr({ dur: timeFanRotate, from: FAN_START_POSITION, to: FAN_END_POSITION });
					this.gArrow.transform("r0," + this.gArrow.getBBox().cx + "," + this.gArrow.getBBox().cy);
				} else if (cmdFanValue == CMD_FAN_VALUE_MODE_REVERSE) {
					this.gArrow.attr({ fill: ARROW_COLOR_ENABLED });
					this.gAnimation.attr({ dur: timeFanRotate, from: FAN_END_POSITION, to: FAN_START_POSITION });
					this.gArrow.transform("r180," + this.gArrow.getBBox().cx + "," + this.gArrow.getBBox().cy);
				} else {
					this.gAnimation.attr({ dur: timeFanRotate, from: FAN_START_POSITION, to: FAN_START_POSITION });
					this.gArrow.attr({ fill: ARROW_COLOR_DISABLED });
				}
			}
			else {

				this.gAnimation.attr({ dur: timeFanRotate, from: FAN_START_POSITION, to: FAN_START_POSITION });
				this.gArrow.attr({ fill: ARROW_COLOR_DISABLED });
			}
		}
	}
}

