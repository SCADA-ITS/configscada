import IncidentTypeInfo from "./incidentTypeInfo.js";

export default class IncidentTypeMain {

	constructor(config, uid, incidentType, onUpdate) {
		
		this.config = config;
		this.incidentType = incidentType;
		this.uid = uid;
		this.onUpdate = onUpdate;
		
		this.incidentTypeInfo = undefined;
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
				css: "bordered-layout-without-border-top",
				rows: [
					
					getViewIncidentTypeInfo()
				]
			}
			
			function getViewIncidentTypeInfo() {
				
				let viewIncidentTypeInfo;
				
				self.incidentTypeInfo = new IncidentTypeInfo(self.config, self.uid, self.incidentType, self.onUpdate);
				
				viewIncidentTypeInfo = self.incidentTypeInfo.getView(); 
				
				return viewIncidentTypeInfo;
			}
					
			return viewMain;
		}
			
		return view;
	}
}	
