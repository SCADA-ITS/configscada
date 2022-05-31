import GenericModule from "./genericModule.js";
import AlarmFiltered from "./alarmFiltered/alarmFiltered.js";
import AlarmFilteredWizard from "./alarmFiltered/alarmFilteredWizard.js";
import AlarmFilteredView from "./alarmFiltered/alarmFilteredView.js";

export default class AlarmFilteredModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();

		this.config = module.config;
		this.args = args;
		this.steps = undefined;

		this.alarmFilteredWizard = new AlarmFilteredWizard(this);
		this.alarmFiltered = new AlarmFiltered(this);
		this.alarmFilteredView = new AlarmFilteredView(this);
	}

	getView() {

		if (this.moduleAction.id === this.config.moduleAction.filterAlarmWizard) {

			return;
		}
	}


	loadData() {

		let self = this;

		if (self.moduleAction.id === self.config.moduleAction.mute) {

			self.alarmFiltered.separateAlarms(true, self.args.selectedPks, false);
			
		} else if (self.moduleAction.id === self.config.moduleAction.disableAlarm) {

			self.alarmFiltered.separateAlarms(true, self.args.selectedPks, true);

		} else if (self.moduleAction.id === self.config.moduleAction.deleteAlarmFilter) {

			self.alarmFiltered.separateAlarms(false, self.args.selectedPks);

		} else if (self.moduleAction.id === self.config.moduleAction.filterAlarmWizard) {

			self.steps = webix.copy(self.config.steps.fromNoMap);

			self.alarmFilteredWizard.drawComponents();

		} else if (self.moduleAction.id === self.config.moduleAction.view) {

			self.alarmFilteredView.show();
		}

	}

}