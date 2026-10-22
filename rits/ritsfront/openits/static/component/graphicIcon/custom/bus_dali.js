import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const ELEMENT_TYPE_ID = "ElementType:85";

const PARAM_COMMAND_VALUE_ID_G_DATA_JSON = "ElementTypeParam:85:2:2";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:85:0","ElementTypeState:85:2"];

const G_ID_TEXT_VALUE_P = "textValueP";
const G_ID_TEXT_VALUE_R = "textValueR";

export default class BusDali extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);
		
		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);

		this.elementTypeStatesNoTreatment = ELEMENT_TYPE_STATES_NO_TREATMENT;
	
		this.elementTypeId = ELEMENT_TYPE_ID;
		
		this.gTextValueP = this.g.select("#" + G_ID_TEXT_VALUE_P);
		this.gTextValueR = this.g.select("#" + G_ID_TEXT_VALUE_R);
	}

	render(elementInfo) {

		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {

			this.gState.attr({ fill: elementInfo.state.color });

			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let cmdGroupValue = this.getValue(PARAM_COMMAND_VALUE_ID_G_DATA_JSON);
			
			if (cmdGroupValue && treatmentValue) {
				var data = JSON.parse(cmdGroupValue);
				for (let groups of data.groups) {
					  if(groups.id === 0)
					  	  	this.gTextValueP.attr("text", groups.value.toString()  + "%");
						else if (groups.id === 1)	
							this.gTextValueR.attr("text", groups.value.toString() + "%");
					}
				
			}
		
		}
	}
}