INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode, custom_provider) VALUES 
(3, 'mapa', 'mapa', 'LBL_MENU_ITEM_MAP', NULL, -4.956077777777778, 36.46929444444445, 11, 16, 10, -6.962095, 34.525937, -2.962095, 38.525937, null, null, true,
'{ 
	"map": {"enabled": true, "type": "WMS", "serverType": "geoserver", "url": "http://192.168.88.165:8700/geoserver/wms", "name": "osm"},
	"router": {"enabled": true, "type": "OpenRouteService", "url": "http://192.168.88.165:8701/ors"},
    "smart-road-info": {"url": "http://192.168.88.165:8702/api"}
}');

INSERT INTO ui.map_incidents(id, incident_graphic_icon_id, incident_report_module_action_id, incident_wizard_module_action_id) VALUES
(3, 1000, 100401, 101301);

INSERT INTO ui.map_layers (id, base_layer, map_id, "name", description, "label", icon, visible) VALUES 
(3, NULL, 3, 'Layer Paneles', 'Layer Paneles', 'LBL_ELEMENT_TYPE_PANEL', NULL, true);
 
INSERT INTO ui.map_layer_elements (id, layer_id, element_id, graphic_icon_id, "label", longitude, latitude, horizontal_flip, rotate, tooltip, z_index_front, show_text, text_color,selectable) VALUES 
(1011, 3, 'Element:71:1', 19371, 'RV-PMV-5MA', -4.9716611111111115, 36.52565555555555, false, null, true, 1, true, '#000000',true),
(1012, 3, 'Element:71:2', 19371, 'RV-PMV-6CA', -4.950216666666667, 36.52636944444444, false, null, true, 1, true, '#000000',true),
(1017, 3, 'Element:71:8', 19371, 'LQ-PMV-3MA', -5.005908333333333, 36.499425, false, null, true, 1, true, '#000000',true),
(1018, 3, 'Element:71:7', 19371, 'LQ-PMV-4CA', -4.9856, 36.51530555555556, false, null, true, 1, true, '#000000',true),
(1019, 3, 'Element:71:3', 19371, 'MM-PMV-1MA', -5.073833333333333, 36.48594166666667, false, null, true, 1, true, '#000000',true),
(1020, 3, 'Element:71:4', 19371, 'MM-PMV-2CA', -5.048258333333333, 36.48600833333334, false, null, true, 1, true, '#000000',true),
(1021, 3, 'Element:71:82', 22171, 'NG-PMV-21CA', -4.924852777777778, 36.513172222222224, false, null, true, 1, true, '#000000',true),
(1022, 3, 'Element:71:83', 22171, 'NG-PMV-25MA', -4.94933611111111, 36.49565555555556, false, null, true, 1, true, '#000000',true),
(1023, 3, 'Element:71:9', 19371, 'CH-PMV-7MA', -4.740575, 36.50481388888889, false, null, true, 1, true, '#000000',true),
(1024, 3, 'Element:71:10', 22171, 'CH-PMV-8CA', -4.709702777777778, 36.50683333333333, false, null, true, 1, true, '#000000',true),
(1025, 3, 'Element:71:5', 22171, 'STMI-PMV-24CA', -4.757883333333333, 36.50729444444445, false, null, true, 1, true, '#000000',true),
(1026, 3, 'Element:71:6', 22171, 'STMI-PMV-23MA', -4.779144444444444, 36.512477777777775, false, null, true, 1, true, '#000000',true);

INSERT INTO ui.map_layer_element_zooms (map_layer_element_id, zoom_lower, zoom_upper, graphic_icon_id, longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color) VALUES
(1011,  10, 13, 19371,  -4.9716611111111115, 36.52565555555555,  false, null, true, false, '#00000'),
(1012,  10, 13, 19371,  -4.950216666666667, 36.52636944444444,  false, null, true, false, '#00000'),
(1017,  10, 13, 19371,  -5.005908333333333, 36.499425,  false, null, true, false, '#00000'),
(1018,  10, 13, 19371,  -4.9856, 36.51530555555556, false, null, true, false, '#00000'),
(1019,  10, 13, 19371,  -5.073833333333333, 36.48594166666667,  false, null, true, false, '#00000'),
(1020,  10, 13, 19371,  -5.048258333333333, 36.48600833333334, false, null, true, false, '#00000'),
(1021,  10, 13, 22171,  -4.924852777777778, 36.513172222222224,  false, null, true, false, '#00000'),
(1022,  10, 13, 22171,  -4.94933611111111, 36.49565555555556, false, null, true, false, '#00000'),
(1023,  10, 13, 19371,  -4.740575, 36.50481388888889,  false, null, true, false, '#00000'),
(1024,  10, 13, 22171,  -4.709702777777778, 36.50683333333333, false, null, true, false, '#00000'),
(1025,  10, 13, 22171,  -4.757883333333333, 36.50729444444445, false, null, true, false, '#00000'),
(1026,  10, 13, 22171,  -4.779144444444444, 36.512477777777775, false, null, true, false, '#00000'),


(1011,  13.5, 16, 19471,  -4.9716611111111115, 36.52465555555555,  false, null, true, false, '#00000'),
(1012,  13.5, 16, 19471,  -4.950216666666667, 36.52436944444444,  false, null, true, false, '#00000'),
(1017,  13.5, 16, 19471,  -5.005908333333333, 36.497425,  false, null, true, false, '#00000'),
(1018,  13.5, 16, 19471,  -4.9856, 36.51330555555556, false, null, true, false, '#00000'),
(1019,  13.5, 16, 19471,  -5.073833333333333, 36.48394166666667,  false, null, true, false, '#00000'),
(1020,  13.5, 16, 19471,  -5.048258333333333, 36.48400833333334, false, null, true, false, '#00000'),
(1021,  13.5, 16, 22271,  -4.924852777777778, 36.512172222222224,  false, null, true, false, '#00000'),
(1022,  13.5, 16, 22271,  -4.94933611111111, 36.49365555555556, false, null, true, false, '#00000'),
(1023,  13.5, 16, 19471,  -4.740575, 36.50281388888889,  false, null, true, false, '#00000'),
(1024,  13.5, 16, 22271,  -4.709702777777778, 36.50483333333333, false, null, true, false, '#00000'),
(1025,  13.5, 16, 22271,  -4.757883333333333, 36.50529444444445,  false, null, true, false, '#00000'),
(1026,  13.5, 16, 22271,  -4.779144444444444, 36.510477777777775, false, null, true, false, '#00000');


INSERT INTO ui.map_commands
(id, map_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES
(101, 3, 1, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(102, 3, 2, 'LBL_CMD_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(103, 3, 3, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(107, 3, 4, 'LBL_CMD_MAINTENANCE', 'mdi mdi-wrench', false, 104601, NULL, 2, true, NULL, false, true);


INSERT INTO ui.map_zones (id, map_id, name, description, label, icon, longitude, latitude, zoom) VALUES
(101, 3, 'RV ', 'Rio Verde', 'LBL_TREE_RIO_VERDE', NULL, -4.962095, 36.525937, 15),
(102, 3, 'MM', 'Montemayor', 'LBL_TREE_MONTEMAYOR', NULL, -5.060833333333333, 36.484722222222224, 15),
(103, 3, 'STM', 'Sta. María I', 'LBL_TREE_STA_MARIA_I', NULL, -4.768797222222222, 36.510441666666665,	15),
(104, 3, 'LQ', 'La Quinta', 'LBL_TREE_LA_QUINTA', NULL,	-4.99525, 36.504694444444446,	15),
(105, 3, 'CH', 'Calahonda', 'LBL_TREE_CALAHONDA', NULL,	-4.727638888888889, 36.50606944444444,	15),
(1010,3, 'NG', 'Nagüeres', 'LBL_TREE_NAGUELES', NULL,	-4.938727777777778, 36.51513888888889,	15);


