-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(906, 'TREE_EC_TFA3D', 'TREE_EC_TFA3D', 'LBL_TREE_EC_TFA3D', NULL, 'img/synoptics/Bratislava_EC_TFA_3D.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90604400, 906, 'TFA_3D', 'LBL_TFA_3D', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(15, 906, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(16, 906, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(17, 906, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90604400, 'Element:44:11', 000044, 'R3DSR01P', 656, 52, false, NULL, true, false, NULL, true),
(90604400, 'Element:44:10', 000044, 'R3DJS02P', 202, 619, false, NULL, true, false, NULL, true),
(90604400, 'Element:44:9', 000044, 'R3DJS01L', 202, 582, false, NULL, true, false, NULL, true),
(90604400, 'Element:44:12', 000044, 'R3DPK01L', 202, 544, false, NULL, true, false, NULL, true),
(90604400, 'Element:44:4', 000044, 'R3DDH03L', 1472, 848, false, NULL, true, false, NULL, true),
(90604400, 'Element:44:3', 000044, 'R3DDH02P', 1472, 813, false, NULL, true, false, NULL, true),
(90604400, 'Element:44:2', 000044, 'R3DDH01L', 1472, 778, false, NULL, true, false, NULL, true),
(90604400, 'Element:44:1', 000044, 'R3DKD01L', 1472, 694, false, NULL, true, false, NULL, true),
(90604400, 'Element:44:6', 000044, 'R3DJS04L', 1472, 621, false, NULL, true, false, NULL, true),
(90604400, 'Element:44:5', 000044, 'R3DJS03P', 1472, 585, false, NULL, true, false, NULL, true),
(90604400, 'Element:44:8', 000044, 'R3DJS05P', 1472, 512, false, NULL, true, false, NULL, true),
(90604400, 'Element:44:7', 000044, 'R3DJS04P', 1472, 471, false, NULL, true, false, NULL, true);

