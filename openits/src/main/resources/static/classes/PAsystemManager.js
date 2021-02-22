import * as elementTypeMap from "../lib/elementTypeMap.js";
import {availablePictos} from "../data/pmvpicto.js";
import {panelData} from "../data/panelData.js";

//const SCALE_ZONE = 5; //Escala de la zona de pictogramas por si la queremos mas grande o mas pequeña 
const TOP_INIT = 5;
let datosAlias;

export default class PAsystemManager {
	constructor(id, idType, gestPlan,scale,values,orderId) {
		this.id=id;
		if (scale !== null && scale !== undefined) {
			this.scale = scale;
		} else {
			this.scale = 5;
		}
		this.elementTypeId = idType;
		this.panelManagerType = gestPlan;
		this.values = values;
		this.messages = [];
		this.zones_checked= [];
		this.messages_checked = [];
		this.messages_checked_alias = [];
		this.zones_checked_alias = [];
		this.orderId = orderId;
		var v_orderId = this.orderId;
		this.graphAll = `query { 
			getElement(elementId: "` + this.id + `"){
				id
				alias
				childs{id alias state}
				description
				state
				elementType {
			        id
			        alias
					commandElementTypes{
						id
						alias
						labelAlias
						commandElementTypeParams{
						  id
						  alias
						}
					}
		      	}
				elementValues {
					id 
					value 
					alerted 
					elementTypeParam {
						id 
						alias  
						representation
						graphics {id alias graphicType graphicValues {id value}}
					}
				}
			}
		}`;
		this.graphqlAll = webix.proxy("GraphQL", this.graphAll);
		this.graphElem = `query { 
			getElement(elementId: "` + this.id + `"){
				id
				alias
				childs{
				id 
				alias 
				state
				elementValues {
					id 
					value 
				}
				}
				elementValues {
					id 
					value 
				}
			}
		}`;

		this.graphElemType = `query {
			getElementType(elementTypeId: "` + this.elementTypeId + `")
			{
				id
				alias
				description
				commandElementTypes{  
					id 
					alias 
					labelAlias
					commandElementTypeParams { 
						id 
						alias 
					}
				}
				elementTypeParams{
				  	id
				  	alias
				  	description
					graphics {
						id 
						alias 
						graphicType       
						graphicValues {id value}
				  	}
				}
			}
		}`;
		this.graphql = webix.proxy("GraphQL", this.graphElem);
		this.graphqlEType = webix.proxy("GraphQL", this.graphElemType);
		
		
	}

	getInitComponent(){
		let initComponent = {
			view:"form",
			id:'paSignalForm' + this.id,
			elements:{}
		  };
		return initComponent;
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

	loadComponent(){
		this.elementReady('[view_id="' + "paSignalForm" + this.id + '"]').then((c) => {
			let spinnerDivToAdd = document.createElement("div");
			spinnerDivToAdd.setAttribute("id", "paSignalForm"  + this.id + "spinner");
			spinnerDivToAdd.setAttribute("class", "spinner");
			document
			.querySelector('[view_id="' + "paSignalForm"  + this.id + '"]')
			.prepend(spinnerDivToAdd);
		});
		

		this.graphql.load().then(elem => {
			this.values = [];
			elem.childs.forEach(child => {
				
				var elementValue = "ElementValue:" + child.id.split(":")[1]+":"+ child.id.split(":")[2]+":"+"5:1"
				child.elementValues.forEach(elementVal => {
					if(elementVal.id === elementValue)
						{
							this.values.push({
								id_zones: elementVal.value,
								zones: child.alias,
							});
						}
				});
				
				
			});
			
			
			
		this.graphqlEType.load().then(dataElement => {
				dataElement.elementTypeParams.forEach(element => {
					if(element.graphics !== null) {
						this.messages = [];
						element.graphics.forEach(item => 
						{
							this.messages.push({
								id_messages: item.graphicValues[0].value,
								messages: item.alias
			                });
						});
						
					}
						
				
			});
				
				var  grid1 = {   
						id: "table_zones",
						view:"datatable",
						   columns:[
						    { id:"check_zones", header:{ content:"masterCheckbox" }, checkValue:'on', uncheckValue:'off', template:"{common.checkbox()}", width:40},
						    { id:"id_zones",	sort:"string", header:"id",width:50},
						    { id:"zones",	sort:"string", header:"Zonas",width:200}
						  ],
						  autoheight:true,
						  autowidth:true,  
						  data: this.values
					};
				var  grid2 = {  
						id: "table_messages",
						view:"datatable",
						   columns:[
						    { id:"check_messages", header:{ content:"masterCheckbox" }, checkValue:'on', uncheckValue:'off', template:"{common.checkbox()}", width:40},
						    { id:"id_messages",	sort:"string", header:"id",width:50},
						    { id:"messages",	sort:"string", header:"Mensajes",width:200}
						  ],
						  autoheight:true,
						  autowidth:true,  
						  data: this.messages
					};
							          		          
				var  gridview ={
						 
						 rows:[
						  {
							  cols:[grid1,grid2]
						  },
						  {
							 id: this.id,
							 view:"button", 
							 value:"Enviar",
							 click:this.doActions
						  }
						]
					}
				webix.ui(gridview, $$('paSignalForm' + this.id));	 
			});
	
			
			
		});
		
		
		   

			


	}

	getComponent(){
		let result = {
			type:"space", cols:[
				{ view:"scrollview", scroll:"auto", body:this.grid, 
				width:64,
				height:120 },
				this.widgets
			]
		};
		return result; 
	}

	doActions() {
		var messages = [];
		var zones = [];
		var zones_checked =[];
		var messages_checked=[];
		var zones_checked_ids =[];
		var messages_checked_ids=[];
		
		$$('table_messages').eachColumn(function(id,node){ 		  
		if(id === "check_messages")
		{
			var node_data = node.node.childNodes;
			node_data.forEach(data => {
					messages_checked.push(data.firstChild.checked);
				});
		}	 
		if(id === "id_messages")
		{
			var node_data = node.node.childNodes;
			node_data.forEach(data => {
					messages_checked_ids.push(data.firstChild.data);
				});
		}
		 });
		
		$$('table_zones').eachColumn(function(id,node){ 		  
			if(id === "check_zones")
			{
				var node_data = node.node.childNodes;
				node_data.forEach(data => {
						zones_checked.push(data.firstChild.checked);
					});
			}	
			if(id === "id_zones")
			{
				var node_data = node.node.childNodes;
				node_data.forEach(data => {
						zones_checked_ids.push(data.firstChild.data);
					});
			}
			 });
		
	
		for (let index = 0; index < zones_checked.length; index++) 
		{
			if(zones_checked[index])
			{
				zones.push(zones_checked_ids[index]);
			}
		}
		

		for (let index = 0; index < messages_checked.length; index++) 
		{
			if(messages_checked[index])
			{
				messages.push(messages_checked_ids[index]);
			}
		}
		
		
	var command ={
			"commands": [
				{
					"commandElementTypeId":"CommandElementType:2:5",
					"elementId": this.data.id,
					"commandElementValues": [
					{
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:1",
						"value": zones
					},
					{
						"commandElementTypeParamId": "CommandElementTypeParam:2:5:2",
						"value": messages
					}
					]
				}
				
				]
	};

	 
	 var sendCommand = `mutation  sendCommandElement($commands: [CommandElementVO!]!){
								sendCommandElement(commands: $commands)  
								}`;
							var graphql = webix.proxy("GraphQL", sendCommand);
							graphql.save(command).then(() => {
								webix.message({type:"success", text:$.i18n("lbl_command_sended")});
								$$('PAsystemManager'  + "ElementType:5").hide();
								
								/*let auditLog={
									"logTypeId": "LogType:3",
									"logSubtypeId": "LogSubtype:1",
									"auditLogValues": [
										{
										"value": datos.v_commandTypeAlias,
										"logTypeParamId": "LogTypeParam:3:1"
										},{
										"value": datosAlias,
										"logTypeParamId": "LogTypeParam:3:2"
										},{
										"value": v_commands_values,
										"logTypeParamId": "LogTypeParam:3:3"
										}
									]
								};
								auditMutation(auditLog);*/
							});
	 
		
	}


	exportCommands(){
		//console.log("exportCommands01");
		let graphElemType = `query {
			getElementType(elementTypeId: "` + this.elementTypeId + `")
			{
				id
				alias
				description
				commandElementTypes{  
					id 
					alias
					labelAlias 
					commandElementTypeParams { 
						id 
						alias 
					}
				}
			}
		}`;
		let graphqlEType = webix.proxy("GraphQL", graphElemType);
		graphqlEType.load().then(data => {
			//console.log(data);
			let v_commandtypeId = null;
			let v_commandTypeAlias = null;
			let v_commands = [];
			data.commandElementTypes.forEach(element => {
				if (element.alias === "SEND MESSAGE") {
					v_commandtypeId = element.id;
					v_commandTypeAlias = element.labelAlias;
					element.commandElementTypeParams.forEach(item => {
						v_commands.push(item.id);
					});
				}
			});
			return v_commands.sort();
			//console.log("FIN");
		});
		
	}

	

}




