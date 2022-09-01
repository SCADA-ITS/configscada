import { util } from "../../lib/util.js";
import EquipmentViewerInfoMeasure from "./equipmentViewerInfoMeasure.js";
import EquipmentViewerInfoIcon from "./equipmentViewerInfoIcon.js";
import EquipmentViewerTable from "./equipmentViewerTable.js";
import EquipmentViewerChart from "./equipmentViewerChart.js";

const MEASURE_MARGINS = 40;
const MEASURE_ITEM_WIDTH = 200;

export default class EquipmentViewerInfo {

	constructor(parent, element, equipmentViewer) {

		this.parent = parent;
		this.uid = parent.uid;
		this.element = element;
		this.equipmentViewer = equipmentViewer;
		this.equipmentViewerInfoMeasure = undefined;
		this.equipmentViewerTable = undefined;
		this.equipmentViewerChart = undefined;
		this.graphicIconInstance = undefined;
		this.equipmentViewerInfoIconFromMosaic = undefined;
		this.equipmentViewerInfoIconFromTable = undefined;
		this.parent.measureWidth = MEASURE_ITEM_WIDTH;
		this.elementValuesForTable = [];
		this.measuresGroups = undefined;
		if (element) {

			this.elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);
		}
	}

	getView() {

		let self = this;
		let view = {};
		let viewMeasures = {};// util.ui.getViewNoData();
		let viewInfoIcon = undefined;
		let initViews = new Map();
		let element = webix.copy(self.element);

		const TAB_MODE = {
			MOSAIC: 1,
			TABLE: 2,
			CHART: 3
		};

		initViews.set(TAB_MODE.MOSAIC, false);
		initViews.set(TAB_MODE.TABLE, false);
		initViews.set(TAB_MODE.CHART, false);

		self.equipmentViewerInfoIcon = new EquipmentViewerInfoIcon(self.parent, element, self.equipmentViewer);
		viewInfoIcon = self.equipmentViewerInfoIcon.getView();

		if (self.equipmentViewer && self.equipmentViewer.equipViewerGrpMeasures && self.equipmentViewer.equipViewerGrpMeasures.length > 0) {

			self.measuresGroups = getMeasuresGroupsFromConfig();

		} else {

			self.measuresGroups = getMeasuresGroupsFromElement();
		}

		let measuresToShow = (self.measuresGroups.find(measuresGroup => measuresGroup.elementValues.length > 0) !== undefined);

		if (!self.equipmentViewer.noShowMeasures && Array.isArray(self.element.elementValues) && self.element.elementValues.length > 0 && measuresToShow) {

			if (self.equipmentViewer.showChart) {

				self.equipmentViewerChart = new EquipmentViewerChart(self.parent, self.element, self.equipmentViewer, self.measuresGroups);
				self.equipmentViewerChart.initMeasuresMaintenance();
			}

			view = {
				id: "view-main-info-multi-" + self.uid,
				css: "bordered-layout",
				rows: [
					{
						cols: [
							{ height: 38 },
							{
								id: "segmented-" + self.uid,
								view: "segmented",
								css: "segment-custom-2",
								width: 250,
								borderless: true,
								value: undefined,
								options: [
									{
										id: "optionToRemove-" + self.uid
									},
									{
										id: "segmented-show-mosaic-" + self.uid,
										value: "<span class='ico webix_icon mdi mdi-collage'></span>"
									},
									{
										id: "segmented-show-table-" + self.uid,
										value: "<span class='ico webix_icon mdi mdi-format-list-bulleted-square'></span>"
									},
									{
										id: "segmented-show-chart-" + self.uid,
										value: "<span class='ico webix_icon mdi mdi-chart-areaspline-variant'></span>"
									}
								],
								tooltip: function(obj) {

									if (obj.id === "segmented-show-mosaic-" + self.uid) {

										return i18n.t("LBL_INFO_MEASURES_MOSAIC");
									}
									else if (obj.id === "segmented-show-table-" + self.uid) {

										return i18n.t("LBL_TABLE_MEASURES");

									} else if (obj.id === "segmented-show-chart-" + self.uid) {

										return i18n.t("LBL_CHART_TAB");
									}
								},
								on: {
									onChange: function(newv, oldv) {

										if (newv === "segmented-show-mosaic-" + self.uid) {

											if (initViews.get(TAB_MODE.MOSAIC)) {

												$$("view-main-mosaic-tab-" + self.uid).show();
												$$("view-main-table-tab-" + self.uid).hide();
												$$("view-main-chart-tab-" + self.uid).hide();

											} else {

												$$("view-main-mosaic-tab-" + self.uid).show();
												$$("view-main-table-tab-" + self.uid).hide();
												$$("view-main-chart-tab-" + self.uid).hide();
												initViews.set(TAB_MODE.MOSAIC, true);
												drawInitialTabMosaic();
											}

										} else if (newv === "segmented-show-table-" + self.uid) {

											if (initViews.get(TAB_MODE.TABLE)) {

												$$("view-main-table-tab-" + self.uid).show();
												$$("view-main-mosaic-tab-" + self.uid).hide();
												$$("view-main-chart-tab-" + self.uid).hide();

											} else {

												$$("view-main-table-tab-" + self.uid).show();
												$$("view-main-mosaic-tab-" + self.uid).hide();
												$$("view-main-chart-tab-" + self.uid).hide();
												initViews.set(TAB_MODE.TABLE, true);
												drawInitialTabTable();
											}

										} else if (newv === "segmented-show-chart-" + self.uid) {

											if (initViews.get(TAB_MODE.CHART)) {

												$$("view-main-chart-tab-" + self.uid).show();
												$$("view-main-table-tab-" + self.uid).hide();
												$$("view-main-mosaic-tab-" + self.uid).hide();

											} else {

												initViews.set(TAB_MODE.CHART, true);
												drawInitialTabChart();
												$$("view-main-table-tab-" + self.uid).hide();
												$$("view-main-mosaic-tab-" + self.uid).hide();
												$$("view-main-chart-tab-" + self.uid).show();
											}
										}
									}
								}
							}
						]
					},
					{
						id: "multiview-main-content-" + self.uid,
						view: "multiview",
						animate: false,
						borderless: true,
						keepViews: true,
						cells: [
							{
								id: "view-main-mosaic-tab-" + self.uid
							},
							{
								id: "view-main-table-tab-" + self.uid
							},
							{
								id: "view-main-chart-tab-" + self.uid
							}
						]
					}
				]
			};

		} else {

			view = {
				id: "view-main-mosaic-tab-" + self.uid,
				borderless: true,
				rows: [
					viewInfoIcon,
					viewMeasures,
					{ height: 1 }
				]
			};
		}


		function drawInitialTabMosaic() {

			let viewTabMosaic;
			element.elementTypeState = self.element.elementTypeState;

			let equipmentViewerInfoIconForCarousel = new EquipmentViewerInfoIcon(self.parent, element, self.equipmentViewer);
			let viewInfoIconForCarousel = equipmentViewerInfoIconForCarousel.getView();

			if (!self.equipmentViewer.noShowMeasures) {

				self.equipmentViewerInfoMeasure = new EquipmentViewerInfoMeasure(self.parent, element, self.equipmentViewer, self.measuresGroups);
				viewMeasures = self.equipmentViewerInfoMeasure.getView();
			}

			viewTabMosaic = {
				id: "view-main-mosaic-tab-" + self.uid,
				borderless: true,
				rows: [
					viewInfoIconForCarousel,
					viewMeasures,
					{ height: 1 }
				]
			};

			webix.ui(viewTabMosaic, $$("view-main-mosaic-tab-" + self.uid));
		}

		function drawInitialTabTable() {

			let viewTabTable;
			let viewTable;

			element.elementTypeState = self.element.elementTypeState;
			element.elementValues = self.element.elementValues;

			self.equipmentViewerTable = new EquipmentViewerTable(self.parent, element, self.equipmentViewer, self.measuresGroups);
			viewTable = self.equipmentViewerTable.getView();

			self.parent.measureTable = self.equipmentViewerTable;

			viewTabTable = {
				id: "view-main-table-tab-" + self.uid,
				borderless: true,
				cols: [
					viewTable
				]
			};

			webix.ui(viewTabTable, $$("view-main-table-tab-" + self.uid));
			self.equipmentViewerTable.measureTable.callbacks();
		}

		function drawInitialTabChart() {

			let viewTabChart;
			let viewChart;

			viewChart = self.equipmentViewerChart.getView();

			viewTabChart = {
				id: "view-main-chart-tab-" + self.uid,
				borderless: true,
				cols: [viewChart]
			};

			webix.ui(viewTabChart, $$("view-main-chart-tab-" + self.uid));
			self.equipmentViewerChart.selectByIndex(0);
		}

		return view;

		function getMeasuresGroupsFromConfig() {

			let measureGroups = [];

			self.equipmentViewer.equipViewerGrpMeasures.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));

			self.equipmentViewer.equipViewerGrpMeasures.forEach(groupMeasure => {

				let measureGroup = {
					group: webix.copy(groupMeasure),
					elementValues: []
				};

				if (groupMeasure.equipViewerMeasures && groupMeasure.equipViewerMeasures.length > 0) {

					groupMeasure.equipViewerMeasures.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));

					let elementValuesFiltered = [];

					groupMeasure.equipViewerMeasures.forEach(equipViewerMeasure => {

						let elementValueFiltered = self.element.elementValues.find(ev => ev.elementTypeParam && ev.elementTypeParam.id === equipViewerMeasure.voEntity.id);

						if (elementValueFiltered) {

							elementValuesFiltered.push(elementValueFiltered);
						}
					});

					measureGroup.elementValues = webix.copy(elementValuesFiltered);
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

				measureGroups.push({
					group: { id: 1, label: i18n.t("LBL_MEASURES") },
					elementValues: elementValues
				});
			}

			return measureGroups;
		}
	}

	setSegmented() {

		let self = this;
		let tabView = "segmented-show-mosaic-" + self.uid;

		if (self.parent.config.tabMode && self.parent.config.tabMode.enabled && !self.equipmentViewer.noShowMeasures) {

			let tabMode;
			const TAB_MODE = {
				MOSAIC: 1,
				TABLE: 2,
				CHART: 3
			};

			if (self.equipmentViewer.infoTabMode) {

				tabMode = self.equipmentViewer.infoTabMode;

			} else if (self.parent.config.tabMode.infoTabMode) {

				tabMode = self.parent.config.tabMode.infoTabMode;
			}

			tabView = (tabMode === TAB_MODE.MOSAIC) ? "segmented-show-mosaic-" + self.uid : (tabMode === TAB_MODE.TABLE) ? "segmented-show-table-" + self.uid : "segmented-show-mosaic-" + self.uid;

			if ($$("segmented-" + self.uid)) {

				$$("segmented-" + self.uid).removeOption("optionToRemove-" + self.uid);
				$$("segmented-" + self.uid).setValue(tabView);

				if (!self.equipmentViewer.showChart) {

					$$("segmented-" + self.uid).hideOption("segmented-show-chart-" + self.uid);
				}
			}

		} else {

			if ($$("segmented-" + self.uid)) {

				$$("segmented-" + self.uid).setValue(tabView);
				$$("segmented-" + self.uid).removeOption("optionToRemove-" + self.uid);
				$$("segmented-" + self.uid).hide();
			}
		}
	}

	updateMeasures(elementValues) {

		if (!this.equipmentViewerInfoMeasure || !this.equipmentViewerTable || !this.equipmentViewerChart) {

			for (let group of this.measuresGroups) {

				let evToUpdate = group.elementValues.find(ev => ev.id === elementValues[0].id);

				if (evToUpdate) {

					evToUpdate.value = elementValues[0].value;
					evToUpdate.elementValueState = elementValues[0].elementValueState;
				}
			}
		}

		if (this.equipmentViewerInfoMeasure) {

			this.equipmentViewerInfoMeasure.updateMeasure(elementValues);
		}

		if (this.equipmentViewerTable) {

			this.equipmentViewerTable.updateMeasures(elementValues);
		}

		if (this.equipmentViewerChart) {

			this.equipmentViewerChart.updateMeasure(elementValues);
		}

		if (this.parent.graphicIconInstance) {

			this.parent.graphicIconInstance.updateValues(elementValues);
		}

		if (this.parent.graphicIconInstanceFromTable) {

			this.parent.graphicIconInstanceFromTable.updateValues(elementValues);
		}
	}

	updateState(elementTypeState) {

		if (elementTypeState != undefined) {

			let viewMainInfoImage = $$("view-main-info-image-" + this.uid);
			let viewMainTableImage = $$("view-main-info-table-image-" + this.uid);

			if (this.parent.graphicIconInstance) {

				this.parent.graphicIconInstance.updateState(elementTypeState);
			}

			if (this.parent.graphicIconInstanceFromTable) {

				this.parent.graphicIconInstanceFromTable.updateState(elementTypeState);
			}

			if (viewMainInfoImage) {

				viewMainInfoImage.setValues({ elementTypeState: elementTypeState });
			}

			if (viewMainTableImage) {

				viewMainTableImage.setValues({ elementTypeState: elementTypeState });
			}
		}
	}

	// TODO: Update alarms
	updateAlarms(alarms) {

		if (this.parent.graphicIconInstance) {

			this.parent.graphicIconInstance.updateAlarms(alarms);
		}
		else {

			// TODO: Update any in equipment viewer???
		}

		if (this.parent.graphicIconInstanceFromTable) {

			this.parent.graphicIconInstanceFromTable.updateAlarms(alarms);
		}
	}

	refresh() {

		if (this.equipmentViewerInfoMeasure) {

			this.equipmentViewerInfoMeasure.refresh();
		}
	}
}	
