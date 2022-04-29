export default class IncidentTypeTabMain {

	constructor(config, uid, incidentType, onUpdate) {
		
		this.config = config;
		this.incidentType = incidentType;
		this.uid = uid;
		this.onUpdate = onUpdate;
	}
	
	getView() {
		
		let self = this;
		
		let view;
		
		view = {
			id: "incident-type-tab-main-" + self.uid,
			header: i18n.t("LBL_MAIN"),
			body: getViewMain()
		}
		
		function getViewMain() {
			
			let viewMain;
			
			viewMain = {
				id: "incident-type-main-" + self.uid,
				css: "bordered-layout"
			}
			
			return viewMain;
		}
			
		return view;
	}
}	
