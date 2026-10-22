import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";

const CHANNEL_PREFIX = "channel_";

export default class SubscriptorService extends GenericModule {
	
	static get TYPE_SESSION_LOGOUT() { return 1 }
	static get TYPE_SESSION_UNAUTHORIZED() { return 2 }
	
	constructor(module) {
		
		super(module, null, null);
		
		this.module = module;
		this.config = module.config;
		
		this.sessionData = util.user.getSessionData();
		
		this.bc = undefined;
		
		this.msg = {
			
			type: undefined,
			details: undefined
		}
	}
	
	loadData() {
		
		let self = this;
		
		const CHANNEL_NAME = CHANNEL_PREFIX + window.sessionUID + "_" + self.sessionData.sessionId;
		
		self.bc = new BroadcastChannel(CHANNEL_NAME);
		
		log.debug("Subscribe to channel: " + CHANNEL_NAME);
		
		// A handler that only logs the event to the console:
		self.bc.onmessage = onMessage;
		
		function onMessage(message) {
			
			if (message.type === "message") {
				
				let origin = message.origin;
				let data = message.data;
				
				if (origin && data) {
					
					log.trace("[" + message.origin + "] << " + data);
			
					if (data.type == SubscriptorService.TYPE_SESSION_LOGOUT ||
						data.type == SubscriptorService.TYPE_SESSION_UNAUTHORIZED) {
						
						util.user.closeSession();
						
						util.user.redirectLogin();
					}
				}
			}
		}
	}
	
	sendMessage(type, details) {
		
		let self = this;
		
		if (self.bc && self.msg && type) {
		
			let msg = webix.copy(self.msg);
			
			msg.type = type;
			msg.details = details;
			
			self.bc.postMessage(msg);
		}
	}
}
