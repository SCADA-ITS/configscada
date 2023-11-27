-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(603, 'SYNOPTIC_CH_TRAFFIC_CONTROL', 'SYNOPTIC_CH_TRAFFIC_CONTROL', 'LBL_SYNOPTIC_CH_TRAFFIC_CONTROL', NULL, 'img/synoptics/Ausol_CH_Tunel_Traffic_Control.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(60300003, 603, 'LAYER_ACCESS_CTRL', 'LBL_LAYER_ACCESS_CTRL', true),
(60300200, 603, 'LAYER_ACCESS_CTRL_CAMERA', 'LBL_LAYER_ACCESS_CTRL_CAMERA', true),
(60300800, 603, 'LAYER_ACCESS_CTRL_BAR', 'LBL_LAYER_ACCESS_CTRL_BAR', true),
(60301300, 603, 'LAYER_ACCESS_CTRL_SEM_3_GROUPS', 'LBL_LAYER_ACCESS_CTRL_SEM_3_GROUPS', true),
(60305300, 603, 'LAYER_ACCESS_CTRL_SEM_2_GROUPS', 'LBL_LAYER_ACCESS_CTRL_SEM_2_GROUPS', true),
(60307100, 603, 'LAYER_ACCESS_CTRL_PMV', 'LBL_LAYER_ACCESS_CTRL_PMV', true),
(60300004, 603, 'LAYER_TRAFFIC', 'LBL_LAYER_TRAFFIC', true),
(60300400, 603, 'LAYER_TRAFFIC_SECTIONS', 'LBL_LAYER_TRAFFIC_SECTIONS', true),
(60305100, 603, 'LAYER_TRAFFIC_ETD', 'LBL_LAYER_TRAFFIC_ETD', true),
(60300005, 603, 'LAYER_WEATHER', 'LBL_LAYER_WEATHER', true),
(60300100, 603, 'LAYER_WEATHER_WS', 'LBL_LAYER_WEATHER_WS', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(603, 60300003, 60300200),
(603, 60300003, 60300800),
(603, 60300003, 60301300),
(603, 60300003, 60305300),
(603, 60300003, 60307100),
(603, 60300004, 60300400),
(603, 60300004, 60305100),
(603, 60300004, 60300100);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(6, 603, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(7, 603, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(8, 603, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(60300200, 'Element:2:72', 02020002, 'CH-TV-1MA', 236, 972, false, NULL, true, false, NULL, true),
(60300200, 'Element:2:79', 02020002, 'CH-TV-8MA', 1663, 972, true, NULL, true, false, NULL, true),
(60300200, 'Element:2:78', 02020002, 'CH-TV-7MA', 1313, 972, false, NULL, true, false, NULL, true),
(60300200, 'Element:2:63', 02020002, 'CH-TV-2CA', 1527, 128, true, NULL, true, false, NULL, true),
(60300200, 'Element:2:62', 02020002, 'CH-TV-1CA', 1677, 128, true, NULL, true, false, NULL, true),
(60300200, 'Element:2:70', 02020002, 'CH-TV-1GA', 905, 526, false, NULL, true, false, NULL, true),
(60300200, 'Element:2:71', 02020002, 'CH-TV-2GA', 1068, 594, true, NULL, true, false, NULL, true),
(60300200, 'Element:2:77', 02020002, 'CH-TV-6MA', 1162, 972, false, NULL, true, false, NULL, true),
(60300200, 'Element:2:76', 02020002, 'CH-TV-5MA', 912, 972, false, NULL, true, false, NULL, true),
(60300200, 'Element:2:75', 02020002, 'CH-TV-4MA', 723, 972, false, NULL, true, false, NULL, true),
(60300200, 'Element:2:61', 01010002, 'CH-TVD-1MA', 1831, 562, false, NULL, true, false, NULL, true),
(60300200, 'Element:2:80', 01010002, 'CH-TVD-1CA', 123, 562, false, NULL, true, false, NULL, true),
(60300200, 'Element:2:73', 02020002, 'CH-TV-2MA', 336, 972, false, NULL, true, false, NULL, true),
(60300200, 'Element:2:74', 02020002, 'CH-TV-3MA', 473, 972, false, NULL, true, false, NULL, true),
(60300200, 'Element:2:67', 02020002, 'CH-TV-6CA', 735, 128, true, NULL, true, false, NULL, true),
(60300200, 'Element:2:69', 02020002, 'CH-TV-8CA', 208, 128, false, NULL, true, false, NULL, true),
(60300200, 'Element:2:68', 02020002, 'CH-TV-7CA', 587, 128, true, NULL, true, false, NULL, true),
(60300200, 'Element:2:66', 02020002, 'CH-TV-5CA', 872, 128, true, NULL, true, false, NULL, true),
(60300200, 'Element:2:65', 02020002, 'CH-TV-4CA', 1123, 128, true, NULL, true, false, NULL, true),
(60300200, 'Element:2:64', 02020002, 'CH-TV-3CA', 1276, 128, true, NULL, true, false, NULL, true),
(60300800, 'Element:8:13', 01020008, 'CH-BAR-1MA', 185, 685, false, NULL, true, false, NULL, true),
(60300800, 'Element:8:14', 01020008, 'CH-BAR-2MA', 185, 863, false, NULL, true, false, NULL, true),
(60300800, 'Element:8:16', 01020008, 'CH-BAR-2CA', 1641, 237, false, NULL, true, false, NULL, true),
(60300800, 'Element:8:15', 01020008, 'CH-BAR-1CA', 1641, 420, false, NULL, true, false, NULL, true),
(60301300, 'Element:13:10', 01000013, 'CH-SMF-RAV-1CA', 1740, 237, false, NULL, true, false, NULL, true),
(60301300, 'Element:13:9', 01000013, 'CH-SMF-RAV-1MA', 85, 863, false, NULL, true, false, NULL, true),
(60305300, 'Element:53:16', 01010053, 'CH-SMF-AA-1CA', 1787, 237, false, NULL, true, false, NULL, true),
(60305300, 'Element:53:18', 02020053, 'CH-SMF-RR-1CA', 1691, 237, false, NULL, true, false, NULL, true),
(60305300, 'Element:53:14', 01010053, 'CH-SMF-AA-1MA', 35, 863, false, NULL, true, false, NULL, true),
(60305300, 'Element:53:17', 02020053, 'CH-SMF-RR-1MA', 135, 863, false, NULL, true, false, NULL, true),
(60307100, 'Element:71:10', 02220071, 'CH-PMV-8CA', 1540, 305, false, NULL, true, false, NULL, true),
(60307100, 'Element:71:9', 01180071, 'CH-PMV-7MA', 35, 757, false, NULL, true, false, NULL, true),
(60300400, 'Element:4:14', 01020004, 'CH-ETD-1-SEC-2MA-OUT', 609, 308, false, NULL, true, false, NULL, true),
(60300400, 'Element:4:13', 01020004, 'CH-ETD-1-SEC-1CA-IN', 1190, 305, false, NULL, true, false, NULL, true),
(60300400, 'Element:4:15', 01020004, 'CH-ETD-2-SEC-1MA-IN', 1190, 750, false, NULL, true, false, NULL, true),
(60300400, 'Element:4:16', 01020004, 'CH-ETD-2-SEC-2CA-OUT', 609, 755, false, NULL, true, false, NULL, true),
(60300100, 'Element:1:3', 01030001, 'CH-MET-1MA', 1732, 562, false, NULL, true, false, NULL, true);

