import { util } from "../../lib/util.js";

export default class IncidentWizardDrawStep {

	constructor(parent, step) {
		
		if (this.constructor == IncidentWizardDrawStep) {

			throw new Error("Abstract classes can't be instantiated.");
		}

		this.parent = parent;
		this.step = step;
	}
	
	getView() {
		
		throw new Error("Method 'getView()' must be implemented.");
	}
	
	loadData() {
		
	}
	
	initialize() {
		
		this.step.initializated = false;
	}
}	
