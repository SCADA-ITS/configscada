-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(903, 'TREE_EC_ATC', 'TREE_EC_ATC', 'LBL_SYNOPTIC_EC_ATC', NULL, 'img/synoptics/Bratislava_EC_ATC.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90304100, 903, 'ATC', 'LBL_SYNOPTIC_EC_ATC', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(6, 903, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(7, 903, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(8, 903, 2, 'LBL_COMMAND_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true),
(95, 903, 3, 'LBL_INCIDENTS_ENABLE', 'mdi mdi-pencil', false, 101701, '{"selectedPks":["Element:23:1"]}', 2, false, NULL, false, false);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90304100, 'Element:41:3', 000041, 'RASDDH02P', 1351, 741, false, NULL, true, false, NULL, true),
(90304100, 'Element:41:2', 000041, 'RASDDH01P', 1351, 696, false, NULL, true, false, NULL, true),
(90304100, 'Element:41:7', 000041, 'RASDJS06P', 1351, 434, false, NULL, true, false, NULL, true),
(90304100, 'Element:41:5', 000041, 'RASDJS04P', 1351, 508, false, NULL, true, false, NULL, true),
(90304100, 'Element:41:6', 000041, 'RASDJS05P', 1351, 471, false, NULL, true, false, NULL, true),
(90304100, 'Element:41:10', 000041, 'RASDJS02P', 202, 632, false, NULL, true, false, NULL, true),
(90304100, 'Element:41:9', 000041, 'RASDJS01P', 202, 596, false, NULL, true, false, NULL, true),
(90304100, 'Element:41:14', 000041, 'RASDPK02L', 202, 386, false, NULL, true, false, NULL, true),
(90304100, 'Element:41:13', 000041, 'RASDPK01L', 202, 352, false, NULL, true, false, NULL, true),
(90304100, 'Element:41:11', 000041, 'RASDSR01P', 1351, 77, false, NULL, true, false, NULL, true),
(90304100, 'Element:41:12', 000041, 'RASDSR02P', 1351, 111, false, NULL, true, false, NULL, true),
(90304100, 'Element:41:1', 000041, 'RASDKD01P', 1351, 652, false, NULL, true, false, NULL, true),
(90304100, 'Element:41:4', 000041, 'RASDJS03P', 1351, 607, false, NULL, true, false, NULL, true),
(90304100, 'Element:41:8', 000041, 'RASDJS07L', 1351, 398, false, NULL, true, false, NULL, true);

