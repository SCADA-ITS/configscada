import Alarms from "../classes/Alarms.js";
import ElementWindow from "../classes/ElementWindow.js";
import {alarmsCfg} from "../data/alarmsCfg.js";
import {RULE} from "../data/GLOBAL_PROPERTIES.js";
import { util } from "../lib/util.js"

const SYNOPTIC_TUNNEL_RIOVERDE_ID = 900;

var graphAlarm = webix.proxy(
	"GraphQL", `
	query { 
  getAlarms {
	  id
	  recognized
	  recognizedComments
	  activation
	  activationComments
	  deactivation
	  deactivationComments
      alarmConfig {
		id 
		alias 
		alarmLevel {
			labelAlias
		}
	}
	sourceElement{id alias}
	targetElement{id alias} 
  }
}`);



//let table = new Alarms(graphAlarm, null);

export function alarms(){
	let widgetId=webix.uid();

	var sendRecognizeAlarms = `mutation  sendRecognizeAlarms ($alarmIds: [String!], $recognizedComments: String ){
		sendRecognizeAlarms(alarmIds: $alarmIds, recognizedComments: $recognizedComments)
	}`;

	var sendDeactivationAlarms = `mutation  sendDeactivationAlarms ($alarmIds: [String!], $deactivationComments: String ){
		sendDeactivationAlarms(alarmIds: $alarmIds, deactivationComments: $deactivationComments)
	}`;

	function getDate(timestamp) {
		//console.log(timestamp);
		//console.log(new Date(timestamp));
		//console.log(webix.i18n.fullDateFormatStr(new Date(timestamp)));
		return timestamp !== null ? webix.i18n.fullDateFormatStr(new Date(timestamp)) : '-';
		//return timestamp !== null ? new Date(timestamp) : '-';
	}
	webix.i18n.fullDateFormat = "%Y-%m-%d %H:%i";
	webix.i18n.setLocale("es");
	let genericTablei18n = {
		rows: [{
			"id": widgetId,
			"view": "datatable",
			"autoConfig": true,
			"select": true,
			"multiselect": true,
			"scroll": "y",
			"columns": [
				{ "id": "id", "header": $.i18n("alarms.d.id"), hidden: true, width: 200 },
				{ "id": "check", "header": { content: "masterCheckbox" }, checkValue: 'on', uncheckValue: 'off', "template": "{common.checkbox()}", width: 40 },
				{ "id": "activation", sort:"date", "header": $.i18n("alarms.d.activation"), 
				//"template": function (obj) { return getDate(obj.activation) }, 
				"template": function (obj) { 
					if(obj.activation !== null){
						return webix.i18n.fullDateFormatStr(obj.activation);
					}else{
						return "-";
					}
				},
				//format: webix.i18n.dateFormatStr,
				//map:"(date)#activation#",
				width: 180 },
				{ "id": "recognized", sort:"date", "header": $.i18n("alarms.d.recognized"),
				// "template": function (obj) { return getDate(obj.recognized) }, 
				"template": function (obj) { 
					if(obj.recognized !== null){
						return webix.i18n.fullDateFormatStr(obj.recognized);
					}else{
						return "-";
					}
				},			 
				width: 180 },
				{ "id": "deactivation", sort:"date", "header": $.i18n("alarms.d.deactivation"), 
				// "template": function (obj) { return getDate(obj.deactivation) }, 
				"template": function (obj) { 
					if(obj.deactivation !== null){
						return webix.i18n.fullDateFormatStr(obj.deactivation);
					}else{
						return "-";
					}
				},
				width: 180 },
				{ "id": "alarmConfig", sort:"text", "header": $.i18n("alarms.d.alarmconfig"), "template": function (obj) { return $.i18n(obj.alarmConfig.id); }, width: 270, fillspace:true },
				{ "id": "alarmLevel", sort:"int", "header": $.i18n("alarms.d.level"), "template": function (obj) { return alarmsCfg[obj.alarmConfig.id].alarmLevel; }, width: 70 },
				{ "id": "sourceElementId", "template": function (obj) { return obj.sourceElement.id; }, hidden:true },
				{ "id": "sourceElementAlias", sort:"text", "header": $.i18n("alarms.d.source"), "template": function (obj) { return obj.sourceElement.alias; }, width: 100 },
				{ "id": "sourceElement", "header":"", "template": "<button class='open_elementForm' ><span class='webix_icon wxi-search'></span></button>", width: 50} , 
				{ "id": "targetElementId", "template": function (obj) { return obj.targetElement.id; }, hidden:true },
				{ "id": "targetElementAlias", sort:"text", "header": $.i18n("alarms.d.target"), "template": function (obj) { return obj.targetElement.alias; }, width: 100 },
				{ "id": "targetElement", "header":"","template": "<button class='open_elementForm' ><span class='webix_icon wxi-search'></span></button>", width: 50},
				/*{ "id": "recognize", "header": "", "template": "<button class='action_recognize' ><span class='mdi mdi-eye-check-outline'></span></button>" //+ 
				"<button class='action_deactivate' ><span class='mdi mdi-delete-outline'></span></button>" + 
				"<button class='action_activate' ><span class='mdi mdi-bell-plus-outline'></span></button>"
				, width: 120 }*/
			],
			onClick: {
				open_elementForm:function (ev, id, html) {
					openElementForm(this.getItem(id)[id.column].id)
				},
				action_recognize: function (ev, id, html) {
					var data = $$(widgetId).serialize();
					var alarmId = id.row;
					for (let index = 0; index < data.length; index++) {
						if (data[index].id == alarmId) {
							if (data[index].recognized != null) {
								webix.alert($.i18n("alarms.popup.messageAlarmRecognized"));
							} else {
								webix.ui({
									view: "window",
									id: "popup_alarm" + alarmId,
									head: alarmId + " " + data[index].alarmConfig.alias,
									width: 330,
									height: 300,
									modal: true,
									position: "top",
									body: {
										view: "form",
										elements: [
											{
												view: "textarea",
												id: "Comment" + alarmId,
												label: "Comment",
												labelAlign: "right",
												height: 150,
												value: "type here"
											},
											{
												cols: [
													{ view: "button", css:"webix_danger", type:"icon", icon:"mdi mdi-cancel", label: "Cancel", value: "Cancel", click: function () { $$("popup_alarm" + alarmId).close() } },
													{
														view: "button", css:"webix_primary", type:"icon", icon:"mdi mdi-content-save", label:$.i18n("alarms.d.recognized"), value: $.i18n("alarms.d.recognized"), click: function () {
															let comment = $$("Comment" + alarmId).getValue();
															let graphql = webix.proxy("GraphQL", sendRecognizeAlarms);
															graphql.save({
																"alarmIds": [alarmId],
																"recognizedComments": comment
															}).then((resp) => {
																if (resp === true) {
																	webix.message({
																		text: "Successful operation",
																		type: "success",
																		expire: 10000,
																		id: "successful_message"
																	});
																	$$("popup_alarm" + alarmId).close();
																	$("#recognize_button").prop("disabled", true);
																} else {
																	webix.message({
																		text: "Error",
																		type: "error ",
																		expire: 10000,
																		id: "error_message"
																	});
																}
															})
														}
													}
												]
											}
										]
									}
								}).show();
							}
						}
					}
				},
				action_deactivate: function (ev, id, html) {
					var data = $$(widgetId).serialize();
					var alarmId = id.row;
					for (let index = 0; index < data.length; index++) {
						if (data[index].id == alarmId) {
							if (data[index].deactivation != null) {
								webix.alert("Alarma ya desactivada");
							} else {
								webix.ui({
									view: "window",
									id: "popup_alarm" + alarmId,
									head: "Deactivate " + alarmId,
									width: 330,
									height: 300,
									modal: true,
									body: {
										view: "form",
										elements: [
											{
												view: "textarea",
												id: "Comment" + alarmId,
												label: "Comment",
												labelAlign: "right",
												height: 150,
												value: "type here"
											},
											{
												cols: [
													{
														view: "button", value: "Accept", css: "webix_primary", click: function () {
															let comment = $$("Comment" + alarmId).getValue();
															let graphql = webix.proxy("GraphQL", sendDeactivationAlarms);
															graphql.save({
																"alarmIds": [alarmId],
																"recognizedComments": comment

															}).then((resp) => {
																if (resp === true) {
																	webix.message({
																		text: "Successful operation",
																		type: "success",
																		expire: 10000,
																		id: "successful_message"
																	});
																	$$("popup_alarm" + alarmId).close();
																} else {
																	webix.message({
																		text: "Error",
																		type: "error ",
																		expire: 10000,
																		id: "error_message"
																	});
																}
															})
														}
													},
													{ view: "button", value: "Cancel", click: function () { $$("popup_alarm" + alarmId).close() } }
												]
											}
										]
									}
								}).show();
							}
						}
					}
				}
			},
			scheme: {
				$change: function (item) { //Item es cada fila de la tabla
					if (item.deactivation != null) {
						item.$css = "alarm_deactivated";
					} else {
						item.$css = "alarm_activated";
					}
				}
			},
			ready: function () {
				if(typeof item!=='undefined'){
					if (item.recognized != null) {
						$('.action_recognize').prop("visibility", "hidden");
					} else if (item.deactivation != null) {
						$('.action_deactivate').prop("visibility", "hidden");
					}
				}
			}
		},
		{
			view: "toolbar",
			id: "toolbar_alarms",
			heigth: 50,
			align: "right",
			rows: [{
				cols: [
					{ gravity: 4 },
					{
						view: "button", id: "recognize_bottom", disabled: !checkUserPermission(RULE.ALARM_RECOGNIZE)  ,align: "right", autowidth: true, type: "icon", icon: "mdi mdi-eye-check-outline", label: $.i18n("alarms.d.button.recognize"),
						click: function () {
							var alarmas = [];
							var alarmasId = [];
							var data = $$(widgetId).serialize();

							for (let index = 0; index < data.length; index++) {
								if (data[index].check == "on") {
									alarmas.push(data[index]);
								}
							}

							if (data.length == 0) {
								webix.alert($.i18n("alarms.popup.noAlamr"));
							} else if (alarmas.length == 0) {
								webix.alert($.i18n("alarms.popup.selectAlarm"));
							}

							for (let index = 0; index < alarmas.length; index++) {
								if (alarmas[index].recognized != null) {
									webix.message("" + alarmas[index].id + " " + $.i18n("alarms.popup.selectAlarm"))
								} else if (alarmas[index].recognized == null) {
									alarmasId.push(alarmas[index].id);
								}

							}

							if (alarmasId.length > 0) {
								webix.ui({
									view: "window",
									id: "popup_alarm_array_recognice",
									head: "Recognize alarms",
									width: 330,
									height: 300,
									modal: true,
									position: "top",
									body: {
										view: "form",
										elements: [
											{
												view: "textarea",
												id: "Comment_array_recognize",
												label: "Comment",
												labelAlign: "right",
												height: 150,
												value: "type here"
											},
											{
												cols: [
													{
														view: "button", css:"webix_danger", type:"icon", icon:"mdi mdi-cancel", label:"Cancel", value: "Cancel", click: function () {
															alarmas = []
															alarmasId = []
															$$("popup_alarm_array_recognice").close();
														}
													},
													{
														view: "button", css:"webix_primary", type:"icon", icon:"mdi mdi-content-save", label: $.i18n("alarms.d.recognized"), value: $.i18n("alarms.d.recognized"), click: function () {
															let comment = $$("Comment_array_recognize").getValue();
															let graphql = webix.proxy("GraphQL", sendRecognizeAlarms);
															graphql.save({
																"alarmIds": alarmasId,
																"recognizedComments": comment
															}).then((resp) => {
																if (resp === true) {
																	webix.message({
																		text: "Successful operation",
																		type: "success",
																		expire: 10000,
																		id: "successful_message"
																	});
																	alarmas = []
																	alarmasId = []
																	$$("popup_alarm_array_recognice").close();
																} else {
																	webix.message({
																		text: "Error",
																		type: "error ",
																		expire: 10000,
																		id: "error_message"
																	});
																	alarmas = []
																	alarmasId = []
																	$$("popup_alarm_array_recognice").close();
																}
															})
														}
													}
												]
											}
										]
									}
								}).show();
							}
						}
					},
					{
						view: "button", id: "deactivate_bottom", hidden: true, align: "right", autowidth: true, type: "icon", icon: "mdi mdi-delete-outline", label: "Deactivate",
						click: function () {
							var alarmas = [];
							var alarmasId = [];
							var data = $$(widgetId).serialize();
							for (let index = 0; index < data.length; index++) {
								if (data[index].check == "on") {
									alarmas.push(data[index])
								}
							}

							if (data.length == 0) {
								webix.alert($.i18n("alarms.popup.noAlamr"));
							} else if (alarmas.length == 0) {
								webix.alert($.i18n("alarms.popup.selectAlarm"));
							}

							for (let index = 0; index < alarmas.length; index++) {
								if (alarmas[index].deactivation != null) {
									webix.message("" + alarmas[index].id  + " " + $.i18n("alarms.popup.selectAlarm"))
								}
								else if (alarmas[index].deactivation == null) {
									alarmasId.push(alarmas[index].id)
								}
							}

							if (alarmasId.length > 0) {
								webix.ui({
									view: "window",
									id: "popup_alarm_array_deactivation",
									head: "Deactivation Alarms",
									width: 330,
									height: 300,
									modal: true,
									body: {
										view: "form",
										elements: [
											{
												view: "textarea",
												id: "Comment_array_deactivation",
												label: "Comment",
												labelAlign: "right",
												height: 150,
												value: "type here"
											},
											{
												cols: [
													{
														view: "button", value: "Deactivate", css: "webix_primary", click: function () {
															let comment = $$("Comment_array_deactivation").getValue();
															let graphql = webix.proxy("GraphQL", sendDeactivationAlarms);
															graphql.save({
																"alarmIds": alarmasId,
																"recognizedComments": comment
															}).then((resp) => {
																if (resp === true) {
																	webix.message({
																		text: "Successful operation",
																		type: "success",
																		expire: 10000,
																		id: "successful_message"
																	});
																	alarmas = []
																	alarmasId = []
																	$$("popup_alarm_array_deactivation").close();
																} else {
																	webix.message({
																		text: "Error: " + resp,
																		type: "error ",
																		expire: 10000,
																		id: "error_message"
																	});
																	alarmas = []
																	alarmasId = []
																}
															})
														}
													},
													{
														view: "button", value: "Cancel", click: function () {
															alarmas = []
															alarmasId = []
															$$("popup_alarm_array_deactivation").close();
														}
													}
												]
											}
										]
									}
								}).show();
							}
						}
					},
					{
						view: "button", id: "activation_bottom", hidden: true, align: "right", autowidth: true, type: "icon", icon: "mdi mdi-bell-plus-outline", label: "Activation",
						click: function () { }
					}
				]
			}]
		}]
	};

	let genericTablei18n_recon = {
		rows: [{
			"id": widgetId+"_recon",
			"view": "datatable",
			"autoConfig": true,
			"select": true,
			"multiselect": true,
			"scroll": "y",
			"columns": [
				{ "id": "id", "header": $.i18n("alarms.d.id"), hidden: true, "template": function (obj_recon) { return obj_recon.id }, width: 200 },
				{ "id": "check_recon", "header": { content: "masterCheckbox" }, checkValue: 'on', uncheckValue: 'off', "template": "{common.checkbox()}", width: 40 },
				{ "id": "activation_recon", sort:"date", "header": $.i18n("alarms.d.activation"), 
				"template": function (obj_recon) { 
					if(obj_recon.activation_recon !== null){
						return webix.i18n.fullDateFormatStr(obj_recon.activation_recon);
					}else{
						return "-";
					}
				},
				//format:webix.i18n.fullDateFormatStr,
				width: 200 },
				{ "id": "recognized_recon", sort:"date", "header": $.i18n("alarms.d.recognized"),
				//"template": function (obj_recon) { return getDate(obj_recon.recognized) },
				//format:webix.i18n.fullDateFormatStr,
				"template": function (obj_recon) { 
					if(obj_recon.recognized_recon !== null){
						return webix.i18n.fullDateFormatStr(obj_recon.recognized_recon);
					}else{
						return "-";
					}
				},
				width: 200 },
				{ "id": "deactivation_recon", sort:"date", "header": $.i18n("alarms.d.deactivation"), 
				//"template": function (obj_recon) { return getDate(obj_recon.deactivation) }, 
				//format:webix.i18n.fullDateFormatStr,
				"template": function (obj_recon) { 
					if(obj_recon.deactivation_recon !== null){
						return webix.i18n.fullDateFormatStr(obj_recon.deactivation_recon);
					}else{
						return "-";
					}
				},
				width: 200 },
				{ "id": "alarmConfig_recon", sort:"text", "header": $.i18n("alarms.d.alarmconfig"), "template": function (obj_recon) { return $.i18n(obj_recon.alarmConfig.id); }, width: 270, fillspace:true },
				{ "id": "alarmLevel_recon", sort:"int", "header": $.i18n("alarms.d.level"), "template": function (obj_recon) { return alarmsCfg[obj_recon.alarmConfig.id].alarmLevel; }, width: 70 },
				{ "id": "sourceElementId", "template": function (obj) { return obj.sourceElement.id; }, hidden:true },
				{ "id": "sourceElementAlias", sort:"text", "header": $.i18n("alarms.d.source"), "template": function (obj) { return obj.sourceElement.alias; }, width: 100 },
				{ "id": "sourceElement", "header":"", "template": "<button class='open_elementForm' ><span class='webix_icon wxi-search'></span></button>", width: 50} , 
				{ "id": "targetElementId", "template": function (obj) { return obj.targetElement.id; }, hidden:true },
				{ "id": "targetElementAlias", sort:"text", "header": $.i18n("alarms.d.target"), "template": function (obj) { return obj.targetElement.alias; }, width: 100 },
				{ "id": "targetElement", "header":"","template": "<button class='open_elementForm' ><span class='webix_icon wxi-search'></span></button>", width: 50}
				//{ "id": "recognize_recon", "header": "", "template": "<button class='action_deactivate' ><span class='mdi mdi-delete-outline'></span></button> <button class='action_activate' ><span class='mdi mdi-bell-plus-outline'></span></button>", width: 120 }
			],
			onClick: {
				open_elementForm:function (ev, id, html) {
					openElementForm(this.getItem(id)[id.column].id)
				},
				action_deactivate: function (ev, id, html) {
					var data = $$(widgetId+"_recon").serialize();
					var alarmId = id.row;
					for (let index = 0; index < data.length; index++) {
						if (data[index].id == alarmId) {
							if (data[index].deactivation != null) {
								webix.alert("Alarma ya desactivada");
							} else {
								webix.ui({
									view: "window",
									id: "popup_alarm_recon" + alarmId,
									head: "Deactivate " + alarmId,
									width: 330,
									height: 300,
									modal: true,
									body: {
										view: "form",
										elements: [
											{
												view: "textarea",
												id: "Comment_recon" + alarmId,
												label: "Comment",
												labelAlign: "right",
												height: 150,
												value: "type here"
											},
											{
												cols: [
													{
														view: "button", value: "Accept", css: "webix_primary", click: function () {
															let comment = $$("Comment_recon" + alarmId).getValue();
															let graphql = webix.proxy("GraphQL", sendDeactivationAlarms);
															graphql.save({
																"alarmIds": [alarmId],
																"recognizedComments": comment

															}).then((resp) => {
																if (resp === true) {
																	webix.message({
																		text: "Successful operation",
																		type: "success",
																		expire: 10000,
																		id: "successful_message"
																	});
																	$$("popup_alarm_recon" + alarmId).close();
																} else {
																	webix.message({
																		text: "Error",
																		type: "error ",
																		expire: 10000,
																		id: "error_message"
																	});
																}
															})
														}
													},
													{ view: "button", value: "Cancel", click: function () { $$("popup_alarm_recon" + alarmId).close() } }
												]
											}
										]
									}
								}).show();
							}
						}
					}
				}
			},
			scheme: {
				$change: function (item) { //Item es cada fila de la tabla
					if (item.deactivation != null) {
						item.$css = "alarm_deactivated";
					} else {
						item.$css = "alarm_activated";
					}
				}
			}
		},
		{
			view: "toolbar",
			id: "toolbar_alarms_recon",
			heigth: 50,
			hidden: true,
			align: "right",
			rows: [{
				cols: [
					{ gravity: 4 },
					{
						view: "button", id: "deactivate_bottom_recon", hidden: true, align: "right", autowidth: true, type: "icon", icon: "mdi mdi-delete-outline", label: "Deactivate",
						click: function () {
							var alarmas = [];
							var alarmasId = [];
							var data = $$(widgetId+"_recon").serialize();
							for (let index = 0; index < data.length; index++) {
								if (data[index].check_recon == "on") {
									alarmas.push(data[index])
								}
							}

							if (data.length == 0) {
								webix.alert($.i18n("alarms.popup.noAlamr"));
							} else if (alarmas.length == 0) {
								webix.alert($.i18n("alarms.popup.selectAlarm"));
							}

							for (let index = 0; index < alarmas.length; index++) {
								if (alarmas[index].deactivation != null) {
									webix.message("" + alarmas[index].id  + " " + $.i18n("alarms.popup.selectAlarm"))
								}
								else if (alarmas[index].deactivation == null) {
									alarmasId.push(alarmas[index].id)
								}
							}

							if (alarmasId.length > 0) {
								webix.ui({
									view: "window",
									id: "popup_alarm_array_deactivation_recon",
									head: "Deactivation Alarms",
									width: 330,
									height: 300,
									modal: true,
									body: {
										view: "form",
										elements: [
											{
												view: "textarea",
												id: "Comment_array_deactivation_recon",
												label: "Comment",
												labelAlign: "right",
												height: 150,
												value: "type here"
											},
											{
												cols: [
													{
														view: "button", value: "Deactivate", css: "webix_primary", click: function () {
															let comment = $$("Comment_array_deactivation_recon").getValue();
															let graphql = webix.proxy("GraphQL", sendDeactivationAlarms);
															graphql.save({
																"alarmIds": alarmasId,
																"recognizedComments": comment
															}).then((resp) => {
																if (resp === true) {
																	webix.message({
																		text: "Successful operation",
																		type: "success",
																		expire: 10000,
																		id: "successful_message"
																	});
																	alarmas = []
																	alarmasId = []
																	$$("popup_alarm_array_deactivation_recon").close();
																} else {
																	webix.message({
																		text: "Error: " + resp,
																		type: "error ",
																		expire: 10000,
																		id: "error_message"
																	});
																	alarmas = []
																	alarmasId = []
																}
															})
														}
													},
													{
														view: "button", value: "Cancel", click: function () {
															alarmas = []
															alarmasId = []
															$$("popup_alarm_array_deactivation_recon").close();
														}
													}
												]
											}
										]
									}
								}).show();
							}
						}
					},
					{
						view: "button", id: "activation_bottom_recon", hidden: true, align: "right", autowidth: true, type: "icon", icon: "mdi mdi-bell-plus-outline", label: "Activation",
						click: function () { }
					}
				]
			}]
		}]
	};

	function openElementForm(id){
		try {
			if(project==0){
				try {
					$$("$sidebar1").unselectAll();
					$$("syn0").show();  
				} catch (error) {
					let module = window.em.getByField(EntityType.MODULE, "id", SYNOPTIC_TUNNEL_RIOVERDE_ID);
					util.module.open(module, null, null, null, null);	
				}
			}else{
				if ($$("winAlarmss" + id) != null) {
					$$("winAlarmss" + id).destructor();
				}
				let elementWindow = new ElementWindow(id,"ElementType:" + id.split(":")[1]);
				webix
				.ui({
				id: "winAlarmss" + id,
				view: "window",
				move: true,
				close: true,
				position: "right",
				body: elementWindow.getComponent(),
				on: {
					onDestruct: function(obj){
					//elementForm.disconnect();
					},
					onHide: function(obj){
					//elementForm.disconnect();
					//$$("win" + elementId).destructor();
					},
					onShow: function(obj){
					elementWindow.loadData();
					}
				}                            
				})
				.show();
			}
		} catch (e) {
			console.log(e);
		}
	}

	let result = {
		//view:"accordion",
		//multi:"mixed",
		rows: [
			{	
				header: $.i18n("defaultheader.activealarms"),
				body: genericTablei18n
			},
			{ view:"resizer" },
			{
				header:$.i18n("defaultheader.deActivealarms"),
				body: genericTablei18n_recon
			}
		],
		on:{
			onViewShow: function(){
				let table = new Alarms(graphAlarm, null, widgetId);
				table.init();
			}
		}
	};
	return result;
}
