-- stretch_stretchs

INSERT INTO conf.stretch_stretchs (stretch_stretch_id, alias, description, enabled, visible, created_at, updated_at) 
VALUES
(3, 'LQ-TUNEL', 'Tunel de La quinta', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.stretch_stretchs_stretchs (stretch_stretch_id, stretch_id, enabled, visible, created_at, updated_at)
VALUES
(3, 1024, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1025, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1026, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1027, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1028, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1029, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1030, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);