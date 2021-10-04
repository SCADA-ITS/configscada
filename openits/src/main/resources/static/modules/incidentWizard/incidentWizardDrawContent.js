import IncidentWizardDrawStepSummary from "./incidentWizardDrawStepSummary.js";
import IncidentWizardDrawStepStretchAndLocation from "./incidentWizardDrawStepStretchAndLocation.js";
import IncidentWizardDrawStepIncidentType from "./incidentWizardDrawStepIncidentType.js";
import IncidentWizardDrawStepLocationFromMap from "./incidentWizardDrawStepLocationFromMap.js";

export default class IncidentWizardDrawContent {

	constructor(parent) {

		this.parent = parent;
	}

	draw(position) {

		let parent = this.parent;

		let step = parent.incidentWizardAction.getStep(position);

		let drawStepIncidentType = function() {

			return (new IncidentWizardDrawStepIncidentType(parent, step)).getView();
		}

		let drawStepStretchAndLocation = function() {
			
			return (new IncidentWizardDrawStepStretchAndLocation(parent, step)).getView();
		}

		let drawStepSummary = function() {
			
			return (new IncidentWizardDrawStepSummary(parent, step)).getView();
		}
		
		let drawStepLocationFromMap = function() {
			
			return (new IncidentWizardDrawStepLocationFromMap(parent, step)).getView();
		}

		return eval(step.drawFunction)();
	}
}	
