import GraphicIconCustomTypeValue from "../graphicIconCustomTypeValue.js";

const ELEMENT_TYPE_ID = "ElementType:14";

const PARAM_COMMAND_VALUE_ID = "ElementTypeParam:14:2:2";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:14:0","ElementTypeState:14:2"];

export default class Lum extends GraphicIconCustomTypeValue {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.elementTypeStatesNoTreatment = ELEMENT_TYPE_STATES_NO_TREATMENT;
		this.paramCommandValueId = PARAM_COMMAND_VALUE_ID;
		this.elementTypeId = ELEMENT_TYPE_ID;
	}
}

