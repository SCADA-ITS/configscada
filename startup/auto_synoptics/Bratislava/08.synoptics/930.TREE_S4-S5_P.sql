-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(930, 'TREE_S4-S5_P', 'TREE_S4-S5_P', 'LBL_TREE_S4-S5_P', NULL, 'img/synoptics/Bratislava_VMS_S4S5_P.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(93007100, 930, 'S4-S5_P', 'LBL_S4-S5_P', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(81, 930, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(82, 930, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(83, 930, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(93007100, 'Element:71:146', 140071, 'PDZDH07P', 343, 778, false, NULL, true, false, NULL, true),
(93007100, 'Element:71:153', 150071, 'PDZDH08P', 343, 909, false, NULL, true, false, NULL, true),
(93007100, 'Element:71:152', 150071, 'PDZDH06P', 123, 909, false, NULL, true, false, NULL, true),
(93007100, 'Element:71:151', 150071, 'PDZDH05P', 123, 778, false, NULL, true, false, NULL, true),
(93007100, 'Element:71:150', 150071, 'PDZDH04aP', 123, 622, false, NULL, true, false, NULL, true),
(93007100, 'Element:71:126', 070071, 'PDZTDH03P', 123, 516, false, NULL, true, false, NULL, true),
(93007100, 'Element:71:124', 070071, 'PDZTDH02P', 123, 284, false, NULL, true, false, NULL, true),
(93007100, 'Element:71:123', 070071, 'PDZTKD01P', 123, 24, false, NULL, true, false, NULL, true),
(93007100, 'Element:71:137', 080071, 'PDZDH28P', 611, 929, false, NULL, true, false, NULL, true),
(93007100, 'Element:71:136', 080071, 'PDZDH27P', 546, 929, false, NULL, true, false, NULL, true),
(93007100, 'Element:71:133', 080071, 'PDZDH24P', 188, 395, false, NULL, true, false, NULL, true),
(93007100, 'Element:71:132', 080071, 'PDZDH23P', 123, 395, false, NULL, true, false, NULL, true),
(93007100, 'Element:71:129', 080071, 'PDZKD02P', 188, 141, false, NULL, true, false, NULL, true),
(93007100, 'Element:71:128', 080071, 'PDZKD01P', 123, 141, false, NULL, true, false, NULL, true),
(93007100, 'Element:71:140', 13007100, 'PDZDH04bP', 252, 706, false, NULL, true, false, NULL, true);

