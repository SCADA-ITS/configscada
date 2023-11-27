-- stretch_stretchs

INSERT INTO conf.stretch_stretchs (stretch_stretch_id, alias, description, enabled, visible, created_at, updated_at) 
VALUES
(1, 'RV-TUNEL', 'Tunel de Río Verde', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.stretch_stretchs_stretchs (stretch_stretch_id, stretch_id, enabled, visible, created_at, updated_at)
VALUES
(1, 1003, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1004, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1005, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 1006, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);