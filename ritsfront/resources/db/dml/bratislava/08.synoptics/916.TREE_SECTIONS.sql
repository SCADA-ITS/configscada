-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(916, 'TREE_SECTIONS', 'TREE_SECTIONS', 'LBL_SYNOPTIC_SECTIONS', NULL, 'img/synoptics/Bratislava_ATC_SECTIONS.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91600400, 916, 'SECTION', 'LBL_SYNOPTIC_SECTIONS', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(45, 916, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(46, 916, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(47, 916, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(108, 916, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91600400, 'Element:4:20', 000004, 'ASDJS04L-7+788', 186, 606, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:8', 000004, 'ASDJS06L-10+086', 392, 541, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:28', 000004, 'ASDPK02L-3+715', 186, 517, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:25', 000004, 'ASDPK01P-1+000', 339, 356, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:27', 000004, 'ASDPK02P-3+715', 426, 448, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:5', 000004, 'ASDDH02P-29+739', 989, 982, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:6', 000004, 'ASDDH02L-29+739', 1436, 982, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:10', 000004, 'ASDJS08L-14+178', 478, 356, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:9', 000004, 'ASDJS07P-14+178', 871, 525, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:7', 000004, 'ASDJS05P-10+086', 871, 648, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:4', 000004, 'ASDDH01L-14+942', 871, 745, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:2', 000004, 'ASDKD01L-8+007', 695, 691, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:1', 000004, 'ASDKD01P-8+007', 554, 745, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:3', 000004, 'ASDDH01P-14+942', 553, 844, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:19', 000004, 'ASDJS03P-7+788', 457, 844, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:17', 000004, 'ASDJS01P-3+534', 306, 745, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:18', 000004, 'ASDJS02L-3+534', 77, 671, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:26', 000004, 'ASDPK01L-1+000', 186, 428, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:11', 000004, 'ASDJS09P-18+695', 871, 362, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:12', 000004, 'ASDJS10L-18+695', 182, 306, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:13', 000004, 'ASDJS11P-24+304', 1047, 157, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:14', 000004, 'ASDJS12P-24+304', 871, 157, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:16', 000004, 'ASDJS14L-24+304', 220, 157, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:15', 000004, 'ASDJS13L-24+304', 46, 157, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:24', 000004, 'ASDSR04L-28+013', 223, 34, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:22', 000004, 'ASDSR02L-25+728', 348, 92, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:23', 000004, 'ASDSR03P-28+013', 590, 34, false, NULL, true, false, NULL, true),
(91600400, 'Element:4:21', 000004, 'ASDSR01P-25+728', 729, 92, false, NULL, true, false, NULL, true);

