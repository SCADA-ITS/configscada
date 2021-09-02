import { util } from "../../lib/util.js";

export default class IncidentReportTaskPlan {

	constructor(taskState, index, uid) {

		this.taskState = taskState;
		this.index = index;
		this.uid = uid;
	}

	getView() {
		
		let self = this;
		let viewPlan;
		
		viewPlan = {}
		
		return viewPlan;
	}
}