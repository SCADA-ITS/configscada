import GenericModule from "./genericModule.js";
import OptionButtonList from "../component/optionButtonList.js";
import MessageBox from "../component/messageBox.js";
import TableMessageBox from "../component/tableMessageBox.js";

const ACTION_MAINTENANCE_ON = 1;
const ACTION_MAINTENANCE_OFF = 2;

export default class EquipmentCommandModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);
		this.config = module.config;
		this.action = undefined;
	}

	getView() {

		if (this.moduleAction.id === this.config.moduleAction.maintenanceMode && Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0 &&
			checkMaintenanceState(this.args.selectedPks)) {

			return this.getDefaultView();

		} else {

			let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_WRONG_SELECTION_DESCRIPTION"), null, null, null);
			ms.show();
		}

		function checkMaintenanceState(elementIds) {

			let response = true;

			for (let index = 0; index < elementIds.length && response; index++) {

				response = false;
				let element = window.em.getByField(EntityType.ELEMENT, "id", elementIds[index]);

				if (element && element.elementType && element.elementType.id) {

					let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);

					response = elementType && elementType.maintenanceState;
				}
			}

			return response;
		}

	}

	loadData() {

		let self = this;

		if ($$(this.uid)) {

			$$("loading-" + self.uid).show();
			$$(self.uid).hide();

			let viewMain = {
				id: self.uid,
				rows: [
					{ height: 20 },
					{
						heigth: 250,
						cols: [
							getViewOptionButtonList()
						]
					},
					{ height: 5 },
					{
						cols: [
							{ width: 50 },
							{
								view: "textarea",
								id: "txt-dialog-" + self.uid,
								scroll: "auto",
								placeholder: i18n.t("LBL_COMMENTS_HERE"),
								disabled: true
							},
							{ width: 50 }
						]
					},
					{ height: 60 },
					{
						cols: [
							{ width: 20 },
							{
								view: "button",
								css: "dialog-cancel",
								width: 130,
								height: 40,
								label: i18n.t("LBL_CANCEL"),
								click: function() {

									$$(self.args.containerId).close();
								}
							},
							{},
							{
								view: "button",
								css: "dialog-accept",
								width: 130,
								height: 40,
								label: i18n.t("LBL_ACCEPT"),
								click: function() {
									sendElementState();
								}
							},
							{ width: 20 }
						]
					},
					{ height: 10 }
				]
			};

			$$("loading-" + self.uid).hide();
			$$(self.uid).show();

			webix.ui(viewMain, $$(self.uid));
		}

		function getViewOptionButtonList() {

			let viewOptionButtonList;
			let optionButtonList;

			let options = [{
				id: ACTION_MAINTENANCE_ON,
				label: i18n.t("LBL_MAINTENANCE_MODE_ON"),
				icon: "mdi mdi-wrench",
				description: i18n.t("LBL_MAINTENANCE_MODE_ON_DESCRIPTION"),
				default: false
			}, {
				id: ACTION_MAINTENANCE_OFF,
				label: i18n.t("LBL_MAINTENANCE_MODE_OFF"),
				icon: "mdi mdi-undo",
				description: i18n.t("LBL_MAINTENANCE_MODE_OFF_DESCRIPTION"),
				default: false
			}];


			optionButtonList = new OptionButtonList(options, undefined, onToggle, OptionButtonList.MODE_TOGGLE);

			viewOptionButtonList = optionButtonList.getView();

			function onToggle(id) {

				self.action = id;

				if (id === ACTION_MAINTENANCE_ON) {
					
					$$("txt-dialog-" + self.uid).enable();
					
				} else {
					
					$$("txt-dialog-" + self.uid).disable();
				}
				
				$$("txt-dialog-" + self.uid).setValue("");
			}
			return viewOptionButtonList;
		}

		function sendElementState() {

			webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.config.restEndpoints.elements, JSON.stringify(self.args.selectedPks)).then(function(data) {

				let responseElements = data.json();
				let elementStates = [];

				let table = {
					fields: ["idIcon=", "element=LBL_EQUIPMENT", "currentElementTypeState=LBL_CURRENT_ELEMENT_TYPE_STATE", "newElementTypeState=LBL_NEW_ELEMENT_TYPE_STATE"],
					pkField: "id",
					data: [],
					icons: {
						idIcon: [
							{
								id: 1,
								icon: "img/grid/confirmed.png"
							},
							{
								id: 2,
								icon: "img/grid/empty.png"
							}]
					},
					width: 600,
					height: 650
				};

				if (Array.isArray(responseElements) && responseElements.length > 0) {

					responseElements.forEach(element => {

						if (element.elementState && element.elementState.currentElementTypeState &&
							element.elementState.currentElementTypeState.id && element.elementType && element.elementType.id) {

							let currentElementTypeState = window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", element.elementState.currentElementTypeState.id);

							if (currentElementTypeState) {

								let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);
								let newElementTypeState = self.action === ACTION_MAINTENANCE_ON ? window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", elementType.maintenanceState.id) : window.em.getByField(EntityType.ELEMENT_TYPE_STATE, "id", elementType.initialState.id);

								if (currentElementTypeState.id !== newElementTypeState.id) {

									elementStates.push({
										elementId: element.id,
										currentElementTypeStateId: newElementTypeState.id,
										maintenanceComment: self.action === ACTION_MAINTENANCE_ON ? $$("txt-dialog-" + self.uid).getValue() : undefined
									});
								}

								table.data.push({
									id: element.id,
									idIcon: currentElementTypeState.id !== newElementTypeState.id ? 1 : 2,
									element: element.alias,
									currentElementTypeState: i18n.t(currentElementTypeState.labelAlias),
									newElementTypeState: i18n.t(newElementTypeState.labelAlias),
								});
							}
						}
					});


					let body = {

						elementStates: elementStates
					};

					if (elementStates.length === 0) {

						$$(self.args.containerId).close();

					} else if (elementStates.length === 1) {

						webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.changeState, body).then((data) => {

							$$(self.args.containerId).close();
							(new MessageBox(MessageBox.INFO, i18n.t("LBL_CHANGE_STATE_SEND_SUCCESSFULLY"), null, null, null)).show();

						});

					} else {

						$$(self.args.containerId).close();

						let s = new TableMessageBox(i18n.t("LBL_CONFIRM"), TableMessageBox.CONFIRM, table, function(items) {

							webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.changeState, body).then((data) => {

								(new MessageBox(MessageBox.INFO, i18n.t("LBL_CHANGE_STATES_SEND_SUCCESSFULLY"), null, null, null)).show();

							});

						});
						s.show();
					}
				}

			});
		}
	}
}