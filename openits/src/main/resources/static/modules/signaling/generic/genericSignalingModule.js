import GenericModule from "../../../modules/genericModule.js";
import { util } from "../../../lib/util.js";
import GenericSignalingCommand from "./genericSignalingCommand.js";
import SearchDialogBox from "../../../component/searchDialogBox.js";

export default class GenericSignalingModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.config = module.config;
		this.element = undefined;
		this.signalingCmds = [];
	}

	getView() {

		return super.getViewCheckModuleActionAndSelectedPks();
	}

	loadData() {
		
		let self = this;

		if (self.moduleAction.id === self.config.moduleAction.commandEquipments &&
			Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

			if ($$(self.uid)) {

				$$("loading-" + self.uid).show();
				$$(self.uid).hide();
	
				loadSingleElement();
			}
			
			function loadSingleElement() {
			
				let elementId = self.args.selectedPks[0];
				
				if (elementId) {
				
					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.element, { id: elementId })).then(function(elementResponse) {
					
						if (elementResponse != null) {
							
							let element = elementResponse.json();
								
							if (element) {
							
								self.element = element;
								
								drawComponents();
	
								$$("loading-" + self.uid).hide();
								$$(self.uid).show();
							}
						}
					});
				}
			}
	
			function drawComponents() {
				
				let viewMain = {
					
					id: self.uid,					
					rows: [
						getViewHeader(),
						{ height: 20 },
						{
							cols: [
								{ width: 20 },
								getViewTabs(),
								{ width: 20 }
							]
						},
						{ height: 20 }
					]
				}
	
				function getViewHeader() {
					
					let viewHeader;
					
					if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
					
						if (self.args.selectedPks.length === 1) {
							
							viewHeader = {
								cols: [
									{
										view: "label",
										label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-view-headline"></i></div>`,
										align: "center",
										height: 50,
										width: 56
									},
									{
										view: "label",
										css: "alarm-view-header-details-text",
										label: `<span class="alarm-view-header-details-text">${self.element.alias}</span>`,
										align: "left",
										height: 50
									},
									{}
								]
							};	
						}
						else {
							
							viewHeader = {
								cols: [
									{},
									{
										view: "label",
										label: self.args.selectedPks.length + " " + i18n.t("LBL_EQUIPMENT_SELECTED"),
										align: "center",
										width: 350,
										css: "label-window-alarms",
										on: {
											onItemClick: function(id) {
				
												getWindowEquipmentSelected();
											}
										}
									},
									{}
								]
							}	
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
							
						let s = new SearchDialogBox("LBL_EQUIPMENT_SELECTED", dialogEntity);
			
						s.show();
					}
					
					return viewHeader;
				}
				
				function getViewTabs() {
					
					let viewTabs = {};

					viewTabs = {
						view: "tabview",
						id: "view-tabs-" + self.uid,
						css: "auxiliary-tab",
						animate: false,
						borderless: true,
						cells: [],
						multiview: {
							keepViews:true
				        },
						tabbar: {
							tabMargin: 6
						}
					};
					
					
					if (self.element && Array.isArray(self.element.commandElements) && self.element.commandElements.length > 0) {
						
						self.element.commandElements.forEach(cmdElement => {
							
							let signalingCmd = new GenericSignalingCommand(self, self.args.selectedPks, cmdElement);
							
							viewTabs.cells.push(signalingCmd.getView());
							
							self.signalingCmds.push(signalingCmd);
						});
					}
					else {
						
						viewTabs =util.ui.getViewNoData();
					}
					
		
					return viewTabs;
				}
				
				webix.ui(viewMain, $$(self.uid));
				
				if (Array.isArray(self.signalingCmds) && self.signalingCmds.length > 0) {
					
					self.signalingCmds.forEach(e => {
						
						e.loadData();
					});
				}
			}
		}
	}

}
