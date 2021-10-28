import GraphicIconCustomTypeValue from "../graphicIconCustomTypeValue.js";

const ELEMENT_TYPE_ID = "ElementType:87";

const PARAM_COMMAND_VALUE_ID = "ElementTypeParam:87:2:1";

const ELEMENT_TYPE_STATES_TANK_TREATMENT = ["ElementTypeState:87:0","ElementTypeState:87:2"];

export default class Tank extends GraphicIconCustomTypeValue {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.elementTypeStatesNoTreatment = ELEMENT_TYPE_STATES_TANK_TREATMENT;
		this.paramCommandValueId = PARAM_COMMAND_VALUE_ID;
		this.elementTypeId = ELEMENT_TYPE_ID;
	}
}