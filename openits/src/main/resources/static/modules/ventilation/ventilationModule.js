import GenericModule from "../genericModule.js";
import TreeView from "../../component/treeview.js";
import { util } from "../../lib/util.js";
import VentilationContent from "./ventilationContent.js";
import { adapter } from "../../lib/adapter.js";

export default class VentilationModule extends GenericModule {
	

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		
		this.treeView;
		this.nodes;
		this.hierarchy;
		this.ventilations;

		this.selectedTunnelId;
		this.selectedTubeId;
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

					self.selectedTunnelId = $$("tree-"+self.treeView.uid).getItem(item.$parent).id_;
					self.selectedTubeId = item.id_;

					let ventilationContent = new VentilationContent(self.selectedTunnelId, self.selectedTubeId, self);
					ventilationContent.loadComponent("view-content-" + self.uid);
				};
	
				events[TreeView.EventTypes.ON_SELECT_FOLDER] = function(item) { 
					
					self.selectedTunnelId = item.id_;
					self.selectedTubeId = null;

					let ventilationContent = new VentilationContent(self.selectedTunnelId, self.selectedTubeId, self);
					ventilationContent.loadComponent("view-content-" + self.uid);
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
	}
}
