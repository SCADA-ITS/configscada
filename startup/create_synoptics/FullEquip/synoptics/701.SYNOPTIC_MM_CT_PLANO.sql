-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(701, 'SYNOPTIC_MM_CT_PLANO', 'SYNOPTIC_MM_CT_PLANO', 'LBL_SYNOPTIC_MM_CT_PLANO', NULL, 'img/synoptics/Ausol_MM_CT_Plano.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(70105500, 701, 'LAYER_CT', 'LBL_LAYER_CT', true),
(70107500, 701, 'LAYER_GE', 'LBL_LAYER_GE', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(36, 701, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(37, 701, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(38, 701, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(70105500, 'Element:55:6', 01020055, 'MM-CITS-1', 1314, 16, false, NULL, true, false, NULL, true),
(70105500, 'Element:55:8', 01020055, 'MM-CPCI-1', 1312, 533, false, NULL, true, false, NULL, true),
(70105500, 'Element:55:7', 01020055, 'MM-CGBT-1', 1055, 434, false, NULL, true, false, NULL, true),
(70107500, 'Element:75:2', 01010075, 'MM-GEL-1CA', 914, 433, false, NULL, true, false, NULL, true);

