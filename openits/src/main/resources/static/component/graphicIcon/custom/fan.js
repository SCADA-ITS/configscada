import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_FAN_COMMAND = "ElementTypeParam:9:2:1";

const G_ID_ANIMATION = "animation";
const G_ID_ARROW = "arrow";
const G_ID_FAN = "fan";

const CMD_FAN_VALUE_STOP = 0;
const CMD_FAN_VALUE_MODE_DIRECT = 1;
const CMD_FAN_VALUE_MODE_REVERSE = 2;

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:9:0", "ElementTypeState:9:2"];

export default class Fan extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, layerElement, ratio) {

		super(s, g, elementInfo, layerElement, ratio);
		
		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.gFan = this.g.select("#" + G_ID_FAN); 
		this.gAnimation = this.g.select("#" + G_ID_ANIMATION);
		this.gArrow = this.g.select("#" + G_ID_ARROW);
	}
	
	render(elementInfo) {
		
		this.elementInfo = elementInfo;
		
		// Update state
			
		if (this.gState && this.gFan && this.gAnimation && this.gArrow) {
			
			this.gState.attr({ fill: elementInfo.state.color });
			this.gFan.attr({ fill: elementInfo.state.color });
			
			// Update others
			let cmdFanValue = this.getValue(PARAM_FAN_COMMAND);
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);
			
			if (cmdFanValue && treatmentValue) {
				
				switch (parseInt(cmdFanValue)) {
				
					case CMD_FAN_VALUE_MODE_DIRECT:
					
					
						this.gArrow.attr({ fill: "#ffcc00" });
						this.gAnimation.attr({ dur: "6s", from: "0 245 410", to: "360 245 410" });
						this.gArrow.transform("r0," + this.gArrow.getBBox().cx + "," + this.gArrow.getBBox().cy);

						break;
						
					case CMD_FAN_VALUE_MODE_REVERSE:
										
						this.gArrow.attr({ fill: "#ffcc00" });
						
						this.gAnimation.attr({ dur: "6s", from: "360 245 410", to: "0 245 410" });
					    this.gArrow.transform("r180," + this.gArrow.getBBox().cx + "," + this.gArrow.getBBox().cy);
	
						break;
						
					default:
					
						this.gAnimation.attr({ dur: "indefinite" });
						this.gArrow.attr({ fill: "transparent" });
				}	
			}
			else {
				
			
				this.gAnimation.attr({ dur: "indefinite" });
				this.gArrow.attr({ fill: "transparent" });
			}
			
		}
	}
}

