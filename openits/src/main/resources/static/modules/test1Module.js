import GenericModule from "../modules/genericModule.js";
import TreeView from "../component/treeview.js";
import { util } from "../lib/util.js";
import { adapter } from "../lib/adapter.js";


export default class Test1Module extends GenericModule {

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

			let events = {};
			
			events[TreeView.EventTypes.ON_CHECK_ITEM] = function(items) {

				console.log(items.join());
			};

			let treeView = new TreeView(metadata, data, events);

			let viewMain = {
				id: self.uid,
				cols: [
					{
						width: 400,
						rows: [
							treeView.getView()		
						]
					},
					{}
				]
			};
			
			webix.ui(viewMain, $$(self.uid));
		}
	}
}
