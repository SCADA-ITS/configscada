import DataTable from "../../../../component/datatable.js";
import IncidentTypeStretchDetail from "./incidentTypeStretchDetail.js";

const STRETCH_PANEL_WIDTH = 450;

export default class IncidentTypeStretch {

	constructor(config, uid, incidentType, onUpdate) {
		
		this.config = config;
		this.incidentType = incidentType;
		this.uid = uid;
		this.onUpdate = onUpdate;
		
		this.dtStretchs = undefined;
	}
	
	getView() {
		
		let self = this;
		
		let view;
		
		view = {
			id: "incident-type-stretch-" + self.uid,
			css: { "background-color": "white" },
			rows: [
				{ height: 10 },
				{
					css: { "background-color": "white !important" },
					cols: [
						{ width: 10 },
						getViewStretchs(),
						{ 
							view: "resizer",
							width: 10
						},
						getViewStretch(),
						{ 
							view: "template",
							borderless: true,
							width: 10,
							on: {
								
								onAfterRender: function() {
									
									if (self.dtStretchs)  {
										
										self.dtStretchs.callbacks();
										
										setTimeout(function() {
								
											self.dtStretchs.selectByIndex(0);
										}, 50);
									}
								}
							}
						}
					]
				},
				{ height: 15 }
			]
		}
		
		function getViewStretchs() {
			
			let viewStretchList;
			
			viewStretchList = {
				
				id: "incident-type-main-stretch-panel-" + self.uid,
				header: function() {

					return `<div>
								<span class='ico webix_icon mdi mdi-view-headline'></span>
								<span>${i18n.t("LBL_STRETCHS")}</span>
							</div>`;
				},
				body: {
					width: STRETCH_PANEL_WIDTH,
					rows: getViewDatatableStretchs()
				}
			}
			
			function getViewDatatableStretchs() {

				const defaultGridOptionValues = [
					{ gridOption: { id: 1 }, value: "false" },
					{ gridOption: { id: 2 }, value: "false" },
					{ gridOption: { id: 3 }, value: undefined },
					{ gridOption: { id: 4 }, value: undefined },
					{ gridOption: { id: 5 }, value: undefined },
					{ gridOption: { id: 6 }, value: undefined },
					{ gridOption: { id: 7 }, value: "false" },
					{ gridOption: { id: 8 }, value: "false" },
					{ gridOption: { id: 9 }, value: "true" },
					{ gridOption: { id: 10 }, value: "false" },
					{ gridOption: { id: 11 }, value: "false" },
					{ gridOption: { id: 12 }, value: "false" },
					{ gridOption: { id: 13 }, value: "false" },
					{ gridOption: { id: 14 }, value: "false" },
					{ gridOption: { id: 15 }, value: "false" },
					{ gridOption: { id: 16 }, value: "img/grid/default.png" },
					{ gridOption: { id: 21 }, value: "auto" },
					{ gridOption: { id: 24 }, value: "false" }
				];
	
				let metadata = {
					filter: true,
					label: "LBL_STRETCHS",
					visible: true,
					gridOptionValues: defaultGridOptionValues,
					gridFields: [
						{
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: true,
							needsTranslation: false,
							parameterized: false,
							position: 1,
							required: true,
							sourceField: "id",
							visible: false,
							widthPx: 1,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						},
						{
							extended: false,
							filtered: false,
							isEpochMilli: false,
							isPk: false,
							label: "",
							labelTooltip: "LBL_NAME",
							needsTranslation: false,
							parameterized: false,
							position: 2,
							required: true,
							sourceField: "alias",
							visible: true,
							adjust: true,
							gridFieldDataType: {
								id: 1,
								name: "string"
							}
						}
					]
				};
	
				let data = [];
	
				if (Array.isArray(self.incidentType.incidentTypeStretchs) && self.incidentType.incidentTypeStretchs.length) {
	
					data = self.incidentType.incidentTypeStretchs.map(e => {
						
						return {
							id: e.id,
							alias: e.stretch.alias
						}	
					});
					
					data.sort((a, b) => (a.alias > b.alias) ? 1 : ((b.alias > a.alias) ? -1 : 0));
				}
	
				let events = {};
	
				events[DataTable.EventTypes.ON_SELECT_ROW] = function(rows) {

					let viewIncidentTypeStretchDetail = $$("incident-type-stretch-detail-" + self.uid);
					
					if (Array.isArray(rows) && rows.length > 0 && viewIncidentTypeStretchDetail) {
						
						let incidentTypeStretchId = rows[0];
						
						let incidentTypeStretch = self.incidentType.incidentTypeStretchs.find(e => e.id === incidentTypeStretchId);
						
						if (incidentTypeStretch) {
							
							let incidentTypeStretchDetail = new IncidentTypeStretchDetail(self.config, self.uid, incidentTypeStretch, self.onUpdate);	
							
							webix.ui(incidentTypeStretchDetail.getView(), viewIncidentTypeStretchDetail);
						}
					}
				};
	
				self.dtStretchs = new DataTable(null, null, metadata, data, events);
	
				return self.dtStretchs.getView();
			}
			
			return viewStretchList;
		}
		
		function getViewStretch() {
			
			let viewStretch;
			
			viewStretch = {
				
				id: "incident-type-stretch-detail-" + self.uid
			} 
			
			return viewStretch;
		}
			
		return view;
	}
}	
