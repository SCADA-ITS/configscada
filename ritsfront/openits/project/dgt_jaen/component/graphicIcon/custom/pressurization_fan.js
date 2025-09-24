import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_FAN_1 = "ElementTypeParam:32:2:8";
const PARAM_FAN_2 = "ElementTypeParam:32:2:9";
const PARAM_FAN_3 = "ElementTypeParam:32:2:10";

const G_ID_ANIMATION = "animation";
const G_ID_FAN = "fan";

const CMD_FAN_VALUE_MODE_DIRECT = 1;
const CMD_FAN_VALUE_MODE_REVERSE = 2;

const SPEED_FAN = 10; // From 1 to 10. Lower value is more slow
const MAX_TIME_ROTATE = 10;
const FAN_START_POSITION = "0 246 413";
const FAN_END_POSITION = "360 246 413";
const ARROW_COLOR_ENABLED = "#323232";
const ARROW_COLOR_DISABLED = "transparent";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:32:0", "ElementTypeState:32:2"];

const CMD_FAN_ON = "true";	

export default class PressurizationFan extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);
		
		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.gFan = this.g.select("#" + G_ID_FAN); 
		this.gAnimation = this.g.select("#" + G_ID_ANIMATION);
		
	}
	
	render(elementInfo) {
	
		this.elementInfo = elementInfo;
		
		// Update state
		if (this.gState && this.gFan && this.gAnimation) {
			
			
			this.gState.attr({ fill: elementInfo.state.color });
			this.gFan.attr({ fill: elementInfo.state.color });
			
			// Update others
			let cmdFanValue1 = this.getValue(PARAM_FAN_1);
			let cmdFanValue2 = this.getValue(PARAM_FAN_2);
			let cmdRejillaValue = this.getValue(PARAM_FAN_3);
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);
			// Ajustar la velocidad de la animación en función de cmdFanValue1 o cmdFanValue2
			let speedFanValue = (cmdFanValue2 === CMD_FAN_ON ? 10 : (cmdFanValue1 === CMD_FAN_ON ? 6 : 0));
			let timeFanRotate = MAX_TIME_ROTATE + 1 - speedFanValue + "s";

			if (cmdRejillaValue != CMD_FAN_ON && treatmentValue) {
				this.mostrarImagen(true);
			}else {
				this.mostrarImagen(false);
			}
			if (cmdFanValue1 && cmdFanValue2 && treatmentValue) {
				
				if(cmdFanValue1 == CMD_FAN_ON || cmdFanValue2 == CMD_FAN_ON ){
				
					this.gAnimation.attr({ dur: timeFanRotate, from: FAN_START_POSITION, to: FAN_END_POSITION });

					
				}else{
					this.gAnimation.attr({ dur: timeFanRotate, from: FAN_START_POSITION, to: FAN_START_POSITION });
				}
				
			}
			else {
			
				this.gAnimation.attr({ dur: timeFanRotate, from: FAN_START_POSITION, to: FAN_START_POSITION });
			}
			
		}
	}
	mostrarImagen(visible) {
		const imagenSVG = this.g.select("#rejilla"); // Reemplaza con el ID real de la imagen
		if (imagenSVG) {
			imagenSVG.attr({ display: visible ? "block" : "none" });
		}
	}
}

