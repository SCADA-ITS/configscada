import {util} from "../lib/util.js";

const MODULE_TYPE_PARAM_PRIORITY = 1;
const SLEEP_WAITING_FOR_SERVICES_MS = 1000;
const MAX_TIME_WAITING_FOR_SERVICES_MS = 20000;

var servicesCount = 0;
var timeWaitingForServices = 0;


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
	
	let intervalService = undefined;
	let onLoadedCallback = onLoaded;
	
	const allModules = window.em.getAll(EntityType.MODULE);
	
	if (Array.isArray(allModules) && allModules.length > 0) {
		
		let svcs = allModules.filter(e => e.moduleType && e.moduleType.id === MODULE_TYPE_SERVICE);

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
			
			timeWaitingForServices = 0;
			
			intervalService = setInterval(function() {

				if (runServices(service, onLoadedCallback)) {
				
					clearInterval(intervalService);
				}

			}, SLEEP_WAITING_FOR_SERVICES_MS);
		}
	}
}

function drawService(service, module) {
	
	let jsFile = util.getContextPath() + "/" + module.jsFile;
			
	log.debug("Loading " + module.jsFile + " ...");
	
	import(jsFile)
		.then(mod => {
	
			log.debug("Loaded " + module.jsFile);	
			let lazyClass = new mod.default(module);
	
			if (lazyClass) {
				
				let serviceItem = {
					module: module,
					views: module.jsMethodDraw ? lazyClass.runOne(module.jsMethodDraw) : undefined,
					object: lazyClass
				}
								
				service.services.push(serviceItem);
			}
		})
		.catch((error) => {
            
			log.error(error);
        });
}

function runServices(service, onLoaded) {
	
	let onLoadedCallback = onLoaded;
	
	if (service.services.length < servicesCount) {
		
		log.debug("Waiting for load " + (servicesCount - service.services.length) + " services...");
		
		timeWaitingForServices += SLEEP_WAITING_FOR_SERVICES_MS;
		
		return (timeWaitingForServices > MAX_TIME_WAITING_FOR_SERVICES_MS);
	}
	
	if (service.services.length < servicesCount && timeWaitingForServices > MAX_TIME_WAITING_FOR_SERVICES_MS) {
	
		log.error("Timeout loading services");
		
		return false;	
	}
	
	log.debug("All services loaded in " + (timeWaitingForServices / 1000) + "s.");
	
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
			
			log.debug(serviceItem.module.name + " is ready");
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
