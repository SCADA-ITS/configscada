import IncidentReportTaskDetail from "./incidentReportTaskDetail.js";
import { util } from "../../lib/util.js";
import DataTable from "../../component/datatable.js";
import DataView from "../../component/dataView.js";
import MessageBox from "../../component/messageBox.js";

const CHANGED_YES = "yes"
const CHANGED_NO = "no"

export default class IncidentReportTaskDetailSignallingSpath extends IncidentReportTaskDetail {

	constructor(config, incidentReport, taskState, index, uid) {

		super(config, incidentReport, taskState, index, uid);

		this.mapSignallingSpaths = new Map();
		this.changed = false;
	}

	getView() {

		let self = this;
		let stretchsInfo = undefined;
		let dtSignallingSpaths = undefined;
		let originalMapSignallingSpaths = undefined;
		let signallingModuleInstance = undefined;
		let selectedSignallingSpath = undefined;
		let winIdSignallingEditor = undefined;

		let viewSignallingSpaths;

		loadStretchs();

		viewSignallingSpaths = {
			id: "task-details-signalling-spaths-" + self.index + "-" + self.uid,
			rows: [
				{
					view: "template",
					borderless: true,
					height: 20,
					on: {
						onAfterRender: function() {

							if (dtSignallingSpaths) {

								dtSignallingSpaths.callbacks();

								if (!self.taskState.incidentTypeTask.rerun && (self.taskState.incidentTypeTask.auto || self.taskState.completedAt)) {

									dtSignallingSpaths.disable();

								} else {

									setTimeout(function() {

										if (self.mapSignallingSpaths.size > 0) {

											let signallingSpathIds = Array.from(self.mapSignallingSpaths.keys());

											signallingSpathIds.sort();

											dtSignallingSpaths.select(signallingSpathIds[0]);
										}

									}, 100);
								}
							}
						}
					}
				}
			]
		};

		viewSignallingSpaths.rows.push(getTableSignallingSpaths());

		if (Array.isArray(self.taskState.elements) && self.taskState.elements.length > 0) {

			viewSignallingSpaths.rows.push({ view: "resizer" });
			viewSignallingSpaths.rows.push({ id: "task-details-signalling-spaths-elements-" + self.index + "-" + self.uid });
		}


		function loadStretchs() {

			if (self.incidentReport && Array.isArray(self.incidentReport.spath) && self.incidentReport.spath.length > 0) {

				let stretchs = self.incidentReport.spath.map(spath => {

					return {

						id: spath.id,
						position: spath.position,
						elements: []
					}
				});

				if (Array.isArray(self.taskState.elements) && self.taskState.elements.length > 0) {

					assignElementsToStretchs(self.taskState.elements);

					stretchsInfo = stretchs;
				}

				function assignElementsToStretchs(elements) {

					if (Array.isArray(stretchs)) {

						stretchs.forEach(stretch => {

							elements.forEach(element => {

								if (element.location.stretchs.findIndex(elementStretch => elementStretch.id === stretch.id) != -1) {

									stretch.elements.push(element);
								}
							});
						});
					}
				}
			}
		}

		function getTableSignallingSpaths() {

			let viewTableSignallingSpaths = {
				id: "task-details-signalling-spaths-table-" + self.index + "-" + self.uid,
				//gravity: 0.65,
				rows: []
			}

			function getMetadata() {

				let metadata;

				const defaultGridOptionValues = [
					{ gridOption: { id: 1 }, value: "false" },
					{ gridOption: { id: 7 }, value: "false" },
					{ gridOption: { id: 8 }, value: "false" },
					{ gridOption: { id: 9 }, value: "true" },
					{ gridOption: { id: 11 }, value: "false" },
					{ gridOption: { id: 16 }, value: "img/grid/default.png" },
					{ gridOption: { id: 21 }, value: "auto" },
					{ gridOption: { id: 23 }, value: "false" }
				];

				metadata = {
					filter: true,
					label: "LBL_SIGNALLING_SPATHS",
					visible: true,
					gridOptionValues: defaultGridOptionValues,
					gridFields: [
						{
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
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "",
							labelTooltip: "LBL_CHANGED",
							needsTranslation: false,
							parameterized: false,
							position: 2,
							required: true,
							sourceField: "changed",
							visible: true,
							widthPx: 45,
							gridFieldDataType: {
								id: 1,
								name: "string"
							},
							gridFieldIcons: [
								{
									icon: "./img/grid/required.png",
									showValue: false,
									value: "yes",
									labelValue: "LBL_W_CHANGES"
								},
								{
									icon: "./img/grid/not_confirmed.png",
									showValue: false,
									value: "no",
									labelValue: "LBL_WO_CHANGES"
								}
							]
						},
						{
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_ALIAS",
							labelTooltip: "LBL_ALIAS",
							needsTranslation: false,
							parameterized: false,
							position: 3,
							required: true,
							sourceField: "alias",
							visible: true,
							widthPx: 35,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						},
						{
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_COMMAND",
							labelTooltip: "LBL_COMMAND",
							replacementFunction: "getLocalDateString",
							needsTranslation: false,
							parameterized: false,
							position: 4,
							required: true,
							sourceField: "commandAlias",
							visible: true,
							widthPx: 25,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						},
						{
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_ELEMENT_TYPE",
							labelTooltip: "LBL_ELEMENT_TYPE",
							needsTranslation: false,
							parameterized: false,
							position: 5,
							required: true,
							sourceField: "elementTypeAlias",
							visible: true,
							widthPx: 25,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						},
						{
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_ELEMENT_SUBTYPE",
							labelTooltip: "LBL_ELEMENT_SUBTYPE",
							needsTranslation: false,
							parameterized: false,
							position: 6,
							required: true,
							sourceField: "elementSubtypeAlias",
							visible: true,
							widthPx: 25,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						}
					],
					gridCommands: (Array.isArray(self.taskState.elements) && self.taskState.elements.length > 0) ?
						[
							{
								id: 1,
								value: "LBL_EDIT",
								icon: "mdi mdi-pencil",
								default_: true,
								label: "LBL_EDIT",
								showText: true,
								position: 1,
								multiselect: false,
								itemRequired: true
							},
							{
								id: 2,
								value: "LBL_UNDO",
								icon: "mdi mdi-undo",
								default_: false,
								label: "LBL_UNDO",
								showText: true,
								position: 2,
								multiselect: false,
								itemRequired: false
							}
						] : []
				};

				return metadata;
			}

			function getEvents() {

				let events = {};

				events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];

				events[DataTable.EventTypes.ON_COMMAND_EXEC].push(
					{
						commandId: 1,
						function: onEditCommand
					},
					{
						commandId: 2,
						function: onUndoCommand
					}
				);

				events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

					if (Array.isArray(rows) && rows.length > 0 && self.mapSignallingSpaths.size > 0) {

						selectedSignallingSpath = self.mapSignallingSpaths.get(rows[0]);

						showElements();
					}
				};

				function showElements() {

					let signallingSpath = selectedSignallingSpath;

					let dataView = new DataView(getMetadataViewData(), getDataViewData(), null);

					let viewDataView = {

						id: "task-details-signalling-spaths-elements-" + self.index + "-" + self.uid,
						height: 90,
						//							gravity: 0.35,
						cols: [dataView.getView()]
					}

					webix.ui(viewDataView, $$("task-details-signalling-spaths-elements-" + self.index + "-" + self.uid));


					function getMetadataViewData() {

						let metadata;

						metadata = {

							options: [
								{ id: DataView.Options.ICON_SIZE, value: 40 },
								//								{ id: DataView.Options.CMD_BUTTONS, value: true },
								{ id: DataView.Options.SEARCH, value: false },
								{ id: DataView.Options.GROUP, value: false },
								{ id: DataView.Options.SORTED, value: true }
							],
							commands: [
								{
									value: "LBL_INFO",
									icon: "mdi mdi-information-outline",
									label: "LBL_INFO",
									showText: true,
									position: 1,
									itemRequired: true,
									default: true,
									callback: showEquipment
								}
							]
						}

						return metadata;
					}

					function getDataViewData() {

						let data = [];

						if (Array.isArray(signallingSpath.elements) && signallingSpath.elements.length > 0) {

							data = signallingSpath.elements.map(e => {

								return {
									id: e.id,
									image: "img/" + e.elementType.elementTypePhoto,
									value: e.alias,
									group: i18n.t(e.elementType.labelAlias)
								}
							});
						}

						data.sort((a, b) => (parseInt(a.value, 10) > parseInt(b.value, 10)) ? 1 : ((parseInt(b.value, 10) > parseInt(a.value, 10)) ? -1 : 0));

						return data;
					}

					function showEquipment(id) {

						if (id) {

							let module = util.module.getFromModuleAction(self.config.moduleAction.equipmentViewer);

							if (module) {

								let moduleAction = { id: self.config.moduleAction.equipmentViewer };
								let args = { selectedPks: [id] };

								util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
							}
						}
					}
				}


				return events;
			}

			function getData() {

				let data = [];
				let jsonSignallingToSpath = undefined;
				let taskTypeParams = self.taskState.incidentTypeTask.taskType.taskTypeParams;

				if (Array.isArray(taskTypeParams) && taskTypeParams.length > 0) {

					let taskTypeParam = taskTypeParams.find(e => e.id == self.config.taskTypeParamSignallingToSpath);

					if (taskTypeParam) {

						let taskValues;

						if (Array.isArray(self.taskState.incidentReportTaskValues)) {

							taskValues = self.taskState.incidentReportTaskValues;
						}
						else if (self.taskState.incidentTypeTask.incidentTypeTaskValues) {

							taskValues = self.taskState.incidentTypeTask.incidentTypeTaskValues;
						}

						if (Array.isArray(taskValues)) {

							let incidentTypeTaskValueResult = taskValues.find(taskValue => taskValue.taskTypeParam.id === taskTypeParam.id);

							if (incidentTypeTaskValueResult && incidentTypeTaskValueResult.value) {

								jsonSignallingToSpath = JSON.parse(incidentTypeTaskValueResult.value);

								evalJsonSignallingToSpath();
							}
						}
					}
				}

				function evalJsonSignallingToSpath() {

					if (Array.isArray(jsonSignallingToSpath) && jsonSignallingToSpath.length > 0) {

						jsonSignallingToSpath.sort((a, b) => (a.id > b.id) ? 1 : ((b.id > a.id) ? -1 : 0));

						jsonSignallingToSpath.forEach(signallingSpath => {

							if (signallingSpath.command.commandElementTypeId) {

								let elementTypeId = util.element.getElementTypeByCommandElementTypeId(signallingSpath.command.commandElementTypeId);

								if (elementTypeId) {

									let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", elementTypeId);

									if (elementType) {

										let elements = getElementsBetweenRange(signallingSpath);

										let commandElement = getCommandElement(signallingSpath);

										if (commandElement) {

											let elementSubtype = signallingSpath.elementSubtypeId && elementType.elementSubtypes ? elementType.elementSubtypes.find(e => e.id === signallingSpath.elementSubtypeId) : null;

											let rowSpathSignallign = {

												id: signallingSpath.id,
												changed: CHANGED_NO,
												alias: signallingSpath.alias,
												commandAlias: i18n.t(commandElement.labelAlias),
												elementTypeAlias: i18n.t(elementType.labelAlias),
												elementSubtypeAlias: elementSubtype ? i18n.t(elementSubtype.labelAlias) : null
											}

											data.push(rowSpathSignallign);

											let mapValue = webix.copy(signallingSpath);

											mapValue.elements = elements;

											self.mapSignallingSpaths.set(signallingSpath.id, mapValue);
										}
									}
								}
							}
						});

						originalMapSignallingSpaths = new Map(JSON.parse(JSON.stringify(Array.from(self.mapSignallingSpaths))));
					}
				}

				function getElementsBetweenRange(signallingSpath) {

					let elements = undefined;

					if (signallingSpath && Array.isArray(stretchsInfo) && stretchsInfo.length > 0) {

						let from = !isNaN(signallingSpath.from) ? signallingSpath.from : -999999;
						let to = !isNaN(signallingSpath.to) ? signallingSpath.to : 999999;

						let stretchsFiltered = stretchsInfo.filter(stretchInfo => from <= stretchInfo.position && stretchInfo.position <= to);

						if (Array.isArray(stretchsFiltered) && stretchsFiltered.length > 0) {

							elements = [];

							stretchsFiltered.forEach(stretchFiltered => {

								let elementsToAdd = stretchFiltered.elements;

								if (Array.isArray(elementsToAdd) && elementsToAdd.length > 0) {

									elementsToAdd = elementsToAdd.filter(e => Array.isArray(e.commandElements) && (e.commandElements.findIndex(c => c.id === signallingSpath.command.commandElementTypeId) != -1));

									if (Array.isArray(elementsToAdd) && elementsToAdd.length > 0) {

										if (signallingSpath.elementSubtypeId) {

											elementsToAdd = stretchFiltered.elements.filter(e => e.elementSubtype && e.elementSubtype.id === signallingSpath.elementSubtypeId);
										}

										if (Array.isArray(elementsToAdd) && elementsToAdd.length > 0) {

											elements.push(...elementsToAdd);
										}
									}
								}
							});
						}
					}

					return elements;
				}

				return data;
			}

			function getCommandElement(signallingSpath) {

				let commandElement = undefined;

				if (signallingSpath.command.commandElementTypeId) {

					let elementTypeId = util.element.getElementTypeByCommandElementTypeId(signallingSpath.command.commandElementTypeId);

					if (elementTypeId) {

						let elementType = window.em.getByField(EntityType.ELEMENT_TYPE, "id", elementTypeId);

						if (elementType && Array.isArray(elementType.commandElementTypes) && elementType.commandElementTypes.length > 0) {

							commandElement = elementType.commandElementTypes.find(commandElementType => commandElementType.id === signallingSpath.command.commandElementTypeId);
						}
					}
				}

				return commandElement;
			}

			function updateSignallingSpath(signallingSpath, commandElement) {

				if (signallingSpath && signallingSpath.command && commandElement) {

					signallingSpath.command.commandElementTypeId = commandElement.commandElementType.id;

					if (Array.isArray(commandElement.commandElementValues) && commandElement.commandElementValues.length > 0) {

						let commandElementTypeParamId = commandElement.commandElementValues[0].commandElementTypeParam.id;
						let commandElementValue = "";

						if (Array.isArray(commandElement.commandElementValues[0].value) ||
							(typeof commandElement.commandElementValues[0].value === 'object' && commandElement.commandElementValues[0].value)) {

							commandElementValue = JSON.stringify(commandElement.commandElementValues[0].value);
						}
						else {

							commandElementValue = commandElement.commandElementValues[0].value;
						}

						signallingSpath.command.commandElementValues = [
							{
								commandElementTypeParamId: commandElementTypeParamId,
								value: commandElementValue
							}
						]
					}

					self.changed = true;
				}
			}

			function updataTable(signallingSpath, noChanged) {

				if (signallingSpath && dtSignallingSpaths) {

					let row = dtSignallingSpaths.getItem(signallingSpath.id);

					if (row) {

						let newRow = webix.copy(row);

						let commandElement = getCommandElement(signallingSpath);

						if (commandElement) {

							newRow.commandAlias = i18n.t(commandElement.labelAlias);
							newRow.changed = !noChanged ? CHANGED_YES : CHANGED_NO;

							dtSignallingSpaths.update(newRow);
						}
					}
				}
			}

			function onEditCommand() {

				let selectedItems = dtSignallingSpaths.getSelectedItems()

				if (Array.isArray(selectedItems) && selectedItems.length === 1) {

					let signallingSpathId = selectedItems[0].id;

					if (self.mapSignallingSpaths.size > 0) {

						let signallingSpath = self.mapSignallingSpaths.get(signallingSpathId);

						if (signallingSpath && Array.isArray(signallingSpath.elements) && signallingSpath.elements.length > 0) {

							let element = signallingSpath.elements[0];

							let modAction = (element.elementType.planOrderSignalingModule) ? element.elementType.planOrderSignalingModule : self.config.moduleAction.defaultCommandEdit;
							let module = util.module.getFromModuleAction(parseInt(modAction));

							if (module) {

								let moduleAction = { id: module.config.moduleAction.commandEdit };

								let args = {
									selectedPks: [element.id],
									data: {
										commandElement: {
											commandElementType: {
												id: signallingSpath.command.commandElementTypeId
											},
											element: element,
											commandElementValues: undefined
										}
									}
								};

								if (args.data.commandElement && Array.isArray(signallingSpath.command.commandElementValues) && signallingSpath.command.commandElementValues.length > 0) {

									args.data.commandElement.commandElementValues = signallingSpath.command.commandElementValues.map(e => { return { commandElementTypeParam: { id: e.commandElementTypeParamId }, value: e.value } });
								}

								const defaultHeight = 768;
								const defaultWidth = 1280;

								const height = util.module.getWindowParamValue(module, WindowParam.HEIGHT, defaultHeight);
								const width = util.module.getWindowParamValue(module, WindowParam.WIDTH, defaultWidth);

								let signallingEditorView = {
									rows: [
										{
											id: "signalling-editor-body-" + self.uid,
											cols: [util.ui.getViewDrawLoading()]
										},
										{
											view: "toolbar",
											height: 45,
											borderless: true,
											elements: [
												{},
												{
													view: "button",
													id: "btn-send-" + self.uid,
													css: "action-button",
													width: 130,
													height: 35,
													label: i18n.t("LBL_ACCEPT"),
													on: {
														onItemClick() {

															if (signallingModuleInstance && selectedSignallingSpath && winIdSignallingEditor) {

																let winSignallingEditor = $$(winIdSignallingEditor);

																if (winSignallingEditor) {

																	let commandElement = signallingModuleInstance.getSignaling();

																	updateSignallingSpath(selectedSignallingSpath, commandElement);

																	updataTable(selectedSignallingSpath);

																	winSignallingEditor.close();
																}
															}
														}
													}
												},
												{ width: 10 }
											]
										}
									]
								}

								winIdSignallingEditor = util.ui.loadWindow(self.taskState.incidentTypeTask.alias + " - " + signallingSpath.alias, signallingEditorView, height, width, true);

								util.module.open(module, moduleAction, VIEW_TYPE_CUSTOM, drawTabContent, args, function(instance) { signallingModuleInstance = instance });

								function drawTabContent(view) {

									let signallingEditor = {
										id: "signalling-editor-body-" + self.uid,
										cols: [view]
									}

									webix.ui(signallingEditor, $$("signalling-editor-body-" + self.uid));
								}

							} else {

								let ms = new MessageBox(MessageBox.ERROR, i18n.t("LBL_MODULE_NOT_FOUND"), null, null);
								ms.show();
							}
						}
					}
				}
			}

			function onUndoCommand() {

				if (self.changed) {

					(new MessageBox(MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_UNDO_COMMANDS"), function() {

						self.mapSignallingSpaths = new Map(JSON.parse(JSON.stringify(Array.from(originalMapSignallingSpaths))));

						if (self.mapSignallingSpaths.size > 0) {

							self.mapSignallingSpaths.forEach(signallingSpath => {

								updataTable(signallingSpath, true);
							});

							self.changed = false;
						}
					}, null)).show();
				}
				else {

					(new MessageBox(MessageBox.WARN, i18n.t("LBL_UNDO_UNAVAILABLE"))).show();
				}
			}

			dtSignallingSpaths = new DataTable(null, null, getMetadata(), getData(), getEvents());

			viewTableSignallingSpaths.rows = dtSignallingSpaths.getView();

			return viewTableSignallingSpaths;
		}

		return viewSignallingSpaths;
	}

	getIncidentTypeTaskValues() {

		let self = this;
		let incidentTypeTaskValues = undefined;

		if (self.changed && self.mapSignallingSpaths.size > 0) {

			let signallingSpathValues = [];

			self.mapSignallingSpaths.forEach((signallingSpath) => {

				let signallingSpathCopy = webix.copy(signallingSpath);

				if (signallingSpathCopy.elements) {

					delete signallingSpathCopy.elements;
				}

				signallingSpathValues.push(signallingSpathCopy)
			});

			if (Array.isArray(signallingSpathValues) && signallingSpathValues.length > 0) {

				incidentTypeTaskValues = [];

				incidentTypeTaskValues.push({

					taskTypeParamId: self.config.taskTypeParamSignallingToSpath,
					value: JSON.stringify(signallingSpathValues)
				});
			}
		}

		return incidentTypeTaskValues;
	}
}