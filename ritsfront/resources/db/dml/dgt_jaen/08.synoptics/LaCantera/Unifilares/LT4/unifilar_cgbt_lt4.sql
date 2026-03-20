-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(912, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_CGBT_LT4', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_CGBT_LT4', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_CGBT_LT4', NULL, 'img/synoptics/unifilar_cgbt_lt4.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91205600, 912, 'SYNOPTIC_LAYERS_UNIFILAR', 'LBL_SYNOPTIC_LAYERS_UNIFILAR', true),
(91205700, 912, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true),
(91207000, 912, 'SYNOPTIC_LAYERS_ANALIZADOR_RED', 'LBL_SYNOPTIC_LAYERS_ANALIZADOR_RED', true),
(91207500, 912, 'SYNOPTIC_LAYERS_GE', 'LBL_SYNOPTIC_LAYERS_GE', true),
(91206500, 912, 'SYNOPTIC_LAYERS_SAI', 'LBL_SYNOPTIC_LAYERS_SAI', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(33, 912, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(34, 912, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(35, 912, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_element_type_commands
(element_type, synoptic_command_id, module_action_id, view_type_id) VALUES
('ElementType:56', 34, 200002, 2);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91205600, 'Element:56:20', 00005601, 'SAI-SUR-DP4', 1242, 848, false, NULL, true, false, NULL, true),
(91205600, 'Element:56:19', 00005601, 'SAI-NOR-DP4', 1081, 848, false, NULL, true, false, NULL, true),
(91205600, 'Element:56:7', 00005601, 'ILU-NOR-DP4', 882, 781, false, NULL, true, false, NULL, true),
(91205600, 'Element:56:8', 00005601, 'ILU-SUR-DP4', 749, 781, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:225', 000057, 'INT-F2-CGBT-DP4', 1414, 671, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:224', 000057, 'INT-Q9-CGBT-DP4', 1253, 671, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:223', 000057, 'INT-Q8-CGBT-DP4', 1090, 671, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:220', 000057, 'INT-Q7-CGBT-DP4', 891, 671, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:219', 000057, 'INT-Q6-CGBT-DP4', 760, 671, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:218', 000057, 'INT-Q5-CGBT-DP4', 617, 671, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:217', 000057, 'INT-Q4-CGBT-DP4', 480, 671, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:214', 000057, 'INT-F1-CGBT-DP4', 589, 401, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:215', 000057, 'INT-Q1-CGBT-DP4', 692, 401, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:216', 00005703, 'INT-Q2-CGBT-DP4', 989, 625, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:222', 00005706, 'INT-Q3-CGBT-DP4', 1163, 401, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:221', 00005702, 'INT-K02-CGBT-DP4', 1154, 504, false, NULL, true, false, NULL, true),
(91205700, 'Element:57:213', 00005702, 'INT-K01-CGBT-DP4', 682, 504, false, NULL, true, false, NULL, true),
(91207000, 'Element:70:6', 00007002, 'AR-1-CA', 1032, 284, false, NULL, true, false, NULL, true),
(91207000, 'Element:70:7', 00007002, 'AR-2-CA', 524, 284, false, NULL, true, false, NULL, true),
(91207500, 'Element:75:3', 00007504, 'GE4-CA', 1149, 144, false, NULL, true, false, NULL, true),
(91206500, 'Element:65:6', 00006503, 'SAI-S-CA', 1242, 779, false, NULL, true, false, NULL, true),
(91206500, 'Element:65:5', 00006503, 'SAI-N-CA', 1081, 779, false, NULL, true, false, NULL, true);

