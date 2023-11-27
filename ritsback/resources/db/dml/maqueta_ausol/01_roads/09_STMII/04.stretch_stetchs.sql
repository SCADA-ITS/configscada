-- stretch_stretchs

INSERT INTO conf.stretch_stretchs (stretch_stretch_id, alias, description, enabled, visible, created_at, updated_at) 
VALUES
(7, 'STMII-TUNEL', 'Tunel de Santamaria 2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.stretch_stretchs_stretchs (stretch_stretch_id, stretch_id, enabled, visible, created_at, updated_at)
VALUES
(7, 1067, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 1068, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 1069, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 1070, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 1071, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 1072, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 1073, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 1074, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);