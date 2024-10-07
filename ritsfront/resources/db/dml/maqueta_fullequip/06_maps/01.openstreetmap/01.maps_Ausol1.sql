INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode) VALUES 
(1, 'mapa', 'mapa', 'LBL_MENU_ITEM_MAP', NULL, -4.956077777777778, 36.46929444444445, 11, 16, 10, -6.962095, 34.525937, -2.962095, 38.525937, null, null, true);

INSERT INTO ui.map_incidents(id, incident_graphic_icon_id, incident_report_module_action_id, incident_wizard_module_action_id) VALUES
(1, 1000, 100401, 101301);

INSERT INTO ui.map_layers (id, base_layer, map_id, "name", description, "label", icon, visible) VALUES 
(1, NULL, 1, 'Layer Paneles', 'Layer Paneles', 'LBL_ELEMENT_TYPE_PANEL', NULL, true);

INSERT INTO ui.map_layer_elements (id, layer_id, element_id, graphic_icon_id, "label", longitude, latitude, horizontal_flip, rotate, tooltip, z_index_front, show_text, text_color,selectable) VALUES 
(11, 1, 'Element:71:1', 19371, 'RV-PMV-5MA', -4.9716611111111115, 36.52565555555555, false, null, true, 1, true, '#000000',true),
(12, 1, 'Element:71:2', 19371, 'RV-PMV-6CA', -4.950216666666667, 36.52636944444444, false, null, true, 1, true, '#000000',true),
(17, 1, 'Element:71:8', 19371, 'LQ-PMV-3MA', -5.005908333333333, 36.499425, false, null, true, 1, true, '#000000',true),
(18, 1, 'Element:71:7', 19371, 'LQ-PMV-4CA', -4.9856, 36.51530555555556, false, null, true, 1, true, '#000000',true),
(19, 1, 'Element:71:3', 19371, 'MM-PMV-1MA', -5.073833333333333, 36.48594166666667, false, null, true, 1, true, '#000000',true),
(20, 1, 'Element:71:4', 19371, 'MM-PMV-2CA', -5.048258333333333, 36.48600833333334, false, null, true, 1, true, '#000000',true),
(21, 1, 'Element:71:82', 22171, 'NG-PMV-21CA', -4.924852777777778, 36.513172222222224, false, null, true, 1, true, '#000000',true),
(22, 1, 'Element:71:83', 22171, 'NG-PMV-25MA', -4.94933611111111, 36.49565555555556, false, null, true, 1, true, '#000000',true),
(23, 1, 'Element:71:9', 19371, 'CH-PMV-7MA', -4.740575, 36.50481388888889, false, null, true, 1, true, '#000000',true),
(24, 1, 'Element:71:10', 22171, 'CH-PMV-8CA', -4.709702777777778, 36.50683333333333, false, null, true, 1, true, '#000000',true),
(25, 1, 'Element:71:5', 22171, 'STMI-PMV-24CA', -4.757883333333333, 36.50729444444445, false, null, true, 1, true, '#000000',true),
(26, 1, 'Element:71:6', 22171, 'STMI-PMV-23MA', -4.779144444444444, 36.512477777777775, false, null, true, 1, true, '#000000',true);

INSERT INTO ui.map_layer_element_zooms (map_layer_element_id, zoom_lower, zoom_upper, graphic_icon_id, longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color) VALUES
(11,  10, 13, 19371,  -4.9716611111111115, 36.52565555555555,  false, null, true, false, '#00000'),
(12,  10, 13, 19371,  -4.950216666666667, 36.52636944444444,  false, null, true, false, '#00000'),
(17,  10, 13, 19371,  -5.005908333333333, 36.499425,  false, null, true, false, '#00000'),
(18,  10, 13, 19371,  -4.9856, 36.51530555555556, false, null, true, false, '#00000'),
(19,  10, 13, 19371,  -5.073833333333333, 36.48594166666667,  false, null, true, false, '#00000'),
(20,  10, 13, 19371,  -5.048258333333333, 36.48600833333334, false, null, true, false, '#00000'),
(21,  10, 13, 22171,  -4.924852777777778, 36.513172222222224,  false, null, true, false, '#00000'),
(22,  10, 13, 22171,  -4.94933611111111, 36.49565555555556, false, null, true, false, '#00000'),
(23,  10, 13, 19371,  -4.740575, 36.50481388888889,  false, null, true, false, '#00000'),
(24,  10, 13, 22171,  -4.709702777777778, 36.50683333333333, false, null, true, false, '#00000'),
(25,  10, 13, 22171,  -4.757883333333333, 36.50729444444445, false, null, true, false, '#00000'),
(26,  10, 13, 22171,  -4.779144444444444, 36.512477777777775, false, null, true, false, '#00000'),


(11,  13.5, 16, 19471,  -4.9716611111111115, 36.52465555555555,  false, null, true, false, '#00000'),
(12,  13.5, 16, 19471,  -4.950216666666667, 36.52436944444444,  false, null, true, false, '#00000'),
(17,  13.5, 16, 19471,  -5.005908333333333, 36.497425,  false, null, true, false, '#00000'),
(18,  13.5, 16, 19471,  -4.9856, 36.51330555555556, false, null, true, false, '#00000'),
(19,  13.5, 16, 19471,  -5.073833333333333, 36.48394166666667,  false, null, true, false, '#00000'),
(20,  13.5, 16, 19471,  -5.048258333333333, 36.48400833333334, false, null, true, false, '#00000'),
(21,  13.5, 16, 22271,  -4.924852777777778, 36.512172222222224,  false, null, true, false, '#00000'),
(22,  13.5, 16, 22271,  -4.94933611111111, 36.49365555555556, false, null, true, false, '#00000'),
(23,  13.5, 16, 19471,  -4.740575, 36.50281388888889,  false, null, true, false, '#00000'),
(24,  13.5, 16, 22271,  -4.709702777777778, 36.50483333333333, false, null, true, false, '#00000'),
(25,  13.5, 16, 22271,  -4.757883333333333, 36.50529444444445,  false, null, true, false, '#00000'),
(26,  13.5, 16, 22271,  -4.779144444444444, 36.510477777777775, false, null, true, false, '#00000');


INSERT INTO ui.map_commands
(id, map_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES
(1, 1, 1, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(2, 1, 2, 'LBL_CMD_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(3, 1, 3, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(7, 1, 4, 'LBL_CMD_MAINTENANCE', 'mdi mdi-wrench', false, 104601, NULL, 2, true, NULL, false, true);


INSERT INTO ui.map_zones (id, map_id, name, description, label, icon, longitude, latitude, zoom) VALUES
(1,	1, 'RV ', 'Rio Verde', 'LBL_TREE_RIO_VERDE', NULL, -4.962095, 36.525937, 15),
(2, 1, 'MM', 'Montemayor', 'LBL_TREE_MONTEMAYOR', NULL, -5.060833333333333, 36.484722222222224, 15),
(3,	1, 'STM', 'Sta. María I', 'LBL_TREE_STA_MARIA_I', NULL, -4.768797222222222, 36.510441666666665,	15),
(4,	1, 'LQ', 'La Quinta', 'LBL_TREE_LA_QUINTA', NULL,	-4.99525, 36.504694444444446,	15),
(5,	1, 'CH', 'Calahonda', 'LBL_TREE_CALAHONDA', NULL,	-4.727638888888889, 36.50606944444444,	15),
(10,1, 'NG', 'Nagüeres', 'LBL_TREE_NAGUELES', NULL,	-4.938727777777778, 36.51513888888889,	15);


