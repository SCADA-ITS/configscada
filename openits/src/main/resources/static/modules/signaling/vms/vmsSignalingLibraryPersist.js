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
			id: this.libraryId
		}
		
		if (parentNodeId) {
			
			templateArgs.parentNodeIds = parentNodeId; 
		}

		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + util.fillTemplate(self.config.actionEndpoints.saveNode, parentNodeId), body);
		
		return data;
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
			id: this.libraryId
		}
		
		if (parentNodeId) {
			
			templateArgs.parentNodeIds = parentNodeId; 
		}

		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + util.fillTemplate(self.config.actionEndpoints.saveNode, templateArgs), body);
		
		return data;
	}
	
	async deleteItem(id) {
		
		let data = await webix.ajax().delete(appConfig.restUrl + util.fillTemplate(self.config.actionEndpoints.deleteNode, { id: id }));
		
		return data;
	}
}	
