-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(924, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_NOR_DP2', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_NOR_DP2', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_NOR_DP2', NULL, 'img/synoptics/unifilar_sai_nor_dp2.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92405700, 924, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(69, 924, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(70, 924, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(71, 924, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92405700, 'Element:57:336', 00005702, 'INT-K01-SAI-NOR-SUR-2', 929, 315, false, NULL, true, false, NULL, true),
(92405700, 'Element:57:337', 000057, 'INT-BAL-SAI-NOR-2', 206, 417, false, NULL, true, false, NULL, true),
(92405700, 'Element:57:338', 000057, 'INT-CIRC-1B-SAI-NOR-2', 450, 417, false, NULL, true, false, NULL, true),
(92405700, 'Element:57:339', 000057, 'INT-CIRC-1C-SAI-NOR-2', 693, 417, false, NULL, true, false, NULL, true),
(92405700, 'Element:57:335', 000057, 'INT-Q1-SAI-NOR-2', 938, 254, false, NULL, true, false, NULL, true),
(92405700, 'Element:57:340', 000057, 'INT-SOS-1-SAI-NOR-2', 937, 417, false, NULL, true, false, NULL, true),
(92405700, 'Element:57:341', 000057, 'INT-SOS-2-SAI-NOR-2', 1181, 417, false, NULL, true, false, NULL, true),
(92405700, 'Element:57:344', 000057, 'INT-EMER-PERM-DER-1-SAI-NOR-2', 1578, 766, false, NULL, true, false, NULL, true),
(92405700, 'Element:57:342', 000057, 'INT-GA3-SAI-NOR-2', 1425, 417, false, NULL, true, false, NULL, true),
(92405700, 'Element:57:343', 000057, 'INT-RED-FLUJO-SAI-NOR-SUR-2', 1669, 417, false, NULL, true, false, NULL, true),
(92405700, 'Element:57:345', 000057, 'INT-EMER-PERM-DER-2-SAI-NOR-2', 1760, 766, false, NULL, true, false, NULL, true);

