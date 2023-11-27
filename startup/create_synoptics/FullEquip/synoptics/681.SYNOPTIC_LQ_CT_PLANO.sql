-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(681, 'SYNOPTIC_LQ_CT_PLANO', 'SYNOPTIC_LQ_CT_PLANO', 'LBL_SYNOPTIC_LQ_CT_PLANO', NULL, 'img/synoptics/Ausol_LQ_CT_Plano.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(68105500, 681, 'LAYER_CT', 'LBL_LAYER_CT', true),
(68107500, 681, 'LAYER_GE', 'LBL_LAYER_GE', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(33, 681, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(34, 681, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(35, 681, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(68105500, 'Element:55:26', 01020055, 'LQ-PCI-1', 1097, 823, false, NULL, true, false, NULL, true),
(68105500, 'Element:55:25', 01020055, 'LQ-CITS-1', 881, 824, false, NULL, true, false, NULL, true),
(68105500, 'Element:55:24', 01020055, 'LQ-CGBT-1', 774, 825, false, NULL, true, false, NULL, true),
(68107500, 'Element:75:4', 01010075, 'LQ-GEL-1MA', 989, 825, false, NULL, true, false, NULL, true);

