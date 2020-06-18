import {getElementSVG, getElementIcon} from "../lib/iconGenerator.js";
import * as elementTypeMap from "../lib/elementTypeMap.js";

export default class ElementForm {
	constructor(id,columnsElementFormList) {
		this.id = id;
		this.columnsElementFormList = columnsElementFormList;
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
						  dataType
						}
					}
		      	}
				elementValues{
			        id
			        value
			        alerted
				  }
				alarms{
					id
					alarmConfig {id alias alarmLevel}
					recognized
					recognizedComments
					activation
					activationComments
					deactivation
					deactivationComments
					sourceElement{id alias}
					targetElement{id alias} 
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
					css: { "text-align": "left !important" }
				},
				{
					id: "elementHeaderTitle"+this.id,
					view: "template",
					type: "header",
					data: {
						value: ""
					},
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
			autoheight: true,
			scroll: false,
			//yCount: 6,
			columns:[
				{ id:"title",   width:370 , template:"{common.subrow()} #title#", fillspace:true },
				//{ id:"activation", width:100 },
				{ id: "recognize", "header": "", 
				 "template": "<button class='action_recognize' ><span class='mdi mdi-eye-check-outline'></span></button>", 
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
								webix.alert("Alarma ya desactivada");
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

		//this.streamtimeout = null;
	}
	
	disconnect() {
		this.client.deactivate();
		try {
			let stream = document.getElementsByClassName('stream_' + this.id);
			stream[0] !== undefined ? stream[0].src = "" : null;
			//stream[0] !== undefined ? clearTimeout(this.streamtimeout) : null;
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

							if (elementTypeMap.getElementType(element.id) == elementTypeMap.DMS_ELEMENT_TYPE) {
								let elementQuery = webix.proxy(
									"GraphQL",
									`
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

										getElementIcon(element, ":Form");
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
									if (item3 !== null) {
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
													alarmConfig {id alias alarmLevel}
													sourceElement{id alias}
												}
											}`
										);

										elementQueryacf.load({
											alarmId: item.id,
										}).then(datacf => {
											let activation = item.activation !== undefined ? item.activation !== null ? new Date(item.activation).toISOString() : null : null;
											let deactivation = item.deactivation !== undefined ? item.deactivation !== null ? new Date(item.deactivation).toISOString() : null : null;
											let recognized = item.recognized !== undefined ? item.recognized !== null ? new Date(item.recognized).toISOString() : null : null;
											let outlets = [];
											outlets.push({id: element.id + "_outlet_activation", title: $.i18n("elementForm.alarms.activation"), measure: item.activation !== undefined ? item.activation !== null ? new Date(item.activation).toISOString() : '-' : '-' });
											outlets.push({id: element.id + "_outlet_alarmLevel", title: $.i18n("elementForm.alarms.alarmLevel"), measure: datacf.alarmConfig.alarmLevel });
											outlets.push({id: element.id + "_outlet_deactivation", title: $.i18n("elementForm.alarms.deactivation"), measure: item.deactivation !== undefined ? item.deactivation !== null ? new Date(item.deactivation).toISOString() : '-' : '-' });
											outlets.push({id: element.id + "_outlet_recognized", title: $.i18n("elementForm.alarms.recognized"), measure: item.recognized !== undefined ? item.recognized !== null ? new Date(item.recognized).toISOString() : '-' : '-' });
											outlets.push({id: element.id + "_outlet_sourceElement", title: $.i18n("elementForm.alarms.sourceElement"), measure: datacf.sourceElement.alias });

											let row = { 
														id: item.id + "_AlarmsElementForm" , 
														title: $.i18n(datacf.alarmConfig.id), 
														recognized: recognized, 
														activation: activation, 
														deactivation: deactivation, 
														outlets: outlets };

											$$(element.id + "_elementAlarmsTable").add(row);
											$$(element.id + "_elementAlarmsTable").show();
											$$(element.id + "_elementAlarmsTable_HEADER").show();
										});
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

	/*streamTimeout(obj) {
		
		this.streamtimeout = setInterval(() => {
			try {
				let stream = document.getElementsByClassName('stream_' + obj)[0];
				let urlstream = stream.src;
				stream.src = "";
				stream.src = urlstream;
				let streamfull = document.getElementsByClassName('streamFullViewElementForm_' + obj)[0];
				streamfull = "";
				streamfull = urlstream;
				} catch (e) {
					console.error("Error. id: "+obj);
					console.error(e);
				}
		}, 30000);
	}*/

	getComponent() {
		
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
					elementTypeMap.getElementType(data.elementType.id) ===
					elementTypeMap.DMS_ELEMENT_TYPE
				) {
					//"ElementType:10"){
					iconoSvg = getElementSVG(data, 200, 40, ":Form");
				} else {
					iconoSvg = getElementSVG(data, 40, 40, ":Form");
				}
				
				$$("elementHeaderIcon" + this.id).setValues({ value: iconoSvg });

				getElementIcon(data, ":Form");
			} catch (e) {
				console.error("Error. id: "+this.id);
				console.error(e);
			}
			
			try {
			// STREAM     getElementType(elementIdString)
				if(elementTypeMap.getElementType(data.elementType.id) === elementTypeMap.CAM_ELEMENT_TYPE) { //"ElementType:2"){
					let stremMjpeg = null;
					if(data.elementValues !== null){
						stremMjpeg = data.elementValues.filter(elementValue => elementValue.id != null && elementTypeMap.getElementParamTypeId(elementValue.id) === elementTypeMap.CAM_STREAM_MJPEG); //"ElementTypeParam:2:1:3" );	
					}	
					if(stremMjpeg != null && stremMjpeg.length >= 1){
						let streamHeight = this.columnsElementFormList[data.elementType.id]["options"]["streamHeight"] !== undefined ? this.columnsElementFormList[data.elementType.id]["options"]["streamHeight"] : 300 ; //700 por defecto
						let streamWidth = this.columnsElementFormList[data.elementType.id]["options"]["streamWidth"] !== undefined ? this.columnsElementFormList[data.elementType.id]["options"]["streamWidth"] : 300 ; //700 por defecto
						let stream = stremMjpeg[0].value;						
						let camera = $$("component"+this.id).addView({  
							id: "stream_"+ this.id,
							height:streamHeight,
							width: streamWidth,
							data: {title: "Image One", src: stream },
							template: function (obj) {
							  // obj is a data record object
							 return '<embed class="' + this.id + '" style="width:100%;height:100%;position:absolute;margin-top:-13px;margin-left:-9px;" src="'+obj.src+'" />'
							}
						}, 1);
						
						let streamFullViewHeight = this.columnsElementFormList[data.elementType.id]["options"]["streamFullViewHeight"] !== undefined ? this.columnsElementFormList[data.elementType.id]["options"]["streamFullViewHeight"] : 700 ; //700 por defecto
						let streamFullViewWidth = this.columnsElementFormList[data.elementType.id]["options"]["streamFullViewWidth"] !== undefined ? this.columnsElementFormList[data.elementType.id]["options"]["streamFullViewWidth"] : 700 ; //700 por defecto

						// let button={
						// 	view:"button",
						// 	value:"video fullscreen",
						// 	click: function(obj){
						// 	  //webix.fullscreen.set($$(camera));

						// 	  webix
						// 	  .ui({
						// 		id:  "fullviewWindowsElementForm_" + this.id,
						// 		view: "window",
						// 		move: true,
						// 		close: true,
						// 		position: "right",
						// 		body: {
						// 			id: "streamFullViewElementForm_" + this.id,
						// 			height:streamFullViewHeight,
						// 			width: streamFullViewWidth,
						// 			data: {title: "Image One", src: stream },
						// 			template: function (obj) {
						// 			  return '<embed class="' + this.id + '" style="width: '+streamFullViewWidth+'px;height:'+streamFullViewHeight + 'px" src="'+obj.src+'" width="' + (streamFullViewWidth-1) + '" "height="' +(streamFullViewHeight-1) + '" />'
						// 			}
						// 		},
						// 		on: {
						// 			onHide: function(obj){
						// 				$$( "fullviewWindowsElementForm_" + this.id).destructor();
						// 			}
						// 		  }
						// 	  })
						// 	  .show();

						// 	}
						// };
						// $$("component" + this.id).addView(button, 2);
						//this.streamTimeout(this.id);
					}
				}
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
					data.elementValues.forEach(item => {
						let value = null;		
						value = item.value;	
						valuesList[elementTypeMap.getElementParamTypeIdi18(item.id)] = {id: "elementBody" + this.id + "_" + elementTypeMap.getElementParamTypeIdi18(item.id), iconAlerted: item.alerted, title: $.i18n(elementTypeMap.getElementParamTypeIdi18(item.id) + ".alias" ), measure: item.value === null ? "" : item.value, tooltiplab: item.id  };
				  });
				}

				this.columnsElementFormList[data.elementType.id]["rows"].forEach(item => {
					try {
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
							outlets.push({id: item.id + "_outlet_activation", title: $.i18n("elementForm.alarms.activation"), measure: item.activation !== null ? new Date(item.activation).toISOString() : '-' });
							outlets.push({id: item.id + "_outlet_alarmLevel", title: $.i18n("elementForm.alarms.alarmLevel"), measure: item.alarmConfig.alarmLevel });
							outlets.push({id: item.id + "_outlet_deactivation", title: $.i18n("elementForm.alarms.deactivation"), measure: item.deactivation !== null ? new Date(item.deactivation).toISOString() : '-' });
							outlets.push({id: item.id + "_outlet_recognized", title: $.i18n("elementForm.alarms.recognized"), measure: item.recognized !== null ? new Date(item.recognized).toISOString() : '-' });
							outlets.push({id: item.id + "_outlet_sourceElement", title: $.i18n("elementForm.alarms.sourceElement"), measure: item.sourceElement.alias });
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
				if(data.elementType.commandElementTypes){
					this.commands = $$("component"+this.id).addView({
						view: "form",
						id: "comandos" + this.id,
						elements:[]
					}, -1);
	
					for (let index = 0; index < data.elementType.commandElementTypes.length; index++) {
						$$(this.commands).addView({
							view: "button", 
							id: "command"+index+"_"+ this.id, 
							value: data.elementType.commandElementTypes[index].alias,
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
								});
							}
						}, -1);
	
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
		let result = {
				id: "component"+this.id,
				rows: [
					this.header,
					this.body,
					this.alarmsHeader,
					this.alarmsTable,
					//this.alarmsNone,
					{}
				]
			};

		return result;
	}
}
