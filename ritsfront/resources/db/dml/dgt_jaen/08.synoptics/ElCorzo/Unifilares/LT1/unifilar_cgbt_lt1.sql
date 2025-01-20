-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(914, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_CGBT_LT1', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_CGBT_LT1', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_CGBT_LT1', NULL, 'img/synoptics/unifilar_cgbt_lt1.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91405600, 914, 'SYNOPTIC_LAYERS_UNIFILAR', 'LBL_SYNOPTIC_LAYERS_UNIFILAR', true),
(91405700, 914, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true),
(91407000, 914, 'SYNOPTIC_LAYERS_ANALIZADOR_RED', 'LBL_SYNOPTIC_LAYERS_ANALIZADOR_RED', true),
(91407500, 914, 'SYNOPTIC_LAYERS_GE', 'LBL_SYNOPTIC_LAYERS_GE', true),
(91406500, 914, 'SYNOPTIC_LAYERS_SAI', 'LBL_SYNOPTIC_LAYERS_SAI', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(39, 914, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(40, 914, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(41, 914, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_element_type_commands
(element_type, synoptic_command_id, module_action_id, view_type_id) VALUES
('ElementType:56', 40, 200002, 2);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91405600, 'Element:56:11', 00005602, 'ILU-NOR-DP1', 884, 842, false, NULL, true, false, NULL, true),
(91405600, 'Element:56:10', 00005602, 'ILU-SUR-DP1', 752, 842, false, NULL, true, false, NULL, true),
(91405700, 'Element:57:240', 000057, 'INT-Q9-CGBT-DP1', 1252, 729, false, NULL, true, false, NULL, true),
(91405700, 'Element:57:239', 000057, 'INT-Q8-CGBT-DP1', 1090, 729, false, NULL, true, false, NULL, true),
(91405700, 'Element:57:236', 000057, 'INT-Q7-CGBT-DP1', 891, 729, false, NULL, true, false, NULL, true),
(91405700, 'Element:57:235', 000057, 'INT-Q6-CGBT-DP1', 759, 729, false, NULL, true, false, NULL, true),
(91405700, 'Element:57:234', 000057, 'INT-Q5-CGBT-DP1', 616, 729, false, NULL, true, false, NULL, true),
(91405700, 'Element:57:233', 000057, 'INT-Q4-CGBT-DP1', 480, 729, false, NULL, true, false, NULL, true),
(91405700, 'Element:57:226', 000057, 'INT-Q1-GEN-CGBT-DP1', 691, 196, false, NULL, true, false, NULL, true),
(91405700, 'Element:57:241', 000057, 'INT-F4-CGBT-DP1', 1265, 459, false, NULL, true, false, NULL, true),
(91405700, 'Element:57:238', 000057, 'INT-Q3-CGBT-DP1', 1163, 459, false, NULL, true, false, NULL, true),
(91405700, 'Element:57:231', 000057, 'INT-Q1-RED-CGBT-DP1', 691, 459, false, NULL, true, false, NULL, true),
(91405700, 'Element:57:228', 000057, 'INT-F1-CGBT-DP1', 588, 459, false, NULL, true, false, NULL, true),
(91405700, 'Element:57:227', 00005702, 'INT-K01-GEN-CGBT-DP1', 684, 292, false, NULL, true, false, NULL, true),
(91405700, 'Element:57:237', 00005702, 'INT-K02-CGBT-DP1', 1156, 570, false, NULL, true, false, NULL, true),
(91405700, 'Element:57:229', 00005702, 'INT-K01-RED-CGBT-DP1', 685, 571, false, NULL, true, false, NULL, true),
(91405700, 'Element:57:232', 00005703, 'INT-Q2-CGBT-DP1', 987, 684, false, NULL, true, false, NULL, true),
(91407000, 'Element:70:8', 00007003, 'AR-1-CZ', 520, 350, false, NULL, true, false, NULL, true),
(91407000, 'Element:70:9', 00007003, 'AR-2-CZ', 1024, 340, false, NULL, true, false, NULL, true),
(91407500, 'Element:75:4', 00007505, 'GE1-CZ', 1144, 124, false, NULL, true, false, NULL, true),
(91406500, 'Element:65:8', 00006505, 'SAI-S-CZ', 1244, 843, false, NULL, true, false, NULL, true),
(91406500, 'Element:65:7', 00006505, 'SAI-N-CZ', 1082, 843, false, NULL, true, false, NULL, true);

