-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(913, 'TREE_ATCS', 'TREE_ATCS', 'LBL_TREE_ATCS', NULL, 'img/synoptics/Bratislava_ETD_ATC.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91305400, 913, 'ATCs', 'LBL_ATCs', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(36, 913, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(37, 913, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(38, 913, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91305400, 'Element:54:13', 000054, 'ASDPK01L-1+000', 193, 344, false, NULL, true, false, NULL, true),
(91305400, 'Element:54:14', 000054, 'ASDPK02L-3+715', 193, 384, false, NULL, true, false, NULL, true),
(91305400, 'Element:54:9', 000054, 'ASDJS01P-3+534', 193, 587, false, NULL, true, false, NULL, true),
(91305400, 'Element:54:10', 000054, 'ASDJS02P-7+788', 193, 627, false, NULL, true, false, NULL, true),
(91305400, 'Element:54:3', 000054, 'ASDDH02-29+739', 1351, 738, false, NULL, true, false, NULL, true),
(91305400, 'Element:54:2', 000054, 'ASDDH01-14+942', 1351, 698, false, NULL, true, false, NULL, true),
(91305400, 'Element:54:1', 000054, 'ASDKD01-8+007', 1351, 650, false, NULL, true, false, NULL, true),
(91305400, 'Element:54:4', 000054, 'ASDJS03-10+086', 1351, 604, false, NULL, true, false, NULL, true),
(91305400, 'Element:54:5', 000054, 'ASDJS04-14+178', 1351, 504, false, NULL, true, false, NULL, true),
(91305400, 'Element:54:6', 000054, 'ASDJS05-18+695', 1351, 464, false, NULL, true, false, NULL, true),
(91305400, 'Element:54:7', 000054, 'ASDJS06-24+304', 1351, 393, false, NULL, true, false, NULL, true),
(91305400, 'Element:54:8', 000054, 'ASDJS07-24+304', 1351, 353, false, NULL, true, false, NULL, true),
(91305400, 'Element:54:12', 000054, 'ASDSR02P-28+013', 1351, 106, false, NULL, true, false, NULL, true),
(91305400, 'Element:54:11', 000054, 'ASDSR01P-25+728', 1351, 66, false, NULL, true, false, NULL, true);

