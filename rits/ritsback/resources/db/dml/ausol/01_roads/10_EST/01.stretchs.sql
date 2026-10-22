-- stretch type IncidentReport
INSERT INTO conf.stretchs (stretch_id, stretch_type_id, alias, description, pk_ini, pk_end, lanes, road_id, enabled, visible, created_at, updated_at)VALUES
(1075, 2, 'EST-TUNEL', 'Tunel de Estepona', 0, 0, 0, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1076, 2, 'EST-TSM', 'Tubo sentido Málaga del tunel de Estepona', 0, 0, 0, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1077, 2, 'EST-TSC', 'Tubo sentido Cádiz del tunel de Estepona', 0, 0, 0, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);