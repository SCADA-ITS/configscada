import GenericModule from "../modules/genericModule.js";
import TreeView from "../component/treeview.js";
import { util } from "../lib/util.js";
import DataTable from "../component/datatable.js";
import { adapter } from "../lib/adapter.js";

export default class VentilationModule extends GenericModule {
	

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		
		this.treeView;
		this.nodes;
		this.hierarchy;

		this.ventilations;
		this.selectedVentilation;
		this.tubeFans;
		this.tubeFansDataTable;
	}

	loadData() {

		let self = this;

		loadTree().then(function(res) {

			drawComponents();
			self.treeView.select(self.treeView.data[0].data[0].id);
		});

		async function loadTree() {

			let data = await webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsByIdType, { id: self.config.elementType }));
			
			self.ventilations = data.json();
			self.nodes = [];
			self.hierarchy = [];

			self.ventilations.forEach(e => {
				
				self.nodes.push({
					id: e.id,
					label: e.alias,
					description: e.description,
					props: {
						folder: "false",
						translate: "false"
					}
				});

				if(e.childs){
					
					e.childs.forEach(c => {
				
						self.nodes.push({
							id: c.id,
							label: c.alias,
							description: c.description,
							props: {
								folder: "false",
								translate: "false"
							}
						});
		
						self.hierarchy.push({
							id: self.hierarchy.length,
							parentId: e.id,
							childId: c.id
						});
					});
				}
			});
			
			self.treeView = new TreeView(getMetadata(), getData(), getEvents());

			function getMetadata() {
				
				let metadata = {
					id: 1,
					label: "LBL_TUNNELS",
					treeOptionValues: [
						{ treeOption: { id: 2}, value: false },	// multiselect
						{ treeOption: { id: 3}, value: true }, 	// sorted
						{ treeOption: { id: 4}, value: false }, // cmd_buttons
						{ treeOption: { id: 5}, value: false }, // search
						{ treeOption: { id: 6}, value: true },	// expand_all
						{ treeOption: { id: 7}, value: false }	// check_all
					]
				};
				
				return metadata;
			}
	
			function getData() {
				
				let data = util.tree.getNodes(self.nodes, self.hierarchy, { parentNodeId: "parentId", childNodeId: "childId" }, true);
				
				return data;
			}
	
			function getEvents() {
					
				let events = {};
	
				events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) { 

					loadContent();
				};
	
				events[TreeView.EventTypes.ON_SELECT_FOLDER] = function(item) { 
					
					let childs = self.treeView.data.find(e =>e.id==item.id);
					self.treeView.select(childs.data[0].id);
				};
				
				return events;
			}
		}

		function drawComponents() {

			$$("loading-" + self.uid).show();

			let viewMain = {
				id: self.uid,
				cols: [
					{
						id: "view-tree-" + self.uid,
						header: i18n.t("LBL_TUNNELS"),
						body: {
							width: 250,
							rows: [
								{
									cols: [
										self.treeView.getView()
									]
								}
							]
						}
					},
					{ view: "resizer" },
					{
						id: "view-content-" + self.uid,
						body: util.ui.getViewDrawNoData()
					}
				]
			};

			$$("loading-" + self.uid).hide();

			webix.ui(viewMain, $$(self.uid));
		}

		function loadContent() {

			webix.ui(util.ui.getViewDrawNoData(), $$("view-content-" + self.uid), 0);

			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementById, { id: self.treeView.getSelected()[0].id_})).then(function(data) {
				
				self.selectedVentilation = data.json();
				self.tubeFans = eval("adapter." + self.config.adapters.fans)(self.selectedVentilation.childs, null);
				webix.ui(getViewContent(), $$("view-content-" + self.uid), 0);
	
				if(self.tubeFansDataTable){
					self.tubeFansDataTable.callbacks();
				}
			});
		}

		function getViewContent() {
			
			let viewContent = {
				rows: [
					{ height: 6 },
					getContentHeader(),
					{ height: 12 },
					{
						cols: [
							{ width: 6 },
							getContentToolbar(),
							{ width: 6 }
						]
					},
					{
						cols: [
							{ width: 6 },
							getContentBody(),
							{ width: 6 }
						]
					}
				]
			};

			function getContentHeader() {

				let header = {
					id: "view-content-header-" + self.uid,
					borderless: true,
					rows: [
						{
							id: "view-content-header-bar-color-" + self.uid,
							height: 15,
							css: { "background": util.element.getColor(self.selectedVentilation.elementTypeState) }
						},
						{
							id: "view-content-header-info" + self.uid,
							height: 80,
							template: function() {

								return `<image class="ims-incident-report-icon" src="./img/ventilation.png" />
										<div class="ims-incident-report-title">
											${self.selectedVentilation.alias}
										</div>
										<div class="ims-incident-report-title">
											<span class="ims-incident-badge" style="background: ${util.element.getColor(self.selectedVentilation.elementTypeState)}; color: white;">${i18n.t(self.selectedVentilation.elementTypeState.labelAlias)}</span>
										</div>`;
							}
						}
					]
				};
			
				return header;
			}

			return viewContent;
		}

		function getContentToolbar() {
					
			let viewContentToolbar = {
				
				id: "view-content-toolbar-" + self.uid,
				view: "toolbar",
				height: 28,
				elements: [],
				borderless: true
			}
			
			let actionPanelSwitcher = {
				view: "segmented",
				css: "segment-custom-2",
				value: "action-panel-switcher-auto" + self.uid,
				width: 500,
				options: [
					{
						id: "action-panel-switcher-auto" + self.uid,
						value: "<span>" + i18n.t("LBL_AUTO") + "</span>",
					},
					{
						id: "action-panel-switcher-auto-maintenance" + self.uid,
						value: "<span>" + i18n.t("LBL_AUTO_MAINTENANCE") + "</span>",
					},
					{
						id: "action-panel-switcher-manual" + self.uid,
						value: "<span>" + i18n.t("LBL_MANUAL") + "</span>",
					}
				],
				tooltip: function(obj) {

					if (obj.id === "action-panel-switcher-auto" + self.uid) {

						return i18n.t("LBL_AUTO");
					}
					else if (obj.id === "action-panel-switcher-auto-maintenance" + self.uid) {

						return i18n.t("LBL_AUTO_MAINTENANCE");
					}
					else if (obj.id === "action-panel-switcher-manual" + self.uid) {

						return i18n.t("LBL_MANUAL");
					}
				},
				on: {
					onChange(newv, oldv) {

						if (newv === "action-panel-switcher-auto" + self.uid) {
							
							webix.message("show-all");
						}
						else if (newv === "action-panel-switcher-auto-maintenance" + self.uid) {

							webix.message("show-top");
						}
						else if (newv === "action-panel-switcher-manual" + self.uid) {

							webix.message("show-bottom");
						}
					}
				}
			};
			
			viewContentToolbar.elements.push({});
			viewContentToolbar.elements.push(actionPanelSwitcher);
			
			return viewContentToolbar;
		}

		function getContentBody(){

			let contentBody = {
				view:"form", 
				id: "view-content-body-" + self.uid,
				rows:[
					{ height: 6 },
					getRegime(),
					{ height: 6 },
					getButtons(),
					getTable(),
					{ height: 6 },
					getBottom()
				]
			}

			function getRegime(){

				let regime = {
					rows:[
						{
							cols:[
								{
									view: "label",
									height: 60,
									width: 180,
									id: "label-regimen-1-" + self.uid,
									template: "<span>" +i18n.t("LBL_ACTUAL_REGIME") + "</span> <br> <br>" + 
										'<div><span class="table-badge" style="background:#ba9b6f3b; color: #ba9b6fa0;">' + "Regimen 3" + '</span></div>',
									css: "global-text"
								},
								{ width: 12 },
								{
									id: "label-regimen-2-" + self.uid,
									height: 60,
									width: 180,
									view:"combo", 
									label: i18n.t("LBL_CHANGE_REGIME_TO"),
									labelPosition:"top",
									value:i18n.t("LBL_REGIME_THREE"), 
									options:[
										i18n.t("LBL_REGIME_ONE"), 
										i18n.t("LBL_REGIME_TWO"), 
										i18n.t("LBL_REGIME_THREE")
									],
									css: "global-text"
								},
								{ width: 12 },
								{
									view: "label",
									height: 60,
									width: 180,
									id: "label-regimen-3-" + self.uid,
									template: "<span>" +i18n.t("LBL_PROPOSED_REGIME") + "</span> <br> <br>" + 
										'<div><span class="table-badge" style="background:#ba9b6f3b; color: #ba9b6fa0;">' + "Regimen 2" + '</span></div>',
									css: "global-text"
								},
								{}	
							]
						},
						{ height: 6 },
						{
							cols:[
								{},
								{ 	
									width: 130,
									view: "button",
									align:"right", 
									css: "button-cta",
									value: i18n.t("LBL_CHANGE_REGIME"),
									click: function () { 
										
										webix.message("por hacer");
									}
								}
							]
						}
					]
				};

				return regime;
			}

			function getButtons(){

				let buttons = {
					cols:[
						{
							view: "segmented",
							css: "segment-custom-2",
							value: "action-panel-switcher-fan" + self.uid,
							width: 200,
							options: [
								{
									id: "action-panel-switcher-fan" + self.uid,
									value: "<span>" + i18n.t("LBL_FAN") + "</span>",
								},
								{
									id: "action-panel-switcher-tube" + self.uid,
									value: "<span>" + i18n.t("LBL_TUBE") + "</span>",
								}
							],
							tooltip: function(obj) {
			
								if (obj.id === "action-panel-switcher-fan" + self.uid) {
			
									return i18n.t("LBL_FAN");
								}
								else if (obj.id === "action-panel-switcher-tube" + self.uid) {
			
									return i18n.t("LBL_TUBE");
								}
							},
							on: {
								onChange(newv, oldv) {
			
									if (newv === "action-panel-switcher-fan" + self.uid) {
										
										webix.message("show-all");
									}
									else if (newv === "action-panel-switcher-tube" + self.uid) {
			
										webix.message("show-top");
									}
								}
							}
						},
						{}
					]
				};

				return buttons;
			}

			function getTable() {

				let data = self.tubeFans;

				const defaultGridOptionValues = [
					{ gridOption: { id: 5 }, value: undefined },
					{ gridOption: { id: 6 }, value: undefined },
					{ gridOption: { id: 7 }, value: "false" },
					{ gridOption: { id: 8 }, value: "false" },
					{ gridOption: { id: 9 }, value: "true" },
					{ gridOption: { id: 10 }, value: "true" },
					{ gridOption: { id: 16 }, value: "img/grid/default.png" },
					{ gridOption: { id: 21 }, value: "custom" },
					{ gridOption: { id: 23 }, value: "true" }
				];

				let metadata = {
					filter: true,
					label: "LBL_FAN",
					visible: true,
					gridOptionValues: defaultGridOptionValues,
					gridFields: [
						{
							id: 102701,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: true,
							needsTranslation: false,
							parameterized: false,
							position: 1,
							required: true,
							sourceField: "id",
							visible: false,
							widthPx: 1,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						},
						{
							id: 102702,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_FAN_ALIAS",
							labelTooltip: "LBL_FAN_ALIAS",
							needsTranslation: false,
							parameterized: false,
							position: 2,
							required: false,
							sourceField: "alias",
							visible: true,
							widthPx: 100,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: [
							]
						},
						{
							id: 102503,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_FAN_STATUS",
							labelTooltip: "LBL_FAN_STATUS",
							needsTranslation: true,
							parameterized: false,
							position: 3,
							required: true,
							sourceField: "statusLabelAlias",
							visible: true,
							widthPx: 200,
							adjust: false,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: [
							]
						},
						// {
						// 	id: 102504,
						// 	extended: false,
						// 	filtered: false,
						// 	isEpochMilli: false,
						// 	isPk: false,
						// 	label: "LBL_PLAN_ORDER_TYPE",
						// 	labelTooltip: "LBL_PLAN_ORDER_TYPE",
						// 	needsTranslation: false,
						// 	parameterized: false,
						// 	position: 4,
						// 	required: true,
						// 	sourceField: "orderTypeLabelAlias",
						// 	visible: true,
						// 	widthPx: 160,
						// 	adjust: false,
						// 	gridFieldDataType: {
						// 		id: 1,
						// 		name: "string"
						// 	},
						// 	gridFieldIcons: []
						// }
					],
					gridCommands: [

					]

				};

				self.tubeFansDataTable = new DataTable(null, null, metadata, data, null);
				
				return {height: 320,rows:self.tubeFansDataTable.getView()};
			};

			function getBottom(){

				let bottom = {
					cols:[
						{},
						{ 	
							width: 130,
							view: "button",
							align:"right", 
							css: "button-cta",
							value: i18n.t("LBL_CHANGE_STATUS"),
							click: function () { 
								
								webix.message("por hacer");
							} 
						}
					]
				};

				return bottom;
			}

			return contentBody;
		}

	}
}
