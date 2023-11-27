INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode) VALUES 
(1, 'mapa', 'mapa', 'LBL_MENU_ITEM_MAP', NULL, -4.956077777777778, 36.46929444444445, 11, 16, 10, -6.962095, 34.525937, -2.962095, 38.525937, null, null, true);

INSERT INTO ui.graphic_icons (id, "name", description, alarms, measures, js_draw_file, width, height, svg_pattern_var, svg_file, js_custom_type_render, keep_aspect_ratio) VALUES
(1000, 'INCIDENT_REPORT', 'INCIDENT_REPORT', false, true, 'component/graphicIcon/graphicIconCustom.js', 70, 120, null, 'img/graphic_icon/incident_report.svg', 'component/graphicIcon/custom/incidentReport.js', null);

INSERT INTO ui.map_incidents(id, incident_graphic_icon_id, incident_report_module_action_id, incident_wizard_module_action_id) VALUES
(1, 1000, 100401, 101301);

INSERT INTO ui.map_layers (id, base_layer, map_id, "name", description, "label", icon, visible) VALUES 
(2, NULL, 1, 'Layer Paneles', 'Layer Paneles', 'LBL_VMS', NULL, true);
 

INSERT INTO ui.graphic_icons (id, "name", description, alarms, measures, js_draw_file, width, height, svg_pattern_var, svg_file, js_custom_type_render) VALUES
(19371, 'PANEL GR_TXT', 'PANEL GR_TXT', false, true, 'component/graphicIcon/graphicIconCustom.js', 120, 40, null, 'img/graphic_icon/71_vms_dgt_gr_txt.svg', 'component/graphicIcon/custom/vms.js'),
(19471, 'PANEL GR_TXT', 'PANEL GR_TXT', false, true, 'component/graphicIcon/graphicIconCustom.js', 200, 80, null, 'img/graphic_icon/71_vms_dgt_gr_txt.svg', 'component/graphicIcon/custom/vms.js'),
(22171, 'PMV', 'PMV', false, true, 'component/graphicIcon/graphicIconCustom.js', 120, 40, null, 'img/graphic_icon/71_vms_dgt.svg', 'component/graphicIcon/custom/vms.js'),
(22271, 'PMV', 'PMV', false, true, 'component/graphicIcon/graphicIconCustom.js', 240, 120, null, 'img/graphic_icon/71_vms_dgt.svg', 'component/graphicIcon/custom/vms.js'),
(0701, 'TUNEL', 'TUNEL', false, false, 'component/graphicIcon/graphicIconDefault.js', 35, 35, null, 'img/graphic_icon/07_zone.svg', null);



INSERT INTO ui.map_layer_elements (id, layer_id, element_id, graphic_icon_id, "label", longitude, latitude, horizontal_flip, rotate, tooltip, z_index_front, show_text, text_color,selectable) VALUES 
(11, 2, 'Element:71:1', 19371, 'RV-PMV-5MA', -4.9716611111111115, 36.52565555555555, false, null, true, 1, true, '#000000',true),
(12, 2, 'Element:71:2', 19371, 'RV-PMV-6CA', -4.950216666666667, 36.52636944444444, false, null, true, 1, true, '#000000',true),
(13, 2, 'Element:71:86', 22171, 'GDR-PMV-9MA', -5.289669444444445, 36.295138888888886, false, null, true, 1, true, '#000000',true),
(14, 2, 'Element:71:87', 22171, 'GDR-PMV-10MA', -5.282908333333333, 36.29730833333333, false, null, true, 1, true, '#000000',true),
(15, 2, 'Element:71:88', 22171, 'MNV-PMV-11MA', -5.268169444444444, 36.371386111111114, false, null, true, 1, true, '#000000',true),
(16, 2, 'Element:71:89', 22171, 'MNV-PMV-13CA', -5.262094444444444, 36.383825, false, null, true, 1, true, '#000000',true),
(17, 2, 'Element:71:8', 19371, 'LQ-PMV-3MA', -5.005908333333333, 36.499425, false, null, true, 1, true, '#000000',true),
(18, 2, 'Element:71:7', 19371, 'LQ-PMV-4CA', -4.9856, 36.51530555555556, false, null, true, 1, true, '#000000',true),
(19, 2, 'Element:71:3', 19371, 'MM-PMV-1MA', -5.073833333333333, 36.48594166666667, false, null, true, 1, true, '#000000',true),
(20, 2, 'Element:71:4', 19371, 'MM-PMV-2CA', -5.048258333333333, 36.48600833333334, false, null, true, 1, true, '#000000',true),
(21, 2, 'Element:71:82', 22171, 'NG-PMV-21CA', -4.924852777777778, 36.513172222222224, false, null, true, 1, true, '#000000',true),
(22, 2, 'Element:71:83', 22171, 'NG-PMV-25MA', -4.94933611111111, 36.49565555555556, false, null, true, 1, true, '#000000',true),
(23, 2, 'Element:71:9', 19371, 'CH-PMV-7MA', -4.740575, 36.50481388888889, false, null, true, 1, true, '#000000',true),
(24, 2, 'Element:71:10', 22171, 'CH-PMV-8CA', -4.709702777777778, 36.50683333333333, false, null, true, 1, true, '#000000',true),
(25, 2, 'Element:71:5', 22171, 'STMI-PMV-24CA', -4.757883333333333, 36.50729444444445, false, null, true, 1, true, '#000000',true),
(26, 2, 'Element:71:6', 22171, 'STMI-PMV-23MA', -4.779144444444444, 36.512477777777775, false, null, true, 1, true, '#000000',true),
(27, 2, 'Element:71:11', 22171, 'CAS-PMV-15CA', -5.225447222222222, 36.40949166666667, false, null, true, 1, true, '#000000',true),
(28, 2, 'Element:71:14', 22171, 'CAS-PMV-14MA', -5.2487055555555555, 36.399594444444446, false, null, true, 1, true, '#000000',true),
(29, 2, 'Element:71:39', 22171, 'COR-PMV-17CA', -5.187919444444445, 36.43279444444444, false, null, true, 1, true, '#000000',true),
(30, 2, 'Element:71:81', 22171, 'EST-PMV-19CA', -5.160819444444445, 36.433425, false, null, true, 1, true, '#000000',true),
(31, 2, 'Element:71:84', 22171, 'EST-PMV-22MA', -5.156405555555556, 36.434263888888886, false, null, true, 1, true, '#000000',true),
(32, 2, 'Element:71:90', 19371, 'EST-PMV-18CA', -5.173377777777778, 36.43239444444444, false, null, true, 1, true, '#000000',true),
(33, 2, 'Element:71:85', 22171, 'EST-PMV-26MA', -5.1849, 36.43289166666666, false, null, true, 1, true, '#000000',true),
(34, 2, 'Element:71:80', 22171, 'STM2-PMV-16MA', -5.218311111111111, 36.42060277777777, false, null, true, 1, true, '#000000',true);



INSERT INTO ui.map_layer_element_zooms (map_layer_element_id, zoom_lower, zoom_upper, graphic_icon_id, longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color) VALUES
(11,  10, 13, 19371,  -4.9716611111111115, 36.52565555555555,  false, null, true, false, '#00000'),
(12,  10, 13, 19371,  -4.950216666666667, 36.52636944444444,  false, null, true, false, '#00000'),
(13,  10, 13, 22171,  -5.289669444444445, 36.295138888888886,  false, null, true, false, '#00000'),
(14,  10, 13, 22171,  -5.282908333333333, 36.29730833333333,  false, null, true, false, '#00000'),
(15,  10, 13, 22171,  -5.268169444444444, 36.371386111111114,  false, null, true, false, '#00000'),
(16,  10, 13, 22171,  -5.262094444444444, 36.383825,  false, null, true, false, '#00000'),
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
(27,  10, 13, 22171,  -5.225447222222222, 36.40949166666667,  false, null, true, false, '#00000'),
(28,  10, 13, 22171,  -5.2487055555555555, 36.399594444444446, false, null, true, false, '#00000'),
(29,  10, 13, 22171,  -5.187919444444445, 36.43279444444444, false, null, true, false, '#00000'),
(30,  10, 13, 22171,  -5.160819444444445, 36.433425, false, null, true, false, '#00000'),
(31,  10, 13, 22171,  -5.156405555555556, 36.434263888888886, false, null, true, false, '#00000'),
(32,  10, 13, 19371,  -5.173377777777778, 36.43239444444444, false, null, true, false, '#00000'),
(33,  10, 13, 22171,  -5.1849, 36.43289166666666, false, null, true, false, '#00000'),
(34,  10, 13, 22171,  -5.218311111111111, 36.42060277777777, false, null, true, false, '#00000'),


(11,  13.5, 16, 19471,  -4.9716611111111115, 36.52565555555555,  false, null, true, false, '#00000'),
(12,  13.5, 16, 19471,  -4.950216666666667, 36.52636944444444,  false, null, true, false, '#00000'),
(13,  13.5, 16, 22271,  -5.289669444444445, 36.296138888888886,  false, null, true, false, '#00000'),
(14,  13.5, 16, 22271,  -5.282908333333333, 36.29730833333333,  false, null, true, false, '#00000'),
(15,  13.5, 16, 22271,  -5.268169444444444, 36.371386111111114,  false, null, true, false, '#00000'),
(16,  13.5, 16, 22271,  -5.262094444444444, 36.383825,  false, null, true, false, '#00000'),
(17,  13.5, 16, 19471,  -5.005908333333333, 36.499425,  false, null, true, false, '#00000'),
(18,  13.5, 16, 19471,  -4.9856, 36.51530555555556, false, null, true, false, '#00000'),
(19,  13.5, 16, 19471,  -5.073833333333333, 36.48594166666667,  false, null, true, false, '#00000'),
(20,  13.5, 16, 19471,  -5.048258333333333, 36.48600833333334, false, null, true, false, '#00000'),
(21,  13.5, 16, 22271,  -4.924852777777778, 36.513172222222224,  false, null, true, false, '#00000'),
(22,  13.5, 16, 22271,  -4.94933611111111, 36.49565555555556, false, null, true, false, '#00000'),
(23,  13.5, 16, 19471,  -4.740575, 36.50481388888889,  false, null, true, false, '#00000'),
(24,  13.5, 16, 22271,  -4.709702777777778, 36.50683333333333, false, null, true, false, '#00000'),
(25,  13.5, 16, 22271,  -4.757883333333333, 36.50729444444445,  false, null, true, false, '#00000'),
(26,  13.5, 16, 22271,  -4.779144444444444, 36.512477777777775, false, null, true, false, '#00000'),
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
(1, 1, 1, 'LBL_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(2, 1, 2, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(3, 1, 3, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);


--INSERT INTO ui.map_zones (id, map_id, name, description, label, icon, longitude, latitude, zoom) VALUES
--(1,	1, 'RV ', 'Rio Verder', 'LBL_TUNEL_RV', NULL, -4.962095, 36.525937, 15),
--(2, 1, 'MM', 'Monte Mayor', 'LBL_TUNEL_MM', NULL, -5.060833333333333, 36.484722222222224, 15),
--(3,	1, 'STM', 'Santa María', 'LBL_TUNEL_STMI', NULL, -4.768797222222222, 36.510441666666665,	15),
--(4,	1, 'LQ', 'La Quinta', 'LBL_TUNEL_LQ', NULL,	-4.99525, 36.504694444444446,	15),
--(5,	1, 'CH', 'Calahonda', 'LBL_TUNEL_CH', NULL,	-4.727638888888889, 36.50606944444444,	15),
--(6,	1, 'CAS', 'Casares', 'LBL_TUNEL_CS', NULL,	-5.237180555555556, 36.4026388888888,	15),
--(7,	1, 'COR', 'Corominas', 'LBL_TUNEL_CO', NULL, -5.198508333333334, 36.4307611111111,	15),
--(8,	1, 'STMII', 'Santa María II ', 'LBL_TUNEL_STMII', NULL,	-5.209619444444445, 36.42483333333333,	15),
--(9,	1, 'EST', 'Estepona', 'LBL_TUNEL_EST', NULL, -5.173011111111111, 36.433277777777775,	15),
--(10,1, 'NG', 'Nagüeres', 'LBL_TUNEL_NG', NULL,	-4.938727777777778, 36.51513888888889,	15);

