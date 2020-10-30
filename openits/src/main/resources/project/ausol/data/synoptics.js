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
			"MATRIXPANEL": {
				"items": ["Element:36:1", "Element:36:2"],
				"translation_key": "",
				"visibility": "visible"
			},
			"CAM": {
				"items": ["Element:2:1", "Element:2:2", "Element:2:3", "Element:2:4", "Element:2:5", "Element:2:6", "Element:2:7", "Element:2:8", "Element:2:9", "Element:2:10"],
				"translation_key": "ElementType:2.description",
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
			"LUX": {
				"items": ["Element:12:1", "Element:12:2", "Element:12:3", "Element:12:4", "Element:12:5", "Element:12:6", "Element:12:7", "Element:12:8"],
				"translation_key": "",
				"visibility": "visible"
			},
			"LUM": {
				"items": ["Element:14:1", "Element:14:2"],
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
			"SOS": {
				"items": ["Element:11:1", "Element:11:2", , "Element:11:3", , "Element:11:4", , "Element:11:5", , "Element:11:6"],
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
	
		"Element:13:1": { "x": 1665, "y": 628, "width": 40,  "height": 40,  "alias": "RV-SRAV-1CA" },
		"Element:13:2": { "x": 84,   "y": 420, "width": 40,  "height": 40,  "alias": "RV-SRAV-1MA" },
		"Element:53:1": { "x": 1685, "y": 818, "width": 40,  "height": 40,  "alias": "RV-SAA-1CA" },
		"Element:53:2": { "x": 1645, "y": 818, "width": 40,  "height": 40,  "alias": "RV-SAA-2CA" },
		"Element:53:3": { "x": 104,  "y": 225, "width": 40,  "height": 40,  "alias": "RV-SAA-1MA" },
		"Element:53:4": { "x": 64,  "y": 225, "width": 40,  "height": 40,  	"alias": "RV-SAA-2MA" },

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

		"Element:36:1": { "x": 63,   "y": 310, "width": 200, "height": 54,  "alias": "GT_1" },
		"Element:36:2": { "x": 1517, "y": 715, "width": 200, "height": 54,  "alias": "GT_2" },

		"Element:23:1": { "x": 100,  "y": 711, "width": 120, "height": 120, "alias": "DETECTOR_1",
			"values": [
				{name: "average_length", label: "AL"},
				{name: "average_speed", label: "AS"},
				{name: "total_intensity", label: "TI"}
			]
		},
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
	
		"Element:13:1": { "x": 1820, "y": 660,  "width": 20,  "height": 20 },
		"Element:13:2": { "x": 1410, "y": 640,  "width": 20,  "height": 20 },

		"Element:53:1": { "x": 1850, "y": 660,  "width": 20,  "height": 20 },
		"Element:53:2": { "x": 1880, "y": 660,  "width": 20,  "height": 20 },
		"Element:53:3": { "x": 1440, "y": 640,  "width": 20,  "height": 20 },
		"Element:53:4": { "x": 1470, "y": 640,  "width": 20,  "height": 20 },
		
		"Element:8:1":  { "x": 1820, "y": 730,  "width": 88,  "height": 48 },
		"Element:8:2":  { "x": 1820, "y": 690,  "width": 88,  "height": 48 },
		"Element:8:3":  { "x": 1411, "y": 590,  "width": 88,  "height": 48 },
		"Element:8:4":  { "x": 1411, "y": 550,  "width": 88,  "height": 48 },

		"Element:7:1":  { "x": 1505, "y": 775,  "width": 305, "height": 37 },
	}
];