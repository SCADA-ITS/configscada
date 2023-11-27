export let synopticData= [
	{
		"id":"synopticTunnel",
		"parentId":"Element:7:1",
		"ExclusiveLayers": false,
		"tittle": "RIO VERDE",
		"BaseLayers": {
			"BASE_LAYER": "tunel.svg"
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
				"items": ["Element:4:1", "Element:4:2"],
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
				"items": ["Element:13:1", "Element:13:2"],
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
				"items": ["Element:16:1", "Element:16:2", "Element:16:3", "Element:16:4", "Element:16:5", "Element:16:6"],
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
				"items": ["Element:51:1"],
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

		"Element:4:1": { "x": 100,  "y": 711, "width": 80, "height": 80, "alias": "SEC_1",
			"values": [
				{name: "etd_speed", label: "V (K/h)"},
				{name: "etd_veh_length_1", label: "C1L"},
				{name: "etd_veh_length_2", label: "C2L"},
				{name: "etd_veh_length_3", label: "C3L"}
			]
		},
		"Element:4:2": { "x": 1600,  "y": 300, "width": 80, "height": 80, "alias": "SEC_2",
			"values": [
				{name: "etd_speed", label: "V (K/h)"},
				{name: "etd_veh_length_1", label: "C1L"},
				{name: "etd_veh_length_2", label: "C2L"},
				{name: "etd_veh_length_3", label: "C3L"}
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
	
		"Element:13:1": { "x": 1665, "y": 628, "width": 40,  "height": 40,  "alias": "RV-SRAV-1CA" },
		"Element:13:2": { "x": 84,   "y": 420, "width": 40,  "height": 40,  "alias": "RV-SRAV-1MA" },

		"Element:14:1": { "x": 1640, "y": 503, "width": 60,  "height": 60,  "alias": "RV-LUM-1CA" },
		"Element:14:2": { "x": 77,   "y": 503, "width": 60,  "height": 60,  "alias": "RV-LUM-1MA" },

		"Element:15:1": { "x": 434,  "y": 870, "width": 315, "height": 20,  "alias": "FIRE_1_SC" },
		"Element:15:2": { "x": 434,  "y": 195, "width": 315, "height": 20,  "alias": "FIRE_1_SM" },
		"Element:15:3": { "x": 748,  "y": 870, "width": 315, "height": 20,  "alias": "FIRE_2_SC" },
		"Element:15:4": { "x": 748,  "y": 195, "width": 315, "height": 20,  "alias": "FIRE_2_SM" },
		"Element:15:5": { "x": 1063, "y": 870, "width": 315, "height": 20,  "alias": "FIRE_3_SC" },
		"Element:15:6": { "x": 1063, "y": 195, "width": 315, "height": 20,  "alias": "FIRE_3_SM" },
		
		"Element:16:1": { "x": 1377,  "y": 718, "width": 40,  "height": 40,  "alias": "RV-MEG_1CA" },
		"Element:16:2": { "x": 881,  "y": 600, "width": 40,  "height": 40,  "alias": "RV-MEG_2CA" },
		"Element:16:3": { "x": 350,  "y": 290, "width": 40,  "height": 40,  "alias": "RV-MEG_1MA" },
		"Element:16:4": { "x": 881,  "y": 444, "width": 40,  "height": 40,  "alias": "RV-MEG_2MA" },
		"Element:16:5": { "x": 350,  "y": 290, "width": 40,  "height": 40,  "alias": "RV-MEG_1MA" },
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

		"Element:36:1": { "x": 63,   "y": 335, "width": 200, "height": 54,  "alias": "GT_1" },
		"Element:36:2": { "x": 1517, "y": 735, "width": 200, "height": 54,  "alias": "GT_2" },
		
		"Element:51:1": { "x": 210,   "y": 360, "width": 184, "height": 204,  "alias": "RV-ETD-1MA" },
		
		"Element:53:1": { "x": 1690, "y": 818, "width": 30,  "height": 35,  "alias": "RV-SAA-1CA" },
		"Element:53:2": { "x": 1645, "y": 818, "width": 30,  "height": 35,  "alias": "RV-SAA-2CA" },
		"Element:53:3": { "x": 95,  "y": 240, "width": 30,  "height": 35,  "alias": "RV-SAA-1MA" },
		"Element:53:4": { "x": 50,  "y": 240, "width": 30,  "height": 35,  	"alias": "RV-SAA-2MA" },

		"Element:55:1": { "x": 1550,   "y": 445, "width": 120, "height": 120,  "alias": "RV-CT-ITS-1" },
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
				"items": ["Element:13:1", "Element:13:2"],
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
		"Element:13:2": { "x": 1407, "y": 652,  "width": 22,  "height": 25 },

		"Element:53:1": { "x": 1845, "y": 653,  "width": 22,  "height": 22 },
		"Element:53:2": { "x": 1880, "y": 653,  "width": 22,  "height": 22 },
		"Element:53:3": { "x": 1430, "y": 660,  "width": 22,  "height": 22 },
		"Element:53:4": { "x": 1465, "y": 660,  "width": 22,  "height": 22 },
		
		"Element:8:1":  { "x": 1820, "y": 730,  "width": 88,  "height": 48 },
		"Element:8:2":  { "x": 1820, "y": 690,  "width": 88,  "height": 48 },
		"Element:8:3":  { "x": 1411, "y": 590,  "width": 88,  "height": 48 },
		"Element:8:4":  { "x": 1411, "y": 550,  "width": 88,  "height": 48 },
		
		"Element:4:1": { "x": 1410,  "y": 700, "width": 80, "height": 80, "alias": "SEC_1",
			"values": [
				{name: "etd_speed", label: "V (K/h)"},
				{name: "etd_veh_length_1", label: "C1L"},
				{name: "etd_veh_length_2", label: "C2L"},
				{name: "etd_veh_length_3", label: "C3L"}
			]
		},
		"Element:4:2": { "x": 1825,  "y": 555, "width": 80, "height": 80, "alias": "SEC_2",
			"values": [
				{name: "etd_speed", label: "V (K/h)"},
				{name: "etd_veh_length_1", label: "C1L"},
				{name: "etd_veh_length_2", label: "C2L"},
				{name: "etd_veh_length_3", label: "C3L"}
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
				"items": ["Element:56:1"],
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
			"TEMP_PROBE" : {
				"items": ["Element:62:1"],
				"translation_key": "",
				"visibility": "visible"
			},
			"AIR_CONDITIONER" : {
				"items": ["Element:63:1"],
				"translation_key": "",
				"visibility": "visible"
			},
		},

		"Element:56:1": { "x": 1220,   "y": 15, "width": 130, "height": 200,  "alias": "RV-CSA" },

		"Element:58:1": { "x": 1440,   "y": 290, "width": 300, "height": 300,  "alias": "RV-CT-FIRE-PULS-1" },

		"Element:59:1": { "x": -30,   "y": 680, "width": 200, "height": 200,  "alias": "RV-CT-FIRE-HUM-1" },

		"Element:60:1": { "x": 900,   "y": 100, "width": 200, "height": 200,  "alias": "RV-CT-FIRE-SIR-1" },

		"Element:61:1": { "x": 150,   "y": 200, "width": 100, "height": 100,  "alias": "RV-CT-FIRE-DET-1" },

		"Element:62:1": { "x": 310,   "y": 870, "width": 50, "height": 50,  "alias": "RV-CT-SONDA-1" },

		"Element:63:1": { "x": 260,   "y": 880, "width": 50, "height": 50,  "alias": "RV-CT-AIRE-1" },
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
				"items": ["Element:57:8", "Element:57:9", "Element:57:10", "Element:57:11", "Element:57:12", "Element:57:13", "Element:57:14", "Element:57:15", "Element:57:16", "Element:57:17", "Element:57:18", "Element:57:19", "Element:57:25", "Element:57:24", "Element:57:26", "Element:57:27", "Element:57:28", "Element:57:29", "Element:57:30", "Element:57:31"],
				"translation_key": "",
				"visibility": "visible"
			}
		},
		"Element:57:8": { "x": 985,   "y": 713, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A3" },
		"Element:57:9": { "x": 793,   "y": 713, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A4" },
		"Element:57:10": { "x": 1081,   "y": 713, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A5" },
		"Element:57:11": { "x": 889,   "y": 713, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A6" },
		"Element:57:12": { "x": 602,   "y": 713, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A10" },
		"Element:57:13": { "x": 410,   "y": 713, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A11" },
		"Element:57:14": { "x": 698,   "y": 713, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A12" },
		"Element:57:15": { "x": 505,   "y": 713, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A13" },
		"Element:57:16": { "x": 1178,   "y": 713, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A15" },
		"Element:57:17": { "x": 1369,   "y": 713, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A1" },
		"Element:57:18": { "x": 1273,   "y": 713, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A16" },
		"Element:57:19": { "x": 1464,   "y": 713, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A18" },
		"Element:57:24": { "x": 890,   "y": 332, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AA1" },
		"Element:57:25": { "x": 795,   "y": 332, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AA2" },
		"Element:57:26": { "x": 698,   "y": 332, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AA8" },
		"Element:57:27": { "x": 602,   "y": 332, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AA9" },
		"Element:57:28": { "x": 985,   "y": 332, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AE7" },
		"Element:57:29": { "x": 1081,   "y": 332, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AE14" },
		"Element:57:30": { "x": 1178,   "y": 332, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AE15" },
		"Element:57:31": { "x": 1273,   "y": 332, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AE16" },
	},
	/*{
		"id":"synopticUnifilarCsb",
		"parentId":"Element:7:1",
		"ExclusiveLayers": false,
		"tittle": "RIO VERDE",
		"BaseLayers": {
			"BASE_LAYER": "UNIFlLAR-RIO-VERDE-1-1-CABLE.svg",
			"UNIFILAR_CSB_ICONO": "UNIFlLAR-RIO-VERDE-1-1-ICONO.svg"
		},
		"Layers": {
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},
			"UNIFILAR_CSB_ICONO": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},
		},
	}*/
];
