import GenericModule from "../../modules/genericModule.js";
import ElementCommand from "../../classes/ElementCommand.js";

export default class genericSignalingModule

	extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.config = module.config;
	}

	getView() {

		let view = undefined;

		if (this.moduleAction.id === this.config.moduleAction.commandEquipments &&
			Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {

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

		if (this.moduleAction.id === this.config.moduleAction.commandEquipments &&
			Array.isArray(this.args.selectedPks) && this.args.selectedPks.length > 0) {

			this.refresh();
		}
	}

	refresh() {

		let self = this;

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();

			drawComponents();

			$$("loading-" + self.uid).hide();
			$$(self.uid).show();
		}

		function drawComponents() {

			let filteredElements = self.args.selectedPks;
			let elementCommand = new ElementCommand(filteredElements);

			let viewMainContent = {
				id: "view-main-content-" + self.uid,
				body: elementCommand.getComponent(),
				on: {
					onHide: function(obj) {
						$$("view-main-content-" + self.uid).destructor();
					},
				},
			};

			let viewMain = {
				id: self.uid,
				cols: [viewMainContent]
			};

			webix.ui(viewMain, $$(self.uid));
		}
	}

}
