DELETE FROM static.level_of_services;

-- 
-- Table: static.level_of_services
--
INSERT INTO static.level_of_services
(level_of_service_id, los, alias, description, label_alias, label_description, colour, enabled, visible, created_at, updated_at) VALUES
(1, 1, 'Fluid Traffic', 'MC levels A and B', 'LBL_LEVEL_OF_SERVICE_FLUID_TRAFFIC', NULL, 'white', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'Slow Traffic', 'MC level C', 'LBL_LEVEL_OF_SERVICE_SLOW_TRAFFIC', NULL, 'green', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 'Withholdings', 'level D and E of the MC', 'LBL_LEVEL_OF_SERVICE_WITHHOLDINGS', NULL, 'yellow', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 4, 'Congestion', 'MC level F', 'LBL_LEVEL_OF_SERVICE_CONGESTION', NULL, 'red', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


-- 
-- Table: static.level_of_services
--

INSERT INTO static.los_arrays
(los_threshold_speed, los_threshold_occupancy, level_of_service_id, enabled, visible, created_at, updated_at) VALUES
(1,	1,	NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1,	2,	2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1,	3,	3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1,	4,	4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(2,	1,	NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,	2,	2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,	3,	3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,	4,	4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(3,	1,	2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,	2,	2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,	3,	2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,	4,	3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(4,	1,	1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,	2,	1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,	3,	2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,	4,	NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 
-- Table: static.los_softened_arrays
--
INSERT INTO static.los_softened_arrays
(los_softened_array_id, init_los_id, end_los_id, count_softened, enabled, visible, created_at, updated_at)
VALUES
(1, NULL,	1,		2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, NULL,	2,		2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, NULL,	3,		2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, NULL,	4,		2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1,		NULL,	1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1,		2,		2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 1,		3,		3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1,		4,		4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 2,		NULL,	1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 2,		1,		1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 2,		3,		2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 2, 	4,		3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 3, 	NULL,	1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 3,		1,		2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 3,		2,		1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 3,		4,		2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 4,		NULL,	1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 4,		1,		3, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 4,		2,		2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 4,		3,		1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);