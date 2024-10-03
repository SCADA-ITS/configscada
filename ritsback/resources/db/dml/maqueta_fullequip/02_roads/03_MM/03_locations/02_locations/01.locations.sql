-- location type incidentReport
INSERT INTO conf.locations (location_id, location_type_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(1010, 2, 'MM-TSM+150', 'Sector TSM+150 del tubo sentido Málaga en el tunel Montemayor', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1011, 2, 'MM-TSM+300', 'Sector TSM+300 del tubo sentido Málaga en el tunel Montemayor', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1012, 2, 'MM-TSM+450', 'Sector TSM+450 del tubo sentido Málaga en el tunel Montemayor', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1013, 2, 'MM-TSM+600', 'Sector TSM+600 del tubo sentido Málaga en el tunel Montemayor', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1014, 2, 'MM-TSC+150', 'Sector TSC+150 del tubo sentido Cádiz en el tunel Montemayor', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1015, 2, 'MM-TSC+300', 'Sector TSC+300 del tubo sentido Cádiz en el tunel Montemayor', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1016, 2, 'MM-TSC+450', 'Sector TSC+450 del tubo sentido Cádiz en el tunel Montemayor', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1017, 2, 'MM-TSC+600', 'Sector TSC+600 del tubo sentido Cádiz en el tunel Montemayor', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.locations
(location_id, location_type_id, alias, description, lat, lon, coordinates, auto_route, enabled, visible, created_at, updated_at) VALUES 
(1007, 2, 'MM-TUNEL', 'Tunel de Montemayor', 36.484722, -5.060833, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1008, 2, 'MM-TSM', 'Boca sentido Málaga del tunel de Montemayor', 36.485303, -5.064358, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1009, 2, 'MM-TSC', 'Boca sentido Cádiz del tunel de Montemayor', 36.484128, -5.057883, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);