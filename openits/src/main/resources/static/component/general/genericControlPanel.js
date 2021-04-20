import { util } from "../../lib/util.js";

export default class GenericControlPanel {

	constructor(config) {
		
		this.config = config;
	}
	
	openModule(moduleId) {
		
		let module = window.em.getByField(EntityType.MODULE, "id", moduleId);
			
		if (module) {
	
			util.module.open(module);
		}
	}
	
	doAction(moduleId, moduleActionId, args) {
		
		let module = window.em.getByField(EntityType.MODULE, "id", moduleId);
		let argsAux;
			
		if (module) {

			let moduleAction = { id: moduleActionId };
				
			if (args) {
				
				argsAux = args;	
			}
			else {
				
				argsAux = { selectedPks: [] };
			}

			util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, argsAux);
		}
	}
}
