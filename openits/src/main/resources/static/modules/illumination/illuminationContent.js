import { util } from "../../lib/util.js";

export default class IlluminationContent {
	
	constructor(parent) {

		this.parent = parent;
	}

	loadComponent(args, drawContent) {

		let self = this;

		let module = findModuleByIdAction(self.parent.config.containerAction.tunnel);
		let moduleAction = { id: self.parent.config.containerAction.tunnel };
		if (module.moduleActions) {

			moduleAction = module.moduleActions.find(action => action.id == moduleAction.id);
		}
		util.module.open(module, moduleAction, VIEW_TYPE_CUSTOM, drawContent, args);
	}
}
