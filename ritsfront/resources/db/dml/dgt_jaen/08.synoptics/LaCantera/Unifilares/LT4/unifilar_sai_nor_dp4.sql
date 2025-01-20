-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(928, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_NOR_DP4', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_NOR_DP4', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_NOR_DP4', NULL, 'img/synoptics/unifilar_sai_nor_dp4.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92805700, 928, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(81, 928, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(82, 928, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(83, 928, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92805700, 'Element:57:374', 00005702, 'INT-K01-SAI-NOR-4', 927, 317, false, NULL, true, false, NULL, true),
(92805700, 'Element:57:373', 000057, 'INT-Q1-SAI-NOR-4', 937, 253, false, NULL, true, false, NULL, true),
(92805700, 'Element:57:375', 000057, 'INT-CIC-1A-SAI-NOR-4', 328, 417, false, NULL, true, false, NULL, true),
(92805700, 'Element:57:376', 000057, 'INT-CIRC-1-SAI-NOR-4', 571, 417, false, NULL, true, false, NULL, true),
(92805700, 'Element:57:377', 000057, 'INT-CIRC-2-SAI-NOR-4', 816, 417, false, NULL, true, false, NULL, true),
(92805700, 'Element:57:378', 000057, 'INT-CIRC-3-SAI-NOR-4', 1058, 417, false, NULL, true, false, NULL, true),
(92805700, 'Element:57:379', 000057, 'INT-RES-SAI-NOR-4', 1302, 417, false, NULL, true, false, NULL, true),
(92805700, 'Element:57:380', 000057, 'INT-RED-FLUJO-EMER-PERM-DER-SAI-NOR-4', 1547, 417, false, NULL, true, false, NULL, true);

