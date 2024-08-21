import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_GROUP_COMMAND = "ElementTypeParam:13:2:1";

const G_ID_GROUP_GREEN = "green";
const G_ID_GROUP_RED = "red";
const G_ID_GROUP_YELLOW = "yellow";
const G_ID_GROUP_YELLOW_FLASH = "yellowFlash";

const CMD_GROUP_OFF = 0;
const CMD_GROUP_RED = 4;	
const CMD_GROUP_YELLOW = 2;	
const CMD_GROUP_GREEN = 1;	
const CMD_GROUP_FLASING_YELLOW = 3;	

const GROUP_COLOR_OFF = "#b3b3b3";
const GROUP_COLOR_RED = "#eb5569";
const GROUP_COLOR_GREEN = "#0ab69f";
const GROUP_COLOR_YELLOW = "#fec165";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:13:0","ElementTypeState:13:2"];

export default class TrafficLight extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);

		this.groups = [
			{ cmd: CMD_GROUP_OFF },
			{ cmd: CMD_GROUP_RED, obj: this.g.select("#" + G_ID_GROUP_RED), color: GROUP_COLOR_RED },
			{ cmd: CMD_GROUP_GREEN, obj: this.g.select("#" + G_ID_GROUP_GREEN), color: GROUP_COLOR_GREEN },
			{ cmd: CMD_GROUP_YELLOW, obj: this.g.select("#" + G_ID_GROUP_YELLOW_FLASH), color: GROUP_COLOR_YELLOW },
			{ cmd: CMD_GROUP_FLASING_YELLOW, obj: this.g.select("#" + G_ID_GROUP_YELLOW_FLASH), color: GROUP_COLOR_YELLOW }
		]
	}
	
	render(elementInfo) {

		let self = this;        
		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {
           
			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let cmdGroupValue = this.getValue(PARAM_GROUP_COMMAND);
			
			if (cmdGroupValue && treatmentValue) {
				
				let group = undefined;
				let cmd = parseInt(cmdGroupValue);
					
				if (group = this.groups.find(e => e.cmd === cmd)) {
					
					switchOff();
						
					
					// Switch on selected traffic light group
					if (group.obj) {
						if(cmd === CMD_GROUP_FLASING_YELLOW){
							group.obj.attr({ from: GROUP_COLOR_OFF, to: GROUP_COLOR_YELLOW });	
							
						}else if (cmd === CMD_GROUP_YELLOW){
							group.obj.attr({ from: GROUP_COLOR_YELLOW, to: GROUP_COLOR_YELLOW });	
						}
						else{
							group.obj.attr( { fill: group.color });	
							
						}
						
						
					}
				}
				else {
					
					console.error("graphicicon/custom/TrafficLight.js => " + elementInfo.id + " PARAM_GROUP_COMMAND = " + cmdGroupValue + " is out of range");
				}
			}
			else {
				
				switchOff();
			}
		}
		
		function switchOff() {
			
			self.groups.forEach(gr => { 
				if (gr.obj) { 
					gr.obj.attr( { fill: GROUP_COLOR_OFF } );
					gr.obj.attr({ from: GROUP_COLOR_OFF, to: GROUP_COLOR_OFF });	
					
				}
			});
			
		}
	}
}

