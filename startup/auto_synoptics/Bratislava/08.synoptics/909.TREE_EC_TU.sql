-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(909, 'TREE_EC_TU', 'TREE_EC_TU', 'LBL_TREE_EC_TU', NULL, 'img/synoptics/Bratislava_EC_TU.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90904700, 909, 'TU', 'LBL_TU', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(24, 909, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(25, 909, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(26, 909, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90904700, 'Element:47:16', 000047, 'TUJS03P', 193, 650, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:15', 000047, 'TUJS02L', 193, 609, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:14', 000047, 'TUJS01P', 193, 569, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:20', 000047, 'TUPK02L', 193, 385, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:19', 000047, 'TUPK01L', 193, 340, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:7', 000047, 'TUDH05L', 1487, 975, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:6', 000047, 'TUDH04L', 1487, 937, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:5', 000047, 'TUDH03L', 1487, 898, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:4', 000047, 'TUDH02L', 1487, 860, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:3', 000047, 'TUDH01L', 1487, 821, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:2', 000047, 'TUKD02L', 1487, 748, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:1', 000047, 'TUKD01L', 1487, 707, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:11', 000047, 'TUJS07P', 1487, 607, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:10', 000047, 'TUJS06P', 1487, 572, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:9', 000047, 'TUJS05P', 1487, 537, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:8', 000047, 'TUJS04P', 1487, 501, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:13', 000047, 'TUJS09P', 1487, 377, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:12', 000047, 'TUJS08P', 1487, 337, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:18', 000047, 'TUSR02P', 1499, 118, false, NULL, true, false, NULL, true),
(90904700, 'Element:47:17', 000047, 'TUSR01P', 1499, 78, false, NULL, true, false, NULL, true);

