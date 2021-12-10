import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const ELEMENT_TYPE_ID = "ElementType:91";

const PARAM_COMMAND_VALUE_ID_G_PERM = "ElementTypeParam:91:2:1";
const PARAM_COMMAND_VALUE_ID_G_REF = "ElementTypeParam:91:2:2";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:91:0","ElementTypeState:91:2"];

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

			let cmdGroupPValue = this.getValue(PARAM_COMMAND_VALUE_ID_G_PERM);
			let cmdGroupRValue = this.getValue(PARAM_COMMAND_VALUE_ID_G_REF);
	
			if (cmdGroupPValue && treatmentValue) {
				this.gTextValueP.attr("text", cmdGroupPValue + "%");
			}
			if (cmdGroupRValue && treatmentValue) {
				this.gTextValueR.attr("text", cmdGroupRValue + "%");
			}
		}
	}
}