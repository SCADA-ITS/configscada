import GenericModule from "../genericModule.js";
import MessageBox from "../../component/messageBox.js";
import InputDialogBox from "../../component/inputDialogBox.js";

import { util } from "../../lib/util.js";

export default class PlanModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.config = module.config;
	}

	getView() {

		return undefined;
	}

	loadData() {

		let self = this;

		if(this.moduleAction.id === this.config.moduleAction.execute){
			
			actionExecute();
		}

		function actionExecute() {
	
			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
		
				if (self.args.confirm === true){
	
					let ms = new MessageBox (MessageBox.CONFIRM,  self.args.confirmDescription ? i18n.t(self.args.confirmDescription) :i18n.t("LBL_PLAN_CONFIRM_EXECUTE_DESCRIPTION"), null, onAccept, null);
					ms.show();
					
					function onAccept() {
					
						executePlan();						
					}
	
				} else {
					
					executePlan();
				}
			}
			
			function executePlan() {
				
				const LOG_TYPE_PLANS = LogType.LOG_TYPE_PLANS;
				let userData = util.user.getUserData();
				let auditLogs = [];
				prepareData(onDataPrepared);
				
				function onDataPrepared(plans) {
				
					if (Array.isArray(plans) && plans.length > 0) {
						
						let showCommentDialog = (self.config.requiredCommentToExecute === true && !self.args.noComments);
						
						auditLogs = getAuditLogs();
						
						if (showCommentDialog && Array.isArray(auditLogs) && auditLogs.length > 0) {
							
							(new InputDialogBox(i18n.t("LBL_SEND_PLAN_COMMENT_DIALOG"), i18n.t("LBL_SEND_PLAN_COMMENT"), function(comment) {
		
								auditLogs = auditLogs.map(auditLog => {
									
									let newAuditLog = webix.copy(auditLog);
									
									if (Array.isArray(newAuditLog.auditLogValues) && newAuditLog.auditLogValues.length > 0) {
										
										let auditLogValueComment = newAuditLog.auditLogValues.find(e => e.logTypeParamId === LOG_TYPE_PLANS.params.COMMENT);
										
										if (auditLogValueComment) {
											
											auditLogValueComment.value = auditLogValueComment.value === "" ? comment : undefined;
										}
									}
									
									return newAuditLog;
								});
								
								send();		
		
							}, false)).show();
						}
						else {
							
							send();
						}
					}
					
					function send() {
						
						let body = {
							planActions: plans.map(e => {
								
								return {
									planId: e.planId,
									action: e.action
								}
							})
						};
						
						webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.execute, body).then(
	
							function() {
								
								if (Array.isArray(auditLogs) && auditLogs.length > 0) {
								
									webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + ENDPOINT_SEND_AUDIT_LOG, { auditLogs: auditLogs });
								}
								
							}
						);	
					}
					
					function getAuditLogs() {
						
						let aLogs = [];
						
						if (self.config.logLevel && LogLevel[self.config.logLevel]) {
							
							plans.forEach(plan => {
							
								let logSubtypeId = LOG_TYPE_PLANS.subtypes.LOG_SUBTYPE_EXECUTE_PLAN;
								
								if (plan.action == PlanAction.STOP) {
									
									logSubtypeId = LOG_TYPE_PLANS.subtypes.LOG_SUBTYPE_STOP_PLAN;
								}
								else if (plan.action == PlanAction.PAUSE) {
									
									logSubtypeId = LOG_TYPE_PLANS.subtypes.LOG_SUBTYPE_EXECUTE_PAUSE;
								}
								
								let auditLog = {
									logTypeId: LOG_TYPE_PLANS.id,
									logSubtypeId: logSubtypeId,
									userId: userData.id,
									level: LogLevel[self.config.logLevel] ? self.config.logLevel : undefined,
									auditLogValues: [
										{
											value: plan.planAlias,
											logTypeParamId: LOG_TYPE_PLANS.params.PLAN
										},
										{
											value: "",
											logTypeParamId: LOG_TYPE_PLANS.params.COMMENT
										}
									]
								};
								
								aLogs.push(auditLog);
								
							});	
						}
						
						return aLogs;
					}
				}
			}
		}

		function prepareData(onDataPrepared) {

			let plans = [];
	
			self.args.selectedPks.forEach(planId => {
				
				let countResponses = 0;
				webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.plan, { id: planId })).then(
				
					function(responsePlan) {
						
						countResponses++;
						
						if (responsePlan) {
					
							let plan = responsePlan.json();
							
							if (plan) {
								
								plans.push({
									planId: plan.id,
									planAlias: plan.alias, 
									action: self.args.planAction
								});
							}
						}
						
						if (countResponses >= self.args.selectedPks.length && onDataPrepared) {
							
							onDataPrepared(plans);
						}
					}
				);
			});
		}
	}

}
