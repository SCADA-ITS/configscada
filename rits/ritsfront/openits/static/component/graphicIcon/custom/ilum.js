import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_GROUP_COMMAND = "ElementTypeParam:2000:2:4";
const PARAM_MODE_COMMAND = "ElementTypeParam:2000:2:3";

const G_ID_NOCTURNO_REDUCIDO = "nocturno_reducido";
const G_ID_NOCTURNO = "nocturno";
const G_ID_CREPUSCULAR = "crepuscular";
const G_ID_SOLEADO = "soleado";
const G_ID_DIURNO = "diurno";
const G_ID_NUBLADO = "nublado";
const G_ID_MODE = "mode";

const ID_NOCTURNO_REDUCIDO = "4";
const ID_NOCTURNO = "3";
const ID_CREPUSCULAR = "2";
const ID_NUBLADO = "1";
const ID_SOLEADO = "0";
const ID_DIURNO = "0";

const ID_AUTOMATICO = "1";
const ID_MANUAL = "0";

const NOCTURNO_REDUCIDO_COLOR = "#252850";
const NOCTURNO_COLOR = "#000000";
const CREPUSCULAR_COLOR = "#ff8000";
const SOLEADO_COLOR = "#ffff00";
const NUBLADO_COLOR = "#9b9b9b";
const OFF_COLOR = "#ffffff";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:2000:0","ElementTypeState:2000:2"];

export default class RedTrafficLight extends GraphicIconCustomType {

	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.gNocturno = this.g.select("#" + G_ID_NOCTURNO);
        this.gCrepuscular = this.g.select("#" + G_ID_CREPUSCULAR);
		this.gSoleado = this.g.select("#" + G_ID_SOLEADO);
        this.gNublado = this.g.select("#" + G_ID_NUBLADO);
        this.gMode = this.g.select("#" + G_ID_MODE);
	}
	
	render(elementInfo) {
       
		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {

			this.gState.attr({ fill: elementInfo.state.color });			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);
			let cmdGroupValue = this.getValue(PARAM_GROUP_COMMAND);
			let cmdModeValue = this.getValue(PARAM_MODE_COMMAND);
     
			if(cmdGroupValue === ID_NOCTURNO) {
          
				this.gNocturno.attr({ fill: NOCTURNO_COLOR});
				this.gCrepuscular.attr({ fill: OFF_COLOR});
				this.gSoleado.attr({ fill: OFF_COLOR});
				this.gNublado.attr({ fill: OFF_COLOR});
        	} else if(cmdGroupValue === ID_NOCTURNO_REDUCIDO){

				this.gNocturno.attr({ fill: NOCTURNO_REDUCIDO_COLOR});
				this.gCrepuscular.attr({ fill: OFF_COLOR});
				this.gSoleado.attr({ fill: OFF_COLOR});
				this.gNublado.attr({ fill: OFF_COLOR});
        	} else if(cmdGroupValue === ID_CREPUSCULAR){

				this.gNocturno.attr({ fill: NOCTURNO_COLOR});
				this.gCrepuscular.attr({ fill: CREPUSCULAR_COLOR});
				this.gSoleado.attr({ fill: OFF_COLOR});
				this.gNublado.attr({ fill: OFF_COLOR});
        	} else if(cmdGroupValue === ID_SOLEADO){

				this.gNocturno.attr({ fill: NOCTURNO_COLOR});
				this.gCrepuscular.attr({ fill: CREPUSCULAR_COLOR});
				this.gSoleado.attr({ fill: SOLEADO_COLOR});
				this.gNublado.attr({ fill: NUBLADO_COLOR});
			} else if(cmdGroupValue === ID_DIURNO){

				this.gNocturno.attr({ fill: NOCTURNO_COLOR});
				this.gCrepuscular.attr({ fill: CREPUSCULAR_COLOR});
				this.gSoleado.attr({ fill: SOLEADO_COLOR});
				this.gNublado.attr({ fill: NUBLADO_COLOR});
        	} else if(cmdGroupValue === ID_NUBLADO){

				this.gNocturno.attr({ fill: NOCTURNO_COLOR});
				this.gCrepuscular.attr({ fill: CREPUSCULAR_COLOR});
				this.gSoleado.attr({ fill: OFF_COLOR});
				this.gNublado.attr({ fill: NUBLADO_COLOR});
			} else {
			
				this.gNocturno.attr({ fill: OFF_COLOR});
				this.gCrepuscular.attr({ fill: OFF_COLOR});
				this.gSoleado.attr({ fill: OFF_COLOR});
				this.gNublado.attr({ fill: OFF_COLOR});
			}
			
			if(cmdModeValue === ID_AUTOMATICO){				
			
				this.gMode.node.textContent="A";	
			} else if(cmdModeValue === ID_MANUAL){
			
				this.gMode.node.textContent="M";
			} else{
			
				this.gMode.node.textContent="D";	
			}
		}
	}		
}


