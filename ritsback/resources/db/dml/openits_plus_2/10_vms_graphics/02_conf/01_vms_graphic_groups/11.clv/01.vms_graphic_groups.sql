INSERT INTO conf.vms_graphic_groups (graphic_group_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(11, 'clv', 'clv', NULL, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.vms_graphic_groups_elements (graphic_group_id, element_type_id, element_id, enabled, visible, created_at, updated_at) VALUES
(11, 71, 15, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 71, 16, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 71, 17, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 71, 18, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 71, 19, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 71, 20, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 71, 21, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 71, 22, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.vms_graphic_graphic_group_values (graphic_type_id, graphic_id, graphic_group_id, value, enabled, visible, created_at, updated_at) VALUES
(1, 315,  11, '160' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 316,  11, '161' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 317,  11, '162' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 318,  11, '163' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 319,  11, '164' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 320,  11, '165' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 321,  11, '166' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 322,  11, '167' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 323,  11, '168' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); 