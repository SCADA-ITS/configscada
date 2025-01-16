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
(92305700, 'Element:57:332', 00005702, 'INT-ST-VENT-4-DP1', 1415, 666, false, NULL, true, false, NULL, true),
(92305700, 'Element:57:326', 00005702, 'INT-ST-VENT-2-DP1', 1413, 145, false, NULL, true, false, NULL, true),
(92305700, 'Element:57:329', 00005702, 'INT-ST-VENT-3-DP1', 438, 666, false, NULL, true, false, NULL, true),
(92305700, 'Element:57:323', 00005702, 'INT-ST-VENT-1-DP1', 437, 144, false, NULL, true, false, NULL, true),
(92305700, 'Element:57:331', 000057, 'INT-VENT-1-06-DP1', 556, 768, false, NULL, true, false, NULL, true),
(92305700, 'Element:57:330', 000057, 'INT-VENT-1-05-DP1', 341, 768, false, NULL, true, false, NULL, true),
(92305700, 'Element:57:324', 000057, 'INT-VENT-1-01-DP1', 342, 247, false, NULL, true, false, NULL, true),
(92305700, 'Element:57:325', 000057, 'INT-VENT-1-02-DP1', 556, 247, false, NULL, true, false, NULL, true),
(92305700, 'Element:57:328', 000057, 'INT-VENT-1-04-DP1', 1533, 247, false, NULL, true, false, NULL, true),
(92305700, 'Element:57:327', 000057, 'INT-VENT-1-03-DP1', 1318, 247, false, NULL, true, false, NULL, true),
(92305700, 'Element:57:333', 000057, 'INT-VENT-1-07-DP1', 1318, 768, false, NULL, true, false, NULL, true),
(92305700, 'Element:57:334', 000057, 'INT-VENT-1-08-DP1', 1533, 768, false, NULL, true, false, NULL, true);

