import GraphicIconCustomTypeValue from "../graphicIconCustomTypeValue.js";

const ELEMENT_TYPE_ID = "ElementType:16";

const PARAM_COMMAND_VALUE_ID = "ElementTypeParam:16:2:1002";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:16:0","ElementTypeState:16:2"];

export default class Pa extends GraphicIconCustomTypeValue {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.elementTypeStatesNoTreatment = ELEMENT_TYPE_STATES_NO_TREATMENT;
		this.paramCommandValueId = PARAM_COMMAND_VALUE_ID;
		this.elementTypeId = ELEMENT_TYPE_ID;
	}
}