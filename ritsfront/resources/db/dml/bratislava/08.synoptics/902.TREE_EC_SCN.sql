-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(902, 'TREE_EC_SCN', 'TREE_EC_SCN', 'LBL_SYNOPTIC_EC_SCN', NULL, 'img/synoptics/Bratislava_EC_SCN.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90203400, 902, 'SCN', 'LBL_SYNOPTIC_EC_SCN', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(3, 902, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(4, 902, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(5, 902, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(94, 902, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90203400, 'Element:34:16', 000034, 'SCNPK.2', 508, 448, false, NULL, true, false, NULL, true),
(90203400, 'Element:34:15', 000034, 'SCNJS.2', 269, 597, false, NULL, true, false, NULL, true),
(90203400, 'Element:34:14', 000034, 'SCNJS.1', 269, 538, false, NULL, true, false, NULL, true),
(90203400, 'Element:34:13', 000034, 'SCNPK.1', 479, 321, false, NULL, true, false, NULL, true),
(90203400, 'Element:34:12', 000034, 'SCNMP.1', 479, 252, false, NULL, true, false, NULL, true),
(90203400, 'Element:34:6', 000034, 'SCNKD.3', 1753, 585, false, NULL, true, false, NULL, true),
(90203400, 'Element:34:5', 000034, 'SCNKD.2', 1753, 514, false, NULL, true, false, NULL, true),
(90203400, 'Element:34:4', 000034, 'SCNKD.1', 1753, 446, false, NULL, true, false, NULL, true),
(90203400, 'Element:34:3', 000034, 'SCNJS.3', 1528, 585, false, NULL, true, false, NULL, true),
(90203400, 'Element:34:2', 000034, 'SCNJS.4', 1528, 514, false, NULL, true, false, NULL, true),
(90203400, 'Element:34:1', 000034, 'SCNJS.5', 1528, 446, false, NULL, true, false, NULL, true),
(90203400, 'Element:34:7', 000034, 'SCNJS.6', 1093, 342, false, NULL, true, false, NULL, true),
(90203400, 'Element:34:8', 000034, 'SCNJS.7', 1093, 272, false, NULL, true, false, NULL, true),
(90203400, 'Element:34:9', 000034, 'SCNSR.1', 876, 142, false, NULL, true, false, NULL, true),
(90203400, 'Element:34:10', 000034, 'SCNSR.2', 876, 74, false, NULL, true, false, NULL, true),
(90203400, 'Element:34:11', 000034, 'SCNSR.3', 876, 2, false, NULL, true, false, NULL, true);

