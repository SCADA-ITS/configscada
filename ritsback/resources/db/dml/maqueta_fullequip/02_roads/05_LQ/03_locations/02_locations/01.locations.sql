-- location type incidentReport
INSERT INTO conf.locations (location_id, location_type_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(1024, 2, 'LQ-TSM+150', 'Sector TSM+150 del tubo sentido Málaga en el tunel de La Quinta', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1025, 2, 'LQ-TSM+300', 'Sector TSM+300 del tubo sentido Málaga en el tunel de La Quinta', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1026, 2, 'LQ-TSM+450', 'Sector TSM+450 del tubo sentido Málaga en el tunel de La Quinta', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1027, 2, 'LQ-TSC+210', 'Sector TSM+210 del tubo sentido Cádiz en el tunel de La Quinta', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1028, 2, 'LQ-TSC+420', 'Sector TSM+420 del tubo sentido Cádiz en el tunel de La Quinta', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1029, 2, 'LQ-TSC+630', 'Sector TSM+630 del tubo sentido Cádiz en el tunel de La Quinta', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1030, 2, 'LQ-TSC+840', 'Sector TSM+840 del tubo sentido Cádiz en el tunel de La Quinta', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.locations
(location_id, location_type_id, alias, description, lat, lon, coordinates, auto_route, enabled, visible, created_at, updated_at) VALUES 
(1021, 2, 'LQ-TUNEL', 'Tunel de La Quinta', 36.50469, -4.99525, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1022, 2, 'LQ-TSM', 'Boca sentido Málaga del tunel de La Quinta', 36.501958, -4.998539, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1023, 2, 'LQ-TSC', 'Boca sentido Cádiz del tunel de La Quinta', 36.507936, -4.993003, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
