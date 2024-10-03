-- location type incidentReport
INSERT INTO conf.locations (location_id, location_type_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(1067, 2, 'STMII-TSM+157', 'Sector TSM+157 del tubo sentido Málaga en el tunel de  Santa María II', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1068, 2, 'STMII-TSM+314', 'Sector TSM+314 del tubo sentido Málaga en el tunel de  Santa María II', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1069, 2, 'STMII-TSM+433', 'Sector TSM+433 del tubo sentido Málaga en el tunel de  Santa María II', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1070, 2, 'STMII-TSM+553', 'Sector TSM+553 del tubo sentido Málaga en el tunel de  Santa María II', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1071, 2, 'STMII-TSC+115', 'Sector TSM+115 del tubo sentido Cadíz en el tunel de  Santa María II', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1072, 2, 'STMII-TSC+230', 'Sector TSM+230 del tubo sentido Cadíz en el tunel de  Santa María II', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1073, 2, 'STMII-TSC+386', 'Sector TSM+386 del tubo sentido Cadíz en el tunel de  Santa María II', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1074, 2, 'STMII-TSC+542', 'Sector TSM+542 del tubo sentido Cadíz en el tunel de  Santa María II', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.locations
(location_id, location_type_id, alias, description, lat, lon, coordinates, auto_route, enabled, visible, created_at, updated_at) VALUES 

(1064, 2, 'STMII-TUNEL', 'Tunel de Santa María II', 36.424833, -5.209619, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1065, 2, 'STMII-TSM', 'Boca sentido Málaga del tunel de Santa María II', 36.423914, -5.212756, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1066, 2, 'STMII-TSC', 'Boca sentido Cádiz del tunel de Santa María II', 36.425899, -5.207031, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
