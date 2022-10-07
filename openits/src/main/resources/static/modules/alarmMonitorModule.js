import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import ListView from "../component/listview.js";
import TreeView from "../component/treeview.js";
import Queue from "../component/queue.js";
import { util } from "../lib/util.js";
import { adapter } from "../lib/adapter.js";
import SearchDialogBox from "../component/searchDialogBox.js";

const ELEMENT_TYPE_ALL = "-1";
const ELEMENT_ALL = "-1";
const ALARM_TYPE_AUTO = 1;
const ALARM_TYPE_MANUAL = 2;

const MODE_TYPE = 1;
const MODE_ELEMENTS = 2;

const TIME_SLEEP_PROCESS_MESSAGES_MS = 100;

const TOPIC_ELEMENTS = "/topic/element.>";

export default class AlarmMonitorModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.idStompSubscriptor = undefined;
		this.idStompSubscriptorTreeState = undefined;
		this.dcNoRecognized = [];
		this.dcRecognized = [];

		this.mode = MODE_TYPE;

		this.listView = undefined;
		this.treeView = undefined;
		
		this.lastSelectedListView = [ELEMENT_TYPE_ALL];
		this.lastSelectedTreeView = [ELEMENT_ALL];
		
		this.elements = undefined;
		this.treeElements = undefined;
		this.elementHierarchies = undefined;
		this.treeCustom = undefined;
		this.treeViewsArr = [];
		
		this.dtNoRecognized = undefined;
		this.dtRecognized = undefined;
		this.filterAlarmType = undefined;
		this.tableEquipmentSelected = undefined;

		this.elementTypeIdSelected = ELEMENT_TYPE_ALL;
		this.elementIdsSelected = [ELEMENT_ALL];

		this.config = module.config;
		
		this.intervalAlarmMonitor = undefined;
		this.queue = new Queue();
		this.idCallbackStats = undefined;
		this.editAlarmConfigsPermission = util.module.isAllowedPermission(this.config.requiredPermissions.edit);
		
		this.alarmStatActive = undefined;
		this.alarmStatNoRecognized = undefined;
		this.alarmStatFiltered = undefined;
	}

	loadData() {

		let self = this;

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();

			let promises = [];

			promises.push(webix.ajax().get(appConfig.restUrl + this.config.restEndpoints.alarms));

			if (self.config.treeElements || self.config.treeCustom) {

				if (self.config.treeElements) {

					promises.push(webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.elementsHierarchy));
				}

				if (self.config.treeCustom) {

					let userData = util.user.getUserData();
					promises.push(webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsTreeByUser, { id: userData.id })));
				}
			}
			
			promises.push(webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.grid, { id: self.config.gridNoRecognized })));
			promises.push(webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.grid, { id: self.config.gridRecognized })));

			webix.promise.all(promises).then(function(responses) {

				let i = 0;
				doOnLoadAlarms(responses[i++]);

				if (self.config.treeElements) {

					doOnLoadTreeElements(responses[i++]);
				}

				if (self.config.treeCustom) {
					
					doOnLoadTreeCustoms(responses[i++]);
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
					
					let alarmService = window.sm.getService(Services.ALARM_SERVICE);

					if (alarmService) {
						
						self.idCallbackStats = alarmService.register(updateStats);
					}
				}
			}
			
			function doOnLoadTreeElements(responseHierarchies) {

				if (responseHierarchies) {

					self.elementHierarchies = responseHierarchies.json();
				}
			}

			function doOnLoadTreeCustoms(responseTreeCustoms) {

				if (responseTreeCustoms) {

					self.treeCustom = responseTreeCustoms.json();
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
					
					let alarmService = window.sm.getService(Services.ALARM_SERVICE);

					if (alarmService) {
						
						updateStats(alarmService.getData());
					}
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

						webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.elements, JSON.stringify([])).then(function(elementsResponse) {

							if (elementsResponse != null) {

								self.elements = elementsResponse.json();
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

								$$(self.uid).attachEvent("onDestruct", onDestruct);

								$$("loading-" + self.uid).hide();
								$$(self.uid).show();

								$$("loading-switcher-" + self.uid).hide();

								if ($$("switcher-views-" + self.uid)) {

									$$("switcher-views-" + self.uid).show();
								}
							}
						});
					}
				}
				
				function onDestruct() {
					
					let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
					if (stompSubscriptorService) {

						stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
						stompSubscriptorService.unsubscribe(self.idStompSubscriptorTreeState);
					}
					
					if (self.intervalAlarmMonitor) {
			
						clearInterval(self.intervalAlarmMonitor);	
					}
					
					if (self.idCallbackStats) {
						
						let alarmService = window.sm.getService(Services.ALARM_SERVICE);

						if (alarmService) {
							
							alarmService.unregister(self.idCallbackStats);
						}	
					}
				}
			}
		}
		
		function updateStats(data) {

			if (data && data.stats) {
				
				let stats = data.stats;
	
				if (stats.activeAlarms && self.alarmStatActive) {

					self.alarmStatActive.setValue(stats.activeAlarms);
				}

				if (stats.noRecognizedAlarms && self.alarmStatNoRecognized) {

					self.alarmStatNoRecognized.setValue(stats.noRecognizedAlarms);
				}

				if (stats.filteredAlarms && self.alarmStatFiltered) {

					self.alarmStatFiltered.setValue(stats.filteredAlarms);
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

		function isFiltered(data) {

			let timeService = window.sm.getService(Services.TIME_SERVICE);

			return (data.filteredUntil && timeService.getEpoch() < data.filteredUntil.getTime());
		}

		function loadDataCollections(data) {
			
			self.dcNoRecognized = data.filter(e => {

				return !e.recognized && !isFiltered(e);
			});

			self.dcRecognized = data.filter(e => {

				return e.recognized && !isFiltered(e) && !e.deactivation;
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

				if (Array.isArray(self.treeViewsArr) && self.treeViewsArr.length > 0) {

					for (let treeView of self.treeViewsArr) {

						treeView.unselect();
					}
				}
				
				setMode(MODE_TYPE, [ELEMENT_TYPE_ALL]);
				
				self.listView.select(self.lastSelectedListView[0]);
			}
		}
		
		function initTreeView() {

			if (Array.isArray(self.treeViewsArr) && self.treeViewsArr.length > 0 && self.listView) {

				self.listView.unselect();
				
				setMode(MODE_ELEMENTS, [ELEMENT_ALL]);
				
//				treeView.select(self.lastSelectedTreeView[0]);
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
					util.ui.getViewDrawLoading(true)
				]
			});
				
			if (Array.isArray(self.elementHierarchies) && self.elementHierarchies.length > 0 || Array.isArray(self.treeCustom) && self.treeCustom.length > 0) {
				
				rowsviewMainListEquipments.push(getSwitcherViews());
				rowsviewMainListEquipments.push({ height: 1, borderless: false });
				rowsviewMainListEquipments.push(getSwitcherButtons());
				
			} else {
				
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

			let viewMainResizerLeft = {
				id: "view-main-resizer-left" + self.uid,
				view: "resizer"
			};
			
			let viewMainResizerRight = {
				id: "view-main-resizer-right" + self.uid,
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

				viewMain.cols = [viewMainListEquipments, viewMainResizerLeft, viewMainContent, viewMainResizerRight, viewMainActionPanel];
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
					
					let options = {}
					
					options[ListView.Options.MODE] = ListView.DrawMode.DEFAULT;
					
					self.listView = new ListView(elementTypeView, options, events);

					viewListEquipments = self.listView.getView();
				}

				view.rows.push(viewListEquipments);

				return view;
			}
			
			function getViewTreeEquipments(treeCustom) {

				let treeView = {};
				let events = {};
				let itemsTree = [];
				let itemRelationships = [];
				let elements = self.elements;
				let hierarchies = self.elementHierarchies;
				let isTreeCustom = (treeCustom) ? true : false;
				
				if (isTreeCustom) {

					elements = treeCustom.elementTreeNodes;
					hierarchies = webix.copy(treeCustom.elementTreeNodeHierarchies);
				}

				events[TreeView.EventTypes.ON_SELECT_ANY] = function(items, callbackEnable) { 
					
					if (Array.isArray(items) && items.length > 0) {
						
						self.lastSelectedTreeView = items.map(e => e.id);
						
						fillGrids(items.map(e => e.id_), callbackEnable);
					}
				};
								
				if (Array.isArray(elements) && elements.length > 0) {

					itemsTree = elements.map(el => {

						if (isTreeCustom) {

							let elementTreeNode = {};

							if (!el.element) {

								elementTreeNode = {
									id: el.id,
									label: el.labelAlias,
									folder: true
								};

							} else {

								let element = self.elements.find(e => e.id === el.element.id);

								if (element) {

									elementTreeNode = {
										id: element.id,
										label: element.alias,
										image: element.elementType.elementTypePhoto,
										stateColor: util.element.getColor({ id: element.elementTypeState.id }),
										stateLabel: i18n.t(element.elementTypeState.labelAlias),
									};

									if (hierarchies) {

										let childHierarchy = hierarchies.find(h => h.child.id === el.id);

										if (childHierarchy) {

											childHierarchy.child = element;
											childHierarchy.child.id = element.id;
										}
									}
								}
							}

							return elementTreeNode;

						} else {

							return {
								id: el.id,
								label: el.alias + " (" + i18n.t(el.elementType.labelAlias) + ")",
								image: el.elementType.elementTypePhoto,
								stateColor: util.element.getColor({ id: el.elementTypeState.id }),
								stateLabel: i18n.t(el.elementTypeState.labelAlias),
							}
						}
					});

					//					itemsTree = elements.map(e => {
					//
					//						return {
					//
					//							id: e.id,
					//							label: e.alias + " (" + i18n.t(e.elementType.labelAlias) + ")",
					//							image: e.elementType.elementTypePhoto
					//						}
					//					});
				}

				if (Array.isArray(hierarchies) && hierarchies.length > 0) {

					itemRelationships = hierarchies.map(e => {

						return {

							id: (e.id) ? e.id : webix.uid(),
							nodeParentId: e.parent.id,
							nodeChildId: e.child.id
						}
					});
				}
				
				let treeData = util.tree.getNodes(itemsTree, itemRelationships, { parentNodeId: "nodeParentId", childNodeId: "nodeChildId" }, false);

				let root = [{
				
					id: ELEMENT_TYPE_ALL,
					value: i18n.t("LBL_ELEMENT_TYPE_ALL"),
					data: treeData,
					props: {
        				folder: true
					}	
				}];
				
				let metadata = {
					id: -1,
					label: "LBL_EQUIPMENTS",
					treeOptionValues: [
						{ value: false, treeOption: { id: 2 } },
						{ value: true, treeOption: { id: 3 } },
						{ value: true, treeOption: { id: 4 } },
						{ value: true, treeOption: { id: 5 } },
						{ value: true, treeOption: { id: 6 } },
						{ value: false, treeOption: { id: 8 } }
					],
					treeCommands: []
				}

				//self.treeView = new TreeView(self.treeElements.metadata, root, events);
				let tree = new TreeView(metadata, root, events);
				treeView = tree.getView();
				treeView.hidden = true;
				self.treeViewsArr.push(tree);

				return treeView;
			}
			
			function getSwitcherViews() {
				
				let switcher;
				
				switcher = {
					id: "switcher-views-" + self.uid,
					hidden: true,
					cols: [
						{
							rows: [
								getViewListEquipments()	
							]
						}
					]
				};
				
				if (Array.isArray(self.elementHierarchies) && self.elementHierarchies.length > 0) {

					let tree = getViewTreeEquipments();

					switcher.cols[0].rows.push(tree);
				}

				if (Array.isArray(self.treeCustom) && self.treeCustom.length > 0) {

					for (let treeCustom of self.treeCustom) {

						let tree = getViewTreeEquipments(treeCustom);

						switcher.cols[0].rows.push(tree);
					}
				}
				
				return switcher;
			}
			
			function getSwitcherButtons() {

				let switcherButtonsView;
				let withTreeElements = undefined;

				let options = [
					{
						id: "switcher-element-types-" + self.uid,
						value: `<div id='${"switcher-element-types-" + self.uid}' style='text-align: center; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;'>
									<span class='ico webix_icon mdi mdi-format-list-bulleted'></span>
									<span>${i18n.t("LBL_LIST")}</span>
								</div>`
					}
				];

				if (Array.isArray(self.elementHierarchies) && self.elementHierarchies.length > 0) {

					options.push({
						id: "ElementTree:0-" + self.uid + "_" + self.treeViewsArr[0].uid,
						value: `<div id='${"ElementTree-0-" + self.uid + "_" + self.treeViewsArr[0].uid}' style='text-align: center; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;'>
									<span class='ico webix_icon mdi mdi-family-tree'></span>
									<span>${i18n.t("LBL_TREE")}</span>
								</div>`
					});

					withTreeElements = true;
				}

				if (Array.isArray(self.treeCustom) && self.treeCustom.length > 0) {

					let idx = withTreeElements ? 1 : 0;

					for (let i = idx; i <= self.treeCustom.length; i++) {

						if (self.treeCustom[i - idx]) {

							let idHTML = self.treeCustom[i - idx].id.replace(":", "-");

							options.push({
								id: self.treeCustom[i - idx].id + "-" + self.uid + "_" + self.treeViewsArr[i].uid,
								value: `<div id='${idHTML + "-" + self.uid + "_" + self.treeViewsArr[i].uid}' title='${i18n.t(self.treeCustom[i - idx].labelAlias)}' style='text-align: center; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;'>
									   <span class='ico webix_icon mdi mdi-family-tree'></span>
									   <span>${i18n.t(self.treeCustom[i - idx].labelAlias)}</span>
								    </div>`
							});
						}
					}
				}

				switcherButtonsView = {
					css: { "background-color": "var(--blue-soft-opacity-1)" },
					height: 50,
					cols: [
						{
							id: "previous-icon-" + self.uid,
							width: 30,
							view: "icon",
							icon: "mdi mdi-play",
							css: "carousel-button-transform carousel-button-disabled",
							disabled: true,
							on: {
								onItemClick: function() {

									let switcher = $$("switcher-" + self.uid);

									if (switcher) {

										let actualPosition = switcher.getScrollState().x;
										switcher.scrollTo(actualPosition - 100, 0);
									}
								}
							}
						},
						{
							rows: [

								{
									id: "switcher-" + self.uid,
									view: "scrollview",
									css: "equipment-carousel",
									height: 41,
									scroll: "x",
									layout: "x",
									borderless: true,
									body: {
										cols: [

											{
												id: "carousel-list-" + self.uid,
												view: "list",
												css: "carousel-list",
												data: options,
												layout: "x",
												select: true,
												type: {
													width: 175

												},
												on: {
													onItemClick: function(id) {

														const VIEWS = {
															TABLE: 0,
															EQUIPMENT: 1
														};

														let HTMLelement;

														$$("loading-switcher-" + self.uid).show();
														$$("switcher-views-" + self.uid).hide();

														webix.delay(function() {

															$$("switcher-views-" + self.uid).show();
															$$("loading-switcher-" + self.uid).hide();

															let list = $$("carousel-list-" + self.uid);
															let nextIcon = $$("next-icon-" + self.uid);
															let prevIcon = $$("previous-icon-" + self.uid);

															if (id === "switcher-element-types-" + self.uid) {

																HTMLelement = $$("carousel-list-" + self.uid).getNode().querySelector('#' + id);

																$$("listview-" + self.listView.uid).show();
																$$("view-main-resizer-left" + self.uid).enable();

																for (let treeIds of self.treeViewsArr) {

																	$$("treeview-" + treeIds.uid).hide();
																}

																$$("view-main-list-equipments-" + self.uid).config.header = webix.template(function() {

																	return `<div>
																			  <span class='ico webix_icon mdi mdi-view-headline'></span>
																			  <span>${i18n.t("LBL_EQUIPMENT_TYPES")}</span>
																			</div>`;
																});

																$$("view-main-list-equipments-" + self.uid).refresh();

																initListView();
																HTMLelement.scrollIntoView({ inline: "center", block: "end", behavior: "smooth" });
															}

															if (id.startsWith("ElementTree:")) {

																let idHTML = id.replace(":", "-");
																let newTreeId = id.split("_")[1];
																let treeView = self.treeViewsArr.find(e => e.uid == newTreeId);
																HTMLelement = $$("carousel-list-" + self.uid).getNode().querySelector('#' + idHTML);

																for (let treeIds of self.treeViewsArr) {

																	$$("treeview-" + treeIds.uid).hide();
																}

																$$("treeview-" + newTreeId).show();
																$$("listview-" + self.listView.uid).hide();
																$$("view-main-resizer-left" + self.uid).disable();

																$$("view-main-list-equipments-" + self.uid).config.header = webix.template(function() {

																	return `<div>
																			  <span class='ico webix_icon mdi mdi-view-headline'></span>
																			  <span>${i18n.t("LBL_EQUIPMENT_TREE")}</span>
																			</div>`;
																});

																$$("view-main-list-equipments-" + self.uid).refresh();

																let itemSelected = treeView.getSelected();
																let element = undefined;																
																
																if (itemSelected[0] && itemSelected[0].id_) {

																	element = window.em.getByField(EntityType.ELEMENT, "id", itemSelected[0].id_);
																}

																if (Array.isArray(itemSelected) && itemSelected.length > 0 && element != undefined) {

																	treeView.unselectAll();
																	treeView.select(itemSelected[0].id);

																} else {

																	initTreeView();
																	let firstItem = treeView.getFirstItem();
																	treeView.select(firstItem.id);
																}

																initTreeView();
																HTMLelement.scrollIntoView({ inline: "center", block: "end", behavior: "smooth" });
															}

															if (nextIcon && prevIcon) {

																let firstItemId = list.getFirstId();
																let lastItemId = list.getLastId();
																let selectedItemId = list.getSelectedItem();

																if (selectedItemId.id === firstItemId) {

																	prevIcon.disable();
																	webix.html.addCss(prevIcon.$view, "carousel-button-disabled");
																	nextIcon.enable();
																	webix.html.removeCss(nextIcon.$view, "carousel-button-disabled");

																} else if (selectedItemId.id === lastItemId) {

																	nextIcon.disable();
																	webix.html.addCss(nextIcon.$view, "carousel-button-disabled");
																	prevIcon.enable();
																	webix.html.removeCss(prevIcon.$view, "carousel-button-disabled");

																} else {

																	nextIcon.enable();
																	prevIcon.enable();
																	webix.html.removeCss(prevIcon.$view, "carousel-button-disabled");
																	webix.html.removeCss(nextIcon.$view, "carousel-button-disabled");
																}
															}
														});
													},
													onAfterRender: function() {

														$$(this).blockEvent();
														$$(this).select("switcher-element-types-" + self.uid);
														$$(this).unblockEvent();
													}
												}
											}
										]
									}
								}
							]
						},
						{
							id: "next-icon-" + self.uid,
							width: 30,
							view: "icon",
							icon: "mdi mdi-play",
							on: {
								onItemClick: function() {

									let switcher = $$("switcher-" + self.uid);

									if (switcher) {

										let actualPosition = switcher.getScrollState().x;
										switcher.scrollTo(actualPosition + 50, 0);
									}
								}
							}
						}
					]
				};

				return switcherButtonsView;

				//				let view;
				//				
				//				view = {
				//					css: { "background-color": "var(--blue-soft-opacity-1)" },
				//					height: 50,
				//					cols: [
				//						{ width: 10 },
				//						{
				//							rows: [
				//								{ height: 5 },
				//								{
				//									view: "segmented",
				//									css: "segment-custom-2",
				//									value: "switcher-element-types-" + self.uid,
				//									options: [
				//											{
				//												id: "switcher-element-types-" + self.uid,
				//												value: `<div>
				//															<span class='ico webix_icon mdi mdi-format-list-bulleted'></span>
				//															<span>${i18n.t("LBL_LIST")}</span>
				//														</div>`,
				//											},
				//											{
				//												id: "switcher-element-hierarchy-" + self.uid,
				//												value: `<div>
				//															<span class='ico webix_icon mdi mdi-family-tree'></span>
				//															<span>${i18n.t("LBL_TREE")}</span>
				//														</div>`,
				//											}
				//									],
				//									tooltip: function(obj) {
				//				
				//										if (obj.id === "switcher-element-types-" + self.uid) {
				//				
				//											return i18n.t("LBL_SELECTION_BY_TYPE");
				//										}
				//										else if (obj.id === "switcher-element-hierarchy-" + self.uid) {
				//				
				//											return i18n.t("LBL_SELECTION_BY_HIERARCHY");
				//										}
				//									},
				//									on: {
				//										onChange(newv, oldv) {
				//				
				//											//console.log("init change => " + newv);
				//											$$("loading-switcher-" + self.uid).show();
				//											$$("switcher-views-" + self.uid).hide();
				//												
				//											webix.delay(function() {
				//												
				//												$$("loading-switcher-" + self.uid).hide();
				//												$$("switcher-views-" + self.uid).show();
				//													
				//												if (newv === "switcher-element-types-" + self.uid) {
				//					
				//													$$("listview-" + self.listView.uid).show();
				//													$$("treeview-" + self.treeView. uid).hide();
				//													
				//													$$("view-main-list-equipments-" + self.uid).config.header = webix.template(function() {
				//									
				//														return `<div>
				//																	<span class='ico webix_icon mdi mdi-view-headline'></span>
				//																	<span>${i18n.t("LBL_EQUIPMENT_TYPES")}</span>
				//																</div>`;
				//													});
				//													$$("view-main-list-equipments-" + self.uid).refresh();
				//													
				//													initListView();
				//												}
				//												else if (newv === "switcher-element-hierarchy-" + self.uid) {
				//									
				//													$$("listview-" + self.listView.uid).hide();
				//													$$("treeview-" + self.treeView. uid).show();
				//													
				//													$$("view-main-list-equipments-" + self.uid).config.header = webix.template(function() {
				//									
				//														return `<div>
				//																	<span class='ico webix_icon mdi mdi-view-headline'></span>
				//																	<span>${i18n.t("LBL_EQUIPMENT_TREE")}</span>
				//																</div>`;
				//													});
				//													$$("view-main-list-equipments-" + self.uid).refresh();
				//													 					
				//													initTreeView();
				//												}
				//												
				//												//console.log("end change => " + newv);
				//											});
				//										}
				//									}
				//								},
				//								{ height: 5 }	
				//							]
				//							
				//						},
				//						{ width: 10 }
				//					]
				//					
				//				};
				//				return view;
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
						{ id: "resizer-dt-" + self.uid, view: "resizer", css: {"margin-top": "0px !important", "border": "none"}},
						{
							cols: [
								{ width: 6 },
								{ id: "viewDtRecognizedSpace-" + self.uid, height: 1, hidden: true },
								viewDtRecognized,
								{ width: 6 }
							]
						},
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
					
					let actionPanelSwitcher = {
						view: "segmented",
						css: "segment-custom-2",
						value: "action-panel-switcher-show-all-" + self.uid,
						width: 250,
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

					let equipmentSelected = {
						view: "label",
						label: (self.args.selectedPks) ? self.args.selectedPks.length + " " + i18n.t("LBL_EQUIPMENT_SELECTED") : "",
						align: "center",
						width: 350,
						css: "label-window-alarms",
						on: {
							onItemClick: function(id) {

								getWindowEquipmentSelected()
							}
						}
					}

//					viewContentToolbar.elements.push(alarmTypeSwitcher);
					viewContentToolbar.elements.push({});

					if (self.moduleAction != null && self.args.selectedPks && self.args.selectedPks.length > 1) {
						viewContentToolbar.elements.push(equipmentSelected);
						//viewContentToolbar.elements.push({width: 262});
						viewContentToolbar.elements.push({});
					}

				
					viewContentToolbar.elements.push(actionPanelSwitcher);

					function onSelectAlarmType() {

						fillGrids(getSelection());
					}


					function getWindowEquipmentSelected() {

						let dialogEntity = webix.copy(SearchDialogBox.CUSTOM);
			
						dialogEntity.data = [];
						
						dialogEntity.pkField = "id"
						dialogEntity.fields = ["element=LBL_EQUIPMENT", "elementType=LBL_EQUIPMENT_TYPE"];
						dialogEntity.height = 600;
						dialogEntity.width = 800;
						
						let elements = window.em.getAll(EntityType.ELEMENT);
						
						if (Array.isArray(elements) && elements.length > 0) {
								
							elements.forEach(element => {
				
								let ele = self.args.selectedPks.find(e => e === element.id);
				
								if (ele) {
									
									dialogEntity.data.push({
										id: element.id,
										element: element.alias,
										elementType: i18n.t(element.elementType.labelAlias)
									})
								}
							});
						}
							
						let s = new SearchDialogBox(i18n.t("LBL_EQUIPMENT_SELECTED"), dialogEntity);
			
						s.show();

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
			
				function alarmFiltered() {
			
					if (self.config && self.config.action && self.config.action.alarmFiltered) {
						
						let module = window.em.getByField(EntityType.MODULE, "id", self.config.action.alarmFiltered);
			
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
								{ view: "label", label: i18n.t("LBL_ALARMS_STATS_FILTERED") }
							]
						},
						{
							height: 66,
							css: "stats-content",
							cols: [
								{ view: "label", id: "action-panel-alarm-stat-active-" + self.uid, label: 0 },
								{ view: "label", id: "action-panel-alarm-stat-no-recognized-" + self.uid, label: 0 },
								{ view: "label", id: "action-panel-alarm-stat-filtered-" + self.uid, label: 0 }
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
									label: i18n.t("LBL_ALARMS_MONITOR_VIEW_FILTERED"),
									tooltip: i18n.t("LBL_ALARMS_MONITOR_VIEW_FILTERED_DESCRIPTION"),
									type: "iconTop",
									icon: "wxi-filter",
									on: {

										onItemClick() {

											alarmFiltered();
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
									hidden: !self.editAlarmConfigsPermission,
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
			
			self.alarmStatActive = $$("action-panel-alarm-stat-active-" + self.uid);
			self.alarmStatNoRecognized = $$("action-panel-alarm-stat-no-recognized-" + self.uid);
			self.alarmStatFiltered = $$("action-panel-alarm-stat-filtered-" + self.uid); 
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

				self.idStompSubscriptor = stompSubscriptorService.subscribe(appConfig.stompUrl, self.config.topic, onMessage, fields);
				self.idStompSubscriptorTreeState = stompSubscriptorService.subscribe(appConfig.stompUrl, TOPIC_ELEMENTS, onMessageTreeState);
				timerMessages();
			}

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

				updateLevelStats();
			}
			
			function onMessage(be){
				
				self.queue.push(be);
			}
			
			function onMessageTreeState(data) {

				let items;

				if (data.elementTypeState) {

					for (let treeView of self.treeViewsArr) {

						items = treeView.getItemsById_(data.id);

						if (Array.isArray(items) && items.length > 0) {

							items.forEach(item => {

								let newElementTypeState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", data.elementTypeState.id);

								if (newElementTypeState.color != item.props.stateColor) {

									let newItem = webix.copy(item);

									newItem.props.stateColor = newElementTypeState.color;
									newItem.props.stateLabel = i18n.t(newElementTypeState.labelAlias);

									treeView.update(newItem.id, newItem);
								}
							});
						}
					}
				}
			}
			
			function timerMessages() {
				
				self.intervalAlarmMonitor = setInterval(function(){
					
					let message = self.queue.pop();
					
					if (message) {
						
						processMessage(message);	
					}
			
				}, TIME_SLEEP_PROCESS_MESSAGES_MS);
			}

			function processMessage(be) {

				let flattenedData = eval("adapter." + self.config.adapter)([be], null);

				if (Array.isArray(flattenedData) && flattenedData.length > 0) {
					
					flattenedData.forEach(flattenedItem => {
						
						let changes = evaluateChanges(flattenedItem);

						updateDataTables(flattenedItem, changes);
	
						if ($$("view-main-action-panel-" + self.uid)) {
	
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

					// Receive alarm recognized and desactivated or alarm is filtered
					// Actions: Remove from any datacollection
					if ((flattenedItem.recognized && flattenedItem.deactivation) || isFiltered(flattenedItem)) {

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

