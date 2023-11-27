import GraphicIconIncident from "../graphicIconIncident.js";

const PICTOGRAM_STATUS_NO_CONFIRMED = "img/incident_status/no_confirmed.png";
const PICTOGRAM_STATUS_IS_FINAL = "img/incident_status/is_final.png";
const PICTOGRAM_TYPE_PATH = "img/incident_type";
const PICTOGRAM_UNKNOWN_TYPE = "img/incident_type/undefined.png";
const NO_PICTOGRAM = "img/undefined.png"; 

export default class IncidentReport {
	
	constructor(s, g, incident, events) {

		this.s = s;
		this.g = g;
		this.incident = incident;
		this.events = events;

		this.gLevel = this.g.select("#" + GraphicIconIncident.G_ID_LEVEL);
		this.gType = this.g.select("#" + GraphicIconIncident.G_ID_TYPE);
		this.gStatus = this.g.select("#" + GraphicIconIncident.G_ID_STATUS);
	}
	
	render(incident) {

		this.incident = incident;

		// Update level
		if (this.gLevel) {

			this.gLevel.attr({ fill: incident.incidentLevel.color });
		}
		
		// Update type
		if (this.gType) {
				
			if (this.incident.incidentType.icon) {
					
				this.gType.attr( { "xlink:href": PICTOGRAM_TYPE_PATH + "/" + this.incident.incidentType.icon });
			}
			else {
				
				this.gType.attr( { "xlink:href": PICTOGRAM_UNKNOWN_TYPE });
			}
		}
		
		// Update status: (No) Confirmed or if final state
		if (this.gStatus) {
			
			if (this.incident.incidentState.finalState === true) {
					
				this.gStatus.attr( { "xlink:href": PICTOGRAM_STATUS_IS_FINAL });
			}
			else {
				
				this.gStatus.attr( { "xlink:href": this.incident.confirm === true ? NO_PICTOGRAM : PICTOGRAM_STATUS_NO_CONFIRMED });
			}
		}
	}
}

