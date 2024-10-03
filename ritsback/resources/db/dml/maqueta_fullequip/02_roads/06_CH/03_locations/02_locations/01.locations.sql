-- location type incidentReport
INSERT INTO conf.locations (location_id, location_type_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(1034, 2, 'CH-TSM+150', 'Sector TSM+150 del tubo sentido Málaga en el tunel de Calahoda', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1035, 2, 'CH-TSM+300', 'Sector TSM+300 del tubo sentido Málaga en el tunel de Calahoda', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1036, 2, 'CH-TSM+450', 'Sector TSM+450 del tubo sentido Málaga en el tunel de Calahoda', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1037, 2, 'CH-TSM+610', 'Sector TSM+610 del tubo sentido Málaga en el tunel de Calahoda', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1038, 2, 'CH-TSC+140', 'Sector TSC+140 del tubo sentido Cádiz en el tunel de Calahoda', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1039, 2, 'CH-TSC+280', 'Sector TSC+280 del tubo sentido Cádiz en el tunel de Calahoda', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1040, 2, 'CH-TSC+420', 'Sector TSC+420 del tubo sentido Cádiz en el tunel de Calahoda', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1041, 2, 'CH-TSC+570', 'Sector TSC+570 del tubo sentido Cádiz en el tunel de Calahoda', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.locations
(location_id, location_type_id, alias, description, lat, lon, coordinates, auto_route, enabled, visible, created_at, updated_at) VALUES 
(1031, 2, 'CH-TUNEL', 'Tunel de Calahoda', 36.506069, -4.727638, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1032, 2, 'CH-TSM', 'Boca sentido Málaga del tunel de Calahoda', 36.504869, -4.730647, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1033, 2, 'CH-TSC', 'Boca sentido Cádiz del tunel de Calahoda', 36.50685, -4.724717, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
