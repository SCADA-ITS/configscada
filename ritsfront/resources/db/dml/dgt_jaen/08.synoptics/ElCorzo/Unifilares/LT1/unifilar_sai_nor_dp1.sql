-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(921, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_NOR_DP1', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_NOR_DP1', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_NOR_DP1', NULL, 'img/synoptics/unifilar_sai_nor_dp1.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92105700, 921, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(60, 921, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(61, 921, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(62, 921, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92105700, 'Element:57:312', 000057, 'INT-EMER-PERM-DER-SAI-NOR-1', 1817, 765, false, NULL, true, false, NULL, true),
(92105700, 'Element:57:311', 000057, 'INT-RED-FLUJO-SAI-NOR-1', 1816, 418, false, NULL, true, false, NULL, true),
(92105700, 'Element:57:310', 000057, 'INT-PMV-GA-SAI-NOR-1', 1569, 418, false, NULL, true, false, NULL, true),
(92105700, 'Element:57:309', 000057, 'INT-BAL-SAI-NOR-1', 1322, 418, false, NULL, true, false, NULL, true),
(92105700, 'Element:57:308', 000057, 'INT-VOD-METEO-SAI-NOR-1', 1074, 418, false, NULL, true, false, NULL, true),
(92105700, 'Element:57:307', 000057, 'INT-SOS-SAI-NOR-1', 826, 418, false, NULL, true, false, NULL, true),
(92105700, 'Element:57:306', 000057, 'INT-CIRC-1B-SAI-NOR-1', 571, 418, false, NULL, true, false, NULL, true),
(92105700, 'Element:57:305', 000057, 'INT-CIRC-1A-ERU-SAI-NOR-1', 327, 418, false, NULL, true, false, NULL, true),
(92105700, 'Element:57:304', 000057, 'INT-MANI-SAI-NOR-1', 84, 418, false, NULL, true, false, NULL, true),
(92105700, 'Element:57:302', 000057, 'INT-Q1-SAI-NOR-1', 951, 254, false, NULL, true, false, NULL, true),
(92105700, 'Element:57:303', 00005702, 'INT-K01-SAI-NOR-1', 943, 317, false, NULL, true, false, NULL, true);

