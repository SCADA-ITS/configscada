-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(909, 'TREE_PANELES_TARIFARIOS', 'TREE_PANELES_TARIFARIOS', 'LBL_TREE_PANELES_TARIFARIOS', NULL, 'img/synoptics/tarifarios.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90900024, 909, 'CTRL_ACCESOS', 'LBL_CTRL_ACCESOS', true),
(90907100, 909, 'PMV', 'LBL_PMV', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(909, 90900024, 90907100);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(24, 909, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(25, 909, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(26, 909, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90907100, 'Element:71:393', 210071, 'ICCO-PPV-102', 1508, 749, false, NULL, true, false, NULL, true),
(90907100, 'Element:71:394', 210071, 'IBTO-PPV-101', 1508, 956, false, NULL, true, false, NULL, true),
(90907100, 'Element:71:392', 210071, 'IPRO-PPV-104', 1508, 512, false, NULL, true, false, NULL, true),
(90907100, 'Element:71:391', 210071, 'INKO-PPV-1207', 1508, 338, false, NULL, true, false, NULL, true),
(90907100, 'Element:71:390', 210071, 'IVIP-PPV-205', 1508, 172, false, NULL, true, false, NULL, true),
(90907100, 'Element:71:389', 210071, 'S1TN-PPV-110', 1508, 19, false, NULL, true, false, NULL, true),
(90907100, 'Element:71:398', 210071, 'ICCP-PPV-209', 64, 827, false, NULL, true, false, NULL, true),
(90907100, 'Element:71:397', 210071, 'ICNP-PPV-206', 64, 686, false, NULL, true, false, NULL, true),
(90907100, 'Element:71:396', 210071, 'ICEO-PPV-201', 64, 552, false, NULL, true, false, NULL, true),
(90907100, 'Element:71:395', 210071, 'IVEN-PPV-202', 64, 418, false, NULL, true, false, NULL, true);

