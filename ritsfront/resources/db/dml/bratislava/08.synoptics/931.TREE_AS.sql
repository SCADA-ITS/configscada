-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(931, 'TREE_AS', 'TREE_AS', 'LBL_SYNOPTIC_AS', NULL, 'img/synoptics/Bratislava_AS_Rovinka.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(93104200, 931, 'NRL', 'LBL_NRL', true),
(93107124, 931, 'VMS', 'LBL_VMS', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(87, 931, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(88, 931, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(89, 931, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(123, 931, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(93104200, 'Element:42:41', 00004200, 'RNRLJSRP2', 1828, 466, false, NULL, true, false, NULL, true),
(93104200, 'Element:42:42', 00004200, 'RNRLJSRL1', 297, 224, false, NULL, true, false, NULL, true),
(93104200, 'Element:42:43', 00004200, 'RNRLJSRL2', 1401, 283, false, NULL, true, false, NULL, true),
(93104200, 'Element:42:40', 00004200, 'RNRLJSRP1', 1828, 407, false, NULL, true, false, NULL, true),
(93104200, 'Element:42:39', 00004200, 'RKDJSRL2', 1401, 92, false, NULL, true, false, NULL, true),
(93104200, 'Element:42:38', 00004200, 'RKDJSRL1', 1401, 31, false, NULL, true, false, NULL, true),
(93104200, 'Element:42:37', 00004200, 'RKDJSRP2', 1476, 898, false, NULL, true, false, NULL, true),
(93104200, 'Element:42:36', 00004200, 'RKDJSRP1', 696, 898, false, NULL, true, false, NULL, true),
(93107124, 'Element:71:177', 240071, 'NRLJSRP2', 1714, 753, false, NULL, true, false, NULL, true),
(93107124, 'Element:71:178', 240071, 'NRLJSRL1', 226, 313, false, NULL, true, false, NULL, true);

