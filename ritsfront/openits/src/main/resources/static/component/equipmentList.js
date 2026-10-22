import ListView from "../component/listview.js";
import DataTable from "../component/datatable.js";

export default class EquipmentList {

	constructor(elementTypes, onSelectElement) {

		this.elementTypes = elementTypes;
		this.onSelectElement = onSelectElement;
		this.listView = undefined;
		this.tableView = undefined;
		this.uid = webix.uid();
	}


	getView() {

		let self = this;
		let bodyContent = {};

		getListviewData();
		getDatatableData();

		bodyContent = {
			id: "equipmentList-content-" + self.uid,
			rows: [drawContent()]
		};


		function getListviewData() {

			let elementsList = [];

			if (Array.isArray(self.elementTypes) && self.elementTypes.length > 0) {

				self.elementTypes.forEach(e => {

					elementsList.push({
						id: e.id,
						alias: i18n.t(e.labelAlias),
						description: i18n.t(e.labelDescription),
						image: e.elementTypePhoto,
						grid: e.grid
					});
				});
			}

			if (Array.isArray(elementsList) != undefined && elementsList.length > 0) {

				elementsList.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));

				let listviewEvents = {};

				listviewEvents[ListView.EventTypes.ON_SELECT_ROW] = function(ids, callbackEnable) {

					if (callbackEnable) {

						callbackEnable(false);
					}

					if (Array.isArray(ids) && ids.length > 0) {

						self.tableView.clear();

						let elements = window.em.getAll(EntityType.ELEMENT);
						let elementsByType = elements.filter(element => element.elementType.id === ids[0]);

						if (Array.isArray(elementsByType) && elementsByType.length > 0) {

							elementsByType.forEach(element => {

								let row = {};

								row.id = element.id;
								row.alias = element.alias;
								row.description = element.description;

								self.tableView.add(row);
							});

							self.tableView.selectByIndex(0);
						}
					}

					if (callbackEnable) {

						callbackEnable(true);
					}
				};

				let options = {}

				options[ListView.Options.MODE] = ListView.DrawMode.COMPACT;

				self.listView = new ListView(elementsList, options, listviewEvents);
			}
		}

		function getDatatableData() {

			let metadata = {};
			let tableEvents = {};

			const defaultGridOptionValues = [
				{ gridOption: { id: 2 }, value: "true" },
				{ gridOption: { id: 3 }, value: "2" },
				{ gridOption: { id: 4 }, value: "asc" },
				{ gridOption: { id: 5 }, value: undefined },
				{ gridOption: { id: 6 }, value: undefined },
				{ gridOption: { id: 7 }, value: "false" },
				{ gridOption: { id: 8 }, value: "false" },
				{ gridOption: { id: 10 }, value: "true" },
				{ gridOption: { id: 11 }, value: "false" },
				{ gridOption: { id: 12 }, value: "false" },
				{ gridOption: { id: 13 }, value: "true" },
				{ gridOption: { id: 16 }, value: "img/grid/default.png" },
				{ gridOption: { id: 23 }, value: "false" }
			];

			metadata = {
				filter: true,
				label: "LBL_ELEMENTS",
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
						widthPx: 1,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: []
					},
					{
						id: 2,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_ALIAS",
						labelTooltip: "LBL_ALIAS",
						needsTranslation: true,
						parameterized: false,
						position: 2,
						required: true,
						sourceField: "alias",
						visible: true,
						widthPx: 230,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: []
					},
					{
						id: 3,
						extended: false,
						filtered: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_DESCRIPTION",
						labelTooltip: "LBL_DESCRIPTION",
						needsTranslation: true,
						parameterized: false,
						position: 3,
						required: true,
						sourceField: "description",
						visible: true,
						fillspace: true,
						widthPx: 400,
						gridFieldDataType: {
							id: 1,
							name: "string"
						},
						gridFieldIcons: []
					}
				],
				gridCommands: []
			};

			tableEvents[DataTable.EventTypes.ON_SELECT_ROW] = function(elementId) {

				if (self.onSelectElement != null) {

					self.onSelectElement(elementId);
				}
			};

			self.tableView = new DataTable(null, null, metadata, [], tableEvents);
		}

		function drawContent() {

			let viewContent = {};

			viewContent = {
				rows: [
					{
						cols: [
							{
								id: "viewList" + self.uid,
								width: 300,
								cols: [
									{
										//height: 385,
										rows: [
											{
												view: "label",
												borderless: true,
												height: 23,
												css: "headers",
												label: i18n.t("LBL_ELEMENT_TYPES")
											},
											{
//												css: {
//													"box-sizing": "border-box",
//													"border": "1px solid #dadee0 !important",
//													"border-top": "none !important",
//													"height": "362px !important"
//												},
												css: {
													"box-sizing": "border-box",
													"border": "1px solid #dadee0 !important",
													"border-top": "none !important"
												},
												cols: [self.listView.getView()]
											}
										]
									},
									{ width: 5 }
								]
							},
							{
								id: "viewTable" + self.uid,
								cols: [
									{ width: 5 },
									{
										rows: [
											{
												view: "label",
												borderless: true,
												height: 23,
												css: "headers",
												label: i18n.t("LBL_EQUIPMENT_S")
											},
											{ height: 5 },
											{
												cols: [
													{
														rows: self.tableView.getView() 
													}
												]
											}
										]
									}
								]
							}
						]
					}
				]
			};

			return viewContent;
		}

		return bodyContent;
	}

}