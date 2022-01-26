import EquipmentViewerInfo from "./equipmentViewerInfo.js";
import EquipmentViewerProperties from "./equipmentViewerProperties.js";
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
		this.currentElementTypeState = element.elementTypeState;

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
				//{ height: 3 },
				{
					cols: [
						{ width: 10 },
						{
							rows: [
								//{ height: 10 },
								getViewTabs(),
								//{ height: 20 }
							]
						},
						{ width: 10 }
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
									getViewHeaderButtons()
								]
							}
						]
					}
				]
			};

			function getViewHeaderBarState() {

				let viewHeaderBarState;
				
				viewHeaderBarState = {
					id: "view-main-content-header-bar-state-" + self.uid,
					borderless: true,
					height: 15,
					template: function(data) {

						let color = util.element.getColor(self.element.elementTypeState);

						if (data && data.elementTypeState) {

							let elementTypeState = self.elementType.elementTypeStates.find(e => e.id === data.elementTypeState.id);

							if (elementTypeState) {

								color = util.element.getColor(elementTypeState);
							}
						}

						return `<div style="height: 15px; background: ${color}"></div>`
					}
				};

				return viewHeaderBarState;
			}

			function getViewHeaderElement() {

				let elementLabelDescription = i18n.t(self.elementType.labelDescription);
				let container = $$(self.parent.args.containerId);
				let ellipsis;

				if (container) {

					ellipsis = `style='padding-left: 21px !important; overflow: hidden; white-space: nowrap; text-overflow: ellipsis'`;
				}

				let viewHeaderElement = {
					rows: [
						{},
						{
							id: "view-main-content-header-element-" + self.uid,
							height: 86,
							borderless: true,
							template: function(data) {

								if (self.elementType) {

									let equipmentIcon = self.elementType.elementTypePhoto;

									if (data && data.elementTypeState) {

										let elementTypeState = self.elementType.elementTypeStates.find(e => e.id === data.elementTypeState.id);

										if (elementTypeState) {

											self.currentElementTypeState = elementTypeState;
										}
									}

									let elementTypeStateLabel = i18n.t(self.currentElementTypeState.labelAlias);
									let color = util.element.getColor(self.currentElementTypeState);

									let res = `<image class="ims-incident-report-icon" src="img/${equipmentIcon}" onerror="this.onerror=null;this.src='img/not_found_50.png';"/>
											<div class="ims-incident-report-title" id="equipDescription" ${ellipsis} title="${elementLabelDescription + ' - ' + self.element.alias}">
												${elementLabelDescription + ' - ' + self.element.alias}
											</div>
											<div class="equip-viewer-header">
												<span class="badge" style="background: ${color}; ">${elementTypeStateLabel}</span>
											</div>`;

									if (data.clockUpdate) {
										
										let timeService = window.sm.getService(Services.TIME_SERVICE);

										res = `<image class="ims-incident-report-icon" src="img/${equipmentIcon}" onerror="this.onerror=null;this.src='img/not_found_50.png';"/>
											<div class="ims-incident-report-title" id="equipDescription" ${ellipsis}>
												${elementLabelDescription + ' - ' + self.element.alias}
											</div>
											<div class="equip-viewer-header">
												<span class="badge" style="background: ${color}; ">${elementTypeStateLabel}</span>
												<span class='clock-ico ico webix_icon mdi mdi-clock-time-four-outline'></span>
												<span class='clock-text'>${util.toLocaleString(Math.round(timeService.getEpoch() / 1000))}</span>
											</div>`;
									}

									return res;
								}
							}
						},
						{}
					]
				};

				return viewHeaderElement;
			}

			function getViewHeaderButtons() {

				let commandsModuleActionId;
				let commandsModule;
				let isAllowedCommands = false;
				let noProperties = true;
				let numberOfHeaderButtons = 0;
				let toolbarButtonsWidth = 0;

				if (self.parent.config.moduleAction.commandEquipments) {

					commandsModuleActionId = self.parent.config.moduleAction.commandEquipments;
					commandsModule = util.module.getFromModuleAction(commandsModuleActionId);

					isAllowedCommands = util.module.isAllowedModuleAction(window.em.getAll(EntityType.MODULE), commandsModuleActionId);
				}

				if ((self.equipmentViewer.equipViewerConfigs && self.equipmentViewer.equipViewerConfigs.length > 0) || self.element.elementValues) {

					noProperties = false;
				}

				numberOfHeaderButtons = (noProperties) ? numberOfHeaderButtons : ++numberOfHeaderButtons;
				numberOfHeaderButtons = !(self.equipmentViewer.showActions) || !isAllowedCommands ? numberOfHeaderButtons : ++numberOfHeaderButtons;
				numberOfHeaderButtons = (self.element.tm && self.element.tm != undefined) ? ++numberOfHeaderButtons : numberOfHeaderButtons;
						
				toolbarButtonsWidth = numberOfHeaderButtons * 150;

				let viewHeaderButtons;
				
				viewHeaderButtons = {
					width: toolbarButtonsWidth,
					rows: [
						{},
						{
							id: "view-main-content-header-buttons-" + self.uid,
							view: "toolbar",
							height: 70,
							borderless: true,
							css: "equip-viewer-header-actions",
							elements: [
								{},
								{
									view: "button",
									width: 135,
									label: i18n.t("LBL_EQUIP_VIEWER_PROPERTIES"),
									tooltip: i18n.t("LBL_EQUIP_VIEWER_PROPERTIES_DESCRIPTION"),
									type: "iconTop",
									icon: "mdi mdi-information",
									hidden: noProperties,
									on: {

										onItemClick() {

											let propertiesWindow = new EquipmentViewerProperties(self.parent, self.element, self.equipmentViewer);
											propertiesWindow.loadData();

										}
									}
								},
								{
									view: "button",
									width: 135,
									hidden: !(self.equipmentViewer.showActions) || !isAllowedCommands,
									label: i18n.t("LBL_EQUIP_VIEWER_COMMANDS"),
									tooltip: i18n.t("LBL_EQUIP_VIEWER_COMMANDS_DESCRIPTION"),
									type: "iconTop",
									icon: "mdi mdi-cog",
									on: {

										onItemClick() {

											if (commandsModule && commandsModuleActionId) {

												let moduleAction = { id: commandsModuleActionId };
						
												let args = { selectedPks: [self.element.id] };
						
												util.module.open(commandsModule, moduleAction, VIEW_TYPE_WINDOW, null, args);
											}
										}
									}
								},
								{
									view: "button",
									width: 135,
									hidden: (self.element.tm && self.element.tm != undefined) ? false : true,
									label: i18n.t("LBL_TERMINAL"),
									tooltip: i18n.t("LBL_TERMINAL_DESCRIPTION"),
									type: "iconTop",
									icon: "mdi mdi-console",
									on: {

										onItemClick() {

											const URL_TYPE = "url";
											let obj = JSON.parse(self.element.tm);

											if (obj.type === URL_TYPE) {

												let screenWidth = window.screen.width;
												let screenHeight = window.screen.height;
												let w = screenWidth;
												let h = screenHeight;
												let positionLeft = 0;
												let positionTop = 0;

												if (obj.data.features) {

													w = obj.data.features.width;
													h = obj.data.features.height;
													positionLeft = screenWidth / 2 - obj.data.features.width / 2;
													positionTop = screenHeight / 2 - obj.data.features.height / 2;

												}
												
												let features = "width=" + w + " ,height=" + h + ", top=" + positionTop + ", left=" + positionLeft + ", toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no";

												window.open(obj.data.url, null, features);
											}
										}
									}
								},
								{ width: 5}
							]
						},
						{}
					]
				};

				return viewHeaderButtons;
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
				multiview: {
					keepViews:true
		        },
				tabbar: {
					tabMargin: 6
				}
			};

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
		let extraTabs = false;
		let equipmentViewerInfoView = undefined;

		if (contentTabs) {

			// Load Info tab
			if (!self.equipmentViewer || // Not exist config 
				(self.equipmentViewer && self.equipmentViewer.showInfoTab)) { // Configured as not show

				self.equipmentViewerInfo = new EquipmentViewerInfo(self.parent, self.element, self.equipmentViewer);
				equipmentViewerInfoView = self.equipmentViewerInfo.getView();
				
				contentTabs.addView({
					header: i18n.t("LBL_EQUIPMENT_VIEWER_INFO"),
					body: {
						id: "view-main-content-tab-info-" + self.uid,
						//height: 605,
						cols: [equipmentViewerInfoView]

					}
				});
			}

			// Load tabs for modules
			addTabs();

			if (extraTabs) {

				self.equipmentViewer.equipViewerTabs.forEach(e => {

					let module = util.module.getFromModuleAction(e.moduleActionId);

					if (module) {

						addModule(module, e);
					}
				});
			}

			contentTabs.removeView("tab2remove" + self.uid);
			self.equipmentViewerInfo.resizeMeasures();	
		
			connectWS();

			$$(self.uid).attachEvent("onDestruct", function() {

				let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

				if (stompSubscriptorService) {

					stompSubscriptorService.unsubscribe(self.idStompSubscriptor);
				}
			});
		}

		function addTabs() {

			if (self.equipmentViewer && Array.isArray(self.equipmentViewer.equipViewerTabs) && self.equipmentViewer.equipViewerTabs.length > 0) {

				self.equipmentViewer.equipViewerTabs.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));

				self.equipmentViewer.equipViewerTabs.forEach(e => {

					contentTabs.addView({
						header: i18n.t(e.label),
						id: "view-main-content-tab-" + e.moduleActionId + "-" + self.uid,
						body: {
							id: "view-main-content-tab_body-" + e.moduleActionId + "-" + self.uid,
							rows: [{ }]
						}
					});
				});

				extraTabs = true;

			}
		}

		function addModule(module, equipViewerTab) {

			const CORRECTION_FACTOR = {WIDTH: 10, HEIGHT: 55};
			let moduleAction = { id: equipViewerTab.moduleActionId };
			let args = { selectedPks: [self.element.id], containerId: contentTabs.config.id, width: contentTabs.$width - CORRECTION_FACTOR.WIDTH, height: contentTabs.$height - CORRECTION_FACTOR.HEIGHT };

			util.module.open(module, moduleAction, VIEW_TYPE_CUSTOM, drawTabContent, args);

			function drawTabContent(view) {

				let newContentTab = {
					id: "view-main-content-tab_body-" + moduleAction.id + "-" + self.uid,
					//height: 605,
					cols: [view]
				};

				webix.ui(newContentTab, $$("view-main-content-tab_body-" + moduleAction.id + "-" + self.uid));

				contentTabs.getTabbar().hideOption("view-main-content-tab_body-" + moduleAction.id + "-" + self.uid);
				contentTabs.getTabbar().setValue("view-main-content-tab_body-" + moduleAction.id + "-" + self.uid);
				contentTabs.getTabbar().showOption("view-main-content-tab_body-" + moduleAction.id + "-" + self.uid);


				if ($$("view-main-content-tab-info-" + self.uid) != undefined) {

					contentTabs.getTabbar().setValue("view-main-content-tab-info-" + self.uid);
				}

				// Select initial tab for modules if configured
				if (equipViewerTab.id === self.equipmentViewer.initialTab) {

					contentTabs.getTabbar().setValue("view-main-content-tab_body-" + moduleAction.id + "-" + self.uid);
				}
			}
		}
		
		
		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				let topic = self.parent.config.topic + "." + self.element.elementType.id + "." + self.element.id;

				self.idStompSubscriptor = stompSubscriptorService.subscribe(appConfig.stompUrl, topic, onMessage);
			}

			function onMessage(data) {
				
				let elementTypeParams = [];
				
				if (Array.isArray(self.element.elementValues) && self.element.elementValues.length > 0) {
				
					elementTypeParams = self.element.elementValues.map(elementValue => elementValue.elementTypeParam);
				}
				
				let element = adapter.ws.element.get(data, elementTypeParams);
									
				if (element) {

					let elementHeader = $$("view-main-content-header-element-" + self.uid);

					if (elementHeader) {

						elementHeader.setValues({});
						elementHeader.setValues({ clockUpdate: true });
					}

					if (element.elementTypeState) {

						let elementStateBar = $$("view-main-content-header-bar-state-" + self.uid);

						if (elementHeader) {

							elementHeader.setValues({ elementTypeState: element.elementTypeState });
						}

						if (elementStateBar) {

							elementStateBar.setValues({ elementTypeState: element.elementTypeState });
						}

						if (self.equipmentViewerInfo) {

							self.equipmentViewerInfo.updateState(element.elementTypeState);
						}
					}

					if (Array.isArray(element.alarms) && element.alarms.length > 0) {

						// TODO: Update alarms
					}

					if (self.equipmentViewerInfo && Array.isArray(element.elementValues) && element.elementValues.length > 0) {

						self.equipmentViewerInfo.updateMeasures(element.elementValues);
					}
				}
			}
		}
	}
}	
