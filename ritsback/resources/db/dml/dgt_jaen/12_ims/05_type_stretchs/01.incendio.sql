-- 
-- Table: conf.ims_incident_type_stretchs
--
INSERT INTO conf.ims_incident_type_stretchs (incident_type_id, stretch_id, default_location_id, operational_plan_url, valid_for_min, alias, description, multi_instance, cctv_scenario_id, spath_id, enabled, visible, created_at, updated_at) VALUES
(101, 1003, 0010015, './resources/ims/operational_plans/incendio.png', null, null, null, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101, 1004, 0200015, './resources/ims/operational_plans/incendio.png', null, null, null, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

--La Cantera
(101, 2003, 0390015, './resources/ims/operational_plans/incendio.png', null, null, null, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101, 2004, 0400015, './resources/ims/operational_plans/incendio.png', null, null, null, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--El corzo
(101, 3003, 0470015, './resources/ims/operational_plans/incendio.png', null, null, null, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(101, 3004, 0480015, './resources/ims/operational_plans/incendio.png', null, null, null, false, null, null, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


