import EquipmentViewerInfo from "./equipmentViewerInfo.js";
import { util } from "../../lib/util.js";
import { adapter } from "../../lib/adapter.js";

export default class EquipmentViewerContent {
	
	constructor(parent, element, equipmentViewer) {

		this.parent = parent;
		this.element = element;
		
		this.uid = parent.uid;
		
		this.equipmentViewerInfo = undefined;
		
		this.equipmentViewer = equipmentViewer;
		this.idStompSubscriptor = undefined;
		
		if (element) {
			
			let allElementTypes = window.em.getAll(EntityType.ELEMENT_TYPE);
			this.elementType = allElementTypes.find(e => e.id === element.elementType.id);	
		}
	}
	
	getView() {
		
		let self = this;
		let view = {};
		
		view = {
			id: "view-main-content-" + self.uid,
			rows: [getViewHeader(), { height: 3 }, getViewTabs()]
		}
		
		function getViewHeader() {
		
			let viewHeader = {};
			
			viewHeader = {
				id: "view-main-content-header-" + self.uid,
				borderless: true,
				height: 95,
				cols: [
					{
						id:  "view-main-content-header-element-" + self.uid, 
						width: 500,
						template: function(data) {
							
							if (self.elementType) {
								
								let equipmentIcon = undefined;
								
								if (!equipmentIcon) {
									equipmentIcon = "not_found_50.png";
								}
								
								let elementTypeStateLabel = i18n.t(self.element.elementTypeState.labelAlias);
								let color = self.element.elementTypeState.color ? self.element.elementTypeState.color : "#000000";
								
								if (data && data.elementTypeState) {
									
									let elementTypeState = self.elementType.elementTypeStates.find(e => e.id === data.elementTypeState.id);
								
									if (elementTypeState) {
										
										elementTypeStateLabel = i18n.t(elementTypeState.labelAlias);
										color = elementTypeState.color ? elementTypeState.color : "#000000";
									}
								}
								
								let res = `<image class="ims-incident-report-icon" src="../img/${equipmentIcon}" />
										<div class="ims-incident-report-title">
											${self.element.alias + ' - ' + self.element.description}
										</div>
										<div class="ims-incident-report-title">
											<span class="ims-incident-badge" style="background: ${color}; color: white;">${elementTypeStateLabel}</span>
										</div>`; 
							
								return res;
							}
						}
					},
					{},
					{
						width: 200
					}
				]
			};
			
			return viewHeader;	
		}
		
		function getViewTabs() {
			
			let viewTabs = {};
			
			viewTabs = {
				view: "tabview",
				id: "view-main-content-tabs-" + self.uid,
				css: "secondary-tab",
				animate: false,
				borderless: true,
				cells: [],
				tabbar: {
					tabMargin: 6
				}
			};
			
			if (!self.equipmentViewer || (self.equipmentViewer && self.equipmentViewer.showInfoTab)) {
				
				self.equipmentViewerInfo = new EquipmentViewerInfo(self.parent, self.element, self.equipmentViewer);
				
				viewTabs.cells.push({
					header: i18n.t("LBL_EQUIPMENT_VIEWER_INFO"),
					body: self.equipmentViewerInfo.getView()
				});
			}
			
			return viewTabs;
		}
		
		return view;
	}
	
	refresh() {
		
		// TODO: Refresh upper content???
		
		// Refresh measures
		if (this.equipmentViewerInfo) {
		
			this.equipmentViewerInfo.refresh();	
		}
	}
	
	loadData() {
		
		let self = this;
		
		if (self.equipmentViewer && Array.isArray(self.equipmentViewer.equipViewerTabs) && self.equipmentViewer.equipViewerTabs.length > 0) {
			
			self.equipmentViewer.equipViewerTabs.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
			
			self.equipmentViewer.equipViewerTabs.forEach(e => {
				
				let module = util.module.getFromModuleAction(e.moduleActionId);
				
				if (module) {
					
					addModule(module, e.moduleActionId, i18n.t(e.label));
				}
			});
		}
		
		connectWS();

		$$(self.uid).attachEvent("onDestruct", function() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
			}
		});
	
		function addModule(module, moduleActionId, label) {
			
			let moduleAction = { id: moduleActionId };		
			let args = { selectedPks: [self.element.id]};
				
			util.module.open(module, moduleAction, VIEW_TYPE_CUSTOM, drawTabContent, args);
			
			function drawTabContent(view) {
			
				let contentTabs = $$("view-main-content-tabs-" + self.uid);
			
				if (contentTabs) {
								
					contentTabs.addView({
						id: "view-main-content-tabs-" + moduleAction + "-" + self.uid,
						header: label,
						body: {
							id: "view-main-content-tabs-body-" + moduleAction + "-" + self.uid,
							autowidth: true,
							cols: [view]
						}
					});	
				}
			}
		}
		
		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {
				
				let topic = self.parent.config.topic + "." + self.element.elementType.id + "." + self.element.id;

				self.idStompSubscriptor = stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(topic), onMessage);
			}
			
			function onMessage(data) {

				if (data) {
					
					if (data.elementTypeState) {
						
						let elementHeader = $$("view-main-content-header-element-" + self.uid);
						
						if (elementHeader) {
							
							elementHeader.setValues( { elementTypeState: data.elementTypeState });
						}
					}
					
					if (Array.isArray(data.alarms) && data.alarms.length > 0)  {
						
						// TODO: Update alarms
					}
					
					if (Array.isArray(data.elementValues) && data.elementValues.length > 0) {

						self.equipmentViewerInfo.updateMeasures(data.elementValues);
					}
				}
			}
		}
	}
}	
