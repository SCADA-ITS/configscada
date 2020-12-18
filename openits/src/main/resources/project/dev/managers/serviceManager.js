import {util} from "../lib/util.js";

const MODULE_TYPE_PARAM_PRIORITY = 1;

function compareByPriority(a, b) {

	var priority_a = util.module.getParamValue(a, MODULE_TYPE_PARAM_PRIORITY);
	var priority_b = util.module.getParamValue(b, MODULE_TYPE_PARAM_PRIORITY);

	if (priority_a < priority_b) {
		return -1;
	}
	if (priority_a > priority_b) {
		return 1;
	}
	return 0;
}

function load(service) {

	webix.ajax(appConfig.restUrl + "module_types/" + MODULE_TYPE_SERVICE + "/modules").then(function(data) {

		let svcs = data.json();

		if (Array.isArray(svcs) && svcs.length > 0) {
			console.log(svcs);

			svcs.sort(compareByPriority);

			svcs.forEach(svc => {
				
				let module = window.em.getByField(EntityType.MODULE.id, "id", svc.id);
						
				if (module) {
				
					util.module.open(module, null, null, null, service.services);	
				}
			});
		}
	});
}

export default class ServiceManager {

	constructor() {

		this.services = [];
		this.isLoaded = false;

		load(this);
	}

	getAll() {

		return this.services;
	}

	getService(serviceId) {

		if (this.services != undefined) {

			return this.services.find(e => e.id === serviceId).object;
		}
	}
}
