import GenericModule from "../../modules/genericModule.js";
import { util } from "../../lib/util.js";

export default class EquipmentViewerModule extends GenericModule {
	
	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();

		this.config = module.config;
	}

	getView() {

		let view = {
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
									hidden: true
								},
								{
									id: "loading-" + this.uid,
									rows: [getViewDrawNoData()],
									hidden: false
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
		
		let viewMain = undefined;
		
		$$("loading-" + this.uid).show();
		$$(this.uid).hide();

		if (this.moduleAction.id === this.config.moduleAction.view) {

			viewMain = moduleActionView();
		}
		
		setTimeout(function() {
			
			$$("loading-" + self.uid).hide();
			$$(self.uid).show();
			
			if (viewMain) {
		
				webix.ui(viewMain, $$(self.uid));
			}
				
		}, 500);
		
		function moduleActionView() {
			
			return drawMain();
		}
		
		function drawMain() {
			
			let viewMain = {
				id: self.uid,
				cols: []
			};
			
			let viewMainTreeHierarchy = {
				id: "view-main-tree-hierarchy-" + self.uid,
				header: i18n.t("LBL_EQUIPMENT_HIERARCHY"),
				body: {
					width: 300,
					rows: [
						{
							cols: [
								getViewTreeHierarchy()
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
			
			let viewMainActionPanel = {
				id: "view-main-action-panel-" + self.uid,
				header: i18n.t("LBL_EQUIPMENT_ACTION_PANEL"),
				body: getViewActionPanel()
			};
			
			viewMain.cols = [viewMainTreeHierarchy, viewMainResizer, viewMainContent, viewMainActionPanel];
			
			function getViewTreeHierarchy() {
				
				let view = {};
				
				return view;
			}
			
			function getViewContent() {
				
				let view = {};
				
				return view;
			}
			
			function getViewActionPanel() {
				
				let view = {};
				
				view = {

					id: "viewActionPanel-" + self.uid,
					width: 300,
					css: "panel-action",
					rows: [
						{}
					]
				}
				
				return view;
			}
			
			return viewMain;
		}
		
		
	}
}	
