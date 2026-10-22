-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(912, 'TREE_WS', 'TREE_WS', 'LBL_TREE_WS', NULL, 'img/synoptics/Bratislava_Meteo.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(91200100, 912, 'METEOS', 'LBL_METEOS', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(33, 912, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(34, 912, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(35, 912, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(91200100, 'Element:1:26', 000001, 'MDPK02L', 488, 346, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:15', 000001, 'MD247', 44, 969, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:23', 000001, 'MZSR403P', 1142, 227, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:22', 000001, 'MDSR409L', 1142, 157, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:8', 000001, 'MZDH03L', 1425, 1002, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:7', 000001, 'MZDH02L', 1425, 932, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:6', 000001, 'MZDH01L', 1425, 861, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:2', 000001, 'MZJS496L', 1425, 154, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:1', 000001, 'MDJS404L', 1425, 83, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:9', 000001, 'MDJS405P', 1425, 224, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:10', 000001, 'MDJS406P', 1425, 295, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:11', 000001, 'MZJS402P', 1425, 366, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:12', 000001, 'MDJS407P', 1425, 436, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:13', 000001, 'MZJS496P', 1426, 507, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:14', 000001, 'MDJS408P', 1426, 578, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:3', 000001, 'MDKD01L', 1425, 649, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:4', 000001, 'MZKD02L', 1426, 719, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:5', 000001, 'MDKD03L', 1425, 790, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:25', 000001, 'MZPK01L', 488, 276, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:24', 000001, 'MDMP01', 488, 206, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:21', 000001, 'MDJS403', 44, 387, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:20', 000001, 'MZJS497', 44, 317, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:19', 000001, 'MZJS498', 44, 247, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:18', 000001, 'MDJS402', 44, 177, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:17', 000001, 'MZJS499L', 44, 107, false, NULL, true, false, NULL, true),
(91200100, 'Element:1:16', 000001, 'MDJS401L', 44, 37, false, NULL, true, false, NULL, true);

