import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_GROUP_COMMAND = "ElementTypeParam:53:2:1";

const G_ID_UPPER_LIGHT = "upperLight";
const G_ID_LOWER_LIGHT = "lowerLight";	

const CMD_GROUP_ON = "1";	

const GROUP_COLOR_OFF = "#b3b3b3";
const GROUP_COLOR_ON = "#ff0000";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:53:0","ElementTypeState:53:2"];

export default class RedTrafficLight extends GraphicIconCustomType {

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
      let cmd = cmdGroupValue;
     
		if(cmd === CMD_GROUP_ON) {

          this.gUpperLight.attr({ from: GROUP_COLOR_ON, to: GROUP_COLOR_OFF });
          this.gLowerLight.attr({ from: GROUP_COLOR_ON, to: GROUP_COLOR_OFF });

        } else {

          this.gUpperLight.attr({ from: GROUP_COLOR_OFF, to: GROUP_COLOR_OFF });
          this.gLowerLight.attr({ from: GROUP_COLOR_OFF, to: GROUP_COLOR_OFF });

        }
			}

		}
		
}


