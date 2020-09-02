import * as elementTypeMap from "../lib/elementTypeMap.js";
import {availablePictos} from "../data/pmvpicto.js";
import {panelData} from "../data/panelData.js";

//const SCALE_ZONE = 5; //Escala de la zona de pictogramas por si la queremos mas grande o mas pequeña 
const TOP_INIT = 5;

export default class PanelManager {
	constructor(id, idType, gestPlan,scale) {
		this.id=id;
		if (scale !== null && scale !== undefined) {
			this.scale = scale;
		} else {
			this.scale = 5;
		}
		this.elementTypeId = idType;
		this.panelManagerType = gestPlan;
		this.graphAll = `query { 
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
				elementValues {
					id 
					value 
					alerted 
					elementTypeParam {
						id 
						alias  
						paramType 
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
					commandElementTypeParams { 
						id 
						alias 
						dataType
					}
				}
				elementTypeParams{
				  	id
				  	alias
				  	description
					paramType
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
			id:'pmvSignalForm',
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
		this.elementReady('[view_id="' + "pmvSignalForm" + '"]').then((c) => {
			let spinnerDivToAdd = document.createElement("div");
			spinnerDivToAdd.setAttribute("id", "pmvSignalForm"  + "spinner");
			spinnerDivToAdd.setAttribute("class", "spinner");
			document
			.querySelector('[view_id="' + "pmvSignalForm"  + '"]')
			.prepend(spinnerDivToAdd);
		});
		
		this.graphqlEType.load().then(dataElement => {
			this.doIt(dataElement);
			webix.ui(this.getComponent(), $$('pmvSignalForm'));
			this.doActions();
			//DATOS
			switch (this.panelManagerType) {
				case elementTypeMap.PANELMANAGER_TYPE_PANEL:
					this.graphql.load().then(elem => {
						elem.elementValues.forEach(evalues => {
							if (parseInt(evalues.id.split(":")[3]) > 1000 && parseInt(evalues.id.split(":")[4]) === 2) {  //ese numero 3 será una constante
								let code = this.config.cod_listaCells[parseInt(evalues.id.split(":")[3]) - 1001];
								if (code !== undefined) {
									if (code.split("_")[10] === "G") {
										evalues.value !== null ? evalues.value !== "" ? $$("panel_" + code.split("_")[1] + "_G").setValues({src: evalues.value, file_type: availablePictos[evalues.value].file_type }) : null : null;
									} else {
										for(let i=1;i<=parseInt(code.split("_")[11]);i++) {
											try {
												let value_text = null;
												value_text = evalues.value !== null ? evalues.value !== "" ? evalues.value.split(":$")[i - 1] !== undefined ? evalues.value.split(":$")[i - 1] : null : null : null;
												if (value_text !== null)
													$$("panel_" + code.split("_")[1] + "_T_L_" + (i - 1)).setValue(evalues.value.split(":$")[i - 1]);
											} catch (e) {
												console.log(e);
											}
										}
									}
								}
							}
						});
						document.getElementById("pmvSignalForm" +  "spinner").style.display = "none";
					});
					break;
				case elementTypeMap.PANELMANAGER_TYPE_MULTI_PANEL:
					document.getElementById("pmvSignalForm" +  "spinner").style.display = "none";
					break;
				case elementTypeMap.PANELMANAGER_TYPE_PLAN:
					document.getElementById("pmvSignalForm" +  "spinner").style.display = "none";
					break;
			}
		});

	}

	doIt(commandElementType) {
		this.config = configData(commandElementType,this.scale);
		//console.log(this.config);
		this.widgets = {
			id:"tree_zone_panelManager",
			rows:[
			  {
				id:"filterWid_panelManager",
				view:"text",
				labelPosition:"top"
			  },
			  {
				view:"tree",
				id:"list_panelManager",
				width:280,
				height:380,
				scroll:"auto",
				drag:true,
				template:"{common.icon()} <img src='./img/pictograms/#image#.#type_img#' style='float:right; margin:3px 4px 0px 1px; width:28px;visibility:#visibility#'> #value#",
				data: this.config.availablePictosList,
			  }
			]
		  };

		  this.grid = {
			view:"abslayout",
			id:"grid_panelManager",
			type:"space",
			drag:true,
			cols: this.config.listaCells,
			borderless: false,
		};
	}

	getComponent(){
		let result = {
			type:"space", cols:[
				{ view:"scrollview", scroll:"auto", body:this.grid, 
				width:this.config.maxTamX * this.scale + 5,
				height:this.config.maxTamY * this.scale },
				this.widgets
			]
		};
		return result; 
	}

	doActions() {
		var datos = this.config;
		var datosId = this.id;


		//SI ES UN PANEL DEL ELEMENTFORM PONEMOS EL BOTON SEÑALIZAR
		if(this.panelManagerType === elementTypeMap.PANELMANAGER_TYPE_PANEL) {
			$$("tree_zone_panelManager").addView({
				id: "tree_zone_panelManager_button",
				view:"button", value:"Señalizar", click:function(){
					let v_commands_values = [];
					let control = true;
					let tamanio = true;
					let v_values = [];
					let v_values_texts = {};
					let v_id_text_panels = []; // por si tenemos distintas zonas con texto como ocurre en el 28 
					let control_text = false;
					datos.v_id_list.forEach(element =>{
						if (element.split("_")[2] === "T") {
							v_id_text_panels.push("panel_" + element.split("_")[1] + "_T");
							control_text = true;
						}
						if (element.split("_")[2] === "G") {//Pictogramas
							v_id_text_panels.push("panel_" + element.split("_")[1] + "_G");
							control_text = true;
						}
					});

					if (control_text) {
						let unique_id_text_panels = [...new Set(v_id_text_panels)];
						unique_id_text_panels.forEach(element =>{
							v_values_texts[element] = null;  //para poder unir las lineas
						});
					}

					datos.v_id_list.forEach(element =>{
						if (element.split("_")[2] === "G") {//Pictogramas
							let valor = $$(element).data.src;
							(valor === undefined || valor === null) ? control = false : v_values_texts["panel_" + element.split("_")[1] + "_G"] = valor; //v_values.push(valor);
						}
						if (element.split("_")[2] === "T") {//Text
							let valor = $$(element).config.value;

							//QUITAMOS LOS :$ por si hubiera
							if (valor.search(":$")>0) {
								let v_array = valor.split(":$");
								let cadena = "";
								for (let i = 0;i<v_array.length; i++ ){
									cadena = cadena + v_array[i];
								}
								valor = cadena;
							}
							//SUSTITUIMOS LOS :%xxx:% por su caracter

							let v_input_value_no_especial = valor.split(":%");
							if (v_input_value_no_especial.length > 1) {
								let v_control = true;
								let v_input_array_string = [];
								let cadena = valor;
								let v_sumar = true;
								
								while(v_control) {
									let pos = 0;
									if (cadena.search(":%")>=0) {
										v_input_array_string.push(cadena.substring(pos,cadena.search(":%")));
										let cadena2 = cadena.substring(cadena.search(":%") + 2);
										if (cadena2.search(":%")>=0) {
											pos = cadena2.search(":%") + 2;
											//al ser cierre de cadena generamos una nueva cadena
											let v_code = String.fromCharCode(parseInt(cadena2.substring(0,cadena2.search(":%"))));
											v_input_array_string.push(v_code);
											cadena = cadena2.substring(pos);
										} else {
											//no hacemos nada, ya que aun no han cerrado la cadena especial, ni siquiera para sumar
											v_control = false;
											v_input_array_string.push(cadena);
										}
									} else {
										v_input_array_string.push(cadena);
										v_control = false;
									}
								}
								let texto = "";
								
								for(let i = 0; i < v_input_array_string.length; i++) {
									texto = texto + v_input_array_string[i];
								}
								//CON ESTO YA HEMOS FORMATEADO EL TEXTO A ENVIAR
								valor = texto;
							}
							let v_suma = 0;
							//COMPROBAMOS EL TAMAÑO DE LA CADENA RESULTANTE
							//Sumamos los que tenemos 
				
							for(let i = 0; i < valor.length; i++) {
								let ptype = datos.pictos_permitidos["panel_" + element.split("_")[1] + "_T"].find(x => parseInt(x[2].split("CH")[1],16) === valor[i].charCodeAt())[1];
								v_suma = v_suma + parseInt(ptype.split("x")[0]);
							}
			
							if(v_suma > parseInt(datos.cod_listaCells[parseInt(element.split("_")[1]) - 1].split("_")[12])) {
								tamanio = false;
							}

							if (v_values_texts["panel_" + element.split("_")[1] + "_T"] === null) {//es el primero 
								v_values_texts["panel_" + element.split("_")[1] + "_T"] = valor;
							} else {
								v_values_texts["panel_" + element.split("_")[1] + "_T"] = v_values_texts["panel_" + element.split("_")[1] + "_T"] + ":$" + valor;
							}
							//(valor === undefined || valor === null) ? control = false : v_values.push(valor);
						}
					});
					Object.entries(v_values_texts).map(([k, v]) => { v_values.push(v); });
					if (control && tamanio) { //Si están todos los pics rellenos , esto es igual para pictos que para lineas
						for (let i=0;i<v_values.length; i++) {
							v_commands_values.push({
								"commandElementTypeParamId": datos.commands[i],
								"value": v_values[i]
							});
						}
						var sendCommand = `mutation  sendCommandElement($commands: [CommandElementVO!]!){
							sendCommandElement(commands: $commands)  
							}`;
						var graphql = webix.proxy("GraphQL", sendCommand);
						graphql.save({
						"commands": [
							{
							"commandElementTypeId":datos.v_commandtypeId,
							"elementId": datosId,
							"commandElementValues": v_commands_values
							}
						]
						}).then(() => {
							webix.message({type:"success", text:$.i18n("lbl_panel_signaling_correctly")});
							$$("panelManager" + datos.elementId).destructor();
						});
					}else{
						if(!tamanio) {
							webix.message({type:"error", text:$.i18n("lbl_panel_signaling_tam_error")});
						} else {
							webix.message({type:"error", text:$.i18n("lbl_panel_signaling_error")});
						}
					}
				}
			}, 2);
		} else if(this.panelManagerType === elementTypeMap.PANELMANAGER_TYPE_MULTI_PANEL) {
			$$("tree_zone_panelManager").addView({
				id: "tree_zone_panelManager_button",
				view:"button", value:"Señalizar", click:function(){
					let v_commands_values = [];
					let control = true;
					let tamanio = true;
					let v_values = [];
					let v_values_texts = {};
					let v_id_text_panels = []; // por si tenemos distintas zonas con texto como ocurre en el 28 
					let control_text = false;
					datos.v_id_list.forEach(element => {
						if (element.split("_")[2] === "T") {
							v_id_text_panels.push("panel_" + element.split("_")[1] + "_T");
							control_text = true;
						}
						if (element.split("_")[2] === "G") {//Pictogramas
							v_id_text_panels.push("panel_" + element.split("_")[1] + "_G");
							control_text = true;
						}
					});

					if (control_text) {
						let unique_id_text_panels = [...new Set(v_id_text_panels)];
						unique_id_text_panels.forEach(element =>{
							v_values_texts[element] = null;  //para poder unir las lineas
						});
					}

					datos.v_id_list.forEach(element => {
						if (element.split("_")[2] === "G") {//Pictogramas
							let valor = $$(element).data.src;
							(valor === undefined || valor === null) ? control = false : v_values_texts["panel_" + element.split("_")[1] + "_G"] = valor; //v_values.push(valor);
						}
						if (element.split("_")[2] === "T") {//Text
							let valor = $$(element).config.value;

							//QUITAMOS LOS :$ por si hubiera
							if (valor.search(":$")>0) {
								let v_array = valor.split(":$");
								let cadena = "";
								for (let i = 0;i<v_array.length; i++ ){
									cadena = cadena + v_array[i];
								}
								valor = cadena;
							}
							//SUSTITUIMOS LOS :%xxx:% por su caracter

							let v_input_value_no_especial = valor.split(":%");
							if (v_input_value_no_especial.length > 1) {
								let v_control = true;
								let v_input_array_string = [];
								let cadena = valor;
								let v_sumar = true;
								
								while(v_control) {
									let pos = 0;
									if (cadena.search(":%")>=0) {
										v_input_array_string.push(cadena.substring(pos,cadena.search(":%")));
										let cadena2 = cadena.substring(cadena.search(":%") + 2);
										if (cadena2.search(":%")>=0) {
											pos = cadena2.search(":%") + 2;
											//al ser cierre de cadena generamos una nueva cadena
											let v_code = String.fromCharCode(parseInt(cadena2.substring(0,cadena2.search(":%"))));
											v_input_array_string.push(v_code);
											cadena = cadena2.substring(pos);
										} else {
											//no hacemos nada, ya que aun no han cerrado la cadena especial, ni siquiera para sumar
											v_control = false;
											v_input_array_string.push(cadena);
										}
									} else {
										v_input_array_string.push(cadena);
										v_control = false;
									}
								}
								let texto = "";
								
								for(let i = 0; i < v_input_array_string.length; i++) {
									texto = texto + v_input_array_string[i];
								}
								//CON ESTO YA HEMOS FORMATEADO EL TEXTO A ENVIAR
								valor = texto;
							}
							let v_suma = 0;
							//COMPROBAMOS EL TAMAÑO DE LA CADENA RESULTANTE
							//Sumamos los que tenemos 
				
							for(let i = 0; i < valor.length; i++) {
								let ptype = datos.pictos_permitidos["panel_" + element.split("_")[1] + "_T"].find(x => parseInt(x[2].split("CH")[1],16) === valor[i].charCodeAt())[1];
								v_suma = v_suma + parseInt(ptype.split("x")[0]);
							}
			
							if(v_suma > parseInt(datos.cod_listaCells[parseInt(element.split("_")[1]) - 1].split("_")[12])) {
								tamanio = false;
							}

							if (v_values_texts["panel_" + element.split("_")[1] + "_T"] === null) {//es el primero 
								v_values_texts["panel_" + element.split("_")[1] + "_T"] = valor;
							} else {
								v_values_texts["panel_" + element.split("_")[1] + "_T"] = v_values_texts["panel_" + element.split("_")[1] + "_T"] + ":$" + valor;
							}
							//(valor === undefined || valor === null) ? control = false : v_values.push(valor);
						}
					});
					Object.entries(v_values_texts).map(([k, v]) => { v_values.push(v); });
					if (control && tamanio) { //Si están todos los pics rellenos , esto es igual para pictos que para lineas
						for (let idt= 0; idt < datosId.length; idt++) {
							for (let i=0;i<v_values.length; i++) {
								v_commands_values.push({
									"commandElementTypeParamId": datos.commands[i],
									"value": v_values[i]
								});
							}
							var sendCommand = `mutation  sendCommandElement($commands: [CommandElementVO!]!){
								sendCommandElement(commands: $commands)  
								}`;
							var graphql = webix.proxy("GraphQL", sendCommand);
							graphql.save({
							"commands": [
								{
								"commandElementTypeId":datos.v_commandtypeId,
								"elementId": datosId[idt],
								"commandElementValues": v_commands_values
								}
							]
							}).then(() => {
								webix.message({type:"success", text:$.i18n("lbl_panel_signaling_correctly")});
								$$("panelManager" + datos.elementId).destructor();
							});
						}
						
					}else{
						if(!tamanio) {
							webix.message({type:"error", text:$.i18n("lbl_panel_signaling_tam_error")});
						} else {
							webix.message({type:"error", text:$.i18n("lbl_panel_signaling_error")});
						}
					}
				}
			}, 2);
		}

		//console.log(this.config.cod_listaCells);
		this.config.cod_listaCells.forEach(element => {
			if (element.split("_")[10] === "G") { 
				webix.DragControl.addDrop($$("panel_" + element.split("_")[1] + "_" + element.split("_")[10]).$view, {
					$drop:function(source, target, event){
						var id = target.getAttribute("view_id");

						var dnd = webix.DragControl.getContext();
						var value = dnd.from.getItem(dnd.source[0]);

						if (!datos.pictos_permitidos["panel_" + element.split("_")[1] + "_" + element.split("_")[10]].includes(value.image))
						{
							$$(id).setValues({src:null});
							webix.message({
								text: "PICTOGRAMA ERRONEO",
								type: "error",
								expire: 10000,
							});
						} else {
							$$(id).setValues({src:value.image, file_type: value.type_img });
						}
					}
				});
			} 
		});
		this.config.v_id_list.forEach(element => {
			if (element.split("_")[2] === "T") { 
				$$(element).attachEvent("onTimedKeyPress",function(obj){
					//console.log(obj);
				});
			}
		});
		$$("filterWid_panelManager").attachEvent("onTimedKeyPress",function(){
			$$("list_panelManager").filter("#value#",this.getValue());
		});

	}

	configExportData() {
		//Metodo para exportar los ids de los paneles para las clases externas puedan usarlos
		let panel_config = panelData[this.elementTypeId].panel;
		let cfgJson = JSON.parse(panel_config.replace('\"','"'));	
		let cdata = generateCodeList(cfgJson);
		let cellList = cdata[0];
		let idsList = [];
		cellList.forEach(item => {
			if (item.split("_")[10] === "G"){
				idsList.push("panel_" + item.split("_")[1] + "_G");
			} else {
				let contador = 0;
				for(let i=1;i<=parseInt(item.split("_")[11]);i++){
					idsList.push("panel_" + item.split("_")[1] + "_T_L_" + contador);
					contador += 1;
				}
			}
		});
		return idsList;
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
					commandElementTypeParams { 
						id 
						alias 
						dataType
					}
				}
			}
		}`;
		let graphqlEType = webix.proxy("GraphQL", graphElemType);
		graphqlEType.load().then(data => {
			//console.log(data);
			let v_commandtypeId = null;
			let v_commands = [];
			data.commandElementTypes.forEach(element => {
				if (element.alias === "SEND MESSAGE") {
					v_commandtypeId = element.id;
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

function generateCodeList(data){
	let maxTamX = 0;
	let maxTamY = 0;
	let contador_zonas = 0;
	let contador = 1;
	let cellList = [];
	data.Subpaneles.forEach(element => {
		let cellListSubpanel = [];
		contador_zonas += 1;
		let col = parseInt(element.NumZonas.split("x")[0]);
		let row = parseInt(element.NumZonas.split("x")[1]);
		let tamX = parseInt(element.Tamanio.split("x")[0]);
		let tamY = parseInt(element.Tamanio.split("x")[1]);
		//maxCol = maxCol + col;
		//maxRow = maxRow < row ? row : maxRow;
		maxTamX = maxTamX + tamX;
		maxTamY = maxTamY < tamY ? tamY : maxTamY;

		let elementos_zona = col * row;

		for (let i = 1; i <= elementos_zona; i++) {
			cellListSubpanel.push(contador_zonas + "_" + contador + "_" + col + "_" + row + "_" + tamX + "_" + tamY + "_");
			contador += 1;
		}
		let v_col = 1;
		let v_row= 1;
		for (let j = 0; j < cellListSubpanel.length; j++) {
			if (v_col === col) {
				cellListSubpanel[j] = cellListSubpanel[j] + v_col + "_" + v_row;
				v_col = 1;
				v_row +=1;
			} else {
				cellListSubpanel[j] = cellListSubpanel[j] + v_col + "_" + v_row;
				v_col +=1;
			}
		};

		let element_control = 0;
		element.Zonas.forEach(item => {
			let tamZoneX = parseInt(item.Tamanio.split("x")[0]);
			let tamZoneY = parseInt(item.Tamanio.split("x")[1]);
			let panel_type = item.Tipo === "Grafico" ? "G" : "T";
			let numLineas = parseInt(item.NumLineas);
			let tamLineas = parseInt(item.TamLineas);
			cellListSubpanel[element_control] = cellListSubpanel[element_control] + "_" + tamZoneX + "_" + tamZoneY + "_" + panel_type + "_" + numLineas.toString() + "_" + tamLineas.toString();
			element_control += 1;
		});
		cellList = [...cellList, ...cellListSubpanel];
	});
	return [cellList,maxTamX,maxTamY];

}

function configData(data,scale) {
	let panel_config = panelData[data.id].panel;
	let cfgJson = JSON.parse(panel_config.replace('\"','"'));
	let v_id_list = [];
	let v_left = 2; //padding inicial 
	let v_top = TOP_INIT;
	let v_zone = v_left;
	const SCALE_ZONE = scale;

	let cdata = generateCodeList(cfgJson);
	let cellList = cdata[0];
	let maxTamX = cdata[1];
	let maxTamY = cdata[2];

	let pictos_activos = {};
	let pictos_permitidos = {};
	let v_pictos_total = [];
	//Pictos permitidos por panel
	data.elementTypeParams.forEach(element => {
		let v_graphs = [];
		if (parseInt(element.id.split(":")[3]) > 1000 && element.paramType === 2) { 
			try {
				element.graphics.forEach(item => {
					if (cellList[parseInt(element.id.split(":")[3]) - 1000 -1].split("_")[10] === "G") {//Tipo picto 
						v_graphs.push(item.id.split(":")[2]);
						v_pictos_total.push(item.id.split(":")[2]);
					} else {
						let temporal = {};
						item.graphicValues.forEach(item3 => {
							temporal[item3.id.split(":")[3]] = item3.value;
						});
						v_graphs.push(temporal);
					}				
				});
			} catch {}
			pictos_permitidos["panel_" + (parseInt(element.id.split(":")[3]) - 1000).toString() + "_" + cellList[parseInt(element.id.split(":")[3]) - 1000 -1].split("_")[10]] = v_graphs;
		}
	});
	
	let unique_pictos = [...new Set(v_pictos_total)];
	let pictos_types = [];
	unique_pictos.forEach(element => {
		pictos_types.push(availablePictos[element].type);
	});
	let unique_pictos_types = [...new Set(pictos_types)];
	let availablePictosList = [];
	unique_pictos_types.forEach(element => {
		availablePictosList.push({ id: "panelManagerTree_Type_" + element, type: element, visibility:"hidden",image:"0", value:$.i18n("pictograms.type." + element), open:false, data:[]});
	});
	unique_pictos.forEach(element => {
		availablePictosList.forEach(item => {
			if (item.type ===availablePictos[element].type)
				item.data.push({ visibility:"visible",image: element, value:$.i18n("VmsGraphic:2:" + element), type_img: availablePictos[element].file_type, picto:element });
		});
	});
	let cells_s = [];
	let numElement = 0;

	//PINTAMOS
	cfgJson.Subpaneles.forEach(element => {
		element.Zonas.forEach(item => {
			let tamZoneX = parseInt(item.Tamanio.split("x")[0]);
			let tamZoneY = parseInt(item.Tamanio.split("x")[1]);
			let panel_type = item.Tipo;
			let numLineas = parseInt(item.NumLineas);
			let tamLineas = parseInt(item.TamLineas);
			let posx = parseInt(cellList[numElement].split("_")[8]) - 1;
			let posy = parseInt(cellList[numElement].split("_")[9]) - 1;
			let v_border = 2; //Borde negro del icono
			let v_padding = 4; // padding entre el icono y el borde negro
			let v_template = 8; //padding del template se come 2px por cada lado
			//Aqui dependiendo si es picto o texto lo modificaremos. 
			//console.log(pictos_activos);
			let v_src = pictos_activos["panel_" + cellList[numElement].split("_")[1] + "_" + cellList[numElement].split("_")[10]] !== undefined && pictos_activos["panel_" + cellList[numElement].split("_")[1] + "_" + cellList[numElement].split("_")[10]] !== null ? pictos_activos["panel_" + cellList[numElement].split("_")[1] + "_" + cellList[numElement].split("_")[10]] : null;
			//console.log(v_src);
			let v_file = null;
			try {
				v_file = availablePictos[v_src].file_type;
			} catch {}
			let v_body = null;
			if (panel_type === "Grafico") {
				v_body = { 
					view:"template", 
					id: "panel_" + cellList[numElement].split("_")[1] + "_" + cellList[numElement].split("_")[10], 
					data:{
						src: v_src,
						file_type: v_file
					},
					template:function(obj){
						let v_css = "";
						if(tamZoneX === tamZoneY){
							v_css = "width: -moz-available;";
						} else {
							if (tamZoneX > tamZoneY)
								v_css = "width: " + ((tamZoneY * SCALE_ZONE) - v_border - v_padding - v_template ).toString() + "px;";
						}
						if (obj.src !== undefined && obj.src !== null)
							return '<img src="./img/pictograms/'+obj.src+'.'+ obj.file_type +'" style="' + v_css + ' max-width:100%;border: '+ v_border +'px solid black;padding: ' + v_border + 'px;"/>';
						else
							return tamZoneX.toString() + "x" + tamZoneY.toString();
					}
				}
				v_id_list.push("panel_" + cellList[numElement].split("_")[1] + "_" + cellList[numElement].split("_")[10]);
			} else {
				let v_lineas = [];
				let v_form = null;
				let v_text = v_src !== null ? v_src.split(":$") : "";
				for (let i=0; i<numLineas; i++) {
					v_lineas.push(
						{
							view: "text", 
							id: "panel_" + cellList[numElement].split("_")[1] + "_" + cellList[numElement].split("_")[10] + "_L_" + i ,
							value: v_text[i],
							//attributes:{ maxlength: parseInt(cellList[numElement].split("_")[12]) },
							on: { 
								onBlur: function(obj) {
									//console.log(obj); 
								},
								onKeyPress: function(code, e) {
									let num_Element = parseInt($$(e.target).config.id.split("_")[1]);
									if (code !== 8) {   //NO HACEMOS NADA ES BACKSPACE
										let v_input = e.target;
										let tamMaxLine = parseInt(cellList[num_Element - 1 ].split("_")[12]);
										let v_suma = 0;
										let v_input_value_no_especial = v_input.value.split(":%");
										if (v_input_value_no_especial.length === 1) {
											let c_permitido = pictos_permitidos["panel_" + cellList[num_Element - 1].split("_")[1] + "_" + cellList[num_Element -1].split("_")[10]].find(x => parseInt(x[2].split("CH")[1],16) === e.key.charCodeAt());
											if (c_permitido) {
							
											//Sumamos los que tenemos 
												for(let i = 0; i < v_input.value.length; i++) {
													let ptype = pictos_permitidos["panel_" + cellList[num_Element - 1].split("_")[1] + "_" + cellList[num_Element -1].split("_")[10]].find(x => parseInt(x[2].split("CH")[1],16) === v_input.value[i].charCodeAt())[1];
													v_suma = v_suma + parseInt(ptype.split("x")[0]);
												}
												let ptype = pictos_permitidos["panel_" + cellList[num_Element - 1].split("_")[1] + "_" + cellList[num_Element -1].split("_")[10]].find(x => parseInt(x[2].split("CH")[1],16) === e.key.charCodeAt())[1];
												v_suma = v_suma + parseInt(ptype.split("x")[0]);
											
											//Si es un caracter permitido 
											} else {
												//caracter no permitido
												webix.message({type:"error", text:"CARACTER NO PERMITIDO"});
												//Eliminamos el caracter
												let val = $$(e.target).getValue();
												$$(e.target).setValueHere(val);
												//Eliminamos el caracter
												$$(e.target).define({
													value:val
												});
												$$(e.target).refresh();
											}
										} else {
											let v_control = true;
											let v_input_array_string = [];
											let cadena = v_input.value + e.key;
											let v_sumar = true;
											
											while(v_control) {
												let pos = 0;
												if (cadena.search(":%")>=0) {
													v_input_array_string.push(cadena.substring(pos,cadena.search(":%")));
													let cadena2 = cadena.substring(cadena.search(":%") + 2);
													if (cadena2.search(":%")>=0) {
														pos = cadena2.search(":%") + 2;
														//al ser cierre de cadena generamos una nueva cadena
														let v_code = String.fromCharCode(parseInt(cadena2.substring(0,cadena2.search(":%"))));
														//Comprobamos que el ascii está permitido 
														let c_permitido = pictos_permitidos["panel_" + cellList[num_Element - 1].split("_")[1] + "_" + cellList[num_Element -1].split("_")[10]].find(x => parseInt(x[2].split("CH")[1],16) === v_code.charCodeAt());
														if (c_permitido) {
															//existe está permitido
														} else {
															webix.message({type:"error", text:"CARACTER EN ASCII NO PERMITIDO"});
															v_control = false;
														}
														v_input_array_string.push(v_code);
														cadena = cadena2.substring(pos);
													} else {
														//no hacemos nada, ya que aun no han cerrado la cadena especial, ni siquiera para sumar
														v_control = false;
														v_input_array_string.push(cadena);
													}
												} else {
													v_input_array_string.push(cadena);
													v_control = false;
												}
											}
											let texto = "";
											
											for(let i = 0; i < v_input_array_string.length; i++) {
												texto = texto + v_input_array_string[i];
											}
											//Sumamos los que tenemos 
											for(let i = 0; i < texto.length; i++) {
												let ptype = pictos_permitidos["panel_" + cellList[num_Element - 1].split("_")[1] + "_" + cellList[num_Element -1].split("_")[10]].find(x => parseInt(x[2].split("CH")[1],16) === texto[i].charCodeAt())[1];
												v_suma = v_suma + parseInt(ptype.split("x")[0]);
											}
										
										//Si es un ca
										}
										if(v_suma > tamMaxLine) {
											webix.message({type:"error", text:"TAMAÑO MAXIMO SUPERADO"});
											//$$(e.target).define({
											//	css: { "background-color": "#fff6f6", "border-color": "#fea5a8"}
											//});
											//$$(e.target).refresh();
										} else {
											//$$(e.target).define({
											//	css: { "background": "#ffffff", "border-color": "#cccccc"}
											//});
											//$$(e.target).refresh();
										}
									}
								}
							}
						}
					);
					v_id_list.push("panel_" + cellList[numElement].split("_")[1] + "_" + cellList[numElement].split("_")[10] + "_L_" + i);
				}
				v_form = v_lineas;
				v_body = {					
					view: "form",
					id: "panel_" + cellList[numElement].split("_")[1] + "_FORM_",
					elements:v_form
				};
			}
			//PINTAR 
			//ZONAS
			if (numElement !== 0) {
				//si la zona es distinta aumentamos left y reiniciamos top )
				//si la zona es la misma y la x es distinta aumentamos left
				//si la zona es la misma y la x es la misma aunmentamos top 
				if (parseInt(cellList[numElement].split("_")[0]) !== parseInt(cellList[numElement - 1].split("_")[0]) ) {
					v_left = v_left + 5 + parseInt(cellList[numElement - 1].split("_")[8]) * SCALE_ZONE;
					v_zone = v_left;
					v_top = TOP_INIT;
				}
				if (parseInt(cellList[numElement].split("_")[0]) === parseInt(cellList[numElement - 1].split("_")[0]) && parseInt(cellList[numElement].split("_")[6]) > parseInt(cellList[numElement - 1].split("_")[6]) ) {
					v_left = v_left + 5 + parseInt(cellList[numElement - 1].split("_")[8]) * SCALE_ZONE;
				}

				if (parseInt(cellList[numElement].split("_")[0]) === parseInt(cellList[numElement - 1].split("_")[0]) && parseInt(cellList[numElement].split("_")[6]) <= parseInt(cellList[numElement - 1].split("_")[6]) ) {
					v_top = v_top + 5 + parseInt(cellList[numElement - 1].split("_")[9]) * SCALE_ZONE;	
					v_left = v_zone;	
				}
					cells_s.push({ 
						view:"panel", 
						css: { "text-align": "center !important" },
						top: v_top, left:v_left, width:SCALE_ZONE * tamZoneX, height: SCALE_ZONE * tamZoneY,
						cellValues: cellList[numElement],
						id: "panel_" + cellList[numElement].split("_")[1], 
						//header: tamZoneX + "x" + tamZoneY, 
						body: v_body,
					});

			} else {
				cells_s.push({ 
					view:"panel", 
					css: { "text-align": "center !important" },
					top: v_top, left: v_left, width:SCALE_ZONE * tamZoneX, height: SCALE_ZONE * tamZoneY,
					cellValues: cellList[numElement],
					id: "panel_" + cellList[numElement].split("_")[1], 
					body: v_body,
				});
			}
			numElement += 1;
		});
	});

	let v_commandtypeId = null;
	let v_commands = [];
	data.commandElementTypes.forEach(element => {
		if (element.alias === "SEND MESSAGE") {
			v_commandtypeId = element.id;
			element.commandElementTypeParams.forEach(item => {
				v_commands.push(item.id);
			});
		}
	});

	return { maxTamX: maxTamX, 
			 maxTamY: maxTamY, 
			 cod_listaCells: cellList, 
			 listaCells: cells_s, 
			 v_id_list: v_id_list,
			 pictos_permitidos: pictos_permitidos, 
			 pictos_activos:pictos_activos, 
			 commands:v_commands.sort(), 
			 availablePictosList: availablePictosList, 
			 v_commandtypeId: v_commandtypeId,
			 elementId: data.id
			};
}
