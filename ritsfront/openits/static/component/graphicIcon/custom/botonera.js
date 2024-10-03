import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_MEASURE1 = "ElementTypeParam:100:2:1";
const PARAM_MEASURE2 = "ElementTypeParam:100:2:2";
const PARAM_MEASURE3 = "ElementTypeParam:100:2:3";

const G_ID_MEASURE1 = "measure1";
const G_ID_MEASURE2 = "measure2";
const G_ID_MEASURE3 = "measure3";
const G_ID_BELL = "bell";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:100:0","ElementTypeState:100:2"];
const ELEMENT_TYPE_STATES_ALARM = ["ElementTypeState:100:3"];

export default class Section extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.measure1 = this.g.select("#" + G_ID_MEASURE1);
        this.measure2 = this.g.select("#" + G_ID_MEASURE2);
        this.measure3 = this.g.select("#" + G_ID_MEASURE3);
     	this.bell = this.g.select("#" + G_ID_BELL);

	}
	
	render(elementInfo) {
       
		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {

			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);
			let alarmValue = (ELEMENT_TYPE_STATES_ALARM.indexOf(elementInfo.state.id) !== -1);

			let measure1Val = this.getValue(PARAM_MEASURE1);
			let measure2Val = this.getValue(PARAM_MEASURE2);
			let measure3Val = this.getValue(PARAM_MEASURE3);

			if (measure1Val && treatmentValue) {
					
				this.measure1.attr( { text: measure1Val} );


			}
			if (measure2Val && treatmentValue) {
				
				this.measure2.attr( { text: measure2Val} );


			}
			if (measure3Val && treatmentValue) {
				
				this.measure3.attr( { text: measure3Val} );


			}
			
			if(alarmValue){
				
				this.bell.attr({ fill: "#FF0D00" });
			}else {
				
				this.bell.attr({ fill: "#CFCFCF" });
			}
			
		}
		
	}
}

