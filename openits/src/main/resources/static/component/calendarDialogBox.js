export default class CalendarDialogBox {
	
	constructor(title, text, multidate, onBlockDates, onAccept) {

		this.title = title;
		this.text = text;
		this.multidate = multidate; 
		this.onBlockDates = onBlockDates;
		this.onAccept = onAccept;
	}
	
	getView() {
		
		let self = this;
		
		var calendar1 = {
			view: "calendar",
			id: "calendar1-" + self.uid,
			date: new Date(),
			timepicker: true,
			blockDates: function(date) {
				
				return self.onBlockDates(date);
			}
		};
		
		var calendar2 = {
		    view:"calendar",
			id: "calendar2-" + self.uid,
			date: new Date(),
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
		
		return viewCalendars;
	}

	show() {
		
		let self = this;
				
		webix.ui({
			view: "window",
			modal: true,
			height: 450,
			width: this.multidate ? 700 : 500,
			css: "dialog-input-custom",
			head: this.title,
			position: "center",
			body: {
			rows: [
					{ template: self.text, borderless: true },
					self.getView(),
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
									
									let dates = self.getSelectedDates();
									
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
	
	getSelectedDates() {
		
		let self = this;
		
		let dates = {};
									
		let dateFrom = $$("calendar1-" + self.uid).getSelectedDate();
		
		if (!dateFrom) {
			
			dateFrom = $$("calendar1-" + self.uid).getVisibleDate();
		}
		
		dateFrom.setSeconds(0, 0);
		
		dates.from = dateFrom;
		
		if (self.multidate && $$("calendar2-" + self.uid)) {
			
			let dateTo = $$("calendar2-" + self.uid).getSelectedDate();
			
			if (!dateTo) {
				
				dateTo = $$("calendar2-" + self.uid).getVisibleDate();
			}	
			
			dateTo.setSeconds(0, 0);
		
			dates.to = dateTo;	
		}
		
		return dates;
	}
}

