import GenericModule from "../modules/genericModule.js";
import { util } from "../lib/util.js";

export default class ExternalApp extends GenericModule{
	
	constructor(module, moduleAction, args){

		super(module, moduleAction, args);
		
		this.config = module.config;
    }

	getView() {
		
		return super.getViewCheckModuleActionAndSelectedPks();
	}
	
	loadData() {
		
		let self = this;
		let url;
		
		if ($$(self.uid)) {

			$$("loading-" + self.uid).show();
			$$(self.uid).hide();

			if (self.moduleAction && Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
		
				if (self.moduleAction.id === self.config.moduleAction.loadUrlFromElementId) {
				
					url = getUrlFromElement();	
				}
				else if (self.moduleAction.id === self.config.moduleAction.loadUrlFromElementIdUsingElementType) {
					
					url = getUrlFromElementType();
				}
				
				if (url) {
					
					let view = {
						id: self.uid,
						view:"iframe", 
						src: url 
					};
					
					webix.ui(view, $$(self.uid));
					
					$$("loading-" + self.uid).hide();
					$$(self.uid).show();
				}
			}
		}
		
		function getUrlFromElement() {
			
			let externalUrl;
			let element = window.em.getByField(EntityType.ELEMENT, "id", self.args.selectedPks[0]);
			
			if (element && element.externalUrl) {
				
				externalUrl = element.externalUrl;
			}
			
			return externalUrl;
		}
		
		function getUrlFromElementType() {
			
			let externalUrl;
			let element = window.em.getByField(EntityType.ELEMENT, "id", self.args.selectedPks[0]);
			
			if (element) {
				
				let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);
				
				if (elementType && elementType.externalUrl) {
					
					externalUrl = elementType.externalUrl;	
				}
			}
			
			return externalUrl;
		}
	}
}
