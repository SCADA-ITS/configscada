import GenericModule from "../modules/genericModule.js";
import ListView from "../component/listview.js";
import { util } from "../../lib/util.js";




export default class signalingManagerModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.listViewElementType = undefined;
		this.elementTypeIdSelected = undefined;
		this.config = module.config;
		this.mapElements = undefined;
		this.mapCommandModule = undefined;
	}

	getView() {

		let view = undefined;

		if (this.moduleAction.id === this.config.moduleAction.commandEquipments) {
			view = {
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
										hidden: false
									},
									{
										id: "loading-" + this.uid,
										rows: [getViewDrawNoData()],
										hidden: true
									},
									{ width: 6 }
								]
							},
							{ height: 6 }
						]
					}
				]
			};
		}

		function getViewDrawNoData() {

			return {
				view: "template",
				template: function() {

					let icon = "<img src='img/loading.svg' align='center'>";

					return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
				}
			}
		}

		return view;
	}

	loadData() {

		if (this.moduleAction.id === this.config.moduleAction.commandEquipments) {

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

							} else if (element.elementType.commandModule) {

								let listElement = self.mapElements.get(element.elementType.id);

								if (!listElement) {

									listElement = [];

									self.mapElements.set(element.elementType.id, listElement);
									self.mapCommandModule.set(element.elementType.id, element.elementType.commandModule);

									elementTypeView.push({
										id: element.elementType.id,
										alias: i18n.t(elementType.labelAlias),
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

				self.listViewElementType = new ListView(elementTypeView, null, events);

				viewListElementType = self.listViewElementType.getView();
			}

			view.rows.push(viewListElementType);

			console.log(self.mapElements);

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

				let listView = new ListView(elementsView, options, null);

				viewListElements = listView.getView();
			}

			view.rows.push(viewListElements);

			return view;
		}

		function fillData(elementTypeArray, listView) {

			let elementTypeId;

			if (listView) {

				listView.disable();
			}

			if (Array.isArray(elementTypeArray) && elementTypeArray.length == 1) {

				elementTypeId = elementTypeArray[0];
			}

			self.elementTypeIdSelected = elementTypeId;

			fillViewSignalingModule();

			fillViewListElements();

			if (listView) {

				listView.enable();
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
				body: {
					width: 350,

					rows: [
						{
							height: 310,
							cols: [
								getViewListElementTypes()
							]
						},
						{
							view: "label",
							label: i18n.t("LBL_LIST_EQUIPMENTS"),
						},
						{
							id: "view-list-element-" + self.uid,
							cols: [
								getViewListElements()
							]
						}

					]
				}
			};

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


			webix.ui(viewMain, $$(self.uid));
		}
	}


}
