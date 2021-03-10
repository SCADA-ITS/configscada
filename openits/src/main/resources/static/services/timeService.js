import {util} from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";

export default class TimeService extends GenericModule {
	
	constructor(module, mod){
		
		super(module, null, null);
		
		this.module = module;
		this.mod = mod;
		
		this.serverTime = undefined; 
	}
	
	getView() {

		let views = [];
		
		views.push({ view: "template", id: "time-service", width: 95, css: "time-template" });
		
		return views;
	}
	
	getDateTime() {
		
		return this.serverTime;
	}
	
	loadData() {
		
		let self = this;
		
		let updateServiceTime = function() {
			
			let updateLabel = function(utcSeconds) {
				
				var tm = new Date(0); // The 0 there is the key, which sets the date to the epoch							
				tm.setUTCSeconds(utcSeconds);
				let h = tm.getHours();
				let m = webix.Date.toFixed(tm.getMinutes());
				let s = webix.Date.toFixed(tm.getSeconds());
				let time = +h + ":" + m + ":" + s;
				let date = util.toLocaleDateString(utcSeconds);
				labelTime.setHTML("<div class='toolbar-time'>" + time + "</div><div class='toolbar-time'>" + date + "</div>");
			}
	
			let labelTime = $$("time-service");
			
			if (!webix.isUndefined(labelTime)) {
				
				let timeElapsed = -1;
				let lastUTCSeconds = 0;
				let waitingResponse = false;
				
				setInterval(function(){
					
					if (!waitingResponse && timeElapsed === -1 || timeElapsed > 60000) {
						
						waitingResponse = true;
						webix.ajax(appConfig.restUrl + "server/time").then(function(data) {
							
							timeElapsed = 1000;
						
							if (typeof data.json() === "object") {
								
								let utcSeconds = data.json().epochSecs + (timeElapsed / 1000);
								
								self.serverTime = utcSeconds; 
								lastUTCSeconds = utcSeconds;

								updateLabel(utcSeconds);
							}
							
							waitingResponse = false;
						});
					}
					else {
						
						if (!waitingResponse) {
							
							let utcSeconds = lastUTCSeconds + (timeElapsed / 1000);
							
							self.serverTime = utcSeconds;

							updateLabel(utcSeconds);
						}
					}
					
					if (!waitingResponse) {
					
						timeElapsed += 1000;	
					}
					
				}, 999);
			}
		};
		
		updateServiceTime();
	}
}
