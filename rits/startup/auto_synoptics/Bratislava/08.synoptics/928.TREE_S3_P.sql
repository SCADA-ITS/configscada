-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(928, 'TREE_S3_P', 'TREE_S3_P', 'LBL_TREE_S3_P', NULL, 'img/synoptics/Bratislava_VMS_S3_P.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92807100, 928, 'S3_P', 'LBL_S3_P', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(75, 928, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(76, 928, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(77, 928, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92807100, 'Element:71:118', 050071, 'NRSPK16PM', 1159, 713, false, NULL, true, false, NULL, true),
(92807100, 'Element:71:117', 050071, 'NRSPK14PM', 1159, 496, false, NULL, true, false, NULL, true),
(92807100, 'Element:71:114', 050071, 'NRSMP02PM', 390, 12, false, NULL, true, false, NULL, true),
(92807100, 'Element:71:89', 040071, 'NRSPK16PB', 877, 713, false, NULL, true, false, NULL, true),
(92807100, 'Element:71:88', 040071, 'NRSPK14PB', 877, 496, false, NULL, true, false, NULL, true),
(92807100, 'Element:71:85', 040071, 'NRSMP02PB', 108, 12, false, NULL, true, false, NULL, true),
(92807100, 'Element:71:165', 170071, 'NRLPK02PA', 110, 254, false, NULL, true, false, NULL, true),
(92807100, 'Element:71:71', 020071, 'NRLPK19P', 877, 929, false, NULL, true, false, NULL, true),
(92807100, 'Element:71:69', 020071, 'NRLPK17P', 877, 821, false, NULL, true, false, NULL, true),
(92807100, 'Element:71:68', 020071, 'NRLPK15P', 877, 604, false, NULL, true, false, NULL, true),
(92807100, 'Element:71:65', 020071, 'NRLPK11P', 877, 388, false, NULL, true, false, NULL, true),
(92807100, 'Element:71:63', 020071, 'NRLPK08P', 877, 280, false, NULL, true, false, NULL, true),
(92807100, 'Element:71:61', 010071, 'NRLMP01P', 108, 118, false, NULL, true, false, NULL, true),
(92807100, 'Element:71:166', 170071, 'NRLPK02PB', 178, 254, false, NULL, true, false, NULL, true);

