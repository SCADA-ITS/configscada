-- Para los identificadores de los sinopticos seguiremos los siguientes criterios
-- Id iterado --> 3 cifras
INSERT INTO ui.synoptics 
(id, name, description, label, icon, base_layer, exclusive_layers) VALUES 
(909, 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_CGBT_LT3', 'SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_CGBT_LT3', 'LBL_SELECTOR_TREE_NODES_SINOPTICO_UNIFILAR_CGBT_LT3', NULL, 'img/synoptics/unifilar_cgbt_lt3.svg', false);

-- Para los identificadores de synoptic_layers seguiremos los siguientes criterios
-- Id Synoptico                                   --> 3 cifras
-- Id tipo (000 si no hay tipo asociado)          --> 3 cifras
-- Id subTipo (00 si tiene tipo, iterador si no)  --> 2 cifras
INSERT INTO ui.synoptic_layers 
(id, synoptic_id, name, label, visible) VALUES 
(90905600, 909, 'SYNOPTIC_LAYERS_UNIFILAR', 'LBL_SYNOPTIC_LAYERS_UNIFILAR', true),
(90905700, 909, 'SYNOPTIC_LAYERS_INTERRUPTOR', 'LBL_SYNOPTIC_LAYERS_INTERRUPTOR', true),
(90907000, 909, 'SYNOPTIC_LAYERS_ANALIZADOR_RED', 'LBL_SYNOPTIC_LAYERS_ANALIZADOR_RED', true),
(90907500, 909, 'SYNOPTIC_LAYERS_GE', 'LBL_SYNOPTIC_LAYERS_GE', true),
(90906500, 909, 'SYNOPTIC_LAYERS_SAI', 'LBL_SYNOPTIC_LAYERS_SAI', true);

INSERT INTO ui.synoptic_commands 
(id, synoptic_id, position, label, icon, "default", module_action_id, args, view_type_id, multiselect, show_text, item_required, context) VALUES 
(24, 909, 0, 'LBL_CMD_ALARMS', 'mdi mdi-pencil', false, 100501, NULL, 2, true, NULL, false, true),
(25, 909, 1, 'LBL_ELEMENT', 'mdi mdi-pencil', true, 101401, NULL, 2, false, NULL, true, true),
(26, 909, 2, 'LBL_CMD_EQUIPMENT', 'mdi mdi-pencil', false, 101701, NULL, 2, true, NULL, false, true);

INSERT INTO ui.synoptic_element_type_commands
(element_type, synoptic_command_id, module_action_id, view_type_id) VALUES
('ElementType:56', 25, 200002, 2);

INSERT INTO ui.synoptic_layer_elements 
(layer_id, element_id, graphic_icon_id, label, x, y,  horizontal_flip, rotate, tooltip, show_text, text_color, selectable) VALUES 
(90905600, 'Element:56:5', 00005601, 'ILU-SUR-DP3', 299, 609, false, NULL, true, false, NULL, true),
(90905600, 'Element:56:4', 00005601, 'ILU-NOR-DP3', 422, 609, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:119', 00005701, 'INT-K01-GEN-CGBT-DP3', 468, 115, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:126', 00005701, 'INT-K01-RED-CGBT-DP3', 464, 199, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:162', 00005701, 'INT-K02-GRU-CGBT-DP3', 696, 173, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:148', 00005701, 'INT-K03-RED-CGBT-DP3', 811, 203, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:122', 00005701, 'INT-K02-GEN-CGBT-DP3', 815, 116, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:133', 000057, 'INT-VENT-3-01-CGBT-DP3', 89, 804, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:134', 000057, 'INT-VENT-3-02-CGBT-DP3', 218, 804, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:135', 000057, 'INT-VENT-3-03-CGBT-DP3', 348, 804, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:136', 000057, 'INT-VENT-3-04-CGBT-DP3', 478, 804, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:137', 000057, 'INT-VENT-3-05-CGBT-DP3', 608, 804, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:138', 000057, 'INT-VENT-3-06-CGBT-DP3', 738, 804, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:139', 000057, 'INT-VENT-3-07-CGBT-DP3', 871, 804, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:140', 000057, 'INT-VENT-3-08-CGBT-DP3', 1000, 804, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:141', 000057, 'INT-VENT-3-09-CGBT-DP3', 1129, 804, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:142', 000057, 'INT-VENT-3-10-CGBT-DP3', 1262, 804, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:156', 000057, 'INT-VENT-3-11-CGBT-DP3', 1391, 804, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:157', 000057, 'INT-VENT-3-12-CGBT-DP3', 1519, 804, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:158', 000057, 'INT-VENT-3-13-CGBT-DP3', 1649, 804, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:159', 000057, 'INT-VENT-3-14-CGBT-DP3', 1779, 804, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:150', 000057, 'INT-Q13-CGBT-DP3', 1787, 502, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:121', 000057, 'INT-Q2-GEN-CGBT-DP3', 1661, 94, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:118', 000057, 'INT-Q1-GEN-CGBT-DP3', 1351, 94, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:149', 000057, 'INT-Q24-CGBT-DP3', 1661, 502, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:146', 000057, 'INT-Q5-CGBT-DP3', 1661, 384, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:160', 000057, 'INT-Q3-CGBT-DP3', 1507, 270, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:147', 000057, 'INT-Q4-CGBT-DP3', 1561, 384, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:161', 00005703, 'INT-Q6-CGBT-DP3', 1507, 454, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:125', 000057, 'INT-Q2-GRU-CGBT-DP3', 1452, 384, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:124', 000057, 'INT-Q1-RED-CGBT-DP3', 1351, 384, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:155', 000057, 'INT-VENT-2-05-CGBT-DP3', 1296, 502, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:154', 000057, 'INT-VENT-2-04-CGBT-DP3', 1173, 502, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:153', 000057, 'INT-VENT-2-03-CGBT-DP3', 1050, 502, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:152', 000057, 'INT-VENT-2-02-CGBT-DP3', 926, 502, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:151', 000057, 'INT-VENT-2-01-CGBT-DP3', 803, 502, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:132', 000057, 'INT-Q12-CGBT-DP3', 680, 502, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:131', 000057, 'INT-Q11-CGBT-DP3', 556, 502, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:130', 000057, 'INT-Q10-CGBT-DP3', 432, 502, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:129', 000057, 'INT-Q9-CGBT-DP3', 310, 502, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:128', 000057, 'INT-Q8-CGBT-DP3', 186, 502, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:127', 000057, 'INT-Q7-CGBT-DP3', 62, 502, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:143', 000057, 'INT-MOD-2-CGBT-DP3', 473, 313, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:144', 000057, 'INT-MOD-4-CGBT-DP3', 589, 313, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:145', 000057, 'INT-MOD-11-CGBT-DP3', 706, 313, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:164', 000057, 'INT-F6-CGBT-DP3', 821, 313, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:123', 000057, 'INT-F2-CGBT-DP3', 900, 211, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:163', 000057, 'INT-F1-GRU-CGBT-DP3', 624, 181, false, NULL, true, false, NULL, true),
(90905700, 'Element:57:120', 000057, 'INT-F1-B-CGBT-DP3', 392, 211, false, NULL, true, false, NULL, true),
(90907000, 'Element:70:5', 00007001, 'AR-3-DP1', 1710, 213, false, NULL, true, false, NULL, true),
(90907000, 'Element:70:4', 00007001, 'AR-2-DP1', 1418, 225, false, NULL, true, false, NULL, true),
(90907000, 'Element:70:3', 00007001, 'AR-1-DP1', 1289, 213, false, NULL, true, false, NULL, true),
(90907500, 'Element:75:2', 00007502, 'GE3-DP', 1497, 185, false, NULL, true, false, NULL, true),
(90906500, 'Element:65:4', 00006503, 'SAI-S-DP1', 1777, 609, false, NULL, true, false, NULL, true),
(90906500, 'Element:65:3', 00006503, 'SAI-N-DP1', 669, 609, false, NULL, true, false, NULL, true);

