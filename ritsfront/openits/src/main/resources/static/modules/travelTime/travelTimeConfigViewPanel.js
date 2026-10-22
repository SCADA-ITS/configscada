import { util } from "../../lib/util.js";
import SearchDialogBox from "../../component/searchDialogBox.js";
import MessageBox from "../../component/messageBox.js";

export default class TravelTimeConfigViewPanel {

	constructor(config, args, travelTimeId) {

		this.config = config;
		this.args = args;
		this.travelTimeId = travelTimeId;
	}
	
	load() {
		
		let self = this;
			
		if (self.travelTimeId) {
			
			viewPanel();
		}
		
		function viewPanel() {
			
			webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.elementWithParents, { id: self.travelTimeId }))
				.then(elementResponse => {
				
					if (elementResponse) {
						
						let element = elementResponse.json();
						let hasPanels = false;
						
						if (Array.isArray(element.parents) && element.parents.length > 0) {
							
							let panelsParents = element.parents.filter(e => e.elementType.id === self.config.vms.elementTypeId);
							
							if (Array.isArray(panelsParents) && panelsParents.length > 0) {
								
								hasPanels = true;
								
								if (panelsParents.length === 1) {
									
									openPanel(panelsParents[0].id);
								}
								else {
									
									dialogPanel(panelsParents);
								}
							
								deletePanels(element, panelsParents.map(e => {return e.id}));
							}
						}
						
						if (!hasPanels) {
							
							(new MessageBox(MessageBox.WARN, i18n.t("LBL_NO_VMS_ASSOCIATED_TO_VIEW"), null, null, null)).show();
						}
					}	
				}
			);
			
			function dialogPanel(panels) {
				
				let dialogPanel = webix.copy(SearchDialogBox.CALLBACK);				
			
				dialogPanel.fillDialogEntity = fillDialogEntity;
				dialogPanel.pkField = "id"
				dialogPanel.fields = ["alias=LBL_PANEL"];
				dialogPanel.sortFieldId = 1;
				dialogPanel.height = 600;
				dialogPanel.width = 800;
				
				function fillDialogEntity(dialogEntity, onFilledDialogEntity) {
	
	
					dialogEntity.data = panels.map(e => {
									
						return {
							id: e.id,
							alias: e.alias
						}
					})
							
					onFilledDialogEntity(dialogEntity);
				}
				
				(new SearchDialogBox(i18n.t("LBL_SELECT_PANEL"), dialogPanel, onAccept)).show();
				
				function onAccept(selection) {
					
					let panel = selection[0];
					
					if (panel) {
						
						openPanel(panel.id);
					}
				}
			}
			
			function openPanel(panelId) {
			
				let module = util.module.getFromModuleAction(self.config.vms.action.vmsSignalingModule);
	
				if (module) {
					
					let moduleAction = { id: self.config.vms.action.vmsSignalingModule };
	
					util.module.open(module, moduleAction, VIEW_TYPE_WINDOW, null, { selectedPks: [panelId] });
				}
			}
		}
	}
}