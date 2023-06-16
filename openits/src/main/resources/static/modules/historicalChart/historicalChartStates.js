import HistoricalChartModule from "./historicalChartModule.js";
import HistoricalChartGeneric from "./historicalChartGeneric.js";

export default class HistoricalChartStates {

	constructor(parent) {

		this.parent = parent;
	}

	getStates() {

		let args = {
			dates: {
				dateIni: this.parent.dateIni,
				dateEnd: this.parent.dateEnd
			},
			elementsIds: this.parent.elementsIds,
			url: this.parent.config.restEndpoints.states,
			isEmbed: this.parent.isEmbed
		};

		let hCGeneric = new HistoricalChartGeneric(args, HistoricalChartModule.MODE_STATES.id, this.parent);
		hCGeneric.getData();
	}
}