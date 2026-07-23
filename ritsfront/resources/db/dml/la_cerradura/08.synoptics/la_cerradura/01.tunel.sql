-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(901, 'SELECTOR_TREE_NODES_SINOPTICO_LA_CERRADURA_TUNNEL', 'SELECTOR_TREE_NODES_SINOPTICO_LA_CERRADURA_TUNNEL', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_LA_CERRADURA_TUNNEL', NULL, 'img/synoptics/tunel_la_cerradura.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90100000, 901, 'SYNOPTIC_LAYERS_CONTROL_DE_ACCESSOS', 'LBL_SYNOPTIC_LAYERS_CONTROL_DE_ACCESSOS', true),
(90100201, 901, 'SYNOPTIC_LAYERS_DOMO_CAMERAS', 'LBL_SYNOPTIC_LAYERS_DOMO_CAMERAS', true),
(90100202, 901, 'SYNOPTIC_LAYERS_DAI_CAMERAS', 'LBL_SYNOPTIC_LAYERS_DAI_CAMERAS', true),
(90101300, 901, 'SYNOPTIC_LAYERS_SEMAFOROS', 'LBL_SYNOPTIC_LAYERS_SEMAFOROS', true),
(90107112, 901, 'SYNOPTIC_LAYERS_PANELES_AF', 'LBL_SYNOPTIC_LAYERS_PANELES_AF', true),
(90100001, 901, 'SYNOPTIC_LAYERS_AMBIENTALES', 'LBL_SYNOPTIC_LAYERS_AMBIENTALES', true),
(90101800, 901, 'SYNOPTIC_LAYERS_ANEMOMETER', 'LBL_SYNOPTIC_LAYERS_ANEMOMETER', true),
(90102000, 901, 'SYNOPTIC_LAYERS_NO', 'LBL_SYNOPTIC_LAYERS_NO', true),
(90101900, 901, 'SYNOPTIC_LAYERS_CO', 'LBL_SYNOPTIC_LAYERS_CO', true),
(90102100, 901, 'SYNOPTIC_LAYERS_OPAC', 'LBL_SYNOPTIC_LAYERS_OPAC', true),
(90105302, 901, 'SYNOPTIC_LAYERS_SEM_2_GRUPOS', 'LBL_SYNOPTIC_LAYERS_SEM_2_GRUPOS', true),
(90101100, 901, 'SYNOPTIC_LAYERS_SOS', 'LBL_SYNOPTIC_LAYERS_SOS', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(901, 90100000, 90100201),
(901, 90100000, 90100202),
(901, 90100000, 90101300),
(901, 90100000, 90107112),
(901, 90100001, 90101800),
(901, 90100001, 90102000),
(901, 90100001, 90101900),
(901, 90100001, 90102100),
(901, 90100000, 90105302);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(0, 901, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(1, 901, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(2, 901, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90100201, 'Element:2:1', 010002, 'TVE-2-01-CE', 268, 556, false, NULL, true, false, NULL, true),
(90100201, 'Element:2:2', 010002, 'TVE-1-01-CE', 1822, 556, false, NULL, true, false, NULL, true),
(90100202, 'Element:2:6', 020002, 'TVI-1-02-CE', 1088, 1008, false, NULL, true, false, NULL, true),
(90100202, 'Element:2:5', 020002, 'TVI-1-01-CE', 622, 1004, false, NULL, true, false, NULL, true),
(90100202, 'Element:2:3', 020002, 'TVI-2-01-CE', 1201, 104, false, NULL, true, false, NULL, true),
(90100202, 'Element:2:4', 020002, 'TVI-2-02-CE', 507, 105, false, NULL, true, false, NULL, true),
(90101300, 'Element:13:5', 000013, 'SMF-1-01-CE', 301, 622, false, NULL, true, false, NULL, true),
(90101300, 'Element:13:6', 000013, 'SMF-1-02-CE', 301, 1005, false, NULL, true, false, NULL, true),
(90101300, 'Element:13:8', 000013, 'SMF-1-04-CE', 456, 1005, false, NULL, true, false, NULL, true),
(90101300, 'Element:13:7', 000013, 'SMF-1-03-CE', 456, 622, false, NULL, true, false, NULL, true),
(90101300, 'Element:13:2', 000013, 'SMF-2-02-CE', 1710, 487, false, NULL, true, false, NULL, true),
(90101300, 'Element:13:1', 000013, 'SMF-2-01-CE', 1710, 104, false, NULL, true, false, NULL, true),
(90101300, 'Element:13:4', 000013, 'SMF-2-04-CE', 1498, 487, false, NULL, true, false, NULL, true),
(90101300, 'Element:13:3', 000013, 'SMF-2-03-CE', 1498, 104, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:3', 120071, 'AF-2-01-CE', 1457, 230, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:4', 120071, 'AF-2-02-CE', 1457, 388, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:2', 120071, 'AF-1-02-CE', 504, 873, false, NULL, true, false, NULL, true),
(90107112, 'Element:71:1', 120071, 'AF-1-01-CE', 504, 729, false, NULL, true, false, NULL, true),
(90101800, 'Element:18:1', 000018, 'AN-1-01-CE', 901, 989, false, NULL, true, false, NULL, true),
(90101800, 'Element:18:2', 000018, 'AN-2-01-CE', 838, 106, false, NULL, true, false, NULL, true),
(90102000, 'Element:20:4', 000020, 'SNO-2-02-CE', 559, 105, false, NULL, true, false, NULL, true),
(90102000, 'Element:20:2', 000020, 'SNO-1-02-CE', 1036, 994, false, NULL, true, false, NULL, true),
(90102000, 'Element:20:1', 000020, 'SNO-1-01-CE', 726, 989, false, NULL, true, false, NULL, true),
(90102000, 'Element:20:3', 000020, 'SNO-2-01-CE', 1097, 104, false, NULL, true, false, NULL, true),
(90101900, 'Element:19:2', 000019, 'SCO-1-02-CE', 985, 994, false, NULL, true, false, NULL, true),
(90101900, 'Element:19:1', 000019, 'SCO-1-01-CE', 674, 989, false, NULL, true, false, NULL, true),
(90101900, 'Element:19:4', 000019, 'SCO-2-02-CE', 611, 105, false, NULL, true, false, NULL, true),
(90101900, 'Element:19:3', 000019, 'SCO-2-01-CE', 1149, 104, false, NULL, true, false, NULL, true),
(90102100, 'Element:21:2', 000021, 'OP-2-01-CE', 786, 104, false, NULL, true, false, NULL, true),
(90102100, 'Element:21:1', 000021, 'OP-1-01-CE', 849, 989, false, NULL, true, false, NULL, true),
(90105302, 'Element:53:2', 020053, 'SMF-AA-1-02-CE', 189, 1005, false, NULL, true, false, NULL, true),
(90105302, 'Element:53:1', 020053, 'SMF-AA-1-01-CE', 189, 622, false, NULL, true, false, NULL, true),
(90105302, 'Element:53:4', 020053, 'SMF-AA-2-02-CE', 1930, 487, false, NULL, true, false, NULL, true),
(90105302, 'Element:53:3', 020053, 'SMF-AA-2-01-CE', 1930, 104, false, NULL, true, false, NULL, true),
(90101100, 'Element:11:6', 000011, 'PSE-1-02-CE', 1465, 1005, false, NULL, true, false, NULL, true),
(90101100, 'Element:11:2', 000011, 'PSI-1-01-CE', 797, 1004, false, NULL, true, false, NULL, true),
(90101100, 'Element:11:5', 000011, 'PSE-1-01-CE', 405, 1005, false, NULL, true, false, NULL, true),
(90101100, 'Element:11:1', 000011, 'PSI-2-01-CE', 890, 106, false, NULL, true, false, NULL, true),
(90101100, 'Element:11:4', 000011, 'PSE-2-02-CE', 164, 106, false, NULL, true, false, NULL, true),
(90101100, 'Element:11:3', 000011, 'PSE-2-01-CE', 1658, 104, false, NULL, true, false, NULL, true);

