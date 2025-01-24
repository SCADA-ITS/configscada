-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(926, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_SUR_DP2', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_SUR_DP2', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_SAI_SUR_DP2', NULL, 'img/synoptics/unifilar_sai_sur_dp2.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92605700, 926, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(75, 926, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(76, 926, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(77, 926, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92605700, 'Element:57:364', 000057, 'INT-EMER-PERM-DER-SAI-SUR-2', 1548, 766, false, NULL, true, false, NULL, true),
(92605700, 'Element:57:343', 000057, 'INT-RED-FLUJO-SAI-NOR-SUR-2', 1547, 417, false, NULL, true, false, NULL, true),
(92605700, 'Element:57:362', 000057, 'INT-GA4-SAI-SUR-2', 1303, 417, false, NULL, true, false, NULL, true),
(92605700, 'Element:57:361', 000057, 'INT-DOMO-SAI-SUR-2', 1059, 417, false, NULL, true, false, NULL, true),
(92605700, 'Element:57:360', 000057, 'INT-SOS-2-SAI-SUR-2', 815, 417, false, NULL, true, false, NULL, true),
(92605700, 'Element:57:359', 000057, 'INT-SOS-1-SAI-SUR-2', 571, 417, false, NULL, true, false, NULL, true),
(92605700, 'Element:57:358', 000057, 'INT-CIRC-1D-SAI-SUR-2', 328, 417, false, NULL, true, false, NULL, true),
(92605700, 'Element:57:356', 000057, 'INT-Q1-SAI-SUR-2', 937, 253, false, NULL, true, false, NULL, true),
(92605700, 'Element:57:336', 00005702, 'INT-K01-SAI-NOR-SUR-2', 929, 314, false, NULL, true, false, NULL, true);

