import { util } from "../../../lib/util.js";
import { adapter } from "../../../lib/adapter.js";
import MessageBox from "../../../component/messageBox.js";

export default class TunnelContent {

	constructor(tunnelId, parent) {

		this.tunnelId = tunnelId;
		this.parent = parent;
		this.uid = parent.uid;
		this.selectedTunnel = undefined;
		this.idStompSubscriptorTunnel = undefined;
	}

	getView() {

		let self = this;

		let viewMain = {
			id: "view-main-content-" + self.uid,
			cols: [],
			on: {
				onDestruct: (obj) => {

					disConnectWS();
				}
			}
		};

		let viewMainLoading = {
			id: "view-tunnel-loading-" + self.uid,
			hidden: false,
			rows: [util.ui.getViewDrawLoading()]
		};

		let viewMainContent = {
			id: "view-tunnel-content-" + self.uid,
			hidden: true
		};

		viewMain.cols = [viewMainLoading, viewMainContent];

		function disConnectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				if (self.idStompSubscriptorTunnel)
					stompSubscriptorService.unsubscribe(self.idStompSubscriptorTunnel);
			}
		}

		return viewMain;
	}

	loadData() {

		let self = this;

		$$("view-tunnel-loading-" + self.uid).show();
		$$("view-tunnel-content-" + self.uid).hide();

		let args = {};
		args.selectedPks = [self.tunnelId];
		args.containerId = "view-tunnel-content-" + self.uid;

		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.parent.config.restEndpoints.elementById, { id: self.tunnelId })).then(function(data) {

			if (data != null) {

				self.selectedTunnel = data.json();
			}

			let module = util.module.getFromModuleAction(self.parent.config.containerAction.tunnel);
			let moduleAction = { id: self.parent.config.containerAction.tunnel };

			if (module.moduleActions) {

				moduleAction = module.moduleActions.find(action => action.id == moduleAction.id);
			}

			util.module.open(module, moduleAction, VIEW_TYPE_CUSTOM, drawContent, args);

			function drawContent(view) {

				function connectWS() {

					let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

					if (stompSubscriptorService) {

						if (self.tunnelId) {

							let topic = self.parent.config.topics.tunnel + "." + self.tunnelId;
							self.idStompSubscriptorTunnel = stompSubscriptorService.subscribe(appConfig.stompUrl, topic, onMessageTunnel);
						}

					}

					function onMessageTunnel(data) {

						if (data) {
							
							$$("view-content-toolbar-" + self.uid).show();

							self.selectedTunnel = eval("adapter." + self.parent.config.adapters.element)(self.selectedTunnel, data);

							if (self.parent.config.ventilationStates[self.selectedTunnel.elementTypeState.id].actions.includes("SEND_VENTILATION_STOP")) {

								$$("view-content-ventilation-stop-" + self.uid).enable();
								
							} else {

								$$("view-content-ventilation-stop-" + self.uid).disable();
							}

							if (self.parent.config.ventilationStates[self.selectedTunnel.elementTypeState.id].actions.includes("SEND_VENTILATION_START")) {

								$$("view-content-ventilation-start-" + self.uid).enable();
							} else {

								$$("view-content-ventilation-start-" + self.uid).disable();
							}
						}
					}
				}

				view.relative = true;

				let viewContent = {
					id: "view-tunnel-content-" + self.uid,
					view: "abslayout",
					cells: [
						view,
						{	
							hidden: !self.parent.commandEquipmentPermission,
							top: 34, right: 186,
							rows: [
								{},
								{
									id: "view-content-toolbar-" + self.uid,
									view: "toolbar",
									height: 70,
									borderless: true,
									css: "equip-viewer-header-actions",
									hidden: self.parent.config.hiddenTunnelButton && (self.parent.config.hiddenTunnelButton.indexOf(self.selectedTunnel.elementTypeState.id) !== -1),
									elements: [
										{
											id: "view-content-ventilation-stop-" + self.uid,
											view: "button",
											width: 135,
											label: i18n.t("LBL_STOP"),
											tooltip: i18n.t("LBL_STOP_DESCRIPTION"),
											type: "iconTop",
											icon: "mdi mdi-stop",
											hidden: self.parent.config.hiddenButton,
											disabled: !self.parent.config.ventilationStates[self.selectedTunnel.elementTypeState.id].actions.includes("SEND_VENTILATION_STOP"),
											on: {
												onItemClick() {

													let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_SEND_VENTILATION_STOP_CONFIRM_DESCRIPTION"), onAccept, null);
													ms.show();
													
													function onAccept() {
														
														let command = self.parent.config.commands.SEND_VENTILATION_STOP;
														command.elementId = self.tunnelId;

														let body = {
															commands: []
														};
														body.commands.push(command);

														webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.restEndpoints.command, body).then(function(data) {

															if (!data.json()) {

																webix.message({ type: "error", text: i18n.t("LBL_ERROR_OPERATION") });
															}
														});														
													};
												}
											}
										},
										{ width: 12 },
										{
											id: "view-content-ventilation-start-" + self.uid,
											view: "button",
											width: 135,
											label: i18n.t("LBL_START"),
											tooltip: i18n.t("LBL_START_DESCRIPTION"),
											type: "iconTop",
											icon: "mdi mdi-play",
											hidden: self.parent.config.hiddenButton,
											disabled: !self.parent.config.ventilationStates[self.selectedTunnel.elementTypeState.id].actions.includes("SEND_VENTILATION_START"),
											on: {
												onItemClick() {
													
													let ms = new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_SEND_VENTILATION_START_CONFIRM_DESCRIPTION"), onAccept, null);
													ms.show();
													
													function onAccept() {

														let command = self.parent.config.commands.SEND_VENTILATION_START;
														command.elementId = self.tunnelId;

														let body = {
															commands: []
														};
														body.commands.push(command);

														webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.restEndpoints.command, body).then(function(data) {

															if (!data.json()) {

																webix.message({ type: "error", text: i18n.t("LBL_ERROR_OPERATION") });
															}
														});														
													};
												}
											}
										}
									]
								},
								{}
							]
						}
					]
				}
				connectWS();

				webix.ui(viewContent, $$("view-tunnel-content-" + self.uid));
				
				$$("view-tunnel-loading-" + self.uid).hide();
				$$("view-tunnel-content-" + self.uid).show();
				self.parent.treeView.enable(true);
			}
		});

	}
}
