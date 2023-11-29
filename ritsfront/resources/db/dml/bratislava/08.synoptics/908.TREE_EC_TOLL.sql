-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(908, 'TREE_EC_TOLL', 'TREE_EC_TOLL', 'LBL_SYNOPTIC_EC_TOLL', NULL, 'img/synoptics/Bratislava_EC_TOLL.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90804600, 908, 'TOLL', 'LBL_SYNOPTIC_EC_TOLL', true),
(90802300, 908, 'INCIDENT', 'LBL_INCIDENT', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(21, 908, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(22, 908, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(23, 908, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(100, 908, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90804600, 'Element:46:13', 000046, 'MBOPK01L', 1127, 88, false, NULL, true, false, NULL, true),
(90804600, 'Element:46:12', 000046, 'MBOSR02P', 1127, 124, false, NULL, true, false, NULL, true),
(90804600, 'Element:46:4', 000046, 'MBODH02L', 1122, 784, false, NULL, true, false, NULL, true),
(90804600, 'Element:46:3', 000046, 'MBODH01L', 1122, 748, false, NULL, true, false, NULL, true),
(90804600, 'Element:46:2', 000046, 'MBOKD01L', 1122, 685, false, NULL, true, false, NULL, true),
(90804600, 'Element:46:1', 000046, 'MBNKD01', 1122, 649, false, NULL, true, false, NULL, true),
(90804600, 'Element:46:5', 000046, 'MBOJS03P', 1122, 597, false, NULL, true, false, NULL, true),
(90804600, 'Element:46:7', 000046, 'MBNJS01P', 1122, 507, false, NULL, true, false, NULL, true),
(90804600, 'Element:46:8', 000046, 'MBOJS05P', 1122, 471, false, NULL, true, false, NULL, true),
(90804600, 'Element:46:6', 000046, 'MBOJS04P', 1122, 434, false, NULL, true, false, NULL, true),
(90804600, 'Element:46:11', 000046, 'MBOSR01P', 198, 626, false, NULL, true, false, NULL, true),
(90804600, 'Element:46:10', 000046, 'MBOJS02P', 198, 590, false, NULL, true, false, NULL, true),
(90804600, 'Element:46:9', 000046, 'MBOJS01L', 198, 554, false, NULL, true, false, NULL, true),
(90802300, 'Element:23:1', 000023, 'Imanager', 1831, 29, false, NULL, true, false, NULL, true);

