INSERT INTO ui.selector_trees(id, "label", default_module_id, enabled, visible) VALUES
(2, 'LBL_MASTER_DATA', 192001, true, true),
(3, 'LBL_PROCESSES', 192101, true, true),
(4, 'LBL_VALIDATION_AND_SALES', 192001, true, true),
(5, 'LBL_SHIFTS_AND_EXPEDITIONS', 192001, true, true),
(6, 'LBL_LIQUIDATION_OF_DRIVING_STAFF', 192001, true, true),
(7, 'LBL_REQUESTS', 192001, true, true);

INSERT INTO ui.selector_tree_nodes(id, selector_tree_id, "label", image, module_id, enabled, visible) VALUES
-- MASTER DATA
(201, 2, 'LBL_MASTER_DATA', null, null, true, true),
	(20101, 2, 'LBL_GEOGRAPHIC_SECTION', null, null, true, true),
		(2010101, 2, 'LBL_MUGI_ZONE', null, 192001, true, true),
		(2010102, 2, 'LBL_MUGI_ZONE_S', null, 192002, true, true),
		(2010103, 2, 'LBL_MUNICIPALITIES', null, 192003, true, true),

	(20102, 2, 'LBL_ADMINISTRATIVE_SECTION', null, null, true, true),
		(2010201, 2, 'LBL_CONSORTIUMS', null, 192004, true, true),

	(20103, 2, 'LBL_TOPOLOGY_SECTION', null, null, true, true),
		(2010301, 2, 'LBL_SUPEROPERATORS', null, 192005, true, true),
		(2010302, 2, 'LBL_BUS_LINES', null, 192006, true, true),
		(2010303, 2, 'LBL_BUS_STOPS', null, 192007, true, true),
		(2010304, 2, 'LBL_TRANSHIPMENT_EXCLUSIONS', null, 192008, true, true),
		
	(20104, 2, 'LBL_MONEY_SECTION', null, null, true, true),
		(2010401, 2, 'LBL_TICKET_TYPE', null, 192009, true, true),
		(2010402, 2, 'LBL_TRAVEL_FARES', null, 192010, true, true),
		(2010403, 2, 'LBL_ORIGIN_DESTINATION_PRESALES', null, 192011, true, true),
		
	(20105, 2, 'LBL_RESOURCES_SECTION', null, null, true, true),
		(2010501, 2, 'LBL_VEHICLES', null, 192012, true, true),
		(2010502, 2, 'LBL_EMPLOYEES', null, 192013, true, true),
		(2010503, 2, 'LBL_DEVICES', null, 192014, true, true),
		
	(20106, 2, 'LBL_PLANNING_SECTION', null, null, true, true),
		(2010601, 2, 'LBL_PLANNING_SHIFT', null, 192015, true, true),
		(2010602, 2, 'LBL_PLANNING_EXPEDITION', null, 192016, true, true),
		
	(20107, 2, 'LBL_PARAMETERS_SECTION', null, null, true, true),
		(2010701, 2, 'LBL_PARAMETERS', null, 192017, true, true),
		
-- PROCESSES
(301, 3, 'LBL_PROCESSES', null, null, true, true),
	(30101, 3, 'LBL_ASSIGN_DEVICE_VEHICLE', null, 192101, true, true),
	(30102, 3, 'LBL_IMPORT_OF_CASH_COLLECTION_DRIVING_STAFF', null, 192102, true, true),
	(30103, 3, 'LBL_SCHEDULES', null, 192001, true, true),
	(30104, 3, 'LBL_PARAMETER_SETTING', null, null, true, true),
		(3010401, 3, 'LBL_PROCESSES', null, 192001, true, true),
		(3010402, 3, 'LBL_USERS_LANGUAGE', null, 192001, true, true),
		(3010403, 3, 'LBL_USERS_OPERATORS', null, 192001, true, true),
		(3010404, 3, 'LBL_PROGRAMMING_GENERATION_STATUS', null, 192001, true, true),
		
-- VALIDATION_AND_SALES
(401, 4, 'LBL_VALIDATION_AND_SALES', null, null, true, true),
	(40101, 4, 'LBL_MANUAL_ENTRY_VALIDATIONS_SALES', null, 192001, true, true),
	(40102, 4, 'LBL_VALIDATIONS_SALES_DETAIL', null, 192001, true, true),
	
-- SHIFTS_AND_EXPEDITIONS
(501, 5, 'LBL_SHIFTS_AND_EXPEDITIONS', null, null, true, true),
	(50101, 5, 'LBL_SHIFTS', null, 192001, true, true),
	(50102, 5, 'LBL_EXPEDITIONS', null, 192001, true, true),
	(50103, 5, 'LBL_MANUAL_SHIFT_ENTRY', null, 192001, true, true),
	(50104, 5, 'LBL_MANUAL_SHIFT_EXPEDITION', null, 192001, true, true),
	
-- LIQUIDATION_OF_DRIVING_STAFF
(601, 6, 'LBL_SHIFTS_AND_EXPEDITIONS', null, null, true, true),
	(60101, 6, 'LBL_SETTLEMENTS_QUERY', null, 192001, true, true),
	(60102, 6, 'LBL_TOTAL_CASH_COLLECTION_BY_DRIVING_STAFF', null, 192001, true, true),
	(60103, 6, 'LBL_SETTLEMENTS_BY_DRIVING_STAFF', null, 192001, true, true),
	(60104, 6, 'LBL_LOST_SETTLEMENTS', null, 192001, true, true),
	
-- REQUESTS
(701, 7, 'LBL_REQUESTS', null, null, true, true),
	(70101, 7, 'LBL_CASH_COLLECTION_BY_DRIVING_STAFF', null, 192001, true, true),
	(70102, 7, 'LBL_COMPARATIVE_LIST_OF_PASSENGERS_BY_PERIOD', null, 192001, true, true),
	(70103, 7, 'LBL_CASH_COLLECTION_BY_OPERATOR_BUS_LINE_AND_EXPEDITION', null, 192001, true, true),
	(70104, 7, 'LBL_TRAVEL_EXPEDITION_AND_SALES_CHANNEL', null, 192001, true, true),
	(70105, 7, 'LBL_ORIGIN_DESTINATION_TRAVEL_AND_SALES_CHANNEL', null, 192001, true, true),
	(70106, 7, 'LBL_LIST_OF_CANCELLED_TITLES', null, 192001, true, true),
	(70107, 7, 'LBL_TRAVEL_RECONSTRUCTION', null, 192001, true, true),
	(70108, 7, 'LBL_VERSION_REQUEST', null, 192001, true, true),
	(70109, 7, 'LBL_SCHEDULES_REQUEST', null, 192001, true, true);

INSERT INTO ui.selector_tree_hierarchies(selector_tree_id,  node_parent_id, node_child_id, enabled, visible) VALUES
-- MASTER DATA
	(2,  201, 20101, true, true),
		(2,  20101, 2010101, true, true),
		(2,  20101, 2010102, true, true),
		(2,  20101, 2010103, true, true),
	(2,  201, 20102, true, true),
		(2,  20102, 2010201, true, true),
	(2,  201, 20103, true, true),
		(2,  20103, 2010301, true, true),
		(2,  20103, 2010302, true, true),
		(2,  20103, 2010303, true, true),
		(2,  20103, 2010304, true, true),
	(2,  201, 20104, true, true),
		(2,  20104, 2010401, true, true),
		(2,  20104, 2010402, true, true),
		(2,  20104, 2010403, true, true),
	(2,  201, 20105, true, true),
		(2,  20105, 2010501, true, true),
		(2,  20105, 2010502, true, true),
		(2,  20105, 2010503, true, true),
	(2,  201, 20106, true, true),
		(2,  20106, 2010601, true, true),
		(2,  20106, 2010602, true, true),
	(2,  201, 20107, true, true),
		(2,  20107, 2010701, true, true),

-- PROCESSES
	(3,  301, 30101, true, true),
	(3,  301, 30102, true, true),
	(3,  301, 30103, true, true),
	(3,  301, 30104, true, true),
		(3,  30104, 3010401, true, true),
		(3,  30104, 3010402, true, true),
		(3,  30104, 3010403, true, true),
		(3,  30104, 3010404, true, true),
		
-- VALIDATION_AND_SALES
	(4,  401, 40101, true, true),
	(4,  401, 40102, true, true),
	
-- SHIFTS_AND_EXPEDITIONS
	(5,  501, 50101, true, true),
	(5,  501, 50102, true, true),
	(5,  501, 50103, true, true),
	(5,  501, 50104, true, true),

-- LIQUIDATION_OF_DRIVING_STAFF
	(6,  601, 60101, true, true),
	(6,  601, 60102, true, true),
	(6,  601, 60103, true, true),
	(6,  601, 60104, true, true),
	
-- REQUESTS
	(7, 701, 70101, true, true),
	(7, 701, 70102, true, true),
	(7, 701, 70103, true, true),
	(7, 701, 70104, true, true),
	(7, 701, 70105, true, true),
	(7, 701, 70106, true, true),
	(7, 701, 70107, true, true),
	(7, 701, 70108, true, true),
	(7, 701, 70109, true, true);