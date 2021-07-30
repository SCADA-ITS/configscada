export default class SplashScreen {

	constructor(callback) {

		this.mainView = this.createView(callback);
	}
	
	getView() {
		
		return this.mainView; 
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

	createView(callback) {

		let splashHeight = 500;
		let splashWidth = 800;
		
		let splashFrame = {
			width: splashWidth,
			height: splashHeight,
			css: "splash-screen",
			rows: [
				{
					view: "template",
					css: "splash-screen-logo-inner",
					template: function(data) {
						
						let logo = "<img src='img/open_its_full.png' align='center'>";
		
						return `<div class="splash-screen-logo-inner">${logo}</div>`;
					}		
				},
				{
					view: "template",
					css: "splash-screen-inner",
					template: function(data) {
				
						let icon = "<img src='img/loading.svg' align='center'>";
		
						return "<div class='splash-screen-inner'>" + icon + "<span class='splash-screen-inner-text'>" + i18n.t("LBL_PLEASE_WAIT") + "</span></div>";
					},
					on: {
						onAfterRender: function() {
							
							//callback();
						}
					}
				}
			]
		};
		
		let view = {
			id: "splashScreen",
			type: "space",
			css: "splash-screen-wallpaper",
			rows: [
				{
					height: (document.documentElement.clientHeight - splashHeight) / 2
				},
				{
					height: 515,
					cols: [
						{
							width: (document.documentElement.clientWidth - splashWidth) / 2
						},
						splashFrame,
						{
							width: (document.documentElement.clientWidth - splashHeight) / 2
						},	
					]	
				}/*,
				{
					height: (document.documentElement.clientHeight - splashHeight) / 2
				}*/
			]
		};

		return webix.ui(view);
	}
}
