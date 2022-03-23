import IncidentReportTaskDetail from "./incidentReportTaskDetail.js";
import { util } from "../../lib/util.js";
import DataView from "../../component/dataView.js";

export default class IncidentReportTaskElement extends IncidentReportTaskDetail {

	constructor(config, incidentReport, taskState, index, uid) {

		super(config, incidentReport, taskState, index, uid);
	}

	getView() {
		
		let self = this;
		let viewElements;
		
		viewElements = {
			id: "task-details-elements-" + self.index + "-" + self.uid,
			rows: [
				{
					view: "template",
					borderless: true,
					height: 20
				}
			]
		};
		
		viewElements.rows.push(getDataViewElements());
				
		function getDataViewElements() {
			
			function getMetadata() {
						
				let metadata;
				
				metadata = {
			
					options: [
						{ id: DataView.Options.ICON_SIZE, value: 40 },
						{ id: DataView.Options.CMD_BUTTONS, value: true },
						{ id: DataView.Options.SEARCH, value: true },
						{ id: DataView.Options.GROUP, value: true },
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
							callback: showEquipment
						},
						{
							value: "LBL_COMMANDS",
							icon: "mdi mdi-cog",
							label: "LBL_COMMANDS",
							showText: true,
							position: 2,
							itemRequired: true,
							callback: showSignalling
						}
					]
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
				
				function showSignalling(id) {
						
					if (id && Array.isArray(self.taskState.elements) && self.taskState.elements.length > 0) {
						
						let element = self.taskState.elements.find(e => e.id == id);
						
						if (element) {
							
							let moduleActionId = (element.elementType.commandModule) ? element.elementType.commandModule : self.config.moduleAction.commandEquipments;
							let module = util.module.getFromModuleAction(parseInt(moduleActionId));
							
							if (module) {
	
								let moduleAction = { id: moduleActionId };
								let args = { selectedPks: [id], readOnly: true };
	
								util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, args);
							}
						}
					}						
				}
				
				return metadata;
			}
			
			function getData() {
				
				let data;
				
				if (Array.isArray(self.taskState.elements) && self.taskState.elements.length > 0) {
					
					data = self.taskState.elements.map(e => { 
						
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
			
			let dataView = new DataView(getMetadata(), getData(), null);
			
			return dataView.getView();
		}
		
		return viewElements;
	}
}