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

