-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(917, 'TREE_RTMS', 'TREE_RTMS', 'LBL_TREE_RTMS', NULL, 'img/synoptics/Bratislava_RTMS.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91709700, 917, 'RTMS', 'LBL_RTMS', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(48, 917, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(49, 917, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(50, 917, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91709700, 'Element:97:1', 000097, 'ADPJ12P+5,148', 459, 860, false, NULL, true, false, NULL, true),
(91709700, 'Element:97:3', 000097, 'ADPJ14P+6,438', 459, 780, false, NULL, true, false, NULL, true),
(91709700, 'Element:97:5', 000097, 'ADPJ16P+7,558', 459, 700, false, NULL, true, false, NULL, true),
(91709700, 'Element:97:2', 000097, 'ADPJ13L+5,148', 172, 537, false, NULL, true, false, NULL, true),
(91709700, 'Element:97:4', 000097, 'ADPJ15L+6,438', 172, 459, false, NULL, true, false, NULL, true);

