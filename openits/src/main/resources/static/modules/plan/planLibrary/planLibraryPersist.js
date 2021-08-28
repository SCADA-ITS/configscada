import { util } from "../../../lib/util.js";

export default class PlanLibraryPersist {

	constructor(config) {
		
		this.config = config;
	}
	
	async createFolder(folderName, parentNodeId) {

		let body = {
			planNode: {
				
				alias: folderName,
				description: folderName,
				parentId: parentNodeId	
			}
			
		};
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + this.config.restEndpoints.createNode, body);
		
		return data.json();
	}
	
	async createItem(itemName, parentNodeId, planId) {
		
		let body = {
			planNode: {
				
				alias: itemName,
				description: itemName,
				planId: planId,
				parentId: parentNodeId	
			}
			
		};
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + this.config.restEndpoints.createNode, body);
		
		return data.json();
	}
	
	async deleteItem(id) {
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).del(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.deleteNode, { id: id }));
		
		return data.json();
	}
	
	async createPlan(plan) {
		
		let body = {
			plan: webix.copy(plan) 
		}
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + this.config.restEndpoints.createPlan, body);
		
		return data.json();
	}
	
	async updatePlan(plan) {
		
		let body = {
			plan: webix.copy(plan) 
		}
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).put(appConfig.restUrl + this.config.restEndpoints.updatePlan, body);
		
		return data.json();
	}
	
	async deletePlan(id) {
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).del(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.deletePlan, { id: id }));
		
		return data.json();
	}

	async createPlanOrder(planOrder) {
		
		let body = {
			planOrder: webix.copy(planOrder)
		};
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + this.config.restEndpoints.createPlanOrder, body);
		
		return data.json();
	}
	
	async updatePlanOrder(planOrder) {
		
		let body = {
			planOrder: webix.copy(planOrder)
		};
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).put(appConfig.restUrl + this.config.restEndpoints.updatePlanOrder, body);
		
		return data.json();
	}
	
	async deletePlanOrder(id) {
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).del(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.deletePlanOrder, { id: id }));
		
		return data.json();
	}
}	
