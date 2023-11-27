-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(927, 'TREE_S3_L', 'TREE_S3_L', 'LBL_SYNOPTIC_S3_L', NULL, 'img/synoptics/Bratislava_VMS_S3_L.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92707100, 927, 'S3_L', 'LBL_SYNOPTIC_S3_L', true),
(92702300, 927, 'INCIDENT', 'LBL_INCIDENT', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(72, 927, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(73, 927, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(74, 927, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(119, 927, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92707100, 'Element:71:116', 050071, 'NRSPK09LM', 1154, 521, false, NULL, true, false, NULL, true),
(92707100, 'Element:71:115', 050071, 'NRSPK03LM', 701, 34, false, NULL, true, false, NULL, true),
(92707100, 'Element:71:86', 040071, 'NRSPK03LB', 419, 34, false, NULL, true, false, NULL, true),
(92707100, 'Element:71:87', 040071, 'NRSPK09LB', 872, 521, false, NULL, true, false, NULL, true),
(92707100, 'Element:71:70', 020071, 'NRLPK18L', 872, 941, false, NULL, true, false, NULL, true),
(92707100, 'Element:71:67', 020071, 'NRLPK13L', 872, 833, false, NULL, true, false, NULL, true),
(92707100, 'Element:71:66', 020071, 'NRLPK12L', 872, 729, false, NULL, true, false, NULL, true),
(92707100, 'Element:71:64', 020071, 'NRLPK10L', 872, 626, false, NULL, true, false, NULL, true),
(92707100, 'Element:71:62', 020071, 'NRLPK05L', 872, 411, false, NULL, true, false, NULL, true),
(92707100, 'Element:71:122', 060071, 'NRSPK09LS', 1314, 521, false, NULL, true, false, NULL, true),
(92707100, 'Element:71:121', 060071, 'NRSPK03LS', 861, 34, false, NULL, true, false, NULL, true),
(92702300, 'Element:23:1', 000023, 'Imanager', 1766, 89, false, NULL, true, false, NULL, true);

