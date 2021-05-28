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
									getViewHeaderButtons()
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

						let color = self.element.elementTypeState.color ? self.element.elementTypeState.color : EquipmentViewerInfo.DEFAULT_STATE_COLOR(self.element.elementTypeState.id.substring(self.element.elementTypeState.id.lastIndexOf(":") + 1));

						if (data && data.elementTypeState) {

							let elementTypeState = self.elementType.elementTypeStates.find(e => e.id === data.elementTypeState.id);

							if (elementTypeState) {

								color = elementTypeState.color ? elementTypeState.color : EquipmentViewerInfo.DEFAULT_STATE_COLOR(elementTypeState.id.substring(elementTypeState.id.lastIndexOf(":") + 1));
							}
						}

						return `<div style="height: 15px; background: ${color}"></div>`
					}
				};

				return viewHeaderBarState;
			}

			function getViewHeaderElement() {

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
									let color = self.currentElementTypeState.color ? self.currentElementTypeState.color : EquipmentViewerInfo.DEFAULT_STATE_COLOR(self.currentElementTypeState.id.substring(self.currentElementTypeState.id.lastIndexOf(":") + 1));

									let timeService = window.sm.getService(Services.TIME_SERVICE);

									let res = `<image class="ims-incident-report-icon" src="img/${equipmentIcon}" onerror="this.onerror=null;this.src='img/not_found_50.png';"/>
											<div class="ims-incident-report-title">
												${self.element.alias + ' - ' + self.element.description}
											</div>
											<div class="equip-viewer-header">
												<span class="badge" style="background: ${color}; ">${elementTypeStateLabel}</span>
												<!-- <span class='clock-ico ico webix_icon mdi mdi-clock-time-four-outline'></span>
												<span class="clock-text">${util.toLocaleString(timeService.getDateTime())}</span> -->
											</div>`;

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

				let viewHeaderButtons = {
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
									on: {
		
										onItemClick() {
											let propertiesWindow = new EquipmentViewerProperties(self.parent, self.element, self.equipmentViewer);
											propertiesWindow.getView();
										}
									}
								},
								{
									view: "button",
									width: 135,
									hidden: !(self.equipmentViewer.showActions),
									label: i18n.t("LBL_EQUIP_VIEWER_COMMANDS"),
									tooltip: i18n.t("LBL_EQUIP_VIEWER_COMMANDS_DESCRIPTION"),
									type: "iconTop",
									icon: "mdi mdi-cog",
									on: {

										onItemClick() {

											if (self.parent.config.moduleAction.commandEquipments) {

												let moduleActionId = self.parent.config.moduleAction.commandEquipments;
												let module = util.module.getFromModuleAction(moduleActionId);

												if (module) {

													let moduleAction = { id: moduleActionId };

													let args = { selectedPks: [self.element.id] };

													util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
												}
											}

										}
									}
								},
								{ width: 20 }
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

		if (contentTabs) {

			// Load Info tab
			if (!self.equipmentViewer || // Not exist config 
				(self.equipmentViewer && self.equipmentViewer.showInfoTab)) { // Configured as not show

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
			let args = { selectedPks: [self.element.id], containerId: contentTabs.config.id };

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
				
				let elementTypeParams = [];
				
				if (Array.isArray(self.element.elementValues) && self.element.elementValues.length > 0) {
				
					elementTypeParams = self.element.elementValues.map(elementValue => elementValue.elementTypeParam);
				}
				
				let element = adapter.ws.element.get(data, elementTypeParams);
									
				if (element) {

					let elementHeader = $$("view-main-content-header-element-" + self.uid);

					if (elementHeader) {

						elementHeader.setValues({});
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
