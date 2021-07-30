import { util } from "../../../../lib/util.js";

export default class PlanLibraryOrder {
	
	constructor(config, uid, plan, planOrder) {

		if (this.constructor == PlanLibraryOrder) {
	
			throw new Error("Abstract classes can't be instantiated.");
		}
		
		this.config = config;
		this.uid = uid;
		this.plan = plan;
		this.planOrder = planOrder;
	}
	
	getView() {
		
		throw new Error("Method 'getView()' must be implemented.");
	}
}	
