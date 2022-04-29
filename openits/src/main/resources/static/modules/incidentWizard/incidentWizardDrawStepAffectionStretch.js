import IncidentWizardDrawStep from "./incidentWizardDrawStep.js";

export default class IncidentWizardDrawStepAffectionStretch extends IncidentWizardDrawStep {

	constructor(parent, step) {

		super(parent, step);
	}
	
	getView() {
		
		let self = this;
		
		let step = this.step;
		
		let view;

		view = {

			rows: [
				{
					cols: [
						{ width: 45 },
						{
							rows: [
								{ height: 15 },
								{
									view: "label",
									label: `<span class="ico webix_icon mdi mdi-numeric-${step.position}-box" style="font-size: 30px;"></span><span class="content-wizard-label">${i18n.t(step.stepLabel)}</span>`
								},
								{ height: 17 },
								{
									cols: [
										getViewLists()
									]
								}
							]
						},
						{ width: 45 }
					]
				}
			]
		};
		
		function getViewLists() {
			
			let viewLists;
			
			viewLists = { 
				id: "view-signaling-multi-dbllist-" + self.uid,
				view: "dbllist",
				css: "dbllist",
			  	labelLeft: i18n.t("LBL_AVAILABLE"),
			  	labelRight: i18n.t("LBL_SELECTED"),
				data: [],
				on: {
					onChange() {
							
						let selectedValuesStr = this.getValue();
						
						this.blockEvent();
														
						if (selectedValuesStr) {
							
							if (!selectedValuesStr.split(",").includes(self.parent.incidentData.stretchId)) {
								
								this.select(self.parent.incidentData.stretchId, true);
							}
						}
						else {
							
							this.select(self.parent.incidentData.stretchId, true);
						}
						
						this.unblockEvent();
						
						self.parent.incidentWizardAction.validateStep(self.parent.incidentWizardAction.getCurrentStep());
					},
					
					onAfterLoad() {
						
						if (!self.parent.dblListAffectionStretchs) {
							
							self.parent.dblListAffectionStretchs = this;	
						}
					}
				}
			};
			
			return viewLists;
		}
		
		return view;
	}
}	
