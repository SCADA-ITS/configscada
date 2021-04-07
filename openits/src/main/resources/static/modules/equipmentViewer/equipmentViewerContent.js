import EquipmentViewerInfo from "./equipmentViewerInfo.js";
import { util } from "../../lib/util.js";

export default class EquipmentViewerContent {
	
	constructor(parent, element) {

		this.parent = parent;
		this.element = element;
		
		this.uid = parent.uid;
		
		this.equipmentViewerInfo = undefined;
		
		this.equipmentViewer = parent.equipmentViewer;
	}
	
	getView() {
		
		let self = this;
		let view = {};
		
		view = {
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
						width: 500,
						template: function() {

							let equipmentIcon = undefined;

							if (equipmentIcon == undefined) {
								equipmentIcon = "not_found_50.png";
							}
							
							let res = `<image class="ims-incident-report-icon" src="../img/${equipmentIcon}" />
										<div class="ims-incident-report-title">
											${self.element.alias}
										</div>
										<div class="ims-incident-report-title">
											<span class="ims-incident-badge" style="background: red; color: white;">${self.element.description}</span>
										</div>`; 
							
							return res;
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
			
			if (self.equipmentViewer.showInfoTab) {
				
				self.equipmentViewerInfo = new EquipmentViewerInfo(self.parent, self.element);
				
				viewTabs.cells.push({
					header: i18n.t("LBL_EQUIPMENT_VIEWER_INFO"),
					body: self.equipmentViewerInfo.getView()
				});
			}
			
			return viewTabs;
		}
		
		return view;
	}
	
	loadData() {
		
		let self = this;
		
		if (Array.isArray(self.equipmentViewer.equipViewerTabs) && self.equipmentViewer.equipViewerTabs.length > 0) {
			
			self.equipmentViewer.equipViewerTabs.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
			
			self.equipmentViewer.equipViewerTabs.forEach(e => {
				
				let module = util.module.getFromModuleAction(e.moduleActionId);
				
				if (module) {
					
					addModule(module, e.moduleActionId, i18n.t(e.label));
				}
			});
		}
	
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
	}
}	
