import GenericModule from "../modules/genericModule.js";
import TreeView from "../component/treeview.js";
import { util } from "../lib/util.js";

const MODULE_TYPE_GENERIC_SELECTOR_TREE_ID = 9;
const SYNOPTIC_REST_ENDPOINT = appConfig.restUrl + "selector_trees/";

export default class treeSectModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.config = module.config;
		this.treeNodes = undefined;
		this.treeView = undefined;
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

			let selectorTreeId = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_TYPE_GENERIC_SELECTOR_TREE_ID).value;

			if (selectorTreeId) {

				webix.ajax().get(SYNOPTIC_REST_ENDPOINT + selectorTreeId).then(function(dat) {

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

			function fillNode(treeData, hierarchies) {

				hierarchies.forEach(hierarchy => {

					let parentElementId = hierarchy.nodeParentId;
					let childElementId = hierarchy.nodeChildId;

					if (parentElementId && childElementId && treeData.id === parentElementId) {

						let childTreeData = newTreeData(childElementId);

						treeData.data.push(childTreeData);

						treeData.props.folder = true;

						fillNode(childTreeData, hierarchies);
					}
				});
			}

			function getTreeViewData(nodes, hierarchies) {

				let data = [];

				if (Array.isArray(nodes) && nodes.length > 0) {

					nodes.forEach(node => {

						let isElementRoot = true;

						for (let i = 0; isElementRoot && i < hierarchies.length; i++) {

							let nodeChildId = hierarchies[i].nodeChildId;

							isElementRoot = (nodeChildId !== node.id);
						}

						if (isElementRoot) {

							let rootNode = newTreeData(node.id);

							data.push(rootNode);

							fillNode(rootNode, hierarchies);
						}
					});
				}
				return data;
			}

			let viewMain = {
				id: self.uid,
				cols: []
			};

			let events = {};

			events[TreeView.EventTypes.ON_SELECT_ITEM] = function(items) {

				if (items) {

					let nodeIdSelected = items;

					if (self.treeNodes) {

						let node = self.treeNodes.find(e => e.id === nodeIdSelected);

						if (node && node.moduleId) {

							loadModule(node.moduleId);
						}
					}
				}

			};

			let data = getTreeViewData(treeSelector.treeNodes, treeSelector.treeHierarchies);

			self.treeView = new TreeView({}, data, events);

			let viewMainSelectorTree = {
				id: "view-main-selector-tree-" + self.uid,
				header: i18n.t(treeSelector.label),
				body: {
					width: 200,
					rows: [
						{
							cols: [
								self.treeView.getView()
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

