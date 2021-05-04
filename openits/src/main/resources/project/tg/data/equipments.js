export let optionElementType = [
	{id:"ElementType:1",  value:"WEATHER STATION"},
	{id:"ElementType:2",  value:"CAMERA"},
	{id:"ElementType:7",  value:"TUNNEL"},
	{id:"ElementType:38", value:"VMS"},
	{id:"ElementType:81", value:"ACCELEROMETER"},
	{id:"ElementType:82", value:"VISIBILIMETER"},
	{id:"ElementType:83", value:"RADAR"},
]

export let columnsElementList = {
	"ElementType:1": {
		1:[
			"alias",
            "ElementTypeParam:1:1:2",
            "ElementTypeParam:1:1:4",
            "ElementTypeParam:1:1:3",
            "ElementTypeParam:1:1:1"
		],
		2:[
			"alias", 
			"state",
			"ElementTypeParam:1:2:14",
			"ElementTypeParam:1:2:20",
			"ElementTypeParam:1:2:13",
			"ElementTypeParam:1:2:10",
			"ElementTypeParam:1:2:17",
			"ElementTypeParam:1:2:4"
		]
	},
	"ElementType:2": { 
		1:[
			"alias",
			"ElementTypeParam:2:1:1",
			"ElementTypeParam:2:1:2",
			"ElementTypeParam:2:1:4",
			"ElementTypeParam:2:1:5",
			"ElementTypeParam:2:1:3",
			"ElementTypeParam:2:1:6",
			"ElementTypeParam:2:1:7",
			"ElementTypeParam:2:1:8",
			"ElementTypeParam:2:1:9"
		],
		2:[
			"alias",
			"state",
			"ElementTypeParam:2:2:2",
			"ElementTypeParam:2:2:5",
			"ElementTypeParam:2:2:4",
			"ElementTypeParam:2:2:3"
		]
	},
	"ElementType:38": { 
		1:[
			"alias",
			"ElementTypeParam:38:1:6",
			"ElementTypeParam:38:1:7",
			"ElementTypeParam:38:1:8",
			"ElementTypeParam:38:1:9"
		],
		2:[
			"alias",
			"state",
		]
	},
	"ElementType:81": { 
		1:[
			"alias",
			"ElementTypeParam:81:1:1",
			"ElementTypeParam:81:1:2",
			"ElementTypeParam:81:1:3",
			"ElementTypeParam:81:1:4",
			"ElementTypeParam:81:1:5",
			"ElementTypeParam:81:1:6",
		],
		2:[
			"alias",
			"state",
			"ElementTypeParam:81:2:1",
			"ElementTypeParam:81:2:2",
			"ElementTypeParam:81:2:3",
			"ElementTypeParam:81:2:4",
			"ElementTypeParam:81:2:5",
			"ElementTypeParam:81:2:6",
			"ElementTypeParam:81:2:7",
		]
	},
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
			}
             
		},
		2:{
			"alias": {
				/*
				"header": {
					text: $.i18n("equipment.table.alias"),
					//rotate: true, 
					autoheight:true,
					autowidth:true,
					//height: 175,
					//content:"textFilter" 
				},
				*/
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"state": {
				"header": [ $.i18n("equipment.table.state"), { content:"textFilter", autoheight:true, autowidth:true }],
				"rotate": false
			},
			"ElementTypeParam:1:2:14": { //"Average_Wind_Direction"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:14.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true,
					//height: 175,
					//content:"textFilter" 
				},
			},
			"ElementTypeParam:1:2:20": { //"Visibility"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:20.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true,
					//height: 175,
					//content:"textFilter" 
				},
			},
            "ElementTypeParam:1:2:13": {  //"Average_Wind_Speed"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:13.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true,
					//height: 175,
					//content:"textFilter" 
				},
			},
            "ElementTypeParam:1:2:10": { //"Relative_Humidity"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:10.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true,
					//height: 175,
					//content:"textFilter" 
				},
			},
            "ElementTypeParam:1:2:17": { //"Air_Temperature"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:17.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true,
					//height: 175,
					//content:"textFilter" 
				},
			},
            "ElementTypeParam:1:2:4": { //"Air_Pressure"
				"header": {
					text: $.i18n("ElementTypeParam:1:2:4.alias"),
					rotate: true, 
					autoheight:true,
					autowidth:true,
					//height: 175,
					//content:"textFilter" 
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
			"ElementTypeParam:2:1:3": { //"stream_mjpeg"
				"header": [ $.i18n("ElementTypeParam:2:1:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:1:1": { //"brand"
				"header": [ $.i18n("ElementTypeParam:2:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:1:1003": { //"MANGO SESSION_UID"
				"header": [ $.i18n("ElementTypeParam:2:1:1003.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:1:4": { //"road"
				"header": [ $.i18n("ElementTypeParam:2:1:4.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:1:6": {
				"header": [ $.i18n("ElementTypeParam:2:1:6.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:1:7": { 
				"header": [ $.i18n("ElementTypeParam:2:1:7.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:1:8": { //"road"
				"header": [ $.i18n("ElementTypeParam:2:1:8.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:1:9": { //"road"
				"header": [ $.i18n("ElementTypeParam:2:1:9.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
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
            "ElementTypeParam:2:2:2": { //"door open"
				"header": [ $.i18n("ElementTypeParam:2:2:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:2:2:5": { //"plc status"
				"header": [ $.i18n("ElementTypeParam:2:2:5.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:2:4": { //"battery low"
				"header": [ $.i18n("ElementTypeParam:2:2:4.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:2:2:3": { //"power supply fail"
				"header": [ $.i18n("ElementTypeParam:2:2:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
		}
	},
	"ElementType:38": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:38:1:6": { //"road"
				"header": [ $.i18n("ElementTypeParam:38:1:6.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:38:1:7": { //"alias_section"
				"header": [ $.i18n("ElementTypeParam:38:1:7.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:38:1:8": { //"direction"
				"header": [ $.i18n("ElementTypeParam:38:1:8.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:38:1:9": { //"model"
				"header": [ $.i18n("ElementTypeParam:38:1:9.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
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
	"ElementType:81": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:81:1:1": { //"Brand"
				"header": [ $.i18n("ElementTypeParam:81:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:81:1:2": { //"Model"
				"header": [ $.i18n("ElementTypeParam:81:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:81:1:3": { //"Road"
				"header": [ $.i18n("ElementTypeParam:81:1:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:81:1:4": { //"Section"
				"header": [ $.i18n("ElementTypeParam:81:1:4.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			} ,
            "ElementTypeParam:81:1:5": { //"PK"
				"header": [ $.i18n("ElementTypeParam:81:1:5.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:81:1:6": { //"Associated PLC"
				"header": [ $.i18n("ElementTypeParam:81:1:6.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
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
			"ElementTypeParam:81:2:1": { //"Visibility PV"
				"header": [ $.i18n("ElementTypeParam:81:2:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:81:2:2": { //"Visibility Trigger 1 SP"
				"header": [ $.i18n("ElementTypeParam:81:2:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:81:2:3": { //"Visibility Trigger 2 SP"
				"header": [ $.i18n("ElementTypeParam:81:2:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:81:2:4": { //"Visibility Trigger 1"
				"header": [ $.i18n("ElementTypeParam:81:2:4.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			} ,
            "ElementTypeParam:81:2:5": { //"Visibility Trigger 2"
				"header": [ $.i18n("ElementTypeParam:81:2:5.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:81:2:6": { //"Visibility Unit System Fault"
				"header": [ $.i18n("ElementTypeParam:81:2:6.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			}  ,
            "ElementTypeParam:81:2:7": { //"Visibility PLC Fault"
				"header": [ $.i18n("ElementTypeParam:81:2:6.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			}       
		}
	},
	"ElementType:82": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:82:1:1": { //"Brand"
				"header": [ $.i18n("ElementTypeParam:82:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:82:1:2": { //"Model"
				"header": [ $.i18n("ElementTypeParam:82:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:82:1:3": { //"Road"
				"header": [ $.i18n("ElementTypeParam:82:1:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:82:1:4": { //"Section"
				"header": [ $.i18n("ElementTypeParam:82:1:4.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			} ,
            "ElementTypeParam:82:1:5": { //"PK"
				"header": [ $.i18n("ElementTypeParam:82:1:5.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:82:1:6": { //"Associated PLC"
				"header": [ $.i18n("ElementTypeParam:82:1:6.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
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
			"ElementTypeParam:82:2:1": { //"Accelerometer Trigger Event"
				"header": [ $.i18n("ElementTypeParam:82:2:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
		}
	},
	"ElementType:83": {
		1:{
			"alias": {
				"header": [ $.i18n("equipment.table.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:83:1:1": { //"Brand"
				"header": [ $.i18n("ElementTypeParam:83:1:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:83:1:2": { //"Model"
				"header": [ $.i18n("ElementTypeParam:83:1:2.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
			"ElementTypeParam:83:1:3": { //"Road"
				"header": [ $.i18n("ElementTypeParam:83:1:3.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
            "ElementTypeParam:83:1:4": { //"Section"
				"header": [ $.i18n("ElementTypeParam:83:1:4.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			} ,
            "ElementTypeParam:83:1:5": { //"PK"
				"header": [ $.i18n("ElementTypeParam:83:1:5.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},      
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
			"ElementTypeParam:83:2:1": { //"Alarm"
				"header": [ $.i18n("ElementTypeParam:83:2:1.alias"), { content:"textFilter", autoheight:true, autowidth:true }],
			},
		}
	},
}

