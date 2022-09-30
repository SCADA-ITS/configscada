import { util } from "../lib/util.js"; 
import GenericModule from "../modules/genericModule.js";
import SearchBox from "../component/searchBox.js";

export default class SearchService extends GenericModule {
	
	constructor(module){
		
		super(module, null, null);
		
		this.module = module;
	}
	
	destroy() {
		
		// Not available implementation	
	}
	
	getView() {

		let self = this;
		let views = [];
		let viewSearch;
			
		let searchBox = new SearchBox(onValidate, undefined, true);
		
		viewSearch = {
			width: 300,
			rows: [
				searchBox.getView()		
			]
		};
		
		views.push(viewSearch);
		
		function onValidate(text, type) {
					
			let mod = util.module.getFromModuleAction(self.module.config.searchModuleAction);

			if (mod) {

				let moduleAction = { id: self.module.config.searchModuleAction };

				let args = {
					text: text,
					type: type
				};

				util.module.open(mod, moduleAction, null, null, args);
			}
		}
		
		return views;
	}
	
	loadData() {
		
		// Not available implementation
	}
}
