import GenericModule from "./genericModule.js";
import TreeView from "../component/treeview.js";
import { util } from "../lib/util.js";
import ListView from "../component/listview.js";

const MODULE_TYPE_GENERIC_SELECTOR_ID = 9;
const SELECTOR_REST_ENDPOINT = appConfig.restUrl + "selector_trees/";

export default class selectorModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.config = module.config;
		this.treeNodes = undefined;
		this.componentView = undefined;
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

			let selectorTreeId = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_TYPE_GENERIC_SELECTOR_ID).value;

			if (selectorTreeId) {

				webix.ajax().get(SELECTOR_REST_ENDPOINT + selectorTreeId).then(function(dat) {

					if (dat != null) {

						let treeSelector = dat.json();

						self.treeNodes = treeSelector.treeNodes;

						drawComponents(treeSelector);

						loadModule(parseInt(treeSelector.defaultModuleId));

						$$("loading-" + self.uid).hide();
						$$(self.uid).show();

					}
				});
			}

		}

		function loadModule(moduleId) {

			if (moduleId) {

				if (moduleId) {

					let module = window.em.getByField(EntityType.MODULE, "id", moduleId);

					if (module) {

						util.module.open(module, null, VIEW_TYPE_CUSTOM, drawContent, null);

						function drawContent(view) {

							let content = $$("view-main-content-selector-module-" + self.uid);

							if (content) {

								let viewContent = {
									rows: [
										{
											id: "view-main-content-selector-module-" + self.uid,
											cols: [view]
										}
									]
								};

								webix.ui(viewContent, $$("view-main-content-selector-module-" + self.uid));
							}
						}
					}
				}
			}
		}

		function drawComponents(treeSelector) {

			function newTreeData(treeNodeId) {

				if (treeNodeId && self.treeNodes) {

					let treeNode = self.treeNodes.find(treeNode => treeNode.id === treeNodeId);

					if (treeNode) {

						console.log(treeNode);
						let treeData = {};
						treeData.id = treeNode.id;
						treeData.value = treeNode.label;
						treeData.props = {};
						treeData.data = [];
						treeData.props.folder = false;
						treeData.props.translate = true;

						if (treeNode.image) {

							treeData.props.image = treeNode.image;
						}

						return treeData;
					}
				}
			}

			function getTreeViewData(nodes, hierarchies) {

				let data = [];
				let events = {};
				let childElements = [];
				let mapNodes = new Map();

				if (Array.isArray(nodes) && nodes.length > 0) {

					nodes.forEach(node => {

						let treeData = newTreeData(node.id);
						mapNodes.set(node.id, treeData);
					});
				}

				if (Array.isArray(hierarchies) && hierarchies.length > 0) {

					hierarchies.forEach(hierarchy => {

						let parentElement = mapNodes.get(hierarchy.nodeParentId);
						let childElement = mapNodes.get(hierarchy.nodeChildId);

						parentElement.props.folder = true;
						parentElement.data.push(childElement);

						childElements.push(hierarchy.nodeChildId);
					});
				}

				childElements.forEach(childElement => {

					mapNodes.delete(childElement);
				});

				mapNodes.forEach(function(item, key, mapObj) {
					data.push(item);
				});

				events[TreeView.EventTypes.ON_SELECT_ITEM] = selectEvent;
				
				return new TreeView({}, data, events);
			}

			let viewMain = {
				id: self.uid,
				cols: []
			};


			let selectEvent = function(items) {

				if (items) {

					let nodeIdSelected = items;

					if (self.treeNodes) {

						let node = self.treeNodes.find(e => e.id == nodeIdSelected);

						if (node && node.moduleId) {

							loadModule(node.moduleId);
						}
					}
				}

			};

			function getListView(nodes) {
				
				let data = [];
				let options = {};
				let events = {};
				
				if (Array.isArray(nodes) && nodes.length > 0) {

					data = nodes.map(e => {
		
						return {
							id: e.id,
							alias: i18n.t(e.label),
							description: "",
							image: e.image
						};
					});
				}

				events[ListView.EventTypes.ON_SELECT_ROW] = selectEvent;

				return new ListView(data, options, events);
			}

			if(treeSelector.treeHierarchies && Array.isArray(treeSelector.treeHierarchies) && treeSelector.treeHierarchies.length > 0) {

				self.componentView = getTreeViewData(treeSelector.treeNodes, treeSelector.treeHierarchies); 
				
			} else {
				
				self.componentView = getListView(treeSelector.treeNodes);
			}
			

			let viewMainSelectorTree = {
				id: "view-main-selector-tree-" + self.uid,
				header: i18n.t(treeSelector.label),
				body: {
					width: 250,
					rows: [
						{
							cols: [
								self.componentView.getView()
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
							id: "view-main-content-selector-module-" + self.uid,
						}
					]
				}
			};

			viewMain.cols = [viewMainSelectorTree, viewMainResizer, viewMainContent];

			webix.ui(viewMain, $$(self.uid));

		}

	}
}

