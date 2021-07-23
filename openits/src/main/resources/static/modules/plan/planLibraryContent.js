import { util } from "../../lib/util.js";

export default class PlanLibraryContent {

	constructor(config, uid, plan) {
		
		this.config = config;
		this.plan = plan;
		this.uid = uid;
	}
	
	getView() {
		
		let self = this;
		
		let view = {
			id: "plan-lib-content-" + self.uid,
			css: { "background-color": "white" },
			rows: [
				{
					template: `<div>${self.plan.alias}</div>`
				}	
			]
			
		};
			
		return view;
	}
}	
