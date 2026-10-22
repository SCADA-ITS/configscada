import {util} from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";

const TRACE_TIME_EACH_MIN = 5;

export default class TimeService extends GenericModule {
	
	constructor(module){
		
		super(module, null, null);
		
		this.module = module;
		
		this.serverTime = undefined;
		this.serverTimeMs = undefined;
		this.timer = undefined;
	}
	
	destroy() {
	
		if (this.timer) {
			
			clearInterval(this.timer);	
		}
	}
	
	getView() {

		let views = [];
		let cssStyleInvertTimer = (window.lightMode) ? "time-template light-templates" : "time-template";
		
		views.push({ view: "template", id: "time-service", width: 95, css: cssStyleInvertTimer });
		
		return views;
	}
	
	getEpoch() {
		
		return this.serverTimeMs;
	}
	
	loadData() {
		
		let self = this;
		let timeElapsedMs = -1;
		let waitingResponse = false;
		let serverTimeMs = 0;
		let initTimeMs = Date.now();
		let clientTimeMs = Date.now();
		let diffCliSrvMs = 0;
		let requestTraceCount = 0;
		
		update();
		
		self.timer = setInterval(function() {

			update();
				
		}, 999);
		
		function update() {
			
			if (!waitingResponse) {
				
				if (timeElapsedMs === -1 || timeElapsedMs > 60000) {
					
					waitingResponse = true;
					
					let responseTimeout = Date.now();
											
					webix.ajax(appConfig.restUrl + "server/time")
						.then(function(data) {
						
							responseTimeout = Date.now() - responseTimeout; 
							
							timeElapsedMs = 0;
						
							if (typeof data.json() === "object") {
								
								initTimeMs = Date.now();
								clientTimeMs = Date.now();
								serverTimeMs = data.json().epochMillis + responseTimeout;
								
								self.serverTimeMs = serverTimeMs;
							
								diffCliSrvMs = clientTimeMs - serverTimeMs;
								
								updateLabel(Math.round(self.serverTimeMs / 1000));
							}
							
							if (!(++requestTraceCount % TRACE_TIME_EACH_MIN)) {
								
								requestTraceCount = 0;
								
								//console.log("[" + (new Date()).toISOString() + "] " + "[TimeService] Server time = " + new Date(self.serverTimeMs).toISOString());
								//console.log("[" + (new Date()).toISOString() + "] " + "[TimeService] Client time = " + new Date(clientTimeMs).toISOString());
								//console.log("[" + (new Date()).toISOString() + "] " + "[TimeService] Diff time (ms) = " + diffCliSrvMs);
								log.debug("Server time = " + new Date(self.serverTimeMs).toISOString());
								log.debug("Client time = " + new Date(clientTimeMs).toISOString());
								log.debug("Diff time (ms) = " + diffCliSrvMs);
							}
							
							waitingResponse = false;
						})
						.fail(function(err) {
							
							//console.error("Server not response => " + appConfig.restUrl + "server/time")
							log.error("Server not response => " + appConfig.restUrl + "server/time");
							
							initTimeMs = Date.now();
							timeElapsedMs = 0;
							waitingResponse = false;
							
							requestTraceCount = TRACE_TIME_EACH_MIN - 1; // Force to trace next retry...
						});
				}
				else {
					
					clientTimeMs = Date.now();
					self.serverTimeMs = clientTimeMs - diffCliSrvMs;
					timeElapsedMs = clientTimeMs - initTimeMs;

					updateLabel(Math.round(self.serverTimeMs / 1000));
				}
			}
		}
		
		function updateLabel(utcSeconds) {

			let labelTime = $$("time-service");

			if (labelTime) {

				var tm = new Date(0); // The 0 there is the key, which sets the date to the epoch							
				tm.setUTCSeconds(utcSeconds);
				let h = tm.getHours();
				let m = webix.Date.toFixed(tm.getMinutes());
				let s = webix.Date.toFixed(tm.getSeconds());
				let time = +h + ":" + m + ":" + s;
				let date = util.toLocaleDateString(utcSeconds);
				labelTime.setHTML("<div class='toolbar-time'>" + time + "</div><div class='toolbar-time'>" + date + "</div>");
			}
		}
	}
}
