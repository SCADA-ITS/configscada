import GraphicIconCustomTypeValue from "../graphicIconCustomTypeValue.js";
import GraphicIcon from "../graphicIcon.js";

const ELEMENT_TYPE_ID = "ElementType:18";

const PARAM_COMMAND_VALUE_ID = "ElementTypeParam:18:2:1";

const G_ID_ARROW = "arrow";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:18:0","ElementTypeState:18:2"];

export default class InAnemometer extends GraphicIconCustomTypeValue {
										  
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.elementTypeStatesNoTreatment = ELEMENT_TYPE_STATES_NO_TREATMENT;
		this.paramCommandValueId = PARAM_COMMAND_VALUE_ID;
		this.elementTypeId = ELEMENT_TYPE_ID;
		
        this.gArrow = this.g.select("#" + G_ID_ARROW);
	}
	
	render(elementInfo) {

		super.render(elementInfo);
		
		let elementType;
		let elementTypeParam;
		let treatmentValue = (this.elementTypeStatesNoTreatment.indexOf(elementInfo.state.id) === -1);
		
		if(this.elementTypeId && this.paramCommandValueId){

			elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", this.elementTypeId);
			elementTypeParam = elementType.elementTypeParams.find(e => { return e.id == this.paramCommandValueId });
		}
		
		if (this.gTextValue && this.paramCommandValueId) {
			let cmdValue = this.getValue(this.paramCommandValueId);
				
			if (cmdValue && treatmentValue) {
			
				if (cmdValue && elementTypeParam && elementTypeParam.dataType){
			
					if(cmdValue < 0){
						this.gArrow.transform("r180," + this.gArrow.getBBox().cx + "," + this.gArrow.getBBox().cy);
					}else{
						this.gArrow.transform("r0," + this.gArrow.getBBox().cx + "," + this.gArrow.getBBox().cy);
					}
				}
			}
		}
			
	}
}

