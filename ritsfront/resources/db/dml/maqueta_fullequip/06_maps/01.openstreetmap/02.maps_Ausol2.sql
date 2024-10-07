INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode) VALUES 
(2, 'mapa', 'mapa', 'LBL_MENU_ITEM_MAP', NULL, -4.956077777777778, 36.46929444444445, 11, 16, 10, -6.962095, 34.525937, -2.962095, 38.525937, null, null, true);

INSERT INTO ui.map_incidents(id, incident_graphic_icon_id, incident_report_module_action_id, incident_wizard_module_action_id) VALUES
(2, 1000, 100401, 101301);

INSERT INTO ui.map_layers (id, base_layer, map_id, "name", description, "label", icon, visible) VALUES 
(2, NULL, 2, 'Layer Paneles', 'Layer Paneles', 'LBL_ELEMENT_TYPE_PANEL', NULL, true);

INSERT INTO ui.map_layer_elements (id, layer_id, element_id, graphic_icon_id, "label", longitude, latitude, horizontal_flip, rotate, tooltip, z_index_front, show_text, text_color,selectable) VALUES 
(13, 2, 'Element:71:86', 22171, 'GDR-PMV-9MA', -5.289669444444445, 36.295138888888886, false, null, true, 1, true, '#000000',true),
(14, 2, 'Element:71:87', 22171, 'GDR-PMV-10MA', -5.282908333333333, 36.29730833333333, false, null, true, 1, true, '#000000',true),
(15, 2, 'Element:71:88', 22171, 'MNV-PMV-11MA', -5.268169444444444, 36.371386111111114, false, null, true, 1, true, '#000000',true),
(16, 2, 'Element:71:89', 22171, 'MNV-PMV-13CA', -5.262094444444444, 36.383825, false, null, true, 1, true, '#000000',true),
(27, 2, 'Element:71:11', 22171, 'CAS-PMV-15CA', -5.225447222222222, 36.40949166666667, false, null, true, 1, true, '#000000',true),
(28, 2, 'Element:71:14', 22171, 'CAS-PMV-14MA', -5.2487055555555555, 36.399594444444446, false, null, true, 1, true, '#000000',true),
(29, 2, 'Element:71:39', 22171, 'COR-PMV-17CA', -5.187919444444445, 36.43279444444444, false, null, true, 1, true, '#000000',true),
(30, 2, 'Element:71:81', 22171, 'EST-PMV-19CA', -5.160819444444445, 36.433425, false, null, true, 1, true, '#000000',true),
(31, 2, 'Element:71:84', 22171, 'EST-PMV-22MA', -5.156405555555556, 36.434263888888886, false, null, true, 1, true, '#000000',true),
(32, 2, 'Element:71:90', 19371, 'EST-PMV-18CA', -5.173377777777778, 36.43239444444444, false, null, true, 1, true, '#000000',true),
(33, 2, 'Element:71:85', 22171, 'EST-PMV-26MA', -5.1849, 36.43289166666666, false, null, true, 1, true, '#000000',true),
(34, 2, 'Element:71:80', 22171, 'STM2-PMV-16MA', -5.218311111111111, 36.42060277777777, false, null, true, 1, true, '#000000',true);

INSERT INTO ui.map_layer_element_zooms (map_layer_element_id, zoom_lower, zoom_upper, graphic_icon_id, longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color) VALUES
(13,  10, 13, 22171,  -5.289669444444445, 36.295138888888886,  false, null, true, false, '#00000'),
(14,  10, 13, 22171,  -5.282908333333333, 36.29730833333333,  false, null, true, false, '#00000'),
(15,  10, 13, 22171,  -5.268169444444444, 36.371386111111114,  false, null, true, false, '#00000'),
(16,  10, 13, 22171,  -5.262094444444444, 36.383825,  false, null, true, false, '#00000'),
(27,  10, 13, 22171,  -5.225447222222222, 36.40949166666667,  false, null, true, false, '#00000'),
(28,  10, 13, 22171,  -5.2487055555555555, 36.399594444444446, false, null, true, false, '#00000'),
(29,  10, 13, 22171,  -5.187919444444445, 36.43279444444444, false, null, true, false, '#00000'),
(30,  10, 13, 22171,  -5.160819444444445, 36.433425, false, null, true, false, '#00000'),
(31,  10, 13, 22171,  -5.156405555555556, 36.434263888888886, false, null, true, false, '#00000'),
(32,  10, 13, 19371,  -5.173377777777778, 36.43239444444444, false, null, true, false, '#00000'),
(33,  10, 13, 22171,  -5.1849, 36.43289166666666, false, null, true, false, '#00000'),
(34,  10, 13, 22171,  -5.218311111111111, 36.42060277777777, false, null, true, false, '#00000'),


(13,  13.5, 16, 22271,  -5.289669444444445, 36.296138888888886,  false, null, true, false, '#00000'),
(14,  13.5, 16, 22271,  -5.282908333333333, 36.29730833333333,  false, null, true, false, '#00000'),
(15,  13.5, 16, 22271,  -5.268169444444444, 36.371386111111114,  false, null, true, false, '#00000'),
(16,  13.5, 16, 22271,  -5.262094444444444, 36.383825,  false, null, true, false, '#00000'),
(27,  13.5, 16, 22271,  -5.225447222222222, 36.40949166666667, false, null, true, false, '#00000'),
(28,  13.5, 16, 22271,  -5.2487055555555555, 36.399594444444446, false, null, true, false, '#00000'),
(29,  13.5, 16, 22271,  -5.187919444444445, 36.43279444444444, false, null, true, false, '#00000'),
(30,  13.5, 16, 22271,  -5.160819444444445, 36.433425, false, null, true, false, '#00000'),
(31,  13.5, 16, 22271,  -5.156405555555556, 36.434263888888886, false, null, true, false, '#00000'),
(32,  13.5, 16, 19471,  -5.173377777777778, 36.43239444444444,  false, null, true, false, '#00000'),
(33,  13.5, 16, 22271,   -5.1819, 36.43289166666666,  false, null, true, false, '#00000'),
(34,  13.5, 16, 22271,   -5.218311111111111, 36.42060277777777,  false, null, true, false, '#00000');


INSERT INTO ui.map_commands
(id, map_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES
(4, 2, 1, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(5, 2, 2, 'LBL_CMD_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(6, 2, 3, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(8, 2, 4, 'LBL_CMD_MAINTENANCE', 'mdi mdi-wrench', false, 104601, NULL, 2, true, NULL, false, true);


INSERT INTO ui.map_zones (id, map_id, name, description, label, icon, longitude, latitude, zoom) VALUES
(6,	2, 'CAS', 'Casares', 'LBL_TREE_CASARES', NULL,	-5.237180555555556, 36.4026388888888,	15),
(7,	2, 'COR', 'Corominas', 'LBL_TREE_COROMINAS', NULL, -5.198508333333334, 36.4307611111111,	15),
(8,	2, 'STMII', 'Sta. María 2 ', 'LBL_TREE_STA_MARIA_2', NULL,	-5.209619444444445, 36.42483333333333,	15),
(9,	2, 'EST', 'Estepona', 'LBL_TREE_ESTEPONA', NULL, -5.173011111111111, 36.433277777777775,	15);


