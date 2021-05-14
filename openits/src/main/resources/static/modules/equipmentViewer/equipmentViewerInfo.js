import EquipmentViewerInfoMeasure from "./equipmentViewerInfoMeasure.js";
import { util } from "../../lib/util.js";
import GraphicIcon from "../../component/graphicIcon/graphicIcon.js";

export default class EquipmentViewerInfo {
	
	constructor(parent, element, equipmentViewer) {

		this.parent = parent;
		this.uid = parent.uid;
		this.element = element;
		this.equipmentViewer = equipmentViewer;
		this.equipmentViewerInfoMeasure = undefined;
		this.graphicIconInstance = undefined;
		
		if (element) {
			
			this.elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);
		}
	}
	
	getView() {
		
		let self = this;
		let view = {};
		
		this.equipmentViewerInfoMeasure = new EquipmentViewerInfoMeasure(this.parent, this.element, this.equipmentViewer);
		
		view = {
			rows: [
				getViewMain(),
				this.equipmentViewerInfoMeasure.getView()
			]
		}
		
		function getViewMain() {

			let viewMain = {};
			
			viewMain = {
				cols: [
					{ width: 100 },
					getViewMainImage(),
					{},
					getViewMainInfo(),
					{ width: 100}
				]
			};
			
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
						width: 300,
						rows: [
							{ height: 20},
							{},
							{
								id:  "view-main-info-image-" + self.uid,
								height: 200,
								borderless: true,
								template: function(data) {
									
									if (self.elementType) {
										
										let equipmentIcon = self.elementType.elementTypePhoto;
																		
										let color = self.element.elementTypeState.color ? self.element.elementTypeState.color : "#000000";
										
										if (data && data.elementTypeState) {
											
											let elementTypeState = self.elementType.elementTypeStates.find(e => e.id === data.elementTypeState.id);
										
											if (elementTypeState) {
												
												color = elementTypeState.color ? elementTypeState.color : "#000000";
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
						width: 300,
						rows: [
							{ height: 20},
							{},
							{
								id:  "view-main-info-image-" + self.uid,
								height: 200,
								borderless: true,
								template: function() {
									return `<div>
												<svg id=${"view-main-info-image-svg-" + self.uid}></svg>
											</div>`
								},
								on: {
									onAfterRender: function() {
					
										drawGraphicIcon(this.$width, this.$height);
									}
								}
							},
							{}
						]
					};
					
					function drawGraphicIcon(w, h) {
						
						let graphicIcon = self.equipmentViewer.graphicIcon;
						graphicIcon.width = w;
						graphicIcon.height = h;
						
						if (graphicIcon) {
							
							const lazy = async _ => {
					
								let jsFile = window.location.protocol + '//' + window.location.host + '/' + graphicIcon.jsDrawFile;
					
								const mod = await import(jsFile);
								
								let layerElement = {
									graphicIcon: graphicIcon
								}
								
								let s = Snap("#" + "view-main-info-image-svg-" + self.uid);
								s.attr({ width: w, height: h });
								
								let container = { 
									type: GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW, 
									obj: s
								} 
								
								self.graphicIconInstance = new mod.default(layerElement, self.element, container, null);
								
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
				
				let viewMainInfo = { 
					width: 300,
					rows: [
						{ height: 20},
						{},
						{
							height: 70,
							borderless: true,
							template: function(data) {
								
								let labelType = i18n.t("LBL_ELEMENT_TYPE");
								let labelTypeValue = i18n.t(self.element.elementType.labelAlias);
								
								if (self.element.elementSubtype) {
									
									labelType += " (" + i18n.t("LBL_ELEMENT_SUBTYPE") + ")";
									labelTypeValue += " (" + i18n.t(self.element.elementSubtype.labelAlias) + ")";
								}
								
								return `<div class="equip-viewer-info">
											<span class="ico webix_icon mdi mdi-view-grid"></span>
											<span class="title">${labelType}</span>
											<span class="value">${labelTypeValue}</span>
										</div>`;	
							}
						},	
						{
							height: 70,
							borderless: true,
							template: function(data) {
								
								return `<div class="equip-viewer-info">
											<span class="ico webix_icon mdi mdi-format-color-text"></span>
											<span class="title">${i18n.t("LBL_ELEMENT_ALIAS")}</span>
											<span class="value">${self.element.alias}</span>
										</div>`;
							}
						},
						{
							height: 70,
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
		
		
		return view;
	}
	
	updateMeasures(elementValues) {

		if (this.equipmentViewerInfoMeasure) {
			
			this.equipmentViewerInfoMeasure.updateMeasure(elementValues);
			
			if (this.graphicIconInstance) {
				
				this.graphicIconInstance.updateValues(elementValues);
			}
		}
	}
	
	updateState(elementTypeState) {

		let viewMainInfoImage = $$("view-main-info-image-" + this.uid);
		
		if (this.graphicIconInstance) {
			
			this.graphicIconInstance.updateState(elementTypeState);
		}
		else {
			
			viewMainInfoImage.setValues( { elementTypeState: elementTypeState });
		}
	}
	
	// TODO: Update alarms
	updateAlarms(alarms) {
		
		if (this.graphicIconInstance) {
			
			this.graphicIconInstance.updateAlarms(alarms);
		}
		else {
			
			// TODO: Update any in equipment viewer???
		}
	}
	
	refresh() {
		
		if (this.equipmentViewerInfoMeasure) {
			
			this.equipmentViewerInfoMeasure.refresh();
		}
	}
}	
