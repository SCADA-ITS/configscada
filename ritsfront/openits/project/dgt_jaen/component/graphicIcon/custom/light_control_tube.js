import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_TUBE_CONFIG = "ElementTypeParam:2000:1:2";
const PARAM_TUBE_MODE = "ElementTypeParam:2000:2:3";
const PARAM_TUBE_REG = "ElementTypeParam:2000:2:4";

const G_ID_MODE = "textValueMode";
const G_ID_REG= "textValueReg";

const PARAM_TUBE_MODE_MANUAL = '0';
const PARAM_TUBE_MODE_AUTO = '1';


const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:2000:0","ElementTypeState:2000:2"];

export default class LightControl extends GraphicIconCustomType {
	
	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.gMode = this.g.select("#" + G_ID_MODE);
		this.gReg = this.g.select("#" + G_ID_REG);
		
	}
	
	render(elementInfo) {

		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {

			this.gState.attr({ fill: elementInfo.state.color });
			
			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let cmdModeValue = this.getValue(PARAM_TUBE_MODE);
			let cmdRegValue = this.getValue(PARAM_TUBE_REG);
			let cmdConfig = this.getValue(PARAM_TUBE_CONFIG);
			
			if (cmdModeValue && treatmentValue) {
				
				switch (cmdModeValue) {

					case PARAM_TUBE_MODE_MANUAL:		
						this.gMode.attr("text", "Manual");
						break;
						
					case PARAM_TUBE_MODE_AUTO:
						this.gMode.attr("text", "Auto");
						break;
						
				}
			}
			
			if (cmdRegValue && treatmentValue) {
				
				var data = JSON.parse(cmdConfig);
				
				for (let regime of data.regimes) {
					  if(regime.id == parseInt(cmdRegValue))
						  this.gReg.attr("text", i18n.t(regime.labelAlias));
					}
				
				
			} else {
				this.gReg.attr("text", "Desconocido");
			}


		}

	}
}

