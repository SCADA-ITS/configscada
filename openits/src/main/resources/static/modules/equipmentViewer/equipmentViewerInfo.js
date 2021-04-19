import EquipmentViewerInfoMeasure from "./equipmentViewerInfoMeasure.js";
import { util } from "../../lib/util.js";

export default class EquipmentViewerInfo {
	
	constructor(parent, element, equipmentViewer) {

		this.parent = parent;
		this.uid = parent.uid;
		this.element = element;
		this.equipmentViewer = equipmentViewer;
		this.equipmentViewerInfoMeasure = undefined;
		
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
				
				let viewMainImage = {
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
																	
									let elementTypeStateLabel = i18n.t(self.element.elementTypeState.labelAlias);
									let color = self.element.elementTypeState.color ? self.element.elementTypeState.color : "#000000";
									
									if (data && data.elementTypeState) {
										
										let elementTypeState = self.elementType.elementTypeStates.find(e => e.id === data.elementTypeState.id);
									
										if (elementTypeState) {
											
											elementTypeStateLabel = i18n.t(elementTypeState.labelAlias);
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
		}
	}
	
	refresh() {
		
		if (this.equipmentViewerInfoMeasure) {
			
			this.equipmentViewerInfoMeasure.refresh();
		}
	}
}	
