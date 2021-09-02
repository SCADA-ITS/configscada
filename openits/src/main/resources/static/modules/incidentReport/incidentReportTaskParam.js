import { util } from "../../lib/util.js";

export default class IncidentReportTaskParam {

	constructor(taskState, index, uid) {

		this.taskState = taskState;
		this.index = index;
		this.uid = uid;
	}

	getView() {
		
		let self = this;
		let viewParam;
		
		viewParam = {}
		
		return viewParam;
	}
}