import GenericModule from "../modules/genericModule.js";
import { util } from "../lib/util.js";


const INCIDENT_REPORT = Object.freeze({
	moduleAction: {
		edit: 100301,
		confirm: 100302,
		cancel: 100303 
	},
	restEndpoint: "incidents",
	topic: "/topic/incidentReport"
});

export default class IncidentReportModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.incidentReport = undefined;
		this.stomp = {};

		if (this.moduleAction.id === INCIDENT_REPORT.moduleAction.edit) {

			if (this.args && this.args.selectedPks && this.args.selectedPks.length > 0) {

				this.incidentReportId = this.args.selectedPks[0];

				// TODO: Delete this hardcode line
				this.incidentReportId = "ImsIncidentReport:1";
			}
		}
	}

	getView() {

		let view = undefined;

		if (this.moduleAction.id === INCIDENT_REPORT.moduleAction.edit) {

			view = {
				css: "custom_window",
				cols: [
					{
						rows: [
							{ height: 6 },
							{
								cols: [
									{ width: 6 },
									{
										id: this.uid,
										rows: [this.getViewDrawNoData()]
									},
									{ width: 6 }
								]
							},
							{ height: 6 }
						]
					}
				]
			}
		}

		return view;
	}

	loadData() {

		if (this.moduleAction.id === INCIDENT_REPORT.moduleAction.edit) {

			this.refresh();
			this.callbacks();
		}
	}

	getViewDrawNoData() {

		return {
			view: "template",
			id: "no-data-" + this.uid,
			template: function(data) {

				let icon = "<img src='img/loading.svg' align='center'>";

				return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
			}
		}
	}

	refresh() {

		let self = this;

		if (this.moduleAction.id === INCIDENT_REPORT.moduleAction.edit) {

			webix.ajax().get(appConfig.restUrl + INCIDENT_REPORT.restEndpoint + "/" + self.incidentReportId).then(function(dat) {

				if (dat != null) {

					self.incidentReport = dat.json();

					self.drawComponents();
				}

			}, function(response) { util.ajax.handleError(response); });
		}
	}

	callbacks() {

		this.connect_ws();
		this.on_destroy();
	}

	on_destroy() {

		let self = this;
		let view = $$(this.uid);

		if (view) {

			view.attachEvent("onDestruct", function() {

				self.disconnect_ws();
			});
		}
	}

	drawComponents() {

		let self = this;

		let viewMain = {
			id: this.uid,
			cols: [
				{
					rows: [
						getViewHeader(),
						{ height: 6 },
						getViewStateTransitions(),
						{ height: 6 },
						getViewContent()
					]
				},
				{ width: 6 },
				drawActionPanel()
			]
		};

		function getViewHeader() {
			
			let viewHeader = {

				id: "viewHeader-" + self.uid,
				rows: [
					{
						id: "view-bar-color-level-" + self.uid,
						height: 15,
						css: {"background": "red"},
					},
					{
						id: "view-header-info-" + self.uid,
						height: 100,
						css: {"background": "lightgrey"},
						template: function() {
						
							let elementTypePhoto = undefined;
	
							if (elementTypePhoto == undefined) {
								elementTypePhoto = "not_found_50.png";
							}
							return `<image class="ims-incident-report-icon" src="img/${elementTypePhoto}" />
									<div class="ims-incident-report-title">
										<p>${self.incidentReport.incidentType.alias} ${self.incidentReport.incidentType.description}</p>
									</div>`;	
						}
					}
				]
			};

			return viewHeader;
		}

		function getViewStateTransitions() {
			
			let tbStates = {
				view: "toolbar",
				height: 70,
				id: "tb-states",
				elements: []
			};
			
			tbStates.elements.push({ width: 10 })
			
			let state = 0;
			let labelNoCss = false;
			
			self.incidentReport.sequenceOfStates.forEach(e => {
			
				let lblState = { 
					view: "label", 
					label: `<div class="text">
						  		<span class="list-first-line">${++state + " - " + i18n.t(e.state.labelAlias)}</span>
						  		<span class="list-second-line">${i18n.t(e.state.labelDescription)} </span>
							</div>`
				};
				
							
				if (self.incidentReport.incidentState.id === e.state.id) {

					lblState.css = { "background": "lightblue", "border-radius": "0px 60px 60px 0px" };
					labelNoCss = true;
				}
				else {
					
					if (!labelNoCss) {
					
						lblState.css = { "background": "lightblue" };	
					}
					
				}
			
				tbStates.elements.push(lblState);	
			});
	
			let viewStateTransitions = {

				id: "viewStateTransitions-" + self.uid,
				height: 75,
				css: {"background": "lightgrey"},
				rows : [
					{ height: 10 },
					tbStates,
					{ height: 10 }
				]
			};

			return viewStateTransitions;
		}

		function getViewContent() {

			let viewContent = {

				id: "viewContent-" + self.uid,
				css: {"background": "lightgrey"},
			};

			return viewContent;
		}

		function drawActionPanel() {

			let viewActionPanel = {

				id: "viewActionPanel-" + self.uid,
				width: 300,
				css: {"background": "lightgrey"},
			};

			return viewActionPanel;
		}

		webix.ui(viewMain, $$(this.uid));
	}

	connect_ws() {

		function on_connected() {

		}

		function on_message(message) {

			var data = JSON.parse(JSON.parse(message.body));

			console.log(data);

		}

		let url = appConfig.stompUrl;
		let topic = INCIDENT_REPORT.topic + "." + this.incidentReportId;

		this.stomp.client = util.stomp.connect(url, topic, on_connected, on_message);
	}

	disconnect_ws() {

		if (this.stomp != undefined && this.stomp.client != undefined && this.stomp.client.connected) {

			util.stomp.disconnect(this.stomp.client);
			this.stomp = {};
		}
	}
}
