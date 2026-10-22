-- stretch type IncidentReport
INSERT INTO conf.stretchs (stretch_id, stretch_type_id, alias, description, pk_ini, pk_end, lanes, road_id, enabled, visible, created_at, updated_at)VALUES
(1000, 2, 'RV-TUNEL', 'Tunel de Río Verde', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1001, 2, 'RV-TSM', 'Tubo sentido Málaga del tunel de Río Verde', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1002, 2, 'RV-TSC', 'Tubo sentido Cádiz del tunel de Río Verde', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1003, 2, 'RV-TSM+150', 'Sector TSM+150 del tubo sentido Málaga en el tunel de Río Verde', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1004, 2, 'RV-TSM+310', 'Sector TSM+310 del tubo sentido Málaga en el tunel de Río Verde', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1005, 2, 'RV-TSC+150', 'Sector TSC+150 del tubo sentido Cádiz en el tunel de Río Verde', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1006, 2, 'RV-TSC+300', 'Sector TSC+300 del tubo sentido Cádiz en el tunel de Río Verde', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);