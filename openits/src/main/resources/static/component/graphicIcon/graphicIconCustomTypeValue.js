import GraphicIcon from "./graphicIcon.js";
import GraphicIconCustomType from "./graphicIconCustomType.js";

const G_ID_TEXT_VALUE = "textValue";
const G_ID_TEXT_UNIT = "textUnit";

const PARAM_DATA_TYPE_DECIMAL = "DataType:3";

const DEFAULT_NO_VALUE_DATA = "-";
const DEFAULT_PRECION = 0;

export default class GraphicIconCustomTypeValue extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.gTextValue = this.g.select("#" + G_ID_TEXT_VALUE);
		this.gTextUnit = this.g.select("#" + G_ID_TEXT_UNIT);
	}
	
	render(elementInfo) {

		this.elementInfo = elementInfo;
		let treatmentValue = (this.elementTypeStatesNoTreatment.indexOf(elementInfo.state.id) === -1);
		let elementType;
		let elementTypeParam;
		let precision = DEFAULT_PRECION;

		if(this.elementTypeId && this.paramCommandValueId){

			elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", this.elementTypeId);
			elementTypeParam = elementType.elementTypeParams.find(e => { return e.id == this.paramCommandValueId });
		}
		
		if (this.gState) {

			this.gState.attr({ fill: elementInfo.state.color });
		}

		if (this.gTextValue && this.paramCommandValueId) {

			let cmdValue = this.getValue(this.paramCommandValueId);

			if (cmdValue && treatmentValue) {

				if (cmdValue && elementTypeParam && elementTypeParam.dataType && elementTypeParam.dataType.id == PARAM_DATA_TYPE_DECIMAL){

					if(elementTypeParam && elementTypeParam.precision){
	
						precision = elementTypeParam.precision;
					}
	
					try {
						
						let regExp = new RegExp('^-?\\d+(?:\.\\d{0,' + (precision || -1) + '})?');
						cmdValue = cmdValue.match(regExp)[0];

					} catch (error) {
						
						console.warn("El valor "+cmdValue+" no puede ser tratado como tipo numerico y aplicarle una precisión de "+precision+" decimales.")
					}
				}
						
				this.gTextValue.attr("text", cmdValue);
			
			} else {

				this.gTextValue.attr("text", DEFAULT_NO_VALUE_DATA);
			}

		}

		if (this.gTextUnit && elementTypeParam) {
		
			this.gTextUnit.attr("text", i18n.t(elementTypeParam.labelUnit));
		
		} else {

			this.gTextUnit.attr("text", "");
		}
	}
}

