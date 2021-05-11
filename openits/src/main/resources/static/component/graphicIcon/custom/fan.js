import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_FAN_COMMAND = "ElementTypeParam:9:2:1";

const G_ID_ANIMATION = "#animation";
const G_ID_ARROW = "#arrow";
const G_ID_FAN = "#fan";

const CMD_FAN_VALUE_STOP = 0;
const CMD_FAN_VALUE_MODE_DIRECT = 1;
const CMD_FAN_VALUE_MODE_REVERSE = 2;

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:9:0", "ElementTypeState:9:2"];

export default class Fan extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, layerElement, ratio) {

		super(s, g, elementInfo, layerElement, ratio);
	}
	
	render(elementInfo) {
		
		this.elementInfo = elementInfo;
		
		// Update state
		let gState = this.g.select(GraphicIcon.G_ID_STATE);
		let gFan = this.g.select(G_ID_FAN); 
		let gAnimation = this.g.select(G_ID_ANIMATION);
		let gArrow = this.g.select(G_ID_ARROW);
			
		if (gState && gFan && gAnimation && gArrow) {
			
			gState.attr({ fill: elementInfo.state.color });
			gFan.attr({ fill: elementInfo.state.color });
			
			// Update others
			let cmdFanValue = this.getValue(PARAM_FAN_COMMAND);
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);
			
			if (cmdFanValue && treatmentValue) {
				
				switch (parseInt(cmdFanValue)) {
				
					case CMD_FAN_VALUE_MODE_DIRECT:
					
						gAnimation.attr({ dur: "6s", from: "0 245 410", to: "360 245 410" });
						gArrow.attr({ visibility: "visible" });
						gArrow.transform("r0," + gArrow.getBBox().cx + "," + gArrow.getBBox().cy);

						break;
						
					case CMD_FAN_VALUE_MODE_REVERSE:
					
						gAnimation.attr({ dur: "6s", from: "360 245 410", to: "0 245 410" });
						gArrow.attr({ visibility: "visible" });
					    gArrow.transform("r180," + gArrow.getBBox().cx + "," + gArrow.getBBox().cy);
	
						break;
						
					default:
					
						gAnimation.attr({ dur: "indefinite" });
						gArrow.attr({ visibility: "hidden" });
				}	
			}
			else {
				
				gAnimation.attr({ dur: "indefinite" });
				gArrow.attr({ visibility: "hidden" });
			}
			
			
			
			//this.g.attr( { transform: "rotateX( 180deg )" });
		}
	}
}

