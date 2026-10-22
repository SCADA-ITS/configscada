const TASKBAR_ID = "taskbar";

export default class Task {
	
	static get WIN() { return 1 }
	static get ALERT() { return 2 }
	
	constructor(type, id, module, moduleAction) {
	
		this.type = type;
		this.id = id;
		this.module = module;
		this.moduleAction = moduleAction;	
	}
}
