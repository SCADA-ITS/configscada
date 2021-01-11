export let synopticData= [
	{
		"id":"synopticTunnel",
		"parentId":"Element:7:1",
		"ExclusiveLayers": false,
		"tittle": "RIO VERDE",
		"BaseLayers": {
			"BASE_LAYER": "tunel_v2.svg"
		},
		"Layers": {
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},

			"VENTILACION": {
				"items": ["FAN", "CO", "NO", "OPAC", "ANEM_EXT", "ANEM_INT"],
				"translation_key": "",
				"visibility": "visible"
			},
			"ILUMINACION": {
				"items": ["LUX", "LUM"],
				"translation_key": "",
				"visibility": "visible"
			},
			"CONTROL ACCESOS": {
				"items": ["MATRIXPANEL", "BAR", "SEM"],
				"translation_key": "",
				"visibility": "visible"
			},
			"CAM": {
				"items": ["Element:2:1", "Element:2:2", "Element:2:3", "Element:2:4", "Element:2:5", "Element:2:6", "Element:2:7", "Element:2:8", "Element:2:9", "Element:2:10"],
				"translation_key": "ElementType:2.description",
				"visibility": "visible"
			},
			"SECTIONS": {
				"items": ["Element:4:1", "Element:4:2", "Element:4:3", "Element:4:4"],
				"translation_key": "",
				"visibility": "visible"
			},
			"BAR": {
				"items": ["Element:8:1", "Element:8:2", "Element:8:3", "Element:8:4"],
				"translation_key": "",
				"visibility": "visible"
			},
			"FAN": {
				"items": ["Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", "Element:9:6", "Element:9:7", "Element:9:8", "Element:9:9", "Element:9:10"],
				"translation_key": "",
				"visibility": "visible"
			},
			"SOS": {
				"items": ["Element:11:1", "Element:11:2", , "Element:11:3", , "Element:11:4", , "Element:11:5", , "Element:11:6"],
				"translation_key": "",
				"visibility": "visible"
			},
			"LUX": {
				"items": ["Element:12:1", "Element:12:2", "Element:12:3", "Element:12:4", "Element:12:5", "Element:12:6", "Element:12:7", "Element:12:8"],
				"translation_key": "",
				"visibility": "visible"
			},
			"SEM": {
				"items": ["Element:13:1", "Element:13:2", "Element:13:3", "Element:13:4"],
				"translation_key": "",
				"visibility": "visible"
			},
			"LUM": {
				"items": ["Element:14:1", "Element:14:2"],
				"translation_key": "",
				"visibility": "visible"
			},
			"FIRE": {
				"items": ["Element:15:1", "Element:15:2", "Element:15:3", "Element:15:4", "Element:15:5", "Element:15:6"],
				"translation_key": "",
				"visibility": "visible"
			},
			"MEG": {
				"items": ["Element:16:3", "Element:16:4", "Element:16:5", "Element:16:6"],
				"translation_key": "",
				"visibility": "visible"
			},
			"ANEM_EXT": {
				"items": ["Element:17:1"],
				"translation_key": "",
				"visibility": "visible"
			},
			"ANEM_INT": {
				"items": ["Element:18:1", "Element:18:2"],
				"translation_key": "",
				"visibility": "visible"
			},
			"CO": {
				"items": ["Element:19:1", "Element:19:2"],
				"translation_key": "",
				"visibility": "visible"
			},
			"NO": {
				"items": ["Element:20:1", "Element:20:2"],
				"translation_key": "",
				"visibility": "visible"
			},
			"OPAC": {
				"items": ["Element:21:1", "Element:21:2"],
				"translation_key": "",
				"visibility": "visible"
			},
			"MATRIXPANEL": {
				"items": ["Element:36:1", "Element:36:2"],
				"translation_key": "",
				"visibility": "visible"
			},
			"ETD": {
				"items": ["Element:51:1", "Element:51:2"],
				"translation_key": "",
				"visibility": "visible"
			},
			"SEM_AMBAR_AMBAR": {
				"items": ["Element:53:1", "Element:53:2", "Element:53:3", "Element:53:4"],
				"translation_key": "",
				"visibility": "visible"
			},
			"CT": {
				"items": ["Element:55:1"],
				"translation_key": "",
				"visibility": "visible"
			}
		},
		
		"Element:2:1":  { "x": 1400, "y": 910, "width": 40,  "height": 40,  "alias": "RV-TV-1CA" },
		"Element:2:2":  { "x": 1150,  "y": 910, "width": 40,  "height": 40,  "alias": "RV-TV-2CA" },
		"Element:2:3":  { "x": 800,  "y": 910, "width": 40,  "height": 40,  "alias": "RV-TV-3CA" },
		"Element:2:4":  { "x": 400,  "y": 910, "width": 40,  "height": 40,  "alias": "RV-TV-4CA" },
		"Element:2:5":  { "x": 1710,  "y": 517, "width": 40,  "height": 40,  "alias": "RV-TVD-1CA" },
		"Element:2:6":  { "x": 270,  "y": 141, "width": 40,  "height": 40,  "alias": "RV-TV-1MA" },
		"Element:2:7":  { "x": 580, "y": 141, "width": 40,  "height": 40,  "alias": "RV-TV-2MA" },
		"Element:2:8":  { "x": 900, "y": 141, "width": 40,  "height": 40,  "alias": "RV-TV-3MA" },
		"Element:2:9":  { "x": 1500,  "y": 141, "width": 40,  "height": 40,  "alias": "RV-TV-4MA" },
		"Element:2:10":  { "x": 35,  "y": 517, "width": 40,  "height": 40,  "alias": "RV-TVD-1MA" },

		"Element:4:1": { "x": 1485,  "y": 711, "width": 80, "height": 80, "alias": "SEC_1_ETD1",
			"values": [
				{name: "etd_intensity", label: "I (V/h)"},
				{name: "etd_speed", label: "V (K/h)"},
				{name: "etd_occupancy", label: "O (%)"},
				{name: "etd_veh_length_1_percent", label: "C (%L)"}
			]
		},
		"Element:4:2": { "x": 1485,  "y": 300, "width": 80, "height": 80, "alias": "SEC_2_ETD1",
			"values": [
				{name: "etd_intensity", label: "I (V/h)"},
				{name: "etd_speed", label: "V (K/h)"},
				{name: "etd_occupancy", label: "O (%)"},
				{name: "etd_veh_length_1_percent", label: "C (%L)"}
			]
		},
		"Element:4:3": { "x": 235,  "y": 300, "width": 80, "height": 80, "alias": "SEC_1_ETD2",
			"values": [
				{name: "etd_intensity", label: "I (V/h)"},
				{name: "etd_speed", label: "V (K/h)"},
				{name: "etd_occupancy", label: "O (%)"},
				{name: "etd_veh_length_1_percent", label: "C (%L)"}
			]
		},
		"Element:4:4": { "x": 235,  "y": 711, "width": 80, "height": 80, "alias": "SEC_2_ETD2",
			"values": [
				{name: "etd_intensity", label: "I (V/h)"},
				{name: "etd_speed", label: "V (K/h)"},
				{name: "etd_occupancy", label: "O (%)"},
				{name: "etd_veh_length_1_percent", label: "C (%L)"}
			]
		},
		
		"Element:8:1":  { "x": 1529, "y": 817, "width": 88,  "height": 48,  "alias": "RV-BAR-1CA" },
		"Element:8:2":  { "x": 1529, "y": 658, "width": 88,  "height": 48,  "alias": "RV-BAR-2CA" },
		"Element:8:3":  { "x": 150,   "y": 258, "width": 88,  "height": 48,  "alias": "RV-BAR-1MA" },
		"Element:8:4":  { "x": 150,   "y": 407, "width": 88,  "height": 48,  "alias": "RV-BAR-2MA" },

		"Element:9:1":  { "x": 1172, "y": 800, "width": 40,  "height": 40,  "alias": "RV-VNT-1CA" },
		"Element:9:2":  { "x": 1172, "y": 738, "width": 40,  "height": 40,  "alias": "RV-VNT-2CA" },
		"Element:9:3":  { "x": 1172, "y": 680, "width": 40,  "height": 40,  "alias": "RV-VNT-3CA" },
		"Element:9:4":  { "x": 780,  "y": 738, "width": 40,  "height": 40,  "alias": "RV-VNT-4CA" },
		"Element:9:5":  { "x": 420,  "y": 280, "width": 40,  "height": 40,  "alias": "RV-VENT-1MA" },
		"Element:9:6":  { "x": 420,  "y": 372, "width": 40,  "height": 40,  "alias": "RV-VENT-2MA" },
		"Element:9:7":  { "x": 780, "y": 332, "width": 40,  "height": 40,  "alias": "RV-VENT-3MA" },
		"Element:9:8":  { "x": 1172, "y": 395, "width": 40,  "height": 40,  "alias": "RV-VENT-4MA" },
		"Element:9:9":  { "x": 1172, "y": 332, "width": 40,  "height": 40,  "alias": "RV-VENT-5MA" },
		"Element:9:10":  { "x": 1172, "y": 270, "width": 40,  "height": 40,  "alias": "RV-VENT-6MA" },

		"Element:11:1": { "x": 670,  "y": 907, "width": 40,  "height": 40,  "alias": "RV-SOSI-1CA" },		
		"Element:11:2": { "x": 640, "y": 140, "width": 40,  "height": 40,  "alias": "RV-SOSI-1MA" },
		"Element:11:3": { "x": 670,  "y": 907, "width": 40,  "height": 40,  "alias": "RV-SOSI-1CA" },		
		"Element:11:4": { "x": 640, "y": 140, "width": 40,  "height": 40,  "alias": "RV-SOSI-1MA" },
		"Element:11:5": { "x": 670,  "y": 907, "width": 40,  "height": 40,  "alias": "RV-SOSI-1CA" },		
		"Element:11:6": { "x": 640, "y": 140, "width": 40,  "height": 40,  "alias": "RV-SOSI-1MA" },
		
		"Element:12:1": { "x": 1440, "y": 901, "width": 50,  "height": 50,  "alias": "RV-LUX-1CA" },
		"Element:12:2": { "x": 1350,  "y": 901, "width": 50,  "height": 50,  "alias": "RV-LUX-2CA" },
		"Element:12:3": { "x": 550, "y": 901, "width": 50,  "height": 50,  "alias": "RV-LUX-3CA" },
		"Element:12:4": { "x": 300,  "y": 901, "width": 50,  "height": 50,  "alias": "RV-LUX-4CA" },
		"Element:12:5": { "x": 320,  "y": 132, "width": 50,  "height": 50,  "alias": "RV-LUX-1MA" },
		"Element:12:6": { "x": 500, "y": 132, "width": 50,  "height": 50,  "alias": "RV-LUX-2MA" },
		"Element:12:7": { "x": 1200,  "y": 132, "width": 50,  "height": 50,  "alias": "RV-LUX-3MA" },
		"Element:12:8": { "x": 1400, "y": 132, "width": 50,  "height": 50,  "alias": "RV-LUX-4MA" },
	
		"Element:13:1": { "x": 1660, "y": 660, "width": 40,  "height": 40,  "alias": "RV-SRAV-1CA" },
		"Element:13:2": { "x": 100,   "y": 405, "width": 40,  "height": 40,  "alias": "RV-SRAV-1MA" },
		"Element:13:3": { "x": 1660, "y": 805, "width": 40,  "height": 40,  "alias": "RV-SRAV-2CA" },
		"Element:13:4": { "x": 100,   "y": 248, "width": 40,  "height": 40,  "alias": "RV-SRAV-2MA" },

		"Element:14:1": { "x": 1640, "y": 503, "width": 60,  "height": 60,  "alias": "RV-LUM-1CA" },
		"Element:14:2": { "x": 77,   "y": 503, "width": 60,  "height": 60,  "alias": "RV-LUM-1MA" },

		"Element:15:1": { "x": 434,  "y": 870, "width": 315, "height": 20,  "alias": "FIRE_1_SC" },
		"Element:15:2": { "x": 434,  "y": 195, "width": 315, "height": 20,  "alias": "FIRE_1_SM" },
		"Element:15:3": { "x": 748,  "y": 870, "width": 315, "height": 20,  "alias": "FIRE_2_SC" },
		"Element:15:4": { "x": 748,  "y": 195, "width": 315, "height": 20,  "alias": "FIRE_2_SM" },
		"Element:15:5": { "x": 1063, "y": 870, "width": 315, "height": 20,  "alias": "FIRE_3_SC" },
		"Element:15:6": { "x": 1063, "y": 195, "width": 315, "height": 20,  "alias": "FIRE_3_SM" },
		
		"Element:16:3": { "x": 1438, "y": 724, "width": 40,  "height": 40,  "alias": "RV-MEG_3CA" },
		"Element:16:4": { "x": 881,  "y": 600, "width": 40,  "height": 40,  "alias": "RV-MEG_4CA" },
		"Element:16:5": { "x": 320,  "y": 320, "width": 40,  "height": 40,  "alias": "RV-MEG_1MA" },
		"Element:16:6": { "x": 881,  "y": 444, "width": 40,  "height": 40,  "alias": "RV-MEG_2MA" },

		"Element:17:1": { "x": 150,  "y": 517, "width": 50,  "height": 50,  "alias": "RV-ANE-1CA" },

		"Element:18:1": { "x": 890,  "y": 813, "width": 50,  "height": 50,  "alias": "RV-ANI-1CA" },
		"Element:18:2": { "x": 900,  "y": 235, "width": 50,  "height": 50,  "alias": "RV-ANI-1MA" },

		"Element:19:1": { "x": 945, "y": 902, "width": 50,  "height": 50,  "alias": "RV-CO-1CA" },
		"Element:19:2": { "x": 805,  "y": 132, "width": 50,  "height": 50,  "alias": "RV-CO-1MA" },

		"Element:20:1": { "x": 1054, "y": 902, "width": 50,  "height": 50,  "alias": "RV-NO-1CA" },
		"Element:20:2": { "x": 689,  "y": 132, "width": 50,  "height": 50,  "alias": "RV-NO-1MA" },

		"Element:21:1": { "x": 1000, "y": 902, "width": 50,  "height": 50,  "alias": "RV-OPC-1CA" },
		"Element:21:2": { "x": 745,  "y": 132, "width": 50,  "height": 50,  "alias": "RV-OPC-1MA" },

		"Element:36:1": { "x": 50,   "y": 320, "width": 200, "height": 54,  "alias": "GT_1" },
		"Element:36:2": { "x": 1580, "y": 725, "width": 200, "height": 54,  "alias": "GT_2" },
		
		"Element:51:1": { "x": 1485,   "y": 360, "width": 184, "height": 204,  "alias": "RV-ETD-1" },
		"Element:51:2": { "x": 75,   "y": -30, "width": 184, "height": 204,  "alias": "RV-ETD-1" },
		
		"Element:53:1": { "x": 1700, "y": 675, "width": 30,  "height": 35,  "alias": "RV-SAA-1CA" },
		"Element:53:2": { "x": 1700, "y": 818, "width": 30,  "height": 35,  "alias": "RV-SAA-2CA" },
		"Element:53:3": { "x": 50,  "y": 260, "width": 30,  "height": 35,  "alias": "RV-SAA-1MA" },
		"Element:53:4": { "x": 50,  "y": 420, "width": 30,  "height": 35,  	"alias": "RV-SAA-2MA" },

		"Element:55:1": { "x": 1560,   "y": 450, "width": 120, "height": 120,  "alias": "RV-CT-ITS-1" },
	},
	{
		"id":"synopticMetro",
		"parentId":"Element:7:2",
		"ExclusiveLayers": false,
		"tittle": "",
		"BaseLayers": {
			"BASE_LAYER": "metro.svg"
		},
		"Layers": {
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},
			"Tunel": {
				"items": ["Element:7:1"],
				"translation_key": "",
				"visibility": "visible",
				"tittle": "RIO VERDE"
			}
		},
		"Element:7:1": { "x": 1091, "y": 607, "width": 60, "height": 60 },
	},
	{
		"id":"synopticGlobal",
		"parentId":"Element:7:3",
		"ExclusiveLayers": false,
		"tittle": "RIO VERDE",
		"BaseLayers": {
			"BASE_LAYER": "global_v0.1.svg"
		},
		"Layers": {
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},
			"MATRIXPANEL": {
				"items": ["Element:36:1", "Element:36:2"],
				"translation_key": "",
				"visibility": "visible"
			},
			"FAN": {
				"items": ["Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", "Element:9:6", "Element:9:7", "Element:9:8", "Element:9:9", "Element:9:10"],
				"translation_key": "",
				"visibility": "visible"
			},
			"BAR": {
				"items": ["Element:8:1", "Element:8:2", "Element:8:3", "Element:8:4"],
				"translation_key": "",
				"visibility": "visible"
			},
			"SEM": {
				"items": ["Element:13:1", "Element:13:2", "Element:13:3", "Element:13:4"],
				"translation_key": "",
				"visibility": "visible"
			},
			"SEM_AMBAR_AMBAR": {
				"items": ["Element:53:1", "Element:53:2", "Element:53:3", "Element:53:4"],
				"translation_key": "",
				"visibility": "visible"
			},
			"SECTIONS": {
				"items": ["Element:4:1", "Element:4:2"],
				"translation_key": "",
				"visibility": "visible"
			}
		},
		"Element:36:1": { "x": 1511, "y": 593,  "width": 108, "height": 25 },
		"Element:36:2": { "x": 1700, "y": 693,  "width": 108, "height": 25 },

		"Element:9:1":  { "x": 1646, "y": 730,  "width": 15,  "height": 15 },
		"Element:9:2":  { "x": 1646, "y": 710,  "width": 15,  "height": 15 },
		"Element:9:3":  { "x": 1646, "y": 690,  "width": 15,  "height": 15 },
		"Element:9:4":  { "x": 1580, "y": 710,  "width": 15,  "height": 15 },
		"Element:9:5":  { "x": 1630, "y": 593,  "width": 15,  "height": 15 },
		"Element:9:6":  { "x": 1630, "y": 623,  "width": 15,  "height": 15 },
		"Element:9:7":  { "x": 1675, "y": 608,  "width": 15,  "height": 15 },
		"Element:9:8":  { "x": 1722, "y": 628,  "width": 15,  "height": 15 },
		"Element:9:9":  { "x": 1722, "y": 608,  "width": 15,  "height": 15 },
		"Element:9:10":  { "x": 1722, "y": 588,  "width": 15,  "height": 15 },
	
		"Element:13:1": { "x": 1823, "y": 645,  "width": 22,  "height": 25 },
		"Element:13:3": { "x": 1846, "y": 645,  "width": 22,  "height": 25 },
		"Element:13:2": { "x": 1407, "y": 652,  "width": 22,  "height": 25 },
		"Element:13:4": { "x": 1430, "y": 652,  "width": 22,  "height": 25 },

		"Element:53:1": { "x": 1867, "y": 654,  "width": 15,  "height": 20 },
		"Element:53:2": { "x": 1889, "y": 654,  "width": 15,  "height": 20 },
		"Element:53:3": { "x": 1450, "y": 661,  "width": 15,  "height": 20 },
		"Element:53:4": { "x": 1472, "y": 661,  "width": 15,  "height": 20 },
		
		"Element:8:1":  { "x": 1820, "y": 730,  "width": 88,  "height": 48 },
		"Element:8:2":  { "x": 1820, "y": 690,  "width": 88,  "height": 48 },
		"Element:8:3":  { "x": 1411, "y": 590,  "width": 88,  "height": 48 },
		"Element:8:4":  { "x": 1411, "y": 550,  "width": 88,  "height": 48 },
		
		"Element:4:4": { "x": 1410,  "y": 700, "width": 80, "height": 80, "alias": "SEC_1",
			"values": [
				{name: "etd_intensity", label: "I (V/h)"},
				{name: "etd_speed", label: "V (K/h)"},
				{name: "etd_occupancy", label: "O (%)"},
				{name: "etd_veh_length_1_percent", label: "C (%L)"}
			]
		},
		"Element:4:2": { "x": 1825,  "y": 555, "width": 80, "height": 80, "alias": "SEC_2",
			"values": [
				{name: "etd_intensity", label: "I (V/h)"},
				{name: "etd_speed", label: "V (K/h)"},
				{name: "etd_occupancy", label: "O (%)"},
				{name: "etd_veh_length_1_percent", label: "C (%L)"}
			]
		},
		"Element:7:1":  { "x": 1505, "y": 775,  "width": 305, "height": 37, "visibility": "hidden"}
	},
	{
		"id":"synopticCTs",
		"parentId":"Element:55:1",
		"ExclusiveLayers": false,
		"tittle": "RIO VERDE",
		"BaseLayers": {
			"BASE_LAYER": "CT-RIO-VERDE-PLANTA 1.svg"
		},
		"Layers": {
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},

			"UNIFILAR" : {
				"items": ["Element:56:1", "Element:56:2", "Element:56:3", "Element:56:4"],
				"translation_key": "",
				"visibility": "visible"
			},
			"PULSADOR" : {
				"items": ["Element:58:1"],
				"translation_key": "",
				"visibility": "visible"
			},
			"SMOKE_DETECTOR" : {
				"items": ["Element:59:1"],
				"translation_key": "",
				"visibility": "visible"
			},
			"SIRENA" : {
				"items": ["Element:60:1"],
				"translation_key": "",
				"visibility": "visible"
			},
			"FIRE_DET_CT" : {
				"items": ["Element:61:1"],
				"translation_key": "",
				"visibility": "visible"
			},
			"SAI" : {
				"items": ["Element:65:1"],
				"translation_key": "",
				"visibility": "visible"
			}
		},

		"Element:56:1": { "x": 1220,   "y": 15, "width": 130, "height": 200,  "alias": "RV-CSA" },
		"Element:56:2": { "x": 1020,   "y": 15, "width": 130, "height": 200,  "alias": "RV-CSB" },
		"Element:56:3": { "x": 820,   "y": 15, "width": 130, "height": 200,  "alias": "RV-CSV" },
		"Element:56:4": { "x": 620,   "y": 15, "width": 130, "height": 200,  "alias": "RV-CGBT" },

		"Element:58:1": { "x": 1440,   "y": 290, "width": 300, "height": 300,  "alias": "RV-CT-FIRE-PULS-1" },

		"Element:59:1": { "x": -30,   "y": 680, "width": 200, "height": 200,  "alias": "RV-CT-FIRE-HUM-1" },

		"Element:60:1": { "x": 900,   "y": 100, "width": 200, "height": 200,  "alias": "RV-CT-FIRE-SIR-1" },

		"Element:61:1": { "x": 150,   "y": 200, "width": 100, "height": 100,  "alias": "RV-CT-FIRE-DET-1" },
		
		"Element:65:1": { "x": 1640,   "y": 300, "width": 300, "height": 300,  "alias": "RV-SAI-1" },
	},
	{
		"id":"synopticUnifilarCsa",
		"parentId":"Element:56:1",
		"ExclusiveLayers": false,
		"tittle": "RIO VERDE",
		"BaseLayers": {
			"BASE_LAYER": "UNIFlLAR-RIO-VERDE-CABLE-CSA_v3.svg",
		},
		"Layers": {
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},
			"MAGNETOTERMICO" : {
				"items": ["Element:57:7", "Element:57:8", "Element:57:9", "Element:57:10", "Element:57:11", "Element:57:12", "Element:57:13", "Element:57:14", 
				          "Element:57:15", "Element:57:16", "Element:57:17", "Element:57:18", "Element:57:19", "Element:57:20", "Element:57:21", "Element:57:22", 
				          "Element:57:23", "Element:57:24", "Element:57:25", "Element:57:26"],
				"translation_key": "",
				"visibility": "visible"
			}
		},
		"Element:57:7": { "x": 995,   "y": 703, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A3" },
		"Element:57:8": { "x": 803,   "y": 703, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A4" },
		"Element:57:9": { "x": 1091,   "y": 703, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A5" },
		"Element:57:10": { "x": 899,   "y": 703, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A6" },
		"Element:57:11": { "x": 612,   "y": 703, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A10" },
		"Element:57:12": { "x": 420,   "y": 703, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A11" },
		"Element:57:13": { "x": 708,   "y": 703, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A12" },
		"Element:57:14": { "x": 515,   "y": 703, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A13" },
		"Element:57:15": { "x": 1188,   "y": 703, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A15" },
		"Element:57:16": { "x": 1379,   "y": 703, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A1" },
		"Element:57:17": { "x": 1283,   "y": 703, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A16" },
		"Element:57:18": { "x": 1474,   "y": 703, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A18" },
		"Element:57:19": { "x": 900,   "y": 322, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AA1" },
		"Element:57:20": { "x": 805,   "y": 322, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AA2" },
		"Element:57:21": { "x": 708,   "y": 322, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AA8" },
		"Element:57:22": { "x": 612,   "y": 322, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AA9" },
		"Element:57:23": { "x": 995,   "y": 322, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AE7" },
		"Element:57:24": { "x": 1091,   "y": 322, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AE14" },
		"Element:57:25": { "x": 1188,   "y": 322, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AE15" },
		"Element:57:26": { "x": 1283,   "y": 322, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AE16" },
	},
	{
		"id":"synopticUnifilarCsb",
		"parentId":"Element:56:2",
		"ExclusiveLayers": false,
		"tittle": "RIO VERDE",
		"BaseLayers": {
			"BASE_LAYER": "UNIFlLAR RIO VERDE CABLE - CSB_v3_1.svg",
		},
		"Layers": {
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},
			"MAGNETOTERMICO" : {
				"items": ["Element:57:1", "Element:57:2", "Element:57:3", "Element:57:4", "Element:57:5", "Element:57:6", "Element:57:29", "Element:57:30", "Element:57:32", 
				          "Element:57:33", "Element:57:34", "Element:57:35", "Element:57:36", "Element:57:37", "Element:57:38", "Element:57:39","Element:57:40", "Element:57:41",
				          "Element:57:65", "Element:57:66"],
				"translation_key": "",
				"visibility": "visible"
			}
		},
		
		"Element:57:41": { "x": 948,   "y": 795, "width": 28, "height": 28,  "alias": "RV-MAG-CSB-ITS-1" },
		"Element:57:29": { "x": 404,   "y": 700, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF10" },
		"Element:57:30": { "x": 503,   "y": 700, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF11" },
		"Element:57:32": { "x": 605,   "y": 700, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF13" },
		"Element:57:33": { "x": 703,   "y": 700, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF14" },	
		"Element:57:34": { "x": 802,   "y": 700, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF15" },
		"Element:57:35": { "x": 900,   "y": 700, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF19" },
		"Element:57:36": { "x": 1000,   "y": 700, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF20" },
		"Element:57:37": { "x": 1097,   "y": 700, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF21" },
		"Element:57:38": { "x": 1198,   "y": 700, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF22" },
		"Element:57:39": { "x": 1295,   "y": 700, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF23" },
		"Element:57:3": { "x": 1394,   "y": 700, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF28-SOS" },	
		"Element:57:40": { "x": 1494,   "y": 700, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF26" },
		
		"Element:57:1": { "x": 948,   "y": 400, "width": 28, "height": 28,  "alias": "RV-MAG-CSB-ITS-2" },
		"Element:57:2": { "x": 703,   "y": 295, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF27-PMV" },
		"Element:57:6": { "x": 796,   "y": 295, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF32" },
		"Element:57:66": { "x": 895,   "y": 295, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF34" },
		"Element:57:4": { "x": 995,   "y": 295, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF30-BARR-M" },
		"Element:57:5": { "x": 1094,   "y": 295, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF31-SEM" },
		"Element:57:65": { "x": 1191,   "y": 295, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF33" },
	},
	{
		"id":"synopticUnifilarCsv",
		"parentId":"Element:56:3",
		"ExclusiveLayers": false,
		"tittle": "RIO VERDE",
		"BaseLayers": {
			"BASE_LAYER": "UNIFlLAR RIO VERDE CABLE - CSV - VENTILACION_v2.svg",
		},
		"Layers": {
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},
			"MAGNETOTERMICO" : {
				"items": ["Element:57:16", "Element:57:18", "Element:57:42", "Element:57:43", "Element:57:44", "Element:57:45", 
				          "Element:57:46", "Element:57:47", "Element:57:48", "Element:57:49", "Element:57:50", "Element:57:51"],
				"translation_key": "",
				"visibility": "visible"
			}
		},
		"Element:57:16": { "x": 1595,   "y": 555, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A1" },
		"Element:57:18": { "x": 1740,   "y": 555, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A18" },
		"Element:57:42": { "x": 153,   "y": 555, "width": 28, "height": 28,  "alias": "RV-INT-VF1" },
		"Element:57:43": { "x": 298,   "y": 555, "width": 28, "height": 28,  "alias": "RV-INT-VF2" },
		"Element:57:44": { "x": 443,   "y": 555, "width": 28, "height": 28,  "alias": "RV-INT-VF3" },
		"Element:57:45": { "x": 586,   "y": 555, "width": 28, "height": 28,  "alias": "RV-INT-VF4" },
		"Element:57:46": { "x": 731,   "y": 555, "width": 28, "height": 28,  "alias": "RV-INT-VF5" },
		"Element:57:47": { "x": 875,   "y": 555, "width": 28, "height": 28,  "alias": "RV-INT-VF6" },
		"Element:57:48": { "x": 1020,   "y": 555, "width": 28, "height": 28,  "alias": "RV-INT-VF7" },
		"Element:57:49": { "x": 1163,   "y": 555, "width": 28, "height": 28,  "alias": "RV-INT-VF8" },
		"Element:57:50": { "x": 1307,   "y": 555, "width": 28, "height": 28,  "alias": "RV-INT-VF9" },
		"Element:57:51": { "x": 1451,   "y": 555, "width": 28, "height": 28,  "alias": "RV-INT-VF10" },
	},
	{
		"id":"synopticUnifilarCGBT",
		"parentId":"Element:56:4",
		"ExclusiveLayers": false,
		"tittle": "RIO VERDE",
		"BaseLayers": {
			"BASE_LAYER": "UNIFlLAR RIO VERDE CABLE - CGBT_v3_1.svg",
		},
		"Layers": {
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},
			"MAGNETOTERMICO" : {
				"items": ["Element:57:52", "Element:57:53", "Element:57:54", "Element:57:55", "Element:57:56", "Element:57:57", 
				          "Element:57:58", "Element:57:59", "Element:57:60", "Element:57:61", "Element:57:62", "Element:57:63", "Element:57:64"],
				"translation_key": "",
				"visibility": "visible"
			}
		},
		"Element:57:52": { "x": 550,   "y": 490, "width": 28, "height": 28,  "alias": "RV-INT-CSA-1" },
		"Element:57:53": { "x": 722,   "y": 490, "width": 28, "height": 28,  "alias": "RV-INT-BOMB-LIQ-1" },
		"Element:57:54": { "x": 915,   "y": 490, "width": 28, "height": 28,  "alias": "RV-INT-CSBT-1" },
		"Element:57:55": { "x": 1303,   "y": 490, "width": 28, "height": 28,  "alias": "RV-INT-SAI-IN-1" },
		"Element:57:56": { "x": 1303,   "y": 335, "width": 28, "height": 28,  "alias": "RV-INT-SAI-OUT-1" },
		"Element:57:57": { "x": 1399,   "y": 490, "width": 28, "height": 28,  "alias": "RV-INT-CSV-1" },
		"Element:57:58": { "x": 1496,   "y": 490, "width": 28, "height": 28,  "alias": "RV-INT-CS-PCI-1" },
		"Element:57:59": { "x": 1593,   "y": 490, "width": 28, "height": 28,  "alias": "RV-INT-BAT-1" },
		"Element:57:60": { "x": 326,   "y": 542, "width": 28, "height": 28,  "alias": "RV-INT-CGBT" },
		"Element:57:61": { "x": 1043,   "y": 600, "width": 28, "height": 28,  "alias": "RV-CONT-COM-CGBT" },
		"Element:57:62": { "x": 1193,   "y": 600, "width": 28, "height": 28,  "alias": "RV-CONT-COM-GE" },
		"Element:57:63": { "x": 1193,   "y": 660, "width": 28, "height": 28,  "alias": "RV-INT-GE" },
		"Element:57:64": { "x": 807,   "y": 660, "width": 28, "height": 28,  "alias": "RV-INT-ACOM-CGBT" },
		
	}
];
