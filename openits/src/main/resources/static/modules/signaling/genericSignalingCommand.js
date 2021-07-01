export default class GenericSignalingCommand {

	constructor(parent, selectedElementIds, commandElement) {

		this.uid = webix.uid();
		this.parent = parent;
		this.selectedElementIds = selectedElementIds;
		this.commandElement = commandElement;
	}

	getView() {
		
		let self = this;

		let view = {
			
			id: this.uid,
			header: i18n.t(this.commandElement.labelAlias),
			body: drawComponents()
		}
		
		function drawComponents() {
			
			let viewComponents = {
				
				rows: [
					{ height: 30 },
					getViewInfo(),
					{ height: 20 },
					getViewParams(),
					{},
					getViewActions()
				]
			};
			
			function getViewInfo() {
				
				let viewInfo;
						
				viewInfo = {
					
					rows: [
						{
							cols: [
								{
									view: "label",
									label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-information"></i></div>`,
									align: "center",
									height: 50,
									width: 56
								},
								{
									view: "label",
									css: "alarm-view-header-details-text",
									label: `<span class="alarm-view-header-details-text">${i18n.t("LBL_INFO")}</span>`,
									align: "left",
									height: 50
								},
								{}
							]
						},
						{
							cols: [
								{ width: 50 },
								{
									borderless: true,
									width: 100,
									template: `<image style="width: 90px; height: 90px; display: block; margin: 0 auto" src="img/${self.commandElement.image}" onerror="this.onerror=null;this.src='img/send-command-default.png';" />`
								},
								{
									width: 20
								},
								{
									view: "textarea",
									readonly : true,
									height: 100,
									value: self.commandElement.labelDescription ? i18n.t(self.commandElement.labelDescription) : i18n.t(self.commandElement.labelAlias) 
								}
							]
						}
					]
				}
				
				return viewInfo;
			}
			
			function getViewParams() {
				
				let viewParams = {};
				
				if (Array.isArray(self.commandElement.commandElementTypeParams) && self.commandElement.commandElementTypeParams.length > 0) {
					
					viewParams = {
					
						rows: [
							{
								cols: [
									{
										view: "label",
										label: `<div class="title"><i style="color:#33425e; font-size: 2em" class="mdi mdi-table"></i></div>`,
										align: "center",
										height: 50,
										width: 56
									},
									{
										view: "label",
										css: "alarm-view-header-details-text",
										label: `<span class="alarm-view-header-details-text">${i18n.t("LBL_PARAMS")}</span>`,
										align: "left",
										height: 50
									},
									{}
								]
							}
						]
					}	
				}
				
				
				
				return viewParams;
			}
			
			function getViewActions() {
			
				let viewActions = {};
				
				viewActions = {
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
							label: i18n.t("LBL_SEND"),
							on: {
								onItemClick() {
			
									webix.confirm({
										title: i18n.t("LBL_SEND_SIGNALING_CONFIRM"),
										ok: i18n.t("LBL_YES"),
										cancel: i18n.t("LBL_NO"),
										text: i18n.t("LBL_SEND_SIGNALING_CONFIRM_DESCRIPTION") + (self.selectedElementIds.length > 1 ? " " + self.selectedElementIds.length + " " + i18n.t("LBL_EQUIPMENT_SELECTED") : ""),
										width: 600
									}).then(function() {
										
										sendCommandElement();
										
									});					
								}
							}
						}
					]
				};
				
				function sendCommandElement() {

					let body = {
						commands : []
					};
					
					if (self.config.restEndpoints && self.config.restEndpoints.command) {
						
						self.selectedElementIds.forEach(selectedElementId => {
	
							let command = self.config.commands.sendMessage;
						
							command.elementId = selectedElementId;
							
							// TODO: Param values
							
							body.commands.push(command);	
							
						});
						
						if (Array.isArray(body.commands) && body.commands.length > 0) {
												
							webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.parent.config.restEndpoints.command, body).then(function(data) {
									
								console.log(data);
							});	
						}
						
					}
				}
				
				return viewActions;
			}
			
			return viewComponents;
		}
		
		return view;
	}
}
