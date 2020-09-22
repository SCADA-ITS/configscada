export let synopticData= [
	{
		"id":"synopticTunnel",
		"parentId":"Element:7:1",
		"ExclusiveLayers": false,
		"BaseLayers": {
			"BASE_LAYER": "tunel.svg"
		},
		"Layers": {
			"DMS": {
				"items": ["Element:10:1", "Element:10:2"],
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
				"items": ["DMS", "MATRIXPANEL", "BAR", "SEM"],
				"translation_key": "",
				"visibility": "visible"
			},
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},
			"MATRIXPANEL": {
				"items": ["Element:26:1", "Element:35:1"],
				"translation_key": "",
				"visibility": "visible"
			},
			"CAM": {
				"items": ["Element:2:1", "Element:2:2", "Element:2:3", "Element:2:4", "Element:2:5", "Element:2:6", "Element:2:7", "Element:2:8"],
				"translation_key": "ElementType:2.description",
				"visibility": "visible"
			},
			"FAN": {
				"items": ["Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", "Element:9:6", "Element:9:7", "Element:9:8"],
				"translation_key": "",
				"visibility": "visible"
			},
			"BAR": {
				"items": ["Element:8:1", "Element:8:2"],
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
				"items": ["Element:12:1", "Element:12:2", "Element:12:3", "Element:12:4", "Element:12:5", "Element:12:6"],
				"translation_key": "",
				"visibility": "visible"
			},
			"LUM": {
				"items": ["Element:14:1", "Element:14:2"],
				"translation_key": "",
				"visibility": "visible"
			},
			"SEM": {
				"items": ["Element:13:1", "Element:13:2", "Element:13:3", "Element:13:4"],
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
				"items": ["Element:11:1", "Element:11:2", "Element:11:3", "Element:11:4", "Element:11:5", "Element:11:6"],
				"translation_key": "",
				"visibility": "visible"
			},
			"FIRE": {
				"items": ["Element:15:1", "Element:15:2", "Element:15:3", "Element:15:4", "Element:15:5", "Element:15:6"],
				"translation_key": "",
				"visibility": "visible"
			},
			"MEG": {
				"items": ["Element:16:1", "Element:16:2"],
				"translation_key": "",
				"visibility": "visible"
			},
		},
		"Element:8:1":  { "x": 1629, "y": 817, "width": 88,  "height": 48,  "alias": "BAR_1_SC" },
		"Element:8:2":  { "x": 60,   "y": 258, "width": 88,  "height": 48,  "alias": "BAR_1_SM" },
		"Element:9:1":  { "x": 1172, "y": 798, "width": 40,  "height": 40,  "alias": "FAN1_1_SC" },
		"Element:9:2":  { "x": 1172, "y": 678, "width": 40,  "height": 40,  "alias": "FAN1_2_SC" },
		"Element:9:3":  { "x": 720,  "y": 798, "width": 40,  "height": 40,  "alias": "FAN2_1_SC" },
		"Element:9:4":  { "x": 720,  "y": 678, "width": 40,  "height": 40,  "alias": "FAN2_2_SC" },
		"Element:9:5":  { "x": 720,  "y": 390, "width": 40,  "height": 40,  "alias": "FAN1_1_SM" },
		"Element:9:6":  { "x": 720,  "y": 272, "width": 40,  "height": 40,  "alias": "FAN1_2_SM" },
		"Element:9:7":  { "x": 1172, "y": 390, "width": 40,  "height": 40,  "alias": "FAN2_1_SM" },
		"Element:9:8":  { "x": 1172, "y": 272, "width": 40,  "height": 40,  "alias": "FAN2_2_SM" },
		"Element:10:1": { "x": 63,   "y": 310, "width": 200, "height": 54,  "alias": "DMS_1_Tunnel_1" },
		"Element:10:2": { "x": 1517, "y": 715, "width": 200, "height": 54,  "alias": "DMS_2_Tunnel_1" },
		"Element:11:1": { "x": 1548, "y": 907, "width": 40,  "height": 40,  "alias": "SOS_1_SC" },
		"Element:11:2": { "x": 156,  "y": 140, "width": 40,  "height": 40,  "alias": "SOS_1_SM" },
		"Element:11:3": { "x": 880,  "y": 907, "width": 40,  "height": 40,  "alias": "SOS_2_SC" },
		"Element:11:4": { "x": 966,  "y": 140, "width": 40,  "height": 40,  "alias": "SOS_2_SM" },
		"Element:11:5": { "x": 208,  "y": 907, "width": 40,  "height": 40,  "alias": "SOS_3_SC" },
		"Element:11:6": { "x": 1550, "y": 140, "width": 40,  "height": 40,  "alias": "SOS_3_SM" },
		"Element:12:1": { "x": 1374, "y": 901, "width": 50,  "height": 50,  "alias": "LUX_1_SC" },
		"Element:12:2": { "x": 324,  "y": 132, "width": 50,  "height": 50,  "alias": "LUX_1_SM" },
		"Element:12:3": { "x": 1310, "y": 901, "width": 50,  "height": 50,  "alias": "LUX_2_SC" },
		"Element:12:4": { "x": 388,  "y": 132, "width": 50,  "height": 50,  "alias": "LUX_2_SM" },
		"Element:12:5": { "x": 388,  "y": 901, "width": 50,  "height": 50,  "alias": "LUX_3_SC" },
		"Element:12:6": { "x": 1320, "y": 132, "width": 50,  "height": 50,  "alias": "LUX_3_SM" },
		"Element:12:7": { "x": 324,  "y": 901, "width": 50,  "height": 50,  "alias": "LUX_4_SC" },
		"Element:12:8": { "x": 1384, "y": 132, "width": 50,  "height": 50,  "alias": "LUX_4_SM" },
		"Element:2:1":  { "x": 1440, "y": 910, "width": 40,  "height": 40,  "alias": "CAM_1_SC" },
		"Element:2:2":  { "x": 459,  "y": 141, "width": 40,  "height": 40,  "alias": "CAM_1_SM" },
		"Element:2:3":  { "x": 939,  "y": 910, "width": 40,  "height": 40,  "alias": "CAM_2_SC" },
		"Element:2:4":  { "x": 900,  "y": 141, "width": 40,  "height": 40,  "alias": "CAM_2_SM" },
		"Element:2:5":  { "x": 539,  "y": 910, "width": 40,  "height": 40,  "alias": "CAM_3_SC" },
		"Element:2:6":  { "x": 1260, "y": 141, "width": 40,  "height": 40,  "alias": "CAM_3_SM" },
		"Element:2:7":  { "x": 1578, "y": 517, "width": 40,  "height": 40,  "alias": "DOMO_1_SC" },
		"Element:2:8":  { "x": 160,  "y": 517, "width": 40,  "height": 40,  "alias": "DOMO_1_SM" },
		"Element:13:1": { "x": 1685, "y": 868, "width": 40,  "height": 40,  "alias": "SEM_1_SC" },
		"Element:13:2": { "x": 64,   "y": 438, "width": 40,  "height": 40,  "alias": "SEM_1_SM" },
		"Element:13:3": { "x": 1685, "y": 598, "width": 40,  "height": 40,  "alias": "SEM_2_SC" },
		"Element:13:4": { "x": 64,   "y": 174, "width": 40,  "height": 40,  "alias": "SEM_2_SM" },
		"Element:14:1": { "x": 1670, "y": 503, "width": 60,  "height": 60,  "alias": "LUM_1_SC" },
		"Element:14:2": { "x": 77,   "y": 503, "width": 60,  "height": 60,  "alias": "LUM_2_SC" },
		"Element:15:1": { "x": 434,  "y": 730, "width": 315, "height": 20,  "alias": "FIRE_1_SC" },
		"Element:15:2": { "x": 434,  "y": 330, "width": 315, "height": 20,  "alias": "FIRE_1_SM" },
		"Element:15:3": { "x": 748,  "y": 730, "width": 315, "height": 20,  "alias": "FIRE_2_SC" },
		"Element:15:4": { "x": 748,  "y": 330, "width": 315, "height": 20,  "alias": "FIRE_2_SM" },
		"Element:15:5": { "x": 1063, "y": 730, "width": 315, "height": 20,  "alias": "FIRE_3_SC" },
		"Element:15:6": { "x": 1063, "y": 330, "width": 315, "height": 20,  "alias": "FIRE_3_SM" },
		"Element:16:1": { "x": 881,  "y": 550, "width": 40,  "height": 40,  "alias": "MEG_1_SC" },
		"Element:16:2": { "x": 881,  "y": 494, "width": 40,  "height": 40,  "alias": "MEG_1_SM" },
		"Element:17:1": { "x": 242,  "y": 140, "width": 50,  "height": 50,  "alias": "ANEM_EXT_1_SM" },
		"Element:18:1": { "x": 859,  "y": 641, "width": 50,  "height": 50,  "alias": "ANEM_INT_1_SC" },
		"Element:18:2": { "x": 1050, "y": 379, "width": 50,  "height": 50,  "alias": "ANEM_INT_1_SM" },
		"Element:19:1": { "x": 1005, "y": 902, "width": 50,  "height": 50,  "alias": "CO_1_SC" },
		"Element:19:2": { "x": 817,  "y": 132, "width": 50,  "height": 50,  "alias": "CO_1_SM" },
		"Element:20:1": { "x": 1154, "y": 902, "width": 50,  "height": 50,  "alias": "NO_1_SC" },
		"Element:20:2": { "x": 669,  "y": 132, "width": 50,  "height": 50,  "alias": "NO_1_SM" },
		"Element:21:1": { "x": 1079, "y": 902, "width": 50,  "height": 50,  "alias": "OPAC_1_SC" },
		"Element:21:2": { "x": 743,  "y": 132, "width": 50,  "height": 50,  "alias": "OPAC_1_SM" },
		"Element:23:1": { "x": 100,  "y": 711, "width": 120, "height": 120, "alias": "DETECTOR_1",
			"values": [
				{name: "average_length", label: "AL"},
				{name: "average_speed", label: "AS"},
				{name: "total_intensity", label: "TI"}
			]
		},
		"Element:26:1": { "x": 1417,  "y": 715, "width": 50,  "height": 50,  "alias": "AF_1_SM" },
		"Element:35:1": { "x": 85,  "y": 695, "width": 290,  "height": 90,  "alias": "AF_1_SC" }
	},
	{
		"id":"synopticMetro",
		"parentId":"Element:7:2",
		"ExclusiveLayers": false,
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
				"visibility": "visible"
			}
		},
		"Element:7:1": { "x": 1091, "y": 607, "width": 60, "height": 60 },
	},
	{
		"id":"synopticGlobal",
		"parentId":"Element:7:3",
		"ExclusiveLayers": false,
		"BaseLayers": {
			"BASE_LAYER": "global.svg"
		},
		"Layers": {
			"BASE_LAYER": {
				"items": [],
				"translation_key": "",
				"visibility": "visible"
			},
			"DMS": {
				"items": ["Element:10:1", "Element:10:2"],
				"translation_key": "",
				"visibility": "visible"
			},
			"FAN": {
				"items": ["Element:9:1", "Element:9:2", "Element:9:3", "Element:9:4", "Element:9:5", "Element:9:6", "Element:9:7", "Element:9:8"],
				"translation_key": "",
				"visibility": "visible"
			},
			"BAR": {
				"items": ["Element:8:1", "Element:8:2"],
				"translation_key": "",
				"visibility": "visible"
			},
			"SEM": {
				"items": ["Element:13:1", "Element:13:2"],
				"translation_key": "",
				"visibility": "visible"
			},
		},
		"Element:10:1": { "x": 1511, "y": 593,  "width": 158, "height": 40 },
		"Element:10:2": { "x": 1645, "y": 693,  "width": 158, "height": 40 },
		"Element:9:1":  { "x": 1606, "y": 720,  "width": 20,  "height": 20 },
		"Element:9:2":  { "x": 1606, "y": 690,  "width": 20,  "height": 20 },
		"Element:9:3":  { "x": 1565, "y": 720,  "width": 20,  "height": 20 },
		"Element:9:4":  { "x": 1565, "y": 690,  "width": 20,  "height": 20 },
		"Element:9:5":  { "x": 1680, "y": 620,  "width": 20,  "height": 20 },
		"Element:9:6":  { "x": 1680, "y": 590,  "width": 20,  "height": 20 },
		"Element:9:7":  { "x": 1722, "y": 620,  "width": 20,  "height": 20 },
		"Element:9:8":  { "x": 1722, "y": 590,  "width": 20,  "height": 20 },
		"Element:13:1": { "x": 1825, "y": 663,  "width": 40,  "height": 40 },
		"Element:13:2": { "x": 1462, "y": 618,  "width": 40,  "height": 40 },
		"Element:8:1":  { "x": 1820, "y": 713,  "width": 88,  "height": 48 },
		"Element:8:2":  { "x": 1411, "y": 558,  "width": 88,  "height": 48 },
		"Element:7:1":  { "x": 1505, "y": 775,  "width": 305, "height": 37 },
	}
];