-- location type incidentReport
INSERT INTO conf.locations (location_id, location_type_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(1003, 2, 'RV-TSM+150', 'Sector TSM+150 del tubo sentido Málaga en el tunel de Río Verde', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1004, 2, 'RV-TSM+310', 'Sector TSM+310 del tubo sentido Málaga en el tunel de Río Verde', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1005, 2, 'RV-TSC+150', 'Sector TSC+150 del tubo sentido Cádiz en el tunel de Río Verde', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1006, 2, 'RV-TSC+300', 'Sector TSC+300 del tubo sentido Cádiz en el tunel de Río Verde', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.locations
(location_id, location_type_id, alias, description, lat, lon, coordinates, auto_route, enabled, visible, created_at, updated_at) VALUES 
(1000, 2, 'RV-TUNEL', 'Tunel de Río Verde', 36.525937, -4.962095, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1001, 2, 'RV-TSM', 'Boca sentido Málaga del tunel de Río Verde', 36.526025, -4.963872, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1002, 2, 'RV-TSC', 'Boca sentido Cádiz del tunel de Río Verde', 36.525917, -4.960522, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);