import { getElementSVG, getElementIcon} from "../lib/iconGenerator.js";

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
		          		description
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
				let iconoSvg = getElementSVG(data, 60, 60);
				$$("component"+this.id).addView({
					view: "template",
					template: iconoSvg,
					height: 80
				}, 1);
				getElementIcon(data);
				/*$$("component"+this.id).addView({
					view: "template",
					data: {
						src: "img/" + staticsIcons[data.elementType.id].state[data.state].icon,
					},
					template: function (obj) {
						return '<img src="' + obj.src + '" alt="Imagen no encontrada" width="75" height="75" />';
					},
					height: 80
				}, 1);*/
			} catch (e) {
				console.error("Error. id: "+this.id);
				console.error(e);
			}
			
			try {
			// STREAM
				if(data.elementType.id == "ElementType:2"){
					
					let stremMjpeg = null;
					
					if(data.elementValues !== null){
						
						stremMjpeg = data.elementValues.filter(elementValue => elementValue.elementTypeParam != null && elementValue.elementTypeParam.alias === "stream_mjpeg" );	
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

			//BODY
			try {
				$$("elementBody" + this.id).setValues({
					id: data.id,
					description: data.description
				});
			} catch (e) {
				console.error("Error. id: "+this.id);
				console.error(e);		
			}
				
			//MEASURES
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
							value: value,
							readonly: true
						}, -1);
						
				  });
				}
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
