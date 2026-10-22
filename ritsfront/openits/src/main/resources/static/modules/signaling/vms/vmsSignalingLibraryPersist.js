import { util } from "../../../lib/util.js";

export default class VmsSignalingLibraryPersist {

	constructor(config, libraryId) {
		
		this.config = config;
		this.libraryId = libraryId;
	}
	
	async createFolder(folderName, parentNodeId) {

		let body = {
			name: folderName,
			label: folderName,
			signaling: undefined,
			enabled: true,
			visible: true
		};
		
		let templateArgs = {
			id: this.libraryId,
			parentNodeIds: parentNodeId ? parentNodeId : "" 
		}
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.saveNode, templateArgs), body);
		
		return data.json();
	}
	
	async saveItem(itemId, itemName, signaling, parentNodeId) {
		
		let body = {
			id: itemId,
			name: itemName,
			label: itemName,
			signaling: signaling,
			enabled: true,
			visible: true
		};
		
		let templateArgs = {
			id: this.libraryId,
			parentNodeIds: parentNodeId ? parentNodeId : ""
		}
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.saveNode, templateArgs), body);
		
		return data.json();
	}
	
	async deleteItem(id) {
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).del(appConfig.restUrl + util.fillTemplate(this.config.restEndpoints.deleteNode, { id: id }));
		
		return data.json();
	}
}	
