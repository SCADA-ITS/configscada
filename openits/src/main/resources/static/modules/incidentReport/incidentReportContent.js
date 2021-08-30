import IncidentReportTabInfo from "./incidentReportTabInfo.js";
import IncidentReportTabTaskBoard from "./incidentReportTabTaskBoard.js";
import IncidentReportTabLogs from "./incidentReportTabLogs.js";

const INDEX_TASK_BOARD_STATE = 0;
const INDEX_TASK_BOARD_INCIDENT = 1;


export default class IncidentReportContent {

	constructor(parent) {

		this.parent = parent;
		this.uid = parent.uid;
		this.config = parent.config;

		this.incidentReport = undefined;
		this.currentSequenceOfState = undefined;

		this.tabInfoViewId = undefined;
		this.tabLogsViewId = undefined;
		this.tabTaskBoardId = undefined;
		this.tabTaskBoardIncidentId = undefined;

		this.incidentReportTabLogs = undefined;
		this.incidentReportTabTaskBoard = undefined;
		this.incidentReportTabTaskBoardIncident = undefined;

		this.selectedTabId = undefined;
	}

	getView() {

		let self = this;
		let tasks = undefined;
		this.incidentReport = this.parent.incidentReport;
		this.currentSequenceOfState = this.parent.currentSequenceOfState;

		let contentTabs = {
			view: "tabview",
			id: "tabview-content" + self.uid,
			css: "secondary-tab",
			animate: false,
			borderless: true,
			cells: [],
			tabbar: {
				tabMargin: 6,
				on: {
					onBeforeTabClick: function(id) {

						self.selectedTabId = id;

						return $$(id).isEnabled();
					}
				}
			}
		};

		// TAB INFO
		let incidentReportTabInfo = new IncidentReportTabInfo(self.parent);
		if (incidentReportTabInfo) {

			let tabInfoView = incidentReportTabInfo.getView();

			if (tabInfoView) {

				self.tabInfoViewId = tabInfoView.id;
				contentTabs.cells.push({
					header: i18n.t("LBL_INFO"),
					body: tabInfoView
				});
			}
		}

		// TAB TASK OF STATE
		if (self.incidentReport.confirm) {

			tasks = self.currentSequenceOfState.taskStates;
			if (Array.isArray(tasks) && tasks.length) {

				if (!self.incidentReportTabTaskBoard) {

					self.incidentReportTabTaskBoard = new IncidentReportTabTaskBoard(self.parent, INDEX_TASK_BOARD_STATE);
				}

				let tabTaskBoardView = self.incidentReportTabTaskBoard.getView(tasks);
				if (tabTaskBoardView) {

					self.tabTaskBoardId = tabTaskBoardView.id;
					contentTabs.cells.push({
						header: i18n.t("LBL_TASK_BOARD"),
						body: tabTaskBoardView
					});
				}
			} else {
				self.incidentReportTabTaskBoard = undefined;
			}
		}

		// TAB TASK OF INCIDENT
		tasks = self.incidentReport.taskStates;
		if (Array.isArray(tasks) && tasks.length) {

			if (!self.incidentReportTabTaskBoardIncident) {
				self.incidentReportTabTaskBoardIncident = new IncidentReportTabTaskBoard(self.parent, INDEX_TASK_BOARD_INCIDENT);
			}

			let tabTaskBoardView = self.incidentReportTabTaskBoardIncident.getView(tasks);

			if (tabTaskBoardView) {
				self.tabTabTaskBoardIncidentId = tabTaskBoardView.id;
				contentTabs.cells.push({
					header: i18n.t("LBL_TASK_BOARD-INCIDENT"),
					body: tabTaskBoardView
				});
			}
		} else {
			 self.incidentReportTabTaskBoardIncident = undefined; 
		}

		// TAB LOGS
		if (self.incidentReport.confirm) {

			self.incidentReportTabLogs = new IncidentReportTabLogs(self.parent);
			if (self.incidentReportTabLogs) {

				let tabLogsView = self.incidentReportTabLogs.getView();

				if (tabLogsView) {

					self.tabLogsViewId = tabLogsView.id;
					contentTabs.cells.push({
						header: i18n.t("LBL_LOGS"),
						body: tabLogsView
					});
				}
			}
		}

		let viewContent = {

			id: "viewContent-" + self.uid,
			rows: [
				{
					cols: [
						{ width: 15 },
						contentTabs,
						{ width: 15 }
					]
				},
				{ height: 2 }
			]

		};

		return viewContent;
	}

	init() {

		let tabViewContent = $$("tabview-content" + this.uid);
		if (tabViewContent) {

			let tabDetails = this.tabInfoViewId ? $$(this.tabInfoViewId) : undefined;
			let tabTaskBoard = this.tabTaskBoardId ? $$(this.tabTaskBoardId) : undefined;
			let tabLogs = this.tabLogsViewId ? $$(this.tabLogsViewId) : undefined;

			if (this.incidentReportTabLogs) {

				this.incidentReportTabLogs.init();
			}

			if (this.incidentReportTabTaskBoard) {

				this.incidentReportTabTaskBoard.init();
			}

			if (this.incidentReportTabTaskBoardIncident) {

				this.incidentReportTabTaskBoardIncident.init();
			}

			if (this.selectedTabId) {

				if (tabViewContent.getTabbar().getOption(this.selectedTabId)) {

					tabViewContent.getTabbar().setValue(this.selectedTabId);

				} else {

					tabViewContent.getTabbar().setValue(tabDetails.config.id);
				}
			} else {

				tabViewContent.getTabbar().setValue(tabDetails.config.id);
			}

			if (this.incidentReport.confirm) {

				if (!this.selectedTabId && tabTaskBoard) {

					tabViewContent.getTabbar().setValue(tabTaskBoard.config.id);
				}
			} else {

				if (tabTaskBoard) {

					tabTaskBoard.disable();
				}

				if (tabLogs) {

					tabLogs.disable();
				}
			}
		}
	}

}