import GenericModule from "../modules/genericModule.js";
import OpenMap from "../component/map/openMap.js";
import { util } from "../lib/util.js";

export default class MapModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.stompClient = undefined;
		this.config = module.config;

	}

	getView() {

		let view = undefined;

		view = {
			css: "custom_window",
			cols: [
				{
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									id: this.uid,
									rows: [],
									hidden: true
								},
								{
									id: "loading-" + this.uid,
									rows: [util.ui.getViewDrawNoData()],
									hidden: false
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		};

		return view;
	}

	loadData() {

		let self = this;
		
		$$("loading-" + this.uid).show();
		$$(this.uid).hide();

		let mapId = this.module.moduleValues.find(e => e.moduleParam.id === PARAM_MODULE_TYPE_GENERIC_MAP_WINDOW_MAP_ID).value;	
		
		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.map, { id: mapId })).then(function(response) {
						
			if (response != null) {
			
				let responseJson = response.json();
				
				if (responseJson) {
					
					let events = {};
					
					let options = {};
						
					options[Map.Options.DEBUG_MODE] = self.config.debugMode;
					
					let map = new OpenMap(responseJson.metadata, responseJson.data, events, options);
					
					$$("loading-" + self.uid).hide();
					$$(self.uid).show();
					
					webix.ui(map.getView(), $$(self.uid));
				}
			}
		});
	}
}
