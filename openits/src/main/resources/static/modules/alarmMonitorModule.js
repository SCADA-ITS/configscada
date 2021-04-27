import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import ListView from "../component/listview.js";
import TreeView from "../component/treeview.js";
import { util } from "../lib/util.js";
import { adapter } from "../lib/adapter.js";

const ELEMENT_TYPE_ALL = "-1";
const ELEMENT_ALL = "-1";
const ALARM_TYPE_AUTO = 1;
const ALARM_TYPE_MANUAL = 2;

const MODE_TYPE = 1;
const MODE_ELEMENTS = 2;

export default class AlarmMonitorModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.idStompSubscriptor = undefined;
		this.dcNoRecognized = [];
		this.dcRecognized = [];

		this.mode = MODE_TYPE;

		this.listView = undefined;
		this.treeView = undefined;
		
		this.lastSelectedListView = [ELEMENT_TYPE_ALL];
		this.lastSelectedTreeView = [ELEMENT_ALL];
		
		this.treeElements = undefined;
		
		this.dtNoRecognized = undefined;
		this.dtRecognized = undefined;
		this.filterAlarmType = undefined;

		this.elementTypeIdSelected = ELEMENT_TYPE_ALL;
		this.elementIdsSelected = [ELEMENT_ALL];

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
									rows: [util.ui.getViewDrawNoData()],
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

//		function getViewDrawNoData() {
//
//			return {
//				view: "template",
//				template: function() {
//
//					let icon = "<img src='img/loading.svg' align='center'>";
//
//					return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
//				}
//			}
//		}

		return view;
	}

	loadData() {

		let self = this;

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();

			let promises = [];

			promises.push(webix.ajax().get(appConfig.restUrl + this.config.restEndpoints.alarms));

			if (self.config.treeElements) {
				
				promises.push(webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.tree, { id: self.config.treeElements })));
			}
			promises.push(webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.grid, { id: self.config.gridNoRecognized })));
			promises.push(webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.grid, { id: self.config.gridRecognized })));

			webix.promise.all(promises).then(function(responses) {

				let i = 0;
				doOnLoadAlarms(responses[i++]);

				if (self.config.treeElements) {
					doOnLoadTreeElements(responses[i++]);

				}
				doOnLoadGrids(responses[i++], responses[i++]);
			});
			
			function doOnLoadAlarms(response) {
				
				if (response != null) {

					let data = response.json();

					if (data && Array.isArray(data)) {

						let flattenedData = [];

						if (data.length > 0) {

							filterElements(data);

							flattenedData = eval("adapter." + self.config.adapter)(data, null);
						}

						loadDataCollections(flattenedData);
					}
				}
			}
			
			function doOnLoadTreeElements(response) {
				
				if (response) {

					self.treeElements = response.json();
				}
			}
			
			function doOnLoadGrids(responseNoRecognizedGrid, responseRecognizedGrid) {
				
				let dataGrids = {};
				
				if (responseNoRecognizedGrid && responseRecognizedGrid) {

					let dataNoRecognizedGrid = responseNoRecognizedGrid.json();
					let dataRecognizedGrid = responseRecognizedGrid.json();
					let metadataNoRecognizedGrid = dataNoRecognizedGrid.metadata;
					let metadataRecognizedGrid = dataRecognizedGrid.metadata;

					filterMetadata(metadataNoRecognizedGrid);
					filterMetadata(metadataRecognizedGrid);

					dataGrids[metadataNoRecognizedGrid.id] = new DataTable(metadataNoRecognizedGrid.id, null, metadataNoRecognizedGrid, [], null);
					dataGrids[metadataRecognizedGrid.id] = new DataTable(metadataRecognizedGrid.id, null, metadataRecognizedGrid, [], null);
					
					load();
				}
				
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
				
				function load() {

					if (dataGrids && dataGrids[self.config.gridNoRecognized] && dataGrids[self.config.gridRecognized]) {
	
						self.dtNoRecognized = dataGrids[self.config.gridNoRecognized];
						self.dtRecognized = dataGrids[self.config.gridRecognized];
	
						drawComponents();
	
						initDataTables();
	
						initListView();
	
						if (self.moduleAction && self.moduleAction.id === self.config.moduleAction.elements && 
							Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

							setMode(MODE_ELEMENTS, self.args.selectedPks);							
							fillGrids(self.args.selectedPks);
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
						
						$$("loading-switcher-" + self.uid).hide();
						$$("switcher-views-" + self.uid).show();
					}
				}
			}
		}
		
		function getSelection() {
			
			let selection = [];
			
			if (self.mode === MODE_TYPE) {
				
				selection = [self.elementTypeIdSelected];
			}
			else if (self.mode === MODE_ELEMENTS) {
				
				selection = self.elementIdsSelected;
			}
			
			return selection; 
		}
		
		function setMode(mode, data) {
			
			if (mode === MODE_TYPE) {
			
				self.elementTypeIdSelected = data;
				self.elementIdsSelected = undefined;
			}
			else if (mode === MODE_ELEMENTS) {
				
				self.elementTypeIdSelected = undefined;
				self.elementIdsSelected = data;
			}
			
			self.mode = mode;
		}

		function filterElements(data) {

			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
				
				data = data.filter(e => self.args.selectedPks.indexOf(e.targetElement.id) !== -1)
				
				setMode(MODE_ELEMENTS, self.args.selectedPks);
			}
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

			if (self.listView && self.treeView) {

				self.treeView.unselect();
				
				setMode(MODE_TYPE, [ELEMENT_TYPE_ALL]);
				
				self.listView.select(self.lastSelectedListView[0]);
			}
		}
		
		function initTreeView() {

			if (self.treeView && self.listView) {

				self.listView.unselect();
				
				setMode(MODE_ELEMENTS, [ELEMENT_ALL]);
				
				self.treeView.select(self.lastSelectedTreeView[0]);
				self.treeView.expand();
			}
		}
		
		function fillGrids(ids, callbackEnable) {
			
			if (callbackEnable) {

				callbackEnable(false);
			}
			
			let noRecognizedAlarms;
			let recognizedAlarms;
			
			filterAlarmType(noRecognizedAlarms, recognizedAlarms);
			
			if (self.mode === MODE_TYPE) {
				
				fillDatatablesFromElementType(noRecognizedAlarms, recognizedAlarms, ids);
			}
			else if (self.mode === MODE_ELEMENTS) {
				
				fillDatatablesFromElements(noRecognizedAlarms, recognizedAlarms, ids);
			}
			
			if (callbackEnable) {

				callbackEnable(true);
			}
			
			function filterAlarmType() {
			
				if (self.dtNoRecognized) {
	
					self.dtNoRecognized.clear();
		
					noRecognizedAlarms = self.dcNoRecognized;
		
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
				}
				
				if (self.dtRecognized) {
	
					self.dtRecognized.clear();
		
					recognizedAlarms = self.dcRecognized;
		
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
				}
			}
			
			function fillDatatablesFromElementType() {

				let elementTypeId;
	
				if (Array.isArray(ids) && ids.length == 1) {
	
					elementTypeId = ids[0];
				}
	
				self.elementTypeIdSelected = elementTypeId;
	
				if (self.dtNoRecognized) {
	
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
	
	
					if (!self.elementTypeIdSelected || self.elementTypeIdSelected === ELEMENT_TYPE_ALL) {
	
						self.dtRecognized.add(recognizedAlarms);
					}
					else {
	
						self.dtRecognized.add(recognizedAlarms.filter(e => {
							return (e.targetElementTypeId == self.elementTypeIdSelected);
						}));
					}
				}
			}
			
			function fillDatatablesFromElements() {
	
				if (Array.isArray(ids) && ids.length > 0) {
	
					self.elementIdsSelected = ids;
					
					if (self.dtNoRecognized) {
	
						if (self.elementIdsSelected.length == 1 &&  self.elementIdsSelected[0] === ELEMENT_TYPE_ALL) {
		
							self.dtNoRecognized.add(noRecognizedAlarms);
						}
						else {
		
							self.dtNoRecognized.add(noRecognizedAlarms.filter(e => {
		
								return (self.elementIdsSelected.indexOf(e.targetElementId) !== -1)
							}));
						}
					}
		
					if (self.dtRecognized) {
		
						if (self.elementIdsSelected.length == 1 &&  self.elementIdsSelected[0] === ELEMENT_TYPE_ALL) {
		
							self.dtRecognized.add(recognizedAlarms);
						}
						else {
		
							self.dtRecognized.add(recognizedAlarms.filter(e => {
								
								return (self.elementIdsSelected.indexOf(e.targetElementId) !== -1)
							}));
						}
					}
				}
			}
		}

		function drawComponents() {

			let viewMain = {
				id: self.uid,
				cols: []
			};

			let rowsviewMainListEquipments = [];
		
			rowsviewMainListEquipments.push({
				id: "loading-switcher-" + self.uid,
				hidden: false,
				cols: [
					util.ui.getViewDrawNoData(true)
				]
			});
				
			if(self.treeElements){
				
				rowsviewMainListEquipments.push(getSwitcherViews());
				rowsviewMainListEquipments.push({ height: 1, borderless: false });
				rowsviewMainListEquipments.push(getSwitcherButtons());
				
			}else{
				rowsviewMainListEquipments.push(getViewListEquipments());
			}
			
			let viewMainListEquipments = {
				id: "view-main-list-equipments-" + self.uid,
				//header: i18n.t("LBL_EQUIPMENT_TYPES"),
				header: function() {

					return `<div>
								<span class='ico webix_icon mdi mdi-view-headline'></span>
								<span>${i18n.t("LBL_EQUIPMENT_TYPES")}</span>
							</div>`;
				},
				body: {
					width: 325,
					rows: rowsviewMainListEquipments
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
				//header: i18n.t("LBL_ALARMS_ACTION_PANEL"),
				header: function() {

					return `<div>
								<span class='ico webix_icon mdi mdi-view-headline'></span>
								<span>${i18n.t("LBL_ALARMS_ACTION_PANEL")}</span>
							</div>`;
				},
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

				view.rows = [];

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

					events[ListView.EventTypes.ON_SELECT_ROW] = function(ids, callbackEnable) {
						
						if (Array.isArray(ids) && ids.length >  0) {
							
							self.lastSelectedListView = ids; 
							
							fillGrids(ids, callbackEnable);	
						}
					};

					self.listView = new ListView(elementTypeView, null, events);

					viewListEquipments = self.listView.getView();
				}

				view.rows.push(viewListEquipments);

				return view;
			}
			
			function getViewTreeEquipments() {

				let view = {};

				let events = {};

				events[TreeView.EventTypes.ON_SELECT_ANY] = function(ids, callbackEnable) { 
					
					if (Array.isArray(ids) && ids.length > 0) {
						
						self.lastSelectedTreeView = ids;
						
						let elementIds = ids.map(e => { return e.split("_")[1] });
					
						fillGrids(elementIds, callbackEnable);
					}
				};
				
				let root = [{
				
					id: ELEMENT_TYPE_ALL,
					value: i18n.t("LBL_ELEMENT_TYPE_ALL"),
					data: self.treeElements.data,
					props: {
        				folder: true
					}	
				}];

				self.treeView = new TreeView(self.treeElements.metadata, root, events);

				view = self.treeView.getView();
				view.hidden = true;

				return view;
			}
			
			function getSwitcherViews() {
				
				return {
					id: "switcher-views-" + self.uid,
					hidden: true,
					cols: [
						{
							rows: [
								getViewListEquipments(), 
								getViewTreeEquipments()	
							]
						}
					]
				};
			}
			
			function getSwitcherButtons() {
				
				let view = {
					css: { "background-color": "var(--blue-soft-opacity-1)" },
					height: 50,
					cols: [
						{ width: 10 },
						{
							rows: [
								{ height: 5 },
								{
									view: "segmented",
									css: "segment-custom-2",
									value: "switcher-element-types-" + self.uid,
									options: [
											{
												id: "switcher-element-types-" + self.uid,
												value: `<div>
															<span class='ico webix_icon mdi mdi-format-list-bulleted'></span>
															<span>${i18n.t("LBL_LIST")}</span>
														</div>`,
											},
											{
												id: "switcher-element-hierarchy-" + self.uid,
												value: `<div>
															<span class='ico webix_icon mdi mdi-family-tree'></span>
															<span>${i18n.t("LBL_TREE")}</span>
														</div>`,
											}
									],
									tooltip: function(obj) {
				
										if (obj.id === "switcher-element-types-" + self.uid) {
				
											return i18n.t("LBL_SELECTION_BY_TYPE");
										}
										else if (obj.id === "switcher-element-hierarchy-" + self.uid) {
				
											return i18n.t("LBL_SELECTION_BY_HIERARCHY");
										}
									},
									on: {
										onChange(newv, oldv) {
				
											console.log("init change => " + newv);
											$$("loading-switcher-" + self.uid).show();
											$$("switcher-views-" + self.uid).hide();
												
											webix.delay(function() {
												
												$$("loading-switcher-" + self.uid).hide();
												$$("switcher-views-" + self.uid).show();
													
												if (newv === "switcher-element-types-" + self.uid) {
					
													$$("listview-" + self.listView.uid).show();
													$$("treeview-" + self.treeView. uid).hide();
													
													$$("view-main-list-equipments-" + self.uid).config.header = webix.template(function() {
									
														return `<div>
																	<span class='ico webix_icon mdi mdi-view-headline'></span>
																	<span>${i18n.t("LBL_EQUIPMENT_TYPES")}</span>
																</div>`;
													});
													$$("view-main-list-equipments-" + self.uid).refresh();
													
													initListView();
												}
												else if (newv === "switcher-element-hierarchy-" + self.uid) {
									
													$$("listview-" + self.listView.uid).hide();
													$$("treeview-" + self.treeView. uid).show();
													
													$$("view-main-list-equipments-" + self.uid).config.header = webix.template(function() {
									
														return `<div>
																	<span class='ico webix_icon mdi mdi-view-headline'></span>
																	<span>${i18n.t("LBL_EQUIPMENT_TREE")}</span>
																</div>`;
													});
													$$("view-main-list-equipments-" + self.uid).refresh();
													 					
													initTreeView();
												}
												
												console.log("end change => " + newv);
											});
										}
									}
								},
								{ height: 5 }	
							]
							
						},
						{ width: 10 }
					]
					
				};
				return view;
			}

			function getViewContent() {

				let viewContent = {};
				let viewContentToolbar = {};
				let viewDtNoRecognized = {};
				let viewDtRecognized = {};
				
				viewContentToolbar = getContentToolbar();

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
								viewContentToolbar,
								{ width: 6 }
							]	
						},
						{ height: 12 },
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
				
				function getContentToolbar() {
					
					let viewContentToolbar = {
						
						id: "viewContentToolbar-" + self.uid,
						view: "toolbar",
						height: 28,
						elements: [],
						borderless: true
					}
					
					let alarmTypeSwitcher = {
							
						view: "segmented",
						css: "segment-custom-2",
						value: "alarm-type-switcher-show-all-" + self.uid,
						width: 250,
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
								}
								else if (newv === "alarm-type-switcher-show-auto-" + self.uid) {
	
									self.filterAlarmType = ALARM_TYPE_AUTO;
								}
								else if (newv === "alarm-type-switcher-show-manual-" + self.uid) {
	
									self.filterAlarmType = ALARM_TYPE_MANUAL;
								}
								
								onSelectAlarmType();
							}
						}
					};
					
					let actionPanelSwitcher = {
						view: "segmented",
						css: "segment-custom-2",
						value: "action-panel-switcher-show-all-" + self.uid,
						width: 200,
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
					
					viewContentToolbar.elements.push(alarmTypeSwitcher);
					viewContentToolbar.elements.push({});
					viewContentToolbar.elements.push(actionPanelSwitcher);
					
					function onSelectAlarmType() {

						fillGrids(getSelection());
					}
					
					return viewContentToolbar;
				}

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
				
				function newAlarm() {

					if (self.config && self.config.action && self.config.action.newAlarm) {
			
						let module = util.module.getFromModuleAction(self.config.action.newAlarm);
						if (module) {
			
							let moduleAction = { id: self.config.action.newAlarm };
			
							let args = { selectedPks: [] };
			
							util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
						}
			
					} else {
			
						util.ui.notImplementedMessage();
					}
				}
			
				function alarmMuted() {
			
					if (self.config && self.config.action && self.config.action.alarmMuted) {
						
						let module = window.em.getByField(EntityType.MODULE, "id", self.config.action.alarmMuted);
			
						if (module) {
			
							util.module.open(module, null, VIEW_TYPE_WINDOW, null, null);
						}
			
					} else {
			
						util.ui.notImplementedMessage();
					}
				}
			
				function newIncident() {
			
					if (self.config && self.config.action && self.config.action.newIncident) {
			
						let module = util.module.getFromModuleAction(self.config.action.newIncident);
						if (module) {
			
							let moduleAction = { id: self.config.action.newIncident };
			
							let args = { selectedPks: [] };
			
							util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
						}
			
					} else {
			
						util.ui.notImplementedMessage();
					}
				}
			
				function alarmConfig() {
			
					if (self.config && self.config.action && self.config.action.alarmConfig) {
			
						let module = window.em.getByField(EntityType.MODULE, "id", self.config.action.alarmConfig);
			
						if (module) {
			
							util.module.open(module, null, VIEW_TYPE_WINDOW, null, null);
						}
			
					} else {
			
						util.ui.notImplementedMessage();
					}
				}

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
									css: { "background": "rgba(236 108 108 / 0.2) !important" },
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
									css: { "background": "rgba(239 132 74 / 0.2) !important" },
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
									css: { "background": "rgba(117 140 168 / 0.2) !important" },
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

											newAlarm();
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

											newIncident();
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

											alarmMuted();
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

											alarmConfig();
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
//						{
//							cols: [
//								{ width: marginActionPanel },
//								actionPanelSwitcher,
//								{ width: marginActionPanel }
//							]
//						},
//						{ height: 30 },
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

				if (Array.isArray(flattenedData) && flattenedData.length > 0) {
					
					flattenedData.forEach(flattenedItem => {
						
						let changes = evaluateChanges(flattenedItem);

						updateDataTables(flattenedItem, changes);
	
						if ($$("view-main-action-panel-" + self.uid)) {
	
							updateStats();
	
							updateLevelStats();
						}	
					});
				}

				function updateDataTables(flattenedItem, changes) {

					let changesNoRec = changes.noRecognized;
					let changesRec = changes.recognized
					
					if (isDataTableUpdatable()) {
					
						if (self.dtNoRecognized) {

							if (changesNoRec.add) {
	
								self.dtNoRecognized.add(flattenedItem);
							}
							else if (changesNoRec.update) {
	
								self.dtNoRecognized.update(flattenedItem);
							}
							else if (changesNoRec.delete) {
	
								self.dtNoRecognized.delete(flattenedItem);
							}
						}
	
						if (self.dtRecognized) {
	
							if (changesRec.add) {
	
								self.dtRecognized.add(flattenedItem);
							}
							else if (changesRec.update) {
	
								self.dtRecognized.update(flattenedItem);
							}
							else if (changesRec.delete) {
	
								self.dtRecognized.delete(flattenedItem);
							}
						}	
					}
					
					function isDataTableUpdatable() {
					
						let isUpdatable = true;
						
						if (isUpdatable && self.filterAlarmType) {
							
							if (self.filterAlarmType === ALARM_TYPE_AUTO) {
								
								isUpdatable &= (self.config.alarmTypeAuto.indexOf(flattenedItem.alarmType) !== -1);
							}
							else if (self.filterAlarmType === ALARM_TYPE_MANUAL) {
								
								isUpdatable &= (self.config.alarmTypeManual.indexOf(flattenedItem.alarmType) !== -1);
							}
						}
											
						if (isUpdatable && self.mode === MODE_TYPE && self.elementTypeIdSelected) {
							
							isUpdatable &= (flattenedItem.targetElementTypeId === self.elementTypeIdSelected || self.elementTypeIdSelected === ELEMENT_TYPE_ALL);		
						}
						else if (isUpdatable && self.mode === MODE_ELEMENTS  && Array.isArray(self.elementIdsSelected) && self.elementIdsSelected.length > 0) {
							
							isUpdatable &= (self.elementIdsSelected.indexOf(flattenedItem.targetElementId) !== -1) || self.elementIdsSelected === [ELEMENT_TYPE_ALL];
						}
						
						return isUpdatable;
					}
				}

				function evaluateChanges(flattenedItem) {

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
					

					return changes;
				}
			}
		}
	}
}

