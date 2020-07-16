import * as elementTypeMap from "../lib/elementTypeMap.js";

var availablePictos = [
	{ visibility:"hidden",image:"0", value:"Velocidad", open:false, data:[
		{ visibility:"visible",image:"315", value:"40 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/315.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"315" } },
		{ visibility:"visible",image:"316", value:"50 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/316.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"316" } },
		{ visibility:"visible",image:"317", value:"60 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/317.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"317" } },
		{ visibility:"visible",image:"318", value:"70 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/318.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"318" } },
		{ visibility:"visible",image:"319", value:"80 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/319.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"319" } },
		{ visibility:"visible",image:"320", value:"90 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/320.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"320" } },
		{ visibility:"visible",image:"321", value:"100 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/321.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"321" } },
		{ visibility:"visible",image:"322", value:"110 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/322.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"322" } },
		{ visibility:"visible",image:"323", value:"120 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/323.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"323" } },
		{ visibility:"visible",image:"324", value:"130 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/324.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"324" } },
		{ visibility:"visible",image:"505", value:"Fin 40 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/505.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"505" } }, 
		{ visibility:"visible",image:"506", value:"Fin 50 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/506.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"506" } }, 
		{ visibility:"visible",image:"507", value:"Fin 60 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/507.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"507" } }, 
		{ visibility:"visible",image:"508", value:"Fin 70 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/508.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"508" } }, 
		{ visibility:"visible",image:"509", value:"Fin 80 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/509.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"509" } }, 
		{ visibility:"visible",image:"510", value:"Fin 90 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/510.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"510" } }, 
		{ visibility:"visible",image:"511", value:"Fin 100 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/511.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"511" } }, 
		{ visibility:"visible",image:"512", value:"Fin 110 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/512.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"512" } }, 
		{ visibility:"visible",image:"513", value:"Fin 120 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/513.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"513" } }, 
		{ visibility:"visible",image:"514", value:"Fin 130 km/h", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/514.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"514" } }   
		]},
	{ visibility:"hidden",image:"0",value:"Especiales", open:false, data:[
    { visibility:"visible", image:"743", value:"Rigth", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/743.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"743" }},
    { visibility:"visible",image:"744", value:"Left", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/744.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"744" } },
    { visibility:"visible",image:"745", value:"Allow", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/745.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"745" } },
    { visibility:"visible",image:"746", value:"Block", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/746.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"746" } },   
    { visibility:"visible",image:"152", value:"Stop", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/152.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"152" } },    
	{ visibility:"visible",image:"328", value:"Adelantamiento", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/328.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"328" } },
	{ visibility:"visible",image:"501", value:"Fin Prohibición", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/501.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"501" } }    
  ]},
  { visibility:"hidden",image:"0",value:"Peligro", open:false, data:[
    { visibility:"visible",image:"63", value:"Estrechamiento", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/63.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"63" } },
    { visibility:"visible",image:"64", value:"Estrecha der.", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/64.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"64" } },
    { visibility:"visible",image:"65", value:"Estrecha izq", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/65.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"65" } },
    { visibility:"visible",image:"66", value:"Obras", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/66.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"66" } },
	{ visibility:"visible",image:"67", value:"Grava", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/67.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"67" } },
	{ visibility:"visible",image:"77", value:"Viento izq.", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/77.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"77" } },
	{ visibility:"visible",image:"78", value:"Viento der.", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/78.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"78" } },
	{ visibility:"visible",image:"80", value:"Atasco", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/80.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"80" } },
	{ visibility:"visible",image:"81", value:"Accidente", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/81.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"81" } },
	{ visibility:"visible",image:"82", value:"Desprendimientos", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/82.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"82" } },
	{ visibility:"visible",image:"84", value:"Hielo", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/84.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"84" } },
	{ visibility:"visible",image:"85", value:"Peligro", dx:1, dy:1, body: { view:"template", template:" <img src='./img/pictograms/85.png' style='width: 86%;border: 3px solid black;padding: 10px;'/>", picto:"85" } }    
	]}
];

const SCALE_ZONE = 3; //Escala de la zona de pictogramas por si la queremos mas grande o mas pequeña 
const TOP_INIT = 50;

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
	data.elementValues.forEach(element => {
		let v_graphs = [];
		if (parseInt(element.elementTypeParam.id.split(":")[3]) > 1000 && element.elementTypeParam.paramType ===2) { 
			element.elementTypeParam.graphics.forEach(item => {
				item.graphicValues.forEach(item2 => {
					v_graphs.push(item2.value);
				});
					
			});
			pictos_permitidos["panel_" + (parseInt(element.elementTypeParam.id.split(":")[3]) - 1000).toString() + "_T"] = v_graphs;
		}
	});

	let cells_s = [];
	let numElement = 0;
	let v_cols = [];
	let v_left = 0;
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
			//Aqui dependiendo si es picto o texto lo modificaremos. 
			let v_body = null;
			if (panel_type === "Grafico") {
				v_body = { 
					view:"template", 
					id: "panel_" + cellList[numElement].split("_")[1] + "_T", 
					//template:" <img src='./img/pictograms/63.png' style='max-width:100%;border: 3px solid black;padding: 10px;'/>", 
					//template:" <img src='' style='max-width:100%;border: 3px solid black;padding: 10px;'/>", 
					//picto:"63",
					data:{
						src:pictos_activos[cellList[numElement].split("_")[1] + "_T"], 
					},
					template:function(obj){
						if (obj.src !== undefined && obj.src !== null)
							return '<img src="./img/pictograms/'+obj.src+'.png" style="max-width:100%;border: 3px solid black;padding: 10px;"/>';
						else
							return tamZoneX.toString() + "x" + tamZoneY.toString();
					} 
				}
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

/*

        "commandElementTypes": [
          {
            "id": "CommandElementType:2:28",
            "alias": "SEND MESSAGE",
            "commandElementTypeParams": [
              {
                "id": "CommandElementTypeParam:2:28:1",
                "alias": "PICTO",
                "dataType": 3
              },
              {
                "id": "CommandElementTypeParam:2:28:2",
                "alias": "AUX_TEXT",
                "dataType": 2
              },
              {
                "id": "CommandElementTypeParam:2:28:3",
                "alias": "MAIN_TEXT",
                "dataType": 2
              }
            ]
          }

*/

	let v_commands = [];
	data.elementType.commandElementTypes.forEach(element => {
		if (element.alias === "SEND MESSAGE") {
			element.commandElementTypeParams.forEach(item => {
				v_commands.push(item.id);
			});
		}
	});

	return { maxTamX: maxTamX, maxTamY: maxTamY, cols: maxCol, rows: maxRow, zonas: contador_zonas, cod_listaCells: cellList, listaCells: cells_s, pictos_permitidos: pictos_permitidos, pictos_activos:pictos_activos, commands:v_commands };
}


export default class PanelManager {
	constructor(id, commandElementType) {
		this.commandElementType = commandElementType;
		this.config = configData(this.commandElementType);
		this.widgets = {
			rows:[
			  {
				id:"filterWid",
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
				template:"{common.icon()} <img src='./img/pictograms/#image#.png' style='float:right; margin:3px 4px 0px 1px; width:28px;visibility:#visibility#'> #value#",
				data: availablePictos,
			  },
			  {
				view:"button", value:"Señalizar", click:function(){
					if($$("1x1") && $$("1x1").config.picto){
						var sendCommand = `mutation  sendCommandElement($commands: [CommandElementVO!]!){
							sendCommandElement(commands: $commands)  
							}`;
						var graphql = webix.proxy("GraphQL", sendCommand);
						graphql.save({
						"commands": [
							{
							"commandElementTypeId":"CommandElementType:2:26",
							"elementId": "Element:26:1",
							"commandElementValues": [
								{
								"commandElementTypeParamId": commandElementType.commandElementTypeParams[0].id,
								"value": $$("1x1").config.picto
								}
							]
							}
						]
						}).then(() => {
							webix.message({type:"success", text:$.i18n("lbl_panel_signaling_correctly")});
							$$("panelManager" + id).hide();
						});
					}else{
						webix.message({type:"error", text:$.i18n("lbl_panel_signaling_error")});
					}
				 
				}
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
		this.config.cod_listaCells.forEach(element => {
			if (element.split("_")[10] === "G") { 
				webix.DragControl.addDrop($$("panel_" + element.split("_")[1] + "_T").$view, {
					$drop:function(source, target, event){
						var id = target.getAttribute("view_id");
						var dnd = webix.DragControl.getContext();
						var value = dnd.from.getItem(dnd.source[0]).image;
						if (!datos.pictos_permitidos["panel_" + element.split("_")[1] + "_T"].includes(value))
						{
							$$(id).setValues({src:null});
							webix.message({
								text: "PICTOGRAMA ERRONEO",
								type: "error",
								expire: 10000,
							});
						} else {
							$$(id).setValues({src:value});
						}
					}
				});
			}
		});

		$$("filterWid").attachEvent("onTimedKeyPress",function(){
			$$("list").filter("#value#",this.getValue());
		});
	}
}
