-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(908, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_CGBT_LT2', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_CGBT_LT2', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_CGBT_LT2', NULL, 'img/synoptics/unifilar_cgbt_lt2.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90805600, 908, 'SYNOPTIC_LAYERS_UNIFILAR', 'LBL_SYNOPTIC_LAYERS_UNIFILAR', true),
(90805700, 908, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true),
(90807000, 908, 'SYNOPTIC_LAYERS_ANALIZADOR_RED', 'LBL_SYNOPTIC_LAYERS_ANALIZADOR_RED', true),
(90807500, 908, 'SYNOPTIC_LAYERS_GE', 'LBL_SYNOPTIC_LAYERS_GE', true),
(90806500, 908, 'SYNOPTIC_LAYERS_SAI', 'LBL_SYNOPTIC_LAYERS_SAI', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(21, 908, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(22, 908, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(23, 908, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_element_type_commands
(element_type, synoptic_command_id, module_action_id, view_type_id) VALUES
('ElementType:56', 22, 200002, 2);


INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90805600, 'Element:56:2', 000056, 'ILU-SUR-DP2', 333, 598, false, NULL, true, false, NULL, true),
(90805600, 'Element:56:1', 000056, 'ILU-NOR-DP2', 467, 598, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:103', 000057, 'INT-VENT-3-15-CGBT-DP2', 54, 804, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:104', 000057, 'INT-VENT-3-16-CGBT-DP2', 214, 804, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:105', 000057, 'INT-VENT-3-17-CGBT-DP2', 364, 804, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:106', 000057, 'INT-VENT-3-18-CGBT-DP2', 524, 804, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:107', 000057, 'INT-VENT-3-19-CGBT-DP2', 673, 804, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:108', 000057, 'INT-VENT-3-20-CGBT-DP2', 836, 804, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:109', 000057, 'INT-VENT-3-21-CGBT-DP2', 1001, 804, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:110', 000057, 'INT-VENT-3-22-CGBT-DP2', 1167, 804, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:111', 000057, 'INT-VENT-3-23-CGBT-DP2', 1320, 804, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:112', 000057, 'INT-VENT-3-24-CGBT-DP2', 1481, 804, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:113', 000057, 'INT-VENT-3-25-CGBT-DP2', 1637, 804, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:114', 000057, 'INT-VENT-3-26-CGBT-DP2', 1799, 804, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:102', 000057, 'INT-VENT-2-10-CGBT-DP2', 1793, 487, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:101', 000057, 'INT-VENT-2-09-CGBT-DP2', 1637, 487, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:100', 000057, 'INT-VENT-2-08-CGBT-DP2', 1481, 487, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:99', 000057, 'INT-VENT-2-07-CGBT-DP2', 1330, 487, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:98', 000057, 'INT-VENT-2-06-CGBT-DP2', 1167, 487, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:95', 000057, 'INT-Q10-CGBT-DP2', 1001, 487, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:94', 000057, 'INT-Q9-CGBT-DP2', 836, 487, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:93', 000057, 'INT-Q8-CGBT-DP2', 673, 487, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:90', 000057, 'INT-Q7-CGBT-DP2', 472, 487, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:89', 000057, 'INT-Q6-CGBT-DP2', 338, 487, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:88', 000057, 'INT-Q5-CGBT-DP2', 192, 487, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:87', 000057, 'INT-Q4-CGBT-DP2', 54, 487, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:85', 00005703, 'INT-Q2-CGBT-DP2', 567, 427, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:117', 000057, 'INT-F46-F47-D3-CGBT-DP2', 1699, 311, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:116', 000057, 'INT-F44-F45-D2-CGBT-DP2', 1560, 311, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:115', 000057, 'INT-F42-F43-D1-CGBT-DP2', 1426, 311, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:97', 000057, 'INT-F32-F6-CGBT-DP2', 1288, 311, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:96', 000057, 'INT-F1-F2-F3-CGBT-DP2', 1150, 311, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:83', 000057, 'INT-F1-F2-F1.1-CGBT-DP2', 1191, 167, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:91', 000057, 'INT-Q3-CGBT-DP2', 747, 320, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:84', 000057, 'INT-Q1-RED-CGBT-DP2', 267, 294, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:81', 000057, 'INT-Q1-GEN-CGBT-DP2', 267, 127, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:92', 00005701, 'INT-K02-CGBT-DP2', 1553, 163, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:82', 00005701, 'INT-K01-GEN-CGBT-DP2', 1281, 84, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:86', 00005701, 'INT-K01-RED-CGBT-DP2', 1281, 168, false, NULL, true, false, NULL, true),
(90807000, 'Element:70:2', 000070, 'AR-2-DP2', 620, 215, false, NULL, true, false, NULL, true),
(90807000, 'Element:70:1', 000070, 'AR-1-DP2', 106, 215, false, NULL, true, false, NULL, true),
(90807500, 'Element:75:1', 00007500, 'GE2-DP', 733, 74, false, NULL, true, false, NULL, true),
(90806500, 'Element:65:2', 00006501, 'SAI-S-DP2', 831, 598, false, NULL, true, false, NULL, true),
(90806500, 'Element:65:1', 00006501, 'SAI-N-DP2', 668, 598, false, NULL, true, false, NULL, true);

