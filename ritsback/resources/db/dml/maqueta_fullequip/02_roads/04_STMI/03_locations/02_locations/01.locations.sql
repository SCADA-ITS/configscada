-- location type incidentReport
INSERT INTO conf.locations
(location_id, location_type_id, alias, description, lat, lon, coordinates, auto_route, enabled, visible, created_at, updated_at) VALUES 
(1018, 2, 'STMI-TUNEL', 'Tunel de Montemayor', 36.510441, -4.768797, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1019, 2, 'STMI-TSM', 'Boca sentido Málaga del tunel de Santa María I', 36.510439, -4.769656, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1020, 2, 'STM-TSC', 'Boca sentido Cádiz del tunel de Santa María I', 36.510372, -4.768039, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
