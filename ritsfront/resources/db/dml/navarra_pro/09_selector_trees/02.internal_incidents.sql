INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(2, 'LBL_SELECTOR_TREE_INCIDENTS_BACKOFFICE', 6011, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, enabled, visible) VALUES
(200001, 2, 'LBL_MASTER', null, null, true, true),
	(20000104, 2, 'LBL_RESOURCES', null, null, true, true),
		(2000010401, 2, 'LBL_ACTIONS', null, 6000, true, true),
		(2000010402, 2, 'LBL_TYPOLOGIES', null, 6031, true, true),

	(20000107, 2, 'LBL_VEHICLES', null, null, true, true),
		(2000010701, 2, 'LBL_VEHICLE_TYPES', null, 6037, true, true),
		(2000010702, 2, 'LBL_VEHICLE_BRANDS', null, 6034, true, true),
		(2000010703, 2, 'LBL_VEHICLE_MODELS', null, 6036, true, true),
		(2000010704, 2, 'LBL_VEHICLE_DAMAGES', null, 6035, true, true),
		(2000010705, 2, 'LBL_ATTENDEDS', null, 6006, true, true),
		(2000010706, 2, 'LBL_ASSISTANCE_TYPES', null, 6004, true, true),
		(2000010707, 2, 'LBL_TRANSFER_PLACES', null, 6030, true, true),

	(20000108, 2, 'LBL_PARTICIPANTS', null, null, true, true),
		(2000010802, 2, 'LBL_GENDERS', null, 6010, true, true),
		(2000010803, 2, 'LBL_STAGES', null, 6029, true, true),
		(2000010804, 2, 'LBL_USER_TYPES', null, 6033, true, true),
		(2000010805, 2, 'LBL_INJURY_TYPES', null, 6013, true, true),
		(2000010806, 2, 'LBL_INJURY_LEVELS', null, 6014, true, true),
		(2000010807, 2, 'LBL_PARTICIPANT_ASSISTANCE_TYPES', null, 6021, true, true),
		(2000010808, 2, 'LBL_PARTICIPANT_TRANSFER_PLACES', null, 6022, true, true),

(200002, 2, 'LBL_INCIDENTS', null, 6011, true, true),
(200004, 2, 'LBL_RESOURCES', null, 6003, true, true),
(200007, 2, 'LBL_VEHICLES', null, 6038, true, true),
(200008, 2, 'LBL_PARTICIPANTS', null, 6024, true, true);

INSERT INTO ui.selector_tree_hierarchies(selector_tree_id,  node_parent_id, node_child_id, enabled, visible) VALUES
(2,  200001, 20000104, true, true),
	(2, 20000104, 2000010401, true, true),
	(2, 20000104, 2000010402, true, true),

(2,  200001, 20000107, true, true),
	(2,  20000107, 2000010701, true, true),
	(2,  20000107, 2000010702, true, true),
	(2,  20000107, 2000010703, true, true),
	(2,  20000107, 2000010704, true, true),
	(2,  20000107, 2000010705, true, true),
	(2,  20000107, 2000010706, true, true),
	(2,  20000107, 2000010707, true, true),

(2,  200001, 20000108, true, true),
	(2,  20000108, 2000010802, true, true),
	(2,  20000108, 2000010803, true, true),
	(2,  20000108, 2000010804, true, true),
	(2,  20000108, 2000010805, true, true),
	(2,  20000108, 2000010806, true, true),
	(2,  20000108, 2000010807, true, true),
	(2,  20000108, 2000010808, true, true);
