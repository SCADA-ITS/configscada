import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const ELEMENT_TYPE_ID = "ElementType:87";

const PARAM_COMMAND_VALUE_ID = "ElementTypeParam:87:2:3";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:87:0", "ElementTypeState:87:2"];
const G_ID_LEVEL_25 = "level25";
const G_ID_LEVEL_50 = "level50";
const G_ID_LEVEL_75 = "level75";
const G_ID_LEVEL_100 = "level100";

const G_ID_TEXT_VALUE = "textValue";

export default class Tank extends GraphicIconCustomType {

	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.elementTypeStatesNoTreatment = ELEMENT_TYPE_STATES_NO_TREATMENT;

		this.paramCommandValueId = PARAM_COMMAND_VALUE_ID;
		this.elementTypeId = ELEMENT_TYPE_ID;

		this.gLevel25 = this.g.select("#" + G_ID_LEVEL_25);
		this.gLevel50 = this.g.select("#" + G_ID_LEVEL_50);
		this.gLevel75 = this.g.select("#" + G_ID_LEVEL_75);
		this.gLevel100 = this.g.select("#" + G_ID_LEVEL_100);
		this.gTextValue = this.g.select("#" + G_ID_TEXT_VALUE);
		
	}

	render(elementInfo) {

		this.elementInfo = elementInfo;

		// Update state
		if (this.gState) {

			this.gState.attr({ fill: elementInfo.state.color });

			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let cmdGroupValue = this.getValue(PARAM_COMMAND_VALUE_ID);
			let level = parseInt(cmdGroupValue);
			if (cmdGroupValue && treatmentValue) {
				switch (true) {
					case level == 0:
						this.gLevel25.attr({ "fill-opacity": 0 });
						this.gLevel50.attr({ "fill-opacity": 0 });
						this.gLevel75.attr({ "fill-opacity": 0 });
						this.gLevel100.attr({ "fill-opacity": 0 });
						break;

					case level > 0 && level <= 25:
						this.gLevel25.attr({ "fill-opacity": 1 });
						this.gLevel50.attr({ "fill-opacity": 0 });
						this.gLevel75.attr({ "fill-opacity": 0 });
						this.gLevel100.attr({ "fill-opacity": 0 });
						break;

					case level > 25 && level <= 50:
						this.gLevel25.attr({ "fill-opacity": 1 });
						this.gLevel50.attr({ "fill-opacity": 1 });
						this.gLevel75.attr({ "fill-opacity": 0 });
						this.gLevel100.attr({ "fill-opacity": 0 });
						break;

					case level > 50 && level <= 75:
						this.gLevel25.attr({ "fill-opacity": 1 });
						this.gLevel50.attr({ "fill-opacity": 1 });
						this.gLevel75.attr({ "fill-opacity": 1 });
						this.gLevel100.attr({ "fill-opacity": 0 });
						break;

					case level > 75 && level <= 100:
						this.gLevel25.attr({ "fill-opacity": 1 });
						this.gLevel50.attr({ "fill-opacity": 1 });
						this.gLevel75.attr({ "fill-opacity": 1 });
						this.gLevel100.attr({ "fill-opacity": 1 });
						break;

					default:
						this.gLevel25.attr({ "fill-opacity": 0 });
						this.gLevel50.attr({ "fill-opacity": 0 });
						this.gLevel75.attr({ "fill-opacity": 0 });
						this.gLevel100.attr({ "fill-opacity": 0 });
						break;
				}

				this.gTextValue.attr("text", cmdGroupValue + "%");
			}
		}
	}
}