import GenericModule from "../modules/genericModule.js";

export default class NotificationService extends GenericModule {
	
	constructor(module){
		
		super(module, null, null);
		
		this.module = module;
		
		this.stomp = {};
		this.notifications = {};
	}
	
	getView() {
		
		let views = [];

		return views;
	}
	
	loadData() {
		
	}
}
