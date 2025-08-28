INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(01, 'LBL_SELECTOR_TREE_INCIDENTS_BACKOFFICE', 6000, true, true),
(02, 'LBL_SELECTOR_TREE_REPORTS_BACKOFFICE', 6100, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, enabled, visible, position) VALUES
(0101, 1, 'LBL_INCIDENTS', null, 6000, true, true, 1),
(0102, 1, 'LBL_MASTER', null, null, true, true, 2),
	(010201, 1, 'LBL_VEHICLES', null, null, true, true, 3),
		(01020101, 1, 'LBL_VEHICLE_TYPES', null, 6012, true, true, 4),
		(01020102, 1, 'LBL_VEHICLE_BRANDS', null, 6016, true, true, 5),
		(01020103, 1, 'LBL_VEHICLE_SERVICES', null, 6013, true, true, 6),
		(01020104, 1, 'LBL_VEHICLE_CONSECUENCES', null, 6014, true, true, 7),
		(01020105, 1, 'LBL_VEHICLE_MANOEUVRES', null, 6015, true, true, 8),
	(010202, 1, 'LBL_PARTICIPANTS', null, null, true, true, 9),
		(01020201, 1, 'LBL_PARTICIPANT_TYPES', null, 6017, true, true, 10),
		(01020202, 1, 'LBL_PARTICIPANT_INJURIES', null, 6018, true, true, 11),
		(01020203, 1, 'LBL_PARTICIPANT_NACIONALITIES', null, 6019, true, true, 12),
	(010203, 1, 'LBL_INCIDENT', null, null, true, true, 13),
		(01020301, 1, 'LBL_INCIDENT_EVENT', null, 6004, true, true, 14),
		(01020302, 1, 'LBL_INCIDENT_SIDE', null, 6005, true, true, 15),
		(01020303, 1, 'LBL_INCIDENT_STRETCH', null, 6006, true, true, 16),
		(01020304, 1, 'LBL_INCIDENT_EVENT_TYPE', null, 6007, true, true, 17),
		(01020305, 1, 'LBL_INCIDENT_COLLISION', null, 6008, true, true, 18),
		(01020306, 1, 'LBL_INCIDENT_OBJECT_CRASH', null, 6009, true, true, 19),
		(01020307, 1, 'LBL_INCIDENT_VEHICLE_CRASH', null, 6010, true, true, 20),
		(01020308, 1, 'LBL_INCIDENT_RELATIVE_LOCATION', null, 6011, true, true, 21),
(0103, 1, 'LBL_REPORTS', null, null, true, true, 23),
	(010301, 1, 'LBL_VEHICLES', null, 6001, true, true, 24),
	(010302, 1, 'LBL_PARTICIPANTS', null, 6002, true, true, 25),
	(010303, 1, 'LBL_CLOSURES', null, 6003, true, true, 26),
	(010304, 1, 'LBL_DAMAGES', null, 6020, true, true, 27),
	
(0201, 2, 'LBL_REPORT_ALARMS', null, 6100, true, true, 1),
(0202, 2, 'LBL_REPORT_STATES', null, 6101, true, true, 1);

INSERT INTO ui.selector_tree_hierarchies(selector_tree_id,  node_parent_id, node_child_id, enabled, visible) VALUES
(1, 0102, 010201, true, true),
	(1, 010201, 01020101, true, true),
	(1, 010201, 01020102, true, true),
	(1, 010201, 01020103, true, true),
	(1, 010201, 01020104, true, true),
	(1, 010201, 01020105, true, true),
(1, 0102, 010202, true, true),	
	(1, 010202, 01020201, true, true),
	(1, 010202, 01020202, true, true),
	(1, 010202, 01020203, true, true),
(1, 0102, 010203, true, true),	
	(1, 010203, 01020301, true, true),
	(1, 010203, 01020302, true, true),
	(1, 010203, 01020303, true, true),
	(1, 010203, 01020304, true, true),
	(1, 010203, 01020305, true, true),
	(1, 010203, 01020306, true, true),
	(1, 010203, 01020307, true, true),
	(1, 010203, 01020308, true, true),
(1, 0103, 010301, true, true),
(1, 0103, 010302, true, true),
(1, 0103, 010303, true, true),
(1, 0103, 010304, true, true);




	
