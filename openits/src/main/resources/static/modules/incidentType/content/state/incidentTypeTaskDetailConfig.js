import SearchDialogBox from "../../../../component/searchDialogBox.js";
import { util } from "../../../../lib/util.js";

const FORM_LABEL_WIDTH = 150;
const FORM_FIELD_WIDTH = 350;

export default class IncidentTypeTaskDetailConfig {

	constructor(config, uid, incidentType, incidentTypeTask, onUpdate) {
			
		this.config = config;
		this.uid = uid;
		this.incidentType = incidentType;
		this.incidentTypeTask = incidentTypeTask;
		this.onUpdate = onUpdate;
	}
	
	draw() {
		
		let self = this;
		let viewTaskConfig; 
		
		viewTaskConfig= {
			id: "incident-type-state-task-detail-content-config-" + self.uid,
			css: "bordered-layout-without-border-top",
			rows: []
		}
		
		if (self.incidentTypeTask) {
			
			let form = {
				id: "incident-type-state-task-detail-content-config-form-" + self.uid,
				view: "form",
				borderless: true,
				elementsConfig: { labelAlign: "left" },
				elements: [
					{
						cols: [
							{ width: 45 },
							{
								rows: [
									{},
									{
										cols: [
											{ 
												view: "switch", 
												id: "incident-type-task-required-" + self.uid, 
												name: "required", 
												required: true, 
												label: i18n.t("LBL_REQUIRED"), 
												labelWidth: FORM_LABEL_WIDTH, 
												width: FORM_FIELD_WIDTH, 
												value: self.incidentTypeTask.mandatory 
											},
											{}
										]
									},
									{ height: 30 },
									{
										cols: [
											{ 
												view: "richselect", 
												id: "incident-type-task-execution-type-" + self.uid, 
												name: "execution-type", 
												required: true, 
												label: i18n.t("LBL_EXECUTION_TYPE"), 
												labelWidth: FORM_LABEL_WIDTH, 
												width: FORM_FIELD_WIDTH,
												options: [
													{ id: 1, value: i18n.t("LBL_AUTO") },
													{ id: 2, value: i18n.t("LBL_MANUAL") },
													{ id: 3, value: i18n.t("LBL_DEFERRED") }
												], 
												value: self.incidentTypeTask.auto ? 1 : (self.incidentTypeTask.delay ? 3 : 2),
												on: {
													onChange: function(newValue, oldValue, config){
												        
												        let counterDeferred = $$("incident-type-task-deferred-" + self.uid);
												        
												        if (counterDeferred) {
													
															newValue == 3 ? counterDeferred.enable() : counterDeferred.disable();
														}
												    }
												}
											},
											{ width: 45 },
											{ 
												view: "counter", 
												id: "incident-type-task-deferred-" + self.uid, 
												name: "deferred", 
												disabled: !self.incidentTypeTask.delay,
												required: false,
												//disabled: true,
												label: i18n.t("LBL_DEFERRED_SECONDS"), 
												labelWidth: FORM_LABEL_WIDTH, 
												width: FORM_FIELD_WIDTH, 
												value: self.incidentTypeTask.delay ? self.incidentTypeTask.delay : 0 
											},
											{}
										]
									},
									{ height: 30 },
									{
										cols: [
											{ 
												view: "text", 
												id: "incident-type-task-script-" + self.uid, 
												name: "script", 
												required: false,
												readonly: true,
												label: i18n.t("LBL_SCRIPT"),
												labelWidth: FORM_LABEL_WIDTH, 
												width: (FORM_FIELD_WIDTH * 2), 
												value: self.incidentTypeTask.onChange 
											},
											{
												view: "icon", icon: "wxi-search",
												on: {
													onItemClick: searchScript										}
											},
											{}
										]
									},
									{}
								]
							},
							{ width: 45 }
						]
					}
				]
			}
			
			viewTaskConfig.rows.push(form);
		}
		
		function searchScript() {
			
			let dialogImages = webix.copy(SearchDialogBox.CALLBACK);
			let label = i18n.t("LBL_SELECT_SCRIPT");
			
			dialogImages.fillDialogEntity = fillDialogEntity;
			dialogImages.height = 600;
			dialogImages.width = 800;
				
			function fillDialogEntity(dialogEntity, onFilledDialogEntity) {
			
				let resource = {
					
					relativePath: self.config.resources.incidentScripts,
					extension: self.config.resources.incidentExtension
				}
				
				webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.config.restEndpoints.serverResources, JSON.stringify(resource)).then(function(response) {
					
					if (response) {
						
						let scripts = response.json();
						
						if (Array.isArray(scripts)) {
							
							dialogEntity.data = [];
	
							dialogEntity.pkField = "id"
							dialogEntity.fields = ["script=LBL_SCRIPT"];
							dialogEntity.sortFieldId = 2; 
							
							scripts.forEach(script => {
															
								dialogEntity.data.push({
									id: script,
									script: script
								});
							});
						}
						
						onFilledDialogEntity(dialogEntity);	
					}
				});
			}
			
			let s = new SearchDialogBox(label, dialogImages, function(items) {

				if (Array.isArray(items) && items.length === 1 && items[0].script) {
					
					let fileScript = items[0].script.replace(self.config.resources.incidentScripts, "").replace("." + self.config.resources.incidentExtension, "");
					
					$$("incident-type-task-script-" + self.uid).setValue(fileScript);
				}
			});

			s.show();
		}
		
		webix.ui(viewTaskConfig, $$(viewTaskConfig.id));
	}
}	
