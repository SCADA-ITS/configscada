SET client_min_messages TO WARNING;
DELETE FROM rt.ext_entity_values;
DELETE FROM rt.ext_entities;
DELETE FROM static.ext_entity_type_params;
DELETE FROM static.ext_entity_types;

-- static.ext_entity_types
INSERT INTO static.ext_entity_types(ext_entity_type_id, alias, description, label_alias, label_description, archive_historical, cron_expression, config_params, enabled, visible, created_at, updated_at) values
(1000, 'Waze', 'Waze', 'Waze', 'Waze', true, NULL, NULL, true, true, '2024-10-28 13:25:45.000', '2024-10-28 13:25:50.453');

INSERT INTO static.ext_entity_type_params(ext_entity_type_id, ext_entity_type_param_id, default_value, alias, description, label_alias, label_description, data_type_id, unit_id, enabled, visible, editable, created_at, updated_at) VALUES
(1000, 1, NULL, 'Medida 1 (Numero)', 'Medida 1 (Numero)', 'Medida 1 (Numero)', 'Medida 1 (Numero)', 1, NULL, true, true, false, '2024-10-28 13:28:17.657', '2024-10-28 13:28:22.225'),
(1000, 2, NULL, 'Medida 2 (String)', 'Medida 2 (String)', 'Medida 2 (String)', 'Medida 2 (String)', 2, NULL, true, true, false, '2024-10-28 13:28:17.657', '2024-10-28 13:28:22.225');

INSERT INTO rt.ext_entities(ext_entity_id, ext_entity_type_id, ext_entity_subtype_id, uid, alias, description, coordinates, auto_route, enabled, visible, last_update, generated_at, created_at, updated_at) VALUES
(1, 1000, NULL, '0001', 'Waze 1', 'Waze 1', '[[-1.7107968479350941,42.852237759601536], [-1.7115585952953236,42.852654625352045], [-1.7125671058849234,42.853106882182175], [-1.7140208631709952,42.85361812504564], [-1.7155068069652415,42.85390913832396], [-1.7222927957729197,42.85492374146088], [-1.727630391712554,42.85543103677841]]', true, true, true, '2024-10-28 13:31:01.694', '2024-10-28 13:31:04.774', '2024-10-28 13:31:11.084', '2024-10-28 13:31:19.685'),
(2, 1000, NULL, '0002', 'Waze 2', 'Waze 2', '[[-1.7398042150188455,42.85983475998012],[-1.7356793101384835,42.861378955496974],[-1.7306410334631845,42.8585712985585],[-1.7330865127851134,42.854791558761974],[-1.740968027467233,42.85397078469052],[-1.74379653367091,42.85788016342315],[-1.7398042150188455,42.85983475998012]]', false, true, true, '2024-10-28 13:31:01.694', '2024-10-28 13:31:04.774', '2024-10-28 13:31:11.084', '2024-10-28 13:31:19.685'),
(3, 1000, NULL, '0003', 'Waze 3', 'Waze 3', '[[-1.682315095032693,42.839611822686805]]', false, true, true, '2024-10-28 13:31:01.694', '2024-10-28 13:31:04.774', '2024-10-28 13:31:11.084', '2024-10-28 13:31:19.685');


INSERT INTO rt.ext_entity_values(ext_entity_type_id, ext_entity_id, ext_entity_type_param_id, value, enabled, visible, created_at, updated_at) VALUES
(1000, 1, 1, '10', true, true, '2024-10-28 13:32:08.173', '2024-10-28 13:32:11.100'),
(1000, 1, 2, 'Prueba', true, true, '2024-10-28 13:32:08.173', '2024-10-28 13:32:11.100'),
(1000, 2, 1, '11', true, true, '2024-10-28 13:32:08.173', '2024-10-28 13:32:11.100'),
(1000, 2, 2, 'Prueba 2', true, true, '2024-10-28 13:32:08.173', '2024-10-28 13:32:11.100'),
(1000, 3, 1, '12', true, true, '2024-10-28 13:32:08.173', '2024-10-28 13:32:11.100'),
(1000, 3, 2, 'Mira como corro!', true, true, '2024-10-28 13:32:08.173', '2024-10-28 13:32:11.100');