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
		let panelWidth = 400;
		
		return webix.ui({
			view: "popup",
			id: "alarmControlPanel",
			move: true,
			css: "control-panel-main",
			hidden: true,
			height: document.documentElement.clientHeight - 147,
			width: panelWidth,
			body: {
				rows: [
					{
						
					}		
				]	
			}, 
			position: function(state) {
				state.left = document.documentElement.clientWidth - panelWidth - 20;
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
