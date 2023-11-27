import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const ELEMENT_TYPE_ID = "ElementType:86";

const PARAM_COMMAND_VALUE_ID = "ElementTypeParam:86:2:1";



const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:86:0","ElementTypeState:86:2"];
const G_ID_ARROW_UP = "arrowUp";
const G_ID_ARROW_DOWN = "arrowDown";
const G_ID_FAN = "fan";
const G_ID_ANIMATION = "animation";



const SPEED_FAN = 1.3; // From 1 to 10. Lower value is more slow
const MAX_TIME_ROTATE = 10;
const FAN_START_POSITION = "0 29.3 28.5";
const FAN_END_POSITION = "360 29.3 28.5";



const CMD_PUMP_ON = "true";	


export default class Pump extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);
		
		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.elementTypeStatesNoTreatment = ELEMENT_TYPE_STATES_NO_TREATMENT;

		this.paramCommandValueId = PARAM_COMMAND_VALUE_ID;
		this.elementTypeId = ELEMENT_TYPE_ID;
		
		this.gArrowUp = this.g.select("#" + G_ID_ARROW_UP);
        this.gArrowDown = this.g.select("#" + G_ID_ARROW_DOWN);

        this.gFan = this.g.select("#" + G_ID_FAN)
        this.gAnimation = this.g.select("#" + G_ID_ANIMATION);

        
	}
	
	render(elementInfo) {
	       
		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {

            let timeFanRotate = MAX_TIME_ROTATE + 1 - SPEED_FAN + "s";


			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let cmdGroupValue = this.getValue(PARAM_COMMAND_VALUE_ID);

            if (cmdGroupValue && treatmentValue) {

				
				if(cmdGroupValue == CMD_PUMP_ON){
					this.gArrowUp.attr({ "fill-opacity": 1 });
                    this.gArrowDown.attr({ "fill-opacity": 1 });
                    this.gAnimation.attr({ dur: timeFanRotate, from: FAN_START_POSITION, to: FAN_END_POSITION });

				}else {
					this.gArrowUp.attr({ "fill-opacity": 0 });
                    this.gArrowDown.attr({ "fill-opacity": 0 });
                    this.gAnimation.attr({ dur: timeFanRotate, from: FAN_START_POSITION, to: FAN_START_POSITION });

				}
			}

		}
		
	}
}