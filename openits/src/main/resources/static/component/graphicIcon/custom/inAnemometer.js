import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_GROUP_COMMAND = "ElementTypeParam:18:2:1";

const G_ID_ARROW = "arrow";

const ARROW_COLOR_DISABLED = "transparent";
const ARROW_COLOR_ENABLED = "#df646e";

const ARROW_CHANGE_VALUE = 12;

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:18:0","ElementTypeState:18:2"];

export default class InAnemometer extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
        this.gArrow = this.g.select("#" + G_ID_ARROW);

	}
	
	render(elementInfo) {
       
		this.elementInfo = elementInfo;
        
		// Update state
		if (this.gState) {
           
			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let cmdGroupValue = this.getValue(PARAM_GROUP_COMMAND);
			
			if (cmdGroupValue && treatmentValue) {
                
				let cmd = parseInt(cmdGroupValue);

				if(cmd <= ARROW_CHANGE_VALUE) {
					
                    this.gArrow.attr({ fill: ARROW_COLOR_ENABLED });
					this.gArrow.transform("r180," + this.gArrow.getBBox().cx + "," + this.gArrow.getBBox().cy);
					
                } else {
                    
                    this.gArrow.attr({ fill: ARROW_COLOR_ENABLED });
                    this.gArrow.transform("r0," + this.gArrow.getBBox().cx + "," + this.gArrow.getBBox().cy);
					
                }

			} else {
				
                this.gArrow.attr({ fill: ARROW_COLOR_DISABLED });
            
            }

		}
		
	}
}

