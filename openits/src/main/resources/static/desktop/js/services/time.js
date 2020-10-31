function getDrawServiceTime(module, args, position) {

	let view = "view-svc-" + module.id;
	let toolbar = $$("toolbar");
	
	if (!webix.isUndefined(toolbar)) {
		
		toolbar.addView({ view: "template", id: view, width: 95, css: "time-template" });
	}
}

function assingCallbackServiceTime(module, args) {

	updateServiceTime(module);
}

function updateServiceTime(module) {
	
	let view = $$("view-svc-" + module.id);
	
	if (!webix.isUndefined(view)) {
		
		let timeElapsed = -1;
		let lastUTCSeconds = 0;
		
		setInterval(function(){
			
			if (timeElapsed === -1 || timeElapsed > 60000) {
				webix.ajax(appConfig.restUrl + "server/time").then(function(data) {
					
					timeElapsed = 0;
				
					if (typeof data.json() === "object") {
						
						let utcSeconds = data.json().epochSecs;
						var tm = new Date(0); // The 0 there is the key, which sets the date to the epoch
						
						lastUTCSeconds = utcSeconds;
						tm.setUTCSeconds(utcSeconds);
						
						let h = tm.getHours();
						let m = webix.Date.toFixed(tm.getMinutes());
						let s = webix.Date.toFixed(tm.getSeconds());
						let day = tm.getDate();
						let month = tm.getMonth() + 1;
						let year = tm.getFullYear();
						let time = +h + ":" + m + ":" + s;
						//let date = month + "/" + day + "/" + year;
						let date = app.util.toLocalTime(utcSeconds);
						view.setHTML("<div class='toolbar-time'>" + time + "</div><div class='toolbar-time'>" + date + "</div>");	
					}
				});
			}
			else {
				
				var tm = new Date(0); // The 0 there is the key, which sets the date to the epoch
				
				let utcSeconds = lastUTCSeconds + (timeElapsed / 1000);
				tm.setUTCSeconds(utcSeconds);
				
				let h = tm.getHours();
				let m = webix.Date.toFixed(tm.getMinutes());
				let s = webix.Date.toFixed(tm.getSeconds());
				let day = tm.getDate();
				let month = tm.getMonth() + 1;
				let year = tm.getFullYear();
				let time = +h + ":" + m + ":" + s;
				//let date = month + "/" + day + "/" + year;
				let date = app.util.toLocalTime(utcSeconds);
				view.setHTML("<div class='toolbar-time'>" + time + "</div><div class='toolbar-time'>" + date + "</div>");
			}
			
			timeElapsed += 1000;
			
		}, 1000);
	}
}