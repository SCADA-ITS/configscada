import GenericModule from "../modules/genericModule.js";

export default class NotificationService extends GenericModule {
	
	constructor(module, mod){
		
		super(module, null, null);
		
		this.module = module;
		this.mod = mod;
		
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
