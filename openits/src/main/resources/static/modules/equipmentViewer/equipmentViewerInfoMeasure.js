import { util } from "../../lib/util.js";

const MEASURE_ITEM_HEIGHT = 85;
const MEASURE_ITEM_WIDTH = 200;

const MEASURE_DEFAULT_ROWS = 2;

const MEASURE_SEPARATOR = 16;
const MEASURE_MARGINS = 40;
const MEASURE_MIN_MARGIN_LEFT = 30;

const VIEW_INFO_MEASURE_SEGMENTED = "view-info-measure-segmented";
const VIEW_INFO_MEASURE_SEGMENTED_ITEM = "view-measure-segmented-item";

const VIEW_INFO_MEASURE_CAROUSEL = "view-info-measure-carousel";
const VIEW_INFO_MEASURE_CAROUSEL_ITEM = "view-info-measure-carousel-item";

const VIEW_INFO_MEASURE_MULTIVIEW = "view-info-measure-multiview";
const VIEW_INFO_MEASURE_MULTIVIEW_ITEM = "view-info-measure-multiview-item";

class MeasureItem {
	
	constructor(parent, elementValue) {
		
		this.parent = parent;
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
				currentValue: undefined,
				template: function(data) {

					let elementValue = self.elementValue;
					
					let stateValueId = self.parent.parent.config.stateValue.online;
					let stateValueLabel = i18n.t("LBL_UNKNOWN");
					let stateValueBackColor = "white";
					
					if (data && data.elementValue) {
						
						elementValue.value = data.elementValue.value;
						
						if (data.elementValue.elementValueState) {
						
							stateValueId = data.elementValue.elementValueState.id;	
						}
						else {
							
							stateValueId = self.parent.parent.config.stateValue.online;
						}
					}
					else {
						
						if (elementValue.elementValueState) {
							
							stateValueId = elementValue.elementValueState.id;
						}
					} 
					
					
					
					let stateValue = window.em.getByField(EntityType.ELEMENT_VALUE_STATE, "id", stateValueId);
					
					if (stateValue) {
						
						stateValueLabel = i18n.t(stateValue.labelAlias);
						stateValueBackColor = stateValue.color ? stateValue.color : "white";
					}
					
					let icon = undefined;
					
					if (!elementValue.elementTypeParam.image) {
						icon = "not_found_50.png";
					}
					
					let value = elementValue.value ? elementValue.value : '-';
					let label = i18n.t(elementValue.elementTypeParam.labelAlias);
					let unit = i18n.t(elementValue.elementTypeParam.labelUnit);
					let iconElementValueState = stateValueId.replace(":", "_") + ".png";
					
					this.currentValue = value;
								
					let res = `<div class="measure-box" style="background: ${stateValueBackColor}">
									<image class="image" src="../../img/${icon}" />
									<div style="width: ${self.measureWidth - 70}px" class="value"><span title="${value}">${value}</span><span style="margin-left: 5px; font-size: 14px; font-weight: 100;">${unit}</span></div>
									<div style="width: ${self.measureWidth - 70}px" class="desc"><span title="${label}">${label}</span></div>
									<img style="margin-left: ${self.measureWidth - 30}px; margin-top: 5px;" class="table-field-icons" src="../../img/state/${iconElementValueState}" 
													title="${stateValueLabel}" onerror="this.onerror=null; this.style.display = 'none'"/>
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
		this.parent = parent;
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
		
		let self = this;
		let viewCarousel = {};
		
		if (this.measureItems) {
			
			this.measureItems.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));
						
			let colsMeasures = Math.floor((this.containerWidth - 20 - (MEASURE_MIN_MARGIN_LEFT * 2)) / (this.measureWidth + MEASURE_SEPARATOR));
			let rowsPerPage = this.parent.measureMaxRows ? this.parent.measureMaxRows : MEASURE_DEFAULT_ROWS;
			let measuresPerPage = rowsPerPage * colsMeasures;
			let measuresCount = this.measureItems.length; 
			let marginLeft = Math.floor((this.containerWidth - 20 - (colsMeasures * (this.measureWidth)) - ((colsMeasures - 1) * MEASURE_SEPARATOR)) / 2); 
			
			let nextMeasureItem = 0;
			
			viewCarousel = getViewCarousel();
			let idxPage = 0;
		
			while (nextMeasureItem < measuresCount) {
				
				let idxMeasurePage = 0;
				let idxRow = -1;
				
				let viewGridMeasures = { 
					id: VIEW_INFO_MEASURE_CAROUSEL_ITEM + "-" + this.id + "-" + (++idxPage) + "-" + this.uid,
					rows: []
				};
				
				while (idxMeasurePage++ < measuresPerPage && nextMeasureItem < measuresCount) {
									
					if (!(nextMeasureItem % colsMeasures)) {
						
						viewGridMeasures.rows.push({ height: MEASURE_ITEM_HEIGHT + (MEASURE_SEPARATOR * 2), cols: [] });
						
						idxRow = viewGridMeasures.rows.length - 1;
						
						viewGridMeasures.rows[idxRow].cols.push({ width: marginLeft });
					}
					
					let measureItem = this.measureItems[nextMeasureItem++];
					
					viewGridMeasures.rows[idxRow].cols.push(measureItem.getView());
					
					if (!(nextMeasureItem % colsMeasures)) {
						
						viewGridMeasures.rows[idxRow].cols.push({ width: marginLeft + (MEASURE_SEPARATOR / 2) + 1 });
					}
					else {
						
						viewGridMeasures.rows[idxRow].cols.push({ width: MEASURE_SEPARATOR });
					}
				}
				
				viewGridMeasures.rows.push({ height: 50 });
				
				viewCarousel.cols.push(viewGridMeasures);
			}
		}
		
		function getViewCarousel() {
		
			return {
				view: "carousel",
			    id: VIEW_INFO_MEASURE_CAROUSEL + "-" + self.id + "-" + self.uid,
				navigation:{
				    type: "side",
				    items: true,
				    buttons: false
				},
			    cols: []
			};
		}
		
		return viewCarousel;
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
		let viewMultiview;
		
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
			viewMultiview = getViewMultiview();
			
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
				
				viewMultiview.cells.push(measureGroup.getView());
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
		}
		
		view = {
			id: "equipmentViewerInfoMeasure-main-" + self.uid,
			rows: [
				{ height: 10},
				{ 
					cols: [
						{ width: 1 },
						{ 
							rows: [viewSegmented, { height: 10 }, viewMultiview, { height: 10 }]
						},		
						{ width: 1 }
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
							
							if (elementValue) {
						
								let measureItem = new MeasureItem(self, elementValue);
						
								measureGroup.addMeasureItem(measureItem);		
							}
						}
					});
				}
				
				measureGroups.push(measureGroup);
			});
			
			return measureGroups;
		}
		
		function getMeasuresGroupsFromElement() {
			
			let measureGroups = [];
			let elementValues = self.element.elementValues; 
			
			if (Array.isArray(elementValues) && elementValues.length > 0) {
			
				if (self.equipmentViewer && self.equipmentViewer.measureOnly) {
				
					elementValues = elementValues.filter(e => e.elementTypeParam.paramType.id === self.parent.config.paramType.measure);	
				}
				
				elementValues.sort((a, b) => (i18n.t(a.elementTypeParam.labelAlias) > i18n.t(b.elementTypeParam.labelAlias)) ? 1 : ((i18n.t(b.elementTypeParam.labelAlias) > i18n.t(a.elementTypeParam.labelAlias)) ? -1 : 0));
				
				let measureGroup = new MeasureGroup(self, 1, i18n.t("LBL_MEASURES"), self.parent.containerWidth - MEASURE_MARGINS);
				
				elementValues.forEach(elementValue => {
					
					let measureItem = new MeasureItem(self, elementValue);
								
					measureGroup.addMeasureItem(measureItem);
				});	
				
				measureGroups.push(measureGroup);
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
		
		function getViewMultiview() {
		
			return {
				view: "multiview",
			    id: VIEW_INFO_MEASURE_MULTIVIEW + "-" + self.uid,
			    cells: []
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
					
							if (measureItem.config.currentValue != elementValue.value) {
								
								measureItem.setValues({ elementValue: elementValue });	
							}
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
