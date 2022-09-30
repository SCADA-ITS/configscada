import { util } from "../../../lib/util.js";
import TreeView from "../../../component/treeview.js";
import MessageBox from "../../../component/messageBox.js";

export default class PasLibrary {

	constructor(pasLibrary, callBack, orderEdit) {

		this.pasLibrary = pasLibrary;
		this.callBack = callBack;
		this.orderEdit = orderEdit;

		this.treeView = undefined;
		this.selectedPasLibraryNode = undefined;

		this.uid = webix.uid();
	}

	getView() {

		let self = this;

		let mainView = {
			css: "dialog-general-custom player-body",
			rows: [
				getHeader(),
				getTreeView(),
				getFolderView(),
				getBottomView()
			]
		};

		function getHeader() {

			let header;
			
			header = {

				id: "viewHeader-" + self.uid,
				cols: [
					{
						view: "label",
						label: "<img src='img/menu/play.png' class='grid-icon'/>",
						height: 75,
						css: "global-header-icon"
					},
					{
						view: "label",
						height: 75,
						id: "label-bottom-" + self.uid,
						template: "<span>" + i18n.t("LBL_PLAYER") + "</span> <br> <span style='font-size: 13px'>" + i18n.t("LBL_PLAYER_DESCRIPTION") + "</span>",
						css: "global-header-label"
					}
				]
			};

			return header;
		}


		function getTreeView() {

			self.treeView = new TreeView(getTreeMetadata(), getTreeData(), getTreeEvents());

			let treeView = {
				height: 300,
				cols: [
					{ width: 25 },
					{
						css: { "border": "1px solid #c1c6ce", "box-sizing": "border-box", "padding": "10px 0px", "background-color": "white" },
						cols: [self.treeView.getView()]
					},
					{ width: 25 }
				]
			};

			return treeView;

			function getTreeMetadata() {

				let metadata;
				
				metadata = {
					id: 1,
					label: "LBL_PAS_LIBRARIES",
					treeOptionValues: [
						{ treeOption: { id: 2 }, value: false },	// multiselect
						{ treeOption: { id: 3 }, value: true }, 	// sorted
						{ treeOption: { id: 4 }, value: false }, // cmd_buttons
						{ treeOption: { id: 5 }, value: true }, // search
						{ treeOption: { id: 6 }, value: true },	// expand_all
						{ treeOption: { id: 7 }, value: false }, // check_all
						{ treeOption: { id: 9 }, value: true }	// no_show_empty_folders
					]
				};

				return metadata;
			}

			function getTreeData() {

				let nodeList = webix.copy(self.pasLibrary.pasLibraryNodes);
				let itemsWithoutMessages = nodeList.filter(item => (item.pasMessage) && (item.pasMessage.message === ""));

				if (Array.isArray(itemsWithoutMessages) && itemsWithoutMessages.length > 0) {

					for (let item of itemsWithoutMessages) {

						let itemIdx = nodeList.indexOf(item);

						if (itemIdx !== -1) {

							nodeList.splice(itemIdx, 1);
						}

						let folderToDelete = self.pasLibrary.pasLibraryHierarchies.find(e => e.nodeChildId == item.id);

						if (folderToDelete) {

							let nodeFolderToDelete = nodeList.find(e => e.id == folderToDelete.nodeParentId);

							if (nodeFolderToDelete) {

								let folderIdx = nodeList.indexOf(nodeFolderToDelete);

								if (folderIdx !== -1) {

									nodeList.splice(folderIdx, 1);
								}
							}
						}
					}
				}

				return util.tree.getNodes(nodeList, self.pasLibrary.pasLibraryHierarchies, { parentNodeId: "nodeParentId", childNodeId: "nodeChildId" }, true);
			}

			function getTreeEvents() {

				let events = {};

				events[TreeView.EventTypes.ON_SELECT_ITEM] = function(item) {

					self.selectedPasLibraryNode = self.pasLibrary.pasLibraryNodes.find(pasLibraryNode => pasLibraryNode.id == item.id_);
					webix.ui(getMessageView(), $$("pass-library-message-" + self.uid));
				};

				events[TreeView.EventTypes.ON_SELECT_FOLDER] = function(item) {

					self.selectedPasLibraryNode = self.pasLibrary.pasLibraryNodes.find(pasLibraryNode => pasLibraryNode.id == item.id_);
					webix.ui(getFolderView(), $$("pass-library-message-" + self.uid));
				};

				return events;
			}
		}

		function getMessageView() {

			if ($$("button-accept" + self.uid) != undefined) {

				$$("button-accept" + self.uid).enable();
			}

			let messageView = {
				id: "pass-library-message-" + self.uid,
				rows: [
					getMessageDetail(),
					getPlayer()
				]
			};


			return messageView;

			function getMessageDetail() {

				let messageDetail = {};

				if (self.selectedPasLibraryNode) {

					let label = (self.selectedPasLibraryNode.pasMessage) ? self.selectedPasLibraryNode.pasMessage.alias : i18n.t("LBL_NO_SIGNALING_MESSAGE");
					let description = (self.selectedPasLibraryNode.pasMessage) ? self.selectedPasLibraryNode.pasMessage.description : i18n.t("LBL_NO_SIGNALING_MESSAGE_DESCRIPTION");

					messageDetail = {
						height: 146,
						cols: [
							{ width: 25 },
							{
								rows: [
									{
										view: "label",
										label: label,
										align: "left",
										css: { "color": "#475466", "font-size": "16px !important", "padding-top": "12px" }
									},
									{
										view: "textarea",
										readonly: true,
										css: "audio-description",
										value: description
									}
								]
							},
							{ width: 25 }
						]
					};
				}

				return messageDetail;
			}

			function getPlayer() {

				let audioFile = undefined;
				let cssClass = "audio-player audio-disable";


				if (self.selectedPasLibraryNode && self.selectedPasLibraryNode.pasMessage && self.selectedPasLibraryNode.pasMessage.audio) {

					audioFile = self.selectedPasLibraryNode.pasMessage.audio;
					cssClass = "audio-player";
					$$("button-accept" + self.uid).enable();
				}

				let player;
				
				player = {
					cols: [
						{ width: 75 },
						{
							id: "audio-player-" + self.uid,
							view: "template",
							align: "center",
							height: 55,
							css: cssClass,
							template: `<audio controls>
								<source src="${audioFile}" type="audio/mpeg">
							  </audio>`
						},
						{ width: 75 }
					]
				};

				return player;
			}
		}


		function getFolderView() {

			if ($$("button-accept" + self.uid) != undefined) {

				$$("button-accept" + self.uid).disable();
			}

			let folderView;
			
			folderView = {
				id: "pass-library-message-" + self.uid,
				rows: [
					{ height: 83 },
					{
						view: "label",
						label: i18n.t("LBL_PAS_LIBRARY_NO_MESSAGE"),
						align: "center"
					},
					{ height: 83 }
				]
			};

			return folderView;

		}

		function getBottomView() {

			let label = (self.orderEdit) ? i18n.t("LBL_ACCEPT") : i18n.t("LBL_SEND");

			let bottomView;
			
			bottomView = {
				rows: [
					{ height: 10 },
					{
						cols: [
							{ width: 20 },
							{
								view: "button",
								css: "dialog-cancel",
								width: 130,
								height: 40,
								value: i18n.t("LBL_CANCEL"),
								click: function() {

									let parentWindow = this.getTopParentView();
									parentWindow.close();
								}
							},
							{},
							{
								id: "button-accept" + self.uid,
								view: "button",
								css: "dialog-accept",
								width: 130,
								height: 40,
								value: label,
								disabled: true,
								click: function() {

									let parentWindow = this.getTopParentView();

									if (self.orderEdit) {

										self.callBack(self.selectedPasLibraryNode);
										parentWindow.close();
										
									} else {

										let textMessage = (self.orderEdit) ? i18n.t("LBL_PAS_LIBRARY_ADD_CONFIRM_DESCRIPTION") : i18n.t("LBL_PAS_LIBRARY_SEND_CONFIRM_DESCRIPTION");

										let ms = new MessageBox(MessageBox.CONFIRM, textMessage, null, onAccept, null);
										ms.show();

										function onAccept() {

											self.callBack(self.selectedPasLibraryNode.pasMessage.message);
											parentWindow.close();
										}
									}
								}
							},
							{ width: 20 }
						]
					}]
			};

			return bottomView;
		}

		return mainView;
	}
}

