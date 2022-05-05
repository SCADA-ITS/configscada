export default class IncidentTypeStretch {

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
			id: "incident-type-info-" + self.uid,
			css: { "background-color": "white" },
			height: 200,
			rows: [
				{ height: 10 },
				{
					cols: [
						{ width: 13 },
						{
							css: {
								"box-sizing": "border-box",
								"border": "1px solid #dadee0 !important"
							}				
						},
						{ width: 12 }
					]
				},
				{ height: 5 }
			]
		}
			
		return view;
	}
}	
