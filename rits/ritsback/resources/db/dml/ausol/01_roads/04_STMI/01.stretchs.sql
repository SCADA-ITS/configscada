-- stretch type IncidentReport
INSERT INTO conf.stretchs (stretch_id, stretch_type_id, alias, description, pk_ini, pk_end, lanes, road_id, enabled, visible, created_at, updated_at)VALUES
(1018, 2, 'STMI-TUNEL', 'Tunel de Montemayor', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1019, 2, 'STMI-TSM', 'Tubo sentido Málaga del tunel de Santa María I', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1020, 2, 'STMI-TSC', 'Tubo sentido Cádiz del tunel de Santa María I', 0, 0, 0, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);