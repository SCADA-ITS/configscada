-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(905, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_2_NORTE', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_2_NORTE', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_ILUMINACION_2_NORTE', NULL, 'img/synoptics/unifilar_ilum_2_norte.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90505700, 905, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true),
(90508400, 905, 'SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', 'LBL_SYNOPTIC_LAYERS_CIRCUITO_ELECTRICO', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(9, 905, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(10, 905, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90505700, 'Element:57:29', 00005702, 'INT-K02-ILU-NOR-2', 928, 314, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:41', 000057, 'INT-EXT-ILU-NOR-2', 1790, 418, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:40', 000057, 'INT-REF-DER-3-ILU-NOR-2', 1635, 418, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:39', 000057, 'INT-REF-DER-2-ILU-NOR-2', 1480, 418, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:38', 000057, 'INT-REF-DER-1-ILU-NOR-2', 1326, 418, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:37', 000057, 'INT-CREP-DER-ILU-NOR-2', 1171, 418, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:36', 000057, 'INT-REF-IZQ-3-ILU-NOR-2', 1015, 418, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:35', 000057, 'INT-REF-IZQ-2-ILU-NOR-2', 860, 418, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:34', 000057, 'INT-REF-IZQ-1-ILU-NOR-2', 705, 418, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:33', 000057, 'INT-NOC-IZQ-2-ILU-NOR-2', 549, 418, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:32', 000057, 'INT-NOC-IZQ-1-ILU-NOR-2', 394, 418, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:31', 000057, 'INT-CREP-IZQ-ILU-NOR-2', 239, 418, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:28', 000057, 'INT-Q27-ILU-NOR-2', 937, 254, false, NULL, true, false, NULL, true),
(90505700, 'Element:57:30', 000057, 'INT-MANI-ILU-NOR-2', 83, 418, false, NULL, true, false, NULL, true),
(90508400, 'Element:84:73', 00008401, 'EXT-ILU-NOR-2', 1783, 544, false, NULL, true, false, NULL, true),
(90508400, 'Element:84:72', 00008401, 'REF-DER-3-ILU-NOR-2', 1628, 544, false, NULL, true, false, NULL, true),
(90508400, 'Element:84:71', 00008401, 'REF-DER-2-ILU-NOR-2', 1473, 544, false, NULL, true, false, NULL, true),
(90508400, 'Element:84:70', 00008401, 'REF-DER-1-ILU-NOR-2', 1319, 544, false, NULL, true, false, NULL, true),
(90508400, 'Element:84:69', 00008401, 'CREP-DER-ILU-NOR-2', 1163, 544, false, NULL, true, false, NULL, true),
(90508400, 'Element:84:68', 00008401, 'REF-IZQ-3-ILU-NOR-2', 1008, 544, false, NULL, true, false, NULL, true),
(90508400, 'Element:84:67', 00008401, 'REF-IZQ-2-ILU-NOR-2', 854, 544, false, NULL, true, false, NULL, true),
(90508400, 'Element:84:66', 00008401, 'REF-IZQ-1-ILU-NOR-2', 697, 544, false, NULL, true, false, NULL, true),
(90508400, 'Element:84:65', 00008401, 'NOC-IZQ-2-ILU-NOR-2', 542, 544, false, NULL, true, false, NULL, true),
(90508400, 'Element:84:64', 00008401, 'NOC-IZQ-1-ILU-NOR-2', 387, 544, false, NULL, true, false, NULL, true),
(90508400, 'Element:84:63', 00008401, 'CREP-IZQ-ILU-NOR-2', 231, 544, false, NULL, true, false, NULL, true);
