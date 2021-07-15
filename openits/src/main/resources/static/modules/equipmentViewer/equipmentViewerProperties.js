import SearchDialogBox from "../../component/searchDialogBox.js";

export default class EquipmentViewerProperties {

	constructor(parent, element, equipmentViewer) {

		this.parent = parent;
		this.element = element;
		this.equipmentViewer = equipmentViewer;
		this.uid = parent.uid;
		this.tableProperties = null;
	}


	loadData() {

		let self = this;
		let elementValues = [];
		let dialogEntity = webix.copy(SearchDialogBox.CUSTOM);
		let label = i18n.t("LBL_EQUIPMENT_PROPERTIES") + " - " + self.element.alias;

		dialogEntity.data = [];

		dialogEntity.pkField = "id";
		dialogEntity.fields = ["element=LBL_PROPERTY", "elementType=LBL_PROPERTY_VALUE"];
		dialogEntity.height = 600;
		dialogEntity.width = 800;

		if (self.equipmentViewer.equipViewerConfigs && self.equipmentViewer.equipViewerConfigs.length > 0) {

			for (let equipViewerConfig of self.equipmentViewer.equipViewerConfigs) {

				let equipConfigs = self.element.elementValues.find(element => {
				return element.elementTypeParam.id === equipViewerConfig.voEntity.id;	
				});

				if (equipConfigs) {

					elementValues.push(equipConfigs);

				} else {

					elementValues = self.element.elementValues;
				}
			}

		} else {

			if (Array.isArray(self.element.elementValues)) {

				elementValues = self.element.elementValues;
			}
		}

		for (let elementValue of elementValues) {

			if (elementValue.elementTypeParam.paramType.id === self.parent.config.paramType.config) {

				dialogEntity.data.push({
					id: elementValue.elementTypeParam.id,
					element: i18n.t(elementValue.elementTypeParam.labelAlias),
					elementType: elementValue.value
				});
			}
		}

		let s = new SearchDialogBox(label, dialogEntity);

		s.show();

	}
}