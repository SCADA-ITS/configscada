import { util } from "../../lib/util.js";
import { travelTimeConfigUtils } from "./travelTimeConfigUtils.js";
import GenericModule from "../genericModule.js";
import MessageBox from "../../component/messageBox.js";
import TravelTimeConfigSectionDetail from "./travelTimeConfigSectionDetail.js";
import TravelTimeConfigAssignPanel from "./travelTimeConfigAssignPanel.js";
import TravelTimeConfigUnassignPanels from "./travelTimeConfigUnassignPanels.js";
import TravelTimeConfigViewPanel from "./travelTimeConfigViewPanel.js";


export default class TravelTimeConfigModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.moduleAction = moduleAction;
		this.args = args;
		
		this.travelTimeConfigSectionDetail = undefined;
		
		this.travelTime = {
			id: undefined,
			alias: undefined,
			description: undefined,
			descendingDirection: false,
			pkIni: undefined,
			pkEnd: undefined,
			message: undefined,
			panels: undefined,
			sections: []
		}

		this.uid = webix.uid();
	}

	getView() {

		let self = this;
		let view = undefined;

		if ((self.moduleAction.id === self.config.moduleAction.add) || (self.moduleAction.id === self.config.moduleAction.edit)) {

			view = {
				cols: [
					{
						id: "loading-" + self.uid,
						hidden: false,
						rows: [util.ui.getViewDrawLoading()]
					},
					{
						id: self.uid,
						hidden: true
					}
				]
			};
		}

		return view;
	}

	loadData() {

		let self = this;
		
		if (self.moduleAction.id === self.config.moduleAction.add) {

			addTravelTime(onLoadedModuleAction);
		}
		else if (self.moduleAction.id === self.config.moduleAction.edit) {
			
			editTravelTime(onLoadedModuleAction);
		}
		else if (self.moduleAction.id === self.config.moduleAction.delete) {

			deleteTravelTime();
		}
		else if (self.moduleAction.id === self.config.moduleAction.assignPanels) {

			assignPanels();
		}
		else if (self.moduleAction.id === self.config.moduleAction.unassignPanels) {

			unassignPanels();
		}
		else if (self.moduleAction.id === self.config.moduleAction.viewPanel) {

			viewPanel();
		}
		
		function onLoadedModuleAction() {
			
			$$(self.uid).show();
			$$("loading-" + self.uid).hide();	
		}
		
		function addTravelTime(onLoadedModuleAction) {
			
			drawContent();
			
			util.ui.setTitle(self.args.containerId, i18n.t("LBL_ADD_NEW_TRAVEL_TIME"));
			
			onLoadedModuleAction();
		}
		
		function editTravelTime(onLoadedModuleAction) {
			
			let elementId = self.args.selectedPks[0];
			
			if (elementId) {
				
				travelTimeConfigUtils.getTravelTime(self.config, elementId).then((travelTime) => {
					
					self.travelTime = travelTime;
					
					drawContent();
					setValuesForm();
					
					util.ui.setTitle(self.args.containerId, i18n.t("LBL_EDIT_TRAVEL_TIME") + " - " + i18n.t(travelTime.alias));
					
					onLoadedModuleAction();
				});
			}
			
			function setValuesForm() {

				let form = $$("form-" + self.uid);
	
				if (form && self.travelTime) {
	
					form.setValues({
						alias: self.travelTime.alias,
						description: self.travelTime.description,
						pkIni: self.travelTime.pkIni,
						pkEnd: self.travelTime.pkEnd,
						message: self.travelTime.message,
						descendingDirection: IS_TRUE(self.travelTime.descendingDirection)
					});
				}
			}	
		}
		
		function deleteTravelTime() {
			
			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
				
				let message = i18n.t("LBL_QUESTION_DELETE_TRAVEL_TIMES_DESCRIPTION"); 
						
				(new MessageBox(MessageBox.CONFIRM, message, null, onAccept, null)).show();
				
				function onAccept() {

					let itemsReponse = 0;
					
					self.args.selectedPks.forEach(e => {
						
						webix.ajax().del(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.deleteElement, { id: e })).then(function(deleteResponse) {

							if (deleteResponse != null) {

								let deleteItem = deleteResponse.json();
								window.em.delete(EntityType.ELEMENT, deleteItem.id);
							}

							if (self.args.selectedPks.length === ++itemsReponse) {

								self.args.callbacks[0]();
							}
						});
					});
				}
			}
		}
		
		function assignPanels() {
			
			let elementId = self.args.selectedPks[0];
			
			if (elementId) {
			
				(new TravelTimeConfigAssignPanel(self.config, self.args, elementId)).load();	
			}
		}
		
		function unassignPanels() {
			
			let elementId = self.args.selectedPks[0];
			
			if (elementId) {
			
				(new TravelTimeConfigUnassignPanels(self.config, self.args, elementId)).load();	
			}
		}
		
		function viewPanel() {
			
			let elementId = self.args.selectedPks[0];
			
			if (elementId) {
			
				(new TravelTimeConfigViewPanel(self.config, self.args, elementId)).load();	
			}
		}

		function drawContent() {

			let viewContent;

			viewContent = {
				id: self.uid,
				css: { "box-sizing": "border-box" },
				rows: [
					getHeader(),
					{ height: 15 },
					{
						cols: [
							{ width: 15 },
							getTabs(),
							{ width: 15 }
						]
					},
					getButtons(),
					{ height: 15 }
				]
			};
			
			webix.ui(viewContent, $$(self.uid));
			
			if (self.travelTimeConfigSectionDetail) {
				
				self.travelTimeConfigSectionDetail.initialize();
			}
			
			function getHeader() {

				let header = {};
				let label = undefined;
				let description = undefined;

				if (self.moduleAction.id === self.config.moduleAction.edit) {

					label = "LBL_EDIT_TRAVEL_TIME";
					description = "LBL_EDIT_TRAVEL_TIME_DESCRIPTION";

				} else if (self.moduleAction.id === self.config.moduleAction.add) {

					label = "LBL_ADD_NEW_TRAVEL_TIME";
					description = "LBL_ADD_TRAVEL_TIME_DESCRIPTION";
				}

				header = {
					id: "viewHeader-" + self.uid,
					cols: [
						{
							view: "label",
							label: "<img src='img/grid/travel_times.png' class='grid-icon'/>",
							height: 75,
							css: "global-header-icon"
						},
						{
							view: "label",
							height: 75,
							width: 450,
							id: "label-bottom-" + self.uid,
							template: "<span>" + i18n.t(label) + "</span> <br> <span style='font-size: 13px'>" + i18n.t(description) + "</span>",
							css: "global-header-label"
						}
					]
				};

				return header;
			}

			function getTabs() {

				let tabs;

				tabs = {
					view: "tabview",
					id: "tabview-" + self.uid,
					borderless: true,
					css: "secondary-tab",
					cells: [],
					tabbar: {
						tabMargin: 6,
					}
				}

				tabs.cells.push({
					header: i18n.t("LBL_INFO"),
					body: getInfoView(),
				});

				tabs.cells.push({
					header: i18n.t("LBL_TRAVEL_TIME_SECTIONS"),
					body: getSectionsView()
				});

				function getInfoView() {

					const r = /^-?(?:(?:0|[1-9][0-9]*)(?:\.[0-9]*)?|\.[0-9]+)$/;
					let infoView;
										
					infoView = {
						id: "form-" + self.uid,
						view: "form",
						css: "bordered-layout-without-border-top",
						elementsConfig: { labelAlign: "left" },
						elements: [
							{
								cols: [
									{ width: 45 },
									{
										rows: [
											{},
											{ view: "text", name: "alias", required: true, label: i18n.t("LBL_ALIAS"), labelWidth: 275 },
											{ view: "text", name: "description", required: false, label: i18n.t("LBL_DESCRIPTION"), labelWidth: 275 },
											{ view: "text", name: "message", required: true, label: i18n.t("LBL_MESSAGE"), labelWidth: 275 },
											{
												view: "text",
												name: "pkIni",
												required: true, 
												label: i18n.t("LBL_PK_INI"), 
												labelWidth: 275,
												css: "inputdialogbox-counter",
												previousValue: "",
												value: "0.0",
												keyPressTimeout: 10,
												validate: function(value) {
					
													let floatValue = parseFloat(value);
					
													return (typeof floatValue === 'number');
												},
												on: {
													onKeyPress: function(code, e) {
					
														if (e.key === "Dead") {
					
															this.disable();
															this.enable();
															this.focus();
														}
					
														this.config.previousValue = this.getValue();
														return code == 46 || code == 8 || code == 9 || (code >= 35 && code <= 40) || (code >= 48 && code <= 57) || code == 190 || code == 173;
													},
													onTimedKeyPress: function() {
					
														if (this.getValue() != "" && this.getValue() != "-" && !r.test(this.getValue())) {
					
															this.setValue(this.config.previousValue);
															this.focus();
															this.getInputNode().setSelectionRange(this.getValue().length, this.getValue().length);
														}
													},
													onAfterRender: function() {
					
														//this.focus();
					
														if (this.getValue()) {
					
															this.getInputNode().setSelectionRange(0, this.getValue().length);
														}
													}
												}
											},
											{
												view: "text",
												name: "pkEnd",
												required: true, 
												label: i18n.t("LBL_PK_END"), 
												labelWidth: 275,
												css: "inputdialogbox-counter",
												previousValue: "",
												value: "0.0",
												keyPressTimeout: 10,
												validate: function(value) {
					
													let floatValue = parseFloat(value);
					
													return (typeof floatValue === 'number');
												},
												on: {
													onKeyPress: function(code, e) {
					
														if (e.key === "Dead") {
					
															this.disable();
															this.enable();
															this.focus();
														}
					
														this.config.previousValue = this.getValue();
														return code == 46 || code == 8 || code == 9 || (code >= 35 && code <= 40) || (code >= 48 && code <= 57) || code == 190 || code == 173;
													},
													onTimedKeyPress: function() {
					
														if (this.getValue() != "" && this.getValue() != "-" && !r.test(this.getValue())) {
					
															this.setValue(this.config.previousValue);
															this.focus();
															this.getInputNode().setSelectionRange(this.getValue().length, this.getValue().length);
														}
													},
													onAfterRender: function() {
					
														//this.focus();
					
														if (this.getValue()) {
					
															this.getInputNode().setSelectionRange(0, this.getValue().length);
														}
													}
												}
											},
											//{ view: "text", name: "pkIni", type: "number", required: true, label: i18n.t("LBL_PK_INI"), labelWidth: 275 },
											//{ view: "text", name: "pkEnd", type: "number", required: true, label: i18n.t("LBL_PK_END"), labelWidth: 275 },
											{ view: "checkbox",	name: "descendingDirection", label: i18n.t("LBL_DESCENDING_DIRECTION"), labelWidth: 275, value: false },
											{}
										]
									},
									{ width: 45 }
								]
							}
						],
						rules: {
							"alias": webix.rules.isNotEmpty,
							"message": webix.rules.isNotEmpty,
							"pkIni": webix.rules.isNotEmpty,
							"pkEnd": webix.rules.isNotEmpty
						}
					};

					return infoView;
				}
				
				function getSectionsView() {

					let sectionsView;
					
					self.travelTimeConfigSectionDetail = new TravelTimeConfigSectionDetail(self.config, self.travelTime);
					
					sectionsView = self.travelTimeConfigSectionDetail.getView();
					
					return sectionsView;
				}

				return tabs;
			}

			function getButtons() {

				let viewToolbarActions = {};

				viewToolbarActions = {
					id: "buttons-" + self.uid,
					borderless: true,
					css: { "margin-top": "1px !important" },
					rows: [
						{
							cols: [
								{ width: 15 },
								{
									view: "button",
									value: i18n.t("LBL_CANCEL"),
									width: 130,
									height: 41,
									css: "dialog-cancel",
									click:
										function() {
											$$(self.args.containerId).close();
										}
								},
								{},
								{
									view: "button",
									value: i18n.t("LBL_ACCEPT"),
									width: 130,
									height: 41,
									css: "dialog-accept",
									click:
										function() {

											let validation = $$("form-" + self.uid).validate({ disabled: true });

											if (validation) {

												let ms = new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_TRAVEL_TIME_SAVE"), null, onAccept, null);
												ms.show();

											} else {

												$$("form-" + self.uid).show();
											}

											function onAccept() {

												let formValues = $$("form-" + self.uid).getValues();
												
												self.travelTime.alias = formValues.alias;
												self.travelTime.description = formValues.description;
												self.travelTime.message = formValues.message;
												self.travelTime.pkIni = formValues.pkIni;
												self.travelTime.pkEnd = formValues.pkEnd;
												self.travelTime.descendingDirection = formValues.descendingDirection;
												
												let body = {
													
													element: {
														
														id: self.travelTime.id,
														alias: self.travelTime.alias,
														description: self.travelTime.description,
														elementTypeId: self.config.travelTime.elementTypeId,
														childIds: self.travelTime.sections.map(e => e.id)
													}
												}
												
												if (self.moduleAction.id === self.config.moduleAction.add) {
													
													body.element.elementValues = [
															{ elementTypeParamId: self.config.travelTime.params.direction, value: self.travelTime.descendingDirection ? "true": "false" },
															{ elementTypeParamId: self.config.travelTime.params.pkIni, value: self.travelTime.pkIni },
															{ elementTypeParamId: self.config.travelTime.params.pkEnd, value: self.travelTime.pkEnd },
															{ elementTypeParamId: self.config.travelTime.params.message, value: self.travelTime.message }
														];
													
													webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.config.restEndpoints.createElement, JSON.stringify(body))
														.then(function(createItemResponse) {
															
															if (createItemResponse) {

																let createItem = createItemResponse.json();
																
																if (createItem) {
																
																	window.em.add(EntityType.ELEMENT, createItem.id, createItem);	
																}
																else {
																	
																	(new MessageBox(MessageBox.ERROR, i18n.t("LBL_ERROR_OF_PERSISTENCE"), null, null, null)).show();
																}
															}
	
															if (self.args.callbacks) {
	
																self.args.callbacks[0]();
															}
														}
													);
												}
												else if (self.moduleAction.id === self.config.moduleAction.edit) {
													
													body.element.elementValues = [
															{ id: util.element.getElementValueId(self.config.travelTime.params.direction, self.travelTime.id), value: self.travelTime.descendingDirection ? "true" : "false" },
															{ id: util.element.getElementValueId(self.config.travelTime.params.pkIni, self.travelTime.id), value: self.travelTime.pkIni },
															{ id: util.element.getElementValueId(self.config.travelTime.params.pkEnd, self.travelTime.id), value: self.travelTime.pkEnd },
															{ id: util.element.getElementValueId(self.config.travelTime.params.message, self.travelTime.id), value: self.travelTime.message }
														];
													
													webix.ajax().headers(HEADER_POST).put(appConfig.restUrl + self.config.restEndpoints.updateElement, JSON.stringify(body))
														.then(function(updateItemResponse) {
															
															if (updateItemResponse) {
															
																let updateItem = updateItemResponse.json();
																
																if (updateItem) {
																	
																	window.em.update(EntityType.ELEMENT, updateItem.id, updateItem);	
																}
																else {
																	
																	(new MessageBox(MessageBox.ERROR, i18n.t("LBL_ERROR_OF_PERSISTENCE"), null, null, null)).show();
																}
															}

															if (self.args.callbacks) {

																self.args.callbacks[0]();
															}
														}
													);
												}												
												
												$$(self.args.containerId).close();
											}
										}
								},
								{ width: 15 }
							]
						}
					]
				}

				return viewToolbarActions;
			}
		}
	}
}