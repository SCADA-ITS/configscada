-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(721, 'SYNOPTIC_NG_CT_PLANO', 'SYNOPTIC_NG_CT_PLANO', 'LBL_SYNOPTIC_NG_CT_PLANO', NULL, 'img/synoptics/Ausol_NG_CT_Plano.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(72105500, 721, 'LAYER_CT', 'LBL_LAYER_CT', true),
(72107500, 721, 'LAYER_GE', 'LBL_LAYER_GE', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(39, 721, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(40, 721, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(41, 721, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(72105500, 'Element:55:31', 01020055, 'NG-CITS-BM', 1686, 110, false, NULL, true, false, NULL, true),
(72105500, 'Element:55:33', 01020055, 'NG-CGBT-BM', 1390, 71, false, NULL, true, false, NULL, true),
(72105500, 'Element:55:32', 01020055, 'NG-CPCI-BM', 1232, 13, false, NULL, true, false, NULL, true),
(72105500, 'Element:55:34', 01020055, 'NG-CITS-BC', 352, 545, false, NULL, true, false, NULL, true),
(72107500, 'Element:75:18', 01010075, 'NG-GEL-1BM', 1522, 7, false, NULL, true, false, NULL, true);

