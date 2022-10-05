export default class MainTab {

	constructor() {
	
		// This is intentional
	}
	
	getView() {
		
		let view; 
		
		view = {
			view: "tabview",
			css: "main-tab",
			borderless: 1,
			id: MAIN_TAB_ID,
			hidden: true,
			tabbar: {
				optionWidth: 283,
				close: true,
				multiview: true,
				on: {
					onOptionAdd: this.refreshBackground,
					onOptionRemove: this.refreshBackground
				}
			},
			multiview: {
				keepViews:true
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

				for(let l of $$(id).getNode().getElementsByTagName("img")) { 
          			l.src = "";
        		}
				view.removeView(id);
			});
			
			view.removeView("tab2remove");	
		}
		
		this.refreshBackground();
	}
	
	refreshBackground() {
			
		let mainTab = $$(MAIN_TAB_ID);
		let mainBackgroundImage = $$(MAIN_BACKGROUND_IMAGE_ID);
		
		if (mainTab && mainBackgroundImage) {
			
			if (Array.isArray(mainTab.getTabbar().config.options) && mainTab.getTabbar().config.options.length > 0) {
						
				mainTab.show();
				mainBackgroundImage.hide();
			}
			else {
				
				mainTab.hide();
				mainBackgroundImage.show();
			}	
		}
	}
}
