import { util } from "../../lib/util.js";
import GenericModule from "../../modules/genericModule.js";

export default class HelpViewerModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
	}
	
	loadData() {
		
		let self = this;
		
		$$(self.uid).hide();
		$$("loading-" + self.uid).show();
		
		let url = HELP_PATH + HELP_FILE;
		
		if (self.moduleAction) {
			
			if (self.moduleAction.id === self.config.moduleAction.viewModuleHelp && self.args.moduleId) {
			
				url = appConfig.restUrl + util.fillTemplate(ENDPOINT_GET_MODULE_HELP, { moduleId: self.args.moduleId });	
			}
			else if (self.moduleAction.id === self.config.moduleAction.viewTagHelp && self.args.tag) {
				
				url = appConfig.restUrl + util.fillTemplate(ENDPOINT_GET_TAG_HELP, { tag: self.args.tag });
			}	
		}
		
		let view = {
			
			id: self.uid,
			borderless: true,
			cols: [
				{ width: 20 },
				{
					rows: [
						{ height: 20 },
						{
							view:"iframe",
							src: url,
						},
						{ height: 20 }
					]	
				},
				{ width: 20 }	
			]
		}
		
		webix.ui(view, $$(self.uid));
				
		$$("loading-" + self.uid).hide();
		$$(self.uid).show();
	}
}
