-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(926, 'TREE_S2_P', 'TREE_S2_P', 'LBL_SYNOPTIC_S2_P', NULL, 'img/synoptics/Bratislava_VMS_S2_P.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92607100, 926, 'S2_P', 'LBL_SYNOPTIC_S2_P', true),
(92602300, 926, 'INCIDENT', 'LBL_INCIDENT', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(69, 926, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(70, 926, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(71, 926, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(118, 926, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92607100, 'Element:71:55', 020071, 'NRSR432P', 904, 289, false, NULL, true, false, NULL, true),
(92607100, 'Element:71:54', 020071, 'NRSR431P', 904, 181, false, NULL, true, false, NULL, true),
(92607100, 'Element:71:58', 010071, 'NRSR434P', 904, 398, false, NULL, true, false, NULL, true),
(92602300, 'Element:23:1', 000023, 'Imanager', 1764, 83, false, NULL, true, false, NULL, true);

