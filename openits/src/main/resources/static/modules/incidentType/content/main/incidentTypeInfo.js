import SearchDialogBox from "../../../../component/searchDialogBox.js";
import MessageBox from "../../../../component/messageBox.js";
import { util } from "../../../../lib/util.js";

const FORM_LABEL_WIDTH = 150;

export default class IncidentTypeStretch {

	constructor(config, uid, incidentType, onUpdate) {
		
		this.config = config;
		this.incidentType = incidentType;
		this.uid = uid;
		this.onUpdate = onUpdate;
	}
	
	getView() {
		
		let self = this;
		
		let view;
		
		view = {
			id: "incident-type-info-" + self.uid,
			css: { "background-color": "white" },
			height: 200,
			rows: [
				{ height: 10 },
				{
					cols: [
						{ width: 13 },
						{
							css: {
								"box-sizing": "border-box",
								"border": "1px solid #dadee0 !important"
							},
							rows: [
								getForm()
							]			
						},
						{ width: 12 }
					]
				},
				{ height: 5 }
			]
		}
		
		function getForm() {

			let form;
			
			let incidentLevels = window.em.getAll(EntityType.IMS_INCIDENT_LEVEL);
			
			form = {
				id: "incident-type-info-form-" + self.uid,
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
											{ view: "text", id: "incident-type-info-form-name-" + self.uid, name: "name", required: true, label: i18n.t("LBL_ALIAS"), labelWidth: FORM_LABEL_WIDTH, width: FORM_LABEL_WIDTH + 300, value: self.incidentType.alias },
											{ width: 45 },		
											{ view: "text", id: "incident-type-info-form-description-" + self.uid, name: "description", required: false, label: i18n.t("LBL_DESCRIPTION"), labelWidth: FORM_LABEL_WIDTH, value: self.incidentType.description },
										]
									},
									{ height: 35 },
									{
										cols: [
											{ 
												view: "richselect", 
												id: "incident-type-info-form-level-" + self.uid, 
												name: "level", 
												required: true, 
												label: i18n.t("LBL_LEVEL"), 
												labelWidth: FORM_LABEL_WIDTH, 
												width: FORM_LABEL_WIDTH + 300,
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
											{ width: 45 },		
											{ view: "text", id: "incident-type-info-form-userId-" + self.uid, name: "userId", hidden: true, value: self.incidentType.defaultUser.id },
											{ view: "text", id: "incident-type-info-form-user-" + self.uid, name: "user", required: true, disabled: true, label: i18n.t("LBL_USER"), labelWidth: FORM_LABEL_WIDTH, width: FORM_LABEL_WIDTH + 300, value: self.incidentType.defaultUser.name },
											{
												view: "icon", icon: "wxi-search",
												on: {
													onItemClick: function() {

														let dialogBox = new SearchDialogBox(i18n.t("LBL_SELECT_USER"), SearchDialogBox.USERS, onSelectUser);

														dialogBox.show();
													}
												}
											},
											{ width: 45 },		
											{ 
												view: "text", 
												id: "incident-type-info-form-img-" + self.uid, 
												name: "img", 
												required: false, 
												disabled: true, 
												//label: `<img src='./${self.config.resources.incidentType + "/" + self.incidentType.icon}' class="grid-icon" onerror="this.onerror=null;this.src='./img/grid/default.png' /><span class="grid-title"> </span>`, 
												label: `<img style="padding-top: 3px" src="${"./" + self.config.resources.incidentType + "/" + self.incidentType.icon}" class="grid-icon" onerror="this.onerror=null;this.src='${"./" + self.config.resources.incidentType + "/" + self.config.resources.defaultIncidentType}'" /><span class="grid-title">${""}</span>`,
												labelWidth: 50, 
												width: FORM_LABEL_WIDTH + 300, 
												value: self.incidentType.icon 
											},
											{
												view: "icon", icon: "wxi-search",
												on: {
													onItemClick: searchImage												}
											},
											{},
											{
												view: "button",
												css: "action-button",
												width: 130,
												height: 35,
												label: i18n.t("LBL_SAVE"),
												on: {
													onItemClick() {
														
														(new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_SAVE_INCIDENT_TYPE_CHANGES_DESCRIPTION"), onAccept, null)).show();
														
														function onAccept() {
														
															// TODO: Save incident type form
														}
													}
												}
											}
										]
									},
									{}
								]
							},
							{ width: 45 }
						]
					}
				],
				rules: {
					"name": webix.rules.isNotEmpty
				}
			}
			
			function searchImage() {
			
				let dialogImages = webix.copy(SearchDialogBox.CALLBACK);
				let label = i18n.t("LBL_SELECT_IMAGE");
				
				dialogImages.fillDialogEntity = fillDialogEntity;
				dialogImages.height = 600;
				dialogImages.width = 800;
					
				function fillDialogEntity(dialogEntity, onFilledDialogEntity) {
				
					webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.clientResources, { folder: encodeURIComponent(self.config.resources.incidentType) })).then(function(response) {
						
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

				if (Array.isArray(users) && users.length > 0) {

				}

			}
	
			return form;
		}
			
		return view;
	}
	
	
}	
