INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(4, 'LBL_SELECTOR_TREE_INCIDENTS_BACKOFFICE', 6011, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, enabled, visible) VALUES
(200001, 4, 'LBL_MASTER', null, null, true, true),
	(20000101, 4, 'LBL_ROAD_CONDITION', null, null, true, true),
		(2000010101, 4, 'LBL_CONDITION', null, 6008, true, true),
		(2000010102, 4, 'LBL_LUMINOSITIES', null, 6019, true, true),
		(2000010103, 4, 'LBL_ATMOSPHERIC_STATES', null, 6005, true, true),
		(2000010104, 4, 'LBL_CAUSES', null, 6007, true, true),
		(2000010105, 4, 'LBL_MANEUVERS', null, 6020, true, true),
		(2000010106, 4, 'LBL_ROAD_IMPACTS', null, 6027, true, true),
		(2000010107, 4, 'LBL_INFRASTRUCTURE_DAMAGES', null, 6012, true, true),

	(20000102, 4, 'LBL_INCIDENTS', null, null, true, true),
		(2000010201, 4, 'LBL_SOURCES', null, 6028, true, true),
		(2000010202, 4, 'LBL_ELEMENTS', null, 6009, true, true),

	(20000103, 4, 'LBL_PDAS', null, 6025, true, true),

	(20000104, 4, 'LBL_RESOURCES', null, null, true, true),
		(2000010401, 4, 'LBL_ACTIONS', null, 6000, true, true),
		(2000010402, 4, 'LBL_TYPOLOGIES', null, 6031, true, true),

	(20000105, 4, 'LBL_AGENTS', null, 6001, true, true),

	(20000106, 4, 'LBL_UNAVAILABLE_LANE', null, null, true, true),
		(2000010601, 4, 'LBL_LANES', null, 6016, true, true),
		(2000010602, 4, 'LBL_LANES_ACTIONS', null, 6017, true, true),
		(2000010603, 4, 'LBL_LANES_STATES', null, 6018, true, true),

	(20000107, 4, 'LBL_VEHICLES', null, null, true, true),
		(2000010701, 4, 'LBL_VEHICLE_TYPES', null, 6037, true, true),
		(2000010702, 4, 'LBL_VEHICLE_BRANDS', null, 6034, true, true),
		(2000010703, 4, 'LBL_VEHICLE_MODELS', null, 6036, true, true),
		(2000010704, 4, 'LBL_VEHICLE_DAMAGES', null, 6035, true, true),
		(2000010705, 4, 'LBL_ATTENDEDS', null, 6006, true, true),
		(2000010706, 4, 'LBL_ASSISTANCE_TYPES', null, 6004, true, true),
		(2000010707, 4, 'LBL_TRANSFER_PLACES', null, 6030, true, true),

	(20000108, 4, 'LBL_PARTICIPANTS', null, null, true, true),
		(2000010801, 4, 'LBL_PARTICIPANT_TYPES', null, 6023, true, true),
		(2000010802, 4, 'LBL_GENDERS', null, 6010, true, true),
		(2000010803, 4, 'LBL_STAGES', null, 6029, true, true),
		(2000010804, 4, 'LBL_USER_TYPES', null, 6033, true, true),
		(2000010805, 4, 'LBL_INJURY_TYPES', null, 6013, true, true),
		(2000010806, 4, 'LBL_INJURY_LEVELS', null, 6014, true, true),
		(2000010807, 4, 'LBL_PARTICIPANT_ASSISTANCE_TYPES', null, 6021, true, true),
		(2000010808, 4, 'LBL_PARTICIPANT_TRANSFER_PLACES', null, 6022, true, true),
		
(200002, 4, 'LBL_INCIDENTS', null, 6011, true, true),
(200003, 4, 'LBL_PDAS', null, 6002, true, true),
(200004, 4, 'LBL_RESOURCES', null, 6003, true, true),
(200005, 4, 'LBL_AGENTS', null, 6015, true, true),
(200006, 4, 'LBL_UNAVAILABLE_LANE', null, 6032, true, true),
(200007, 4, 'LBL_VEHICLES', null, 6038, true, true),
(200008, 4, 'LBL_PARTICIPANTS', null, 6024, true, true);

INSERT INTO ui.selector_tree_hierarchies(selector_tree_id,  node_parent_id, node_child_id, enabled, visible) VALUES
(4,  200001, 20000101, true, true),
	(4,  20000101, 2000010101, true, true),
	(4,  20000101, 2000010102, true, true),
	(4,  20000101, 2000010103, true, true),
	(4,  20000101, 2000010104, true, true),
	(4,  20000101, 2000010105, true, true),
	(4,  20000101, 2000010106, true, true),
	(4,  20000101, 2000010107, true, true),

(4,  200001, 20000102, true, true),
	(4,  20000102, 2000010201, true, true),
	(4,  20000102, 2000010202, true, true),

(4,  200001, 20000103, true, true),

(4,  200001, 20000104, true, true),
	(4, 20000104, 2000010401, true, true),
	(4, 20000104, 2000010402, true, true),

(4,  200001, 20000105, true, true),


(4,  200001, 20000106, true, true),
	(4,  20000106, 2000010601, true, true),
	(4,  20000106, 2000010602, true, true),
	(4,  20000106, 2000010603, true, true),


(4,  200001, 20000107, true, true),
	(4,  20000107, 2000010701, true, true),
	(4,  20000107, 2000010702, true, true),
	(4,  20000107, 2000010703, true, true),
	(4,  20000107, 2000010704, true, true),
	(4,  20000107, 2000010705, true, true),
	(4,  20000107, 2000010706, true, true),
	(4,  20000107, 2000010707, true, true),

(4,  200001, 20000108, true, true),
	(4,  20000108, 2000010801, true, true),
	(4,  20000108, 2000010802, true, true),
	(4,  20000108, 2000010803, true, true),
	(4,  20000108, 2000010804, true, true),
	(4,  20000108, 2000010805, true, true),
	(4,  20000108, 2000010806, true, true),
	(4,  20000108, 2000010807, true, true),
	(4,  20000108, 2000010808, true, true);
