-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(922, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_SUR_DP1', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_SUR_DP1', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_SUR_DP1', NULL, 'img/synoptics/unifilar_sai_sur_dp1.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92205700, 922, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(63, 922, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(64, 922, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(65, 922, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92205700, 'Element:57:270', 00005702, 'INT-K01-ILU-SUR-1', 927, 317, false, NULL, true, false, NULL, true),
(92205700, 'Element:57:322', 000057, 'INT-EMER-PERM-DER-SAI-SUR-1', 1669, 766, false, NULL, true, false, NULL, true),
(92205700, 'Element:57:321', 000057, 'INT-RED-FLUJO-SAI-SUR-1', 1669, 418, false, NULL, true, false, NULL, true),
(92205700, 'Element:57:320', 000057, 'INT-AF-SV-SAI-SUR-1', 1424, 418, false, NULL, true, false, NULL, true),
(92205700, 'Element:57:319', 000057, 'INT-BAL-SAI-SUR-1', 1181, 418, false, NULL, true, false, NULL, true),
(92205700, 'Element:57:318', 000057, 'INT-ETD-SAI-SUR-1', 937, 418, false, NULL, true, false, NULL, true),
(92205700, 'Element:57:317', 000057, 'INT-SMF-STOP-SAI-SUR-1', 692, 418, false, NULL, true, false, NULL, true),
(92205700, 'Element:57:316', 000057, 'INT-SOS-SAI-SUR-1', 450, 418, false, NULL, true, false, NULL, true),
(92205700, 'Element:57:315', 000057, 'INT-DET-FASE-SAI-SUR-1', 205, 418, false, NULL, true, false, NULL, true),
(92205700, 'Element:57:269', 000057, 'INT-Q1-ILU-SUR-1', 937, 253, false, NULL, true, false, NULL, true);

