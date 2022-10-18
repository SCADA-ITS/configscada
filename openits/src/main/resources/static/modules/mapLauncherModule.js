import GenericModule from "../modules/genericModule.js";
import OpenMap from "../component/map/openMap.js";
import SearchDialogBox from "../component/searchDialogBox.js";
import Synoptic from "../component/map/synoptic.js";
import { util } from "../lib/util.js";
import MessageBox from "../component/messageBox.js";

const DEBUG_MODE = false;
const IDX_PROMISE_MAPS_ELEMENT = 0;
const IDX_PROMISE_SYNOPTICS_ELEMENT = 1;

export default class MapLauncherModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.stompClient = undefined;
		this.config = module.config;
	}

	getView() {

		return super.getViewCheckModuleActionAndSelectedPks();
	}

	loadData() {

		let self = this;
		let container = $$(self.args.containerId);
		$$("loading-" + this.uid).show();
		$$(this.uid).hide();

		if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
			
			if (self.moduleAction.id === self.config.moduleAction.loadIncidentInMap) {
				
				loadFromIncident();
			}
			else if (self.moduleAction.id === self.config.moduleAction.loadAlarmInMap) {

				loadFromAlarm();
			}				
			else if (self.moduleAction.id === self.config.moduleAction.element) {
				
				loadFromElementDialog();
			}
			else {
				
				loadFromElement();
			}
			
			function loadFromIncident() {
				
				let incident;
				
				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.incident, { id: self.args.selectedPks[0] })).then(function(response) {
					
					if(response != null) {
						
						incident = response.json();
						
						if(incident){

							let mapId = (incident.incidentType.map) ? incident.incidentType.map : self.config.defaultMap;

							incident.map = mapId;
							loadMap(mapId, onLoadMapIncident, null, incident);							
						}
					}					
				});
			}
			
			function loadFromAlarm() {

				let alarm;

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.alarm, { id: self.args.selectedPks[0] })).then(function(alarmResponse) {

					if (alarmResponse != null) {

						alarm = alarmResponse.json();

						if (alarm.targetElement && alarm.targetElement.id) {

							loadFromElementDialog(alarm.targetElement.id);

						} else {

							(new MessageBox(MessageBox.WARN, i18n.t("LBL_MAP_NOT_AVAILABLE_FOR_THIS_ALARM"))).show();

							if (container) {

								if (self.args.viewTypeId === VIEW_TYPE_WINDOW) {

									container.close();

								} else {

									let view = $$(MAIN_TAB_ID);

									if (view) {

										view.removeView(self.args.containerId);
									}
								}
							}
						}
					}
				});
			}
			
			function loadFromElementDialog(fromAlarmElementId) {
				
				let elementId = (fromAlarmElementId) ? fromAlarmElementId : self.args.selectedPks[0];
				let element = window.em.getByField(EntityType.ELEMENT, "id", elementId);
				
				if (container) {
					
					//container.hide();
				}
				
				if (element) {
										
					let promises = [];
		
					promises.push(webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.mapsElement, { id: element.id })));
					promises.push(webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.synopticsElement, { id: element.id })));
		
					webix.promise.all(promises).then(function(responses) {
						
						if (Array.isArray(responses) && responses.length === 2) {
							
							let maps = responses[IDX_PROMISE_MAPS_ELEMENT] ? responses[IDX_PROMISE_MAPS_ELEMENT].json() : undefined;
							let synoptics = responses[IDX_PROMISE_SYNOPTICS_ELEMENT] ? responses[IDX_PROMISE_SYNOPTICS_ELEMENT].json() : undefined;
							let mapSynList = [];
							
							if (maps) {
								
								mapSynList.push(...maps.map(e => {
									
									return {
										id: [IDX_PROMISE_MAPS_ELEMENT, e.metadata.id].join(":"),
										label: i18n.t(e.metadata.label),
										type: i18n.t("LBL_MAP")
									};
								}));
							}
							
							if (synoptics) {
								
								mapSynList.push(...synoptics.map(e => {
									
									return {
										id: [IDX_PROMISE_SYNOPTICS_ELEMENT, e.metadata.id].join(":"),
										label: i18n.t(e.metadata.label),
										type: i18n.t("LBL_SYNOPTIC")
									};
								}));
							}
							
							if (Array.isArray(mapSynList) && mapSynList.length === 1) {
							
								load(mapSynList[0], element);	
							}
							else if (Array.isArray(mapSynList) && mapSynList.length > 1) {
								
								let dialogEntity = webix.copy(SearchDialogBox.CUSTOM);
	
								dialogEntity.data = mapSynList;
								
								dialogEntity.pkField = "id"
								dialogEntity.fields = ["label=LBL_MAP","type=LBL_TYPE"];
								dialogEntity.height = 600;
								dialogEntity.width = 800;
								dialogEntity.sortFieldId = 1;
									
								(new SearchDialogBox(i18n.t("LBL_SELECT_PARAM"), dialogEntity, onAccept)).show();	
							}
							else {
								
								(new MessageBox(MessageBox.WARN, i18n.t("LBL_MAP_NOT_AVAILABLE_FOR") + ": " + element.alias)).show();
								
								if (container) {

									if (self.args.viewTypeId === VIEW_TYPE_WINDOW) {

										container.close();

									} else {

										let view = $$(MAIN_TAB_ID);
										 
										if (view) {

											view.removeView(self.args.containerId);
										}
									}
								}
							}
						}
					});	
				}
				
				function onAccept(selected) {

					if (element && Array.isArray(selected) && selected.length > 0) {

						load(selected[0], element);
					}
					else {

						if (container) {

							if (self.args.viewTypeId === VIEW_TYPE_WINDOW) {

								container.close();

							} else {

								let view = $$(MAIN_TAB_ID);

								if (view) {

									view.removeView(self.args.containerId);
								}
							}
						}
					}
				}
			}

			function loadFromElement() {
				
				let element = window.em.getByField(EntityType.ELEMENT, "id", self.args.selectedPks[0]);

				if (self.moduleAction.id === self.config.moduleAction.map) {
					
					loadMap(element.map, onLoadMapElement, element);

				} else if (self.moduleAction.id === self.config.moduleAction.synoptic) {

					loadSynoptic(element);
				}
			}
		}
		
		function onLoadMapElement(map, container, label, element) {
				
			if (element && map) {
				
				$$("loading-" + self.uid).hide();
				$$(self.uid).show();

				webix.ui(map.getView(function() { map.select(element.id) }), $$(self.uid));
																
				if (container) {
					
					util.ui.setTitle(container.config.id, i18n.t(label));
				}	
			}
		}
		
		function onLoadMapIncident(map, container, label, incident) {
				
			if (incident && map) {
				
				$$("loading-" + self.uid).hide();
				$$(self.uid).show();
				
				webix.ui(map.getView(function() { map.selectIncident(incident.id) }), $$(self.uid));
																
				if (container) {
					
					util.ui.setTitle(container.config.id, i18n.t(label));
				}	
			}
		}
		
		function load(mapSyn, element) {
			
			if (mapSyn && mapSyn.id && mapSyn.id.split(":").length == 2) {
				
				let type = mapSyn.id.split(":")[0];
				let id = mapSyn.id.split(":")[1];
				
				if (container) {
							
					//container.show();
				}
				
				if (type == IDX_PROMISE_MAPS_ELEMENT) {
				
					loadMap(id, onLoadMapElement, element);
				}
				else if (type == IDX_PROMISE_SYNOPTICS_ELEMENT) {
					
					element.synoptic = id;
					
					loadSynoptic(element);
				}
			}
		}
		
		function loadMap(mapId, onLoad, element, incident) {
			
			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.map, { id: mapId })).then(function(response) {

				if (response != null) {

					let responseJson = response.json();

					if (responseJson) {

						let events = {};

						let options = {};

						options[Map.Options.DEBUG_MODE] = DEBUG_MODE;
						
						let w = self.args.width;
						let h = self.args.height;
						let container = $$(self.args.containerId);
						
						if (container && (!w || !h)) {
							
							w = container.$width - 10;
							h = container.$height - 10;
						}

						let map = new OpenMap(responseJson.metadata, responseJson.data, events, w, h, options);
						
						if (element) {
							
							onLoad(map, container, responseJson.metadata.label + " - " + element.alias + " - " + i18n.t(element.elementType.labelAlias), element);	
						}
						else if (incident) {
							
							onLoad(map, container, responseJson.metadata.label + " - " + incident.incidentType.description + " - " + incident.incidentType.alias + " - " + util.toLocaleString(incident.generatedAt / 1000) + " - " + incident.affectionStretch.alias, incident);
						}

					}
				}
			});
		}

		function loadSynoptic(element) {

			if (element && element.synoptic) {

				let synopticId = element.synoptic;

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.synoptic, { id: synopticId })).then(function(response) {

					if (response != null) {

						let responseJson = response.json();

						if (responseJson) {

							let events = {};

							let options = {};

							options[Synoptic.Options.DEBUG_MODE] = DEBUG_MODE;
							options[Synoptic.Options.SELECTED_ELEMENT_ID] = element.id;
							
							let w = self.args.width;
							let h = self.args.height;
							let container = $$(self.args.containerId);
							
							if (container && (!w || !h)) {
								
								w = container.$width - 10;
								h = container.$height - 10;
							}

							let synoptic = new Synoptic(responseJson.metadata, responseJson.data, events, w, h, options);

							if (synoptic) {

								$$("loading-" + self.uid).hide();
								$$(self.uid).show();

								webix.ui(synoptic.getView(), $$(self.uid));
								
								if (container) {
									
									util.ui.setTitle(container.config.id, i18n.t(responseJson.metadata.label) + " - " + element.alias);
								}
							}
						}
					}
				});
			}
		}

	}
}
