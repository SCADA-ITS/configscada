import GenericModule from "../../genericModule.js";
import PasSignalingContent from "./pasSignalingContent.js";
import PasSignalingList from "./pasSignalingList.js";
import { util } from "../../../lib/util.js";

const LIST_PANEL_WIDTH = 325;

export default class VmsSignalingModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.pasSignalingList = undefined;
		this.pasSignalingContent = undefined;
		this.currentElement = undefined;
		this.elements = undefined;
		this.currentViewContent = undefined;
		this.viewMainListHierarchyHidden = false;		
	}

	loadData() {

		let self = this;

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();
		}
		
		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsByType, { id: self.config.pas.type })).then(function(elementsResponse) {
			
			if (elementsResponse != null) {
						
				self.elements = elementsResponse.json();
				
				if (Array.isArray(self.elements) && self.elements.length > 0) {

					if(self.moduleAction && self.moduleAction.id == self.config.moduleAction.pas){
			
						self.elements = self.elements.filter(element => self.args.selectedPks.includes(element.id));
						self.currentElement = getElementFromElementId(self.args.selectedPks[0]);
					}
					
					if(self.moduleAction && self.moduleAction.id == self.config.moduleAction.zone){
			
						let elementsHierarchyResponse = webix.ajax().sync().get(appConfig.restUrl + self.config.restEndpoints.elementsHierarchy);
						let elementsHierarchy = JSON.parse(elementsHierarchyResponse.responseText);
						let pasHierarchy = elementsHierarchy.filter(element => element.parent.elementType.id == self.config.pas.type);
						pasHierarchy = pasHierarchy.filter(element => self.args.selectedPks.includes(element.child.id));
						let elementsId = pasHierarchy.map(e => { return e.parent.id; }); 

						self.elements = self.elements.filter(element => elementsId.includes(element.id));
						self.currentElement = getElementFromElementId(elementsId[0]);
					}
					
					self.viewMainListHierarchyHidden = (self.elements.length == 1);

					load();	
				}
				else {
					
					let container = $$(self.args.containerId);
					
					if (container) {
						
						container.close();
						
						webix.alert({
							title: i18n.t("LBL_NO_VMS_AVAILABLE"),
							text: i18n.t("LBL_NO_VMS_AVAILABLE_DESCRIPTION"),
							type: "alert-error",
							width: 500
						});
					}
				}
			}
		});
		
		function load() {
			
			let view = {};
			
			view = drawMain();
			
			$$("loading-" + self.uid).hide();
			$$(self.uid).show();
			
			webix.ui(view, $$(self.uid));
			
			if (!self.currentElement) {
				
				self.elements.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));
				
				self.currentElement = self.elements[0];
			}
			
			if (self.pasSignalingList) {
				
				self.pasSignalingList.select(self.currentElement);
			}
		}
		
		function drawMain() {
			
			let container = $$(self.args.containerId);

			if (container) {
				
				self.containerWidth += container.$width;
			}
			
			let viewMain = {
				id: self.uid,
				cols: []
			};			

			let viewMainListHierarchy = {
				id: "view-main-list-hierarchy-" + self.uid,
				hidden: self.viewMainListHierarchyHidden,
				header: function() {
					
					return `<div>
								<span class='ico webix_icon mdi mdi-view-headline'></span>
								<span>${i18n.t("LBL_PAS")}</span>
							</div>`;
				},
				body: {
					width: LIST_PANEL_WIDTH,
					rows: [getViewList()]
				}
			};
					
			let viewMainResizer = { 
				id: "view-main-resizer-" + self.uid,
				hidden: self.viewMainListHierarchyHidden,
				view: "resizer" 
			};
			
			self.currentViewContent = "view-main-content-" + self.uid;
			
			let viewMainContent = {
				body: {
					rows: [
						{
							id: self.currentViewContent		
						}
					]
					
				}
			};
			
			let viewMainBody = {
				id: "view-main-body-" + self.uid,
				hidden: true,
				rows: [
					{
						id: "view-loading-panel-" + self.uid,
						hidden: true,
						rows: [util.ui.getViewDrawLoading(true)],
					},
					{
						id: "view-panel-" + self.uid,
						hidden: false,
						cols: [viewMainContent],
					}
				]	
			};

			let viewMainLoading = {
				id: "view-main-loading-" + self.uid,
				hidden: false,
				rows: [util.ui.getViewDrawLoading()],
			};
			
			viewMain.cols = [viewMainListHierarchy, viewMainResizer, viewMainBody, viewMainLoading];
			

			function getViewList() {
				
				let view = {};
				
				self.pasSignalingList = new PasSignalingList(self.elements, onSelectElement);
				
				view = self.pasSignalingList.getView();
				
				return view;
			}
			
			function onSelectElement(elementId) {
				
				$$("view-main-body-" + self.uid).hide();
				$$("view-main-loading-" + self.uid).show();
				
				$$("view-loading-panel-" + self.uid).show();
				$$("view-panel-" + self.uid).hide();
				
				self.currentElement = getElementFromElementId(elementId);
				
				loadContent();

				$$("view-main-loading-" + self.uid).hide();
				$$("view-main-body-" + self.uid).show();

				//TODO DEPRECATED sustituir por evento, no usar DELAY siempre que se pueda
				webix.delay(function() {

					$$("view-loading-panel-" + self.uid).hide();
					$$("view-panel-" + self.uid).show();
				}, null, null, 500);
			}
			
			function loadContent() {
				
				let view = {};
				
				let previousViewContent = self.currentViewContent;

				self.pasSignalingContent = new PasSignalingContent(self.config, self.currentElement);
				self.pasSignalingContent.loadData()			
				view = self.pasSignalingContent.getView();
			
				self.currentViewContent = view.id;
				
				webix.ui(view, $$(previousViewContent));

				self.pasSignalingContent.callbacks();
			}
			
			return viewMain;
		}

		function getElementFromElementId(elementId) {
				
			return self.elements.find(e => e.id == elementId);
		}
	}
}

