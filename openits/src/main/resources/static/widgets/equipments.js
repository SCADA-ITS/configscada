import EquipmentTable from "../classes/EquipmentTable.js"
import {optionElementType, columnsElementConfig, columnsElementList} from "../data/equipments.js"


export function equipments(){
	let widgetId=webix.uid();
	let equipmentId= "table_"+widgetId;
	let result ={
		rows:[{
			view:"combo",
			autoConfig:true,
			label: $.i18n("equipment.combo.label"),
			labelWidth:160,
			labelAlign:"center",
			icon: "mdi mdi-arrow-down",
			tooltip:"Element Type #value#",
			options: optionElementType,
			on:{
				onChange: function(newValue){
				let param = new EquipmentTable(newValue,1,null,null,columnsElementConfig,columnsElementList,widgetId);
				let measure = new EquipmentTable(newValue,2,null,null,columnsElementConfig,columnsElementList,widgetId);
				try {
					$$(widgetId).removeView(equipmentId);
				} catch  {}

				$$(widgetId).addView({
					id: equipmentId,
					view:"tabview", 
					cells:[
						{
							id:"equipmentMeasures",
							header: $.i18n("equipment.tabbar.measures"), 
							width:100,
							body: {
								view:"scrollview", body:{
									//padding:10,
									scroll:false, 
									//type:"clean", 
									rows:[
									measure.getComponent(),
									]
								}
							}
						},
						{
							id:"equipmentParameter",
							header: $.i18n("equipment.tabbar.parameter"),
							width:100,
							body:param.getComponent(),
						}
					],
			
				})
				}
			}
		}, {            
			id: widgetId, 
			rows:[{id: equipmentId}]
		}]
	};
	return result;
}