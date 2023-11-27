-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(904, 'TREE_EC_NRL', 'TREE_EC_NRL', 'LBL_SYNOPTIC_EC_NRL', NULL, 'img/synoptics/Bratislava_EC_NRL.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90404200, 904, 'NRL', 'LBL_SYNOPTIC_EC_NRL', true),
(90402300, 904, 'INCIDENT', 'LBL_INCIDENT', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(9, 904, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(10, 904, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(11, 904, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(96, 904, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90404200, 'Element:42:9', 000042, 'RPDZDH10L', 1399, 804, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:8', 000042, 'RPDZDH09L', 1399, 768, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:7', 000042, 'RPDZDH08L', 1399, 731, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:6', 000042, 'RPDZDH06P', 1399, 694, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:5', 000042, 'RPDZDH04L', 1399, 658, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:4', 000042, 'RPDZDH03L', 1399, 621, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:3', 000042, 'RPDZDH02L', 1399, 584, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:2', 000042, 'RNRKD07L', 1214, 720, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:1', 000042, 'RNRKD05L', 1214, 681, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:16', 000042, 'RPDZJS06L', 1214, 608, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:15', 000042, 'RPDZJS05L', 1214, 571, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:14', 000042, 'RNRSPSJS489L', 1214, 534, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:13', 000042, 'RPDZJS03P', 1214, 498, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:12', 000042, 'RPDZJS02P', 1214, 461, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:11', 000042, 'RPDZJS01P', 1214, 424, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:10', 000042, 'RNRJS21P', 1214, 388, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:23', 000042, 'RNRJS15P', 500, 955, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:35', 000042, 'RNRJS13P', 500, 919, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:22', 000042, 'RNRJS12P', 500, 883, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:34', 000042, 'RNRJS11P', 500, 847, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:33', 000042, 'RNRJS10P', 500, 811, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:21', 000042, 'RNRJS06L', 500, 775, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:17', 000042, 'RNRJS400', 31, 682, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:20', 000042, 'RNR250_TS-287', 31, 881, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:19', 000042, 'RNR245_TS-277', 31, 847, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:18', 000042, 'RNR244_TS-275', 31, 813, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:32', 000042, 'RNRPK12L', 197, 375, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:31', 000042, 'RNRPK10L', 197, 338, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:30', 000042, 'RNRPK05L', 197, 302, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:29', 000042, 'RNLPK05L', 197, 265, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:28', 000042, 'RNSPK03L', 197, 229, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:27', 000042, 'RNRMP02', 197, 192, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:26', 000042, 'RNRSR04P', 785, 143, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:25', 000042, 'RNRSR467L', 785, 108, false, NULL, true, false, NULL, true),
(90404200, 'Element:42:24', 000042, 'RNRSR02P', 785, 72, false, NULL, true, false, NULL, true),
(90402300, 'Element:23:1', 000023, 'Imanager', 1834, 36, false, NULL, true, false, NULL, true);

