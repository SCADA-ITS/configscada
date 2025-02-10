-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(701, 'SELECTOR_TREE_NODES_SINOPTICOS_CENTRAL_NUCLEAR', 'SELECTOR_TREE_NODES_SINOPTICOS_CENTRAL_NUCLEAR', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_CENTRAL_NUCLEAR', NULL, 'img/synoptics/central_nuclear.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(70108600, 701, 'SYNOPTIC_LAYERS_TURBINAS', 'LBL_SYNOPTIC_LAYERS_TURBINAS', true),
(70100022, 701, 'SYNOPTIC_LAYERS_GENERADORES', 'LBL_SYNOPTIC_LAYERS_GENERADORES', true),
(70106300, 701, 'SYNOPTIC_LAYERS_GENERADORES_VAPOR', 'LBL_SYNOPTIC_LAYERS_GENERADORES_VAPOR', true),
(70107500, 701, 'SYNOPTIC_LAYERS_GENERADORES_ELECTRICO', 'LBL_SYNOPTIC_LAYERS_GENERADORES_ELECTRICO', true),
(70106200, 701, 'SYNOPTIC_LAYERS_REACTOR', 'LBL_SYNOPTIC_LAYERS_REACTOR', true),
(70110000, 701, 'SYNOPTIC_LAYERS_BOTONERA', 'LBL_SYNOPTIC_LAYERS_BOTONERA', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(701, 70100022, 70106300),
(701, 70100022, 70107500);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(48, 701, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(49, 701, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(50, 701, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(4001, 701, 3, 'LBL_CMD_MAINTENANCE', 'mdi mdi-wrench',false,104601, NULL,2,true, NULL,false,true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(70108600, 'Element:86:7', 020086, 'PN-BB-VAP', 966, 266, false, NULL, true, false, NULL, true),
(70108600, 'Element:86:6', 020086, 'PN-BB-REF', 333, 628, false, NULL, true, false, NULL, true),
(70108600, 'Element:86:5', 030086, 'PN-BB-RET', 937, 606, false, NULL, true, false, NULL, true),
(70108600, 'Element:86:3', 040086, 'PN-BB-MAR', 1733, 754, false, NULL, true, false, NULL, true),
(70108600, 'Element:86:4', 010086, 'PN-BB-CON', 1356, 749, false, NULL, true, false, NULL, true),
(70106300, 'Element:63:1', 000063, 'PN-GENERADOR', 524, 314, false, NULL, true, false, NULL, true),
(70107500, 'Element:75:2', 000075, 'PN-GRUPO_ELECTROGENO', 1140, 266, false, NULL, true, false, NULL, true),
(70106200, 'Element:62:1', 000062, 'PN-REACTOR', 27, 394, false, NULL, true, false, NULL, true),
(70110000, 'Element:100:1', 000100, 'PN-BOTONERA', 60, 60, false, NULL, true, false, NULL, true);

