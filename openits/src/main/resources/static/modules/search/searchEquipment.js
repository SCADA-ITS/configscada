import { util } from "../../lib/util.js";

export default class SearchEquipment {

	constructor(searchModule, openModule) {

		this.config = searchModule.config;
		this.uid = searchModule.uid;
		this.text = searchModule.text;
		this.openModule = openModule;
	}
	
	drawResults(onReady) {
		
		let self = this;		
		let elementsData = window.em.getAll(EntityType.ELEMENT);
		let elements = webix.copy(filterResults());
		
		if (self.config.restEndpoints.elements) {
			
			let elementIds = [];
			
			if (Array.isArray(elements) && elements.length > 0) {
				
				elementIds = elements.map(e => {
				
					return e.id;
				});
				
				webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.elements, JSON.stringify(elementIds)).then(function(response) {
					
					if (response) {

						let elementsWithState = response.json();
						
						if (Array.isArray(elementsWithState) && elementsWithState.length > 0 && elementsWithState.length === elements.length) {
							
							elementsWithState.forEach(elementWithState => {
						
								let elem = elements.find(e => e.id === elementWithState.id);
								
								if (elem) {
									
									elem.elementTypeState = webix.copy(elementWithState.elementTypeState);
								}		
							});
						}
						
						drawViewResultsElements();
						
						if (onReady) {
			
							onReady();
						}
					}
				});	
			}
			else {
	
				drawViewResultsElements();
			
				if (onReady) {
				
					onReady();
				}				
			}
		}
		else {
			
			drawViewResultsElements();
			
			if (onReady) {
			
				onReady();
			}
		}
		
		function drawViewResultsElements() {
		
			let viewResultsEquipments;
			
			viewResultsEquipments = {
			
				id: "results-elements-" + self.uid,
				borderless: true,
				hidden: false,
				cols: [
					{ width: 100 },
					{
						rows: function() {
							
							let rows = [];
							
							rows.push({ height: 30 });
							
							rows.push({
								height: 30, 
								cols: [
									{
										view: "icon",
										icon: "mdi mdi-menu-down",
										width: 30,
										collapse: false,
										on: {
											onItemClick: function() {
												
												this.collapse = !this.collapse;
												
												this.define("icon", this.collapse ? "mdi mdi-menu-right" : "mdi mdi-menu-down");
												this.refresh();
												
												if ($$("results-elements-rows-" + self.uid)) {
													
													this.collapse ? $$("results-elements-rows-" + self.uid).hide() : $$("results-elements-rows-" + self.uid).show(); 
												}
											}
										}
									},
									{ 
										view: "label", 
										css: "label-search-type-result",
										label: i18n.t("LBL_EQUIPMENTS") + " (" + elements.length + " " + i18n.t("LBL_RESULTS") + ")" 
									}
								] 
							});
							
							rows.push({ height: 20 });
							
							let rowsResult = [];
							
							if (Array.isArray(elements) && elements.length > 0) {
				
								elements.sort((a, b) => (a.alias.toLowerCase() > b.alias.toLowerCase()) ? 1 : ((b.alias.toLowerCase() > a.alias.toLowerCase()) ? -1 : 0));
	
								elements.forEach(element => {
									
									rowsResult.push(getRowResult(element));
								});
							}								
							
							rows.push({ id: "results-elements-rows-" + self.uid, cols: [{ width: 25 }, { rows: rowsResult }] });
							
							function getRowResult(element) {
								
								let lbl = element.alias + " - " + i18n.t(element.elementType.labelAlias);
								
								return {
									
									cols: [
										{
											id: "element-row-selected-" + element.id + "-" + self.uid,
											view: "template",
											borderless: true,
											width : 32,
											template: `<span style='font-size: 30px; color: transparent' class='mdi mdi-menu-right'></span>`
										},
										{
											view: "template",
											borderless: true,
											width : 36,
											template: function() {
												
												let cssCircle = `width: 26px; height: 26px; -webkit-border-radius: 13px; -moz-border-radius: 13px; border-radius: 13px; margin: 5px 0px 0px 5px; background: ${util.element.getColor(element.elementTypeState) + 'A0'};`;
											
												return `<div title="${i18n.t(element.elementTypeState.labelAlias)}" style="${cssCircle}"></div>`;		
											}
										},
										{ width : 15 },
										{
											view: "template",
											borderless: true,
											width : 36,
											template: function() {
											
												return `<div title="${i18n.t(element.elementType.labelAlias)}"><img style='display:block; height:32px; width:auto; margin:auto; padding:1px;' src='${"img/" + element.elementType.elementTypePhoto}' onerror='this.onerror=null;this.src="./img/not_found_50.png"'></div>`;		
											}
										},
										{ width : 15 },
										{
											view: "label",
											label: lbl,
											tooltip: lbl,
											css: "label-search-result",
											on: {
												onItemClick: function(id) {
					
													if (self.openModule) {
										
														self.openModule(self.config.types.element.default, { selectedPks: [element.id] });	
													}
												},
												onAfterRender: function() {
													
													selectRow(this.getNode(), element);
												}
											}
										},
										{ width: 50 },
										getViewCommands(element),
										{ width: 1 }
									]
								}
							}
							
							return rows;
						}()
					},
					{ width: 100 }
				] 
			}
			
			if ($$("results-elements-" + self.uid)) {
					
				webix.ui(viewResultsEquipments, $$("results-elements-" + self.uid));	
			}
		}
		
		function selectRow(object, element) {
			
			let rowSelected =$$("element-row-selected-" + element.id + "-" + self.uid);
												
			if (rowSelected) {
				
				object.addEventListener("mouseover", function() {
																				
					rowSelected.define("template", `<span style='font-size: 30px; color: var(--blue-text-default)' class='mdi mdi-menu-right'></span>`);
					rowSelected.refresh();
					 
				});	
				
				object.addEventListener("mouseout", function() { 
					
					rowSelected.define("template", `<span style='font-size: 30px; color: transparent' class='mdi mdi-menu-right'></span>`);
					rowSelected.refresh();
				});	
			}
		}
		
		function getViewCommands(element) {
			
			let viewCommands;
			
			viewCommands = {
				view: "toolbar",
				width: 600,
				borderless: true,
				css: "search-links-toolbar",
				elements: [
					{ 
						view: "button", 
						width: 120, 
						type:"icon",
						hidden: !self.config.types.element.view, 
						icon:"mdi mdi-share", 
						label: i18n.t("LBL_SEARCH_LINK_VIEW"), 
						tooltip: i18n.t("LBL_SEARCH_LINK_VIEW_DESCRIPTION"),
						on: {
							onItemClick: function() {

								if (self.openModule) {
									
									self.openModule(self.config.types.element.view, { selectedPks: [element.id] });	
								}
							},
							onAfterRender: function() {
								
								selectRow(this.getNode(), element);
							}
						}
					},
					{ 
						view: "button", 
						width: 120, 
						type:"icon",
						hidden: !self.config.types.element.map, 
						icon:"mdi mdi-map-marker", 
						label: i18n.t("LBL_SEARCH_LINK_LOCATE"), 
						tooltip: i18n.t("LBL_SEARCH_LINK_LOCATE_DESCRIPTION"),
						on: {
							onItemClick: function() {

								if (self.openModule) {
									
									self.openModule(self.config.types.element.map, { selectedPks: [element.id] });	
								}
							},
							onAfterRender: function() {
								
								selectRow(this.getNode(), element);
							}
						}
					},
					{}
				]
			}
			
			return viewCommands;
		}
		
		function filterResults() {
			
			let filteredResults = [];
			
			if (Array.isArray(elementsData)) {
				
				filteredResults = elementsData.filter(e => {
				
					let alias = e.alias ? util.getStringNormalized(e.alias) : undefined;
					let description = e.description ? util.getStringNormalized(e.description) : undefined;
					let elementTypeLabelAlias = e.elementType.labelAlias ? util.getStringNormalized(i18n.t(e.elementType.labelAlias)) : undefined; 
				
	
					return 	(alias && alias.includes(self.text)) ||
							(description && description.includes(self.text)) ||
							(elementTypeLabelAlias && elementTypeLabelAlias.includes(self.text));
				});	
			}
			
			return filteredResults;
		}
	}
}
