import { util } from "../../../../lib/util.js";
import GenericModule from "../../../genericModule.js";
import DataTable from "../../../../component/datatable.js";
import MessageBox from "../../../../component/messageBox.js";
import GraphicIcon from "../../../../component/graphicIcon/graphicIcon.js";

const QUEUE_COLOR_ACTIVE = "#009688";
const QUEUE_COLOR_NO_ACTIVE = "#f44336";
const QUEUE_COLOR_UNAVAILABLE = "#475466";
const UPDATE_INTERVAL_MILLIS = 1000;

export default class VmsSignalingQueuesModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
		this.moduleAction = moduleAction;
		this.args = args;

		this.uid = webix.uid();

		this.dataTable = undefined;
		this.data = [];
		this.idStompSubscriptor = [];
		this.elementsIds = [];

		this.initialPanelLoad = new Map();

		this.dataToUpdate = [];

		this.setTimeOut = false;

		let vmsSignalingModule = window.em.getByField(EntityType.MODULE, "id", this.config.action.vmsSignalingModule);

		if (vmsSignalingModule) {

			this.vmsSignalingModuleConfig = vmsSignalingModule.config;
		}
	}


	getView() {

		let self = this;
		let view;

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
									id: self.uid,
									rows: [util.ui.getViewDrawLoading()]
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			],
			on: {
				
				onDestruct: function() {

					let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);
	
					if (stompSubscriptorService && Array.isArray(self.idStompSubscriptor) && self.idStompSubscriptor.length > 0) {
	
						for (let subscriptorId of self.idStompSubscriptor) {
	
							stompSubscriptorService.unsubscribe(subscriptorId);
						}
					}
				}	
			}
			
		}

		return view;
	}

	loadData() {

		let self = this;
		let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", self.config.elementTypeId);

		webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementsQueuesByElementTypeId, { id: self.config.elementTypeId })).then(function(elementTypeElementsResponse) {

			if (elementTypeElementsResponse != null) {

				let elements = elementTypeElementsResponse.json();

				if (Array.isArray(elements) && elements.length > 0) {

					for (let element of elements) {

						self.elementsIds.push(element.id);
						let queuesOfElement = load(element);

						if (Array.isArray(queuesOfElement) && queuesOfElement.length > 0) {

							for (let queue of queuesOfElement) {

								self.data.push(queue);
							}
						}
					}
				}

				webix.ui(drawContent(), $$(self.uid));

				if (self.dataTable) {

					self.dataTable.callbacks();
				}

				connectWS();
			}
		});

		function load(element) {

			let queue = [];

			if (element.queue && Array.isArray(element.queue.commandElements) && element.queue.commandElements.length > 0) {

				let commandPosition = 1;
				let priorities = [];
				element.queue.commandElements.sort((a, b) => (a.enable ? 0 : 1) - (b.enable ? 0 : 1) || a.startTime - b.startTime);

				for (let commandElement of element.queue.commandElements) {

					let panel;
					let priorityLabel;
					let priority = elementType.priorities.find(e => e.id === commandElement.priority.id);
					let priorityEnable = element.queue.priorities.find(e => e.id === commandElement.priority.id && e.enable);

					if (priority) {

						priority.enable = (priorityEnable) ? priorityEnable.enable : undefined;
						priorityLabel = i18n.t(priority.labelAlias);
						priorities.push(priority.id);

						commandPosition = priorities.filter(p => p === priority.id).length;
					}

					if (Array.isArray(commandElement.commandElementValues) && commandElement.commandElementValues.length > 0) {

						let svgId = webix.uid();

						panel = {
							template: function() {

								let divHTML = `<div>
										<svg id=${"vms-composer-svg-" + svgId}></svg>
										</div>`;

								if (self.initialPanelLoad.has(svgId)) {

									divHTML = self.initialPanelLoad.get(svgId);

								} else {

									let config = self.config;

									drawGraphicIcon(svgId, element, commandElement, priority, function(graphicIconInstance) {

										graphicIconInstance.stop();
										config.graphicIconInstance = graphicIconInstance;
										let svgHTML = $$("table-" + self.uid).getNode().querySelector('#vms-composer-svg-' + svgId);

										if (svgHTML && svgHTML.outerHTML) {

											self.initialPanelLoad.set(svgId, svgHTML.outerHTML);
										}

										if (graphicIconInstance.getConfiguration) {

											let animated = graphicIconInstance.getConfiguration();

											if (animated.isAnimated) {

												let datatable = $$("dt-" + self.dataTable.uid);
												let itemToUpdate = datatable.config.data.find(dat => dat.id === commandElement.id);

												itemToUpdate.animated = i18n.t("LBL_QUEUE_ITEM_ANIMATED");
												
												datatable.render(commandElement.id, itemToUpdate, "update");
											}
										}
									});
								}

								return divHTML;
							},
							tooltip: function(obj) {

								let svgHTML = $$("table-" + self.uid).getNode().querySelector('#vms-composer-svg-' + svgId);

								return `<div style='transform: scale(3)'>
										${svgHTML.outerHTML}
										</div>`;
							}
						};
					}

					if (commandElement.commandElementType.id === self.vmsSignalingModuleConfig.commands.switchOff.commandElementTypeId) {

						panel = {
							template: function() {

								return `<div style='display: flex;'>
										<image style="margin-top: 5px; width: 24px; height: 24px;" src="img/${commandElement.commandElementType.image}" onerror="this.onerror=null;this.src='img/send-command-default.png';" />
										<span>${i18n.t(commandElement.commandElementType.labelAlias)}</span>
										</div>`;
							},
							tooltip: function(obj) {

								return i18n.t(commandElement.commandElementType.labelAlias);
							}
						};
					}

					queue.push({
						id: commandElement.id,
						labelAlias: element.alias,
						state: element.elementState ? util.element.getAliasState(element.elementState) : i18n.t(element.elementTypeState.labelAlias),
						conflict: (commandElement.conflict) ? i18n.t("LBL_CONFLICT") : "",
						message: panel,
						initialDate: commandElement.startTime,
						endDate: commandElement.endTime,
						priorityType: priorityLabel,
						origin: (commandElement.source) ? commandElement.source : "-",
						position: commandPosition,
						elementId: element.id,
						enable: (commandElement.enable) ? i18n.t("LBL_QUEUE_ITEM_ACTIVE") : "",
						animated: ""
					});
				}
			}

			return queue;
		}

		function drawContent() {

			let main;

			main = {
				id: "table-" + self.uid,
				rows: getTable()
			};

			return main;
		}

		function getTable() {

			const defaultGridOptionValues = [
				{ gridOption: { id: 1 }, value: "true" },
				{ gridOption: { id: 2 }, value: "true" },
				{ gridOption: { id: 3 }, value: 3 },
				{ gridOption: { id: 4 }, value: "asc" },
				{ gridOption: { id: 5 }, value: undefined },
				{ gridOption: { id: 6 }, value: undefined },
				{ gridOption: { id: 7 }, value: "true" },
				{ gridOption: { id: 8 }, value: "false" },
				{ gridOption: { id: 9 }, value: "true" },
				{ gridOption: { id: 10 }, value: "true" },
				{ gridOption: { id: 11 }, value: "true" },
				{ gridOption: { id: 12 }, value: "true" },
				{ gridOption: { id: 13 }, value: "true" },
				{ gridOption: { id: 14 }, value: "false" },
				{ gridOption: { id: 15 }, value: "false" },
				{ gridOption: { id: 16 }, value: "img/grid/queue-menu.png" },
				{ gridOption: { id: 17 }, value: "true" },
				{ gridOption: { id: 20 }, value: "false" }
			];

			let metadata = {
				filter: true,
				label: "LBL_SIGNALING_QUEUES",
				visible: true,
				gridOptionValues: defaultGridOptionValues,
				gridFields: [
					{
						id: 1,
						enabled: true,
						extended: false,
						filtered: false,
						groupingSummary: false,
						isEpochMilli: false,
						isPk: true,
						label: "LBL_ID",
						labelTooltip: "LBL_ID",
						needsTranslation: false,
						parameterized: false,
						position: 1,
						required: true,
						sourceField: "id",
						visible: false,
						widthPx: 35,
						gridFieldDataType: {
							id: 1,
							description: "string",
							name: "string"
						}
					},
					{
						id: 2,
						enabled: true,
						extended: false,
						filtered: true,
						groupingSummary: false,
						isEpochMilli: false,
						isPk: false,
						needsTranslation: false,
						parameterized: true,
						position: 2,
						required: true,
						sourceField: "conflict",
						visible: true,
						widthPx: 35,
						gridFieldDataType: {
							id: 1,
							description: "string",
							name: "string"
						},
						gridFieldIcons: [
							{
								id: 1,
								avatar: false,
								badgeColor: false,
								enabled: true,
								icon: "./img/grid/critical.png",
								showValue: false,
								value: i18n.t("LBL_CONFLICT"),
								labelValue: "LBL_CONFLICT"
							}
						]
					},
					{
						id: 5,
						enabled: true,
						extended: false,
						filtered: true,
						groupingSummary: false,
						isEpochMilli: false,
						isPk: false,
						needsTranslation: false,
						parameterized: true,
						position: 3,
						required: true,
						sourceField: "enable",
						visible: true,
						widthPx: 35,
						gridFieldDataType: {
							id: 1,
							description: "string",
							name: "string"
						},
						gridFieldIcons: [
							{
								id: 1,
								avatar: false,
								badgeColor: false,
								enabled: true,
								icon: "./img/grid/confirmed.png",
								showValue: false,
								value: i18n.t("LBL_QUEUE_ITEM_ACTIVE"),
								labelValue: "LBL_QUEUE_ITEM_ACTIVE"
							}
						]
					},
					{
						id: 3,
						enabled: true,
						extended: false,
						filtered: true,
						groupingSummary: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_ALIAS",
						labelTooltip: "LBL_ALIAS",
						needsTranslation: true,
						parameterized: true,
						position: 4,
						required: true,
						sourceField: "labelAlias",
						visible: true,
						widthPx: 150,
						gridFieldDataType: {
							id: 1,
							description: "string",
							name: "string"
						}
					},
					{
						id: 4,
						enabled: true,
						extended: false,
						filtered: true,
						groupingSummary: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_STATE",
						labelTooltip: "LBL_STATE",
						needsTranslation: true,
						parameterized: true,
						position: 5,
						required: true,
						sourceField: "state",
						visible: true,
						widthPx: 150,
						gridFieldDataType: {
							id: 1,
							description: "string",
							name: "string"
						}
					},
					{
						id: 12,
						enabled: true,
						extended: false,
						filtered: true,
						groupingSummary: false,
						isEpochMilli: false,
						isPk: false,
						needsTranslation: false,
						parameterized: true,
						position: 6,
						required: true,
						sourceField: "animated",
						visible: true,
						widthPx: 35,
						gridFieldDataType: {
							id: 1,
							description: "string",
							name: "string"
						},
						gridFieldIcons: [
							{
								id: 1,
								avatar: false,
								badgeColor: false,
								enabled: true,
								icon: "./img/grid/alternate.png",
								showValue: false,
								value: i18n.t("LBL_QUEUE_ITEM_ANIMATED"),
								labelValue: "LBL_QUEUE_ITEM_ANIMATED"
							}
						]
					},
					{
						id: 6,
						enabled: true,
						extended: false,
						filtered: false,
						groupingSummary: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_MESSAGE_TEXT",
						labelTooltip: "LBL_MESSAGE_TEXT",
						needsTranslation: false,
						parameterized: false,
						position: 7,
						required: true,
						sourceField: "message",
						visible: true,
						widthPx: 400,
						gridFieldDataType: {
							id: 1,
							description: "string",
							name: "string"
						}
					},
					{
						id: 7,
						enabled: true,
						extended: false,
						filtered: true,
						groupingSummary: false,
						isEpochMilli: true,
						isPk: false,
						label: "LBL_SINCE",
						labelTooltip: "LBL_SINCE",
						needsTranslation: false,
						parameterized: true,
						position: 8,
						required: true,
						sourceField: "initialDate",
						visible: true,
						widthPx: 200,
						gridFieldDataType: {
							id: 4,
							description: "date",
							name: "date"
						}
					},
					{
						id: 8,
						enabled: true,
						extended: false,
						filtered: true,
						groupingSummary: false,
						isEpochMilli: true,
						isPk: false,
						label: "LBL_UNTIL",
						labelTooltip: "LBL_UNTIL",
						needsTranslation: false,
						parameterized: true,
						position: 9,
						required: true,
						sourceField: "endDate",
						visible: true,
						widthPx: 200,
						gridFieldDataType: {
							id: 4,
							description: "date",
							name: "date"
						}
					},
					{
						id: 9,
						enabled: true,
						extended: false,
						filtered: true,
						groupingSummary: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_PRIORITY_TYPE",
						labelTooltip: "LBL_PRIORITY_TYPE",
						needsTranslation: false,
						parameterized: true,
						position: 10,
						required: true,
						sourceField: "priorityType",
						visible: true,
						widthPx: 200,
						gridFieldDataType: {
							id: 1,
							description: "string",
							name: "string"
						}
					},
					{
						id: 10,
						enabled: true,
						extended: false,
						filtered: true,
						groupingSummary: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_ORIGIN",
						labelTooltip: "LBL_ORIGIN",
						needsTranslation: false,
						parameterized: true,
						position: 11,
						required: true,
						sourceField: "origin",
						visible: true,
						widthPx: 200,
						gridFieldDataType: {
							id: 1,
							description: "string",
							name: "string"
						}
					},
					{
						id: 11,
						enabled: true,
						extended: false,
						filtered: true,
						groupingSummary: false,
						isEpochMilli: false,
						isPk: false,
						label: "LBL_POSITION",
						labelTooltip: "LBL_POSITION",
						needsTranslation: false,
						parameterized: true,
						position: 12,
						required: true,
						sourceField: "position",
						visible: true,
						widthPx: 100,
						gridFieldDataType: {
							id: 1,
							description: "string",
							name: "string"
						}
					}
				],
				gridCommands: [
					{
						id: 1,
						value: "LBL_VIEW",
						icon: "mdi mdi-eye",
						default_: true,
						label: "LBL_CMD_VIEW",
						showText: false,
						position: 1,
						multiselect: false,
						itemRequired: true,
					},
					{
						id: 2,
						value: "LBL_DELETE",
						icon: "mdi mdi-delete",
						default_: false,
						label: "LBL_CMD_DELETE",
						showText: false,
						position: 2,
						multiselect: true,
						itemRequired: true,
					}
				]
			};

			let events = {};

			events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 1,
				function: (obj) => {

					let itemSelected = self.dataTable.getSelectedItems();
					let elementIds = [itemSelected[0].elementId];
					let module = window.em.getByField(EntityType.MODULE, "id", self.config.action.vmsSignalingModule);

					if (module) {

						let args = { selectedPks: elementIds };
						util.module.open(module, null, VIEW_TYPE_WINDOW, null, args);
					}
				}
			});

			events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
				commandId: 2,
				function: (obj) => {

					util.element.deleteCommand(obj, function(res) {

						if (res) {

							(new MessageBox(MessageBox.INFO, i18n.t("LBL_COMMAND_SEND_SUCCESSFULLY"), null, null, null)).show();
						}
					}, true);
				}
			});

			self.dataTable = new DataTable(null, null, metadata, self.data, events);

			return self.dataTable.getView();
		}


		function drawGraphicIcon(svgId, element, commandElement, priority, onAfterRender) {

			let property = self.vmsSignalingModuleConfig.properties.find(e => e.id === element.elementSubtype.id);
			let graphicIcon = undefined;

			if ((graphicIcon = property.graphicIcon) != null) {

				graphicIcon.width = 100;
				graphicIcon.height = 30;

				const lazy = async _ => {

					let jsFile = util.getContextPath() + '/' + graphicIcon.jsDrawFile;

					const mod = await import(jsFile);

					let layerElement = {
						graphicIcon: graphicIcon
					}

					let s = Snap("#" + "vms-composer-svg-" + svgId);

					if (s) {

						s.attr({
							width: 100,
							height: 30
						});

						let container = {
							type: GraphicIcon.CONTAINER_TYPE_WEBIX_VIEW,
							obj: s
						}


						let graphicIconInstance = new mod.default(layerElement, element, container);

						graphicIconInstance.load(function() {

							graphicIconInstance.loadRender(function() {

								let color = QUEUE_COLOR_UNAVAILABLE;

								if (priority.enable) {

									color = (commandElement && commandElement.enable) ? QUEUE_COLOR_ACTIVE : QUEUE_COLOR_NO_ACTIVE;
								}

								graphicIconInstance.updateState({
									color: color
								});

								let jsonValue = [];

								if (commandElement && Array.isArray(commandElement.commandElementValues) &&
									commandElement.commandElementValues.length > 0) {

									let commandElementValue = commandElement.commandElementValues.find(e => e.commandElementTypeParam.id === self.vmsSignalingModuleConfig.commands.sendMessage.commandElementValues[0].commandElementTypeParamId);

									if (commandElementValue) {

										jsonValue = JSON.parse(commandElementValue.value);
									}
								}

								let elementValueJsonData = {

									elementTypeParam: {
										id: self.vmsSignalingModuleConfig.vms.jsonData
									},
									value: JSON.stringify(jsonValue)
								}

								graphicIconInstance.updateValues([elementValueJsonData]);

								if (onAfterRender) {

									onAfterRender(graphicIconInstance);
								}
							});
						});
					}
				}

				lazy();
			}
		}
		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				if (Array.isArray(self.elementsIds) && self.elementsIds.length > 0) {

					self.elementsIds.forEach(elementId => {

						let topic = self.vmsSignalingModuleConfig.topics.elementQueue + "." + elementId;
						let subscriptorId = stompSubscriptorService.subscribe(appConfig.stompUrl, topic, onMessage);
						self.idStompSubscriptor.push(subscriptorId);
					});
				}
			}

			function onMessage(data) {

				if (data && data.id) {

					let sameId = self.dataToUpdate.find(e => e === data.id);

					if (!sameId) {

						self.dataToUpdate.push(data.id);
					}

					if (self.dataToUpdate.length === 1 && !sameId && !self.setTimeOut) {

						self.setTimeOut = true;

						setTimeout(function() {

							let datatable = $$("dt-" + self.dataTable.uid);
							let dataToUpdateCopy = webix.copy(self.dataToUpdate);
							self.dataToUpdate = [];

							webix.extend(datatable, webix.ProgressBar);

							datatable.showProgress({
								hide: false, type: "bottom"
							});

							webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.restEndpoints.element, JSON.stringify(dataToUpdateCopy)).then(function(elementsResponse) {

								if (elementsResponse != null) {

									let elements = elementsResponse.json();

									if (Array.isArray(elements) && elements.length > 0) {

										let queuesToAdd = [];
										let queuesToDelete = [];
										let queuesToUpdate = [];

										elements.forEach(element => {

											let oldQueuesOfElement = self.data.filter(queue => queue.elementId === element.id);
											let newQueuesOfElement = load(element);

											oldQueuesOfElement.forEach(queue => {

												let q = newQueuesOfElement.find(q => q.id === queue.id);

												if (!q) {

													queuesToDelete.push(queue);
													self.data.splice(self.data.indexOf(queue), 1);

												} else {

													queue.endDate = new Date(queue.endDate);
													queue.initialDate = new Date(queue.initialDate);
													queuesToUpdate.push(q);
												}
											});

											newQueuesOfElement.forEach(queue => {

												let q = oldQueuesOfElement.find(q => q.id === queue.id);

												if (!q) {

													self.data.push(queue);
													queue.endDate = new Date(queue.endDate);
													queue.initialDate = new Date(queue.initialDate);
													queuesToAdd.push(queue);

												} else {

													queue.endDate = new Date(queue.endDate);
													queue.initialDate = new Date(queue.initialDate);
													queuesToUpdate.push(queue);
												}
											});
										});

										if (Array.isArray(queuesToDelete) && queuesToDelete.length > 0) {

											self.dataTable.delete(queuesToDelete);
										}

										if (Array.isArray(queuesToAdd) && queuesToAdd.length > 0) {

											self.dataTable.add(queuesToAdd);
										}

										datatable.eachRow(function(row) {

											let rowToUpdate = queuesToUpdate.find(q => q.id === row);

											if (rowToUpdate) {

												let iconConflictHTML = null;
												let iconActiveHTML = null;
												let conflictCellNodeHTML = datatable.getItemNode({ row: row, column: "conflict" });
												let activeCellNodeHTML = datatable.getItemNode({ row: row, column: "enable" });
												let itemToUpdate = datatable.config.data.find(dat => dat.id === row);

												if (conflictCellNodeHTML) {

													iconConflictHTML = conflictCellNodeHTML.querySelector(".table-field-icons");
												}

												if (activeCellNodeHTML) {

													iconActiveHTML = activeCellNodeHTML.querySelector(".table-field-icons");
												}

												if (rowToUpdate.conflict != "" && iconConflictHTML === null) {

													if (itemToUpdate) {

														itemToUpdate.conflict = i18n.t("LBL_CONFLICT");
													}

												} else if (rowToUpdate.conflict === "" && iconConflictHTML) {

													if (itemToUpdate) {

														itemToUpdate.conflict = "";
													}
												}

												if (itemToUpdate) {

													itemToUpdate.position = rowToUpdate.position;
													itemToUpdate.state = rowToUpdate.state;
												}

												if ((rowToUpdate.enable === i18n.t("LBL_QUEUE_ITEM_ACTIVE") && iconActiveHTML === null) || (rowToUpdate.enable === "" && iconActiveHTML)) {

													self.dataTable.update(rowToUpdate);
												}
											}
										});
									}
								}

								if (datatable) {

									datatable.hide();
									datatable.show();
								}

								datatable.hideProgress();
								self.setTimeOut = false;
							});

						}, UPDATE_INTERVAL_MILLIS);
					}
				}
			}
		}
	}
} 