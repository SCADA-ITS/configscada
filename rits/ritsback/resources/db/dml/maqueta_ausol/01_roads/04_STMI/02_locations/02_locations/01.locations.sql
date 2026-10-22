-- location type incidentReport
INSERT INTO conf.locations (location_id, location_type_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(1018, 2, 'STMI-TUNEL', 'Tunel de Montemayor', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1019, 2, 'STMI-TSM', 'Boca sentido Málaga del tunel de Santa María I', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1020, 2, 'STM-TSC', 'Boca sentido Cádiz del tunel de Santa María I', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);