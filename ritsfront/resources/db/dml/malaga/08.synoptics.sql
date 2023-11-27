
-- Para los identificadores de los graphic_icons seguiremos los siguientes criterios
-- Orden del graphic_icon dentro del mismo tipo --> 2 cifras
-- Subtipo del elemento --> 2 cifras (0 si no tiene subtipo)
-- Tipo del elemento --> 4 cifras
INSERT INTO ui.graphic_icons 
(id, name, description, alarms, measures, js_draw_file, width, height, svg_file, js_custom_type_render, keep_aspect_ratio) VALUES
(220071, 'VMS', 'VMS', false, true, 'component/graphicIcon/graphicIconCustom.js', 200, 48.370773, 'img/graphic_icon/71_vms_dgt.svg', 'component/graphicIcon/custom/vms.js',true),
(120071, 'VMS', 'VMS', false, true, 'component/graphicIcon/graphicIconCustom.js', 50, 50, 'img/graphic_icon/71_vms_clv_af.svg', 'component/graphicIcon/custom/vms.js',true),
(000036, 'BALIZA', 'BALIZA', false, true, 'component/graphicIcon/graphicIconCustom.js', 20, 20, 'img/graphic_icon/36_baliza_amarilla.svg', 'component/graphicIcon/custom/baliza_amarilla.js',true),
(100036, 'BALIZA', 'BALIZA', false, true, 'component/graphicIcon/graphicIconCustom.js', 20, 20, 'img/graphic_icon/36_baliza_verde.svg', 'component/graphicIcon/custom/baliza_verde.js',true),
(000037, 'IPL', 'IPL', false, true, 'component/graphicIcon/graphicIconCustom.js', 50, 63.636383, 'img/graphic_icon/37_ipl.svg', 'component/graphicIcon/custom/ipl.js',true),
(000038, 'BUS_VAO', 'BUS_VAO', false, false, 'component/graphicIcon/graphicIconDefault.js', 120, 120, 'img/graphic_icon/38_bus_vao.svg', 'null',true);


-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(901, 'TREE_SINOPTICO', 'TREE_SINOPTICO', 'LBL_TREE_SINOPTICO', NULL, 'img/synoptics/SVG_SCADA.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90100000, 901, 'SEÑALIZACION', 'LBL_LAYER_SEÑALIZACION', true),
(90100001, 901, 'BALIZAMIENTO', 'LBL_LAYER_BALIZAMIENTO', true),
(90107122, 901, 'PANEL GTG', 'LBL_LAYER_PANEL_GTG', true),
(90107112, 901, 'PANEL AF', 'LBL_LAYER_PANEL_AF', true),
(90103600, 901, 'BALIZA', 'LBL_LAYER_BALIZA', true),
(90103700, 901, 'IPL', 'LBL_LAYER_IPL', true),
(90103800, 901, 'BUS_VAO', 'LBL_LAYER_BUS_VAO', true);


INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(901, 90100000, 90107122),
(901, 90100000, 90107112),
(901, 90100001, 90103600),
(901, 90100001, 90103700),
(901, 90100001, 90103800);


INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(0, 901, 0, 'LBL_CMD_ALARMS', 'mdi mdi-bell', false, 100501, NULL, 2, true, NULL, false, true),
(1, 901, 1, 'LBL_ELEMENT', 'mdi mdi-engine', true, 101401, NULL, 2, false, NULL, true, true),
(2, 901, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-cog', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90107122, 'Element:71:5', 220071, 'PMV-A357-PK-61-645-D', 1247, 867, false, NULL, true, false, NULL, true),
(90107122, 'Element:71:3', 220071, 'PMV-A7056-PK-0-050-C', 929, 623, false, NULL, true, false, NULL, true),
(90107122, 'Element:71:4', 220071, 'PMV-A357-PK-59-950-C', 534, 709, false, NULL, true, false, NULL, true),
(90107122, 'Element:71:1', 220071, 'PMV-A7056-PK-1-050-C', 1018, 302, false, NULL, true, false, NULL, true),
(90107122, 'Element:71:2', 220071, 'PMV-A7056-PK-0-550-D', 643, 451, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:6', 120071, 'PGC1-A7056-PK-0-550-C', 1080, 450, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:7', 120071, 'PGC2-A7056-PK-0-550-C', 1030, 450, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:8', 120071, 'PGC3-A7056-PK-0-550-C', 980, 450, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:9', 120071, 'PGC1-A7056-PK-0-050-C', 1079, 672, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:10', 120071, 'PGC2-A7056-PK-0-050-C', 1005, 672, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:11', 120071, 'PGC3-A7056-PK-0-050-C', 929, 672, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:12', 120071, 'PGC1-A357-PK-61-000-D', 979, 744, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:13', 120071, 'PGC2-A357-PK-61-000-D', 929, 744, false, NULL, true, false, NULL, true),

(90103600, 'Element:36:2', 100036, 'BAL_V', 977, 245, false, NULL, true, false, NULL, true),
(90103600, 'Element:36:2', 100036, 'BAL_V', 968, 291, false, NULL, true, false, NULL, true),
(90103600, 'Element:36:2', 100036, 'BAL_V', 959, 338, false, NULL, true, false, NULL, true),
(90103600, 'Element:36:2', 100036, 'BAL_V', 885, 788, false, NULL, true, false, NULL, true),
(90103600, 'Element:36:2', 100036, 'BAL_V', 928, 807, false, NULL, true, false, NULL, true),

(90103600, 'Element:36:1', 000036, 'BAL_A', 950, 383, false, NULL, true, false, NULL, true),
(90103600, 'Element:36:1', 000036, 'BAL_A', 939, 430, false, NULL, true, false, NULL, true),
(90103600, 'Element:36:1', 000036, 'BAL_A', 927, 474, false, NULL, true, false, NULL, true),
(90103600, 'Element:36:1', 000036, 'BAL_A', 915, 519, false, NULL, true, false, NULL, true),
(90103600, 'Element:36:1', 000036, 'BAL_A', 902, 565, false, NULL, true, false, NULL, true),
(90103600, 'Element:36:1', 000036, 'BAL_A', 889, 610, false, NULL, true, false, NULL, true),
(90103600, 'Element:36:1', 000036, 'BAL_A', 875, 655, false, NULL, true, false, NULL, true),
(90103600, 'Element:36:1', 000036, 'BAL_A', 861, 700, false, NULL, true, false, NULL, true),
(90103600, 'Element:36:1', 000036, 'BAL_A', 864, 746, false, NULL, true, false, NULL, true),

(90103700, 'Element:37:2', 000037, 'IPL-1200', 989, 800, false, NULL, true, false, NULL, true),
(90103700, 'Element:37:1', 000037, 'IPL-2500', 953, 534, false, NULL, true, false, NULL, true),

(90103800, 'Element:38:1', 000038, 'BUS-VAO', 30, 30, false, NULL, true, false, NULL, true);
