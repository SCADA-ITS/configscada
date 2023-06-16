import HistoricalChartModule from "./historicalChartModule.js";
import HistoricalChartGeneric from "./historicalChartGeneric.js";

export default class HistoricalChartAlarms {

	constructor(parent) {

		this.parent = parent;
	}

	getAlarms() {

		let args = {
			dates: {
				dateIni: this.parent.dateIni,
				dateEnd: this.parent.dateEnd
			},
			elementsIds: this.parent.elementsIds,
			url: this.parent.config.restEndpoints.alarms,
			isEmbed: this.parent.isEmbed
		};

		let hCGeneric = new HistoricalChartGeneric(args, HistoricalChartModule.MODE_ALARMS.id, this.parent);
		hCGeneric.getData();
	}
}