import GraphicIconCustomTypeValue from "../graphicIconCustomTypeValue.js";
import GraphicIcon from "../graphicIcon.js";

const ELEMENT_TYPE_ID = "ElementType:18";

const PARAM_COMMAND_VALUE_ID = "ElementTypeParam:18:2:1";

const G_ID_ARROW = "arrow";
const G_ID_TEXT_VALUE = "textValue";
const G_ID_TEXT_UNIT = "textUnit";

const ARROW_COLOR_DISABLED = "transparent";
const ARROW_COLOR_ENABLED = "#df646e";

const ARROW_CHANGE_VALUE = 12;

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:18:0","ElementTypeState:18:2"];

export default class InAnemometer extends GraphicIconCustomTypeValue {
										  
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.elementTypeStatesNoTreatment = ELEMENT_TYPE_STATES_NO_TREATMENT;
		this.paramCommandValueId = PARAM_COMMAND_VALUE_ID;
		this.elementTypeId = ELEMENT_TYPE_ID;
		/*this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
        this.gArrow = this.g.select("#" + G_ID_ARROW);
		this.gTextValue = this.g.select("#" + G_ID_TEXT_VALUE);
		this.gTextUnit = this.g.select("#" + G_ID_TEXT_UNIT);*/
	}
	
	/*render(elementInfo) {
       
		this.elementInfo = elementInfo;
        
		// Update state
		if (this.gState) {
           
			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let cmdGroupValue = this.getValue(PARAM_GROUP_COMMAND);
			
			if (cmdGroupValue && treatmentValue) {

				this.gTextValue.attr("text", cmdGroupValue);
                
				let cmd = parseInt(cmdGroupValue);

				if(cmd <= ARROW_CHANGE_VALUE) {
					
                    this.gArrow.attr({ fill: ARROW_COLOR_ENABLED });
					this.gArrow.transform("r180," + this.gArrow.getBBox().cx + "," + this.gArrow.getBBox().cy);
					
                } else {
                    
                    this.gArrow.attr({ fill: ARROW_COLOR_ENABLED });
                    this.gArrow.transform("r0," + this.gArrow.getBBox().cx + "," + this.gArrow.getBBox().cy);
					
                }

			} else {
				
                this.gArrow.attr({ fill: ARROW_COLOR_DISABLED });
            
            }

			let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", ELEMENT_TYPE_ID);
			let elementTypeParam = elementType.elementTypeParams.find(e => { return e.id == PARAM_GROUP_COMMAND });
			if(this.gTextUnit){
				this.gTextUnit.attr("text", i18n.t(elementTypeParam.labelUnit));
			}
			
		}
		
	}*/
}

