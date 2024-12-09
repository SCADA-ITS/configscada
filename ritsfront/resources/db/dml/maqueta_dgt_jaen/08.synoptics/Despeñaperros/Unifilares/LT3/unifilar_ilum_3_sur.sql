-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(907, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_3_SUR', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_3_SUR', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_3_SUR', NULL, 'img/synoptics/unifilar_ilum_3_sur.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90705700, 907, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true),
(90708400, 907, 'SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', 'LBL_SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(18, 907, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(19, 907, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90705700, 'Element:57:68', 00005702, 'INT-K01-ILU-SUR-3', 927, 317, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:80', 000057, 'INT-EXT-ILU-SUR-3', 1791, 419, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:79', 000057, 'INT-REF-DER-3-ILU-SUR-3', 1636, 419, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:78', 000057, 'INT-REF-DER-2-ILU-SUR-3', 1481, 419, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:77', 000057, 'INT-REF-DER-1-ILU-SUR-3', 1327, 419, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:76', 000057, 'INT-CREP-DER-ILU-SUR-3', 1171, 419, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:75', 000057, 'INT-REF-IZQ-3-ILU-SUR-3', 1016, 419, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:74', 000057, 'INT-REF-IZQ-2-ILU-SUR-3', 861, 419, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:73', 000057, 'INT-REF-IZQ-1-ILU-SUR-3', 705, 419, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:72', 000057, 'INT-NOC-IZQ-2-ILU-SUR-3', 550, 419, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:71', 000057, 'INT-NOC-IZQ-1-ILU-SUR-3', 395, 419, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:70', 000057, 'INT-CREP-IZQ-ILU-SUR-3', 239, 419, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:69', 000057, 'INT-MANI-ILU-SUR-3', 84, 419, false, NULL, true, false, NULL, true),
(90705700, 'Element:57:67', 000057, 'INT-Q26-ILU-SUR-3', 937, 256, false, NULL, true, false, NULL, true),
(90708400, 'Element:84:106', 00008401, 'EXT-ILU-SUR-3', 1783, 545, false, NULL, true, false, NULL, true),
(90708400, 'Element:84:105', 00008401, 'REF-DER-3-ILU-SUR-3', 1629, 545, false, NULL, true, false, NULL, true),
(90708400, 'Element:84:104', 00008401, 'REF-DER-2-ILU-SUR-3', 1475, 545, false, NULL, true, false, NULL, true),
(90708400, 'Element:84:103', 00008401, 'REF-DER-1-ILU-SUR-3', 1319, 545, false, NULL, true, false, NULL, true),
(90708400, 'Element:84:102', 00008401, 'CREP-DER-ILU-SUR-3', 1165, 545, false, NULL, true, false, NULL, true),
(90708400, 'Element:84:101', 00008401, 'REF-IZQ-3-ILU-SUR-3', 1008, 545, false, NULL, true, false, NULL, true),
(90708400, 'Element:84:100', 00008401, 'REF-IZQ-2-ILU-SUR-3', 854, 545, false, NULL, true, false, NULL, true),
(90708400, 'Element:84:99', 00008401, 'REF-IZQ-1-ILU-SUR-3', 697, 545, false, NULL, true, false, NULL, true),
(90708400, 'Element:84:98', 00008401, 'NOC-IZQ-2-ILU-SUR-3', 542, 545, false, NULL, true, false, NULL, true),
(90708400, 'Element:84:97', 00008401, 'NOC-IZQ-1-ILU-SUR-3', 387, 545, false, NULL, true, false, NULL, true),
(90708400, 'Element:84:96', 00008401, 'CREP-IZQ-ILU-SUR-3', 231, 545, false, NULL, true, false, NULL, true);
