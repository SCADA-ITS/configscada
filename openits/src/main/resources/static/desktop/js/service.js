const MODULE_TYPE_PARAM_PRIORITY = 1;

if (window.app)

	app.service = {

		services: [],

		isLoaded: false,

		load() {

			webix.ajax(appConfig.restUrl + "module_types/" + MODULE_TYPE_SERVICE + "/modules").then(function(data) {

				let svcs = data.json();

				if (Array.isArray(svcs) && svcs.length > 0) {
					console.log(svcs);

					svcs.sort(app.service.compareByPriority);

					svcs.forEach(svc => {

						app.service.services.push(svc);

						app.module.loadScript(svc.jsFile, function(path, result) {

							if (result === "ok") {

								if (typeof window[svc.jsMethodDraw] === "function") {

									let priority = app.module.getParamValue(svc, MODULE_TYPE_PARAM_PRIORITY);
									let svcView = window[svc.jsMethodDraw](svc, null);

									if (typeof window[svc.jsMethodCallbacks] === "function") {
										window[svc.jsMethodCallbacks](svc, null);
									}
								}
							}
						});

						app.service.services.isLoaded = true;
					});
				}
			});
		},

		compareByPriority(a, b) {

			var priority_a = app.module.getParamValue(a, MODULE_TYPE_PARAM_PRIORITY);
			var priority_b = app.module.getParamValue(b, MODULE_TYPE_PARAM_PRIORITY);

			if (priority_a < priority_b) {
				return -1;
			}
			if (priority_a > priority_b) {
				return 1;
			}
			return 0;
		}
	};
