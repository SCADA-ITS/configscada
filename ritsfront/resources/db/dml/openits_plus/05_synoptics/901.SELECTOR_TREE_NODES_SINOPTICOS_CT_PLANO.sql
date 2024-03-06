-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(901, 'SELECTOR_TREE_NODES_SINOPTICOS_CT_PLANO', 'SELECTOR_TREE_NODES_SINOPTICOS_CT_PLANO', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_CT_PLANO', NULL, 'img/synoptics/Ausol_LQ_CT_Plano.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90105500, 901, 'SYNOPTIC_LAYERS_LOCALES_TECNICOS', 'LBL_SYNOPTIC_LAYERS_LOCALES_TECNICOS', true),
(90107500, 901, 'SYNOPTIC_LAYERS_GRUPOS_ELECTRÓGENOS', 'LBL_SYNOPTIC_LAYERS_GRUPOS_ELECTRÓGENOS', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(0, 901, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(1, 901, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(2, 901, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90105500, 'Element:55:3', 000055, 'TUN-PCI-1', 1097, 823, false, NULL, true, false, NULL, true),
(90105500, 'Element:55:2', 000055, 'TUN-CITS-1', 881, 824, false, NULL, true, false, NULL, true),
(90105500, 'Element:55:1', 000055, 'TUN-CGBT-1', 774, 825, false, NULL, true, false, NULL, true),
(90107500, 'Element:75:1', 000075, 'TUN-GEL-1MA', 989, 825, false, NULL, true, false, NULL, true);

