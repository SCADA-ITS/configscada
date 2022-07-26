function assign(entitiesManager, em_type) {

	function fill(response) {
		
		entitiesManager.entities[EntityType[em_type].id] = response;
			
		if (Array.isArray(response) && response.length > 0) {
		
			log.debug("Cached " + response.length + " entities of type " + em_type);	
		}
	}
	
	entitiesManager.statusEntities[EntityType[em_type].id] = EntitiesManager.ENTITY_STATE_LOADING;
	
	if (!EntityType[em_type].syncLoad) {
		
		webix.ajax().get(EntityType[em_type].endpoint).then(function(dat){
		
			if (dat != undefined) {
			
				let response = dat.json();
				
				fill(response);
				
				entitiesManager.statusEntities[EntityType[em_type].id] = EntitiesManager.ENTITY_STATE_LOADED;
			}
		});	
	}
	else {
		
		let dat = webix.ajax().sync().get(EntityType[em_type].endpoint);
		
		if (dat != undefined) {
			
			let response = JSON.parse(dat.responseText);

			fill(response);
			
			entitiesManager.statusEntities[EntityType[em_type].id] = EntitiesManager.ENTITY_STATE_LOADED;
		}
	}
}

function load(entitiesManager) {

	for (var em_type in EntityType) {
		
		try {

			if (EntityType[em_type].load) {
	
				assign(entitiesManager, em_type);
			}
		}
		catch {}
	}
}


export default class EntitiesManager {

	static get ENTITY_STATE_LOADING() { return 0 }
	static get ENTITY_STATE_LOADED() { return 1 }
	static get ACTION_CREATED() { return 1 }
	static get ACTION_UPDATED() { return 2 }
	static get ACTION_DELETED() { return 2 }

	constructor() {

		this.entities = {};
		this.tempEntities = {};
		this.statusEntities = {};
		this.lastUpdateTempEntities = {};
		load(this);
	}
	
	destroy() {
	
	}

	getAll(em_type) {

		return this.entities[em_type.id];
	}
	
	getState(em_type) {
		
		return this.statusEntities[em_type.id];
	}

	getByField(em_type, field, value) {

		if (this.entities[em_type.id] != undefined) {

			return this.entities[em_type.id].find(e => { return e[field] === value });
		}
	}
	
	getEntityTypeById(id) {
		
		let entityType = undefined;
		
		Object.keys(EntityType).forEach(key => {
			
			if (EntityType[key].id === id) {
				
				entityType = EntityType[key]; 
			}
		});
		
		return entityType;
	}
	
	add(em_type, id, entity) {
		
		if (em_type && id && entity && Array.isArray(this.entities[em_type.id])) {
			
			if (!this.getByField(em_type, "id", id)) {
				
				this.entities[em_type.id].push(entity);
			}
		}
	}
	
	update(em_type, id, entity) {
		
		if (em_type && id && entity && Array.isArray(this.entities[em_type.id])) {
			
			let idxEntity = this.entities[em_type.id].findIndex(e => e["id"] === id);
			
			if (idxEntity !== -1) {
				
				this.entities[em_type.id][idxEntity] = entity;
			}
		}
	}
	
	delete(em_type, id) {
		
		if (em_type && id && Array.isArray(this.entities[em_type.id])) {
			
			let idxEntity = this.entities[em_type.id].findIndex(e => e["id"] === id);
			
			if (idxEntity !== -1) {
				
				this.entities[em_type.id].splice(idxEntity, 1);
			}
		}
	}
}
