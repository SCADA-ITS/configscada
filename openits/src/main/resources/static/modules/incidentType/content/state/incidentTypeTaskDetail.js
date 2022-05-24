import IncidentTypeTaskDetailConfig from "./incidentTypeTaskDetailConfig.js";
import IncidentTypeTaskDetailParam from "./incidentTypeTaskDetailParam.js";
import IncidentTypeTaskDetailPlan from "./incidentTypeTaskDetailPlan.js";

export default class IncidentTypeTaskDetail {

	constructor(config, uid, incidentType, incidentTypeTask, onUpdate, lastTabViewId, updateLastViewId) {
			
		this.config = config;
		this.uid = uid;
		this.incidentType = incidentType;
		this.incidentTypeTask = incidentTypeTask;
		this.onUpdate = onUpdate;
		this.lastTabViewId = lastTabViewId;
		this.updateLastViewId = updateLastViewId;
		
		this.incidentTypeTaskDetailConfig = undefined;
		this.incidentTypeTaskDetailParam = undefined;
		this.incidentTypeTaskDetailPlan = undefined;
	}
	
	getView() {
		
		let self = this;
		let editPermission = true;//util.module.isAllowedPermission(self.config.requiredPermissions.edit);	

		let view = {
			id: "incident-type-state-task-detail-" + self.uid,
			rows: [
				getViewHeader(),
				{ height: 10 },
				getViewContent()
			] 
		};
		
		function getViewHeader() {
			
			let viewHeader;
			let pencilIcon = {};

			pencilIcon = {
				view: "icon",
				icon: "mdi mdi-pencil",
				tooltip: i18n.t("LBL_EDIT_TASK_NAME"),
				hidden: !editPermission,
				on: {
					onItemClick: function() {

						let incidentTypeTaskAlias = self.incidentTypeTask.alias;

						(new InputDialogBox(i18n.t("LBL_INCIDENT_TYPE_TASK_ALIAS"), i18n.t("LBL_ENTER_NEW_INCIDENT_TYPE_TASK_ALIAS"), function(newIncidentTypeTaskAlias) {

							if (incidentTypeTaskAlias != newIncidentTypeTaskAlias) {

								// TODO									
							}

						}, true, incidentTypeTaskAlias)).show();
					}
				}
			}

			viewHeader = {
				cols: [
					{ width: 10 },
					{
						rows: [
							{
								cols: [
									{
										view: "label",
										label: "<img src='" + self.incidentTypeTask.taskType.icon + "' class='grid-icon'/><span class='grid-title'>" + i18n.t(self.incidentTypeTask.taskType.labelAlias) + " - " + self.incidentTypeTask.alias + "</span>",
										autowidth: true
									},
									{ width: 5 },
									pencilIcon,
									{},
									getViewActions()
								]
							}
						]
					},
					{ width: 10 }
				]

			};
			
			function getViewActions() {

				let viewActions = { height: 45 };
	
				viewActions = {
					view: "toolbar",
					height: 45,
					borderless: true,
					hidden: !editPermission,
					elements: [
						{},
						{
							view: "button",
							css: "action-button",
							width: 130,
							height: 35,
							label: i18n.t("LBL_SAVE"),
							on: {
								onItemClick() {
									
									(new MessageBox (MessageBox.CONFIRM, i18n.t("LBL_SAVE_TASK_CHANGES_DESCRIPTION"), onAccept, null)).show();
									
									function onAccept() {
									
										// TODO
									}
								}
							}
						}
					]
				};
	
				return viewActions;
			}

			return viewHeader;
		}

		function getViewContent() {
			
			let viewContent;
			
			viewContent = {
				view: "tabview",
				id: "incident-type-state-task-detail-content-" + self.uid,
				css: "secondary-tab",
				animate: false,
				borderless: true,
				cells: [
					getViewTaskConfig(),
					getViewTaskParams(),
					getViewTaskPlans()
				],
				multiview: {
					keepViews: true
		        },
				tabbar: {
					tabMargin: 6
				}
			}
			
			function getViewTaskConfig() {
				
				let viewTaskConfig;
				
				viewTaskConfig = {
					id: "incident-type-state-task-detail-content-tab-config-" + self.uid,
					header: i18n.t("LBL_CONFIG"),
					body: {
						id: "incident-type-state-task-detail-content-config-" + self.uid,
						css: "bordered-layout"	
					}
				}
				
				return viewTaskConfig;
			}
			
			function getViewTaskParams() {
				
				let viewTaskParams;
				
				viewTaskParams = {
					id: "incident-type-state-task-detail-content-tab-params-" + self.uid,
					header: i18n.t("LBL_PARAMS"),
					body: {
						id: "incident-type-state-task-detail-content-params-" + self.uid,
						css: "bordered-layout"	
					}
				}
				
				return viewTaskParams;
			}
			
			function getViewTaskPlans() {
				
				let viewTaskPlans;
				
				viewTaskPlans = {
					id: "incident-type-state-task-detail-content-tab-plans-" + self.uid,
					header: i18n.t("LBL_PLANS"),
					body: {
						id: "incident-type-state-task-detail-content-plans-" + self.uid,
						css: "bordered-layout"	
					}
				}
				
				return viewTaskPlans;
			}
			
			return viewContent;
		}
		
		return view;
	}
	
	loadData() {
		
		let self = this;

		self.incidentTypeTaskDetailConfig = new IncidentTypeTaskDetailConfig(self.config, self.uid, self.incidentType, self.incidentTypeTask, self.onUpdate);
		self.incidentTypeTaskDetailConfig.draw();
		
		self.incidentTypeTaskDetailParam = new IncidentTypeTaskDetailParam(self.config, self.uid, self.incidentType, self.incidentTypeTask, self.onUpdate);
		self.incidentTypeTaskDetailParam.draw();
		
		self.incidentTypeTaskDetailPlan = new IncidentTypeTaskDetailPlan(self.config, self.uid, self.incidentType, self.incidentTypeTask, self.onUpdate);
		self.incidentTypeTaskDetailPlan.draw();
		
		let tabContent = $$("incident-type-state-task-detail-content-" + self.uid);
		
		if (tabContent) {
			
			tabContent.setValue(self.lastTabViewId ? self.lastTabViewId : "incident-type-state-task-detail-content-config-" + self.uid);
			
			tabContent.getTabbar().attachEvent("onChange", function(newValue){
				
				if (typeof self.updateLastViewId === 'function') {
				
					self.updateLastViewId(newValue);	
				}
			});	
		}
	}
	
	async create(incidentTypeTask, position) {
		
		let body = {
			
		}
		
		let data = await webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + this.config.restEndpoints.updateIncidentType, body);
		
		return data.json();
	}
	
	delete (onDelete) {
		
		if (onDelete) {
			
			onDelete();
		}
	}
}	
