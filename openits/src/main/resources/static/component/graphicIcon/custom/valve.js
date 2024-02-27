import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const ELEMENT_TYPE_ID = "ElementType:22";

const PARAM_COMMAND_VALUE_ID = "ElementTypeParam:22:2:1";
const PARAM_VALV_FLIP = "ElementTypeParam:22:1:3";



const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:22:0","ElementTypeState:22:2"];
const G_ID_WATER_FILL = "waterfill";
const G_ID_LEVEL_ARROW = "arrow";


const CMD_VALVE_ON = "true";	


export default class Valve extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);
		
		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.elementTypeStatesNoTreatment = ELEMENT_TYPE_STATES_NO_TREATMENT;

		this.paramCommandValueId = PARAM_COMMAND_VALUE_ID;
		this.elementTypeId = ELEMENT_TYPE_ID;
		
		this.gWaterFill = this.g.select("#" + G_ID_WATER_FILL);
		this.gArrow = this.g.select("#" + G_ID_LEVEL_ARROW);
		
		if (IS_TRUE(this.getValue(PARAM_VALV_FLIP))) {
			
			this.flip();	
		}
	}
        
	
	
	
	render(elementInfo) {
	       
		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {

			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let cmdGroupValue = this.getValue(PARAM_COMMAND_VALUE_ID);
			let level = parseInt(cmdGroupValue);
			if (cmdGroupValue && treatmentValue) {

				
				if(cmdGroupValue == CMD_VALVE_ON){
					//this.gWaterFill.attr({ "fill-opacity": 1 });
					this.gArrow.attr({ "fill-opacity": 1 });
				}else {
					//this.gWaterFill.attr({ "fill-opacity": 0 });
					this.gArrow.attr({ "fill-opacity": 0 });
				}
			}

		}
		
	}
}
