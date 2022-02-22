import GenericModule from "../../../modules/genericModule.js";
import ListView from "../../../component/listview.js";
import { util } from "../../../lib/util.js";
import SearchDialogBox from "../../../component/searchDialogBox.js";

export default class SignalingManagerModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.listViewElementType = undefined;
		this.elementTypeIdSelected = undefined;
		this.config = module.config;
		this.mapElements = undefined;
		this.mapCommandModule = undefined;
		this.useDefaultModule = false;
	}

	getView() {

		return super.getViewCheckModuleActionAndSelectedPks();
	}

	loadData() {

		if (this.moduleAction && this.moduleAction.id === this.config.moduleAction.commandEquipments) {

			this.refresh();
		}
	}

	refresh() {

		let self = this;

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();

			drawComponents();

			initListView();

			$$("loading-" + self.uid).hide();
			$$(self.uid).show();
		}

		function getViewListElementTypes() {

			let view = {};
			let viewListElementType = {};
			let elementTypeView = [];
			view.rows = [];

			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

				self.mapElements = new Map();
				self.mapCommandModule = new Map();

				self.args.selectedPks.forEach(id => {

					let element = window.em.getByField(EntityType.ELEMENT, "id", id);

					if (element.elementType && element.elementType.id) {

						let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);

						if (elementType) {

							if (element.elementSubtype && element.elementSubtype.id &&
								elementType.elementSubtypes && elementType.elementSubtypes.length > 0) {

								let elementSubtype = elementType.elementSubtypes.find(e => e.id === element.elementSubtype.id);

								if (elementSubtype) {
									
									let commandModule = self.config.defaultSignalingModuleAction;
									let alias = "";
									
									if (element.elementSubtype.commandModule) {
										
										commandModule = element.elementSubtype.commandModule; 
									}
									else if (element.elementType.commandModule) {
										
										commandModule = element.elementType.commandModule;
									}
									
									if (commandModule == self.config.defaultSignalingModuleAction) {
										
										alias = "(*) ";
									}
									
									let listElement = self.mapElements.get(element.elementSubtype.id);

									if (!listElement) {

										listElement = [];

										self.mapElements.set(element.elementSubtype.id, listElement);
										self.mapCommandModule.set(element.elementSubtype.id, commandModule);

										elementTypeView.push({
											id: element.elementSubtype.id,
											alias: alias + i18n.t(elementType.labelAlias) + ' - ' + i18n.t(elementSubtype.labelAlias),
											description: i18n.t(elementSubtype.labelDescription),
											image: elementType.elementTypePhoto
										});
									}
									listElement.push(id);
								}

							} else {

								let listElement = self.mapElements.get(element.elementType.id);

								if (!listElement) {

									let alias = undefined;
									let commandModule = undefined;

									if (element.elementType.commandModule) {

										alias = i18n.t(elementType.labelAlias);
										commandModule = element.elementType.commandModule;

									} else {
										alias = "(*) " + i18n.t(elementType.labelAlias);
										commandModule = self.config.defaultSignalingModuleAction;
										self.useDefaultModule = true;
									}

									listElement = [];

									self.mapElements.set(element.elementType.id, listElement);
									self.mapCommandModule.set(element.elementType.id, commandModule);

									elementTypeView.push({
										id: element.elementType.id,
										alias: alias,
										description: i18n.t(elementType.labelDescription),
										image: elementType.elementTypePhoto
									});
								}
								listElement.push(id);
							}
						}
					}

				});
			}
			if (elementTypeView.length > 0) {

				elementTypeView.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));

				elementTypeView.forEach(e => {
					e.alias = e.alias + " (" + self.mapElements.get(e.id).length + ")";
				});


				let events = {};

				events[ListView.EventTypes.ON_SELECT_ROW] = fillData;

				let options = {};

				options[ListView.Options.SEARCH] = true;

				self.listViewElementType = new ListView(elementTypeView, options, events);

				viewListElementType = self.listViewElementType.getView();
			}

			view.rows.push(viewListElementType);

			return view;
		}

		function fillData(elementTypeArray, callbackEnable) {

			let elementTypeId;

			if (callbackEnable) {

				callbackEnable(false);
			}

			if (Array.isArray(elementTypeArray) && elementTypeArray.length == 1) {

				elementTypeId = elementTypeArray[0];
			}

			self.elementTypeIdSelected = elementTypeId;

			fillViewSignalingModule(callbackEnable);

		}

		function fillViewSignalingModule(callbackEnable) {

			if (self.elementTypeIdSelected && self.mapCommandModule && self.mapElements) {

				let moduleActionId = parseInt(self.mapCommandModule.get(self.elementTypeIdSelected));
				let elementIds = self.mapElements.get(self.elementTypeIdSelected);
				let element = window.em.getByField(EntityType.ELEMENT, "id", elementIds[0]);

				if (moduleActionId && elementIds) {

					let module = util.module.getFromModuleAction(moduleActionId);

					if (module) {

						let moduleAction = { id: moduleActionId };
						let args = { selectedPks: elementIds };

						util.module.open(module, moduleAction, VIEW_TYPE_CUSTOM, drawContent, args ,function(instance){
							
							if (callbackEnable) {

								callbackEnable(true);
							}
						});

						if (Array.isArray(elementIds) && elementIds.length > 0) {

							let container = $$(self.args.containerId);

							if (container) {
							
								if (elementIds.length === 1) {
									
									util.ui.setTitle(container.config.id, i18n.t("LBL_SIGNALING_MANAGER") + " - " + element.alias);		
								}
								else {

									util.ui.setTitle(container.config.id, i18n.t("LBL_SIGNALING_MANAGER") + " - " + elementIds.length + " " + i18n.t("LBL_EQUIPMENT_SELECTED"));
								}	
							}
						}
						
						webix.ui(getViewHeader(), $$("view-main-content-signaling-header-" + self.uid));

						function drawContent(view) {

							let content = $$("view-main-content-signaling-module-" + self.uid);

							if (content) {

								let viewContent = {
									rows: [
										{
											id: "view-main-content-signaling-module-" + self.uid,
											cols: [view]
										}
									]
								};

								webix.ui(viewContent, $$("view-main-content-signaling-module-" + self.uid));
							}
						}
					}
				}
				
				function getViewHeader() {
					
					let viewHeader;
					let elementTypeLabelDescription;
					let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);
					const _TRUE = "true";
					
					if (elementType) {
						
						elementTypeLabelDescription = elementType.labelDescription; 
					}
					
					let label = i18n.t("LBL_EQUIPMENT_SELECTED") + ": " + elementIds.length + " " + i18n.t(element.elementType.labelAlias) + (elementTypeLabelDescription ? " (" + i18n.t(elementTypeLabelDescription) + ")" : ""); 
					
					if (Array.isArray(elementIds) && elementIds.length > 0) {

						if ((elementIds.length > 1 && !elementType.showSignalingManagerSelectedItems)  || 
						(elementIds.length > 1 && elementType.showSignalingManagerSelectedItems && elementType.showSignalingManagerSelectedItems === _TRUE)) {

							viewHeader = {
								id: "view-main-content-signaling-header-" + self.uid,
								cols: [
									{ width: 50 },
									{
										rows: [
											{ height: 10 },
											{
												view: "label",
												label: label, 
												align: "center",
												//width: 350,
												css: "label-window-alarms",
												on: {
													onItemClick: function(id) {
						
														getWindowEquipmentSelected();
													}
												}
											},
											{ height: 10 }
										]
									},
									{ width: 50 }
								]
							}	
						}
						else {
							
							viewHeader = { 
								id: "view-main-content-signaling-header-" + self.uid,
								height: 1
							};
						}
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
								
							elements.forEach(el => {
				
								let ele = elementIds.find(e => e === el.id);
				
								if (ele) {
									
									dialogEntity.data.push({
										id: el.id,
										element: el.alias,
										elementType: i18n.t(el.elementType.labelAlias)
									})
								}
							});
						}
							
						let s = new SearchDialogBox(label, dialogEntity);
			
						s.show();
					}
					
					return viewHeader;
				}
			}
			
			
		}

		function initListView() {

			if (self.listViewElementType && self.mapElements && self.mapElements.size > 0) {

				self.listViewElementType.selectByIndex(0);
			}
		}

		function drawComponents() {

			let viewMain = {
				id: self.uid,
				cols: []
			};

			let viewMainListEquipments = {
				id: "view-main-list-equipments-" + self.uid,
				header: i18n.t("LBL_EQUIPMENT_SELECTED"),
				hidden: (self.args.selectedPks.length === 1),
				body: {
					width: 340,
					rows: [

					]
				}
			};


			let viewListElementTypes = {

				//height: 260,
				css: {"border-bottom": "1px solid #dadee0 !important"},
				cols: [
					getViewListElementTypes()
				]
			}

			let viewLabelDefaulSigalingModule = {

				view: "label",
				label: "(*) " + i18n.t("LBL_DEFAULT_SIGNALING_MODULE"),
				css: "label-default"
			}

			if (self.useDefaultModule) {

				viewMainListEquipments.body.rows = [viewListElementTypes, viewLabelDefaulSigalingModule];

			} else {
				viewMainListEquipments.body.rows = [viewListElementTypes];
			}

			let viewMainResizer = {
				id: "view-main-resizer-" + self.uid,
				view: "resizer"
			};

			let viewMainContent = {
				id: "view-main-content-" + self.uid,
				css: {"box-sizing": "border-box"},
				body: {
					rows: [
						{
							id: "view-main-content-signaling-header-" + self.uid
						},
						{
							id: "view-main-content-signaling-module-" + self.uid
						}
					]
				}
			};

			viewMain.cols = [viewMainListEquipments, viewMainResizer, viewMainContent];

			webix.ui(viewMain, $$(self.uid));
			
			if (self.mapElements.size === 1) {
				
				let listView = $$("view-main-list-equipments-" + self.uid);
				let resizer = $$("view-main-resizer-" + self.uid);
				
				if (listView && resizer) {
					
					listView.hide();
					resizer.hide();
				}				
			}
		}
	}


}
