-- location type incidentReport
INSERT INTO conf.locations (location_id, location_type_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(1081, 2, 'NG-TSM+162', 'Sector TSM+162 del tubo sentido Málaga en el tunel de  Nagüeles', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1082, 2, 'NG-TSM+324', 'Sector TSM+324 del tubo sentido Málaga en el tunel de  Nagüeles', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1083, 2, 'NG-TSM+486', 'Sector TSM+486 del tubo sentido Málaga en el tunel de  Nagüeles', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1084, 2, 'NG-TSM+648', 'Sector TSM+648 del tubo sentido Málaga en el tunel de  Nagüeles', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1085, 2, 'NG-TSC+173', 'Sector TSM+173 del tubo sentido Cadíz en el tunel de  Nagüeles', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1086, 2, 'NG-TSC+346', 'Sector TSM+346 del tubo sentido Cadíz en el tunel de  Nagüeles', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1087, 2, 'NG-TSC+519', 'Sector TSM+519 del tubo sentido Cadíz en el tunel de  Nagüeles', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1088, 2, 'NG-TSC+694', 'Sector TSM+694 del tubo sentido Cadíz en el tunel de  Nagüeles', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.locations
(location_id, location_type_id, alias, description, lat, lon, coordinates, auto_route, enabled, visible, created_at, updated_at) VALUES 
(1078, 2, 'NG-TUNEL', 'Tunel de Nagüeles', 36.515138, -4.938727, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1079, 2, 'NG-TSM', 'Boca sentido Málaga del tunel de Nagüeles', 36.514267, -4.941269, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1080, 2, 'NG-TSC', 'Boca sentido Cádiz del tunel de Nagüeles', 36.515019, -4.934214, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
