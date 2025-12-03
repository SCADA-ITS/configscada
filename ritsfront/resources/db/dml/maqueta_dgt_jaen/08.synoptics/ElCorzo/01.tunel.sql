-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(913, 'SELECTOR_TREE_NODES_SINOPTICO_EL_CORZO_TUNEL', 'SELECTOR_TREE_NODES_SINOPTICO_EL_CORZO_TUNEL', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_EL_CORZO_TUNEL', NULL, 'img/synoptics/tunel_el_corzo.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91300006, 913, 'SYNOPTIC_LAYERS_CONTROL_DE_ACCESSOS', 'LBL_SYNOPTIC_LAYERS_CONTROL_DE_ACCESSOS', true),
(91307111, 913, 'SYNOPTIC_LAYERS_PANELES_CLV', 'LBL_SYNOPTIC_LAYERS_PANELES_CLV', true),
(91307112, 913, 'SYNOPTIC_LAYERS_PANELES_AF', 'LBL_SYNOPTIC_LAYERS_PANELES_AF', true),
(91300007, 913, 'SYNOPTIC_LAYERS_VENTILACION', 'LBL_SYNOPTIC_LAYERS_VENTILACION', true),
(91300904, 913, 'SYNOPTIC_LAYERS_JETFAN', 'LBL_SYNOPTIC_LAYERS_JETFAN', true),
(91300201, 913, 'SYNOPTIC_LAYERS_DOMO_CAMERAS', 'LBL_SYNOPTIC_LAYERS_DOMO_CAMERAS', true),
(91300202, 913, 'SYNOPTIC_LAYERS_DAI_CAMERAS', 'LBL_SYNOPTIC_LAYERS_DAI_CAMERAS', true),
(91301100, 913, 'SYNOPTIC_LAYERS_SOS', 'LBL_SYNOPTIC_LAYERS_SOS', true),
(91301300, 913, 'SYNOPTIC_LAYERS_SEMAFOROS', 'LBL_SYNOPTIC_LAYERS_SEMAFOROS', true),
(91301700, 913, 'SYNOPTIC_LAYERS_GALIBO', 'LBL_SYNOPTIC_LAYERS_GALIBO', true),
(91305302, 913, 'SYNOPTIC_LAYERS_SEM_2_GRUPOS', 'LBL_SYNOPTIC_LAYERS_SEM_2_GRUPOS', true),
(91300800, 913, 'SYNOPTIC_LAYERS_BARRIER', 'LBL_SYNOPTIC_LAYERS_BARRIER', true),
(91307122, 913, 'SYNOPTIC_LAYERS_PANELES_GR_TXT_GR', 'LBL_SYNOPTIC_LAYERS_PANELES_GR_TXT_GR', true),
(91307126, 913, 'SYNOPTIC_LAYERS_PANELES_STOP', 'LBL_SYNOPTIC_LAYERS_PANELES_STOP', true),
(91307127, 913, 'SYNOPTIC_LAYERS_PANELES_GALIBO', 'LBL_SYNOPTIC_LAYERS_PANELES_GALIBO', true),
(91300008, 913, 'SYNOPTIC_LAYERS_AMBIENTALES', 'LBL_SYNOPTIC_LAYERS_AMBIENTALES', true),
(91300105, 913, 'SYNOPTIC_LAYERS_METEO', 'LBL_SYNOPTIC_LAYERS_METEO', true),
(91301800, 913, 'SYNOPTIC_LAYERS_ANEMOMETER', 'LBL_SYNOPTIC_LAYERS_ANEMOMETER', true),
(91301900, 913, 'SYNOPTIC_LAYERS_CO', 'LBL_SYNOPTIC_LAYERS_CO', true),
(91302100, 913, 'SYNOPTIC_LAYERS_OPAC', 'LBL_SYNOPTIC_LAYERS_OPAC', true),
(91301400, 913, 'SYNOPTIC_LAYERS_LUMINANCIMETER', 'LBL_SYNOPTIC_LAYERS_LUMINANCIMETER', true),
(91301500, 913, 'SYNOPTIC_LAYERS_FIRE_DET', 'LBL_SYNOPTIC_LAYERS_FIRE_DET', true),
(91300402, 913, 'SYNOPTIC_LAYERS_ETD', 'LBL_SYNOPTIC_LAYERS_ETD', true),
(91300004, 913, 'SYNOPTIC_LAYERS_ILUM', 'LBL_SYNOPTIC_LAYERS_ILUM', true),
(91302000, 913, 'SYNOPTIC_LAYERS_ILUM_REG', 'LBL_SYNOPTIC_LAYERS_ILUM_REG', true);



INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(913, 91300006, 91307111),
(913, 91300006, 91307112),
(913, 91300007, 91300904),
(913, 91300006, 91300201),
(913, 91300006, 91300202),
(913, 91300006, 91301300),
(913, 91300006, 91301700),
(913, 91300006, 91305302),
(913, 91300006, 91300800),
(913, 91300006, 91307122),
(913, 91300006, 91307126),
(913, 91300006, 91307127),
(913, 91300008, 91300105),
(913, 91300008, 91301800),
(913, 91300008, 91301900),
(913, 91300008, 91302100),
(913, 91300008, 91301400),
(913, 91300004, 91302000);


INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(36, 913, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(37, 913, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(38, 913, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91307111, 'Element:71:85', 11007108, 'SV-1-01-CZ-D', 467, 886, false, NULL, true, false, NULL, true),
(91307111, 'Element:71:86', 11007108, 'SV-1-01-CZ-C', 467, 782, false, NULL, true, false, NULL, true),
(91307111, 'Element:71:87', 11007108, 'SV-1-01-CZ-I', 467, 678, false, NULL, true, false, NULL, true),
(91307111, 'Element:71:89', 11007108, 'SV-2-01-CZ-I', 1070, 411, false, NULL, true, false, NULL, true),
(91307111, 'Element:71:88', 11007108, 'SV-2-01-CZ-D', 1070, 263, false, NULL, true, false, NULL, true),
(91307112, 'Element:71:80', 12007106, 'AF-1-01-CZ-D', 467, 937, false, NULL, true, false, NULL, true),
(91307112, 'Element:71:81', 12007106, 'AF-1-01-CZ-C', 467, 834, false, NULL, true, false, NULL, true),
(91307112, 'Element:71:82', 12007106, 'AF-1-01-CZ-I', 467, 730, false, NULL, true, false, NULL, true),
(91307112, 'Element:71:84', 12007106, 'AF-2-01-CZ-I', 1070, 360, false, NULL, true, false, NULL, true),
(91307112, 'Element:71:83', 12007106, 'AF-2-01-CZ-D', 1070, 211, false, NULL, true, false, NULL, true),
(91300904, 'Element:9:44', 04000902, 'VEN-1-08-CZ', 987, 758, true, NULL, true, false, NULL, true),
(91300904, 'Element:9:42', 04000902, 'VEN-1-06-CZ', 833, 758, true, NULL, true, false, NULL, true),
(91300904, 'Element:9:40', 04000902, 'VEN-1-04-CZ', 670, 758, true, NULL, true, false, NULL, true),
(91300904, 'Element:9:38', 04000902, 'VEN-1-02-CZ', 516, 758, true, NULL, true, false, NULL, true),
(91300904, 'Element:9:43', 04000902, 'VEN-1-07-CZ', 987, 857, true, NULL, true, false, NULL, true),
(91300904, 'Element:9:41', 04000902, 'VEN-1-05-CZ', 833, 857, true, NULL, true, false, NULL, true),
(91300904, 'Element:9:39', 04000902, 'VEN-1-03-CZ', 670, 857, true, NULL, true, false, NULL, true),
(91300904, 'Element:9:37', 04000902, 'VEN-1-01-CZ', 516, 857, true, NULL, true, false, NULL, true),
(91300201, 'Element:2:56', 01000201, 'TVE-2-01-CZ', 1898, 562, false, NULL, true, false, NULL, true),
(91300201, 'Element:2:55', 01000201, 'TVE-1-01-CZ', 21, 562, false, NULL, true, false, NULL, true),
(91300202, 'Element:2:62', 02000202, 'TVI-2-02-CZ', 806, 167, true, NULL, true, false, NULL, true),
(91300202, 'Element:2:61', 02000202, 'TVI-2-01-CZ', 998, 167, true, NULL, true, false, NULL, true),
(91300202, 'Element:2:60', 02000202, 'TVI-1-04-CZ', 1028, 962, false, NULL, true, false, NULL, true),
(91300202, 'Element:2:59', 02000202, 'TVI-1-03-CZ', 847, 962, false, NULL, true, false, NULL, true),
(91300202, 'Element:2:58', 02000202, 'TVI-1-02-CZ', 675, 962, false, NULL, true, false, NULL, true),
(91300202, 'Element:2:57', 02000202, 'TVI-1-01-CZ', 543, 962, false, NULL, true, false, NULL, true),
(91301100, 'Element:11:53', 00001112, 'PSI-1-03-CZ', 1030, 1014, false, NULL, true, false, NULL, true),
(91301100, 'Element:11:56', 00001112, 'PSE-1-02-CZ', 1300, 1014, false, NULL, true, false, NULL, true),
(91301100, 'Element:11:55', 00001112, 'PSE-1-01-CZ', 355, 1014, false, NULL, true, false, NULL, true),
(91301100, 'Element:11:51', 00001112, 'PSI-1-01-CZ', 675, 1014, false, NULL, true, false, NULL, true),
(91301100, 'Element:11:57', 00001112, 'PSE-2-01-CZ', 1182, 117, false, NULL, true, false, NULL, true),
(91301100, 'Element:11:58', 00001112, 'PSE-2-02-CZ', 539, 117, false, NULL, true, false, NULL, true),
(91301100, 'Element:11:54', 00001112, 'PSI-2-01-CZ', 806, 117, false, NULL, true, false, NULL, true),
(91301100, 'Element:11:52', 00001112, 'PSI-1-02-CZ', 849, 1014, false, NULL, true, false, NULL, true),
(91301300, 'Element:13:20', 00001303, 'SMF-2-01-CZ-I', 1462, 392, false, NULL, true, false, NULL, true),
(91301300, 'Element:13:19', 00001303, 'SMF-2-01-CZ-D', 1462, 233, false, NULL, true, false, NULL, true),
(91301300, 'Element:13:23', 00001303, 'SMF-1-01-DP', 1070, 463, false, NULL, true, false, NULL, true),
(91301300, 'Element:13:24', 00001303, 'SMF-1-01-CZ', 467, 626, false, NULL, true, false, NULL, true),
(91301300, 'Element:13:24', 00001303, 'SMF-1-02-DP', 467, 989, false, NULL, true, false, NULL, true),
(91301300, 'Element:13:23', 00001303, 'SMF-2-04-CZ', 1070, 159, false, NULL, true, false, NULL, true),
(91301300, 'Element:13:22', 00001303, 'SMF-2-02-CZ-I', 1343, 392, false, NULL, true, false, NULL, true),
(91301300, 'Element:13:21', 00001303, 'SMF-2-02-CZ-D', 1343, 233, false, NULL, true, false, NULL, true),
(91301700, 'Element:17:2', 00001706, 'GA-2-01-CZ', 1917, 132, false, NULL, true, false, NULL, true),
(91305302, 'Element:53:6', 02005306, 'SMF-2-03-CZ-I', 1204, 367, false, NULL, true, false, NULL, true),
(91305302, 'Element:53:5', 02005306, 'SMF-2-03-CZ-D', 1204, 210, false, NULL, true, false, NULL, true),
(91300800, 'Element:8:4', 00000807, 'BA-2-01-CZ-I', 1204, 419, false, NULL, true, false, NULL, true),
(91300800, 'Element:8:3', 00000807, 'BA-2-01-CZ-D', 1204, 262, false, NULL, true, false, NULL, true),
(91307122, 'Element:71:90', 22007108, 'PMV-2-01-CZ', 1621, 299, false, NULL, true, false, NULL, true),
(91307126, 'Element:71:92', 26007109, 'STP-2-01-CZ-I', 1535, 384, false, NULL, true, false, NULL, true),
(91307126, 'Element:71:91', 26007109, 'STP-2-01-CZ-D', 1535, 213, false, NULL, true, false, NULL, true),
--(91307127, 'Element:71:93', 27007111, 'PG-2-01-CZ', 1844, 111, false, NULL, true, false, NULL, true),
(91300105, 'Element:1:1', 050001, 'EM-1-01-CZ', 1686, 1014, false, NULL, true, false, NULL, true),
(91301800, 'Element:18:9', 00001812, 'AN-1-01-CZ', 847, 853, false, NULL, true, false, NULL, true),
(91301900, 'Element:19:12', 00001909, 'SCO-2-01-CZ', 703, 117, false, NULL, true, false, NULL, true),
(91301900, 'Element:19:11', 00001909, 'SCO-1-01-CZ', 899, 1004, false, NULL, true, false, NULL, true),
(91302100, 'Element:21:11', 00002110, 'OP-1-01-CZ', 950, 1004, false, NULL, true, false, NULL, true),
(91302100, 'Element:21:12', 00002110, 'OP-2-01-CZ', 755, 117, false, NULL, true, false, NULL, true),
(91301400, 'Element:14:4', 00001404, 'LUM-0-04-CZ', 1527, 1003, false, NULL, true, false, NULL, true),
(91301500, 'Element:15:50', 00001513, 'ZI-1-04-CZ', 1087, 689, false, NULL, true, false, NULL, true),
(91301500, 'Element:15:49', 00001513, 'ZI-1-03-CZ', 933, 689, false, NULL, true, false, NULL, true),
(91301500, 'Element:15:48', 00001513, 'ZI-1-02-CZ', 770, 689, false, NULL, true, false, NULL, true),
(91301500, 'Element:15:47', 00001513, 'ZI-1-01-CZ', 616, 689, false, NULL, true, false, NULL, true),
(91301500, 'Element:15:51', 00001513, 'ZI-2-01-CZ', 949, 410, false, NULL, true, false, NULL, true),
(91301500, 'Element:15:52', 00001513, 'ZI-2-02-CZ', 735, 410, false, NULL, true, false, NULL, true),
(91300402, 'Element:4:4', 020004, 'PM-2-1-CZ', 1393, 311, false, NULL, true, false, NULL, true),
(91300402, 'Element:4:3', 020004, 'PM-1-1-CZ', 1393, 807, false, NULL, true, false, NULL, true),
(91302000, 'Element:2000:5', 2000, 'CZ Sentido Madrid', 1157, 807, false, NULL, true, false, NULL, true),
(91302000, 'Element:2000:6', 2000, 'CZ Sentido Córdoba', 744, 242, false, NULL, true, false, NULL, true);



