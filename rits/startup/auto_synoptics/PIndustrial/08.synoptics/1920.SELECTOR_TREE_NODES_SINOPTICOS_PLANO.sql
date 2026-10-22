-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(1920, 'SELECTOR_TREE_NODES_SINOPTICOS_PLANO', 'SELECTOR_TREE_NODES_SINOPTICOS_PLANO', 'LBL_SELECTOR_TREE_NODES_SINOPTICOS_PLANO', NULL, 'img/synoptics/PI_Plano.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(192000200, 1920, 'SYNOPTIC_LAYERS_PLANO', 'LBL_SYNOPTIC_LAYERS_PLANO', true),
(192000400, 1920, 'SYNOPTIC_LAYERS_PLANO', 'LBL_SYNOPTIC_LAYERS_PLANO', true),
(192007100, 1920, 'SYNOPTIC_LAYERS_PLANO', 'LBL_SYNOPTIC_LAYERS_PLANO', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(0, 1920, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(1, 1920, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(2, 1920, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(192000200, 'Element:2:20', 000002, 'DOMO-020', 483, 78, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:19', 000002, 'DOMO-019', 465, 51, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:18', 000002, 'DOMO-018', 441, 58, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:17', 000002, 'DOMO-017', 426, 85, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:15', 000002, 'DOMO-015', 411, 122, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:16', 000002, 'DOMO-016', 446, 110, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:14', 000002, 'DOMO-014', 413, 167, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:13', 000002, 'DOMO-013', 397, 213, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:12', 000002, 'DOMO-012', 380, 233, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:11', 000002, 'DOMO-011', 360, 219, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:9', 000002, 'DOMO-009', 287, 228, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:8', 000002, 'DOMO-008', 245, 228, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:10', 000002, 'DOMO-010', 333, 228, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:7', 000002, 'DOMO-007', 202, 228, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:6', 000002, 'DOMO-006', 177, 246, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:5', 000002, 'DOMO-005', 164, 228, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:4', 000002, 'DOMO-004', 133, 215, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:3', 000002, 'DOMO-003', 98, 134, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:2', 000002, 'DOMO-002', 95, 187, false, NULL, true, false, NULL, true),
(192000200, 'Element:2:1', 000002, 'DOMO-001', 49, 203, false, NULL, true, false, NULL, true),
(192000400, 'Element:4:2', 000004, 'SEC-2', 196, 260, false, NULL, true, false, NULL, true),
(192000400, 'Element:4:1', 000004, 'SEC-1', 197, 201, false, NULL, true, false, NULL, true),
(192007100, 'Element:71:2', 000071, 'PMV-02', 317, 146, false, NULL, true, false, NULL, true),
(192007100, 'Element:71:1', 000071, 'PMV-01', 49, 252, false, NULL, true, false, NULL, true);

