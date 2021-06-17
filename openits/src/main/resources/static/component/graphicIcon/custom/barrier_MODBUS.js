import GraphicIconCustomType from "../graphicIconCustomType.js";
import GraphicIcon from "../graphicIcon.js";

const PARAM_BARRIER_RISED = "ElementTypeParam:8:2:2";
const PARAM_BARRIER_LOWERED = "ElementTypeParam:8:2:3";


const G_ID_BARRIER = "barrier";

const BARRIER_PULL_DOWN_POSITION = "0 11 29";
const BARRIER_PULL_UP_POSITION = "-45 11 29";

const ELEMENT_TYPE_STATES_NO_TREATMENT = ["ElementTypeState:8:0", "ElementTypeState:8:2"];

export default class Barrier extends GraphicIconCustomType {

	constructor(s, g, elementInfo, events) {

		super(s, g, elementInfo, events);

		this.gState = this.g.select("#" + GraphicIcon.G_ID_STATE);
		this.gBarrier = this.g.select("#" + G_ID_BARRIER);
	}

	render(elementInfo) {

		this.elementInfo = elementInfo;

		// Update state
		if (this.gState && this.gBarrier) {

			this.gState.attr({ fill: elementInfo.state.color });

			let treatmentValue = (ELEMENT_TYPE_STATES_NO_TREATMENT.indexOf(elementInfo.state.id) === -1);

			let cmdBarrierRised = this.getValue(PARAM_BARRIER_RISED);
			let cmdBarrierLowered = this.getValue(PARAM_BARRIER_LOWERED);

			if (treatmentValue) {
				if (cmdBarrierRised == "true") {
					this.gBarrier.transform("rotate(" + BARRIER_PULL_UP_POSITION + ")");

				} else if (cmdBarrierLowered == "true") {
					this.gBarrier.transform("rotate(" + BARRIER_PULL_DOWN_POSITION + ")");

				} else {
					this.gBarrier.transform("rotate(" + BARRIER_PULL_UP_POSITION + ")");
				}
			}
			else {
				this.gBarrier.transform("rotate(" + BARRIER_PULL_UP_POSITION + ")");

			}

		}

	}
}

