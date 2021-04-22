import GenericModule from "../modules/genericModule.js";
import CalendarDialogBox from "../component/calendarDialogBox.js";
import TreeView from "../component/treeview.js";
import DataTable from "../component/datatable.js";
import { util } from "../lib/util.js";

export default class AlarmMutedModule extends GenericModule {

	constructor(module, moduleAction, args) {

		super(module, moduleAction, args);

		this.uid = webix.uid();
		//this.userData = webix.storage.session.get(SESSION_STORAGE_USER_DATA);
		this.requestAlarm = true;
		this.stompClient = undefined;
		this.tableAlarmsModal = undefined;
		this.requestReport = true;
		this.config = module.config;

		this.alarmMutedElementSelect = undefined;

    }

	getView() {

		let view = undefined;

		view = {
			css: "custom_window",
			cols: [
				{
					rows: [
						{ height: 6 },
						{
							cols: [
								{ width: 6 },
								{
									id: this.uid,
									rows: [],
									hidden: false
								},
								{
									id: "loading-" + this.uid,
									rows: [this.getViewDrawNoData()],
									hidden: true
								},
								{ width: 6 }
							]
						},
						{ height: 6 }
					]
				}
			]
        };
    
    }
    
	loadData() {

		let self = this;

		if (this.moduleAction.id === this.config.moduleAction.mute) {

			mute(true);
		}
		else if (this.moduleAction.id === this.config.moduleAction.unmute) {

			mute(false);
		}
		else if (this.moduleAction.id === this.config.moduleAction.createMuteAlarm) {

			const createMutedAction = () => {

				if (self.tableAlarmsModal !== undefined && self.tableAlarmsModal.getSelectedIds().length > 0 && this.alarmMutedElementSelect !== undefined )
				{

					(new CalendarDialogBox(i18n.t("LBL_DIALOG_MUTE_ALARMS"), i18n.t("LBL_DIALOG_UNMUTE_ALARMS"), false, 
					
						function(date) {
							
							return (Math.floor(date) < Math.floor(new Date()));
						},
						function(dates) {
							
							let mutedAlarms = [];
									
							self.tableAlarmsModal.getSelectedIds().forEach(alarm => {
								
								mutedAlarms.push({
									
									alarmConfigId: alarm,
									mutedUntil: dates.from.getTime(),
									elementId: self.alarmMutedElementSelect.id,
									enable: true
								});
							});
							
							if (Array.isArray(mutedAlarms) && mutedAlarms.length > 0) {
								
								let body = {
								
									alarmMuteds: mutedAlarms							
								};
								
								webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.mute, body).then(function(data) {
									console.log(data);
								});									
							} 
			
						}
					)).show();

				}
			}

			let view = {
				css: "custom_window",
				cols: [
					{
						rows: [
							{ height: 6 },
							{
								cols: [
									{ width: 6 },
									{
										id: "viewTree" + this.uid,
										rows: [],
										hidden: false
									},
									{
										id: "viewTable" + this.uid,
										rows: [],
										hidden: false
									},
									{
										id: "view" + "loading-" + this.uid,
										rows: [this.getViewDrawNoData()],
										hidden: true
									},
									{ width: 6 }
								]
							},
							{ height: 6 },
							{
								cols:[
									{},
									{
										view: "button", label: i18n.t("LBL_CREATE_BUTTON"),
										tooltip: i18n.t("LBL_CREATE_BUTTON_DESCRIPTION"), css: "dialog-download webix_button", width: 150, // 
										on: {
		
											onItemClick() {

												 createMutedAction()
											}
										}
									},
									{
										width: 22	
									}
								]
							}
						]
					}
				]
			};

			let h = document.documentElement.clientHeight - 200;
			let w = document.documentElement.clientWidth - 675;
			util.ui.loadWindow(
				i18n.t("LBL_CREATE_MUTE_ALARMS"),
				view,
				h,
				w
			);
			
			webix.ajax().get(appConfig.restUrl + util.fillTemplate("trees/${id}", { id: self.config.treeElementType })).then(function(dat) {

				if (dat != null) {

					let metadata = dat.json().metadata;
					let data = dat.json().data;
					
					let events = {};
				
					events[TreeView.EventTypes.ON_SELECT_ITEM] = function(items) {

						self.tableAlarmsModal.clear();

						let element = window.em.getByField(EntityType.ELEMENT, undefined, items);
						self.alarmMutedElementSelect = element;

						let elementTypeId = element.elementType.id;
						let alarmConfig = window.em.getAll(EntityType.ALARM_CONFIG);
						let aconfitArray = alarmConfig.find(e => e.elementType.id === elementTypeId);

						if (Array.isArray(aconfitArray) && aconfitArray.length) {
							let alarmsConfig = aconfitArray;
							for (let i = 0; i < alarmsConfig.length; i++) {
º
								let row = {};

								row.id = alarmsConfig[i].id;
								row.alarmConfigAlias = alarmsConfig[i].alias;
								row.alarmTypeLabelAlias = i18n.t(alarmsConfig[i].alarmType.labelAlias);
								row.alarmLevelLabelAlias = i18n.t(alarmsConfig[i].alarmLevel.labelAlias);

								self.tableAlarmsModal.add(row);
							}
						} else {

							let row = {};

							row.id = aconfitArray.id;
							row.alarmConfigAlias = aconfitArray.alias;
							row.alarmTypeLabelAlias = i18n.t(aconfitArray.alarmType.labelAlias);
							row.alarmLevelLabelAlias = i18n.t(aconfitArray.alarmLevel.labelAlias);

							self.tableAlarmsModal.add(row);
						}
						
					};
		
					let treeView = new TreeView(metadata, data, events);
		
					let viewMain = {
						id: "viewTree" + self.uid,
						cols: [
							{
								width: 400,
								rows: [
									treeView.getView()		
								]
							}
						]
					};

					webix.ui(viewMain, $$("viewTree" + self.uid));
					//self.tableAlarmsModal = createDatatable();
					webix.ajax().get(appConfig.restUrl + "grids/" + self.config.gridAlarmsConfig + "/" + "id").then((dat)=>{

						if (dat != null) {

							let metadata = dat.json().metadata;

							self.tableAlarmsModal = new DataTable(null, null, metadata, []);

							let viewMaintable = {
								id: "viewTable" + self.uid,
								cols: [
									{
										width: 800,
										rows: self.tableAlarmsModal.getView()
										
									}
								]
							};

							webix.ui(viewMaintable, $$("viewTable" + self.uid));
							
							$$("view" + "loading-" + self.uid).hide();
							$$("view" + self.uid).show();

						}

					});

				}
			});

		}

		function mute(action) {

			let title;
			let text;
			
			if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {
				console.log("01");
				title = action ? i18n.t("LBL_DIALOG_MUTE_ALARMS") : i18n.t("LBL_DIALOG_UNMUTE_ALARMS");
				text = action ? i18n.t("LBL_ARE_YOU_SURE_MUTE") : i18n.t("LBL_ARE_YOU_SURE_UNMUTE");
				
				if(action === true){
					console.log("0true");
					(new CalendarDialogBox(title, text, false, 
					
						function(date) {
							
							return (Math.floor(date) < Math.floor(new Date()));
						},
						function(dates) {
							
							webix.ajax().headers(HEADER_POST).post(appConfig.restUrl + self.config.restEndpoints.alarmsArray, JSON.stringify(self.args.selectedPks)).then(function(data) {
							
								let responseAlarms = data.json();
								
								if (Array.isArray(responseAlarms) && responseAlarms.length > 0) {
									
									let mutedAlarms = [];
									
									responseAlarms.forEach(alarm => {
										
										mutedAlarms.push({
											
											alarmConfigId: alarm.alarmConfig.id,
											mutedUntil: dates.from.getTime(),
											elementId: alarm.targetElement.id,
											enable: true
										});
									});
									
									if (Array.isArray(mutedAlarms) && mutedAlarms.length > 0) {
										
										let body = {
										
											alarmMuteds: mutedAlarms							
										};
										console.log("pwpwpwpwpw");
										console.log(appConfig.restUrl + self.config.actionEndpoints.mute);
										console.log(body);
										webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.mute, body).then(function(data) {
											console.log(data);
										});									
									} 
								}
							});					
						}
					)).show();

				}

				if(action === false){
					
					webix.confirm({
						title: i18n.t("LBL_ALARM_CONFIRM_CONFIRM"),
						ok: i18n.t("LBL_YES"),
						cancel: i18n.t("LBL_NO"),
						text: i18n.t("LBL_ALARM_CONFIRM_CONFIRM_DESCRIPTION"),
						width: 600
					}).then(function() {

						if (Array.isArray(self.args.selectedPks) && self.args.selectedPks.length > 0) {

							let mutedAlarms = [];
							
							self.args.selectedPks.forEach(e => {

								webix.ajax().get(appConfig.restUrl + util.fillTemplate(self.config.restEndpoints.alarmMuted, { id: e })).then(function(dat) {

									if (dat != null) {
										
										let responseAlarms = dat.json();
									
										mutedAlarms.push({
											
											alarmConfigId: responseAlarms.alarmConfig.id,
											mutedUntil: responseAlarms.mutedUntil,
											elementId: responseAlarms.element.id,
											enable: false

										});

										if (Array.isArray(mutedAlarms) && mutedAlarms.length > 0) {
															
											let body = {
											
												alarmMuteds: mutedAlarms							
											};
											
											webix.ajax().headers(CONTENT_TYPE_JSON).post(appConfig.restUrl + self.config.actionEndpoints.unmute, body).then(function(data) {
												console.log(data);
											});									
										} 
	
									}
	
								});
	
							});
	
						}
						
					});

				}
				
			}
		}

	}

	getViewDrawNoData = () => {

		return {
			view: "template",
			template: function() {

				let icon = "<img src='img/loading.svg' align='center'>";

				return "<div class='empty-app-inner'>" + icon + "<span class='empty-app-inner-text'>" + i18n.t("LBL_LOADING_DATA") + "</span></div>";
			}
		}
	}
    
}