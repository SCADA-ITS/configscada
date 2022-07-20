import { util } from "../../lib/util.js";
import GraphicIcon from "../../component/graphicIcon/graphicIcon.js";

export default class EquipmentViewerInfoIcon {

	constructor(parent, element, equipmentViewer) {

		this.parent = parent;
		this.element = element;
		this.equipmentViewer = equipmentViewer;
		this.graphicIconInstance = undefined;
		this.uid = parent.uid;
		this.fromTable = (parent.args.fromTable) ? true : false;

		if (element) {

			this.elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);
		}
	}

	getView() {

		let self = this;
		let viewMain = {};

		if (self.fromTable) {

			viewMain = {
				width: 420,
				rows: [
					{},
					{
						cols: [
							{ width: 10 },
							getViewMainImage(),
							{ width: 10 }
						]
					},
					{
						cols: [
							{},
							getViewMainInfo(),
							{}
						]
					},
					{}
				]
			};

		} else {

			viewMain = {
				cols: [
					{},
					getViewMainImage(),
					{},
					getViewMainInfo(),
					{}
				]
			};
		}

		function getViewMainImage() {

			let viewMainImage = {};

			if (self.equipmentViewer.graphicIcon) {

				viewMainImage = getGraphicIcon();
			}
			else {

				viewMainImage = getDefaultImage();
			}

			function getDefaultImage() {

				let viewDefaultImage = {
					width: 400,
					rows: [
						{},
						{
							id: (self.fromTable) ? "view-main-info-table-image-" + self.uid : "view-main-info-image-" + self.uid,
							height: 200,
							borderless: true,
							template: function(data) {

								if (self.elementType) {

									let equipmentIcon = self.elementType.elementTypePhoto;

									let color = util.element.getColor(self.element.elementTypeState);

									if (data && data.elementTypeState) {

										let elementTypeState = self.elementType.elementTypeStates.find(e => e.id === data.elementTypeState.id);

										if (elementTypeState) {

											color = util.element.getColor(elementTypeState);
										}
									}

									let res = `<div class="equip-viewer-info-image">
														<image class="image" src="img/${equipmentIcon}" onerror="this.onerror=null;this.src='img/not_found_50.png';" />
														<div style="background: ${color}" class="shadow"></div>
												   <div>`;

									return res;
								}
							}
						},
						{}
					]
				};

				return viewDefaultImage;

			}

			function getGraphicIcon() {

				let viewGraphicIcon = {
					id: (self.fromTable) ? "graphic-icon-table-" + self.uid : "graphic-icon-info-" + self.uid,
					width: (self.fromTable) ? 400 : self.parent.containerWidth * 0.45,
					rows: [
						{},
						{
							height: (self.fromTable) ? 200 : 200 * 0.9,
							borderless: true,
							template: function() {

								let template = (self.fromTable) ? `<div><svg id=${"view-main-info-image-svg-table-" + self.uid}></svg></div>` : `<div><svg id=${"view-main-info-image-svg-" + self.uid}></svg></div>`;

								return template;
							},
							on: {
								onAfterRender: function() {

									drawGraphicIcon(this.$width, this.$height);
								}
							}
						},
						{
							id: (self.fromTable) ? "view-main-info-table-image-" + self.uid : "view-main-info-image-" + self.uid,
							borderless: true,
							height: 25,
							template: function(data) {

								let color = util.element.getColor(self.element.elementTypeState);

								if (data && data.elementTypeState) {

									let elementTypeState = self.elementType.elementTypeStates.find(e => e.id === data.elementTypeState.id);

									if (elementTypeState) {

										color = util.element.getColor(elementTypeState);
									}
								}

								return `<div class="equip-viewer-info-image" style="height: 35px;">
												<div style="background: ${color}" class="shadow"></div>
											</div>`
							}
						},
						{}
					]
				};

				function drawGraphicIcon(w, h) {

					let graphicIcon = webix.copy(self.equipmentViewer.graphicIcon);

					if (graphicIcon) {

						graphicIcon.width = w;
						graphicIcon.height = h;

						const lazy = async _ => {

							let jsFile = util.getContextPath() + '/' + graphicIcon.jsDrawFile;

							const mod = await import(jsFile);

							let layerElement = {
								graphicIcon: graphicIcon
							}

							let s;

							if (self.fromTable) {

								s = Snap("#" + "view-main-info-image-svg-table-" + self.uid);
								s.attr({ width: w, height: h });

							} else {

								s = Snap("#" + "view-main-info-image-svg-" + self.uid);
								s.attr({ width: w, height: h });
							}

							let container = {
								type: GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW,
								obj: s
							}

							self.graphicIconInstance = new mod.default(layerElement, self.element, container, null);

							if (self.fromTable) {

								self.parent.graphicIconInstanceFromTable = self.graphicIconInstance;

							} else {

								self.parent.graphicIconInstance = self.graphicIconInstance;
							}

							self.graphicIconInstance.load(function() {

								self.graphicIconInstance.loadRender(function() {

									if (self.element.elementTypeState) {

										self.graphicIconInstance.updateState(self.element.elementTypeState);
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

			return viewMainImage;
		}

		function getViewMainInfo() {

			let addRow = (self.fromTable) ? { height: 15 } : {};

			let viewMainInfo = {
				width: 300,
				rows: [
					addRow,
					{},
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

								ellipsis = `style='max-width: ${305 * 0.95}px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis'`;
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
											<span class="value">${self.element.location.alias} - ${self.element.location.stretchs[0].alias} - ${self.element.location.stretchs[0].road.alias} ${ellipsis}</span>									
										</div>`;
								} else {
									return `<div class="equip-viewer-info">
											<span class="ico webix_icon mdi mdi-map-marker"></span>
											<span class="title">${i18n.t("LBL_ELEMENT_LOCATION")}</span>
											<span class="value">${self.element.location.alias}</span>									
										</div>`;
								}
							} else {
								return `<div class="equip-viewer-info">
											<span class="ico webix_icon mdi mdi-map-marker"></span>
											<span class="title">${i18n.t("LBL_ELEMENT_LOCATION")}</span>
											<span class="value"> - </span>									
										</div>`;
							}
						}
					},
					{
						height: 65,
						borderless: true,
						template: function(data) {

							return `<div class="equip-viewer-info">
											<span class="ico webix_icon mdi mdi-view-headline"></span>
											<span class="title">${i18n.t("LBL_ELEMENT_DESCRIPTION")}</span>
											<span class="value">${self.element.description}</span>
										</div>`;
						}
					},
					{}
				]
			};

			return viewMainInfo;
		}

		return viewMain;
	}
}