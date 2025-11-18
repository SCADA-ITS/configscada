INSERT INTO conf.stretchs (stretch_id, stretch_type_id, alias, description, pk_ini, pk_end, lanes, road_id, enabled, visible, created_at, updated_at)VALUES
(1, 1, 'S1S1', 'S1S1', 0, 6+5, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 'S1S2', 'S1S2', 6+5, 19, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 'S1S3', 'S1S3', 19, 25+8, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1, 'S2', 'S2', 25+8, 61, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
