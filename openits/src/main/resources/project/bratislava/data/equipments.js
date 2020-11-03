export let optionElementType = [
	{id:"ElementType:1", value: $.i18n("ElementType:1.alias")},
	{id:"ElementType:2", value: $.i18n("ElementType:2.alias")},
	{id:"ElementType:25", value: $.i18n("ElementType:25.alias")},
	{id:"ElementType:34", value: $.i18n("ElementType:34.alias")},
	{id:"ElementType:41", value: $.i18n("ElementType:41.alias")},
	{id:"ElementType:42", value: $.i18n("ElementType:42.alias")},
	{id:"ElementType:43", value: $.i18n("ElementType:43.alias")},
	{id:"ElementType:44", value: $.i18n("ElementType:44.alias")},
	{id:"ElementType:45", value: $.i18n("ElementType:45.alias")},
	{id:"ElementType:46", value: $.i18n("ElementType:46.alias")},
	{id:"ElementType:47", value: $.i18n("ElementType:47.alias")}
]

export let columnsElementList = {
	"ElementType:1": {
		1:[
			"alias",
            "ElementTypeParam:1:1:2",
            "ElementTypeParam:1:1:4",
            "ElementTypeParam:1:1:3",
            "ElementTypeParam:1:1:1",
            "ElementTypeParam:1:1:5"
		],
		2:[
			"alias", 
			"state",
			"ElementTypeParam:1:2:3",
			"ElementTypeParam:1:2:5",
			"ElementTypeParam:1:2:12",
			"ElementTypeParam:1:2:11",
			"ElementTypeParam:1:2:9",
			"ElementTypeParam:1:2:8",
			"ElementTypeParam:1:2:7",
			"ElementTypeParam:1:2:6",
			"ElementTypeParam:1:2:14",
			"ElementTypeParam:1:2:13",
			"ElementTypeParam:1:2:15",
			"ElementTypeParam:1:2:10",
			"ElementTypeParam:1:2:21",
			"ElementTypeParam:1:2:16",
			"ElementTypeParam:1:2:2",
			"ElementTypeParam:1:2:1",
			"ElementTypeParam:1:2:18",
			"ElementTypeParam:1:2:17",
			"ElementTypeParam:1:2:4",
			"ElementTypeParam:1:2:19",
			"ElementTypeParam:1:2:22",
			"ElementTypeParam:1:2:20"
		]
	},
	"ElementType:2": { 
		1:[
			"alias",
			"ElementTypeParam:2:1:1",
			"ElementTypeParam:2:1:2",
			"ElementTypeParam:2:1:4",
			"ElementTypeParam:2:1:5",
            "ElementTypeParam:2:1:6",
            "ElementTypeParam:2:1:7",
            "ElementTypeParam:2:1:8",
            "ElementTypeParam:2:1:10"
		],
		2:[
			"alias",
			"state",
		]
	},
	"ElementType:4": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:4:1:1": {
				"header": [ $.i18n("ElementTypeParam:4:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:4:1:2": {
				"header": [ $.i18n("ElementTypeParam:4:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			}
             
		},
		2:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"state": {
				"header": [ $.i18n("equipment.table.state"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			}
		}
	},
	"ElementType:25": { 
		1:[
			"alias",
			"ElementTypeParam:25:1:1",
			"ElementTypeParam:25:1:2",
			"ElementTypeParam:25:1:3",
			"ElementTypeParam:25:1:4"
		],
		2:[
			"alias",
			"state",
		]
	},
	"ElementType:34": { 
		1:[
			"alias",
			"ElementTypeParam:34:1:1",
			"ElementTypeParam:34:1:2",
			"ElementTypeParam:34:1:3"
		],
		2:[
			"alias",
			"state",
		]
	},
	"ElementType:41": { 
		1:[
			"alias",
			"ElementTypeParam:41:1:1",
			"ElementTypeParam:41:1:2",
			"ElementTypeParam:41:1:3"
		],
		2:[
			"alias",
			"state",
		]
	},
	"ElementType:42": { 
		1:[
			"alias",
			"ElementTypeParam:42:1:1",
			"ElementTypeParam:42:1:2",
			"ElementTypeParam:42:1:3"
		],
		2:[
			"alias",
			"state",
		]
	},
	"ElementType:43": { 
		1:[
			"alias",
			"ElementTypeParam:43:1:1",
			"ElementTypeParam:43:1:2",
			"ElementTypeParam:43:1:3"
		],
		2:[
			"alias",
			"state",
		]
	},
	"ElementType:44": { 
		1:[
			"alias",
			"ElementTypeParam:44:1:1",
			"ElementTypeParam:44:1:2",
			"ElementTypeParam:44:1:3"
		],
		2:[
			"alias",
			"state",
		]
	},
	"ElementType:45": { 
		1:[
			"alias",
			"ElementTypeParam:45:1:1",
			"ElementTypeParam:45:1:2",
			"ElementTypeParam:45:1:3"
		],
		2:[
			"alias",
			"state",
		]
	},
	"ElementType:46": { 
		1:[
			"alias",
			"ElementTypeParam:46:1:1",
			"ElementTypeParam:46:1:2",
			"ElementTypeParam:46:1:3"
		],
		2:[
			"alias",
			"state",
		]
	},
	"ElementType:47": { 
		1:[
			"alias",
			"ElementTypeParam:47:1:1",
			"ElementTypeParam:47:1:2",
			"ElementTypeParam:47:1:3"
		],
		2:[
			"alias",
			"state",
		]
	}
}

export let columnsElementConfig = {

	"ElementType:1": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:1:1:1003": { //"MANGO SESSION_UID"
				"header": [ $.i18n("ElementTypeParam:1:1:1003.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:1:1:2": { //"model"
				"header": [ $.i18n("ElementTypeParam:1:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:1:1:4": { //"section"
				"header": [ $.i18n("ElementTypeParam:1:1:4.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:1:1:3": { //Road
				"header": [ $.i18n("ElementTypeParam:1:1:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:1:1:1": { //"brand"
				"header": [ $.i18n("ElementTypeParam:1:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:1:1:5": { //"PK"
				"header": [ $.i18n("ElementTypeParam:1:1:5.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			}
             
		},
		2:{
			"alias": {

				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"state": {
				"header": [ $.i18n("equipment.table.state"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"ElementTypeParam:1:2:19": { //"Precipitation_Type"
			
				"header": {
					text: $.i18n("ElementTypeParam:1:2:19.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
			"ElementTypeParam:1:2:15": { //"Gust_Wind_Speed"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:15.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
			"ElementTypeParam:1:2:14": { //"Average_Wind_Direction"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:14.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true			},
			},
			"ElementTypeParam:1:2:2": { //"Sub_Surface_Temperature"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:2.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
			"ElementTypeParam:1:2:12": { //"Snow_Fall_Intensity"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:12.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
			"ElementTypeParam:1:2:20": { //"Visibility"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:20.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
			"ElementTypeParam:1:2:6": { //"Concentration_NaCl"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:6.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
			"ElementTypeParam:1:2:18": { //"Dew_Point_Temperature"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:18.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
			"ElementTypeParam:1:2:8": { //"Concentration_CaCl"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:8.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
            "ElementTypeParam:1:2:9": { //"Road_Condition"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:9.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
			"ElementTypeParam:1:2:16": { //"Gust_Wind_Direction"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:16.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
			"ElementTypeParam:1:2:21": { //"Black_Ice_Signal"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:21.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
			"ElementTypeParam:1:2:3": {  //"Freeze_Point" 
				"header": {
					text: $.i18n("ElementTypeParam:1:2:3.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true		
				},
			},
            "ElementTypeParam:1:2:11": { //"Precipitation_Intensity"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:11.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
            "ElementTypeParam:1:2:13": {  //"Average_Wind_Speed"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:13.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
            "ElementTypeParam:1:2:1": { //"Surface_Temperature"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:1.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
            "ElementTypeParam:1:2:5": { //"Water_Depth"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:5.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
            "ElementTypeParam:1:2:10": { //"Relative_Humidity"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:10.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
            "ElementTypeParam:1:2:7": { //"Concentration_MgCl2"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:7.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
            "ElementTypeParam:1:2:17": { //"Air_Temperature"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:17.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
            "ElementTypeParam:1:2:22": { //"Prediction_Alarm_Code"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:22.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			},
            "ElementTypeParam:1:2:4": { //"Air_Pressure"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:4.alias"),
					tooltip: true, 
					autoheight:true,
					rotate:true
				},
			}
		},
	},
	"ElementType:2": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:2:1:5": { //"alias_section"
				"header": [ $.i18n("ElementTypeParam:2:1:5.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:1:2": { //"model"
				"header": [ $.i18n("ElementTypeParam:2:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:1:1": { //"brand"
				"header": [ $.i18n("ElementTypeParam:2:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:1:4": { //"road"
				"header": [ $.i18n("ElementTypeParam:2:1:4.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:1:6": { //"code keyboard"
				"header": [ $.i18n("ElementTypeParam:2:1:6.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:1:7": { //"location"
				"header": [ $.i18n("ElementTypeParam:2:1:7.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:1:8": { //"PK"
				"header": [ $.i18n("ElementTypeParam:2:1:8.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:1:10": { //"Associate Cabinet"
				"header": [ $.i18n("ElementTypeParam:2:1:10.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			}
             
		},
		2:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"state": {
				"header": [ $.i18n("equipment.table.state"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
		}
	},
	"ElementType:4": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:4:1:1": { //"direction"
				"header": [ $.i18n("ElementTypeParam:4:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:4:1:2": { //"virtual"
				"header": [ $.i18n("ElementTypeParam:4:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			}
		},
		2:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"state": {
				"header": [ $.i18n("equipment.table.state"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
		}
	},
	"ElementType:25": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:25:1:1": { //"PK"
				"header": [ $.i18n("ElementTypeParam:25:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:25:1:2": { //"SECCION"
				"header": [ $.i18n("ElementTypeParam:25:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:25:1:3": { //"CABINET_NAME"
				"header": [ $.i18n("ElementTypeParam:25:1:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:25:1:4": { //"ASSOCIATED CABINET"
				"header": [ $.i18n("ElementTypeParam:25:1:4.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:25:1:1003": { //"MANGO SESSION_UID"
				"header": [ $.i18n("ElementTypeParam:25:1:1003.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
            }
		},
		2:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"state": {
				"header": [ $.i18n("equipment.table.state"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
		}
	},
	"ElementType:34": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:34:1:1": { //"PK"
				"header": [ $.i18n("ElementTypeParam:34:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:34:1:2": { //"SECCION"
				"header": [ $.i18n("ElementTypeParam:34:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:34:1:3": { //"CABINET_NAME"
				"header": [ $.i18n("ElementTypeParam:34:1:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:34:1:1003": { //"MANGO SESSION_UID"
				"header": [ $.i18n("ElementTypeParam:34:1:1003.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
            }
		},
		2:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"state": {
				"header": [ $.i18n("equipment.table.state"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
		}
	},
	"ElementType:41": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:41:1:1": { //"PK"
				"header": [ $.i18n("ElementTypeParam:41:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:41:1:2": { //"SECCION"
				"header": [ $.i18n("ElementTypeParam:41:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:41:1:3": { //"CABINET_NAME"
				"header": [ $.i18n("ElementTypeParam:41:1:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:41:1:1003": { //"MANGO SESSION_UID"
				"header": [ $.i18n("ElementTypeParam:41:1:1003.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
            }
		},
		2:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"state": {
				"header": [ $.i18n("equipment.table.state"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
		}
	},
	"ElementType:42": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:42:1:1": { //"PK"
				"header": [ $.i18n("ElementTypeParam:42:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:42:1:2": { //"SECCION"
				"header": [ $.i18n("ElementTypeParam:42:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:42:1:3": { //"CABINET_NAME"
				"header": [ $.i18n("ElementTypeParam:42:1:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:42:1:1003": { //"MANGO SESSION_UID"
				"header": [ $.i18n("ElementTypeParam:42:1:1003.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
            }
		},
		2:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"state": {
				"header": [ $.i18n("equipment.table.state"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
		}
	},
	"ElementType:43": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:43:1:1": { //"PK"
				"header": [ $.i18n("ElementTypeParam:43:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:43:1:2": { //"SECCION"
				"header": [ $.i18n("ElementTypeParam:43:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:43:1:3": { //"CABINET_NAME"
				"header": [ $.i18n("ElementTypeParam:43:1:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:43:1:1003": { //"MANGO SESSION_UID"
				"header": [ $.i18n("ElementTypeParam:43:1:1003.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
            }
		},
		2:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"state": {
				"header": [ $.i18n("equipment.table.state"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
		}
	},
	"ElementType:44": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:44:1:1": { //"PK"
				"header": [ $.i18n("ElementTypeParam:44:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:44:1:2": { //"SECCION"
				"header": [ $.i18n("ElementTypeParam:44:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:44:1:3": { //"CABINET_NAME"
				"header": [ $.i18n("ElementTypeParam:44:1:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:44:1:1003": { //"MANGO SESSION_UID"
				"header": [ $.i18n("ElementTypeParam:44:1:1003.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
            }
		},
		2:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"state": {
				"header": [ $.i18n("equipment.table.state"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
		}
	},
	"ElementType:45": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:45:1:1": { //"PK"
				"header": [ $.i18n("ElementTypeParam:45:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:45:1:2": { //"SECCION"
				"header": [ $.i18n("ElementTypeParam:45:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:45:1:3": { //"CABINET_NAME"
				"header": [ $.i18n("ElementTypeParam:45:1:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:45:1:1003": { //"MANGO SESSION_UID"
				"header": [ $.i18n("ElementTypeParam:45:1:1003.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
            }
		},
		2:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"state": {
				"header": [ $.i18n("equipment.table.state"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
		}
	},
	"ElementType:46": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:46:1:1": { //"PK"
				"header": [ $.i18n("ElementTypeParam:46:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:46:1:2": { //"SECCION"
				"header": [ $.i18n("ElementTypeParam:46:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:46:1:3": { //"CABINET_NAME"
				"header": [ $.i18n("ElementTypeParam:46:1:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:46:1:1003": { //"MANGO SESSION_UID"
				"header": [ $.i18n("ElementTypeParam:46:1:1003.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
            }
		},
		2:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"state": {
				"header": [ $.i18n("equipment.table.state"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
		}
	},
	"ElementType:47": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:47:1:1": { //"PK"
				"header": [ $.i18n("ElementTypeParam:47:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:47:1:2": { //"SECCION"
				"header": [ $.i18n("ElementTypeParam:47:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:47:1:3": { //"CABINET_NAME"
				"header": [ $.i18n("ElementTypeParam:47:1:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:47:1:1003": { //"MANGO SESSION_UID"
				"header": [ $.i18n("ElementTypeParam:47:1:1003.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
            }
		},
		2:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"state": {
				"header": [ $.i18n("equipment.table.state"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
		}
	}
}

