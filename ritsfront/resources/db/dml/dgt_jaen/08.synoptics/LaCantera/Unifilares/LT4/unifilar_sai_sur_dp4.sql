-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(929, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_SUR_DP4', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_SUR_DP4', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_SUR_DP4', NULL, 'img/synoptics/unifilar_sai_sur_dp4.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92905700, 929, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(84, 929, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(85, 929, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(86, 929, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92905700, 'Element:57:383', 000057, 'INT-CIRC-1B-SAI-SUR-4', 572, 418, false, NULL, true, false, NULL, true),
(92905700, 'Element:57:384', 000057, 'INT-CIRC-2-DOMO-SAI-SUR-4', 815, 418, false, NULL, true, false, NULL, true),
(92905700, 'Element:57:385', 000057, 'INT-CIRC-2B-SAI-SUR-4', 1059, 418, false, NULL, true, false, NULL, true),
(92905700, 'Element:57:386', 000057, 'INT-RED-FLUJO-EMER-PERM-DER-SAI-SUR-4', 1302, 418, false, NULL, true, false, NULL, true),
(92905700, 'Element:57:381', 000057, 'INT-Q1-SAI-SUR-4', 937, 253, false, NULL, true, false, NULL, true),
(92905700, 'Element:57:382', 00005702, 'INT-K01-SAI-SUR-4', 925, 318, false, NULL, true, false, NULL, true);

