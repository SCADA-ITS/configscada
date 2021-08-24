import PlanLibraryOrder from "./planLibraryOrder.js";
import { util } from "../../../../lib/util.js";

export default class PlanLibrarySignalingOrder extends PlanLibraryOrder {

	constructor(config, uid, plan, planOrder, onUpdate) {
		
		super(config, uid, plan, planOrder, onUpdate);
		
		this.signalingModuleInstance = undefined;
	}
	
	loadData() {
	
		let self = this;

		let moduleActionId = self.config.moduleAction.defaultPlanOrderEdit;
		
		if (self.planOrder && self.planOrder.comandElement && self.planOrder.comandElement.element && self.planOrder.comandElement.element.elementType) {
			
			if (self.planOrder.comandElement.element.elementType.planOrderSignalingModule) {
			
				moduleActionId = parseInt(self.planOrder.comandElement.element.elementType.planOrderSignalingModule);
			}
			
			let module = util.module.getFromModuleAction(moduleActionId);
			
			if (module) {
	
				let moduleAction = { id: moduleActionId };
				let args = { 
					selectedPks: [self.planOrder.comandElement.element.id],
					data: {
						planOrder: self.planOrder,
						newPlanOrder: undefined
					}
				};
	
				util.module.open(module, moduleAction, VIEW_TYPE_CUSTOM, drawTabContent, args, function(instance) { self.signalingModuleInstance = instance });
				
				function drawTabContent(view) {

					let newContentTab = {
						id: "plan-order-signaling-content-" + self.uid,
						cols: [view]
					};
	
					webix.ui(newContentTab, $$("plan-order-signaling-content-" + self.uid));
				}
			}
		}
		
		function saveOrder(commandElement) {
			
			// TODO: Implement saveOrder and call save method
		}
	}
	
	getContent() {
			
		let self = this;
		
		let viewContent = {
			id: "plan-order-signaling-content-" + self.uid,
		}
		
		return viewContent;
	}
	
	getPlanOrder() {

		let self = this;		
		let planOrder = this.planOrder;
		
		if (self.signalingModuleInstance) {
			
			let commandElement = self.signalingModuleInstance.getSignaling();
			
			if (commandElement) {
				
				planOrder.comandElement = commandElement;
			}
		}
		
		return planOrder;
	}
}	
