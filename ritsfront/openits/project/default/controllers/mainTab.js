//const MAIN_TAB_ID = "tabs";

export default class MainTab {

	constructor() {
	
	}
	
	getView() {
		
		let view = {
			view: "tabview",
			css: "main-tab",
			borderless: 1,
			id: MAIN_TAB_ID,
			tabbar: {
				optionWidth: 283,
				close: true
			},
			animate: false,
			cells: [{ id: "tab2remove" }]
		};
				
		return view;
	}
	
	attachOnBeforeTabClose() {
		
		let view = $$(MAIN_TAB_ID); 
			
		if (view) {
		
			$$(MAIN_TAB_ID).getTabbar().attachEvent("onBeforeTabClose", function(id, e) {

				webix.message("Close tab: " + id);
				view.removeView(id);
			});
			
			view.removeView("tab2remove");	
		}
	}
}
