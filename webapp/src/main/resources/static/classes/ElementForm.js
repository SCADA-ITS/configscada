import { staticsIcons } from "../icons/synopticJson.js";

export default class ElementForm {
	constructor(id) {
		this.id = id;
		var graphElem = `query { 
			getElement(elementId: "` + this.id + `"){
				id
				alias
				description
				state
				stateLabel
				longitude
				latitude
				icon
				zoomIn
				zoomOut
				positionX
				positionY
				elementType {
			        id
			        alias
					description
					commandElementTypes{
						id
						alias
						description
						commandElementTypeParams{
						  id
						  alias
						  description
						  dataType
						}
					}
		      	}
				elementValues{
			        id
			        value
			        alerted
		         	elementTypeParam{
		          		id
		          		alias
		        	}
      			}
			}
		}`;
		this.graphql = webix.proxy("GraphQL", graphElem);
		this.header = {
			view: "template",
			type: "header",
			id: "elementHeader"+this.id,
			data: {
				value: ""
			},
			template: function (obj) {
				return obj.value;
			},
			css: { "text-align": "center !important" }
		};
		this.elementIcon = {
			view: "template",
			id: "template1" + this.id,
			data: {
				src: "",
			},
			template: function (obj) {
				return '<img src="' + obj.src + '" alt="Imagen no encontrada" width="75" height="75" />';
			},
			height: 80
		};

		this.form = {
			view: "form",
			id: "elementformresult" + this.id,
			width: 315,
			margin: 0,
			elements: [
				{ view: "text", name: "id", label: "id" },
				{ view: "text", name: "alias", label: "alias" },
				{ view: "text", name: "description", label: "description" },
				{ view: "text", name: "state", label: "state" },
				{ view: "text", name: "longitude", label: "longitude" },
				{ view: "text", name: "latitude", label: "latitude" },
				{ view: "text", name: "icon", label: "icon" }
			]
		};

		this.commands = {
			view: "form",
			id: "comandos" + this.id,
			elements: [
				{ view: "button", id: "command 1" + this.id, value: "" },
				{ view: "button", id: "command 2" + this.id, value: "" },
				{ view: "button", id: "command 3" + this.id, value: "" }
			]
		}
		
		this.camera= {     
                view:"iframe",
                borderless:true,
                height:358,
                width: 370,
                src:"http://admin:R4D7Revenga@192.168.88.250/cgi-bin/mjpg/video.cgi?channel=1&subtype=1"
            };
		this.button={
	            view:"button",
	            value:"video fullscreen",
	            click: function(){
	              webix.fullscreen.set($$("$iframe3"));
	            }
			};
	}

	getComponent() {
		this.graphql.load().then(data => {
			let icon = "";
			let header = "";
			icon = "img/" + staticsIcons[data.elementType.id].state[data.state].icon;
			header = data.elementType.alias + " " + data.alias;
			$$("elementformresult" + this.id).setValues({
				id: data.id,
				alias: data.alias,
				description: data.description,
				state: data.state,
				longitude: data.longitude,
				latitude: data.latitude,
				icon: data.icon
			});
			if(this.id!="Element:2:1"){
				$$("template1" + this.id).setValues({ src: icon });

			}
			$$("elementHeader" + this.id).setValues({ value: header });
//			if(this.id==="Element:2:1"){
//				$$("elementformresultElement:2:1").addView({
//		            view:"button",
//		            value:"video fullScreen",
//		            click: function(){
//		              webix.fullscreen.set($$("$iframe3"));
//		        }}, 0);
//			}
			if(data.elementType.commandElementTypes!=null && data.elementType.commandElementTypes.length>0){
				let comandos = [];
				for (let index = 0; index < data.elementType.commandElementTypes.length; index++) {
					comandos.push(data.elementType.commandElementTypes[index].alias);
				}
				$$("command 1" + this.id).setValue(comandos[0]);
				$$("command 2" + this.id).setValue(comandos[1]);
				$$("command 3" + this.id).setValue(comandos[2]);
				if (data.elementType.commandElementTypes.length == 2) {
					$$("command 3" + this.id).hide();
				} else if (data.elementType.commandElementTypes.length == 1) {
					$$("command 2" + this.id).hide() && $$("command 3" + this.id).hide();
				} else if (data.elementType.commandElementTypes == null) {
					$$("comandos" + this.id).hide();
				}
	
				$$("command 1" + this.id).attachEvent("onItemClick", function () {
					var sendCommand1 = `mutation  sendCommandElement($commands: [CommandElementVO!]!){
						sendCommandElement(commands: $commands)  
					}`;
					var graphql = webix.proxy("GraphQL", sendCommand1);
					graphql.save({
						"commands": [
							{
								"commandElementTypeId": data.elementType.commandElementTypes[0].id,
								"elementId": data.id
							}
						]
					}).then(() => {
//						webix.message("Done")
					});
				});
	
				$$("command 2" + this.id).attachEvent("onItemClick", function () {
					var sendCommand2 = `mutation  sendCommandElement($commands: [CommandElementVO!]!){
						sendCommandElement(commands: $commands)  
					}`;
					var graphql2 = webix.proxy("GraphQL", sendCommand2);
					graphql2.save({
						"commands": [
							{
								"commandElementTypeId": data.elementType.commandElementTypes[1].id,
								"elementId": data.id
							}
						]
					}).then(() => {
//						webix.message("Done")
					});
				});
	
				$$("command 3" + this.id).attachEvent("onItemClick", function () {
					var sendCommand3 = `mutation  sendCommandElement($commands: [CommandElementVO!]!){
						sendCommandElement(commands: $commands)  
					}`;
					var graphql3 = webix.proxy("GraphQL", sendCommand3);
					graphql3.save({
						"commands": [
							{
								"commandElementTypeId": data.elementType.commandElementTypes[2].id,
								"elementId": data.id
							}
						]
					}).then(() => {
	//					webix.message("Done")
					});
				});
			}else{
				$$("comandos" + this.id).hide();
				$$("comandos 2" + this.id).hide();
				$$("comandos 3" + this.id).hide();
			}
		});
		let genericForm = {
			rows: [
				this.header,
				this.elementIcon,
				this.form,
				this.commands,
				{}
			]
		};
		let specificForm = {
				rows: [
					this.header,
					this.camera,
							this.button,
					this.form,
					this.commands,
					{}
				]
			};
		return (this.id==="Element:2:1")?specificForm:genericForm;
	}
}