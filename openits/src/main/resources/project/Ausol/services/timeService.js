import {util} from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";

export default class TimeService extends GenericModule {
	
	constructor(module, mod){
		
		super(module, null, null);
		
		this.module = module;
		this.mod = mod;
	}
	
	getView() {

		let views = [];
		
		views.push({ view: "template", id: "time-service", width: 95, css: "time-template" });
		
		return views;
	}
	
	loadData() {
		
		this.updateServiceTime(this.module);
	}
	
	updateServiceTime() {
	
		let labelTime = $$("time-service");
		
		if (!webix.isUndefined(labelTime)) {
			
			let timeElapsed = -1;
			let lastUTCSeconds = 0;
			
			setInterval(function(){
				
				if (timeElapsed === -1 || timeElapsed > 60000) {
					webix.ajax(appConfig.restUrl + "server/time").then(function(data) {
						
						timeElapsed = 1000;
					
						if (typeof data.json() === "object") {
							
							let utcSeconds = data.json().epochSecs + (timeElapsed / 1000);
							
							window.serverTime = utcSeconds; 
							var tm = new Date(0); // The 0 there is the key, which sets the date to the epoch
							
							lastUTCSeconds = utcSeconds;
							tm.setUTCSeconds(utcSeconds);
							
							let h = tm.getHours();
							let m = webix.Date.toFixed(tm.getMinutes());
							let s = webix.Date.toFixed(tm.getSeconds());
							let time = +h + ":" + m + ":" + s;
							//let date = month + "/" + day + "/" + year;
							let date = util.toLocaleDateString(utcSeconds);
							labelTime.setHTML("<div class='toolbar-time'>" + time + "</div><div class='toolbar-time'>" + date + "</div>");
							
							//labelTime.setValue(time + " " + date);	
						}
					});
				}
				else {
					
					var tm = new Date(0); // The 0 there is the key, which sets the date to the epoch
					
					let utcSeconds = lastUTCSeconds + (timeElapsed / 1000);
					tm.setUTCSeconds(utcSeconds);
					
					window.serverTime = utcSeconds;
					
					let h = tm.getHours();
					let m = webix.Date.toFixed(tm.getMinutes());
					let s = webix.Date.toFixed(tm.getSeconds());
					let time = +h + ":" + m + ":" + s;
					//let date = month + "/" + day + "/" + year;
					let date = util.toLocaleDateString(utcSeconds);
					labelTime.setHTML("<div class='toolbar-time'>" + time + "</div><div class='toolbar-time'>" + date + "</div>");
					
					//labelTime.setValue(time + " " + date)
				}
				
				timeElapsed += 1000;
				
			}, 999);
		}
	}
}
