function assign(entitiesManager, em_type) {

	let response = webix.ajax().sync().get(EntityType[em_type].endpoint);

	if (response != undefined) {

		entitiesManager.entities[EntityType[em_type].id] = JSON.parse(response.responseText);

//		if (Object.keys(entitiesManager.entities).length === Object.keys(EntityType).length) {
//
//			entitiesManager.loaded = true;
//		}
	}
}

function load(entitiesManager) {

	for (var em_type in EntityType) {
		
		try {

			if (EntityType[em_type].mode === ENTITY_TYPE_MODE_PERMANENT && entitiesManager.entities[em_type] == undefined) {
	
				assign(entitiesManager, em_type);
			}
		}
		catch {}
	}
}

export default class EntitiesManager {

	constructor() {

		this.entities = {};
		this.tempEntities = {};
		load(this);
	}

	getAll(em_type) {

		if (em_type.mode === ENTITY_TYPE_MODE_PERMANENT) {
		
			return this.entities[em_type.id];	
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
			
			if (this.tempEntities[em_type.id].length > 0) {
			
				let index = this.tempEntities[em_type.id].findIndex(v => v.id === value);
				
				if (index > -1) {
					
					tempEntity = this.tempEntities[em_type.id][index];
					
					if (tempEntity && (Math.floor(new Date() / 1000) - tempEntity.date) > em_type.expireSeconds) {
					
						this.tempEntities[em_type.id].splice(index, 1);
						tempEntity = undefined;
					}
				}
			}
			
			if (!tempEntity) {
				
				let response = webix.ajax().sync().get(em_type.endpoint + "/" + value) ;
				
				if (response) {
				
					let responseObject = JSON.parse(response.responseText);
				
					this.tempEntities[em_type.id].push({
						id: value,
						date: Math.floor(new Date() / 1000),
						data: responseObject
					});
					
					return responseObject;	
				}
			}
			else {
				
				return tempEntity.data;
			}
		}
	}
}
