export default class IncidentReportTaskDetail {

	constructor(config, incidentReport, taskState, index, uid) {

		if (this.constructor == IncidentReportTaskDetail) {

			throw new Error("Abstract classes can't be instantiated.");
		}
		
		this.config = config;
		this.incidentReport = incidentReport;
		this.taskState = taskState;
		this.index = index;
		this.uid = uid;
	}

	getView() {
		
		throw new Error("Method 'getView()' must be implemented.");
	}
}