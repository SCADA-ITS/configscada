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
				"items": ["Element:15:1", "Element:15:2", "Element:15:3", "Element:15:4"],
				"translation_key": "",
				"visibility": "visible"
			},
			"MEG_CONTROLLER": {
				"items": ["Element:5:1"],
				"translation_key": "",
				"visibility": "visible"
			},
			"MEG": {
				"items": ["Element:16:1", "Element:16:2", "Element:16:3", "Element:16:4"],
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
				"items": ["Element:55:2"],
				"translation_key": "",
				"visibility": "visible"
			},
			"VELETA": {
				"items": ["Element:66:1"],
				"translation_key": "",
				"visibility": "visible"
			},
			"METEO": {
				"items": ["Element:1:1"],
				"translation_key": "",
				"visibility": "visible"
			},
			"SEM_ROJO_ROJO": {
				"items": ["Element:67:1", "Element:67:2", "Element:67:3", "Element:67:4"],
				"translation_key": "",
				"visibility": "visible"
			},
			"GRUPO_ELECTROGENO": {
				"items": ["Element:75:1"],
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

		"Element:4:1": { "x": 1485,  "y": 711, "width": 60, "height": 80, "alias": "SEC_1_ETD1",
			"values": [
				{name: "etd_intensity", label: "I (V/h)"},
				{name: "etd_speed", label: "V (K/h)"},
				{name: "etd_occupancy", label: "O (%)"},
				{name: "etd_veh_length_1_percent", label: "C (%L)"}
			]
		},
		"Element:4:2": { "x": 1485,  "y": 300, "width": 60, "height": 80, "alias": "SEC_2_ETD1",
			"values": [
				{name: "etd_intensity", label: "I (V/h)"},
				{name: "etd_speed", label: "V (K/h)"},
				{name: "etd_occupancy", label: "O (%)"},
				{name: "etd_veh_length_1_percent", label: "C (%L)"}
			]
		},
		"Element:4:3": { "x": 265,  "y": 300, "width": 60, "height": 80, "alias": "SEC_1_ETD2",
			"values": [
				{name: "etd_intensity", label: "I (V/h)"},
				{name: "etd_speed", label: "V (K/h)"},
				{name: "etd_occupancy", label: "O (%)"},
				{name: "etd_veh_length_1_percent", label: "C (%L)"}
			]
		},
		"Element:4:4": { "x": 265,  "y": 711, "width": 60, "height": 80, "alias": "SEC_2_ETD2",
			"values": [
				{name: "etd_intensity", label: "I (V/h)"},
				{name: "etd_speed", label: "V (K/h)"},
				{name: "etd_occupancy", label: "O (%)"},
				{name: "etd_veh_length_1_percent", label: "C (%L)"}
			]
		},

		"Element:1:1": { "x": 260,  "y": 517, "width": 40,  "height": 40,  "alias": "RV-MET-1MA" },		

		"Element:8:1":  { "x": 1529, "y": 817, "width": 88,  "height": 48,  "alias": "RV-BAR-1CA" },
		"Element:8:2":  { "x": 1529, "y": 658, "width": 88,  "height": 48,  "alias": "RV-BAR-2CA" },
		"Element:8:3":  { "x": 170,   "y": 258, "width": 88,  "height": 48,  "alias": "RV-BAR-1MA" },
		"Element:8:4":  { "x": 170,   "y": 407, "width": 88,  "height": 48,  "alias": "RV-BAR-2MA" },

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

		"Element:11:1": { "x": 1700,  "y": 910, "width": 40,  "height": 50,  "alias": "RV-SOSE-1CA" },		
		"Element:11:2": { "x": 40, "y": 910, "width": 40,  "height": 50,  "alias": "RV-SOSE-2CA" },
		"Element:11:3": { "x": 40,  "y": 140, "width": 40,  "height": 50,  "alias": "RV-SOSE-1MA" },		
		"Element:11:4": { "x": 1700, "y": 140, "width": 40,  "height": 50,  "alias": "RV-SOSE-2MA" },
		"Element:11:5": { "x": 670,  "y": 910, "width": 40,  "height": 50,  "alias": "RV-SOS-1CA" },		
		"Element:11:6": { "x": 640, "y": 140, "width": 40,  "height": 50,  "alias": "RV-SOS-1MA" },
		
		"Element:12:1": { "x": 1450, "y": 947, "width": 6,  "height": 9,  "alias": "RV-LUX-1CA" },
		"Element:12:2": { "x": 1350,  "y": 947, "width": 6,  "height": 9,  "alias": "RV-LUX-2CA" },
		"Element:12:3": { "x": 550, "y": 947, "width": 6,  "height": 9,  "alias": "RV-LUX-3CA" },
		"Element:12:4": { "x": 300,  "y": 947, "width": 6,  "height": 9,  "alias": "RV-LUX-4CA" },
		"Element:12:5": { "x": 320,  "y": 178, "width": 6,  "height": 9,  "alias": "RV-LUX-1MA" },
		"Element:12:6": { "x": 500, "y": 178, "width": 6,  "height": 9,  "alias": "RV-LUX-2MA" },
		"Element:12:7": { "x": 1200,  "y": 178, "width": 6,  "height": 9,  "alias": "RV-LUX-3MA" },
		"Element:12:8": { "x": 1400, "y": 178, "width": 6,  "height": 9,  "alias": "RV-LUX-4MA" },
	
		"Element:13:1": { "x": 1619, "y": 650, "width": 50,  "height": 50,  "alias": "RV-SRAV-1CA" },
		"Element:13:2": { "x": 115,   "y": 385, "width": 50,  "height": 50,  "alias": "RV-SRAV-1MA" },
		"Element:13:3": { "x": 1619, "y": 785, "width": 50,  "height": 50,  "alias": "RV-SRAV-2CA" },
		"Element:13:4": { "x": 115,   "y": 233, "width": 50,  "height": 50,  "alias": "RV-SRAV-2MA" },

		"Element:14:1": { "x": 1660, "y": 553, "width": 6,  "height": 9,  "alias": "RV-LUM-1CA" },
		"Element:14:2": { "x": 85,   "y": 553, "width": 6,  "height": 9,  "alias": "RV-LUM-1MA" },

		"Element:15:1": { "x": 898,  "y": 800, "width": 240, "height": 90,  "alias": "RV-ZI-1CA" },
		"Element:15:2": { "x": 184,  "y": 800, "width": 240, "height": 90,  "alias": "RV-ZI-2CA" },
		"Element:15:3": { "x": 184,  "y": 125, "width": 240, "height": 90,  "alias": "RV-ZI-1MA" },
		"Element:15:4": { "x": 898,  "y": 125, "width": 240, "height": 90,  "alias": "RV-ZI-2MA" },
		
		"Element:5:1": { "x": 1398, "y": 410, "width": 160,  "height": 160,  "alias": "PA-ESTEPONA" },
		
		"Element:16:1": { "x": 1428, "y": 645, "width": 120,  "height": 120,  "alias": "PA-ZONE-59" },
		"Element:16:2": { "x": 911,  "y": 450, "width": 120,  "height": 120,  "alias": "PA-ZONE-60" },
		"Element:16:3": { "x": 360,  "y": 245, "width": 120,  "height": 120,  "alias": "PA-ZONE-61" },
		"Element:16:4": { "x": 851,  "y": 450, "width": 120,  "height": 120,  "alias": "PA-ZONE-62" },

		"Element:17:1": { "x": 120,  "y": 517, "width": 50,  "height": 50,  "alias": "RV-ANE-1CA" },

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
		
		"Element:51:1": { "x": 1475,   "y": 470, "width": 80, "height": 100,  "alias": "RV-ETD-1" },
		"Element:51:2": { "x": 90,   "y": 80, "width": 80, "height": 100,  "alias": "RV-ETD-1" },
		
		"Element:53:1": { "x": 1670, "y": 685, "width": 5,  "height": 5,  "alias": "RV-SAA-1CA" },
		"Element:53:2": { "x": 1670, "y": 818, "width": 5,  "height": 5,  "alias": "RV-SAA-2CA" },
		"Element:53:3": { "x": 80,  "y": 270, "width": 5,  "height": 5,  "alias": "RV-SAA-1MA" },
		"Element:53:4": { "x": 80,  "y": 420, "width": 5,  "height": 5,  	"alias": "RV-SAA-2MA" },

		"Element:55:2": { "x": 1570,   "y": 500, "width": 55, "height": 80,  "alias": "RV-MAP-CT" },
		
		"Element:66:1": { "x": 170,  "y": 495, "width": 80,  "height": 80,  "alias": "RV-VTA-1C" },

		"Element:67:1": { "x": 1715,   "y": 685, "width": 5, "height": 5,  "alias": "RV-SMF-RR-1CA" },
		"Element:67:2": { "x": 1715,   "y": 818, "width": 5, "height": 5,  "alias": "RV-SMF-RR-1MA" },
		"Element:67:3": { "x": 40,   "y": 270, "width": 5, "height": 5,  "alias": "RV-SMF-RR-2CA" },
		"Element:67:4": { "x": 40,   "y": 420, "width": 5, "height": 5,  "alias": "RV-SMF-RR-2MA" },

		"Element:75:1": { "x": 340,   "y": 460, "width": 110, "height": 110,  "alias": "RV-GEL-1CA" },
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
			"SEM_ROJO_ROJO": {
				"items": ["Element:67:1", "Element:67:2", "Element:67:3", "Element:67:4"],
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
	
		"Element:13:1": { "x": 1823, "y": 670,  "width": 25,  "height": 25 },
		"Element:13:3": { "x": 1823, "y": 645,  "width": 25,  "height": 25 },
		"Element:13:2": { "x": 1407, "y": 670,  "width": 25,  "height": 25 },
		"Element:13:4": { "x": 1407, "y": 645,  "width": 25,  "height": 25 },

		"Element:53:1": { "x": 1853, "y": 690,  "width": 3,  "height": 3 },
		"Element:53:2": { "x": 1853, "y": 665,  "width": 3,  "height": 3 },
		"Element:53:3": { "x": 1437, "y": 690,  "width": 3,  "height": 3 },
		"Element:53:4": { "x": 1437, "y": 665,  "width": 3,  "height": 3 },
		
		"Element:67:1": { "x": 1883, "y": 690,  "width": 3,  "height": 3 },
		"Element:67:2": { "x": 1467, "y": 690,  "width": 3,  "height": 3 },
		"Element:67:3": { "x": 1883, "y": 665,  "width": 3,  "height": 3 },
		"Element:67:4": { "x": 1467, "y": 665,  "width": 3,  "height": 3 },
		
		"Element:8:1":  { "x": 1820, "y": 740,  "width": 88,  "height": 48 },
		"Element:8:2":  { "x": 1820, "y": 700,  "width": 88,  "height": 48 },
		"Element:8:3":  { "x": 1411, "y": 590,  "width": 88,  "height": 48 },
		"Element:8:4":  { "x": 1411, "y": 550,  "width": 88,  "height": 48 },
		
		"Element:4:4": { "x": 1410,  "y": 710, "width": 60, "height": 80, "alias": "SEC_1",
			"values": [
				{name: "etd_intensity", label: "I (V/h)"},
				{name: "etd_speed", label: "V (K/h)"},
				{name: "etd_occupancy", label: "O (%)"},
				{name: "etd_veh_length_1_percent", label: "C (%L)"}
			]
		},
		"Element:4:2": { "x": 1825,  "y": 555, "width": 60, "height": 80, "alias": "SEC_2",
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
			},
			"ANALIZADOR_RED" : {
				"items": ["Element:70:1"],
				"translation_key": "",
				"visibility": "visible"
			},
			"ERU" : {
				"items": ["Element:74:1", "Element:74:2"],
				"translation_key": "",
				"visibility": "visible"
			},
			"GRUPO_ELECTROGENO" : {
				"items": ["Element:75:2"],
				"translation_key": "",
				"visibility": "visible"
			}
		},

		"Element:56:1": { "x": 1220,   "y": 15, "width": 130, "height": 200,  "alias": "RV-CSA" },
		"Element:56:2": { "x": 1020,   "y": 15, "width": 130, "height": 200,  "alias": "RV-CSB" },
		"Element:56:3": { "x": 820,   "y": 15, "width": 130, "height": 200,  "alias": "RV-CSV" },
		"Element:56:4": { "x": 620,   "y": 15, "width": 130, "height": 200,  "alias": "RV-CGBT" },

		"Element:58:1": { "x": 1640,   "y": -15, "width": 300, "height": 300,  "alias": "RV-CT-FIRE-PULS-1" },

		"Element:59:1": { "x": 150,   "y": 700, "width": 90, "height": 90,  "alias": "RV-CT-FIRE-HUM-1" },

		"Element:60:1": { "x": 900,   "y": 100, "width": 200, "height": 200,  "alias": "RV-CT-FIRE-SIR-1" },

		"Element:61:1": { "x": 150,   "y": 200, "width": 100, "height": 100,  "alias": "RV-CT-FIRE-DET-1" },
		
		"Element:65:1": { "x": 1640,   "y": 475, "width": 120, "height": 120,  "alias": "RV-SAI-1" },

		"Element:70:1": { "x": 1640,   "y": 675, "width": 22, "height": 27,  "alias": "RV-ANALIZADOR_RED-1" },
		
		"Element:74:1": { "x": 1640,   "y": 390, "width": 35, "height": 35,  "alias": "RV-ERUM-1" },
		"Element:74:2": { "x": 1640,   "y": 330, "width": 35, "height": 35,  "alias": "RV-ERUS-1" },

		"Element:75:2": { "x": 1640,   "y": 680, "width": 110, "height": 110,  "alias": "RV-DEP-GE" },
	},
	{
		"id":"synopticUnifilarCsa",
		"parentId":"Element:56:1",
		"ExclusiveLayers": false,
		"tittle": "RIO VERDE",
		"BaseLayers": {
			"BASE_LAYER": "UNIFILARES ILUMINACION CABLES-Model_v0.2.svg",
		},
		"Layers": {
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},
			"INTERRUPTOR" : {
				"items": ["Element:57:7", "Element:57:8", "Element:57:9", "Element:57:10", "Element:57:11", "Element:57:12", "Element:57:13", "Element:57:14", 
				          "Element:57:15", "Element:57:16", "Element:57:17", "Element:57:18", "Element:57:19", "Element:57:20", "Element:57:21", "Element:57:22", 
				          "Element:57:23", "Element:57:24", "Element:57:25", "Element:57:26", "Element:57:67", "Element:57:68", "Element:57:69", "Element:57:70",
						  "Element:57:71", "Element:57:72", "Element:57:73", "Element:57:74", "Element:57:75", "Element:57:76", "Element:57:77", "Element:57:78", 
						  "Element:57:79", "Element:57:80", "Element:57:81", "Element:57:82", "Element:57:83", "Element:57:84", "Element:57:85", "Element:57:86", 
						  "Element:57:87", "Element:57:88"],
				"translation_key": "",
				"visibility": "visible"
			}
		},
		"Element:57:67": { "x": 948,   "y": 870, "width": 28, "height": 28,  "alias": "RV-INT-CSA-2" },
		"Element:57:7": { "x": 998,   "y": 770, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A3" },
		"Element:57:69": { "x": 998,   "y": 689, "width": 28, "height": 28,  "alias": "RV-CONT-A3" },
		"Element:57:8": { "x": 797,   "y": 770, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A4" },
		"Element:57:70": { "x": 797,   "y": 689, "width": 28, "height": 28,  "alias": "RV-CONT-A4" },
		"Element:57:9": { "x": 1098,   "y": 770, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A5" },
		"Element:57:71": { "x": 1098,   "y": 689, "width": 28, "height": 28,  "alias": "RV-CONT-A5" },
		"Element:57:10": { "x": 897,   "y": 770, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A6" },
		"Element:57:72": { "x": 897,   "y": 689, "width": 28, "height": 28,  "alias": "RV-CONT-A6" },
		"Element:57:11": { "x": 595,   "y": 770, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A10" },
		"Element:57:73": { "x": 595,   "y": 689, "width": 28, "height": 28,  "alias": "RV-CONT-A10" },
		"Element:57:12": { "x": 394,   "y": 770, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A11" },
		"Element:57:74": { "x": 394,   "y": 689, "width": 28, "height": 28,  "alias": "RV-CONT-A11" },
		"Element:57:13": { "x": 696,   "y": 770, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A12" },
		"Element:57:75": { "x": 696,   "y": 689, "width": 28, "height": 28,  "alias": "RV-CONT-A12" },
		"Element:57:14": { "x": 494,   "y": 770, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A13" },
		"Element:57:76": { "x": 494,   "y": 689, "width": 28, "height": 28,  "alias": "RV-CONT-A13" },
		"Element:57:15": { "x": 1199,   "y": 770, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A15" },
		"Element:57:77": { "x": 1199,   "y": 689, "width": 28, "height": 28,  "alias": "RV-CONT-A15" },
		"Element:57:16": { "x": 1400,   "y": 770, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A1" },
		"Element:57:78": { "x": 1400,   "y": 689, "width": 28, "height": 28,  "alias": "RV-CONT-A1" },
		"Element:57:17": { "x": 1299,   "y": 770, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A16" },
		"Element:57:79": { "x": 1299,   "y": 689, "width": 28, "height": 28,  "alias": "RV-CONT-A16" },
		"Element:57:18": { "x": 1501,   "y": 770, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A18" },
		"Element:57:80": { "x": 1501,   "y": 689, "width": 28, "height": 28,  "alias": "RV-CONT-A18" },
		
		"Element:57:68": { "x": 947,   "y": 427, "width": 28, "height": 28,  "alias": "RV-MAG-CSA-SAI" },
		"Element:57:19": { "x": 897,   "y": 330, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AA1" },
		"Element:57:84": { "x": 897,   "y": 248, "width": 28, "height": 28,  "alias": "RV-CONT-AA1" },
		"Element:57:20": { "x": 797,   "y": 330, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AA2" },
		"Element:57:83": { "x": 797,   "y": 248, "width": 28, "height": 28,  "alias": "RV-CONT-AA2" },
		"Element:57:21": { "x": 696,   "y": 330, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AA8" },
		"Element:57:82": { "x": 696,   "y": 248, "width": 28, "height": 28,  "alias": "RV-CONT-AA8" },
		"Element:57:22": { "x": 596,   "y": 330, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AA9" },
		"Element:57:81": { "x": 596,   "y": 248, "width": 28, "height": 28,  "alias": "RV-CONT-AA9" },
		"Element:57:23": { "x": 998,   "y": 330, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AE7" },
		"Element:57:85": { "x": 998,   "y": 248, "width": 28, "height": 28,  "alias": "RV-CONT-AE7" },
		"Element:57:24": { "x": 1098,   "y": 330, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AE14" },
		"Element:57:86": { "x": 1098,   "y": 248, "width": 28, "height": 28,  "alias": "RV-CONT-AE14" },
		"Element:57:25": { "x": 1198,   "y": 330, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AE15" },
		"Element:57:87": { "x": 1198,   "y": 248, "width": 28, "height": 28,  "alias": "RV-CONT-AE15" },
		"Element:57:26": { "x": 1299,   "y": 330, "width": 28, "height": 28,  "alias": "RV-MAGDIF-AE16" },
		"Element:57:88": { "x": 1299,   "y": 248, "width": 28, "height": 28,  "alias": "RV-CONT-AE16" },

	},
	{
		"id":"synopticUnifilarCsb",
		"parentId":"Element:56:2",
		"ExclusiveLayers": false,
		"tittle": "RIO VERDE",
		"BaseLayers": {
			"BASE_LAYER": "UNIFlLAR RIO VERDE CABLE -CSB_v4.svg",
		},
		"Layers": {
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},
			"INTERRUPTOR" : {
				"items": ["Element:57:1", "Element:57:2", "Element:57:3", "Element:57:4", "Element:57:5", "Element:57:6", "Element:57:29", "Element:57:30", "Element:57:32", 
				          "Element:57:33", "Element:57:34", "Element:57:35", "Element:57:36", "Element:57:37", "Element:57:38", "Element:57:39","Element:57:40", "Element:57:41",
				          "Element:57:65", "Element:57:66","Element:57:92", "Element:57:93"],
				"translation_key": "",
				"visibility": "visible"
			}
		},
		
		"Element:57:41": { "x": 948,   "y": 815, "width": 28, "height": 28,  "alias": "RV-MAG-CSB-ITS-1" },
		"Element:57:29": { "x": 395,   "y": 720, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF10" },
		"Element:57:30": { "x": 496,   "y": 720, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF11" },
		"Element:57:32": { "x": 597,   "y": 720, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF13" },
		"Element:57:33": { "x": 698,   "y": 720, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF14" },	
		"Element:57:34": { "x": 799,   "y": 720, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF15" },
		"Element:57:92": { "x": 799,   "y": 639, "width": 28, "height": 28,  "alias": "RV-CONT-BF15-BARR" },
		"Element:57:35": { "x": 900,   "y": 720, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF19" },
		"Element:57:36": { "x": 1000,   "y": 720, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF20" },
		"Element:57:37": { "x": 1102,   "y": 720, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF21" },
		"Element:57:38": { "x": 1202,   "y": 720, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF22" },
		"Element:57:39": { "x": 1303,   "y": 720, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF23" },
		"Element:57:3": { "x": 1404,   "y": 720, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF28-SOS" },	
		"Element:57:40": { "x": 1505,   "y": 720, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF26" },
		
		"Element:57:1": { "x": 948,   "y": 410, "width": 28, "height": 28,  "alias": "RV-MAG-CSB-ITS-2" },
		"Element:57:2": { "x": 700,   "y": 315, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF27-PMV" },
		"Element:57:6": { "x": 796,   "y": 315, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF32" },
		"Element:57:66": { "x": 897,   "y": 315, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF34" },
		"Element:57:4": { "x": 997,   "y": 315, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF30-BARR-M" },
		"Element:57:93": { "x": 997,   "y": 232, "width": 28, "height": 28,  "alias": "RV-CONT-BF30-BARR" },
		"Element:57:5": { "x": 1099,   "y": 315, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF31-SEM" },
		"Element:57:65": { "x": 1201,   "y": 315, "width": 28, "height": 28,  "alias": "RV-MAGDIF-BF33" },
	},
	{
		"id":"synopticUnifilarCsv",
		"parentId":"Element:56:3",
		"ExclusiveLayers": false,
		"tittle": "RIO VERDE",
		"BaseLayers": {
			"BASE_LAYER": "UNIFILARES VENTILACION CABLES-Model_v0.3.svg",
		},
		"Layers": {
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},
			"INTERRUPTOR" : {
				"items": ["Element:57:16", "Element:57:18", "Element:57:42", "Element:57:43", "Element:57:44", "Element:57:45", 
				          "Element:57:46", "Element:57:47", "Element:57:48", "Element:57:49", "Element:57:50", "Element:57:51",
						  "Element:57:89", "Element:57:90", "Element:57:91"],
				"translation_key": "",
				"visibility": "visible"
			}
		},
		"Element:57:89": { "x": 948,   "y": 736, "width": 28, "height": 28,  "alias": "RV-INT-CSV-2" },
		"Element:57:42": { "x": 154,   "y": 615, "width": 28, "height": 28,  "alias": "RV-INT-VF1" },
		"Element:57:43": { "x": 299,   "y": 615, "width": 28, "height": 28,  "alias": "RV-INT-VF2" },
		"Element:57:44": { "x": 444,   "y": 615, "width": 28, "height": 28,  "alias": "RV-INT-VF3" },
		"Element:57:45": { "x": 587,   "y": 615, "width": 28, "height": 28,  "alias": "RV-INT-VF4" },
		"Element:57:46": { "x": 732,   "y": 615, "width": 28, "height": 28,  "alias": "RV-INT-VF5" },
		"Element:57:47": { "x": 875,   "y": 615, "width": 28, "height": 28,  "alias": "RV-INT-VF6" },
		"Element:57:48": { "x": 1020,   "y": 615, "width": 28, "height": 28,  "alias": "RV-INT-VF7" },
		"Element:57:49": { "x": 1163,   "y": 615, "width": 28, "height": 28,  "alias": "RV-INT-VF8" },
		"Element:57:50": { "x": 1307,   "y": 615, "width": 28, "height": 28,  "alias": "RV-INT-VF9" },
		"Element:57:51": { "x": 1451,   "y": 615, "width": 28, "height": 28,  "alias": "RV-INT-VF10" },
		"Element:57:16": { "x": 1595,   "y": 615, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A1" },
		"Element:57:18": { "x": 1740,   "y": 615, "width": 28, "height": 28,  "alias": "RV-MAGDIF-A18" },
		"Element:57:90": { "x": 1595,   "y": 345, "width": 28, "height": 28,  "alias": "RV-RCAL-CV1" },
		"Element:57:91": { "x": 1740,   "y": 345, "width": 28, "height": 28,  "alias": "RV-RCAL-CV2" }

	},
	{
		"id":"synopticUnifilarCGBT",
		"parentId":"Element:56:4",
		"ExclusiveLayers": false,
		"tittle": "RIO VERDE",
		"BaseLayers": {
			"BASE_LAYER": "UNIFlLAR RIO VERDE CABLE - CGBT_v4.svg",
		},
		"Layers": {
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},
			"INTERRUPTOR" : {
				"items": ["Element:57:52", "Element:57:53", "Element:57:54", "Element:57:55", "Element:57:56", "Element:57:57", 
				          "Element:57:58", "Element:57:59", "Element:57:60", "Element:57:61", "Element:57:62", "Element:57:63", "Element:57:64"],
				"translation_key": "",
				"visibility": "visible"
			}
		},
		"Element:57:52": { "x": 537,   "y": 490, "width": 28, "height": 28,  "alias": "RV-INT-CSA-1" },
		"Element:57:53": { "x": 716,   "y": 490, "width": 28, "height": 28,  "alias": "RV-INT-BOMB-LIQ-1" },
		"Element:57:54": { "x": 918,   "y": 490, "width": 28, "height": 28,  "alias": "RV-INT-CSBT-1" },
		"Element:57:55": { "x": 1320,   "y": 490, "width": 28, "height": 28,  "alias": "RV-INT-SAI-IN-1" },
		"Element:57:56": { "x": 1320,   "y": 295, "width": 28, "height": 28,  "alias": "RV-INT-SAI-OUT-1" },
		"Element:57:57": { "x": 1422,   "y": 490, "width": 28, "height": 28,  "alias": "RV-INT-CSV-1" },
		"Element:57:58": { "x": 1522,   "y": 490, "width": 28, "height": 28,  "alias": "RV-INT-CS-PCI-1" },
		"Element:57:59": { "x": 1623,   "y": 490, "width": 28, "height": 28,  "alias": "RV-INT-BAT-1" },
		"Element:57:60": { "x": 304,   "y": 490, "width": 28, "height": 28,  "alias": "RV-INT-CGBT" },
		"Element:57:61": { "x": 1030,   "y": 597, "width": 28, "height": 28,  "alias": "RV-CONT-COM-CGBT" },
		"Element:57:62": { "x": 1207,   "y": 597, "width": 28, "height": 28,  "alias": "RV-CONT-COM-GE" },
		"Element:57:63": { "x": 1207,   "y": 730, "width": 28, "height": 28,  "alias": "RV-INT-GE" },
		"Element:57:64": { "x": 805,   "y": 730, "width": 28, "height": 28,  "alias": "RV-INT-ACOM-CGBT" },
		
	},
	{
		"id":"synopticUnifilarCtMap",
		"parentId":"Element:55:2",
		"ExclusiveLayers": false,
		"tittle": "CT MAP RIO VERDE",
		"BaseLayers": {
			"BASE_LAYER": "CUARTOS-TECNICOS-BOCA-RIO-VERDE_v0.14.svg",
		},
		"Layers": {
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},
			"CT" : {
				"items": ["Element:55:1"],
				"translation_key": "",
				"visibility": "visible"
			}
		},
		"Element:55:1": { "x": 925,   "y": 110, "width": 100, "height": 80,  "alias": "RV-CT-ITS-1" },
	}
];
