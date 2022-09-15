import { util } from "../../lib/util.js";
import DataTable from "../../component/datatable.js";
import EquipmentViewerInfoIcon from "./equipmentViewerInfoIcon.js";

const DEFAULT_PRECISION = 2;

export default class EquipmentViewerTable {

	constructor(parent, element, equipmentViewer, measuresGroups) {

		this.parent = parent;
		this.element = element;
		this.equipmentViewer = equipmentViewer;
		this.measuresGroups = measuresGroups;
		this.data = [];
		this.uid = parent.uid;
	}

	getView() {

		let self = this;
		let mainTable = {};
		let valuesForPrint;

		if (Array.isArray(self.measuresGroups) && self.measuresGroups.length > 0) {

			self.measuresGroups.forEach(group => {

				group.elementValues.forEach(ev => {

					valuesForPrint = util.element.getValuesForPrint(null, ev);
					valuesForPrint.id = ev.id;
					valuesForPrint.group = i18n.t(group.group.label);

					let paramType = (ev.elementTypeParam.paramType.id === "ParamType:1") ? i18n.t("LBL_CONFIG") : i18n.t("LBL_EQUIPMENT_MEASURES");
					valuesForPrint.paramType = paramType;
					self.data.push(valuesForPrint);
				});
			});
		}

		mainTable = {
			id: "view-main-table-" + self.uid,
			rows: [
				{ height: 10 },
				{
					cols: [
						{ width: 10 },
						{
							css: { "border": "1px solid #dadee0", "box-sizing": "border-box" },
							rows: [getImage()]

						},
						{ width: 10 },
						{
							css: { "border": "1px solid #dadee0", "box-sizing": "border-box" },
							rows: getTable()
						},
						{ width: 10 }
					]
				},
				{ height: 10 }
			],
			on: {
				onDestruct: function(){
					
					self.parent.args.fromTable = false;
				}
			}
		};

		if (self.measureTable) {

			self.parent.equipmentViewerTable = self.measureTable;
		}

		function getTable() {

			const defaultGridOptionValues = [
				{ gridOption: { id: 1 }, value: "false" },
				{ gridOption: { id: 2 }, value: "true" },
				{ gridOption: { id: 3 }, value: 4 },
				{ gridOption: { id: 4 }, value: "asc" },
				{ gridOption: { id: 7 }, value: "true" },
				{ gridOption: { id: 8 }, value: "false" },
				{ gridOption: { id: 9 }, value: "true" },
				{ gridOption: { id: 11 }, value: "true" },
				{ gridOption: { id: 12 }, value: "true" },
				{ gridOption: { id: 13 }, value: "true" },
				{ gridOption: { id: 16 }, value: "img/grid/default.png" },
				{ gridOption: { id: 21 }, value: "custom" },
				{ gridOption: { id: 23 }, value: "true" }
			];

			let metadata = {
				filter: true,
				label: "LBL_MEASURES",
				visible: true,
				gridOptionValues: defaultGridOptionValues,
				gridFields: [
					{
						id: 1,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: true,
						needsTranslation: false,
						parameterized: false,
						position: 1,
						required: true,
						sourceField: "id",
						visible: false,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						id: 2,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "",
						needsTranslation: false,
						parameterized: false,
						position: 2,
						required: true,
						sourceField: "icon",
						visible: true,
						widthPx: 35,
						gridFieldIcons: [
							{
								id: 1,
								avatar: false,
								badgeColor: false,
								enabled: true,
								icon: function() {

									let icon;

									for (let dat of self.data) {

										icon = (dat.icon != "not_found_50.png") ? "./img/grid/" + dat.icon + ".png" : "./img/grid/empty.png";
									}

									return icon;
								}(),
								showValue: false,
								value: "not_found_50.png"
							}],
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						id: 3,
						extended: false,
						filtered: true,
						isEpochMilli: false,
						isPk: true,
						label: "",
						needsTranslation: false,
						parameterized: true,
						position: 3,
						required: true,
						sourceField: "paramType",
						visible: true,
						widthPx: 35,
						gridFieldIcons: [
							{
								id: 1,
								avatar: false,
								badgeColor: false,
								enabled: true,
								icon: "./img/grid/conf.png",
								showValue: false,
								value: i18n.t("LBL_CONFIG"),
								labelValue: "LBL_CONFIG"
							},
							{
								id: 2,
								avatar: false,
								badgeColor: false,
								enabled: true,
								icon: "./img/grid/measure.png",
								showValue: false,
								value: i18n.t("LBL_EQUIPMENT_MEASURES"),
								labelValue: "LBL_EQUIPMENT_MEASURES"
							}
						],
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						id: 4,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_PARAM",
						labelTooltip: "LBL_PARAM",
						needsTranslation: false,
						parameterized: false,
						position: 4,
						required: true,
						sourceField: "label",
						visible: true,
						widthPx: 250,
						//adjust: true,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						id: 5,
						extended: false,
						filtered: true,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_GROUP",
						needsTranslation: false,
						parameterized: true,
						position: 5,
						required: true,
						sourceField: "group",
						widthPx: 200,
						visible: (self.measuresGroups && self.measuresGroups.length > 1),
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						id: 6,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "",
						needsTranslation: false,
						parameterized: false,
						position: 6,
						required: true,
						sourceField: "iconElementValueState",
						visible: true,
						widthPx: 35,
						gridFieldIcons: [
							{
								id: 1,
								avatar: false,
								badgeColor: false,
								enabled: true,
								icon: "./img/state/ElementValueState_1.png",
								showValue: false,
								value: "ElementValueState_1.png",
								labelValue: "LBL_ELEMENT_VALUE_STATE_INITIALIZED"
							},
							{
								id: 2,
								avatar: false,
								badgeColor: false,
								enabled: true,
								icon: "./img/state/ElementValueState_2.png",
								showValue: false,
								value: "ElementValueState_2.png",
								labelValue: "LBL_ELEMENT_VALUE_STATE_ONLINE"
							},
							{
								id: 3,
								avatar: false,
								badgeColor: false,
								enabled: true,
								icon: "./img/state/ElementValueState_3.png",
								showValue: false,
								value: "ElementValueState_3.png",
								labelValue: "LBL_ELEMENT_VALUE_STATE_ALERTED"
							},
							{
								id: 4,
								avatar: false,
								badgeColor: false,
								enabled: true,
								icon: "./img/state/ElementValueState_4.png",
								showValue: false,
								value: "ElementValueState_4.png",
								labelValue: "LBL_ELEMENT_VALUE_STATE_OFFLINE"
							},
							{
								id: 5,
								avatar: false,
								badgeColor: false,
								enabled: true,
								icon: "./img/state/ElementValueState_5.png",
								showValue: false,
								value: "ElementValueState_5.png",
								labelValue: "LBL_MEASURE_OUT_OF_RANGE"
							}
						],
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						id: 7,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_VALUE",
						labelTooltip: "LBL_VALUE",
						needsTranslation: false,
						parameterized: false,
						position: 7,
						required: true,
						sourceField: "value",
						visible: true,
						widthPx: 200,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					},
					{
						id: 8,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_UNIT",
						labelTooltip: "LBL_UNIT",
						needsTranslation: false,
						parameterized: false,
						position: 8,
						required: true,
						sourceField: "unit",
						visible: true,
						widthPx: 125,
						gridFieldDataType: {
							id: 1,
							name: "string"
						}
					}
				]
			};

			self.measureTable = new DataTable(null, null, metadata, self.data, null);

			return self.measureTable.getView();
		}

		function getImage() {

			self.parent.args.fromTable = true;

			self.equipmentViewerInfoIcon = new EquipmentViewerInfoIcon(self.parent, self.element, self.equipmentViewer);
			return self.equipmentViewerInfoIcon.getView();
		}

		return mainTable;


	}

	updateMeasures(elementValue) {

		let self = this;

		if (Array.isArray(elementValue) && elementValue.length > 0) {

			for (let ev of elementValue) {

				let dataToUpdate = self.data.find(dat => dat.id === ev.id);

				if (dataToUpdate && ((dataToUpdate.value != ev.value) || (ev.elementValueState && dataToUpdate.elementValueState != ev.elementValueState.id))) {

					if (ev.elementTypeParam.dataType.id === DataType.DECIMAL) {

						let precision = ev.elementTypeParam.precision ? ev.elementTypeParam.precision : DEFAULT_PRECISION;
						let val = ev.value.replace(",", ".");
						let parseValue = (typeof (val) === "string") ? parseFloat(val) : val;

						dataToUpdate.value = parseValue.toFixed(precision);

					} 
					else if (ev.elementTypeParam.dataType.id === DataType.TIMESTAMP) {

						let parseValue = (typeof(ev.value) === "string") ? parseInt(ev.value) : ev.value;

						dataToUpdate.value = util.toLocaleString(parseValue / 1000);
					}
					else {

						dataToUpdate.value = ev.value;						
					}
					
					if (ev.elementValueState) {

						let iconElementValueState = ev.elementValueState.id.replace(":", "_") + ".png";
						dataToUpdate.iconElementValueState = iconElementValueState;
						dataToUpdate.elementValueState = ev.elementValueState.id;
					}

					self.measureTable.update(dataToUpdate);
				}
			}
		}
	}
}