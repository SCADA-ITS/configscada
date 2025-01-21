import GraphicIconCustomTypeValue from "../graphicIconCustomTypeValue.js";

const ELEMENT_TYPE_ID = "ElementType:66";

const PARAM_WIND_SPEED_ID = "ElementTypeParam:66:2:2";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:66:0","ElementTypeState:66:2"];

export default class InAnemometer extends GraphicIconCustomTypeValue {

	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.elementTypeStatesNoTreatment = ELEMENT_TYPE_STATES_NO_TREATMENT;
		this.paramCommandValueId = PARAM_WIND_SPEED_ID;
		this.elementTypeId = ELEMENT_TYPE_ID;
	}
}