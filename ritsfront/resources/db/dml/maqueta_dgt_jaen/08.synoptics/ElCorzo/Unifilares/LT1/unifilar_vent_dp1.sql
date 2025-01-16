-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(923, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_VENT_DP1', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_VENT_DP1', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_VENT_DP1', NULL, 'img/synoptics/unifilar_vent_dp1.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92305700, 923, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(66, 923, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(67, 923, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(68, 923, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92305700, 'Element:57:314', 00005700, 'INT-K01-ILU-SUR-1', 437, 144, false, NULL, true, false, NULL, true),
(92305700, 'Element:57:313', 00005701, 'INT-Q1-ILU-SUR-1', 1533, -813, false, NULL, true, false, NULL, true);

