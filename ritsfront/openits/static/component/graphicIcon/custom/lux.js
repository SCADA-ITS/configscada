import GraphicIconCustomTypeValue from "../graphicIconCustomTypeValue.js";

const ELEMENT_TYPE_ID = "ElementType:12";

const PARAM_COMMAND_VALUE_ID = "ElementTypeParam:12:2:1";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:12:0","ElementTypeState:12:2"];

export default class Lux extends GraphicIconCustomTypeValue {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.elementTypeStatesNoTreatment = ELEMENT_TYPE_STATES_NO_TREATMENT;
		this.paramCommandValueId = PARAM_COMMAND_VALUE_ID;
		this.elementTypeId = ELEMENT_TYPE_ID;
	}
}

