import { util } from "../../lib/util.js";

const MEASURE_ITEM_HEIGHT = 85;
const MEASURE_ITEM_WIDTH = 175;

const MEASURE_DEFAULT_ROWS = 2;

const MEASURE_SEPARATOR = 16;

const VIEW_INFO_MEASURE_SEGMENTED = "view-info-measure-segmented";
const VIEW_INFO_MEASURE_SEGMENTED_ITEM = "view-measure-segmented-item";

const VIEW_INFO_MEASURE_CAROUSEL = "view-info-measure-carousel";
const VIEW_INFO_MEASURE_CAROUSEL_ITEM = "view-info-measure-carousel-item";

class MeasureItem {
	
	constructor(parent, elementValue) {
		
		this.uid = parent.uid;
		this.measureWidth = parent.measureWidth; 
		this.elementValue = elementValue;
	}
	
	getView() {
		
		let self = this;
		let view = {};
		
		if (self.elementValue.elementTypeParam) {
			
			view = {
				id: "measureItem-" + self.elementValue.elementTypeParam.id + "-" + self.uid,
				css: { "padding-top": MEASURE_SEPARATOR + "px" },
				borderless: true,
				height: MEASURE_ITEM_HEIGHT,
				width: self.measureWidth,
				template: function(data) {

					let elementValue = self.elementValue;
					
					if (data && data.value) {
						
						elementValue.value = data.value;
					}
					
					let icon = undefined;
					
					if (!elementValue.elementTypeParam.image) {
						icon = "not_found_50.png";
					}
					
					let value = elementValue.value ? elementValue.value : '-';
					let label = i18n.t(elementValue.elementTypeParam.labelAlias);
					let unit = i18n.t(elementValue.elementTypeParam.labelUnit);
								
					let res = `<div class="measure-box">
									<image class="image" src="../../img/${icon}" />
									<div style="width: ${self.measureWidth - 70}px" class="value"><span title="${value}">${value}</span><span style="margin-left: 5px; font-size: 14px; font-weight: 100;">${unit}</span></div>
									<div style="width: ${self.measureWidth - 70}px" class="desc"><span title="${label}">${label}</span></div>
							   </div>`;
					
					return res;	
				}
			};
	
		}
				
		return view;
	}
}

class MeasureGroup {
	
	constructor(parent, id, label, containerWidth) {
		
		this.uid = parent.uid;
		this.measureWidth = parent.measureWidth;
		this.id = id;
		this.label = label;
		this.containerWidth = containerWidth;
		
		this.measureItems = [];
	}
	
	addMeasureItem(measureItem) {
		
		this.measureItems.push(measureItem);
	}
	
	refresh() {
	
		if ($$("grid-measures-" + this.id + "-" + this.uid)) {
			
			this.carousel = this.getViewCarousel();
		
			webix.ui(this.getView(), $$("grid-measures-" + this.id + "-" + this.uid));	
		}
	}
	
	getView() {
		
		let viewGridMeasures = {};
		
		if (this.measureItems) {
			
			viewGridMeasures = { id: VIEW_INFO_MEASURE_CAROUSEL_ITEM + "-" + this.id + "-" + this.uid};

			this.measureItems.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
						
			let colsMeasures = Math.floor(this.containerWidth / (this.measureWidth + MEASURE_SEPARATOR));
			let rowsMeasures = Math.floor(this.measureItems.length / colsMeasures) + 1;
			let marginLeft = Math.floor((this.containerWidth - colsMeasures * (this.measureWidth + MEASURE_SEPARATOR)) / 2); 
			
			let nextMeasureItem = 0;
			
			for (let idxRow = 0; nextMeasureItem < this.measureItems.length && idxRow < rowsMeasures; idxRow++) {
				
				if (!viewGridMeasures.rows) {
					
					viewGridMeasures.rows = [];
				}
				
				viewGridMeasures.rows.push({ height: MEASURE_ITEM_HEIGHT + (MEASURE_SEPARATOR * 2), cols: [] });
				
				let idxCol = 0;
				
				viewGridMeasures.rows[idxRow].cols.push({ width: marginLeft });
				
				for (idxCol = 0; nextMeasureItem < this.measureItems.length && idxCol < colsMeasures; idxCol++) {
					
					let measureItem = this.measureItems[nextMeasureItem++];
					
					if (idxCol > 0) {
						
						viewGridMeasures.rows[idxRow].cols.push({ width: MEASURE_SEPARATOR });	
					}
					
					viewGridMeasures.rows[idxRow].cols.push(measureItem.getView());
				}

				viewGridMeasures.rows[idxRow].cols.push({});
			}
			
			if (viewGridMeasures.rows) {
					
				viewGridMeasures.rows.push({ height: 50 });
			}
		}
		
		return viewGridMeasures;
	}
}

export default class EquipmentViewerInfoMeasure {
	
	constructor(parent, element, equipmentViewer) {

		this.uid = webix.uid();
		this.parent = parent;
		this.element = element;
		this.equipmentViewer = equipmentViewer;
		this.measuresGroups = [];
		this.measureWidth = MEASURE_ITEM_WIDTH;
		this.measureMaxRows = MEASURE_DEFAULT_ROWS;
	}
	
	getView() {
		
		let self = this;
		let view = {};
		let viewSegmented;
		let viewCarousel;
		
		this.measureWidth = MEASURE_ITEM_WIDTH;
		
		if (self.equipmentViewer && self.equipmentViewer.measureWidth) {
		
			this.measureWidth = self.equipmentViewer.measureWidth;
		}
				
		if (self.equipmentViewer && self.equipmentViewer.equipViewerGrpMeasures && self.equipmentViewer.equipViewerGrpMeasures.length > 0) {
						
			self.measuresGroups = getMeasuresGroupsFromConfig();
		}
		else {
			
			self.measuresGroups = getMeasuresGroupsFromElement();
		}
		
		if (self.measuresGroups) {
			
			viewSegmented = getViewSegmented();
			viewCarousel = getViewCarousel();
			
			let firstSelected = false;
			
			self.measuresGroups.forEach(measureGroup => {
				
				if (!firstSelected) {
			
					viewSegmented.value = VIEW_INFO_MEASURE_SEGMENTED_ITEM + "-" + measureGroup.id + "-" + self.uid;		
					firstSelected = !firstSelected;
				}
				
				viewSegmented.options.push({
					
					id: VIEW_INFO_MEASURE_SEGMENTED_ITEM + "-" + measureGroup.id + "-" + self.uid,
					width: 150,
					value: measureGroup.label
				});
				
				viewCarousel.cols.push(measureGroup.getView());
			});
			
			viewSegmented.tooltip = function(obj) {
				
				return obj.value; 
			};
			
			viewSegmented.on = {
				onChange(id) {
					
					let viewInfoMeasureCarousel = VIEW_INFO_MEASURE_CAROUSEL + "-" + self.uid; 
					let viewInfoMeasureCarouselItem = id.replace(VIEW_INFO_MEASURE_SEGMENTED_ITEM, VIEW_INFO_MEASURE_CAROUSEL_ITEM);
					
					if ($$(viewInfoMeasureCarousel) && $$(viewInfoMeasureCarouselItem)) {
						
						$$(viewInfoMeasureCarousel).setActive(viewInfoMeasureCarouselItem);
					}
				}
			};
			
			viewCarousel.on = {
				
				onShow(id) {
					
					let viewInfoMeasureSegmented = VIEW_INFO_MEASURE_SEGMENTED + "-" + self.uid;
					let viewInfoMeasureSegmentedItem = id.replace(VIEW_INFO_MEASURE_CAROUSEL_ITEM, VIEW_INFO_MEASURE_SEGMENTED_ITEM);
					
					if ($$(viewInfoMeasureSegmented)) {
						
						$$(viewInfoMeasureSegmented).setValue(viewInfoMeasureSegmentedItem);
					}
				}
			}
		}
		
		view = {
			id: "equipmentViewerInfoMeasure-main-" + self.uid,
			rows: [
				{ height: 10},
				{ 
					cols: [
						{ width: 10 },
						{ 
							rows: [viewSegmented, { height: 10 }, viewCarousel, { height: 10 }]
						},		
						{ width: 10 }
					]
				},
				{ height: 10}
			]
		};
		
		function getMeasuresGroupsFromConfig() {
			
			let measureGroups = [];
			
			self.equipmentViewer.equipViewerGrpMeasures.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
			
			self.equipmentViewer.equipViewerGrpMeasures.forEach(groupMeasure => {
				
				let measureGroup = new MeasureGroup(self, groupMeasure.id, groupMeasure.label, self.parent.containerWidth);
				
				if (groupMeasure.equipViewerMeasures && groupMeasure.equipViewerMeasures.length > 0) {
				
					groupMeasure.equipViewerMeasures.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
					
					groupMeasure.equipViewerMeasures.forEach(equipViewerMeasure => {
					
						if (self.element.elementValues) {
						
							let elementValue = self.element.elementValues.find(ev => 
								
								(ev.elementTypeParam && ev.elementTypeParam.id === equipViewerMeasure.voEntity.id)
							);
							
							putViewMeasure(equipViewerMeasure, elementValue);
						}
						
						let measureItem = new MeasureItem(self, elementValue);
						
						measureGroup.addMeasureItem(measureItem);
					});
				}
				
				measureGroups.push(measureGroup);
			});
		}
		
		function getMeasuresGroupsFromElement() {
			
			let measureGroups = [];
			let rows = self.parent.measureMaxRows ? self.parent.measureMaxRows : MEASURE_DEFAULT_ROWS; 
			let maxMeasuresPerPage = (Math.floor(self.parent.containerWidth / (self.measureWidth + MEASURE_SEPARATOR))) * rows;
			
			if (self.element.elementValues) {
			
				let measureByGroup = maxMeasuresPerPage;
				let groupNumber = 0;
				let measureGroup;
				
				self.element.elementValues.forEach(elementValue => {
					
					if (measureByGroup === maxMeasuresPerPage) {
						
						groupNumber++;
						
						measureGroup = new MeasureGroup(self, groupNumber, i18n.t("LBL_GROUP_MEASURES") + " " + groupNumber, self.parent.containerWidth);
					}
					
					let measureItem = new MeasureItem(self, elementValue);
								
					measureGroup.addMeasureItem(measureItem);
					
					if (!(--measureByGroup)) {
					
						measureGroups.push(measureGroup);
						
						measureByGroup = maxMeasuresPerPage;
						
						measureGroup = null;
					}
				});	
				
				if (measureGroup) {
					
					measureGroups.push(measureGroup);
				}	
			}

			return measureGroups;
		}
		
		function getViewSegmented() {
				
			return {
				view: "segmented",
				id: VIEW_INFO_MEASURE_SEGMENTED + "-" + self.uid,
				css: "segment-custom-1",
				value: "measure-group-0-" + self.uid,
				options: [],
			};
		}
		
		function getViewCarousel() {
		
			return {
				view: "carousel",
			    id: VIEW_INFO_MEASURE_CAROUSEL + "-" + self.uid,
				navigation:{
				    type: "side",
				    items: true,
				    buttons: false
				},
			    cols: []
			};
		}
		
		return view;
	}
	
	updateMeasure(elementValues) {

		if (Array.isArray(elementValues) && elementValues.length > 0) {
			
			if (this.element && Array.isArray(this.element.elementValues) && this.element.elementValues.length > 0) {
				
				elementValues.forEach(elementValue => {
					
					let elementValueAux = this.element.elementValues.find(e => e.id === elementValue.id);
					
					if (elementValueAux && elementValueAux.elementTypeParam) {
				
						let measureItem = $$("measureItem-" + elementValueAux.elementTypeParam.id + "-" + this.uid);
								
						if (measureItem) {
					
							measureItem.setValues({ value: elementValue.value });
						}		
					}
				});
			}
		}
	}
	
	refresh() {
		
		let lastUID = this.uid;
		
		this.uid = webix.uid();
		
		webix.ui(this.getView(), $$("equipmentViewerInfoMeasure-main-" + lastUID));
	}
}	
