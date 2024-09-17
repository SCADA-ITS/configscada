INSERT INTO ui.maps (id, name, description, "label", icon, longitude, latitude, zoom, zoom_max, zoom_min, limit_init_lon, limit_init_lat, limit_end_lon, limit_end_lat, custom_provider_url, default_zoom_search, dark_mode) VALUES 
(1, 'mapa', 'mapa', 'LBL_MENU_ITEM_MAP', NULL, -4.956077777777778, 36.46929444444445, 11, 16, 10, -6.962095, 34.525937, -2.962095, 38.525937, null, null, true);

INSERT INTO ui.map_layers (id, base_layer, map_id, "name", description, "label", icon, visible) VALUES 
(9700002, NULL, 1, 'Layer Camaras', 'Layer Camaras', 'LBL_ELEMENT_TYPE_CAMERA', NULL, true),
(9800071, NULL, 1, 'Layer Paneles', 'Layer Paneles', 'LBL_ELEMENT_TYPE_PANEL', NULL, true);

INSERT INTO ui.graphic_icons (id, "name", description, alarms, measures, js_draw_file, width, height, svg_pattern_var, svg_file, js_custom_type_render) VALUES
(040002, 'CAM_FIX', 'CAM_FIX', false, true, 'component/graphicIcon/graphicIconDefault.js', 30, 30,null,  'img/graphic_icon/02_camera.svg',true),
(050002, 'CAM_FIX', 'CAM_FIX', false, true, 'component/graphicIcon/graphicIconDefault.js', 50, 50,null,  'img/graphic_icon/02_camera.svg',true),
(100071, 'PMV', 'PMV', false, true, 'component/graphicIcon/graphicIconCustom.js', 240, 120, null, 'img/graphic_icon/71_vms_dgt.svg', 'component/graphicIcon/custom/vms.js');

INSERT INTO ui.map_layer_elements (id, layer_id, element_id, graphic_icon_id, "label", longitude, latitude, horizontal_flip, rotate, tooltip, z_index_front, show_text, text_color,selectable) VALUES 
(1, 9700002, 'Element:2:25', 040002, 'MAP-TV-1', -4.9716611111111115, 36.52565555555555, false, null, true, 1, true, '#000000',true),
(2, 9700002, 'Element:2:26', 040002, 'MAP-TV-2', -4.950216666666667, 36.52636944444444, false, null, true, 1, true, '#000000',true),
(3, 9700002, 'Element:2:27', 040002, 'MAP-TV-3', -5.005908333333333, 36.499425, false, null, true, 1, true, '#000000',true),
(4, 9700002, 'Element:2:28', 040002, 'MAP-TV-4', -4.9856, 36.51530555555556, false, null, true, 1, true, '#000000',true),
(5, 9700002, 'Element:2:29', 040002, 'MAP-TV-5', -5.073833333333333, 36.48594166666667, false, null, true, 1, true, '#000000',true),
(6, 9700002, 'Element:2:30', 040002, 'MAP-TV-6', -4.94933611111111, 36.49565555555556, false, null, true, 1, true, '#000000',true),
(7, 9700002, 'Element:2:31', 040002, 'MAP-TV-7', -4.740575, 36.50481388888889, false, null, true, 1, true, '#000000',true),
(8, 9700002, 'Element:2:32', 040002, 'MAP-TV-8', -4.709702777777778, 36.50683333333333, false, null, true, 1, true, '#000000',true),
(9, 9700002, 'Element:2:33', 040002, 'MAP-TV-9', -4.757883333333333, 36.50729444444445, false, null, true, 1, true, '#000000',true),
(10, 9700002, 'Element:2:34', 040002, 'MAP-TV-10', -4.779144444444444, 36.512477777777775, false, null, true, 1, true, '#000000',true),

(11, 9800071, 'Element:71:3', 100071, 'MAP-DGT-1', -4.9716611111111115, 36.52565555555555, false, null, true, 1, true, '#000000',true),
(12, 9800071, 'Element:71:4', 100071, 'MAP-DGT-2', -4.950216666666667, 36.52636944444444, false, null, true, 1, true, '#000000',true),
(13, 9800071, 'Element:71:5', 100071, 'MAP-DGT-3', -5.005908333333333, 36.499425, false, null, true, 1, true, '#000000',true),
(14, 9800071, 'Element:71:6', 100071, 'MAP-DGT-4', -4.9856, 36.51530555555556, false, null, true, 1, true, '#000000',true),
(15, 9800071, 'Element:71:7', 100071, 'MAP-DGT-5', -5.073833333333333, 36.48594166666667, false, null, true, 1, true, '#000000',true),
(16, 9800071, 'Element:71:8', 100071, 'MAP-DGT-6', -5.048258333333333, 36.48600833333334, false, null, true, 1, true, '#000000',true),
(17, 9800071, 'Element:71:9', 100071, 'MAP-DGT-7', -4.924852777777778, 36.513172222222224, false, null, true, 1, true, '#000000',true),
(18, 9800071, 'Element:71:10', 100071, 'MAP-DGT-8', -4.94933611111111, 36.49565555555556, false, null, true, 1, true, '#000000',true),
(19, 9800071, 'Element:71:11', 100071, 'MAP-DGT-9', -4.740575, 36.50481388888889, false, null, true, 1, true, '#000000',true),
(20, 9800071, 'Element:71:12', 100071, 'MAP-DGT-10', -4.709702777777778, 36.50683333333333, false, null, true, 1, true, '#000000',true),
(21, 9800071, 'Element:71:13', 100071, 'MAP-DGT-11', -4.757883333333333, 36.50729444444445, false, null, true, 1, true, '#000000',true),
(22, 9800071, 'Element:71:14', 100071, 'MAP-DGT-12', -4.779144444444444, 36.512477777777775, false, null, true, 1, true, '#000000',true);



INSERT INTO ui.map_layer_element_zooms (map_layer_element_id, zoom_lower, zoom_upper, graphic_icon_id, longitude, latitude, horizontal_flip, rotate, tooltip, show_text, text_color) VALUES
(1, 10, 13, 040002,  -4.9816611111111115, 36.52065555555555,  false, null, true, false, '#00000'),
(2, 10, 13, 040002,  -4.940216666666667, 36.52036944444444,  false, null, true, false, '#00000'),
(3, 10, 13, 040002,  -5.025908333333333, 36.498425,  false, null, true, false, '#00000'),
(4, 10, 13, 040002,  -4.9876, 36.50830555555556, false, null, true, false, '#00000'),
(5, 10, 13, 040002,  -5.073833333333333, 36.48894166666667,  false, null, true, false, '#00000'),
(6, 10, 13, 040002,  -4.94933611111111, 36.49995555555556, false, null, true, false, '#00000'),
(7, 10, 13, 040002,  -4.740575, 36.51081388888889,  false, null, true, false, '#00000'),
(8, 10, 13, 040002,  -4.709702777777778, 36.51083333333333, false, null, true, false, '#00000'),
(9, 10, 13, 040002,  -4.757883333333333, 36.51029444444445, false, null, true, false, '#00000'),
(10, 10, 13, 040002,  -4.779144444444444, 36.519477777777775, false, null, true, false, '#00000'),


(11, 10, 13, 100071,  -4.9716611111111115, 36.52565555555555,  false, null, true, false, '#00000'),
(12, 10, 13, 100071,  -4.950216666666667, 36.52636944444444,  false, null, true, false, '#00000'),
(13, 10, 13, 100071,  -5.005908333333333, 36.499425,  false, null, true, false, '#00000'),
(14, 10, 13, 100071,  -4.9856, 36.51530555555556, false, null, true, false, '#00000'),
(15, 10, 13, 100071,  -5.073833333333333, 36.48594166666667,  false, null, true, false, '#00000'),
(16, 10, 13, 100071,  -5.048258333333333, 36.48600833333334, false, null, true, false, '#00000'),
(17, 10, 13, 100071,  -4.924852777777778, 36.513172222222224,  false, null, true, false, '#00000'),
(18, 10, 13, 100071,  -4.94933611111111, 36.49565555555556, false, null, true, false, '#00000'),
(19, 10, 13, 100071,  -4.740575, 36.50481388888889,  false, null, true, false, '#00000'),
(20, 10, 13, 100071,  -4.709702777777778, 36.50683333333333, false, null, true, false, '#00000'),
(21, 10, 13, 100071,  -4.757883333333333, 36.50729444444445, false, null, true, false, '#00000'),
(22, 10, 13, 100071,  -4.779144444444444, 36.512477777777775, false, null, true, false, '#00000'),


(1, 13.5, 16, 050002,  -4.9816611111111115, 36.52065555555555,  false, null, true, false, '#00000'),
(2, 13.5, 16, 050002,  -4.940216666666667, 36.52036944444444,  false, null, true, false, '#00000'),
(3, 13.5, 16, 050002,  -5.025908333333333, 36.498425,  false, null, true, false, '#00000'),
(4, 13.5, 16, 050002,  -4.9876, 36.50830555555556, false, null, true, false, '#00000'),
(5, 13.5, 16, 050002,  -5.073833333333333, 36.48894166666667,  false, null, true, false, '#00000'),
(6, 13.5, 16, 050002,  -4.94933611111111, 36.49995555555556, false, null, true, false, '#00000'),
(7, 13.5, 16, 050002,  -4.740575, 36.51081388888889,  false, null, true, false, '#00000'),
(8, 13.5, 16, 050002,  -4.709702777777778, 36.51083333333333, false, null, true, false, '#00000'),
(9, 13.5, 16, 050002,  -4.757883333333333, 36.51029444444445,  false, null, true, false, '#00000'),
(10, 13.5, 16, 050002,  -4.779144444444444, 36.519477777777775, false, null, true, false, '#00000'),

(11, 13.5, 16, 100071,  -4.9716611111111115, 36.52465555555555,  false, null, true, false, '#00000'),
(12, 13.5, 16, 100071,  -4.950216666666667, 36.52436944444444,  false, null, true, false, '#00000'),
(13, 13.5, 16, 100071,  -5.005908333333333, 36.497425,  false, null, true, false, '#00000'),
(14, 13.5, 16, 100071,  -4.9856, 36.51330555555556, false, null, true, false, '#00000'),
(15, 13.5, 16, 100071,  -5.073833333333333, 36.48394166666667,  false, null, true, false, '#00000'),
(16, 13.5, 16, 100071,  -5.048258333333333, 36.48400833333334, false, null, true, false, '#00000'),
(17, 13.5, 16, 100071,  -4.924852777777778, 36.512172222222224,  false, null, true, false, '#00000'),
(18, 13.5, 16, 100071,  -4.94933611111111, 36.49365555555556, false, null, true, false, '#00000'),
(19, 13.5, 16, 100071,  -4.740575, 36.50281388888889,  false, null, true, false, '#00000'),
(20, 13.5, 16, 100071,  -4.709702777777778, 36.50483333333333, false, null, true, false, '#00000'),
(21, 13.5, 16, 100071,  -4.757883333333333, 36.50529444444445,  false, null, true, false, '#00000'),
(22, 13.5, 16, 100071,  -4.779144444444444, 36.510477777777775, false, null, true, false, '#00000');


INSERT INTO ui.map_commands
(id, map_id, "position", "label", icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES
(1, 1, 1, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(2, 1, 2, 'LBL_CMD_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(3, 1, 3, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(4, 1, 4, 'LBL_CMD_MAINTENANCE', 'mdi mdi-wrench', false, 104601, NULL, 2, true, NULL, false, true);


