import GenericModule from "./genericModule.js";
import AlarmFilteredMute from "./alarmFiltered/alarmFilteredMute.js";
import AlarmFilteredWizard from "./alarmFiltered/alarmFilteredWizard.js";
import AlarmFilteredView from "./alarmFiltered/alarmFilteredView.js";

export default class AlarmFilteredModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();

		this.config = module.config;
		this.args = args;
		this.steps = undefined;

		this.AlarmFilteredWizard = new AlarmFilteredWizard(this);
		this.AlarmFilteredMute = new AlarmFilteredMute(this);
		this.AlarmFilteredView = new AlarmFilteredView(this);
	}

	getView() {

		if (this.moduleAction.id === this.config.moduleAction.filterAlarmWizard) {

			return;
		}
	}


	loadData() {

		let self = this;

		if (self.moduleAction.id === self.config.moduleAction.mute) {

			self.AlarmFilteredMute.separateAlarms(true, self.args.selectedPks);

		} else if (self.moduleAction.id === self.config.moduleAction.deleteAlarmFilter) {

			self.AlarmFilteredMute.separateAlarms(false, self.args.selectedPks);

		} else if (self.moduleAction.id === self.config.moduleAction.filterAlarmWizard) {

			self.steps = webix.copy(self.config.steps.fromNoMap);

			self.AlarmFilteredWizard.drawComponents();

		} else if (self.moduleAction.id === self.config.moduleAction.view){
			
			self.AlarmFilteredView.show();
		}

	}

}