import { util } from "../../lib/util.js";

const MEASURE_ITEM_HEIGHT = 85;
const MEASURE_ITEM_WIDTH = 200;

const MEASURE_DEFAULT_ROWS = 2;
const MEASURE_ROWS_HEIGHT = 103;

const MEASURE_SEPARATOR = 9;
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

					const STANDARD_FACTORS = {
						FONT_FAMILY: "Roboto",
						FONT_SIZE_VALUE: 26,
						FONT_SIZE_UNIT: 14,
						LETTER_SPACING: 0.2,
						MARGIN_TOP_NORMAL: 14,
						MARGIN_LEFT_NORMAL: 52,
						NEW_MARGIN_TOP: 24,
						WIDTH_CORRECTION: 70,
						IMG_WIDTH_CORRECTION: 35,
						FACTOR_CORRECTION: 0.9
					};

					let elementValue = self.elementValue;
					let unit = "";
					let valuesForPrint = util.element.getValuesForPrint(data, elementValue);
					let newFontSize = undefined;
					let fontSizeValue = STANDARD_FACTORS.FONT_SIZE_VALUE;
					let fontSizeUnit = STANDARD_FACTORS.FONT_SIZE_UNIT;
					let marginTop = STANDARD_FACTORS.MARGIN_TOP_NORMAL;

					let standardWidth = (self.measureWidth - STANDARD_FACTORS.WIDTH_CORRECTION) * STANDARD_FACTORS.FACTOR_CORRECTION;

					this.currentValue = valuesForPrint.value;

					if (valuesForPrint.hasEnum === undefined) {

						unit = valuesForPrint.unit;
					}

					let valueHTML = (valuesForPrint.hasEnumImage != undefined) ? `<img style="width:40px" src="${valuesForPrint.value}">` : `<span title="${valuesForPrint.value}">${valuesForPrint.value}</span>`;
					let res;

					if (elementValue.elementTypeParam.showCompleteMeasure || self.parent.parent.config.forceAdjustMeasures) {

						let textWidth = util.ui.calculateTextWidth(valuesForPrint.value, STANDARD_FACTORS.FONT_FAMILY, STANDARD_FACTORS.FONT_SIZE_VALUE, STANDARD_FACTORS.LETTER_SPACING);

						if (textWidth > standardWidth) {

							newFontSize = util.ui.calculateFontSize(valuesForPrint.value, STANDARD_FACTORS.FONT_FAMILY, STANDARD_FACTORS.FONT_SIZE_VALUE, STANDARD_FACTORS.LETTER_SPACING, standardWidth);
						}
					}

					if (newFontSize != undefined) {

						fontSizeValue = newFontSize;
						fontSizeUnit = newFontSize;
						let proportion = fontSizeValue / STANDARD_FACTORS.FONT_SIZE_VALUE;
						marginTop = (proportion < 1) ? (STANDARD_FACTORS.MARGIN_TOP_NORMAL * (1 + (1 - proportion))) : (STANDARD_FACTORS.MARGIN_TOP_NORMAL * proportion);
					}

					let divImage = "";
					let divMeasure = "";
					let divMeasureDesc = "";
					let divIconMeasureState = "";
					
					if (valuesForPrint.icon) {
						
						divImage = `<image class="image" src="img/${valuesForPrint.icon}" onerror="this.onerror=null;this.src="${NO_IMAGE}" />'`;
						divMeasure = `<div style="margin-left: ${STANDARD_FACTORS.MARGIN_LEFT_NORMAL}px; width: ${self.measureWidth - STANDARD_FACTORS.WIDTH_CORRECTION}px; font-size: ${fontSizeValue}px !important; margin-top: ${marginTop}px" class="value">${valueHTML}<span style="margin-left: 5px; font-size:${fontSizeUnit}px; font-weight: 100;">${unit}</span></div>`;
						divMeasureDesc = `<div style="margin-left: ${STANDARD_FACTORS.MARGIN_LEFT_NORMAL}px; width: ${self.measureWidth - STANDARD_FACTORS.WIDTH_CORRECTION}px" class="desc"><span title="${valuesForPrint.elementTypeParamDescription}">${valuesForPrint.label}</span></div>`;
					}
					else {
						
						divMeasure = `<div style="margin-left: ${STANDARD_FACTORS.MARGIN_LEFT_NORMAL - STANDARD_FACTORS.IMG_WIDTH_CORRECTION}px; width: ${self.measureWidth - STANDARD_FACTORS.IMG_WIDTH_CORRECTION}px; font-size: ${fontSizeValue}px !important; margin-top: ${marginTop}px" class="value">${valueHTML}<span style="margin-left: 5px; font-size:${fontSizeUnit}px; font-weight: 100;">${unit}</span></div>`;
						divMeasureDesc = `<div style="margin-left: ${STANDARD_FACTORS.MARGIN_LEFT_NORMAL - STANDARD_FACTORS.IMG_WIDTH_CORRECTION}px; width: ${self.measureWidth - STANDARD_FACTORS.IMG_WIDTH_CORRECTION}px" class="desc"><span title="${valuesForPrint.elementTypeParamDescription}">${valuesForPrint.label}</span></div>`;
					}
					
					divIconMeasureState = `<img style="margin-left: ${self.measureWidth - STANDARD_FACTORS.IMG_WIDTH_CORRECTION}px; margin-top: 5px;" class="table-field-icons" src="img/state/${valuesForPrint.iconElementValueState}" title="${valuesForPrint.stateValueLabel}" onerror="this.onerror=null; this.style.display = 'none'"/>`;
					

					res = `<div class="measure-box" style="background: ${valuesForPrint.stateValueBackColor}">
								${divImage} 
								${divMeasure}
								${divMeasureDesc}
								${divIconMeasureState}
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

			webix.ui(this.getView(), $$("grid-measures-" + this.id + "-" + this.uid));
		}
	}

	getView() {

		let self = this;
		let viewCarousel = {};
		let viewControls = {};
		let viewMain = {};

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

						viewGridMeasures.rows[idxRow].cols.push({});

						//						viewGridMeasures.rows[idxRow].cols.push({ width: marginLeft + (MEASURE_SEPARATOR / 2) + 1 });
					}
					else {

						viewGridMeasures.rows[idxRow].cols.push({ width: MEASURE_SEPARATOR });
					}
				}

				if (viewGridMeasures.rows.length === 1) {

					viewGridMeasures.rows.push({ height: MEASURE_ROWS_HEIGHT });
				}

				viewGridMeasures.rows[idxRow].cols.push({});
				viewCarousel.cells.push(viewGridMeasures);
			}
		}

		function getViewCarousel() {

			return {
				view: "multiview",
				borderless: true,
				id: VIEW_INFO_MEASURE_CAROUSEL + "-" + self.id + "-" + self.uid,
				css: "equipment-viewer-carousel",
				cells: [{}]
			};
		}

		if (viewCarousel.cells.length > 1) {

			viewCarousel.cells.shift();
		}

		let controlOptions = [];

		for (let cell of viewCarousel.cells) {

			if (cell.id) {

				controlOptions.push({
					id: cell.id,
					value: ""
				});
			}
		}

		viewControls = {
			view: "radio",
			css: "equipment-control-radio",
			keepViews: true,
			animate: true,
			options: controlOptions,
			value: controlOptions[0].id,
			on: {
				onChange(newv) {

					$$(newv).show();
				}
			}
		};

		viewMain = {
			id: VIEW_INFO_MEASURE_MULTIVIEW_ITEM + "-" + this.id + "-" + this.uid,
			rows: [
				viewCarousel,
				viewControls
			]
		};

		return viewMain;
	}
}

export default class EquipmentViewerInfoMeasure {

	constructor(parent, element, equipmentViewer, measuresGroups) {

		this.uid = webix.uid();
		this.parent = parent;
		this.element = element;
		this.equipmentViewer = equipmentViewer;
		this.measuresGroups = measuresGroups;
		this.measureMaxRows = MEASURE_DEFAULT_ROWS;
	}

	getView() {

		let self = this;
		let view = {};
		let viewSegmented = {};
		let viewMultiview = {};
		let measuresGroups;
		let groupArr = [];

		this.measureWidth = MEASURE_ITEM_WIDTH;

		if (self.equipmentViewer && self.equipmentViewer.measureWidth) {

			this.measureWidth = self.equipmentViewer.measureWidth;
		}

		self.measuresGroups.forEach(groupMeasure => {

			measuresGroups = new MeasureGroup(self, groupMeasure.group.id, groupMeasure.group.label, self.parent.containerWidth);

			if (groupMeasure.elementValues && groupMeasure.elementValues.length > 0) {

				groupMeasure.elementValues.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));

				groupMeasure.elementValues.forEach(equipViewerMeasure => {

					let measureItem = new MeasureItem(self, equipViewerMeasure);
					measuresGroups.measureItems.push(measureItem);
				});
			}

			groupArr.push(measuresGroups);
		});

		if (Array.isArray(groupArr) && groupArr.length > 0) {

			viewSegmented = getViewSegmented();
			viewMultiview = getViewMultiview();

			let firstSelected = false;

			if (Array.isArray(groupArr) && groupArr.length > 0) {

				groupArr.forEach(msGroup => {

					if (!firstSelected) {

						viewSegmented.value = VIEW_INFO_MEASURE_SEGMENTED_ITEM + "-" + msGroup.id + "-" + self.uid;
						firstSelected = !firstSelected;
					}

					viewSegmented.options.push({

						id: VIEW_INFO_MEASURE_SEGMENTED_ITEM + "-" + msGroup.id + "-" + self.uid,
						measureGroupId: msGroup.id,
						width: 150,
						value: i18n.t(msGroup.label)
					});

					viewMultiview.cells.push(msGroup.getView());
				});
			}
			else {

				viewMultiview.cells.push({});
			}

			viewSegmented.tooltip = function(obj) {

				return obj.value;
			};

			viewSegmented.on = {
				onChange(id) {

					let option = this.config.options.find(e => e.id === id);
					let measureMultiview = $$(VIEW_INFO_MEASURE_MULTIVIEW + "-" + self.uid);

					if (option && measureMultiview) {

						measureMultiview.setValue(VIEW_INFO_MEASURE_MULTIVIEW_ITEM + "-" + option.measureGroupId + "-" + self.uid);
					}
				}
			};
		}

		view = {
			id: "equipmentViewerInfoMeasure-main-" + self.uid,
			rows: [
				{ height: 1 },
				{
					cols: [
						{ width: 1 },
						{
							rows: [
								viewSegmented,
								{ height: 1 },
								viewMultiview,
								{ height: 1 }
							]
						},
						{ width: 1 }
					]
				},
			]
		};

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
				animate: false,
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
