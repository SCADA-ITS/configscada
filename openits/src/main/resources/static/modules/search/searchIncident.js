import { util } from "../../lib/util.js";

export default class SearchIncident {

	constructor(searchModule, openModule) {

		this.config = searchModule.config;
		this.uid = searchModule.uid;
		this.text = searchModule.text;
		this.openModule = openModule;
	}
	
	drawResults(onReady) {

		let self = this;				
		let incidents = [];
		
		webix.ajax(appConfig.restUrl + self.config.restEndpoints.incidents).then(function(response) {

			if (response) {

				incidents = response.json();
				
				incidents = filterResults();
				
				drawViewResultsIncidents();
				
				if (onReady) {
					
					onReady();
				}
			}
		});
		
		function drawViewResultsIncidents() {
		
			let viewResultsIncidents; 
			
			viewResultsIncidents = {
			
				id: "results-incidents-" + self.uid,
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
												
												if ($$("results-incidents-rows-" + self.uid)) {
													
													this.collapse ? $$("results-incidents-rows-" + self.uid).hide() : $$("results-incidents-rows-" + self.uid).show(); 
												}
											}
										}
									},
									{ 
										view: "label", 
										css: "label-search-type-result",
										label: i18n.t("LBL_INCIDENTS") + " (" + incidents.length + " " + i18n.t("LBL_RESULTS") + ")"
									}
								] 
							});
							
							rows.push({ height: 20 });
							
							let rowsResult = [];
							
							if (Array.isArray(incidents) && incidents.length > 0) {
				
								incidents.sort((a, b) => (a.incidentType.alias.toLowerCase() > b.incidentType.alias.toLowerCase()) ? 1 : ((b.incidentType.alias.toLowerCase() > a.incidentType.alias.toLowerCase()) ? -1 : 0));

								incidents.forEach(incident => {
									
									rowsResult.push(getRowResult(incident));
								});
							}								
							
							rows.push({ id: "results-incidents-rows-" + self.uid, cols: [{ width: 25 }, { rows: rowsResult }] });
							
							function getRowResult(incident) {
								
								let lbl = incident.incidentType.description + " - " + incident.incidentType.alias + " - " + util.toLocaleString(incident.generatedAt / 1000) + " - " + incident.affectionStretch.alias;
								
								return {
									
									cols: [
										{
											id: "incident-row-selected-" + incident.id + "-" + self.uid,
											view: "template",
											borderless: true,
											width : 32,
											template: `<span style='font-size: 30px; color: transparent' class='mdi mdi-menu-right'></span>`
										},
										{
											view: "template",
											width : 36,
											borderless: true,
											template: function() {
											
												let img = incident.confirm ? self.config.types.incident.confirmed : self.config.types.incident.notConfirmed;
												let confirmed = incident.confirm ? i18n.t("LBL_INCIDENT_CONFIRMED") : i18n.t("LBL_INCIDENT_NO_CONFIRMED");
												
												return `<div><img style='display:block; height:32px; width:auto; margin:auto; padding:1px;' src='${img}' title='${confirmed}' onerror='this.onerror=null;this.src="/img/grid/empty.png"'></div>`;		
											}
										},
										{ width : 15 },
										{
											view: "template",
											borderless: true,
											width : 36,
											template: function() {
												
												let cssCircle = `width: 26px; height: 26px; -webkit-border-radius: 13px; -moz-border-radius: 13px; border-radius: 13px; margin: 5px 0px 0px 5px; background: ${incident.incidentLevel.color ? incident.incidentLevel.color + 'A0' : 'transparent'};`;
											
												return `<div title="${i18n.t(incident.incidentLevel.labelAlias)}" style="${cssCircle}"></div>`;		
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
									
														self.openModule(self.config.types.incident.default, { selectedPks: [incident.id] });	
													}
												},
												onAfterRender: function() {
													
													selectRow(this.getNode(), incident);
												}
											}
										},
										{ width: 50 },
										getViewCommands(incident),
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
			
			if ($$("results-incidents-" + self.uid)) {
				
				webix.ui(viewResultsIncidents, $$("results-incidents-" + self.uid));	
			}
		}
		
		function selectRow(object, incident) {
			
			let rowSelected =$$("incident-row-selected-" + incident.id + "-" + self.uid);
												
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
		
		function getViewCommands(incident) {
			
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
						hidden: !self.config.types.incident.view,
						icon:"mdi mdi-share", 
						label: i18n.t("LBL_SEARCH_LINK_VIEW"), 
						tooltip: i18n.t("LBL_SEARCH_LINK_VIEW_DESCRIPTION"),
						on: {
							onItemClick: function() {

								if (self.openModule) {
									
									self.openModule(self.config.types.incident.view, { selectedPks: [incident.id] });	
								}
							},
							onAfterRender: function() {
								
								selectRow(this.getNode(), incident);
							}
						}
					},
					{ 
						view: "button", 
						width: 120, 
						type:"icon",
						hidden: !self.config.types.incident.confirm,
						disabled: (incident.confirm ? true : false), 
						icon:"mdi mdi-check", 
						label: i18n.t("LBL_SEARCH_LINK_CONFIRM"), 
						tooltip: i18n.t("LBL_SEARCH_LINK_CONFIRM_DESCRIPTION"),
						on: {
							onItemClick: function() {

								if (self.openModule) {
									
									self.openModule(self.config.types.incident.confirm, { selectedPks: [incident.id] });	
								}
							},
							onAfterRender: function() {
								
								selectRow(this.getNode(), incident);
							}
						} 
					},
					{ 
						view: "button", 
						width: 120, 
						type:"icon",
						hidden: !self.config.types.incident.map, 
						icon:"mdi mdi-map-marker", 
						label: i18n.t("LBL_SEARCH_LINK_LOCATE"), 
						tooltip: i18n.t("LBL_SEARCH_LINK_LOCATE_DESCRIPTION"),
						on: {
							onItemClick: function() {

								if (self.openModule) {
									
									self.openModule(self.config.types.incident.map, { selectedPks: [incident.id] });	
								}
							},
							onAfterRender: function() {
								
								selectRow(this.getNode(), incident);
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
			
			if (Array.isArray(incidents)) {
			
				filteredResults = incidents.filter(e => {
						
						let alias = e.incidentType.alias ? util.getStringNormalized(e.incidentType.alias) : undefined;
						let description = e.incidentType.description ? util.getStringNormalized(e.incidentType.description) : undefined;
						let affectionStretch = e.affectionStretch.alias ? util.getStringNormalized(e.affectionStretch.alias) : undefined;
						
						return 	(alias && alias.toLowerCase().includes(self.text)) ||
								(description && description.toLowerCase().includes(self.text)) ||
								(affectionStretch && affectionStretch.toLowerCase().includes(self.text));
						
				});
			}
			
			return filteredResults;
		}
	}	
}
