import {util} from "../lib/util.js";

const MODULE_TYPE_PARAM_PRIORITY = 1;

var servicesCount = 0;

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

function load(service, onLoaded) {
	
	let onLoadedCallback = onLoaded;

	webix.ajax(appConfig.restUrl + "module_types/" + MODULE_TYPE_SERVICE + "/modules").then(function(data) {

		let svcs = data.json();

		if (Array.isArray(svcs) && svcs.length > 0) {
			
			svcs = svcs.filter(svc => svc.enabled);

			svcs.sort(compareByPriority);
			
			servicesCount = svcs.length;

			svcs.forEach(svc => {
				
				let module = window.em.getByField(EntityType.MODULE, "id", svc.id);
						
				if (module) {
				
					drawService(service, module)	
				}
			});
			
			webix.delay(function() {

				while (!runServices(service, onLoadedCallback));
				
			}, null, null, 1000);
		}
	});
}

function drawService(service, module) {
	
	try {

		const lazy = async _ => {

			let jsFile = util.getContextPath() + "/" + module.jsFile;
			const mod = await import(jsFile);

			let lazyClass = new mod.default(module);
	
			if (lazyClass) {
				
				let serviceItem = {
					module: module,
					views: module.jsMethodDraw ? lazyClass.runOne(module.jsMethodDraw) : undefined,
					object: lazyClass
				}
								
				service.services.push(serviceItem);
			}
		}

		lazy();

	} catch (error) {

		console.log(error);
	}
}

function runServices(service, onLoaded) {
	
	let onLoadedCallback = onLoaded;
	
	if (service.services.length < servicesCount) {
		
		return false;
	}
	
	let toolbar = $$(MAIN_TOOLBAR_ICONS_ID);
	
	service.services.sort(function(a, b) {
		
		var priority_a = util.module.getParamValue(a.module, MODULE_TYPE_PARAM_PRIORITY);
		var priority_b = util.module.getParamValue(b.module, MODULE_TYPE_PARAM_PRIORITY);
	
		if (priority_a < priority_b) {
			return -1;
		}
		if (priority_a > priority_b) {
			return 1;
		}
		return 0;
	});
	
	service.services.forEach(serviceItem => {
		
		if (toolbar) {
			
			if (serviceItem.views) {
				
				serviceItem.views.forEach(view => {
				
					toolbar.addView(view);	
				});
			}
		}
	
		if (serviceItem.module.jsMethodCallbacks && serviceItem.object) {
			
			serviceItem.object.runOne(serviceItem.module.jsMethodCallbacks);
		}
	});
	
	if (toolbar) {
		
		toolbar.addView({width: 1});
	}
	
	if (onLoadedCallback) {
		
		onLoadedCallback();	
	}
	
	return true;
}

export default class ServiceManager {

	constructor(onLoaded) {

		this.services = [];
		this.onLoaded = onLoaded;

		load(this, onLoaded);
	}

	getAll() {

		return this.services;
	}

	getService(serviceId) {

		if (this.services != undefined) {

			let service = this.services.find(e => e.module.id === serviceId);
			
			if (service) {
				
				return service.object;
			}
		}
	}
}
