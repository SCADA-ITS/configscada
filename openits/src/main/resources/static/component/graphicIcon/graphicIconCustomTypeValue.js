import GraphicIcon from "./graphicIcon.js";
import GraphicIconCustomType from "./graphicIconCustomType.js";

const G_ID_TEXT_VALUE = "textValue";
const G_ID_TEXT_UNIT = "textUnit";

export default class GraphicIconCustomTypeValue extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.gTextValue = this.g.select("#" + G_ID_TEXT_VALUE);
		this.gTextUnit = this.g.select("#" + G_ID_TEXT_UNIT);
	}
	
	render(elementInfo) {

		this.elementInfo = elementInfo;

		// Update state
		if (this.gState && this.gTextValue) {

			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (this.elementTypeStatesNoTreatment.indexOf(elementInfo.state.id) === -1);

			let cmdValue = this.getValue(this.paramCommandValueId);

			if (cmdValue && treatmentValue) {
						
				this.gTextValue.attr("text", cmdValue);
			
			} else {

				this.gTextValue.attr("text", " - ");
			}

			let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", this.elementTypeId);
			let elementTypeParam = elementType.elementTypeParams.find(e => { return e.id == this.paramCommandValueId });
			this.gTextUnit.attr("text", i18n.t(elementTypeParam.labelUnit));
		}

	}
}

