import GenericModule from "../../genericModule.js";
import TreeView from "../../../component/treeview.js";
import { util } from "../../../lib/util.js";
import TubeContent from "./tubeContent.js";
import LightingContent from "./lightingContent.js";
import { adapter } from "../../../lib/adapter.js";

export default class LightingnModule extends GenericModule {
	
	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		
		this.treeView;
		this.nodes;
		this.hierarchy;
		this.tunnels;

		this.selectedTunnelId;
		this.selectedTubeId;
		
		this.enabledSelectedPks;
		this.tubeTableOption;
	}

	loadData() {

		let self = this;
		let enabledTunnelIds;

		if(this.moduleAction && this.moduleAction.id == this.config.moduleAction.tunnel){
			
			if(this.args && this.args.selectedPks){

				enabledTunnelIds = this.args.selectedPks;

				loadTree(enabledTunnelIds).then(function(res) {

					drawComponents();

					let selectedItem = self.treeView.data.find(treeOption => treeOption.id_ == enabledTunnelIds[0]);
					self.treeView.select(selectedItem.id);
				});
			}
		
		} else if(this.moduleAction && this.moduleAction.id == this.config.moduleAction.tube){
			
			if(this.args && this.args.selectedPks){

				let enabledTubeIds = this.args.selectedPks;

				webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.elementsHierarchy).then(function(response) {

					if(response){

						let elementsHierarchy = response.json();
						let tunnelsHierarchy = elementsHierarchy.filter(element => element.parent.elementType.id == self.config.elementType);
						tunnelsHierarchy = tunnelsHierarchy.filter(element => enabledTubeIds.includes(element.child.id));
						enabledTunnelIds = tunnelsHierarchy.map(e => { return e.parent.id; }); 

						loadTree(enabledTunnelIds).then(function(res) {

							drawComponents();

							let selectedTreeId;
							self.treeView.data.some(tunnelOptionTree => {

								return tunnelOptionTree.data.some(tubeOptionTree => {

									if(enabledTubeIds.includes(tubeOptionTree.id_)){

										selectedTreeId = tubeOptionTree.id;
										return true;
									}
								});
							});
							self.treeView.select(selectedTreeId);
						});
					}
				});
			}
		
		} else if(this.moduleAction && (this.moduleAction.id == this.config.moduleAction.circuit || this.moduleAction.id == this.config.moduleAction.dali)) {
			
			if(this.args && this.args.selectedPks){

				self.enabledSelectedPks = this.args.selectedPks;

				if(this.moduleAction.id == this.config.moduleAction.dali){
				
					self.tubeTableOption = "action-panel-switcher-dali" + this.uid;
				}
				
				webix.ajax().get(appConfig.restUrl + self.config.restEndpoints.elementsHierarchy).then(function(response) {

					if(response){

						let elementsHierarchy = response.json();
						let tubesHierarchy = elementsHierarchy.filter(element => self.enabledSelectedPks.includes(element.child.id));
						tubesHierarchy = tubesHierarchy.filter(element => element.parent.elementType.id == self.config.elementTypeTube);
						let enabledTubeIds = tubesHierarchy.map(e => { return e.parent.id; }); 

						let tunnelsHierarchy = elementsHierarchy.filter(element => element.parent.elementType.id == self.config.elementType);
						tunnelsHierarchy = tunnelsHierarchy.filter(element => enabledTubeIds.includes(element.child.id));
						enabledTunnelIds = tunnelsHierarchy.map(e => { return e.parent.id; }); 

						loadTree(enabledTunnelIds).then(function(res) {

							drawComponents();
							
							let selectedTreeId;
							self.treeView.data.some(tunnelOptionTree => {

								return tunnelOptionTree.data.some(tubeOptionTree => {

									if(enabledTubeIds.includes(tubeOptionTree.id_)){

										selectedTreeId = tubeOptionTree.id;
										return true;
									}
								});
							});
							self.treeView.select(selectedTreeId);
						});
					}
				});
			}
		} else {

			loadTree().then(function(res) {

				drawComponents();
				self.treeView.select(self.treeView.data[0].id);
			});
		}

		async function loadTree(enabledTunnelIds) {

			let data = await webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsByIdType, { id: self.config.elementType }));
			
			self.tunnels = data.json();
			self.nodes = [];
			self.hierarchy = [];

			self.tunnels.forEach(e => {
				
				if(!enabledTunnelIds || (enabledTunnelIds.includes(e.id))){

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
				}
			});
			
			self.treeView = new TreeView(getTreeMetadata(), getTreeData(), getTreeEvents());

			function getTreeMetadata() {
				
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
	
			function getTreeData() {
				
				let data = util.tree.getNodes(self.nodes, self.hierarchy, { parentNodeId: "parentId", childNodeId: "childId" }, false);
				
				return data;
			}
	
			function getTreeEvents() {
					
				let events = {};
	
				events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) { 

					self.selectedTunnelId = $$("tree-"+self.treeView.uid).getItem(item.$parent).id_;
					self.selectedTubeId = item.id_;

					function drawContent(view) {

						let viewContent = {
							id: "view-content-" + self.uid,
							rows: [view],
							borderless:true
						};

						$$("view-content-loading-" + self.uid).hide();
						$$("view-content-" + self.uid).show();
						webix.ui(viewContent, $$("view-content-" + self.uid));
					}

					$$("view-content-" + self.uid).hide();
					$$("view-content-loading-" + self.uid).show();
					
					let containerContent = new TubeContent(self.args, self.selectedTubeId, self);
					let args = {};
					args.selectedPks = self.enabledSelectedPks;
					args.tubeTableOption = self.tubeTableOption;
					containerContent.loadData(args, drawContent);
				};
	
				events[TreeView.EventTypes.ON_SELECT_FOLDER] = function(item) { 
					
					let args = {};
					args.selectedPks = [item.id_];
					args.containerId = "view-content-" + self.uid;

					function drawContent(view) {

						let viewContent = {
							id: "view-content-" + self.uid,
							rows: [view],
							borderless:true
						};

						$$("view-content-loading-" + self.uid).hide();
						$$("view-content-" + self.uid).show();
						webix.ui(viewContent, $$("view-content-" + self.uid));
					}

					$$("view-content-" + self.uid).hide();
					$$("view-content-loading-" + self.uid).show();

					let containerContent = new LightingContent(self);
					containerContent.loadData(args, drawContent);

					// let childs = self.treeView.data.find(e =>e.id==item.id);

					// if (childs && childs.data && childs.data[0]) {

					// 	self.treeView.select(childs.data[0].id);
					// }
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
						rows: [],
						hidden: true
					},
					{
						id: "view-content-loading-" + self.uid,
						rows: [util.ui.getViewDrawLoading()],
						hidden: false
					}
				]
			};

			$$("loading-" + self.uid).hide();

			webix.ui(viewMain, $$(self.uid));
		}
	}
}
