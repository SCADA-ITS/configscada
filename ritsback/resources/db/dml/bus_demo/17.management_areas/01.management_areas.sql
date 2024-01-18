-- static.management_areas
INSERT INTO static.management_areas 
(management_area_id, alias, description, enabled, visible, created_at, updated_at) VALUES
(1, 'LUR-DG-07', 'LUR-DG-07 AVANZA MOVILIDAD GIPUZKOA S.A.', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'LUR-E-01', 'LUR-E-01 EKIALDEBUS S.L.', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'LUR-Z-04', 'LUR-Z-04 BILMAN BUS S.L.', true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- conf.management_areas
INSERT INTO conf.management_area_users 
(management_area_id, user_id, enabled, visible, created_at, updated_at) VALUES
(1, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);