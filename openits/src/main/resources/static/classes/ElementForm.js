import {getElementSVG, getElementIcon} from "../lib/iconGenerator.js";
import * as elementTypeMap from "../lib/elementTypeMap.js";
import {alarmsCfg} from "../data/alarmsCfg.js";
import {columnsElementFormList as colMetaData} from "../data/elementForm.js";
import PanelManager from "../classes/PanelManager.js";
import * as revenga from "../lib/revenga.js";
import {RULE} from "../data/GLOBAL_PROPERTIES.js";

export default class TabForm {
	constructor(id,columnsElementFormList) {
		this.id = id;
		if(columnsElementFormList){
			this.columnsElementFormList = columnsElementFormList;
		}else{
			this.columnsElementFormList = colMetaData;
		}
		var idtable = this.id;
		let self = this;

		var graphElem = `query { 
			getElement(elementId: "` + this.id + `"){
				id
				alias
				description
				state
				elementType {
			        id
			        alias
					commandElementTypes{
						id
						alias
						commandElementTypeParams{
						  id
						  alias
						}
					}
				}
				childs {
					id
					alias
					state
				}  
				elementValues {
					id 
					value 
					alerted 
					elementTypeParam {
						id 
						alias  
						representation
						dataType{
							id
						}
					}
				}
				alarms{
					id
					alarmConfig {
						id 
						alias 
						alarmLevel {
							labelAlias
					  	}
					}
					recognized
					recognizedComments
					activation
					activationComments
					deactivation
					deactivationComments
				}
			}
		}`;

		var sendRecognizeAlarms = `mutation  sendRecognizeAlarms ($alarmIds: [String!], $recognizedComments: String ){
			sendRecognizeAlarms(alarmIds: $alarmIds, recognizedComments: $recognizedComments)
		}`;
		
		var sendDeactivationAlarms = `mutation  sendDeactivationAlarms ($alarmIds: [String!], $deactivationComments: String ){
			sendDeactivationAlarms(alarmIds: $alarmIds, deactivationComments: $deactivationComments)
		}`;

		this.graphql = webix.proxy("GraphQL", graphElem);
		this.header = {
			id: "elementHeader"+this.id,
			cols:[
				{
					id: "elementHeaderIcon"+this.id,
					view: "template",
					type: "header",
					data: {
						value: ""
					},
					template: function (obj) {
						return obj.value;
					},
					width: 200,
					css: { "text-align": "center !important" }
				},
				{
					id: "elementHeaderTitle"+this.id,
					view: "template",
					type: "header",
					data: {
						value: ""
					},
					width: 400,
					template: function (obj) {
						return obj.value;
					},
					css: { "text-align": "center !important" }
				}
			]
		};

		this.body = {
			id:"elementBody" + this.id,	
            view:"datatable",
			editable:true,
			width: this.columnsElementFormList["ElementType:" + elementTypeMap.getElementType(this.id)]["width"],
			scroll:this.columnsElementFormList["ElementType:" + elementTypeMap.getElementType(this.id)]["scroll"],
			yCount:this.columnsElementFormList["ElementType:" + elementTypeMap.getElementType(this.id)]["rowCounts"],
			margin: 0,
			header: false,
			autoConfig:true,
			tooltip:true,
			columns:[
				{ id:"iconAlerted", width:40,template:function(obj){
					if(obj.iconAlerted === true) {
							return "<span style='color:red;' class='webix_icon mdi mdi-alert-circle'></span>";
						} else {
							return "";
						}
					} 
				},
				{ id:"title",   width:300, tooltip:function(obj){  
						return $.i18n(elementTypeMap.getElementParamTypeIdi18(obj.tooltiplab) + ".description");
					}
				},
				{ id:"measure", width:500, tooltip:function(obj){  
						return $.i18n(elementTypeMap.getElementParamTypeIdi18(obj.tooltiplab) + ".description");
					}
				}
			],
            data:[]
		};	

		this.alarmsHeader = { id:  this.id + "_elementAlarmsTable_HEADER" ,template: $.i18n("defaultheader.managementAlarms"), type:"section" };
		this.alarmsTable = {
			id:  this.id + "_elementAlarmsTable",	
			view:"datatable",
			header: false,
			yCount: 5,
			scrollX:false,
			columns:[
				{ id:"title",   width:370 , template:"{common.subrow()} #title#", fillspace:true },
				//{ id:"activation", width:100 },
				{ id: "recognize",
				cssFormat:hidde_recognize,
				 "header": "", 
				 "template": !checkUserPermission(RULE.ALARM_RECOGNIZE) ?"":  "<button class='action_recognize' ><span class='mdi mdi-eye-check-outline'></span></button>", 
				 width: 50 }
				//{ id:"recognized", width:100 }
			],
			data:[],
			subview:{
				borderless:true,
				view:"datatable",
				header:false,
				columns:[
					{ id:"title",   autowidth:true, fillspace:true },
					{ id:"measure", autowidth:true, fillspace:true }
				],
				scrollX:false,
				autoheight:true,
				autoweidth:true
				},  
			on:{
				onSubViewCreate:function(view, item){
				  view.parse(item.outlets);
				  view.config.id = item.id + "_SUBVIEW";
				}
			},
			onClick: {
				action_recognize: function (ev, id, html) {
					var data = $$(idtable + "_elementAlarmsTable").serialize();
					var alarmId = id.row;
					for (let index = 0; index < data.length; index++) {
						if (data[index].id == alarmId) {
							if (data[index].recognized != null) {
								webix.alert($.i18n("Alarm_recon"));
							} else {
								webix.ui({
									view: "window",
									id: "popup_alarm" + alarmId.split("_")[0],
									head: alarmId + " " + data[index].title,
									width: 330,
									height: 300,
									modal: true,
									body: {
										view: "form",
										elements: [
											{
												view: "textarea",
												id: "Comment" + alarmId.split("_")[0],
												label: "Comment",
												labelAlign: "right",
												height: 150,
												value: "type here"
											},
											{
												cols: [
													{
														view: "button", value: "Recognize", css: "webix_primary", click: function () {
															let comment = $$("Comment" + alarmId.split("_")[0]).getValue();
															let graphql = webix.proxy("GraphQL", sendRecognizeAlarms);
															graphql.save({
																"alarmIds": [alarmId.split("_")[0]],
																"recognizedComments": comment
															}).then((resp) => {
																if (resp === true) {
																	webix.message({
																		text: "Successful operation",
																		type: "success",
																		expire: 10000,
																		id: "successful_message"
																	});
																	$$("popup_alarm" + alarmId.split("_")[0]).close();
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
													{ view: "button", value: "Cancel", click: function () { $$("popup_alarm" + alarmId.split("_")[0]).close() } }
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
					var data = $$(idtable + "_elementAlarmsTable").serialize();
					var alarmId = id.row;
					for (let index = 0; index < data.length; index++) {
						if (data[index].id == alarmId) {
							if (data[index].deactivation != null) {
								webix.alert($.i18n("Alarm_deactivated"));
							} else {
								webix.ui({
									view: "window",
									id: "popup_alarm" + alarmId.split("_")[0],
									head: "Deactivate " + alarmId.split("_")[0],
									width: 330,
									height: 300,
									modal: true,
									body: {
										view: "form",
										elements: [
											{
												view: "textarea",
												id: "Comment" + alarmId.split("_")[0],
												label: "Comment",
												labelAlign: "right",
												height: 150,
												value: "type here"
											},
											{
												cols: [
													{
														view: "button", value: "Accept", css: "webix_primary", click: function () {
															let comment = $$("Comment" + alarmId.split("_")[0]).getValue();
															let graphql = webix.proxy("GraphQL", sendDeactivationAlarms);
															graphql.save({
																"alarmIds": [alarmId.split("_")[0]],
																"recognizedComments": comment
	
															}).then((resp) => {
																if (resp === true) {
																	webix.message({
																		text: "Successful operation",
																		type: "success",
																		expire: 10000,
																		id: "successful_message"
																	});
																	$$("popup_alarm" + alarmId.split("_")[0]).close();
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
													{ view: "button", value: "Cancel", click: function () { $$("popup_alarm" + alarmId.split("_")[0]).close() } }
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
		};

		this.alarmsNone = {
			template:"Ninguna",
			id: this.id + "_elementAlarmsTable_NONE",
			hidden: true
		}

        // creates the stomp connection
        this.client = new StompJs.Client({
			brokerURL: webix.storage.session.get("url.ws.stomp"),
			connectHeaders: {
			login: "user",
			passcode: "user"
			},
			//debug: function(str) {
			//console.log(str);
			//},
			reconnectDelay: 5000,
			heartbeatIncoming: 4000,
			heartbeatOutgoing: 4000
		});

		this.topic = "/topic/element." + this.id;
		this.client.onConnect = function(frame) {
			this.suscription = self.client.subscribe(self.topic, self.update_table);
		};
		this.client.onStompError = function(frame) {
			// Will be invoked in case of error encountered at Broker
			// Bad login/passcode typically will cause an error
			// Complaint brokers will set `message` header with a brief message. Body may contain details.
			// Compliant brokers will terminate the connection after any error
			console.log("Broker reported error: " + frame.headers["message"]);
			console.log("Additional details: " + frame.body);
		};

		this.streamtimeout = null;
	}
	
	disconnect() {
		this.client.deactivate();
		try {
			let stream = document.getElementsByClassName('stream_' + this.id);
			stream[0] !== undefined ? stream[0].src = "" : null;
			stream[0] !== undefined ? clearTimeout(this.streamtimeout) : null;
			let fullstream = document.getElementsByClassName('streamFullViewElementForm_' + this.id);
			fullstream[0] !== undefined ? fullstream[0].src = "" : null;
		} catch (e) {
			console.error("Error. id: "+this.id);
			console.error(e);
		}
	}

    getDate(timestamp)
    {
        return timestamp !== null ? new Date(timestamp).toISOString() : '-';
    }

	update_table(message) {
		try {
			let obj = JSON.parse(JSON.parse(message.body));
			obj.basicElementsVO.forEach((element) => {
				try {
					let item = $$("elementBody" + element.id);
					if (item != null) {
						let evalues = element.elementValues !== undefined ? element.elementValues : null;
						let ealarms = element.alarms !== undefined ? element.alarms : null;
						try {
							if (evalues === null)
								element["elementValues"] = [];

							if (
							elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_10_DMS
							|| elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_24_PMV_A_C
							|| elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_26_PMV_64X64
							|| elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_27_PMV_A_B
							|| elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_28_PMV_192X64
							|| elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_29_PMV_B_D
							|| elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_30_PMV_128X64
							|| elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_31_PMV
							|| elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_32_PMV
							|| elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_35_PMV
							|| elementTypeMap.getElementType(element.id) === elementTypeMap.ELEMENT_TYPE_36_PMV
							) {
								let elementQuery = webix.proxy(
									"GraphQL",`
									query ($elem: String!) {
										getElement(elementId: $elem)
										{
											id
											state
											elementType {
												id
												alias
											}
											elementValues
											{
												id
												value
												alerted
												elementTypeParam {
													description
												}
											}
										}
									}
									`
								);

								// loads the data
								elementQuery
									.load({
										elem: element.id,
									})
									.then((data) => {
										// process the received data
										let element = data;

										getElementIcon(data, ":Form");
									});
							} else {
								getElementIcon(element, ":Form");
							}
						} catch (err) {
							console.log("Error: " + err.message);
						}
						if (evalues !== null) {
							element.elementValues.forEach(evalue => {
								try {
									let eparam = elementTypeMap.getElementParamTypeIdi18(evalue.id);
									let item3 = $$("elementBody" + element.id).getItem("elementBody" + element.id + "_" + eparam);
									if (item3 !== null && typeof item3 != 'undefined') {
										let value = evalue.value !== undefined ? evalue.value === null ? "" : evalue.value : "";
										let alerted = evalue.alerted !== undefined ? evalue.alerted : -1;
										item3["measure"] = value;
										if (alerted !== -1) {
											item3["iconAlerted"] = evalue.alerted;
										}
										try {
											$$("elementBody" + element.id).updateItem("elementBody" + element.id + "_" + eparam, item3);
										} catch (err) {
											console.log("Error: " + err.message);
										}
									}
								} catch (err) {
									console.log("Error: " + err.message);
								}
							});
						}
						if (ealarms !== null)
							//gestionAlarms(element.alarms);
							element.alarms.forEach(item => {
								let itemSearch = $$( element.id + "_elementAlarmsTable").getItem(item.id + "_AlarmsElementForm");
								if (itemSearch !== undefined) {
									
									let deactivation = item.deactivation !== undefined ? item.deactivation === null ? "-" : item.deactivation : "NA"; //NA por si no nos dan valor
									let recognized = item.recognized !== undefined ? item.recognized === null ? "-" : item.recognized : "NA";
									
									let controlDeactivation = (deactivation !== "-" && deactivation !== "NA") ? true : false;
									let controlrecognized = (recognized !== "-" && recognized !== "NA") ? true : false;
									if (controlDeactivation && controlrecognized) {
										try {
											$$( element.id + "_elementAlarmsTable").remove(itemSearch.id);
										} catch (err) {
											console.log("Error: " + err.message);
										}
					
										let itemSearch2 = $$( element.id + "_elementAlarmsTable");
										let items = itemSearch2.count();
										
										if (items === 0) {
											$$(element.id + "_elementAlarmsTable").hide();
											$$(element.id + "_elementAlarmsTable_HEADER").hide();
										}
									} else {
										let outlets = itemSearch["outlets"];
										if (deactivation !== "NA") {
											itemSearch["deactivation"] = deactivation;
											outlets[2]["measure"] = new Date(item.deactivation).toISOString();
											if (itemSearch.$subContent !== undefined) {
												let subDeactivation = $$(itemSearch.$subContent).getItem(element.id + "_outlet_deactivation");
												if (subDeactivation !== undefined) {
													let v_subDeactivation = subDeactivation;
													v_subDeactivation["measure"] = new Date(item.deactivation).toISOString();
													$$(itemSearch.$subContent).updateItem(element.id + "_outlet_deactivation", v_subDeactivation);
												}
											}
										}
										if (recognized !== "NA") {
											itemSearch["recognized"] = recognized;
											outlets[3]["measure"] = new Date(item.recognized).toISOString();
											if (itemSearch.$subContent !== undefined) {
												let subrecognized = $$(itemSearch.$subContent).getItem(element.id + "_outlet_recognized");
												if (subrecognized !== undefined) {
													let v_subrecognized = subrecognized;
													v_subrecognized["measure"] = new Date(item.recognized).toISOString();
													$$(itemSearch.$subContent).updateItem(element.id + "_outlet_recognized", v_subrecognized);
												}
											}
										}

										itemSearch["outlets"] = outlets;
										try {
											$$( element.id + "_elementAlarmsTable").updateItem(item.id + "_AlarmsElementForm", itemSearch);
											
										} catch (err) {
											console.log("Error: " + err.message);
										}
									}
					
								} else {
									
									let deactivation = item.deactivation !== undefined ? item.deactivation === null ? "-" : item.deactivation : "NA"; //NA por si no nos dan valor
									let recognized = item.recognized !== undefined ? item.recognized === null ? "-" : item.recognized : "NA";

									let controlDeactivation = (deactivation !== "-" && deactivation !== "NA") ? true : false;
									let controlrecognized = (recognized !== "-" && recognized !== "NA") ? true : false;

									if (controlDeactivation && controlrecognized) {
										//no hacemos nada
									} else {
										let elementQueryacf = webix.proxy(
											"GraphQL",`query getAlarm($alarmId: String!) {
												getAlarm(alarmId: $alarmId) {
													id
													alarmConfig {
														id 
														alias 
														alarmLevel {
															labelAlias
														}
													}
												}
											}`
										);

											let activation = item.activation !== undefined ? item.activation !== null ? new Date(item.activation).toISOString() : null : null;
											let deactivation = item.deactivation !== undefined ? item.deactivation !== null ? new Date(item.deactivation).toISOString() : null : null;
											let recognized = item.recognized !== undefined ? item.recognized !== null ? new Date(item.recognized).toISOString() : null : null;
											let outlets = [];
											outlets.push({id: element.id + "_outlet_activation", title: $.i18n("elementForm.alarms.activation"), measure: item.activation !== undefined ? item.activation !== null ? moment(new Date(item.activation)).format("YYYY-MM-DD HH:mm:ss") : '-' : '-' });
											outlets.push({id: element.id + "_outlet_alarmLevel", title: $.i18n("elementForm.alarms.alarmLevel"), measure: alarmsCfg[item.alarmConfig.id].alarmLevel });
											outlets.push({id: element.id + "_outlet_deactivation", title: $.i18n("elementForm.alarms.deactivation"), measure: item.deactivation !== undefined ? item.deactivation !== null ? moment(new Date(item.deactivation)).format("YYYY-MM-DD HH:mm:ss") : '-' : '-' });
											outlets.push({id: element.id + "_outlet_recognized", title: $.i18n("elementForm.alarms.recognized"), measure: item.recognized !== undefined ? item.recognized !== null ? moment(new Date(item.recognized)).format("YYYY-MM-DD HH:mm:ss") : '-' : '-' });

											let row = { 
														id: item.id + "_AlarmsElementForm" , 
														title: $.i18n(item.alarmConfig.id), 
														recognized: recognized, 
														activation: activation, 
														deactivation: deactivation, 
														outlets: outlets };

											$$(element.id + "_elementAlarmsTable").add(row);
											$$(element.id + "_elementAlarmsTable").show();
											$$(element.id + "_elementAlarmsTable_HEADER").show();
									}
								}
							});
					}
				} catch (err) {
					console.log("Error: " + err.message);
				}
			});
		} catch (err) {
			console.log("Error: " + err.message);
		}
	}

    // got from Github
    elementReady(selector) {
        return new Promise((resolve, reject) => {
            let el = document.querySelector(selector);
            if (el) {
                resolve(el);
            }
            new MutationObserver((mutationRecords, observer) => {
                // Query for elements matching the specified selector
                Array.from(document.querySelectorAll(selector)).forEach((element) => {
                    resolve(element);
                    //Once we have resolved we don't need the observer anymore.
                    observer.disconnect();
                });
            }).observe(document.documentElement, {
                childList: true,
                subtree: true,
            });
        });
    }

	getComponent() {
		var client = this.client;
		let result = {
			id: "component"+this.id,
			rows: [
				this.header,
				this.body,
				this.alarmsHeader,
				this.alarmsTable,
				//this.alarmsNone,
				{}		
			],
			on:{
				onDestruct: function(obj){
					client.deactivate();
				}
			}
		};
		return result;
	}

	loadData() {
		
		// waits for the element and starts drawing
		this.elementReady('[view_id="' + "elementBody" + this.id + '"]').then((c) => {
			let spinnerDivToAdd = document.createElement("div");
			spinnerDivToAdd.setAttribute("id", "elementBody" + this.id + "spinner");
			spinnerDivToAdd.setAttribute("class", "spinner");
			document
			.querySelector('[view_id="' + "elementBody" + this.id + '"]')
			.prepend(spinnerDivToAdd);
		});

		this.graphql.load().then(data => {
			this.data = data;
			// HEADER
			try {
				let headerTitle = $.i18n(data.elementType.id + ".alias") + " " + data.alias;
				$$("elementHeaderTitle" + this.id).setValues({ value: headerTitle });
			} catch (e) {
				console.error("Error. id: "+this.id);
				console.error(e);
			}

			try {
				// ICON
				let iconoSvg = "";
				if (
					elementTypeMap.getElementType(data.id) === elementTypeMap.ELEMENT_TYPE_24_PMV_A_C
					|| elementTypeMap.getElementType(data.id) === elementTypeMap.ELEMENT_TYPE_26_PMV_64X64
					|| elementTypeMap.getElementType(data.id) === elementTypeMap.ELEMENT_TYPE_27_PMV_A_B
					|| elementTypeMap.getElementType(data.id) === elementTypeMap.ELEMENT_TYPE_28_PMV_192X64
					|| elementTypeMap.getElementType(data.id) === elementTypeMap.ELEMENT_TYPE_29_PMV_B_D
					|| elementTypeMap.getElementType(data.id) === elementTypeMap.ELEMENT_TYPE_30_PMV_128X64
					|| elementTypeMap.getElementType(data.id) === elementTypeMap.ELEMENT_TYPE_31_PMV
					|| elementTypeMap.getElementType(data.id) === elementTypeMap.ELEMENT_TYPE_32_PMV
					|| elementTypeMap.getElementType(data.id) === elementTypeMap.ELEMENT_TYPE_35_PMV
					|| elementTypeMap.getElementType(data.id) === elementTypeMap.ELEMENT_TYPE_36_PMV
				) {
					//"ElementType:10"){
					
					// obtain the panel configuration
					let panelConfig = data.elementValues.find(
						(ic) =>
							elementTypeMap.getElementTypeParam(ic.id) ==
							elementTypeMap.PANEL_MANAGER_CONFIG
					);

					let total_width = 0;
					let total_height = 0;

					// calculate the width and height of the panel
					if(typeof panelConfig != 'undefined' && panelConfig != null) {
						panelConfig = JSON.parse(panelConfig.value);

						// Generate the matrix with sizes
						for (let nsubpanel=0; nsubpanel<panelConfig["Subpaneles"].length; nsubpanel++) {
							let n_size = panelConfig["Subpaneles"][nsubpanel]["Tamanio"];
							let panel_width = parseInt(n_size.split("x")[0]);
							let panel_height = parseInt(n_size.split("x")[1]);
							total_width += panel_width;
							total_height = panel_height;
						}

					}

					// get the panel icon
					let scale_height = 40 / total_height;
					let scaled_width = total_width * scale_height;
					//iconoSvg = getElementSVG(data, total_width, total_height, ":Form");
					iconoSvg = getElementSVG(data, scaled_width, total_height, ":Form");

					$$("elementHeaderIcon" + this.id).setValues({ value: iconoSvg });

					// scale the element to fit in the bar
					document.getElementById(data.id + ":Form").setAttribute("transform", "scale(" + scale_height + "," + scale_height + ")");

				} else if(elementTypeMap.getElementType(data.id) === elementTypeMap.ELEMENT_TYPE_10_DMS) {
					iconoSvg = getElementSVG(data, 180, 40, ":Form");
					$$("elementHeaderIcon" + this.id).setValues({ value: iconoSvg });
			 	} else {
					iconoSvg = getElementSVG(data, 40, 40, ":Form");
					$$("elementHeaderIcon" + this.id).setValues({ value: iconoSvg });
				}
				
				getElementIcon(data, ":Form");
			} catch (e) {
				console.error("Error. id: "+this.id);
				console.error(e);
			}

			let valuesList = {};
			
			//BODY
			try {
				valuesList["id"] = {id: "elementBody" + this.id + "_id", iconAlerted: false, title: $.i18n("role.popup.id"), measure: data.id, tooltiplab: data.id  };
				valuesList["alias"] = {id: "elementBody" + this.id + "_alias", iconAlerted: false, title: $.i18n("role.popup.alias"), measure: data.alias, tooltiplab: data.id  };
			} catch (e) {
				console.error("Error. id: "+this.id);
				console.error(e);		
			}
				
			//MEASURES
			try {
				if(data.elementValues) {
					// console.warn("values: ")
					// console.warn(data.elementValues)
					data.elementValues.forEach(item => {
						// console.warn("item: ")
						// console.warn(item)
						if(item){
							let value = null;		
							value = item.value;
							//Tipado server date metido a fuego
							try {
								if(item.elementTypeParam && item.elementTypeParam.dataType && item.elementTypeParam.dataType.id && item.elementTypeParam.dataType.id == "DataType:4" && item.value){
									valuesList[elementTypeMap.getElementParamTypeIdi18(item.id)] = {id: "elementBody" + this.id + "_" + elementTypeMap.getElementParamTypeIdi18(item.id), iconAlerted: item.alerted, title: $.i18n(elementTypeMap.getElementParamTypeIdi18(item.id) + ".alias" ), measure: item.value === null ? "" : parseUIParam(item.value, item.elementTypeParam.dataType.id), tooltiplab: item.id  };
								}else{
									valuesList[elementTypeMap.getElementParamTypeIdi18(item.id)] = {id: "elementBody" + this.id + "_" + elementTypeMap.getElementParamTypeIdi18(item.id), iconAlerted: item.alerted, title: $.i18n(elementTypeMap.getElementParamTypeIdi18(item.id) + ".alias" ), measure: item.value === null ? "" : item.value, tooltiplab: item.id  };
								}	
							} catch (error) {
								valuesList[elementTypeMap.getElementParamTypeIdi18(item.id)] = {id: "elementBody" + this.id + "_" + elementTypeMap.getElementParamTypeIdi18(item.id), iconAlerted: item.alerted, title: $.i18n(elementTypeMap.getElementParamTypeIdi18(item.id) + ".alias" ), measure: item.value === null ? "" : item.value, tooltiplab: item.id  };
							}
						}
				  });
				}

				this.columnsElementFormList[data.elementType.id]["rows"].forEach(item => {
					try {
						if(valuesList[item] !== undefined) 
							$$("elementBody" + this.id).add(valuesList[item]);
					} catch (e) {
						console.error("Error. id: "+this.id+ "_ " + item);
						console.error(e);
					}
				});

			} catch (e) {
				console.error("Error. id: "+this.id);
				console.error(e);
			}

			//ALARMS
			try {
				if(data.alarms !== null){
					data.alarms.forEach(item => {
						let deactivation = item.deactivation !== undefined ? ( item.deactivation === null ? "-" : item.deactivation ) : "NA"; //NA por si no nos dan valor
						let recognized = item.recognized !== undefined ? item.recognized === null ? "-" : item.recognized : "NA";
						
						let controlDeactivation = (deactivation !== "-" && deactivation !== "NA") ? true : false;
						let controlrecognized = (recognized !== "-" && recognized !== "NA") ? true : false;
						if (controlDeactivation && controlrecognized) {
							//no hacemos nada
						} else {

							let outlets = [];
							outlets.push({id: item.id + "_outlet_activation", title: $.i18n("elementForm.alarms.activation"), measure: item.activation !== null ?  moment(new Date(item.activation)).format("YYYY-MM-DD HH:mm:ss") : '-' });
							outlets.push({id: item.id + "_outlet_alarmLevel", title: $.i18n("elementForm.alarms.alarmLevel"), measure: item.alarmConfig.alarmLevel });
							outlets.push({id: item.id + "_outlet_deactivation", title: $.i18n("elementForm.alarms.deactivation"), measure: item.deactivation !== null ? moment(new Date(item.deactivation)).format("YYYY-MM-DD HH:mm:ss") : '-' });
							outlets.push({id: item.id + "_outlet_recognized", title: $.i18n("elementForm.alarms.recognized"), measure: item.recognized !== null ? moment(new Date(item.recognized)).format("YYYY-MM-DD HH:mm:ss") : '-' });
							let row = {id: item.id + "_AlarmsElementForm", title: $.i18n(item.alarmConfig.id), recognized: item.recognized, activation: item.activation, deactivation: item.deactivation, outlets: outlets };
							$$(this.id + "_elementAlarmsTable").add(row);

						}
					});
				} else {
					$$(this.id + "_elementAlarmsTable").hide();
					$$(this.id + "_elementAlarmsTable_HEADER").hide();
				} 

			} catch (e) {
				//console.error("Error. id: "+this.id);
				console.error(e);
			}

			//COMMANDS
			try {
				if (checkUserPermission(RULE.COMMAND_ACTION)){
					if(data.elementType.commandElementTypes){
						this.commands = $$("component"+this.id).addView({
							view: "form",
							id: "comandos" + this.id,
							elements:[]
						}, -1);
		
						for (let index = 0; index < data.elementType.commandElementTypes.length; index++) {
	
							if(
								data.elementType.commandElementTypes[index].id==="CommandElementType:" + elementTypeMap.COMMAND_TYPE_PMV_A_C
								|| data.elementType.commandElementTypes[index].id==="CommandElementType:" + elementTypeMap.COMMAND_TYPE_MATRIX_PANEL_64X64
								|| data.elementType.commandElementTypes[index].id==="CommandElementType:" + elementTypeMap.COMMAND_TYPE_PMV_A_B
								|| data.elementType.commandElementTypes[index].id==="CommandElementType:" + elementTypeMap.COMMAND_TYPE_MATRIX_PANEL_192X64
								|| data.elementType.commandElementTypes[index].id==="CommandElementType:" + elementTypeMap.COMMAND_TYPE_PMV_B_D
								|| data.elementType.commandElementTypes[index].id==="CommandElementType:" + elementTypeMap.COMMAND_TYPE_MATRIX_PANEL_128X64
								|| data.elementType.commandElementTypes[index].id==="CommandElementType:" + elementTypeMap.COMMAND_TYPE_MATRIX_PANEL_TEMP
								|| data.elementType.commandElementTypes[index].id==="CommandElementType:" + elementTypeMap.COMMAND_TYPE_MATRIX_PANEL_TEMP_A
								|| data.elementType.commandElementTypes[index].id==="CommandElementType:" + elementTypeMap.COMMAND_TYPE_MATRIX_PANEL_DGT
								|| data.elementType.commandElementTypes[index].id==="CommandElementType:" + elementTypeMap.COMMAND_TYPE_MATRIX_PANEL_DGT_GR_TXT
							){
	
								$$(this.commands).addView({
									view: "button", 
									id: "command"+index+"_"+ this.id, 
									value: $.i18n(data.elementType.commandElementTypes[index].id),
									click: function(){
										let idtype = "ElementType:" + elementTypeMap.getElementType(data.id);
										//if( $$("panelManager" + data.id)){
										//	$$("panelManager" + data.id).show();
										if($$("panelManager" + idtype)){
											$$("panelManager" + idtype).show();
										}else{
											let panelMan = new PanelManager(data.id, data.elementType.id,elementTypeMap.PANELMANAGER_TYPE_PANEL,null,null);//data.elementType.commandElementTypes[index]);
											webix.ui({
												id:"panelManager"+idtype,
												view:"window",	
												move:false,
												position:"center",
												modal:true,
												close: true,
												body: panelMan.getInitComponent(),
												head:$.i18n("elementForm.señalization.panel.head"),
												on: {
													onShow: function(obj){
														panelMan.loadComponent();
													},
													onHide: function(obj){
														if(typeof keyLayout != 'undefined' && Keyboard){
															Keyboard.close();
														}
														$$("panelManager" + idtype).destructor();
													}
												}
											  }).show();
										}
									}								
								}, -1);
							}else{
	
								$$(this.commands).addView({
									view: "button", 
									id: "command"+index+"_"+ this.id, 
									value: $.i18n(data.elementType.commandElementTypes[index].id),
									click: function(){
										var sendCommand = `mutation  sendCommandElement($commands: [CommandElementVO!]!){
											sendCommandElement(commands: $commands)  
										}`;
										var graphql = webix.proxy("GraphQL", sendCommand);
										graphql.save({
											"commands": [
												{
													"commandElementTypeId": data.elementType.commandElementTypes[index].id,
													"elementId": data.id
												}
											]
										}).then(() => {
											webix.message({type:"success", text:$.i18n("lbl_command_sended")});
										});
									}
								}, -1);
							}
						}
					}

				}
				
			} catch (e) {
				console.error("Error. id: "+this.id);
				console.error(e);
			}

			document.getElementById("elementBody" + this.id + "spinner").style.display = "none";
			this.client.activate();
			return data;
		});


		/*
		var client = this.client;
		let result = {
			id: "component"+this.id,
			rows: [
				this.header,
				this.body,
				this.alarmsHeader,
				this.alarmsTable,
				//this.alarmsNone,
				{}		
			],
			on:{
				onDestruct: function(obj){
					client.deactivate();
				}
			}
		};
		return result;
		*/
	}

}

function hidde_recognize(value, obj){
	if(obj.recognized){
		return {"pointer-events": "none",
		"opacity": "0.6",
		"cursor": "not-allowed"};
	}
}
