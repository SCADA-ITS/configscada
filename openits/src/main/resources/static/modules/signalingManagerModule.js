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

			//initSignalingModule();

			//initListView();

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

				self.args.selectedPks.forEach(id => {

					let element = window.em.getByField(EntityType.ELEMENT, "id", id);

					if (element.elementType && element.elementType.id) {

						let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", element.elementType.id);

						if (elementType) {

							if (element.elementSubtype && element.elementSubtype.id &&
								elementType.elementSubtypes && elementType.elementSubtypes.length > 0) {

								let elementSubtype = elementType.elementSubtypes.find(e => e.id === element.elementSubtype.id);

								if (elementSubtype) {

									let listElement = self.mapElements.get(element.elementSubtype.id);

									if (!listElement) {

										listElement = [];

										self.mapElements.set(element.elementSubtype.id, listElement);
									}
									listElement.push(id);

									elementTypeView.push({
										id: element.elementSubtype.id,
										alias: i18n.t(elementType.labelAlias) + ' - ' + i18n.t(elementSubtype.labelAlias),
										description: i18n.t(elementSubtype.labelDescription),
										image: elementType.elementTypePhoto
									});
								}

							} else if (element.elementType.commandModule) {

								let listElement = self.mapElements.get(element.elementType.id);

								if (!listElement) {

									listElement = [];

									self.mapElements.set(element.elementType.id, listElement);
								}
								listElement.push(id);

								elementTypeView.push({
									id: element.elementType.id,
									alias: i18n.t(elementType.labelAlias),
									description: i18n.t(elementType.labelDescription),
									image: elementType.elementTypePhoto
								});
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

				//events[ListView.EventTypes.ON_SELECT_ROW] = fillSignalingModule;

				self.listViewElementType = new ListView(elementTypeView, null, events);

				viewListElementType = self.listViewElementType.getView();
			}

			view.rows.push(viewListElementType);

			console.log(self.mapElements);

			return view;
		}

		function getViewListElements() {

			let view = {};

			return view;
		}
		
		function initSignalingModule() {

		}

		function initListView() {

			if (self.listViewElementType && this.mapElements && this.mapElements.size > 0) {

				self.listView.selectByIndex(0);
			}
		}
		function fillSignalingModule(elementTypeArray, listView) {

			let elementTypeId;

			if (listView) {

				listView.disable();
			}

			if (Array.isArray(elementTypeArray) && elementTypeArray.length == 1) {

				elementTypeId = elementTypeArray[0];
			}

			self.elementTypeIdSelected = elementTypeId;


			if (listView) {

				listView.enable();
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
							height: 300,
							cols: [
								getViewListElementTypes()
							]
						},
						{}
					]
				}
			};

			let viewMainResizer = {
				id: "view-main-resizer-" + self.uid,
				view: "resizer"
			};

			let viewMainContent = {
				id: "view-main-content-" + self.uid,
				body: getViewContent()
			};


			let module = window.em.getByField(EntityType.MODULE, "id", 901);

			if (module) {

				addModule(module, null);
			}

			viewMain.cols = [viewMainListEquipments, viewMainResizer, viewMainContent];

			function getViewContent() {

				let viewContent = {};

				viewContent = {
					rows: [
						{
							id: "view-main-content-signaling-module-" + self.uid,
						}
					]
				};

				return viewContent;
			}

			function addModule(module, moduleActionId) {

				let moduleAction = { id: moduleActionId };
				let args = { selectedPks: [] };

				util.module.open(module, null, VIEW_TYPE_CUSTOM, drawContent, args);

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

			webix.ui(viewMain, $$(self.uid));
		}
	}


}
