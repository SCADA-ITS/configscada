export default class IncidentTypeState {

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
			id: "incident-type-tab-state-" + self.uid,
			header: i18n.t("LBL_STATES_AND_TASKS"),
			body: getViewState()
		}
		
		function getViewState() {
			
			let viewState;
			
			viewState = {
				id: "incident-type-state-" + self.uid,
				css: "bordered-layout"
			}
			
			return viewState;
		}
			
		return view;
	}
}	
