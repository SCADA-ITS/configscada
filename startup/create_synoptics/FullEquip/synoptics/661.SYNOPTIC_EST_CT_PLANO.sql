-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(661, 'SYNOPTIC_EST_CT_PLANO', 'SYNOPTIC_EST_CT_PLANO', 'LBL_SYNOPTIC_EST_CT_PLANO', NULL, 'img/synoptics/Ausol_EST_CT_Plano.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(66105500, 661, 'LAYER_CT', 'LBL_LAYER_CT', true),
(66107500, 661, 'LAYER_GE', 'LBL_LAYER_GE', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(30, 661, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(31, 661, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(32, 661, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(66105500, 'Element:55:28', 01020055, 'EST-CGBT-1', 322, 879, false, NULL, true, false, NULL, true),
(66105500, 'Element:55:27', 01020055, 'EST-CITS-1', 322, 778, false, NULL, true, false, NULL, true);

