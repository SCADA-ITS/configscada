import GenericModule from "../modules/genericModule.js";
import TreeView from "../component/treeview.js";
import { util } from "../lib/util.js";


export default class treeSectModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.config = module.config;
	}

	getView() {

		let view = undefined;

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

		let self = this;

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();

			webix.ajax().get(appConfig.restUrl + util.fillTemplate("trees/${id}", { id: "1" })).then(function(dat) {

				if (dat != null) {

					let metadata = dat.json().metadata;
					let data = dat.json().data;

					drawComponents(metadata, data);

					$$("loading-" + self.uid).hide();
					$$(self.uid).show();

				}
			});
		}

		function drawComponents(metadata, data) {

			let viewMain = {
				id: self.uid,
				cols: []
			};

			let events = {};

			events[TreeView.EventTypes.ON_CHECK_ITEM] = function(items) {

				console.log(items.join());
			};

			let treeView = new TreeView(metadata, data, events);

			let viewMainListEquipments = {
				id: "view-main-list-equipments-" + self.uid,
				header: i18n.t("LBL_EQUIPMENT_TYPES"),
				body: {
					width: 200,
					rows: [
						{
							cols: [
								treeView.getView()
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
				body: getViewContent()
			};

			let module = window.em.getByField(EntityType.MODULE, "id", 901);

			if (module) {

				addModule(module, null);
			}

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

			function addModule(module) {

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
			viewMain.cols = [viewMainListEquipments, viewMainResizer, viewMainContent];

			webix.ui(viewMain, $$(self.uid));
		}
	}
}
