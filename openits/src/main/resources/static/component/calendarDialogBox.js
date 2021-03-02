export default class CalendarDialogBox {
	
	constructor(title, text, multidate, onBlockDates, onAccept) {

		this.title = title;
		this.text = text;
		this.multidate = multidate; 
		this.onBlockDates = onBlockDates;
		this.onAccept = onAccept;
	}

	show() {
		
		let self = this;
		
		var calendar1 = {
			view: "calendar",
			id: "calendar1-" + self.uid,
			timepicker: true,
			blockDates: function(date) {
				
				return self.onBlockDates(date);
			}
		};
		
		var calendar2 = {
		    view:"calendar",
			id: "calendar2-" + self.uid,
		    timepicker:true,
			blockDates: function(date) {
				
				return self.onBlockDates(date);
			} 
		};
		
		let viewCalendars = { cols: [] };
		
		viewCalendars.cols.push({});
		viewCalendars.cols.push(calendar1);
		
		if (self.multidate) {
		
			viewCalendars.cols.push({ width: 20 });
			viewCalendars.cols.push(calendar2);	
		}
		viewCalendars.cols.push({});
		
		webix.ui({
			view: "window",
			modal: true,
			height: 450,
			width: self.multidate ? 700 : 500,
			css: "dialog-input-custom",
			head: self.title,
			position: "center",
			body: {
				rows: [
					{ template: self.text, borderless: true },
					viewCalendars,
					{},
					{
						cols: [
							{},
							{
								view: "button", 
								value: i18n.t("LBL_NO"),
								width: 100, 
								click: function() {
									
									const window = this.getTopParentView();
									window.close();
								}
							},
							{ width: 5 },
							{
								view: "button", 
								value: i18n.t("LBL_YES"),
								width: 100, 
								click: function() {
									
									const window = this.getTopParentView();
									
									let dates = {};
									
									dates.from = $$("calendar1-" + self.uid).getSelectedDate();
									
									if (self.multidate) {
									
										dates.to = $$("calendar2-" + self.uid).getSelectedDate();	
									}
									
									self.onAccept(dates);
									
									window.close();
								}
							},
							{}
						]
					}
				],
				type: "form"
			}
		}).show();
	}
}

