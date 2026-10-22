-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(905, 'TREE_EC_NRS', 'TREE_EC_NRS', 'LBL_SYNOPTIC_EC_NRS', NULL, 'img/synoptics/Bratislava_EC_NRS.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90504300, 905, 'NRS', 'LBL_SYNOPTIC_EC_NRS', true),
(90502300, 905, 'INCIDENT', 'LBL_INCIDENT', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(12, 905, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(13, 905, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(14, 905, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(97, 905, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90504300, 'Element:43:2', 000043, 'RNRKD04L', 1029, 572, false, NULL, true, false, NULL, true),
(90504300, 'Element:43:1', 000043, 'RNRKD02L', 1029, 527, false, NULL, true, false, NULL, true),
(90504300, 'Element:43:4', 000043, 'RNRJS20P', 851, 572, false, NULL, true, false, NULL, true),
(90504300, 'Element:43:3', 000043, 'RNRSPS405JSPP', 851, 527, false, NULL, true, false, NULL, true),
(90504300, 'Element:43:9', 000043, 'RNRPK22P', 211, 381, false, NULL, true, false, NULL, true),
(90504300, 'Element:43:7', 000043, 'RNRPK20L', 211, 345, false, NULL, true, false, NULL, true),
(90504300, 'Element:43:6', 000043, 'RNRPK02L', 211, 309, false, NULL, true, false, NULL, true),
(90504300, 'Element:43:8', 000043, 'RNRPK21P', 211, 272, false, NULL, true, false, NULL, true),
(90502300, 'Element:23:1', 000023, 'Imanager', 1842, 27, false, NULL, true, false, NULL, true);

