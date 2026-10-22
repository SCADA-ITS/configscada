-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(921, 'TREE_S1F2_6-11_L', 'TREE_S1F2_6-11_L', 'LBL_TREE_S1F2_6-11_L', NULL, 'img/synoptics/Bratislava_VMS_S1F2_6-11_L.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92107100, 921, 'S1F2_6-11_L', 'LBL_S1F2_6-11_L', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(54, 921, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(55, 921, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(56, 921, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92107100, 'Element:71:99', 050071, 'PSJS488LM', 813, 456, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:100', 050071, 'PSJS489LM', 1389, 126, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:77', 040071, 'PSJS489LB', 1107, 126, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:76', 040071, 'PSJS488LB', 531, 456, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:149', 140071, 'PDZJS06L', 1107, 232, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:161', 150071, 'PDZJS07L', 1107, 444, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:162', 150071, 'PDZJS08L', 1683, 119, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:164', 150071, 'PDZJS10La', 1772, 266, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:163', 150071, 'PDZJS09La', 1692, 266, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:145', 130071, 'PDZJS10Lb', 1771, 411, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:144', 130071, 'PDZJS09Lb', 1692, 411, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:12', 010071, 'NRJS484L', 1683, 457, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:10', 010071, 'NRJS485L', 1107, 338, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:8', 010071, 'NRJS487L', 1107, 21, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:6', 010071, 'NRJS488L', 531, 227, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:53', 010071, 'NRLJS490L', 156, 430, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:51', 010071, 'NRLJS491L', 157, 328, false, NULL, true, false, NULL, true),
(92107100, 'Element:71:49', 010071, 'NRLJS492L', 155, 220, false, NULL, true, false, NULL, true);

