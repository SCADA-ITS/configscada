-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(908, 'SELECTOR_TREE_NODES_SINOPTICOS_UF_CSG', 'SELECTOR_TREE_NODES_SINOPTICOS_UF_CSG', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_UF_CSG', NULL, 'img/synoptics/Ausol_LQ_UF_CSG.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90805700, 908, 'SYNOPTIC_LAYERS_INTERRUPTORES', 'LBL_SYNOPTIC_LAYERS_INTERRUPTORES', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(21, 908, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(22, 908, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(23, 908, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90805700, 'Element:57:121', 010057, 'TUN-MAG-GAL', 979, 382, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:127', 010057, 'TUN-MAGDIF-PRES-GAL', 1557, 512, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:126', 010057, 'TUN-MAGDIF-GF9-COMM', 1227, 512, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:125', 010057, 'TUN-MAGDIF-GF6-GAL', 1062, 512, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:124', 010057, 'TUN-MAGDIF-GE1-LUM', 732, 512, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:123', 010057, 'TUN-MAGDIF-GA2-LUM', 566, 512, false, NULL, true, false, NULL, true),
(90805700, 'Element:57:122', 010057, 'TUN-MAGDIF-GA1-LUM', 401, 512, false, NULL, true, false, NULL, true);

