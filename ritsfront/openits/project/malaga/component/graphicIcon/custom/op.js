import GraphicIconCustomTypeValue from "../graphicIconCustomTypeValue.js";

const ELEMENT_TYPE_ID = "ElementType:21";

const PARAM_COMMAND_VALUE_ID = "ElementTypeParam:21:2:2";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:21:0","ElementTypeState:21:2"];

export default class Op extends GraphicIconCustomTypeValue {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.elementTypeStatesNoTreatment = ELEMENT_TYPE_STATES_NO_TREATMENT;
		this.paramCommandValueId = PARAM_COMMAND_VALUE_ID;
		this.elementTypeId = ELEMENT_TYPE_ID;
	}
}