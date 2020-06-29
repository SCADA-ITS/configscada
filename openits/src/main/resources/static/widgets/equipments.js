
import EquipmentTable from "../classes/EquipmentTable.js"
import {optionElementTypeAusol, columnsElementConfigAusol, columnsElementListAusol} from "../project/ausol/data/equipments.js"
import {optionElementTypeBratislava, columnsElementConfigBratislava, columnsElementListBratislava} from "../project/bratislava/data/equipments.js"
let optionElementType = null;
let columnsElementConfig = null;
let columnsElementList = null;
if (project) {
	optionElementType = optionElementTypeBratislava;
	columnsElementConfig = columnsElementConfigBratislava;
	columnsElementList = columnsElementListBratislava;
  } else { 
	optionElementType = optionElementTypeAusol;
	columnsElementConfig = columnsElementConfigAusol;
	columnsElementList = columnsElementListAusol;
  }

function build_table(){
	
	let elementTypeId = $$("combo_element_type").getValue();
	let param = new EquipmentTable(elementTypeId,1,null,null,columnsElementConfig,columnsElementList);
	let measure = new EquipmentTable(elementTypeId,2,null,null,columnsElementConfig,columnsElementList);
	try {
		$$("equipmentsView").removeView("tabview_equipmentsView");
	} catch  {}

	$$("equipmentsView").addView({
		id: "tabview_equipmentsView",
		view:"tabview", 
		cells:[
			{
				id:"equipmentMeasures",
				header: $.i18n("equipment.tabbar.measures"), 
				width:100,
				body:measure.getComponent(),
			},
			{
				id:"equipmentParameter",
				header: $.i18n("equipment.tabbar.parameter"),
				width:100,
				body:param.getComponent(),
			}
		],

	});
}

var comboElementType = {
  id:"combo_element_type",	
  view:"combo",
  autoConfig:true,
  label: $.i18n("equipment.combo.label"),
  labelWidth:160,
  labelAlign:"center",
  icon: "mdi mdi-arrow-down",
  tooltip:"Element Type #value#",
  options: optionElementType,
  on:{
	  onChange: build_table
    }
};


var equipmentsView = {            
	id: "equipmentsView", 
	rows:[{id: "tabview_equipmentsView"}]
};

export let equipments = {
	rows:[comboElementType, equipmentsView]
};
