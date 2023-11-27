-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(741, 'SYNOPTIC_RV_CT_PLANO', 'SYNOPTIC_RV_CT_PLANO', 'LBL_SYNOPTIC_RV_CT_PLANO', NULL, 'img/synoptics/Ausol_RV_CT_Plano.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(74105500, 741, 'LAYER_CT', 'LBL_LAYER_CT', true),
(74107500, 741, 'LAYER_GE', 'LBL_LAYER_GE', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(42, 741, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(43, 741, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(44, 741, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(74105500, 'Element:55:1', 01020055, 'RV-CITS-1', 752, 950, false, NULL, true, false, NULL, true),
(74105500, 'Element:55:3', 01020055, 'RV-CGBT-1', 1135, 511, false, NULL, true, false, NULL, true),
(74105500, 'Element:55:5', 01020055, 'RV-CPCI-1', 702, 505, false, NULL, true, false, NULL, true),
(74107500, 'Element:75:1', 01010075, 'RV-GEL-1CA', 975, 506, false, NULL, true, false, NULL, true);

