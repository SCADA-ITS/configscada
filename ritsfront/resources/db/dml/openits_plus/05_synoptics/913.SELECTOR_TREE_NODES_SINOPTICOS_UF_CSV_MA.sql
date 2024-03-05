-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(913, 'SELECTOR_TREE_NODES_SINOPTICOS_UF_CSV_MA', 'SELECTOR_TREE_NODES_SINOPTICOS_UF_CSV_MA', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_UF_CSV_MA', NULL, 'img/synoptics/Ausol_LQ_UF_CSV_MA.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91305700, 913, 'SYNOPTIC_LAYERS_INTERRUPTORES', 'LBL_SYNOPTIC_LAYERS_INTERRUPTORES', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(36, 913, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(37, 913, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(38, 913, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

