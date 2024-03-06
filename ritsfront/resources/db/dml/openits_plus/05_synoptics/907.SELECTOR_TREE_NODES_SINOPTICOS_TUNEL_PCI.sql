-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(907, 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL_PCI', 'SELECTOR_TREE_NODES_SINOPTICOS_TUNEL_PCI', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_TUNEL_PCI', NULL, 'img/synoptics/Ausol_LQ_PCI.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90700013, 907, 'SYNOPTIC_LAYERS_DETECCION_INCENDIOS', 'LBL_SYNOPTIC_LAYERS_DETECCION_INCENDIOS', true),
(90700014, 907, 'SYNOPTIC_LAYERS_PCI', 'LBL_SYNOPTIC_LAYERS_PCI', true),
(90702900, 907, 'SYNOPTIC_LAYERS_CENTRAL_LINEAL', 'LBL_SYNOPTIC_LAYERS_CENTRAL_LINEAL', true),
(90706100, 907, 'SYNOPTIC_LAYERS_CENTRAL_PUNTUAL', 'LBL_SYNOPTIC_LAYERS_CENTRAL_PUNTUAL', true),
(90705800, 907, 'SYNOPTIC_LAYERS_DETECTOR_HUMOS', 'LBL_SYNOPTIC_LAYERS_DETECTOR_HUMOS', true),
(90705900, 907, 'SYNOPTIC_LAYERS_PULSADORES', 'LBL_SYNOPTIC_LAYERS_PULSADORES', true),
(90706000, 907, 'SYNOPTIC_LAYERS_SIRENAS', 'LBL_SYNOPTIC_LAYERS_SIRENAS', true),
(90701500, 907, 'SYNOPTIC_LAYERS_ZONAS_INCENDIO', 'LBL_SYNOPTIC_LAYERS_ZONAS_INCENDIO', true),
(90708600, 907, 'SYNOPTIC_LAYERS_BOMBAS', 'LBL_SYNOPTIC_LAYERS_BOMBAS', true),
(90708800, 907, 'SYNOPTIC_LAYERS_SENSORES_PERDIDAS', 'LBL_SYNOPTIC_LAYERS_SENSORES_PERDIDAS', true),
(90708700, 907, 'SYNOPTIC_LAYERS_TANQUE', 'LBL_SYNOPTIC_LAYERS_TANQUE', true),
(90702200, 907, 'SYNOPTIC_LAYERS_VALVULAS', 'LBL_SYNOPTIC_LAYERS_VALVULAS', true);

INSERT INTO ui.synoptic_layer_relationships 
(synoptic_id, parent_layer_id, child_layer_id) VALUES 
(907, 90700013, 90702900),
(907, 90700013, 90706100),
(907, 90700013, 90705800),
(907, 90700013, 90705900),
(907, 90700013, 90706000),
(907, 90700013, 90701500),
(907, 90700014, 90708600),
(907, 90700014, 90708800),
(907, 90700014, 90708700),
(907, 90700014, 90702200);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(18, 907, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(19, 907, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(20, 907, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90702900, 'Element:29:1', 000029, 'LQ-OTS-1', 857, 770, false, NULL, true, false, NULL, true),
(90706100, 'Element:61:1', 000061, 'LQ-CT-FIRE-DET-1', 856, 823, false, NULL, true, false, NULL, true),
(90705800, 'Element:58:3', 000058, 'LQ-CT-PCI-PULS-1', 1324, 814, false, NULL, true, false, NULL, true),
(90705800, 'Element:58:2', 000058, 'LQ-CT-ITS-PULS-1', 688, 768, false, NULL, true, false, NULL, true),
(90705800, 'Element:58:1', 000058, 'LQ-CT-CGBT-PULS-1', 367, 813, false, NULL, true, false, NULL, true),
(90705900, 'Element:59:6', 000059, 'LQ-CT-PCI-HUM-2', 1326, 919, false, NULL, true, false, NULL, true),
(90705900, 'Element:59:5', 000059, 'LQ-CT-PCI-HUM-1', 1196, 919, false, NULL, true, false, NULL, true),
(90705900, 'Element:59:4', 000059, 'LQ-CT-ITS-HUM-2', 817, 792, false, NULL, true, false, NULL, true),
(90705900, 'Element:59:3', 000059, 'LQ-CT-ITS-HUM-1', 742, 791, false, NULL, true, false, NULL, true),
(90705900, 'Element:59:2', 000059, 'LQ-CT-CGBT-HUM-2', 510, 781, false, NULL, true, false, NULL, true),
(90705900, 'Element:59:1', 000059, 'LQ-CT-CGBT-HUM-1', 435, 780, false, NULL, true, false, NULL, true),
(90706000, 'Element:60:1', 000060, 'LQ-CT-CGBT-SIR-1', 575, 816, false, NULL, true, false, NULL, true),
(90706000, 'Element:60:2', 000060, 'LQ-CT-PCI-SIR-1', 1195, 813, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:20', 000015, 'LQ-ZI-9MA', 1348, 429, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:19', 010015, 'LQ-ZI-8MA', 1268, 429, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:18', 010015, 'LQ-ZI-7MA', 1188, 429, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:17', 020015, 'LQ-ZI-6MA', 1120, 429, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:16', 030015, 'LQ-ZI-5MA', 1010, 429, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:15', 030015, 'LQ-ZI-4MA', 900, 429, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:14', 030015, 'LQ-ZI-3MA', 790, 429, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:13', 000015, 'LQ-ZI-2MA', 650, 429, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:12', 040015, 'LQ-ZI-1MA', 490, 429, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:1', 000015, 'LQ-ZI-1CA', 1363, 341, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:2', 010015, 'LQ-ZI-2CA', 1283, 341, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:3', 010015, 'LQ-ZI-3CA', 1203, 341, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:4', 010015, 'LQ-ZI-4CA', 1123, 341, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:5', 010015, 'LQ-ZI-5CA', 1043, 341, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:6', 010015, 'LQ-ZI-6CA', 963, 341, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:7', 010015, 'LQ-ZI-7CA', 883, 341, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:8', 050015, 'LQ-ZI-8CA', 793, 341, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:9', 010015, 'LQ-ZI-9CA', 713, 341, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:10', 050015, 'LQ-ZI-10CA', 623, 341, false, NULL, true, false, NULL, true),
(90701500, 'Element:15:11', 000015, 'LQ-ZI-11CA', 483, 341, false, NULL, true, false, NULL, true),
(90708600, 'Element:86:1', 000086, 'LQ-BEL-1', 1202, 854, false, NULL, true, false, NULL, true),
(90708600, 'Element:86:2', 000086, 'LQ-BDI-1', 1185, 977, false, NULL, true, false, NULL, true),
(90708800, 'Element:88:2', 000088, 'LQ-DETFLJ-PCI-2', 1159, 815, false, NULL, true, false, NULL, true),
(90708800, 'Element:88:1', 000088, 'LQ-DETFLJ-PCI-1', 1126, 815, false, NULL, true, false, NULL, true),
(90708700, 'Element:87:2', 010087, 'LQ-DALJ-1', 945, 837, false, NULL, true, false, NULL, true),
(90708700, 'Element:87:1', 010087, 'LQ-DEP-DIESEL-PCI', 1303, 959, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:5', 000022, 'LQ-VC-1MA', 414, 639, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:17', 000022, 'LQ-VC-3MA', 1427, 639, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:4', 000022, 'LQ-VM-5MA', 1190, 640, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:16', 000022, 'LQ-VC-2MA', 1109, 608, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:15', 000022, 'LQ-VM-4MA', 1063, 639, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:3', 000022, 'LQ-VM-3MA', 1013, 515, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:14', 000022, 'LQ-VM-6MA', 1345, 636, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:1', 000022, 'LQ-VM-1MA', 495, 640, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:2', 000022, 'LQ-VM-2MA', 713, 518, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:13', 000022, 'LQ-VM-7CA', 416, 116, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:11', 000022, 'LQ-VM-6CA', 497, 115, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:10', 000022, 'LQ-VM-5CA', 713, 227, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:9', 000022, 'LQ-VM-4CA', 976, 114, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:8', 000022, 'LQ-VM-3CA', 1044, 115, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:7', 000022, 'LQ-VM-2CA', 1142, 114, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:6', 000022, 'LQ-VM-1CA', 1349, 116, false, NULL, true, false, NULL, true),
(90702200, 'Element:22:12', 000022, 'LQ-VC-1CA', 1432, 115, false, NULL, true, false, NULL, true);

