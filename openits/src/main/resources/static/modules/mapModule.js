import GenericModule from "../modules/genericModule.js";
import OpenMap from "../component/map/openMap.js";
import { util } from "../lib/util.js";

const PARAM_MODULE_TYPE_GENERIC_MAP_WINDOW_MAP_ID = 6;
const MAP_REST_ENDPOINT = "maps/${id}";
const DEBUG_MODE = false;

export default class MapModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.stompClient = undefined;
	}

	loadData() {

		let self = this;
		
		$$("loading-" + this.uid).show();
		$$(this.uid).hide();

		let mapId = this.module.moduleValues.find(e => e.moduleParam.id === PARAM_MODULE_TYPE_GENERIC_MAP_WINDOW_MAP_ID).value;	
		
		webix.ajax().get(appConfig.restUrl + util.fillTemplate(MAP_REST_ENDPOINT, { id: mapId })).then(function(response) {
						
			if (response != null) {
			
				let responseJson = response.json();
				
				if (responseJson) {
					
					let events = {};
					
					let options = {};
						
					options[Map.Options.DEBUG_MODE] = DEBUG_MODE;
					
					let map = new OpenMap(responseJson.metadata, responseJson.data, events, $$("loading-" + self.uid).$width, $$("loading-" + self.uid).$height, options);
					
					$$("loading-" + self.uid).hide();
					$$(self.uid).show();
					
					webix.ui(map.getView(), $$(self.uid));
				}
			}
		});
	}
}
