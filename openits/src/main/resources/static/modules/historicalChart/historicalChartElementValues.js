import HistoricalChartModule from "./historicalChartModule.js";
import HistoricalChartGeneric from "./historicalChartGeneric.js";

export default class HistoricalChartElementValues {

	constructor(parent) {

		this.parent = parent;
	}

	getElementValues() {

		let args = {
			dates: {
				dateIni: this.parent.dateIni,
				dateEnd: this.parent.dateEnd
			},
			elementsIds: this.parent.elementsIds,
			url: this.parent.config.restEndpoints.elementValues
		};

		let hCGeneric = new HistoricalChartGeneric(args, HistoricalChartModule.MODE_ELEMENT_VALUES.id, this.parent);
		hCGeneric.getData();
	}
}