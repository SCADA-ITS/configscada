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
		this.selectedTube;
		this.tubeFans;
		this.tubeFansDataTable;
		this.proposedRegime;
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
			
			self.treeView = new TreeView(getTreeMetadata(), getTreeData(), getTreeEvents());

			function getTreeMetadata() {
				
				let metadata = {
					id: 1,
					label: "LBL_VENTILATIONS",
					treeOptionValues: [
						// { treeOption: { id: 1}, value: "./img/ventilation.png" },
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
	
			function getTreeData() {
				
				let data = util.tree.getNodes(self.nodes, self.hierarchy, { parentNodeId: "parentId", childNodeId: "childId" }, true);
				
				return data;
			}
	
			function getTreeEvents() {
					
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
						header: i18n.t("LBL_VENTILATIONS"),
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
				
				self.selectedTube = data.json();
				self.tubeFans = eval("adapter." + self.config.adapters.fans)(self.selectedTube.childs, null);
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
					},
					{}
				]
			};

			function getContentHeader() {

				let header = {
					id: "view-content-header-" + self.uid,
					rows: [
						{
							id: "view-content-header-bar-color-" + self.uid,
							height: 15,
							css: { "background": util.element.getColor(self.selectedTube.elementTypeState) }
						},
						{ height: 4 },
						{
							cols:[
								{
									borderless:true,
									id: "view-content-header-info" + self.uid,
									height: 80,
									template: function() {

										return `<image class="ims-incident-report-icon" src="./img/grid/element_type_07.png" />
												<div class="ims-incident-report-title">
													${self.selectedTube.alias}
												</div>
												<div class="ims-incident-report-title">
													<span class="ims-incident-badge" style="background: ${util.element.getColor(self.selectedTube.elementTypeState)}; color: white;">${i18n.t(self.selectedTube.elementTypeState.labelAlias)}</span>
												</div>`;
									}
								},
								{
									rows:[
										{height:18},
										{
											cols:[
												{ 	
													width: 135,
													height: 40,
													type:"icon", 
													icon:"mdi mdi-stop",
													view: "button",
													align:"right", 
													css: "button-ventilation-action",
													label: i18n.t("LBL_STOP"),
													click: function () { 
														
														webix.message("STOP POR HACER");
													}
												},
												{ width: 12 },
												{ 	
													width: 135,
													height: 40,
													type:"icon", 
													icon:"mdi mdi-play",
													view: "button",
													align:"right",
													css: "button-ventilation-action",
													label: i18n.t("LBL_START"),
													click: function () { 
														
														webix.message("PLAY POR HACER");
													}
												}
											]
										}
									]
								},
								{width: 24 }
							]
						}
					]
				};
			
				return header;
			}

			return viewContent;
		}

		function getContentToolbar() {

			let ventilationModeValue = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.params.ventilationMode);
			let ventilationModeOptions = [];

			self.config.ventilationModeOptions.forEach(option => {

				ventilationModeOptions.push({
					id : option.id, 
					value: i18n.t(option.label) 
				});
			});
					
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
				value: ventilationModeValue.value,
				width: 500,
				options: ventilationModeOptions,
				tooltip: true,
				on: {
					onChange(newv, oldv) {

						let selectedOption = self.config.ventilationModeOptions.find(o => o.id == newv);
						webix.message(selectedOption.disable);
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
					getRegime(),
					getButtons(),
					getTable(),
					getBottom(),
					{}
				]
			}

			function getRegime(){

				let ventilationRegimeConfig = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.params.ventilationRegimeConfig);
				let availableRegimes;
				let actualRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.params.actualRegime);
				self.proposedRegime = self.selectedTube.elementValues.find(elementValue => elementValue.elementTypeParam.id == self.config.params.proposedRegime);

				try {

					ventilationRegimeConfig = JSON.parse(ventilationRegimeConfig.value);
					
					availableRegimes = ventilationRegimeConfig.ventilationRegimes.map(function (regime) {
						return { 
							id: regime.id, 
							value: i18n.t(regime.labelAlias)
						};
					});

					if(actualRegime.value && ventilationRegimeConfig.ventilationRegimes){

						actualRegime.labelAlias = ventilationRegimeConfig.ventilationRegimes.find(e =>e.id==actualRegime.value).labelAlias;
					} else {
						actualRegime.labelAlias = "LBL_NO_REGIME";
					}

					updateProposedRegime(self.proposedRegime.value, ventilationRegimeConfig.ventilationRegimes);
					
				} catch (error) {

					console.error("El Json de regimenes no esta correctamente formateado.")
				}

				let regime = {
					cols:[
						{
							rows: [
								{ height: 26 },
								{
									cols:[
										{
											view: "label",
											height: 25,
											width: 180,
											template: "<span>" +i18n.t("LBL_ACTUAL_REGIME") + "</span>",
											css: "global-text"
										},
										{ width: 12 },
										{
											view: "label",
											height: 24,
											width: 180,
											template: "<span>" +i18n.t("LBL_PROPOSED_REGIME") + "</span> ",
											css: "global-text"
										},
										{ width: 12 },
										{
											view: "label",
											height: 24,
											width: 180,
											template: "<span>" +i18n.t("LBL_CHANGE_REGIME_TO") + "</span>",
											css: "global-text"
										}
									]
								},
								{ height: 12 },
								{
									cols:[
										{
											view: "label",
											width: 180,
											height: 20,
											id: "label-regimen-1-" + self.uid,
											template: '<div><span class="table-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(actualRegime.labelAlias) + '</span></div>',
											css: "global-text"
										},
										{ width: 12 },
										getViewProposedRegimeLabel(),
										{ width: 12 },
										{
											id: "label-regimen-3-" + self.uid,
											width: 180,
											height: 30,
											view:"combo", 
											value: self.proposedRegime.value, 
											options: availableRegimes,
											on: {
												onChange(newv, oldv) {

													updateProposedRegime(newv, ventilationRegimeConfig.ventilationRegimes);
													webix.ui(getViewProposedRegimeLabel(), $$("label-regimen-2-" + self.uid));
													webix.ui(getViewDocument(), $$("view-content-regime-document-" + self.uid));
												}
											}
										}										
									]
								},
								{}
							] 
						},
						{},
						{
							rows:[
								getViewDocument(),
								{
									height: 58,
    								width: 980,
									borderless: true,
									view: "template",
									template: `<div class="ventilation-description-box"><span > ${self.proposedRegime.description}</span></div>`
								},
								{ height: 12 },
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
						}
					]
				};

				function getViewProposedRegimeLabel(){

					let viewProposedRegimeLabel = {
						view: "label",
						width: 180,
						height: 20,
						id: "label-regimen-2-" + self.uid,
						template: '<div><span class="table-badge" style="background:#9c70303b; color: #9c7030a0;">' + i18n.t(self.proposedRegime.labelAlias) + '</span></div>',
						css: "global-text"
					};

					return viewProposedRegimeLabel;
				}

				function getViewDocument(){

					let viewDocument = { 
						id: "view-content-regime-document-" + self.uid,
						height: 26 
					};

					if(self.proposedRegime.documentUrl){

						viewDocument = {
							id: "view-content-regime-document-" + self.uid,
							cols:[
								{},
								{
									height: 26,
									align: "right",
									width: 200,
									view: "button",
									type: "icon",
									label: self.proposedRegime.documentName + " " + self.proposedRegime.documentType,
									icon: "mdi mdi-file",
									click: function (){
										window.open(self.proposedRegime.documentUrl, '_blank');
									},
									css: "button-icon-transparent"
								}		
							]
						};
					}

					return viewDocument;
				}

				function updateProposedRegime(value, ventilationRegimes){

					self.proposedRegime.value = value;

					if(self.proposedRegime.value && ventilationRegimes){

						let proposedConfig = ventilationRegimes.find(e =>e.id==self.proposedRegime.value);
						self.proposedRegime.labelAlias = proposedConfig.labelAlias;
						self.proposedRegime.description = proposedConfig.description;
						self.proposedRegime.documentUrl = proposedConfig.documentUrl

						if (self.proposedRegime.documentUrl){

							self.proposedRegime.documentName = self.proposedRegime.documentUrl.substring(self.proposedRegime.documentUrl.lastIndexOf("/")+1, self.proposedRegime.documentUrl.lastIndexOf("."));
							self.proposedRegime.documentType = self.proposedRegime.documentUrl.substring(self.proposedRegime.documentUrl.lastIndexOf(".")+1, self.proposedRegime.documentUrl.length);
						}
					} else {

						self.proposedRegime.labelAlias = "LBL_NO_REGIME";
						self.proposedRegime.description = "";
					}
				}

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
				
				return {height: 338,rows:self.tubeFansDataTable.getView()};
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
