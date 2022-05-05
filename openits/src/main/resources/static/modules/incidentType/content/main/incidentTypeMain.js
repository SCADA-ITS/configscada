import IncidentTypeInfo from "./incidentTypeInfo.js";
import IncidentTypeStretch from "./incidentTypeStretch.js";

export default class IncidentTypeMain {

	constructor(config, uid, incidentType, onUpdate) {
		
		this.config = config;
		this.incidentType = incidentType;
		this.uid = uid;
		this.onUpdate = onUpdate;
		
		this.incidentTypeStretch = undefined;
	}
	
	getView() {
		
		let self = this;
		
		let view;
		
		view = {
			id: "incident-type-tab-main-" + self.uid,
			header: i18n.t("LBL_INCIDENT"),
			body: getViewMain()
		}
		
		function getViewMain() {
			
			let viewMain;
			
			viewMain = {
				id: "incident-type-main-" + self.uid,
				css: "bordered-layout",
				rows: [
					
					getViewIncidentTypeInfo(),
					getViewIncidentTypeStretch()
				]
			}
			
			function getViewIncidentTypeInfo() {
				
				let viewIncidentTypeInfo;
				
				self.viewIncidentTypeInfo = new IncidentTypeInfo(self.config, self.uid, self.incidentType, self.onUpdate);
				
				viewIncidentTypeInfo = self.viewIncidentTypeInfo.getView(); 
				
				return viewIncidentTypeInfo;
			}
			
			function getViewIncidentTypeStretch() {
				
				let viewIncidentTypeStretch;
				
				self.incidentTypeStretch = new IncidentTypeStretch(self.config, self.uid, self.incidentType, self.onUpdate);
				
				viewIncidentTypeStretch = self.incidentTypeStretch.getView(); 
				
				return viewIncidentTypeStretch;
			}
			
			return viewMain;
		}
			
		return view;
	}
}	
