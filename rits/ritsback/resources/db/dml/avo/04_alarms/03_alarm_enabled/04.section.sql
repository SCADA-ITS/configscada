DELETE FROM master.element_subtype_active_params
WHERE element_type_id=4 and element_subtype_id=1;

INSERT INTO master.element_subtype_active_params
(element_subtype_id, element_type_id, param_type_id, element_type_param_id, enabled, visible, created_at, updated_at) VALUES
--KINEO
(1, 4, 2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 2, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 4, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 5, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 6, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 7, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 9, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 10, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 12, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 13, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 80, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 81, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 82, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 140, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 141, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 142, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 4, 2, 143, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
