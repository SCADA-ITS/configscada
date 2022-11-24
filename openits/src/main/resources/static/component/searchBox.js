import { util } from "../lib/util.js";

export default class SearchBox {

	static get TYPE() { 
		return {
			ALL: { id: 1, placeHolder: "LBL_SEARCHBOX_SEARCH_ALL", label: "LBL_ALL", enabled: true },
			ELEMENT: { id: 2, placeHolder: "LBL_SEARCHBOX_SEARCH_ELEMENT", label: "LBL_ELEMENT", enabled: true },
			ALARM: { id: 3, placeHolder: "LBL_SEARCHBOX_SEARCH_ALARM", label: "LBL_ALARM", enabled: true },
			INCIDENT: { id: 4, placeHolder: "LBL_SEARCHBOX_SEARCH_INCIDENT", label: "LBL_INCIDENT", enabled: util.module.isAllowedPermission(REQUIRED_PERMISSIONS.INCIDENT_VIEW) },
			PLAN: { id: 5, placeHolder: "LBL_SEARCHBOX_SEARCH_PLAN", label: "LBL_PLAN", enabled: true }
		} 
	}

	constructor(onValidate, types, isToolbar) {
		
		this.onValidate = onValidate;		
		this.uid = webix.uid();	 
		this.isToolbar = isToolbar;
		this.typeSelected = SearchBox.TYPE.ALL;
		this.types = [];

		if (Array.isArray(types)) {

			this.types = types;
			
			if (types.length === 1) {

				let searchBoxTypes = webix.copy(SearchBox.TYPE);
				let options = Object.keys(searchBoxTypes);

				options.forEach(op => {

					let type = searchBoxTypes[op];

					if (type && type.id === types[0]) {

						this.typeSelected = type;
					}
				});					
				
			} else {
				
				this.types.unshift(SearchBox.TYPE.ALL.id);
			}			

		} else {

			this.types = types ? types : SearchBox.TYPE.ALL;
		}
	}
	
	getView() {
		
		let self = this;
		let view;
		let filteredTypes = [];
		let cssStyleInvertUser = (window.lightMode) ? "searchbox-template light-templates" : "searchbox-template";
		
		view = {
			id: "search-main-" + self.uid,
			borderless: true,
			cols: [
				{
					id: "search-" + self.uid,
					view: "search",
					icon: undefined,
					css: self.isToolbar ? cssStyleInvertUser : undefined,
//					placeholder: i18n.t(self.typeSelected.placeHolder),
					on: {
						onEnter: search
					}
				},
				{
					view: "icon",
					icon: "wxi-search",
					on: {
						onItemClick: search
					}
				},
				getViewMenuChangeSearchType()
			]
		}
		
		function search() {
			
			let searchView = $$("search-" + self.uid);
			
			if (searchView) {
				
				let text = searchView.getValue(); 
				
				if (text && text.trim().length > 0 && self.onValidate) {
				
					self.onValidate(text.trim(), self.typeSelected);
				}
				
				searchView.setValue("");
				searchView.focus();
			}
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
						
						let newType = filteredTypes.find(t=> t.id == id);
						
//						Object.keys(SearchBox.TYPE).forEach(key => {
//							
//							if (SearchBox.TYPE[key].id == id) {
//								
//								newType = SearchBox.TYPE[key];
//							}
//						});
						
						if (newType) {
							
							self.typeSelected = newType;
							
							let searchView = $$("search-" + self.uid);
						
							if (searchView) {
								
								searchView.define("placeholder", i18n.t(self.typeSelected.placeHolder));
								searchView.refresh();
								
								refreshMenuItemSelected();
							}	
						} 
					}
				}
			};
			
			function getMenuSearchTypes() {

				Object.keys(SearchBox.TYPE).forEach(key => {

					let showType = true;
					let type = webix.copy(SearchBox.TYPE[key]);

					if (Array.isArray(self.types) && self.types.length > 0) {

						showType = self.types.find(t => t === type.id);
					}

					if (type.enabled !== false && showType) {

						type.value = getMenuTemplate(type);
						filteredTypes.push(type);
					}
				});

				return filteredTypes;
			}
			
			function refreshMenuItemSelected() {
				
				//let menuSearchTypes = getMenuSearchTypes();
				
				filteredTypes.forEach(type => {
				
					let typeElem = document.getElementById('search-menu-type-' + self.uid + "-" + type.id); 

					if (typeElem) {
						
						typeElem.innerHTML = getMenuTemplate(type); 	
					}
				});
			}
			
			function getMenuTemplate(type) {
				 			
				let select = 'style="color: transparent"';

				if (self.typeSelected && self.typeSelected.id) {

					select = (type.id != self.typeSelected.id) ? 'style="color: transparent"' : 'style="color: var(--blue-text-default)"';
				}
				
				return `<div id="${'search-menu-type-' + self.uid + "-" + type.id}"><span ${select} class="webix_icon mdi mdi-check"></span>  ${i18n.t(type.label)}</div>`;
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