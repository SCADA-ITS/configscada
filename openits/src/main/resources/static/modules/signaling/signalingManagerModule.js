import GenericModule from "../../modules/genericModule.js";
import ListView from "../../component/listview.js";
import { util } from "../../lib/util.js";


export default class SignalingManagerModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.listViewElementType = undefined;
		this.elementTypeIdSelected = undefined;
		this.config = module.config;
		this.mapElements = undefined;
		this.mapCommandModule = undefined;
		this.useDefaultModue = false;
	}

	getView() {

		return super.getViewCheckModuleActionAndSelectedPks();
	}

	loadData() {

		if (this.moduleAction && this.moduleAction.id === this.config.moduleAction.commandEquipments) {

			this.refresh();
		}
	}

	refresh() {

		let self = this;

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();

			drawComponents();

			initListView();

			$$("loading-" + self.uid).hide();
			$$(self.uid).show();
		}

		function getViewListElementTypes() {

			let view = {};
			let viewListElementType = {};
			let elementTypeView = [];
			view.rows = [];

			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

				self.mapElements = new Map();
				self.mapCommandModule = new Map();

				self.args.selectedPks.forEach(id => {

					let element = window.em.getByField(EntityType.ELEMENT, "id", id);

					if (element.elementType && element.elementType.id) {

						let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);

						if (elementType) {

							if (element.elementSubtype && element.elementSubtype.id && element.elementSubtype.commandModule &&
								elementType.elementSubtypes && elementType.elementSubtypes.length > 0) {

								let elementSubtype = elementType.elementSubtypes.find(e => e.id === element.elementSubtype.id);

								if (elementSubtype) {

									let listElement = self.mapElements.get(element.elementSubtype.id);

									if (!listElement) {

										listElement = [];

										self.mapElements.set(element.elementSubtype.id, listElement);
										self.mapCommandModule.set(element.elementSubtype.id, element.elementSubtype.commandModule);

										elementTypeView.push({
											id: element.elementSubtype.id,
											alias: i18n.t(elementType.labelAlias) + ' - ' + i18n.t(elementSubtype.labelAlias),
											description: i18n.t(elementSubtype.labelDescription),
											image: elementType.elementTypePhoto
										});
									}
									listElement.push(id);
								}

							} else {

								let listElement = self.mapElements.get(element.elementType.id);

								if (!listElement) {

									let alias = undefined;
									let commandModule = undefined;

									if (element.elementType.commandModule) {

										alias = i18n.t(elementType.labelAlias);
										commandModule = element.elementType.commandModule;

									} else {
										alias = "(*) " + i18n.t(elementType.labelAlias);
										commandModule = self.config.defaultSignalingModuleAction;
										self.useDefaultModue = true;
									}

									listElement = [];

									self.mapElements.set(element.elementType.id, listElement);
									self.mapCommandModule.set(element.elementType.id, commandModule);

									elementTypeView.push({
										id: element.elementType.id,
										alias: alias,
										description: i18n.t(elementType.labelDescription),
										image: elementType.elementTypePhoto
									});
								}
								listElement.push(id);
							}
						}
					}

				});
			}
			if (elementTypeView.length > 0) {

				elementTypeView.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));

				elementTypeView.forEach(e => {
					e.alias = e.alias + " (" + self.mapElements.get(e.id).length + ")";
				});


				let events = {};

				events[ListView.EventTypes.ON_SELECT_ROW] = fillData;

				let options = {};

				options[ListView.Options.SEARCH] = false;

				self.listViewElementType = new ListView(elementTypeView, options, events);

				viewListElementType = self.listViewElementType.getView();
			}

			view.rows.push(viewListElementType);

			return view;
		}

		function getViewListElements() {

			let view = {};
			let viewListElements = {};
			let elementsView = [];
			view.rows = [];

			if (self.elementTypeIdSelected && self.mapElements) {

				let elementList = self.mapElements.get(self.elementTypeIdSelected);

				if (elementList && elementList.length > 0) {

					elementList.forEach(id => {

						let element = window.em.getByField(EntityType.ELEMENT, "id", id);

						if (element) {

							elementsView.push({
								id: element.id,
								alias: element.alias,
								description: element.description
							});
						}
					});
				}
				elementsView.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));

				let options = {};

				options[ListView.Options.ICON] = false;
				options[ListView.Options.SEARCH] = false;
				options[ListView.Options.ROW_HEIGHT] = 35;
				options[ListView.Options.COMPACT] = true;

				let listView = new ListView(elementsView, options, null);

				viewListElements = listView.getView();
			}

			view.rows.push(viewListElements);

			return view;
		}

		function fillData(elementTypeArray, callbackEnable) {

			let elementTypeId;

			if (callbackEnable) {

				callbackEnable(false);
			}

			if (Array.isArray(elementTypeArray) && elementTypeArray.length == 1) {

				elementTypeId = elementTypeArray[0];
			}

			self.elementTypeIdSelected = elementTypeId;

			fillViewSignalingModule();

			fillViewListElements();

			if (callbackEnable) {

				callbackEnable(true);
			}
		}

		function fillViewListElements() {

			let content = $$("view-list-element-" + self.uid);

			if (content) {

				let viewContent = {

					id: "view-list-element-" + self.uid,
					cols: [getViewListElements()]
				};

				webix.ui(viewContent, $$("view-list-element-" + self.uid));
			}
		}

		function fillViewSignalingModule() {

			if (self.elementTypeIdSelected && self.mapCommandModule && self.mapElements) {

				let moduleActionId = parseInt(self.mapCommandModule.get(self.elementTypeIdSelected));
				let elementIds = self.mapElements.get(self.elementTypeIdSelected);

				if (moduleActionId && elementIds) {

					let module = util.module.getFromModuleAction(moduleActionId);

					if (module) {

						let moduleAction = { id: moduleActionId };
						let args = { selectedPks: elementIds };

						util.module.open(module, moduleAction, VIEW_TYPE_CUSTOM, drawContent, args);

						function drawContent(view) {

							let content = $$("view-main-content-signaling-module-" + self.uid);

							if (content) {

								let viewContent = {
									rows: [
										{
											id: "view-main-content-signaling-module-" + self.uid,
											cols: [view]
										}
									]
								};

								webix.ui(viewContent, $$("view-main-content-signaling-module-" + self.uid));
							}
						}
					}
				}
			}
		}

		function initListView() {

			if (self.listViewElementType && self.mapElements && self.mapElements.size > 0) {

				self.listViewElementType.selectByIndex(0);
			}
		}

		function drawComponents() {

			let viewMain = {
				id: self.uid,
				cols: []
			};

			let viewMainListEquipments = {
				id: "view-main-list-equipments-" + self.uid,
				header: i18n.t("LBL_EQUIPMENT_TYPES"),
				hidden: (self.args.selectedPks.length === 1),
				body: {
					width: 350,
					rows: [

					]
				}
			};


			let viewListElementTypes = {

				height: 260,
				css: {"border-bottom": "1px solid #dadee0 !important"},
				cols: [
					getViewListElementTypes()
				]
			}

			let viewLabelDefaulSigalingModule = {

				view: "label",
				label: "(*) " + i18n.t("LBL_DEFAULT_SIGNALING_MODULE"),
				css: "label-default"
			}

			let viewLabelListEquipments = {

				view: "label",
				label: i18n.t("LBL_LIST_EQUIPMENTS"),
				height: 43,
				css: "label-list-equipments"
			}

			let viewListElements = {

				id: "view-list-element-" + self.uid,
				cols: [
					getViewListElements()
				]
			}

			if (self.useDefaultModue) {

				viewMainListEquipments.body.rows = [viewListElementTypes, viewLabelListEquipments, viewListElements, viewLabelDefaulSigalingModule];

			} else {
				viewMainListEquipments.body.rows = [viewListElementTypes, viewLabelListEquipments, viewListElements];
			}

			let viewMainResizer = {
				id: "view-main-resizer-" + self.uid,
				view: "resizer"
			};

			let viewMainContent = {
				id: "view-main-content-" + self.uid,
				body: {
					rows: [
						{
							id: "view-main-content-signaling-module-" + self.uid,
						}
					]
				}
			};

			viewMain.cols = [viewMainListEquipments, viewMainResizer, viewMainContent];

			if (self.args.selectedPks.length === 1) {
				
				let container = $$(self.args.containerId);

				if (container) {

					let element = window.em.getByField(EntityType.ELEMENT, "id", self.args.selectedPks[0]);
					
					util.ui.setWindowTitle(container, i18n.t("LBL_SIGNALING_MANAGER") + " - " + element.alias);
				}
			}


			webix.ui(viewMain, $$(self.uid));
		}
	}


}
