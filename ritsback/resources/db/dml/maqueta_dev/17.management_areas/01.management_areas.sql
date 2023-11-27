-- static.management_areas
INSERT INTO static.management_areas 
(management_area_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(1, 'Area 1', 'Descripción area 1', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Area 2', 'Descripción area 2', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- conf.management_areas
INSERT INTO conf.management_area_users 
(management_area_id, user_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);