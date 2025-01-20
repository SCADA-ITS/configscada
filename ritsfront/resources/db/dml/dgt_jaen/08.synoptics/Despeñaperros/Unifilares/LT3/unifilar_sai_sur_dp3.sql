-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(927, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_SUR_DP3', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_SUR_DP3', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_SUR_DP3', NULL, 'img/synoptics/unifilar_sai_sur_dp3.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92705700, 927, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(78, 927, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(79, 927, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(80, 927, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92705700, 'Element:57:366', 00005702, 'INT-K01-SAI-SUR-3', 927, 317, false, NULL, true, false, NULL, true),
(92705700, 'Element:57:372', 000057, 'INT-EMER-PERM-DER-SAI-SUR-3', 1426, 766, false, NULL, true, false, NULL, true),
(92705700, 'Element:57:371', 000057, 'INT-RED-FLUJO-SAI-SUR-3', 1425, 418, false, NULL, true, false, NULL, true),
(92705700, 'Element:57:370', 000057, 'INT-GA2-SAI-SUR-3', 1181, 418, false, NULL, true, false, NULL, true),
(92705700, 'Element:57:369', 000057, 'INT-SOS-4-SAI-SUR-3', 936, 418, false, NULL, true, false, NULL, true),
(92705700, 'Element:57:368', 000057, 'INT-SOS-3-SAI-SUR-3', 694, 418, false, NULL, true, false, NULL, true),
(92705700, 'Element:57:367', 000057, 'INT-BAL-SAI-SUR-3', 449, 418, false, NULL, true, false, NULL, true),
(92705700, 'Element:57:365', 000057, 'INT-Q1-SAI-SUR-3', 937, 254, false, NULL, true, false, NULL, true);

