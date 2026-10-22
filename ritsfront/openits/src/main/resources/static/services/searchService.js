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
		let types = undefined;
		let allFalse = false;
		let searchBoxView = {height: 1};
		
		if (self.module.config.searchOptions) {

			types = typesForShow();

			if (Array.isArray(types) && types.length === 0) {

				allFalse = true;
			}
		}
			
		let searchBox = new SearchBox(onValidate, types, true);

		if (!allFalse) {

			searchBoxView = searchBox.getView() 
		}
		
		viewSearch = {
			width: 300,
			rows: [searchBoxView]
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
		
		function typesForShow() {

			let searchBoxOptions = [];
			let searchOptions = self.module.config.searchOptions;
			let options = Object.keys(self.module.config.searchOptions);

			if (Array.isArray(options) && options.length > 0) {

				let searchBoxTypes = webix.copy(SearchBox.TYPE);

				options.forEach(op => {

					if (searchOptions[op]) {

						let type = searchBoxTypes[op];

						if (type) {

							searchBoxOptions.push(type.id);
						}
					}
				});
			}

			return searchBoxOptions;
		}
		
		return views;
	}
	
	loadData() {
		
		// Not available implementation
	}
}
