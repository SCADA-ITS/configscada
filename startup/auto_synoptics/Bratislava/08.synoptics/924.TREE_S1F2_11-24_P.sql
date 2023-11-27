-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(924, 'TREE_S1F2_11-24_P', 'TREE_S1F2_11-24_P', 'LBL_TREE_S1F2_11-24_P', NULL, 'img/synoptics/Bratislava_VMS_S1F2_11-24_P.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92407100, 924, 'S1F2_11-24_P', 'LBL_S1F2_11-24_P', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(63, 924, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(64, 924, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(65, 924, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92407100, 'Element:71:102', 050071, 'PSJS419PM', 1186, 782, false, NULL, true, false, NULL, true),
(92407100, 'Element:71:101', 050071, 'PSJS418PM', 1186, 549, false, NULL, true, false, NULL, true),
(92407100, 'Element:71:79', 040071, 'PSJS419PB', 904, 782, false, NULL, true, false, NULL, true),
(92407100, 'Element:71:78', 040071, 'PSJS418PB', 904, 549, false, NULL, true, false, NULL, true),
(92407100, 'Element:71:33', 010071, 'NRJS428P', 904, 959, false, NULL, true, false, NULL, true),
(92407100, 'Element:71:30', 010071, 'NRJS426P', 1125, 882, false, NULL, true, false, NULL, true),
(92407100, 'Element:71:29', 010071, 'NRJS426P_2', 1326, 881, false, NULL, true, false, NULL, true),
(92407100, 'Element:71:27', 010071, 'NRJS425P', 904, 664, false, NULL, true, false, NULL, true),
(92407100, 'Element:71:25', 010071, 'NRJS424P', 904, 440, false, NULL, true, false, NULL, true),
(92407100, 'Element:71:23', 010071, 'NRJS423P', 1125, 356, false, NULL, true, false, NULL, true),
(92407100, 'Element:71:21', 010071, 'NRJS422P', 904, 265, false, NULL, true, false, NULL, true),
(92407100, 'Element:71:19', 010071, 'NRJS420P', 1125, 188, false, NULL, true, false, NULL, true),
(92407100, 'Element:71:17', 010071, 'NRJS419P', 904, 90, false, NULL, true, false, NULL, true),
(92407100, 'Element:71:15', 010071, 'NRJS418P', 1125, 19, false, NULL, true, false, NULL, true);

