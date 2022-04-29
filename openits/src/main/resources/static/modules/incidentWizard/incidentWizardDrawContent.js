import IncidentWizardDrawStepSummary from "./incidentWizardDrawStepSummary.js";
import IncidentWizardDrawStepAffectionStretch from "./incidentWizardDrawStepAffectionStretch.js";
import IncidentWizardDrawStepStretchAndLocation from "./incidentWizardDrawStepStretchAndLocation.js";
import IncidentWizardDrawStepIncidentType from "./incidentWizardDrawStepIncidentType.js";
import IncidentWizardDrawStepLocationFromMap from "./incidentWizardDrawStepLocationFromMap.js";

export default class IncidentWizardDrawContent {

	constructor(parent) {

		this.parent = parent;
		
		this.stepsMap = new Map();
	}

	draw(position) {

		let parent = this.parent;

		let step = parent.incidentWizardAction.getStep(position);

		function drawStepIncidentType() {
			
			return new IncidentWizardDrawStepIncidentType(parent, step);
		}

		function drawStepStretchAndLocation() {
			
			return new IncidentWizardDrawStepStretchAndLocation(parent, step);
		}
		
		function drawStepAffectionStretch() {
			
			return new IncidentWizardDrawStepAffectionStretch(parent, step);
		}

		let drawStepSummary = function() {
			
			return new IncidentWizardDrawStepSummary(parent, step);
		}
		
		let drawStepLocationFromMap = function() {
			
			return new IncidentWizardDrawStepLocationFromMap(parent, step);
		}
		
		let incidentWizardDrawStep = eval(step.drawFunction)();
		
		this.stepsMap.set(position, incidentWizardDrawStep); 

		return incidentWizardDrawStep.getView();
	}
	
	load(position, onReady) {
		
		let incidentWizardDrawStep = this.stepsMap.get(position);
		
		if (incidentWizardDrawStep) {
			
			incidentWizardDrawStep.loadData(onReady);
		}
	}
}	
