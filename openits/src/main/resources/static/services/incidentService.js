import { util } from "../lib/util.js";
import GenericModule from "../modules/genericModule.js";

export default class IncidentService extends GenericModule {

	constructor(module) {

		super(module, null, null);

		this.module = module;
		this.config = module.config;

		this.stomp = {};
		this.incidents = {
			data: {
				stats: {
					incidentPendingToConfirm: 0
				}
			}
		};
	}

	getData() {

		return this.incidents.data;
	}

	getView() {

		let views = [];

		return views;
	}

	loadData() {

		let self = this;

		self.updateStatistics();

	}

	updateStatistics() {

		if (this.config && this.config.stats && this.config.stats.restEndpoint && this.config.stats.topic) {
			this.updateIncidentPendingToConfirm();
		}
	}

	updateIncidentPendingToConfirm() {

		let self = this;

		function connectWS() {

			let stompSubscriptorService = window.sm.getService(Services.STOMP_SUBSCRIPTOR_SERVICE);

			if (stompSubscriptorService) {

				stompSubscriptorService.subscribe(util.stomp.getDataSourceFromTopic(self.config.stats.topic.incidentPendingToConfirm), onMessage);
			}

			function onMessage(data) {

				self.incidents.data.stats.incidentPendingToConfirm = data.value;

				window.userControlPanel.updateData();
			}
		}

		webix.ajax(appConfig.restUrl + this.config.stats.restEndpoint.incidentPendingToConfirm).then(function(data) {

			if (data.json() != undefined) {

				let incidentPendingToConfirm = data.json().value;

				self.incidents.data.stats.incidentPendingToConfirm = incidentPendingToConfirm;

				window.userControlPanel.updateData();

				connectWS();
			}
		});
	}
}
