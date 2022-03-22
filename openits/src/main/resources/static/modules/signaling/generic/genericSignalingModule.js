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

		if ((self.moduleAction.id === self.config.moduleAction.commandEquipments || 
			 self.moduleAction.id === self.config.moduleAction.commandEdit) && 
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

					let viewHeader = {height: 1};

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

					} else if ((Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 1) && (self.element && Array.isArray(self.element.commandElements) && self.element.commandElements.length > 0)){

						let elementTypeLabelDescription;
						let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", self.element.elementType.id);

						if (elementType) {

							elementTypeLabelDescription = elementType.labelDescription;
						}

						let label = i18n.t("LBL_EQUIPMENT_SELECTED") + ": " + self.args.selectedPks.length + " " + i18n.t(self.element.elementType.labelAlias) + (elementTypeLabelDescription ? " (" + i18n.t(elementTypeLabelDescription) + ")" : "");

						viewHeader = {
							id: "view-main-content-signaling-header-" + self.uid,
							cols: [
								{ width: 50 },
								{
									rows: [
										{ height: 10 },
										{
											id: "signaling-header" + self.uid,
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


						function getWindowEquipmentSelected() {					

							let dialogEntity = webix.copy(SearchDialogBox.CUSTOM);

							dialogEntity.data = [];

							dialogEntity.fields = ["element=LBL_EQUIPMENT", "elementType=LBL_EQUIPMENT_TYPE"];

							let elements = window.em.getAll(EntityType.ELEMENT);

							if (Array.isArray(elements) && elements.length > 0) {

								elements.forEach(el => {

									let ele = self.args.selectedPks.find(e => e === el.id);

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
							else if (self.moduleAction.id === self.config.moduleAction.commandEdit) {
														
								if (self.args.data && self.args.data.commandElement && 
									self.args.data.commandElement.commandElementValues) {
										
									commandElement.commandElementValues = webix.copy(self.args.data.commandElement.commandElementValues);	
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
				
				if (self.moduleAction.id === self.config.moduleAction.commandEdit) {

					let signalingTabs = $$("view-tabs-" + self.uid);
					
					let viewTabSelected = "view-generic-signaling-command-body-";
					
					if (signalingTabs) {
						
						if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 1 && 
							Array.isArray(self.element.commandElements) && self.element.commandElements.length > 0) {

							viewTabSelected += self.element.commandElements[0].id + "-" + self.uid; 	
						}
						else {
							
							viewTabSelected += self.args.data.commandElement.commandElementType.id + "-" + self.uid;
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
