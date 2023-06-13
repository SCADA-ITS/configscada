import { util } from "../../lib/util.js";
import EquipmentList from "../../component/equipmentList.js";

export default class HistoricalChartWizard {

	constructor(parent, onAccept) {

		this.parent = parent;
		this.onAccept = onAccept;
		this.uid = webix.uid();
	}

	drawComponent() {

		let self = this;
		let elementTypes = window.em.getAll(EntityType.ELEMENT_TYPE);
		let elements = window.em.getAll(EntityType.ELEMENT);

		if (Array.isArray(elementTypes) && elementTypes.length > 0) {

			if (Array.isArray(elements) && elements.length > 0) {

				elementTypes = elementTypes.filter(elementType => (elements.findIndex(element => element.elementType.id === elementType.id) !== -1));
			}
		}

		let equipmentList = new EquipmentList(elementTypes, onSelect);

		function onSelect(items) {

			if (Array.isArray(items) && items.length > 0) {

				$$("dialog-accept-" + self.uid).enable();

			} else {

				$$("dialog-accept-" + self.uid).disable();
			}
		}

		let equipmentViewBody = {
			id: "equipmentList-" + self.uid,
			rows: [
				{
					cols: [
						{ width: 20 },
						{
							rows: [
								{ height: 20 },
								equipmentList.getView(),
								{ height: 15 }
							]
						},
						{ width: 20 }
					]
				},
				{
					cols: [
						{ width: 15 },
						{
							id: "toolbar-" + self.uid,
							view: "toolbar",
							height: 45,
							borderless: true,
							elements: [
								{
									view: "button",
									css: "dialog-cancel",
									width: 130,
									height: 35,
									label: i18n.t("LBL_CANCEL"),
									on: {
										onItemClick() {

											$$(equipmentListWindow).close();
											$$(MAIN_TAB_ID).removeView(self.parent.args.containerId);
										}
									}
								},
								{},
								{
									id: "dialog-accept-" + self.uid,
									view: "button",
									css: "dialog-accept",
									width: 130,
									height: 35,
									label: i18n.t("LBL_ACCEPT"),
									on: {
										onItemClick() {

											self.parent.elementsIds = equipmentList.tableView.getSelectedIds();
											self.onAccept();
											$$(equipmentListWindow).close();
										}
									}
								}
							]
						},
						{ width: 15 }
					]
				}
			]
		};

		let equipmentListWindow = util.ui.loadWindow(i18n.t("LBL_ALARM_FILTER_LIST_EQUIPMENT_GRID"), equipmentViewBody, 600, 1100, true);

		if (equipmentList.listView && equipmentList.tableView) {

			equipmentList.tableView.callbacks();
			equipmentList.listView.selectByIndex(0);
		}

	}
}