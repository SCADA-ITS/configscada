-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(925, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_NOR_DP3', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_NOR_DP3', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_NOR_DP3', NULL, 'img/synoptics/unifilar_sai_nor_dp3.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92505700, 925, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(72, 925, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(73, 925, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(74, 925, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92505700, 'Element:57:355', 000057, 'INT-EMER-PERM-DER-SAI-NOR-3', 1669, 766, false, NULL, true, false, NULL, true),
(92505700, 'Element:57:354', 000057, 'INT-RED-FLUJO-SAI-NOR-3', 1669, 417, false, NULL, true, false, NULL, true),
(92505700, 'Element:57:353', 000057, 'INT-GA1-SAI-NOR-3', 1425, 417, false, NULL, true, false, NULL, true),
(92505700, 'Element:57:352', 000057, 'INT-SOS-2-SAI-NOR-3', 1180, 417, false, NULL, true, false, NULL, true),
(92505700, 'Element:57:348', 000057, 'INT-CIRC-1B-SAI-NOR-3', 206, 417, false, NULL, true, false, NULL, true),
(92505700, 'Element:57:349', 000057, 'INT-CIRC-1C-SAI-NOR-3', 450, 417, false, NULL, true, false, NULL, true),
(92505700, 'Element:57:350', 000057, 'INT-BAL-SAI-NOR-3', 694, 417, false, NULL, true, false, NULL, true),
(92505700, 'Element:57:351', 000057, 'INT-SOS-1-SAI-NOR-3', 937, 417, false, NULL, true, false, NULL, true),
(92505700, 'Element:57:346', 000057, 'INT-Q1-SAI-NOR-3', 936, 254, false, NULL, true, false, NULL, true),
(92505700, 'Element:57:347', 00005702, 'INT-K01-SAI-NOR-3', 927, 312, false, NULL, true, false, NULL, true);

