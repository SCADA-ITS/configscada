export default class AlarmControlPanel {

	constructor() {

		this.mainView = this.createView();
	}

	show() {

		if (!this.hidden()) {

			return;
		}

		this.mainView.show();
	}

	hide() {

		this.mainView.hide();
	}

	hidden() {

		return this.mainView.config.hidden;
	}

	createView() {

		// for using the object inside callbacks
		let self = this;

		return webix.ui({
			view: "popup",
			id: "alarmControlPanel",
			move: true,
			css: "alarm-control-panel-main",
			hidden: true,
			height: document.documentElement.clientHeight - 147,
			width: 500,
			body: {
				rows: [
					{
						
					}		
				]	
			}, 
			position: function(state) {
				state.left = document.documentElement.clientWidth - 520;
				state.top = 96;
			},
			on: {
				onBeforeShow: function() {
					
					this.config.height = document.documentElement.clientHeight - 147; 
					this.adjust(); 
				}
			}
		});
	}
}
