import { util } from "../../lib/util.js";
import DataTable from "../../component/datatable.js";
import DataView from "../../component/dataView.js";

export default class IncidentReportTaskSignallingSpath {

	constructor(config, taskState, stretchsInfo, index, uid) {

		this.config = config;
		this.taskState = taskState;
		this.stretchsInfo = stretchsInfo;
		this.index = index;
		this.uid = uid;
		this.dtSignallingSpaths = undefined;
		this.lvEquipments = undefined;
		this.mapSignallingSpaths = new Map();
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
							label: "LBL_ALIAS",
							labelTooltip: "LBL_ALIAS",
							needsTranslation: false,
							parameterized: false,
							position: 2,
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
							position: 3,
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
							position: 4,
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
							position: 5,
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
							value: "LBL_COMMAND",
							icon: "mdi mdi-cog",
							default_: true,
							label: "LBL_COMMAND",
							showText: true,
							position: 1,
							multiselect: false,
							itemRequired: true
						}
					]
				};
				
				return metadata;
			}
			
			function getEvents() {
				
				let events = {};
			
				events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
	
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 1,
					function: onEdit
				});
				
				events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

					if (Array.isArray(rows) && rows.length > 0) {

						showElements(rows[0]);	
					}
				};
				
				function showElements(spathSignallingId) {
					
					let spathSignalling;
					
					if (self.mapSignallingSpaths.size > 0) {
						
						spathSignalling = self.mapSignallingSpaths.get(spathSignallingId);
						
						if (spathSignalling && Array.isArray(spathSignalling.elements) && spathSignalling.elements.length > 0) {
							
							let dataView = new DataView(getMetadataViewData(), getDataViewData(), null);
							
							let viewDataView = {
								
								id: "task-details-signalling-spaths-elements-" + self.index + "-" + self.uid,
								gravity: 0.35,
								cols: [dataView.getView()]
							}
							
							webix.ui(viewDataView, $$("task-details-signalling-spaths-elements-" + self.index + "-" + self.uid));	
						}
									
							
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
						
						if (Array.isArray(spathSignalling.elements) && spathSignalling.elements.length > 0) {
							
							data = spathSignalling.elements.map(e => { 
							
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
						
						jsonSignallingToSpath.forEach(spathSignalling => {
							
							let elements = getElementsBetweenRange(spathSignalling);
							
							if (Array.isArray(elements) && elements.length > 0) {
								
								let refElement = elements[0];
								let commandElement = getCommandElement(spathSignalling, refElement);
								
								if (commandElement) {
									
									let rowSpathSignallign = {
										id: spathSignalling.id,
										alias: spathSignalling.alias,
										commandAlias: i18n.t(commandElement.labelAlias),
										elementTypeAlias: i18n.t(refElement.elementType.labelAlias),
										elementSubtypeAlias: spathSignalling.elementSubtypeId ? i18n.t(refElement.elementSubtype.labelAlias) : null
									}
								
									data.push(rowSpathSignallign);
									
									let mapValue = webix.copy(spathSignalling);
							
									mapValue.elements = elements; 
									
									self.mapSignallingSpaths.set(spathSignalling.id, mapValue);	
								}
							}
						});
					}
				}
				
				function getElementsBetweenRange(spathSignalling) {
					
					let elements = undefined;
					
					if (spathSignalling && Array.isArray(self.stretchsInfo) && self.stretchsInfo.length > 0) {
						
						let from = !isNaN(spathSignalling.from) ? spathSignalling.from : -999999;
						let to = !isNaN(spathSignalling.to) ? spathSignalling.to : 999999; 
						
						let stretchsFiltered = self.stretchsInfo.filter(stretchInfo => from <= stretchInfo.position && stretchInfo.position <= to);
						
						if (Array.isArray(stretchsFiltered) && stretchsFiltered.length > 0) {
							
							elements = [];
							
							stretchsFiltered.forEach(stretchFiltered => {
								
								let elementsToAdd = stretchFiltered.elements;
								
								if (Array.isArray(elementsToAdd) && elementsToAdd.length > 0) {
									
									elementsToAdd = elementsToAdd.filter(e => Array.isArray(e.commandElements) && (e.commandElements.findIndex(c => c.id === spathSignalling.command.commandElementTypeId) != -1));
									
									if (Array.isArray(elementsToAdd) && elementsToAdd.length > 0) {
										
										if (spathSignalling.elementSubtypeId) {
										
											elementsToAdd = stretchFiltered.elements.filter(e => e.elementSubtype && e.elementSubtype.id === spathSignalling.elementSubtypeId);
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
				
				function getCommandElement(spathSignalling, element) {
					
					let commandElement = undefined;
					
					if (spathSignalling && element && Array.isArray(element.commandElements) && element.commandElements.length > 0) {
						
						commandElement = element.commandElements.find(e => e.id == spathSignalling.command.commandElementTypeId);
					}
					
					return commandElement;
				}
				
				return data;
			}
			
			function onEdit() {
						
				let selectedItems = self.dtSignallingSpaths.getSelectedItems()
						
				if (Array.isArray(selectedItems) && selectedItems.length === 1) {
					
					let selectedItem = selectedItems[0];
				}
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
		
		if (self.mapSignallingSpaths.size > 0) {
			
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
			
			return incidentTypeTaskValues;
		}
		
		return incidentTypePaskValues;
	}
}