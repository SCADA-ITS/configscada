-- stretch_stretchs

INSERT INTO conf.stretch_stretchs (stretch_stretch_id, alias, description, enabled, visible, created_at, updated_at) 
VALUES
(8, 'NG-TUNEL', 'Tunel de Nagüeles', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.stretch_stretchs_stretchs (stretch_stretch_id, stretch_id, enabled, visible, created_at, updated_at)
VALUES
(8, 1081, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1082, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1083, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1084, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1085, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1086, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1087, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1088, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);