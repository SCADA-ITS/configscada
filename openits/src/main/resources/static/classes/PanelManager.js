import * as elementTypeMap from "../lib/elementTypeMap.js";
import {availablePictos} from "../data/graphics.js";

const SCALE_ZONE = 5; //Escala de la zona de pictogramas por si la queremos mas grande o mas pequeña 
const TOP_INIT = 5;

function configData (data) {
	let panel_config = data.elementValues.filter(element => elementTypeMap.getElementTypeParam(element.id) === elementTypeMap.PANEL_MANAGER_CONFIG )[0].value;
	let cfgJson = JSON.parse(panel_config.replace('\"','"'));
	
	let maxRow = 0;
	let maxCol = 0;
	let maxTamX = 0;
	let maxTamY = 0;
	let contador_zonas = 0;
	let contador = 1;
	let cellList = [];
	let v_id_list = [];
	cfgJson.Subpaneles.forEach(element => {
		let cellListSubpanel = [];
		contador_zonas += 1;
		let col = parseInt(element.NumZonas.split("x")[0]);
		let row = parseInt(element.NumZonas.split("x")[1]);
		let tamX = parseInt(element.Tamanio.split("x")[0]);
		let tamY = parseInt(element.Tamanio.split("x")[1]);
		maxCol = maxCol + col;
		maxRow = maxRow < row ? row : maxRow;
		maxTamX = maxTamX + tamX;
		maxTamY = maxTamY < tamY ? tamY : maxTamY;
		
		let elementos_zona = col * row;

		for (let i = 1; i <= elementos_zona; i++) {
			cellListSubpanel.push(contador_zonas + "_" + contador + "_" + col + "_" + row + "_" + tamX + "_" + tamY + "_");
			contador += 1;
		}
		let v_col = 1;
		for (let j = 0; j < cellListSubpanel.length; j++) {
			if (v_col === col) {
				cellListSubpanel[j] = cellListSubpanel[j] + v_col + "_";
				v_col= 1;
			} else {
				cellListSubpanel[j] = cellListSubpanel[j] + v_col + "_";
				v_col +=1;
			}
		};
		let v_row= 1;
		for (let j = 0; j < cellListSubpanel.length; j++) {
			if (v_row === row) {
				cellListSubpanel[j] = cellListSubpanel[j] + v_row;
				v_row= 1;
			} else {
				cellListSubpanel[j] = cellListSubpanel[j] + v_row;
				v_row +=1;
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

	let pictos_activos = {};
	data.elementValues.forEach(element => {
		if (parseInt(element.elementTypeParam.id.split(":")[3]) > 1000 && element.elementTypeParam.paramType ===2) {  //ese numero 3 será una constante
			pictos_activos["panel_" + (parseInt(element.elementTypeParam.id.split(":")[3]) - 1000).toString() + "_T"] = element.value;
		}
	});

	let pictos_permitidos = {};
	let v_pictos_total = [];

	data.elementValues.forEach(element => {
		let v_graphs = [];
		if (parseInt(element.elementTypeParam.id.split(":")[3]) > 1000 && element.elementTypeParam.paramType === 2) { 
			element.elementTypeParam.graphics.forEach(item => {
				if (item.id.split(":")[1] === "1") {//Tipo picto 
					v_graphs.push(item.id.split(":")[2]);
					v_pictos_total.push(item.id.split(":")[2]);
				}					
			});
			pictos_permitidos["panel_" + (parseInt(element.elementTypeParam.id.split(":")[3]) - 1000).toString() + "_T"] = v_graphs;
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
	let v_left = 2; //padding inicial 
	let v_top = TOP_INIT;

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
			let v_src = pictos_activos["panel_" + cellList[numElement].split("_")[1] + "_T"] !== undefined && pictos_activos["panel_" + cellList[numElement].split("_")[1] + "_T"] !== null ? pictos_activos["panel_" + cellList[numElement].split("_")[1] + "_T"] : null;
			let v_file = null;
			try {
				v_file = availablePictos[v_src].file_type;
			} catch {}
			let v_body = null;
			if (panel_type === "Grafico") {
				v_body = { 
					view:"template", 
					id: "panel_" + cellList[numElement].split("_")[1] + "_T", 
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
				v_id_list.push("panel_" + cellList[numElement].split("_")[1] + "_T");
			} else {
				let v_lineas = [];
				for (let i=0; i<numLineas; i++) {
					v_lineas.push(
						{
							view: "text", 
							id: "panel_" + cellList[numElement].split("_")[1] + "_L_" + i ,
							on: { "onBlur": function() { 
								}
							}
						}
					);
				}
				v_body = v_lineas;
				v_id_list.push("panel_" + cellList[numElement].split("_")[1] + "_L_" + i);
			}

			
			if (numElement !== 0) {
				//si la zona es distinta aumentamos left)
				//si la zona es la misma y la x es distinta aumentamos left
				//si la zona es la misma y la x es la misma aunmentamos top 
				if (parseInt(cellList[numElement].split("_")[0]) !== parseInt(cellList[numElement - 1].split("_")[0]) ) {
					v_left = v_left + 5 + parseInt(cellList[numElement - 1].split("_")[8]) * SCALE_ZONE;
					v_top = TOP_INIT;
				}
				if (parseInt(cellList[numElement].split("_")[0]) === parseInt(cellList[numElement - 1].split("_")[0]) && parseInt(cellList[numElement].split("_")[2]) !== parseInt(cellList[numElement - 1].split("_")[2]) ) {
					v_left = v_left + 5 + parseInt(cellList[numElement - 1].split("_")[8]) * SCALE_ZONE;
				}

				if (parseInt(cellList[numElement].split("_")[0]) === parseInt(cellList[numElement - 1].split("_")[0]) && parseInt(cellList[numElement].split("_")[2]) === parseInt(cellList[numElement - 1].split("_")[2]) ) {
					v_top = v_top + 5 + parseInt(cellList[numElement - 1].split("_")[9]) * SCALE_ZONE;				
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
	data.elementType.commandElementTypes.forEach(element => {
		if (element.alias === "SEND MESSAGE") {
			v_commandtypeId = element.id;
			element.commandElementTypeParams.forEach(item => {
				v_commands.push(item.id);
			});
		}
	});

	return { maxTamX: maxTamX, 
			 maxTamY: maxTamY, 
			 cols: maxCol, 
			 rows: maxRow, 
			 zonas: contador_zonas, 
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


export default class PanelManager {
	constructor(id, commandElementType) {
		this.id = id;
		this.commandElementType = commandElementType;
		this.config = configData(this.commandElementType);
		this.values = this.config.pictos_activos;
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
				width:this.config.maxTamX * SCALE_ZONE + 5,
				height:this.config.maxTamY * SCALE_ZONE },
				this.widgets
			]
		};
		return result; 
	}

	doActions() {
		var datos = this.config;

		$$("tree_zone_panelManager").addView({
			id: "tree_zone_panelManager_button",
			view:"button", value:"Señalizar", click:function(){
				let v_commands_values = [];
				let control = true;
				let v_values = [];
				datos.v_id_list.forEach(element =>{
					if (element.split("_")[2] === "T") {//Pictogramas
						let valor = $$(element).data.src;
						(valor === undefined || valor === null) ? control = false : v_values.push(valor);
					}
				});
				if (control) { //Si están todos los pics rellenos , esto es igual para pictos que para lineas
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
						"elementId": datos.elementId,
						"commandElementValues": v_commands_values
						}
					]
					}).then(() => {
						webix.message({type:"success", text:$.i18n("lbl_panel_signaling_correctly")});
						$$("panelManager" + datos.elementId).destructor();
					});
				}else{
					webix.message({type:"error", text:$.i18n("lbl_panel_signaling_error")});
				}
			}
		}, 2);

		this.config.cod_listaCells.forEach(element => {
			if (element.split("_")[10] === "G") { 
				webix.DragControl.addDrop($$("panel_" + element.split("_")[1] + "_T").$view, {
					$drop:function(source, target, event){
						var id = target.getAttribute("view_id");
						var dnd = webix.DragControl.getContext();
						var value = dnd.from.getItem(dnd.source[0]);
						if (!datos.pictos_permitidos["panel_" + element.split("_")[1] + "_T"].includes(value.image))
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

		$$("filterWid_panelManager").attachEvent("onTimedKeyPress",function(){
			$$("list_panelManager").filter("#value#",this.getValue());
		});
	}
}
