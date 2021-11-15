import GenericModule from "../../../modules/genericModule.js";
import { util } from "../../../lib/util.js";
import GenericSignalingCommand from "./genericSignalingCommand.js";

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

		if ((self.moduleAction.id === self.config.moduleAction.commandEquipments || 
			 self.moduleAction.id === self.config.moduleAction.planOrderEdit) && 
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
						{
							cols: [
								{ width: 20 },
								getViewTabs(),
								{ width: 20 }
							]
						},
						{ height: 1 }
					]
				}
	
				function getViewHeader() {
					
					let viewHeader = { height: 1 };
					
					if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length === 1) {
					
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
							
							let commandElement = webix.copy(cmdElement);
							let signalingCmd = undefined;
							
							if (self.moduleAction.id === self.config.moduleAction.commandEquipments) {
								
								signalingCmd = new GenericSignalingCommand(self, self.args.selectedPks, commandElement);
							}
							else if (self.moduleAction.id === self.config.moduleAction.planOrderEdit) {
														
								if (self.args.data && self.args.data.planOrder && self.args.data.planOrder.comandElement && 
									self.args.data.planOrder.comandElement.commandElementValues) {
										
									commandElement.commandElementValues = webix.copy(self.args.data.planOrder.comandElement.commandElementValues);	
								}
									
								signalingCmd = new GenericSignalingCommand(self, self.args.selectedPks, commandElement, true);
							}
							
							viewTabs.cells.push(signalingCmd.getView());
							
							self.signalingCmds.push(signalingCmd);
						});
					}
					else {
						
						viewTabs = util.ui.getViewNoData();
					}
					
		
					return viewTabs;
				}

				webix.ui(viewMain, $$(self.uid));
				
				if (Array.isArray(self.signalingCmds) && self.signalingCmds.length > 0) {
					
					self.signalingCmds.forEach(e => {
						
						e.loadData();
					});
				}
				
				if (self.moduleAction.id === self.config.moduleAction.planOrderEdit) {

					let signalingTabs = $$("view-tabs-" + self.uid);
					
					let viewTabSelected = "view-generic-signaling-command-body-";
					
					if (signalingTabs) {
						
						if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 1 && 
							Array.isArray(self.element.commandElements) && self.element.commandElements.length > 0) {

							viewTabSelected += self.element.commandElements[0].id + "-" + self.uid; 	
						}
						else {
							
							viewTabSelected += self.args.data.planOrder.comandElement.commandElementType.id + "-" + self.uid;
						}
						
						signalingTabs.getTabbar().setValue(viewTabSelected);
					}					
				}
			}
		}
	}

	getSignaling() {
		
		let self = this;
		let commandElement = undefined;
		
		let signalingTabs = $$("view-tabs-" + self.uid);
		
		if (signalingTabs) {
					
			let signalingTabSelected = $$(signalingTabs.getTabbar().getValue());
			
			if (signalingTabSelected) {
				
				let signalingCmd = this.signalingCmds.find(e => e.commandElementType.id === signalingTabSelected.config.commandElementTypeId);
				
				if (signalingCmd) {
					
					commandElement = signalingCmd.getCommandElement();
				}
			}
		}
		
		return commandElement;
	}
}
