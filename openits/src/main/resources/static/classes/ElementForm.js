import { getElementSVG, getElementIcon} from "../lib/iconGenerator.js";
import * as elementTypeMap from "../lib/elementTypeMap.js";

export default class ElementForm {
	constructor(id,columnsElementFormList) {
		this.id = id;
		this.columnsElementFormList = columnsElementFormList;

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
			}
		}`;
		this.graphql = webix.proxy("GraphQL", graphElem);
		this.header = {
			id: "elementHeader"+this.id,
			view: "template",
			type: "header",
			data: {
				value: ""
			},
			template: function (obj) {
				return obj.value;
			},
			css: { "text-align": "center !important" }
		};

		this.body = {
			id:"elementBody" + this.id,	
            view:"datatable",
			editable:true,
			height: 250,
			width: 420,
			scroll:"y",
			margin: 0,
			header: false,
            autoConfig:true,
			columns:[
				{ id:"iconAlerted", width:40,template:function(obj){
					if(obj.iconAlerted === true) {
							return "<span style='color:red;' class='webix_icon mdi mdi-alert-circle'></span>";
						} else
							return "";
					} 
				},
				{ id:"title", width:200},
				{ id:"measure", width:160}
			],
            data:[]
		};	
		
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
		this.client.activate();
	}
	
	disconnect() {
		this.client.deactivate();
	}

   update_table(message){
	try {
		let obj = JSON.parse(JSON.parse(message.body));
		obj.basicElementsVO.forEach((element) => {
			try {
				let item = $$("elementBody" + element.id);
                if(item != null) {
					getElementIcon(element, ":Form");
					let evalues = element.elementValues !== undefined ? element.elementValues : null;
					if(evalues !== null) {
						element.elementValues.forEach(evalue => {
							try {
								let eparam = elementTypeMap.getElementParamTypeIdi18(evalue.id);
								let item3 = $$("elementBody" + element.id).getItem("elementBody" + element.id + "_" + eparam);
								if (item3 !== null) {
									let value = evalue.value !== undefined ? evalue.value === null ? "" : evalue.value : "";
									let alerted = evalue.alerted !== undefined ? evalue.alerted : -1 ;
									item3["measure"] = value;
									if (alerted !== -1) {
										item3["iconAlerted"] = evalue.alerted;
									}
									try {
										$$("elementBody" + element.id).updateItem("elementBody" + element.id + "_" + eparam, item3);    
									} catch (err) { console.log("Error: " + err.message); }                     
								}
							} catch (err) { console.log("Error: " + err.message); }           
						});
					}
				}
			} catch (err) { console.log("Error: " + err.message); }
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
				let header = data.elementType.alias + " " + data.alias;
				$$("elementHeader" + this.id).setValues({ value: header });
			} catch (e) {
				console.error("Error. id: "+this.id);
				console.error(e);
			}

			try {
				// ICON
				let iconoSvg = "";
				if(elementTypeMap.getElementType(data.elementType.id) === elementTypeMap.DMS_ELEMENT_TYPE) { //"ElementType:10"){
					iconoSvg = getElementSVG(data, 260, 60, ":Form");
				}else{
					iconoSvg = getElementSVG(data, 60, 60, ":Form");
				}
				$$("component"+this.id).addView({
					view: "template",
					template: iconoSvg,
					height: 80
				}, 1);
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
						let strem = stremMjpeg[0].value;						
						let camera = $$("component"+this.id).addView({    
							view:"iframe",
							borderless:true,
							height:358,
							width: 370,
							src:strem
						}, 1);
						
						let button={
							view:"button",
							value:"video fullscreen",
							click: function(){
							  webix.fullscreen.set($$(camera));
							}
						};
						$$("component" + this.id).addView(button, 2);	
					}
				}
			} catch (e) {
				console.error("Error. id: "+this.id);
				console.error(e);
			}			
			let valuesList = {};
			//BODY
			try {
				valuesList["id"] = {id: "elementBody" + this.id + "_id", iconAlerted: false, title: $.i18n("role.popup.id"), measure: data.id };
				valuesList["alias"] = {id: "elementBody" + this.id + "_alias", iconAlerted: false, title: $.i18n("role.popup.alias"), measure: data.alias };
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
						valuesList[elementTypeMap.getElementParamTypeIdi18(item.id)] = {id: "elementBody" + this.id + "_" + elementTypeMap.getElementParamTypeIdi18(item.id), iconAlerted: item.alerted, title: $.i18n(elementTypeMap.getElementParamTypeIdi18(item.id) + ".alias" ), measure: item.value === null ? "" : item.value };
				  });
				}

				this.columnsElementFormList[data.elementType.id].forEach(item => {
					$$("elementBody" + this.id).add(valuesList[item]);
				});

			} catch (e) {
				console.error("Error. id: "+this.id);
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
			return data;
		});
		let result = {
				id: "component"+this.id,
				rows: [
					this.header,
					this.body
				]
			};
		return result;
	}
}
