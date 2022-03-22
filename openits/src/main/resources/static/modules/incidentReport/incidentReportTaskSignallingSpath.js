import { util } from "../../lib/util.js";
import DataTable from "../../component/datatable.js";
import DataView from "../../component/dataView.js";
import MessageBox from "../../component/messageBox.js";

const CHANGED_YES = "yes"
const CHANGED_NO = "no"

export default class IncidentReportTaskSignallingSpath {

	constructor(config, taskState, stretchsInfo, index, uid) {

		this.config = config;
		this.taskState = taskState;
		this.stretchsInfo = stretchsInfo;
		this.index = index;
		this.uid = uid;
		this.dtSignallingSpaths = undefined;
		this.lvEquipments = undefined;
		this.originalMapSignallingSpaths = undefined;
		this.mapSignallingSpaths = new Map();
		this.signallingModuleInstance = undefined;
		this.selectedSignallingSpath = undefined;
		this.winIdSignallingEditor = undefined;
		this.changed = false;
	}

	getView() {
		
		let self = this;
		let viewSignallingSpaths;
		
		viewSignallingSpaths = {
			id: "task-details-signalling-spaths-" + self.index + "-" + self.uid,
			rows: [
				{
					view: "template",
					borderless: true,
					height: 20,
					on: {
						onAfterRender: function() {
			
							if (self.dtSignallingSpaths) {
								
								self.dtSignallingSpaths.callbacks();
								
								if (!self.taskState.incidentTypeTask.rerun && ( self.taskState.incidentTypeTask.auto || self.taskState.completedAt)) {
									
									self.dtSignallingSpaths.disable();
								}
								else {
									
									setTimeout(function() {
										
										if (self.mapSignallingSpaths.size > 0) {
										
											let signallingSpathIds = Array.from(self.mapSignallingSpaths.keys());
											
											signallingSpathIds.sort();
											
											self.dtSignallingSpaths.select(signallingSpathIds[0]);	
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
		viewSignallingSpaths.rows.push({ view: "resizer" });
		
		viewSignallingSpaths.rows.push({ id: "task-details-signalling-spaths-elements-" + self.index + "-" + self.uid, gravity: 0.35});
				
		function getTableSignallingSpaths() {
			
			let viewTableSignallingSpaths = {
				id: "task-details-signalling-spaths-table-" + self.index + "-" + self.uid,
				gravity: 0.65,
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
							label: "LBL_COMMAND_ALIAS",
							labelTooltip: "LBL_COMMAND_ALIAS",
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
					gridCommands: [
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
					]
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
						
						self.selectedSignallingSpath = self.mapSignallingSpaths.get(rows[0]);
						
						showElements();
					}
				};
				
				function showElements() {
					
					let signallingSpath = self.selectedSignallingSpath;
					
					if (signallingSpath && Array.isArray(signallingSpath.elements) && signallingSpath.elements.length > 0) {
						
						let dataView = new DataView(getMetadataViewData(), getDataViewData(), null);
						
						let viewDataView = {
							
							id: "task-details-signalling-spaths-elements-" + self.index + "-" + self.uid,
							gravity: 0.35,
							cols: [dataView.getView()]
						}
						
						webix.ui(viewDataView, $$("task-details-signalling-spaths-elements-" + self.index + "-" + self.uid));	
					}
					
					function getMetadataViewData() {
						
						let metadata;
						
						metadata = {
					
							options: [
								{ id: DataView.Options.ICON_SIZE, value: 40 },
								{ id: DataView.Options.CMD_BUTTONS, value: true },
								{ id: DataView.Options.SEARCH, value: true },
								{ id: DataView.Options.GROUP, value: false },
								{ id: DataView.Options.SORTED, value: true }
							],
							commands: [
								{
									value: "LBL_VIEW",
									icon: "mdi mdi-information-outline",
									label: "LBL_VIEW",
									showText: true,
									position: 1,
									itemRequired: true,
									callback: showEquipment
								}
							]
						}
						
						return metadata;
					}
					
					function getDataViewData() {
				
						let data;
						
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
						
						if (Array.isArray(self.taskState.incidentTypeTask.incidentTypeTaskValues)) {
							
							let incidentTypeTaskValueResult = self.taskState.incidentTypeTask.incidentTypeTaskValues.find(incidentTypeTaskValue => incidentTypeTaskValue.taskTypeParam.id === taskTypeParam.id);
							
							if (incidentTypeTaskValueResult && incidentTypeTaskValueResult.value) {
								
								jsonSignallingToSpath = JSON.parse(incidentTypeTaskValueResult.value);
								
								evalJsonSignallingToSpath() ;
							}
						}
					}
				}
				
				function evalJsonSignallingToSpath() {
					
					if (Array.isArray(jsonSignallingToSpath) && jsonSignallingToSpath.length > 0) {
						
						jsonSignallingToSpath.sort((a, b) => (a.id > b.id) ? 1 : ((b.id > a.id) ? -1 : 0));
						
						jsonSignallingToSpath.forEach(signallingSpath => {
							
							let elements = getElementsBetweenRange(signallingSpath);
							
							if (Array.isArray(elements) && elements.length > 0) {
								
								let refElement = elements[0];
								let commandElement = getCommandElement(signallingSpath, refElement);
								
								if (commandElement) {
									
									let rowSpathSignallign = {
										id: signallingSpath.id,
										changed: CHANGED_NO,
										alias: signallingSpath.alias,
										commandAlias: i18n.t(commandElement.labelAlias),
										elementTypeAlias: i18n.t(refElement.elementType.labelAlias),
										elementSubtypeAlias: signallingSpath.elementSubtypeId ? i18n.t(refElement.elementSubtype.labelAlias) : null
									}
								
									data.push(rowSpathSignallign);
									
									let mapValue = webix.copy(signallingSpath);
							
									mapValue.elements = elements; 
									
									self.mapSignallingSpaths.set(signallingSpath.id, mapValue);	
								}
							}
						});
						
						self.originalMapSignallingSpaths = new Map(JSON.parse(JSON.stringify(Array.from(self.mapSignallingSpaths)))); 
					}
				}
				
				function getElementsBetweenRange(signallingSpath) {
					
					let elements = undefined;
					
					if (signallingSpath && Array.isArray(self.stretchsInfo) && self.stretchsInfo.length > 0) {
						
						let from = !isNaN(signallingSpath.from) ? signallingSpath.from : -999999;
						let to = !isNaN(signallingSpath.to) ? signallingSpath.to : 999999; 
						
						let stretchsFiltered = self.stretchsInfo.filter(stretchInfo => from <= stretchInfo.position && stretchInfo.position <= to);
						
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
			
			function getCommandElement(signallingSpath, element) {
					
				let commandElement = undefined;
				
				if (signallingSpath && element && Array.isArray(element.commandElements) && element.commandElements.length > 0) {
					
					commandElement = element.commandElements.find(e => e.id == signallingSpath.command.commandElementTypeId);
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
				
				if (signallingSpath && Array.isArray(signallingSpath.elements) && signallingSpath.elements.length > 0 && self.dtSignallingSpaths) {
					
					let row = self.dtSignallingSpaths.getItem(signallingSpath.id);
				
					if (row) {
						
						let newRow = webix.copy(row);
						
						let commandElement = getCommandElement(signallingSpath, signallingSpath.elements[0]);
						
						if (commandElement) {
							
							newRow.commandAlias = i18n.t(commandElement.labelAlias);
							newRow.changed = !noChanged ? CHANGED_YES : CHANGED_NO;
							
							self.dtSignallingSpaths.update(newRow);
						}
					} 
				}
			}
			
			function onEditCommand() {
						
				let selectedItems = self.dtSignallingSpaths.getSelectedItems()
						
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
									
									args.data.commandElement.commandElementValues = signallingSpath.command.commandElementValues.map(e => { return { commandElementTypeParam: { id: e.commandElementTypeParamId }, value: e.value}});
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
															
															if (self.signallingModuleInstance && self.selectedSignallingSpath && self.winIdSignallingEditor) {
																
																let winSignallingEditor = $$(self.winIdSignallingEditor);
																
																if (winSignallingEditor) {
																	
																	let commandElement = self.signallingModuleInstance.getSignaling();
																
																	updateSignallingSpath(self.selectedSignallingSpath, commandElement);
																	
																	updataTable(self.selectedSignallingSpath);
																
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
						
								self.winIdSignallingEditor = util.ui.loadWindow(self.taskState.incidentTypeTask.alias + " - " + signallingSpath.alias, signallingEditorView, height, width, true);
								
								util.module.open(module, moduleAction, VIEW_TYPE_CUSTOM, drawTabContent, args, function(instance) { self.signallingModuleInstance = instance });
						
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
				
				(new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_ARE_YOU_SURE_UNDO_COMMANDS"), function() {
					
					self.mapSignallingSpaths = new Map(JSON.parse(JSON.stringify(Array.from(self.originalMapSignallingSpaths))));
				
					if (self.mapSignallingSpaths.size > 0) {
						
						self.mapSignallingSpaths.forEach(signallingSpath => {
						
							updataTable(signallingSpath, true);		
						});
					}	
				}, null)).show();
				
				
				
			}

			self.dtSignallingSpaths = new DataTable(null, null, getMetadata(), getData(), getEvents());
			
			viewTableSignallingSpaths.rows = self.dtSignallingSpaths.getView();
			
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