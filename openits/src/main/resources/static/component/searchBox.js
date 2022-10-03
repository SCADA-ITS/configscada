export default class SearchBox {

	static get TYPE() { 
		return {
			ALL: { id: 1, placeHolder: "LBL_SEARCHBOX_SEARCH_ALL", label: "LBL_ALL" },
			ELEMENT: { id: 2, placeHolder: "LBL_SEARCHBOX_SEARCH_ELEMENT", label: "LBL_ELEMENT" },
			ALARM: { id: 3, placeHolder: "LBL_SEARCHBOX_SEARCH_ALARM", label: "LBL_ALARM" },
			INCIDENT: { id: 4, placeHolder: "LBL_SEARCHBOX_SEARCH_INCIDENT", label: "LBL_INCIDENT" }
		} 
	}

	constructor(onValidate, type, isToolbar) {
		
		this.onValidate = onValidate;
		this.type = type ? type : SearchBox.TYPE.ALL;
		this.uid = webix.uid();	 
		this.isToolbar = isToolbar;
	}
	
	getView() {
		
		let self = this;
		let view;
		let cssStyleInvertUser = (window.lightMode) ? "searchbox-template light-templates" : "searchbox-template";
		
		view = {
			id: "search-main-" + self.uid,
			borderless: true,
			cols: [
				{
					view: "search",
					css: self.isToolbar ? cssStyleInvertUser : undefined,
					id: "search-" + self.uid,
					placeholder: i18n.t(self.type.placeHolder),
					on: {
						onEnter: function() {
							
							if (this.getValue() && this.getValue().trim().length > 0 && self.onValidate) {
								
								self.onValidate(this.getValue().trim(), self.type);
							}
							
							this.setValue("");
						}
					}
				},
				getViewMenuChangeSearchType()
			]
		}
		
		function getViewMenuChangeSearchType() {
			
			let viewMenuChangeSearchType;
			
			viewMenuChangeSearchType = {
				view: "menu",
				id: "search-menu-" + self.uid,
				autowidth: true,
				openAction: "click",
				width: 34,
				css: self.isToolbar ? "dt-submenu_searchbox-tb" : "dt-submenu_searchbox",
				subMenuPos: "down",
				data: [
					{ 
						config: { css: self.isToolbar ? "dt-submenu_searchbox-tb" : "dt-submenu_searchbox" },
						value: "", 
						icon: "mdi mdi-menu-down", 
						submenu: getMenuSearchTypes() }
				],
				on: {
					onMenuItemClick: function(id) {
						
						let newType;
						
						Object.keys(SearchBox.TYPE).forEach(key => {
							
							if (SearchBox.TYPE[key].id == id) {
								
								newType = SearchBox.TYPE[key];
							}
						});
						
						if (newType) {
							
							self.type = newType;
							
							let searchView = $$("search-" + self.uid);
						
							if (searchView) {
								
								searchView.define("placeholder", i18n.t(self.type.placeHolder));
								searchView.refresh();
								
								refreshMenuItemSelected();
							}	
						} 
					}
				}
			};
			
			if (self.isToolbar) {
				
				//viewMenuChangeSearchType.css = "dt-submenu_searchbox";
			}
			
			function getMenuSearchTypes() {
				
				return Object.keys(SearchBox.TYPE).map(key => {
				
					let type = webix.copy(SearchBox.TYPE[key]);
					
					type.value = getMenuTemplate(type);
					
					return type;
				});
			}
			
			function refreshMenuItemSelected() {
				
				let menuSearchTypes = getMenuSearchTypes();
				
				menuSearchTypes.forEach(type => {
				
					let typeElem = document.getElementById('search-menu-type-' + self.uid + "-" + type.id); 

					if (typeElem) {
						
						typeElem.innerHTML = getMenuTemplate(type); 	
					}
					 	
				});
			}
			
			function getMenuTemplate(type) {
				
				return `<div id="${'search-menu-type-' + self.uid + "-" + type.id}"><span ${(type.id != self.type.id) ? 'style="color: transparent"' : 'style="color: var(--blue-text-default)"'} class="webix_icon mdi mdi-check"></span>  ${i18n.t(type.label)}</div>`;
			}
			
			return viewMenuChangeSearchType;
		}
		
		return view;
	}
	
	disable() {

		if ($$("search-main-" + this.uid)) {
			
			$$("search-main-" + this.uid).disable();
		}
	}
	
	enable() {
		
		if ($$("search-main-" + this.uid)) {
			
			$$("search-main-" + this.uid).enable();
		}
	}
}