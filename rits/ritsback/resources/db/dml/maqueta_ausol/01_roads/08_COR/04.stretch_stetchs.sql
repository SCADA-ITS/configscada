-- stretch_stretchs

INSERT INTO conf.stretch_stretchs (stretch_stretch_id, alias, description, enabled, visible, created_at, updated_at) 
VALUES
(6, 'COR-TUNEL', 'Tunel de Corominas', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.stretch_stretchs_stretchs (stretch_stretch_id, stretch_id, enabled, visible, created_at, updated_at)
VALUES
(6, 1056, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1057, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1058, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1059, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1060, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1061, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1062, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);