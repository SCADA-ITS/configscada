import { util } from "../../../lib/util.js";
import { adapter } from "../../../lib/adapter.js";
import SearchDialogBox from "../../../component/searchDialogBox.js";
import GraphicIcon from "../../../component/graphicIcon/graphicIcon.js";

const VMS_MAX_WIDTH = 525;
const VMS_MAX_HEIGHT = 200;

export default class VmsSignalingRealTimePanel {

	constructor(config, element) {

		this.config = config;
		this.element = element;
		this.uid = webix.uid();

		this.idStompSubscriptor = undefined;
	}

	getView() {

		let self = this;
		let realTimePanel = {};

		realTimePanel = {
			height: 220,
			cols: [
				{ width: 60 },
				getPanel(),
				{ width: 30 },
				getInfo(),
				{ width: 30 }
			]
		};

		connectWS();

		function getPanel() {

			let viewGraphicIcon = {};

			viewGraphicIcon = {
				id: "graphic-icon-info-" + self.uid,
				height: VMS_MAX_HEIGHT,
				width: VMS_MAX_WIDTH,
				rows: [
					{},
					{
						height: 200,
						borderless: true,
						template: function() {

							return `<div><svg id=${"view-main-info-image-svg-" + self.uid}></svg></div>`;
						},
						on: {
							onAfterRender: function() {

								drawGraphicIcon(VMS_MAX_WIDTH, VMS_MAX_HEIGHT);
							}
						}
					},
					{}
				]
			};

			function drawGraphicIcon(w, h) {

				let graphicIcon = webix.copy(self.element.graphicIcon);

				if (graphicIcon) {

					graphicIcon.width = w;
					graphicIcon.height = h;

					const lazy = async _ => {

						let jsFile = util.getContextPath() + '/' + graphicIcon.jsDrawFile;

						const mod = await import(jsFile);

						let layerElement = {
							graphicIcon: graphicIcon
						};

						let s;
						s = Snap("#" + "view-main-info-image-svg-" + self.uid);
						s.attr({ width: w, height: h });

						let container = {
							type: GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW,
							obj: s
						};

						self.graphicIconInstance = new mod.default(layerElement, self.element, container, null);

						self.graphicIconInstance.load(function() {

							self.graphicIconInstance.loadRender(function() {

								if (self.element.elementState) {

									self.graphicIconInstance.updateElementState(self.element.elementState);
								}

								if (Array.isArray(self.element.elementValues) && self.element.elementValues.length > 0) {

									self.graphicIconInstance.updateValues(self.element.elementValues);
								}

								if (Array.isArray(self.element.alarms) && self.element.alarms.length > 0) {

									self.graphicIconInstance.updateAlarms(self.element.alarms);
								}
							});
						});
					}

					lazy();
				}
			}

			return viewGraphicIcon;

		}

		function getInfo() {

			let viewMainInfo;

			viewMainInfo = {
				width: 300,
				rows: [
					{ height: 30 },
					{
						height: 65,
						borderless: true,
						template: function(data) {

							let ellipsis;
							let tooltip = "";
							let labelType = i18n.t("LBL_ELEMENT_TYPE");
							let labelTypeValue = i18n.t(self.element.elementType.labelDescription ? self.element.elementType.labelDescription : self.element.elementType.labelAlias);

							if (self.element.elementSubtype) {

								labelType += " (" + i18n.t("LBL_ELEMENT_SUBTYPE") + ")";
								labelTypeValue += " (" + i18n.t(self.element.elementSubtype.labelDescription ? self.element.elementSubtype.labelDescription : self.element.elementSubtype.labelAlias) + ")";
							}

							let textWidth = util.ui.calculateTextWidth(labelTypeValue, "Roboto", 14, 0.2);

							if (textWidth > 305) {

								ellipsis = `style='max-width: ${300 * 0.85}px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis'`;
								tooltip = `title="${labelTypeValue}"`;
							}

							return `<div class="equip-viewer-info">
											<span class="ico webix_icon mdi mdi-view-grid"></span>
											<span class="title">${labelType}</span>
											<span class="value" ${tooltip} ${ellipsis}>${labelTypeValue}</span>
										</div>`;
						}
					},
					{
						height: 65,
						borderless: true,
						tooltip: {
							template: function() {

								if (self.element.location && Array.isArray(self.element.location.stretchs) && self.element.location.stretchs.length > 1) {

									let tooltip = `<div class='title'>${self.element.location.alias}</div>`;

									for (let stretch of self.element.location.stretchs) {
										tooltip += `<div style="color: #35445f; padding-top: 5px">${stretch.alias} - ${stretch.road.alias}</div>`;
									}

									return tooltip;

								} else {

									return "";
								}
							},
							css: "graphic-icon-tooltip"
						},
						template: function(data) {

							if (self.element.location) {

								if (Array.isArray(self.element.location.stretchs) && self.element.location.stretchs.length > 0) {

									let ellipsis = (self.element.location.stretchs.length > 1) ? "..." : "";

									return `<div class="equip-viewer-info">
											<span class="ico webix_icon mdi mdi-map-marker"></span>
											<span class="title">${i18n.t("LBL_ELEMENT_LOCATION")}</span>
											<span class="value link-value">${self.element.location.alias} - ${self.element.location.stretchs[0].alias} - ${self.element.location.stretchs[0].road.alias} ${ellipsis}</span>									
										</div>`;

								} else {

									return `<div class="equip-viewer-info">
											<span class="ico webix_icon mdi mdi-map-marker"></span>
											<span class="title">${i18n.t("LBL_ELEMENT_LOCATION")}</span>
											<span class="value link-value">${self.element.location.alias}</span>									
										</div>`;
								}

							} else {

								return `<div class="equip-viewer-info">
											<span class="ico webix_icon mdi mdi-map-marker"></span>
											<span class="title">${i18n.t("LBL_ELEMENT_LOCATION")}</span>
											<span class="value"> - </span>									
										</div>`;
							}
						},
						on: {
							onAfterRender: function() {

								let linkLabel = $$(this).getNode().querySelector('.link-value');

								if (linkLabel) {

									linkLabel.addEventListener('click', function() {

										if (self.config.moduleAction && self.config.action.showInMapOrSynoptic) {

											let mod = util.module.getFromModuleAction(self.config.action.showInMapOrSynoptic);

											let moduleAction = {
												id: self.config.action.showInMapOrSynoptic
											};

											let args = {
												selectedPks: [self.element.id]
											};

											util.module.open(mod, moduleAction, undefined, null, args, undefined);
										}
									});
								}
							}
						}
					},
					{
						height: 60,
						borderless: true,
						template: function(data) {

							return `<div class="equip-viewer-info">
											<span class="ico webix_icon mdi mdi-view-headline"></span>
											<span class="title">${i18n.t("LBL_ELEMENT_DESCRIPTION")}</span>
											<span title="${i18n.t("LBL_EQUIP_VIEWER_PROPERTIES_DESCRIPTION")}" class="ico webix_icon mdi mdi-information signaling-properties-info-icon"></span>
											<span class="value">${self.element.description}</span>
										</div>`;
						},
						on: {
							onAfterRender: function() {

								let infoIcon = $$(this).getNode().querySelector('.signaling-properties-info-icon');

								if (infoIcon) {

									infoIcon.addEventListener('click', getProperties);
								}
							}
						}
					},
					{}
				]
			};

			function getProperties() {

				let elementValues = [];
				let dialogEntity = webix.copy(SearchDialogBox.CUSTOM);
				let label = i18n.t("LBL_EQUIPMENT_PROPERTIES") + " - " + self.element.alias;

				dialogEntity.data = [];

				dialogEntity.pkField = "id";
				dialogEntity.fields = ["element=LBL_PROPERTY", "elementType=LBL_PROPERTY_VALUE"];
				dialogEntity.height = 600;
				dialogEntity.width = 800;

				if (self.element.equipmentViewer && Array.isArray(self.element.equipmentViewer.equipViewerConfigs) && self.element.equipmentViewer.equipViewerConfigs.length > 0) {

					for (let equipViewerConfig of self.element.equipmentViewer.equipViewerConfigs) {

						let equipConfigs = self.element.elementValues.find(element => {
							return element.elementTypeParam.id === equipViewerConfig.voEntity.id;
						});

						if (equipConfigs) {

							elementValues.push(equipConfigs);

						} else {

							elementValues = self.element.elementValues;
						}
					}

				} else {

					if (Array.isArray(self.element.elementValues)) {

						elementValues = self.element.elementValues;
					}
				}

				for (let elementValue of elementValues) {

					dialogEntity.data.push({
						id: elementValue.elementTypeParam.id,
						element: i18n.t(elementValue.elementTypeParam.labelAlias),
						elementType: elementValue.value
					});
				}

				let s = new SearchDialogBox(label, dialogEntity);
				s.show();
			}

			return viewMainInfo;
		}

		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				let topic = self.config.topics.element + "." + self.element.elementType.id + "." + self.element.id;

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

					if (Array.isArray(element.elementValues) && element.elementValues.length > 0) {

						self.graphicIconInstance.updateValues(element.elementValues);
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

						if (self.graphicIconInstance) {

							self.graphicIconInstance.updateElementState(element.elementState);
						}
					}
				}
			}
		}

		return realTimePanel;
	}
}