import GenericModule from "../modules/genericModule.js";
import Synoptic from "../component/map/synoptic.js";
import { util } from "../lib/util.js";

export default class SynopticModule extends GenericModule {

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

		let synopticId = this.module.moduleValues.find(e => e.moduleParam.id === PARAM_MODULE_TYPE_GENERIC_SYNOPTIC_WINDOW_SYNOPTIC_ID).value;	
		
		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.synoptic, { id: synopticId })).then(function(response) {
						
			if (response != null) {
			
				let responseJson = response.json();
				
				if (responseJson) {
					
					let events = {};
					
					let options = {};
						
					options[Synoptic.Options.DEBUG_MODE] = self.config.debugMode;
					
					let synoptic = new Synoptic(responseJson.metadata, responseJson.data, events, $$("loading-" + self.uid).$width, $$("loading-" + self.uid).$height, options);
					
					$$("loading-" + self.uid).hide();
					$$(self.uid).show();
					
					webix.ui(synoptic.getView(), $$(self.uid));
				}
			}
		});
	}
}
