import { util } from "../../lib/util.js";
import GenericModule from "../genericModule.js";
import MessageBox from "../../component/messageBox.js"
import SearchBox from "../../component/searchBox.js"
import SearchEquipment from "./searchEquipment.js"
import SearchAlarm from "./searchAlarm.js"
import SearchIncident from "./searchIncident.js"

export default class SearchModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.type = SearchBox.TYPE.ALL;
		this.text = undefined;
		this.searchBox = undefined;
		
	}

	loadData() {

		let self = this;
		
		let container = $$(self.args.containerId);
		
		if (self.moduleAction.id === self.config.moduleAction.search) {
			
			searchAction();
		}
		
		function searchAction() {
			
			if (self.args.text && self.args.type) {
				
				drawContent();
				
				onValidate(self.args.text, self.args.type);
			}
		}
		
		function drawContent() {
			
			let viewContent;
			
			viewContent = {
				id: self.uid,
				rows: [
					{ height: 40 },
					function() {
					
						let row;
							
						if (self.moduleAction.id === self.config.moduleAction.search) {
							
							row = {
								cols: [
									{ width: 100 },
									{
										view: "template",
										height: 50,
										width: 200,
										borderless: true,
										template: function(data) {
											
											let logo = "<img style='width: 150px' src='img/open_its_full.png'>";
							
											return `<div style='text-align: left'>${logo}</div>`;
										}
									},
									{
										width: 600,
										css: { "margin-top": "5px !important" },
										rows: [
											
											drawSearchInput()									
										]	
									},
									{ width: 20 },
									{
										id: "label-searching-" + self.uid,
										view: "template",
										borderless: true,
										template: function() {
											
											return `<div class="label-searching"><span>${i18n.t("LBL_SEARCHING")}</span><span class="label-searching_blink"> ...</span></div>`;
										}
									},
									//{ id: "label-searching-" + self.uid, view: "label", css: "label-searching", hidden:true, label: i18n.t("LBL_SEARCHING") + "..." },
									{}
								]
							}
						}
						else {
							
							row = {
								cols: [
									{
										rows: [
											{
												cols: [
													{},
													{
														view: "template",
														height: 100,
														borderless: true,
														template: function(data) {
															
															let logo = "<img style='width: 300px' src='img/open_its_full.png'>";
											
															return `<div style='text-align: center'>${logo}</div>`;
														}
													},		
													{}
												]
											},
											{ height: 20 },
											{
												height: 40,
												cols: [
													{},
													{
														width: 600,
														rows: [
															
															drawSearchInput()									
														]	
													},
													{ width: 20 },
													{ id: "label-searching-" + self.uid, view: "label", hidden:true, label: i18n.t("LBL_SEARCHING") + "..." },
													{}
												]
											}	
										]
									}
								]
							}
						}
						
						return row;
					}(),
					{ height: 20 },
					{
						height: 40,
						cols: [
							{ width: 100 },
							{
								id: "search-label-" + self.uid,
								view: "label",
								css: "label-search",
								hidden: true,
								label: ""		
							},
							{}
						]
					},
					{ 
						id: "search-results-" + self.uid,
						view:"scrollview",
						borderless: true,
						body: function() {
							
							let body = { rows: [] };
						
							if (self.type.id === SearchBox.TYPE.ELEMENT.id || self.type.id === SearchBox.TYPE.ALL.id) {
								
								body.rows.push({ id: "results-elements-" + self.uid, hidden: true });
							}
							
							if (self.type.id === SearchBox.TYPE.ALARM.id || self.type.id === SearchBox.TYPE.ALL.id) {
								
								body.rows.push({ id: "results-alarms-" + self.uid, hidden: true });
							}
							
							if (self.type.id === SearchBox.TYPE.INCIDENT.id || self.type.id === SearchBox.TYPE.ALL.id) {
								
								body.rows.push({ id: "results-incidents-" + self.uid, hidden: true });
							}
							
							body.rows.push({ height: 50 });
							
							return body;	
						}()
	    			}
				]				
			}
			
			webix.ui(viewContent, $$(self.uid));
			
			$$("loading-" + self.uid).hide();
			$$(self.uid).show();
		}
		
		function drawSearchInput() {
			
			self.searchBox = new SearchBox(onValidate, self.args.type);
			
			return self.searchBox.getView();
		}
		
		function onValidate(searchText, searchType) {
			
			if (searchText) {
				
				self.text = util.getStringNormalized(searchText);
				self.type = searchType;
				
				let searchLabel = $$("search-label-" + self.uid);
				
				if (searchLabel) {
					
					self.args.text = self.text;
					
					self.refresh();
					
					searchLabel.hide();
					
					searchLabel.setValue(i18n.t("LBL_SEARCH_RESULTS_FOR") + " \"" + searchText + "\" ...");
					
					if (container) {
			
						util.ui.setTitle(container.config.id, i18n.t("LBL_SEARCH") + ": " + " \"" + searchText + "\" ...");	
					}
				}
				
				drawResults();	
			}
			
			function openModule(moduleActionCfg, args) {

				util.module.openFromJSONConfig(moduleActionCfg, null, args);
			}
			
			function drawResults() {
				
				let readyTypes = {};
				
				initReadyTypes();
				initDrawResults();
				
				let searchingLabel = $$("label-searching-" + self.uid);
				
				if (searchingLabel) {
							
					searchingLabel.show();
					self.searchBox.disable();
				}
				
				if (SearchBox.TYPE.ELEMENT.enabled !== false && (self.type.id === SearchBox.TYPE.ELEMENT.id || self.type.id === SearchBox.TYPE.ALL.id)) {
					
					let searchEquipment = new SearchEquipment(self, openModule);
					
					searchEquipment.drawResults(function() { onReady(SearchBox.TYPE.ELEMENT) });
				}
				
				if (SearchBox.TYPE.ALARM.enabled !== false && (self.type.id === SearchBox.TYPE.ALARM.id || self.type.id === SearchBox.TYPE.ALL.id)) {
					
					let searchAlarm = new SearchAlarm(self, openModule);
					
					searchAlarm.drawResults(function() { onReady(SearchBox.TYPE.ALARM) });
				}
				
				if (SearchBox.TYPE.INCIDENT.enabled !== false && (self.type.id === SearchBox.TYPE.INCIDENT.id || self.type.id === SearchBox.TYPE.ALL.id)) {
					
					let searchIncident = new SearchIncident(self, openModule);
					
					searchIncident.drawResults(function() { onReady(SearchBox.TYPE.INCIDENT) });
				}
				
				function onReady(type) {
			
					let allReady = true;
					let keyTypes = Object.keys(readyTypes);
		
					if (Array.isArray(keyTypes) && keyTypes.length > 0) {
						
						readyTypes[type.id] = true;
		
						keyTypes.forEach(keyType => {
							
							allReady &= readyTypes[keyType];
						});	
					}
					
					let searchLabel = $$("search-label-" + self.uid);
				
					if (searchLabel) {
						
						searchLabel.show();
					}
					
					if (allReady) {
						
						initReadyTypes();
						
						if (searchingLabel) {
							
							searchingLabel.hide();
							self.searchBox.enable();
						}
					}
				}
				
				function initDrawResults() {
					
					if ($$("results-elements-" + self.uid)) {
						
						$$("results-elements-" + self.uid).hide();
					}
					
					if ($$("results-alarms-" + self.uid)) {
						
						$$("results-alarms-" + self.uid).hide();
					}
					
					if ($$("results-incidents-" + self.uid)) {
						
						$$("results-incidents-" + self.uid).hide();
					}
				}
				
				function initReadyTypes() {
		
					readyTypes = {};
					
					if (SearchBox.TYPE.ELEMENT.enabled !== false && (self.type.id === SearchBox.TYPE.ELEMENT.id || self.type.id === SearchBox.TYPE.ALL.id)) {
						
						readyTypes[SearchBox.TYPE.ELEMENT.id] = false;	
					}
					
					if (SearchBox.TYPE.ALARM.enabled !== false && (self.type.id === SearchBox.TYPE.ALARM.id || self.type.id === SearchBox.TYPE.ALL.id)) {
					
						readyTypes[SearchBox.TYPE.ALARM.id] = false;	
					}
					
					if (SearchBox.TYPE.INCIDENT.enabled !== false && (self.type.id === SearchBox.TYPE.INCIDENT.id || self.type.id === SearchBox.TYPE.ALL.id)) {
						
						readyTypes[SearchBox.TYPE.INCIDENT.id] = false;	
					}
				}
			}
		}
	}
}
