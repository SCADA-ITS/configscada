import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_SEM_COMMAND = "ElementTypeParam:13:2:1";

const G_ID_ANIMATION = "animation";
const G_ID_SEM_GREEN = "green";
const G_ID_SEM_RED = "red";
const G_ID_SEM_YELLOW = "yellow";

const CMD_SEM_VALUE_MODE_OFF = 0;
const CMD_SEM_VALUE_MODE_RED = 1;	
const CMD_SEM_VALUE_MODE_YELLOW = 2;	
const CMD_SEM_VALUE_MODE_GREEN = 3;	

const SEM_COLOR_OFF = "#b3b3b3";
const SEM_COLOR_RED = "#eb5569";
const SEM_COLOR_GREEN = "#0ab69f";
const SEM_COLOR_YELOOW = "#fec165";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:13:0","ElementTypeState:13:2"];

export default class Sem extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, layerElement, ratio) {

		super(s, g, elementInfo, layerElement, ratio);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.gAnimation = this.g.select("#" + G_ID_ANIMATION);
		this.gSemGreen = this.g.select("#" + G_ID_SEM_GREEN);
        this.gSemYellow = this.g.select("#" + G_ID_SEM_YELLOW);
        this.gSemRed = this.g.select("#" + G_ID_SEM_RED);
	}
	
	render(elementInfo) {
        
		this.elementInfo = elementInfo;

		// Update state
		if (this.gState && this.gSemGreen && this.gSemYellow && this.gSemRed) {
           
			this.gState.attr({ fill: elementInfo.state.color });

			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let cmdSemValue = this.getValue(PARAM_SEM_COMMAND);
			
			if (cmdSemValue && treatmentValue) {
				
				switch (parseInt(cmdSemValue)) {

					case CMD_SEM_VALUE_MODE_OFF:
                      
                        this.gSemGreen.attr({ fill: SEM_COLOR_OFF });
                        this.gSemYellow.attr({ fill: SEM_COLOR_OFF });
                        this.gSemRed.attr({ fill: SEM_COLOR_OFF });

						break;
						
					case CMD_SEM_VALUE_MODE_RED:
                       
                        this.gSemGreen.attr({ fill: SEM_COLOR_OFF });
                        this.gSemYellow.attr({ fill: SEM_COLOR_OFF });
                        this.gSemRed.attr({ fill: SEM_COLOR_RED });

						break;

                    case CMD_SEM_VALUE_MODE_YELLOW:
                       
                        this.gSemGreen.attr({ fill: SEM_COLOR_OFF });
                        this.gSemYellow.attr({ fill: SEM_COLOR_YELOOW });
                        this.gSemRed.attr({ fill: SEM_COLOR_OFF });

                        break;

                    case CMD_SEM_VALUE_MODE_GREEN:
                       
                        this.gSemGreen.attr({ fill: SEM_COLOR_GREEN });
                        this.gSemYellow.attr({ fill: SEM_COLOR_OFF });
                        this.gSemRed.attr({ fill: SEM_COLOR_OFF });

                        break;
						
					default:

                        this.gSemGreen.attr({ fill: SEM_COLOR_OFF });
                        this.gSemYellow.attr({ fill: SEM_COLOR_OFF });
                        this.gSemRed.attr({ fill: SEM_COLOR_OFF });
				}
			}
			else {

                this.gSemGreen.attr({ fill: SEM_COLOR_OFF });
                this.gSemYellow.attr({ fill: SEM_COLOR_OFF });
                this.gSemRed.attr({ fill: SEM_COLOR_OFF });
			}
			
		}

	}
}

