import { util } from "../../lib/util.js";

export default class SearchAlarm {

	constructor(searchModule, openModule) {

		this.config = searchModule.config;
		this.uid = searchModule.uid;
		this.text = searchModule.text;
		this.openModule = openModule;
	}
	
	drawResults(onReady) {
				
		let self = this;
		let alarms = [];
		
		webix.ajax(appConfig.restUrl + self.config.restEndpoints.alarms).then(function(response) {

			if (response) {

				alarms = response.json();
				
				alarms = filterResults();
				
				drawViewResultsAlarms();
				
				if (onReady) {
					
					onReady();
				}
			}
		});
		
		function drawViewResultsAlarms() {
		
			let viewResultsAlarms; 
			
			viewResultsAlarms = {
			
				id: "results-alarms-" + self.uid,
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
												
												if ($$("results-alarms-rows-" + self.uid)) {
													
													this.collapse ? $$("results-alarms-rows-" + self.uid).hide() : $$("results-alarms-rows-" + self.uid).show(); 
												}
											}
										}
									},
									{ 
										view: "label", 
										css: "label-search-type-result",
										label: i18n.t("LBL_ALARMS") + " (" + alarms.length + " " + i18n.t("LBL_RESULTS") + ")" 
									}
								] 
							});
							
							rows.push({ height: 20 });
							
							let rowsResult = [];
							
							if (Array.isArray(alarms) && alarms.length > 0) {
				
								alarms.sort((a, b) => (i18n.t(a.alarmConfig.labelAlias).toLowerCase() > i18n.t(b.alarmConfig.labelAlias).toLowerCase()) ? 1 : ((i18n.t(b.alarmConfig.labelAlias).toLowerCase() > i18n.t(a.alarmConfig.labelAlias).toLowerCase()) ? -1 : 0));

								alarms.forEach(alarm => {
									
									rowsResult.push(getRowResult(alarm));
								});
							}								
							
							rows.push({ id: "results-alarms-rows-" + self.uid, cols: [{ width: 25 }, { rows: rowsResult }] });
							
							function getRowResult(alarm) {
								
								let lbl = alarm.targetElement.alias + " - " + i18n.t(alarm.alarmConfig.labelAlias) + (!alarm.recognized ? " (" + i18n.t("LBL_ALARM_NO_RECOGNIZED") + ")" : "") +  " - " + util.toLocaleString(alarm.activation / 1000);
								
								return {
									
									cols: [
										{
											id: "alarm-row-selected-" + alarm.id + "-" + self.uid,
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
											
												let img = alarm.deactivation ? self.config.types.alarm.inactive : self.config.types.alarm.active;
												let active = i18n.t(alarm.deactivation ? "LBL_ICON_ALARM_INACTIVE" : "LBL_ICON_ALARM_ACTIVE");
												
												return `<div><img style='display:block; height:32px; width:auto; margin:auto; padding:1px;' src='${img}' title='${active}' onerror='this.onerror=null;this.src="/img/grid/empty.png"'></div>`;		
											}
										},
										{ width : 15 },
										{
											view: "template",
											borderless: true,
											width : 36,
											template: function() {
											
												let img = self.config.types.alarm.levels.info.img;
												let lvl = i18n.t("LBL_ALARM_LEVEL_CLASIF_INFO");
												
												if (self.config.types.alarm.levels.critical.values.indexOf(alarm.alarmConfig.alarmLevel.id) > -1)  {
												
													img = self.config.types.alarm.levels.critical.img;
													lvl = i18n.t("LBL_ALARM_LEVEL_CLASIF_CRITICAL"); 	
												}
												else if (self.config.types.alarm.levels.warn.values.indexOf(alarm.alarmConfig.alarmLevel.id) > -1)  {
													
													img = self.config.types.alarm.levels.warn.img;
													lvl = i18n.t("LBL_ALARM_LEVEL_CLASIF_WARNING");
													
												}
											
												return `<div><img style='display:block; height:32px; width:auto; margin:auto; padding:1px;' title='${lvl}' src='${img}' onerror='this.onerror=null;this.src="/img/grid/empty.png"'></div>`;		
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
									
														self.openModule(self.config.types.alarm.default, { selectedPks: [alarm.id] });	
													}
												},
												onAfterRender: function() {
													
													selectRow(this.getNode(), alarm);
												}
											}
										},
										{ width: 50 },
										getViewCommands(alarm),
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
			
			if ($$("results-alarms-" + self.uid)) {
				
				webix.ui(viewResultsAlarms, $$("results-alarms-" + self.uid));	
			}
		}
		
		function selectRow(object, alarm) {
			
			let rowSelected =$$("alarm-row-selected-" + alarm.id + "-" + self.uid);
												
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
		
		function getViewCommands(alarm) {
			
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
						hidden: !self.config.types.alarm.view,
						icon:"mdi mdi-share", 
						label: i18n.t("LBL_SEARCH_LINK_VIEW"), 
						tooltip: i18n.t("LBL_SEARCH_LINK_VIEW_DESCRIPTION"),
						on: {
							onItemClick: function() {

								if (self.openModule) {
									
									self.openModule(self.config.types.alarm.view, { selectedPks: [alarm.id] });	
								}
							},
							onAfterRender: function() {
								
								selectRow(this.getNode(), alarm);
							}
						} 
					},
					{ 
						view: "button", 
						width: 120, 
						type:"icon",
						hidden: !self.config.types.alarm.recognize,
						disabled: (alarm.recognized ? true : false),
						icon:"mdi mdi-check", 
						label: i18n.t("LBL_SEARCH_LINK_RECOGNIZE"), 
						tooltip: i18n.t("LBL_SEARCH_LINK_RECOGNIZE_DESCRIPTION"),
						on: {
							onItemClick: function() {

								if (self.openModule) {
									
									self.openModule(self.config.types.alarm.recognize, { selectedPks: [alarm.id] });	
								}
							},
							onAfterRender: function() {
								
								selectRow(this.getNode(), alarm);
							}
						} 
					},
					{ 
						view: "button", 
						width: 120, 
						type:"icon",
						hidden: !self.config.types.alarm.equipment, 
						icon:"mdi mdi-engine", 
						label: i18n.t("LBL_SEARCH_LINK_EQUIPMENT_VIEWER"), 
						tooltip: i18n.t("LBL_SEARCH_LINK_EQUIPMENT_VIEWER_DESCRIPTION"),
						on: {
							onItemClick: function() {

								if (self.openModule) {
									
									self.openModule(self.config.types.alarm.equipment, { selectedPks: [alarm.id] });	
								}
							},
							onAfterRender: function() {
								
								selectRow(this.getNode(), alarm);
							}
						} 
					},
					{ 
						view: "button", 
						width: 120, 
						type:"icon",
						hidden: !self.config.types.alarm.map,
						icon:"mdi mdi-map-marker", 
						label: i18n.t("LBL_SEARCH_LINK_LOCATE"), 
						tooltip: i18n.t("LBL_SEARCH_LINK_LOCATE_DESCRIPTION"),
						on: {
							onItemClick: function() {

								if (self.openModule) {
									
									self.openModule(self.config.types.alarm.map, { selectedPks: [alarm.targetElement.id] });	
								}
							},
							onAfterRender: function() {
								
								selectRow(this.getNode(), alarm);
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
			
			if (Array.isArray(alarms)) {
				
				filteredResults = alarms.filter(e => {
					
					let alias = e.alarmConfig.labelAlias ? util.getStringNormalized(i18n.t(e.alarmConfig.labelAlias)) : undefined;
					let element = window.em.getByField(EntityType.ELEMENT, "id", e.targetElement.id);
					let elementAlias;
					let elementTypeLabelAlias;
					
					if (element) {
						
						elementAlias = element.alias ? util.getStringNormalized(element.alias) : undefined;
						elementTypeLabelAlias = element.elementType.labelAlias ? util.getStringNormalized(element.elementType.labelAlias) : undefined;	
					}
					
					return 	(alias && alias.toLowerCase().includes(self.text)) ||
							(elementAlias && elementAlias.toLowerCase().includes(self.text)) ||
							(elementTypeLabelAlias && elementTypeLabelAlias.toLowerCase().includes(self.text));
						
				});	
			}
			
			return filteredResults;
		}
	}
}
