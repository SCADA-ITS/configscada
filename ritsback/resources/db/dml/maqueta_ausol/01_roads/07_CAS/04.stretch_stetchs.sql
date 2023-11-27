-- stretch_stretchs

INSERT INTO conf.stretch_stretchs (stretch_stretch_id, alias, description, enabled, visible, created_at, updated_at) 
VALUES
(5, 'CAS-TUNEL', 'Tunel de Casares', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.stretch_stretchs_stretchs (stretch_stretch_id, stretch_id, enabled, visible, created_at, updated_at)
VALUES
(5, 1045, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1046, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1047, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1048, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1049, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1050, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1051, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 1052, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);