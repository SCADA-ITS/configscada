if (window.app)
	
	window.app.em = {
		
		em_types: { 
			elementType: { id: 1, endpoint: appConfig.restUrl + "element_types" },
			elementTypeState: { id: 2, endpoint: appConfig.restUrl + "element_type_states" },
			alarmType: { id: 3, endpoint: appConfig.restUrl + "alarms/alarm_types" },
			alarmLevel: { id: 4, endpoint: appConfig.restUrl + "alarms/alarm_levels" },
			alarmConfig: { id: 5, endpoint: appConfig.restUrl + "alarms/alarm_configs" },
			module: {id: 6, endpoint: appConfig.restUrl + "modules" }
		},
		
		entities: {},
	
		load: function() {
	
			for(var em_type in window.app.em.em_types) {
				
				if (window.app.em.entities[em_type] == undefined) {
				
					window.app.em.assign(em_type);
				}   
			}
			
		},
		
		assign: function(em_type) {
			
			let response = webix.ajax().sync().get(window.app.em.em_types[em_type].endpoint);
				
			if (response != undefined) {
				
				window.app.em.entities[window.app.em.em_types[em_type].id] = JSON.parse(response.responseText);
				
				if (Object.keys(window.app.em.entities).length === Object.keys(window.app.em.em_types).length) {
					
					window.app.em.loaded = true;
				}
			}
			
//			webix.ajax().get(em_types[em_type].endpoint).then(function(data){
//			
//				app.em.entities[em_types[em_type].id] = data.json();
//				
//				if (Object.keys(app.em.entities).length === Object.keys(em_types).length) {
//					
//					app.em.loaded = true;
//				}	
//			});
		    
		},
		
		getAll: function(entity) {
				
			return window.app.em.entities[entity];
		},
		
		getByField: function(entity, field, value) {
			
			if (window.app.em.entities[entity] != undefined) {
				
				return window.app.em.entities[entity].find(e => { return e[field] === value });
			}
		}
	}
