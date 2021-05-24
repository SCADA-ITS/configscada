import DataTable from "../../component/datatable.js";
import { util } from "../../lib/util.js";

export default class EquipmentViewerProperties {

	constructor(parent, element, equipmentViewer) {

		this.parent = parent;
		this.element = element;
		this.equipmentViewer = equipmentViewer;
		this.uid = parent.uid;
		this.tableProperties = null;
	}

	getView() {
		
		let view = {
			css: "custom_window",
			cols: [
				{
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									id: this.uid,
									rows: [],
									hidden: true
								},
								{
									id: "loading-" + this.uid,
									rows: util.ui.getViewDrawNoData(),
									hidden: false
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
		};

		this.loadData();
		return view;
	}


	loadData() {

		let self = this;

		$$("loading-" + this.uid).show();

		drawComponents();

		if (self.tableProperties) {

			self.tableProperties.callbacks();
		}

		function drawComponents() {

			let viewMain = {
				id: "properties" + self.uid,
				cols: [
					{ width: 20 },
					{
						rows: [
							{ height: 20 },
							getDatatable(),
							{ height: 20 },
							{
								cols: [
									{},
									{
										view: "button",
										css: "dialog-cancel",
										height: 41,
										width: 130,
										label: i18n.t("LBL_CLOSE"),
										on: {

											onItemClick() {

												$$(propertiesWindow).close();
											}
										}
									}
								]
							},
							{ height: 7 }
						]
					},
					{ width: 20 }
				]
			};

			$$("loading-" + self.uid).hide();


			function getDatatable() {

				let elementValues = [];

				const defaultGridOptionValues = [
					{ gridOption: { id: 1 }, value: "true" },
					{ gridOption: { id: 2 }, value: "true" },
					{ gridOption: { id: 3 }, value: "1" },
					{ gridOption: { id: 4 }, value: "asc" },
					{ gridOption: { id: 7 }, value: "false" },
					{ gridOption: { id: 8 }, value: "false" },
					{ gridOption: { id: 9 }, value: "true" },
					{ gridOption: { id: 10 }, value: "true" },
					{ gridOption: { id: 11 }, value: "false" },
					{ gridOption: { id: 12 }, value: "false" },
					{ gridOption: { id: 13 }, value: "true" },
					{ gridOption: { id: 14 }, value: "false" },
					{ gridOption: { id: 15 }, value: "false" },
					{ gridOption: { id: 16 }, value: "img/grid/default.png" }
				];

				let metadata = {
					filter: true,
					label: "LBL_EQUIPMENT_PROPERTIES",
					visible: true,
					gridOptionValues: defaultGridOptionValues,
					gridFields: [
						{
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
							widthPx: 1,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						},
						{
							id: 1,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							needsTranslation: false,
							parameterized: false,
							position: 2,
							required: true,
							sourceField: "property",
							visible: true,
							label: "LBL_PROPERTY",
							widthPx: 360,
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
							needsTranslation: false,
							parameterized: false,
							position: 3,
							required: true,
							sourceField: "value",
							visible: true,
							label: "LBL_PROPERTY_VALUE",
							widthPx: 360,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						}
					]
				};

				let data = [];

				if (self.equipmentViewer.equipViewerConfigs && self.equipmentViewer.equipViewerConfigs.length > 0) {

					for (let equipViewerConfig of self.equipmentViewer.equipViewerConfigs) {

						let equipConfigs = self.element.elementValues.find(element => element.elementTypeParam.id === equipViewerConfig.voEntity.id);

						if (equipConfigs) {
							
							elementValues.push(equipConfigs);

						} else {

							elementValues = self.element.elementValues;
						}
					}

				} else {

					elementValues = self.element.elementValues;
				}

				for (let elementValue of elementValues) {

					let row = {};

					row.id = elementValue.elementTypeParam.id;
					row.property = elementValue.elementTypeParam.labelAlias;
					row.value = elementValue.value;

					if (elementValue.elementTypeParam.paramType.id === self.parent.config.paramType.config) {
						data.push(row);
					}

				}

				self.tableProperties = new DataTable(null, null, metadata, data);

				let table = {
					rows:
						self.tableProperties.getView()
				}

				return table;
			}

			let propertiesWindow = util.ui.loadWindow(i18n.t("LBL_EQUIPMENT_PROPERTIES") + " - " + self.element.alias, viewMain, 600, 800, true)

		}
	}
}