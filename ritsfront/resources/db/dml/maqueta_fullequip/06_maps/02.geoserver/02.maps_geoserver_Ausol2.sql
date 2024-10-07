INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode, custom_provider) VALUES 
(4, 'mapa', 'mapa', 'LBL_MENU_ITEM_MAP', NULL, -4.956077777777778, 36.46929444444445, 11, 16, 10, -6.962095, 34.525937, -2.962095, 38.525937, null, null, true,
'{ 
	"map": {"enabled": true, "type": "WMS", "serverType": "geoserver", "url": "http://192.168.88.165:8700/geoserver/wms", "name": "osm"},
	"router": {"enabled": true, "type": "OpenRouteService", "url": "http://192.168.88.165:8701/ors"},
    "smart-road-info": {"url": "http://192.168.88.165:8702/api"}
}');

INSERT INTO ui.map_incidents(id, incident_graphic_icon_id, incident_report_module_action_id, incident_wizard_module_action_id) VALUES
(4, 1000, 100401, 101301);

INSERT INTO ui.map_layers (id, base_layer, map_id, "name", description, "label", icon, visible) VALUES 
(4, NULL, 4, 'Layer Paneles', 'Layer Paneles', 'LBL_ELEMENT_TYPE_PANEL', NULL, true);

INSERT INTO ui.map_layer_elements (id, layer_id, element_id, graphic_icon_id, "label", longitude, latitude, horizontal_flip, rotate, tooltip, z_index_front, show_text, text_color,selectable) VALUES 
(1013, 4, 'Element:71:86', 22171, 'GDR-PMV-9MA', -5.289669444444445, 36.295138888888886, false, null, true, 1, true, '#000000',true),
(1014, 4, 'Element:71:87', 22171, 'GDR-PMV-10MA', -5.282908333333333, 36.29730833333333, false, null, true, 1, true, '#000000',true),
(1015, 4, 'Element:71:88', 22171, 'MNV-PMV-11MA', -5.268169444444444, 36.371386111111114, false, null, true, 1, true, '#000000',true),
(1016, 4, 'Element:71:89', 22171, 'MNV-PMV-13CA', -5.262094444444444, 36.383825, false, null, true, 1, true, '#000000',true),
(1027, 4, 'Element:71:11', 22171, 'CAS-PMV-15CA', -5.225447222222222, 36.40949166666667, false, null, true, 1, true, '#000000',true),
(1028, 4, 'Element:71:14', 22171, 'CAS-PMV-14MA', -5.2487055555555555, 36.399594444444446, false, null, true, 1, true, '#000000',true),
(1029, 4, 'Element:71:39', 22171, 'COR-PMV-17CA', -5.187919444444445, 36.43279444444444, false, null, true, 1, true, '#000000',true),
(1030, 4, 'Element:71:81', 22171, 'EST-PMV-19CA', -5.160819444444445, 36.433425, false, null, true, 1, true, '#000000',true),
(1031, 4, 'Element:71:84', 22171, 'EST-PMV-22MA', -5.156405555555556, 36.434263888888886, false, null, true, 1, true, '#000000',true),
(1032, 4, 'Element:71:90', 19371, 'EST-PMV-18CA', -5.173377777777778, 36.43239444444444, false, null, true, 1, true, '#000000',true),
(1033, 4, 'Element:71:85', 22171, 'EST-PMV-26MA', -5.1849, 36.43289166666666, false, null, true, 1, true, '#000000',true),
(1034, 4, 'Element:71:80', 22171, 'STM2-PMV-16MA', -5.218311111111111, 36.42060277777777, false, null, true, 1, true, '#000000',true);

INSERT INTO ui.map_layer_element_zooms (map_layer_element_id, zoom_lower, zoom_upper, graphic_icon_id, longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color) VALUES
(1013,  10, 13, 22171,  -5.289669444444445, 36.295138888888886,  false, null, true, false, '#00000'),
(1014,  10, 13, 22171,  -5.282908333333333, 36.29730833333333,  false, null, true, false, '#00000'),
(1015,  10, 13, 22171,  -5.268169444444444, 36.371386111111114,  false, null, true, false, '#00000'),
(1016,  10, 13, 22171,  -5.262094444444444, 36.383825,  false, null, true, false, '#00000'),
(1027,  10, 13, 22171,  -5.225447222222222, 36.40949166666667,  false, null, true, false, '#00000'),
(1028,  10, 13, 22171,  -5.2487055555555555, 36.399594444444446, false, null, true, false, '#00000'),
(1029,  10, 13, 22171,  -5.187919444444445, 36.43279444444444, false, null, true, false, '#00000'),
(1030,  10, 13, 22171,  -5.160819444444445, 36.433425, false, null, true, false, '#00000'),
(1031,  10, 13, 22171,  -5.156405555555556, 36.434263888888886, false, null, true, false, '#00000'),
(1032,  10, 13, 19371,  -5.173377777777778, 36.43239444444444, false, null, true, false, '#00000'),
(1033,  10, 13, 22171,  -5.1849, 36.43289166666666, false, null, true, false, '#00000'),
(1034,  10, 13, 22171,  -5.218311111111111, 36.42060277777777, false, null, true, false, '#00000'),


(1013,  13.5, 16, 22271,  -5.289669444444445, 36.296138888888886,  false, null, true, false, '#00000'),
(1014,  13.5, 16, 22271,  -5.282908333333333, 36.29730833333333,  false, null, true, false, '#00000'),
(1015,  13.5, 16, 22271,  -5.268169444444444, 36.371386111111114,  false, null, true, false, '#00000'),
(1016,  13.5, 16, 22271,  -5.262094444444444, 36.383825,  false, null, true, false, '#00000'),
(1027,  13.5, 16, 22271,  -5.225447222222222, 36.40949166666667, false, null, true, false, '#00000'),
(1028,  13.5, 16, 22271,  -5.2487055555555555, 36.399594444444446, false, null, true, false, '#00000'),
(1029,  13.5, 16, 22271,  -5.187919444444445, 36.43279444444444, false, null, true, false, '#00000'),
(1030,  13.5, 16, 22271,  -5.160819444444445, 36.433425, false, null, true, false, '#00000'),
(1031,  13.5, 16, 22271,  -5.156405555555556, 36.434263888888886, false, null, true, false, '#00000'),
(1032,  13.5, 16, 19471,  -5.173377777777778, 36.43239444444444,  false, null, true, false, '#00000'),
(1033,  13.5, 16, 22271,   -5.1819, 36.43289166666666,  false, null, true, false, '#00000'),
(1034,  13.5, 16, 22271,   -5.218311111111111, 36.42060277777777,  false, null, true, false, '#00000');


INSERT INTO ui.map_commands
(id, map_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES
(104, 4, 1, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(105, 4, 2, 'LBL_CMD_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(106, 4, 3, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(108, 4, 4, 'LBL_CMD_MAINTENANCE', 'mdi mdi-wrench', false, 104601, NULL, 2, true, NULL, false, true);


INSERT INTO ui.map_zones (id, map_id, name, description, label, icon, longitude, latitude, zoom) VALUES
(106,	4, 'CAS', 'Casares', 'LBL_TREE_CASARES', NULL,	-5.237180555555556, 36.4026388888888,	15),
(107,	4, 'COR', 'Corominas', 'LBL_TREE_COROMINAS', NULL, -5.198508333333334, 36.4307611111111,	15),
(108,	4, 'STMII', 'Sta. María 2 ', 'LBL_TREE_STA_MARIA_2', NULL,	-5.209619444444445, 36.42483333333333,	15),
(109,	4, 'EST', 'Estepona', 'LBL_TREE_ESTEPONA', NULL, -5.173011111111111, 36.433277777777775,	15);


