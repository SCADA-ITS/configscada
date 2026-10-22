export const NotificationType = {
	ALARM: 1,
	EVENT: 2,
	OTHER: 3
};

export default class Notification {

	constructor(notificationType, date, title, message, sound, callbacks) {

		this.notificationType = notificationType;
		this.date = date;
		this.title = title;
		this.message = message;
		this.sound = sound;
		this.callbacks = callbacks;
		
		this.audio = undefined;
		this.uid = webix.uid();
	}

	getView() {
		
		self = this;

		let notificationId = "notification-" + this.uid;

		let head = {
			view: "toolbar",
			//css: "notification-alarm-window-head",
			//height: 35,
			elements: [
				{

					view: "label",
					template: this.title
				}

			],
			borderless: 1
		};

		if (this.notificationType === NotificationType.ALARM) {

			head.css = "notification-alarm-window-head";
			head.elements.push({
				view: "icon",
				icon: "mdi mdi-window-minimize",
				on: {
					onItemClick: function() {
						$$(notificationId).hide();
						self.stopAudio();
					}
				}
			});
		}
		else if (this.notificationType === NotificationType.EVENT) {
			
			head.css = "notification-event-window-head";
			head.elements.push({
				view: "icon",
				icon: "mdi mdi-window-minimize",
				on: {
					onItemClick: function() {
						$$(notificationId).hide();
						self.stopAudio();
					}
				}
			});
		}
		else if (this.notificationType === NotificationType.OTHER) {
			
			head.css = "notification-other-window-head";
			head.elements.push({
				view: "icon",
				icon: "mdi mdi-close",
				on: {
					onItemClick: function() {
						$$(notificationId).close();
						self.stopAudio();
					}
				}
			});
		}

		let win = {
			view: "window",
			id: notificationId,
			move: true,
			toFront: true,
			head: head,
			height: 350,
			width: 800,
			body: {
				rows: [
					{
						view: "label",
						template: this.message
					}
				]
			},
			position: function(state) {
				state.left = (document.documentElement.clientWidth / 2) - 400;
				state.top = (document.documentElement.clientHeight / 2) - 360;
			},
			on: {
				onBeforeShow: function() {
			
					self.playAudio();		
				}
			}
		};
		
		if (this.notificationType === NotificationType.ALARM) {

			win.css = "notification-alarm-window";
		}
		else if (this.notificationType === NotificationType.EVENT) {
			
			win.css = "notification-event-window";
		}
		else if (this.notificationType === NotificationType.OTHER) {
			
			win.css = "notification-other-window";
		}
		
		return win;
	}
	
	playAudio() {
		
		if (this.sound && !this.audio) {
			
			this.audio = new Audio('./sound/' + this.sound);
			this.audio.loop = false;
	  		this.audio.play();	
		}
	}
	
	stopAudio() {
		
		if (this.audio) {
			
			this.audio.pause();
    		this.audio.currentTime = 0;
			this.audio = undefined;
		}
	}
}
