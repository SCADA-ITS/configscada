-- stretch_stretchs

INSERT INTO conf.stretch_stretchs (stretch_stretch_id, alias, description, enabled, visible, created_at, updated_at) 
VALUES
(2, 'MM-TUNEL', 'Tunel de Montemayor', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.stretch_stretchs_stretchs (stretch_stretch_id, stretch_id, enabled, visible, created_at, updated_at)
VALUES
(2, 1010, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1011, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1012, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1013, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1014, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1015, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1016, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1017, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);