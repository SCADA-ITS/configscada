import EquipmentViewerInfo from "./equipmentViewerInfo.js";
import EquipmentViewerProperties from "./equipmentViewerProperties.js";
import { util } from "../../lib/util.js";
import { adapter } from "../../lib/adapter.js";
import MessageBox from "../../component/messageBox.js";
import BackOfficeModule from "../backOffice/backOfficeModule.js";


export default class EquipmentViewerContent {

	constructor(parent, element, equipmentViewer) {

		this.parent = parent;
		this.element = element;

		this.uid = parent.uid;

		this.equipmentViewerInfo = undefined;

		this.equipmentViewer = equipmentViewer;
		this.idStompSubscriptor = undefined;
		this.currentElementState = element.elementState;

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

						let color = util.element.getColorByState(self.element.elementState);

						if (data && data.elementState && data.elementState.currentElementTypeState) {

							let elementTypeState = self.elementType.elementTypeStates.find(e => e.id === data.elementState.currentElementTypeState.id);

							if (elementTypeState) {

								data.elementState.currentElementTypeState = elementTypeState;
								color = util.element.getColorByState(data.elementState);
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

									if (data && data.elementState && data.elementState.currentElementTypeState) {

										let elementTypeState = self.elementType.elementTypeStates.find(e => e.id === data.elementState.currentElementTypeState.id);

										if (elementTypeState) {

											self.currentElementState = data.elementState;
											self.currentElementState.currentElementTypeState = elementTypeState;
										}
									}

									let elementTypeStateLabel = util.element.getAliasState(self.currentElementState);
									let color = util.element.getColorByState(self.currentElementState);

									let commentIcon = self.currentElementState && self.currentElementState.maintenanceComment ? `<span id="comment_icon" class='webix_nav_item mdi mdi-comment'></span>` : ``;

									let res = `<image class="ims-incident-report-icon" src="img/${equipmentIcon}" onerror="this.onerror=null;this.src='img/not_found_50.png';"/>
											<div class="ims-incident-report-title" id="equipDescription" ${ellipsis} title="${elementLabelDescription + ' - ' + self.element.alias}">
												${elementLabelDescription + ' - ' + self.element.alias}
											</div>
											<div class="equip-viewer-header">
												<span class="badge" style="background: ${color}; ">${elementTypeStateLabel}</span>
												${commentIcon}
											</div>`;

									if (data.clockUpdate) {

										let timeService = window.sm.getService(Services.TIME_SERVICE);

										res = `<image class="ims-incident-report-icon" src="img/${equipmentIcon}" onerror="this.onerror=null;this.src='img/not_found_50.png';"/>
											<div class="ims-incident-report-title" id="equipDescription" ${ellipsis}>
												${elementLabelDescription + ' - ' + self.element.alias}
											</div>
											<div class="equip-viewer-header">
												<span class="badge" style="background: ${color}; ">${elementTypeStateLabel}</span>
												${commentIcon}
												<span class='clock-ico ico webix_icon mdi mdi-clock-time-four-outline'></span>
												<span class='clock-text'>${util.toLocaleString(Math.round(timeService.getEpoch() / 1000))}</span>
											</div>`;
									}

									return res;
								}
							},
							on: {
								onAfterRender: function() {

									if (self.currentElementState && self.currentElementState.maintenanceComment) {

										let headerElement = $$("view-main-content-header-element-" + self.uid)

										if (headerElement) {

											let commentIcon = headerElement.getNode().querySelector('#comment_icon');

											if (commentIcon) {

												let tooltip = document.createElement("div");
												tooltip.setAttribute("id", "comment_icon-icon-tooltip");
												tooltip.setAttribute("class", "graphic-icon-tooltip");
												tooltip.setAttribute("display", "none");
												tooltip.setAttribute("style", "position: absolute; display: none;");
												document.body.appendChild(tooltip);

												commentIcon.addEventListener('click', function() {

													(new MessageBox(MessageBox.INFO, i18n.t("LBL_ELEMENT_STATE_COMMENT"), { textMultiline: self.currentElementState.maintenanceComment }, null, null)).show();
												});

												commentIcon.addEventListener('mouseover', function(e) {

													let tooltip = document.getElementById("comment_icon-icon-tooltip");

													if (tooltip) {

														tooltip.innerHTML = `<div>${i18n.t("LBL_ELEMENT_STATE_COMMENT")}</div>`;

														tooltip.style.display = "block";
														tooltip.style.left = e.pageX + 10 + 'px';
														tooltip.style.top = e.pageY + 10 + 'px';
														tooltip.style.zIndex = 99999;
													}
												});

												commentIcon.addEventListener('mouseout', function() {

													var tooltip = document.getElementById("comment_icon-icon-tooltip");

													if (tooltip) {

														tooltip.style.display = "none";
													}
												});

											}
										}

									}
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
				let noMaintenanceMode = !(self.elementType.maintenanceState) || !self.parent.config.moduleAction.maintenanceMode;
				let numberOfHeaderButtons = 0;
				let toolbarButtonsWidth = 0;

				if (self.parent.config.moduleAction.commandEquipments) {

					commandsModuleActionId = self.parent.config.moduleAction.commandEquipments;
					commandsModule = util.module.getFromModuleAction(commandsModuleActionId);

					isAllowedCommands = util.module.isAllowedModuleAction(window.em.getAll(EntityType.MODULE), commandsModuleActionId);
				}

				if ((self.equipmentViewer.equipViewerConfigs && self.equipmentViewer.equipViewerConfigs.length > 0) || self.element.elementValues) {

					noProperties = (self.equipmentViewer.noShowProperties) ? true : false;
				}

				if (!noProperties) ++numberOfHeaderButtons;
				if (!noMaintenanceMode) ++numberOfHeaderButtons;
				if (self.equipmentViewer.showActions && isAllowedCommands) ++numberOfHeaderButtons;
				if (self.element.tm && self.element.tm != undefined) ++numberOfHeaderButtons;
				if (self.element.doc && self.element.doc != undefined) ++numberOfHeaderButtons;
				if (getBackOffice()) ++numberOfHeaderButtons;

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
									label: i18n.t("LBL_MORE_INFO"),
									tooltip: i18n.t("LBL_MORE_INFO_DESCRIPTION"),
									type: "iconTop",
									icon: "mdi mdi-file-document",
									hidden: !getBackOffice(),
									on: {

										onItemClick() {

											let mod = util.module.getFromModuleAction(BackOfficeModule.Config.MODULE_ACTION_OPEN_ID);

											let moduleAction = { id: BackOfficeModule.Config.MODULE_ACTION_OPEN_ID };

											let args = {
												backOffice: getBackOffice()
											};

											util.module.open(mod, moduleAction, VIEW_TYPE_WINDOW, null, args);
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
								{
									view: "button",
									width: 135,
									label: i18n.t("LBL_EQUIPMENT_DOC"),
									tooltip: i18n.t("LBL_EQUIPMENT_DOC"),
									type: "iconTop",
									icon: "mdi mdi-file-download",
									hidden: (self.element.doc && self.element.doc != undefined) ? false : true,
									on: {
										onItemClick: function() {

											let element = document.createElement('a');
											element.setAttribute('href', self.element.doc);
											element.setAttribute('download', self.element.doc);
											document.body.appendChild(element);
											element.click();
											document.body.removeChild(element);
										}
									},
								},
								{
									view: "button",
									width: 135,
									label: i18n.t("LBL_EQUIP_VIEWER_MAINTENANCE_MODE"),
									tooltip: i18n.t("LBL_EQUIP_VIEWER_MAINTENANCE_MODE_DESCRIPTION"),
									type: "iconTop",
									icon: "mdi  mdi-wrench",
									hidden: noMaintenanceMode,
									on: {

										onItemClick() {

											let module = util.module.getFromModuleAction(self.parent.config.moduleAction.maintenanceMode);

											if (module) {

												let moduleAction = { id: self.parent.config.moduleAction.maintenanceMode };
												let args = { selectedPks: [self.element.id] };

												util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
											}

										}
									}
								},
								{ width: 5 }
							]
						},
						{}
					]
				};

				return viewHeaderButtons;
			}

			function getBackOffice() {

				let backOffice;

				backOffice = self.parent.config.backOffice ? self.parent.config.backOffice : undefined;
				backOffice = self.element.elementType.elementBackOffice ? JSON.parse(self.element.elementType.elementBackOffice) : backOffice;

				if (backOffice && self.element.id) {
					backOffice.filterColumnValue = self.element.id;
				}

				return backOffice;
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
					keepViews: true
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
		let tabCount = 0;

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
						css: "bordered-layout-without-border-top",
						cols: [equipmentViewerInfoView]
					}
				});
			}

			// Load tabs for modules
			addTabs();

			if (extraTabs) {

				tabCount = self.equipmentViewer.equipViewerTabs.length;

				self.equipmentViewer.equipViewerTabs.forEach(e => {

					let module = util.module.getFromModuleAction(e.moduleActionId);

					if (module) {

						addModule(module, e);
					}
				});

			} else {

				contentTabs.removeView("tab2remove" + self.uid);
			}

			if (self.equipmentViewerInfo) {

				self.equipmentViewerInfo.setSegmented();
			}

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
							css: "bordered-layout-without-border-top",
							rows: []
						}
					});
				});

				extraTabs = true;

			}
		}

		function addModule(module, equipViewerTab) {

			const CORRECTION_FACTOR = { WIDTH: 10, HEIGHT: 55 };
			let moduleAction = { id: equipViewerTab.moduleActionId };
			let args = { selectedPks: [self.element.id], containerId: contentTabs.config.id, width: contentTabs.$width - CORRECTION_FACTOR.WIDTH, height: contentTabs.$height - CORRECTION_FACTOR.HEIGHT };

			util.module.open(module, moduleAction, VIEW_TYPE_CUSTOM, drawTabContent, args);

			function drawTabContent(view) {

				let newContentTab = {
					id: "view-main-content-tab_body-" + moduleAction.id + "-" + self.uid,
					css: "bordered-layout-without-border-top",
					cols: [view]
				};

				webix.ui(newContentTab, $$("view-main-content-tab_body-" + moduleAction.id + "-" + self.uid));

				contentTabs.getTabbar().hideOption("view-main-content-tab_body-" + moduleAction.id + "-" + self.uid);
				contentTabs.getTabbar().setValue("view-main-content-tab_body-" + moduleAction.id + "-" + self.uid);
				contentTabs.getTabbar().showOption("view-main-content-tab_body-" + moduleAction.id + "-" + self.uid);
				contentTabs.removeView("tab2remove" + self.uid);

				if (!(--tabCount)) {

					if ($$("view-main-content-tab-info-" + self.uid) != undefined) {

						contentTabs.getTabbar().setValue("view-main-content-tab-info-" + self.uid);
					}

					// Select initial tab for modules if configured
					if (self.equipmentViewer.initialTab) {

						let initialTab = self.equipmentViewer.equipViewerTabs.find(tab => tab.id === self.equipmentViewer.initialTab);

						if (initialTab) {

							contentTabs.getTabbar().setValue("view-main-content-tab_body-" + initialTab.moduleActionId + "-" + self.uid);
						}
					}
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

				let element = adapter.ws.element.getByState(data, elementTypeParams);

				if (element) {

					updateElement();

					let elementHeader = $$("view-main-content-header-element-" + self.uid);

					if (elementHeader) {

						elementHeader.setValues({});
						elementHeader.setValues({ clockUpdate: true });
					}

					if (element.elementState) {

						let elementStateBar = $$("view-main-content-header-bar-state-" + self.uid);

						if (elementHeader) {

							elementHeader.setValues({ elementState: element.elementState });
						}

						if (elementStateBar) {

							elementStateBar.setValues({ elementState: element.elementState });
						}

						if (self.equipmentViewerInfo) {

							self.equipmentViewerInfo.updateState(element.elementState);
						}
					}

					if (Array.isArray(element.alarms) && element.alarms.length > 0) {

						// TODO: Update alarms
					}

					if (self.equipmentViewerInfo && Array.isArray(element.elementValues) && element.elementValues.length > 0) {

						self.equipmentViewerInfo.updateMeasures(element.elementValues);
					}
				}

				function updateElement() {

					if (Array.isArray(element.elementValues) && element.elementValues.length > 0) {

						let sameElement = self.element.elementValues.find(e => e.id === element.elementValues[0].id);

						if (sameElement) {

							if (sameElement.value && element.elementValues[0].value && (sameElement.value != element.elementValues[0].value)) {

								sameElement.value = element.elementValues[0].value;
								sameElement.elementValueState = element.elementValues[0].elementValueState;
							}
						}
					}

					if (element.elementTypeState) {

						self.element.elementTypeState = element.elementTypeState;
					}

					if (element.elementState) {

						self.element.elementState = element.elementState;
					}
				}
			}
		}
	}
}	
