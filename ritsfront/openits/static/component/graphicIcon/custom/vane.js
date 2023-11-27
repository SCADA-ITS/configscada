import GraphicIconCustomTypeValue from "../graphicIconCustomTypeValue.js";
import GraphicIcon from "../graphicIcon.js";

const ELEMENT_TYPE_ID = "ElementType:66";

const PARAM_WIND_SPEED_ID = "ElementTypeParam:66:2:2";
const PARAM_WIND_DIRECTION_ID = "ElementTypeParam:66:2:3";

const G_ID_ARROW = "arrow";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:66:0","ElementTypeState:66:2"];

export default class InAnemometer extends GraphicIconCustomTypeValue {
										  
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.elementTypeStatesNoTreatment = ELEMENT_TYPE_STATES_NO_TREATMENT;
		this.paramCommandValueId = PARAM_WIND_SPEED_ID;
		this.paramWindDirectionValueId = PARAM_WIND_DIRECTION_ID;
		this.elementTypeId = ELEMENT_TYPE_ID;
		
        this.gArrow = this.g.select("#" + G_ID_ARROW);
	}
	
	render(elementInfo) {

		super.render(elementInfo);
		
		let elementType;
		let elementTypeParam;
		let treatmentValue = (this.elementTypeStatesNoTreatment.indexOf(elementInfo.state.id) === -1);
		
		if(this.elementTypeId && this.paramWindDirectionValueId){

			elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", this.elementTypeId);
			elementTypeParam = elementType.elementTypeParams.find(e => { return e.id == this.paramWindDirectionValueId });
		}
		
		if (this.gTextValue && this.paramWindDirectionValueId) {
			let cmdValue = this.getValue(this.paramWindDirectionValueId);
				
			if (cmdValue && treatmentValue) {
			
				if (cmdValue && elementTypeParam && elementTypeParam.dataType){
			
					if(cmdValue == "false"){
						this.gArrow.transform("r180," + this.gArrow.getBBox().cx + "," + this.gArrow.getBBox().cy);
					}else{
						this.gArrow.transform("r0," + this.gArrow.getBBox().cx + "," + this.gArrow.getBBox().cy);
					}
				}
			}
		}
			
	}
}

