const MODULE_TYPE_PARAM_PRIORITY = 1;

function load() {

	webix.ajax(appConfig.restUrl + "module_types/" + MODULE_TYPE_SERVICE + "/modules").then(function(data) {

		let services = data.json();

		if (Array.isArray(services) && services.length > 0) {
			console.log(services);

			services.sort(window.app.service.compareByPriority);

			let position = 10;

			services.forEach(svc => {

				window.app.service.services.push(svc);

				window.app.module.loadScript(svc.jsFile, function(path, result) {

					if (result === "ok") {

						if (typeof window[svc.jsMethodDraw] === "function") {

							let priority = window.app.module.getParamValue(svc, MODULE_TYPE_PARAM_PRIORITY);
							let svcView = window[svc.jsMethodDraw](svc, null, ++position);

							if (typeof window[svc.jsMethodCallbacks] === "function") {
								window[svc.jsMethodCallbacks](svc, null);
							}
						}
					}
				});

				window.app.service.services.isLoaded = true;
			});
		}
	});
}

function compareByPriority(a, b) {

	var priority_a = window.app.module.getParamValue(a, MODULE_TYPE_PARAM_PRIORITY);
	var priority_b = window.app.module.getParamValue(b, MODULE_TYPE_PARAM_PRIORITY);

	if (priority_a < priority_b) {
		return -1;
	}
	if (priority_a > priority_b) {
		return 1;
	}
	return 0;
}
		
export default class Service {
	
	constructor() {
		
		load(this);
	}
}

if (window.app)

	window.app.service = {

		services: [],

		isLoaded: false,

		load: function() {

			webix.ajax(appConfig.restUrl + "module_types/" + MODULE_TYPE_SERVICE + "/modules").then(function(data) {

				let svcs = data.json();

				if (Array.isArray(svcs) && svcs.length > 0) {
					console.log(svcs);

					svcs.sort(window.app.service.compareByPriority);

					let position = 10;

					svcs.forEach(svc => {

						window.app.service.services.push(svc);

						window.app.module.loadScript(svc.jsFile, function(path, result) {

							if (result === "ok") {

								if (typeof window[svc.jsMethodDraw] === "function") {

									let priority = window.app.module.getParamValue(svc, MODULE_TYPE_PARAM_PRIORITY);
									let svcView = window[svc.jsMethodDraw](svc, null, ++position);

									if (typeof window[svc.jsMethodCallbacks] === "function") {
										window[svc.jsMethodCallbacks](svc, null);
									}
								}
							}
						});

						window.app.service.services.isLoaded = true;
					});
				}
			});
		},
		
		unload: function() {
			
		},

		compareByPriority: function(a, b) {

			var priority_a = window.app.module.getParamValue(a, MODULE_TYPE_PARAM_PRIORITY);
			var priority_b = window.app.module.getParamValue(b, MODULE_TYPE_PARAM_PRIORITY);

			if (priority_a < priority_b) {
				return -1;
			}
			if (priority_a > priority_b) {
				return 1;
			}
			return 0;
		}
	};
