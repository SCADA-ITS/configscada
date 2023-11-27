-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(761, 'SYNOPTIC_STM2_CT_PLANO', 'SYNOPTIC_STM2_CT_PLANO', 'LBL_SYNOPTIC_STM2_CT_PLANO', NULL, 'img/synoptics/Ausol_STM2_CT_Plano.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(76105500, 761, 'LAYER_CT', 'LBL_LAYER_CT', true),
(76107500, 761, 'LAYER_GE', 'LBL_LAYER_GE', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(45, 761, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(46, 761, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(47, 761, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(76105500, 'Element:55:30', 01020055, 'STM2-CGBT-BC', 1280, 489, false, NULL, true, false, NULL, true),
(76105500, 'Element:55:29', 01020055, 'STM2-CITS-BC', 1279, 66, false, NULL, true, false, NULL, true),
(76107500, 'Element:75:16', 01010075, 'STM2-GEL-BC', 1124, 484, false, NULL, true, false, NULL, true);

