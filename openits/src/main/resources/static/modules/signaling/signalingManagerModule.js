import GenericModule from "../../modules/genericModule.js";
import ListView from "../../component/listview.js";
import { util } from "../../lib/util.js";

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

		if (this.moduleAction && this.moduleAction.id === this.config.moduleAction.commandEquipments) {
			
			return super.getViewCheckModuleActionAndSelectedPks();
		}
	}

	loadData() {

		let self = this;

		if (self.moduleAction) {
			
			if (self.moduleAction.id === self.config.moduleAction.commandEquipments) {
				
				loadCommandEquipments();
			}
			else if (self.moduleAction.id === self.config.moduleAction.getEquipmentsTypeCommands) {
				
			}
			else if (self.moduleAction.id === self.config.moduleAction.sendCommandEquipments) {
				
			}
		}
		
		function loadCommandEquipments() {
			
			if ($$(self.uid)) {
	
				$$("loading-" + self.uid).show();
				$$(self.uid).hide();
	
				drawComponents();
				initListView();
	
				$$("loading-" + self.uid).hide();
				$$(self.uid).show();
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
				
				function getViewListElementTypes() {
	
					let view = {};
					let viewListElementType = {};
					let elementTypeView = [];
					view.rows = [];
					
					if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
		
						self.mapElements = new Map();
						self.mapCommandModule = new Map();
						
						util.element.calculateElementTypeCommands(self.args.selectedPks, self.config.defaultSignalingModuleAction, 
																  elementTypeView, self.mapElements, self.mapCommandModule, self.useDefaultModule);
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
										
										} else {
		
											let title = i18n.t("LBL_SIGNALING_MANAGER") + " - " + elementIds.length + " " + i18n.t("LBL_EQUIPMENT_SELECTED");
											
											if (element.elementType && element.elementType.commandModule) {
		
												title = i18n.t(util.module.getWindowParamValue(module, WindowParam.LABEL, i18n.t("LBL_SIGNALING_MANAGER")));
											}
		
											util.ui.setTitle(container.config.id, title);
										}
									}
								}
								
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
					}
				}
			}
			
			function initListView() {
	
				if (self.listViewElementType && self.mapElements && self.mapElements.size > 0) {
	
					self.listViewElementType.selectByIndex(0);
				}
			}
		}	
	}
}