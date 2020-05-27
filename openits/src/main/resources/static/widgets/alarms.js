import Alarms from "../classes/Alarms.js";

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
      alarmConfig {id alias alarmLevel}
      sourceElement{alias}
      targetElement{alias} 
  }
}`);

var sendRecognizeAlarms = `mutation  sendRecognizeAlarms ($alarmIds: [String!], $recognizedComments: String ){
	sendRecognizeAlarms(alarmIds: $alarmIds, recognizedComments: $recognizedComments)
}`;

var sendDeactivationAlarms = `mutation  sendDeactivationAlarms ($alarmIds: [String!], $deactivationComments: String ){
	sendDeactivationAlarms(alarmIds: $alarmIds, deactivationComments: $deactivationComments)
}`;

function getDate(timestamp) {
	return timestamp !== null ? new Date(timestamp).toISOString() : '-';
}

let genericTablei18n = {
	rows: [{
		"id": "alarmsTable",
		"view": "datatable",
		"autoConfig": true,
		"select": true,
		"multiselect": true,
		"scroll": "y",
		"columns": [
			{ "id": "id", "header": $.i18n("alarms.d.id"), hidden: true, width: 200 },
			{ "id": "check", "header": { content: "masterCheckbox" }, checkValue: 'on', uncheckValue: 'off', "template": "{common.checkbox()}", width: 40 },
			{ "id": "activation", "header": $.i18n("alarms.d.activation"), "template": function (obj) { return getDate(obj.activation) }, width: 200 },
			{ "id": "recognized", "header": $.i18n("alarms.d.recognized"), "template": function (obj) { return getDate(obj.recognized) }, width: 200 },
			{ "id": "deactivation", "header": $.i18n("alarms.d.deactivation"), "template": function (obj) { return getDate(obj.deactivation) }, width: 200 },
			{ "id": "alarmConfig", "header": $.i18n("alarms.d.alarmconfig"), "template": function (obj) { return obj.alarmConfig.alias; }, width: 270, fillspace:true },
			{ "id": "alarmLevel", "header": $.i18n("alarms.d.level"), "template": function (obj) { return obj.alarmConfig.alarmLevel; }, width: 50 },
			{ "id": "sourceElement", "header": $.i18n("alarms.d.source"), "template": function (obj) { return obj.sourceElement.alias; }, width: 100 },
			{ "id": "targetElement", "header": $.i18n("alarms.d.target"), "template": function (obj) { return obj.targetElement.alias; }, width: 100 },
			{ "id": "recognize", "header": "", "template": "<button class='action_recognize' ><span class='mdi mdi-eye-check-outline'></span></button> <button class='action_deactivate' ><span class='mdi mdi-delete-outline'></span></button> <button class='action_activate' ><span class='mdi mdi-bell-plus-outline'></span></button>", width: 120 }
		],
		onClick: {
			action_recognize: function (ev, id, html) {
				var data = $$("alarmsTable").serialize();
				var alarmId = id.row;
				for (let index = 0; index < data.length; index++) {
					if (data[index].id == alarmId) {
						if (data[index].recognized != null) {
							webix.alert("Alarma ya reconocida");
						} else {
							webix.ui({
								view: "window",
								id: "popup_alarm" + alarmId,
								head: alarmId + " " + data[index].alarmConfig.alias,
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
													view: "button", value: "Recognize", css: "webix_primary", click: function () {
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
			},
			action_deactivate: function (ev, id, html) {
				var data = $$("alarmsTable").serialize();
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
		url: function () {
			table.init();
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
			if (item.recognized != null) {
				$('.action_recognize').prop("visibility", "hidden");
			} else if (item.deactivation != null) {
				$('.action_deactivate').prop("visibility", "hidden");
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
					view: "button", id: "recognize_bottom", align: "right", autowidth: true, type: "icon", icon: "mdi mdi-eye-check-outline", label: "Recognize",
					click: function () {
						var alarmas = [];
						var alarmasId = [];
						var data = $$("alarmsTable").serialize();

						for (let index = 0; index < data.length; index++) {
							if (data[index].check) {
								alarmas.push(data[index]);
							}
						}

						if (data.length == 0) {
							webix.alert("No hay alarmas activas");
						} else if (alarmas.length == 0) {
							webix.alert("Seleccione alarmas");
						}

						for (let index = 0; index < alarmas.length; index++) {
							if (alarmas[index].recognized != null) {
								webix.message("La alarma " + alarmas[index].id + " ya esta reconocida")
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
													view: "button", value: "Recognize", css: "webix_primary", click: function () {
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
												},
												{
													view: "button", value: "Cancel", click: function () {
														alarmas = []
														alarmasId = []
														$$("popup_alarm_array_recognice").close();
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
					view: "button", id: "deactivate_bottom", align: "right", autowidth: true, type: "icon", icon: "mdi mdi-delete-outline", label: "Deactivate",
					click: function () {
						var alarmas = [];
						var alarmasId = [];
						var data = $$("alarmsTable").serialize();
						for (let index = 0; index < data.length; index++) {
							if (data[index].check) {
								alarmas.push(data[index])
							}
						}

						if (data.length == 0) {
							webix.alert("No hay alarmas activas");
						} else if (alarmas.length == 0) {
							webix.alert("Seleccione alarmas");
						}

						for (let index = 0; index < alarmas.length; index++) {
							if (alarmas[index].deactivation != null) {
								webix.message("La alarma " + alarmas[index].id + " ya esta desactivada")
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
					view: "button", id: "activation_bottom", align: "right", autowidth: true, type: "icon", icon: "mdi mdi-bell-plus-outline", label: "Activation",
					click: function () { }
				}
			]
		}]
	}]
};

let genericTablei18n_recon = {
	rows: [{
		"id": "alarmsTable_recon",
		"view": "datatable",
		"autoConfig": true,
		"select": true,
		"multiselect": true,
		"scroll": "y",
		"columns": [
			{ "id": "id_recon", "header": $.i18n("alarms.d.id"), hidden: true, width: 200 },
			{ "id": "check_recon", "header": { content: "masterCheckbox" }, checkValue: 'on', uncheckValue: 'off', "template": "{common.checkbox()}", width: 40 },
			{ "id": "activation_recon", "header": $.i18n("alarms.d.activation"), "template": function (obj_recon) { return getDate(obj_recon.activation) }, width: 200 },
			{ "id": "recognized_recon", "header": $.i18n("alarms.d.recognized"), "template": function (obj_recon) { return getDate(obj_recon.recognized) }, width: 200 },
			{ "id": "deactivation_recon", "header": $.i18n("alarms.d.deactivation"), "template": function (obj_recon) { return getDate(obj_recon.deactivation) }, width: 200 },
			{ "id": "alarmConfig_recon", "header": $.i18n("alarms.d.alarmconfig"), "template": function (obj_recon) { return obj_recon.alarmConfig.alias; }, width: 270, fillspace:true },
			{ "id": "alarmLevel_recon", "header": $.i18n("alarms.d.level"), "template": function (obj_recon) { return obj_recon.alarmConfig.alarmLevel; }, width: 50 },
			{ "id": "sourceElement_recon", "header": $.i18n("alarms.d.source"), "template": function (obj_recon) { return obj_recon.sourceElement.alias; }, width: 100 },
			{ "id": "targetElement_recon", "header": $.i18n("alarms.d.target"), "template": function (obj_recon) { return obj_recon.targetElement.alias; }, width: 100 },
			{ "id": "recognize_recon", "header": "", "template": "<button class='action_deactivate' ><span class='mdi mdi-delete-outline'></span></button> <button class='action_activate' ><span class='mdi mdi-bell-plus-outline'></span></button>", width: 120 }
		],
		onClick: {
			action_deactivate: function (ev, id, html) {
				var data = $$("alarmsTable_recon").serialize();
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
		align: "right",
		rows: [{
			cols: [
				{ gravity: 4 },
				{
					view: "button", id: "deactivate_bottom_recon", align: "right", autowidth: true, type: "icon", icon: "mdi mdi-delete-outline", label: "Deactivate",
					click: function () {
						var alarmas = [];
						var alarmasId = [];
						var data = $$("alarmsTable_recon").serialize();
						for (let index = 0; index < data.length; index++) {
							if (data[index].check_recon) {
								alarmas.push(data[index])
							}
						}

						if (data.length == 0) {
							webix.alert("No hay alarmas activas");
						} else if (alarmas.length == 0) {
							webix.alert("Seleccione alarmas");
						}

						for (let index = 0; index < alarmas.length; index++) {
							if (alarmas[index].deactivation != null) {
								webix.message("La alarma " + alarmas[index].id + " ya esta desactivada")
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
					view: "button", id: "activation_bottom_recon", align: "right", autowidth: true, type: "icon", icon: "mdi mdi-bell-plus-outline", label: "Activation",
					click: function () { }
				}
			]
		}]
	}]
};

let table = new Alarms(graphAlarm, null);

export let alarms = {
	rows: [
		{
			body: genericTablei18n
		},
		{
			body: genericTablei18n_recon
		}
	]
};
