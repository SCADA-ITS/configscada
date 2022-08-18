import GenericModule from "../modules/genericModule.js";

export default class GenericService extends GenericModule {

	constructor(module) {
		
		super(module, null, null);
		
		if (this.constructor == GenericService) {

			throw new Error("Abstract classes can't be instantiated.");
		}

		this.module = module;
		this.config = module.config;

		this.idStompSubscriptor = undefined;
		this.mapCallbacks = new Map();
	}
	
	destroy() {
		
		let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
		if (stompSubscriptorService && self.idStompSubscriptor) {

			stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
		}
	}
	
	register(callback) {
		
		let idCallback;
		
		if (typeof callback === "function") {
			
			idCallback = Date.now();
			
			this.mapCallbacks.set(idCallback, callback);	
		}
		
		return idCallback;
	}
	
	executeCallbacks(data) {
		
		if (this.mapCallbacks.size > 0) {
			
			this.mapCallbacks.forEach(callback => {
				
				try {
					
					callback(data);	
				}
				catch {
					
				}
			});
		}
	}
	
	unregister(idCallback) {
		
		return this.mapCallbacks.delete(idCallback);
	}
}
