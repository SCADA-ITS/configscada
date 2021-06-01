import GenericModule from "./genericModule.js";
import AlarmHiddenMute from "./alarmHidden/alarmHiddenMute.js";
import AlarmHiddenWizard from "./alarmHidden/alarmHiddenWizard.js";
import { util } from "../lib/util.js";

export default class AlarmHiddenModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();

		this.config = module.config;
		this.args = args;
		this.steps = undefined;

		this.AlarmHiddenWizard = new AlarmHiddenWizard(this);
		this.AlarmHiddenMute = new AlarmHiddenMute(this);
	}

	getView() {

		if (this.moduleAction.id === this.config.moduleAction.filterAlarmWizard) {

			return super.getViewCheckModuleAction();
		}
	}


	loadData() {

		let self = this;
		
		if (self.moduleAction.id === self.config.moduleAction.mute) {

			self.AlarmHiddenMute.separateAlarms(true, self.args.selectedPks);

		} else if (self.moduleAction.id === self.config.moduleAction.deleteAlarmFilter) {

			self.AlarmHiddenMute.separateAlarms(false, self.args.selectedPks);

		} else if (self.moduleAction.id === self.config.moduleAction.filterAlarmWizard) {

			self.steps = webix.copy(self.config.steps.fromNoMap);

			let container = $$(self.args.containerId);

			if (container) {

				util.ui.setWindowTitle(container, i18n.t("LBL_CREATE_ALARMS_FILTER"));
			}

			drawComponents();
		}


		function drawComponents() {

			$$("loading-" + self.uid).show();

			let view = {
				height: 550,
				width: 855,
				css: "custom_window",
				position: "center",
				cols: [
					{
						rows: [
							{ height: 6 },
							{
								cols: [
									{ width: 6 },
									{
										id: self.uid,
										rows: [self.AlarmHiddenWizard.drawMain()],
										height: 550,
										width: 845
									},
									{ width: 6 }
								]
							},
							{ height: 30 }
						]
					}
				]
			};

			$$("loading-" + self.uid).hide();

			webix.ui(view, $$(self.uid));
		}

	}

}