-- stretch_stretchs

INSERT INTO conf.stretch_stretchs (stretch_stretch_id, alias, description, enabled, visible, created_at, updated_at) 
VALUES
(4, 'CH-TUNEL', 'Tunel de Calahonda', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.stretch_stretchs_stretchs (stretch_stretch_id, stretch_id, enabled, visible, created_at, updated_at)
VALUES
(4, 1034, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1035, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1036, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1037, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1038, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1039, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1040, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1041, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);