-- location type incidentReport
INSERT INTO conf.locations (location_id, location_type_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(1075, 2, 'EST-TUNEL', 'Tunel de Estepona', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1076, 2, 'EST-TSM', 'Boca sentido Málaga del tunel de Estepona', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1077, 2, 'EST-TSC', 'Boca sentido Cádiz del tunel de Estepona', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);