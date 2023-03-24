import { util } from "../../lib/util.js";
import DataTable from "../../component/datatable.js";
import SearchDialogBox from "../../component/searchDialogBox.js";

export default class TravelTimeConfigSectionDetail {

	constructor(config, travelTime) {

		this.config = config;
		this.travelTime = travelTime;
		this.uid = webix.uid();
		this.dtSections = undefined;
	}

	getView() {

		let self = this;
		let view = undefined;

		let data = [];

		if (Array.isArray(self.travelTime.sections) && self.travelTime.sections.length > 0) {

			data = self.travelTime.sections.map(e => {
				
				return {
					
					id: e.id,
					alias: e.alias,
					pk: e.pk
				}
			});
		}

		const defaultGridOptionValues = [
			{ gridOption: { id: 1 }, value: "false" },
			{ gridOption: { id: 2 }, value: "true" },
			{ gridOption: { id: 3 }, value: "3" },
			{ gridOption: { id: 4 }, value: "asc" },
			{ gridOption: { id: 7 }, value: "false" },
			{ gridOption: { id: 8 }, value: "false" },
			{ gridOption: { id: 9 }, value: "true" },
			{ gridOption: { id: 11 }, value: "false" },
			{ gridOption: { id: 13 }, value: "true" },
			{ gridOption: { id: 16 }, value: "img/grid/default.png" },
			{ gridOption: { id: 21 }, value: "auto" },
			{ gridOption: { id: 23 }, value: "false" }
		];

		let metadata = {
			filter: true,
			label: "LBL_TRAVEL_TIME_SECTIONS",
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
					label: "LBL_NAME",
					needsTranslation: false,
					parameterized: false,
					position: 2,
					required: true,
					sourceField: "alias",
					visible: true,
					gridFieldDataType: {
						id: 1,
						name: "string"
					}
				},
				{
					id: 3,
					extended: false,
					filtered: false,
					isEpochMilli: false,
					isPk: false,
					label: "PK",
					needsTranslation: false,
					parameterized: false,
					position: 3,
					required: false,
					sourceField: "pk",
					visible: true,
					gridFieldDataType: {
						id: 3,
						name: "decimal"
					}
				}
			],
			gridCommands: [
				{
					id: 1,
					value: "LBL_ADD",
					icon: "mdi mdi-plus",
					default_: false,
					label: "LBL_CMD_ADD",
					showText: false,
					position: 1,
					multiselect: false,
					itemRequired: false
				},
				{
					id: 2,
					value: "LBL_DELETE",
					icon: "mdi mdi-minus",
					default_: false,
					label: "LBL_CMD_DELETE",
					showText: false,
					position: 2,
					multiselect: true,
					itemRequired: true
				}
			]
		};

		let events = {};
		events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

		events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
			commandId: 1,
			function: () => {

				addSection();
			}
		});

		events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
			commandId: 2,
			function: (data) => {
				
				deleteSections(data);
			}
		});

		self.dtSections = new DataTable(null, null, metadata, data, events);

		view = {
			css: { "box-sizing": "border-box", "border-bottom": "1px solid #e2e2e2", "border-left": "1px solid #e2e2e2", "border-right": "1px solid #e2e2e2" },
			rows: self.dtSections.getView()
		};
		
		function addSection() {
			
			let dialogSection = webix.copy(SearchDialogBox.CALLBACK);				
			
			dialogSection.fillDialogEntity = fillDialogEntity;
			dialogSection.pkField = "id"
			dialogSection.fields = ["alias=LBL_NAME", "pk=LBL_PK"];
			dialogSection.sortFieldId = 1;
			dialogSection.height = 500;
			dialogSection.width = 600;
			
			function fillDialogEntity(dialogEntity, onFilledDialogEntity) {

				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsByType, { id: self.config.section.elementTypeId}))
			
					.then(function(elementsResponse) {
						
						let elements = elementsResponse.json();
						
						if (Array.isArray(elements)) {
							
							dialogEntity.data = elements.map(e => {
								
								let pk;
								
								if (Array.isArray(e.elementValues)) {
									
									let elementValuePk = e.elementValues.find(ev => ev.elementTypeParam.id === self.config.section.params.pk);
									
									if (elementValuePk) {
									
										pk = elementValuePk.value; 	
									}
								}

								return {
									id: e.id,
									alias: e.alias,
									pk: pk
								}
							});
							
							dialogEntity.data = dialogEntity.data.filter(e => self.travelTime.sections.find(sec => sec.id === e.id) == null);
						}
						
						onFilledDialogEntity(dialogEntity);
					}
				);	
			
			}
			
			(new SearchDialogBox(i18n.t("LBL_SELECT_SECTION"), dialogSection, onAccept)).show();
			
			function onAccept(selection) {

				if (Array.isArray(selection) && selection.length > 0) {
					
					let section = selection[0];
					
					let newSection = {
						id: section.id,
						alias: section.alias,
						pk: section.pk
					}
					
					self.dtSections.add(newSection);					
					self.travelTime.sections.push(newSection);
				}				
			}
		}
		
		function deleteSections(ids) {
			
			if (Array.isArray(ids) && ids.length > 0) {

				self.dtSections.delete(ids.map(e => {return { id : e }}));
				
				self.travelTime.sections = self.travelTime.sections.filter(e => !ids.includes(e.id));
			}
		}

		return view;
	}
	
	initialize() {

		if (this.dtSections) {
			
			this.dtSections.callbacks();
		}		
	}
}