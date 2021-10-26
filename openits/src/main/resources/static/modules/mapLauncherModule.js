import GenericModule from "../modules/genericModule.js";
import OpenMap from "../component/map/openMap.js";
import Synoptic from "../component/map/synoptic.js";
import { util } from "../lib/util.js";

const DEBUG_MODE = false;

export default class MapLauncherModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.stompClient = undefined;
		this.config = module.config;
	}

	getView() {

		return super.getViewCheckModuleActionAndSelectedPks();
	}

	loadData() {

		let self = this;

		$$("loading-" + this.uid).show();
		$$(this.uid).hide();

		if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

			let element = window.em.getByField(EntityType.ELEMENT, "id", self.args.selectedPks[0]);

			if (self.moduleAction.id === self.config.moduleAction.map) {

				loadMap(element);

			} else if (self.moduleAction.id === self.config.moduleAction.synoptic) {

				loadSynoptic(element);
			}
		}

		function loadMap(element) {

			if (element && element.map) {

				let mapId = element.map;

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.map, { id: mapId })).then(function(response) {

					if (response != null) {

						let responseJson = response.json();

						if (responseJson) {

							let events = {};

							let options = {};

							options[Map.Options.DEBUG_MODE] = DEBUG_MODE;
							
							let w = self.args.width;
							let h = self.args.height;
							let container = $$(self.args.containerId);
							
							if (container && (!w || !h)) {
								
								w = container.$width - 10;
								h = container.$height - 10;
							}

							let map = new OpenMap(responseJson.metadata, responseJson.data, events, w, h, options);

							if (map) {
								
								$$("loading-" + self.uid).hide();
								$$(self.uid).show();

								webix.ui(map.getView(function() { map.select(element.id) }), $$(self.uid));
								
								if (container) {
									
									util.ui.setTitle(container.config.id, i18n.t(responseJson.metadata.label));
								}
							}
						}
					}
				});
			}
		}

		function loadSynoptic(element) {

			if (element && element.synoptic) {

				let synopticId = element.synoptic;

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.synoptic, { id: synopticId })).then(function(response) {

					if (response != null) {

						let responseJson = response.json();

						if (responseJson) {

							console.log(responseJson);
							let events = {};

							let options = {};

							options[Synoptic.Options.DEBUG_MODE] = DEBUG_MODE;
							
							let w = self.args.width;
							let h = self.args.height;
							let container = $$(self.args.containerId);
							
							if (container && (!w || !h)) {
								
								w = container.$width - 10;
								h = container.$height - 10;
							}

							let synoptic = new Synoptic(responseJson.metadata, responseJson.data, events, w, h, options);

							if (synoptic) {

								$$("loading-" + self.uid).hide();
								$$(self.uid).show();

								webix.ui(synoptic.getView(), $$(self.uid));
								
								if (container) {
									
									util.ui.setTitle(container.config.id, i18n.t(responseJson.metadata.label));
								}
							}
						}
					}
				});
			}
		}

	}
}
