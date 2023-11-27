-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(925, 'TREE_S2_L', 'TREE_S2_L', 'LBL_SYNOPTIC_S2_L', NULL, 'img/synoptics/Bratislava_VMS_S2_L.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(92507100, 925, 'S2_L', 'LBL_SYNOPTIC_S2_L', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(66, 925, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(67, 925, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(68, 925, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(117, 925, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(92507100, 'Element:71:113', 050071, 'PSSR479LM', 549, 486, false, NULL, true, false, NULL, true),
(92507100, 'Element:71:112', 050071, 'PSSR480LM', 549, 275, false, NULL, true, false, NULL, true),
(92507100, 'Element:71:84', 040071, 'PSSR479LB', 267, 486, false, NULL, true, false, NULL, true),
(92507100, 'Element:71:83', 040071, 'PSSR480LB', 267, 275, false, NULL, true, false, NULL, true),
(92507100, 'Element:71:56', 020071, 'NRSR467L', 266, 377, false, NULL, true, false, NULL, true),
(92507100, 'Element:71:57', 020071, 'NRSR466L', 266, 609, false, NULL, true, false, NULL, true),
(92507100, 'Element:71:60', 010071, 'NRSR463L', 266, 830, false, NULL, true, false, NULL, true),
(92507100, 'Element:71:59', 010071, 'NRSR464L', 266, 721, false, NULL, true, false, NULL, true);

