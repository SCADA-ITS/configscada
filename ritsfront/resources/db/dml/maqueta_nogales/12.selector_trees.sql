INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(1, 'LBL_SELECTOR_TREE_INCIDENTS_BACKOFFICE', 6011, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, enabled, visible) VALUES
(01, 1, 'LBL_MASTER', null, null, true, true),
	(0101, 1, 'LBL_ROAD_CONDITION', null, null, true, true),
		(010101, 1, 'LBL_CONDITION', null, 6008, true, true),
		(010102, 1, 'LBL_LUMINOSITIES', null, 6019, true, true),
		(010103, 1, 'LBL_ATMOSPHERIC_STATES', null, 6005, true, true),
		(010104, 1, 'LBL_CAUSES', null, 6007, true, true),
		(010105, 1, 'LBL_MANEUVERS', null, 6020, true, true),
		(010106, 1, 'LBL_ROAD_IMPACTS', null, 6027, true, true),
		(010107, 1, 'LBL_INFRASTRUCTURE_DAMAGES', null, 6012, true, true),

	(0102, 1, 'LBL_INCIDENTS', null, null, true, true),
		(010201, 1, 'LBL_SOURCES', null, 6028, true, true),
		(010202, 1, 'LBL_ELEMENTS', null, 6009, true, true),

	(0103, 1, 'LBL_PDAS', null, 6025, true, true),

	(0104, 1, 'LBL_RESOURCES', null, null, true, true),
		(010401, 1, 'LBL_ACTIONS', null, 6000, true, true),
		(010402, 1, 'LBL_TYPOLOGIES', null, 6031, true, true),

	(0105, 1, 'LBL_AGENTS', null, 6001, true, true),

	(0106, 1, 'LBL_UNAVAILABLE_LANE', null, null, true, true),
		(010601, 1, 'LBL_LANES', null, 6016, true, true),
		(010602, 1, 'LBL_LANES_ACTIONS', null, 6017, true, true),
		(010603, 1, 'LBL_LANES_STATES', null, 6018, true, true),

	(0107, 1, 'LBL_VEHICLES', null, null, true, true),
		(010701, 1, 'LBL_VEHICLE_TYPES', null, 6037, true, true),
		(010702, 1, 'LBL_VEHICLE_BRANDS', null, 6034, true, true),
		(010703, 1, 'LBL_VEHICLE_MODELS', null, 6036, true, true),
		(010704, 1, 'LBL_VEHICLE_DAMAGES', null, 6035, true, true),
		(010705, 1, 'LBL_ATTENDEDS', null, 6006, true, true),
		(010706, 1, 'LBL_ASSISTANCE_TYPES', null, 6004, true, true),
		(010707, 1, 'LBL_TRANSFER_PLACES', null, 6030, true, true),

	(0108, 1, 'LBL_PARTICIPANTS', null, null, true, true),
		(010801, 1, 'LBL_PARTICIPANT_TYPES', null, 6023, true, true),
		(010802, 1, 'LBL_GENDERS', null, 6010, true, true),
		(010803, 1, 'LBL_STAGES', null, 6029, true, true),
		(010804, 1, 'LBL_USER_TYPES', null, 6033, true, true),
		(010805, 1, 'LBL_INJURY_TYPES', null, 6013, true, true),
		(010806, 1, 'LBL_INJURY_LEVELS', null, 6014, true, true),
		(010807, 1, 'LBL_PARTICIPANT_ASSISTANCE_TYPES', null, 6021, true, true),
		(010808, 1, 'LBL_PARTICIPANT_TRANSFER_PLACES', null, 6022, true, true),


(02, 1, 'LBL_INCIDENTS', null, 6011, true, true),
(03, 1, 'LBL_PDAS', null, 6002, true, true),
(04, 1, 'LBL_RESOURCES', null, 6003, true, true),
(05, 1, 'LBL_AGENTS', null, 6015, true, true),
(06, 1, 'LBL_UNAVAILABLE_LANE', null, 6032, true, true),
(07, 1, 'LBL_VEHICLES', null, 6038, true, true),
(08, 1, 'LBL_PARTICIPANTS', null, 6024, true, true);


INSERT INTO ui.selector_tree_hierarchies(selector_tree_id,  node_parent_id, node_child_id, enabled, visible) VALUES
(1, 01, 0101, true, true),
	(1, 0101, 010101, true, true),
	(1, 0101, 010102, true, true),
	(1, 0101, 010103, true, true),
	(1, 0101, 010104, true, true),
	(1, 0101, 010105, true, true),
	(1, 0101, 010106, true, true),
	(1, 0101, 010107, true, true),

(1, 01, 0102, true, true),
	(1, 0102, 010201, true, true),
	(1, 0102, 010202, true, true),

(1, 01, 0103, true, true),

(1, 01, 0104, true, true),
	(1, 0104, 010401, true, true),
	(1, 0104, 010402, true, true),

(1, 01, 0105, true, true),


(1, 01, 0106, true, true),
	(1, 0106, 010601, true, true),
	(1, 0106, 010602, true, true),
	(1, 0106, 010603, true, true),


(1, 01, 0107, true, true),
	(1, 0107, 010701, true, true),
	(1, 0107, 010702, true, true),
	(1, 0107, 010703, true, true),
	(1, 0107, 010704, true, true),
	(1, 0107, 010705, true, true),
	(1, 0107, 010706, true, true),
	(1, 0107, 010707, true, true),

(1, 01, 0108, true, true),
	(1, 0108, 010801, true, true),
	(1, 0108, 010802, true, true),
	(1, 0108, 010803, true, true),
	(1, 0108, 010804, true, true),
	(1, 0108, 010805, true, true),
	(1, 0108, 010806, true, true),
	(1, 0108, 010807, true, true),
	(1, 0108, 010808, true, true);





