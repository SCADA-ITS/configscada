import GenericModule from "./genericModule.js";
import TreeView from "../component/treeview.js";
import { util } from "../lib/util.js";
import ListView from "../component/listview.js";

const MODULE_TYPE_GENERIC_SELECTOR_ID = 9;
const SELECTOR_REST_ENDPOINT = appConfig.restUrl + "selector_trees/";

export default class SelectorModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		this.config = module.config;
		this.treeNodes = undefined;
		this.componentView = undefined;
		this.treeView = undefined;		
		this.sessionData = webix.storage.session.get(SESSION_STORAGE_SESSION_DATA);
		
	}

	loadData() {

		let self = this;
		let managementAreasEnables = (self.sessionData.managementAreaId) ?? undefined;  

		if ($$(this.uid)) {

			$$("loading-" + this.uid).show();
			$$(this.uid).hide();

			let selectorTreeId = this.module.moduleValues.find(e => e.moduleParam.id === MODULE_TYPE_GENERIC_SELECTOR_ID).value;

			if (selectorTreeId) {

				webix.ajax().get(SELECTOR_REST_ENDPOINT + selectorTreeId).then(function(dat) {

					if (dat != null) {

						let treeSelector = dat.json();

						self.treeNodes = treeSelector.treeNodes;
						
						self.treeNodes.sort((a, b) => (a.position > b.position) ? 1 : ((b.position > a.position) ? -1 : 0));

						drawComponents(treeSelector);

						selectModule(parseInt(treeSelector.defaultModuleId));

						$$("loading-" + self.uid).hide();
						$$(self.uid).show();

					}
				});
			}

		}
		
		function loadModule(moduleId) {

			if (moduleId) {

				let module = window.em.getByField(EntityType.MODULE, "id", moduleId);

				if (module) {

					util.module.open(module, null, VIEW_TYPE_CUSTOM, drawContent, {containerId: "view-main-content-selector-module-" + self.uid});

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

		function selectModule(moduleId) {

			let nodeToSelect = self.treeNodes.find(node => node.moduleId === moduleId);

			if (nodeToSelect) {

				if (self.treeView) {

					self.treeView.select(nodeToSelect.id);
					self.treeView.expand(nodeToSelect.id);
	
				} else {
	
					self.componentView.select(nodeToSelect.id);
				}
			}
		}

		function drawComponents(treeSelector) {

			function selectEvent(nodeIdSelected) {

				if (nodeIdSelected) {

					if (self.treeNodes) {

						let node = self.treeNodes.find(e => e.id == nodeIdSelected);

						if (node && node.moduleId) {

							loadModule(node.moduleId);

							util.ui.setTitle(self.args.containerId, i18n.t(node.label));
						}
					}
				};
			}

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
						
						let managementAreas = undefined;

						if (node.managementAreas) {

							try {

								managementAreas = JSON.parse(node.managementAreas);

							} catch (e) {

								managementAreas = undefined;
							}
						}

						if ((managementAreas === undefined || (managementAreasEnables && managementAreas.find(id => id === managementAreasEnables)))) {

							let treeData = newTreeData(node.id);
							mapNodes.set(node.id, treeData);
						}
					});
				}

				if (Array.isArray(hierarchies) && hierarchies.length > 0) {

					hierarchies.forEach(hierarchy => {

						let parentElement = mapNodes.get(hierarchy.nodeParentId);
						let childElement = mapNodes.get(hierarchy.nodeChildId);

						if (parentElement) {

							parentElement.props.folder = true;
						}

						if (childElement && parentElement) {

							parentElement.data.push(childElement);
						}

						childElements.push(hierarchy.nodeChildId);
					});
				}

				childElements.forEach(childElement => {

					mapNodes.delete(childElement);
				});

				mapNodes.forEach(function(item, key, mapObj) {
					data.push(item);
				});

				events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) {

					if (item && item.id) {

						selectEvent(item.id);
					}
				}

				self.treeView = new TreeView({}, data, events);

				return self.treeView;
			}

			let viewMain = {
				id: self.uid,
				cols: []
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

				events[ListView.EventTypes.ON_SELECT_ROW] = function(item) {

					if (item) {

						selectEvent(item);
					}
				}

				return new ListView(data, options, events);
			}

			if (treeSelector.treeHierarchies && Array.isArray(treeSelector.treeHierarchies) && treeSelector.treeHierarchies.length > 0) {

				self.componentView = getTreeViewData(treeSelector.treeNodes, treeSelector.treeHierarchies);

			} else {

				self.componentView = getListView(treeSelector.treeNodes);
			}


			let viewMainSelectorTree = {
				id: "view-main-selector-tree-" + self.uid,
				header: i18n.t(treeSelector.label),
				body: {
					width: 350,
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

