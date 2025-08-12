INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(01, 'LBL_SELECTOR_TREE_INCIDENTS_BACKOFFICE', 6000, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, enabled, visible) VALUES
(01, 1, 'LBL_INCIDENTS', null, 6000, true, true),
(03, 1, 'LBL_VEHICLES', null, 6001, true, true),
(04, 1, 'LBL_PARTICIPANTS', null, 6002, true, true),
(05, 1, 'LBL_CLOSURES', null, 6003, true, true),
(06, 1, 'LBL_DAMAGES', null, 6020, true, true),

(02, 1, 'LBL_MASTER', null, null, true, true),
	(0201, 1, 'LBL_VEHICLES', null, null, true, true),
		(020101, 1, 'LBL_VEHICLE_TYPES', null, 6012, true, true),
		(020102, 1, 'LBL_VEHICLE_BRANDS', null, 6016, true, true),
		(020103, 1, 'LBL_VEHICLE_SERVICES', null, 6013, true, true),
		(020104, 1, 'LBL_VEHICLE_CONSECUENCES', null, 6014, true, true),
		(020105, 1, 'LBL_VEHICLE_MANOEUVRES', null, 6015, true, true),
	(0202, 1, 'LBL_PARTICIPANTS', null, null, true, true),
		(020201, 1, 'LBL_PARTICIPANT_TYPES', null, 6017, true, true),
		(020202, 1, 'LBL_PARTICIPANT_INJURIES', null, 6018, true, true),
		(020203, 1, 'LBL_PARTICIPANT_NACIONALITIES', null, 6019, true, true),
	(0203, 1, 'LBL_INCIDENT', null, null, true, true),
		(020301, 1, 'LBL_INCIDENT_EVENT', null, 6004, true, true),
		(020302, 1, 'LBL_INCIDENT_SIDE', null, 6005, true, true),
		(020303, 1, 'LBL_INCIDENT_STRETCH', null, 6006, true, true),
		(020304, 1, 'LBL_INCIDENT_EVENT_TYPE', null, 6007, true, true),
		(020305, 1, 'LBL_INCIDENT_COLLISION', null, 6008, true, true),
		(020306, 1, 'LBL_INCIDENT_OBJECT_CRASH', null, 6009, true, true),
		(020307, 1, 'LBL_INCIDENT_VEHICLE_CRASH', null, 6010, true, true),
		(020308, 1, 'LBL_INCIDENT_RELATIVE_LOCATION', null, 6011, true, true);
		
INSERT INTO ui.selector_tree_hierarchies(selector_tree_id,  node_parent_id, node_child_id, enabled, visible) VALUES
(1, 02, 0201, true, true),
	(1, 0201, 020101, true, true),
	(1, 0201, 020102, true, true),
	(1, 0201, 020103, true, true),
	(1, 0201, 020104, true, true),
	(1, 0201, 020105, true, true),
(1, 02, 0202, true, true),	
	(1, 0202, 020201, true, true),
	(1, 0202, 020202, true, true),
	(1, 0202, 020203, true, true),
(1, 02, 0203, true, true),	
	(1, 0203, 020301, true, true),
	(1, 0203, 020302, true, true),
	(1, 0203, 020303, true, true),
	(1, 0203, 020304, true, true),
	(1, 0203, 020305, true, true),
	(1, 0203, 020306, true, true),
	(1, 0203, 020307, true, true),
	(1, 0203, 020308, true, true);
	