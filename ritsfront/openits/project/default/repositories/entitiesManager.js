function assign(entitiesManager, em_type) {

	let response = webix.ajax().sync().get(EntityType[em_type].endpoint);

	if (response != undefined) {

		entitiesManager.entities[EntityType[em_type].id] = JSON.parse(response.responseText);

		if (Object.keys(entitiesManager.entities).length === Object.keys(EntityType).length) {

			entitiesManager.loaded = true;
		}
	}
}

function load(entitiesManager) {

	for (var em_type in EntityType) {

		if (entitiesManager.entities[em_type] == undefined) {

			assign(entitiesManager, em_type);
		}
	}

	entitiesManager.isLoaded = true;
}

export default class EntitiesManager {

	constructor() {

		this.entities = {};
		this.isLoaded = false;

		load(this);
	}

	getAll(entity) {

		return this.entities[entity];
	}

	getByField(entity, field, value) {

		if (this.entities[entity] != undefined) {

			return this.entities[entity].find(e => { return e[field] === value });
		}
	}
}
