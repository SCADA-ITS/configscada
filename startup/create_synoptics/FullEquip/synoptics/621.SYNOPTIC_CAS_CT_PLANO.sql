-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(621, 'SYNOPTIC_CAS_CT_PLANO', 'SYNOPTIC_CAS_CT_PLANO', 'LBL_SYNOPTIC_CAS_CT_PLANO', NULL, 'img/synoptics/Ausol_CAS_CT_Plano.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(62105500, 621, 'LAYER_CT', 'LBL_LAYER_CT', true),
(62107500, 621, 'LAYER_GE', 'LBL_LAYER_GE', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(24, 621, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(25, 621, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(26, 621, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(62105500, 'Element:55:11', 01020055, 'CAS-PCI-BC', 230, 443, false, NULL, true, false, NULL, true),
(62105500, 'Element:55:12', 01020055, 'CAS-CGBT-BC', 230, 552, false, NULL, true, false, NULL, true),
(62105500, 'Element:55:15', 01020055, 'CAS-CITS-BM', 1730, 505, false, NULL, true, false, NULL, true),
(62105500, 'Element:55:14', 01020055, 'CAS-CGBT-BM', 1623, 505, false, NULL, true, false, NULL, true),
(62105500, 'Element:55:13', 01020055, 'CAS-CITS-BC', 334, 552, false, NULL, true, false, NULL, true),
(62107500, 'Element:75:10', 01010075, 'CAS-GEL-BC', 125, 553, false, NULL, true, false, NULL, true),
(62107500, 'Element:75:11', 01010075, 'CAS-GEL-BM', 1516, 505, false, NULL, true, false, NULL, true);

