import { util } from "../../../lib/util.js";

export default class PlanLibraryPersist {

	constructor(config) {
		
		this.config = config;
	}
	
	async createFolder(folderName, parentNodeId) {

		let body = {
			alias: folderName,
			description: folderName,
			parentId: parentNodeId
		};
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + this.config.restEndpoints.createNode, body);
		
		return data.json();
	}
	
	async createItem(itemName, parentNodeId) {
		
		let body = {
			alias: itemName,
			description: itemName,
			planId: -1,
			parentId: parentNodeId
		};
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + this.config.restEndpoints.createNode, body);
		
		return data.json();
	}
	
	async deleteItem(id) {
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).del(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.deleteNode, { id: id }));
		
		return data.json();
	}
	
	async createPlanOrder(planId, planOrderTypeId) {
		
		let body = {
			planId: planId,
			planOrderTypeId: planOrderTypeId
		};
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + this.config.restEndpoints.createPlanOrder, body);
		
		return data.json();
	}
	
	async updatePlanOrder(planOrder) {
		
		let body = {
			planOrderTypeId: planOrder.planOrderType.id
		};
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).put(appConfig.restUrl + this.config.restEndpoints.updatePlanOrder, body);
		
		return data.json();
	}
	
	async deletePlanOrder(id) {
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).del(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.deletePlanOrder, { id: id }));
		
		return data.json();
	}
}	
