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
			
			this.elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);
		}
	}
	
	getView() {
		
		let self = this;
		let view = {};
		
		view = {
			id: "view-main-content-" + self.uid,
			rows: [
				getViewHeader(), 
				{ height: 3 }, 
				{
					cols: [
						{ width: 20 },
						{
							rows: [
								{ height: 20 },
								getViewTabs(),				
								{ height: 20 }		
							]
						},
						{ width: 20 }
					]
				}
			]
		}
		
		function getViewHeader() {
		
			let viewHeader = {};
			
			viewHeader = {
				id: "view-main-content-header-" + self.uid,
				borderless: true,
				cols: [
					{
						rows: [
							getViewHeaderBarState(),
							{
								height: 95,
								borderless: true,
								cols: [
									getViewHeaderElement(),
									getViewHeaderDate()
								]
							}	
						]
					}
				]
				
			};
			
			function getViewHeaderBarState() {
				
				let viewHeaderBarState = {
					id: "view-main-content-header-bar-state-" + self.uid,
					borderless: true,
					height: 15,
					template: function(data) {
						
						let color = self.element.elementTypeState.color ? self.element.elementTypeState.color : "#000000";
						
						if (data && data.elementTypeState) {
										
							let elementTypeState = self.elementType.elementTypeStates.find(e => e.id === data.elementTypeState.id);
						
							if (elementTypeState) {
								
								color = elementTypeState.color ? elementTypeState.color : "#000000";
							}
						}
						
						return `<div style="height: 15px; background: ${color}"></div>`
					}
				};
				
				return viewHeaderBarState;
			}
			
			function getViewHeaderElement() {
				
				let viewHeaderElement = {
					id:  "view-main-content-header-element-" + self.uid, 
					borderless: true,
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
				};
				
				return viewHeaderElement;
			}
			
			function getViewHeaderDate() {
				
				let viewHeaderDate = {
					id:  "view-main-content-header-date-" + self.uid, 
					width: 300,
					borderless: true,
					template: function(data) {
						
						let timeService = window.sm.getService(Services.TIME_SERVICE);
						
						if (timeService) {
							
							return `<div class='equip-viewer-header-date'> 
										<span class='clock-ico ico webix_icon mdi mdi-clock-time-four-outline'></span>
										<span class="clock-text">${util.toLocaleString(timeService.getDateTime())}</span>
									</div>`;	
						}
					
						return res;
					}
				};
				
				return viewHeaderDate;
			}
			
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
				cells: [{ id: "tab2remove" + self.uid }],
				tabbar: {
					tabMargin: 6
				}
			};
			
//			let tabsConfigured = self.equipmentViewer && (Array.isArray(self.equipmentViewer.equipViewerTabs) && self.equipmentViewer.equipViewerTabs.length > 0);
//			
//			if (!self.equipmentViewer || // Not exist config 
//				(self.equipmentViewer && self.equipmentViewer.showInfoTab) || // Configured as not show 
//				!tabsConfigured // Tabs not configured
//			   ) {
//				
//				self.equipmentViewerInfo = new EquipmentViewerInfo(self.parent, self.element, self.equipmentViewer);
//				
//				viewTabs.cells.push({
//					header: i18n.t("LBL_EQUIPMENT_VIEWER_INFO"),
//					body: self.equipmentViewerInfo.getView()
//				});
//			}
			
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
		let contentTabs = $$("view-main-content-tabs-" + self.uid);
		
		if (contentTabs) {
			
			// Load Info tab
			if (!self.equipmentViewer || // Not exist config 
				(self.equipmentViewer && self.equipmentViewer.showInfoTab)){ // Configured as not show
					
				self.equipmentViewerInfo = new EquipmentViewerInfo(self.parent, self.element, self.equipmentViewer);
				
				contentTabs.addView({
					header: i18n.t("LBL_EQUIPMENT_VIEWER_INFO"),
					body: self.equipmentViewerInfo.getView()
				});	
			}
			
			// Load tabs for modules
			if (self.equipmentViewer && Array.isArray(self.equipmentViewer.equipViewerTabs) && self.equipmentViewer.equipViewerTabs.length > 0) {
				
				self.equipmentViewer.equipViewerTabs.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
				
				self.equipmentViewer.equipViewerTabs.forEach(e => {
					
					let module = util.module.getFromModuleAction(e.moduleActionId);
					
					if (module) {
						
						addModule(module, e);
					}
				});
			}
			
			contentTabs.removeView("tab2remove" + self.uid);
			
			connectWS();
	
			$$(self.uid).attachEvent("onDestruct", function() {
	
				let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
				if (stompSubscriptorService) {
	
					stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
				}
			});
		}

		function addModule(module, equipViewerTab) {
			
			let moduleAction = { id: equipViewerTab.moduleActionId };		
			let args = { selectedPks: [self.element.id]};
				
			util.module.open(module, moduleAction, VIEW_TYPE_CUSTOM, drawTabContent, args);
			
			function drawTabContent(view) {
								
				let tabId = "view-main-content-tab-" + moduleAction.id + "-" + self.uid;
				let tabBodyId = tabId.replace("tab", "tab_body");

				contentTabs.addView({
					id: tabId,
					header: i18n.t(equipViewerTab.label),
					body: {
						id: tabBodyId,
						autowidth: true,
						cols: [view]
					}
				});
				
				// Select initial tab for modules if configured
				if (equipViewerTab.id === self.equipmentViewer.initialTab) {
					
					contentTabs.getTabbar().setValue(tabBodyId);	
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
					
					let dateHeader = $$("view-main-content-header-date-" + self.uid);
					
					if (dateHeader) {
						
						dateHeader.setValues( { update: true });
					}
					
					if (data.elementTypeState) {
						
						let elementHeader = $$("view-main-content-header-element-" + self.uid);
						let elementStateBar = $$("view-main-content-header-bar-state-" + self.uid);
						
						if (elementHeader) {
							
							elementHeader.setValues( { elementTypeState: data.elementTypeState });
						}
						
						if (elementStateBar) {
							
							elementStateBar.setValues( { elementTypeState: data.elementTypeState });
						}
					}
					
					if (Array.isArray(data.alarms) && data.alarms.length > 0)  {
						
						// TODO: Update alarms
					}
					
					if (self.equipmentViewerInfo && Array.isArray(data.elementValues) && data.elementValues.length > 0) {

						self.equipmentViewerInfo.updateMeasures(data.elementValues);
					}
				}
			}
		}
	}
}	
