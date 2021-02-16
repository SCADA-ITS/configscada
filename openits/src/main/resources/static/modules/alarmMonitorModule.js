import GenericModule from "../modules/genericModule.js";
import DataTable from "../component/datatable.js";
import ListView from "../component/listview.js";
import { util } from "../lib/util.js";
import { adapter } from "../lib/adapter.js";

const ELEMENT_TYPE_ALL = "-1";

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
		
		this.elementTypeIdSelected = ELEMENT_TYPE_ALL;

		this.config = JSON.parse(module.moduleValues.find(e => e.moduleParam.id === PARAM_MODULE_TYPE_CUSTOM_WINDOW_CONFIG).value);

		console.log(this.uid);
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

			webix.ajax().get(appConfig.restUrl + this.config.restEndpoint).then(function(dat) {

				if (dat != null) {

					let data = dat.json();

					if (data && Array.isArray(data)) {

						let flattenedData = [];

						if (data.length > 0) {

							flattenedData = eval("adapter." + self.config.adapter)(data, null);
						}

						loadDataCollections(flattenedData);

						connectWS();

						drawComponents();

						initDataTables();
						
						initListView();

						$$(self.uid).attachEvent("onDestruct", function() {

							let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

							if (stompSubscriptorService) {

								stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
							}
						});
					}

					$$("loading-" + self.uid).hide();
					$$(self.uid).show();

				}
			});
		}
		
		function loadDataCollections(data) {

			self.dcNoRecognized = data.filter(e => {
	
				return !e.recognized && !e.mutedUntil;
			});
			
			self.dcRecognized = data.filter(e => {
	
				return e.recognized && !e.mutedUntil;
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
		
		function drawComponents() {

			let viewMain = {
				id: self.uid,
				cols: [
					{
						width: 350,
						rows: [
							{
								cols: [
									getViewListEquipments()				
								]
							}
						]
					},
					{ view: "resizer" },
					getViewContent(),
					getViewActionPanel()
				]
			};
	
			function getViewListEquipments() {
	
				let viewListEquipments = {};
				let elementTypes = window.em.getAll(EntityType.ELEMENT_TYPE);
				let elementTypesFiltered = undefined;
				let elementTypeView = [];
				
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
					
					self.listView = new ListView(i18n.t("LBL_EQUIPMENT_TYPE_SEARCH"), elementTypeView, onSelect);
					
					viewListEquipments = self.listView.getView();
				}
				
				function onSelect(data) {
				
					console.log(data);
					
					if (Array.isArray(data) && data.length > 0) {
						
						self.elementTypeIdSelected = data[0];
					
						if (self.dtNoRecognized) {
							
							self.dtNoRecognized.clear();
					
							if (self.elementTypeIdSelected === ELEMENT_TYPE_ALL) {
							
								self.dtNoRecognized.add(self.dcNoRecognized);
							}
							else {
							
								self.dtNoRecognized.add(self.dcNoRecognized.filter(e => {
									return (e.targetElementTypeId == self.elementTypeIdSelected);
								}));
							}		
						}
						
						if (self.dtRecognized) {
							
							self.dtRecognized.clear();
							
							if (self.elementTypeIdSelected === ELEMENT_TYPE_ALL) {
							
								self.dtRecognized.add(self.dcRecognized);
							}
							else {
								
								self.dtRecognized.add(self.dcRecognized.filter(e => {
									return (e.targetElementTypeId == self.elementTypeIdSelected);
								}));
							}
						}
					}	
				}
	
				return viewListEquipments;
			}
	
			function getViewContent() {
	
				let viewContent = {};
				let viewDtNoRecognized = {};
				let viewDtRecognized = {};
	
				self.dtNoRecognized = loadDataTable(self.config.gridNoRecognized);
				self.dtRecognized = loadDataTable(self.config.gridRecognized);
	
				viewDtNoRecognized = {
	
					rows: []
				};
	
				viewDtRecognized = {
	
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
								viewDtNoRecognized,
								{ width: 6 }
							]
						},
						{ view: "resizer" },
						{
							cols: [
								{ width: 6 },
								viewDtRecognized,
								{ width: 6 }
							]
						},
						{ view: "resizer" },
						{ height: 6 }
					]
				};
	
				function loadDataTable(gridId) {
	
					let response = webix.ajax().sync().get(appConfig.restUrl + "grids/" + gridId);
	
					if (response != undefined) {
	
						let dat = JSON.parse(response.responseText);
	
						let metadata = dat.metadata;
	
						return new DataTable(gridId, null, metadata, [], null);
					}
				}
	
				return viewContent;
			}
	
			function getViewActionPanel() {
	
				let viewActionPanel = {};
	
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
	
				viewActionPanel = {
	
					id: "viewActionPanel-" + self.uid,
					width: 300,
					css: "panel-action",
					rows: [
						{},
						infiniteSvgView,
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
	
				self.idStompSubscriptor = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(self.config.topic), onMessage);
			}
	
			function onMessage(be) {
	
				console.log(be);
				
				let flattenedData = eval("adapter." + self.config.adapter)([be], null);
				
				if (flattenedData) {
					
					let changes = updateDataSources(flattenedData);
					
					updateDataTables(flattenedData, changes); 
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
		
						// Receive alarm recognized and desactivated.
						// Actions: Remove from any datacollection
						if (flattenedItem.recognized && flattenedItem.deactivation) {
							
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
