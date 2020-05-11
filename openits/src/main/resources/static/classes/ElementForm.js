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
		         	elementTypeParam{
		          		id
		          		alias
		        	}
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
			id: "elementBody" + this.id,
			view: "form",
			height: 250,
			width: 400,
			scroll: true,
			margin: 0,
			elements: [
				{ view: "text", name: "id", label: "id" },
				{ view: "text", name: "description", label: "description" }
			]
		};
		
	}

	getComponent() {
		this.graphql.load().then(data => {
			//header
			try {
				let header = data.elementType.alias + " " + data.alias;
				$$("elementHeader" + this.id).setValues({ value: header });
			} catch (e) {
				console.error("Error. id: "+this.id);
				console.error(e);
			}
			//icon
			try {
				//FIXME Codigo hecho a medida para camara Element:2:1, quitar else!!
				if(this.id=="Element:2:1"){
					let camera = $$("component"+this.id).addView({    
						view:"iframe",
						borderless:true,
						height:358,
						width: 370,
						src:"http://admin:R4D7Revenga@192.168.88.250/cgi-bin/mjpg/video.cgi?channel=1&subtype=1"
					}, 1);
					let button={
						view:"button",
						value:"video fullscreen",
						click: function(){
						  webix.fullscreen.set($$(camera));
						}
					};
					$$("component"+this.id).addView(button, 2);
				}else if(data.elementType.id!="ElementType:10"){
					$$("component"+this.id).addView({
						view: "template",
						data: {
							src: "img/" + staticsIcons[data.elementType.id].state[data.state].icon,
						},
						template: function (obj) {
							return '<img src="' + obj.src + '" alt="Imagen no encontrada" width="75" height="75" />';
						},
						height: 80
					}, 1);
				}
			} catch (e) {
				console.error("Error. id: "+this.id);
				console.error(e);
			}
			//body
			try {
				$$("elementBody" + this.id).setValues({
					id: data.id,
					description: data.description
				});
			} catch (e) {
				console.error("Error. id: "+this.id);
				console.error(e);		
			}
				
			//Measures
			try {
				if(data.elementValues) {
					data.elementValues.forEach(item => {
						var value = null;		
						if(item.alerted != null && item.alerted){
							value = "!!" + item.value + "!!";
						}else{
							value = item.value;
						}	
						$$("elementBody"+this.id).addView({ 
							view: "text", 
							name: item.elementTypeParam.alias, 
							label: item.elementTypeParam.alias,
							value: value
						}, -1);
						
				  });
				}
			} catch (e) {
				console.error("Error. id: "+this.id);
				console.error(e);
			}
			
			//Commands
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
