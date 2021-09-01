import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_SECTION_INTESITY = "ElementTypeParam:4:2:5";
const PARAM_SECTION_SPEED = "ElementTypeParam:4:2:9";
const PARAM_SECTION_LENGHT_1 = "ElementTypeParam:4:2:100";
const PARAM_SECTION_LENGHT_2 = "ElementTypeParam:4:2:101";
const PARAM_LEVEL_OF_SERVICE = "ElementTypeParam:4:2:12";

const G_ID_INTESITY = "intensity";
const G_ID_SPEED = "speed";
const G_ID_OCCUPANCY = "occupancy";
const G_ID_OCCUPANCY_LONG = "occupancy_long";
const G_ID_HEADER = "lol";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:4:0","ElementTypeState:4:2"];

const SECTION_COLOR_WHITE = "white";
const SECTION_COLOR_GREEN = "green";
const SECTION_COLOR_GOLD = "gold";
const SECTION_COLOR_RED = "red";

export default class Section extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.intensity = this.g.select("#" + G_ID_INTESITY);
        this.speed = this.g.select("#" + G_ID_SPEED);
        this.occupancy = this.g.select("#" + G_ID_OCCUPANCY);
        this.occupancy_long = this.g.select("#" + G_ID_OCCUPANCY_LONG);
        this.header = this.g.select("#" + G_ID_HEADER);
     

	}
	
	render(elementInfo) {
       
		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {

			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let intensityVal = this.getValue(PARAM_SECTION_INTESITY);
			let speedVal = this.getValue(PARAM_SECTION_SPEED);
			let lenght1Val = this.getValue(PARAM_SECTION_LENGHT_1);
			let lenght2Val = this.getValue(PARAM_SECTION_LENGHT_2);
			let levelOfService = this.getValue(PARAM_LEVEL_OF_SERVICE);

			if (intensityVal && treatmentValue) {
					
				this.intensity.attr( { text: intensityVal, x: 14.23828,y:24.143534} );


			}
			if (speedVal && treatmentValue) {
				
				this.speed.attr( { text: speedVal, x: 14.23828,y:24.143534} );


			}
			if (lenght1Val && treatmentValue) {
				
				this.occupancy.attr( { text: lenght1Val, x: 14.23828,y:24.143534} );


			}

			if (lenght2Val && treatmentValue) {
				
				this.occupancy_long.attr( { text: lenght2Val, x: 14.23828,y:24.143534 } );


			}
			if(levelOfService == 1){
				this.header.attr({ fill: SECTION_COLOR_WHITE });
			} else if(levelOfService == 2){
				this.header.attr({ fill: SECTION_COLOR_GREEN });
			} else if(levelOfService == 3){
				this.header.attr({ fill: SECTION_COLOR_GOLD });
			} else if(levelOfService == 4){
				this.header.attr({ fill: SECTION_COLOR_RED });
			}
			
		}
		
	}
}

