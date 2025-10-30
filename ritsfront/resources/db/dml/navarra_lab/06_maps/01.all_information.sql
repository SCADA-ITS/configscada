INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode, custom_provider) VALUES 
(1, 'Mapa completo', 'Mapa completo', 'Mapa completo', NULL, -1.65, 42.7, 8, 20, 8, -4, 44, 1, 41.1, null, 7, true,
	'{ 
		"map": {"enabled": false, "type": "WMS", "serverType": "geoserver", "url": "https://descontroltrafico.admon-cfnavarra.esgeoserver/wms", "name": "osm"},
		"router": {"enabled": true, "type": "OpenRouteService", "url": "https://descontroltrafico.admon-cfnavarra.es/ors"},
        "smartRoadInfo": {"enabled": true, "url": "https://descontroltrafico.admon-cfnavarra.es/api"}
	}'),
(1000, 'MAPA PARA COMPONENTE MAPEDITOR', 'MAPA PARA COMPONENTE MAPEDITOR', 'LBL_MENU_ITEM_MAP', NULL, -1.65, 42.7, 8, 20, 8, -4, 44, 1, 41.1, null, 8, true,
	'{ 
		"map": {"enabled": false, "type": "WMS", "serverType": "geoserver", "url": "https://descontroltrafico.admon-cfnavarra.esgeoserver/wms", "name": "osm"},
		"router": {"enabled": true, "type": "OpenRouteService", "url": "https://descontroltrafico.admon-cfnavarra.es/ors"},
        "smartRoadInfo": {"enabled": true, "url": "https://descontroltrafico.admon-cfnavarra.es/api"}
	}');
	
INSERT INTO ui.map_incidents(id, incident_graphic_icon_id, incident_report_module_action_id, incident_wizard_module_action_id) VALUES
(1, 1000, 100401, 101303);

INSERT INTO ui.map_layers (id, base_layer, map_id, "name", description, "label", icon, visible) VALUES 
(2, NULL, 1, 'CAMERA', 'CAMERA', 'LBL_ELEMENT_TYPE_CAMERA', NULL, true), 
(71, NULL, 1, 'PMV', 'PMV', 'LBL_ELEMENT_TYPE_PANEL', NULL, true), 
(99, NULL, 1, 'RADAR', 'RADAR', 'LBL_ELEMENT_TYPE_TRAFFIC_RADAR', NULL, true),
(101, '{"enabled":true,"type":"FILE","fileType":"KML","fileName":"REFERE_Lin_Navarra.shp.kml","name":"Frontera Navarra"}', 1, 'Frontera Navarra', 'Frontera Navarra', 'Frontera Navarra', 'mdi mdi-google-maps', true);


--
-- routes_config format:
--
--    {
--      "routeClosedColor": (Opcional) Color de ruta con cortes o cortada
--      "routeOpenColor": (Opcional) Color de ruta abierta
--      "routeClosureColor": (Opcional) Color de representación de cortes
--      "alternativeRoutes": (Opcional) Habilita el cálculo de rutas adicionales a partir de un corte
--    }
--
INSERT INTO ui.map_routes (id, default_module_action_id, routes_config) VALUES
(1, null,
  '{
      "routeClosedColor": "#FF4500",
      "routeOpenColor": "#36B5DD",
      "avoidPolygonColor": "#8B0000",
      "alternativeRoutes": true,
      "alternativeRouteColor": "#46B482"
  }'),
(2, null,
  '{
		"routeClosedColor": "#FF4500",
		"routeOpenColor": "#36B5DD",
		"avoidPolygonColor": "#8B0000",
		"alternativeRoutes": true,
		"alternativeRouteColor": "#46B482"
}');

INSERT INTO ui.map_layer_elements (id, layer_id, element_id, graphic_icon_id, "label", latitude, longitude, horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(0010002, 2,'Element:2:1', 2,'Alsasua', 42.88534118, -2.177320663, false, null, true, false, null, true ),
(0020002, 2,'Element:2:2', 2,'Etxegarate', 42.92928501, -2.227834578, false, null, true, false, null, true ),
(0030002, 2,'Element:2:3', 2,'Arbizu', 42.90640052, -2.046534012, false, null, true, false, null, true ),
(0040002, 2,'Element:2:4', 2,'Zizur Mayor', 42.79340855, -1.682892472, false, null, true, false, null, true ),
(0050002, 2,'Element:2:5', 2,'Irurtzun', 42.91793996, -1.838383512, false, null, true, false, null, true ),
(0060002, 2,'Element:2:6', 2,'Leitza', 43.10125626, -1.944168447,  false, null, true, false, null, true ),
(0070002, 2,'Element:2:7', 2,'Pagozelai', 43.05934066, -1.955722233,  false, null, true, false, null, true ),
(0080002, 2,'Element:2:8', 2,'Lekunberri', 43.01722856, -1.904364804,  false, null, true, false, null, true ),
(0090002, 2,'Element:2:9', 2,'Areta', 42.81420623, -1.602486659,  false, null, true, false, null, true ),
(0100002, 2,'Element:2:10', 2,'Berriozar', 42.83264561, -1.6672769482,  false, null, true, false, null, true ),
(0110002, 2,'Element:2:11', 2,'Talluntxe', 42.7755504, -1.631563348,  false, null, true, false, null, true ),
(0120002, 2,'Element:2:12', 2,'Cordovilla', 42.78413274, -1.636612405,  false, null, true, false, null, true ),
(0130002, 2,'Element:2:13', 2,'Belagua', 42.95755607, -0.800989197,  false, null, true, false, null, true ),
(0140002, 2,'Element:2:14', 2,'Tudela', 42.04560299, -1.618388954,  false, null, true, false, null, true ),
(0150002, 2,'Element:2:15', 2,'Arre', 42.84479838, -1.623058612,  false, null, true, false, null, true ),
(0160002, 2,'Element:2:16', 2,'Ezkaba túnel Francia', 42.836679, -1.6182419,  false, null, true, false, null, true ),
(0170002, 2,'Element:2:17', 2,'Túnel de Belate', 43.05401035, -1.614583662,  false, null, true, false, null, true ),
(0180002, 2,'Element:2:18', 2,'Túnel de Yesa', 42.62254724, -1.195976004,  false, null, true, false, null, true ),
(0190002, 2,'Element:2:19', 2,'Túnel del Perdón', 42.7330244, -1.754499646,  false, null, true, false, null, true ),
(0200002, 2,'Element:2:20', 2,'Endarlatza 1', 43.2792225, -1.711128641,  false, null, true, false, null, true ),
(0210002, 2,'Element:2:21', 2,'Almandoz Norte', 43.0800550, -1.6087037,  false, null, true, false, null, true ),
(0220002, 2,'Element:2:22', 2,'N-121-A PK35', 43.087697, -1.606209,  false, null, true, false, null, true ),
(0230002, 2,'Element:2:23', 2,'Bera Tunel Sur 1', 43.2739305, -1.6908707,  false, null, true, false, null, true ),
(0240002, 2,'Element:2:24', 2,'Bera Tunel Sur 2', 43.2739305, -1.6908707,  false, null, true, false, null, true ),
(0250002, 2,'Element:2:25', 2,'Bera Tunel Norte 1', 43.27574, -1.695665,  false, null, true, false, null, true ),
(0260002, 2,'Element:2:26', 2,'Bera Tunel Norte 2', 43.2757475, -1.6956659,  false, null, true, false, null, true ),
--(0270002, 2,'Element:2:27', 2,'Endarlatza 2', 43.2792225, -1.711128641,  false, null, true, false, null, true ),
(0280002, 2,'Element:2:28', 2,'Talluntxe LPR', 42.7755542, -1.6315534,  false, null, true, false, null, true ),
(0290002, 2,'Element:2:29', 2,'TV1 PA-30', 42.8366740, -1.6540958,  false, null, true, false, null, true ),
(0300002, 2,'Element:2:30', 2,'TV2 PA-30', 42.8364513, -1.6541466,  false, null, true, false, null, true ),
(0310002, 2,'Element:2:31', 2,'TV3.1 PA-30', 42.8365174, -1.6530294,  false, null, true, false, null, true ),
(0320002, 2,'Element:2:32', 2,'TV3.2 PA-30', 42.8364513, -1.6541466,  false, null, true, false, null, true ),
(0330002, 2,'Element:2:33', 2,'TV4.1 PA-30', 42.8358428, -1.6460624,  false, null, true, false, null, true ),
(0340002, 2,'Element:2:34', 2,'TV4.2 PA-30', 42.8357197, -1.6460416,  false, null, true, false, null, true ),
(0350002, 2,'Element:2:35', 2,'TV5.1 PA-30', 42.8362652, -1.6450612,  false, null, true, false, null, true ),
(0360002, 2,'Element:2:36', 2,'TV5.2 PA-30', 42.8359269, -1.6449530,  false, null, true, false, null, true ),
(0370002, 2,'Element:2:37', 2,'TV6.1 PA-30', 42.8363625, -1.6366738,  false, null, true, false, null, true ),
(0380002, 2,'Element:2:38', 2,'TV6.2 PA-30', 42.8364505, -1.6367068,  false, null, true, false, null, true ),
(0390002, 2,'Element:2:39', 2,'TV7.1 PA-30', 42.8365150, -1.6343868,  false, null, true, false, null, true ),
(0400002, 2,'Element:2:40', 2,'TV7.2 PA-30', 42.8364206, -1.6343904,  false, null, true, false, null, true ),
(0410002, 2,'Element:2:41', 2,'TV8.1 PA-30', 42.8364413, -1.6244711,  false, null, true, false, null, true ),
(0420002, 2,'Element:2:42', 2,'TV8.2 PA-30', 42.8350084, -1.6196880,  false, null, true, false, null, true ),
(0430002, 2,'Element:2:43', 2,'TV9.1 PA-30', 42.8358745, -1.6213852,  false, null, true, false, null, true ),
(0440002, 2,'Element:2:44', 2,'TV9.2 PA-30', 42.8372739, -1.6185923,  false, null, true, false, null, true ),
(0450002, 2,'Element:2:45', 2,'TV00 PK 6+930', 42.7586010, -1.6283790,  false, null, true, false, null, true ),
(0460002, 2,'Element:2:46', 2,'TV09B PK 40+380', 42.6167780, -1.2914440,  false, null, true, false, null, true ),

(0010071, 71, 'Element:71:1', 71, 'PMV-VIRTUAL-1', 43.39616189919906, -2.0825991357475653, false, null, true, false, null, true ),
(0020071, 71, 'Element:71:2', 71, 'PMV-VIRTUAL-2', 43.39616189919906, -2.5795991357475653, false, null, true, false, null, true ),

(0010099, 99,'Element:99:1', 99,'RADF-127+670D',43.01762766,-1.90737257, false, null, true, false, null, true ),
(0020099, 99,'Element:99:2', 99,'RADF-91+358D', 42.07934146,-1.79528762, false, null, true, false, null, true ),
(0030099, 99,'Element:99:3', 99,'RADF-32+560C', 43.0658902, -1.6177618, false, null, true, false, null, true ),
(0040099, 99,'Element:99:4', 99,'RADF-401+561C',42.9161053, -2.1916473, false, null, true, false, null, true ),
(0050099, 99,'Element:99:5', 99,'RADF-25+900D', 43.0113198, -1.6367476, false, null, true, false, null, true ),
(0060099, 99,'Element:99:6', 99,'RADF-60+230C', 43.2475295, -1.6712459, false, null, true, false, null, true );

INSERT INTO ui.map_layer_element_zooms (map_layer_element_id, zoom_lower, zoom_upper, graphic_icon_id, latitude, longitude, horizontal_flip, rotate, tooltip, show_text, text_color) VALUES
(0230002,  8, 20, 2,  43.2689305, -1.7008707,  false, null, true, false, '#00000'),
(0240002,  8, 20, 2,  43.2789305, -1.6808707,  false, null, true, false, '#00000'),
(0250002,  8, 20, 2,  43.2707475, -1.7056659,  false, null, true, false, '#00000'),
(0260002,  8, 20, 2,  43.2807475, -1.6856659,  false, null, true, false, '#00000');

INSERT INTO ui.map_commands
(id, map_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required,  context) VALUES
(0, 1, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, true, true),
(1, 1, 1, 'LBL_CMD_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, false, true),
(2, 1, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, true, true);

INSERT INTO ui.map_ext_entities (id, default_graphic_icon_id, default_module_action_id, ext_entity_types_config) VALUES
(1, 010001, NULL, 
	'[
		{ 
			"extEntityTypeId": "ExtEntityType:1",
			"visible": true,
			"enabled": true,
			"animate": false
		},
		{ 
			"extEntityTypeId": "ExtEntityType:4",
			"visible": true,
			"enabled": true,
			"animate": false
		},
		{ 
			"extEntityTypeId": "ExtEntityType:7",
			"visible": true,
			"enabled": true,
			"animate": false
		},
		{ 
			"extEntityTypeId": "ExtEntityType:8",
			"visible": true,
			"enabled": true,
			"animate": false
		}
	]'
);

