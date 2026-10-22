import SearchDialogBox from "../../../../component/searchDialogBox.js";
import InputDialogBox from "../../../../component/inputDialogBox.js";
import DataTable from "../../../../component/datatable.js";
import MessageBox from "../../../../component/messageBox.js";

import { util } from "../../../../lib/util.js";

const FORM_LABEL_WIDTH = 300;

export default class IncidentTypeStretch {

	constructor(config, uid, incidentType, onUpdate) {
		
		this.config = config;
		this.incidentType = incidentType;
		this.uid = uid;
		this.onUpdate = onUpdate;
		
		this.dtIncidentTypeCauses = undefined;
	}
	
	getView() {
		
		let self = this;
		
		let view;
		
		view = {
			id: "incident-type-info-" + self.uid,
			css: { "background-color": "white" },
			borderless: true,
			//height: 125,
			rows: [
				{ height: 15 },
				{
					cols: [
						{ width: 10 },
						{
							rows: [
								getIncidentTypeFields(),
								{ height: 15 },
								getViewCauses(),
								{ height: 15 },
								{
									cols: [
										{},
										{
											id: "incident-type-info-save-" + self.uid,
											view: "button",
											css: "action-button",
											width: 130,
											height: 38,
											disabled: true,
											label: i18n.t("LBL_SAVE"),
											on: {
												onItemClick() {
													
													(new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_SAVE_INCIDENT_TYPE_CHANGES_DESCRIPTION"), null, onAccept, null)).show();
													
													function onAccept() {
													
														updateIncidentType();
													}
												}
											}		
										}
									]	
								},
								{ height: 15 }
							]			
						},
						{ 
							view: "template",
							borderless: true,
							width: 1,
							on: {
								
								onAfterRender: function() {
									
									if (self.dtIncidentTypeCauses)  {
										
										self.dtIncidentTypeCauses.callbacks();
									}
																		
									setTimeout(function() {
							
										self.dtIncidentTypeCauses.selectByIndex(0);
										
									}, 50);
									
									$$("incident-type-info-form-" + self.uid).attachEvent("onChange", function() {
									    
									    $$("incident-type-info-save-" + self.uid).enable();
									});
								}
							}
						},
						{ width: 12 }
					]
				},
				{ height: 5 }
			]
		}
		
		function updateIncidentType() {
			
			let body = {
				imsIncidentType: {
					id: self.incidentType.id,
					alias: $$("incident-type-info-form-name-" + self.uid).getValue(),
					description: $$("incident-type-info-form-description-" + self.uid).getValue(),
					iniIncidentStateId: self.incidentType.iniIncidentState.id,
					incidentLevelId: $$("incident-type-info-form-level-" + self.uid).getValue(),
					defaultUserId: $$("incident-type-info-form-userId-" + self.uid).getValue(),
					incidentGroupId: self.incidentType.incidentGroup.id	
				}
			}
			
			webix.ajax().headers(CONTENT_TYPE_JSON).put(appConfig.restUrl + self.config.restEndpoints.updateIncidentType, body).then(
				function(response) {
			
					let incidentTypeResponse = response.json(); 
					
					if (incidentTypeResponse) {
						
						if (self.onUpdate) {
						
							self.onUpdate();
						}
					}	
				}
			);
		}
		
		function getIncidentTypeFields() {

			let form;
			
			let incidentLevels = window.em.getAll(EntityType.IMS_INCIDENT_LEVEL);
			
			form = {
				id: "incident-type-info-form-" + self.uid,
				view: "form",
				css: {
					"box-sizing": "border-box",
					"border": "1px solid #dadee0 !important"
				},
				elementsConfig: { labelAlign: "left" },
				elements: [
					{
						cols: [
							{ width: 45 },
							{
								rows: [
									{ height: 20 },
									{
										cols: [
											{ view: "text", id: "incident-type-info-form-name-" + self.uid, name: "name", required: true, label: i18n.t("LBL_ALIAS"), labelWidth: FORM_LABEL_WIDTH, value: self.incidentType.alias, gravity: 3 },
											{
												gravity: 1,
												cols: [
													{width: 50},
													{
														view: "text",
														id: "incident-type-info-form-img-" + self.uid,
														name: "img",
														required: false,
														readonly: true,
														//label: `<img src='./${self.config.resources.incidentType + "/" + self.incidentType.icon}' class="grid-icon" onerror="this.onerror=null;this.src='./img/grid/default.png' /><span class="grid-title"> </span>`, 
														label: `<img style="padding-top: 3px" src="${"./" + self.config.resources.incidentType + "/" + self.incidentType.icon}" class="grid-icon" onerror="this.onerror=null;this.src='${"./" + self.config.resources.incidentType + "/" + self.config.resources.defaultIncidentType}'" /><span class="grid-title">${""}</span>`,
														labelWidth: 35,
														value: self.incidentType.icon
													},
													{
														view: "icon", icon: "wxi-search",
														on: {
															onItemClick: searchImage
														}
													}
												]
											},
										]
									},
									{ height: 20 },
									{ view: "text", id: "incident-type-info-form-description-" + self.uid, name: "description", required: false, label: i18n.t("LBL_DESCRIPTION"), labelWidth: FORM_LABEL_WIDTH, value: self.incidentType.description },
									{ height: 20 },
									{ 
										view: "richselect", 
										id: "incident-type-info-form-level-" + self.uid, 
										name: "level", 
										required: true, 
										label: i18n.t("LBL_LEVEL"), 
										labelWidth: FORM_LABEL_WIDTH, 
										options: function() {
											
											return incidentLevels.map(e => {
												
												return {
													
													id: e.id,
													value: i18n.t(e.labelAlias)
												}
											});
										}(),
										value: self.incidentType.incidentLevel.id
									},
									{ height: 20 },
									{ view: "text", id: "incident-type-info-form-userId-" + self.uid, name: "userId", hidden: true, value: self.incidentType.defaultUser.id },
									{
										cols: [
										
											{ view: "text", id: "incident-type-info-form-user-" + self.uid, name: "user", required: true, readonly: true, label: i18n.t("LBL_USER"), labelWidth: FORM_LABEL_WIDTH, value: self.incidentType.defaultUser.name },
											{
												view: "icon", icon: "wxi-search",
												on: {
													onItemClick: function() {
		
														let dialogBox = new SearchDialogBox(i18n.t("LBL_SELECT_USER"), SearchDialogBox.USERS, onSelectUser);
		
														dialogBox.show();
													}
												}
											}		
										]
									},
									{ height: 20 }
								]
							},
							{ width: 45 }
						]
					}
				]
			}
			
			function searchImage() {
			
				let dialogImages = webix.copy(SearchDialogBox.CALLBACK);
				let label = i18n.t("LBL_SELECT_IMAGE");
				
				dialogImages.fillDialogEntity = fillDialogEntity;
				dialogImages.height = 600;
				dialogImages.width = 800;
					
				function fillDialogEntity(dialogEntity, onFilledDialogEntity) {
					
					//let url = appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.clientResources, { folder: encodeURIComponent(self.config.resources.incidentType) });
				
					//webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.clientResources, { folder: encodeURIComponent(self.config.resources.incidentType) })).then(function(response) {
						
					webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.config.restEndpoints.clientResources, self.config.resources.incidentType).then(function(response) {
					//webix.ajax().get(url).then(function(response) {
						
						if (response) {
							
							let images = response.json();
							
							if (Array.isArray(images)) {
								
								dialogEntity.data = [];
		
								dialogEntity.pkField = "id"
								dialogEntity.fields = ["idIcon=", "image=LBL_IMAGE"];
								dialogEntity.sortFieldId = 3; 
								
								dialogEntity.icons = {
									idIcon: []
								}
								
								images.forEach(img => {
									
									dialogEntity.icons.idIcon.push({
										id: img,
										icon: self.config.resources.incidentType + "/" + img
									});
									
									dialogEntity.data.push({
										id: img,
										idIcon: img,
										image: img
									});
								});
							}
							
							onFilledDialogEntity(dialogEntity);	
						}
					});
				}
				
				let s = new SearchDialogBox(label, dialogImages, function(items) {
	
					if (Array.isArray(items) && items.length === 1) {
	
						// TODO
					}
				});
	
				s.show();
			}
			
			function onSelectUser(users) {

				if (Array.isArray(users) && users.length === 1) {

					let newUser = users[0];
					
					$$("incident-type-info-form-userId-" + self.uid).setValue(newUser.id);
					$$("incident-type-info-form-user-" + self.uid).setValue(newUser.name);
				}
			}
	
			return form;
		}
		
		function getViewCauses() {
		
			let viewCauses;
			
			viewCauses = {
				
				id: "incident-type-causes-" + self.uid,
				css: {
					"box-sizing": "border-box",
					"border": "1px solid #dadee0 !important"
				},
				rows: getViewDatatableCauses()
			}
			
			function getViewDatatableCauses() {
					
				const defaultGridOptionValues = [
					{ gridOption: { id: 1 }, value: "true" },
					{ gridOption: { id: 2 }, value: "false" },
					{ gridOption: { id: 3 }, value: "2" },
					{ gridOption: { id: 4 }, value: "asc" },
					{ gridOption: { id: 5 }, value: undefined },
					{ gridOption: { id: 6 }, value: undefined },
					{ gridOption: { id: 7 }, value: "true" },
					{ gridOption: { id: 9 }, value: "true" },
					{ gridOption: { id: 11 }, value: "false" },
					{ gridOption: { id: 13 }, value: "false" },
					{ gridOption: { id: 16 }, value: "img/grid/default.png" },
					{ gridOption: { id: 21 }, value: "custom" },
					{ gridOption: { id: 23 }, value: "false" }
				];

				let metadata = {
					filter: true,
					label: "LBL_INCIDENT_TYPE_CAUSES",
					visible: true,
					gridOptionValues: defaultGridOptionValues,
					gridFields: [
						{
							id: 1,
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
							id: 2,
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "LBL_INCIDENT_TYPE_CAUSE",
							labelTooltip: "LBL_INCIDENT_TYPE_CAUSE",
							needsTranslation: false,
							parameterized: false,
							position: 2,
							required: true,
							sourceField: "cause",
							visible: true,
							widthPx: 300,
							adjust: true,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						}
					]
				};
				
				metadata.gridCommands = [];
					
				metadata.gridCommands.push(
					{
						id: 1,
						default_: false,
						icon: "mdi mdi-plus",
						label: "LBL_NEW",
						multiselect: true,
						position: 1,
						showText: false,
						itemRequired: false
					},
					{
						id: 2,
						default_: true,
						icon: "mdi mdi-pencil",
						label: "LBL_EDIT",
						multiselect: false,
						position: 2,
						showText: false,
						itemRequired: true
					},
					{
						id: 3,
						default_: false,
						icon: "mdi mdi-delete",
						label: "LBL_DELETE",
						multiselect: true,
						position: 3,
						showText: false,
						itemRequired: true
					}
				);
				
				let data = [];
				
				if (Array.isArray(self.incidentType.incidentTypeCauses)) {
					
					data = self.incidentType.incidentTypeCauses.map(incidentTypeCause => {
						
						return {
							id: incidentTypeCause.id,
							cause: incidentTypeCause.alias
						}
					});
				}
	
				let events = {};
			
				events[DataTable.EventTypes.ON_COMMAND_EXEC] = [];
					
				// NEW TASK
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 1,
					function: () => {
	
						newIncidentTypeCause();
					}
				});
				
				// update
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 2,
					function: (selectedIds) => {
	
						if (Array.isArray(selectedIds) && selectedIds.length === 1) {
						
							updateIncidentTypeCause(selectedIds[0]);	
						}
						
					}
				});
				
				// DELETE
				events[DataTable.EventTypes.ON_COMMAND_EXEC].push({
					commandId: 3,
					function: (selectedIds) => {
	
						if (Array.isArray(selectedIds)) {
							
							deleteIncidentTypeCauses(selectedIds);
						} 
					}
				});
	
				self.dtIncidentTypeCauses = new DataTable(null, null, metadata, data, events);
				
				return self.dtIncidentTypeCauses.getView();
			}
			
			function newIncidentTypeCause() {

				(new InputDialogBox (i18n.t("LBL_NEW_INCIDENT_TYPE_CAUSE"), i18n.t("LBL_NEW_INCIDENT_TYPE_CAUSE_DESCRIPTION"), 
				
					function(value) {
					
						self.dtIncidentTypeCauses.add({ cause: value });		
					}
				, true, "", DataType.ALPHANUMERIC)).show();
			}
			
			function updateIncidentTypeCause(selectedId) {
				
				let itemToUpdate = self.dtIncidentTypeCauses.getItem(selectedId);
				
				if (itemToUpdate) {
					
					(new InputDialogBox (i18n.t("LBL_EDIT_INCIDENT_TYPE_CAUSE"), i18n.t("LBL_EDIT_INCIDENT_TYPE_CAUSE_DESCRIPTION"), 
				
						function(value) {
							
							let updatedItem = webix.copy(itemToUpdate);
							
							if (updatedItem) {
								
								updatedItem.cause = value;
								
								self.dtIncidentTypeCauses.update(updatedItem);
							}
						}
					, true, itemToUpdate.cause, DataType.ALPHANUMERIC)).show();	
				}
			}
			
			function deleteIncidentTypeCauses(selectedIds) {
				
				if (Array.isArray(selectedIds)) {
					
					let itemsToDelete = self.dtIncidentTypeCauses.getAllItems().filter(e => selectedIds.includes(e.id));
					
					if (Array.isArray(itemsToDelete) && itemsToDelete.length > 0) {
						
						self.dtIncidentTypeCauses.delete(itemsToDelete);	
					}
				}
			}
			
			return viewCauses;	
		}
			
		return view;
	}
	
	
}	
