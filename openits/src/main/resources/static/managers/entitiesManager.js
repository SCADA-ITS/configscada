function assign(entitiesManager, em_type) {

	function fill(response) {
		
		if (EntityType[em_type].mode === ENTITY_TYPE_MODE_PERMANENT) {
				
			entitiesManager.entities[EntityType[em_type].id] = response;
			
			if (Array.isArray(response) && response.length > 0) {
			
				//console.log("[" + (new Date()).toISOString() + "] " + "[EntitiesManager] Cached " + response.length + " entities of type " + em_type);
				
				log.debug("Cached " + response.length + " entities of type " + em_type);	
			}
				
		}
		else if (EntityType[em_type].mode === ENTITY_TYPE_MODE_TEMPORARY) {
					
			let tempEntities = response.map(function (obj) {
 
			    return { 
					id: obj.id, 
					date: Math.floor(new Date() / 1000),
					data: obj
				};
			});
					
			entitiesManager.tempEntities[EntityType[em_type].id] = tempEntities;
			
			if (Array.isArray(response) && response.length > 0) {
			
				let nextUpdate = new Date((new Date()).getTime() + (EntityType[em_type].expireSeconds * 1000));
				//console.log("[" + (new Date()).toISOString() + "] " + "[EntitiesManager] Cached " + response.length + " entities of type " + em_type + ". Next update at " + nextUpdate.toLocaleString());
				log.debug("Cached " + response.length + " entities of type " + em_type + ". Next update at " + nextUpdate.toLocaleString());	
			}
			
			entitiesManager.lastUpdateTempEntities[[EntityType[em_type].id]] = Math.floor(new Date() / 1000);
		}
	}
	
	if (!EntityType[em_type].syncLoad) {
		
		webix.ajax().get(EntityType[em_type].endpoint).then(function(dat){
		
			if (dat != undefined) {
			
				let response = dat.json();
				
				fill(response);
				
				
			}
		});	
	}
	else {
		
		let dat = webix.ajax().sync().get(EntityType[em_type].endpoint);
		
		if (dat != undefined) {
			
			let response = JSON.parse(dat.responseText);

			fill(response);
		}
	}
}

function load(entitiesManager) {

	for (var em_type in EntityType) {
		
		try {

			//if (EntityType[em_type].mode === ENTITY_TYPE_MODE_PERMANENT && entitiesManager.entities[em_type] == undefined) {
			if (EntityType[em_type].load) {
	
				assign(entitiesManager, em_type);
			}
		}
		catch {}
	}
}

function scheduler(entitiesManager) {
	
	return setInterval(function(){
	
		for (var em_type in EntityType) {
		
			try {

				if (EntityType[em_type].load && EntityType[em_type].mode === ENTITY_TYPE_MODE_TEMPORARY) {
					
					let nextSchedulerDate = Math.floor(new Date() / 1000) + ENTITIES_MANAGER_SCHEDULER_SECS;
					let nextExpireType = entitiesManager.lastUpdateTempEntities[EntityType[em_type].id] + EntityType[em_type].expireSeconds;
					  
					//if ((Math.floor(new Date() / 1000) - entitiesManager.lastUpdateTempEntities[EntityType[em_type].id]) > EntityType[em_type].expireSeconds) {
					if (nextSchedulerDate > nextExpireType) {
					
						//console.log("[" + (new Date()).toISOString() + "] " + "[EntitiesManager] Scheduled refresh cache for entity of type " + em_type);
						log.debug("Scheduled refresh cache for entity of type " + em_type);
						assign(entitiesManager, em_type);	
					}
				}
			}
			catch {}
		}	
		
	}, ENTITIES_MANAGER_SCHEDULER_SECS * 1000);
	
}

export default class EntitiesManager {

	constructor() {

		this.entities = {};
		this.tempEntities = {};
		this.lastUpdateTempEntities = {};
		load(this);
		this.sched = scheduler(this);
	}
	
	destroy() {
	
		if (this.sched) {
			
			clearInterval(this.sched);	
		}
	}

	getAll(em_type) {

		if (em_type.mode === ENTITY_TYPE_MODE_PERMANENT) {
		
			return this.entities[em_type.id];	
		}
		else if (em_type.mode === ENTITY_TYPE_MODE_TEMPORARY) {
			
			return this.tempEntities[em_type.id].map(function (obj) {
 
			    return obj.data;
			});
		}
	}

	getByField(em_type, field, value) {

		if (em_type.mode=== ENTITY_TYPE_MODE_PERMANENT && this.entities[em_type.id] != undefined) {

			return this.entities[em_type.id].find(e => { return e[field] === value });
		}
		else if (em_type.mode === ENTITY_TYPE_MODE_TEMPORARY) {
			
			if (!this.tempEntities[em_type.id]) {
				
				this.tempEntities[em_type.id] = [];
			}
			
			let tempEntity = undefined;
			let index = -1;
			
			if (this.tempEntities[em_type.id].length > 0) {
			
				index = this.tempEntities[em_type.id].findIndex(v => v[field] === value);
				
				if (index > -1) {
					
					tempEntity = this.tempEntities[em_type.id][index];
					
					if (tempEntity && (Math.floor(new Date() / 1000) - tempEntity.date) > em_type.expireSeconds) {
						
						//console.log("[" + (new Date()).toISOString() + "] " + tempEntity + " needs to be updated because it exceeds expiration time");
						log.debug(tempEntity + " needs to be updated because it exceeds expiration time");
					
						//this.tempEntities[em_type.id].splice(index, 1);
						tempEntity = undefined;
					}
				}
			}
			
			if (!tempEntity) {
				
				//console.log("[" + (new Date()).toISOString() + "] " + "Querying => " + em_type.endpoint + "/" + value);
				log.debug("Querying => " + em_type.endpoint + "/" + value);
				
				let response = webix.ajax().sync().get(em_type.endpoint + "/" + value) ;
						
				if (response) {
					
					//console.log("[" + (new Date()).toISOString() + "] " + "Response => " + em_type.endpoint + "/" + value);
					log.debug("Response => " + em_type.endpoint + "/" + value);
				
					let responseObject = JSON.parse(response.responseText);
					
					if (index > -1) {
						
						//console.log("[" + (new Date()).toISOString() + "] " + "Deleting old value => " + this.tempEntities[em_type.id][index]);
						log.debug("Deleting old value => " + this.tempEntities[em_type.id][index]);

						this.tempEntities[em_type.id].splice(index, 1);
						
						//console.log("[" + (new Date()).toISOString() + "] " + "Deleted old value");
						log.debug("Deleted old value");
					}
				
					let newValue = {
						id: value,
						date: Math.floor(new Date() / 1000),
						data: responseObject
					};
					
					//console.log("[" + (new Date()).toISOString() + "] " + "Adding new value => " + newValue);
					log.debug("Adding new value => " + newValue);
					
					this.tempEntities[em_type.id].push(newValue);
					
					//console.log("[" + (new Date()).toISOString() + "] " + "Added value");
					log.debug("Added value");
					
					return responseObject;	
				}
			}
			else {
				
				return tempEntity.data;
			}
		}
	}
	
	update(em_type) {
		
		assign(this, em_type.id);
	}
}
