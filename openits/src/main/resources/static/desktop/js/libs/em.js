const em_types = { 
	elementType: { id: 1, endpoint: appConfig.restUrl + "element_types" },
	elementTypeState: { id: 2, endpoint: appConfig.restUrl + "element_type_states" },
	alarmType: { id: 3, endpoint: appConfig.restUrl + "alarms/alarm_types" },
	alarmLevel: { id: 4, endpoint: appConfig.restUrl + "alarms/alarm_levels" },
	alarmConfig: { id: 5, endpoint: appConfig.restUrl + "alarms/alarm_configs" },
	module: {id: 6, endpoint: appConfig.restUrl + "modules" }
}

if (window.app)
	
	app.em = {
		
		entities: {},
	
		load: function() {
	
			for(var em_type in em_types) {
				
				if (app.em.entities[em_type] == undefined) {
				
					app.em.assign(em_type);
				}   
			}
			
		},
		
		assign: function(em_type) {
			
			webix.ajax().get(em_types[em_type].endpoint).then(function(data){
			
				app.em.entities[em_types[em_type].id] = data.json();	
			});
		    
		},
		
		getAll: function(entity) {
				
			return app.em.entities[entity];
		},
		
		getByField: function(entity, field, value) {
			
			if (app.em.entities[entity] != undefined) {
				
				return app.em.entities[entity].find(e => { return e[field] === value });
			}
		}
	}
