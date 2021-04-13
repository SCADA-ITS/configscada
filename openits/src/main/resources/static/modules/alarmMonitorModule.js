import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import ListView from "../component/listview.js";
import { util } from "../lib/util.js";
import { adapter } from "../lib/adapter.js";

const ELEMENT_TYPE_ALL = "-1";
const ALARM_TYPE_AUTO = 1;
const ALARM_TYPE_MANUAL = 2;

export default class AlarmMonitorModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.idStompSubscriptor = undefined;
		this.dcNoRecognized = [];
		this.dcRecognized = [];

		this.listView = undefined;
		this.dtNoRecognized = undefined;
		this.dtRecognized = undefined;
		this.filterAlarmType = undefined;

		this.elementTypeIdSelected = ELEMENT_TYPE_ALL;

		this.config = module.config;
	}

	getView() {

		let view = undefined;

		view = {
			css: "custom_window",
			cols: [
				{
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									id: this.uid,
									rows: [],
									hidden: false
								},
								{
									id: "loading-" + this.uid,
									rows: [getViewDrawNoData()],
									hidden: true
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		};

		function getViewDrawNoData() {

			return {
				view: "template",
				template: function() {

					let icon = "<img src='img/loading.svg' align='center'>";

					return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
				}
			}
		}

		return view;
	}

	loadData() {

		let self = this;

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();
			
			function loadDataTables(load) {
				
				let gridIds = [self.config.gridNoRecognized, self.config.gridRecognized];
				let dataGrids = {};
				let respCount = 0;
				
				gridIds.forEach(gridId => {
					
					webix.ajax().get(appConfig.restUrl + "grids/" + gridId).then(function(dat) {

						if (dat != null) {
		
							let data = dat.json();
							let metadata = data.metadata;
							
							filterMetadata(metadata);
							
							dataGrids[gridId] = new DataTable(gridId, null, metadata, [], null);
						}
						
						if (++respCount === gridIds.length) {
							
							load(dataGrids);
						}
					});
				});
				
				function filterMetadata(metadata) {
					
					if (self.moduleAction && self.moduleAction.id === self.config.moduleAction.elements) {
						
						if (Array.isArray(metadata.gridCommands) && metadata.gridCommands.length > 0) {
							
							metadata.gridCommands = metadata.gridCommands.map(e => {
								
								e.showText = false;
								
								return e;
							});	
						}							

						if (self.args.selectedPks.length === 1) {
							
							if (Array.isArray(metadata.gridFields) && metadata.gridFields.length > 0) {
								
								// Remove fields config in moduleAction like ignoreSourceFields
								metadata.gridFields = metadata.gridFields.filter(e => 
									
									self.config.moduleAction[self.moduleAction.id].ignoreSourceFields.findIndex(sf => sf === e.sourceField) === -1
								);	
							}
							
							if (Array.isArray(metadata.gridCommands) && metadata.gridCommands.length > 0) {
								
								// Remove commands config in moduleAction like ignoreCommandModuleActions
								metadata.gridCommands = metadata.gridCommands.filter(e => 
									
									self.config.moduleAction[self.moduleAction.id].ignoreCommandModuleActions.findIndex(sf => sf === e.moduleAction.id) === -1
								);	
							}
						}
					}
				}
			}

			webix.ajax().get(appConfig.restUrl + this.config.restEndpoint).then(function(dat) {

				if (dat != null) {

					let data = dat.json();

					if (data && Array.isArray(data)) {

						let flattenedData = [];

						if (data.length > 0) {
							
							data = filterElements(data);

							flattenedData = eval("adapter." + self.config.adapter)(data, null);
						}

						loadDataCollections(flattenedData);
					}
					
					loadDataTables(load);
					
					function load(dataGrids) {
						
						if (dataGrids && dataGrids[self.config.gridNoRecognized] && dataGrids[self.config.gridRecognized]) {
							
							self.dtNoRecognized = dataGrids[self.config.gridNoRecognized];
							self.dtRecognized = dataGrids[self.config.gridRecognized];
							
							drawComponents();

							initDataTables();
		
							initListView();
							
							if (self.moduleAction && self.moduleAction.id === self.config.moduleAction.elements) {
							
								fillDatatables();	
							}
							
							connectWS();
		
							$$(self.uid).attachEvent("onDestruct", function() {
		
								let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
		
								if (stompSubscriptorService) {
		
									stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
								}
							});
		
							$$("loading-" + self.uid).hide();
							$$(self.uid).show();
						}
					}
				}
			});
		}
		
		function filterElements(data) {
		
			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
				
				return data.filter(e => self.args.selectedPks.indexOf(e.targetElement.id) !== -1);
			}
			
			return data;
		}
		
		function isMuted(data) {
			
			let timeService = window.sm.getService(Services.TIME_SERVICE);
			
			return (data.mutedUntil && timeService.getDateTime() < (data.mutedUntil.getTime() / 1000));
		}
		
		function loadDataCollections(data) {
			
			self.dcNoRecognized = data.filter(e => {

				return !e.recognized && !isMuted(e);
			});

			self.dcRecognized = data.filter(e => {

				return e.recognized && !isMuted(e);
			});
		}

		function initDataTables() {

			if (self.dtNoRecognized) {

				self.dtNoRecognized.callbacks();
			}

			if (self.dtRecognized) {

				self.dtRecognized.callbacks();
			}
		}

		function initListView() {

			if (self.listView) {

				self.listView.select(ELEMENT_TYPE_ALL);
			}
		}
		
		function fillDatatables(elementTypeArray, listView) {
			
			let elementTypeId;
			
			if (listView) {
			
				listView.disable();	
			}
			
			if (Array.isArray(elementTypeArray) && elementTypeArray.length == 1) {
				
				elementTypeId = elementTypeArray[0];
			}
			
			self.elementTypeIdSelected = elementTypeId;

			if (self.dtNoRecognized) {

				self.dtNoRecognized.clear();
				
				let noRecognizedAlarms = self.dcNoRecognized;
				
				if (self.filterAlarmType) {
					
					if (self.filterAlarmType === ALARM_TYPE_AUTO) {
					
						noRecognizedAlarms = noRecognizedAlarms.filter(e => {
								return (self.config.alarmTypeAuto.indexOf(e.alarmType) !== -1)
						});	
					}
					else if (self.filterAlarmType === ALARM_TYPE_MANUAL) {
						
						noRecognizedAlarms = noRecognizedAlarms.filter(e => {
								return (self.config.alarmTypeManual.indexOf(e.alarmType) !== -1)
						});
					}
				}
				
				if (!self.elementTypeIdSelected || self.elementTypeIdSelected === ELEMENT_TYPE_ALL) {

					self.dtNoRecognized.add(noRecognizedAlarms);
				}
				else {

					self.dtNoRecognized.add(noRecognizedAlarms.filter(e => {
						return (e.targetElementTypeId == self.elementTypeIdSelected);
					}));
				}	
			}

			if (self.dtRecognized) {

				self.dtRecognized.clear();
				
				let recognizedAlarms = self.dcRecognized;
				
				if (self.filterAlarmType) {
					
					if (self.filterAlarmType === ALARM_TYPE_AUTO) {
					
						recognizedAlarms = recognizedAlarms.filter(e => {
								return (self.config.alarmTypeAuto.indexOf(e.alarmType) !== -1)
						});	
					}
					else if (self.filterAlarmType === ALARM_TYPE_MANUAL) {
						
						recognizedAlarms = recognizedAlarms.filter(e => {
								return (self.config.alarmTypeManual.indexOf(e.alarmType) !== -1)
						});
					}
				}

				if (!self.elementTypeIdSelected || self.elementTypeIdSelected === ELEMENT_TYPE_ALL) {

					self.dtRecognized.add(recognizedAlarms);
				}
				else {

					self.dtRecognized.add(recognizedAlarms.filter(e => {
						return (e.targetElementTypeId == self.elementTypeIdSelected);
					}));
				}
			}
			
			if (listView) {
			
				listView.enable();	
			}
		}

		function drawComponents() {

			let viewMain = {
				id: self.uid,
				cols: []
			};
			
			let viewMainListEquipments = {
				id: "view-main-list-equipments-" + self.uid,
				header: i18n.t("LBL_EQUIPMENT_TYPES"),
				body: {
					width: 275,
					rows: [
						{
							cols: [
								getViewListEquipments()
							]
						}
					]
				}
			}; 
			
			let viewMainResizer = { 
				id: "view-main-resizer-" + self.uid,
				view: "resizer" 
			};
			
			let viewMainContent = {
				id: "view-main-content-" + self.uid,
				body: getViewContent()
			};
			
			let viewMainActionPanel = {
				id: "view-main-action-panel-" + self.uid,
				header: i18n.t("LBL_ALARMS_ACTION_PANEL"),
				body: getViewActionPanel()
			};
			
			if (!self.moduleAction) {
				
				viewMain.cols = [viewMainListEquipments, viewMainResizer, viewMainContent, viewMainActionPanel];
			}
			else {
				
				viewMain.cols = [viewMainContent];
			}

			function getViewListEquipments() {

				let view = {};
				
				let viewListEquipments = {};
				let elementTypes = window.em.getAll(EntityType.ELEMENT_TYPE);
				let elementTypesFiltered = undefined;
				let elementTypeView = [];
				
				let alarmTypeSwitcher = {
					view: "segmented",
					css: "segment-custom-2",
					value: "alarm-type-switcher-show-all-" + self.uid,
					options: [
						{
							id: "alarm-type-switcher-show-all-" + self.uid,
							value: i18n.t("LBL_ALL")
						},
						{
							id: "alarm-type-switcher-show-auto-" + self.uid,
							value: i18n.t("LBL_AUTO")
						},
						{
							id: "alarm-type-switcher-show-manual-" + self.uid,
							value: i18n.t("LBL_MANUAL")
						}
					],
					tooltip: function(obj) {

						if (obj.id === "alarm-type-switcher-show-all-" + self.uid) {

							return i18n.t("LBL_SHOW_ALL_ALARM_TYPES");
						}
						else if (obj.id === "alarm-type-switcher-show-auto-" + self.uid) {

							return i18n.t("LBL_SHOW_ALARM_TYPE_AUTO");
						}
						else if (obj.id === "alarm-type-switcher-show-manual-" + self.uid) {

							return i18n.t("LBL_SHOW_ALARM_TYPE_MANUAL");
						}
					},
					on: {
						onChange(newv, oldv) {

							if (newv === "alarm-type-switcher-show-all-" + self.uid) {

								self.filterAlarmType = undefined; 
								onSelectAlarmType();
							}
							else if (newv === "alarm-type-switcher-show-auto-" + self.uid) {

								self.filterAlarmType = ALARM_TYPE_AUTO;
								onSelectAlarmType();
							}
							else if (newv === "alarm-type-switcher-show-manual-" + self.uid) {

								self.filterAlarmType = ALARM_TYPE_MANUAL;
								onSelectAlarmType();	
							}
						}
					}
				};
				
				view.rows = [];
				
				view.rows.push(alarmTypeSwitcher);

				elementTypesFiltered = elementTypes.filter(e => e.visible === true && e.grid);

				if (Array.isArray(elementTypesFiltered) && elementTypesFiltered.length > 0) {

					elementTypesFiltered.forEach(e => {

						elementTypeView.push({

							id: e.id,
							alias: i18n.t(e.labelAlias),
							description: i18n.t(e.labelDescription),
							image: e.elementTypePhoto
						});
					});
				}

				if (Array.isArray(elementTypeView) != undefined && elementTypeView.length > 0) {

					elementTypeView.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));

					elementTypeView.splice(0, 0, {
						id: ELEMENT_TYPE_ALL,
						alias: i18n.t("LBL_ELEMENT_TYPE_ALL"),
						description: i18n.t("LBL_ELEMENT_TYPE_ALL_DESCRIPTION"),
						image: null
					});
					
					let events = {};

					events[ListView.EventTypes.ON_SELECT_ROW] = fillDatatables;
					
					self.listView = new ListView(elementTypeView, null, events);

					viewListEquipments = self.listView.getView();
				}
				
				function onSelectAlarmType() {
					
					let elementTypeArray = [];
					
					elementTypeArray.push(self.elementTypeIdSelected);
					
					fillDatatables(elementTypeArray);
				}
				
				view.rows.push(viewListEquipments);

				return view;
			}

			function getViewContent() {

				let viewContent = {};
				let viewDtNoRecognized = {};
				let viewDtRecognized = {};

				viewDtNoRecognized = {

					id: "viewDtNoRecognized-" + self.uid,
					rows: []
				};

				viewDtRecognized = {

					id: "viewDtRecognized-" + self.uid,
					rows: []
				};

				self.dtNoRecognized.getView().forEach(dtView => {

					viewDtNoRecognized.rows.push(dtView);
				});

				self.dtRecognized.getView().forEach(dtView => {

					viewDtRecognized.rows.push(dtView);
				});

				viewContent = {
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{ id: "viewDtNoRecognizedSpace-" + self.uid, height: 1, hidden: true },
								viewDtNoRecognized,
								{ width: 6 }
							]
						},
						{ id: "resizer-dt-" + self.uid, view: "resizer" },
						{
							cols: [
								{ width: 6 },
								{ id: "viewDtRecognizedSpace-" + self.uid, height: 1, hidden: true },
								viewDtRecognized,
								{ width: 6 }
							]
						},
						{ view: "resizer" },
						{ height: 6 }
					]
				};

				return viewContent;
			}

			function getViewActionPanel() {

				let viewActionPanel = {};
				const marginActionPanel = 15;

				let infiniteSvgView = {
					view: "template",
					id: "infiniteSvgView" + self.uid,
					height: 200,
					template: function() {

						return `<svg viewBox="0 0 200 100" xmlns="http://www.w3.org/2000/svg">
								  <path fill="none" stroke="lightgrey"
								    d="M20,50 C20,-50 180,150 180,50 C180-50 20,150 20,50 z" />
								
								  <circle id="circle-${self.uid}"  r="5" fill="${getRandomColor()}">
								    <animateMotion dur="10s" repeatCount="indefinite"
								      path="M20,50 C20,-50 180,150 180,50 C180-50 20,150 20,50 z" />
								  </circle>
								</svg>`;
					}
				};

				function getRandomColor() {
					var letters = '0123456789ABCDEF';
					var color = '#';
					for (var i = 0; i < 6; i++) {
						color += letters[Math.floor(Math.random() * 16)];
					}
					return color;
				}

				let actionPanelSwitcher = {
					view: "segmented",
					css: "segment-custom-2",
					value: "action-panel-switcher-show-all-" + self.uid,
					options: [
						{
							id: "action-panel-switcher-show-all-" + self.uid,
							value: "<span class='ico webix_icon mdi mdi-dock-window'></span>",
						},
						{
							id: "action-panel-switcher-show-top-" + self.uid,
							value: "<span class='ico webix_icon mdi mdi-dock-top'></span>",
						},
						{
							id: "action-panel-switcher-show-bottom-" + self.uid,
							value: "<span class='ico webix_icon mdi mdi-dock-bottom'></span>",
						}
					],
					tooltip: function(obj) {

						if (obj.id === "action-panel-switcher-show-all-" + self.uid) {

							return i18n.t("LBL_SHOW_ALL_PANELS");
						}
						else if (obj.id === "action-panel-switcher-show-top-" + self.uid) {

							return i18n.t("LBL_SHOW_NO_RECOGNIZED_PANEL");
						}
						else if (obj.id === "action-panel-switcher-show-bottom-" + self.uid) {

							return i18n.t("LBL_SHOW_RECOGNIZED_PANEL");
						}
					},
					on: {
						onChange(newv, oldv) {

							if (newv === "action-panel-switcher-show-all-" + self.uid) {
								$$("viewDtNoRecognized-" + self.uid).show();
								$$("viewDtRecognized-" + self.uid).show();
								$$("viewDtNoRecognizedSpace-" + self.uid).hide();
								$$("viewDtRecognizedSpace-" + self.uid).hide();
								$$("resizer-dt-" + self.uid).show();
								//								$$("viewDtNoRecognized-" + self.uid).config.height = self.uid.config.height / 2; 
								$$("viewDtNoRecognized-" + self.uid).adjust();
								//								$$("viewDtRecognized-" + self.uid).config.height = self.uid.config.height / 2; 
								$$("viewDtRecognized-" + self.uid).adjust();
							}
							else if (newv === "action-panel-switcher-show-top-" + self.uid) {

								$$("viewDtNoRecognized-" + self.uid).show();
								$$("viewDtRecognized-" + self.uid).hide();
								$$("viewDtNoRecognizedSpace-" + self.uid).hide();
								$$("viewDtRecognizedSpace-" + self.uid).show();
								$$("resizer-dt-" + self.uid).hide();
								//								$$("viewDtNoRecognized-" + self.uid).config.height = self.uid.config.height; 
								$$("viewDtNoRecognized-" + self.uid).adjust();
							}
							else if (newv === "action-panel-switcher-show-bottom-" + self.uid) {

								$$("viewDtNoRecognized-" + self.uid).hide();
								$$("viewDtRecognized-" + self.uid).show();
								$$("viewDtNoRecognizedSpace-" + self.uid).show();
								$$("viewDtRecognizedSpace-" + self.uid).hide();
								$$("resizer-dt-" + self.uid).hide();
								//								$$("viewDtRecognized-" + self.uid).config.height = self.uid.config.height; 
								$$("viewDtRecognized-" + self.uid).adjust();
							}
						}
					}
				};

				let actionPanelAlarmStats = {

					rows: [
						{
							height: 34,
							css: "stats-header",
							cols: [
								{ view: "label", label: i18n.t("LBL_ALARMS_STATS_ACTIVE") },
								{ view: "label", label: i18n.t("LBL_ALARMS_STATS_NO_RECOGNIZED") },
								{ view: "label", label: i18n.t("LBL_ALARMS_STATS_MUTED") }
							]
						},
						{
							height: 66,
							css: "stats-content",
							cols: [
								{ view: "label", id: "action-panel-alarm-stat-active-" + self.uid, label: 0 },
								{ view: "label", id: "action-panel-alarm-stat-no-recognized-" + self.uid, label: 0 },
								{ view: "label", id: "action-panel-alarm-stat-muted-" + self.uid, label: 0 }
							]
						}
					]
				}
				
				let actionPanelAlarmLevelStats = {
					rows: [
						{
							css: "stats-badge-content",
							cols: [
								{
									view: "toolbar",
									css: { "background":"rgba(236 108 108 / 0.2) !important" },
									height: 38,
									elements: [
										{
											view: "label",
											width: 35,
											label: `<div><img style="margin-top: 2px; margin-left: 10px; width: 24px; height: 24px;" src="./img/grid/critical.png"></div>`,
										},
										{
											view: "label",
											width: 160,
											label: `<div><span style="color: rgba(236 108 108 / 0.9); font-size: 16px;">${i18n.t("LBL_ALARMS_CRITICAL")}</span></div>`,
										},
										{},
										{
											view: "label",
											css: { "text-align": "right" },
											width: 50,
											id: "alarm-level-stats-critical-" + self.uid,
											label: `<div><span style="color: rgba(236 108 108 / 0.9); padding-right: 10px;">-</span></div>`,
											borderless: true
										},
									],
									borderless: true
								}
							]
						},
						{ height: 20 },
						{
							css: "stats-badge-content",
							cols: [
								{
									view: "toolbar",
									css: { "background":"rgba(239 132 74 / 0.2) !important" },
									height: 38,
									elements: [
										{
											view: "label",
											width: 35,
											label: `<div><img style="margin-top: 2px; margin-left: 10px; width: 24px; height: 24px;" src="./img/grid/warn.png"></div>`,
										},
										{
											view: "label",
											width: 160,
											label: `<div><span style="color: rgba(239 132 74 / 0.9); font-size: 16px;">${i18n.t("LBL_ALARMS_WARNING")}</span></div>`,
										},
										{},
										{
											view: "label",
											css: { "text-align": "right" },
											width: 50,
											id: "alarm-level-stats-warning-" + self.uid,
											label: `<div><span style="color: rgba(239 132 74 / 0.9); padding-right: 10px;">-</span></div>`,
											borderless: true
										}		
									],
									borderless: true
								}
							]
						},
						{ height: 20 },
						{
							css: "stats-badge-content",
							cols: [
								{
									view: "toolbar",
									css: { "background":"rgba(117 140 168 / 0.2) !important" },
									height: 38,
									elements: [
										{
											view: "label",
											width: 35,
											label: `<div><img style="margin-top: 2px; margin-left: 10px; width: 24px; height: 24px;" src="./img/grid/info.png"></div>`,
										},
										{
											view: "label",
											width: 160,
											label: `<div><span style="color: rgba(117 140 168 / 0.9); font-size: 16px;">${i18n.t("LBL_ALARMS_INFO")}</span></div>`,
										},
										{},
										{
											view: "label",
											css: { "text-align": "right" },
											width: 50,
											id: "alarm-level-stats-info-" + self.uid,
											label: `<div><span style="color: rgba(117 140 168 / 0.9); padding-right: 10px;">-</span></div>`,
											borderless: true
										}		
									],
									borderless: true
								}
							]
						}
					]
				}

				let actionPanelActions = {
					height: 200,
					rows: [
						{
							view: "toolbar",
							height: 75,
							css: "tb-actions",
							elements: [
								{
									view: "button",
									width: 135,
									label: i18n.t("LBL_ALARMS_MONITOR_NEW"),
									tooltip: i18n.t("LBL_ALARMS_MONITOR_NEW_DESCRIPTION"),
									type: "iconTop",
									icon: "mdi mdi-plus",
									on: {

										onItemClick() {

											util.ui.notImplementedMessage();
										}
									}
								},
								{},
								{
									view: "button",
									width: 135,
									label: i18n.t("LBL_ALARMS_MONITOR_NEW_INCIDENT"),
									tooltip: i18n.t("LBL_ALARMS_MONITOR_NEW_INCIDENT_DESCRIPTION"),
									type: "iconTop",
									icon: "mdi mdi-alert",
									on: {

										onItemClick() {

											util.ui.notImplementedMessage();
										}
									}
								}
							],
							borderless: true
						},
						{
							view: "toolbar",
							height: 75,
							css: "tb-actions",
							elements: [
								{
									view: "button",
									width: 135,
									label: i18n.t("LBL_ALARMS_MONITOR_VIEW_MUTED"),
									tooltip: i18n.t("LBL_ALARMS_MONITOR_VIEW_MUTED_DESCRIPTION"),
									type: "iconTop",
									icon: "mdi mdi-volume-mute",
									on: {

										onItemClick() {

											util.ui.notImplementedMessage();
										}
									}
								},
								{},
								{
									view: "button",
									width: 135,
									label: i18n.t("LBL_ALARMS_MONITOR_CONFIG"),
									tooltip: i18n.t("LBL_ALARMS_MONITOR_CONFIG_DESCRIPTION"),
									type: "iconTop",
									icon: "mdi mdi-cog",
									on: {

										onItemClick() {

											util.ui.notImplementedMessage();
										}
									}
								}
							],
							borderless: true
						},
					]
				};

				viewActionPanel = {

					id: "viewActionPanel-" + self.uid,
					width: 320,
					css: "panel-action",
					rows: [
						{ height: 20 },
						{
							cols: [
								{ width: marginActionPanel },
								actionPanelSwitcher,
								{ width: marginActionPanel }
							]
						},
						{ height: 30 },
						{
							cols: [
								{ width: marginActionPanel },
								actionPanelAlarmStats,
								{ width: marginActionPanel }
							]
						},
						{ height: 30 },
						{
							cols: [
								{ width: marginActionPanel },
								actionPanelAlarmLevelStats,
								{ width: marginActionPanel }
							]
						},
						{ height: 40 },
						{
							view: "label",
							css: "section",
							label: `<div class="title"><span class="section-text">${i18n.t("LBL_ALARMS_MONITOR_ACTIONS")}</span></div>`,
							borderless: true
						},
						{ height: 20 },
						{
							cols: [
								{ width: marginActionPanel },
								actionPanelActions,		
								{ width: marginActionPanel }
							]
						},
						{},
						//infiniteSvgView,
						{ height: 6 }
					]
				};

				return viewActionPanel;
			}

			webix.ui(viewMain, $$(self.uid));
		}

		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				let fields = undefined;
				
				if (self.moduleAction && self.moduleAction.id === self.config.moduleAction.elements) {
					
					if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
						
						fields = [];
						
						fields.push({
							target: "targetElement.id",
							values: []
						});
						
						self.args.selectedPks.forEach(e => fields[0].values.push(e));
					}
				}

				self.idStompSubscriptor = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(self.config.topic), onMessage, fields);
			}
			
			let updateStats = function() {
				
				let alarmService = window.sm.getService(Services.ALARM_SERVICE);
	
				if (alarmService) {
				
					let stats = alarmService.getData().stats;
		
					if (stats) {
						
						if (stats.activeAlarms) {
							
							$$("action-panel-alarm-stat-active-" + self.uid).setValue(stats.activeAlarms);
						}
						
						if (stats.noRecognizedAlarms) {
						
							$$("action-panel-alarm-stat-no-recognized-" + self.uid).setValue(stats.noRecognizedAlarms);	
						}
						
						if (stats.mutedAlarms) {
						
							$$("action-panel-alarm-stat-muted-" + self.uid).setValue(stats.mutedAlarms);	
						}
					}
				}	
			};
			
			let updateLevelStats = function() {
				
				function updateLevelStatsCritical() {
					
					let levelBadge = $$("alarm-level-stats-critical-" + self.uid);
					
					if (levelBadge) {
						
						let criticalAlarms = [];
						
						criticalAlarms = self.dcNoRecognized.filter(alarm => {
							
							return (self.config.levels.critical.indexOf(alarm.alarmLevelId) > -1); 
						});
						
						criticalAlarms.push(...self.dcRecognized.filter(alarm => {
							
							return (self.config.levels.critical.indexOf(alarm.alarmLevelId) > -1); 
						}));
						
						if (Array.isArray(criticalAlarms)) {
							
							levelBadge.setHTML(`<div><span style="color: rgba(236 108 108 / 0.9); padding-right: 10px;">${criticalAlarms.length}</span></div>`);
						}
					}
				}
				
				function updateLevelStatsWarning() {
					
					let levelBadge = $$("alarm-level-stats-warning-" + self.uid);
					
					if (levelBadge) {
						
						let warningAlarms = [];
						
						warningAlarms = self.dcNoRecognized.filter(alarm => {
							
							return (self.config.levels.warn.indexOf(alarm.alarmLevelId) > -1); 
						});
						
						warningAlarms.push(...self.dcRecognized.filter(alarm => {
							
							return (self.config.levels.warn.indexOf(alarm.alarmLevelId) > -1); 
						}));
						
						if (Array.isArray(warningAlarms)) {
							
							levelBadge.setHTML(`<div><span style="color: rgba(239 132 74 / 0.9);; padding-right: 10px;">${warningAlarms.length}</span></div>`);
						}
					}
				}
				
				function updateLevelStatsInfo() {
					
					let levelBadge = $$("alarm-level-stats-info-" + self.uid);
					
					if (levelBadge) {
						
						let infoAlarms = [];
						
						infoAlarms = self.dcNoRecognized.filter(alarm => {
							
							return (self.config.levels.info.indexOf(alarm.alarmLevelId) > -1); 
						});
						
						infoAlarms.push(...self.dcRecognized.filter(alarm => {
							
							return (self.config.levels.info.indexOf(alarm.alarmLevelId) > -1); 
						}));
						
						if (Array.isArray(infoAlarms)) {
							
							levelBadge.setHTML(`<div><span style="color: rgba(117 140 168 / 0.9); padding-right: 10px;">${infoAlarms.length}</span></div>`);
						}
					}
				}
				
				updateLevelStatsCritical();
				
				updateLevelStatsWarning();
				
				updateLevelStatsInfo();
			}
			
			if ($$("view-main-action-panel-" + self.uid)) {
			
				updateStats();
			
				updateLevelStats();	
			}

			function onMessage(be) {

				let flattenedData = eval("adapter." + self.config.adapter)([be], null);

				if (flattenedData) {

					let changes = updateDataSources(flattenedData);

					updateDataTables(flattenedData, changes);
					
					if ($$("view-main-action-panel-" + self.uid)) {
					
						updateStats();
						
						updateLevelStats();	
					}
				}

				function updateDataTables(data, changes) {

					let changesNoRec = changes.noRecognized;
					let changesRec = changes.recognized

					if (self.dtNoRecognized && (changesNoRec.add || changesNoRec.update || changesNoRec.delete)) {

						if (changesNoRec.add) {

							self.dtNoRecognized.add(data);
						}
						else if (changesNoRec.update) {

							self.dtNoRecognized.update(data);
						}
						else if (changesNoRec.delete) {

							self.dtNoRecognized.delete(data);
						}
					}

					if (self.dtRecognized && (changesRec.add || changesRec.update || changesRec.delete)) {

						if (changesRec.add) {

							self.dtRecognized.add(data);
						}
						else if (changesRec.update) {

							self.dtRecognized.update(data);
						}
						else if (changesRec.delete) {

							self.dtRecognized.delete(data);
						}
					}
				}

				function updateDataSources(data) {

					let changes = {
						noRecognized: {
							add: false,
							update: false,
							delete: false
						},
						recognized: {
							add: false,
							update: false,
							delete: false
						}
					};

					if (Array.isArray(data) && data.length == 1) {

						let flattenedItem = data[0];

						let elementInDataCollectionNoRecognized = self.dcNoRecognized.find(function(e) {

							return e.id === flattenedItem.id;
						}, true);

						let elementInDataCollectionRecognized = self.dcRecognized.find(function(e) {

							return e.id === flattenedItem.id;
						}, true);

						// Receive alarm recognized and desactivated or alarm is muted
						// Actions: Remove from any datacollection
						if ((flattenedItem.recognized && flattenedItem.deactivation) || isMuted(flattenedItem)) {

							let items = 0;

							if (elementInDataCollectionNoRecognized) {

								items = self.dcNoRecognized.length;

								if (items > 0) {

									self.dcNoRecognized = self.dcNoRecognized.filter(e => {

										return (e.id !== elementInDataCollectionNoRecognized.id)
									});

									if (items - self.dcNoRecognized.length > 0) {

										changes.noRecognized.delete = true;
									}
								}
							}

							if (elementInDataCollectionRecognized) {

								items = self.dcRecognized.length;

								if (items > 0) {

									self.dcRecognized = self.dcRecognized.filter(e => {

										return (e.id !== elementInDataCollectionRecognized.id)
									});

									if (items - self.dcRecognized.length > 0) {

										changes.recognized.delete = true;
									}
								}
							}
						}
						else {

							// Receive alarm recognized
							if (flattenedItem.recognized) {

								let items = 0;

								// Recognized alarm exists in no recognized datacollection. 
								// Actions: Remove from this datacollection		
								if (elementInDataCollectionNoRecognized) {

									items = self.dcNoRecognized.length;

									if (items > 0) {

										self.dcNoRecognized = self.dcNoRecognized.filter(e => {

											return (e.id !== elementInDataCollectionNoRecognized.id)
										});

										if (items - self.dcNoRecognized.length > 0) {

											changes.noRecognized.delete = true;
										}
									}
								}

								// Recognized alarm not exists in recognized datacollection. 
								// Actions: Add to this datacollection
								if (!elementInDataCollectionRecognized) {

									self.dcRecognized.push(flattenedItem);

									changes.recognized.add = true;
								}
								// Recognized alarm exists in recognized datacollection. 
								// Actions: Update this datacollection
								else {

									let index = self.dcRecognized.findIndex(e => e.id == elementInDataCollectionRecognized.id);

									if (index > -1) {

										self.dcRecognized[index] = flattenedItem;

										changes.recognized.update = true;
									}
								}
							}
							// Receive alarm not recognized
							else {

								// Reveice alarm not recognized and not exists in no recognized datacollection
								// Actions: Add to this datacollection		
								if (!elementInDataCollectionNoRecognized) {

									self.dcNoRecognized.push(flattenedItem);

									changes.noRecognized.add = true;
								}
								// Reveice alarm not recognized and exists in no recognized datacollection
								// Actions: Update this datacollection
								else {

									let index = self.dcNoRecognized.findIndex(e => e.id == elementInDataCollectionNoRecognized.id);

									if (index > -1) {

										self.dcNoRecognized[index] = flattenedItem;

										changes.noRecognized.update = true;
									}
								}
							}
						}
					}

					return changes;
				}
			}
		}
	}
}
