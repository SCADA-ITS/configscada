import IncidentTypeMain from "./main/incidentTypeMain.js";
import IncidentTypeStretch from "./stretch/incidentTypeStretch.js";
import IncidentTypeState from "./state/incidentTypeState.js";

export default class IncidentTypeContent {

	constructor(config, uid, incidentType, onUpdate) {
		
		this.config = config;
		this.incidentType = incidentType;
		this.uid = uid;
		this.onUpdate = onUpdate;
		
		this.incidentTypeMain = new IncidentTypeMain(config, uid, incidentType, onUpdate);
		this.incidentTypeStretch = new IncidentTypeStretch(config, uid, incidentType, onUpdate);
		this.incidentTypeState = new IncidentTypeState(config, uid, incidentType, onUpdate);
		
	}
	
	getView() {
		
		let self = this;
		
		let view = {
			id: "incident-type-content-" + self.uid,
			css: { "background-color": "white" },
			cols: [
				{ width: 20 },
				{
					css: { "box-sizing": "border-box" },
					rows: [
						{ height: 10 },
						drawHeader(),
						{ height: 20 },
						drawIncidentTypeDetail(),
						{ height: 10 }
					]
				},
				{ width: 20 }
			]
			
		};
		
		function drawHeader() {

			let viewHeader;
			
			viewHeader = {

				id: "view-incidentType-header-" + self.uid,
				borderless: true,
				rows: [
					{
						id: "view-incidentType-header-info-" + self.uid,
						height: 80,
						template: function() {

							let incidentIcon = self.incidentType.icon;

							if (incidentIcon == undefined) {
								incidentIcon = "undefined.png";
							}
							return `<image class="ims-incident-report-icon" src="img/incident_type/${incidentIcon}" />
									<div class="ims-incident-report-title">
										${self.incidentType.alias} ${self.incidentType.description}
									</div>
									<div class="ims-incident-report-title">
										<span class="ims-incident-badge" style="background: ${self.incidentType.incidentLevel.color}; color: white;">${i18n.t(self.incidentType.incidentLevel.labelAlias)}</span>
									</div>`;
						}
					}
				]
			}
			
			return viewHeader;
		}
		
		function drawIncidentTypeDetail() {
			
			let viewIncidentTypeDetail;
			
			viewIncidentTypeDetail = {
				view: "tabview",
				id: "view-incidentType-detail-" + self.uid,
				css: "secondary-tab",
				animate: false,
				borderless: true,
				cells: [
					self.incidentTypeMain.getView(),
					self.incidentTypeStretch.getView(),
					self.incidentTypeState.getView()
				],
				multiview: {
					keepViews: true
		        },
				tabbar: {
					tabMargin: 6
				}
			}
			
			return viewIncidentTypeDetail;
		}
			
		return view;
	}
	
	selectTab(tabId) {
		
		let self = this;
		let tabView = $$("view-incidentType-detail-" + self.uid);
		
		if (tabView) {
			
			tabView.setValue(tabId);
		}		
	}
}	
