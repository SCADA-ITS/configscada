INSERT INTO conf.vms_graphic_groups (graphic_group_id, alias, description, label_alias, label_description, enabled, visible, created_at, updated_at) VALUES
(26, 'stop', 'stop', NULL, NULL, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO conf.vms_graphic_groups_elements (graphic_group_id, element_type_id, element_id, enabled, visible, created_at, updated_at) VALUES
(26, 71, 75, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(26, 71, 76, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(26, 71, 77, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(26, 71, 91, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(26, 71, 92, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO conf.vms_graphic_graphic_group_values (graphic_type_id, graphic_id, graphic_group_id, value, enabled, visible, created_at, updated_at) VALUES
(1, 3001,  26, '1' ,true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); 
