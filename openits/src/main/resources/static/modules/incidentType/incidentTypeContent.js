import InputDialogBox from "../../component/inputDialogBox.js";
import IncidentTypeTabMain from "./incidentTypeTabMain.js";
import IncidentTypeTabState from "./incidentTypeTabState.js";
import { util } from "../../lib/util.js";

export default class IncidentTypeContent {

	constructor(config, uid, incidentType, onUpdate) {
		
		this.config = config;
		this.incidentType = incidentType;
		this.uid = uid;
		this.onUpdate = onUpdate;
		
		this.incidentTypeTabMain = new IncidentTypeTabMain(config, uid, incidentType, onUpdate);
		this.incidentTypeTabState = new IncidentTypeTabState(config, uid, incidentType, onUpdate);
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
					self.incidentTypeTabMain.getView(),
					self.incidentTypeTabState.getView()
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
}	
