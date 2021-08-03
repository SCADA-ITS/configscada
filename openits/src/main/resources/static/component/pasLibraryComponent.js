import { util } from "../lib/util.js";
import TreeView from "./treeview.js";

export default class PasLibraryComponent {

	constructor(pasLibrary, callBack) {

		this.pasLibrary = pasLibrary;
		this.callBack = callBack;
		
		this.treeView;
		this.selectedPasLibraryNode;
		
		this.uid = webix.uid();
	}

	show() {

		let self = this;

		self.optionWindow = util.ui.loadWindow( i18n.t("LBL_PAS_LIBRARY_PLAYER_HEADER"), this.getView(), 600, 500, true);
	}

	getView() {

		let self = this;

		let mainView = {
			css: "dialog-general-custom",
			rows: [
				getTreeView(),
				{ height: 16 },
				getMessageView(),
				{ height: 4 },
				getBottomView()
			]
		};

		function getTreeView() {

			self.treeView = new TreeView(getTreeMetadata(), getTreeData(), getTreeEvents());

			let treeView = {
				height: 300,
				cols: [
					{ width : 6 },
					self.treeView.getView(),
					{ width : 6 }
				]
			};

			return treeView;

			function getTreeMetadata() {
				
				let metadata = {
					id: 1,
					label: "LBL_PAS_LIBRARIES",
					treeOptionValues: [
						{ treeOption: { id: 2}, value: false },	// multiselect
						{ treeOption: { id: 3}, value: true }, 	// sorted
						{ treeOption: { id: 4}, value: false }, // cmd_buttons
						{ treeOption: { id: 5}, value: true }, // search
						{ treeOption: { id: 6}, value: true },	// expand_all
						{ treeOption: { id: 7}, value: false }	// check_all
					]
				};
				
				return metadata;
			}
	
			function getTreeData() {
				
				let data = util.tree.getNodes(self.pasLibrary.pasLibraryNodes, self.pasLibrary.pasLibraryHierarchies, { parentNodeId: "nodeParentId", childNodeId: "nodeChildId" }, false);
				
				return data;
			}
	
			function getTreeEvents() {
					
				let events = {};
	
				events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) { 

					self.selectedPasLibraryNode = self.pasLibrary.pasLibraryNodes.find(pasLibraryNode => pasLibraryNode.id == item.id_);
					webix.ui(getMessageView(), $$("pass-library-message-" + self.uid));
				};
	
				events[TreeView.EventTypes.ON_SELECT_FOLDER] = function(item) { 
					
					self.selectedPasLibraryNode = self.pasLibrary.pasLibraryNodes.find(pasLibraryNode => pasLibraryNode.id == item.id_);
					webix.ui(getMessageView(), $$("pass-library-message-" + self.uid));
				};
				
				return events;
			}
		}

		function getMessageView() {

			let messageView = {
				id: "pass-library-message-" + self.uid,
				rows: [
					{},
					{
						view: "label",
						label: i18n.t("LBL_PAS_LIBRARY_NO_MESSAGE"),
						align: "center"
					},
					{}
				]
			};

			if(self.selectedPasLibraryNode && self.selectedPasLibraryNode.pasMessage){

				messageView = {
					id: "pass-library-message-" + self.uid,
					rows: [
						getMessageDetail(),
						{},
						getPlayer()
					]
				};
			}

			return messageView;

			function getMessageDetail() {

				let messageDetail = {};

				if(self.selectedPasLibraryNode && self.selectedPasLibraryNode.pasMessage) {

					messageDetail = {
						rows: [
							{
								view: "label",
								label: self.selectedPasLibraryNode.pasMessage.alias,
								align: "center",
								css: { "color": "#475466", "font-size": "16px !important"}
							},
							{
								height: 88,
								view: "label",
								align:"right",
								label: self.selectedPasLibraryNode.pasMessage.description,
								css: { "font-size" : "14px", "line-height" : "20px !important", "color": "#475466", "padding" : "8px"}
							}
						]
					};
				}
	
				return messageDetail;
			}

			function getPlayer() {

				let player = {
					view: "label",
					label: i18n.t("LBL_PAS_LIBRARY_NO_MESSAGE_AUDIO"),
					align: "center"
				};

				if( self.selectedPasLibraryNode && self.selectedPasLibraryNode.pasMessage && self.selectedPasLibraryNode.pasMessage.audio){

					player = {
						view: "template",
						template: `<audio controls autoplay>
								<source src="${self.selectedPasLibraryNode.pasMessage.audio}" type="audio/mpeg">
							  </audio>`
					};
				}
	
				return player;
			}
		}

		function getBottomView() {

			let bottomView = {
				cols: [
					{ width: 4 },
					{
						view: "button",
						css: "dialog-button",
						align: "right",
						width: 105,
						value: i18n.t("LBL_CANCEL"),
						click: function() {

							let parentWindow = this.getTopParentView();
							parentWindow.close();
						}
					},
					{ width: 286 },
					{
						view: "button",
						align: "left",
						css: "dialog-button",
						value: i18n.t("LBL_SEND"),
						click: function() {

							if (!self.selectedPasLibraryNode || !self.selectedPasLibraryNode.pasMessage) {

								webix.alert({
									title: i18n.t("LBL_PAS_LIBRARY_MESSAGE_NEEDED"),
									text: i18n.t("LBL_PAS_LIBRARY_MESSAGE_NEEDED_DESCRIPTION"),
									type: "alert-warning",
									width: 500
								});
							} else {
		
								webix.confirm({
									title: i18n.t("LBL_PAS_LIBRARY_SEND_CONFIRM"),
									ok: i18n.t("LBL_ACCEPT"),
									cancel: i18n.t("LBL_CANCEL"),
									text: i18n.t("LBL_PAS_LIBRARY_SEND_CONFIRM_DESCRIPTION"),
									width: 600
								}).then(function(res) {

									self.callBack(self.selectedPasLibraryNode.pasMessage.message);

									let parentWindow = this.getTopParentView();
									parentWindow.close();
								});
							}
						}
					}
				]
			};

			return bottomView;
		}

		return mainView;
	}
}

