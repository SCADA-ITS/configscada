export let optionElementType = [
	{id:"ElementType:1", value: $.i18n("ElementType:1.alias")},
	{id:"ElementType:2", value: $.i18n("ElementType:2.alias")},
	{id:"ElementType:25", value: $.i18n("ElementType:25.alias")}
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
            "ElementTypeParam:2:1:8"
		],
		2:[
			"alias",
			"state",
		]
	},
	"ElementType:25": { 
		1:[
			"alias",
			"ElementTypeParam:25:1:1",
			"ElementTypeParam:25:1:2",
			"ElementTypeParam:25:1:3"
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
					rotate: true, 
					autoheight:true,
					autowidth:true,
				},
			},
			"ElementTypeParam:1:2:15": { //"Gust_Wind_Speed"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:15.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true 
				},
			},
			"ElementTypeParam:1:2:14": { //"Average_Wind_Direction"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:14.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true,				},
			},
			"ElementTypeParam:1:2:2": { //"Sub_Surface_Temperature"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:2.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
			"ElementTypeParam:1:2:12": { //"Snow_Fall_Intensity"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:12.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true 
				},
			},
			"ElementTypeParam:1:2:20": { //"Visibility"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:20.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true 
				},
			},
			"ElementTypeParam:1:2:6": { //"Concentration_NaCl"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:6.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
			"ElementTypeParam:1:2:18": { //"Dew_Point_Temperature"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:18.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
			"ElementTypeParam:1:2:8": { //"Concentration_CaCl"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:8.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
            "ElementTypeParam:1:2:9": { //"Road_Condition"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:9.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true 
				},
			},
			"ElementTypeParam:1:2:16": { //"Gust_Wind_Direction"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:16.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
			"ElementTypeParam:1:2:21": { //"Black_Ice_Signal"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:21.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
			"ElementTypeParam:1:2:3": {  //"Freeze_Point" 
				"header": {
					text: $.i18n("ElementTypeParam:1:2:3.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true,				},
			},
            "ElementTypeParam:1:2:11": { //"Precipitation_Intensity"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:11.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
            "ElementTypeParam:1:2:13": {  //"Average_Wind_Speed"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:13.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
            "ElementTypeParam:1:2:1": { //"Surface_Temperature"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:1.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
            "ElementTypeParam:1:2:5": { //"Water_Depth"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:5.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
            "ElementTypeParam:1:2:10": { //"Relative_Humidity"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:10.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
            "ElementTypeParam:1:2:7": { //"Concentration_MgCl2"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:7.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
            "ElementTypeParam:1:2:17": { //"Air_Temperature"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:17.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
            "ElementTypeParam:1:2:22": { //"Prediction_Alarm_Code"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:22.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
            "ElementTypeParam:1:2:4": { //"Air_Pressure"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:4.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
            "ElementTypeParam:1:2:24": { //"door open"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:24.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
            "ElementTypeParam:1:2:25": { //"power supply fail"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:25.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
            "ElementTypeParam:1:2:26": { //"battery low"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:26.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
            "ElementTypeParam:1:2:27": { //"overload UPS"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:27.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true
				},
			},
            "ElementTypeParam:1:2:28": { //plc status
				"header": {
					text: $.i18n("ElementTypeParam:1:2:28.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true 
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
	}
}

