const em_types = { 
	elementType: { id: 1, endpoint: appConfig.restUrl + "element_types" }
}

if (window.app)
	
	app.em = {
		
		entities: {},
	
		load: function() {
	
			for(var em_type in em_types) {
				
				if (app.em.entities[em_type] == undefined) {
				
					webix.ajax().get(em_types[em_type].endpoint).then(function(data) {
			
						app.em.entities[em_types[em_type].id] = data.json();			
					});
				}   
			}
			
		},
		
		getAll: function(entity) {
			
			return app.em.entities[entity];
		},
		
		getByField: function(entity, field, value) {
			
			if (entities[entity] != undefined) {
				
				return app.em.entities[entity].filter(e => e[field] === value);
			}
		}
	}
