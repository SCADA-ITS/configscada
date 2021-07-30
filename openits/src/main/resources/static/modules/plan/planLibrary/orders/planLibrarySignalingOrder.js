import PlanLibraryOrder from "./planLibraryOrder.js";

export default class PlanLibrarySignalingOrder extends PlanLibraryOrder {

	constructor(config, uid, plan, planOrder) {
		
		super(config, uid, plan, planOrder);
	}
	
	getView() {
		
		let self = this;
		
		let view = {
			id: "plan-order-lib-plan-" + self.uid,
			rows: [
				drawHeader(),
				drawContent()
			]
		};
		
		function drawHeader() {
			
			let viewHeader = {
				view: "toolbar",
				height: 30,
				borderless: true,
				elements: [
					{
						view: "label",
						label: "<img src='" + self.planOrder.planOrderType.icon + "' class='grid-icon'/><span class='grid-title'>" + i18n.t(self.planOrder.planOrderType.labelAlias) + "</span>" 
					}
				]
			}
			
			return viewHeader;
		}
		
		function drawContent() {
			
			let viewContent = {
				
			}
			
			return viewContent;
		}
		
		return view;
	}
}	
