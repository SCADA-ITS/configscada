import IncidentWizardDrawStep from "./incidentWizardDrawStep.js";

export default class IncidentWizardDrawStepSummary extends IncidentWizardDrawStep {

	constructor(parent, step) {

		super(parent, step);
	}
	
	getView() {
		
		let parent = this.parent;
		let step = this.step;
		
		let viewStepSummary;
			
		viewStepSummary = {

			rows: [
				{
					cols: [
						{ width: 45 },
						{
							rows: [
								{ height: 15 },
								{
									view: "label",
									label: `<span class="ico webix_icon mdi mdi-flag-checkered" style="font-size: 30px;"></span><span class="content-wizard-label">${i18n.t(step.stepLabel)}</span>`
								},
								{ height: 17 },
								getViewSummary()
							]
						},
						{ width: 45 }
					]
				}
			]
		};
		
		function getViewSummary() {

			let viewSummary = {};

			let maxWidthLabels = 610;

			let container = $$(parent.args.containerId);

			if (container) {

				maxWidthLabels = container.config.width / 2;
			}

			viewSummary = {
				cols: [
					{ width: 1, borderless: false, css: { "border-left": "transparent !important" } },
					{
						rows: [
							{ height: 1, borderless: false, css: { "border-bottom": "transparent !important" } },
							{
								cols: [
									{ width: 60, borderless: true },
									{
										id: "summary-" + parent.uid,
										css: "ims-incident-report-tab-details",
										rows: [
											{ height: 30, borderless: true },
											{
												id: "summary-incident-" + parent.uid,
												height: 50,
												borderless: true,
												template: function(obj) {
													return `<div><span class="ico webix_icon mdi mdi-exclamation-thick"></span><span class="field">${i18n.t("LBL_INCIDENT")}</span></div>
														   <div class="value" style="max-width: ${maxWidthLabels}px;">${(obj.incidentType && obj.incidentTypeDesc) ? obj.incidentType + ' - ' + obj.incidentTypeDesc : ''}</div>`
												}
											},
											{
												id: "summary-level-" + parent.uid,
												height: 50,
												borderless: true,
												template: function(obj) {
													return `<div><span class="ico webix_icon mdi mdi-signal-cellular-3"></span><span class="field">${i18n.t("LBL_LEVEL")}</span></div>
														    <div class="ims-incident-report-title" style="position: absolute; padding-left: 375px !important; margin-top: -15px !important;">
																<span class="ims-incident-badge" style="background: ${obj.levelColor}; color: white;">${obj.level}</span>
															</div>`
												}
											},
											{
												id: "summary-location-" + parent.uid,
												height: 50,
												borderless: true,
												template: function(obj) {
													return `<div><span class="ico webix_icon mdi mdi-map-marker"></span><span class="field">${i18n.t("LBL_INCIDENT_LOCATION")}</span></div>
														   <div class="value" style="max-width: ${maxWidthLabels}px;">${obj.location ? obj.location : ''}</div>`
												}
											},
											{
												id: "summary-stretch-" + parent.uid,
												height: 50,
												borderless: true,
												template: function(obj) {
													return `<div><span class="ico webix_icon mdi mdi-road"></span><span class="field">${i18n.t("LBL_INCIDENT_STRETCH")}</span></div>
														   <div class="value" style="max-width: ${maxWidthLabels}px;">${obj.stretch ? obj.stretch : ''}</div>`
												}
											},
											{
												id: "summary-user-" + parent.uid,
												height: 50,
												borderless: true,
												template: function(obj) {
													return `<div><span class="ico webix_icon mdi mdi-account"></span><span class="field">${i18n.t("LBL_INCIDENT_USER_ASSIGNED")}</span></div>
														   <div class="value" style="max-width: ${maxWidthLabels}px;">${obj.user ? obj.user : ''}</div>`
												}
											},
											{}
										]
									},
									{ width: 60, borderless: true }
								]
							},
							{ height: 1, borderless: false, css: { "border-bottom": "transparent !important" } }
						]
					},
					{ width: 1, borderless: false, css: { "border-right": "transparent !important" } }
				]
			}

			return viewSummary;
		}

		return viewStepSummary;
	}
}	
